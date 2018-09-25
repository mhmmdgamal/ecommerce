<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="includes/templates/header.jsp"/>
<c:import url="includes/templates/navbar.jsp"/>

<h1 class="text-center">Manage Users </h1>
<c:import url="includes/templates/check_one_error.jsp"/>

<c:choose>
    <c:when test="${requestScope['users'].size() gt 0}">

        <div class="container">
            <a href="${initParam['adminPath']}add-user" class="btn btn-primary">
                <i class="fa fa-plus"></i> Add New User 
            </a>
            <div class="table-responsive">
                <table class="main-table manage-members text-center table table-bordered">
                    <tr>
                        <td>#ID</td>
                        <td>Avatar</td>
                        <td>Username</td>
                        <td>Email</td>
                        <td>Full Name</td>
                        <td>Registered Date</td>
                        <td>Control</td>
                    </tr>
                    <c:forEach items="${requestScope['users']}" var="user">
                        <tr>
                            <td>${user.getId()}</td>
                            <td>
                                <% if (true) { %>     
                                No Image
                                <% } else { %>
                                <img src='uploads/avatars/avatar.jpg' alt='editthis' />
                                <% }%>
                            </td>
                            <td>${user.name}</td>
                            <td>${user.email}</td>
                            <td>${user.fullName}</td>
                            <td>${user.date}</td>
                            <td>
                                <a href='${initParam['adminPath']}edit-user?userid=${user.id}' class='btn btn-success'><i class='fa fa-edit'></i> Edit</a>
                                <a href='${initParam['adminPath']}delete-user?userid=${user.id}' class='btn btn-danger confirm'><i class='fa fa-close'></i> Delete </a>

                                <c:if test="${user.regStatus eq 0}">
                                    <a href='${initParam['adminPath']}active-user?userid=${user.id}' class='btn btn-info activate'><i class='fa fa-check'></i> Activate</a>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
            <a href="${initParam['adminPath']}add-user" class="btn btn-primary">
                <i class="fa fa-plus"></i> Add New User 
            </a>
        </div>

    </c:when>
    <c:otherwise>
        <div class="container">
            <div class="nice-message">There's No Users To Show</div>
            <a href="${initParam['adminPath']}add-user" class="btn btn-primary">
                <i class="fa fa-plus"></i> Add New User 
            </a>
        </div>
    </c:otherwise>
</c:choose>
<c:import url="includes/templates/footer.jsp"/>
