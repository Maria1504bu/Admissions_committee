<%--
  Created by IntelliJ IDEA.
  User: knkyu
  Date: 31.07.2022
  Time: 18:25
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/jspf/directives.jspf" %>
<html>
<head>
    <title>Admin Profile</title>
</head>
<body>
<h3>Welcome, admin!</h3>
<hr/>
${user.id}, ${user.email} hello!
<hr/>
<a href="exams.jsp">Exams</a>
<br>
<a href="faculties.jsp">Faculties</a>
<br>
<a href="candidates.jsp">Candidates</a>
<br>
<a href="controller?command=Logout">Logout</a>
</body>
</html>
