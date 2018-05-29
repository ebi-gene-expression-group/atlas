<%--@elvariable id="experimentCount" type="int"--%>
<%--@elvariable id="latestExperiments" type="int"--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

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
                            <s:eval expression="T(java.text.NumberFormat).getInstance().format(experimentInfo.numberOfAssays)" var="fmtNumberOfAssays" />
                            <span class="button secondary no-action fixed-width-medium" data-tooltip title="Number of assays in experiment">${fmtNumberOfAssays} assays</span>
                        </div>
                        <div class="media-object-section middle hide-for-small-only ">
                            <a class="button" href="${pageContext.request.contextPath}/experiments/${experimentInfo.experimentAccession}">Results</a>
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
