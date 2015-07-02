<%--
  ~ Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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

<script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/resources/latest/js/lib/datatables-1.9.4/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/resources/latest/js/experimentsPageModule.js"></script>

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
