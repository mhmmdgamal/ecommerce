<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="com.ecommerce.general.helper.PathsHelper" %>

<c:import url='${PathsHelper.getPublicInclude("header")}' />

<h1 class="text-center">${user.fullName}</h1>
<div class="information block">
    <div class="container">
        <div class="panel panel-primary">
            <div class="panel-heading"> Information about ${user['name']}</div>
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
            </div>
        </div>
    </div>
</div>
<!--start show item--> 
<div id="my-ads" class="my-ads block">
    <div class="container">
        <div class="panel panel-primary">
            <div class="panel-heading"> Items of ${user['name']}</div>
            <div class="panel-body">
                <c:choose>
                    <c:when test="${userItems.size() > 0}">
                        <div class="row">
                            <c:forEach items="${userItems}" var="item">
                                <div class="col-sm-6 col-md-3">
                                    <div class="thumbnail item-box">
                                        <c:choose>
                                            <c:when test="${item['approve'] eq 1}">
                                                <span class="price-tag">${item['price']}</span>
                                                <img class="img-responsive" src="${PathsHelper.getPublicImg('img.png')}" alt="No Image" />
                                                <div class="caption">
                                                    <h3><a href="${initParam['customerPath']}show-item?itemid=${item['id']}">${item['name']}</a></h3>
                                                    <p>${item['description']}</p>
                                                    <div class="date">${item.addDate}</div>
                                                </div>
                                            </c:when>
                                            <c:otherwise>
                                                <img class="img-responsive" src="${PathsHelper.getPublicImg('2.png')}" alt="No Image" />
                                                <div class="caption">
                                                    <h3>there is a new item from ( ${user['name']} ) will be displayed later ..
                                                        <br>follow ( ${user['name']} ) to receive notification</h3>
                                                </div>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="well">Sorry There's No Items To Show</div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</div>
<div class="my-comments block">
    <div class="container">
        <div class="panel panel-primary">
            <div class="panel-heading">Latest Comments</div>
            <div class="panel-body">
                <c:choose>
                    <c:when test="${userComments.size() > 0}">
                        <c:forEach items="${userComments}" var="comment">
                            <div class="well">${comment['comment']}</div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <div class="well">There's No Comments to Show</div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</div>

<c:import url='${PathsHelper.getPublicInclude("footer")}' />
