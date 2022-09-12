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
<!-- Login Header beginging------------------------------------------------------------------->
<tags:header></tags:header>
<!-- Body beginging--------------------------------------------------------------------------->
<div class="container">
    <div class="row justify-content-center align-items-center">
        <div class="col-sm-12 title-div">
            <h2 class="text-xs-center"><fmt:message key="registration.Title"/></h2>
            <hr>
        </div>
        <div class="col-sm-5 form-div">
            <form id="registration_form" action="controller" method="post" class="was-validated" onsubmit="return initialFormRegistValidation()">
                <input type="hidden" name="command" value="signupStart"/>
                <div class="form-group">
                    <label for="email"><fmt:message key="login.Username"/></label>
                    <input type="text" class="form-control form-input" id="email" name="email" placeholder="<fmt:message key="login.EnterUsername"/>" required>
                    <div class="invalid-feedback"><fmt:message key="createFaculty.FormFail"/></div>
                </div>
                <div class="form-group">
                    <label for="password"><fmt:message key="login.Password"/></label>
                    <input type="password" class="form-control form-input" id="password" placeholder="<fmt:message key="login.EnterPassword"/>" name="password" required>
                    <div class="invalid-feedback"><fmt:message key="createFaculty.FormFail"/></div>
                </div>
                <div class="form-group">
                    <label for="confirm-password"><fmt:message key="registration.ConfirmPassword"/></label>
                    <input type="password" class="form-control form-input" id="confirm-password" placeholder="<fmt:message key="registration.ReConfirmPassword"/>" name="confirm-password" required>
                    <div class="invalid-feedback"><fmt:message key="createFaculty.FormFail"/></div>
                </div>
                <br>
                <button type="submit" class="btn btn-primary"><fmt:message key="registration.SignUp"/></button>
            </form>
        </div>
    </div>
</div>
<!-- Modal ------------------------------------------>
<%@ include file="/jspf/modal.jspf" %>
<!-- JavaScript functions ---------------------------->
<%--<%@ include file="/js/javascript.jspf" %>--%>
<!-- Footer ----------------------------------------->
<%@ include file="/jspf/footer.jspf" %>

</body>
</html>
