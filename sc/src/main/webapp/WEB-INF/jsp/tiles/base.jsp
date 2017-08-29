<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<html lang="en">

<head>
    <meta charset="utf-8">
    <title><tiles:insertAttribute name="title"/>Single Cell Atlas &lt; EMBL-EBI</title>
    <meta name="description" content="EMBL-EBI Single Cell Expression Atlas, an open public repository of single cell gene expression data">
    <meta name="keywords" content="expression atlas, single cell expression, gene expression, baseline expression, functional genomics, public repository, repository, bioinformatics, europe, institute">
    <meta name="author" content="EMBL-EBI Single Cell Expression Atlas development team: github.com/gxa/atlas/graphs/contributors">
    <meta name="HandheldFriendly" content="true" />
    <meta name="MobileOptimized" content="width" />
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <meta name="theme-color" content="#70BDBD"> <!-- Android Chrome mobile browser tab color -->

    <!-- Add information on the life cycle of this page -->
    <meta name="ebi:owner" content="Irene Papatheodorou <irenep@ebi.ac.uk>">
    <meta name="ebi:review-cycle" content="60">
    <meta name="ebi:last-review" content="2017-07-01">
    <meta name="ebi:expiry" content="2017-09-01">

    <!-- If you link to any other sites frequently, consider optimising performance with a DNS prefetch -->
    <link rel="dns-prefetch" href="https://embl.de/">
    <link rel="dns-prefetch" href="https://www.ebi.ac.uk/gxa/">

    <!-- If you have custom icon, replace these as appropriate.
       You can generate them at realfavicongenerator.net -->
    <link rel="icon" type="image/x-icon" href="//www.ebi.ac.uk/web_guidelines/EBI-Framework/v1.2/images/logos/EMBL-EBI/favicons/favicon.ico" />
    <link rel="icon" type="image/png" href="//www.ebi.ac.uk/web_guidelines/EBI-Framework/v1.2/images/logos/EMBL-EBI/favicons/favicon-32x32.png" />
    <link rel="icon" type="image/png" sizes="192×192" href="//www.ebi.ac.uk/web_guidelines/EBI-Framework/v1.2/images/logos/EMBL-EBI/favicons/android-chrome-192x192.png" /> <!-- Android (192px) -->
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="//www.ebi.ac.uk/web_guidelines/EBI-Framework/v1.2/images/logos/EMBL-EBI/favicons/apple-icon-114x114.png" /> <!-- For iPhone 4 Retina display (114px) -->
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="//www.ebi.ac.uk/web_guidelines/EBI-Framework/v1.2/images/logos/EMBL-EBI/favicons/apple-icon-72x72.png" /> <!-- For iPad (72px) -->
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="//www.ebi.ac.uk/web_guidelines/EBI-Framework/v1.2/images/logos/EMBL-EBI/favicons/apple-icon-144x144.png" /> <!-- For iPad retinat (144px) -->
    <link rel="apple-touch-icon-precomposed" href="//www.ebi.ac.uk/web_guidelines/EBI-Framework/v1.2/images/logos/EMBL-EBI/favicons/apple-icon-57x57.png" /> <!-- For iPhone (57px) -->
    <link rel="mask-icon" href="//www.ebi.ac.uk/web_guidelines/EBI-Framework/v1.2/images/logos/EMBL-EBI/favicons/safari-pinned-tab.svg" color="#ffffff" /> <!-- Safari icon for pinned tab -->
    <meta name="msapplication-TileColor" content="#2b5797" /> <!-- MS Icons -->
    <meta name="msapplication-TileImage" content="//www.ebi.ac.uk/web_guidelines/EBI-Framework/v1.2/images/logos/EMBL-EBI/favicons/mstile-144x144.png" />

    <!-- CSS: implied media=all -->
    <!-- CSS concatenated and minified via ant build script-->
    <link rel="stylesheet" href="https://www.ebi.ac.uk/web_guidelines/EBI-Framework/v1.2/libraries/foundation-6/css/foundation.css" type="text/css" media="all" />
    <link rel="stylesheet" href="https://www.ebi.ac.uk/web_guidelines/EBI-Framework/v1.2/css/ebi-global.css" type="text/css" media="all" />
    <link rel="stylesheet" href="https://www.ebi.ac.uk/web_guidelines/EBI-Icon-fonts/v1.2/fonts.css" type="text/css" media="all" />

    <!-- Use this CSS file for any custom styling -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/atlas.css" type="text/css" media="all">

    <!-- If you have a custom header image or colour -->
    <!-- <meta name="ebi:localmasthead-color" content="#000"> -->
    <!-- <meta name="ebi:localmasthead-image" content="https://www.ebi.ac.uk/web_guidelines/EBI-Framework/images/backgrounds/embl-ebi-background.jpg"> -->

    <!-- you can replace this with theme-[projectname].css. See http://www.ebi.ac.uk/web/style/colour for details of how to do this -->
    <!-- also inform ES so we can host your colour palette file -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/theme-atlas.css" type="text/css" media="all">

  <!-- All JavaScript at the bottom, except for jQuery -->
    <script src="https://code.jquery.com/jquery-2.2.4.min.js"></script>
    <script src="https://code.jquery.com/jquery-migrate-1.4.1.min.js"></script>
    <!-- end CSS-->
</head>

<body>
<div id="skip-to">
    <ul>
        <li><a href="#content">Skip to main content</a></li>
        <li><a href="#local-nav">Skip to local navigation</a></li>
        <li><a href="#global-nav">Skip to EBI global navigation menu</a></li>
        <li><a href="#global-nav-expanded">Skip to expanded EBI global navigation menu (includes all sub-sections)</a></li>
    </ul>
</div>

<tiles:insertAttribute name="global-masthead"/>

<div id="content">
    <tiles:insertAttribute name="local-masthead"/>
    <section id="main-content-area" class="margin-top-large margin-bottom-large" role="main">
        <tiles:insertAttribute name="content"/>
    </section>
    <tiles:insertAttribute name="local-footer"/>
    <tiles:insertAttribute name="credits"/>
</div>

<tiles:insertAttribute name="global-footer"/>

<!-- JavaScript -->

<!-- Grab jQuery from CDN -->
<script src="https://code.jquery.com/jquery-2.2.4.min.js"></script>

<!-- Your custom JavaScript file scan go here... change names accordingly -->
<!--
<script defer="defer" src="//www.ebi.ac.uk/web_guidelines/js/plugins.js"></script>
<script defer="defer" src="//www.ebi.ac.uk/web_guidelines/js/script.js"></script>
-->

<script defer="defer" src="//www.ebi.ac.uk/web_guidelines/EBI-Framework/v1.2/js/script.js"></script>

<!-- The Foundation theme JavaScript -->
<script src="//www.ebi.ac.uk/web_guidelines/EBI-Framework/v1.2/libraries/foundation-6/js/foundation.js"></script>
<script src="//www.ebi.ac.uk/web_guidelines/EBI-Framework/v1.2/js/foundationExtendEBI.js"></script>
<script type="text/JavaScript">$(document).foundation();</script>
<script type="text/JavaScript">$(document).foundationExtendEBI();</script>

</body>
</html>
