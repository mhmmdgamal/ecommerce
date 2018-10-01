<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="${initParam['adminIncludePath']}header.jsp"/>
<c:import url="${initParam['adminIncludePath']}navbar.jsp"/>

<h1 class="text-center">Manage Items</h1>

<c:choose>
    <c:when test="${requestScope['items'].size() gt 0}">
        <div class="container">
            <div class="table-responsive">
                <table class="main-table text-center table table-bordered">
                    <tr>
                        <td>#ID</td>
                        <td>Item Name</td>
                        <td>Description</td>
                        <td>Price</td>
                        <td>Adding Date</td>
                        <td>User Name</td>
                        <td>Category</td>
                        <td>Control</td>
                    </tr>

                    <c:forEach items="${requestScope['items']}" var="item">

                        <tr>
                            <td>${item['id']}</td>
                            <td>${item['name']}</td>
                            <td>${item['description']}</td>
                            <td>${item['price']}</td>
                            <td>${item['addDate']}</td>
                            <td>${item['user']['name']}</td>
                            <td>${item['category']['name']}</td>
                            <td>
                                <a href='${initParam['adminPath']}edit-item?itemid=${item['id']}' class='btn btn-success'><i class='fa fa-edit'></i> Edit</a>
                                <a href='${initParam['adminPath']}delete-item?itemid=${item['id']}' class='btn btn-danger confirm'><i class='fa fa-close'></i> Delete</a>
                                <c:if test="${item['approve'] eq 0}">
                                    <a href='${initParam['adminPath']}approve-item?itemid=${item['id']}' class='btn btn-info activate'> <i class='fa fa-check'></i> Approve</a>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
            <a href="${initParam['adminPath']}add-item" class="btn btn-sm btn-primary">
                <i class="fa fa-plus"></i> New Item
            </a>
        </div>

    </c:when>
    <c:otherwise>
        <div class="container">
            <div class="nice-message">There's No Items To Show</div>
            <a href="${initParam['adminPath']}add-item" class="btn btn-sm btn-primary">
                <i class="fa fa-plus"></i> New Item
            </a>
        </div>
    </c:otherwise>
</c:choose>
<c:import url="${initParam['adminIncludePath']}footer.jsp"/>
