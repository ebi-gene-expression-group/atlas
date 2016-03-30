<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/experiments-table.css" media="screen">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/atlas-data-tables.css" media="screen">

<div class="gxaExperimentsPageHeading">Experiments in Expression Atlas</div>
<input type="hidden" id="hiddenTypeSelected" name="hiddenTypeSelected" value="">
<input type="hidden" id="hiddenKingdomSelected" name="hiddenKingdomSelected" value="">

<table cellspacing="0" cellpadding="0" border="0" id="experiments-table" class="display">
    <thead/>
    <tfoot>
    <tr>
        <th rowspan="1" colspan="1">
            <input type="hidden" class="search_init">
            <select id="gxaExperimentsTableExperimentTypeSelect">
                <option value="">All</option>
                <option value="baseline">Baseline</option>
                <option value="differential">Differential</option>
            </select>
        </th>
        <th rowspan="1" colspan="1">
            <input type="hidden" class="search_init">
            <select id="gxaExperimentsTableKingdomSelect">
                <option value="">All</option>
                <option value="plants">Plants</option>
                <option value="animals-fungi">Animals and Fungi</option>
            </select>
        </th>
        <th rowspan="1" colspan="1">
            <input type="text" class="search_init" value="Search description" name="search_description">
        </th>
        <th><input type="hidden" class="search_init"></th>
        <th><input type="hidden" class="search_init"></th>
        <th rowspan="1" colspan="1">
            <input id="gxaExperimentsTableOrganismInput" type="text" class="search_init" value="Search organisms" name="search_organisms">
        </th>
        <th rowspan="1" colspan="1">
            <input type="text" class="search_init" value="Search factors" name="search_factors">
        </th>
        <th><input type="hidden" class="search_init"></th>
        <th><input type="hidden" class="search_init"></th>
    </tr>
    </tfoot>
    <tbody/>
</table>

<script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/resources/js/lib/datatables-1.9.4/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/resources/js/experimentsPageModule.js"></script>

<script>

    $(function () {
        clearLocalNav();
        $('#gxaLocalNavHome').addClass("active");
    });

    (function ($) {
        $(document).ready(function () {
            experimentsPageModule.init("${experimentType}", "${kingdom}", "${organism}");
        });
    })(jQuery);

</script>
