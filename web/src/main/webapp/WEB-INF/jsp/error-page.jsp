<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isErrorPage="true" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set value="${requestScope['javax.servlet.error.status_code']}" var="statusCode"/>

<section class="gxaSection">
    <div class="gxaError">
        <c:choose>
            <c:when test="${statusCode == '404'}">
                <p>Resource not found.</p>
            </c:when>
            <c:otherwise>
                <p>We are sorry - an unexpected error occurred. If it persists, please contact <a href="mailto:arrayexpress@ebi.ac.uk">arrayexpress@ebi.ac.uk</a></p>
            </c:otherwise>
        </c:choose>
    </div>
</section>

<section class="gxaSection">
    <a style="font-weight: bold" href="/gxa">Go to Expression Atlas home page</a>
</section>
