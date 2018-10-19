<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="com.ecommerce.general.path.ViewPath" %>
<%@page import="com.ecommerce.general.path.ControllerPath"%>


<input type="hidden" name="itemid" value="${item.id}" />
<!-- Start Name Field -->
<div class="form-group form-group-lg">
    <label class="control-label">Name</label>
    <div class="">
        <input 
            type="text" 
            name="name" 
            class="form-control" 
            required="required"  
            placeholder="Name of The Item"
            value="${item['name']}" />
    </div>
</div>
<!-- End Name Field -->
<!-- Start Description Field -->
<div class="form-group form-group-lg">
    <label class="control-label">Description</label>
    <div class="">
        <input 
            type="text" 
            name="description" 
            class="form-control" 
            required="required"  
            placeholder="Description of The Item"
            value="${item['description']}" />
    </div>
</div>
<!-- End Description Field -->
<!-- Start Price Field -->
<div class="form-group form-group-lg">
    <label class="control-label">Price</label>
    <div class="">
        <input 
            type="text" 
            name="price" 
            class="form-control" 
            required="required" 
            placeholder="Price of The Item"
            value="${item['price']}" />
    </div>
</div>
<!-- End Price Field -->
<!-- Start Country Field -->
<div class="form-group form-group-lg">
    <label class="control-label">Country</label>
    <div class="">
        <input 
            type="text" 
            name="country" 
            class="form-control" 
            required="required" 
            placeholder="Country of Made"
            value="${item['countryMade']}" />
    </div>
</div>
<!-- End Country Field -->
<!-- Start Status Field -->
<div class="form-group form-group-lg">
    <label class="control-label">Status</label>
    <div class="">
        <select name="status">
            <option value="1" ${(item['status'].equals("1")) ? "selected" : ""}>New</option>
            <option value="2" ${(item['status'].equals("2")) ? "selected" : ""}>Like New</option>
            <option value="3" ${(item['status'].equals("3")) ? "selected" : ""}>Used</option>
            <option value="4" ${(item['status'].equals("4")) ? "selected" : ""}>Very Old</option>
        </select>
    </div>
</div>
<!-- End Status Field -->
<!-- Start Members Field -->
<div class="form-group form-group-lg">
    <label class="control-label">Member</label>
    <div class="">
        <select name="user">

            <c:forEach items="${requestScope['users']}" var="user">
                <option value='${user['id']}' ${(item['user']['id'] eq user['id']) ? "selected" : ""}>
                    ${user['name']}
                </option>

            </c:forEach>
        </select>
    </div>
</div>
<!-- End Members Field -->
<!-- Start Categories Field -->
<div class="form-group form-group-lg">
    <label class="control-label">Category</label>
    <div class="">
        <select name="category">
            <c:forEach items="${requestScope['categories']}" var="category">
                <option value='${category['id']}' ${(item['category']['id'] eq category['id']) ? "selected" : ""}>
                    ${category['name']}
                </option>
            </c:forEach>
        </select>
    </div>
</div>
<!-- End Categories Field -->
<!-- Start Tags Field -->
<div class="form-group form-group-lg">
    <label class="control-label">Tags</label>
    <div class="">
        <input 
            type="text" 
            name="tags" 
            class="form-control" 
            placeholder="Separate Tags With Comma (,)" 
            value="${item['tags']}" />
    </div>
</div>
<!-- End Tags Field -->