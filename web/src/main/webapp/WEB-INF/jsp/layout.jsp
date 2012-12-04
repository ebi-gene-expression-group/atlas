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

<!DOCTYPE html>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<html xmlns="http://www.w3.org/1999/xhtml" lang="eng">

<head>
    <base href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/"/>
    <!-- old style start -->

    <meta content="text/html; charset=utf-8" http-equiv="Content-Type">
    <meta content="en-GB" http-equiv="Content-Language">
    <meta content="_top" http-equiv="Window-target">
    <meta content="http://www.unspam.com/noemailcollection/" name="no-email-collection">
    <meta content="IE=9" http-equiv="X-UA-Compatible"/>

    <link rel="stylesheet" href="http://www.ebi.ac.uk/inc/css/contents.css" type="text/css"/>
    <link rel="stylesheet" href="http://www.ebi.ac.uk/inc/css/userstyles.css" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/old/atlas-ebi.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/old/atlas-searchform.css">
    <script src="http://www.ebi.ac.uk/inc/js/contents.js" type="text/javascript"></script>
    <link rel="SHORTCUT ICON" href="http://www.ebi.ac.uk/bookmark.ico"/>

    <style type="text/css">
        @media print {
            body, .contents, .header, .contentsarea, .head {
                position: relative;
            }
        }
    </style>

    <!-- old style end -->

    <title><tiles:insertAttribute name="title" ignore="true"/></title>

    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/ui-lightness/jquery-ui-1.9.1.custom.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/atlas.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/table-grid.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/anatomogram.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/js/chosen/chosen.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/experiment-design-table.css">
    <link media="screen" type="text/css" href="${pageContext.request.contextPath}/resources/css/home.css"
          rel="stylesheet">
    <link media="screen" type="text/css" href="${pageContext.request.contextPath}/resources/css/jqcloud.css"
          rel="stylesheet">

    <script language="JavaScript" type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/jquery-1.8.3.min.js"></script>
    <script language="JavaScript" type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/jquery-ui/jquery-ui-1.9.1.custom.min.js"></script>

</head>

<body>

<tiles:insertAttribute name="header"/>

<div id="contents" class="page-contents">

    <tiles:insertAttribute name="menu" ignore="true"/>

    <tiles:insertAttribute name="body"/>

</div>

<tiles:insertAttribute name="footer"/>

</body>

</html>