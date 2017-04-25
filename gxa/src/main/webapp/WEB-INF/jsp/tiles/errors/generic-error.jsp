<%--@elvariable id="exceptionMessage" type="java.lang.String"--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isErrorPage="true" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<div class="row margin-top-xxlarge">
    <div class="small-6 small-centered columns">
        <h4>Error: <span style="color:indianred;font-weight: bold;"><%=response.getStatus()%></span></h4>
        <c:choose>
            <c:when test="${not empty exceptionMessage}">
                <h5>${exceptionMessage}</h5>
            </c:when>
            <c:otherwise>
                <h5>We are sorry - an unexpected error occurred. If it persists, please contact <a href="mailto:arrayexpress@ebi.ac.uk">arrayexpress@ebi.ac.uk</a></h5>
            </c:otherwise>
        </c:choose>
    </div>
</div>

