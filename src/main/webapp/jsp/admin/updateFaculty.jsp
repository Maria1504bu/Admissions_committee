<%@ include file="/jspf/directives.jspf" %>

<html>
<head>
  <title>Update Faculty</title>
  <%@ include file="/jspf/headDirectives.jspf" %>
  <style>
    <%@ include file="/css/styles.css" %>
  </style>
</head>
<body>

<c:set var="faculty" value="${sessionScope['facultyToDisplay']}" />
<!-- Header----------------------------------------------------------------------------------->
<tags:header></tags:header>
<!-- Body beginning--------------------------------------------------------------------------->

<div class="container">
  <div class="row justify-content-center align-items-center">
    <div class="col-sm-12 title-div">
      <h2 class="text-xs-center"><fmt:message key="updateFaculty.Title"/></h2>
      <hr>
    </div>
    <div class="col-sm-6 form-div">
      <form method="POST" action='${pageContext.request.contextPath}/controller?command=updateFaculty' id="facultyForm">
        <input type="hidden" name="facultyId" value="${faculty.getId()}"/>
        <div class="form-group">
          <label for="englishName"><fmt:message key="createFaculty.NameInEnglish"/></label>
          <input type="text" class="form-control form-input" id="englishName" placeholder="<fmt:message key="createFaculty.PlaceHolderEngl"/>" name="englishName" required
                 value="${faculty.getNames().get(Language.valueOf("EN"))}">
          <div class="invalid-feedback"><fmt:message key="createFaculty.FormFail"/></div>
        </div>
        <div class="form-group">
          <label for="ukrainianName"><fmt:message key="createFaculty.NameInUkrainian"/></label>
          <input type="text" class="form-control form-input" id="ukrainianName" placeholder="<fmt:message key="createFaculty.PlaceHolderUkr"/>" name="ukrainianName" required
                 value="${faculty.getNames().get(Language.valueOf("UK"))}">
          <div class="invalid-feedback"><fmt:message key="createFaculty.FormFail"/></div>
        </div>
        <div class="form-group">
          <label for="budgetQty"><fmt:message key="createFaculty.BudgetPlaces"/></label>
          <input type="text" class="form-control form-input" id="budgetQty" placeholder="<fmt:message key="createFaculty.BudgetPlacesPlcHolder"/>" name="budgetQty" required
                 value="${faculty.getBudgetPlaces()}">
          <div class="invalid-feedback"><fmt:message key="createFaculty.FormFail"/></div>
        </div>
        <div class="form-group">
          <label for="totalQty"><fmt:message key="createFaculty.TotalPlaces"/></label>
          <input type="text" class="form-control form-input" id="totalQty" placeholder="<fmt:message key="createFaculty.TotalPlacesPlcHolder"/>" name="totalQty" required
                 value="${faculty.getTotalPlaces()}">
          <div class="invalid-feedback"><fmt:message key="createFaculty.FormFail"/></div>
        </div>
        <br>
        <button type="submit" class="btn btn-primary"><fmt:message key="updateFaculty.UpdateBtn"/></button>
        <a href="${pageContext.request.contextPath}/controller?command=faculties">
          <button type="button" class="btn btn-primary"><fmt:message key="createFaculty.CancelBtn"/></button></a>
      </form>
    </div>
  </div>
</div>
<!-- Footer ----------------------------------------->
<%@ include file="/jspf/footer.jspf" %>

</body>
</html>
