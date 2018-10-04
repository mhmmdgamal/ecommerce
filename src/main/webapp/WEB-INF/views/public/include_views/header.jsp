<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.ecommerce.general.helper.PathsHelper" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <title>${title}</title>
        <link rel="stylesheet" href="${PathsHelper.getPublicLayout("css/bootstrap.min.css")}" />
        <link rel="stylesheet" href="${PathsHelper.getPublicLayout("css/font-awesome.min.css")}" />
        <link rel="stylesheet" href="${PathsHelper.getPublicLayout("css/jquery-ui.css")}" />
        <link rel="stylesheet" href="${PathsHelper.getPublicLayout("css/jquery.selectBoxIt.css")}" />
        <link rel="stylesheet" href="${PathsHelper.getPublicLayout("css/public.css")}" />
    </head>
    <body>
        
        <c:import url='${PathsHelper.getPublicInclude("navbar")}' />
        <c:import url='${PathsHelper.getPublicInclude("success_error")}' />