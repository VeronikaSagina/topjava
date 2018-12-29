<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<div class="jumbotron">
    <div class="container">
        <p/>
        <form method="post" action="users">
            <div class="form-group">
                <spring:message code="app.Login"/>: <select name="userId">
                <option value="100000"><spring:message code="login.user"/></option>
                <option value="100001"><spring:message code="login.admin"/></option>
            </select>
            </div>
            <button type="submit" title="submit" class="btn btn-primary"><spring:message code="common.select"/></button>
        </form>
        <ul>
            <li><a href="users"><spring:message code="users.title"/></a></li>
            <li><a href="meals"><spring:message code="meals.title"/> </a></li>
        </ul>
    </div>
</div>
</body>
</html>
