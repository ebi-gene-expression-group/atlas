<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <title><tiles:insertAttribute name="title"/>Single Cell Expression Atlas &lt; EMBL-EBI</title>
    <meta name="description" content="EMBL-EBI Single Cell Expression Atlas, an open public repository of single cell gene expression data">
    <meta name="keywords" content="expression atlas, single cell expression, gene expression, baseline expression, functional genomics, public repository, repository, bioinformatics, europe, institute">
    <meta name="author" content="EBI Gene Expression Team – https://www.ebi.ac.uk/about/people/irene-papatheodorou">
    <meta name="HandheldFriendly" content="true" />
    <meta name="MobileOptimized" content="width" />
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <meta name="theme-color" content="#70BDBD"> <!-- Android Chrome mobile browser tab color -->

    <!-- Add information on the life cycle of this page -->
    <meta name="ebi:owner" content="Irene Papatheodorou <irenep@ebi.ac.uk>">
    <meta name="ebi:review-cycle" content="60">
    <meta name="ebi:last-review" content="2018-05-01">
    <meta name="ebi:expiry" content="2018-07-01">

    <!-- If you link to any other sites frequently, consider optimising performance with a DNS prefetch -->
    <link rel="dns-prefetch" href="https://www.ebi.ac.uk/gxa/">
    <link rel="dns-prefetch" href="https://www.ebi.ac.uk/arrayexpress/">
    <link rel="dns-prefetch" href="https://www.ebi.ac.uk/">
    <link rel="dns-prefetch" href="https://embl.de/">

    <!-- If you have custom icon, replace these as appropriate. You can generate them at realfavicongenerator.net -->
    <link rel="icon" type="image/x-icon" href="https://ebi.emblstatic.net/web_guidelines/EBI-Framework/v1.3/images/logos/EMBL-EBI/favicons/favicon.ico" />
    <link rel="icon" type="image/png" href="https://ebi.emblstatic.net/web_guidelines/EBI-Framework/v1.3/images/logos/EMBL-EBI/favicons/favicon-32x32.png" />
    <link rel="icon" type="image/png" sizes="192×192" href="https://ebi.emblstatic.net/web_guidelines/EBI-Framework/v1.3/images/logos/EMBL-EBI/favicons/android-chrome-192x192.png" /> <!-- Android (192px) -->
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="https://ebi.emblstatic.net/web_guidelines/EBI-Framework/v1.3/images/logos/EMBL-EBI/favicons/apple-icon-114x114.png" /> <!-- For iPhone 4 Retina display (114px) -->
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="https://ebi.emblstatic.net/web_guidelines/EBI-Framework/v1.3/images/logos/EMBL-EBI/favicons/apple-icon-72x72.png" /> <!-- For iPad (72px) -->
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="https://ebi.emblstatic.net/web_guidelines/EBI-Framework/v1.3/images/logos/EMBL-EBI/favicons/apple-icon-144x144.png" /> <!-- For iPad retina (144px) -->
    <link rel="apple-touch-icon-precomposed" href="https://ebi.emblstatic.net/web_guidelines/EBI-Framework/v1.3/images/logos/EMBL-EBI/favicons/apple-icon-57x57.png" /> <!-- For iPhone (57px) -->
    <link rel="mask-icon" href="https://ebi.emblstatic.net/web_guidelines/EBI-Framework/v1.3/images/logos/EMBL-EBI/favicons/safari-pinned-tab.svg" color="#ffffff" /> <!-- Safari icon for pinned tab -->
    <meta name="msapplication-TileColor" content="#2b5797" /> <!-- MS Icons -->
    <meta name="msapplication-TileImage" content="https://ebi.emblstatic.net/web_guidelines/EBI-Framework/v1.3/images/logos/EMBL-EBI/favicons/mstile-144x144.png" />

    <!-- CSS: implied media=all -->
    <!-- CSS concatenated and minified via ant build script-->
    <link rel="stylesheet" href="https://ebi.emblstatic.net/web_guidelines/EBI-Framework/v1.3/css/ebi-global.css" type="text/css" media="all" />
    <link rel="stylesheet" href="https://ebi.emblstatic.net/web_guidelines/EBI-Icon-fonts/v1.2/fonts.css" type="text/css" media="all" />

    <!-- If you have a custom header image or colour -->
    <meta name="ebi:masthead-color" content="rgb(0,124,130)" />
    <meta name="ebi:masthead-image" content="${pageContext.request.contextPath}/resources/images/cells_background.png" />

    <!-- you can replace this with theme-[projectname].css. See http://www.ebi.ac.uk/web/style/colour for details of how to do this -->
    <!-- also inform ES so we can host your colour palette file -->
    <!-- <link rel="stylesheet" href="https://dev.ebi.emblstatic.net/web_guidelines/EBI-Framework/v1.3/css/theme-embl-petrol.css" type="text/css" media="all" /> -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/theme-atlas.css" type="text/css" media="all">

    <!-- Use this CSS file for any custom styling -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/atlas.css" type="text/css" media="all">
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
<tiles:insertAttribute name="local-masthead"/>

<div id="content">
    <div id="main-content-area" class="margin-top-large margin-bottom-large" role="main">
        <tiles:insertAttribute name="content"/>
    </div>
</div>

<tiles:insertAttribute name="local-footer"/>
<tiles:insertAttribute name="global-footer"/>

<!-- Custom JavaScript -->
<!-- Don’t defer or async, these two need to be loaded before other bundles, which are effectively deferred -->
<script src="${pageContext.request.contextPath}/resources/js/lib/babel-polyfill.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/lib/fetch-polyfill.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/lib/url-search-params-polyfill.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js-bundles/vendorCommons.bundle.js"></script>

<!-- JavaScript -->
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script defer="defer" src="https://ebi.emblstatic.net/web_guidelines/EBI-Framework/v1.3/js/script.js"></script>

<!-- The Foundation theme JavaScript -->
<script src="https://ebi.emblstatic.net/web_guidelines/EBI-Framework/v1.3/libraries/foundation-6/js/foundation.js"></script>
<script src="https://ebi.emblstatic.net/web_guidelines/EBI-Framework/v1.3/js/foundationExtendEBI.js"></script>
<script type="text/JavaScript">$(document).foundation();</script>
<script type="text/JavaScript">$(document).foundationExtendEBI();</script>

<!-- Google Analytics -->
<script>
  window.ga=window.ga||function(){(ga.q=ga.q||[]).push(arguments)};ga.l=+new Date;
  ga('create', 'UA-37676851-3', 'auto');
  ga('send', 'pageview');
</script>
<script async src="https://www.google-analytics.com/analytics.js"></script>
<!-- End Google Analytics -->

</body>
</html>
