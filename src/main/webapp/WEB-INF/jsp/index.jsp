<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<div class="center">
    <%--<section>--%>
        <form method="post" action="users">
            <spring:message code="app.Login"/>: <select name="userId">
            <option value="100000"><spring:message code="login.user"/></option>
            <option value="100001"><spring:message code="login.admin"/></option>
        </select>
            <button title="submit"><spring:message code="common.select"/></button>
        </form>
        <ul>
            <li><a href="users"><spring:message code="users.title"/></a></li>
            <li><a href="meals"><spring:message code="meals.title"/> </a></li>
        </ul>
    <%--</section>--%>
</div>
</body>
</html>