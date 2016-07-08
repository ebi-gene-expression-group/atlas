<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<section class="gxaSection">
    <div class="gxaError">
        <c:if test="${not empty errorMessage}">
            <h5>${errorMessage}</h5>
        </c:if>
    </div>
</section>
