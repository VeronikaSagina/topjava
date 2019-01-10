<%@page session="false" %>
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

            <%--<form method="get" id="filterForm">--%>
            <div class="view-box">
                <div class="row">
                    <div class="col-sm-7">
                        <div class="panel panel-default">
                            <div class="panel-body">
                                <form class="form-horizontal" id="filter">

                                    <div class="form-group">
                                        <label class="control-label col-sm-3" for="startDate"><spring:message
                                                code="meals.startDate"/>:</label>
                                        <div class="col-sm-3">
                                            <input class="form-control" name="startDate" id="startDate">
                                        </div>

                                        <label class="control-label col-sm-4" for="startTime"><spring:message
                                                code="meals.startTime"/>:</label>
                                        <div class="col-sm-2">
                                            <input class="form-control" name="startTime" id="startTime">
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class='control-label col-sm-3' for="endDate"><spring:message
                                                code="meals.endDate"/>:</label>
                                        <div class="col-sm-3">
                                            <input name="endDate" id="endDate" class="form-control">
                                        </div>

                                        <label class="control-label col-sm-4" for="endTime"><spring:message
                                                code="meals.endTime"/>:</label>
                                        <div class="col-sm-2">
                                            <input name="endTime" id="endTime" class="form-control">
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <div class="panel-footer text-right">
                                <button type="button" class="btn btn-primary" onclick="clearFilter()"><spring:message
                                        code="meals.reset"/></button>
                                <button type="button" class="btn btn-info" onclick="filter()"><spring:message
                                        code="meals.filter"/></button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
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
                </table>
            </div>
            <h3>
                <a class="btn btn-info" onclick="add()">
                    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span></a>
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
                <h2 class="modal-title" hidden id="headerEdit"><spring:message code="meals.edit"/></h2>
                <h2 class="modal-title" hidden id="headerAdd"><spring:message code="meals.add"/></h2>
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
                            <input  class="form-control" id="dateTime"
                                   name="dateTimeUI" placeholder="<spring:message code="meals.dateTime"/> ">
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
                            <input type="text" class="form-control" id="description"
                                   name="description"
                                   placeholder="<spring:message code="meals.description"/>">
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-xs-offset-3 col-xs-9">
                            <button type="button" class="btn btn-primary" onclick="save()">
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
<script type="text/javascript">
    var i18n = [];
    <c:forEach var='key' items='<%=new String[]{"common.deleted","common.saved","common.enabled","common.disabled","common.errorStatus"}%>'>
    i18n['${key}'] = '<spring:message code="${key}"/>';
    </c:forEach>
</script>
</html>
