<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="callout experiment-list-latest padding-bottom-for-button" data-equalizer-watch>
    <ul class="tabs" data-tabs id="experiments-tabs">
        <li class="tabs-title is-active"><a href="#featured-experiments">Featured</a></li>
        <li class="tabs-title"><a href="#latest-experiments" aria-selected="true">Latest experiments</a></li>
    </ul>


    <div class="tabs-content" data-tabs-content="experiments-tabs">

        <%--Using line height hack to align contents vertically, waiting for Flexible Grid support to align stuff inside rows: https://github.com/ebiwd/EBI-Framework/issues/71--%>
        <%--Links inside links to highlight text on hover, not necessary but aesthetically pleasing--%>
        <div class="tabs-panel is-active" id="featured-experiments">
            <div class="row margin-bottom-xxlarge">
                <div class="small-4 columns combo text-center padding-top-medium">
                    <a href="${pageContext.request.contextPath}/experiments?experimentSet=ENCODE">
                        <img class="margin-bottom-large experiment-thumbnail" src="${pageContext.request.contextPath}/resources/images/encode_logo.png" />
                        <h6>
                            <a href="${pageContext.request.contextPath}/experiments/E-MTAB-4344">Human tissues</a><br>
                            <a href="${pageContext.request.contextPath}/experiments/E-GEOD-26284">Human cells</a>
                        </h6>
                    </a>
                </div>
                <div class="small-4 columns combo text-center padding-top-medium">
                    <a href="${pageContext.request.contextPath}/experiments?experimentSet=BLUEPRINT">
                        <img class="margin-bottom-large experiment-thumbnail" src="${pageContext.request.contextPath}/resources/images/blueprint_logo.png"/>
                        <h6>
                            <a href="${pageContext.request.contextPath}/experiments/E-MTAB-4754">Plasma cells of tonsil</a><br>
                            <a href="${pageContext.request.contextPath}/experiments/E-MTAB-3819">Rare types of haemopoetic cells</a><br>
                            <a href="${pageContext.request.contextPath}/experiments/E-MTAB-3827">Common types of haemopoetic cells</a>
                        </h6>
                    </a>
                </div>
                <div class="small-4 columns combo text-center padding-top-medium">
                    <a href="${pageContext.request.contextPath}/experiments?experimentSet=FANTOM5">
                        <img class="margin-bottom-large experiment-thumbnail" src="${pageContext.request.contextPath}/resources/images/fantom_logo.png" />
                        <h6>
                            <a href="${pageContext.request.contextPath}/experiments/E-MTAB-3578">Mouse cells</a><br>
                            <a href="${pageContext.request.contextPath}/experiments/E-MTAB-3579">Mouse tissues</a><br>
                            <a href="${pageContext.request.contextPath}/experiments/E-MTAB-3358">Human tissues</a>
                        </h6>
                    </a>
                </div>
            </div>

            <div class="row margin-bottom-xxlarge">
                <div class="small-4 columns combo text-center padding-top-medium">
                    <a href="${pageContext.request.contextPath}/experiments/E-PROT-3">
                        <img class="margin-bottom-large experiment-thumbnail" src="${pageContext.request.contextPath}/resources/images/human_protein_atlas_logo.png" />
                        <h6>
                            <a href="${pageContext.request.contextPath}/experiments/E-PROT-3">Human tissues</a>
                        </h6>
                    </a>
                </div>
                <div class="small-4 columns combo text-center padding-top-medium">
                    <a href="${pageContext.request.contextPath}/experiments/E-MTAB-2770">
                        <img class="margin-bottom-large experiment-thumbnail" src="${pageContext.request.contextPath}/resources/images/ccle_logo.png" />
                        <h6>
                            <a href="${pageContext.request.contextPath}/experiments/E-MTAB-2770">Cancer Cell Line Encyclopedia</a>
                        </h6>
                    </a>
                </div>
                <div class="small-4 columns combo text-center padding-top-medium">
                    <a href="${pageContext.request.contextPath}/experiments?experimentSet=HipSci">
                        <img class="margin-bottom-large experiment-thumbnail" src="${pageContext.request.contextPath}/resources/images/hipsci_logo.png" />

                        <h6>
                            <a href="${pageContext.request.contextPath}/experiments/E-PROT-5">Proteomics – Cell Lines</a><br>
                            <a href="${pageContext.request.contextPath}/experiments/E-MTAB-4748">RNA – Cell lines</a>
                        </h6>
                    </a>
                </div>
            </div>

            <div class="row margin-bottom-xxlarge">
                <div class="small-4 columns combo text-center padding-top-medium">
                    <a href="${pageContext.request.contextPath}/experiments/E-MTAB-5214">
                        <img class="margin-bottom-large experiment-thumbnail" src="${pageContext.request.contextPath}/resources/images/gtex_logo.png" />
                        <h6>
                            <a href="${pageContext.request.contextPath}/experiments/E-MTAB-5214">Human tissues</a>
                        </h6>
                    </a>
                </div>
                <div class="small-4 columns combo text-center padding-top-medium">
                    <a href="${pageContext.request.contextPath}/baseline/experiments">
                        <img class="margin-bottom-large experiment-thumbnail" src="${pageContext.request.contextPath}/resources/images/baseline.png" />
                        <h6>
                            <a href="${pageContext.request.contextPath}/baseline/experiments">All baseline experiments</a>
                        </h6>
                    </a>
                </div>
                <div class="small-4 columns combo text-center padding-top-medium">
                    <a href="${pageContext.request.contextPath}/plant/experiments">
                        <img class="margin-bottom-large experiment-thumbnail" src="${pageContext.request.contextPath}/resources/images/gramene_logo_big.png" />
                        <h6>
                            <a href="${pageContext.request.contextPath}/plant/experiments">Plant experiments</a>
                        </h6>
                    </a>
                </div>
            </div>

            <div class="row">
                <div class="small-4 columns combo text-center padding-top-medium">
                    <a href="${pageContext.request.contextPath}/experiments?experimentSet=Pan-Cancer">
                        <img class="margin-bottom-large experiment-thumbnail" src="${pageContext.request.contextPath}/resources/images/pcawg_logo.png" />
                        <h6>
                            <a href="${pageContext.request.contextPath}/experiments/E-MTAB-5200">PCAWG by disease</a><br>
                        </h6>
                    </a>
                </div>
            </div>
        </div>

        <div class="tabs-panel" id="latest-experiments">
            <div class="row column">
                <h5 class="margin-bottom-large">${formattedExperimentCount} experiments</h5>
                    <c:forEach items="${latestExperiments}" var="experimentInfo">
                        <div class="media-object small combo padding-bottom-large padding-top-large padding-left-large margin-bottom-none">
                            <a href="${pageContext.request.contextPath}/experiments/${experimentInfo.experimentAccession}">
                                <div class="media-object-section middle">
                                    <c:choose>
                                        <c:when test="${experimentInfo.experimentType.baseline}">
                                            <span class="baseline button-rd">B</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="differential button-rd">D</span>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <div class="media-object-section middle">
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
                                </div>
                            </a>
                        </div>
                    </c:forEach>
            </div>
        </div>

    </div>

    <div class="row align-row-to-bottom">
        <div class="small-6 small-centered columns margin-top-large">
            <a href='${pageContext.request.contextPath}/experiments' class="button float-center">View all experiments</a>
        </div>
    </div>

</div>
