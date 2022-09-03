<%@ include file="/jspf/directives.jspf" %>
<html>
<head>
  <title>Candidate Details</title>
  <%@ include file="/jspf/headDirectives.jspf" %>
  <style>
    <%@ include file="/css/styles.css" %>
  </style>
</head>
<body>
<!-- Header----------------------------------------------------------------------------------->
<%@ include file="/jspf/adminHeader.jspf" %>
<!-- Body beginning--------------------------------------------------------------------------->
<div class="container indexMainCtr">
  <div class="col-sm-12 col-sm-12-custom">
    <h2><fmt:message key="getCandidate.title"/></h2>
    <hr>
    <h3><c:out value="${candidate.getSecondName()+= ' '+= candidate.getFirstName() += ' ' += candidate.getFatherName()}"/></h3>
    <h5><c:out value="${'Login: ' += candidate.getEmail()}"/></h5>
    <h4><c:out value="${candidate.isBlocked() == true ? 'Status: Under Checking' : 'Status: Details Approved'}"/></h4>
    <hr>
    <h3 id="table-header"><fmt:message key="getCandidate.tableTitle"/></h3>
    <hr>
    <table class="table table-hover">
      <thead>
      <tr>
        <th><fmt:message key="getCandidate.Priority"/></th>
        <th><fmt:message key="getCandidate.Faculty"/></th>
        <th><fmt:message key="getCandidate.AverageGrade"/></th>
        <th><fmt:message key="getCandidate.Status"/></th>
      </tr>
      </thead>
      <tbody>
      <c:forEach items="${applications}" var="a" varStatus="loop">
      <tr>
        <td class="td-to-align">${a.getPriority()}</td>
        <td>${a.getFaculty().getNamesList().get(0)}</td>

          <c:set var="avaregeGrade" value="${0}"/>
        <c:forEach var="g" items="${a.getGradesList()}">
          <c:set var="avaregeGrade" value="${avaregeGrade + g.getGrade()}"/>
        </c:forEach>
          <c:set var="avaregeGrade" value="${avaregeGrade / a.getGradesList().size()}"/>
        <td class="td-to-align">${fn:substringBefore(avaregeGrade, '.')}</td>

        <td class="td-to-align">${a.getApplicationStatus()}</td>
        <c:forEach items="${a.getGradesList()}" var="g" varStatus="loop">
      <tr>
      <thead><th>${g.getSubject().getNameList().get(0)}</th></thead>
      </tr>
      <tr>
        <td>${g.getGrade()}</td>
      </tr>
      </c:forEach>
      </tr>
      </c:forEach>

      </tbody>
    </table>
    <br/><hr>
    <div id="certDiv" style="display:none">
      <h1><fmt:message key="getCandidate.CertCopy"/></h1>
      <img src="${pageContext.request.contextPath}/certificateUploads/${candidate.getCertificate()}" width="600"/>
    </div>
  </div>
</div>
<!-- Footer ------------------------------------------>
<%@ include file="/jspf/footer.jspf" %>

<!-- JavaScript functions ---------------------------->
<%@ include file="/js/javascript.jspf" %>
</body>
</html>

