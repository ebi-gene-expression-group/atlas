<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h" %>

<%--
  ~ Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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

<%--@elvariable id="colourGradient" type="uk.ac.ebi.atlas.utils.ColourGradient"--%>

<%@ attribute name="geneProfiles" required="true" type="uk.ac.ebi.atlas.model.baseline.BaselineProfilesList"%>

<div style="display: inline-table">
    <%--@elvariable id="baselineExpressionLevelRounder" type="uk.ac.ebi.atlas.profiles.baseline.BaselineExpressionLevelRounder"--%>
    <c:set var="minExpressionLevel" value="${baselineExpressionLevelRounder.format(geneProfiles.getMinExpressionLevel())}"/>
    <c:set var="maxExpressionLevel" value="${baselineExpressionLevelRounder.format(geneProfiles.getMaxExpressionLevel())}"/>

    <h:gradient-table-row lowValueColour="${colourGradient.getHexByColourName('lightGray')}"
                          highValueColour="${colourGradient.getHexByColourName('blue')}"
                          lowValueColorExpressionLevel="${minExpressionLevel}"
                          highValueColorExpressionLevel="${maxExpressionLevel}"/>
</div>
<div id="baseline-help-diff" data-help-loc="#gradient-base" style="display: inline-block; vertical-align: top; padding-left: 2px"></div>
