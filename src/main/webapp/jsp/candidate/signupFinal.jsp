<%@ include file="/jspf/directives.jspf" %>
<html>
<head>
    <title>Final Registration</title>
    <%@ include file="/jspf/headDirectives.jspf" %>
    <style>
        <%@ include file="/css/styles.css" %>
    </style>
</head>
<body>
<!-- Header----------------------------------------------------------------------------------->
<tags:header></tags:header>
<!-- Body beginning--------------------------------------------------------------------------->

<div class="container">
    <div class="row justify-content-center align-items-center">
        <div class="col-sm-12 title-div">
            <h2 class="text-xs-center"><fmt:message key="registrationFinal.Title"/></h2>
            <hr>
        </div>
        <div class="col-sm-12 col-sm-12-custom">
            <h3 id="table-header"><fmt:message key="registrationFinal.CandidateDetails"/></h3>

            <form method="POST" action="${pageContext.request.contextPath}/controller" class="was-validated">
                <input type="hidden" name="command" value="signupFinal"/>
                <div class="form-group">
                    <label for="secondName"><fmt:message key="candidate.secondName"/></label>
                    <input type="text" class="form-control" id="secondName" placeholder="<fmt:message key="candidate.secondName"/>"
                           name="secondName" required>
                    <div class="invalid-feedback">Please fill out Second Name</div>
                    <div class="form-group">
                        <label for="firstName"><fmt:message key="candidate.firstName"/></label>
                        <input type="text" class="form-control" id="firstName" placeholder="<fmt:message key="candidate.firstName"/>" name="firstName"
                        required>
                        <div class="invalid-feedback">Please fill out first Name</div>
                    </div>
                </div>
                <div class="form-group">
                    <label for="fatherName"><fmt:message key="registrationFinal.FatherName"/></label>
                    <input type="text" class="form-control" id="fatherName" placeholder="<fmt:message key="registrationFinal.FatherNamePlch"/>"
                           name="fatherName" required>
                    <div class="invalid-feedback">Please fill out Father Name</div>
                </div>
                <div class="form-group">
                    <label for="city"><fmt:message key="candidate.city"/></label>
                    <select class="form-control" id="city" name="city" required>
                        <c:forEach items="${cities}" var="city">
                            <option value="${city}" >${city}</option>
                        </c:forEach>
                    </select>
                    <div class="invalid-feedback">Please fill out City</div>
                </div>
                <div class="form-group">
                    <label for="schoolName"><fmt:message key="registrationFinal.SchoolName"/></label>
                    <input type="text" class="form-control" id="schoolName" placeholder="<fmt:message key="registrationFinal.SchoolNamePlch"/>"
                           name="schoolName" required>
                    <div class="invalid-feedback">Please fill out School Name</div>
                </div>
                <div class="form-group form-check">
                    <label class="form-check-label">
                        <input class="form-check-input" type="checkbox" name="remember" required> <br><span><fmt:message key="registrationFinal.IAgree"/></span>
                        <div class="invalid-feedback"><fmt:message key="registrationFinal.CheckBox"/></div>
                    </label>
                </div>
                <input type="submit" class="btn btn-primary" value='<fmt:message key="registrationFinal.RegisterProfile"/>'/>
            </form>

        </div>
    </div>
</div>

<!-- Footer ----------------------------------------->
<%@ include file="/jspf/footer.jspf" %>
</body>
</html>
