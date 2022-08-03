<%--
  Created by IntelliJ IDEA.
  User: knkyu
  Date: 26.07.2022
  Time: 15:38
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/jspf/directives.jspf" %>
<html>
<head>
    <title>Faculties</title>
</head>
<body>
    <h2> All faculties:</h2>
    <table>
        <div class="faculties">
            <tr>
                <th>Id</th>
                <th>Name</th>
                <th>Budget places</th>
                <th>All places</th>
            </tr>
            <form action="/controller" method="post">
                <input type="hidden" name="command" value="edit_faculty">
                <c:forEach items="${faculties}" var="faculty">
                    <tr>
                        <input type="hidden" name="facultyToEdit" value="${faculty.id}">
                        <td>${faculty.id}</td>
                        <td>${faculty.name}</td>
                        <td>${faculty.budgetPlaces}</td>
                        <td>${faculty.allPlaces}</td>
                        <td><input type="submit" value="Edit"></td>
                        <td><input type="button" value="Delete"></td>
                    </tr>
                </c:forEach>
            </form>
        </div>
    </table>
    <hr>

    <div>
        <h2> Add exam:</h2>
        <form action="/controller" method="post">
            <input type="hidden" name="command" value="add_faculty">
            Faculty name :
            <input type=text name="newFaculty">
            <br>
            Budget places :
            <input type="number" name="budgetPlaces">
            <br>
            All places :
            <input type="number" name="allPlaces">
            <br>
            <input type="submit" value="Add">
            ${alreadyExist}
        </form>
</div>
<br>
<a href="controller?command=Logout">Logout</a>
</body>
</html>
