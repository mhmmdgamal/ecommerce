<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:import url="includes/templates/header.jsp" />
<c:import url="includes/templates/check_errors.jsp" />

<div class="container login-page">
    <h1 class="text-center">
        <span class="selected" data-class="login">Login</span> | 
        <span data-class="signup">Signup</span>
    </h1>
    <!-- Start Login Form -->
    <form class="login" action="${initParam['customerPath']}login" method="POST">
        <!-- action will go to the LoginRegisterController -->
        <div class="input-container">
            <input 
                class="form-control" 
                type="text" 
                name="user" 
                autocomplete="off"
                placeholder="Type your username" 
                required />
        </div>
        <div class="input-container">
            <input 
                class="form-control" 
                type="password" 
                name="pass" 
                autocomplete="new-password"
                placeholder="Type your password" 
                required />
        </div>
        <input class="btn btn-primary btn-block" name="login" type="submit" value="Login" />
    </form>
    <!-- End Login Form -->
    <!-- Start Signup Form -->
    <form class="signup" action="${initParam['customerPath']}login" method="POST">
        <div class="input-container">
            <input 
                pattern=".{4,}"
                title="Username must contain 4 or more characters"
                class="form-control" 
                type="text" 
                name="user" 
                autocomplete="off"
                placeholder="Type your username" 
                required />
        </div>
        <div class="input-container">
            <input 
                minlength="4"
                class="form-control" 
                type="password" 
                name="pass" 
                autocomplete="new-password"
                placeholder="Type a Complex password" 
                required />
        </div>
        <div class="input-container">
            <input 
                minlength="4"
                class="form-control" 
                type="password" 
                name="pass2" 
                autocomplete="new-password"
                placeholder="Type a password again" 
                required />
        </div>
        <div class="input-container">
            <input 
                class="form-control" 
                type="email" 
                name="email" 
                placeholder="Type a Valid email" />
        </div>
        <input class="btn btn-success btn-block" name="signup" type="submit" value="Signup" />
    </form>
    <!-- End Signup Form -->
</div>
<c:import url="includes/templates/footer.jsp" />