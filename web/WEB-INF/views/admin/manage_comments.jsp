<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="includes/templates/header.jsp"/>
<c:import url="includes/templates/navbar.jsp"/>

<h1 class="text-center">Manage Comments</h1>
<c:import url="includes/templates/check_one_error.jsp"/>

<c:choose>
    <c:when test="${requestScope['comments'].size() gt 0}">
        <div class="container"><div class="table-responsive"><table class="main-table text-center table table-bordered"><tr><td>#ID</td>
                        <td>Comment</td>
                        <td>Item Name</td>
                        <td>User Name</td>
                        <td>Adding Date</td>
                        <td>Control</td>
                    </tr>
                    <c:forEach items="${requestScope['comments']}" var="comment">
                        <tr>
                            <td>${comment['id']}</td>
                            <td>${comment['comment']}</td>
                            <td>${comment['item.name']}</td>
                            <td>${comment['user.name']}</td>
                            <td>${comment['addDate']}</td>
                            <td>
                                <a href='${initParam['adminPath']}comments?action=Edit&commentid=${comment['id']}' class='btn btn-success'><i class='fa fa-edit'></i> Edit</a>
                                <a href='${initParam['adminPath']}comments?action=Delete&commentid=${comment['id']}' class='btn btn-danger confirm'><i class='fa fa-close'></i> Delete</a>
                                <c:if test="${comment['status'] eq 0}">
                                    <a href='${initParam['adminPath']}comments?action=Approve&commentid=${comment['id']}' class='btn btn-info activate'> <i class='fa fa-check'></i> Approve</a>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
            <a href="${initParam['adminPath']}comments?action=Add" class="btn btn-sm btn-primary">
                <i class="fa fa-plus"></i> New Comment
            </a>
        </div>

    </c:when>
    <c:otherwise>
        <div class="container">
            <div class="nice-message">There's No Comment To Show</div>
            <a href="${initParam['adminPath']}comments?action=Add" class="btn btn-sm btn-primary">
                <i class="fa fa-plus"></i> New Comment
            </a>
        </div>
    </c:otherwise>
</c:choose>
<c:import url="includes/templates/footer.jsp"/>
