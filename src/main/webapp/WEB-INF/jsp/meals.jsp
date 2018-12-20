<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://mycompany.com" prefix="f" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<section>
    <h2><spring:message code="meals.title"/></h2>
    <form method="get" action="meals">
        <div class="row">
            <div class="col-xs-6">
                <spring:message code="meals.date"/>
                <input type="date" name="startDate" value="${param.startDate}">
                <input type="date" name="endDate" value="${param.endDate}">
            </div>
            <div class="col-xs-6">
                <spring:message code="meals.time"/>
                <input type="time" name="startTime" value="${param.startDate}">
                <input type="time" name="endTime" value="${param.endTime}">
            </div>
        </div>
        <button type="submit"><spring:message code="meals.filter"/></button>
    </form>
    <hr>
    <a href="meals/new"><spring:message code="meals.createMeal"/></a>
    <hr>
    <table border="1px" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th><spring:message code="meals.dateTime"/></th>
            <th><spring:message code="meals.description"/></th>
            <th><spring:message code="meals.calories"/></th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.MealWithExceed"/>
            <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
                <td><c:out value="${f:formatLocalDateTime(meal.dateTime)}"/></td>
                <td><c:out value="${meal.description}"/></td>
                <td><c:out value="${meal.calories}"/></td>
                <td><a href="meals/update?id=${meal.id}"><spring:message code="meals.Update"/></a>
                </td>
                <td><a href="meals/delete?id=${meal.id}"><spring:message code="meals.Delete"/></a>
                </td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>
