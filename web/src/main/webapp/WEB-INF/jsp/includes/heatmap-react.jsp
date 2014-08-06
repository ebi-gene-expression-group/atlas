<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<%-- console polyfill to make the unminified React work with IE8/9 --%>
<!--[if lt IE 10]>
<script type="text/javascript"> if (!window.console) console = {log: function() {}, warn: function() {}}; </script>
<![endif]-->

<%-- polyfills to make React work with IE8 --%>
<!--[if lt IE 9]>
<script src="//cdnjs.cloudflare.com/ajax/libs/es5-shim/4.0.1/es5-shim.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/es5-shim/4.0.1/es5-sham.min.js"></script>
<![endif]-->

<script src="//cdnjs.cloudflare.com/ajax/libs/react/0.11.1/react.js"></script>


<script src="${pageContext.request.contextPath}/resources/js/transcriptPopupModule.js"></script>
<script language="JavaScript" type="text/javascript"
        src="${pageContext.request.contextPath}/resources/js/highlight.js"></script>
<script language="JavaScript" type="text/javascript"
        src="${pageContext.request.contextPath}/resources/js/genePropertiesTooltipModule.js"></script>
<script language="JavaScript" type="text/javascript"
        src="${pageContext.request.contextPath}/resources/js/contrastInfoTooltipModule.js"></script>
<script language="JavaScript" type="text/javascript"
        src="${pageContext.request.contextPath}/resources/js/factorInfoTooltipModule.js"></script>

<script src="${pageContext.request.contextPath}/resources/js/EventEmitter-4.2.7.min.js" type="text/javascript"></script>

<script src="${pageContext.request.contextPath}/resources/jsx/heatmap.js" type="text/javascript"></script>

<script src="${pageContext.request.contextPath}/resources/js/jquery.toolbar.js"></script>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery.toolbars.css" />

<script src="${pageContext.request.contextPath}/resources/js/jquery.stickytableheaders.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery.sticky.js"></script>


<div id="genenametooltip-content" style="display: none"></div>

<section id="contrastInfo" style="display:none">
    <div id="contrastExperimentDescription" style="font-weight: bold; color:blue; text-align: center"></div>
    <div id="contrastDescription" style="text-align: center"></div>
    <table class='table-grid' style="padding: 0px; margin: 0px;">
        <thead>
        <tr>
            <th class='header-cell'>
                Property
            </th>
            <th class='header-cell'>
                Test value
            </th>
            <th class='header-cell'>
                Reference value
            </th>
        </tr>
        </thead>
        <tbody></tbody>
    </table>
</section>

<section id="factorInfo" style="display:none">
    <div id="factorDescription" style="text-align: center"></div>
    <table class='table-grid' style="padding: 0px; margin: 0px;">
        <thead>
        <tr>
            <th class='header-cell'>
                Property
            </th>
            <th class='header-cell'>
                Value
            </th>
        </tr>
        </thead>
        <tbody></tbody>
    </table>
</section>

<c:set var="serverPort" value="${pageContext.request.serverPort == 80 ? '' : ':'.concat(pageContext.request.serverPort)}"/>
<c:set var="atlasHost" value="${pageContext.request.serverName == 'localhost' ? 'wwwdev' : pageContext.request.serverName.concat(serverPort)}"/>

<script type="text/javascript">
    var heatmapData = (function (genePropertiesTooltipModule) {

        var config = (function (genePropertiesTooltipModule) {
            return {
                atlasHost: '${atlasHost}',
                contextRoot: '${pageContext.request.contextPath}',
                experimentAccession: '${experimentAccession}',
                accessKey: '${param.accessKey}',
                species: '${species}',
                ensemblDB: '${ensemblDB}',
                columnType: '${fn:toLowerCase(queryFactorName)}',
                queryFactorType: '${preferences.queryFactorType}',
                isExactMatch: ${preferences.exactMatch},
                enableGeneLinks: true,
                enableEnsemblLauncher: ${empty enableEnsemblLauncher ? true : enableEnsemblLauncher},
                showMaPlotButton: true,
                gseaPlots: ${empty gseaPlots ? 'undefined' : gseaPlots},
                selectedFilterFactorsJson: ${selectedFilterFactorsJson != null ? selectedFilterFactorsJson : "''"},
                toolTipHighlightedWords: genePropertiesTooltipModule.splitIntoWords('${preferences.geneQuery}'),
                downloadProfilesURL: '${applicationProperties.buildDownloadURL(pageContext.request)}'
            };
        })(genePropertiesTooltipModule);

        var columnHeaders = ${jsonColumnHeaders};

        var geneSetProfiles = ${not empty jsonGeneSetProfiles ? jsonGeneSetProfiles : 'undefined'};

        var profiles = ${jsonProfiles};

        return {
            config: config,
            columnHeaders: columnHeaders,
            geneSetProfiles: geneSetProfiles,
            profiles: profiles
        }
    })(genePropertiesTooltipModule);
</script>

<script type="text/javascript">
    (function ($, React, heatmapModule, heatmapConfig, columnHeaders, profiles, geneSetProfiles) {

        $(document).ready(function () {
            // call this inside ready() so all scripts load first in IE8

            var build = ${isDifferential ? 'heatmapModule.buildDifferential': 'heatmapModule.buildBaseline'};
            var heatmap = build(heatmapConfig, $('#displayLevels'));

            React.renderComponent(heatmap.Heatmap({columnHeaders: columnHeaders, profiles: profiles, geneSetProfiles: geneSetProfiles}),
                    document.getElementById('heatmap-react')
            );

            if (heatmap.EnsemblLauncher) {
                React.renderComponent(heatmap.EnsemblLauncher(),
                        document.getElementById('${hasAnatomogram ? "anatomogram-ensembl-launcher" : "ensembl-launcher"}')
                );
            }
        });

    })(jQuery, React, heatmapModule, heatmapData.config,
            heatmapData.columnHeaders, heatmapData.profiles, heatmapData.geneSetProfiles);
</script>

