<div class="row expanded">
    <div class="columns small-2">
        <img src="${pageContext.request.contextPath}/resources/images/foundation/heatmap-hero.png">
    </div>

    <div class="columns small-10">
        <h3>Exploring gene expression results across species under different biological conditions</h3>
        <p>
             Expression Atlas is an open science resource that gives users a powerful way to find information about gene and protein expression across species and biological conditions such as different tissues, cell types, developmental stages and diseases among others. Expression Atlas aims to help answering questions such as ‘where is a certain gene expressed?’ or ‘how does its expression change in a disease?’.
        </p>
        <!-- SP: customise animation  http://foundation.zurb.com/sites/docs/motion-ui.html-->
        <div id="description-more" data-toggler=".is-visible" style="display:none;">
            <p>
                Expression Atlas provides gene expression results on more than 3,000 experiments (microarray and RNA-sequencing) from 40 different organisms, including metazoans and plants. Expression profiles of tissues from Human Protein Atlas, GTEx and FANTOM5, and of cancer cell lines from ENCODE, CCLE and Genentech projects can be explored in Expression Atlas. All data are manually curated, annotated to ontology terms allowing for much richer queries and re-analysed using standardised methods.
            </p>

            <p>
                Expression Atlas visualises gene expression results using heatmaps showing gene expression levels across different biological conditions. Novel analyses and visualisations include: ‘enrichment’ in each differential comparison of GO terms, Reactome, Plant Reactome pathways and InterPro domains; hierarchical clustering (by baseline expression) of most variable genes and experimental conditions; and, for a given gene-condition, distribution of baseline expression across biological replicates.
            </p>
        </div>

        <!-- Keep grouping otherwise button "is-visible" sets display:block to the less button -->
        <div class="button-group float-left">
            <a class="button" id="show-more" data-toggle="description-more show-more show-less" data-toggler=".hide" >Read more about Expression Atlas</a>
            <a class="button" id="show-less" data-toggle="description-more show-more show-less" data-toggler=".is-visible" style="display:none;">Show less about Expression Atlas</a>
        </div>
    </div>
</div>
