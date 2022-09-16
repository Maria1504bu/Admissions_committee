<%@ include file="/jspf/directives.jspf" %>
<html>
<head>
    <title>User DashBoard</title>
    <%@ include file="/jspf/headDirectives.jspf" %>
    <style>
        <%@ include file="/css/styles.css" %>
        <%@ include file="/css/modalStyle.css" %>
    </style>
</head>
<body>
<!-- Login Header beginning------------------------------------------------------------------->
<tags:header></tags:header>
<!-- Body beginning--------------------------------------------------------------------------->
<div class="container">
    <div class="row justify-content-center align-items-center">
        <div class="col-sm-12 title-div">
            <h2 class="text-xs-center"><fmt:message key="registration.Title"/></h2>
            <hr>
        </div>
        <div class="col-sm-5 form-div">
            <form id="registration_form" action="controller" method="post" class="was-validated">
                <input type="hidden" name="command" value="signupStart"/>
                <div class="form-group">
                    <label for="email"><fmt:message key="login.Username"/></label>
                    <input type="text" class="form-control form-input" id="email" name="email"
                           placeholder="<fmt:message key="login.EnterUsername"/>" required
                           pattern="^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$"
                           oninvalid="this.setCustomValidity('<fmt:message key="login.InvalidUsername"/>')">
                </div>
                <div class="form-group">
                    <label for="password"><fmt:message key="login.Password"/></label>
                    <input type="password" class="form-control form-input" id="password"
                           placeholder="<fmt:message key="login.EnterPassword"/>" name="password" required
                           pattern="^(?=.*\d)(?=.*\p{Lower})(?=.*\p{Upper}).{6,20}$"
                           oninvalid="this.setCustomValidity('<fmt:message key="login.InvalidPass"/>')">
                </div>
                <div class="form-group">
                    <label for="confirm_password"><fmt:message key="registration.ConfirmPassword"/></label>
                    <input type="password" class="form-control form-input" id="confirm_password" placeholder="
<fmt:message key="registration.ReConfirmPassword"/>" name="confirm_password" required>
                </div>
                <br>
                <button type="submit" class="btn btn-primary"><fmt:message key="registration.SignUp"/></button>
            </form>
        </div>
    </div>
</div>
<!-- Footer ----------------------------------------->
<%@ include file="/jspf/footer.jspf" %>
<%@include file="/js/comparePasswords.jspf" %>
</body>
</html>
