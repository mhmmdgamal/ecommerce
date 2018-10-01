<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="${initParam['adminIncludePath']}header.jsp"/>
<c:import url="${initParam['adminIncludePath']}navbar.jsp"/>

<h1 class="text-center">Edit Item</h1>

<div class="container">

    <form action="${initParam['adminPath']}edit-item" class="form-horizontal" method="POST">

        <input type="hidden" name="itemid" value="${item.id}" />
        <!-- Start Name Field -->
        <div class="form-group form-group-lg">
            <label class="col-sm-2 control-label">Name</label>
            <div class="col-sm-10 col-md-6">
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
            <label class="col-sm-2 control-label">Description</label>
            <div class="col-sm-10 col-md-6">
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
            <label class="col-sm-2 control-label">Price</label>
            <div class="col-sm-10 col-md-6">
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
            <label class="col-sm-2 control-label">Country</label>
            <div class="col-sm-10 col-md-6">
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
            <label class="col-sm-2 control-label">Status</label>
            <div class="col-sm-10 col-md-6">
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
            <label class="col-sm-2 control-label">Member</label>
            <div class="col-sm-10 col-md-6">
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
            <label class="col-sm-2 control-label">Category</label>
            <div class="col-sm-10 col-md-6">
                <select name="category">
                    <c:forEach items="${requestScope['categories']}" var="category">
                        <option value='${category['id']}' ${(item['category']['id'] eq category['id']) ? "selected" : ""}>
                            ${category['name']}
                        </option>
                    </c:forEach>

                    <!--                    <?php
                                        $allCats = getAllFrom("*", "categories", "where parent = 0", "", "ID");
                                        foreach ($allCats as $cat) {
                                        echo "<option value='" . $cat['ID'] . "'";
                                        if ($item['Cat_ID'] == $cat['ID']) { echo ' selected'; }
                                        echo ">" . $cat['Name'] . "</option>";
                                        $childCats = getAllFrom("*", "categories", "where parent = {$cat['ID']}", "", "ID");
                                        foreach ($childCats as $child) {
                                        echo "<option value='" . $child['ID'] . "'";
                                        if ($item['Cat_ID'] == $child['ID']) { echo ' selected'; }
                                        echo ">--- " . $child['Name'] . "</option>";
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
                    placeholder="Separate Tags With Comma (,)" 
                    value="${item['tags']}" />
            </div>
        </div>
        <!-- End Tags Field -->
        <!-- Start Submit Field -->
        <div class="form-group form-group-lg">
            <div class="col-sm-offset-2 col-sm-10">
                <input type="submit" value="Save Item" class="btn btn-primary btn-sm" />
            </div>
        </div>
        <!-- End Submit Field -->
    </form>
    <h1 class="text-center">Manage [ ${item['name']} ] Comments</h1>
    <div class="table-responsive">
        <table class="main-table text-center table table-bordered">
            <tr>
                <td>Comment</td>
                <td>User Name</td>
                <td>Added Date</td>
                <td>Control</td>
            </tr>
            <c:forEach items="${requestScope['itemComments']}" var="comment">
                <tr>
                    <td>${comment['comment']}</td>
                    <td>${comment['user']['name']}</td>
                    <td>${comment['addDate']}</td>
                    <td>
                        <a href='${initParam['adminPath']}edit-comment?commentid=${comment['id']}' class='btn btn-success'><i class='fa fa-edit'></i> Edit</a>
                        <a href='${initParam['adminPath']}delete-comment?commentid=${comment['id']}' class='btn btn-danger confirm'><i class='fa fa-close'></i> Delete </a>
                        <c:if test="${comment['status'] eq 0}">

                            <a href='${initParam['adminPath']}approve-comment?commentid=${comment['id']}' class='btn btn-info activate'><i class='fa fa-check'></i> Approve</a>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>

<c:import url="${initParam['adminIncludePath']}footer.jsp"/>
