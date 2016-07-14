<%--@elvariable id="preferences" type="uk.ac.ebi.atlas.web.ExperimentPageRequestPreferences"--%>
<%--@elvariable id="experimentDescription" type="java.lang.String"--%>
<%--@elvariable id="hasExtraInfo" type="boolean"--%>
<%--@elvariable id="isWidget" type="boolean"--%>
<%--@elvariable id="dataProviderURL" type="java.util.List<String>"--%>
<%--@elvariable id="dataProviderDescription" type="java.util.List<String>"--%>
<%--@elvariable id="allArrayDesigns" type="java.util.SortedSet<String>"--%>
<%--@elvariable id="pubMedIds" type="java.util.List<String>"--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${not empty param.accessKey}">
    <c:set var="accessKeyQueryString" value="?accessKey=${param.accessKey}"/>
</c:if>

<c:if test="${isWidget && not empty param.accessKey}">
    <c:set var="additionalQueryOptionsString"
           value="&geneQuery=${preferences.geneQuery.toUrlEncodedJson()}&serializedFilterFactors=${preferences.serializedFilterFactors}"/>
</c:if>

<c:if test="${isWidget && empty param.accessKey}">
    <c:set var="additionalQueryOptionsString"
           value="?geneQuery=${preferences.geneQuery.toUrlEncodedJson()}&serializedFilterFactors=${preferences.serializedFilterFactors}"/>
</c:if>

<c:set var="experimentURL" value="${applicationProperties.buildServerURL(pageContext.request)}/experiments/${experimentAccession}${accessKeyQueryString}${additionalQueryOptionsString}"/>


<div id="experimentDescription">
    <a id="goto-experiment" class="thick-link" href="${experimentURL}">${experimentDescription}</a>
    <c:if test="${hasExtraInfo}">
        <a id="extra-info"
           href="${applicationProperties.buildServerURL(pageContext.request)}/external-resources/${experimentAccession}/extra-info.png">
            <img alt="more information"
                 src="${applicationProperties.buildServerURL(pageContext.request)}/resources/images/overview_button.png">
        </a>
    </c:if>
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
                        <a class="pubmed-genequery" style="cursor: pointer" data-pubmed-id="${pubMedId}" title="Filter by text-mined genes/proteins in reference publication">(Filter by genes in paper)</a>
                        &nbsp;&nbsp;&nbsp;
                    </span>
        </c:forEach>
    </div>
</c:if>
<c:if test="${not empty dataProviderURL and not empty dataProviderDescription}">
    <div id="dataProvider">Raw Data Provider:
        <c:forEach var="dataProvider" items="${dataProviderURL}" varStatus="i">
            <a id="goto-dataprovider" class="thick-link" title="Experiment Data Provider"
        href="${dataProvider}">${dataProviderDescription.get(i.index)}</a>
        </c:forEach>
    </div>
</c:if>
<c:if test="${not empty alternativeViews}">
    <div id="alternativeViews">See also:
        <c:forEach var="alternativeViewAccession" items="${alternativeViews}">
            <a id="goto-alternativeView" class="thick-link" title="Alternative view"
               href="${applicationProperties.buildServerURL(pageContext.request)}/experiments/${alternativeViewAccession}">
                Alternative view
            </a>

        </c:forEach>
    </div>
</c:if>



<script>
    var europepmcUrl = "<spring:eval expression="@configuration['europepmc.base.url']"/>" + "webservices/rest/MED/%pubmedId%/textMinedTerms/GENE_PROTEIN/1/json";
</script>

<script src="${pageContext.request.contextPath}/resources/js/pubmedMinedBioentitiesModule.js"></script>
<script>
    (function ($, pubmedMinedBioentitiesModule) {
        $(document).ready(function () {

            var $pubmedGeneQueries = $('.pubmed-genequery');

            $pubmedGeneQueries.tooltip({
                tooltipClass: "gxaHelpTooltip"
            });

            $pubmedGeneQueries.click(function (event) {
                var pubmedId = $(event.target).attr("data-pubmed-id");

                pubmedMinedBioentitiesModule.fetchPubmedMinedBioentities(pubmedId, function (err, bioentities) {

                    if (err) {
                        throw new Error("Error fetching pubmed mined bioentities for id " + pubmedId + ": " + err.message);
                    }

                    if (!bioentities || bioentities.length == 0) {
                        alert("No text-mined genes/proteins available in Europe PubMed Central for PMID " + pubmedId);
                        console.warn("No pubmed mined bioentities for id " + pubmedId);
                        return;
                    }

                    function replaceGeneQueryWithBioentities(url, bioentities) {
                        var newGeneQuery = bioentities.join("%09");

                        if (url.indexOf("geneQuery") > -1) {
                            return url.replace(/geneQuery=[^&]*/, "geneQuery="+newGeneQuery);
                        }

                        return url + (url.indexOf("?") > -1 ? "&" : "?") + "geneQuery="+newGeneQuery;
                    }

                    var experimentUrlForPubMedBioentities = replaceGeneQueryWithBioentities(document.URL, bioentities);
                    window.open(experimentUrlForPubMedBioentities, '_blank');
                });

            });

        });
    })(jQuery, pubmedMinedBioentitiesModule);
</script>

