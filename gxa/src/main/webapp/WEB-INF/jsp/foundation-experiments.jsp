<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/foundation/experiments-table.css" media="screen">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/lib/dataTables-1.10.13/media/css/dataTables.foundation.css" media="screen">

<h3>Experiments in Expression Atlas</h3>

<input type="hidden" id="hiddenTypeSelected" name="hiddenTypeSelected" value="">
<input type="hidden" id="hiddenKingdomSelected" name="hiddenKingdomSelected" value="">

<div class="row">
    <div class="large-2 medium-4 small-8 columns">
        <label> Kingdom: <input type="hidden" class="search_init">
            <select id="gxaExperimentsTableKingdomSelect">
                <option value="">All</option>
                <option value="plants">Plants</option>
                <option value="animals">Animals</option>
                <option value="fungi">Fungi</option>
            </select>
        </label>
    </div>
</div>

<table id="experiments-table">
    <thead></thead>
    <tbody></tbody>
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

        </th>
        <th rowspan="1" colspan="1">
            <input id="gxaExperimentsTableDescriptionInput" type="text" class="search_init" value="Search" name="search_description">
        </th>
        <th><input type="hidden" class="search_init"></th>
        <th><input type="hidden" class="search_init"></th>
        <th rowspan="1" colspan="1">
            <input id="gxaExperimentsTableOrganismInput" type="text" class="search_init" value="Search" name="search_organisms">
        </th>
        <th rowspan="1" colspan="1">
            <input id="gxaExperimentsTableFactorsInput" type="text" class="search_init" value="Search" name="search_factors">
        </th>
        <th><input type="hidden" class="search_init"></th>
        <th><input type="hidden" class="search_init"></th>
    </tr>
    </tfoot>
</table>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>

<script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/resources/js/lib/dataTables-1.10.13/media/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/resources/js/lib/dataTables-1.10.13/media/js/dataTables.foundation.min.js"></script>
<script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/resources/js/foundationExperimentsPageModule.js"></script>

<script>
    (function ($) {
        $(document).ready(function () {
            foundationExperimentsPageModule.init("${experimentType}", "${kingdom}", "${organism}", "${experimentSet}");
        });
    })(jQuery);
</script>
