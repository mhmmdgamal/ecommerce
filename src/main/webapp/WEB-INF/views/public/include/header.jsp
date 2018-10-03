<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <title>${title}</title>
        <link rel="stylesheet" href="${initParam['publicLayoutPath']}css/bootstrap.min.css" />
        <link rel="stylesheet" href="${initParam['publicLayoutPath']}css/font-awesome.min.css" />
        <link rel="stylesheet" href="${initParam['publicLayoutPath']}css/jquery-ui.css" />
        <link rel="stylesheet" href="${initParam['publicLayoutPath']}css/jquery.selectBoxIt.css" />
        <link rel="stylesheet" href="${initParam['publicLayoutPath']}css/public.css" />
    </head>
    <body>
        
        <c:import url="${initParam['publicIncludePath']}navbar.jsp" />
        <c:import url="${initParam['publicIncludePath']}success_error.jsp" />