<%--
  ~ Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~ http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software
  ~ distributed under the License is distributed on an "AS IS" BASIS,
  ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  ~ See the License for the specific language governing permissions and
  ~ limitations under the License.
  ~
  ~
  ~ For further details of the Gene Expression Atlas project, including source code,
  ~ downloads and documentation, please see:
  ~
  ~ http://gxa.github.com/gxa
  --%>
<%--@elvariable id="bioEntityPropertyService" type="uk.ac.ebi.atlas.bioentity.properties.BioEntityPropertyService"--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h" %>


<section class="grid_17 alpha extra-padding">
    <h2 class="strapline">
        Expression Atlas results for <span class="searchterm">${searchDescription}</span>
    </h2>
</section>
<h:ebiGlobalSearch ebiSearchTerm="${not empty globalSearchTerm ? applicationProperties.urlParamEncode(globalSearchTerm) : not empty originalSearchTerm ? originalSearchTerm : entityIdentifier}"/>


<section class="grid_23 extra-padding">
    <div id="atlasHeatmap"></div>
</section>

<div id="help-placeholder" style="display: none"></div>

<script language="JavaScript" type="text/javascript" src="//www.ebi.ac.uk/Tools/biojs/biojs/Biojs.js"></script>
<script language="JavaScript" type="text/javascript" src="/gxa/resources/biojs/AtlasHeatmapReact.js"></script>

<%@ include file="includes/react.jsp" %>
<%@ include file="includes/heatmap-js.jsp" %>
<%@ include file="includes/anatomogram.jsp" %>

<section class="grid_23 extra-padding">
    <div id="facets"> FACETS TREE SELECTION</div>
    <div id="heatmaps"></div>
</section>

<script src="${pageContext.request.contextPath}/resources/js/lib/query-string.js"></script>
<script src="${pageContext.request.contextPath}/resources/jsx/heatmaps.js"></script>
<script src="${pageContext.request.contextPath}/resources/jsx/facets.js"></script>
<script src="${pageContext.request.contextPath}/resources/jsx/heatmapsRouter.js"></script>



<script src="${pageContext.request.contextPath}/resources/jsx/bioJSAtlasHeatmap.js"></script>
<script src="${pageContext.request.contextPath}/resources/jsx/heatmapContainer.js"></script>

<script>

    <%--var facetsData = <%@ include file="includes/facets-data.jsp" %>;--%>

    var facetsData = ${jsonFacets};

    (function (HeatmapsRouter, facetsData) {

        HeatmapsRouter( document.getElementById('facets'),
                document.getElementById('heatmaps'), facetsData);

    })(HeatmapsRouter, facetsData);

</script>
<script>

    window.onload = function () {

        var widgetParameters = "${isGeneSet ? "" : "&propertyType=bioentity_identifier" }" + "${not empty species ? "&species=".concat(species) : ""}";
        var gxaBaseUrl = '${pageContext.request.contextPath}';

        React.renderComponent(BioJSAtlasHeatmap({widgetParameters:widgetParameters, gxaBaseUrl:gxaBaseUrl, geneQuery:'${identifierSearch}'}), document.getElementById('atlasHeatmap'));

    };
</script>


