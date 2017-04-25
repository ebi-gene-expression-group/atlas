<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isErrorPage="true" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set value="${requestScope['javax.servlet.error.status_code']}" var="statusCode"/>

<div class="row margin-top-xxlarge">
    <div class="small-6 small-centered columns">
        <h4>Error: <span style="color:indianred;font-weight: bold;">${statusCode}</span></h4>
        <h5>We are sorry - an unexpected error occurred. If it persists, please contact <a href="mailto:arrayexpress@ebi.ac.uk">arrayexpress@ebi.ac.uk</a></h5>
    </div>
</div>

