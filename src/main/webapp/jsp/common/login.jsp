<%@ page language ="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>Login</title>
</head>
<body>
<form name="LoginForm" method="Post" action="controller">
    <input type="hidden" name="command" value="Login"/>
    Login:<br/>
    <input type="email" name="login" value=""/>
    <br/>Password:<br/>
    <input type="password" name="password" value=""/>
    <br/>
    ${errorLoginPassMessage}
    <br/>
    ${wrongAction}
    <br/>
    ${nullPage}
    <br/>
    <input type="submit" value="Log in"/>
    <a href="controller?command=InitSignup">Sign Up</a>
</form>
<hr/>
</body>
</html>
