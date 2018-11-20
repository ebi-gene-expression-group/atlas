<%--@elvariable id="experimentDescription" type="String"--%>
<%--@elvariable id="experimentAccession" type="String"--%>
<%--@elvariable id="hasExtraInfo" type="boolean"--%>
<%--@elvariable id="dataProviderURL" type="List<String>"--%>
<%--@elvariable id="dataProviderDescription" type="List<String>"--%>
<%--@elvariable id="pubMedIds" type="List<String>"--%>
<%--@elvariable id="publications" type="List<uk.ac.ebi.atlas.model.Publication>"--%>
<%--@elvariable id="species" type="uk.ac.ebi.atlas.species.Species"--%>
<%--@elvariable id="type" type="String"--%>
<%--@elvariable id="alternativeViews" type="List<String>"--%>
<%--@elvariable id="alternativeViewDescriptions" type="List<String>"--%>
<%--@elvariable id="numberOfCells" type="Integer"--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div id="experimentDescription" class="row column expanded">
    <div class="media-object stack-for-small">
        <c:if test="${fn:startsWith(experimentAccession, 'E-EHCA-')}">
        <div class="media-object-section middle paddin-right-medium">
            <a class="clear padding-right-xlarge" href="https://preview.data.humancellatlas.org/">
                <img alt="Human Cell Atlas" style="max-width: 275px"
                     src="${pageContext.request.contextPath}/resources/images/logos/human_cell_atlas.png">
            </a>
        </div>
        </c:if>

        <div class="media-object-section middle">
            <h3 id="goto-experiment">
                ${experimentDescription}
            </h3>

            <h5>${type}</h5>
            <div>Number of cells:  <fmt:formatNumber type = "number" value="${numberOfCells}"/></div>

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

                <ul>
                    <c:forEach var="publication" items="${publications}">
                    <li>
                        <c:if test="${not empty publication.getAuthors()}">
                        <span>${publication.getAuthors()} (${publication.getPublicationYear()})</span>
                        </c:if>
                        <i>
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
                        </i>
                    </li>
                    </c:forEach>
                </ul>
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
</div>
