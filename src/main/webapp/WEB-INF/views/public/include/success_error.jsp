<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container">
    <c:if test="${requestScope['errors'] ne null}">
        <c:forEach items="${requestScope['errors']}" var="error">
            <div class="alert alert-danger alert-dismissible">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                ${error}
            </div>
        </c:forEach>
        <c:remove var="errors" />
    </c:if> 
    
    <c:if test="${requestScope['error'] ne null}">
            <div class="alert alert-danger alert-dismissible">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                ${error}
            </div>
        <c:remove var="error" />
    </c:if>
    
    <c:if test="${sessionScope['error'] ne null}">
            <div class="alert alert-danger alert-dismissible">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                ${error}
            </div>
        <c:remove var="error" />
    </c:if> 
    
    
    <c:if test="${requestScope['success'] ne null}">
        <div class="alert alert-success alert-dismissible">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            ${success}
        </div>
        <c:remove var="success" />
    </c:if>
    
    <c:if test="${sessionScope['success'] ne null}">
        <div class="alert alert-success alert-dismissible">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            ${success}
        </div>
        <c:remove var="success" />
    </c:if>
</div>