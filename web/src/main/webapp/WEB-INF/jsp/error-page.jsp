<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isErrorPage="true" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set value="${requestScope['javax.servlet.error.status_code']}" var="statusCode"/>


<div id="error-content" class="gxaBlock">
    <div class="gxaError">
        <c:choose>
            <c:when test="${statusCode == '404'}">
                Resource not found.
            </c:when>
            <c:otherwise>
                We are sorry - an unexpected error occurred. If it persists, please contact <a href="mailto:arrayexpress@ebi.ac.uk">arrayexpress@ebi.ac.uk</a>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<div id="content" class="gxaBlock">
<a href="/gxa">Go to Expression Atlas home page</a>
</div>