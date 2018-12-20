<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<section>
    <h2><spring:message code="${meal.isNew() ? 'meals.add' : 'meals.edit'}"/></h2>
    <hr>
    <form method="post" action="meals">
        <input hidden type="text" name="id" value="<c:out value="${meal.id}"/>">
        <table>
            <tr>
                <td><spring:message code="meals.dateTime"/></td>
                <td><input type="datetime-local" name="date" value="<c:out value="${meal.dateTime}"/>"></td>
            </tr>
            <tr>
                <td><spring:message code="meals.description"/></td>
                <td><input type="text" name="description" value="<c:out value="${meal.description}"/>"></td>
            </tr>
            <tr>
                <td><spring:message code="meals.calories"/></td>
                <td><input type="text" name="calories" value="<c:out value="${meal.calories}"/>"></td>
            </tr>
        </table>
        <button type="submit"><spring:message code="meals.save"/></button>
        <button onclick="<%=request.getContextPath()%>/meals"><spring:message code="meals.cancel"/></button>
    </form>
</section>
</body>
</html>
