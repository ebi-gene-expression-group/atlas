<%--@elvariable id="experimentAccessionsBySpecies" type="com.google.common.collect.SortedSetMultimap<String, String>"--%>
<%--@elvariable id="experimentLinks" type="java.util.Map<String, Integer>"--%>
<%--@elvariable id="experimentDisplayNames" type="java.util.Map<String, String>"--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/baseline_plant-experiments.css">

<div class="row">
    <div class="small-12 columns">
        <h3>Browse baseline experiments</h3>

        <p>
            These datasets show baseline gene expression for many different tissues and cell types from a wide range of
            species, from human and mouse to Arabidopsis and maize. In Expression Atlas, "baseline" expression is the
            expression level of each gene in normal, untreated conditions. All baseline experiments are either RNA-seq
            or proteomics data. Each experiment is manually curated to a high standard, and RNA expression levels are
            calculated using the <a href="https://github.com/nunofonseca/irap">iRAP</a> pipeline.
        </p>

        <div class="row small-up-1 medium-up-2 large-up-3">
        <c:forEach items="${experimentAccessionsBySpecies.keySet()}" var="species">
            <%@ include file="baseline-plants-landing-page/species-icon-selector.jsp" %>

            <div class="column column-block species_item margin-top-xxlarge">
                <h4>${species}</h4>
                <span class="icon icon-species ${speciesColorCode}" data-icon="${speciesIconCode}"></span>
                <ul class="show_more" style="list-style:none;padding-left:0; margin-left:0;">
                    <c:set var="total" value="${fn:length(experimentAccessionsBySpecies.get(species))}"/>
                    <c:forEach items="${experimentAccessionsBySpecies.get(species)}" begin="0" var="experimentAccession">
                        <c:set var="key" value="${experimentAccession}${species}"/>
                        <li>
                            <a href="${pageContext.request.contextPath}/experiments/${experimentAccession}${experimentLinks.get(key)}" style="color:#337ab7; border-bottom: none;">${experimentDisplayNames.get(experimentAccession)}</a>
                        </li>
                    </c:forEach>

                    <c:if test="${total > 5}">
                        <div class="show_more_buttons">
                            <Button class="button small show_button"> See more…</Button>
                            <Button class="button small hide_button"> Hide…</Button>
                        </div>
                    </c:if>
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
