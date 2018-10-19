<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.ecommerce.general.path.ViewPath" %>
<%@page import="com.ecommerce.general.path.ControllerPath" %>


<!-- Start Username Field -->       
Username
<div class="form-group form-group-lg">
    <div class="">
        <input type="text" name="username" class="form-control" autocomplete="off" required="required" placeholder="Username" />
    </div>
</div>
<!-- End Username Field -->
<!-- Start Password Field -->
Password
<div class="form-group form-group-lg">
    <div class="">
        <input type="password" name="password" class="password form-control" required="required" autocomplete="new-password" placeholder="Password" />
    </div>
</div>
<!-- End Password Field -->
<!-- Start Email Field -->
Email
<div class="form-group form-group-lg">
    <div class="">
        <input type="email" name="email" class="form-control" required="required" placeholder="Email" />
    </div>
</div>
<!-- End Email Field -->
<!-- Start Full Name Field -->
Full Name
<div class="form-group form-group-lg">
    <div class="">
        <input type="text" name="full" class="form-control" required="required" placeholder="Full Name" />
    </div>
</div>