<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:import url="includes/templates/header.jsp" />

<c:if test="${sessionScope['username'] ne null}">
    <c:redirect url="dashboard"/>
</c:if>

<form class="login" action="${pageContext.request.contextPath}/admin/login" method="POST">
    <h4 class="text-center">Admin Login</h4>
    <input class="form-control" type="text" name="user" placeholder="Username" autocomplete="off" />
    <input class="form-control" type="password" name="pass" placeholder="Password" autocomplete="new-password" />
    <input class="btn btn-primary btn-block" type="submit" value="Login" />
</form>

<c:import url="includes/templates/footer.jsp" />