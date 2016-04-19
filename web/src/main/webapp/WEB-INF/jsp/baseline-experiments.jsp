<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/baseline_plant-experiments.css">

<div class="grid_24" id="species-nav">

    <!-- Simple page header -->
    <div class="container">
        <div class="page-header">
            <h2>Browse baseline experiments</h2>
            <p>These datasets show baseline gene expression for many different tissues and cell types from a wide range of species,
                from human and mouse to Arabidopsis and maize. In Expression Atlas, "baseline" expression is the expression level of
                each gene in normal, untreated conditions. All baseline experiments are either RNA-seq or proteomics data. Each experiment is manually
                curated to a high standard, and RNA expression levels are calculated using the <a href="http://nunofonseca.github.io/irap/">iRAP</a> pipeline.
            </p>
        </div>
    </div>
    <!-- /Simple page header -->

    <c:forEach items="${experimentAccessionsBySpecies.keySet()}" var="species">

        <div class="grid_8 species_item">

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
                <c:when test="${species == 'Caenorhabditis elegans'}">
                    <c:set var="speciesIconCode" value="W"/>
                    <c:set var="speciesColorCode" value="red"/>
                </c:when>
                <c:when test="${species == 'Gallus gallus'}">
                    <c:set var="speciesIconCode" value="k"/>
                    <c:set var="speciesColorCode" value="red"/>
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
                    <c:set var="speciesIconCode" value="×"/>
                    <c:set var="speciesColorCode" value="green"/>
                </c:when>
                <c:when test="${species == 'Vitis vinifera'}">
                    <c:set var="speciesIconCode" value="×"/>
                    <c:set var="speciesColorCode" value="green"/>
                </c:when>
                <c:otherwise>
                    <c:set var="speciesIconCode" value="×"/>
					<c:set var="speciesColorCode" value="grey" />
                </c:otherwise>
            </c:choose>

            <h3>${species}</h3>
            <span class="icon icon-species ${speciesColorCode}" data-icon="${speciesIconCode}"></span>
            <ul class="show_more" style="list-style:none;padding-left:0; margin-left:0;">
                <c:set var="total" value="${fn:length(experimentAccessionsBySpecies.get(species))}"/>
                <c:forEach items="${experimentAccessionsBySpecies.get(species)}" begin="0" end="20" var="experimentAccession">
                    <c:set var="key" value="${experimentAccession}${species}"/>

                    <li>
                        <a href="experiments/${experimentAccession}${experimentLinks.get(key)}" style="color:#337ab7; border-bottom: none;">
                                ${experimentDisplayNames.get(experimentAccession)}</a>
                    </li>


                </c:forEach>

                <c:if test="${total > 5}">
                    <div class="show_more_buttons">
                        <button class="show_button"> See more..</button>
                        <button class="hide_button"> Hide ...</button>
                    </div>
                </c:if>

            </ul>

        </div>
    </c:forEach>

<script>
    //hide/show when there is more than 5 items in the list
    $(function() {
        $("ul").find(".hide_button").hide();//temp - to add in css by default
        $("ul").find("li:gt(4)").hide();//hide extra list item

        $("ul.show_more").find(".show_button").click(function() {
            $(this).parent().parent().find("li:gt(4)").show();
            $(this).hide();
            $(this).parent().find(".hide_button").show();
        });
        $("ul.show_more").find(".hide_button").click(function() {
            $(this).parent().parent().find("li:gt(4)").hide();
            $(this).hide();
            $(this).parent().find(".show_button").show();
        });
});
</script>
</div>
