<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="com.ecommerce.general.helper.PathesHelper" %>

<c:import url='${PathesHelper.getPublicInclude("header")}' />

<div class="container">
    <div class="row">
        <h1 class='text-center'>${requestScope['error']}</h1>
        <h1 class='text-center'>${requestScope['tag']}</h1>
        <c:forEach items="${tagItems}" var="item">
            <div class="col-sm-6 col-md-3">
                <div class="thumbnail item-box">
                    <span class="price-tag">${item['price']}</span>
                    <img class="img-responsive" src="${PathesHelper.getPublicImg('img.png')}" alt="No Image" />
                    <div class="caption">
                        <h3><a href="${initParam['customerPath']}show-item?itemid=${item['id']}">${item['name']}</a></h3>
                        <p>${item['description']}</p>
                        <div class="date">${item['addDate']}</div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<c:import url='${PathesHelper.getPublicInclude("footer")}' />