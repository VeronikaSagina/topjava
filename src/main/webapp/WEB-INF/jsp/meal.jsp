<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<div class="container">
<div class="jumbotron" align="center">

    <h2><spring:message code="${meal.isNew() ? 'meals.add' : 'meals.edit'}"/></h2>
    <hr>
    <form method="post" action="meals">
        <input hidden type="text" name="id" value="<c:out value="${meal.id}"/>">
        <div class="input-group mb-3">
            <div class="input-group-prepend">
                <span class="input-group-text"> <spring:message code="meals.dateTime"/></span></div>
            <input type="datetime-local" class="form-control" aria-label="Default"
                   aria-describedby="inputGroup-sizing-default" name="date"
                   value="<c:out value="${meal.dateTime}"/>">

            <div class="input-group-prepend">
                <span class="input-group-text"><spring:message code="meals.description"/></span></div>
            <input type="text" class="form-control" aria-label="Default"
                   aria-describedby="inputGroup-sizing-default" name="description"
                   value="<c:out value="${meal.description}"/>">

            <div class="input-group-prepend">
                <span class="input-group-text"><spring:message code="meals.calories"/></span></div>
            <input type="text" class="form-control" aria-label="Default"
                   aria-describedby="inputGroup-sizing-default" name="calories"
                   value="<c:out value="${meal.calories}"/>">

        </div>
        <hr>
        <button class="btn btn-lg btn-success" type="submit"><spring:message code="meals.save"/></button>
        <a class="btn btn-lg btn-default" href="<%=request.getContextPath()%>/meals"><spring:message
                code="meals.cancel"/></a>
    </form>
</div>
<div>
    <img class="img-responsive" src="resources/image/veg0112__resized_1920x1080.jpg">
</div>
<%--<div class="view"
     style="background-image: url('https://www.free-wallpapers.su/data/media/2279/big/veg0112.jpg') ;
      background-repeat: no-repeat; background-size: cover; background-position: center center;">
</div>--%>
</body>
</html>
