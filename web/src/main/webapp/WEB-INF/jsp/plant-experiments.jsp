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
            <h2>Browse plant experiments</h2>
        </div>
        <div class="grid_26 alpha" style="padding-top: 20px">
            <div class="grid_4 alpha">
                <p><div><img src="${pageContext.request.contextPath}/resources/images/gramene_logo.png"/></div></p>

                <p><div class="icon icon-functional" data-icon="1"><a href="/gxa/home">Search Atlas</a></div></p>
            </div>

            <div class="grid_18 omega">
                <p>Thanks to funding from the <a href="http://www.gramene.org/">Gramene</a> project,
                    Expression Atlas contains <b>${numberOfPlantExperiments}</b> <a href="/gxa/experiments?kingdom=plants">plant experiments</a>, studying e.g. <a href="/gxa/experiments?organism=Arabidopsis+thaliana">Arabidopsis</a>, <a href="/gxa/experiments?organism=Oryza+sativa">rice</a>, and <a href="/gxa/experiments?organism=Zea+mays">maize</a>.
               </p>

                <p>
                    The <i>baseline</i> experiments, are either RNA-seq or proteomics, and display expression levels of gene products under 'normal' conditions (e.g. normal rice tissues).
                    Each experiment is manually curated to a high standard, and RNA expression levels are calculated using the
                    <a href="http://nunofonseca.github.io/irap/">iRAP</a> pipeline.</p>

                <p>The <i>differential</i> experiments in Atlas, containing both microarray and RNA-seq data, allows users to query which genes are up-/down-regulated
                    in different experimental conditions, e.g. 'in Arabidopsis shoots, what genes are upregulated in plants treated by X?'
                    </p>
            </div>
        </div>
    </div>
    <!-- /Simple page header -->
    <div class="grid_24 alpha" style="padding-top: 20px">
        <div class="page-header">
    <h3>Baseline experiments</h3>

    <c:forEach items="${baselineExperimentAccessionsBySpecies.keySet()}" var="species">

        <div class="grid_8 specie_item">
            <c:choose>
                <c:when test="${species == 'Arabidopsis thaliana'}">
                    <c:set var="speciesIconCode" value="B"/>
					<c:set var="speciesColorCode" value="green" />
                </c:when>
                <c:when test="${species == 'Hordeum vulgare subsp. vulgare'}">
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
                <c:when test="${species == 'Zea mays'}">
                    <c:set var="speciesIconCode" value="5"/>
					<c:set var="speciesColorCode" value="green" />
                </c:when>

                <c:otherwise>
                    <c:set var="speciesIconCode" value="P"/>
					<c:set var="speciesColorCode" value="green" />
                </c:otherwise>
            </c:choose>

                <h4>${species}</h4>
				<span class="icon icon-species ${speciesColorCode}" data-icon="${speciesIconCode}"></span>
                <ul style="list-style:none;padding-left:0; margin-left:0;">
                    <c:forEach items="${baselineExperimentAccessionsBySpecies.get(species)}" begin="0" end="20" var="experimentAccession">
                        <c:set var="key" value="${experimentAccession}${species}"/>
                        <li>
                            <a href="experiments/${experimentAccession}${experimentLinks.get(key)}" style="color:#337ab7; border-bottom: none;">
                            ${experimentDisplayNames.get(experimentAccession)}</a>
                        </li>
                    </c:forEach>
                </ul>
        </div>
    </c:forEach>
    </div>

    <div class="grid_24 alpha" style="padding-top: 20px">
        <div class="page-header">
            <h3>Differential experiments</h3>

            <c:forEach items="${numDifferentialExperimentsBySpecies.keySet()}" var="species">

                <div class="grid_8 specie_item">
                    <c:choose>
                        <c:when test="${species == 'Arabidopsis thaliana'}">
                            <c:set var="speciesIconCode" value="B"/>
							<c:set var="speciesColorCode" value="green" />
                        </c:when>
                        <c:when test="${species == 'Hordeum vulgare subsp. vulgare'}">
                            <c:set var="speciesIconCode" value="5"/>
							<c:set var="speciesColorCode" value="green" />
                        </c:when>
                        <c:when test="${species == 'Oryza sativa Japonica Group'}">
                            <c:set var="speciesIconCode" value="6"/>
							<c:set var="speciesColorCode" value="green" />
                        </c:when>
                        <c:when test="${species == 'Oryza sativa Indica Group'}">
                            <c:set var="speciesIconCode" value="6"/>
							<c:set var="speciesColorCode" value="green" />
                        </c:when>
                        <c:when test="${species == 'Zea mays'}">
                            <c:set var="speciesIconCode" value="5"/>
							<c:set var="speciesColorCode" value="green" />
                        </c:when>

                        <c:otherwise>
                            <c:set var="speciesIconCode" value="P"/>
							<c:set var="speciesColorCode" value="green" />
                        </c:otherwise>
                    </c:choose>

                    <h4>${species}</h4>
					<span class="icon icon-species ${speciesColorCode}" data-icon="${speciesIconCode}"></span>
                    <ul style="list-style:none;padding-left:0; margin-left:0;">
                        <a href="experiments?organism=${species}&experimentType=differential" style="color:#337ab7; border-bottom: none;">
                                ${numDifferentialExperimentsBySpecies.get(species)} experiment${numDifferentialExperimentsBySpecies.get(species) > 1 ? "s" : "" }</a>
                    </ul>
                </div>
            </c:forEach>
        </div>
</div>
