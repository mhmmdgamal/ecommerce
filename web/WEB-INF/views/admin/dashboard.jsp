<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="includes/templates/header.jsp"/>
<c:import url="includes/templates/navbar.jsp"/>

<div class="home-stats">
    <h1 class="text-center">Dashboard</h1>
    <c:import url="includes/templates/check_one_error.jsp"/>

    <div class="container text-center">
        <div class="row">
            <div class="col-md-3">
                <div class="stat st-members">
                    <i class="fa fa-users"></i>
                    <div class="info">
                        Total Users
                        <span>
                            <a href="${initParam['adminPath']}manage-users">${numUsers}</a>
                        </span>
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="stat st-pending">
                    <i class="fa fa-user-plus"></i>
                    <div class="info">
                        Pending Users
                        <span>
                            <a href="${initParam['adminPath']}users?action=Manage&page=Pending">
                                ${numPendingUsers}
                            </a>
                        </span>
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="stat st-items">
                    <i class="fa fa-tag"></i>
                    <div class="info">
                        Total Items
                        <span>
                            <a href="${initParam['adminPath']}items">${numItems}</a>
                        </span>
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="stat st-comments">
                    <i class="fa fa-comments"></i>
                    <div class="info">
                        Total Comments
                        <span>
                            <a href="${initParam['adminPath']}comments">${numComments}</a>
                        </span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="latest">
    <div class="container">
        <div class="row">
            <div class="col-sm-6">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <i class="fa fa-users"></i> 
                        Latest ${latestUsers.size()} Registered Users 
                        <span class="toggle-info pull-right">
                            <i class="fa fa-plus fa-lg"></i>
                        </span>
                    </div>
                    <div class="panel-body">
                        <ul class="list-unstyled latest-users">
                            <c:choose>
                                <c:when test="${latestUsers.size() gt 0}">
                                    <c:forEach items="${latestUsers}" var="user">
                                        <li>
                                            ${user.name}

                                            <a href='${initParam['adminPath']}edit-user?userid=${user.id}'>
                                                <span class='btn btn-success pull-right'>
                                                    <i class="fa fa-edit"></i> Edit
                                                </span>
                                            </a>

                                            <a href='${initParam['adminPath']}delete-user?userid=${user.id}'>
                                                <span class='btn btn-danger pull-right confirm'>
                                                    <i class="fa fa-edit"></i> Delete
                                                </span>
                                            </a>
                                            <c:if test="${user.regStatus eq 0}">
                                                <a href='${initParam['adminPath']}active-user?userid=${user.id}'>
                                                    <span class='btn btn-info pull-right'>
                                                        <i class='fa fa-check'></i> Activate
                                                    </span>
                                                </a>
                                            </c:if>

                                        </li>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    There's No Users To Show
                                </c:otherwise>
                            </c:choose>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="col-sm-6">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <i class="fa fa-tag"></i> Latest ${latestItems.size()} Items 
                        <span class="toggle-info pull-right">
                            <i class="fa fa-plus fa-lg"></i>
                        </span>
                    </div>
                    <div class="panel-body">
                        <ul class="list-unstyled latest-users">
                            <c:choose>
                                <c:when test="${latestItems.size() gt 0}">
                                    <c:forEach items="${latestItems}" var="item">
                                        <li>
                                            ${item.name}

                                            <a href='${initParam['adminPath']}items?action=Edit&itemid=${item.id}'>
                                                <span class='btn btn-success pull-right'>
                                                    <i class="fa fa-edit"></i> Edit
                                                </span>
                                            </a>

                                            <a href='${initParam['adminPath']}items?action=Delete&itemid=${item.id}'>
                                                <span class='btn btn-danger pull-right confirm'>
                                                    <i class="fa fa-edit"></i> Delete
                                                </span>
                                            </a>
                                            <c:if test="${item.approve eq 0}">
                                                <a href='${initParam['adminPath']}items?action=Approve&itemid=${item.id}'>
                                                    <span class='btn btn-info pull-right'>
                                                        <i class='fa fa-check'></i> Activate
                                                    </span>
                                                </a>
                                            </c:if>    
                                        </li>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    There's No Users To Show
                                </c:otherwise>
                            </c:choose>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <!-- Start Latest Comments -->
        <div class="row">
            <div class="col-sm-6">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <i class="fa fa-comments-o"></i> 
                        Latest ${latestComments.size()} Comments 
                        <span class="toggle-info pull-right">
                            <i class="fa fa-plus fa-lg"></i>
                        </span>
                    </div>
                    <div class="panel-body">
                        <c:choose>
                            <c:when test="${latestComments.size() gt 0}">
                                <c:forEach items="${latestComments}" var="comment">
                                    <div class="comment-box">
                                        <span class="member-n">
                                            <a href="${applicationScope[""]}users?action=Edit&userid=${comment.user.id}">
                                                ${comment.user.name}
                                            </a>
                                        </span>
                                        <p class="member-c">${comment.comment}</p>
                                    </div>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                There's No Users To Show
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>
        <!-- End Latest Comments -->
    </div>
</div>
<c:import url="includes/templates/footer.jsp"/>
