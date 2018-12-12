<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib uri="http://mycompany.com" prefix="f" %>
<%@ page import ="java.time.LocalDateTime" %>
<%@ page import="ru.javawebinar.topjava.util.DateTimeUtil" %>
<%@ page import="java.time.ZoneId" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<%--<h2><a href="index.html">Home</a></h2>--%>
<jsp:include page="fragments/bodyHeader.jsp"/>
<section>
    <h3><spring:message code="users.title"/> </h3>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th><spring:message code="users.Name"/> </th>
            <th><spring:message code="users.email"/></th>
            <th><spring:message code="users.roles"/></th>
            <th><spring:message code="users.active"/></th>
            <th><spring:message code="users.registered"/></th>
        </tr>
        </thead>
        <c:forEach items="${users}" var="user">
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
