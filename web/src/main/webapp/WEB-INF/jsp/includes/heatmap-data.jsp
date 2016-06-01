<%--@elvariable id="geneQuery" type="uk.ac.ebi.atlas.web.GeneQuery"--%>
<%--@elvariable id="applicationProperties" type="uk.ac.ebi.atlas.web.ApplicationProperties"--%>
<%--@elvariable id="species" type="java.lang.String"--%>
<%--@elvariable id="jsonProfiles" type="java.lang.String"--%>
<%--@elvariable id="experimentAccession" type="java.lang.String"--%>
<%--@elvariable id="ensemblDB" type="java.lang.String"--%>
<%--@elvariable id="queryFactorName" type="java.lang.String"--%>
<%--@elvariable id="isMultiExperiment" type="boolean"--%>
<%--@elvariable id="enableEnsemblLauncher" type="boolean"--%>
<%--@elvariable id="gseaPlots" type="java.lang.String"--%>
<%--@elvariable id="downloadURL" type="java.lang.String"--%>
<%--@elvariable id="jsonColumnHeaders" type="java.lang.String"--%>
<%--@elvariable id="jsonNonExpressedColumnHeaders" type="java.lang.String"--%>
<%--@elvariable id="jsonMultipleColumnHeaders" type="java.lang.String"--%>
<%--@elvariable id="jsonGeneSetProfiles" type="java.lang.String"--%>
<%--@elvariable id="hasAnatomogram" type="boolean"--%>
<%--@elvariable id="maleAnatomogramFile" type="java.lang.String"--%>
<%--@elvariable id="femaleAnatomogramFile" type="java.lang.String"--%>
<%--@elvariable id="brainAnatomogramFile" type="java.lang.String"--%>
<%--@elvariable id="allSvgPathIds" type="java.lang.String"--%>
<%--@elvariable id="toggleButtonMaleImageTemplate" type="java.lang.String"--%>
<%--@elvariable id="toggleButtonFemaleImageTemplate" type="java.lang.String"--%>
<%--@elvariable id="toggleButtonBrainImageTemplate" type="java.lang.String"--%>
<%--@elvariable id="isWidget" type="boolean"--%>
<%--@elvariable id="experimentDescription" type="java.lang.String"--%>
<%--@elvariable id="allSpecies" type="java.lang.String"--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
                "atlasHost": "${applicationProperties.buildAtlasHostURL(pageContext.request)}",
                "contextRoot": "${pageContext.request.contextPath}",
                "experimentAccession": "${experimentAccession}",
                "geneQuery": "${geneQuery.asUrlQueryParameter()}",
                "accessKey": "${param.accessKey}",
                "species": "${species}",
                "ensemblDB": "${ensemblDB}",
                "columnType": "${fn:toLowerCase(queryFactorName)}",
                "isExactMatch": ${empty exactMatch ? "true": exactMatch},
                "enableGeneLinks": true,
                "enableEnsemblLauncher": ${isMultiExperiment ? false : (empty enableEnsemblLauncher ? true : enableEnsemblLauncher)},
                "showMaPlotButton": true,
                "gseaPlots": ${empty gseaPlots ? "null" : gseaPlots},
                "downloadProfilesURL": "${not empty downloadURL ? downloadURL : applicationProperties.buildDownloadURL(pageContext.request)}",
                "isFortLauderdale": ${empty isFortLauderdale ? "false" : isFortLauderdale}
            },
            "columnHeaders": ${not empty jsonColumnHeaders ? jsonColumnHeaders : "null"},
            "nonExpressedColumnHeaders": ${not empty jsonNonExpressedColumnHeaders ? jsonNonExpressedColumnHeaders : "[]"},
            "multipleColumnHeaders": ${not empty jsonMultipleColumnHeaders ? jsonMultipleColumnHeaders : "null"},
            "profiles": ${not empty jsonProfiles ? jsonProfiles : "null"},
            "geneSetProfiles": ${not empty jsonGeneSetProfiles ? jsonGeneSetProfiles : "null"},
            "jsonCoexpressions" :${not empty jsonCoexpressions? jsonCoexpressions : "[]"},
            "anatomogram" : ${not empty anatomogram? anatomogram : "null"},
            "experiment" : ${not empty experimentDescription? experimentDescription: "null"}
        }
    </c:otherwise>
</c:choose>

