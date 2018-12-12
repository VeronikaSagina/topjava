<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://mycompany.com" prefix="f" %>
<%@ page import ="java.time.LocalDateTime" %>
<%@ page import="ru.javawebinar.topjava.util.DateTimeUtil" %>
<%@ page import="java.time.ZoneId" %>
<fmt:setBundle basename="messages.app"/>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<%--<h2><a href="index.html">Home</a></h2>--%>
<jsp:include page="fragments/bodyHeader.jsp"/>
<section>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th><fmt:message key="users.Name"/> </th>
            <th><fmt:message key="users.email"/></th>
            <th><fmt:message key="users.roles"/></th>
            <th><fmt:message key="users.active"/></th>
            <th><fmt:message key="users.registered"/></th>
        </tr>
        </thead>
        <c:forEach items="${userList}" var="user">
            <jsp:useBean id="user" scope="page" type="ru.javawebinar.topjava.model.User"/>
            <tr>
                <td>${user.name}</td>
                <td><a href="mailto:${user.email}">${user.email}</a></td>
                <td>${user.authorities}</td>
                <td>${user.enabled}</td>
                <td><%out.println(DateTimeUtil.formatLocalDateTime(
                        LocalDateTime.ofInstant(user.getRegistered(), ZoneId.systemDefault())));%></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>
