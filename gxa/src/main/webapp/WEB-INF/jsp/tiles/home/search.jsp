<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="row expanded margin-top-large">
    <div class="small-12 columns">
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
                                <a href='${pageContext.request.contextPath}/search?geneQuery=[{"value":"GO:0008150"}]'>GO:0019080 (viral gene expression)</a>,
                                <a href='${pageContext.request.contextPath}/search?geneQuery=[{"value":"PO:0004714"}]'>PO:0004714 (terminal flower bud)</a>
                            </div>
                        </div>
                        <div class="small-12 medium-4 large-2 columns">
                            <label>Organism</label>
                            <form:select id="organism" name="organism" path="organismPath">
                                <form:options items="${topSixByExperimentCount}"/>
                                <form:option value="${separator}" disabled="true"/>
                                <form:option value="" label="Any" selected="true"/>
                                <form:options items="${organisms}" />
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
                                <a href='${pageContext.request.contextPath}/search?conditionQuery=[{"value":"cancer"}]'>cancer</a></label>
                            </div>
                        </div>
                    </div>

                    <div class="row expanded margin-top-large">
                        <div class="small-12 columns">
                            <input id="home-search-atlas-search-button" class="button" type="submit" value="Search"/>

                            <%--<label for="file-upload" class="button">Upload file</label>--%>
                            <%--<input type="file" id="file-upload" class="show-for-sr">--%>

                            <input id="home-search-atlas-clear-button" class="secondary hollow button" type="button" value="Clear"/>
                        </div>
                    </div>
                </form>
            </div>

            <div class="tabs-panel" id="search-gene-set-enrichment" style="background-color: #e6e6e6;">
                <form action="genesetenrichment" method="get" id="home-genesetenrichment-atlas-form">
                    <div class="row expanded">
                        <div class="small-12 columns">
                            <label>Provide a set of Ensembl gene identifiers to test enrichment against differentially expressed genes by comparison</label>
                            <input class="margin-bottom-none" type="text" placeholder="Enter gene IDs…" name="query"/>
                        </div>
                    </div>

                    <div class="row expanded">
                        <div class="small-12 columns small margin-top-small">
                            Example: <a
                                href="${pageContext.request.contextPath}/genesetenrichment?query=AT1G48030%20AT1G53240%20AT2G17130%20AT2G20420%20AT2G44350%20AT2G47510%20AT3G09810%20AT3G15020%20AT3G17240%20AT3G27380%20AT3G55410%20AT3G60100%20AT4G26910%20AT4G35260%20AT4G35650%20AT4G35830%20AT5G03290%20AT5G08300%20AT5G23250%20AT5G40650%20AT5G50950%20AT5G55070%20AT5G65165%20AT5G65750%20AT5G66760">
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
                            <a href="https://www.ebi.ac.uk/~rpetry/geteam/gsa/gsa_apispec.pdf">Analyse Fisher-exact enrichment through our restful API.</a>
                        </div>
                    </div>
                </form>
            </div>

        </div>
    </div>
</div>
