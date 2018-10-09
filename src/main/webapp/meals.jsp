<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://mycompany.com" prefix="f" %>

<html>
<head>
    <title>MealsList</title>
</head>
<body>
<h2><a href="index.html">Home</a></h2>
<a href="meals?action=create">Create Meal</a>
<br>
<br>
<table border="1px">
    <c:forEach items="${MealList}" var="meal">
    <tr style="color: ${!meal.exceed ? 'lime' : 'red'}">
        <td><c:out value="${f:formatLocalDateTime(meal.dateTime)}"/></td>
        <td><c:out value="${meal.description}"/></td>
        <td><c:out value="${meal.calories}"/></td>
        <td><a href="meals?action=edit&id=<c:out value="${meal.id}"/>">Update</a></td>
        <td><a href="meals?action=delete&id=<c:out value="${meal.id}"/>">Delete</a></td>
        </c:forEach>
</table>
</body>
</html>
