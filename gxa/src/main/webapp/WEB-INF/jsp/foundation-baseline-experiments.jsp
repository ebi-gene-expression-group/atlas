<%--@elvariable id="experimentAccessionsBySpecies" type="com.google.common.collect.SortedSetMultimap<String, String>"--%>
<%--@elvariable id="experimentLinks" type="java.util.Map<String, Integer>"--%>
<%--@elvariable id="experimentDisplayNames" type="java.util.Map<String, String>"--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/baseline_plant-experiments.css">
<script language="javascript" type="text/javascript" src="https://code.jquery.com/jquery-2.2.1.min.js"></script>

<div class="row">
    <div class="small-12 columns">
        <h3>Browse baseline experiments</h3>

        <p>
            These datasets show baseline gene expression for many different tissues and cell types from a wide range of
            species,from human and mouse to Arabidopsis and maize. In Expression Atlas, "baseline" expression is the
            expression level ofeach gene in normal, untreated conditions. All baseline experiments are either RNA-seq
            or proteomics data. Each experiment is manuallycurated to a high standard, and RNA expression levels are
            calculated using the <a href="http://nunofonseca.github.io/irap/">iRAP</a> pipeline.
        </p>

        <div class="row small-up-1 medium-up-2 large-up-3">
        <c:forEach items="${experimentAccessionsBySpecies.keySet()}" var="species">
            <c:choose>
                <c:when test="${species == 'Anolis carolinensis'}">
                    <c:set var="speciesIconCode" value="7"/>
                    <c:set var="speciesColorCode" value="red"/>
                </c:when>
                <c:when test="${species == 'Arabidopsis thaliana'}">
                    <c:set var="speciesIconCode" value="B"/>
                    <c:set var="speciesColorCode" value="green"/>
                </c:when>
                <c:when test="${species == 'Bos taurus'}">
                    <c:set var="speciesIconCode" value="C"/>
                    <c:set var="speciesColorCode" value="red"/>
                </c:when>
                <c:when test="${species == 'Brachypodium distachyon'}">
                    <c:set var="speciesIconCode" value="P"/>
                    <c:set var="speciesColorCode" value="green"/>
                </c:when>
                <c:when test="${species == 'Caenorhabditis elegans'}">
                    <c:set var="speciesIconCode" value="W"/>
                    <c:set var="speciesColorCode" value="red"/>
                </c:when>
                <c:when test="${species == 'Danio rerio'}">
                    <c:set var="speciesIconCode" value="Z"/>
                    <c:set var="speciesColorCode" value="red"/>
                </c:when>
                <c:when test="${species == 'Equus caballus'}">
                    <c:set var="speciesIconCode" value="h"/>
                    <c:set var="speciesColorCode" value="red"/>
                </c:when>
                <c:when test="${species == 'Gallus gallus'}">
                    <c:set var="speciesIconCode" value="k"/>
                    <c:set var="speciesColorCode" value="red"/>
                </c:when>
                <c:when test="${species == 'Glycine max'}">
                    <c:set var="speciesIconCode" value="P"/>
                    <c:set var="speciesColorCode" value="green"/>
                </c:when>
                <c:when test="${species == 'Gorilla gorilla'}">
                    <c:set var="speciesIconCode" value="G"/>
                    <c:set var="speciesColorCode" value="red"/>
                </c:when>
                <c:when test="${species == 'Homo sapiens'}">
                    <c:set var="speciesIconCode" value="H"/>
                    <c:set var="speciesColorCode" value="red"/>
                </c:when>
                <c:when test="${species == 'Hordeum vulgare subsp. vulgare'}">
                    <c:set var="speciesIconCode" value="5"/>
                    <c:set var="speciesColorCode" value="green"/>
                </c:when>
                <c:when test="${species == 'Macaca mulatta'}">
                    <c:set var="speciesIconCode" value="r"/>
                    <c:set var="speciesColorCode" value="red"/>
                </c:when>
                <c:when test="${species == 'Monodelphis domestica'}">
                    <c:set var="speciesIconCode" value="9"/>
                    <c:set var="speciesColorCode" value="red"/>
                </c:when>
                <c:when test="${species == 'Mus musculus'}">
                    <c:set var="speciesIconCode" value="M"/>
                    <c:set var="speciesColorCode" value="red"/>
                </c:when>
                <c:when test="${species == 'Ovis aries'}">
                    <c:set var="speciesIconCode" value="x"/>
                    <c:set var="speciesColorCode" value="red"/>
                </c:when>
                <c:when test="${species == 'Oryctolagus cuniculus'}">
                    <c:set var="speciesIconCode" value="t"/>
                    <c:set var="speciesColorCode" value="red" />
                </c:when>
                <c:when test="${species == 'Oryza sativa Japonica Group'}">
                    <c:set var="speciesIconCode" value="6"/>
                    <c:set var="speciesColorCode" value="green"/>
                </c:when>
                <c:when test="${species == 'Pan paniscus'}">
                    <c:set var="speciesIconCode" value="i"/>
                    <c:set var="speciesColorCode" value="red"/>
                </c:when>
                <c:when test="${species == 'Pan troglodytes'}">
                    <c:set var="speciesIconCode" value="i"/>
                    <c:set var="speciesColorCode" value="red"/>
                </c:when>
                <c:when test="${species == 'Papio anubis'}">
                    <c:set var="speciesIconCode" value="8"/>
                    <c:set var="speciesColorCode" value="red"/>
                </c:when>
                <c:when test="${species == 'Pongo pygmaeus'}">
                    <c:set var="speciesIconCode" value=""/>
                    <c:set var="speciesColorCode" value="red"/>
                </c:when>
                <c:when test="${species == 'Rattus norvegicus'}">
                    <c:set var="speciesIconCode" value="R"/>
                    <c:set var="speciesColorCode" value="red"/>
                </c:when>
                <c:when test="${species == 'Schistosoma mansoni'}">
                    <c:set var="speciesIconCode" value="W"/>
                    <c:set var="speciesColorCode" value="red"/>
                </c:when>
                <c:when test="${species == 'Solanum lycopersicum'}">
                    <c:set var="speciesIconCode" value="P"/>
                    <c:set var="speciesColorCode" value="green"/>
                </c:when>
                <c:when test="${species == 'Sorghum bicolor'}">
                    <c:set var="speciesIconCode" value="P"/>
                    <c:set var="speciesColorCode" value="green"/>
                </c:when>
                <c:when test="${species == 'Tetraodon nigroviridis'}">
                    <c:set var="speciesIconCode" value="E"/>
                    <c:set var="speciesColorCode" value="red"/>
                </c:when>
                <c:when test="${species == 'Zea mays'}">
                    <c:set var="speciesIconCode" value="c"/>
                    <c:set var="speciesColorCode" value="green" />
                </c:when>
                <c:when test="${species == 'Xenopus (Silurana) tropicalis'}">
                    <c:set var="speciesIconCode" value="f"/>
                    <c:set var="speciesColorCode" value="red"/>
                </c:when>
                <c:when test="${species == 'Xenopus tropicalis'}">
                    <c:set var="speciesIconCode" value="f"/>
                    <c:set var="speciesColorCode" value="red"/>
                </c:when>
                <c:when test="${species == 'Triticum aestivum'}">
                    <c:set var="speciesIconCode" value="5"/>
                    <c:set var="speciesColorCode" value="green"/>
                </c:when>
                <c:when test="${species == 'Sorghum bicolor'}">
                    <c:set var="speciesIconCode" value="P"/>
                    <c:set var="speciesColorCode" value="green"/>
                </c:when>
                <c:when test="${species == 'Vitis vinifera'}">
                    <c:set var="speciesIconCode" value="P"/>
                    <c:set var="speciesColorCode" value="green"/>
                </c:when>
                <c:otherwise>
                    <c:set var="speciesIconCode" value="×"/>
                    <c:set var="speciesColorCode" value="grey" />
                </c:otherwise>
            </c:choose>

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
