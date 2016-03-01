<%--@elvariable id="geneQuery" type="uk.ac.ebi.atlas.web.GeneQuery"--%>
<%--@elvariable id="applicationProperties" type="uk.ac.ebi.atlas.web.ApplicationProperties"--%>
<%--@elvariable id="preferences" type="uk.ac.ebi.atlas.web.ExperimentPageRequestPreferences"--%>
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
                <%-- TODO: Maybe this is not needed (?) --%>
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
                "isCTTV": "${isCTTV}"
            },
            "columnHeaders": ${not empty jsonColumnHeaders ? jsonColumnHeaders : "null"},
            "nonExpressedColumnHeaders": ${not empty jsonNonExpressedColumnHeaders ? jsonNonExpressedColumnHeaders : "[]"},
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
                    "toggleButtonBrainImageTemplate": "${toggleButtonBrainImageTemplate}"
                    }
                </c:when>
                <c:otherwise>
                    null
                </c:otherwise>
            </c:choose>,
            "experiment" :
            <c:choose>
                <c:when test="${isWidget && !isMultiExperiment}">
                    <c:set var="additionalQueryOptionsString" value="?geneQuery=${geneQuery.asUrlQueryParameter()}&serializedFilterFactors=${preferences.serializedFilterFactors}"/>
                    <c:set var="experimentURL" value="/experiments/${experimentAccession}${additionalQueryOptionsString}"/>
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

