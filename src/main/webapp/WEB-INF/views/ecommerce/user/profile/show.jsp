<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="com.ecommerce.general.path.ViewPath" %>
<%@page import="com.ecommerce.general.path.ResourcePath" %>

<c:import url='<%=ViewPath.header %>' />

<h1 class="text-center">My Profile</h1>
<div class="information block">
    <div class="container">
        <div class="panel panel-primary">
            <div class="panel-heading">My Information</div>
            <div class="panel-body">
                <ul class="list-unstyled">
                    <li>
                        <i class="fa fa-unlock-alt fa-fw"></i>
                        <span>Login Name</span> : ${user['name']}
                    </li>
                    <li>
                        <i class="fa fa-envelope-o fa-fw"></i>
                        <span>Email</span> : ${user['email']}
                    </li>
                    <li>
                        <i class="fa fa-user fa-fw"></i>
                        <span>Full Name</span> : ${user['fullName']}
                    </li>
                    <li>
                        <i class="fa fa-calendar fa-fw"></i>
                        <span>Registered Date</span> : ${user['date']}
                    </li>
                </ul>
                <!--<improve>Button not work-->
                <a href="${initParam['customerPath']}edit-profile" class="btn btn-default">Edit Information</a>
            </div>
        </div>
    </div>
    <div class="container">
        <div class="panel panel-primary">
            <div class="panel-heading">My Items</div>
            <div class="panel-body">
                <c:choose>
                    <c:when test="${userItems.size() > 0}">
                        <div class="row">
                            <c:forEach items="${userItems}" var="item">
                                <div class="col-sm-6 col-md-3">
                                    <div class="thumbnail item-box">
                                        <c:if test="${item['approve'] eq 0}">
                                            <span class="approve-status">Waiting Approval</span>
                                        </c:if>
                                        <span class="price-tag">${item['price']}</span>
                                        <img class="img-responsive" src="<%=ResourcePath.img %>img.png" alt="No Image" />
                                        <div class="caption">
                                            <h3><a href="${initParam['customerPath']}show-item?itemid=${item['id']}">${item['name']}</a></h3>
                                            <p>${item['description']}</p>
                                            <div class="date">${item['addDate']}</div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="well">Sorry There's No Items To Show, Create <a href="${initParam['customerPath']}new-item">New Item</a></div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</div>
<div class="my-comments block">
    <div class="container">
        <div class="panel panel-primary">
            <div class="panel-heading">Your Latest Activities</div>
            <div class="panel-body">
                <c:choose>
                    <c:when test="${requestScope['userComments'].size() gt 0}">
                        <div class="container"><div class="table-responsive">
                                <table class="main-table text-center table table-bordered">
                                    <tr>
                                        <td>Comment</td>
                                        <td>Item Name</td>
                                        <td>Adding Date</td>
                                        <td>Control</td>
                                    </tr>
                                    <c:forEach items="${requestScope['userComments']}" var="comment">
                                        <tr>
                                            <td>${comment['comment']}</td>
                                            <td>
                                                <a href='${initParam['customerPath']}show-item?itemid=${comment['item']['id']}'>
                                                    <i>${comment['item']['name']}</i></a>
                                            </td>
                                            <td>${comment['addDate']}</td>
                                            <td>
                                                <a href='${initParam['customerPath']}edit-comment?commentid=${comment['id']}' 
                                                   class='btn btn-success'><i class='fa fa-edit'></i> Edit</a>

                                                <a href='${initParam['customerPath']}delete-comment?commentid=${comment['id']}' 
                                                   class='btn btn-danger confirm'><i class='fa fa-close'></i> Delete</a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="well">There's No Comments to Show</div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</div>

<c:import url='<%=ViewPath.footer %>' />
