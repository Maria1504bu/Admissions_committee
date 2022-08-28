<%@ include file="/jspf/directives.jspf" %>
<html>
<head>
    <title>Create Faculty</title>
    <%@ include file="/jspf/headDirectives.jspf" %>
    <style>
        <%@ include file="/css/styles.css" %>
    </style>
</head>
<body>
<!-- Header----------------------------------------------------------------------------------->
<%@ include file="/jspf/adminHeader.jspf" %>
<!-- Body beginning--------------------------------------------------------------------------->

<div class="container">
    <div class="row justify-content-center align-items-center">
        <div class="col-sm-12 title-div">
            <h2 class="text-xs-center"><fmt:message key="createFaculty.Title"/></h2>
            <hr>
        </div>
        <div class="col-sm-6 form-div">
            <form method="POST" action="controller" id="facultyForm" class="was-validated">
                <input type="hidden" name="command" value="saveFaculty"/>
                <div class="form-group">
                    <label for="englishName"><fmt:message key="createFaculty.NameInEnglish"/></label>
                    <input type="text" class="form-control form-input" id="englishName"
                           placeholder="<fmt:message key="createFaculty.PlaceHolderEngl"/>" name="englishName" required>
                    <div class="invalid-feedback"><fmt:message key="createFaculty.FormFail"/></div>
                </div>
                <div class="form-group">
                    <label for="ukrainianName"><fmt:message key="createFaculty.NameInUkrainian"/></label>
                    <input type="text" class="form-control form-input" id="ukrainianName"
                           placeholder="<fmt:message key="createFaculty.PlaceHolderUkr"/>" name="ukrainianName"
                           required>
                    <div class="invalid-feedback"><fmt:message key="createFaculty.FormFail"/></div>
                </div>
                <div class="row justify-content-center ">
                    <div class="form-group">
                        <label for="budgetQty"><fmt:message key="createFaculty.BudgetPlaces"/></label>
                        <input type="text" class="form-control form-input" id="budgetQty"
                               placeholder="<fmt:message key="createFaculty.BudgetPlacesPlcHolder"/>" name="budgetQty"
                               required>
                        <div class="invalid-feedback"><fmt:message key="createFaculty.FormFail"/></div>
                    </div>
                    <div class="form-group">
                        <label for="totalQty"><fmt:message key="createFaculty.TotalPlaces"/></label>
                        <input type="text" class="form-control form-input" id="totalQty"
                               placeholder="<fmt:message key="createFaculty.TotalPlacesPlcHolder"/>" name="totalQty"
                               required>
                        <div class="invalid-feedback"><fmt:message key="createFaculty.FormFail"/></div>
                    </div>
                </div>
                <hr>
                <div id="optionDiv">
                    <div id="dynamicInput[0]">
                        <label><fmt:message key="createFaculty.SubjectName"/></label>
                        <select class="browser-default custom-select" name="subject">
                            <c:forEach items="${subjects}" var="subject">
                                <option value="${subject.getId()}"
                                        <c:if test="${subject.getId() eq selectedSubjId}">selected="selected"</c:if>
                                >
                                        ${subject.getNameList().get(0)}
                                </option>
                            </c:forEach>
                        </select>
                        <input class="btn btn-primary" type="button" value="+" onClick="addInput();">
                    </div>
                </div>
                <button type="submit" class="btn btn-primary dynamic"><fmt:message
                        key="createFaculty.CreateFaculty"/></button>
                <a href="${pageContext.request.contextPath}/jsp/admin/faculties.jsp">
                    <button type="button" class="btn btn-primary dynamic"><fmt:message
                            key="createFaculty.CancelBtn"/></button>
                </a>
            </form>
        </div>
    </div>
</div>
<!-- Footer ----------------------------------------->
<%@ include file="/jspf/footer.jspf" %>
<!-- JavaScript functions ---------------------------->
<%@ include file="/js/createFacultyJs.jspf" %>

</body>
</html>
