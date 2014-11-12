<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="geneQuery" value="${empty preferences ? geneQuery : preferences.geneQuery}" />
{
    <%--
    //TODO: extract ensemlb genome launcher config parameters (ensemblDB, columnType etc.) out into separate object
    //TODO: remove enableGeneLinks parameter
    //TODO: investigate why showMaPlotButton is always true
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
        <c:if test="${empty disableTranscriptPopups || !disableTranscriptPopups}">
            "transcripts": {
                "serializedFilterFactors": "${serializedFilterFactors != null ? serializedFilterFactors : ""}",
                "queryFactorType": "${isMultiExperiment ? "ORGANISM_PART" : preferences.queryFactorType}"
            },
        </c:if>
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
            "contextRoot": "${pageContext.request.contextPath}"
        }
    </c:when>
    <c:otherwise>
        null
    </c:otherwise>
</c:choose>

}