<%--@elvariable id="hasBaselineResults" type="boolean"--%>
<%--@elvariable id="jsonFacets" type="java.lang.String"--%>
<%--@elvariable id="identifier" type="java.lang.String"--%>
<%--@elvariable id="geneQuery" type="java.lang.String"--%>
<%--@elvariable id="conditionQuery" type="java.lang.String"--%>
<%--@elvariable id="conditionQuery" type="java.lang.String"--%>
<%--@elvariable id="query" type="java.lang.String"--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="gxaBaselineTab"></div>

<script>
    // function ({atlasHostUrl : hostUrl = "https://www.ebi.ac.uk", target = 'gxaBaselineTab', facetsTreeData, geneQuery, conditionQuery, species})
    <c:if test="${hasBaselineResults}">
        var baselineFacetsData = ${jsonFacets};
        expressionAtlasBaselineExpression({
            target: 'gxaBaselineTab',
            facetsTreeData: baselineFacetsData,
            query: "${query}",
            geneQuery: "${geneQuery}",
            conditionQuery: "${conditionQuery}",
            species: "${species}"
        });
    </c:if>
</script>


