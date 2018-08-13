<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="https://cdn.datatables.net/1.10.15/css/dataTables.foundation.min.css" media="screen">

<div class="row expanded">
    <div class="small-12 columns">
        <h3>Gene set enrichment results</h3>
        <h4>Species: <i> ${species} </i></h4>
        <h4>Genes: </h4>
        <div class="margin-bottom-medium" id="query-short">
            <span>${queryShort}</span>
            <a style="cursor: pointer;" onclick="$('#query-short').hide() ; $('#query-full').show()" >… (show all)</a>
        </div>
        <div  class="margin-bottom-medium" id="query-full" style="display:none">
            <span>${query}</span>
            <a style="cursor: pointer;" onclick="$('#query-full').hide() ; $('#query-short').show()" >(show fewer)</a>
        </div>

        <table id="gene-set-enrichment-results-table">
        </table>
    </div>
</div>


<script defer src="https://cdn.datatables.net/1.10.15/js/jquery.dataTables.min.js"></script>
<script defer src="https://cdn.datatables.net/1.10.15/js/dataTables.foundation.min.js"></script>
<script defer src="https://cdn.datatables.net/buttons/1.3.1/js/dataTables.buttons.min.js"></script>
<script defer src="https://cdn.datatables.net/buttons/1.3.1/js/buttons.html5.min.js"></script>
<script defer src="${pageContext.request.contextPath}/resources/js/lib/datatables.buttons.foundation.min.js"></script>

<script defer src="${pageContext.request.contextPath}/resources/js/geneSetEnrichmentModule.js"></script>

<script>
    document.addEventListener("DOMContentLoaded", function(event) {
        geneSetEnrichmentModule.init("#gene-set-enrichment-results-table", ${data});
    });
</script>
