<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.ecommerce.general.path.ViewPath" %>
<%@page import="com.ecommerce.general.path.ControllerPath" %>


<input type="hidden" name="userid" value="${user['id']}" />
<!-- Start Username Field -->
Username
<div class="form-group form-group-lg">
    <div class="">
        <input type="text" name="username" class="form-control" value="${user['name']}" autocomplete="off" required="required" />
    </div>
</div>
<!-- End Username Field -->
<!-- Start Password Field -->
Password
<div class="form-group form-group-lg">
    <div class="">
        <input type="hidden" name="oldPassword" value="${user['password']}" />
        <input type="password" name="newPassword" class="form-control" autocomplete="new-password" placeholder="Leave Blank If You Dont Want To Change" />
    </div>
</div>
<!-- End Password Field -->
Email
<!-- Start Email Field -->
<div class="form-group form-group-lg">
    <div class="">
        <input type="email" name="email" value="${user['email']}" class="form-control" required="required" />
    </div>
</div>
<!-- End Email Field -->
<!-- Start Full Name Field -->
Full Name
<div class="form-group form-group-lg">
    <div class="">
        <input type="text" name="full" value="${user['fullName']}" class="form-control" required="required" />
    </div>
</div>
<!-- End Full Name Field -->
