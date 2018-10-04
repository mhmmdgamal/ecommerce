<%@page import="com.ecommerce.general.helper.PathsHelper" %>

<nav class="navbar navbar-inverse">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#app-nav" aria-expanded="false">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${initParam['adminPath']}dashboard">Home</a>
        </div>
        <div class="collapse navbar-collapse" id="app-nav">
            <ul class="nav navbar-nav">
                <li><a href="${initParam['adminPath']}manage-categories">Categories</a></li>
                <li><a href="${initParam['adminPath']}manage-items">Items</a></li>
                <li><a href="${initParam['adminPath']}manage-users">Users</a></li>
                <li><a href="${initParam['adminPath']}manage-comments">Comments</a></li>
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
                        <li><a href="${initParam['adminPath']}edit-user?userid=${sessionScope['userId']}${cookie['userId']['value']}">Edit Profile</a></li>
                        <li><a href="${initParam['adminPath']}settings">Settings</a></li>
                        <li><a href="${initParam['publicPath']}logout">Logout</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>

<a href="${initParam['customerPath']}">Home Page</a>
<c:import url='${PathsHelper.getPublicInclude("success_error")}' />