<%--
  Created by IntelliJ IDEA.
  User: knkyu
  Date: 31.07.2022
  Time: 22:09
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/jspf/directives.jspf" %>
<html>
<head>
    <title>Exams</title>
</head>
<body>
<h2> All exams:</h2>
<table>
    <div class="mainExams">
    <form action="/controller" method="post">
        <input type="hidden" name="command" value="edit_exam">
        <c:forEach items="${exams}" var="exam">
        <tr>
            <input type="hidden" name="examToEdit" value="${exam.id}">
            <td>${exam.id}</td>
            <td>${exam.name}</td>
            <td><input type="submit" value="Edit"></td>
        </tr>
        </c:forEach>
    </form>
        </div>
</table>
<hr>

<div>
    <h2> Add exam:</h2>
    <form action="/controller" method="post">
        <input type="hidden" name="command" value="add_exam">
        <input type=text name="newExam">
        <input type="submit" value="Add">
        ${alreadyExist}
    </form>
</div>
<br>
<a href="controller?command=Logout">Logout</a>
</body>
</html>
