<%@ include file="/jspf/directives.jspf" %>
<%@ include file="/jspf/lang.jspf" %>

<html>
<head>
    <title>Display Candidate</title>
<%--    <%@ include file="/jspf/headDirectives.jspf" %>--%>
    <style>
        <%@ include file="/css/styles.css" %>
    </style>
</head>
<body>
<!-- Header----------------------------------------------------------------------------------->
<%@ include file="/jspf/loginedHeader.jspf" %>
<!-- Body beginging--------------------------------------------------------------------------->

<c:set var="candidate" value="${sessionScope['user']}"/>
<c:set var="applList" value="${sessionScope['applicationsList']}"/>

<div class="container indexMainCtr">
    <div class="col-sm-12 col-sm-12-custom">
        <h2><fmt:message key="candidateDash.Title"/></h2>
        <hr>

        <h3 id="table-header"><fmt:message key="candidateDash.YourDetails"/></h3>
        <h3>${candidate.getSecondName() += " " += candidate.getFirstName()}</h3>
        <h4>${candidate.getCity()}</h4>
        <h4><c:out
                value="${candidate.isBlocked() == true ? 'Candidate Status: Under Checking' : 'Candidate Status: Ddetails Approved'}"/></h4>

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
            <c:forEach items="${applList}" var="a" varStatus="loop">
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
            <thead>
            <th>${g.getSubject().getNameList().get(0)}</th>
            </thead>
            </tr>
            <tr>
                <td>${g.getGrade()}</td>

            </tr>
            </c:forEach>
            </tr>
            </c:forEach>

            </tbody>
        </table>
    </div>
    <hr>
    <form method="POST" action="controller" enctype="multipart/form-data">
        <label class="btn btn-primary" for="certificateFile"><fmt:message key="candidateDash.CertificateImage"/></label>
        <input type="file" name="certificateFile" id="certificateFile" class="d-none"><br>
        <input type="submit" class="btn btn-primary" value="<fmt:message key="candidateDash.UploadCertificate"/>"/>
    </form>
          ${message}
        <br/><br/>
        <button class="btn btn-primary" type="button" id="getCertbtnEnrlProfile" onclick="getCertCandidateProfile(this)">
            <fmt:message key="candidateDash.ShowCertificate"/></button>
        <hr>
        <div id="certDiv" style="display:none">
            <h1><fmt:message key="candidateDash.CertScan"/></h1>
            <img src="/certificateUploads/${candidate.getCertificate()}" width="600" />
            ${candidate.getCertificate()}
        </div>
    </div>
</div>

<!-- Footer ----------------------------------------->
<%@ include file="/jspf/footer.jspf" %>

<!-- JavaScript functions ---------------------------->
<%@ include file="/js/javascript.jspf" %>

</body>
</html>