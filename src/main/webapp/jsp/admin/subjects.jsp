<%@ include file="/jspf/directives.jspf" %>

<%@ taglib prefix="tags" uri="subjTag" %>
<html>
<head>
    <title>Subjects DashBoard</title>
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
<div class="container-fluid indexMainCtr">
    <h2><fmt:message key="subjectsDash.Title"/></h2>
    <hr>
    <div class="row">
        <div class="col-sm-3">
            <p style="font-weight: bold;"><fmt:message key="candidates.navTitle"/></p>
            <ul class="nav nav-pills flex-column custom">
                <li class="nav-item">
                    <a class="nav-link"
                       href="${pageContext.request.contextPath}/jsp/admin/createSubject.jsp"><fmt:message
                            key="facultiesDash.CreatePill"/></a>
                </li>
                <li class="nav-item">
                    <form action="${pageContext.request.contextPath}/controller" method="POST" id="updateIdForm">
                        <input type="hidden" name="command" value="updateSubject"/>
                        <input type="hidden" name="updateSubjectId" id="updateS" value="">
                        <a class="nav-link" href="javascript:{}" onclick="updateSubj();"><fmt:message
                                key="facultiesDash.UpdatePill"/></a>
                    </form>
                </li>
                <li class="nav-item">
                    <form action="${pageContext.request.contextPath}/controller" method="POST" id="deleteIdForm">
                        <input type="hidden" name="command" value="deleteSubject"/>
                        <input type="hidden" name="deleteSubjectId" id="deleteS" value="">
                        <a class="nav-link" href="javascript:{}" onclick="deleteSubj();"><fmt:message
                                key="facultiesDash.DeletePill"/></a>
                    </form>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/jsp/admin/faculties.jsp"><fmt:message
                            key="facultiesDash.ReturnPill"/></a>
                </li>
            </ul>
        </div>
        <div class="col-sm-9 col-sm-9-custom">
            <h2 id="table-header"><fmt:message key="subjectsDash.OurSubjects"/></h2>
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>No</th>
                    <th><fmt:message key="createFaculty.SubjectName"/></th>
                    <th><fmt:message key="subjectsDash.CourseDuration"/></th>
                    <th><fmt:message key="candidates.tableThSelect"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${subjects}" var="f" varStatus="loop">
                    <tags:subj index="${loop.index + 1}" name="${f.getNameList().get(0)}"
                               courseDuration="${f.getCourseDuration()}"/>
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
<%@ include file="/js/subjectsJS.jspf" %>
<%@ include file="/js/modal.jspf" %>
</body>
</html>

