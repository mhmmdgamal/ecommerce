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
                        <div class="container">
                            <div class="table-responsive">
                                <table class="main-table text-center table table-bordered">
                                    <tr>
                                        <td>Comment</td>
                                        <td>Item Name</td>
                                        <td>Adding Date</td>
                                        <td>Control</td>
                                    </tr>
                                    <c:forEach items="${requestScope['userComments']}" var="comment">
                                        <tr id="row${comment['id']}">
                                            <td id="${comment['id']}">${comment['comment']}</td>
                                            <td>
                                                <a href='<%=ControllerPath.SHOW__ITEM%>?itemid=${comment['item']['id']}'>
                                                    ${comment['item']['name']}</a>
                                            </td>
                                            <td>${comment['addDate']}</td>
                                            <td>
                                                <!--Edit Comment button-->
                                                <a href="#" class="edit-comment btn btn-info" data-toggle="modal" data-target="#myModal">
                                                    <i class='fa fa-edit'></i> Edit 
                                                </a>
                                                <a href='#' class='delete-comment btn btn-danger'>
                                                    <i class='fa fa-close'></i> Delete
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    <!-- show Modal -->
                                    <div class="modal text-center" id="myModal" role="dialog">
                                        <div class="modal-dialog modal-sm">
                                            <!-- Modal content-->
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                                                    <h4 class="modal-title">Modal Header</h4>
                                                </div>
                                                <form id='edit-comment-form' action='<%=ControllerPath.EDIT_COMMENT%>' method='POST'>
                                                    <div class="modal-body">
                                                        <input type="hidden" name="commentid"/>
                                                        <input type="text" name="comment"/>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-default close-modal" data-dismiss="modal">Close</button>
                                                        <button type="submit" class="btn btn-default" >Edit </button>
                                                    </div>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                    <!--  end Modal -->
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

<script src="<%=ResourcePath.js%>jquery-1.12.1.min.js"></script>
<script>
    $(function () {
        // var to store <td> data that contain comment id and value
        let commentData;
        // var to store comment id 
        let commentId;
        // var to store comment value
        let comment;
        // store object input of modal that will contain comment value
        let modalComment = $('.modal .modal-content .modal-body input[type="text"]');
        // store object input of modal that will contain comment id
        let modalId = $('.modal .modal-content .modal-body input[type="hidden"]');
        // when click edit button do:
        $('.edit-comment').click(function (event) {
            console.log("error");
            // prevent the form from making the default action (submit)
            event.preventDefault();

            // git td contain comment data
            commentData = $(this).parent().parent().find('td').first();
            // get comment id
            commentId = commentData.attr('id');
            // get comment value
            comment = commentData.text();
            // set comment id to modal
            modalId.val(commentId);
            // set comment value to comment
            modalComment.val(comment);
        });
        // when click Delete button do:
        $('.delete-comment').click(function (event) {
            // prevent the form from making the default action (submit)
            event.preventDefault();

            if (confirm("Are you sure?")) {
                // get comment id
                commentId = $(this).parent().parent().find('td').first().attr('id');

                // send request with ajax
                $.ajax({//define object from XML Http Request 
                    url: '<%=ControllerPath.DELETE_COMMENT%>', //action : go to Add Comment controller
                    type: 'GET', //GET OR POST 
                    data: {commentid: commentId}, //get Comment(FORM Data)
                    dataType: 'json', // data will be deal with it
                    // on success do:
                    success: function (response) {
                        // if comment updated successfuly do:
                        if (response.success) {
                            //delete row
                            $("#row" + commentId).remove();
                        }
                    }
                });

            }
        });
        // when submit edit request 
        $('#edit-comment-form').on('submit', function (event) {
            // prevent the form from making the default action (submit)
            event.preventDefault();
            // git form object
            form = $(this);
            // get form action
            requestUrl = form.attr('action');
            // get form method
            requestMethod = form.attr('method');
            // get form params
            requestData = form.serialize(); //read comment

            // send request with ajax
            $.ajax({//define object from XML Http Request 
                url: requestUrl, //action : go to Add Comment controller
                type: requestMethod, //GET OR POST 
                data: requestData, //get Comment(FORM Data)
                dataType: 'json', // data will be deal with it

                // on success do:
                success: function (response) {
                    // if comment updated successfuly do:
                    if (response.success) {
                        // get tag object with this id and set comment value to it
                        $("#" + commentId).text(modalComment.val());
                        // close modal window after comment updated
                        $('.modal .modal-content .modal-footer .close-modal').click();
                    }
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