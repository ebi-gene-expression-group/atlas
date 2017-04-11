<%--@elvariable id="propertyNames" type="java.util.Map"--%>
<%--@elvariable id="relevantGoPoLinks" type="java.util.List"--%>
<%--@elvariable id="allGoPoLinks" type="java.util.List"--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="row">
    <div class="small-12 columns">
        <div id="bioentityInformationTab"></div>
    </div>
</div>

<script>
    expressionAtlasBioentityInformation.render({
        target: 'bioentityInformationTab',
        payload: ${bioentityProperties}
    });
</script>
