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
        <nav class="navbar navbar-inverse">
            <div class="container">
                <div class="navbar-header">
                    <button 
                        type="button" 
                        class="navbar-toggle collapsed" 
                        data-toggle="collapse" 
                        data-target="#app-nav" 
                        aria-expanded="false">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="${initParam['customerPath']}">Homepage</a>
                </div>
                <div class="collapse navbar-collapse" id="app-nav">
                    <ul class="nav navbar-nav navbar-right">
                        <c:forEach items="${navCategories}" var="category">
                            <li>
                                <a href="${initParam['customerPath']}categories?pageid=${category['id']}">${category['name']}</a>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </nav> 
        <c:import url="${initParam['publicIncludePath']}success_error.jsp" />
        
        <%--
        <error> i can't get value by Servelt Context in controller 
                <c:set var="url" value="${header.referer}"
                       scope="application" />

        <c:out value="${applicationScope.url}"/>
        <c:set var="previousPage" value="${header.referer}" scope="request"/>
        <%  session.setAttribute("previousPage", request.getHeader("referer"));
//            request.getServletContext().setAttribute("previousPage", request.getHeader("referer"));
            out.print(session.getAttribute("previousPage"));
        %>

        <input type="hidden" name="previousPage" value="${header.referer}" />
        --%>
