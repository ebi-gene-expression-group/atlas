<%--@elvariable id="propertyNames" type="java.util.Map"--%>
<%--@elvariable id="relevantGoPoLinks" type="java.util.List"--%>
<%--@elvariable id="allGoPoLinks" type="java.util.List"--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/bioentities/bioentity-information.css"/>

<div class="grid_18 omega" id="bioentityInformationTab">
</div>

<script>
    expressionAtlasBioentityInformation.render({
        target: 'bioentityInformationTab',
        payload: ${bioentityProperties}
    });
</script>
