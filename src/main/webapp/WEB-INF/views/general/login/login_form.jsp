<%@page import="com.ecommerce.general.path.ControllerPath"%>
<!-- Start Login Form -->
<form id="login-form" class="login" action="<%=ControllerPath.LOGIN%>" method="POST">
    <div id="login-results" style="font-weight: bold"></div>
    <!-- action will go to the Login Controller -->
    <input type="text" hidden name="previous" value="${param['previous']}">

    <div class="input-container">
        <input 
            class="form-control" 
            type="text" 
            name="user" 
            autocomplete="off"
            placeholder="Type your username" 
            required 
            value="mhmmd"/>
    </div>
    <div class="input-container">
        <input 
            class="form-control" 
            type="password" 
            name="pass" 
            autocomplete="new-password"
            placeholder="Type your password" 
            required 
            value="1234"/>
        <input id="save1" type="checkbox" name="remember" value="y">
        <label for="save1" >Remember me</label>
    </div>
    <input class="btn btn-primary btn-block" name="login" type="submit" value="Login" />
</form>
<!-- End Login Form -->
