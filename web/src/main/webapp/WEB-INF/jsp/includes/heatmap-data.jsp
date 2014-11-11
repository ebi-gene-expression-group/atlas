{
    <%--
    //TODO: extract ensemlb genome launcher config parameters (ensemblDB, columnType etc.) out into separate object
    //TODO: remove enableGeneLinks parameter
    //TODO: investigate why showMaPlotButton is always true
    --%>
    config: {
        atlasHost: '${atlasHost}',
        contextRoot: '${pageContext.request.contextPath}',
        experimentAccession: '${experimentAccession}',
        geneQuery: '${empty preferences ? geneQuery : preferences.geneQuery}',
        accessKey: '${param.accessKey}',
        species: '${species}',
        ensemblDB: '${ensemblDB}',
        columnType: '${fn:toLowerCase(queryFactorName)}',
        isExactMatch: ${not empty preferences ? preferences.exactMatch : 'true'},
        enableGeneLinks: true,
        enableEnsemblLauncher: ${isMultiExperiment ? false : (empty enableEnsemblLauncher ? true : enableEnsemblLauncher)},
        showMaPlotButton: true,
        gseaPlots: ${empty gseaPlots ? 'undefined' : gseaPlots},
        <c:if test="${empty disableTranscriptPopups || !disableTranscriptPopups}">
            transcripts: {
            serializedFilterFactors: '${serializedFilterFactors != null ? serializedFilterFactors : ""}',
            queryFactorType: '${isMultiExperiment ? 'ORGANISM_PART' : preferences.queryFactorType}'
            },
        </c:if>
        downloadProfilesURL: '${applicationProperties.buildDownloadURL(pageContext.request)}'
    },
    columnHeaders: ${jsonColumnHeaders},
    profiles: ${jsonProfiles},
    geneSetProfiles: ${not empty jsonGeneSetProfiles ? jsonGeneSetProfiles : 'undefined'}
}