<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>

<html>
<head>
    <title>Meal</title>
</head>
<body>
<h2>${meal.id == null ? "Создание записи" : "Обновление записи"}</h2>
<form method="POST" action="meals?action=<c:out value="${meal.id != null ? 'update' : 'create'}"/>">
    <input hidden type="text" name="id" value="<c:out value="${meal.id}"/>">
    <table>
        <tr>
            <td>дата</td>
            <td><input type="text" name="date" value="<c:out value="${meal.dateTime.format(formatter)}"/>"></td>
        </tr>
        <tr>
            <td>описание</td>
            <td><input type="text" name="description" value="<c:out value="${meal.description}"/>"></td>
        </tr>
        <tr>
            <td>калории</td>
            <td><input type="text" name="calories" value="<c:out value="${meal.calories}"/>"></td>
        </tr>
        <tr>
            <td align="right" colspan="2"><input type="submit" value="Отправить"></td>
        </tr>
    </table>

</form>
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
