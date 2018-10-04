<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.ecommerce.general.helper.PathesHelper" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <title>${title}</title>
        <link rel="stylesheet" href="${PathesHelper.getPublicLayout("css/bootstrap.min.css")}" />
        <link rel="stylesheet" href="${PathesHelper.getPublicLayout("css/font-awesome.min.css")}" />
        <link rel="stylesheet" href="${PathesHelper.getPublicLayout("css/jquery-ui.css")}" />
        <link rel="stylesheet" href="${PathesHelper.getPublicLayout("css/jquery.selectBoxIt.css")}" />
        <link rel="stylesheet" href="${PathesHelper.getPublicLayout("css/public.css")}" />
    </head>
    <body>
        
        <c:import url='${PathesHelper.getPublicInclude("navbar")}' />
        <c:import url='${PathesHelper.getPublicInclude("success_error")}' />