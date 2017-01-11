<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="callout experiment-list-latest" data-equalizer-watch>
    <ul class="tabs" data-tabs id="experiments-tabs">
        <li class="tabs-title is-active"><a href="#latest-experiments" aria-selected="true">Browse experiments</a></li>
    </ul>


    <div class="tabs-content" data-tabs-content="experiments-tabs">

        <div class="tabs-panel is-active" id="latest-experiments">
            <h5 class="margin-top-medium margin-bottom-xlarge"><small>
                Total : ${experimentCount} experiments</small></h5>
            <div class="list-project-l">
                <ul>
                    <c:forEach items="${latestExperiments}" var="experimentInfo">
                        <li>
                            <a href="experiments/${experimentInfo.experimentAccession}">
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
                                        ${experimentInfo.numberOfAssays} assays &ndash;
                                    </c:when>
                                    <c:otherwise>
                                        ${experimentInfo.numberOfContrasts} contrasts &ndash;
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
</div>
