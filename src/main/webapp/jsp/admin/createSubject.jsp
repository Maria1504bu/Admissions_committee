<%@ include file="/jspf/directives.jspf" %>
<html>
<head>
    <title>Create Subject</title>
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
            <h2 class="text-xs-center"><fmt:message key="createSubj.Title"/></h2>
            <hr>
        </div>
        <div class="col-sm-6 form-div">
            <form method="POST" action='${pageContext.request.contextPath}/controller?command=saveSubject' id="subjectForm" class="was-validated">
                <div class="form-group">
                    <label for="englishName"><fmt:message key="updateSubj.SubjNameInEngl"/></label>
                    <input type="text" class="form-control form-input" id="englishName" placeholder="<fmt:message key="updateSubj.NameInEnglish"/>" name="englishName" required>
                    <div class="invalid-feedback"><fmt:message key="createFaculty.FormFail"/></div>
                </div>
                <div class="form-group">
                    <label for="ukrainianName"><fmt:message key="updateSubj.SubjNameInUkr"/></label>
                    <input type="text" class="form-control form-input" id="ukrainianName" placeholder="<fmt:message key="updateSubj.NameInUkrainian"/>" name="ukrainianName" required>
                    <div class="invalid-feedback"><fmt:message key="createFaculty.FormFail"/></div>
                </div>
                <div class="form-group">
                    <label for="maxGrade"><fmt:message key="updateSubj.MaxGrade"/></label>
                    <input type="text" class="form-control form-input" id="maxGrade" placeholder="<fmt:message key="updateSubj.MaxGrade"/>" name="maxGrade" required>
                    <div class="invalid-feedback"><fmt:message key="createFaculty.FormFail"/></div>
                </div>
                <br>
                <button type="submit" class="btn btn-primary"><fmt:message key="createSubj.Title"/></button>
                <a href="${pageContext.request.contextPath}/admin/subjects/subjects.jsp">
                    <button type="button" class="btn btn-primary"><fmt:message key="createFaculty.CancelBtn"/></button></a>
            </form>
        </div>
    </div>
</div>

<!-- Footer ----------------------------------------->
<%@ include file="/jspf/footer.jspf" %>
</body>
</html>
