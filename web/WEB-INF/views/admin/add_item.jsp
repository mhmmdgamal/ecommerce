<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="includes/templates/header.jsp"/>
<c:import url="includes/templates/navbar.jsp"/>

<h1 class="text-center">Add New Item</h1>
<div class="row">
    <div class="container">
        <c:if test="${requestScope['errors'] ne null}">
            <c:forEach items="${requestScope['errors']}" var="error">
                <div class="alert alert-danger alert-dismissible col-sm-offset-2 col-sm-10">
                    <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                    ${error}
                </div>
            </c:forEach>
            <c:remove var="error" />
        </c:if> 
        <c:if test="${requestScope['success'] ne null}">
            <div class="alert alert-success alert-dismissible col-sm-12">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                ${success}
            </div>
            <c:remove var="success" />
        </c:if>

    </div>
</div>
<div class="container">

    <form action="${initParam['adminJspPath']}items?action=Add" class="form-horizontal" method="POST">
        <!-- Start Name Field -->
        <div class="form-group form-group-lg">
            <label class="col-sm-2 control-label">Name</label>
            <div class="col-sm-10 col-md-6">
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
            <label class="col-sm-2 control-label">Description</label>
            <div class="col-sm-10 col-md-6">
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
            <label class="col-sm-2 control-label">Price</label>
            <div class="col-sm-10 col-md-6">
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
            <label class="col-sm-2 control-label">Country</label>
            <div class="col-sm-10 col-md-6">
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
            <label class="col-sm-2 control-label">Status</label>
            <div class="col-sm-10 col-md-6">
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
            <label class="col-sm-2 control-label">Member</label>
            <div class="col-sm-10 col-md-6">
                <select name="user">
                    <option value="0">...</option>
                    <c:forEach items="${users}" var="user">
                        <option value='${user.id}'>${user.name}</option>
                    </c:forEach>
                    <!--                    <?php
                                        $allMembers = getAllFrom("*", "users", "", "", "UserID");
                                        foreach ($allMembers as $user) {
                                        echo "<option value='" . $user['UserID'] . "'>" . $user['username'] . "</option>";
                                        }
                                        ?>-->
                </select>
            </div>
        </div>
        <!-- End Members Field -->
        <!-- Start Categories Field -->
        <div class="form-group form-group-lg">
            <label class="col-sm-2 control-label">Category</label>
            <div class="col-sm-10 col-md-6">
                <select name="category">
                    <option value="0">...</option>
                    <c:forEach items="${categories}" var="category">
                        <option value='${category.id}'>${category.name}</option>
                    </c:forEach>

                    <!--                    <?php
                                        $allCats = getAllFrom("*", "categories", "where parent = 0", "", "ID");
                                        foreach ($allCats as $cat) {
                                        echo "<option value='" . $cat['ID'] . "'>" . $cat['Name'] . "</option>";
                    
                                        $childCats = getAllFrom("*", "categories", "where parent = {$cat['ID']}", "", "ID");
                                        foreach ($childCats as $child) {
                                        echo "<option value='" . $child['ID'] . "'>--- " . $child['Name'] . "</option>";
                                        }
                                        }
                                        ?>-->
                </select>
            </div>
        </div>
        <!-- End Categories Field -->
        <!-- Start Tags Field -->
        <div class="form-group form-group-lg">
            <label class="col-sm-2 control-label">Tags</label>
            <div class="col-sm-10 col-md-6">
                <input 
                    type="text" 
                    name="tags" 
                    class="form-control" 
                    placeholder="Separate Tags With Comma (,)" />
            </div>
        </div>
        <!-- End Tags Field -->
        <!-- Start Submit Field -->
        <div class="form-group form-group-lg">
            <div class="col-sm-offset-2 col-sm-10">
                <input type="submit" value="Add Item" class="btn btn-primary btn-sm" />
            </div>
        </div>
        <!-- End Submit Field -->
    </form>
</div>
<c:import url="includes/templates/footer.jsp"/>
