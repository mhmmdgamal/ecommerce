<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="com.ecommerce.general.path.ViewPath" %>
<%@page import="com.ecommerce.general.path.ControllerPath" %>

<input type="hidden" name="commentid" value="${comment['id']}" />
<!-- Start Comment Field -->
<div class="form-group form-group-lg">
    <label class="control-label">Comment</label>
    <div class="">
        <textarea class="form-control" name="comment">${comment['comment']}</textarea>
    </div>
</div>
<!-- End Comment Field -->