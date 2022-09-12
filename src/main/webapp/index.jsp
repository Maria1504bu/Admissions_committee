<%@ include file="/jspf/directives.jspf" %>
<!DOCTYPE html>
<html>
<head>
    <title>Welcome page</title>
    <%@ include file="/jspf/headDirectives.jspf" %>
    <style>
        <%@ include file="/css/styles.css" %>
        <%@ include file="/css/modalStyle.css"%>
    </style>
</head>
<body>
<!-- Login Header beginning -------------------->
<tags:header></tags:header>
<!-- Login Header end -------------------------->
<br>
<div class="container indexMainCtr">
    <h2><fmt:message key="index.aboutUs"/></h2>
    <hr>
    <div id="about-us-text-id">
        <p class="indexText"><fmt:message key="index.sentence1"/></p>
        <p class="indexText"><fmt:message key="index.sentence2"/></p>
        <p class="indexText"><fmt:message key="index.sentence3"/></p>
    </div>

    <%--Hiden by default table--%>
    <table class="table table-hover" style="display:none" id="facultiesTable">
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
                <%@ page import="models.Language" %>
                <td>${loop.index + 1}</td>
                <td>${f.getNames().get(Language.valueOf(language.toString().toUpperCase()))}</td>
                <td class="td-to-align">${f.getBudgetPlaces()}</td>
                <td class="td-to-align">${f.getTotalPlaces()}</td>
                <td>
                    <form id="form${f.getId()}" action="${pageContext.request.contextPath}/controller" method="get">
                        <input type="hidden" name="command" value="applToFaculty">
                        <input type="hidden" name="facultyId" value="${f.getId()}">
                    <a  href="javascript:{}" onclick="createApplication(${f.getId()});">
                    <input type="button" value="<fmt:message key="index.sendApplication"/>">
                    </a>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<!-- Modal ------------------------------------------>
<%@ include file="/jspf/createApplModal.jspf" %>
<%@ include file="/jspf/pleaseLoginModal.jspf" %>
<!-- Footer ------------------------------------------>
<%@ include file="/jspf/footer.jspf" %>
<!-- JavaScript functions ---------------------------->
<%@ include file="/js/javascript.jspf" %>
<%@ include file="/js/createApplicationJs.jspf" %>

</body>
</html>
