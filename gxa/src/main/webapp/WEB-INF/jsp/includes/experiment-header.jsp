<%--@elvariable id="experimentAccession" type="java.lang.String"--%>
<%--@elvariable id="species" type="java.lang.String"--%>
<%--@elvariable id="rawDownloadUrl" type="java.lang.String"--%>
<%--@elvariable id="analyticsDownloadUrl" type="java.lang.String"--%>
<%--@elvariable id="normalizedUrl" type="java.lang.String"--%>
<%--@elvariable id="logFoldUrl" type="java.lang.String"--%>
<%--@elvariable id="qcArrayDesigns" type="java.util.SortedSet"--%>
<%--@elvariable id="experiment" type="atlas.model.Experiment"--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${not empty param.accessKey}">
    <c:set var="accessKeyQueryString" value="?accessKey=${param.accessKey}"/>
</c:if>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/experiment-header.css">

<script language="JavaScript" type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/fancybox/2.1.5/jquery.fancybox.pack.js"></script>
<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/fancybox/2.1.5/jquery.fancybox.min.css" media="screen">

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/customized-bootstrap-3.3.5.css">
<script src="${pageContext.request.contextPath}/resources/js/lib/bootstrap-3.3.5.min.js"></script>

<script language="JavaScript" type="text/javascript" src="${pageContext.request.contextPath}/resources/js/experiment-page-buttons-and-tooltips.js"></script>

<script>
    var bootstrapButton = $.fn.button.noConflict(); // return $.fn.button to previously assigned value
    $.fn.bootstrapBtn = bootstrapButton;            // give $().bootstrapBtn the Bootstrap functionality

    var bootstrapTooltip = $.fn.tooltip.noConflict();
    $.fn.bootstrapTt = bootstrapTooltip;
</script>

<section id="gxaExperimentHeader" style="text-align: justify;">

    <div id="helpContentTooltip" style="display: none;"></div>

    <div class="grid_18">
        <%@ include file="experiment-description.jsp" %>
    </div>

    <div class="grid_6">
        <table style="width: auto; float: right;">
            <tbody>
                <tr>
                    <td>
                        <a id="display-ae" title="View experiment in ArrayExpress"
                        <%--@elvariable id="applicationProperties" type="atlas.web.ApplicationProperties"--%>
                           href="${applicationProperties.getArrayExpressURL(experimentAccession)}">
                            <img src="${pageContext.request.contextPath}/resources/images/ae-logo-64.png"/></a>
                    </td>

                    <td>
                        <a id="display-experiment" title="Experiment Page"
                           href="${pageContext.request.contextPath}/experiments/${experimentAccession}${accessKeyQueryString}">
                            <img src="${pageContext.request.contextPath}/resources/images/experiment_page_small.png"/></a>
                    </td>

                    <td>
                        <a id="display-analysis-methods" title="Analysis Methods"
                           href="${pageContext.request.contextPath}/experiments/${experimentAccession}/analysis-methods${accessKeyQueryString}">
                            <img style="width:23px;height:23px"
                                 src="${pageContext.request.contextPath}/resources/images/analysis_icon.png"/></a>
                    </td>

                    <td>
                        <a id="display-experiment-design" title="Experiment Design"
                           href="${pageContext.request.contextPath}/experiments/${experimentAccession}/experiment-design${accessKeyQueryString}">
                            <img src="${pageContext.request.contextPath}/resources/images/experiment_design_icon.png"/></a>
                    </td>


                    <c:if test="${qcArrayDesigns!=null}">

                        <c:forEach items="${qcArrayDesigns}" var="arrayDesign" varStatus="loop">

                            <c:if test="${loop.first}">
                                <%--@elvariable id="qcReportUtil" type="atlas.experimentpage.qc.QCReportUtil"--%>
                                <c:set var="hasQcReport" value="${qcReportUtil.hasQCReport(experimentAccession, arrayDesign)}"/>

                                <c:if test="${hasQcReport}">
                                    <td>
                                        <a id="display-qc-report" title="Quality Metrics Report"
                                           href="${pageContext.request.contextPath}/experiments/${experimentAccession}/qc/${arrayDesign}/index.html${accessKeyQueryString}">
                                            <img src="${pageContext.request.contextPath}/resources/images/qc_v15.png"/>
                                        </a>
                                    </td>
                                </c:if>

                            </c:if>
                        </c:forEach>

                    </c:if>


                    <%--@elvariable id="fastQCReportUtil" type="atlas.experimentpage.fastqc.FastQCReportUtil"--%>
                    <c:set var="hasFastQcReport" value="${fastQCReportUtil.hasFastQC(experimentAccession, species)}"/>
                    <c:if test="${hasFastQcReport && qcArrayDesigns==null}" >
                        <td>
                            <a id="display-fastqc-report" title="Quality Metrics Report"
                               href="${pageContext.request.contextPath}/experiments/${experimentAccession}/fastqc/${species}/qc.html${accessKeyQueryString}">
                                <img src="${pageContext.request.contextPath}/resources/images/qc_v15.png"/>
                            </a>
                        </td>
                    </c:if>


                    <%--@elvariable id="clusteringPdfViewHelper" type="atlas.experimentpage.ClusteringPdfViewHelper"--%>
                    <c:if test="${clusteringPdfViewHelper.hasFile(experimentAccession)}" >
                        <td>
                            <a id="clustering-pdf" title="Explore hierarchical clustering between experimental conditions and the top 100 most variable genes in the experiment"
                               href="${pageContext.request.contextPath}${clusteringPdfViewHelper.generateUrl(experimentAccession)}${accessKeyQueryString}">
                                <img src="${pageContext.request.contextPath}/resources/images/cluster_button.png"/>
                            </a>
                        </td>
                    </c:if>


                    <c:set var="download-expressions" value="${false}"/>
                    <%--@elvariable id="type" type="atlas.model.ExperimentType"--%>
                    <%--@elvariable id="rDataViewHelper" type="atlas.experimentpage.RDataViewHelper"--%>
                    <c:if test="${rDataViewHelper.hasFile(experimentAccession)}">
                        <td>
                            <c:choose>
                                <c:when test="${not empty disclaimer}">
                                    <a id="download-r-modal" title="Download experiment data ready to load into R"
                                       role="button"
                                       data-load-url="${pageContext.request.contextPath}/resources/html/disclaimer/${disclaimer}.html"
                                       data-toggle="modal" data-target="#download-modal">
                                        <img src="${pageContext.request.contextPath}/resources/images/r-button.png"/>
                                    </a>
                                </c:when>
                                <c:otherwise>
                                    <a id="download-r" title="Download experiment data ready to load into R"
                                       href="${pageContext.request.contextPath}${rDataViewHelper.generateUrl(experimentAccession)}${accessKeyQueryString}">
                                        <img src="${pageContext.request.contextPath}/resources/images/r-button.png"/>
                                    </a>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </c:if>


                    <c:if test="${type.rnaSeqDifferential}">
                        <td>
                            <a id="download-raw" title="Download all raw counts for the experiment"
                               href="${rawDownloadUrl}${accessKeyQueryString}">
                                <img src="${pageContext.request.contextPath}/resources/images/download_blue_small_raw.png"/></a>
                        </td>
                    </c:if>

                    <c:if test="${type.microarray}">
                        <td>
                            <c:choose>
                                <c:when test="${type.twoColour}">
                                    <a id="download-logFold" title="Download all log fold expression changes for the experiment"
                                       href="${logFoldUrl}${accessKeyQueryString}">
                                        <img src="${pageContext.request.contextPath}/resources/images/download_blue_small_logfold.png"/></a>
                                </c:when>
                                <c:otherwise>
                                    <a id="download-normalized" title="Download all normalized expressions for the experiment"
                                       href="${normalizedUrl}${accessKeyQueryString}">
                                        <img src="${pageContext.request.contextPath}/resources/images/download_blue_small_normalized.png"/></a>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </c:if>


                    <c:choose>
                        <c:when test="${type.baseline}">
                            <td>
                                <c:choose>
                                    <c:when test="${not empty disclaimer}">
                                        <a id="download-expressions-modal" title="Download all expressions for the experiment"
                                           role="button"
                                           data-load-url="${pageContext.request.contextPath}/resources/html/disclaimer/${disclaimer}.html"
                                           data-toggle="modal" data-target="#download-modal">
                                            <img src="${pageContext.request.contextPath}/resources/images/download_blue_small_normalized.png"/>
                                        </a>
                                    </c:when>
                                    <c:otherwise>
                                        <a id="download-expressions" title="Download all expressions for the experiment"
                                           href="${applicationProperties.buildServerURL(pageContext.request)}/experiments/${experimentAccession}.tsv?accessKey=${param.accessKey}&geneQuery=&cutoff=0">
                                            <img src="${pageContext.request.contextPath}/resources/images/download_blue_small_normalized.png"/>
                                        </a>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </c:when>
                        <c:otherwise>
                            <td>
                                <a id="download-analytics" title="Download all analytics for the experiment"
                                   href="${analyticsDownloadUrl}${accessKeyQueryString}">
                                    <img src="${pageContext.request.contextPath}/resources/images/download_blue_small_analytics.png"/></a>
                            </td>
                        </c:otherwise>
                    </c:choose>
                </tr>
            </tbody>
        </table>
    </div>

    <!-- Modal -->
    <div id="download-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class = "modal-content">

                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h4></h4>
                </div>

                <div class="modal-body edit-content" style="max-height: 360px">

                </div>

                <div class="modal-footer">
                    <button class="btn btn-default" data-dismiss="modal" aria-hidden="true">Close</button>
                        <c:if test="${type.baseline && not empty disclaimer}">
                            <button class="btn btn-primary" id="continue-download-expressions"
                               onclick="location.href='${applicationProperties.buildServerURL(pageContext.request)}/experiments/${experimentAccession}.tsv?accessKey=${param.accessKey}&geneQuery=&cutoff=-0.1'">
                                Continue downloading
                            </button>
                        </c:if>
                        <c:if test="${rDataViewHelper.hasFile(experimentAccession)}">
                             <button class="btn btn-primary" id="continue-download-R"
                                     onclick="location.href='${pageContext.request.contextPath}${rDataViewHelper.generateUrl(experimentAccession)}${accessKeyQueryString}'">Continue downloading</button>
                        </c:if>
                </div>

            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

</section>

<script>
    (function ($) { //self invoking wrapper function that prevents $ namespace conflicts
        $(document).ready(function () {

            initExperimentPageButtonsAndTooltips();

            $("#extra-info").fancybox({
                padding:0,
                openEffect:'elastic',
                closeEffect:'elastic'
            });

            $('#download-modal').on('show.bs.modal', function (event) {
                var loadURL = event.relatedTarget;
                $(this).find('.modal-body').load(loadURL.dataset.loadUrl);
            });

            $("#download-expressions-modal").click(function(event) {
                event.preventDefault();
                $("#continue-download-R").hide();
                $("#continue-download-expressions").show();

            });
            $("#download-r-modal").click(function(event) {
                event.preventDefault();
                $("#continue-download-expressions").hide();
                $("#continue-download-R").show();

            });

            $("#continue-download-expressions").click(function(event) {
                $('#download-modal').modal('hide');
            });

            $("#continue-download-R").click(function(event) {
                $('#download-modal').modal('hide');
            });

        });
    })(jQuery);
</script>

