<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="${initParam['adminIncludePath']}header.jsp"/>
<c:import url="${initParam['adminIncludePath']}navbar.jsp"/>

<h1 class="text-center">Edit User</h1>

<div class="container">

    <form action="${initParam['adminPath']}edit-user" class="form-horizontal" method="POST">

        <input type="hidden" name="userid" value="${user['id']}" />
        <!-- Start Username Field -->
        <div class="form-group form-group-lg">
            <label class="col-sm-2 control-label">Username</label>
            <div class="col-sm-10 col-md-6">
                <input type="text" name="username" class="form-control" value="${user['name']}" autocomplete="off" required="required" />
            </div>
        </div>
        <!-- End Username Field -->
        <!-- Start Password Field -->
        <div class="form-group form-group-lg">
            <label class="col-sm-2 control-label">Password</label>
            <div class="col-sm-10 col-md-6">
                <input type="hidden" name="oldPassword" value="${user['password']}" />
                <input type="password" name="newPassword" class="form-control" autocomplete="new-password" placeholder="Leave Blank If You Dont Want To Change" />
            </div>
        </div>
        <!-- End Password Field -->
        <!-- Start Email Field -->
        <div class="form-group form-group-lg">
            <label class="col-sm-2 control-label">Email</label>
            <div class="col-sm-10 col-md-6">
                <input type="email" name="email" value="${user['email']}" class="form-control" required="required" />
            </div>
        </div>
        <!-- End Email Field -->
        <!-- Start Full Name Field -->
        <div class="form-group form-group-lg">
            <label class="col-sm-2 control-label">Full Name</label>
            <div class="col-sm-10 col-md-6">
                <input type="text" name="full" value="${user['fullName']}" class="form-control" required="required" />
            </div>
        </div>
        <!-- End Full Name Field -->
        <!-- Start Submit Field -->
        <div class="form-group form-group-lg">
            <div class="col-sm-offset-2 col-sm-10">
                <input type="submit" value="Save" class="btn btn-primary btn-lg" />
            </div>
        </div>
        <!-- End Submit Field -->
    </form>
</div>

<c:import url="${initParam['adminIncludePath']}footer.jsp"/>
