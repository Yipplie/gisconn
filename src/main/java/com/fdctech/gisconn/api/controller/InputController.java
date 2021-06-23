package com.fdctech.gisconn.api.controller;

import com.fdctech.coreandr.exception.DAOException;
import com.fdctech.coreandr.http.Response;
import com.fdctech.gisconn.api.model.entity.MessageFile;
import com.fdctech.gisconn.api.model.entity.OrderState;
import com.fdctech.gisconn.api.model.entity.TemplateFile;
import com.fdctech.gisconn.api.model.entity.support.State;
import com.fdctech.gisconn.core.business.MessageFileBusinessService;
import com.fdctech.gisconn.core.business.OrderStateBusinessService;
import com.fdctech.gisconn.core.business.TemplateFileBusinessService;
import com.fdctech.gisconn.core.creator.MessageFactory;
import com.fdctech.gisconn.core.file.FileManager;
import com.fdctech.nautils.JSON.JSONArray;
import com.fdctech.nautils.JSON.JSONException;
import com.fdctech.nautils.JSON.JSONObject;
import com.fdctech.nautils.time.TimeUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Base64;
import java.util.List;

@Controller
public class InputController {
    private Logger logger = LogManager.getLogger(InputController.class);
    @Value("${order_id_key}")
    private String orderIdKey;
    @Value("${order_type_key}")
    private String orderTypeKey;

    @Resource
    private MessageFactory messageFactory;
    @Resource
    private MessageFileBusinessService messageFileBusinessService;
    @Resource
    private TemplateFileBusinessService templateFileBusinessService;
    @Resource
    private OrderStateBusinessService orderStateBusinessService;
    @Resource
    private FileManager fileManager;


    @PostMapping("request")
    public ResponseEntity createNewMessage(@RequestBody JSONObject body) throws DAOException {
        if (!body.has(orderIdKey))
            return Response.missingFieldResult(orderIdKey);
        if (!body.has(orderTypeKey))
            return Response.missingFieldResult(orderTypeKey);

        try {
            MessageFile messageFile = messageFactory.createMessage(body);
            MessageFile old = messageFileBusinessService.getCurrentEntityByCommonID(messageFile.getCommonId());
            if (old != null) {
                String badRequestErrorMessage = String.format("order_id %s already exist", messageFile.getCommonId());
                logger.error(badRequestErrorMessage);
                return Response.restExceptionResult(
                        badRequestErrorMessage,
                        HttpStatus.BAD_REQUEST
                );
            }
            messageFileBusinessService.save(messageFile);
            logger.info(
                    String.format("%s request with order id %s was created",
                            body.opt(orderTypeKey),
                            body.opt(orderIdKey))
            );
            orderStateBusinessService.save(
                    new OrderState(messageFile.getCommonId(),
                            State.RECEIVED.toString())
            );
        } catch (Exception err) {
            logger.error(err);
            return Response.serverErrorResult(err.getMessage());
        }

        return Response.okResult();
    }

    @PostMapping("command/{commandType}")
    public ResponseEntity createCommand(@PathVariable String commandType) {
        if (commandType.equals("kick")) {
            List<OrderState> states = orderStateBusinessService.getAllWithState(State.RECEIVED);
            for (OrderState state : states) {
                try {
                    MessageFile messageFile = messageFileBusinessService.getCurrentEntityByCommonID(state.getOrderId());
                    messageFactory.saveMessage(messageFile);
                    state.setStatus(State.STARTED.toString());
                    orderStateBusinessService.save(state);
                } catch (Exception e) {
                    logger.error(e);
                    return Response.serverErrorResult(e.getMessage());
                }
            }
            return Response.okResult();
        } else if (commandType.equals("freeMemory")) {
            try {
                fileManager.freeMemory();
                return Response.okResult();
            } catch (Exception e) {
                logger.error(e);
                return Response.serverErrorResult(e.getMessage());
            }
        } else return Response.restExceptionResult(
                String.format("Unexpected command %s", commandType),
                HttpStatus.BAD_REQUEST);
    }


    @PutMapping("template/{orderType}")
    public ResponseEntity createNewTemplate(@PathVariable String orderType, @RequestBody JSONObject body) throws DAOException {
        try {
            byte[] data = Base64.getDecoder().decode(body.getString("data"));

            TemplateFile templateFile = new TemplateFile();
            templateFile.setBody(new String(data));
            templateFile.setName(orderType);

            templateFileBusinessService.save(templateFile);
            return Response.okResult();
        } catch (JSONException e) {
            logger.error(e);
            return Response.restExceptionResult(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("status/order/{orderId}")
    public ResponseEntity getStatusByOrderId(@PathVariable Long orderId) {
        OrderState orderState = orderStateBusinessService.getEntityByOrderId(orderId);
        if (orderState == null)
            return Response.restExceptionResult(String.format("No order with id %s", orderId), HttpStatus.BAD_REQUEST);

        return Response.okResult("status", orderState.getStatus());
    }

    @GetMapping("orders/status/{status}")
    public ResponseEntity getWithStatus(@PathVariable String status) {
        try {
            State state = State.valueOf(status);
            List<OrderState> orders = orderStateBusinessService.getAllWithState(state);
            JSONArray result = new JSONArray();
            for (OrderState os : orders)
                result.put(os.getOrderId());

            return Response.okResult(result);
        }catch (IllegalArgumentException err){
            logger.error(err);
            return Response.restExceptionResult(
                    String.format("No status registered with name %s", status),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("orders/unfinished")
    public ResponseEntity getUnfinished() {
        List<OrderState> orders = orderStateBusinessService.getAllUnfinished();
        JSONArray result = new JSONArray();
        for (OrderState os : orders)
            result.put(os.getOrderId());
        return Response.okResult(result);
    }

    @GetMapping("orders/")
    public ResponseEntity getWithStatusInPeriod(
            @Param("from")Long from,
            @Param("to")Long to,
            @Param("status") String status) {
        try {
            List<MessageFile> files = messageFileBusinessService.getAllWithStateInPeriod(
                    TimeUtils.toLocalDateTime(from),
                    TimeUtils.toLocalDateTime(to),
                    State.valueOf(status)
            );
            JSONArray result = new JSONArray();
            for (MessageFile file : files)
                result.put(
                    new JSONObject()
                        .put("order_id", file.getCommonId())
                        .put("body", file.getRawTextContent())
                );
            return Response.okResult(result);
        }catch (IllegalArgumentException err){
            logger.error(err);
            return Response.restExceptionResult(
                    String.format("No status registered with name %s", status),
                    HttpStatus.BAD_REQUEST);
        }
    }

}
