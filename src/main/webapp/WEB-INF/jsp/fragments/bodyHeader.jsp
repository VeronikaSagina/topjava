<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <a href="meals" class="navbar-brand"><spring:message code="app.title"/></a>
        <div class="collapse navbar-collapse">
            <form class="navbar-form navbar-right">
                <a class="btn btn-info" href="users"><spring:message code="users.title"/></a>
                <a class="btn btn-primary" href="">
                    <span class="glyphicon glyphicon-log-in" aria-hidden="true"></span>
                </a>
            </form>
        </div>
    </div>
</div>

<%--<link rel="stylesheet" type="text/css" href="resources/css/style.css">--%>

