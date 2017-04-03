<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="callout experiment-list-latest" data-equalizer-watch>
    <ul class="tabs" data-tabs id="experiments-tabs">
        <li class="tabs-title is-active"><a href="#featured-experiments">Featured</a></li>
        <li class="tabs-title"><a href="#latest-experiments" aria-selected="true">Latest experiments</a></li>
    </ul>


    <div class="tabs-content" data-tabs-content="experiments-tabs">

        <%--Using line height hack to align contents vertically, waiting for Flexible Grid support to align stuff inside rows: https://github.com/ebiwd/EBI-Framework/issues/71--%>
        <%--Links inside links to highlight text on hover, not necessary but aesthetically pleasing--%>
        <div class="tabs-panel is-active" id="featured-experiments">
            <div class="list-project-l">
                <ul>
                    <li>
                        <a href="${pageContext.request.contextPath}/experiments?experimentType=baseline">
                            <div class="row">
                                <div class="columns small-4">
                                    <img style="height: 65px" src="${pageContext.request.contextPath}/resources/images/baseline.png"/>
                                </div>
                                <div class="columns small-8">
                                    <h5 style="line-height: 65px"><a href="${pageContext.request.contextPath}/experiments?experimentType=baseline">All baseline experiments</a></h5>
                                </div>
                            </div>
                        </a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/plant/experiments">
                            <div class="row align-middle">
                                <div class="columns small-4">
                                    <img style="height: 65px" src="${pageContext.request.contextPath}/resources/images/gramene_logo_big.png"/>
                                </div>
                                <div class="columns small-8">
                                    <h5 style="line-height: 65px"><a href="${pageContext.request.contextPath}/plant/experiments">Plant experiments</a></h5>
                                </div>
                            </div>
                        </a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/experiments?foundation&experimentSet=HipSci">
                            <div class="row align-middle">
                                <div class="columns small-4">
                                    <img style="height: 65px" src="${pageContext.request.contextPath}/resources/images/hipsci_logo.png"/>
                                </div>
                                <div class="columns small-8">
                                    <h5>
                                        <a href="${pageContext.request.contextPath}/experiments/E-PROT-5">Proteomics - Cell Lines - HipSci</a><br/>
                                        <%--HipSci project pilot submission for 18 IPS cell lines--%>
                                        <a href="${pageContext.request.contextPath}/experiments/E-MTAB-4748">Cell lines - 196 HipSci</a>
                                        <%--RNA-seq of coding RNA in human fibroblasts, PBMCs and induced pluripotent stem cells (iPS cells) as part of the HipSci project--%>
                                    </h5>
                                </div>
                            </div>
                        </a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/experiments/E-MTAB-5214">
                            <div class="row align-middle">
                                <div class="columns small-4">
                                    <img style="height: 65px" src="${pageContext.request.contextPath}/resources/images/gtex_logo.png"/>
                                </div>
                                <div class="columns small-8">
                                    <h5><a href="${pageContext.request.contextPath}/experiments/E-MTAB-5214">RNA-seq from 53 human tissue samples from the Genotype-Tissue Expression (GTEx) Project</a></h5>
                                </div>
                            </div>
                        </a>
                    </li>
                </ul>
            </div>
        </div>

        <div class="tabs-panel is-active" id="latest-experiments">
            <h5 class="margin-top-medium margin-bottom-xlarge">Total : ${experimentCount} experiments</h5>
            <div class="list-project-l">
                <ul>
                    <c:forEach items="${latestExperiments}" var="experimentInfo">
                        <li>
                            <a href="${pageContext.request.contextPath}/experiments/${experimentInfo.experimentAccession}">
                                <c:choose>
                                    <c:when test="${experimentInfo.experimentType.baseline}">
                                        <span data-tooltip aria-haspopup="true" class="float-left baseline button-rd" title="Baseline experiment">B</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span data-tooltip aria-haspopup="true" class="float-left differential button-rd" title="Differential experiment">D</span>
                                    </c:otherwise>
                                </c:choose>
                                <small>${experimentInfo.lastUpdate}</small><br/>
                                    ${experimentInfo.experimentDescription}
                                <c:choose>
                                    <c:when test="${experimentInfo.experimentType.baseline}">
                                        (${experimentInfo.numberOfAssays} assays) &ndash;
                                    </c:when>
                                    <c:otherwise>
                                        (${experimentInfo.numberOfContrasts} contrasts) &ndash;
                                    </c:otherwise>
                                </c:choose>
                                <em>${experimentInfo.species}</em>
                            </a>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>

    </div>

    <div class="row">
        <div class="small-6 small-centered columns margin-top-large">
            <a href='${pageContext.request.contextPath}/experiments?foundation' class="button float-center">View all experiments</a>
        </div>
    </div>

</div>
