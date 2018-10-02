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
        <div class="upper-bar">
            <div class="container">
                <c:choose>
                    <c:when test="${(sessionScope['user'] ne null) || (cookie['user']['value'] ne null)}">
                        <img class="my-image img-thumbnail img-circle" src="${initParam['publicImgPath']}img.png" alt="No Image" />
                        <div class="btn-group my-info">
                            <span class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                ${sessionScope['fullName']}
                                ${cookie['fullName']['value']}
                                <span class="caret"></span>
                            </span>
                            <ul class="dropdown-menu">
                                <li><a href="${initParam['customerPath']}profile">My Profile</a></li>
                                <li><a href="${initParam['customerPath']}new-item">New Item</a></li>
                                <li><a href="${initParam['customerPath']}logout">Logout</a></li>
                            </ul>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <a href="${initParam['customerPath']}login">
                            <span class="pull-right">Login/Signup</span>
                        </a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <c:import url="${initParam['publicIncludePath']}navbar.jsp" />
        <c:import url="${initParam['publicIncludePath']}success_error.jsp" />