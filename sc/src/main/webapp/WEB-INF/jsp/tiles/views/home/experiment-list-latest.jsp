<%--@elvariable id="experimentCount" type="int"--%>
<%--@elvariable id="latestExperiments" type="int"--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="columns experiment-list-latest" data-equalizer-watch>
    <ul class="tabs" data-tabs id="experiments-tabs">
        <li class="tabs-title is-active"><a href="#latest-experiments" aria-selected="true">List of single-cell experiments</a></li>
    </ul>

    <div class="tabs-content" data-tabs-content="experiments-tabs">

        <div class="tabs-panel is-active" id="latest-experiments" role="tabpanel">
            <h5 class="margin-top-medium margin-bottom-xlarge">
                <small>Total: ${experimentCount} experiments</small>
            </h5>
            <div class="flex-list">
                <ul>
                    <c:forEach items="${latestExperiments}" var="experimentInfo">
                        <a href="experiments/${experimentInfo.experimentAccession}">
                            <li class="flex-container">
                                <div class="flex-item">
                                    <c:choose>
                                        <c:when test="${experimentInfo.experimentType.baseline}">
                                            <span data-tooltip class="float-left baseline button-rd" title="Baseline experiment">B</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span data-tooltip class="float-left differential button-rd" title="Differential experiment">D</span>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <div class="flex-item" style="flex-grow:1;">
                                    <small>${experimentInfo.lastUpdate}</small><br/>
                                    ${experimentInfo.experimentDescription}
                                    <i>${experimentInfo.species}</i>
                                </div>
                                <div class="flex-item">
                                    <span class="button primary small">${experimentInfo.numberOfAssays}</span>
                                    <span class="button small">Analysis result</span>
                                    <span class="button icon icon-functional small" data-icon="="></span>
                                </div>
                            </li>
                        </a>
                    </c:forEach>
                </ul>
            </div>

        </div>

    </div>
</div>
