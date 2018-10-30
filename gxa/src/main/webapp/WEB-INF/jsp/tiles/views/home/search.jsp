<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/js/lib/jquery-ui-1.12.1.custom/jquery-ui.min.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/js/lib/jquery-json-tag-editor/jquery.json-tag-editor.foundation.css" media="screen">

<ul class="tabs" data-tabs id="search-tabs">
    <li class="tabs-title is-active"><a href="#search-atlas" aria-selected="true">Search</a></li>
    <li class="tabs-title"><a href="#search-gene-set-enrichment">Gene set enrichment</a></li>
</ul>

<div class="tabs-content" data-tabs-content="search-tabs">

    <div class="tabs-panel is-active " id="search-atlas" style="background-color: #e6e6e6;">
        <form method="get" action="${pageContext.request.contextPath}/search" id="home-search-atlas-form">
            <div class="row expanded">
                <div class="small-12 medium-4 large-5 columns">
                    <div class="row column">
                        <label>Gene / Gene properties</label>
                        <input id="home-search-gene-query-input" type="text" placeholder="Enter gene query…" name="geneQuery"/>
                    </div>
                    <div class="row column small margin-top-small">
                        Examples: <a href='${pageContext.request.contextPath}/search?geneQuery=[{"value":"REG1B"}]'>REG1B</a>,
                        <a href='${pageContext.request.contextPath}/search?geneQuery=[{"value":"zinc finger"}]'>zinc finger</a>,
                        <a href='${pageContext.request.contextPath}/search?geneQuery=[{"value":"O14777", "category":"uniprot"}]'>O14777 (UniProt)</a>,
                        <a href='${pageContext.request.contextPath}/search?geneQuery=[{"value":"GO:0010468", "category":"go"}]'>GO:0010468 (regulation of gene expression)</a>
                    </div>
                </div>
                <div class="small-12 medium-4 large-2 columns">
                    <label>Species</label>
                    <form:select id="species" name="species" path="speciesPath">
                        <form:options items="${topSixByExperimentCount}"/>
                        <form:option value="${separator}" disabled="true"/>
                        <form:option value="" label="Any" selected="true"/>
                        <form:options items="${species}" />
                    </form:select>
                </div>
                <div id="sample-properties-section" class="small-12 medium-4 large-5 columns">
                    <div class="row column">
                        <label>Biological conditions</label>
                        <input id="home-search-condition-query-input" type="text" placeholder="Enter condition query…" name="conditionQuery" />
                    </div>
                    <div class="row column small margin-top-small">
                        Examples: <a href='${pageContext.request.contextPath}/search?conditionQuery=[{"value":"lung"}]'>lung</a>,
                        <a href='${pageContext.request.contextPath}/search?conditionQuery=[{"value":"leaf"}]'>leaf</a>,
                        <a href='${pageContext.request.contextPath}/search?conditionQuery=[{"value":"valproic acid"}]'>valproic acid</a>,
                        <a href='${pageContext.request.contextPath}/search?conditionQuery=[{"value":"cancer"}]'>cancer</a>
                    </div>
                </div>
            </div>

            <div class="row expanded margin-top-large">
                <div class="small-12 columns">
                    <input id="home-search-atlas-search-button" class="button" type="submit" value="Search"/>

                    <input id="home-search-atlas-clear-button" class="secondary hollow button" type="button" value="Clear"/>
                </div>
            </div>
        </form>
    </div>

    <div class="tabs-panel" id="search-gene-set-enrichment" style="background-color: #e6e6e6;">
        <form method="get" action="${pageContext.request.contextPath}/genesetenrichment" id="home-genesetenrichment-atlas-form">
            <div class="row expanded">
                <div class="small-12 columns">
                    <label>Provide a set of Ensembl gene identifiers to test enrichment against differentially expressed genes by comparison</label>
                    <input class="margin-bottom-none" type="text" placeholder="Enter gene IDs…" name="query"/>
                </div>
            </div>

            <div class="row expanded">
                <div class="small-12 columns small margin-top-small">
                    Example: <a
                        href="${pageContext.request.contextPath}/genesetenrichment?query=AT1G48030 AT1G53240 AT2G17130 AT2G20420 AT2G44350 AT2G47510 AT3G09810 AT3G15020 AT3G17240 AT3G27380 AT3G55410 AT3G60100 AT4G26910 AT4G35260 AT4G35650 AT4G35830 AT5G03290 AT5G08300 AT5G23250">
                    AT1G48030 AT1G53240 AT2G17130 AT2G20420 AT2G44350 AT2G47510 AT3G09810 AT3G15020 AT3G17240 AT3G27380 AT3G55410 AT3G60100 AT4G26910 AT4G35260 AT4G35650 AT4G35830 AT5G03290 AT5G08300 AT5G23250</a>
                </div>
            </div>

            <div class="row expanded">
                <div class="small-12 columns margin-top-large">
                    <input id="genesetenrichment-atlas-search-button" class="button" type="submit" value="Search"/>
                </div>
            </div>

            <div class="row expanded">
                <div class="small-12 columns">
                    <a href="${pageContext.request.contextPath}/resources/gsa_apispec.pdf">Gene Set Enrichment API documentation</a>
                </div>
            </div>
        </form>
    </div>
</div>

<script defer src="https://cdnjs.cloudflare.com/ajax/libs/URI.js/1.17.0/URI.min.js"></script>

<!-- JSON Tag Editor -->
<script defer src="${pageContext.request.contextPath}/resources/js/lib/jquery-json-tag-editor/jquery.caret.min.js"></script>
<script defer src="${pageContext.request.contextPath}/resources/js/lib/jquery-json-tag-editor/jquery.json-tag-editor.min.js"></script>
<script defer src="${pageContext.request.contextPath}/resources/js/geneQueryTagEditorModule.js"></script>

<!-- Condition AUTOCOMPLETE -->
<script defer language="JavaScript" type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/arrayexpress-autocomplete/jquery.caret-range-1.0.js"></script>
<script defer language="JavaScript" type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/arrayexpress-autocomplete/jquery.array-express.autocomplete-1.1.0.150319.js"></script>
<script defer language="JavaScript" type="text/javascript" src="${pageContext.request.contextPath}/resources/js/conditionAutocompleteModule.js"></script>

<script defer src="${pageContext.request.contextPath}/resources/js/lib/jquery-ui-1.12.1.custom/jquery-ui.min.js"></script>

<script>
    document.addEventListener("DOMContentLoaded", function(event) {
        var contextPath = '${pageContext.request.contextPath}/';
        var geneQueryPlaceHolder = 'Enter gene query…';

        geneQueryTagEditorModule.init('#local-searchbox', '', function(){}, geneQueryPlaceHolder, contextPath);
        geneQueryTagEditorModule.init('#home-search-gene-query-input', '', function(){}, geneQueryPlaceHolder, contextPath);
        conditionAutocompleteModule.init('#home-search-condition-query-input', function(){});

        $('#home-search-atlas-clear-button').on('click' , function () {
            // Remove all tags
            var $atlasSearchInput = $('#home-search-gene-query-input'),
                atlasSearchTags = $atlasSearchInput.jsonTagEditor('getTags')[0].tags;
            atlasSearchTags.forEach(function (searchTag) {
                $atlasSearchInput.jsonTagEditor('removeTag', searchTag.value);
            });

            var $conditionQuery = $('#home-search-condition-query-input'),
                conditionQueryTags = $conditionQuery.jsonTagEditor('getTags')[0].tags;
            conditionQueryTags.forEach(function (searchTag) {
                $conditionQuery.jsonTagEditor('removeTag', searchTag.value);
            });
        });

        $('#home-search-atlas-form').submit(function(event) {
            var $atlasSearchInput = $('#home-search-gene-query-input'),
                atlasSearchTags = $atlasSearchInput.jsonTagEditor('getTags')[0].tags;
            $atlasSearchInput.val(JSON.stringify(atlasSearchTags));

            var $conditionQuery = $('#home-search-condition-query-input'),
                conditionQueryTags = $conditionQuery.jsonTagEditor('getTags')[0].tags;
            $conditionQuery.val(JSON.stringify(conditionQueryTags));
        });
    });
</script>