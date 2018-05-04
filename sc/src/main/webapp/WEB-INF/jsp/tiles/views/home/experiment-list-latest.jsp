<%--@elvariable id="experimentCount" type="int"--%>
<%--@elvariable id="latestExperiments" type="int"--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<div>
    <ul class="tabs" data-tabs id="experiments-tabs">
        <li class="tabs-title is-active"><a href="#latest-experiments" aria-selected="true">Latest experiments</a></li>
    </ul>

    <div class="tabs-content" data-tabs-content="experiments-tabs">
        <div class="tabs-panel is-active" id="latest-experiments">
            <ul class="clear">
                <c:forEach items="${latestExperiments}" var="experimentInfo">
                    <li>
                        <div class="media-object stack-for-small">
                            <div class="media-object-section middle hide-for-small-only">
                                <c:choose>
                                    <c:when test="${experimentInfo.experimentType.baseline}">
                                        <span data-tooltip class="baseline button-rd" title="Baseline experiment">B</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span data-tooltip class="differential button-rd" title="Differential experiment">D</span>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="media-object-section middle hide-for-small-only">
                                <s:eval expression="T(java.text.NumberFormat).getInstance().format(experimentInfo.numberOfAssays)" var="fmtNumberOfAssays" />
                                <span class="button secondary no-action">${fmtNumberOfAssays}</span>
                            </div>
                            <div class="media-object-section middle hide-for-small-only ">
                                <a class="button" href="${pageContext.request.contextPath}/experiments/${experimentInfo.experimentAccession}">Analysis result</a>
                            </div>
                            <div class="media-object-section middle">
                                <small>${experimentInfo.lastUpdate}</small><br/>
                                <a href="${pageContext.request.contextPath}/experiments/${experimentInfo.experimentAccession}">
                                    ${experimentInfo.experimentDescription} <i>${experimentInfo.species}</i>
                                </a>
                            </div>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>
</div>
