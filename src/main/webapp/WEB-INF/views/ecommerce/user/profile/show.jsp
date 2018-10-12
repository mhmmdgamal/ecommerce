<%@page import="com.ecommerce.general.path.ControllerPath"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="com.ecommerce.general.path.ViewPath" %>
<%@page import="com.ecommerce.general.path.ResourcePath" %>

<c:import url='<%=ViewPath.header%>' />

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
                <a href="<%=ControllerPath.EDIT_PROFILE%>" class="btn btn-default">Edit Information</a>
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
                                        <img class="img-responsive" src="<%=ResourcePath.img%>img.png" alt="No Image" />
                                        <div class="caption">
                                            <h3><a href="<%=ControllerPath.SHOW__ITEM%>?itemid=${item['id']}">${item['name']}</a></h3>
                                            <p>${item['description']}</p>
                                            <div class="date">${item['addDate']}</div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="well">Sorry There's No Items To Show, Create <a href="<%=ControllerPath.ADD_ITEM%>">New Item</a></div>
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
                                                <a href='<%=ControllerPath.SHOW__ITEM%>?itemid=${comment['item']['id']}'>
                                                    <i>${comment['item']['name']}</i></a>
                                            </td>
                                            <td id="${comment['id']}">${comment['addDate']}</td>
                                            <td>
                                                <button type="button" class="btn btn-info btn-lg"
                                                        data-toggle="modal" data-target="#myModal">
                                                    Edit 
                                                </button>
                                                <!-- show Modal -->
                                                <div class="modal fade" id="myModal" role="dialog">
                                                    <div class="modal-dialog">

                                                        <!-- Modal content-->
                                                        <div class="modal-content">
                                                            <div class="modal-header">
                                                                <button type="button" class="close" data-dismiss="modal">&times;</button>
                                                                <h4 class="modal-title">Modal Header</h4>
                                                            </div>
                                                            <form action="<%=ControllerPath.EDIT_COMMENT%>?commentid=${comment['id']}" method="POST">
                                                                <div class="modal-body">
                                                                    <input type="input" value="${comment['comment']}" name="comment"/>
                                                                </div>
                                                                <div class="modal-footer">
                                                                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                                                </div>
                                                                <button type="submit" name="edit-comment-model" >Edit </button>
                                                            </form>
                                                        </div>
                                                    </div>
                                                </div>
                                                <!--  end Modal -->

                                                <a href='<%=ControllerPath.DELETE_COMMENT%>?commentid=${comment['id']}' 
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

<c:import url='<%=ViewPath.footer%>' />

<script src="<%=ResourcePath.js%>jquery-1.12.1.min.js"></script>
<script>
    $(function () {
//        $('textarea[name="comment"]').keypress(function (event) {
//            if (event.which === 13) {
//                $('#add-comment-form').submit();
//                return false;
//            }
//        });
    var flag = false;
//        $('#add-comment-form').on('submit', function (event) {
//            if ($('textarea[name="comment"]').val() === '') {
//                alert('enter comment');
//                return false;
//            }
//            event.preventDefault();
//            if (flag === true) {
//                return false;
//            }
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
                            $('button[name="edit-comment-model"]').attr('disabled', true);
                    },
                    success: function (response) {
                    var commentid = "#" + response.data.commentid;
                            $(commentid).text(response.data.comment);
                    }
            });
    });
    }
    );
</script>
<script src="<%=ResourcePath.js%>jquery-ui.min.js"></script>
<script src="<%=ResourcePath.js%>bootstrap.min.js"></script>
<script src="<%=ResourcePath.js%>nicescroll.min.js"></script>
<script src="<%=ResourcePath.js%>jquery.selectBoxIt.min.js"></script>
<script src="<%=ResourcePath.js%>ecommerce.js"></script>
