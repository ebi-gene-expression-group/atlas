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

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h" %>

<c:set var="base" value="${pageContext.request.contextPath}"/>
<%-- TODO: remove rootContext when BioJs no longer uses HTML insert --%>
<c:if test="${not empty preferences.rootContext}">
    <c:set var="base" value="${preferences.rootContext}"/>
</c:if>

<c:set var="pathImage" value="${base.concat('/resources/images/male_selected.png')}"/>
<c:if test="${species.equals('oryza sativa japonica group')}">
    <c:set var="pathImage" value="${base.concat('/resources/images/plant_switch_buttons_1.png')}"/>
</c:if>

<%-- TODO: replace this file with heatmapContainer.jsx (which duplicates this page)dd  --%>
<c:if test="${not empty jsonProfiles}">
    <%@ include file="anatomogram.jsp" %>

    <div id="heatmap-anatomogram" class="gxaHeatmapAnatomogramRow">

        <div id="anatomogram" class="gxaDoubleClickNoSelection gxaAside">
            <table>
                <tr>
                    <td style="padding-top: 15px; vertical-align:top">
                        <span id="sex-toggle">
                            <img id="sex-toggle-image" title="Switch anatomogram" class="gxaButtonImage"
                                 style="width:20px;height:38px;padding:2px" src="${pathImage}"/>
                        </span>
                        <!--
                        <span data-help-loc="#anatomogram"/>
                        -->
                    </td>
                    <td>
                        <c:set var="height" value="250px"/>
                        <c:if test="${fn:contains(maleAnatomogramFile,\"human\")}">
                            <c:set var="height" value="360px"/>
                        </c:if>

                        <div id="anatomogramBody" style="display:inline-block;width: 230px; height:${height}">
                        </div>
                    </td>
                </tr>
            </table>
            <div id="anatomogram-ensembl-launcher"></div>
        </div>

        <div id="ensembl-launcher" class="gxaAside" style="display:inline"></div>

        <div id="heatmap-react" class="heatmap-position${isWidget ? '-widget' : ''}"></div>

    </div>
</c:if>