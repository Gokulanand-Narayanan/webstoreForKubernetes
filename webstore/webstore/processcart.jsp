<%@page import="com.webstore.core.engine.web.http.EngineCallerService"%>
<%@page import="java.util.Properties"%>
<%@page import="com.webstore.core.engine.web.props.EnginePropertyReader"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Product Cart</title>
</head>
<body>
<%
EnginePropertyReader engineProps = new EnginePropertyReader();
Properties enginePropsObj = engineProps.getEnginePropsObj();
String getUrl = enginePropsObj.getProperty("GET_URL");
//String postUrl = enginePropsObj.getProperty("POST_URL");
String userAgent = enginePropsObj.getProperty("USER_AGENT");
//String postParams = enginePropsObj.getProperty("POST_PARAMS");

out.println("<h1>process engine server send the request to : <h1>"+getUrl);
out.println(EngineCallerService.processCart(getUrl.trim(),userAgent.trim()));

//out.println("<h1>process engine server send the request to : <h1>"+postUrl);
//out.println(EngineCallerService.processAutrentication(postUrl.trim(), userAgent.trim(), postParams.trim()));
%>
</body>
</html>