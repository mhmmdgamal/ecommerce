<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Start Name Field -->
<div class="form-group form-group-lg">
    <label class="col-sm-3 control-label">Name</label>
    <div class="col-sm-10 col-md-9">
        <input 
            pattern=".{4,}"
            title="This Field Require At Least 4 Characters"
            type="text" 
            name="name" 
            class="form-control live"  
            placeholder="Name of The Item"
            data-class=".live-title"
            required />
    </div>
</div>
<!-- End Name Field -->
<!-- Start Description Field -->
<div class="form-group form-group-lg">
    <label class="col-sm-3 control-label">Description</label>
    <div class="col-sm-10 col-md-9">
        <input 
            pattern=".{10,}"
            title="This Field Require At Least 10 Characters"
            type="text" 
            name="description" 
            class="form-control live"   
            placeholder="Description of The Item" 
            data-class=".live-desc"
            required />
    </div>
</div>
<!-- End Description Field -->
<!-- Start Price Field -->
<div class="form-group form-group-lg">
    <label class="col-sm-3 control-label">Price</label>
    <div class="col-sm-10 col-md-9">
        <input 
            type="text" 
            name="price" 
            class="form-control live" 
            placeholder="Price of The Item" 
            data-class=".live-price" 
            required />
    </div>
</div>
<!-- End Price Field -->
<!-- Start Country Field -->
<div class="form-group form-group-lg">
    <label class="col-sm-3 control-label">Country</label>
    <div class="col-sm-10 col-md-9">
        <input 
            type="text" 
            name="country" 
            class="form-control" 
            placeholder="Country of Made" 
            required />
    </div>
</div>
<!-- End Country Field -->
<!-- Start Status Field -->
<div class="form-group form-group-lg">
    <label class="col-sm-3 control-label">Status</label>
    <div class="col-sm-10 col-md-9">
        <select name="status" required>
            <option value="0">...</option>
            <option value="1">New</option>
            <option value="2">Like New</option>
            <option value="3">Used</option>
            <option value="4">Very Old</option>
        </select>
    </div>
</div>
<!-- End Status Field -->
<!-- Start Categories Field -->
<div class="form-group form-group-lg">
    <label class="col-sm-3 control-label">Category</label>
    <div class="col-sm-10 col-md-9">
        <select name="category" required>
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
    <label class="col-sm-3 control-label">Tags</label>
    <div class="col-sm-10 col-md-9">
        <input 
            type="text" 
            name="tags" 
            class="form-control" 
            placeholder="Separate Tags With Comma (,)" />
    </div>
</div>
<!-- End Tags Field -->