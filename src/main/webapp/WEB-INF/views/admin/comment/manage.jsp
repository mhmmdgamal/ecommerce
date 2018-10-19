<%@page import="com.ecommerce.general.path.ResourcePath"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="com.ecommerce.general.path.ViewPath" %>
<%@page import="com.ecommerce.general.path.ControllerPath" %>

<c:import url='<%=ViewPath.header_admin%>' />
<c:import url='<%=ViewPath.navebar_admin%>' />

<h1 class="text-center">Manage Comments</h1>

<c:choose>
    <c:when test="${requestScope['comments'].size() gt 0}">
        <div class="container"><div class="table-responsive">
                <form action="" id="comment-form" method="POST">
                    <table id="comments" class="main-table text-center table table-bordered">
                        <tr>
                            <td>#ID</td>
                            <td>Comment</td>
                            <td>Item Name</td>
                            <td>User Name</td>
                            <td>Adding Date</td>
                            <td>Control</td>
                        </tr>
                        <c:forEach items="${requestScope['comments']}" var="comment">
                            <tr id="row${comment['id']}">
                                <td>${comment['id']}</td>
                                <td>${comment['comment']}</td>
                                <td>${comment['item']['name']}</td>
                                <td>${comment['user']['name']}</td>
                                <td>${comment['addDate']}</td>
                                <td>
                                    <a href='#' class='edit-comment btn btn-success' data-toggle="modal" data-target="#commentModal">
                                        <i class='fa fa-edit'></i> Edit
                                    </a>
                                    <a href='#' class='delete-comment btn btn-danger'>
                                        <i class='fa fa-close'></i> Delete
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                        <!-- show comment Modal -->
                        <div class="modal fade" id="commentModal" role="dialog">
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
                        <!--  end comment Modal -->
                    </table>
                </form>
            </div>
            <!--<improve> go to page and show all categories and items -->
        </div>

    </c:when>
    <c:otherwise>
        <div class="container">
            <div class="nice-message">There's No Comment To Show</div>
        </div>
    </c:otherwise>
</c:choose>


<div class="footer"></div>
<script src="<%=ResourcePath.js_admin%>jquery-1.12.1.min.js"></script>
<script>
    $(function () {

        // when click Edit button do:
        $(document).on('click', '.edit-comment', function (event) {
            // prevent the form from making the default action (submit)
            event.preventDefault();
            let commentId = $(this).parent().parent().find('td:nth-child(1)').text();
            // send request with ajax
            $.ajax({//define object from XML Http Request 
                url: '<%=ControllerPath.EDIT_COMMENT_ADMIN%>', //action : go to Add Comment controller
                type: 'GET', //GET OR POST 
                data: {commentid: commentId},
                // on success do:
                success: function (response) {
                    $('#commentModal h4').text("Edit Comment");
                    $('#commentModal .modal-content .modal-body').html(response);
                }
            });
        });
        let flag = false;
        $(document).on('submit', '#comment-form', function (e) {

            e.preventDefault();
            if (flag === true) {
                return false;
            }

            form = $(this);
            requestUrl = '<%=ControllerPath.EDIT_COMMENT_ADMIN%>';
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
                    if (!response.success) {
                        $('.form-results').remove();
                            $('.modal .modal-body').prepend("<div class='form-results alert alert-danger'>error in update</div>");
                    } else {
                        $('.form-results').remove();
                        $('.modal .modal-body').prepend("<div class='form-results alert alert-success'>updated success</div>");
                        
                            let row = '#comments tr#row' + response.data.id;
                            let comment = row + ' td:nth(1)';
                            $(comment).text(response.data.comment);
                        
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
    });
</script>
<script src="<%=ResourcePath.js_admin%>jquery-ui.min.js"></script>
<script src="<%=ResourcePath.js_admin%>bootstrap.min.js"></script>
<script src="<%=ResourcePath.js_admin%>nicescroll.min.js"></script>
<script src="<%=ResourcePath.js_admin%>jquery.selectBoxIt.min.js"></script>
<script src="<%=ResourcePath.js_admin%>backend.js"></script>
</body>
</html>