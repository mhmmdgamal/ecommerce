<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:import url="${initParam['publicIncludePath']}header.jsp" />

<div class="container login-page">
    <h1 class="text-center">
        <span class="selected" data-class="login">Login</span> | 
        <span data-class="signup">Signup</span>
    </h1>
    
    <c:import url="login_form.jsp" />
    <c:import url="register_form.jsp" />

</div>
<c:import url="${initParam['publicIncludePath']}footer.jsp" />