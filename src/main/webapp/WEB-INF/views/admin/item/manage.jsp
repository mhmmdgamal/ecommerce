<%@page import="com.ecommerce.general.path.ResourcePath"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="com.ecommerce.general.path.ViewPath" %>
<%@page import="com.ecommerce.general.path.ControllerPath" %>


<c:import url='<%=ViewPath.header_admin%>' />
<c:import url='<%=ViewPath.navebar_admin%>' />

<h1 class="text-center">Manage Items</h1>

<c:choose>
    <c:when test="${requestScope['items'].size() gt 0}">
        <div class="container">
            <div class="table-responsive">
                <form action="" id="item-form" method="POST">
                    <table id="items" class="main-table text-center table table-bordered">
                        <tr>
                            <td>#ID</td>
                            <td>Item Name</td>
                            <td>Description</td>
                            <td>Price</td>
                            <td>Adding Date</td>
                            <td>User Name</td>
                            <td>Category</td>
                            <td>Control</td>
                        </tr>

                        <c:forEach items="${requestScope['items']}" var="item">

                            <tr id="row${item['id']}">
                                <td>${item['id']}</td>
                                <td>${item['name']}</td>
                                <td>${item['description']}</td>
                                <td>${item['price']}</td>
                                <td>${item['addDate']}</td>
                                <td>${item['user']['name']}</td>
                                <td>${item['category']['name']}</td>
                                <td>
                                    <a href='#' class='edit-item btn btn-success' data-toggle="modal" data-target="#itemModal">
                                        <i class='fa fa-edit'></i> Edit
                                    </a>
                                    <a href='#' class='delete-item btn btn-danger'>
                                        <i class='fa fa-close'></i> Delete
                                    </a> 
                                    <c:if test="${item['approve'] eq 0}">
                                        <a href=#' class='approve-item btn btn-info activate'> 
                                            <i class='fa fa-check'></i> Approve
                                        </a> 
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                        <!-- show item Modal -->
                        <div class="modal fade" id="itemModal" role="dialog">
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
                        <!--  end item Modal -->
                    </table>
                </form>
            </div>
            <a href="#" class="add-item btn btn-primary" data-toggle="modal" data-target="#itemModal">
                <i class="fa fa-plus"></i> New Item
            </a>
        </div>

    </c:when>
    <c:otherwise>
        <div class="container">
            <div class="nice-message">There's No Items To Show</div>
            <a href="#" class="add-item btn btn-primary" data-toggle="modal" data-target="#itemModal">
                <i class="fa fa-plus"></i> New Item
            </a>
        </div>
    </c:otherwise>
</c:choose>
<div class="footer"></div>
<script src="<%=ResourcePath.js_admin%>jquery-1.12.1.min.js"></script>
<script>
    $(function () {

        let addEditItemURL = '<%=ControllerPath.ADD_ITEM_ADMIN%>';
        let flagAdd = false;

        // when click Add button do:
        $(document).on('click', '.add-item', function (event) {
            // prevent the form from making the default action (submit)
            event.preventDefault();
            flagAdd = true;
            itemData = $(this).parent().parent().find('td:nth-child(1)');
            addEditItemURL = '<%=ControllerPath.ADD_ITEM_ADMIN%>';
            // send request with ajax
            $.ajax({//define object from XML Http Request 
                url: '<%=ControllerPath.ADD_ITEM_ADMIN%>', //action : go to Add Comment controller
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
        // when click Edit button do:
        $(document).on('click', '.edit-item', function (event) {
            // prevent the form from making the default action (submit)
            event.preventDefault();
            flagAdd = false;
            let itemId = $(this).parent().parent().find('td:nth-child(1)').text();
            addEditItemURL = '<%=ControllerPath.EDIT_ITEM_ADMIN%>';
            // send request with ajax
            $.ajax({//define object from XML Http Request 
                url: '<%=ControllerPath.EDIT_ITEM_ADMIN%>', //action : go to Add Comment controller
                type: 'GET', //GET OR POST 
                data: {itemid: itemId},
                // on success do:
                success: function (response) {
                    $('#itemModal h4').text("Edit Item");
                    $('#itemModal .modal-content .modal-body').html(response);
                    $("select").selectBoxIt({
                        autoWidth: false
                    });
                }
            });
        });
        let flag = false;
        $(document).on('submit', '#item-form', function (e) {

            e.preventDefault();
            if (flag === true) {
                return false;
            }

            form = $(this);
            requestUrl = addEditItemURL;
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

                            $('#items tr:first').after(
                                    '<tr id="row' + response.data.id + '">' +
                                    '<td>' + response.data.id + '</td>' +
                                    '<td>' + response.data.name + '</td>' +
                                    '<td>' + response.data.description +'</td>' +
                                    '<td>' + response.data.price + '</td>' +
                                    '<td>' + response.data.date + '</td>' +
                                    '<td>' + response.data.username + '</td>' +
                                    '<td>' + response.data.categoryName + '</td>' +
                                    '<td>' +
                                    '<a href="#" class="edit-item btn btn-success" data-toggle="modal" data-target="#userModal">' +
                                    '<i class="fa fa-edit"></i> Edit' +
                                    '</a> ' +
                                    '<a href="#" class="delete-item btn btn-danger">' +
                                    '<i class="fa fa-close"></i> Delete ' +
                                    '</a>' +
                                    '<a href="#" class="active-item btn btn-info activate">' +
                                    '<i class="fa fa-check"></i> Activate</a>' +
                                    '</td>' +
                                    '</tr>');
                        } else {
                            let row = '#items tr#row' + response.data.id;
                            let name = row + ' td:nth(1)';
                            let description = row + ' td:nth(2)';
                            let price = row + ' td:nth(3)';
                            let user = row + ' td:nth(5)';
                            let category = row + ' td:nth(6)';
                            
                            $(name).text(response.data.name);
                            $(description).text(response.data.description);
                            $(price).text(response.data.pricw);
                            $(user).text(response.data.username);
                            $(category).text(response.data.categoryName);
                        }
                        $('.modal .modal-content .modal-footer .close-modal').click();
                    }
                }
            });
        });
        // when click Delete button do:
        $(document).on('click', '.delete-item', function (event) {
            // prevent the form from making the default action (submit)
            event.preventDefault();
            if (confirm("Are you sure?")) {
                // get comment id
                let itemId = $(this).parent().parent().find('td:nth-child(1)').text();
                // send request with ajax
                $.ajax({//define object from XML Http Request 
                    url: '<%=ControllerPath.DELETE_ITEM_ADMIN%>', //action : go to Add Comment controller
                    type: 'GET', //GET OR POST 
                    data: {itemid: itemId}, //get Comment(FORM Data)
                    dataType: 'json', // data will be deal with it
                    // on success do:
                    success: function (response) {
                        // if comment updated successfuly do:
                        if (response.success) {
                            //delete row
                            $("#row" + itemId).remove();
                        }
                    }
                });
            }
        });
        // when click Delete button do:
        $(document).on('click', '.approve-item', function (event) {
            let activeButton = $(this);
            // prevent the form from making the default action (submit)
            event.preventDefault();
            if (confirm("Are you sure?")) {
                // get comment id
                itemId = $(this).parent().parent().find('td:nth-child(1)').text();
                // send request with ajax
                $.ajax({//define object from XML Http Request 
                    url: '<%=ControllerPath.APPROVE_ITEM_ADMIN%>', //action : go to Add Comment controller
                    type: 'GET', //GET OR POST 
                    data: {itemid: itemId}, //get Comment(FORM Data)
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