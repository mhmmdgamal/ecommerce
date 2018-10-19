<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="com.ecommerce.general.path.ResourcePath"%>
<%@page import="com.ecommerce.general.path.ViewPath" %>
<%@page import="com.ecommerce.general.path.ControllerPath" %>

<c:import url='<%=ViewPath.header_admin%>' />
<c:import url='<%=ViewPath.navebar_admin%>' />

<h1 class="text-center">Manage Users </h1>
<c:choose>
    <c:when test="${requestScope['users'].size() gt 0}">


        <div class="container">
            <a href="#" class="add-user btn btn-primary" data-toggle="modal" data-target="#userModal">
                <i class="fa fa-plus"></i> Add New User 
            </a>
            <div class="table-responsive">
                <form action="" id="user-form" method="POST">
                    <table id="users" class="main-table manage-members text-center table table-bordered">
                        <tr>
                            <td>#ID</td>
                            <td>Username</td>
                            <td>Avatar</td>
                            <td>Email</td>
                            <td>Full Name</td>
                            <td>Registered Date</td>
                            <td>Control</td>
                        </tr>
                        <c:forEach items="${requestScope['users']}" var="user">
                            <tr id="row${user['id']}">
                                <td>${user['id']}</td>
                                <td>${user['name']}</td>
                                <td>
                                    <% if (true) { %>     
                                    No Image
                                    <% } else { %>
                                    <img src='uploads/avatars/avatar.jpg' alt='editthis' />
                                    <% }%>
                                </td>
                                <td>${user['email']}</td>
                                <td>${user['fullName']}</td>
                                <td>${user['date']}</td>
                                <td>
                                    <a href='#' class='edit-user btn btn-success' data-toggle="modal" data-target="#userModal">
                                        <i class='fa fa-edit'></i> Edit
                                    </a>
                                    <a href='#' class='delete-user btn btn-danger'>
                                        <i class='fa fa-close'></i> Delete 
                                    </a>

                                    <c:if test="${user['regStatus'] eq 0}">
                                        <a href='' class='active-user btn btn-info activate'>
                                            <i class='fa fa-check'></i> Activate</a>
                                        </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                        <!-- show user Modal -->
                        <div class="modal fade" id="userModal" role="dialog">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                                        <h4 class="modal-title">title</h4>
                                    </div>
                                    <div class="modal-body">
                                    </div>
                                    <div class="modal-footer">
                                        <input type="submit"  name="save" value="Save" class="btn btn-default btn-lg" />
                                        <button type="button" class="btn btn-default btn-lg close-modal" data-dismiss="modal">Close</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!--  end user Modal -->
                    </table>
                </form>
            </div>
            <a href="#" class="add-user btn btn-primary" data-toggle="modal" data-target="#userModal">
                <i class="fa fa-plus"></i> Add New User 
            </a>
        </div>

    </c:when>
    <c:otherwise>
        <div class="container">
            <div class="nice-message">There's No Users To Show</div>
            <a href="#" class="add-user btn btn-primary" data-toggle="modal" data-target="#userModal">
                <i class="fa fa-plus"></i> Add New User 
            </a>
        </div>
    </c:otherwise>
</c:choose>

<div class="footer"></div>
<script src="<%=ResourcePath.js_admin%>jquery-1.12.1.min.js"></script>
<script>
    $(function () {

        let addEditUserURL = '<%=ControllerPath.ADD_USER_ADMIN%>';
        let flagAdd = false;

        // when click Add button do:
        $(document).on('click', '.add-user', function (event) {
            // prevent the form from making the default action (submit)
            event.preventDefault();
            flagAdd = true;
            userData = $(this).parent().parent().find('td:nth-child(1)');
            addEditUserURL = '<%=ControllerPath.ADD_USER_ADMIN%>';
            // send request with ajax
            $.ajax({//define object from XML Http Request 
                url: '<%=ControllerPath.ADD_USER_ADMIN%>', //action : go to Add Comment controller
                type: 'GET', //GET OR POST 
                // on success do:
                success: function (response) {
                    $('#userModal h4').text("Add User");
                    $('#userModal .modal-content .modal-body').html(response);
                }
            });
        });
        // when click Edit button do:
        $(document).on('click', '.edit-user', function (event) {
            // prevent the form from making the default action (submit)
            event.preventDefault();
            flagAdd = false;
            let userId = $(this).parent().parent().find('td:nth-child(1)').text();
            addEditUserURL = '<%=ControllerPath.EDIT_USER_ADMIN%>';
            // send request with ajax
            $.ajax({//define object from XML Http Request 
                url: '<%=ControllerPath.EDIT_USER_ADMIN%>', //action : go to Add Comment controller
                type: 'GET', //GET OR POST 
                data: {userid: userId},
                // on success do:
                success: function (response) {
                    $('#userModal h4').text("Edit User");
                    $('#userModal .modal-content .modal-body').html(response);
                }
            });
        });
        let flag = false;
        $(document).on('submit', '#user-form', function (e) {

            e.preventDefault();
            if (flag === true) {
                return false;
            }

            form = $(this);
            requestUrl = addEditUserURL;
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
                        $('.form-results').remove();
                        for (let i = 0; i < response.errors.length; i++) {
                            $('.modal .modal-body').prepend("<div class='form-results alert alert-danger'>" + response.errors[i] + "</div>");
                        }
                    } else if (response.success !== null) {
                        $('.form-results').remove();
                        $('.modal .modal-body').prepend("<div class='form-results alert alert-success'>" + response.success + "</div>");
                        if (flagAdd) {

                            $('#users tr:first').after(
                                    '<tr id="row' + response.data.id + '">' +
                                    '<td>' + response.data.id + '</td>' +
                                    '<td>' + response.data.name + '</td>' +
                                    '<td>' +
                                    'No Image' +
                                    '</td>' +
                                    '<td>' + response.data.email + '</td>' +
                                    '<td>' + response.data.fullName + '</td>' +
                                    '<td>' + response.data.date + '</td>' +
                                    '<td>' +
                                    '<a href="#" class="edit-user btn btn-success" data-toggle="modal" data-target="#userModal">' +
                                    '<i class="fa fa-edit"></i> Edit' +
                                    '</a> ' +
                                    '<a href="#" class="delete-user btn btn-danger">' +
                                    '<i class="fa fa-close"></i> Delete ' +
                                    '</a>' +
                                    '<a href="#" class="active-user btn btn-info activate">' +
                                    '<i class="fa fa-check"></i> Activate</a>' +
                                    '</td>' +
                                    '</tr>');
                        } else {
                            let row = '#users tr#row' + response.data.id;
                            let name = row + ' td:nth(1)';
                            let email = row + ' td:nth(3)';
                            let fullName = row + ' td:nth(4)';
                            $(name).text(response.data.name);
                            $(email).text(response.data.email);
                            $(fullName).text(response.data.fullName);
                        }
                        $('.modal .modal-content .modal-footer .close-modal').click();
                    }
                }
            });
        });
        // when click Delete button do:
        $(document).on('click', '.delete-user', function (event) {
            // prevent the form from making the default action (submit)
            event.preventDefault();
            if (confirm("Are you sure?")) {
                // get comment id
                let userId = $(this).parent().parent().find('td:nth-child(1)').text();
                // send request with ajax
                $.ajax({//define object from XML Http Request 
                    url: '<%=ControllerPath.DELETE_USER_ADMIN%>', //action : go to Add Comment controller
                    type: 'GET', //GET OR POST 
                    data: {userid: userId}, //get Comment(FORM Data)
                    dataType: 'json', // data will be deal with it
                    // on success do:
                    success: function (response) {
                        // if comment updated successfuly do:
                        if (response.success) {
                            //delete row
                            $("#row" + userId).remove();
                        }
                    }
                });
            }
        });
        // when click Delete button do:
        $(document).on('click', '.active-user', function (event) {
            let activeButton = $(this);
            // prevent the form from making the default action (submit)
            event.preventDefault();
            if (confirm("Are you sure?")) {
                // get comment id
                userId = $(this).parent().parent().find('td:nth-child(1)').text();
                // send request with ajax
                $.ajax({//define object from XML Http Request 
                    url: '<%=ControllerPath.ACTIVE_USER_ADMIN%>', //action : go to Add Comment controller
                    type: 'GET', //GET OR POST 
                    data: {userid: userId}, //get Comment(FORM Data)
                    dataType: 'json', // data will be deal with it
                    // on success do:
                    success: function (response) {
                        // if comment updated successfuly do:
                        if (response.success) {
                            //delete row
                            activeButton.remove();
                        }
                    }
                });
            }
        });
    });
</script>
<script src="<%=ResourcePath.js_admin%>jquery-ui.min.js"></script>
<script src="<%=ResourcePath.js_admin%>bootstrap.min.js"></script>
<script src="<%=ResourcePath.js_admin%>nicescroll.min.js"></script>
<script src="<%=ResourcePath.js_admin%>jquery.selectBoxIt.min.js"></script>
<script src="<%=ResourcePath.js_admin%>backend.js"></script>
</body>
</html>