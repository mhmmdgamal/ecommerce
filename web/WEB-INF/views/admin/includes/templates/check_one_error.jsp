<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="row">
    <div class="container">
        <c:if test="${sessionScope['error'] ne null}">

            <div class="alert alert-danger alert-dismissible col-sm-12">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                ${sessionScope['error']}
            </div>
            <c:remove var="error" />
        </c:if>
        <c:if test="${sessionScope['success'] ne null}">
            <div class="alert alert-success alert-dismissible col-sm-12">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                ${sessionScope['success']}
            </div>
            <c:remove var="success" />
        </c:if>

    </div>
</div>