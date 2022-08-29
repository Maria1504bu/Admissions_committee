<%@ include file="/jspf/directives.jspf" %>
<c:set var="subj" value="${sessionScope['subToDisplay']}"/>
<html>
<head>
    <title>Update Subject</title>
    <%@ include file="/jspf/headDirectives.jspf" %>
    <style>
        <%@ include file="/css/styles.css" %>
    </style>
</head>
<body>
<!-- Header----------------------------------------------------------------------------------->
<%@ include file="/jspf/loginedHeader.jspf" %>
<!-- Body beginning--------------------------------------------------------------------------->
<div class="container">
    <div class="row justify-content-center align-items-center">
        <div class="col-sm-12 title-div">
            <h2 class="text-xs-center"><fmt:message key="updateSubj.Title"/></h2>
            <hr>
        </div>
        <div class="col-sm-6 form-div">
            <form method="POST" action='${pageContext.request.contextPath}/controller?command=updateSubject' id="subjectForm">
                <input type="hidden" name="subjId" value="${subj.getId()}"/>
                <div class="form-group">
                    <label for="subjEngName"><fmt:message key="updateSubj.SubjNameInEngl"/></label>
                    <input type="text" class="form-control form-input" id="subjEngName" placeholder="<fmt:message key="updateSubj.NameInEnglish"/>" name="subjEngName" required
                           value="${subj.getNameList().get(0)}">
                    <div class="invalid-feedback"><fmt:message key="createFaculty.FormFail"/></div>
                </div>
                <div class="form-group">
                    <label for="subjUkrName"><fmt:message key="updateSubj.SubjNameInUkr"/></label>
                    <input type="text" class="form-control form-input" id="subjUkrName" placeholder="<fmt:message key="updateSubj.NameInUkrainian"/>" name="subjUkrName" required
                           value="${subj.getNameList().get(1)}">
                    <div class="invalid-feedback"><fmt:message key="createFaculty.FormFail"/></div>
                </div>
                <div class="form-group">
                    <label for="courseDuration"><fmt:message key="updateSubj.CourseDuration"/></label>
                    <input type="text" class="form-control form-input" id="courseDuration" placeholder="<fmt:message key="updateSubj.CourseDurationPlcHlr"/>" name="courseDuration" required
                           value="${subj.getCourseDuration()}">
                    <div class="invalid-feedback"><fmt:message key="createFaculty.FormFail"/></div>
                </div>
                <br>
                <button type="submit" class="btn btn-primary"><fmt:message key="updateFaculty.UpdateBtn"/></button>
                <a href="${pageContext.request.contextPath}/controller?command=subjects">
                    <button type="button" class="btn btn-primary"><fmt:message key="createFaculty.CancelBtn"/></button></a>
            </form>

        </div>
    </div>

</div>

<!-- Footer ----------------------------------------->
<%@ include file="/jspf/footer.jspf" %>
</body>
</html>
