<%@page import="com.ecommerce.general.path.ControllerPath"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.ecommerce.general.path.ViewPath" %>


<!-- Start Name Field -->
<div class="form-group form-group-lg">
    <label class="control-label">Name</label>
    <div class="">
        <input 
            type="text" 
            name="name" 
            class="form-control" 
            required="required"  
            placeholder="Name of The Item" />
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
            placeholder="Description of The Item" />
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
            placeholder="Price of The Item" />
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
            placeholder="Country of Made" />
    </div>
</div>
<!-- End Country Field -->
<!-- Start Status Field -->
<div class="form-group form-group-lg">
    <label class="control-label">Status</label>
    <div class="">
        <select name="status">
            <option value="0">...</option>
            <option value="1">New</option>
            <option value="2">Like New</option>
            <option value="3">Used</option>
            <option value="4">Very Old</option>
        </select>
    </div>
</div>
<!-- End Status Field -->
<!-- Start Members Field -->
<div class="form-group form-group-lg">
    <label class="control-label">User</label>
    <div class="">
        <select name="user">
            <option value="0">...</option>
            <c:forEach items="${users}" var="user">
                <option value='${user['id']}'>${user['name']}</option>
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
            <option value="0">...</option>
            <c:forEach items="${categories}" var="category">
                <option value='${category['id']}'>${category['name']}</option>
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
            placeholder="Separate Tags With Comma (,)" />
    </div>
</div>
<!-- End Tags Field -->
