<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.ecommerce.general.path.ViewPath" %>
<%@page import="com.ecommerce.general.path.ControllerPath" %>

<c:import url='<%=ViewPath.header_admin %>' />
<c:import url='<%=ViewPath.navebar_admin %>' />

<h1 class="text-center">Add New User</h1>

<div class="container">

    <form action="<%=ControllerPath.ADD_USER_ADMIN %>" class="form-horizontal" method="POST">
        <!-- Start Username Field -->        
        <div class="form-group form-group-lg">
            <label class="col-sm-2 control-label">Username</label>
            <div class="col-sm-10 col-md-6">
                <input type="text" name="username" class="form-control" autocomplete="off" required="required" placeholder="Username To Login Into Shop" />
            </div>
        </div>
        <!-- End Username Field -->
        <!-- Start Password Field -->
        <div class="form-group form-group-lg">
            <label class="col-sm-2 control-label">Password</label>
            <div class="col-sm-10 col-md-6">
                <input type="password" name="password" class="password form-control" required="required" autocomplete="new-password" placeholder="Password Must Be Hard & Complex" />
                <i class="show-pass fa fa-eye fa-2x"></i>
            </div>
        </div>
        <!-- End Password Field -->
        <!-- Start Email Field -->
        <div class="form-group form-group-lg">
            <label class="col-sm-2 control-label">Email</label>
            <div class="col-sm-10 col-md-6">
                <input type="email" name="email" class="form-control" required="required" placeholder="Email Must Be Valid" />
            </div>
        </div>
        <!-- End Email Field -->
        <!-- Start Full Name Field -->
        <div class="form-group form-group-lg">
            <label class="col-sm-2 control-label">Full Name</label>
            <div class="col-sm-10 col-md-6">
                <input type="text" name="full" class="form-control" required="required" placeholder="Full Name Appear In Your Profile Page" />
            </div>
        </div>
        <!-- End Full Name Field -->
        <!-- Start Avatar Field -->
        <!--<div class="form-group form-group-lg">-->
        <!--<label class="col-sm-2 control-label">User Avatar</label>-->
        <!--<div class="col-sm-10 col-md-6">-->
        <!--<input type="file" name="avatar" class="form-control" required="required" />-->
        <!--</div>-->
        <!--</div>-->
        <!-- End Avatar Field -->
        <!-- Start Submit Field -->
        <div class="form-group form-group-lg">
            <div class="col-sm-offset-2 col-sm-10">
                <input type="submit" value="Add Member" class="btn btn-primary btn-lg" />
            </div>
        </div>
        <!-- End Submit Field -->

    </form>
</div>
<c:import url='<%=ViewPath.footer_admin %>' />
