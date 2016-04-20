<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<section>
    <div class="gxaError">
        <c:if test="${not empty errorMessage}">
            <span style="font-weight: bold;">${errorMessage}</span>
        </c:if>
    </div>
</section>