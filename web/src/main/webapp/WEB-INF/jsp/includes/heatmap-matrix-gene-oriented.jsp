<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%--
  ~ Copyright 2008-2012 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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
                        <a href='${genePageURL}' target='_blank'>${geneProfile.geneName}</a>
                    </display:column>

                    <c:forEach var="factorValue" items="${allQueryFactors}">

                        <c:set var="expressionLevel"
                               value="${geneProfile.getExpressionLevel(factorValue)}"/>

                        <c:if test="${expressionLevel != 0}">

                            <c:set var="cellColour"
                                   value="${colourGradient.getGradientColour(expressionLevel, geneProfiles.getMinExpressionLevel(), geneProfiles.getMaxExpressionLevel())}"/>

                            <c:set var="style" value="background-color:${cellColour}"/>

                        </c:if>

                        <display:column
                                title="<div tableHeaderCell data-organism-part='${factorValue.value}' class='rotate_text' title='${factorValue.value}'></div>"
                                headerClass='rotated_cell'
                                style="${expressionLevel !=0 ? style : ''}">

                            <c:if test="${expressionLevel != 0}">

                                <div class="hide_cell"
                                     data-organism-part="${factorValue.value}" data-color="${cellColour}">
                                    <fmt:formatNumber type="number"
                                                      maxFractionDigits="${expressionLevel >= 1 ? 0 : 1}"
                                                      value="${expressionLevel}" groupingUsed="false"/>
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

    //required for the positioning of the stuff that must go inside the top-left corner header cell
    //$("#tooltip-span").parent().addClass("heatmap-matrix-top-left-corner");

</script>
