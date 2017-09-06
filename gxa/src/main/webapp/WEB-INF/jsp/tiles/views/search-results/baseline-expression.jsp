<%--@elvariable id="hasBaselineResults" type="boolean"--%>
<%--@elvariable id="jsonFacets" type="java.lang.String"--%>
<%--@elvariable id="identifier" type="java.lang.String"--%>
<%--@elvariable id="geneQuery" type="java.lang.String"--%>
<%--@elvariable id="conditionQuery" type="java.lang.String"--%>
<%--@elvariable id="conditionQuery" type="java.lang.String"--%>
<%--@elvariable id="query" type="java.lang.String"--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/alt-customized-bootstrap-3.3.5.css"/>"/>

<div id="gxaBaselineTab"></div>

<script>
    <c:if test="${hasBaselineResults}">
        var baselineFacetsData = ${jsonFacets};
        // Running within $(document).ready() ensures that the heatmap stretches to the tab container width
        $(function() {
            expressionAtlasBaselineExpression.render({
                atlasUrl: "${pageContext.request.contextPath}/",
                target: 'gxaBaselineTab',
                facetsTreeData: baselineFacetsData,
                geneQuery: "${geneQuery}",
                conditionQuery: "${conditionQuery}",
                species: "${species}"
            });
        });
    </c:if>
</script>


