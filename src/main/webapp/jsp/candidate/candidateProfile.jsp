<%--
  Created by IntelliJ IDEA.
  User: knkyu
  Date: 06.07.2022
  Time: 18:00
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/jspf/directives.jspf" %>
<html>
<head>
    <title>Welcome</title>
</head>
<body>
<h3>Welcome</h3>
<hr/>
 ${user.email} hello!
<hr/>
<h4> Your exams:</h4>
<table>
    <tr>
        <th>Subject</th>
        <th>Mark</th>
    </tr>
    <c:forEach items="${candidatesExams}" var="examEntry">
        <tr>
            <td><c:out value="${examEntry.key}"/></td>
            <td><c:out value="${examEntry.value}"/></td>
        </tr>
    </c:forEach>
</table>
<hr/>
<h4>Add new exam</h4>
<form name="AddExamForm" action="/controller" method="post">
    <input type="hidden" name="command" value="add_candidate_exam"/>
    Choose new exam:
    <select name="examId">
        <c:forEach items="${notPassedExams}" var="exam">
            <option value="${exam.id}">${exam.name}</option>
        </c:forEach>
    </select>
    </br>
    Choose mark:
    <input type="number" min="100" max="200" name="mark">
    <input type="submit" value="Add">
    ${examIsAdded}
</form>
<hr/>
<a href="controller?command=logout">Logout</a>
</body>
</html>
