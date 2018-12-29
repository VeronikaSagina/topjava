<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://mycompany.com" prefix="f" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="jumbotron">
    <div class="container">
        <div class="shadow">
            <h2 class="sub-header"><spring:message code="meals.title"/></h2>
            <form method="get" action="meals/filter">
                <div class="row">
                    <div class="col-md-5">
                        <spring:message code="meals.date"/>
                        <div class='input-group date' id='datetimepicker6'>
                            <input type="date" class="form-control" name="startDate" value="${param.startDate}">
                            <input type="time" class="form-control" name="startTime" value="${param.startDate}">
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-calendar"></span>  </span>
                        </div>
                    </div>
                    <div class="col-md-5">
                        <spring:message code="meals.time"/>
                        <div class="form-group">
                            <div class='input-group date' id='datetimepicker7'>
                                 <input type="date" name="endDate" class="form-control" value="${param.endDate}">
                                 <input type="time" name="endTime" class="form-control" value="${param.endTime}">
                                <span class="input-group-addon">
                                    <span class="glyphicon glyphicon-calendar"></span>
                                </span>
                            </div>
                        </div>
                    </div>
                </div>
            <p align="right">
                <button class="btn btn-info" type="submit"><spring:message code="meals.filter"/></button>
            </p>
            </form>
            <hr>
            <hr>
            <div class="table table-responsive">
                <table class="table table-striped">
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
            </div>
            <h3>
                <a class="label label-info" role="button" href="meals/new"><spring:message code="meals.createMeal"/></a>
            </h3>
            <hr>
        </div>
    </div>
</div>
</body>
</html>
