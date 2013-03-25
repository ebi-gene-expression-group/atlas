<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

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

<fmt:setBundle basename="configuration" var="configuration"/>

<div class="block">
    <table>
        <tbody>
        <tr>
            <td>
                <display:table name="${geneProfiles}" id="geneProfile"
                               htmlId="heatmap-table" class="table-grid">
                    <display:column
                            title="
                                <div class='heatmap-matrix-top-left-corner'>
                                <span id='tooltip-span' style='display:block;position:absolute;top:0%;margin:5px' data-help-loc='#heatMapTableCellInfo'></span>
                                <button style='margin-top:38%;margin-left:5px;margin-right:5px;' id='display-levels' />
                                    <label for='display-levels'>Display levels</label>
                                </button>
                                </div>"
                            class="header-cell">

                        <fmt:message bundle="${configuration}" key="gene.url.template" var="genePageURL">
                            <fmt:param value="${geneProfile.geneId}"/>
                        </fmt:message>
                        <a class="genename" id="${geneProfile.geneId}" href='${genePageURL}' target='_blank'
                           title="">${geneNamesProvider.getGeneName(geneProfile.geneId)}</a>
                    </display:column>

                    <c:forEach var="queryFactor" items="${allQueryFactors}">

                        <c:set var="expressionLevel"
                               value="${geneProfile.getExpressionLevel(queryFactor)}"/>

                        <c:choose>
                            <c:when test="${expressionLevel != 0}">
                                <c:choose>
                                    <c:when test="${type eq 'BASELINE'}">
                                        <c:set var="cellColour"
                                               value="${colourGradient.getGradientColour(expressionLevel, geneProfiles.getMinExpressionLevel(), geneProfiles.getMaxExpressionLevel())}"/>
                                    </c:when>
                                    <c:otherwise>
                                        <c:choose>
                                            <c:when test="${geneProfile.getExpression(queryFactor).overExpressed}">
                                                <c:set var="cellColour"
                                                       value="${colourGradient.getGradientColour(1 - expressionLevel, 1 - geneProfiles.getMaxUpRegulatedExpressionLevel(), 1 - geneProfiles.getMinUpRegulatedExpressionLevel(), 'pink', 'red')}"/>
                                            </c:when>
                                            <c:otherwise>
                                                <c:set var="cellColour"
                                                       value="${colourGradient.getGradientColour(1 - expressionLevel,  1 - geneProfiles.getMaxDownRegulatedExpressionLevel(), 1 - geneProfiles.getMinDownRegulatedExpressionLevel(), 'lightGray', 'blue')}"/>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:otherwise>
                                </c:choose>
                                <c:set var="style" value="background-color:${cellColour}"/>
                            </c:when>
                            <c:otherwise>
                                <c:set var="style" value=""/>
                            </c:otherwise>
                        </c:choose>

                        <c:set var="columnHeader"
                               value="${type eq 'BASELINE' ? queryFactor.value : queryFactor.displayName}"/>

                        <display:column
                                title="<div tableHeaderCell data-organism-part=\"${columnHeader}\" class=\"rotate_text\" title=\"${columnHeader}\"></div>"
                                headerClass='rotated_cell'
                                style="${style}">

                            <c:if test="${expressionLevel != 0}">
                                <c:choose>
                                    <c:when test="${type eq 'BASELINE'}">
                                        <fmt:formatNumber type="number"
                                                          maxFractionDigits="${expressionLevel >= 1 ? 0 : 1}"
                                                          value="${expressionLevel}" groupingUsed="false"
                                                          var="expressionLevel"/>
                                    </c:when>
                                    <c:when test="${type eq 'DIFFERENTIAL'}">
                                        <c:choose>
                                            <c:when test="${geneProfile.getExpression(queryFactor).notApplicable}">
                                                <c:set var="foldChange" value="N/A"/>
                                            </c:when>
                                            <c:otherwise>
                                                <fmt:formatNumber type="number"
                                                                  maxFractionDigits="2"
                                                                  value="${geneProfile.getExpression(queryFactor).foldChange}"
                                                                  groupingUsed="false"
                                                                  var="foldChange"/>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:when>
                                </c:choose>

                                <div class="hide_cell" ${type eq 'DIFFERENTIAL' ? 'data-fold-change="'.concat(foldChange).concat('"'):''}
                                     data-organism-part="${columnHeader}" data-color="${cellColour}">
                                        ${type eq 'DIFFERENTIAL' ? numberUtils.htmlFormatDouble(expressionLevel) : expressionLevel}
                                </div>

                            </c:if>

                        </display:column>

                    </c:forEach>

                </display:table>
            </td>
            <td style="vertical-align: top">
                <div style="float:left">
                    <!--
                     <button id="download-profiles" class="button-image" value="D"></button>

                    -->
                    <a id="download-profiles-link"
                       title="Top 50 genes displayed on page. Download results to see the rest." href="${downloadUrl}"
                       class="button-image" target="_blank">
                        <img id="download-profiles" alt="Download query results" style="width:20px"
                             src="resources/images/download_blue_small.png">
                    </a>
                </div>
            </td>
        </tr>
        </tbody>
    </table>

    <div id="genenametooltip-content"/>
</div>

<script type="text/javascript">

    var tableHeaderDivs = $("[tableHeaderCell]");
    tableHeaderDivs.tooltip();

    $.each(tableHeaderDivs, function () {
        if ($.browser.msie) {
            $(this).append($(this).attr("data-organism-part"));
        } else {
            var organismPartName = $(this).attr("data-organism-part");
            organismPartName = restrictSize(organismPartName, 17);
            $(this).append(organismPartName);
        }
    });

    function restrictSize(s, maxSize) {
        var result = s;
        if (result.length > maxSize) {
            result = result.substring(0, maxSize);
            if (result.lastIndexOf(" ") > maxSize - 5) {
                result = result.substring(0, result.lastIndexOf(" "));
            }
            result = result + "...";
        }
        return result;
    }

    $(".genename").tooltip({content:function (callback) {
        var identifier = $(this).attr("id");
        $("#genenametooltip-content").load("rest/genenametooltip?identifier=" + identifier,
                function (response, status, xhr) {
                    var tooltipContent;
                    if (status === "error") {
                        tooltipContent = "Sorry but there was an error: " + xhr.status + " " + xhr.statusText;
                        callback(tooltipContent);
                        return;
                    }
                    tooltipContent = $(this).html();
                    if (!tooltipContent) {
                        tooltipContent = "Missing properties for id = " + identifier + " in Solr.";
                    }
                    callback(tooltipContent);
                }
        );
    }
    });

    //required for the positioning of the stuff that must go inside the top-left corner header cell
    //$("#tooltip-span").parent().addClass("heatmap-matrix-top-left-corner");

</script>
