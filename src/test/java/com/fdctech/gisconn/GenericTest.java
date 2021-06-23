package com.fdctech.gisconn;

import com.fdctech.gisconn.core.file.FileUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = com.fdctech.gisconn.Application.class)
@TestPropertySource(locations = "classpath:test.properties")
public abstract class GenericTest {
    @Resource
    NamedParameterJdbcTemplate nJdbcTemplate;
    @Value("${spring.datasource.url}")
    String dbName;

    @Before
    public void truncateDB() {
        if (dbName.equals("jdbc:sqlserver://localhost:1433;database=gisgmp")) {
            JdbcTemplate jdbcTemplate = nJdbcTemplate.getJdbcTemplate();
            jdbcTemplate.execute("truncate table message");
            jdbcTemplate.execute("truncate table template");
            jdbcTemplate.execute("truncate table state");
        }
    }

    private String[] pushBufferFileName = new String[6];
    private String[] pushBufferFileData = new String[6];

    @Before
    public void pushBuffer() throws IOException {
        File[] files = {
                new File("D:\\Sources\\IdeaProjects\\gisconn\\src\\test\\resources\\{fe3a435e-a30f-4e7f-a844-87e1ba3933f5.xml}.error"),
                new File("D:\\Sources\\IdeaProjects\\gisconn\\src\\test\\resources\\sentSample.xml"),
                new File("D:\\Sources\\IdeaProjects\\gisconn\\src\\test\\resources\\import.xml"),
                new File("D:\\Sources\\IdeaProjects\\gisconn\\src\\test\\resources\\statusSample.xml"),
                new File("D:\\Sources\\IdeaProjects\\gisconn\\src\\test\\resources\\primarySample.xml"),
                new File("D:\\Sources\\IdeaProjects\\gisconn\\src\\test\\resources\\requestSample.xml")
        };

        for (int i = 0; i < files.length; i++) {
            pushBufferFileName[i] = files[i].getAbsolutePath();
            pushBufferFileData[i] = FileUtil.getStringContent(files[i]);
        }

    }

    @After
    public void popBuffer() throws IOException {
        for (int i = 0; i < pushBufferFileName.length; i++) {
            new FileOutputStream(pushBufferFileName[i]).write(pushBufferFileData[i].getBytes());
        }
    }
}
