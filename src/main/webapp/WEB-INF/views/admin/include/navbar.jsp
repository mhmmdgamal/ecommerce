<%@page import="com.ecommerce.general.path.ViewPath" %>
<%@page import="com.ecommerce.general.path.ControllerPath" %>

<nav class="navbar navbar-inverse">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#app-nav" aria-expanded="false">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="<%=ControllerPath.DASHBOARD_ADMIN %>">Home</a>
        </div>
        <div class="collapse navbar-collapse" id="app-nav">
            <ul class="nav navbar-nav">
                <li><a href="<%=ControllerPath.MANAGE_CATEGORY_ADMIN %>">Categories</a></li>
                <li><a href="<%=ControllerPath.MANAGE_ITEM_ADMIN %>">Items</a></li>
                <li><a href="<%=ControllerPath.MANAGE_USER_ADMIN %>">Users</a></li>
                <li><a href="<%=ControllerPath.MANAGE_COMMENT_ADMIN%>">Comments</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"
                       role="button" aria-haspopup="true" aria-expanded="false">
                        ${sessionScope['fullName']}${cookie['fullName']['value']} 
                        <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="${pageContext['request']['contextPath']}/">Visit Shop</a></li>
                        <li><a href="<%=ControllerPath.EDIT_USER_ADMIN%>?userid=${sessionScope['userId']}${cookie['userId']['value']}">Edit Profile</a></li>
                        <li><a href="${initParam['adminPath']}settings">Settings</a></li>
                        <li><a href="${initParam['publicPath']}logout">Logout</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>

<c:import url='<%=ViewPath.success_error%>' />