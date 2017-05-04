<%--@elvariable id="hasBaselineResults" type="boolean"--%>
<%--@elvariable id="jsonFacets" type="java.lang.String"--%>
<%--@elvariable id="identifier" type="java.lang.String"--%>
<%--@elvariable id="geneQuery" type="java.lang.String"--%>
<%--@elvariable id="conditionQuery" type="java.lang.String"--%>
<%--@elvariable id="conditionQuery" type="java.lang.String"--%>
<%--@elvariable id="query" type="java.lang.String"--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="protocol" value="${pageContext.request.scheme}://"/>
<c:set var="atlasHost" value="${pageContext.request.serverName}"/>
<c:set var="serverPort" value="${pageContext.request.serverPort == 80 ? '' : ':'.concat(pageContext.request.serverPort)}"/>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/alt-customized-bootstrap-3.3.5.css"/>

<div id="gxaBaselineTab"></div>

<script>
    // function ({atlasHostUrl : hostUrl = "https://www.ebi.ac.uk", target = 'gxaBaselineTab', facetsTreeData, geneQuery, conditionQuery, species})
    <c:if test="${hasBaselineResults}">
        var baselineFacetsData = ${jsonFacets};
        // Running within $(document).ready() ensures that the heatmap stretches to the tab container width
        $(function() {
            expressionAtlasBaselineExpression.render({
                atlasUrl: '${protocol}${atlasHost}${serverPort}/${contextPath}/',
                target: 'gxaBaselineTab',
                facetsTreeData: baselineFacetsData,
                geneQuery: '${geneQuery}',
                conditionQuery: '${conditionQuery}',
                species: '${species}'
            });
        });
    </c:if>
</script>


