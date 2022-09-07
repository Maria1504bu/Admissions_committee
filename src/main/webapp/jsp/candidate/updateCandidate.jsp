<%@ include file="/jspf/directives.jspf" %>
<html>
<head>
    <title>Update Candidate</title>
    <%@ include file="/jspf/headDirectives.jspf" %>
    <style>
        <%@ include file="/css/styles.css" %>
    </style>
</head>
<body>

<c:set var="faculty" value="${sessionScope['facultyToDisplay']}"/>
<!-- Header----------------------------------------------------------------------------------->
<%@ include file="/jspf/candidateHeader.jspf" %>
<!-- Body beginning--------------------------------------------------------------------------->

<div class="container">
    <div class="row justify-content-center align-items-center">
        <div class="col-sm-12 title-div">
            <h2 class="text-xs-center"><fmt:message key="updateCandidate.Title"/></h2>
            <hr>
        </div>
        <div class="col-sm-6 form-div">
            <form method="POST" action='${pageContext.request.contextPath}/controller?command=updateCandidate'
                  id="candidateForm">
                <input type="hidden" name="candidateId" value="${user.getId()}"/>
                <div class="form-group">
                    <label for="email"><fmt:message key="candidate.email"/></label>
                    <input type="text" class="form-control form-input" id="email"
                           placeholder="<fmt:message key="candidate.email"/>" name="email"
                           value="${user.getEmail()}">
                </div>
                <div class="form-group">
                    <label for="secondName"><fmt:message key="candidate.secondName"/></label>
                    <input type="text" class="form-control form-input" id="secondName"
                           placeholder="<fmt:message key="candidate.secondName"/>" name="secondName" required
                           value="${user.getSecondName()}">
                </div>
                <div class="form-group">
                    <label for="firstName"><fmt:message key="candidate.firstName"/></label>
                    <input type="text" class="form-control form-input" id="firstName"
                           placeholder="<fmt:message key="candidate.firstName"/>" name="firstName" required
                           value="${user.getFirstName()}">
                </div>
                <div class="form-group">
                    <label for="fatherName"><fmt:message key="candidate.fatherName"/></label>
                    <input type="text" class="form-control form-input" id="fatherName"
                           placeholder="<fmt:message key="candidate.fatherName"/>" name="fatherName" required
                           value="${user.getFatherName()}">
                </div>
                <%--                //TODO: select --%>
                <div class="form-group">
                    <label for="city"><fmt:message key="candidate.city"/></label>
                    <select class="form-control" id="city" name="city" required>
                        <c:forEach items="${cities}" var="city">
                            <c:when test="${city eq user.getCity().name()}">
                                <option selected="selected" value="${city}">${city}</option>
                            </c:when>
                            <c:otherwise>
                                <option value="${city}">${city}</option>
                            </c:otherwise>
                        </c:forEach>
                    </select>
                    <div class="invalid-feedback"><fmt:message key="createFaculty.FormFail"/></div>
                </div>
                <div class="form-group">
                    <label for="schoolName"><fmt:message key="candidate.schoolName"/></label>
                    <input type="text" class="form-control form-input" id="schoolName"
                           placeholder="<fmt:message key="candidate.schoolName"/>" name="schoolName" required
                           value="${user.getSchoolName()}">
                </div>
                <br>
                <button type="submit" class="btn btn-primary"><fmt:message key="updateFaculty.UpdateBtn"/></button>
                <a href="${pageContext.request.contextPath}/candidate/candidateProfile.jsp">
                    <button type="button" class="btn btn-primary"><fmt:message key="createFaculty.CancelBtn"/></button>
                </a>
            </form>
        </div>
    </div>
</div>
<!-- Footer ----------------------------------------->
<%@ include file="/jspf/footer.jspf" %>

</body>
</html>
