<%--@elvariable id="experimentDescription" type="String"--%>
<%--@elvariable id="experimentAccession" type="String"--%>
<%--@elvariable id="hasExtraInfo" type="boolean"--%>
<%--@elvariable id="dataProviderURL" type="List<String>"--%>
<%--@elvariable id="dataProviderDescription" type="List<String>"--%>
<%--@elvariable id="allArrayDesigns" type="SortedSet<String>"--%>
<%--@elvariable id="pubMedIds" type="List<String>"--%>
<%--@elvariable id="species" type="uk.ac.ebi.atlas.species.Species"--%>
<%--@elvariable id="type" type="uk.ac.ebi.atlas.model.ExperimentType"--%>
<%--@elvariable id="alternativeViews" type="List<String>"--%>
<%--@elvariable id="alternativeViewDescriptions" type="List<String>"--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div id="experimentDescription" class="row expanded">
    <div class="small-12 columns">
        <h3 id="goto-experiment">
            ${experimentDescription}
        </h3>
        <h5>${type}</h5>

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

        <c:if test="${not empty arrayDesignAccessions and not empty arrayDesignNames}">
        <div id="experimentArrayDesigns">Array Design(s):
            <c:forEach items="${arrayDesignAccessions}" var="arrayDesignAccession" varStatus="status">
                <a class="array-design" id="${arrayDesignAccession}" title="View array design in ArrayExpress"
                   href="https://www.ebi.ac.uk/arrayexpress/arrays/${arrayDesignAccession}"
                   target='_blank'>${arrayDesignNames.get(status.index)}</a>
            </c:forEach>
        </div>
        </c:if>

        <c:if test="${not empty pubMedIds and not empty pubMedIds.get(0)}">
        <div id="experimentReferences">Reference(s):
            <c:forEach var="pubMedId" items="${pubMedIds}">
                    <span><a class="pubmed-id" href="https://europepmc.org/abstract/MED/${pubMedId}"
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

<script src="${pageContext.request.contextPath}/resources/js/pubmedMinedBioentitiesModule.js"></script>
<script>
    (function ($, pubmedMinedBioentitiesModule) {
        $(document).ready(function () {

            var $pubmedGeneQueries = $('.pubmed-genequery');
            
            $pubmedGeneQueries.click(function (event) {
                var pubmedId = $(event.target).attr("data-pubmed-id");

                pubmedMinedBioentitiesModule.fetchPubmedMinedBioentities(pubmedId, function (err, bioentities) {

                    if (err) {
                        throw new Error("Error fetching pubmed mined bioentities for id " + pubmedId + ": " + err.message);
                    }

                    if (!bioentities || bioentities.length === 0) {
                        alert("No text-mined genes/proteins available in Europe PubMed Central for PMID " + pubmedId);
                        console.warn("No pubmed mined bioentities for id " + pubmedId);
                        return;
                    }

                    function replaceGeneQueryWithBioentities(url, bioentities) {
                        var newGeneQuery = bioentities.map(function(e){
                            return ({
                                value: e
                            });
                        });

                        if (url.indexOf("geneQuery") > -1) {
                            return url.replace(/geneQuery=[^&]*/, "geneQuery=" + JSON.stringify(newGeneQuery));
                        }

                        return url + (url.indexOf("?") > -1 ? "&" : "?") + "geneQuery=" + JSON.stringify(newGeneQuery);

                    }

                    var experimentUrlForPubMedBioentities = replaceGeneQueryWithBioentities(document.URL, bioentities);
                    window.open(encodeURI(experimentUrlForPubMedBioentities), '_blank');
                });

            });

        });
    })(jQuery, pubmedMinedBioentitiesModule);
</script>

