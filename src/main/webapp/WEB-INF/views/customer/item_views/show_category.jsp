<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="${initParam['publicIncludePath']}header.jsp"/>

<div class="container">
    <h1 class="text-center">Show Category Items</h1>
    <div class="row">
        <c:forEach items="${categoryItems}" var="item">
            <div class="col-sm-6 col-md-3">
                <div class="thumbnail item-box">
                    <span class="price-tag">${item['price']}</span>
                    <img class="img-responsive" src="${initParam['publicImgPath']}img.png" alt="" />
                    <div class="caption">
                        <h3><a href="${initParam['customerPath']}items?itemid=${item['id']}">${item['name']}</a></h3>
                        <p>${item['description']}</p>
                        <div class="date">${item['addDate']}</div>
                        </div>
                    </div>
                </div>
        </c:forEach>
    </div>
</div>

<c:import url="${initParam['publicIncludePath']}footer.jsp"/>