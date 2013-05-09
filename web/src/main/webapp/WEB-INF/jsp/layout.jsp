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
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<html xmlns="http://www.w3.org/1999/xhtml" lang="eng"
      class=" js flexbox canvas canvastext no-touch rgba hsla multiplebgs backgroundsize borderimage
              borderradius boxshadow textshadow opacity cssanimations csscolumns cssgradients cssreflections
              csstransforms csstransforms3d csstransitions fontface generatedcontent applicationcache">

<head>
    <base href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/"/>

    <meta content="text/html; charset=utf-8" http-equiv="Content-Type">
    <meta content="en-GB" http-equiv="Content-Language">
    <meta content="_top" http-equiv="Window-target">
    <meta content="http://www.unspam.com/noemailcollection/" name="no-email-collection">
    <meta content="IE=9" http-equiv="X-UA-Compatible"/>

    <!-- compliance style start -->

    <meta name="description" content="EMBL-EBI">
    <!-- Describe what this page is about -->
    <meta name="keywords" content="bioinformatics, atlas, gxa, ebi, functional, genomics">
    <!-- A few keywords that relate to the content of THIS PAGE (not the whol project) -->
    <meta name="author" content="EMBL-EBI">
    <!-- Your [project-name] here -->

    <!-- Mobile viewport optimized: j.mp/bplateviewport -->
    <meta name="viewport" content="width=device-width,initial-scale=1">

    <!-- Place favicon.ico and apple-touch-icon.png in the root directory: mathiasbynens.be/notes/touch-icons -->

    <!-- CSS: implied media=all -->
    <!-- CSS concatenated and minified via ant build script-->

    <link type="text/css" rel="stylesheet"
          href="//www.ebi.ac.uk/web_guidelines/css/compliance/develop/boilerplate-style.css"/>
    <link type="text/css" rel="stylesheet"
          href="//www.ebi.ac.uk/web_guidelines/css/compliance/develop/ebi-global.css"/>
    <link type="text/css" rel="stylesheet"
          href="//www.ebi.ac.uk/web_guidelines/css/compliance/develop/ebi-visual.css"/>
    <link type="text/css" rel="stylesheet"
          href="//www.ebi.ac.uk/web_guidelines/css/compliance/develop/984-24-col-fluid.css"/>
    <link type="text/css" rel="stylesheet"
          href="//www.ebi.ac.uk/web_guidelines/css/compliance/develop/embl-petrol-colours.css"/>

    <style type="text/css">
            /* You have the option of setting a maximum width for your page, and making sure everything is centered */
            /* body { max-width: 1600px; margin: 0 auto; } */
    </style>

    <!-- end CSS-->

    <!-- All JavaScript at the bottom, except for Modernizr / Respond.
         Modernizr enables HTML5 elements & feature detects; Respond is a polyfill for min/max-width CSS3 Media Queries
         For optimal performance, use a custom Modernizr build: www.modernizr.com/download/ -->

    <!-- Full build -->
    <script src="//www.ebi.ac.uk/web_guidelines/js/libs/modernizr.minified.2.1.6.js"></script>

    <!-- compliance style end -->

    <title><tiles:insertAttribute name="title" ignore="true"/> &lt; EMBL-EBI</title>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/atlas.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/js/jquery-ui-1.10.2.custom/css/ui-lightness/jquery-ui-1.10.2.custom.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/table-grid.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/heatmap-and-anatomogram.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/js/chosen/chosen.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/experiment-design-table.css">
    <link media="screen" type="text/css" href="${pageContext.request.contextPath}/resources/css/home.css"
          rel="stylesheet">
    <link media="screen" type="text/css" href="${pageContext.request.contextPath}/resources/css/jqcloud.css"
          rel="stylesheet">

    <script defer="defer" src="//www.ebi.ac.uk/web_guidelines/js/cookiebanner.js"></script>
    <script defer="defer" src="//www.ebi.ac.uk/web_guidelines/js/foot.js"></script>

    <script language="JavaScript" type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/json2.js"></script>
    <script language="JavaScript" type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/jquery-1.9.1.min.js"></script>
    <script language="JavaScript" type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/jquery-migrate-1.2.0.min.js"></script>
    <script language="JavaScript" type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/jquery-ui-1.10.2.custom/js/jquery-ui-1.10.2.custom.min.js"></script>
    <script language="JavaScript" type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/experiment-page-buttons-and-tooltips.js"></script>
    <script language="JavaScript" type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/helpTooltipsModule.js"></script>

    <!-- fancybox start -->
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/js/fancyapps-fancyBox-0ffc358/source/jquery.fancybox.css"
          type="text/css" media="screen"/>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/fancyapps-fancyBox-0ffc358/source/jquery.fancybox.pack.js"></script>
    <!-- fancybox end -->

    <script>
        (function ($) { //self invoking wrapper function that prevents $ namespace conflicts
            $(document).ready(function () {

                initExperimentPageButtonsAndTooltips();

            });
        })(jQuery);
    </script>

</head>

<body class="level2">
<div id="skip-to">
    <ul>
        <li><a href="#content">Skip to main content</a></li>
        <li><a href="#local-nav">Skip to local navigation</a></li>
        <li><a href="#global-nav">Skip to EBI global navigation menu</a></li>
        <li><a href="#global-nav-expanded">Skip to expanded EBI global navigation menu (includes all sub-sections)</a>
        </li>
    </ul>
</div>

<div id="wrapper" class="container_24">

    <tiles:insertAttribute name="header"/>

    <div id="contents" class="grid_24">

        <div style="padding-left: 10px; padding-bottom: 10px; padding-right: 10px;">
            <tiles:insertAttribute name="menu" ignore="true"/>
            <tiles:insertAttribute name="body"/>
        </div>

    </div>

    <tiles:insertAttribute name="footer"/>

</div>


</body>

</html>