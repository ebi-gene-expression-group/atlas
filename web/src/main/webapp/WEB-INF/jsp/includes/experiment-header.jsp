<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<div id="helpContentTooltip" style='display:none'></div>

<table width="100%">
    <tbody>
    <tr>
        <td style="width:140px;">
            <div class="experiment-accession">
                <a id="goto-ae"
                   href="${applicationProperties.getArrayExpressURL(experimentAccession)}"
                   title="View experiment in ArrayExpress"
                   target="_blank">${experimentAccession}</a>
            </div>
        </td>
        <td width="100%">
            <div id="experimentDescription" style="font-weight: bold;">
                <a id="goto-experiment" title="Experiment Page"
                   href="experiments/${experimentAccession}">${experimentDescription}</a>
                <c:if test="${hasExtraInfo}">
                    <a id="extra-info" href="external-resources/${experimentAccession}/extra-info.png">
                        <img alt="more information" src="/gxa/resources/images/balloon-ellipsis-icon-left.png">
                    </a>
                </c:if>
            </div>
            <div>Organism(s): ${allSpecies}</div>
            <c:if test="${allArrayDesigns!=null}">
                <div>Array Design(s):
                    <c:forEach items="${allArrayDesigns}" var="arrayDesign">
                        <a class="array-design" id="${arrayDesign}" title="View array design in ArrayExpress"
                           href="http://www.ebi.ac.uk/arrayexpress/arrays/${arrayDesign}"
                           target='_blank'>${arrayDesign}</a>
                    </c:forEach>
                </div>
            </c:if>
        </td>
        <td width="130px">
            <table cellpadding="2" cellspacing="0" border="0" style="float:right">
                <tr>
                    <td>
                        <a id="display-experiment" class="button-image"
                           title="Experiment Page" href="experiments/${experimentAccession}">
                            <img src="resources/images/experiment_page_small.png"/></a>
                    </td>
                    <td>
                        <a id="display-analysis-methods" class="button-image" title="Analysis Methods"
                           href="experiments/${experimentAccession}/analysis-methods">
                            <img style="width:23px;height:23px"
                                 src="resources/images/analysis_icon.png"/></a>
                    </td>
                    <td>
                        <a id="display-experiment-design" class="button-image"
                           title="Experiment Design" href="experiments/${experimentAccession}/experiment-design">
                            <img src="resources/images/experiment_design_icon.png"/></a>
                    </td>
                    <c:if test="${type eq 'DIFFERENTIAL'}">
                        <td>
                            <a id="download-raw" class="button-image" title="Download all raw counts for the experiment"
                               href="#">
                                <img src="resources/images/download_blue_small_raw.png"/></a>
                        </td>
                    </c:if>
                    <c:if test="${type eq 'MICROARRAY'}">
                        <td>
                            <a id="download-normalized" class="button-image"
                               title="Download all normalized expressions for the experiment"
                               href="#">
                                <img src="resources/images/download_blue_small_normalized.png"/></a>
                        </td>
                    </c:if>
                    <c:if test="${type != 'BASELINE'}">
                        <td>
                            <a id="download-analytics" class="button-image"
                               title="Download all analytics for the experiment"
                               href="#">
                                <img src="resources/images/download_blue_small_analytics.png"/></a>
                        </td>
                    </c:if>
                </tr>
            </table>
        </td>
    </tr>
    </tbody>

</table>


<script>
    (function ($) { //self invoking wrapper function that prevents $ namespace conflicts
        $(document).ready(function () {

            $("#extra-info").fancybox({
                /*
                 beforeLoad: function(){
                 this.title = "Look at this marvelous title... yes this is the title";
                 },*/
                padding:0,
                openEffect:'elastic',
                closeEffect:'elastic'
            });

        });
    })(jQuery);
</script>