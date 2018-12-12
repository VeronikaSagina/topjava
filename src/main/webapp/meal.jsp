<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
<fmt:setBundle basename="messages.app"/>

<html>
    <jsp:include page="fragments/headTag.jsp"/>
<body>
<section>
    <h2><a href="index.jsp"><fmt:message key="app.home"/> </a></h2>
    <h2>${param.action == 'create' ? 'Create meal' : 'Edit meal'}</h2>
    <hr>
    <%-- <h2>${meal.id == null ? "Создание записи" : "Обновление записи"}</h2>--%>
    <%--  <form method="POST" action="meals?action=<c:out value="${meal.id != null ? 'update' : 'create'}"/>">--%>
    <form method="post" action="meals?action=${param.action}">
        <input hidden type="text" name="id" value="<c:out value="${meal.id}"/>">
        <table>
            <tr>
                <td><fmt:message key="meals.dateTime"/></td>
                <td><input type="datetime-local" name="date" value="<c:out value="${meal.dateTime}"/>"></td>
            </tr>
            <tr>
                <td><fmt:message key="meals.description"/></td>
                <td><input type="text" name="description" value="<c:out value="${meal.description}"/>"></td>
            </tr>
            <tr>
                <td><fmt:message key="meals.calories"/></td>
                <td><input type="text" name="calories" value="<c:out value="${meal.calories}"/>"></td>
            </tr>
        </table>
        <button type="submit"><fmt:message key="meals.save"/></button>
        <button onclick="window.history.back()"><fmt:message key="meals.cancel"/></button>
    </form>
</section>
<%--
<table border="1px">
    <h2><a href="index.html">Home</a></h2>
    <c:forEach items="${MealList}" var="meal">
    <tr style="color: ${meal.exceed ? 'lime' : 'red'}">
        <td><c:out value="${meal.dateTime.format(formatter)}"/></td>
        <td><c:out value="${meal.description}"/></td>
        <td><c:out value="${meal.calories}"/></td>
        <td><c:out value="${meal.exceed}"/></td>
        <td><a href="meals?action=edit&id=<c:out value="${meal.id}"/>">Update</a></td>
        <td><a href="meals?action=delete&id=<c:out value="${meal.id}"/>">Delete</a></td>
        </c:forEach>
</table>
--%>
</body>
</html>
