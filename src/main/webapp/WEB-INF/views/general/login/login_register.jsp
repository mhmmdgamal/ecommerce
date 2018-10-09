<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="com.ecommerce.general.path.ViewPath" %>

<c:import url='<%=ViewPath.header%>' />

<div class="container login-page">
    <h1 class="text-center">
        <span class="selected" data-class="login">Login</span> | 
        <span data-class="signup">Signup</span>
    </h1>

    <c:import url="<%=ViewPath.login_Form %>" />
    <c:import url="<%=ViewPath.register_Form %>" />

</div>
<c:import url='<%=ViewPath.footer%>' />