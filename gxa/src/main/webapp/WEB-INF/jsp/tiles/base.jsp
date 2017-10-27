<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html lang="en">

<head>
    <meta charset="utf-8">
    <title><tiles:insertAttribute name="title"/>Expression Atlas &lt; EMBL-EBI</title>
    <meta name="description" content="EMBL-EBI Expression Atlas, an open public repository of gene expression pattern data under different biological conditions">
    <meta name="keywords" content="expression atlas, gene expression, baseline expression, differential expression, functional genomics, public repository, repository, bioinformatics, europe, institute">
    <meta name="author" content="EMBL-EBI Expression Atlas development team: github.com/gxa/atlas/graphs/contributors">
    <meta name="HandheldFriendly" content="true" />
    <meta name="MobileOptimized" content="width" />
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <meta name="theme-color" content="#70BDBD"> <!-- Android Chrome mobile browser tab color -->

    <!-- Add information on the life cycle of this page -->
    <meta name="ebi:owner" content="Irene Papatheodorou <irenep@ebi.ac.uk>">
    <meta name="ebi:review-cycle" content="60">
    <meta name="ebi:last-review" content="2017-02-01">
    <meta name="ebi:expiry" content="2017-06-01">

    <!-- If you link to any other sites frequently, consider optimising performance with a DNS prefetch -->
    <link rel="dns-prefetch" href="https://embl.de/">
    <link rel="dns-prefetch" href="https://www.ebi.ac.uk/">
    <link rel="dns-prefetch" href="https://www.ebi.ac.uk/arrayexpress/">

    <!-- favicons generated at realfavicongenerator.net, and merged with https://github.com/ebiwd/EBI-Pattern-library/blob/gh-pages/sample-site/boilerplate/blank.html -->
    <meta name="theme-color" content="#ffffff">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/favicons/favicon.ico" />
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/resources/favicons/favicon.ico" />
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/resources/favicons/favicon-16x16.png" sizes="16x16" />
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/resources/favicons/favicon-32x32.png" sizes="32x32" />

    <link rel="icon" type="image/png" sizes="192×192" href="${pageContext.request.contextPath}/resources/favicons/android-chrome-192x192.png" /> <!-- Android (192px) -->
    <link rel="manifest" href="${pageContext.request.contextPath}/resources/favicons/manifest.json" />

    <!-- Apple icons -->
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="${pageContext.request.contextPath}/resources/favicons/apple-icon-114x114.png" /> <!-- For iPhone 4 Retina display (114px) -->
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="${pageContext.request.contextPath}/resources/favicons/apple-icon-72x72.png" /> <!-- For iPad (72px) -->
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="${pageContext.request.contextPath}/resources/favicons/apple-icon-144x144.png" /> <!-- For iPad retina (144px) -->
    <link rel="apple-touch-icon-precomposed" href="${pageContext.request.contextPath}/resources/favicons/apple-icon-57x57.png" /> <!-- For iPhone (57px) -->
    <link rel="apple-touch-icon" sizes="180x180" href="${pageContext.request.contextPath}/resources/favicons/apple-touch-icon.png" />
    <link rel="mask-icon" href="${pageContext.request.contextPath}/resources/favicons/safari-pinned-tab.svg" color="#5bbad5" /> <!-- Safari icon for pinned tab -->

    <!-- MS icons -->
    <meta name="msapplication-TileColor" content="#da532c" />
    <meta name="msapplication-TileImage" content="${pageContext.request.contextPath}/resources/favicons/mstile-144x144.png" />
    <meta name="msapplication-config" content="${pageContext.request.contextPath}/resources/favicons/browserconfig.xml" />

    <!-- CSS: implied media=all -->
    <!-- CSS concatenated and minified via ant build script-->
    <link rel="stylesheet" href="https://www.ebi.ac.uk/web_guidelines/EBI-Framework/v1.2/libraries/foundation-6/css/foundation.css" type="text/css" media="all">
    <link rel="stylesheet" href="https://www.ebi.ac.uk/web_guidelines/EBI-Framework/v1.2/css/ebi-global.css" type="text/css" media="all">
    <link rel="stylesheet" href="https://www.ebi.ac.uk/web_guidelines/EBI-Icon-fonts/v1.2/fonts.css" type="text/css" media="all">

    <!-- Use this CSS file for any custom styling -->
    <!-- <link rel="stylesheet" href="css/custom.css" type="text/css" media="all"> -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/foundation/atlas.css" type="text/css" media="all">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/foundation/theme-atlas.css" type="text/css" media="all">

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/js/lib/jquery-ui-1.12.1.custom/jquery-ui.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/js/lib/jquery-json-tag-editor/jquery.json-tag-editor.foundation.css" media="screen">
    <!-- end CSS-->

    <!-- All JavaScript at the bottom, except for jQuery -->
    <script src="https://code.jquery.com/jquery-2.2.4.min.js"></script>
    <script src="https://code.jquery.com/jquery-migrate-1.4.1.min.js"></script>

    <script language="JavaScript" type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/babel-polyfill.min.js"></script>
    <script language="JavaScript" type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/fetch-polyfill.min.js"></script>
</head>

<body class="level2">
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
</div>

<tiles:insertAttribute name="global-footer"/>

<!-- JavaScript -->
<script defer="defer" src="https://www.ebi.ac.uk/web_guidelines/EBI-Framework/v1.2/js/script.js"></script>
<script src="https://www.ebi.ac.uk/web_guidelines/EBI-Framework/v1.2/libraries/foundation-6/js/foundation.js"></script>
<script src="https://www.ebi.ac.uk/web_guidelines/EBI-Framework/v1.2/js/foundationExtendEBI.js"></script>

<!-- The Foundation theme JavaScript -->
<script type="text/JavaScript">$(document).foundation();</script>
<script type="text/JavaScript">$(document).foundationExtendEBI();</script>

<!-- JSON Tag Editor -->
<script language="JavaScript" type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/jquery-json-tag-editor/jquery.caret.min.js"></script>
<script language="JavaScript" type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/jquery-json-tag-editor/jquery.json-tag-editor.min.js"></script>
<script language="JavaScript" type="text/javascript" src="${pageContext.request.contextPath}/resources/js/geneQueryTagEditorModule.js"></script>

<!-- Google Analytics details -->
<script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
      (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
    m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','https://www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-37676851-1', 'auto');
  ga('send', 'pageview');
</script>

<script language="JavaScript" type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/jquery-ui-1.12.1.custom/jquery-ui.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/URI.js/1.17.0/URI.min.js"></script>


<!-- Condition AUTOCOMPLETE -->
<script language="JavaScript" type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/arrayexpress-autocomplete/jquery.caret-range-1.0.js"></script>
<script language="JavaScript" type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/arrayexpress-autocomplete/jquery.array-express.autocomplete-1.1.0.150319.js"></script>
<script language="JavaScript" type="text/javascript" src="${pageContext.request.contextPath}/resources/js/conditionAutocompleteModule.js"></script>
<script>
    var contextPath = '${pageContext.request.contextPath}/';
    var geneQueryPlaceHolder = 'Enter gene query…';

    geneQueryTagEditorModule.init('#local-searchbox', '', function(){}, geneQueryPlaceHolder, contextPath);
    geneQueryTagEditorModule.init('#home-search-gene-query-input', '', function(){}, geneQueryPlaceHolder, contextPath);
    conditionAutocompleteModule.init('#home-search-condition-query-input', function(){});

    $('#home-search-atlas-clear-button').on('click' , function () {
        // Remove all tags
        var $atlasSearchInput = $('#home-search-gene-query-input'),
            atlasSearchTags = $atlasSearchInput.jsonTagEditor('getTags')[0].tags;
        atlasSearchTags.forEach(function (searchTag) {
            $atlasSearchInput.jsonTagEditor('removeTag', searchTag.value);
        });

        var $conditionQuery = $('#home-search-condition-query-input'),
            conditionQueryTags = $conditionQuery.jsonTagEditor('getTags')[0].tags;
        conditionQueryTags.forEach(function (searchTag) {
            $conditionQuery.jsonTagEditor('removeTag', searchTag.value);
        });
    });

    $('#home-search-atlas-form').submit(function(event) {
        var $atlasSearchInput = $('#home-search-gene-query-input'),
            atlasSearchTags = $atlasSearchInput.jsonTagEditor('getTags')[0].tags;
        $atlasSearchInput.val(JSON.stringify(atlasSearchTags));

        var $conditionQuery = $('#home-search-condition-query-input'),
            conditionQueryTags = $conditionQuery.jsonTagEditor('getTags')[0].tags;
        $conditionQuery.val(JSON.stringify(conditionQueryTags));
    });

    $('#local-search').submit(function(event) {
        var $atlasSearchInput = $('#local-searchbox'),
            atlasSearchTags = $atlasSearchInput.jsonTagEditor('getTags')[0].tags;
        $atlasSearchInput.val(JSON.stringify(atlasSearchTags));
    });
</script>

</body>
</html>
