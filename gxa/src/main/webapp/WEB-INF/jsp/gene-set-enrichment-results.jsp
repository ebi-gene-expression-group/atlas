<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/foundation/experiments-table.css" media="screen">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/foundation/atlas-data-tables.css" media="screen">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/lib/dataTables-1.10.13/media/css/dataTables.foundation.css" media="screen">

<section>
    <h3>Gene set enrichment results</h3>
    <h4>Species: ${species}</h4>
    <h4>Genes: </h4>
    <p>${query}</p>

    <input type="hidden" id="hiddenTypeSelected" name="hiddenTypeSelected" value="">
    <input type="hidden" id="hiddenKingdomSelected" name="hiddenKingdomSelected" value="">

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
