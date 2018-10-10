<%@page import="com.ecommerce.general.path.ResourcePath"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="com.ecommerce.general.path.ViewPath" %>

<c:import url='<%=ViewPath.header%>' />

<div class="container login-page">
    <h1 class="text-center">
        <span class="selected" data-class="login">Login</span> | 
        <span data-class="signup">Signup</span>
    </h1>

    <c:import url="<%=ViewPath.login_Form%>" />
    <c:import url="<%=ViewPath.register_Form%>" />

</div>



<!-- Start Scroll To Top -->
<div class="scroll-to-top">
    <span></span>
</div>
<!-- Start Scroll To Top -->
<div class="footer text-center">
    <div class="container">
        <div class="row">
            <!-- Start Col-Sm-6 -->
            <div class="col-xs-12 col-sm-6">
                <p class="copyright">
                    Made With 
                    <i class="fa fa-heart"></i> 
                    By  Name &copy; 
                    2018
                </p>
            </div>
            <!-- End Col-Sm-6 -->
            <!-- Start Col-Sm-6 -->
            <div class="col-xs-12 col-sm-6">
                <ul class="list-unstyled social text-right">
                    <li>
                        <a target="_blank" href="#">
                            <i class="fa fa-youtube fa-fw fa-2x"></i>
                        </a>
                    </li>
                    <li>
                        <a target="_blank" href="#">
                            <i class="fa fa-facebook fa-fw fa-2x"></i>
                        </a>
                    </li>
                    <li>
                        <a target="_blank" href="#">
                            <i class="fa fa-github fa-fw fa-2x"></i>
                        </a>
                    </li>
                    <br class="visible-xs">
                    <li>
                        <a target="_blank" href="#">
                            <i class="fa fa-twitter fa-fw fa-2x"></i>
                        </a>
                    </li>
                    <li>
                        <a target="_blank" href="#">
                            <i class="fa fa-codepen fa-fw fa-2x"></i>
                        </a>
                    </li>
                    <li>
                        <a target="_blank" href="#">
                            <i class="fa fa-instagram fa-fw fa-2x"></i>
                        </a>
                    </li>
                </ul>
            </div>
            <!-- End Col-Sm-6 -->

        </div>
    </div>
</div>
<script src="<%=ResourcePath.js%>jquery-1.12.1.min.js"></script>
<script>
    $(function () {
        var flag = false;
        loginResults = $('#login-results');
        $('#login-form').on('submit', function (e) {
            e.preventDefault();
            if (flag === true) {
                return false;
            }
            form = $(this);
            requestUrl = form.attr('action');
            requestMethod = form.attr('method');
            requestData = form.serialize();
            $.ajax({
                url: requestUrl,
                type: requestMethod,
                data: requestData,
                dataType: 'json',
                beforeSend: function () {
                    flag = true;
                    $('input[name="login"]').attr('disabled', true);
                    loginResults.removeClass().addClass('alert alert-info').html('Logging...');
                },
                success: function (results) {
                    if (results.errors.length > 0) {
                        loginResults.removeClass().addClass('alert alert-danger').html(results.errors);
                        $('input[name="login"]').removeAttr('disabled');
                        flag = false;
                    } else if (results.success !== null) {
                        loginResults.removeClass().addClass('alert alert-success').html(results.success);
                        if (results.redirect) {
                            window.location.href = results.redirect;
                        }
                    }
                }
            });
        });
    });
</script>
<script src="<%=ResourcePath.js%>jquery-ui.min.js"></script>
<script src="<%=ResourcePath.js%>bootstrap.min.js"></script>
<script src='<%=ResourcePath.js%>nicescroll.min.js")}'></script>
<script src="<%=ResourcePath.js%>jquery.selectBoxIt.min.js"></script>
<script src="<%=ResourcePath.js%>ecommerce.js"></script>
</body>
</html>