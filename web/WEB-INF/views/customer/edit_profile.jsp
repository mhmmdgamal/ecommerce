<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:import url="includes/templates/header.jsp" />
<c:import url="includes/templates/check_errors.jsp" />
<c:import url="includes/templates/check_one_error.jsp" />


<h1 class="text-center">Edit Information of Profile</h1>
<div class="information block">
    <div class="container">
        <div class="panel panel-primary">
            <div class="panel-heading">Edit Information</div>
            <div class="panel-body">
                <form class="form-horizontal main-form" action="${initParam['customerPath']}edit-profile" method="POST">
                    <!-- Start id Field -->
                    <input type="hidden" name="itemid" value="${useri.d}" />
                    <!-- End id Field -->
                    <div class="form-group form-group-lg">
                        <label class="col-sm-3 control-label"><span>Login Name</span> :</label>
                        <div class="col-sm-10 col-md-9">
                            <input 
                                pattern=".{4,}"
                                title="This Field Require At Least 4 Characters"
                                type="text" 
                                name="name" 
                                class="form-control live"  
                                placeholder="Name of The Item"
                                data-class=".live-title"
                                required 
                                value="${user.name}"/>
                        </div>
                    </div>
                    <div class="form-group form-group-lg">
                        <label class="col-sm-3 control-label"><span>Password</span> :</label>
                        <div class="col-sm-10 col-md-9">
                            <input 
                                pattern=".{4,}"
                                title="This Field Require At Least 4 Characters"
                                type="password" 
                                name="pass" 
                                class="form-control live"  
                                placeholder="New password"
                                data-class=".live-title"
                                required 
                                value="${user.password}"/>
                        </div>
                    </div>
                    <div class="form-group form-group-lg">
                        <label class="col-sm-3 control-label"><span>Email</span> : </label>
                        <div class="col-sm-10 col-md-9">
                            <input 
                                pattern=".{4,}"
                                title="This Field Require At Least 4 Characters"
                                type="text" 
                                name="email" 
                                class="form-control live"  
                                placeholder="Name of The Item"
                                data-class=".live-title"
                                required 
                                value="${user.email}"/>
                        </div>
                    </div>
                    <div class="form-group form-group-lg">
                        <label class="col-sm-3 control-label"><span>Full Name</span> : </label>
                        <div class="col-sm-10 col-md-9">
                            <input 
                                pattern=".{4,}"
                                title="This Field Require At Least 4 Characters"
                                type="text" 
                                name="fullName" 
                                class="form-control live"  
                                placeholder="Name of The Item"
                                data-class=".live-title"
                                value="${user.fullName}"/>
                        </div>
                    </div>
                    <div class="form-group form-group-lg">
                        <label class="col-sm-3 control-label"><span>Registered Date</span> : </label>
                        <div class="col-sm-10 col-md-9">
                            <input 
                                pattern=".{4,}"
                                title="This Field Require At Least 4 Characters"
                                type="text" 
                                name="date" 
                                class="form-control live"  
                                placeholder="Name of The Item"
                                data-class=".live-title"
                                value="${user.date}"/>
                        </div>
                    </div>
                    <!-- Start Submit Field -->
                    <div class="form-group form-group-lg">
                        <div class="col-sm-offset-3 col-sm-9">
                            <input type="submit" value="Edit Information" class="btn btn-primary btn-sm" />
                        </div>
                    </div>
                    <!-- End Submit Field -->
                </form>
            </div>
        </div>
    </div>
</div>
<c:import url="includes/templates/footer.jsp" />
