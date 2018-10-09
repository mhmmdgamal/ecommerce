<%@page import="com.ecommerce.general.path.ControllerPath"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.ecommerce.general.path.ResourcePath" %>

<nav class="navbar navbar-inverse">
        <!--Start Show Full Name-->
    <div class="upper-bar">
        <div class="container">
            <c:choose>
                <c:when test="${(sessionScope['user'] ne null) || (cookie['user']['value'] ne null)}">
                    <img class="my-image img-thumbnail img-circle" src="<%=ResourcePath.img%>img.png" alt="No Image" />
                    <div class="btn-group my-info">
                        <button class="btn btn-primary dropdown-toggle"
                                type="button" data-toggle="dropdown">
                            ${sessionScope['fullName']}
                            ${cookie['fullName']['value']}
                            <span class="caret"></span>
                        </button>
                        <ul class="dropdown-menu">
                            <li><a href="<%=ControllerPath.SHOW_PROFILE %>">My Profile</a></li>
                            <li><a href="<%=ControllerPath.ADD_ITEM %>">New Item</a></li>
                            <li><a href="<%=ControllerPath.LOGOUT %>">Logout</a></li>
                        </ul>
                    </div>
                </c:when>
                <c:otherwise>
                    <a href="<%=ControllerPath.LOGIN %>">
                        <span class="pull-right">Login/Signup</span>
                    </a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    <!--End Show Full Name-->
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
            <a class="navbar-brand" href="<%=ControllerPath.HOME %>">Homepage</a>
        </div>
        <div class="collapse navbar-collapse" id="app-nav">
            <ul class="nav navbar-nav navbar-right">
                <c:forEach items="${navCategories}" var="category">
                    <li>
                        <a href="<%=ControllerPath.SHOW_CATEGORY %>?pageid=${category['id']}">${category['name']}</a>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>
</nav>