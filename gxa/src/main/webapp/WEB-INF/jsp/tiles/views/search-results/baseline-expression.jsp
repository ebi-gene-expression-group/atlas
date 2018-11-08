<%--@elvariable id="hasBaselineResults" type="boolean"--%>
<%--@elvariable id="jsonFacets" type="java.lang.String"--%>
<%--@elvariable id="identifier" type="java.lang.String"--%>
<%--@elvariable id="geneQuery" type="java.lang.String"--%>
<%--@elvariable id="conditionQuery" type="java.lang.String"--%>
<%--@elvariable id="conditionQuery" type="java.lang.String"--%>
<%--@elvariable id="query" type="java.lang.String"--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/alt-customized-bootstrap-3.3.5.css"/>

<div id="gxaBaselineTab"></div>

<script defer src="${pageContext.request.contextPath}/resources/js-bundles/expressionAtlasBaselineExpression.bundle.js"></script>
<script>
    document.addEventListener("DOMContentLoaded", function(event) {
        <c:if test="${hasBaselineResults}">
        var baselineFacetsData = ${jsonFacets};
        // Running within $(document).ready() ensures that the heatmap stretches to the tab container width
        $(function() {
            expressionAtlasBaselineExpression.render(
              {
                atlasUrl: "${pageContext.request.contextPath}/",
                facetsTreeData: baselineFacetsData,
                geneQuery: "${geneQuery}",
                conditionQuery: "${conditionQuery}",
                species: "${species}"
              }, 'gxaBaselineTab');
        });
        </c:if>
    })
</script>


