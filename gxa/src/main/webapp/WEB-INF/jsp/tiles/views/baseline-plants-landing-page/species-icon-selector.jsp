<%--@elvariable id="species" type="String"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:choose>
    <c:when test="${species == 'Anolis carolinensis'}">
        <c:set var="speciesIconCode" value="7"/>
        <c:set var="speciesColorCode" value="blue"/>
    </c:when>
    <c:when test="${species.startsWith('Arabidopsis')}">
        <c:set var="speciesIconCode" value="B"/>
        <c:set var="speciesColorCode" value="green"/>
    </c:when>
    <c:when test="${species.startsWith('Beta vulgaris')}">
        <c:set var="speciesIconCode" value="B"/>
        <c:set var="speciesColorCode" value="green"/>
    </c:when>
    <c:when test="${species == 'Bos taurus'}">
        <c:set var="speciesIconCode" value="C"/>
        <c:set var="speciesColorCode" value="red"/>
    </c:when>
    <c:when test="${species == 'Brachypodium distachyon'}">
        <c:set var="speciesIconCode" value="%"/>
        <c:set var="speciesColorCode" value="green"/>
    </c:when>
    <c:when test="${species.startsWith('Brassica')}">
        <c:set var="speciesIconCode" value="B"/>
        <c:set var="speciesColorCode" value="green"/>
    </c:when>
    <c:when test="${species == 'Caenorhabditis elegans'}">
        <c:set var="speciesIconCode" value="W"/>
        <c:set var="speciesColorCode" value="blue"/>
    </c:when>
    <c:when test="${species == 'Chlamydomonas reinhardtii'}">
        <c:set var="speciesIconCode" value="Y"/>
        <c:set var="speciesColorCode" value="green"/>
    </c:when>
    <c:when test="${species == 'Chlorocebus sabaeus'}">
        <c:set var="speciesIconCode" value="r"/>
        <c:set var="speciesColorCode" value="red"/>
    </c:when>
    <c:when test="${species == 'Danio rerio'}">
        <c:set var="speciesIconCode" value="Z"/>
        <c:set var="speciesColorCode" value="blue"/>
    </c:when>
    <c:when test="${species == 'Drosophila melanogaster'}">
        <c:set var="speciesIconCode" value="F"/>
        <c:set var="speciesColorCode" value="blue"/>
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
        <c:set var="speciesIconCode" value="^"/>
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
    <c:when test="${species.startsWith('Hordeum vulgare')}">
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
    <c:when test="${species.startsWith('Musa acuminata')}">
        <c:set var="speciesIconCode" value="P"/>
        <c:set var="speciesColorCode" value="green"/>
    </c:when>
    <c:when test="${species == 'Oryctolagus cuniculus'}">
        <c:set var="speciesIconCode" value="t"/>
        <c:set var="speciesColorCode" value="red" />
    </c:when>
    <c:when test="${species.startsWith('Oryza sativa')}">
        <c:set var="speciesIconCode" value="6"/>
        <c:set var="speciesColorCode" value="green"/>
    </c:when>
    <c:when test="${species == 'Ovis aries'}">
        <c:set var="speciesIconCode" value="x"/>
        <c:set var="speciesColorCode" value="red"/>
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
    <c:when test="${species == 'Populus trichocarpa'}">
        <c:set var="speciesIconCode" value="P"/>
        <c:set var="speciesColorCode" value="green"/>
    </c:when>
    <c:when test="${species == 'Rattus norvegicus'}">
        <c:set var="speciesIconCode" value="R"/>
        <c:set var="speciesColorCode" value="red"/>
    </c:when>
    <c:when test="${species == 'Schistosoma mansoni'}">
        <c:set var="speciesIconCode" value="W"/>
        <c:set var="speciesColorCode" value="blue"/>
    </c:when>
    <c:when test="${species == 'Setaria italica'}">
        <c:set var="speciesIconCode" value="%"/>
        <c:set var="speciesColorCode" value="green"/>
    </c:when>
    <c:when test="${species == 'Solanum lycopersicum'}">
        <c:set var="speciesIconCode" value=")"/>
        <c:set var="speciesColorCode" value="green"/>
    </c:when>
    <c:when test="${species == 'Sorghum bicolor'}">
        <c:set var="speciesIconCode" value="P"/>
        <c:set var="speciesColorCode" value="green"/>
    </c:when>
    <c:when test="${species == 'Sus scrofa'}">
        <c:set var="speciesIconCode" value="p"/>
        <c:set var="speciesColorCode" value="red"/>
    </c:when>
    <c:when test="${species == 'Triticum aestivum'}">
        <c:set var="speciesIconCode" value="5"/>
        <c:set var="speciesColorCode" value="green"/>
    </c:when>
    <c:when test="${species == 'Vitis vinifera'}">
        <c:set var="speciesIconCode" value="O"/>
        <c:set var="speciesColorCode" value="green"/>
    </c:when>
    <c:when test="${species.startsWith('Xenopus')}">
        <c:set var="speciesIconCode" value="f"/>
        <c:set var="speciesColorCode" value="blue"/>
    </c:when>
    <c:when test="${species == 'Zea mays'}">
        <c:set var="speciesIconCode" value="c"/>
        <c:set var="speciesColorCode" value="green" />
    </c:when>
    <c:otherwise>
        <c:set var="speciesIconCode" value="❔"/>
        <c:set var="speciesColorCode" value="grey" />
    </c:otherwise>
</c:choose>
