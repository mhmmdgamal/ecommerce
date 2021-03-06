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
                        <span>Login Name</span> : 
                        <span id="name-info"> ${user['name']}</span>
                    </li>
                    <li>
                        <i class="fa fa-envelope-o fa-fw"></i>
                        <span>Email</span> : 
                        <span id="email-info">${user['email']}</span>
                    </li>
                    <li>
                        <i class="fa fa-user fa-fw"></i>
                        <span>Full Name</span> : 
                        <span id="fullName-info">${user['fullName']}</span>
                    </li>
                    <li>
                        <i class="fa fa-calendar fa-fw"></i>
                        <span>Registered Date</span> : <span id="date-info">${user['date']}</span>
                    </li>
                </ul>

                <a href="#" class="edit-comment btn btn-default" data-toggle="modal" 
                   data-target="#edit-info-Modal">
                    <i class='fa fa-edit'></i> Edit Your Information
                </a>

                <!-- show Modal -->
                <div class="modal text-center" id="edit-info-Modal" role="dialog">
                    <div class="modal-dialog modal-lg">
                        <!-- Modal content for Information -->
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal">&times;</button>
                                <h4 class="modal-title">Modal Header</h4>
                            </div>
                            <form id='edit-info-form' class="form-horizontal main-form" 
                                  action="<%=ControllerPath.EDIT_PROFILE%>" method="POST">
                                <!--<div class="modal-body">-->
                                <!-- Start id Field -->
                                <input type="hidden" name="userid" value="${user['id']}" />
                                <!-- End id Field -->
                                <div class="form-group form-group-lg">
                                    <label class="col-sm-3 control-label"><span>Login Name</span> :</label>
                                    <div class="col-sm-10 col-md-9">
                                        <input 
                                            id="name-info-model"
                                            pattern=".{4,}"
                                            title="This Field Require At Least 4 Characters"
                                            type="text" 
                                            name="name" 
                                            class="form-control live"  
                                            placeholder="Name of The Item"
                                            data-class=".live-title"
                                            required 
                                            value="${user['name']}"/>
                                    </div>
                                </div>
                                <div class="form-group form-group-lg">
                                    <label class="col-sm-3 control-label"><span>Password</span> :</label>
                                    <div class="col-sm-10 col-md-9">
                                        <input 
                                            type="hidden" 
                                            name="oldPassword" 
                                            value="${user['password']}" />
                                        <input 
                                            title="This Field Require At Least 4 Characters"
                                            type="password" 
                                            name="newPassword" 
                                            class="form-control live"  
                                            placeholder="Leave Blank If You Dont Want To Change"
                                            data-class=".live-title"
                                            />
                                    </div>
                                </div>
                                <div class="form-group form-group-lg">
                                    <label class="col-sm-3 control-label"><span>Email</span> : </label>
                                    <div class="col-sm-10 col-md-9">
                                        <input 
                                            id="email-info-model"
                                            pattern=".{4,}"
                                            title="This Field Require At Least 4 Characters"
                                            type="text" 
                                            name="email" 
                                            class="form-control live"  
                                            placeholder="Name of The Item"
                                            data-class=".live-title"
                                            required 
                                            value="${user['email']}"/>
                                    </div>
                                </div>
                                <div class="form-group form-group-lg">
                                    <label class="col-sm-3 control-label"><span>Full Name</span> : </label>
                                    <div class="col-sm-10 col-md-9">
                                        <input 
                                            id="fulName-info-model"
                                            pattern=".{4,}"
                                            title="This Field Require At Least 4 Characters"
                                            type="text" 
                                            name="fullName" 
                                            class="form-control live"  
                                            placeholder="Name of The Item"
                                            data-class=".live-title"
                                            value="${user['fullName']}"/>
                                    </div>
                                </div>
                                <!--</div>-->
                                <div class="modal-footer">
                                    <!-- Start Submit Field -->
                                    <div class="form-group form-group-lg">
                                        <div class="col-sm-offset-3 col-sm-9">
                                            <button type="submit" class="btn btn-default" >Edit Your Information </button>
                                            <button type="button" class="btn btn-default close-modal" data-dismiss="modal">Close</button>
                                        </div>
                                    </div>
                                </div>
                                <!-- End Submit Field -->
                            </form>
                        </div>
                    </div>
                </div>
                <!--  end Modal -->
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
                                <div class="show-item col-sm-6 col-md-3">
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
                        <div class="col-sm-6 col-md-3">
                            <a href="#" class="add-item btn btn-primary" 
                               data-toggle="modal" data-target="#itemModal">
                                <i class="fa fa-plus"></i> New Item
                            </a>
                            <!-- show item Modal -->
                            <div class="modal fade" id="itemModal" role="dialog">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <form id="add-item-form" method="POST">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal">&times;</button>
                                                <h4 class="modal-title">title</h4>
                                            </div>
                                            <!-- Start modal-body --> 
                                            <div class="modal-body">
                                            </div>
                                            <!-- End modal-body --> 
                                            <div class="modal-footer">
                                                <input type="submit"  name="save" value="Save" class="btn btn-default btn-lg" />
                                                <button type="button" class="btn btn-default btn-lg close-modal" data-dismiss="modal">Close</button>
                                            </div>
                                        </form>
                                        <!-- start show Image-->
                                        <div class="col-md-4">
                                            <div class="thumbnail item-box live-preview">
                                                <span class="price-tag">
                                                    $<span class="live-price">0</span>
                                                </span>
                                                <img class="img-responsive" src="<%=ResourcePath.img%>img.png" alt="No Image" />
                                                <div class="caption">
                                                    <h3 class="live-title">Title</h3>
                                                    <p class="live-desc">Description</p>
                                                </div>
                                            </div>
                                        </div>
                                        <!-- End show Image-->
                                    </div>
                                </div>
                            </div>
                            <!--  end item Modal -->
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="well">There's No Items To Show, Create <a href="<%=ControllerPath.ADD_ITEM%>">Add New Item</a></div>
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
                                                <a href="#" class="edit-comment btn btn-info" data-toggle="modal" data-target="#edit-comment-Modal">
                                                    <i class='fa fa-edit'></i> Edit 
                                                </a>
                                                <a href='#' class='delete-comment btn btn-danger'>
                                                    <i class='fa fa-close'></i> Delete
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    <!-- show Modal -->
                                    <div class="modal text-center" id="edit-comment-Modal" role="dialog">
                                        <div class="modal-dialog modal-sm">
                                            <!-- Modal content for edit comment -->
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

        let name_info = $('.modal .modal-content input[id="name-info-model"]');

        // when click edit button do:
        $('.edit-comment').click(function (event) {
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

        // when click Delete button do:
        $('.delete-comment').click(function (event) {
            // prevent the form from making the default action (submit)
            event.preventDefault();

            if (confirm("Are you sure?")) {
                // get comment id
                commentId = $(this).parent().parent().find('td').first().attr('id');

                // send request with ajax
                $.ajax({//define object from XML Http Request 
                    url: '<%=ControllerPath.DELETE_COMMENT%>', //go to Delete Comment controller
                    type: 'GET', //GET or POST 
                    data: {commentid: commentId},
                    dataType: 'json', // data will be deal with it
                    // on success do:
                    success: function (response) {
                        // if comment Deleted Successfully
                        if (response.success) {
                            //delete row
                            $("#row" + commentId).remove();
                        }
                    }
                });
            }
        });

        // when submit edit Info request 
        $('#edit-info-form').on('submit', function (event) {
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

                        $("#name-info").text($('#name-info-model').val());
                        $("#email-info").text($('#email-info-model').val());
                        $("#fullName-info").text($('#fulName-info-model').val());
                        // close modal window after comment updated
                        $('.modal .modal-content .modal-footer .close-modal').click();
                        //<error>consol.log("demo"); don't work
                        //Ensures there will be no 'console is undefined' errors
//                        window.console = window.console || (function () {
//                            var c = {};
//                            c.log = c.warn = c.debug = c.info = c.error = c.time = c.dir = c.profile = c.clear = c.exception = c.trace = c.assert = function (s) {};
//                            return c;
//                        })();
                    }
                }
            });
        });

        ////////////////////////////////////////////////////////////////////////
        //add item 

        let flagAdd = false;

        // when click Add button do:
        $(document).on('click', '.add-item', function (event) {
            // prevent the form from making the default action (submit)
            event.preventDefault();
            flagAdd = true;
//            itemData = $(this).parent().parent().find('td:nth-child(1)');

            // send request with ajax
            $.ajax({//define object from XML Http Request 
                url: '<%=ControllerPath.ADD_ITEM%>', //action : go to Add Comment controller
                type: 'GET', //GET OR POST 
                // on success do:
                success: function (response) {
                    $('#itemModal h4').text("Add Item");
                    $('#itemModal .modal-content .modal-body').html(response);
                    $("select").selectBoxIt({
                        autoWidth: false
                    });
                }
            });
        });

        let flag = false;
        $(document).on('submit', '#add-item-form', function (e) {

            e.preventDefault();
            if (flag === true) {
                return false;
            }

            form = $(this);
            requestUrl = '<%=ControllerPath.ADD_ITEM%>';
            requestMethod = form.attr('method');
            requestData = form.serialize();
            $.ajax({
                url: requestUrl,
                type: requestMethod,
                data: requestData,
                dataType: 'json',
                beforeSend: function () {
                    flag = true;
                    $('input[name="save"]').attr('disabled', true);
                },
                success: function (response) {
                    $('input[name="save"]').removeAttr('disabled');
                    flag = false;
                    if (response.errors.length > 0) {
                        alert("Erorrs : AJAX request say there's some Erorrs");
                    } else if (response.success !== null) {

                        $(document).ajaxSuccess(function () {
                            alert("AJAX request successfully completed");
                        });
                        //<error>(bad show item ) ???
                        $('.show-item :first').after(
                                '<div class="show-item col-sm-6 col-md-3">'
                                + '<div class="thumbnail item-box">'
                                + ' <span class="approve-status">Waiting Approval</span>'
                                + ' <span class="price-tag">' + response.data.price + '</span>'
                                + '<img class="img-responsive" src="' + response.data.img + '" alt="No Image" />'
                                + '<div class="caption">'
                                + '<h3><a href="' + response.data.show_item + '?itemid=' + response.data.id + '">'
                                + response.data.name + '</a>' + '</h3>'
                                + '<p>' + response.data.description + '</p>'
                                + '<div class="date">' + response.data.date + '</div>'
                                + '</div>'
                                + '</div>'
                                + '</div>'
                                );
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