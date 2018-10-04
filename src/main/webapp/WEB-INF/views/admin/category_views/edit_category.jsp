<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.ecommerce.general.helper.PathsHelper" %>

<c:import url='${PathsHelper.getAdminInclude("header")}' />
<c:import url='${PathsHelper.getAdminInclude("navbar")}' />

<div class="container">
    <form action="${initParam['adminPath']}edit-category" class="form-horizontal" method="POST">

        <br><br>
        <input type="hidden" name="categoryid" value="${category['id']}" />
        <!-- Start Name Field -->
        <div class="form-group form-group-lg">
            <label class="col-sm-2 control-label">Name</label>
            <div class="col-sm-10 col-md-6">
                <input type="text" name="name" class="form-control" required="required" placeholder="Name Of The Category" value="${category['name']}" />
            </div>
        </div>
        <!-- End Name Field -->
        <!-- Start Description Field -->
        <div class="form-group form-group-lg">
            <label class="col-sm-2 control-label">Description</label>
            <div class="col-sm-10 col-md-6">
                <input type="text" name="description" class="form-control" placeholder="Describe The Category" value="${category['description']}" />
            </div>
        </div>
        <!-- End Description Field -->
        <!-- Start Ordering Field -->
        <div class="form-group form-group-lg">
            <label class="col-sm-2 control-label">Ordering</label>
            <div class="col-sm-10 col-md-6">
                <input type="text" name="ordering" class="form-control" placeholder="Number To Arrange The Categories" value="${category['ordering']}" />
            </div>
        </div>
        <!-- End Ordering Field -->

        <!-- Start Category Type -->
        <div class="form-group form-group-lg">
            <label class="col-sm-2 control-label">Parent</label>
            <div class="col-sm-10 col-md-6">
                <select name="parent">
                    <option value="0">None</option>
                    <c:forEach items="${requestScope['supCategories']}" var="supCategory">
                        <option value='${supCategory['id']}' ${(category['parent'] == supCategory['id']) ? "selected" : ""}>
                            ${supCategory['name']}
                        </option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <!-- End Category Type -->

        <!-- Start Visibility Field -->
        <div class="form-group form-group-lg">
            <label class="col-sm-2 control-label">Visible</label>
            <div class="col-sm-10 col-md-6">
                <div>
                    <input id="vis-yes" type="radio" name="visibility" value="0" ${(category['visibility']) eq 0 ? "checked" : ""} />
                    <label for="vis-yes">Yes</label> 
                </div>
                <div>
                    <input id="vis-no" type="radio" name="visibility" value="1" ${(category['visibility']) eq 1 ? "checked" : ""} />
                    <label for="vis-no">No</label> 
                </div>
            </div>
        </div>
        <!-- End Visibility Field -->
        <!-- Start Commenting Field -->
        <div class="form-group form-group-lg">
            <label class="col-sm-2 control-label">Allow Commenting</label>
            <div class="col-sm-10 col-md-6">
                <div>
                    <input id="com-yes" type="radio" name="commenting" value="0" ${(category['allowComments']) eq 0 ? "checked" : ""} />
                    <label for="com-yes">Yes</label> 
                </div>
                <div>
                    <input id="com-no" type="radio" name="commenting" value="1" ${(category['allowComments']) == 1 ? "checked" : ""} />
                    <label for="com-no">No</label> 
                </div>
            </div>
        </div>
        <!-- End Commenting Field -->
        <!-- Start Ads Field -->
        <div class="form-group form-group-lg">
            <label class="col-sm-2 control-label">Allow Ads</label>
            <div class="col-sm-10 col-md-6">
                <div>
                    <input id="ads-yes" type="radio" name="ads" value="0" ${(category['allowAds']) eq 0 ? "checked" : ""} />
                    <label for="ads-yes">Yes</label> 
                </div>
                <div>
                    <input id="ads-no" type="radio" name="ads" value="1" ${(category['allowAds']) eq 1 ? "checked" : ""} />
                    <label for="ads-no">No</label> 
                </div>
            </div>
        </div>
        <!-- End Ads Field -->
        <!-- Start Submit Field -->
        <div class="form-group form-group-lg">
            <div class="col-sm-offset-2 col-sm-10">
                <input type="submit" value="Save" class="btn btn-primary btn-lg" />
            </div>
        </div>
        <!-- End Submit Field -->
    </form>
</div>

<c:import url='${PathsHelper.getAdminInclude("footer")}' />
