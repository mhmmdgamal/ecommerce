<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="includes/templates/header.jsp"/>
<c:import url="includes/templates/navbar.jsp"/>

<h1 class="text-center">Manage Categories</h1>
<div class="row">
    <div class="container">
        <c:if test="${sessionScope['error'] ne null}">

            <div class="alert alert-danger alert-dismissible col-sm-12">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                ${error}
            </div>
            <c:remove var="error" />
        </c:if>
        <c:if test="${sessionScope['success'] ne null}">
            <div class="alert alert-success alert-dismissible col-sm-12">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                ${success}
            </div>
            <c:remove var="success" />
        </c:if>    </div>
</div>

<c:choose>
    <c:when test="${requestScope['categories'].size() gt 0}">

        <div class="container categories">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <i class="fa fa-edit"></i> Manage Categories
                    <div class="option pull-right">
                        <i class="fa fa-sort"></i> Ordering: [
                        <a class="${(sort == "ASC") ? "active" : "" }" href="?sort=ASC">Asc</a> | 
                        <a class="${(sort == "DES") ? "active" : "" }" href="?sort=DESC">Desc</a> ] 
                        <i class="fa fa-eye"></i> View: [
                        <span class="active" data-view="full">Full</span> |
                        <span data-view="classic">Classic</span> ]
                    </div>
                </div>
                <div class="panel-body">

                    <c:forEach items="${requestScope['categories']}" var="category">
                        <div class='cat'>
                            <div class='hidden-buttons'>
                                <a href='${initParam['sourceJspOfAdmin']}categories?action=Edit&categoryid=${category.id}' class='btn btn-xs btn-primary'><i class='fa fa-edit'></i> Edit</a>
                                <a href='${initParam['sourceJspOfAdmin']}categories?action=Delete&categoryid=${category.id}' class='confirm btn btn-xs btn-danger'><i class='fa fa-close'></i> Delete</a>
                            </div>
                            <h3>${category.name}</h3>
                            <div class='full-view'>
                                <p>${(category.description.equals("")) ? "This category has no description" : category.getDescription()}</p>

                                <c:if test="${category.visibility eq 1}">
                                    <span class="visibility cat-span"><i class="fa fa-eye"></i> Hidden</span> 
                                </c:if>

                                <c:if test="${category.allowComments eq 1}">
                                    <span class="commenting cat-span"><i class="fa fa-close"></i> Comment Disabled</span> 
                                </c:if>

                                <c:if test="${category.allowAds eq 1}">     
                                    <span class="advertises cat-span"><i class="fa fa-close"></i> Ads Disabled</span> 
                                </c:if>

                            </div>

                            <!--            // Get Child Categories
                                        $childCats = getAllFrom("*", "categories", "where parent = {$cat['ID']}", "", "ID", "ASC");
                                        if (! empty($childCats)) {
                                        echo "<h4 class='child-head'>Child Categories</h4>";
                                        echo "<ul class='list-unstyled child-cats'>";
                                        foreach ($childCats as $c) {
                                        echo "<li class='child-link'>
                                        <a href='categories.php?do=Edit&catid=" . $c['ID'] . "'>" . $c['Name'] . "</a>
                                        <a href='categories.php?do=Delete&catid=" . $c['ID'] . "' class='show-delete confirm'> Delete</a>
                                        </li>";
                                        }
                                        echo "</ul>";
                                        }-->

                        </div>
                        <hr
                    </div>
                </c:forEach>
            </div>
        </div>

        <a class="add-category btn btn-primary" href="${initParam['sourceJspOfAdmin']}categories?action=Add"><i class="fa fa-plus"></i> Add New Category</a>
    </div>
</c:when>
<c:otherwise>

    <div class="container">
        <div class="nice-message">There\'s No Categories To Show</div>
        <a href="${initParam['sourceJspOfAdmin']}categories?action=Add" class="btn btn-primary">
            <i class="fa fa-plus"></i> New Category
        </a>
    </div>
</c:otherwise>
</c:choose>

<c:import url="includes/templates/footer.jsp"/>
