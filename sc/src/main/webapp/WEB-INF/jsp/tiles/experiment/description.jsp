<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<div id="experimentDescription">
    <h4>${experimentDescription}</h4>
    <h5>Experiment information</h5>
</div>
<div id="experimentOrganisms">Organism:
    <span style="font-style:italic">${species}</span>
</div>
<c:if test="${allArrayDesigns!=null}">
    <div id="experimentArrayDesigns">Array Design(s):
        <c:forEach items="${allArrayDesigns}" var="arrayDesign">
            <a class="array-design" id="${arrayDesign}" title="View array design in ArrayExpress"
               href="${applicationProperties.getArrayExpressArrayURL(arrayDesign)}"
               target='_blank'>${arrayDesign}</a>
        </c:forEach>
    </div>
</c:if>
<c:if test="${not empty pubMedIds and not empty pubMedIds.get(0)}">
    <div id="experimentReferences">Reference(s):
        <c:forEach var="pubMedId" items="${pubMedIds}">
                    <span><a class="pubmed-id" href="${applicationProperties.getPubMedURL(pubMedId)}"
                             title="View publication in PubMed" target='_blank'>${pubMedId}</a>
                        &nbsp;&nbsp;&nbsp;
                    </span>
        </c:forEach>
    </div>
</c:if>
<c:if test="${not empty dataProviderURL and not empty dataProviderDescription}">
    <div id="dataProvider">Raw Data Provider:
        <c:forEach var="dataProvider" items="${dataProviderURL}" varStatus="i">
            <a id="goto-${dataProvider}" class="thick-link" title="Experiment Data Provider"
               href="${dataProvider}">${dataProviderDescription.get(i.index)}</a>
        </c:forEach>
    </div>
</c:if>
<c:if test="${not empty alternativeViews and not empty alternativeViewDescriptions}">
    <div id="alternativeViews">See also:
        <c:forEach var="alternativeViewAccession" items="${alternativeViews}" varStatus="i">
            <a id="goto-${alternativeViewAccession}" class="thick-link" title="Alternative view"
               href="${applicationProperties.buildServerURL(pageContext.request)}/experiments/${alternativeViewAccession}">
                    ${alternativeViewDescriptions.get(i.index)}
            </a>

        </c:forEach>
    </div>
</c:if>

<c:if test="${not empty messagesAboutCells}">
    <div id="messagesAboutCells">
        <c:forEach var="message" items="${messagesAboutCells}" varStatus="i">
            <div>
                    ${message}
            </div>

        </c:forEach>
    </div>
</c:if>
<br>