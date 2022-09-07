<%@ include file="/jspf/directives.jspf" %>
<html>
<head>
    <title>User DashBoard</title>
    <%@ include file="/jspf/headDirectives.jspf" %>
    <style>
        <%@ include file="/css/styles.css" %>
    </style>
</head>
<body>
<!-- Header----------------------------------------------------------------------------------->
<%@ include file="/jspf/candidateHeader.jspf" %>
<!-- Body beginging--------------------------------------------------------------------------->
<c:set var="selectedFacultyId1" value="${sessionScope['priority1Subjects']}" />
<c:set var="selectedFacultyId2" value="${sessionScope['priority2Subjects']}" />
<c:set var="selectedFacultyId3" value="${sessionScope['priority3Subjects']}" />
<c:set var="facultiesList" value="${sessionScope['facultiesList']}" />
<div class="container indexMainCtr">
    <div class="col-sm-12 col-sm-12-custom">
        <h2><fmt:message key="candidateDash.Title"/></h2>
        <hr>
        <h3>${e.getSecondName() += " " += e.getFirstName()}</h3>
        <h4>${e.getCity()}</h4>
        <hr>
    </div>
    <div class="col-sm-6 col-sm-6-custom">
        <h3 id="table-header"><fmt:message key="candidateInsertAppl.TableTitle"/></h3>

        <div id="choice1" class="priorityAplication">
            <hr>
            <h4><fmt:message key="candidateInsertAppl.Priority1"/></h4>
            <label for="selectFaculty1"><fmt:message key="candidateInsertAppl.SelectFaculty"/></label>
            <select class="form-control" name="faculty" id="selectFaculty1" onchange="selectRun()">
                <c:forEach items="${facultiesList}" var="faculty">
                    <option value="${faculty.getId()}"

                            <c:if test="${faculty.getId() eq selectedFacultyId1}">selected="selected"</c:if>
                    >
                            ${faculty.getNamesList().get(0)}
                    </option>
                </c:forEach>
            </select>
            <br>
            <c:forEach items="${facultiesList.get(selectedFacultyId1 - 1).getSubjectList()}" var="f" varStatus="loop">
                <div class="form-group was-validated">
                    <label for="priority1subj${f.getId()}"><c:out value="${f.getNameList().get(0)}"/>:</label>
                    <input type="text" class="form-control prioritySubjects" id="priority1subj${f.getId()}" placeholder="<fmt:message key="candidateInsertAppl.YourZNOGrade"/>" required>
                    <div class="invalid-feedback"><fmt:message key="createFaculty.FormFail"/></div>
                </div>
            </c:forEach>
        </div>

        <div id="choice2" class="priorityAplication">
            <hr>
            <h4><fmt:message key="candidateInsertAppl.Priority2"/></h4>
            <label for="selectFaculty2"><fmt:message key="candidateInsertAppl.SelectFaculty"/></label>
            <select class="form-control" name="faculty" id="selectFaculty2" onchange="selectRun()">
                <c:forEach items="${facultiesList}" var="faculty">
                    <option value="${faculty.getId()}"

                            <c:if test="${faculty.getId() eq selectedFacultyId2}">selected="selected"</c:if>
                    >
                            ${faculty.getNamesList().get(0)}
                    </option>
                </c:forEach>
<%--            </select>--%>
<%--            <br>--%>
<%--            <c:forEach items="${facultiesList.get(selectedFacultyId2 - 1).getSubjectList()}" var="s" varStatus="loop">--%>
<%--                <div class="form-group was-validated">--%>
<%--                    <label for="priority2subj${s.getId()}"><c:out value="${s.getNameList().get(0)}"/></label>--%>
<%--                    <input type="text" class="form-control prioritySubjects" id="priority2subj${s.getId()}" placeholder="<fmt:message key="candidateInsertAppl.YourZNOGrade"/>" required>--%>
<%--                    <div class="invalid-feedback"><fmt:message key="createFaculty.FormFail"/></div>--%>
<%--                </div>--%>
<%--            </c:forEach>--%>
        </div>

        <div id="choice3" class="priorityAplication">
            <hr>
            <h4><fmt:message key="candidateInsertAppl.Priority3"/></h4>
            <label for="selectFaculty3"><fmt:message key="candidateInsertAppl.SelectFaculty"/></label>
            <select class="form-control" name="faculty" id="selectFaculty3" onchange="selectRun()">
                <c:forEach items="${facultiesList}" var="faculty">
                    <option value="${faculty.getId()}"

                            <c:if test="${faculty.getId() eq selectedFacultyId3}">selected="selected"</c:if>
                    >
                            ${faculty.getNamesList().get(0)}
                    </option>
                </c:forEach>
            </select>
<%--            <br>--%>
<%--            <c:forEach items="${facultiesList.get(selectedFacultyId3 - 1).getSubjectList()}" var="s" varStatus="loop">--%>
<%--                <div class="form-group was-validated">--%>
<%--                    <label for="priority3subj${s.getId()}"><c:out value="${s.getNameList().get(0)}"/></label>--%>
<%--                    <input type="text" class="form-control prioritySubjects" id="priority3subj${s.getId()}" placeholder="<fmt:message key="candidateInsertAppl.YourZNOGrade"/>" required>--%>
<%--                    <div class="invalid-feedback"><fmt:message key="createFaculty.FormFail"/></div>--%>
<%--                </div>--%>
<%--            </c:forEach>--%>

</div>

<!-- Footer ----------------------------------------->
<%@ include file="/jspf/footer.jspf" %>
<!-- JavaScript functions ---------------------------->
<%--<%@ include file="/js/javascript.jspf" %>--%>
</body>
</html>
