<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isErrorPage="true" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set value="${requestScope['javax.servlet.error.status_code']}" var="statusCode"/>

<section class="gxaSection">
    <div class="gxaError">
        <c:choose>
            <c:when test="${statusCode == '404'}">
                <h4><strong>404</strong> Page not found</h4>
            </c:when>
            <c:otherwise>
                <h4>We are sorry - an unexpected error occurred. If it persists, please contact <a href="mailto:arrayexpress@ebi.ac.uk">arrayexpress@ebi.ac.uk</a></h4>
            </c:otherwise>
        </c:choose>
        <c:if test="${not empty exceptionMessage}">
            <h5>${exceptionMessage}</h5>
        </c:if>
    </div>
</section>

<section class="gxaSection">
    <p><a style="font-weight: bold" href="/gxa">Go to Expression Atlas home page</a></p>
</section>
