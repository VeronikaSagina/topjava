<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://mycompany.com" prefix="f" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="messages.app"/>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<section>
    <h2><fmt:message key="meals.title"/></h2>
    <form method="post" action="meals?action=filter">
        <div class="row">
            <div class="col-xs-6">
                <fmt:message key="meals.date"/>
                <input type="date" name="startDate" value="${param.startDate}">
                <input type="date" name="endDate" value="${param.endDate}">
            </div>
            <div class="col-xs-6">
                <fmt:message key="meals.time"/>
                <input type="time" name="startTime" value="${param.startDate}">
                <input type="time" name="endTime" value="${param.endTime}">
            </div>
        </div>
        <button type="submit"><fmt:message key="meals.filter"/></button>
    </form>
    <hr>
    <a href="meals?action=create"><fmt:message key="meals.createMeal"/></a>
    <hr>
    <%-- <br>
     <br>--%>
    <table border="1px" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th><fmt:message key="meals.dateTime"/></th>
            <th><fmt:message key="meals.description"/></th>
            <th><fmt:message key="meals.calories"/></th>
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
                <td><a href="meals?action=update&id=<c:out value="${meal.id}"/>"><fmt:message key="meals.Update"/></a>
                </td>
                <td><a href="meals?action=delete&id=<c:out value="${meal.id}"/>"><fmt:message key="meals.Delete"/></a>
                </td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>
