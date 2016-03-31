<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div>
    <form:radiobuttons path="regulation" element="div" itemLabel="label" items="${regulationValues}"/>
</div>