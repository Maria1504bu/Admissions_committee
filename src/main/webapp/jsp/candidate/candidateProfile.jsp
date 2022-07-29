<%--
  Created by IntelliJ IDEA.
  User: knkyu
  Date: 06.07.2022
  Time: 18:00
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/jspf/directives.jspf"%>
<html>
<head>
    <title>Welcome</title>
</head>
<body>
<h3>Welcome</h3>
<hr/>
${user.id}, ${user.email} hello!
<hr/>
<h4> Your exams:</h4>
${exams}
<hr/>
<h4>Add new exam</h4>
<%--//TODO: command--%>
    <%-- <input type="hidden" name="command" value=""/>--%>
Choose new exam:
    <select name="list">
        <c:forEach items="${exams}" var="exam">
            <option>${exam.name}</option>
        </c:forEach>
    </select>
    </br>
Choose mark:
    <input type="number" min="100" aria-valuemax="200" name="mark">
    <input type="button" value="Add">
</form>
<hr/>
<a href="controller?command=Logout">Logout</a>
</body>
</html>
