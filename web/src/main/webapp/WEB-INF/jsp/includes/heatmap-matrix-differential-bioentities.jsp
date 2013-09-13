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

<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="base" value="${pageContext.request.contextPath}"/>
<c:if test="${not empty preferences.rootContext}">
    <c:set var="base" value="${preferences.rootContext}"/>
</c:if>

<table id="diff-heatmap-table" class="table-grid">
    <thead>
    <tr>
        <th class="horizontal-header-cell" style="padding: 5px; text-align:center;">
            <div>Identifier</div>
        </th>
        <th class="horizontal-header-cell" style="padding: 5px; text-align:center;">
            <div>Design Element</div>
        </th>
        <th class="horizontal-header-cell" style="padding: 5px; text-align:center;">
            <div>Organism</div>
        </th>
        <th class="horizontal-header-cell" style="padding: 5px; text-align:center;">
            <div>Contrast</div>
        </th>
        <th class="horizontal-header-cell" style="padding: 5px;">
            <div class='factor-header' data-organism-part=''>Adjusted P-value</div>
        </th>
    </tr>
    </thead>
    <tbody>

    <c:forEach items="${bioentities}"
               var="differentialBioentityExpression">
        <tr>
            <td class="horizontal-header-cell">
                ${differentialBioentityExpression.bioentityId}
            </td>
            <td class="horizontal-header-cell">
                    ${differentialBioentityExpression.designElement}
            </td>
            <td class="horizontal-header-cell contrastNameCell">
                    ${differentialBioentityExpression.species}
            </td>
            <td class="horizontal-header-cell contrastNameCell"
                data-experiment-accession="${differentialBioentityExpression.experimentAccession}"
                data-contrast-id="${differentialBioentityExpression.contrastId}">
                <a href="experiments/${differentialBioentityExpression.experimentPageUrl}">${differentialBioentityExpression.contrastDisplayName}</a>
            </td>

            <c:set var="expression" value="${differentialBioentityExpression.expression}"/>

            <c:set var="expressionLevel"
                   value="${expression.level}"/>

            <c:if test="${expressionLevel != 0}">

                <c:choose>
                    <c:when test="${expressionLevel != 0}">
                        <c:choose>
                            <c:when test="${expression.overExpressed}">
                                <c:set var="cellColour"
                                       value="${colourGradient.getGradientColour(1 - expressionLevel, 1 - bioentities.getMaxUpRegulatedExpressionLevel(), 1 - bioentities.getMinUpRegulatedExpressionLevel(), 'pink', 'red')}"/>
                            </c:when>
                            <c:otherwise>
                                <c:set var="cellColour"
                                       value="${colourGradient.getGradientColour(1 - expressionLevel,  1 - bioentities.getMaxDownRegulatedExpressionLevel(), 1 - bioentities.getMinDownRegulatedExpressionLevel(), 'lightGray', 'blue')}"/>
                            </c:otherwise>
                        </c:choose>

                        <c:set var="style" value="background-color:${cellColour}"/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="style" value=""/>
                    </c:otherwise>
                </c:choose>

            </c:if>

            <td style="${expressionLevel != 0? style : ''}">

                <c:if test="${expressionLevel != 0}">



                    <div class="hide_cell" data-color="${cellColour}">
                        ${numberUtils.htmlFormatDouble(expressionLevel)}
                    </div>

                </c:if>

            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<script language="JavaScript" type="text/javascript" src="${base}/resources/js/heatmapModule.js"></script>

<script type="text/javascript">
    (function ($) { //self invoking wrapper function that prevents $ namespace conflicts
        $(document).ready(function () {

            heatmapModule.initRnaSeqHeatmap(${preferences.cutoff});

            $("#injected-header").remove();
            $("#heatmap-table th").attr("rowspan", "1");
        });
    })(jQuery);
</script>