<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${role eq 'ADMIN'}">
    <%@ include file="../../jspf/guestHeader.jspf" %>
</c:if>
<c:if test='${role eq "CANDIDATE"}'>
    <%@ include file="/jspf/candidateHeader.jspf" %>
</c:if>
<c:if test="${empty role}">
    <%@ include file="/jspf/guestHeader.jspf" %>
    <h1>eeeee</h1>
</c:if>
