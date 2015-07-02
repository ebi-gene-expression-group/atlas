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

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="base" value="${pageContext.request.contextPath}"/>
<c:if test="${not empty preferences.rootContext}">
    <c:set var="base" value="${preferences.rootContext}"/>
</c:if>

<!DOCTYPE html>
<html>
<head>

    <link rel="stylesheet" type="text/css" href="${base}/resources/latest/css/atlas.css">
    <link rel="stylesheet" type="text/css" href="${base}/resources/latest/css/table-grid.css">
    <link rel="stylesheet" type="text/css" href="${base}/resources/latest/css/heatmap-and-anatomogram.css">

    <script language="JavaScript" type="text/javascript" src="${base}/resources/latest/js/lib/json2.js"></script>
    <script language="JavaScript" type="text/javascript" src="${base}/resources/latest/js/lib/jquery-1.9.1.min.js"></script>
    <script language="JavaScript" type="text/javascript" src="${base}/resources/latest/js/lib/jquery-migrate-1.2.0.min.js"></script>
    <!-- jQuery UI start -->
    <script language="JavaScript" type="text/javascript" src="${pageContext.request.contextPath}/resources/latest/js/lib/jquery-ui-1.11.4.custom/jquery-ui.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/latest/js/lib/jquery-ui-1.11.4.custom/jquery-ui.min.css">
    <!-- jQuery UI end -->
    <script language="JavaScript" type="text/javascript" src="${base}/resources/latest/js/lib/jquery.svg.package-1.4.5/jquery.svg.js"></script>
    <script language="JavaScript" type="text/javascript" src="${base}/resources/latest/js/experiment-page-buttons-and-tooltips.js"></script>
    <script language="JavaScript" type="text/javascript" src="${base}/resources/latest/js/helpTooltipsModule.js"></script>
    <script language="JavaScript" type="text/javascript" src="${base}/resources/latest/js/anatomogramModule.js"></script>

    <script src="//www.ebi.ac.uk/web_guidelines/js/libs/modernizr.minified.2.1.6.js"></script>

    <!-- fancybox start -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/latest/js/lib/fancyapps-fancyBox-0ffc358/source/jquery.fancybox.css" media="screen"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/latest/js/lib/fancyapps-fancyBox-0ffc358/source/jquery.fancybox.pack.js"></script>
    <!-- fancybox end -->

</head>
<body>

<tiles:insertAttribute name="body"/>
</body>
</html>

