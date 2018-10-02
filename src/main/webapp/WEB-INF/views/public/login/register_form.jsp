<!-- Start Signup Form -->
<form class="signup" action="${initParam['customerPath']}register" method="POST">
    <input type="text" hidden name="previous" value="${param['previous']}">

    <div class="input-container">
        <input 
            pattern=".{4,}"
            title="Username must contain 4 or more characters"
            class="form-control" 
            type="text" 
            name="user" 
            autocomplete="off"
            placeholder="Type your username" 
            required 
            value="ahmed"/>
    </div>
    <div class="input-container">
        <input 
            minlength="4"
            class="form-control" 
            type="password" 
            name="pass" 
            autocomplete="new-password"
            placeholder="Type a Complex password" 
            required 
            value="ahmed"/>
    </div>
    <div class="input-container">
        <input 
            minlength="4"
            class="form-control" 
            type="password" 
            name="pass2" 
            autocomplete="new-password"
            placeholder="Type a password again" 
            required 
            value="ahmed"/>
    </div>
    <div class="input-container">
        <input 
            class="form-control" 
            type="email" 
            name="email" 
            placeholder="Type a Valid email"
            value="ahmedRamadan@gmai.com"/>
    </div>
    <div class="input-container">
        <input 
            pattern=".{4,}"
            title="Full Name must contain 4 or more characters"
            class="form-control" 
            type="text" 
            name="full_name" 
            autocomplete="off"
            placeholder="Type your Full Name" 
            required 
            value="ahmed ramadan teleb"/>
    </div>
    <input id="save" type="checkbox" name="remember" value="y">
    <label for="save" >Remember me</label>
    <input class="btn btn-success btn-block" name="signup" type="submit" value="Signup" />
</form>
<!-- End Signup Form -->