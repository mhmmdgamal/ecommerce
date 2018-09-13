<nav class="navbar navbar-inverse">
  <div class="container">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#app-nav" aria-expanded="false">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="${pageContext.request.contextPath}/admin/dashboard">Home</a>
    </div>
    <div class="collapse navbar-collapse" id="app-nav">
      <ul class="nav navbar-nav">
        <li><a href="${pageContext.request.contextPath}/admin/categories">Categories</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/items">Items</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/users">Members</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/comments">Comments</a></li>
      </ul>
      <ul class="nav navbar-nav navbar-right">
        <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">${sessionScope['fullName']} <span class="caret"></span></a>
          <ul class="dropdown-menu">
              <li><a href="${pageContext.request.contextPath}/admin/">Visit Shop</a></li>
              <li><a href="${pageContext.request.contextPath}/admin/users?action=Edit&userid=${sessionScope['id']}">Edit Profile</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/settings">Settings</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/logout">Logout</a></li>
          </ul>
        </li>
      </ul>
    </div>
  </div>
</nav>