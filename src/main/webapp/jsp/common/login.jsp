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
<header>
    <!-- Login Header beginning------------------------------------------------------------------->
    <tags:header></tags:header>
</header>
<!-- Body beginning--------------------------------------------------------------------------->
<div class="container indexMainCtr">
    <div class="row justify-content-center align-items-center">
        <div class="col-sm-12 title-div">
            <h2 class="text-xs-center"><fmt:message key="login.SignIn"/></h2>
            <hr>
        </div>
        <div class="col-sm-4 form-div">
            <form id="login_form" action="controller" method="post" class="was-validated" onsubmit="return loginFormValidation()">
                <input type="hidden" name="command" value="login"/>
                <div class="form-group">
                    <label for="login"><fmt:message key="login.Username"/></label>
                    <input type="text" class="form-control form-input" id="login" name="login" placeholder="<fmt:message key="login.EnterUsername"/>" required
                    pattern="^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$"
                    oninvalid="this.setCustomValidity('<fmt:message key="login.InvalidUsername"/>')">
                </div>
                <div class="form-group">
                    <label for="password"><fmt:message key="login.Password"/></label>
                    <input type="password" class="form-control form-input" id="password" placeholder="<fmt:message key="login.EnterPassword"/>" name="password" required
                           pattern="^(?=.*\d)(?=.*\p{Lower})(?=.*\p{Upper})(?!.*\s).{6,20}$"
                    oninvalid="this.setCustomValidity('<fmt:message key="login.InvalidPass"/>')">
                </div>
                <br>
                <button type="submit" class="btn btn-primary" id="login-btn"><fmt:message key="index.login"/></button>
            </form>
        </div>
    </div>
</div>
<!-- Modal ------------------------------------------>
<%@ include file="/jspf/modal.jspf" %>
<!-- Footer ----------------------------------------->
<%@ include file="/jspf/footer.jspf" %>
</body>
</html>

