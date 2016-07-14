<%--@elvariable id="exceptionMessage" type="java.lang.String"--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<section class="gxaSection">
    <div class="gxaError">
        <c:if test="${not empty exceptionMessage}">
            <p>${exceptionMessage}</p>
        </c:if>
    </div>
</section>
