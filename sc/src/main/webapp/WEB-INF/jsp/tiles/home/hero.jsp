<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="row">
    <div class="columns small-2" data-tooltip title="This is a Gene Expression Heatmap showing high or low expression of the gene">
        <img src="${pageContext.request.contextPath}/resources/images/foundation/heatmap-hero.png">
    </div>

    <div class="columns small-10">
        <h3>Analysing single-cell gene expression under different biological conditions</h3>
        <p>
            The Single-Cell Expression Atlas provides information on single-cell gene expression patterns under different
            biological conditions.
        </p>
        <!-- SP: customise animation  http://foundation.zurb.com/sites/docs/motion-ui.html-->
        <div id="description-more" data-toggler=".is-visible" style="display:none;">
            <p>
                Expression Atlas provides information about gene and protein expression in animal and plant samples of
                different cell types, organism parts, developmental stages, diseases and other conditions. It consists
                of selected microarray and RNA-sequencing studies from ArrayExpress, which have been manually curated,
                annotated with ontology terms, checked for high quality and processed using standardised analysis
                methods. Since the last update, Atlas has grown seven-fold (1572 studies as of August 2015), and
                incorporates baseline expression profiles of tissues from Human Protein Atlas, GTEx and FANTOM5, and of
                cancer cell lines from ENCODE, CCLE and Genentech projects. Plant studies constitute a quarter of Atlas
                data. For genes of interest, the user can view baseline expression in tissues, and differential
                expression for biologically meaningful pairwise comparisons—estimated using consistent methodology
                across all of Atlas. Our first proteomics study in human tissues is now displayed alongside
                transcriptomics data in the same tissues. Novel analyses and visualisations include: ‘enrichment’ in
                each differential comparison of GO terms, Reactome, Plant Reactome pathways and InterPro domains;
                hierarchical clustering (by baseline expression) of most variable genes and experimental conditions;
                and, for a given gene-condition, distribution of baseline expression across biological replicates.
            </p>
        </div>

        <!-- Keep grouping otherwise button "is-visible" sets display:block to the less button -->
        <div class="button-group float-left">
            <a class="button" id="show-more" data-toggle="description-more show-more show-less" data-toggler=".hide" >Read more about Expression Atlas</a>
            <a class="button" id="show-less" data-toggle="description-more show-more show-less" data-toggler=".is-visible" style="display:none;">Show less about Expression Atlas</a>
        </div>
    </div>
</div>
