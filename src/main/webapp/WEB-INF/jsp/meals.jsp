<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://mycompany.com" prefix="f" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<script type="text/javascript" src="resources/js/datatablesUtil.js" defer></script>
<script type="text/javascript" src="resources/js/mealDatatables.js" defer></script>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="jumbotron">
    <div class="container">
        <div class="shadow">
            <h2 class="sub-header"><spring:message code="meals.title"/></h2>
            <form method="get" id="filterForm">
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
<%--                <p align="right">
                    <button class="btn btn-info" type="button" onclick="filter()"><spring:message
                            code="meals.filter"/></button>
                    <button class="btn btn-primary" type="reset"><spring:message code="${meals.reset}"/></button>
                </p>--%>
                <div class="btn-group" role="group">
                    <button type="button" class="btn btn-info" onclick="filter()"><spring:message
                            code="meals.filter"/></button>
                    <button type="reset" class="btn btn-primary" onclick="findAll()"><spring:message code="meals.reset"/></button>
                </div>
            </form>
            <hr>
            <hr>
            <div class="table table-responsive">
                <table class="table table-striped display" id="datatable">
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
                <a class="btn btn-info" onclick="add()">
                    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span></a>
                <%-- <a class="label label-info" role="button" href="meals/new"><spring:message code="meals.createMeal"/></a>--%>
            </h3>
            <hr>
        </div>
    </div>
</div>

<div class="modal fade" id="editRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h2 class="modal-title"><spring:message code="${meal.isNew() ? 'meals.add' : 'meals.edit'}"/></h2>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="detailsForm">
                    <input type="text" hidden="hidden" id="id" name="id">
                    <%--
                                        <div class="form-group" id="datetimepicker">
                                            <label for="dateTime" class="control-label col-xs-3"><spring:message
                                                    code="meals.dateTime"/></label>
                                            <div class="input-group bootstrap-timepicker timepicker">
                                                <input id="dateTime" type="text" class="form-control" data-provide="timepicker"
                                                       data-template="modal" data-minute-step="1" data-modal-backdrop="true" name="dateTime"
                                                       placeholder="<spring:message code="meals.dateTime"/> ">
                                                <span class="input-group-addon">
                                            <span class="glyphicon glyphicon-calendar"></span>
                                        </span>
                                            </div>
                                        </div>
                    --%>
                    <div class="form-group">
                        <label for="dateTime" class="control-label col-xs-3"><spring:message
                                code="meals.dateTime"/></label>

                        <div class="col-xs-9">
                            <input type="datetime-local" class="form-control" id="dateTime" name="dateTime">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="calories" class="control-label col-xs-3"><spring:message
                                code="meals.calories"/></label>

                        <div class="col-xs-9">
                            <input type="text" class="form-control" id="calories" name="calories"
                                   placeholder="<spring:message code="meals.calories"/>">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="description" class="control-label col-xs-3"><spring:message
                                code="meals.description"/></label>

                        <div class="col-xs-9">
                            <input type="text" class="form-control" id="description" name="description"
                                   placeholder="<spring:message code="meals.description"/>">
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-xs-offset-3 col-xs-9">
                            <button type="submit" class="btn btn-primary">
                                <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
