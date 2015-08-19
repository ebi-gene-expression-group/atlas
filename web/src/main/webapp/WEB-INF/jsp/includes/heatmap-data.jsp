<%--@elvariable id="applicationProperties" type="uk.ac.ebi.atlas.web.ApplicationProperties"--%>
<%--@elvariable id="preferences" type="uk.ac.ebi.atlas.web.SearchRequest"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="geneQuery" value="${empty preferences ? geneQuery : preferences.geneQuery}" />
<c:set var="serverPort" value="${pageContext.request.serverPort == 80 ? '' : ':'.concat(pageContext.request.serverPort)}"/>
<c:set var="atlasHost" value="${pageContext.request.serverName == 'localhost' ? 'wwwdev.ebi.ac.uk' : pageContext.request.serverName.concat(serverPort)}"/>

<%--TODO Remove when anatomogram without brains are no longer supported https://www.pivotaltracker.com/story/show/101029574--%>
<c:set var="toggleButtonImage" value="/resources/images/male_selected.png"/>
<c:if test="${species.equals('oryza sativa') || species.equals('oryza sativa japonica group')}">
    <c:set var="toggleButtonImage" value="/resources/images/plant_switch_buttons_1.png"/>
</c:if>

<c:choose>
    <c:when test="${empty jsonProfiles}">
        {
            "error" : "No expression found for ${geneQuery.description()}"
        }
    </c:when>
    <c:otherwise>
        {
            <%--
            //TODO: extract ensembl genome launcher config parameters (ensemblDB, columnType etc.) out into separate object
            //TODO: remove enableGeneLinks parameter
            //TODO: investigate why showMaPlotButton is always true
            //TODO: break into common params, differential params, and baseline and multiexperiment params (if any)
            --%>
            "config": {
                "atlasHost": "${atlasHost}",
                "contextRoot": "${pageContext.request.contextPath}",
                "experimentAccession": "${experimentAccession}",
                "geneQuery": "${geneQuery.asUrlQueryParameter()}",
                "accessKey": "${param.accessKey}",
                "species": "${species}",
                "ensemblDB": "${ensemblDB}",
                "columnType": "${fn:toLowerCase(queryFactorName)}",
                "isExactMatch": ${not empty preferences ? preferences.exactMatch : "true"},
                "enableGeneLinks": true,
                "enableEnsemblLauncher": ${isMultiExperiment ? false : (empty enableEnsemblLauncher ? true : enableEnsemblLauncher)},
                "showMaPlotButton": true,
                "gseaPlots": ${empty gseaPlots ? "null" : gseaPlots},
                "downloadProfilesURL": "${not empty downloadURL ? downloadURL : applicationProperties.buildDownloadURL(pageContext.request)}",
                "isSingleGene": ${empty isSingleGene ? "false" : isSingleGene}
            },
            "columnHeaders": ${not empty jsonColumnHeaders ? jsonColumnHeaders : "null"},
            "multipleColumnHeaders": ${not empty jsonMultipleColumnHeaders ? jsonMultipleColumnHeaders : "null"},
            "profiles": ${not empty jsonProfiles ? jsonProfiles : "null"},
            "geneSetProfiles": ${not empty jsonGeneSetProfiles ? jsonGeneSetProfiles : "null"},
            "anatomogram" :
            <c:choose>
                <c:when test="${hasAnatomogram}">
                    {
                    "maleAnatomogramFile": "${maleAnatomogramFile}",
                    "femaleAnatomogramFile":  "${femaleAnatomogramFile}",
                    "brainAnatomogramFile": "${brainAnatomogramFile}",
                    "allSvgPathIds": ${empty allSvgPathIds ? "null" : allSvgPathIds},
                    "contextRoot": "${pageContext.request.contextPath}",
                    "toggleButtonMaleImageTemplate": "${toggleButtonMaleImageTemplate}",
                    "toggleButtonFemaleImageTemplate": "${toggleButtonFemaleImageTemplate}",
                    "toggleButtonBrainImageTemplate": "${toggleButtonBrainImageTemplate}",
                    <%--TODO Remove when anatomogram without brains are no longer supported https://www.pivotaltracker.com/story/show/101029574--%>
                    "toggleButtonImage": "${toggleButtonImage}"
                    }
                </c:when>
                <c:otherwise>
                    null
                </c:otherwise>
            </c:choose>,
            "experiment" :
            <c:choose>
                <c:when test="${isWidget && !isMultiExperiment}">
                    <c:set var="additionalQueryOptionsString"
                           value="?geneQuery=${geneQuery.asUrlQueryParameter()}&serializedFilterFactors=${preferences.serializedFilterFactors}"></c:set>
                    <c:set var="experimentURL" value="/experiments/${experimentAccession}${additionalQueryOptionsString}"></c:set>
                    {
                    "URL": "${experimentURL}",
                    "description":  "${experimentDescription}",
                    "allSpecies": "${allSpecies}"
                    }
                </c:when>
                <c:otherwise>
                    null
                </c:otherwise>
            </c:choose>
        }
    </c:otherwise>
</c:choose>

