<%@ include file="/jspf/directives.jspf" %>
<html>
<head>
    <title>Faculties Info</title>
    <%@ include file="/jspf/headDirectives.jspf" %>
    <style>
        <%@ include file="/css/styles.css" %>
        <%@ include file="/css/modalStyle.css" %>
    </style>
</head>
<body>
<!-- Header----------------------------------------------------------------------------------->
<tags:header></tags:header>
<!-- Body beginning--------------------------------------------------------------------------->
<div class="container indexMainCtr">
    <h2><fmt:message key="facultiesDash.Title"/></h2>
    <hr>
    <div class="row">
        <div class="col-sm-3">
            <p style="font-weight: bold;"><fmt:message key="candidates.navTitle"/></p>
            <ul class="nav nav-pills flex-column custom">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/controller?command=prepareFacultyForm"><fmt:message key="facultiesDash.CreatePill"/></a>
                </li>
                <li class="nav-item">
                    <form action="${pageContext.request.contextPath}/controller" method="POST" id="updateIdForm">
                        <input type="hidden" name="command" value="updateFaculty"/>
                        <input type="hidden" name="updateFacultyId" id="updateF" value="">
                        <a class="nav-link" href="javascript:{}" onclick="updateFaculty();"><fmt:message key="facultiesDash.UpdatePill"/></a>
                    </form>
                </li>
                <li class="nav-item">
                    <form action="${pageContext.request.contextPath}/controller" method="POST" id="deleteIdForm">
                        <input type="hidden" name="command" value="deleteFaculty"/>
                        <input type="hidden" name="deleteFacultyId" id="deleteF" value="">
                        <a class="nav-link" href="javascript:{}" onclick="deleteFaculty();"><fmt:message key="facultiesDash.DeletePill"/></a>
                    </form>
                </li>
            </ul>
        </div>
        <div class="col-sm-9 col-sm-9-custom">
            <h2 id="table-header"><fmt:message key="facultiesDash.OurFaculties"/></h2>
            <h4> <fmt:message key="faculties.faculty"/> : ${appls.get(0).getFaculty().getNames().get(Language.valueOf(language.toString().toUpperCase()))}</h4>
            <h4><fmt:message key="faculties.BudgetPlaces"/> : ${appls.get(0).getFaculty().getBudgetPlaces()}</h4>
            <h4><fmt:message key="faculties.TotalPlaces"/> :  ${appls.get(0).getFaculty().getTotalPlaces()}</h4>
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>N</th>
                    <th><fmt:message key="candidates.tableThCandidate"/></th>
                    <c:forEach items="${appls.get(0).getGradesList()}" var="grade">
                    <th>${grade.getSubject().getNames().get(Language.valueOf(language.toString().toUpperCase()))}</th>
                    </c:forEach>
                    <th><fmt:message key="candidates.tableThStatus"/></th>
                    <th></th>
                </thead>
                <tbody>
                <c:forEach items="${appls}" var="a" varStatus="loop">
                    <tr>
                        <td>${loop.index + 1}</td>
                        <td><tags:initials secondName="${a.getCandidate().getSecondName()}" firstName="${a.getCandidate().getFirstName()}" fatherName="${a.getCandidate().getFatherName()}"></tags:initials></td>
                        <c:forEach items="${a.getGradesList()}" var="grade">
                            <td class="td-to-align">${grade.getGrade()}</td>
                        </c:forEach>
                        <td>
                            ${a.getApplicationStatus()}
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<!-- Modal ------------------------------------------>
<%@ include file="/jspf/modal.jspf" %>
<!-- Footer ----------------------------------------->
<%@ include file="/jspf/footer.jspf" %>

<!-- JavaScript functions ---------------------------->
<%@ include file="/js/facultiesDashJS.jspf" %>
<%@ include file="/js/modal.jspf" %>

</body>
</html>

