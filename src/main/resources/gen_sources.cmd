@set Target=D:\Sources\IdeaProjects\gisconn\src\main\java\com\fdctech\gisconn\gen
@set Source=D:\Sources\IdeaProjects\gisconn\src\main\resources\

rem xjc -d %Target% %Source%ChargeCreation.xsd
rem xjc -d %Target% %Source%ExportCharges.xsd
rem xjc -d %Target% %Source%ExportNotice.xsd
rem xjc -d %Target% %Source%ExportPayments.xsd
rem xjc -d %Target% %Source%ExportQuittances.xsd
rem xjc -d %Target% %Source%ExportRefunds.xsd
rem xjc -d %Target% %Source%ForcedAcknowledgement.xsd
xjc -d %Target% %Source%ImportCharges.xsd
rem xjc -d %Target% %Source%ImportPayments.xsd
rem xjc -d %Target% %Source%ImportRefunds.xsd
rem xjc -d %Target% %Source%SubscriptionService.xsd