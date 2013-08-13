<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${not empty param.accessKey}">
    <c:set var="accessKeyQueryString" value="?accessKey=${param.accessKey}"></c:set>
</c:if>

<section class="extra-padding">

    <div id="helpContentTooltip" style='display:none'></div>

    <table width="100%">
        <tbody>
        <tr>
            <%@ include file="experiment-description.jsp" %>
            <td width="130px">
                <table cellpadding="2" cellspacing="0" style="float:right">
                    <tr>
                        <td>
                            <a id="display-experiment" class="button-image"
                               title="Experiment Page" href="experiments/${experimentAccession}${accessKeyQueryString}">
                                <img src="resources/images/experiment_page_small.png"/></a>
                        </td>
                        <td>
                            <a id="display-analysis-methods" class="button-image" title="Analysis Methods"
                               href="experiments/${experimentAccession}/analysis-methods${accessKeyQueryString}">
                                <img style="width:23px;height:23px"
                                     src="resources/images/analysis_icon.png"/></a>
                        </td>
                        <td>
                            <a id="display-experiment-design" class="button-image"
                               title="Experiment Design" href="experiments/${experimentAccession}/experiment-design${accessKeyQueryString}">
                                <img src="resources/images/experiment_design_icon.png"/></a>
                        </td>
                        <c:if test="${type.isDifferential()}">
                            <td>
                                <a id="download-raw" class="button-image"
                                   title="Download all raw counts for the experiment"
                                   href="${rawDownloadUrl}${accessKeyQueryString}">
                                    <img src="resources/images/download_blue_small_raw.png"/></a>
                            </td>
                        </c:if>
                        <c:if test="${type.isMicroarray()}">
                            <td>
                                <c:choose>
                                    <c:when test="${type.isTwoColour()}">
                                        <a id="download-logFold" class="button-image"
                                           title="Download all log fold expression changes for the experiment"
                                           href="${logFoldUrl}${accessKeyQueryString}">
                                            <img src="resources/images/download_blue_small_logfold.png"/></a>
                                    </c:when>
                                    <c:otherwise>
                                        <a id="download-normalized" class="button-image"
                                           title="Download all normalized expressions for the experiment"
                                           href="${normalizedUrl}${accessKeyQueryString}">
                                            <img src="resources/images/download_blue_small_normalized.png"/></a>
                                    </c:otherwise>
                                </c:choose>

                            </td>
                        </c:if>
                        <c:if test="${!type.isBaseline()}">
                            <td>
                                <a id="download-analytics" class="button-image"
                                   title="Download all analytics for the experiment"
                                   href="${analyticsDownloadUrl}${accessKeyQueryString}">
                                    <img src="resources/images/download_blue_small_analytics.png"/></a>
                            </td>
                        </c:if>
                    </tr>
                </table>
            </td>
        </tr>
        </tbody>

    </table>

</section>

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

