<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="includes/templates/header.jsp"/>
<c:import url="includes/templates/navbar.jsp"/>

<h1 class="text-center">Edit Comment</h1>
<c:import url="includes/templates/check_one_error_sm10.jsp"/>

<div class="container">

    <form action="${initParam['adminPath']}comments?action=Edit" class="form-horizontal" method="POST">

        <input type="hidden" name="commentid" value="${comment['id']}" />
        <!-- Start Comment Field -->
        <div class="form-group form-group-lg">
            <label class="col-sm-2 control-label">Comment</label>
            <div class="col-sm-10 col-md-6">
                <textarea class="form-control" name="comment">${comment['comment']}</textarea>
            </div>
        </div>
        <!-- End Comment Field -->
        <!-- Start Submit Field -->
        <div class="form-group form-group-lg">
            <div class="col-sm-offset-2 col-sm-10">
                <input type="submit" value="Save" class="btn btn-primary btn-sm" />
            </div>
        </div>
        <!-- End Submit Field -->
    </form>
</div>

<c:import url="includes/templates/footer.jsp"/>
