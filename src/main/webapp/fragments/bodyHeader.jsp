<%--
  Created by IntelliJ IDEA.
  User: Veronika
  Date: 09-12-18
  Time: 00:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="messages.app"/>
<header><a href="index.jsp">
    <fmt:message key="app.home"/></a>&nbsp;|&nbsp;<a href="meals">
    <fmt:message key="app.title"/></a></header>
<link rel="stylesheet" type="text/css" href="css/style.css">