<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="row">
    <div class="small-12 columns">
        <ul class="tabs" data-tabs id="search-tabs">
            <li class="tabs-title is-active"><a href="#search-atlas" aria-selected="true">Search Expression Atlas</a></li>
            <li class="tabs-title"><a href="#search-gene-set-enrichment">Gene set enrichment</a></li>
        </ul>

        <div class="tabs-content" data-tabs-content="search-tabs">
            <div class="tabs-panel is-active " id="search-atlas" style="background-color: #e6e6e6;">
                <!-- Grid Example -->
                <form method="get" action="search" id="home-search-atlas-form">
                    <div class="row">
                        <div class="small-12 columns">
                            <label>Gene, tissue or biological condition</label>
                            <input id="home-search-atlas-input" type="text" placeholder="Enter your search" name="query"/>
                        </div>
                    </div>

                    <div class="row">
                        <div class="small-12 columns small">
                            Examples: <a href='${pageContext.request.contextPath}/query?geneQuery=[{"value":"REG1B"}]'>REG1B</a>,
                            <a href='${pageContext.request.contextPath}/query?geneQuery=[{"value":"zinc finger"}]'>zinc finger</a>,
                            <a href='${pageContext.request.contextPath}/query?conditionQuery=[{"value":"lung"}]'>lung</a>,
                            <a href='${pageContext.request.contextPath}/query?conditionQuery=[{"value":"leaf"}]'>leaf</a>,
                            <a href='${pageContext.request.contextPath}/query?conditionQuery=[{"value":"valproic acid"}]'>valproic acid</a>,
                            <a href='${pageContext.request.contextPath}/query?conditionQuery=[{"value":"cancer"}]'>cancer</a></label>
                        </div>
                    </div>

                    <div class="row margin-top-large">
                        <div class="small-12 columns">
                            <input id="home-search-atlas-search-button" class="button" type="submit" value="Search"/>

                            <label for="file-upload" class="button">Upload file</label>
                            <input type="file" id="file-upload" class="show-for-sr">

                            <input id="home-search-atlas-clear-button" class="secondary hollow button" type="button" value="Clear"/>
                        </div>
                    </div>
                </form>
            </div>

            <div class="tabs-panel" id="search-gene-set-enrichment" style="background-color: #e6e6e6;">
                <p>
                    A simple RESTful API to analyse (Fisher-exact) enrichment of
                    user-provided set of Ensembl gene identifiers against
                    differentially expressed genes in each Atlas comparison in an organism.
                </p>
                <form>
                    <div class="secondary ">
                        <div class="row">
                            <div class="small-12 columns">
                                <label>List of gene identifiers</label>
                                <input type="text" placeholder="Add genes" />
                            </div>
                        </div>

                        <div class="row">
                            <div class="small-12 columns small">
                                Example: <a href="http://www.ebi.ac.uk/fg/gsa/api/tsv/getOverlappingComparisons/arabidopsis_thaliana/AT1G48030%20AT1G53240%20AT2G17130%20AT2G20420%20AT2G44350%20AT2G47510%20AT3G09810%20AT3G15020%20AT3G17240%20AT3G27380%20AT3G55410%20AT3G60100%20AT4G26910%20AT4G35260%20AT4G35650%20AT4G35830%20AT5G03290%20AT5G08300%20AT5G23250%20AT5G40650%20AT5G50950%20AT5G55070%20AT5G65165%20AT5G65750%20AT5G66760">
                                AT1G48030 AT1G53240 AT2G17130 AT2G20420 AT2G44350 AT2G47510 AT3G09810 AT3G15020 AT3G17240 AT3G27380 AT3G55410 AT3G60100 AT4G26910 AT4G35260 AT4G35650 AT4G35830 AT5G03290 AT5G08300 AT5G23250</a>
                            </div>
                        </div>

                        <div class="row margin-top-large">
                            <div class="small-12 columns">
                                <a href="#" class="button">Search</a> <a href="#" class="secondary hollow button">Clear</a>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
