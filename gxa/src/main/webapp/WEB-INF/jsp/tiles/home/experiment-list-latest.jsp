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
            <div class="row">
                <div class="columns small-6 medium-4 text-center">
                    <a href="${pageContext.request.contextPath}/experiments?experimentSet=ENCODE">
                        <img style="height: 65px" src="${pageContext.request.contextPath}/resources/images/encode_logo.png" class="float-center"/>
                        <h5>
                            <a href="${pageContext.request.contextPath}/experiments/E-MTAB-4344">Human tissues</a><br/>
                            <a href="${pageContext.request.contextPath}/experiments/E-GEOD-26284">Human cells</a><br/>
                        </h5>
                    </a>
                </div>
                <div class="columns small-6 medium-4 text-center">
                    <a href="${pageContext.request.contextPath}/experiments/E-MTAB-4754">
                        <img style="height: 65px" src="${pageContext.request.contextPath}/resources/images/blueprint_logo.png" class="float-left"/>
                        <a href="${pageContext.request.contextPath}/experiments/E-MTAB-4754">Plasma cells of tonsil</a><br/>
                        <a href="${pageContext.request.contextPath}/experiments/E-MTAB-3819">Primary cells rare types</a><br/>
                        <a href="${pageContext.request.contextPath}/experiments/E-MTAB-3827">Primary cells common types</a><br/>
                    </a>
                </div>

                <div class="columns small-6 medium-4 text-center">
                    <a href="${pageContext.request.contextPath}/experiments?experimentSet=FANTOM5">
                        <img style="height: 65px" src="${pageContext.request.contextPath}/resources/images/fantom_logo.png" class="float-center"/>
                        <h5>
                            <a href="${pageContext.request.contextPath}/experiments/E-MTAB-3578">Mice cells</a><br/>
                            <a href="${pageContext.request.contextPath}/experiments/E-MTAB-3579">Mice tissues</a><br/>
                            <a href="${pageContext.request.contextPath}/experiments/E-MTAB-3358">Human tissues</a>
                        </h5>
                    </a>
                </div>
                <div class="columns small-6 medium-4 text-center">
                    <a href="${pageContext.request.contextPath}/experiments/E-PROT-3">
                        <img style="height: 65px" src="${pageContext.request.contextPath}/resources/images/human_protein_atlas_logo.png" class="float-center"/>
                        <h5>Human Protein Atlas</h5>
                    </a>
                </div>

                <div class="columns small-6 medium-4 text-center">
                    <a href="${pageContext.request.contextPath}/experiments/E-MTAB-2770">
                        <img style="height: 65px" src="${pageContext.request.contextPath}/resources/images/ccle_logo.png" class="float-center"/>
                        <h5>Cancer Cell Line Encyclopedia</h5>
                    </a>
                </div>

                <div class="columns small-6 medium-4 text-center">
                    <a href="${pageContext.request.contextPath}/experiments?experimentSet=HipSci">
                        <img style="height: 65px" src="${pageContext.request.contextPath}/resources/images/hipsci_logo.png" class="float-center"/>

                        <h5>
                                    <a href="${pageContext.request.contextPath}/experiments/E-PROT-5">Proteomics – Cell Lines</a><br/>
                                    <a href="${pageContext.request.contextPath}/experiments/E-MTAB-4748">RNA – Cell lines</a>
                                </h5>
                    </a>
                </div>

                <div class="columns small-6 medium-4 text-center">
                    <a href="${pageContext.request.contextPath}/experiments/E-MTAB-5214">
                        <img style="height: 65px" src="${pageContext.request.contextPath}/resources/images/gtex_logo.png" class="float-center"/>
                        <h5>RNA-seq from 53 human tissue samples from the Genotype-Tissue Expression (GTEx) Project</h5>
                    </a>
                </div>

                <div class="columns small-6 medium-4 text-center">
                    <a href="${pageContext.request.contextPath}/baseline/experiments">
                        <img src="${pageContext.request.contextPath}/resources/images/baseline.png" class="float-center"/>
                        <h5>All baseline experiments</h5>
                    </a>
                </div>
                <div class="columns small-6 medium-4 text-center end">
                    <a href="${pageContext.request.contextPath}/plant/experiments">
                        <img style="height: 65px" src="${pageContext.request.contextPath}/resources/images/gramene_logo_big.png" class="float-center"/>
                        <h5>Plant experiments</h5>
                    </a>
                </div>

            </div>

        </div>

        <div class="tabs-panel is-active" id="latest-experiments">
            <h5 class="margin-top-medium margin-bottom-xlarge">${formattedExperimentCount} experiments</h5>
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

    <div class="row align-row-to-bottom">
        <div class="small-6 small-centered columns margin-top-large">
            <a href='${pageContext.request.contextPath}/experiments' class="button float-center">View all experiments</a>
        </div>
    </div>

</div>
