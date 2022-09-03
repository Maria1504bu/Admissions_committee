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
<%@ include file="/jspf/candidateHeader.jspf" %>
<!-- Body begining--------------------------------------------------------------------------->

<div class="container-fluid indexMainCtr">
    <h2><fmt:message key="registrationFinal.Title"/></h2>
    <hr>
    <div class="row">
        <div class="col-sm-12 col-sm-12-custom">
            <h3 id="table-header"><fmt:message key="registrationFinal.CandidateDetails"/></h3>

            <form method="POST" action="/committee/controller" class="was-validated">
                <input type="hidden" name="command" value="signupFinal"/>
                <div class="form-group">
                    <label for="fatherName"><fmt:message key="registrationFinal.FatherName"/></label>
                    <input type="text" class="form-control" id="fatherName" placeholder="<fmt:message key="registrationFinal.FatherNamePlch"/>"
                           name="fatherName" required>
                    <div class="invalid-feedback">Please fill out Father Name</div>
                </div>
                <div class="form-group">
                    <label for="firstName"><fmt:message key="candidatesControlDash.tableThFirstName"/></label>
                    <input type="text" class="form-control" id="firstName" placeholder="<fmt:message key="candidatesControlDash.tableThFirstName"/>" name="firstName"
                           required>
                    <div class="invalid-feedback">Please fill out first Name</div>
                </div>
                <div class="form-group">
                    <label for="secondName"><fmt:message key="candidatesControlDash.tableThSecondName"/></label>
                    <input type="text" class="form-control" id="secondName" placeholder="<fmt:message key="candidatesControlDash.tableThSecondName"/>"
                           name="secondName" required>
                    <div class="invalid-feedback">Please fill out Second Name</div>
                </div>
                <div class="form-group">
                    <label for="city"><fmt:message key="candidatesControlDash.tableThCity"/></label>
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
                <input type="submit" class="btn btn-primary"><fmt:message key="registrationFinal.RegisterProfile"/></input>
            </form>

        </div>
    </div>
</div>

<!-- Footer ----------------------------------------->
<%@ include file="/jspf/footer.jspf" %>
</body>
</html>
