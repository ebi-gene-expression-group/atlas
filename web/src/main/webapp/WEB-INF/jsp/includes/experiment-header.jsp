<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- Add fancyBox -->

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/fancyapps-fancyBox-0ffc358/source/jquery.fancybox.css" type="text/css" media="screen" />
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/fancyapps-fancyBox-0ffc358/source/jquery.fancybox.pack.js"></script>


<script>
    (function ($) { //self invoking wrapper function that prevents $ namespace conflicts
        $(document).ready(function () {

            $("#extra-info").fancybox({
                /*
                beforeLoad: function(){
                    this.title = "Look at this marvelous title... yes this is the title";
                },*/
                padding : 0,
                openEffect	: 'elastic',
                closeEffect	: 'elastic'
            });

        });
    })(jQuery);
</script>



<div id="helpContentTooltip" style='display:none'></div>

<table width="100%">
    <tbody>
    <tr>
        <td style="width:140px;">
            <div class="experiment-accession">
                <a href="experiments/${experimentAccession}">${experimentAccession}</a>
            </div>
        </td>
        <td width="100%">
            <div id="experimentDescription" style="font-weight: bold;">
                ${experimentDescription}
                <c:if test="${hasExtraInfo}">
                    <a id="extra-info" href="experiments/${experimentAccession}/extra-info.png">
                        <img alt="more information" src="/gxa/resources/images/balloon-ellipsis-icon-left.png">
                    </a>
                </c:if>
            </div>
            <div>Organism(s): ${allSpecies}</div>
        </td>
        <td width="130px" style="float:right">
            <div style="text-align:right">
                <a id="display-analysis-methods" class="button-image" title="Analysis Methods"
                   href="experiments/${experimentAccession}/analysis-methods">
                    <img style="width:23px;height:23px"
                         src="resources/images/analysis_icon.png"/></a>

                <a id="display-experiment-design" class="button-image"
                   title="Experiment Design" href="experiments/${experimentAccession}/experiment-design">
                    <img src="resources/images/experiment_design_icon.png"/></a>

                <a id="goto-ae" class="button-image"
                   href="${applicationProperties.getArrayExpressURL(experimentAccession)}" title="ArrayExpress"
                   target="_blank">
                    <img src="resources/images/ae2_icon.png"/></a>

            </div>
        </td>
    </tr>
    </tbody>

</table>