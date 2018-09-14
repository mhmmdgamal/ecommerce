<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <title>${(title eq null) ? "Login" : title}</title>
        <link rel="stylesheet" href="${initParam['sourceAdminLayout']}css/bootstrap.min.css" />
        <link rel="stylesheet" href="${initParam['sourceAdminLayout']}css/font-awesome.min.css" />
        <link rel="stylesheet" href="${initParam['sourceAdminLayout']}css/jquery-ui.css" />
        <link rel="stylesheet" href="${initParam['sourceAdminLayout']}css/jquery.selectBoxIt.css" />
        <link rel="stylesheet" href="${initParam['sourceAdminLayout']}css/backend.css" />
    </head>
    <body>