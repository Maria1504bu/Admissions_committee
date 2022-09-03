<%@ include file="/jspf/directives.jspf" %>
<html>
<head>
    <title>Faculties DashBoard</title>
    <%@ include file="/jspf/headDirectives.jspf" %>
    <style>
        <%@ include file="/css/styles.css" %>
        <%@ include file="/css/modalStyle.css" %>
    </style>
</head>
<body>
<!-- Header----------------------------------------------------------------------------------->
<%@ include file="/jspf/adminHeader.jspf" %>
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
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>N</th>
                    <th><fmt:message key="faculties.faculty"/>
                        <a href="${pageContext.request.contextPath}/controller?command=faculties&order=DESC&by=name">&#9650</a>
                        <a href="${pageContext.request.contextPath}/controller?command=faculties&order=ASC&by=name">&#9660</a></th>
                    <th><fmt:message key="faculties.BudgetPlaces"/>
                        <a href="${pageContext.request.contextPath}/controller?command=faculties&order=DESC&by=budget_places">&#9650</a>
                        <a href="${pageContext.request.contextPath}/controller?command=faculties&order=ASC&by=budget_places">&#9660</a></th>
                    <th><fmt:message key="faculties.TotalPlaces"/>
                        <a href="${pageContext.request.contextPath}/controller?command=faculties&order=DESC&by=total_places">&#9650</a>
                        <a href="${pageContext.request.contextPath}/controller?command=faculties&order=ASC&by=total_places">&#9660</a></th>
                    <th><fmt:message key="candidates.tableThSelect"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${faculties}" var="f" varStatus="loop">
                    <tr>
                        <td>${loop.index + 1}</td>
                        <td>${f.getNamesList().get(0)}</td>
                        <td class="td-to-align">${f.getBudgetPlaces()}</td>
                        <td class="td-to-align">${f.getTotalPlaces()}</td>
                        <td>
                            <INPUT type="radio" class="radioGroup" name="radioGroup" value="${f.getId()}"/>
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

