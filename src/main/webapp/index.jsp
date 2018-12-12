<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="messages.app"/>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<div class="center">
    <%--<section>--%>
        <form method="post" action="users">
            <fmt:message key="app.Login"/>: <select name="userId">
            <option value="100000"><fmt:message key="login.user"/></option>
            <option value="100001"><fmt:message key="login.admin"/></option>
        </select>
            <button title="submit"><fmt:message key="common.select"/></button>
        </form>
        <ul>
            <li><a href="users"><fmt:message key="users.title"/></a></li>
            <li><a href="meals"><fmt:message key="meals.title"/> </a></li>
        </ul>
    <%--</section>--%>
</div>
</body>
</html>
