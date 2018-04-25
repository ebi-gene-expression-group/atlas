<%--@elvariable id="experimentDescription" type="String"--%>
<%--@elvariable id="experimentAccession" type="String"--%>
<%--@elvariable id="hasExtraInfo" type="boolean"--%>
<%--@elvariable id="dataProviderURL" type="List<String>"--%>
<%--@elvariable id="dataProviderDescription" type="List<String>"--%>
<%--@elvariable id="pubMedIds" type="List<String>"--%>
<%--@elvariable id="publications" type="List<uk.ac.ebi.atlas.model.Publication>"--%>
<%--@elvariable id="species" type="uk.ac.ebi.atlas.species.Species"--%>
<%--@elvariable id="type" type="uk.ac.ebi.atlas.model.ExperimentType"--%>
<%--@elvariable id="alternativeViews" type="List<String>"--%>
<%--@elvariable id="alternativeViewDescriptions" type="List<String>"--%>
<%--@elvariable id="applicationProperties" type="uk.ac.ebi.atlas.web.ApplicationProperties"--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div id="experimentDescription" class="row">
    <div class="small-12 columns">
        <h3 id="goto-experiment">
            ${experimentDescription}
        </h3>
        <h5>${type.humanDescription}</h5>

        <c:if test="${hasExtraInfo}">
            <a id="extra-info"
               href="${pageContext.request.contextPath}/external-resources/${experimentAccession}/extra-info.png">
                <img alt="more information"
                     src="${pageContext.request.contextPath}/resources/images/overview_button.png">
            </a>
        </c:if>

        <div id="experimentOrganisms">Organism:
            <span style="font-style:italic">${species}</span>
        </div>

        <c:if test="${not empty publications}">
            <div id="experimentReferences">
                <c:choose>
                    <c:when test="${publications.size() == 1}">
                        Publication:
                    </c:when>
                    <c:otherwise>
                        Publications:
                    </c:otherwise>
                </c:choose>

                <c:forEach var="publication" items="${publications}">
                    <span>
                    <c:choose>
                        <c:when test="${not empty publication.getPubmedId()}">
                            <a class="pubmed-id"
                               href="https://europepmc.org/abstract/MED/${publication.getPubmedId()}"
                               title="Read publication"
                               target='_blank'>${publication.getTitle()}</a>
                        </c:when>
                        <c:otherwise>
                            <a class="pubmed-id"
                               href="https://doi.org/${publication.getDoi()}"
                               title="Read publication"
                               target='_blank'>${publication.getTitle()}</a>
                        </c:otherwise>
                    </c:choose>
                    </span>
                </c:forEach>
            </div>
        </c:if>

        <c:if test="${not empty dataProviderURL and not empty dataProviderDescription}">
            <div id="dataProvider">Raw Data Provider:
                <c:forEach var="dataProvider" items="${dataProviderURL}" varStatus="i">
                    <a class="thick-link" title="Experiment Data Provider"
                       href="${dataProvider}">${dataProviderDescription.get(i.index)}</a>
                </c:forEach>
            </div>
        </c:if>

        <c:if test="${not empty alternativeViews and not empty alternativeViewDescriptions}">
            <div id="alternativeViews">See also:
                <c:forEach var="alternativeViewAccession" items="${alternativeViews}" varStatus="i">
                    <a class="thick-link" title="Alternative view"
                       href="${pageContext.request.contextPath}/experiments/${alternativeViewAccession}">
                            ${alternativeViewDescriptions.get(i.index)}
                    </a>

                </c:forEach>
            </div>
        </c:if>
    </div>
</div>
