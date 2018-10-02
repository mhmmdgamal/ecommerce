<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
                <c:forEach items="${navCategories}" var="category">
                    <li>
                        <a href="${initParam['customerPath']}categories?pageid=${category['id']}">${category['name']}</a>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>
</nav>