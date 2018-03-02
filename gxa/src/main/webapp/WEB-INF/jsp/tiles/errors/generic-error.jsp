<%--@elvariable id="exceptionMessage" type="java.lang.String"--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isErrorPage="true" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="statusCode" value="<%=response.getStatus()%>"/>

<div class="row margin-top-xxlarge margin-bottom-xxlarge">
    <div class="small-6 small-centered columns">
        <h4>Error: <span style="color:indianred;font-weight: bold;">${statusCode}</span></h4>
        <c:choose>
            <c:when test="${not empty exceptionMessage}">
                <h5>${exceptionMessage}</h5>
            </c:when>
            <c:when test="${statusCode == 404}">
                <h5>Page not found. If you think a resource is missing, please contact <a href="mailto:arrayexpress-atlas@ebi.ac.uk">arrayexpress-atlas@ebi.ac.uk</a>.</h5>
            </c:when>
            <c:otherwise>
                <h5>We are sorry - an unexpected error occurred. If it persists, please contact <a href="mailto:arrayexpress-atlas@ebi.ac.uk">arrayexpress-atlas@ebi.ac.uk</a>.</h5>
            </c:otherwise>
        </c:choose>
        <h5 class="margin-top-xlarge">Go back to <a href="${pageContext.request.contextPath}/home">Expression Atlas home page</a>.</h5>
    </div>
</div>

