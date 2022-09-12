<%@attribute name="secondName" required="true" %>
<%@attribute name="firstName" required="true" %>
<%@attribute name="fatherName" required="true" %>
<%=secondName + " " + firstName.substring(0, 1) + ". " + fatherName.substring(0, 1) + "."%>