<%--
  Created by IntelliJ IDEA.
  User: knkyu
  Date: 19.07.2022
  Time: 16:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign Up</title>
</head>
<body>
<form name="SignupForm" method="Post" action="controller">
    <input type="hidden" name="command" value="signup"/>
    Sign up:<br/>
    <input type="email" name="login" value=""/>
    <br/>Password:<br/>
    <input type="password" name="password" value=""/>
    <br/>
    <input type="submit" value="Sign up"/>
${errorLoginPassMessage}
</form>
</body>
</html>
