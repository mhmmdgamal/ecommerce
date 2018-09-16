<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <title>${title}</title>
        <link rel="stylesheet" href="${initParam['customerLayoutPath']}css/bootstrap.min.css" />
        <link rel="stylesheet" href="${initParam['customerLayoutPath']}css/font-awesome.min.css" />
        <link rel="stylesheet" href="${initParam['customerLayoutPath']}css/jquery-ui.css" />
        <link rel="stylesheet" href="${initParam['customerLayoutPath']}css/jquery.selectBoxIt.css" />
        <link rel="stylesheet" href="${initParam['customerLayoutPath']}css/front.css" />
    </head>
    <body>
        <div class="upper-bar">
            <div class="container">
                <c:choose>
                    <c:when test="${sessionScope['user'] ne null}">
                        <img class="my-image img-thumbnail img-circle" src="${initParam['customerImgPath']}img.png" alt="No Image" />
                        <div class="btn-group my-info">
                            <span class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                ${sessionScope['fullName']}
                                <span class="caret"></span>
                            </span>
                            <ul class="dropdown-menu">
                                <li><a href="${initParam['customerPath']}profile">My Profile</a></li>
                                <li><a href="${initParam['customerPath']}newad">New Item</a></li>
                                <!--TODO: not work think again--> 
                                <li><a href="${initParam['customerPath']}profile?id=my-ads">My Items</a></li>
                                <li><a href="${initParam['customerPath']}logout">Logout</a></li>
                            </ul>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <a href="${initParam['customerPath']}login">
                            <span class="pull-right">Login/Signup</span>
                        </a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <nav class="navbar navbar-inverse">
            <div class="container">
                <div class="navbar-header">
                    <button 
                        type="button" 
                        class="navbar-toggle collapsed" 
                        data-toggle="collapse" 
                        data-target="#app-nav" 
                        aria-expanded="false">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="${initParam['customerPath']}">Homepage</a>
                </div>
                <div class="collapse navbar-collapse" id="app-nav">
                    <ul class="nav navbar-nav navbar-right">
                        <!--                       
                        <?php
                        $allCats = getAllFrom("*", "categories", "where parent = 0", "", "ID", "ASC");
                        foreach ($allCats as $cat) {
                            echo
                            '<li><a href="categories.php?pageid=' . $cat['ID'] . '">
				  ' . $cat['Name'] . '</a></li>';
                        }?>
                        -->

                        <c:forEach items="${navCategories}" var="category">
                            <li>
                                <a href="${initParam['customerPath']}categories?pageid=${category.id}">${category.name}</a>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </nav> 