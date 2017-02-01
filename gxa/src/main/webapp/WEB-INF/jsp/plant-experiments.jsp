<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/baseline_plant-experiments.css">

<section>
    <h2>Browse plant experiments</h2>

    <div class="grid_4">
        <p><img src="${pageContext.request.contextPath}/resources/images/gramene_logo.png"/></p>
        <p><div class="icon icon-functional" data-icon="1"><a href="/gxa/home">Search Atlas</a></div></p>
    </div>

    <div class="grid_20" style="padding-bottom: 20px">
        <p>
           Thanks to funding from the <a href="http://www.gramene.org/">Gramene</a> project,
           Expression Atlas contains <b>${numberOfPlantExperiments}</b> <a href="/gxa/experiments?kingdom=plants">plant experiments</a>, studying e.g. <a href="/gxa/experiments?organism=Arabidopsis+thaliana">Arabidopsis</a>, <a href="/gxa/experiments?organism=Oryza+sativa">rice</a>, and <a href="/gxa/experiments?organism=Zea+mays">maize</a>.
        </p>
        <p>
           The <i>baseline</i> experiments, are either RNA-seq or proteomics, and display expression levels of gene products under 'normal' conditions (e.g. normal rice tissues).
           Each experiment is manually curated to a high standard, and RNA expression levels are calculated using the <a href="http://nunofonseca.github.io/irap/">iRAP</a> pipeline.
        </p>
        <p>
           The <i>differential</i> experiments in Atlas, containing both microarray and RNA-seq data, allows users to query which genes are up-/down-regulated
           in different experimental conditions, e.g. 'in Arabidopsis shoots, what genes are upregulated in plants treated by X?'
        </p>
    </div>


    <h3>Baseline experiments</h3>
    <div class="grid_24 species-nav" style="padding-bottom: 20px">

        <c:set var="speciesCount" value="0"/>
        <c:forEach items="${baselineExperimentAccessionsBySpecies.keySet()}" var="species">

            <c:if test="${speciesCount %3 == 0}">
            <div class="grid_24 species-nav">
            </c:if>

            <c:choose>
                <c:when test="${species == 'Arabidopsis thaliana'}">
                    <c:set var="speciesIconCode" value="B"/>
                    <c:set var="speciesColorCode" value="green" />
                </c:when>
                <c:when test="${species == 'Hordeum vulgare subsp. vulgare'}">
                    <c:set var="speciesIconCode" value="5"/>
                    <c:set var="speciesColorCode" value="green" />
                </c:when>
                <c:when test="${species == 'Hordeum vulgare'}">
                    <c:set var="speciesIconCode" value="5"/>
                    <c:set var="speciesColorCode" value="green" />
                </c:when>
                <c:when test="${species == 'Oryza sativa Indica Group'}">
                    <c:set var="speciesIconCode" value="6"/>
                    <c:set var="speciesColorCode" value="green" />
                </c:when>
                <c:when test="${species == 'Oryza sativa Japonica Group'}">
                    <c:set var="speciesIconCode" value="6"/>
                    <c:set var="speciesColorCode" value="green" />
                </c:when>
                <c:when test="${species == 'Oryza sativa'}">
                    <c:set var="speciesIconCode" value="6"/>
                    <c:set var="speciesColorCode" value="green" />
                </c:when>
                <c:when test="${species == 'Triticum aestivum'}">
                    <c:set var="speciesIconCode" value="5"/>
                    <c:set var="speciesColorCode" value="green"/>
                   </c:when>
                <c:when test="${species == 'Zea mays'}">
                    <c:set var="speciesIconCode" value="c"/>
                    <c:set var="speciesColorCode" value="green" />
                </c:when>

                <c:otherwise>
                    <c:set var="speciesIconCode" value="P"/>
                    <c:set var="speciesColorCode" value="green" />
                </c:otherwise>
            </c:choose>

            <div class="grid_8 species_item">
                <h4>${species}</h4>
                <span class="icon icon-species ${speciesColorCode}" data-icon="${speciesIconCode}"></span>
                <ul class="show_more" style="list-style:none;padding-left:0; margin-left:0;">
                    <c:set var="total" value="${fn:length(baselineExperimentAccessionsBySpecies.get(species))}"/>
                    <c:forEach items="${baselineExperimentAccessionsBySpecies.get(species)}" begin="0" var="experimentAccession">
                        <c:set var="key" value="${experimentAccession}${species}"/>
                        <li>
                            <a href="experiments/${experimentAccession}${experimentLinks.get(key)}" style="color:#337ab7; border-bottom: none;">
                            ${experimentDisplayNames.get(experimentAccession)}</a>
                        </li>
                    </c:forEach>

                    <c:if test="${total > 5}">
                        <div class="show_more_buttons">
                            <button class="show_button"> See more…</button>
                            <button class="hide_button"> Hide…</button>
                        </div>
                    </c:if>
                </ul>
            </div>

            <c:if test="${(speciesCount + 1) %3 == 0}">
                </div>
            </c:if>

            <c:set var="speciesCount" value="${speciesCount + 1}"/>
        </c:forEach>

    </div>


    <h3>Differential experiments</h3>
    <div class="grid_24 species-nav">

        <c:set var="speciesCount" value="0"/>
        <c:forEach items="${numDifferentialExperimentsBySpecies.keySet()}" var="species">

            <c:if test="${speciesCount %3 == 0}">
            <div class="grid_24 species-nav">
            </c:if>

            <c:choose>
                <c:when test="${species == 'Arabidopsis thaliana'}">
                    <c:set var="speciesIconCode" value="B"/>
                    <c:set var="speciesColorCode" value="green" />
                </c:when>
                <c:when test="${species == 'Hordeum vulgare subsp. vulgare'}">
                    <c:set var="speciesIconCode" value="5"/>
                    <c:set var="speciesColorCode" value="green" />
                </c:when>
                <c:when test="${species == 'Hordeum vulgare'}">
                    <c:set var="speciesIconCode" value="5"/>
                    <c:set var="speciesColorCode" value="green" />
                </c:when>
                <c:when test="${species == 'Oryza sativa Japonica Group'}">
                    <c:set var="speciesIconCode" value="6"/>
                    <c:set var="speciesColorCode" value="green" />
                </c:when>
                <c:when test="${species == 'Oryza sativa Japonica group'}">
                    <c:set var="speciesIconCode" value="6"/>
                    <c:set var="speciesColorCode" value="green" />
                </c:when>
                <c:when test="${species == 'Oryza sativa Indica Group'}">
                    <c:set var="speciesIconCode" value="6"/>
                    <c:set var="speciesColorCode" value="green" />
                </c:when>
                <c:when test="${species == 'Oryza sativa'}">
                    <c:set var="speciesIconCode" value="6"/>
                    <c:set var="speciesColorCode" value="green" />
                </c:when>
                <c:when test="${species == 'Zea mays'}">
                    <c:set var="speciesIconCode" value="c"/>
                    <c:set var="speciesColorCode" value="green" />
                </c:when>
                <c:when test="${species == 'Triticum aestivum'}">
                    <c:set var="speciesIconCode" value="5"/>
                    <c:set var="speciesColorCode" value="green"/>
                </c:when>
                <c:otherwise>
                    <c:set var="speciesIconCode" value="P"/>
                    <c:set var="speciesColorCode" value="green" />
                </c:otherwise>
            </c:choose>

            <div class="grid_8 species_item">
                <h4>${species}</h4>
                <span class="icon icon-species ${speciesColorCode}" data-icon="${speciesIconCode}"></span>
                <ul style="list-style:none;padding-left:0; margin-left:0;">
                    <li>
                        <a href="experiments?organism=${species}&experimentType=differential" style="color:#337ab7; border-bottom: none;">
                                ${numDifferentialExperimentsBySpecies.get(species)} experiment${numDifferentialExperimentsBySpecies.get(species) > 1 ? "s" : "" }</a>
                    </li>
                </ul>
            </div>

            <c:if test="${(speciesCount + 1) %3 == 0}">
                </div>
            </c:if>

            <c:set var="speciesCount" value="${speciesCount + 1}"/>

        </c:forEach>
    </div>
</section>

<script>
    //hide/show when there is more than 5 items in the list
    $(function() {
        var $ul = $(".species_item ul");
        $ul.find(".hide_button").hide();//temp - to add in css by default
        $ul.find("li:gt(4)").hide();//hide extra list item

        var $ulShowMore = $("ul.show_more");
        $ulShowMore.find(".show_button").click(function() {
            $(this).parent().parent().find("li:gt(4)").show();
            $(this).hide();
            $(this).parent().find(".hide_button").show();
        });
        $ulShowMore.find(".hide_button").click(function() {
            $(this).parent().parent().find("li:gt(4)").hide();
            $(this).hide();
            $(this).parent().find(".show_button").show();
        });
    });
</script>
