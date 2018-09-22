<%@page import="com.ecommerce.helper.Helper"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:import url="includes/templates/header.jsp" />
<c:import url="includes/templates/check_one_error.jsp" />

<c:choose>
    <c:when test="${item ne null}">

        <h1 class="text-center">${item.name}</h1>
        <div class="container">
            <div class="row">
                <div class="col-md-3">
                    <img class="img-responsive img-thumbnail center-block" src="${initParam['customerImgPath']}img.png" alt="No Image" />
                </div>

                <div class="col-md-9 item-info">
                    <h2>${item.name}</h2>
                    <p>${item.description}</p>
                    <ul class="list-unstyled">
                        <li>
                            <i class="fa fa-calendar fa-fw"></i>
                            <span>Added Date</span> : ${item.addDate}
                        </li>
                        <li>
                            <i class="fa fa-money fa-fw"></i>
                            <span>Price</span> : ${item.price}
                        </li>
                        <li>
                            <i class="fa fa-building fa-fw"></i>
                            <span>Made In</span> : ${item.countryMade}
                        </li>
                        <li>
                            <i class="fa fa-tags fa-fw"></i>
                            <span>Category</span> : <a href="${initParam['customerPath']}categories?pageid=${item.category.id}">${item.category.name}</a>
                        </li>
                        <li>
                            <i class="fa fa-user fa-fw"></i>
                            <span>Added By</span> : <a href="${initParam['customerPath']}users?userid=${item.user.id}">${item.user.name}</a>
                        </li>
                        <li class="tags-items">
                            <i class="fa fa-user fa-tags"></i>
                            <span>Tags</span> : 
                            <c:forEach items="${Helper.explode(item.tags, ',')}" var="tag">
                                <a href="${initParam['customerPath']}tags?name=${tag.toLowerCase()}">${tag}</a>
                            </c:forEach>
                        </li>
                    </ul>
                    <c:if test="${(item.user.id eq sessionScope['userId']) || (item.user.id eq cookie['userId']['value'])}">
                        <a href="${initParam['customerPath']}edit-item?itemid=${item.id}" class="btn btn-default">Edit Item</a>
                    </c:if>
                </div>
            </div>
            <hr class="custom-hr">
            <c:choose>
                <c:when test="${(sessionScope['user'] ne null) || (cookie['user']['value'] ne null)}">
                    <!-- Start Add Comment -->
                    <div class="row">
                        <div class="col-md-offset-3">
                            <div class="add-comment">
                                <h3>Add Your Comment</h3>
                                <form action="${initParam['customerPath']}items?itemid=${item.id}" method="POST">
                                    <textarea name="comment" required></textarea>
                                    <input class="btn btn-primary" type="submit" value="Add Comment">
                                </form>
                            </div>
                        </div>
                    </div>
                    <!-- End Add Comment -->
                </c:when>
                <c:otherwise>
                    <a href="${initParam['customerPath']}login">Login/Register</a> To Add Comment
                </c:otherwise>
            </c:choose>
            <hr class="custom-hr">
            <c:forEach items="${comments}" var="comment">

                <div class="comment-box">
                    <div class="row">
                        <div class="col-sm-2 text-center">
                            <img class="img-responsive img-thumbnail img-circle center-block" src="${initParam['customerImgPath']}img.png" alt="No Image" />
                            ${comment.user.name}
                        </div>
                        <div class="col-sm-10">
                            <p class="lead">${comment.comment}</p>
                        </div>
                    </div>
                </div>
                <hr class="custom-hr">
            </c:forEach>
        </c:when>
        <c:otherwise>
            <div class="container">
                <div class="alert alert-danger alert-dismissable">
                    <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                    There's no Such ID Or This Item Is Waiting Approval
                </div>
            </div>
        </c:otherwise>
    </c:choose>
</div>
<c:import url="includes/templates/footer.jsp" />
