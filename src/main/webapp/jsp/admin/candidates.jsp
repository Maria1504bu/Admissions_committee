<%--
  Created by IntelliJ IDEA.
  User: knkyu
  Date: 31.07.2022
  Time: 22:08
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/jspf/directives.jspf"%>
<html>
<head>
    <title>Candidates</title>
    </head>
<body>
<h2> All candidates:</h2>
<table>
    <div class="candidates">
        <form action="/controller" method="post">
            <input type="hidden" name="command" value="edit_candidates">
            <c:forEach items="${candidates}" var="candidate">
                <tr>
                    <input type="hidden" name="examToEdit" value="${candidate.id}">
                    <td>${candidate.id}</td>
                    <td>${candidate.surname}</td>
                    <td>${candidate.name}</td>
                    <td></td>
                    <td><input type="submit" value="Edit"></td>
                </tr>
            </c:forEach>
        </form>
    </div>
</table>
<hr>
<br>
<a href="controller?command=logout">Logout</a>
</body>
</html>
