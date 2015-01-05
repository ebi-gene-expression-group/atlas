<%--@elvariable id="applicationProperties" type="uk.ac.ebi.atlas.web.ApplicationProperties"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="geneQuery" value="${empty preferences ? geneQuery : preferences.geneQuery}" />
<c:set var="serverPort" value="${pageContext.request.serverPort == 80 ? '' : ':'.concat(pageContext.request.serverPort)}"/>
<c:set var="atlasHost" value="${pageContext.request.serverName == 'localhost' ? 'wwwdev' : pageContext.request.serverName.concat(serverPort)}"/>

<c:set var="toggleButtonImage" value="/resources/images/male_selected.png"/>
<c:if test="${species.equals('oryza sativa')}">
    <c:set var="toggleButtonImage" value="/resources/images/plant_switch_buttons_1.png"/>
</c:if>

{
    <%--
    //TODO: extract ensemlb genome launcher config parameters (ensemblDB, columnType etc.) out into separate object
    //TODO: remove enableGeneLinks parameter
    //TODO: investigate why showMaPlotButton is always true
    //TODO: break into common params, differential params, and baseline and multiexperiment params (if any)
    --%>
    "config": {
        "atlasHost": "${atlasHost}",
        "contextRoot": "${pageContext.request.contextPath}",
        "experimentAccession": "${experimentAccession}",
        "geneQuery": "${fn:replace(geneQuery, '\"', '\\\"')}",
        "accessKey": "${param.accessKey}",
        "species": "${species}",
        "ensemblDB": "${ensemblDB}",
        "columnType": "${fn:toLowerCase(queryFactorName)}",
        "isExactMatch": ${not empty preferences ? preferences.exactMatch : "true"},
        "enableGeneLinks": true,
        "enableEnsemblLauncher": ${isMultiExperiment ? false : (empty enableEnsemblLauncher ? true : enableEnsemblLauncher)},
        "showMaPlotButton": true,
        "gseaPlots": ${empty gseaPlots ? "null" : gseaPlots},
        "downloadProfilesURL": "${not empty downloadURL ? downloadURL : applicationProperties.buildDownloadURL(pageContext.request)}"
    },
    "columnHeaders": ${jsonColumnHeaders},
    "profiles": ${jsonProfiles},
    "geneSetProfiles": ${not empty jsonGeneSetProfiles ? jsonGeneSetProfiles : "null"},
    "anatomogram" :
        <c:choose>
            <c:when test="${hasAnatomogram}">
                {
                    "maleAnatomogramFile": "${maleAnatomogramFile}",
                    "femaleAnatomogramFile":  "${femaleAnatomogramFile}",
                    "allSvgPathIds": ${empty allSvgPathIds ? "null" : allSvgPathIds},
                    "contextRoot": "${pageContext.request.contextPath}",
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
                   value="?geneQuery=${preferences.geneQuery}&serializedFilterFactors=${preferences.serializedFilterFactors}"></c:set>
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