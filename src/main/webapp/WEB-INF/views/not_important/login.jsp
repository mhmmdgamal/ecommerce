<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:import url="includes/templates/header.jsp" />

<form class="login" action="${initParam['adminPath']}login" method="POST">
    <h4 class="text-center">Admin Login</h4>
    <input class="form-control" 
           type="text" 
           name="user" 
           placeholder="Username" 
           autocomplete="off" 
           value="mhmmd"/>
    <input class="form-control" 
           type="password" 
           name="pass" 
           placeholder="Password" 
           autocomplete="new-password" 
           value="1234"/>
    <input id="save" type="checkbox" name="remember" value="y">
    <label for="save" >Remember me</label>
    <input class="btn btn-primary btn-block" 
           type="submit" 
           value="Login" />
</form>

<c:import url="includes/templates/footer.jsp" />
<!--
    ** the action firstly will go to Login Controller by check web.xml(or annotation)
    * example for web.xml
    * <servlet-mapping>
    * <servlet-name>LoginController</servlet-name>
    * <url-pattern>/login</url-pattern>
    * </servlet-mapping>
    * example for annotation 
    * @WebServlet(urlPatterns = {"/users"})
-->