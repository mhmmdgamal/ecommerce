<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="com.ecommerce.general.path.ViewPath" %>
<%@page import="com.ecommerce.general.path.ResourcePath" %>
<%@page import="com.ecommerce.general.path.ControllerPath"%>

<c:import url='<%=ViewPath.header%>' />

<c:choose>
    <c:when test="${item ne null}">

        <h1 class="text-center">${item['name']}</h1>
        <div class="container">
            <div class="row">
                <div class="col-md-3">
                    <img class="img-responsive img-thumbnail center-block" src="<%=ResourcePath.img%>img.png" alt="No Image" />
                </div>

                <div class="col-md-9 item-info">
                    <h2>${item['name']}</h2>
                    <p>${item['description']}</p>
                    <ul class="list-unstyled">
                        <li>
                            <i class="fa fa-calendar fa-fw"></i>
                            <span>Added Date</span> : ${item['addDate']}
                        </li>
                        <li>
                            <i class="fa fa-money fa-fw"></i>
                            <span>Price</span> : ${item['price']}
                        </li>
                        <li>
                            <i class="fa fa-building fa-fw"></i>
                            <span>Made In</span> : ${item['countryMade']}
                        </li>
                        <li>
                            <i class="fa fa-tags fa-fw"></i>
                            <span>Category</span> : <a href="<%=ControllerPath.SHOW_CATEGORY%>?pageid=${item['category']['id']}">${item['category']['name']}</a>
                        </li>
                        <c:choose>
                            <c:when test="${(item['user']['id'] eq sessionScope['userId']) || (item['user']['id'] eq cookie['userId']['value'])}">
                                <li>
                                    <i class="fa fa-user fa-fw"></i>
                                    <span>Added By</span> : <a href="<%=ControllerPath.SHOW_PROFILE%>?userid=${item['user']['id']}">${item['user']['name']}</a>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <li>
                                    <i class="fa fa-user fa-fw"></i>
                                    <span>Added By</span> : <a href="<%=ControllerPath.SHOW_USER%>?userid=${item['user']['id']}">${item['user']['name']}</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                        <li class="tags-items">
                            <i class="fa fa-user fa-tags"></i>
                            <span>Tags</span> : 
                            <c:forEach items="${Helper.explode(item['tags'], ',')}" var="tag">
                                <c:if test="${tag ne null && !tag.isEmpty()}">
                                    <a href="<%=ControllerPath.SHOW_TAG%>?name=${tag.toLowerCase()}">${tag}</a>
                                </c:if>
                            </c:forEach>
                        </li>
                    </ul>
                    <c:if test="${(item['user']['id'] eq sessionScope['userId']) || (item.user.id eq cookie['userId']['value'])}">
                        <a href="<%=ControllerPath.EDIT__ITEM%>?itemid=${item['id']}" class="btn btn-default">Edit Item</a>
                        <a href="<%=ControllerPath.DELETE__ITEM%>?itemid=${item['id']}" class="btn btn-default">Delete Item</a>
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
                                <form id="add-comment-form" action="<%=ControllerPath.ADD_COMMENT%>?itemid=${item['id']}" method="POST">
                                    <textarea name="comment"></textarea>
                                    <!--<input name="add-comment" class="btn btn-primary" type="submit" value="Add Comment">-->
                                </form>
                            </div>
                        </div>
                    </div>
                    <!-- End Add Comment -->
                </c:when>
                <c:otherwise>
                    <a href="<%=ControllerPath.LOGIN%>">Login/Register</a> To Add Comment
                </c:otherwise>
            </c:choose>
            <hr class="custom-hr">
            <div id="comments">
                <c:forEach items="${itemComments}" var="comment">
                    <div class="comment-box">
                        <div class="row">
                            <div class="col-sm-2 text-center">
                                <img class="img-responsive img-thumbnail img-circle center-block" src="<%=ResourcePath.img%>img.png" alt="No Image" />
                                ${comment['user']['name']}
                            </div>
                            <div class="col-sm-10">
                                <p class="lead">${comment['comment']}</p>
                            </div>
                        </div>
                    </div>
                    <hr class="custom-hr">
                </c:forEach>
            </div>
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

<!-- Start Scroll To Top -->
<div class="scroll-to-top">
    <span></span>
</div>
<!-- Start Scroll To Top -->
<div class="footer text-center">
    <div class="container">
        <div class="row">
            <!-- Start Col-Sm-6 -->
            <div class="col-xs-12 col-sm-6">
                <p class="copyright">
                    Made With 
                    <i class="fa fa-heart"></i> 
                    By  Name &copy; 
                    2018
                </p>
            </div>
            <!-- End Col-Sm-6 -->
            <!-- Start Col-Sm-6 -->
            <div class="col-xs-12 col-sm-6">
                <ul class="list-unstyled social text-right">
                    <li>
                        <a target="_blank" href="#">
                            <i class="fa fa-youtube fa-fw fa-2x"></i>
                        </a>
                    </li>
                    <li>
                        <a target="_blank" href="#">
                            <i class="fa fa-facebook fa-fw fa-2x"></i>
                        </a>
                    </li>
                    <li>
                        <a target="_blank" href="#">
                            <i class="fa fa-github fa-fw fa-2x"></i>
                        </a>
                    </li>
                    <br class="visible-xs">
                    <li>
                        <a target="_blank" href="#">
                            <i class="fa fa-twitter fa-fw fa-2x"></i>
                        </a>
                    </li>
                    <li>
                        <a target="_blank" href="#">
                            <i class="fa fa-codepen fa-fw fa-2x"></i>
                        </a>
                    </li>
                    <li>
                        <a target="_blank" href="#">
                            <i class="fa fa-instagram fa-fw fa-2x"></i>
                        </a>
                    </li>
                </ul>
            </div>
            <!-- End Col-Sm-6 -->

        </div>
    </div>
</div>
<script src="<%=ResourcePath.js%>jquery-1.12.1.min.js"></script>
<script>
    $(function () {
        $('textarea[name="comment"]').keypress(function (event) {
            if (event.which === 13) {
                $('#add-comment-form').submit();
                return false;
            }
        });
        var flag = false;// mean success is true 
        $('#add-comment-form').on('submit', function (event) {
            if ($('textarea[name="comment"]').val() === '') {
                alert('enter comment');
                return false;
            }
            event.preventDefault();
            if (flag === true) {
                return false;
            }
            form = $(this);
            requestUrl = form.attr('action');
            requestMethod = form.attr('method');
            requestData = form.serialize(); //read comment

            $.ajax({//define object from XML Http Request 
                url: requestUrl, //action : go to Add Comment controller
                type: requestMethod, //GET OR POST 
                data: requestData, //get Comment(FORM Data)
                dataType: 'json',
                beforeSend: function () {
                    flag = true;
                    $('input[name="add-comment"]').attr('disabled', true);
                },
                success: function (response) {
//                    console.log(results);
                    if (response.success !== null) {
                        $('#comments').prepend(
                                '<div class="comment-box">' +
                                '<div class="row">' +
                                '<div class="col-sm-2 text-center">' +
                                '<img class="img-responsive img-thumbnail img-circle center-block" src="<%=ResourcePath.img%>img.png" alt="No Image" />' +
                                response.data.user +
                                '</div>' +
                                '<div class="col-sm-10">' +
                                '<p class="lead">' + response.data.comment + '</p>' +
                                '</div>' +
                                '</div>' +
                                '</div>' +
                                '<hr class="custom-hr">');
                        $('input[name="add-comment"]').removeAttr('disabled');
                        $('textarea[name="comment"]').val('');
                    }
                    flag = false;
                }
            });
        });
    });
</script>
<script src="<%=ResourcePath.js%>jquery-ui.min.js"></script>
<script src="<%=ResourcePath.js%>bootstrap.min.js"></script>
<script src="<%=ResourcePath.js%>nicescroll.min.js"></script>
<script src="<%=ResourcePath.js%>jquery.selectBoxIt.min.js"></script>
<script src="<%=ResourcePath.js%>ecommerce.js"></script>
</body>
</html>