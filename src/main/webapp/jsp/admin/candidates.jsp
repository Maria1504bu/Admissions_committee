<%@ include file="/jspf/directives.jspf" %>
<%--set init values to pagination if admin select any parameters--%>
<c:set var="selectedFacultyId" value="${empty param.selectedFacultyId ? 1 : param.selectedFacultyId}"/>"
<c:set var="limit" value="${empty param.limitItems ? 2 : param.limitItems}"/>
<c:set var="offset" value="${empty param.offset ? 0 : param.offset}"/>
<html>
<head>
    <title>Control of Candidates by Admin</title>
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
    <h2><fmt:message key="candidates.title"/></h2>
    <hr>
    <div class="row">
        <div class="col-sm-3">
            <p style="font-weight: bold;"><fmt:message key="candidates.navTitle"/></p>
            <ul class="nav nav-pills flex-column custom">
                <li class="nav-item">
                    <form action="${pageContext.request.contextPath}/controller" method="POST" id="showCandidateForm">
                        <input type="hidden" name="command" value="candidateDetails"/>
                        <input type="hidden" name="selectedCandidateId" id="candidateId" value="">
                        <a class="nav-link" href="javascript:{}" onclick="displayCandidate();"><fmt:message key="candidates.displayCanDetails"/></a>
                    </form>
                </li>
                <li class="nav-item">
                    <form action="${pageContext.request.contextPath}/controller" method="POST" id="blockCandidateForm">
                        <input type="hidden" name="command" value="blockCandidate"/>
                        <input type="hidden" name="blockCandidateId" id="candidateToBlockId" value="">
                        <a class="nav-link" href="javascript:{}" onclick="blockCandidate();"><fmt:message key="candidates.(un)blockCan"/></a>
                    </form>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/jsp/admin/faculties.jsp"><fmt:message key="candidates.return"/></a>
                </li>
            </ul>
        </div>
        <div class="col-sm-9 col-sm-9-custom">
            <h3 id="table-header"><fmt:message key="candidates.tableTile"/></h3>
            <select  class="browser-default custom-select" name="faculty" id="selectFaculty" onchange="selectFaculty()">
                <c:forEach items="${faculties}" var="faculty">
                    <option value="${faculty.getId()}"
                            <c:if test="${faculty.getId() eq selectedFacultyId}">selected="selected"</c:if>
                    >
                            ${faculty.getNames().get(Language.valueOf(language.toString().toUpperCase()))}
                    </option>
                </c:forEach>
            </select><br>
            <hr>
            <div id="limSelectDiv">
                <select class="browser-default custom-select" id="limSelect" onchange="selectLimit(this)">
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="5">5</option>
                    <option value="10">10</option>
                    <option value="25">25</option>
                </select>
            </div>

            <table class="table table-hover" id="tCandidates">
                <thead>
                <tr>
                    <th>No</th>
                    <th><fmt:message key="candidate.secondName"/></th>
                    <th><fmt:message key="candidate.firstName"/></th>
                    <th><fmt:message key="candidate.city"/></th>
                    <th><fmt:message key="candidates.tableThStatus"/></th>
                    <th><fmt:message key="candidates.tableThApplicationDate"/></th>
                    <th><fmt:message key="candidates.tableThSelect"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${candidates}" var="c" varStatus="loop">
                    <tr>
                        <td>${loop.index + 1}</td>
                        <td>${c.getSecondName()}</td>
                        <td>${c.getFirstName()}</td>
                        <td>${c.getCity()}</td>
                        <td class="td-to-align">${c.isBlocked() ? "Blocked" : "Approved"}</td>
                        <td class="td-to-align">${c.getApplicationDate()}</td>
                        <td class="td-to-align">
                            <INPUT type="radio" class="rgCandidates" name="rgCandidates" value="${c.getId()}"/>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

            <nav id="pag_nav" aria-label="Page navigation">
                <ul class="pagination justify-content-center">
                </ul>
            </nav>
        </div>
        <div>
<%--  set at the form values from previous query and by the js functions it changes when user click on certain elements and submits--%>
            <form id="paginationForm" action="${pageContext.request.contextPath}/controller" method="post">
                <input type="hidden" name="command" value="candidates"/>
                <input type="hidden" id="selFacultyId" name="selectedFacultyId" value="${selectedFacultyId}"/>
                <input type="hidden" id="limitItemsId" name="limitItems" value="${limit}"/>
                <input type="hidden" id="offsetId" name="offset" value="${offset}"/>
            </form>
        </div>
    </div>
</div>
<!-- Modal ------------------------------------------>
<%@ include file="/jspf/modal.jspf" %>
<!-- Footer ----------------------------------------->
<%@ include file="/jspf/footer.jspf" %>

<!-- JavaScript functions ---------------------------->
<%@ include file="/js/candidatesJS.jspf" %>
<%@ include file="/js/modal.jspf" %>

</body>
</html>
