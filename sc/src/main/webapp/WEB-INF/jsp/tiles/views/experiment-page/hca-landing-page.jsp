<%--
  Created by IntelliJ IDEA.
  User: lingyun
  Date: 25/10/2018
  Time: 09:14
  To change this template use File | Settings | File Templates.
--%>
<%--@elvariable id="endpoint" type="String"--%>
<%--@elvariable id="geneQueryTerm" type="String"--%>
<%--@elvariable id="geneQueryCategory" type="String"--%>
<%--@elvariable id="species" type="String"--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>


        <div class="row column margin-bottom-large expanded">
            <div class="media-object">
                <div class="media-object-section top hide-for-small-only">
            <div class="thumbnail">
                <img alt="Human Cell Atlas" style="max-width: 275px"
                        src="${pageContext.request.contextPath}/resources/images/logos/human_cell_atlas.png"/>
            </div>
                </div>
            <div class="media-object-section top">
                <h3>Human Cell Atlas Experiments</h3>
                <p>
                    Thanks to funding from the <a href="https://www.humancellatlas.org/">HCA</a> project,
                    Expression Atlas contains <b>791</b>
                    <a href="/gxa/experiments?kingdom=plants">plant experiments</a>, studying
                    e.g.
                    <a href="/gxa/experiments?organism=Arabidopsis+thaliana">Arabidopsis</a>,
                    <a href="/gxa/experiments?organism=Oryza+sativa">rice</a>, and
                    <a href="/gxa/experiments?organism=Zea+mays">maize</a>.
                </p>
                <p>
                    The <i>baseline</i> experiments, are either RNA-seq or proteomics, and display expression levels of
                    gene products under 'normal' conditions (e.g. normal rice tissues). Each experiment is manually curated
                    to a high standard, and RNA expression levels are calculated using the
                    <a href="https://github.com/nunofonseca/irap">iRAP</a> pipeline.
                </p>
                <p>
                    The <i>differential</i> experiments in Atlas, containing both microarray and RNA-seq data, allows users
                    to query which genes are up-/down-regulatedin different experimental conditions, e.g. 'in Arabidopsis
                    shoots, what genes are upregulated in plants treated by X?'
                </p>
            </div>
            </div>
        </div>



<div class="row column expanded">
    <div class="row column expanded">
        <h3>Search results</h3>
    </div>

    <div class="row column expanded margin-bottom-large">
        <div id="search-form"></div>
    </div>

    <div id="search-results-list"></div>
</div>

<script defer src="${pageContext.request.contextPath}/resources/js-bundles/geneSearch.bundle.js"></script>
<script defer src="${pageContext.request.contextPath}/resources/js-bundles/geneSearchForm.bundle.js"></script>
<script>
    document.addEventListener("DOMContentLoaded", function(event) {
        geneSearchForm.render({
            atlasUrl: '${pageContext.request.contextPath}/',
            wrapperClassName: 'row expanded',
            actionEndpoint: 'search',
            autocompleteClassName: 'small-12 medium-8 columns',
            suggesterEndpoint: 'json/suggestions/gene_ids',
            enableSpeciesSelect: true,
            speciesEndpoint: 'json/suggestions/species',
            speciesSelectClassName: 'small-12 medium-4 columns',
            defaultSpecies: '${species}',
            defaultValue: {
                term: '${geneQueryTerm}',
                category: '${geneQueryCategory}',
            }
        }, 'search-form');

        geneSearch.render({
            atlasUrl: '${pageContext.request.contextPath}/',
            resource: '${endpoint}',
            noResultsMessage: '${geneQueryTerm} is not expressed in any experiment. Try searching for a different gene.',
            resultsMessage: '${geneQueryTerm} is expressed in:'
        }, 'search-results-list');
    });
</script>
