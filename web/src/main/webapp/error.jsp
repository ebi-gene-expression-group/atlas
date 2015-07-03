<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <title>Experiment</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/widget/latest/css/atlas.css"/>
</head>

<body class="atlas-body">
<%-- Log error on server side --%>
<div class="gxaError">
    An error occurred:
</div>
<div class="errorMessage">
    ${requestScope['javax.servlet.error.exception']}
</div>
</body>
</html>