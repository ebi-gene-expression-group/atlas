<%--
  ~ Copyright 2008-2012 Microarray Informatics Team, EMBL-European Bioinformatics Institute
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~ http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software
  ~ distributed under the License is distributed on an "AS IS" BASIS,
  ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  ~ See the License for the specific language governing permissions and
  ~ limitations under the License.
  ~
  ~
  ~ For further details of the Gene Expression Atlas project, including source code,
  ~ downloads and documentation, please see:
  ~
  ~ http://gxa.github.com/gxa
  --%>
<!-- blue icon 5bc0de-->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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

    <c:forEach items="${experimentAccessionsBySpecies.keySet()}" var="specie">

        <div class="grid_8 specie_item">

            <c:choose>
                <c:when test="${specie == 'Anolis carolinensis'}">
                    <c:set var="speciesIconCode" value="7"/>
                    <c:set var="speciesColorCode" value="blue"/>
                </c:when>
                <c:when test="${specie == 'Arabidopsis thaliana'}">
                    <c:set var="speciesIconCode" value="B"/>
                    <c:set var="speciesColorCode" value="green"/>
                </c:when>
                <c:when test="${specie == 'Bos taurus'}">
                    <c:set var="speciesIconCode" value="C"/>
                    <c:set var="speciesColorCode" value="red"/>
                </c:when>
                <c:when test="${specie == 'Caenorhabditis elegans'}">
                    <c:set var="speciesIconCode" value="W"/>
                    <c:set var="speciesColorCode" value="blue"/>
                </c:when>
                <c:when test="${specie == 'Gallus gallus'}">
                    <c:set var="speciesIconCode" value="k"/>
                    <c:set var="speciesColorCode" value="red"/>
                </c:when>
                <c:when test="${specie == 'Gorilla gorilla'}">
                    <c:set var="speciesIconCode" value="G"/>
                    <c:set var="speciesColorCode" value="red"/>
                </c:when>
                <c:when test="${specie == 'Homo sapiens'}">
                    <c:set var="speciesIconCode" value="H"/>
                    <c:set var="speciesColorCode" value="red"/>
                </c:when>
                <c:when test="${specie == 'Hordeum vulgare subsp. vulgare'}">
                    <c:set var="speciesIconCode" value="5"/>
                    <c:set var="speciesColorCode" value="green"/>
                </c:when>
                <c:when test="${specie == 'Macaca mulatta'}">
                    <c:set var="speciesIconCode" value="r"/>
                    <c:set var="speciesColorCode" value="red"/>
                </c:when>
                <c:when test="${specie == 'Monodelphis domestica'}">
                    <c:set var="speciesIconCode" value="9"/>
                    <c:set var="speciesColorCode" value="red"/>
                </c:when>
                <c:when test="${specie == 'Mus musculus'}">
                    <c:set var="speciesIconCode" value="M"/>
                    <c:set var="speciesColorCode" value="red"/>
                </c:when>
                <c:when test="${specie == 'Mus musculus'}">
                    <c:set var="speciesIconCode" value="M"/>
                    <c:set var="speciesColorCode" value="red"/>
                </c:when>
                <c:when test="${specie == 'Oryctolagus cuniculus'}">
                    <c:set var="speciesIconCode" value="t"/>
                </c:when>
                <c:when test="${specie == 'Oryza sativa Japonica Group'}">
                    <c:set var="speciesIconCode" value="6"/>
                    <c:set var="speciesColorCode" value="green"/>
                </c:when>
                <c:when test="${specie == 'Pan paniscus'}">
                    <c:set var="speciesIconCode" value="i"/>
                    <c:set var="speciesColorCode" value="red"/>
                </c:when>
                <c:when test="${specie == 'Pan troglodytes'}">
                    <c:set var="speciesIconCode" value="i"/>
                    <c:set var="speciesColorCode" value="red"/>
                </c:when>
                <c:when test="${specie == 'Papio anubis'}">
                    <c:set var="speciesIconCode" value="8"/>
                    <c:set var="speciesColorCode" value="red"/>
                </c:when>
                <c:when test="${specie == 'Pongo pygmaeus'}">
                    <c:set var="speciesIconCode" value=""/>
                    <c:set var="speciesColorCode" value="red"/>
                </c:when>
                <c:when test="${specie == 'Rattus norvegicus'}">
                    <c:set var="speciesIconCode" value="R"/>
                    <c:set var="speciesColorCode" value="red"/>
                </c:when>
                <c:when test="${specie == 'Tetraodon nigroviridis'}">
                    <c:set var="speciesIconCode" value="E"/>
                    <c:set var="speciesColorCode" value="blue"/>
                </c:when>
                <c:when test="${specie == 'Zea mays'}">
                    <c:set var="speciesIconCode" value="5"/>
                </c:when>
                <c:when test="${specie == 'Xenopus (Silurana) tropicalis'}">
                    <c:set var="speciesIconCode" value="f"/>
                    <c:set var="speciesColorCode" value="blue"/>
                </c:when>
                <c:otherwise>
                    <c:set var="speciesIconCode" value="X"/>
                </c:otherwise>
            </c:choose>

            <h3>${specie}</h3>
            <span class="icon icon-species ${speciesColorCode}" data-icon="${speciesIconCode}"></span>
            <ul style="list-style:none;padding-left:0; margin-left:0;">
                <c:forEach items="${experimentAccessionsBySpecies.get(specie)}" begin="0" end="20" var="experimentAccession">
                    <c:set var="key" value="${experimentAccession}${specie}"/>
                    <li>
                        <a href="experiments/${experimentAccession}${experimentLinks.get(key)}" style="color:#337ab7; border-bottom: none;">
                                ${experimentDisplayNames.get(experimentAccession)}</a>
                    </li>
                </c:forEach>
            </ul>

        </div>
    </c:forEach>

</div>