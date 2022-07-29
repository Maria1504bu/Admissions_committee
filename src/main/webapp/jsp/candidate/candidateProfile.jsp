<%--
  Created by IntelliJ IDEA.
  User: knkyu
  Date: 06.07.2022
  Time: 18:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<html>
<head>
    <title>Welcome</title>
</head>
<body>
<h3>Welcome</h3>
<hr/>
${user}, hello!
<hr/>
<b2> Your exams:
</b2>
${exams}
lo
${exams[0]}
<a href="controller?command=Logout">Logout</a>
</body>
</html>
