<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${not empty param.accessKey}">
    <c:set var="accessKeyQueryString" value="?accessKey=${param.accessKey}"></c:set>
</c:if>

<section class="gxaExtraPadding" id="gxaExperimentHeader">

    <div id="helpContentTooltip" style='display:none'></div>
    <table width="100%">
        <tbody>
        <tr>
            <%@ include file="experiment-description.jsp" %>
            <td width="130px">
                <table cellpadding="2" cellspacing="0" style="float:right">
                    <tr>
                        <td>
                            <a id="display-ae" class="gxaButtonImage"
                               title="View experiment in ArrayExpress"
                               href="${applicationProperties.getArrayExpressURL(experimentAccession)}">
                                <img src="${pageContext.request.contextPath}/resources/latest/images/ae-logo-64.png"/></a>
                        </td>
                        <td>
                            <a id="display-experiment" class="gxaButtonImage"
                               title="Experiment Page" href="${pageContext.request.contextPath}/experiments/${experimentAccession}${accessKeyQueryString}">
                                <img src="${pageContext.request.contextPath}/resources/latest/images/experiment_page_small.png"/></a>
                        </td>
                        <td>
                            <a id="display-analysis-methods" class="gxaButtonImage" title="Analysis Methods"
                               href="${pageContext.request.contextPath}/experiments/${experimentAccession}/analysis-methods${accessKeyQueryString}">
                                <img style="width:23px;height:23px"
                                     src="${pageContext.request.contextPath}/resources/latest/images/analysis_icon.png"/></a>
                        </td>
                        <td>
                            <a id="display-experiment-design" class="gxaButtonImage"
                               title="Experiment Design" href="${pageContext.request.contextPath}/experiments/${experimentAccession}/experiment-design${accessKeyQueryString}">
                                <img src="${pageContext.request.contextPath}/resources/latest/images/experiment_design_icon.png"/></a>
                        </td>

                        <c:if test="${qcArrayDesigns!=null}">

                            <c:forEach items="${qcArrayDesigns}" var="arrayDesign" varStatus="loop">

                                <c:if test="${loop.first}">
                                    <%--@elvariable id="qcReportUtil" type="uk.ac.ebi.atlas.experimentpage.qc.QCReportUtil"--%>
                                    <c:set var="hasQcReport" value="${qcReportUtil.hasQCReport(experimentAccession, arrayDesign)}"/>

                                    <c:if test="${hasQcReport}">
                                        <td>
                                            <a id="display-qc-report" class="gxaButtonImage" title="Quality Metrics Report"
                                               href="${pageContext.request.contextPath}/experiments/${experimentAccession}/qc/${arrayDesign}/index.html${accessKeyQueryString}">
                                                <img src="${pageContext.request.contextPath}/resources/latest/images/qc_v15.png"/>
                                            </a>
                                        </td>
                                    </c:if>

                                </c:if>

                            </c:forEach>
                        </c:if>

                        <%--@elvariable id="fastQReportUtil" type="uk.ac.ebi.atlas.experimentpage.fastqc.FastQCReportUtil"--%>
                        <c:set var="hasFastQcReport" value="${fastQReportUtil.hasFastQC(experimentAccession, species)}"/>
                        <c:if test="${hasFastQcReport && qcArrayDesigns==null}" >
                            <td>
                                <a id="display-fastqc-report" class="gxaButtonImage" title="Quality Metrics Report"
                                   href="${pageContext.request.contextPath}/experiments/${experimentAccession}/fastqc/${species}/qc.html${accessKeyQueryString}">
                                    <img src="${pageContext.request.contextPath}/resources/latest/images/qc_v15.png"/>
                                </a>
                            </td>
                        </c:if>

                        <%--@elvariable id="hierarchicalClusteringPdfViewHelper" type="uk.ac.ebi.atlas.experimentpage.HierarchicalClusteringPdfViewHelper"--%>
                        <c:if test="${experiment.multiOrganismExperiment ? hierarchicalClusteringPdfViewHelper.hasPdf(experimentAccession, species) : hierarchicalClusteringPdfViewHelper.hasSingleSpeciesPdf(experimentAccession)}" >
                            <td>
                                <a id="clustering-pdf" class="gxaButtonImage" title="Explore hierarchical clustering between experimental conditions and the top 100 most variable genes in the experiment"
                                   href="${pageContext.request.contextPath}${experiment.multiOrganismExperiment ? hierarchicalClusteringPdfViewHelper.generateUrl(experimentAccession, species) : hierarchicalClusteringPdfViewHelper.generateSingleSpeciesUrl(experimentAccession)}${accessKeyQueryString}">
                                    <img src="${pageContext.request.contextPath}/resources/latest/images/cluster_button.png"/>
                                </a>
                            </td>
                        </c:if>

                        <!-- download-r button section -->
                        <%--@elvariable id="type" type="uk.ac.ebi.atlas.model.ExperimentType"--%>
                        <c:if test="${type.differential}">
                            <td>
                                <a id="download-r" class="gxaButtonImage"
                                   title="Download experiment data ready to load into R"
                                   href="${rDownloadUrl}${accessKeyQueryString}">
                                    <img src="${pageContext.request.contextPath}/resources/latest/images/r-button.png"/>
                                </a>
                            </td>
                        </c:if>

                        <c:if test="${type.rnaSeqDifferential}">
                            <td>
                                <a id="download-raw" class="gxaButtonImage"
                                   title="Download all raw counts for the experiment"
                                   href="${rawDownloadUrl}${accessKeyQueryString}">
                                    <img src="${pageContext.request.contextPath}/resources/latest/images/download_blue_small_raw.png"/></a>
                            </td>
                        </c:if>
                        <c:if test="${type.microarray}">
                            <td>
                                <c:choose>
                                    <c:when test="${type.isTwoColour()}">
                                        <a id="download-logFold" class="gxaButtonImage"
                                           title="Download all log fold expression changes for the experiment"
                                           href="${logFoldUrl}${accessKeyQueryString}">
                                            <img src="${pageContext.request.contextPath}/resources/latest/images/download_blue_small_logfold.png"/></a>
                                    </c:when>
                                    <c:otherwise>
                                        <a id="download-normalized" class="gxaButtonImage"
                                           title="Download all normalized expressions for the experiment"
                                           href="${normalizedUrl}${accessKeyQueryString}">
                                            <img src="${pageContext.request.contextPath}/resources/latest/images/download_blue_small_normalized.png"/></a>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </c:if>
                        <c:choose>
                            <c:when test="${type.baseline}">
                                <td>
                                    <a id="download-expressions" class="gxaButtonImage"
                                       title="Download all expressions for the experiment"
                                       href="${applicationProperties.buildServerURL(pageContext.request)}/experiments/${experimentAccession}.tsv?accessKey=${param.accessKey}&geneQuery=&cutoff=-0.1">
                                        <img src="${pageContext.request.contextPath}/resources/latest/images/download_blue_small_normalized.png"/></a>
                                </td>
                            </c:when>
                            <c:otherwise>
                                <td>
                                    <a id="download-analytics" class="gxaButtonImage"
                                       title="Download all analytics for the experiment"
                                       href="${analyticsDownloadUrl}${accessKeyQueryString}">
                                        <img src="${pageContext.request.contextPath}/resources/latest/images/download_blue_small_analytics.png"/></a>
                                </td>
                            </c:otherwise>
                        </c:choose>

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

