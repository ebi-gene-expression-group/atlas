<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<link rel="stylesheet" href="<c:url value="/resources/css/experiment.css"/>">
<link rel="stylesheet" href="<c:url value="/resources/css/alt-customized-bootstrap-3.3.5.css"/>">

<%@ include file="experiment-page/experiment-description.jsp" %>

<div class="row expanded margin-top-large">
    <div class="small-12 columns">
        <div id="experiment-page" ></div>
    </div>
</div>

<script src="<c:url value="/resources/js-bundles/vendorCommons.bundle.js"/>"></script>
<script src="<c:url value="/resources/js-bundles/experimentPage.bundle.js"/>"></script>

<script>
    experimentPage.render({
        atlasUrl: '${pageContext.request.contextPath}/',
        content: ${content},
        target: 'experiment-page'
    });
</script>
