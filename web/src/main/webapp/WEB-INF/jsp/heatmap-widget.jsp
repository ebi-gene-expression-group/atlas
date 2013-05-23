<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<div id="content" class="block">
    <table width="100%">
        <tbody>
        <tr>
            <td style="width:140px;padding-right:20px">
                <div class="experiment-accession">
                    <a id="goto-ae" class="thick-link"
                       href="${applicationProperties.getArrayExpressURL(experimentAccession)}"
                       title="View experiment in ArrayExpress"
                       target="_blank">${experimentAccession}</a>
                </div>
            </td>
            <td width="100%">
                <div id="experimentDescription">
                    <a id="goto-experiment" class="thick-link" title="Experiment Page"
                       href="experiments/${experimentAccession}">${experimentDescription}</a>
                    <c:if test="${hasExtraInfo}">
                        <a id="extra-info" href="external-resources/${experimentAccession}/extra-info.png">
                            <img alt="more information" src="/gxa/resources/images/balloon-ellipsis-icon-left.png">
                        </a>
                    </c:if>
                </div>
                <div>Organism(s): ${allSpecies}</div>
            </td>
        </tbody>
    </table>
    <div id="heatmap" class="row stickem-container">

        <div id="anatomogram" class="aside stickem double-click-noselection">
            <table>
                <tr>
                    <td style="padding-top: 15px; vertical-align:top">
                <span id="sex-toggle">
                    <img id="sex-toggle-image" title="Switch anatomogram" class="button-image"
                         style="width:20px;height:38px;padding:2px"
                         src="resources/images/male_selected.png"/>
                </span>
                        <!--
                        <span data-help-loc="#anatomogram"/>
                        -->
                    </td>
                    <td>
                        <div id="anatomogramBody" style="display:inline-block;width: 230px; height:360px">
                        </div>
                    </td>
                </tr>
            </table>
        </div>

        <div id="heatmap-div" class="heatmap-position" style="display:none">

            <table>
                <tr>
                    <td>
                        <span id="geneCount">Showing ${geneProfiles.size()}
                            of ${geneProfiles.getTotalResultCount()} genes found:
                        </span>
                    </td>
                    <td>
                        <c:import url="includes/gradient-legend.jsp"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <c:import url="includes/heatmap-matrix-gene-oriented.jsp"/>
                    </td>
                </tr>
            </table>
        </div>
    </div>

</div>
<script language="JavaScript" type="text/javascript"
        src="${pageContext.request.contextPath}/resources/js/jquery.svg.package-1.4.5/jquery.svg.js"></script>
<script language="JavaScript" type="text/javascript"
        src="${pageContext.request.contextPath}/resources/js/anatomogramModule.js"></script>
<script>

    (function ($) { //self invoking wrapper function that prevents $ namespace conflicts

        $(document).ready(function () {

            var anyAnatomogramFile = "${maleAnatomogramFile}" + "${femaleAnatomogramFile}"


            //ToDo: this should be replaced with a JSON array directly sent from backend layer
            var allQueryFactorValues = [${allQueryFactors.size()}];
            <c:forEach varStatus="i" var="queryFactor" items="${allQueryFactors}">
                allQueryFactorValues[${i.index}] = "${type == 'BASELINE' ? queryFactor.value : queryFactor.displayName}";
            </c:forEach>

            if (anyAnatomogramFile && 0 < anyAnatomogramFile.length) {
                anatomogramModule.init(allQueryFactorValues, '${maleAnatomogramFile}', '${femaleAnatomogramFile}');
            }


            //configurations required for any browser...

            if (!anyAnatomogramFile || 0 === anyAnatomogramFile.length) {
                $("#anatomogram").remove();//remove the anatomogram
                $("#heatmap-div").removeClass();
            }


            $('#stickem-container').stickem();
        });

    })(jQuery);

</script>