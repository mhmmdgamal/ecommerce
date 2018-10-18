<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.ecommerce.general.path.ViewPath" %>
<%@page import="com.ecommerce.general.path.ResourcePath" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <title>${title}</title>
        <link rel="shortcut icon" href="#">
        <link rel="stylesheet" href="<%=ResourcePath.css%>bootstrap.min.css" />
        <link rel="stylesheet" href="<%=ResourcePath.css%>font-awesome.min.css" />
        <link rel="stylesheet" href="<%=ResourcePath.css%>jquery-ui.css" />
        <link rel="stylesheet" href="<%=ResourcePath.css%>jquery.selectBoxIt.css" />
        <link rel="stylesheet" href="<%=ResourcePath.css%>ecommerce.css" />
    </head>
    <body>
        <c:import url='<%=ViewPath.navbar%>' />
        <c:import url='<%=ViewPath.success_error%>' />