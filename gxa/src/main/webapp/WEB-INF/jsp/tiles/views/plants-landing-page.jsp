<%--@elvariable id="numberOfPlantExperiments" type="int"--%>
<%--@elvariable id="baselineExperimentAccessionsBySpecies" type="com.google.common.collect.SortedSetMultimap<String, String>"--%>
<%--@elvariable id="numDifferentialExperimentsBySpecies" type="java.util.SortedMap<String, Integer>"--%>
<%--@elvariable id="experimentDisplayNames" type="java.util.Map<String, Integer>"--%>
<%--@elvariable id="experimentLinks" type="java.util.Map<String, Integer>"--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/baseline_plant-experiments.css">

<div class="row">
    <div class="small-12 columns">
        <h3>Browse plant experiments</h3>

        <div class="row">
            <div class="small-1 column">
                <p><img src="${pageContext.request.contextPath}/resources/images/gramene_logo.png"/></p>
            </div>

            <div class="small-11 columns">
                <p>
                    Thanks to funding from the <a href="http://www.gramene.org/">Gramene</a> project,
                    Expression Atlas contains <b>${numberOfPlantExperiments}</b>
                    <a href="${pageContext.request.contextPath}/experiments?kingdom=plants">plant experiments</a>, studying
                    e.g.
                    <a href="${pageContext.request.contextPath}/experiments?organism=Arabidopsis+thaliana">Arabidopsis</a>,
                    <a href="${pageContext.request.contextPath}/experiments?organism=Oryza+sativa">rice</a>, and
                    <a href="${pageContext.request.contextPath}/experiments?organism=Zea+mays">maize</a>.
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
</div>

<div class="row">
    <div class="small-12 columns">
        <h3>Baseline experiments</h3>

        <div class="row small-up-1 medium-up-2 large-up-3">
        <c:forEach items="${baselineExperimentAccessionsBySpecies.keySet()}" var="species">
            <c:choose>
                <c:when test="${species.startsWith('Arabidopsis')}">
                    <c:set var="speciesIconCode" value="B"/>
                </c:when>
                <c:when test="${species.startsWith('Beta vulgaris')}">
                    <c:set var="speciesIconCode" value="B"/>
                </c:when>
                <c:when test="${species == 'Brachypodium distachyon'}">
                    <c:set var="speciesIconCode" value="%"/>
                </c:when>
                <c:when test="${species.startsWith('Brassica')}">
                    <c:set var="speciesIconCode" value="B"/>
                </c:when>
                <c:when test="${species == 'Chlamydomonas reinhardtii'}">
                    <c:set var="speciesIconCode" value="Y"/>
                </c:when>
                <c:when test="${species == 'Glycine max'}">
                    <c:set var="speciesIconCode" value="^"/>
                </c:when>
                <c:when test="${species.startsWith('Hordeum vulgare')}">
                    <c:set var="speciesIconCode" value="5"/>
                </c:when>
                <c:when test="${species.startsWith('Musa acuminata')}">
                    <c:set var="speciesIconCode" value="P"/>
                </c:when>
                <c:when test="${species == 'Oryza sativa Japonica Group'}">
                    <c:set var="speciesIconCode" value="6"/>
                </c:when>
                <c:when test="${species == 'Populus trichocarpa'}">
                    <c:set var="speciesIconCode" value="P"/>
                </c:when>
                <c:when test="${species == 'Setaria italica'}">
                    <c:set var="speciesIconCode" value="%"/>
                </c:when>
                <c:when test="${species == 'Solanum lycopersicum'}">
                    <c:set var="speciesIconCode" value=")"/>
                </c:when>
                <c:when test="${species == 'Sorghum bicolor'}">
                    <c:set var="speciesIconCode" value="P"/>
                </c:when>
                <c:when test="${species == 'Sus scrofa'}">
                    <c:set var="speciesIconCode" value="p"/>
                </c:when>
                <c:when test="${species == 'Triticum aestivum'}">
                    <c:set var="speciesIconCode" value="5"/>
                </c:when>
                <c:when test="${species == 'Vitis vinifera'}">
                    <c:set var="speciesIconCode" value="O"/>
                </c:when>
                <c:when test="${species == 'Zea mays'}">
                    <c:set var="speciesIconCode" value="c"/>
                </c:when>
                <c:otherwise>
                    <c:set var="speciesIconCode" value="❔"/>
                    <c:set var="speciesColorCode" value="grey"/>
                </c:otherwise>
            </c:choose>

            <div class="column column-block species_item margin-top-xxlarge">
                <h4>${species}</h4>
                <span class="icon icon-species green" data-icon="${speciesIconCode}"></span>
                <ul class="show_more" style="list-style:none;padding-left:0; margin-left:0;">
                    <c:set var="total" value="${fn:length(baselineExperimentAccessionsBySpecies.get(species))}"/>
                    <c:forEach items="${baselineExperimentAccessionsBySpecies.get(species)}" begin="0" var="experimentAccession">
                        <c:set var="key" value="${experimentAccession}${species}"/>
                        <li>
                            <a href="${pageContext.request.contextPath}/experiments/${experimentAccession}${experimentLinks.get(key)}" style="color:#337ab7; border-bottom: none;">${experimentDisplayNames.get(experimentAccession)}</a>
                        </li>
                    </c:forEach>

                    <c:if test="${total > 5}">
                        <div class="show_more_buttons">
                            <button class="button small show_button"> See more…</button>
                            <button class="button small hide_button"> Hide…</button>
                        </div>
                    </c:if>
                </ul>
            </div>
        </c:forEach>
        </div>
    </div>
</div>

<div class="row">
    <div class="small-12 columns">
        <h3>Differential experiments</h3>

        <div class="row small-up-1 medium-up-2 large-up-3">
        <c:forEach items="${numDifferentialExperimentsBySpecies.keySet()}" var="species">
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

            <div class="column column-block species_item margin-top-xxlarge">
                <h4>${species}</h4>
                <span class="icon icon-species ${speciesColorCode}" data-icon="${speciesIconCode}"></span>
                <ul style="list-style:none;padding-left:0; margin-left:0;">
                    <li>
                        <a href="${pageContext.request.contextPath}/experiments?organism=${species}&experimentType=differential" style="color:#337ab7; border-bottom: none;">${numDifferentialExperimentsBySpecies.get(species)} experiment${numDifferentialExperimentsBySpecies.get(species) > 1 ? "s" : "" }</a>
                    </li>
                </ul>
            </div>
        </c:forEach>
        </div>
    </div>
</div>

<script>
    document.addEventListener("DOMContentLoaded", function(event) {
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
    })
</script>
