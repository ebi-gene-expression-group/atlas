<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/foundation/experiments-table.css" media="screen">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/foundation/atlas-data-tables.css" media="screen">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/lib/dataTables-1.10.13/media/css/dataTables.foundation.css" media="screen">

<section>
    <h3>Gene set enrichment results</h3>
    <h4>Species: <i> ${species} </i></h4>
    <h4>Genes: </h4>
    <div style="margin-bottom: 30px">
        <div id="query-short">
            <span>
        ${queryShort}
            </span>
            <a role="button" style="cursor: pointer;" onclick="$('#query-short').hide() ; $('#query-full').show()" >
                … (show all)</a>
        </div>
        <div id="query-full" style="display:none">
        <span>
            ${query}
        </span>
        <a role="button" style="cursor: pointer;" onclick="$('#query-full').hide() ; $('#query-short').show()" >
            (show fewer)</a>
        </div>
    </div>


    <table id="gene-set-enrichment-results-table">
        <thead></thead>

        <tbody/>
    </table>
</section>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>

<script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/resources/js/lib/dataTables-1.10.13/media/js/jquery.dataTables.js"></script>
<script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/resources/js/lib/dataTables-1.10.13/media/js/dataTables.foundation.js"></script>
<script type="text/javascript" language="javascript"
        src="${pageContext.request.contextPath}/resources/js/geneSetEnrichmentModule.js"></script>

<script>
    (function ($) {
        $(document).ready(function () {
            geneSetEnrichmentModule.init(${data});
        });
    })(jQuery);
</script>
