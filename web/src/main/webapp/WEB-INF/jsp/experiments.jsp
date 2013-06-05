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

<div class="experimentsPageHeading">Experiments loaded in the Gene Expression Atlas</div>

<table cellspacing="0" cellpadding="0" border="0" id="experiments-table" class="display">

</table>

<script type="text/javascript" language="javascript"
        src="${pageContext.request.contextPath}/resources/js/datatables-1.9.4/js/jquery.dataTables.min.js"></script>

<script>

    $(function () {
        clearLocalNav();
        $('#local-nav-home').addClass("active");
    });

    (function ($) {
        $(document).ready(function () {

            function replaceZero(data, type, full) {
                if (data == 0) {
                    return '';
                }
                return data.toString();
            }

            function formatFactors(data, type, full) {
                var html = '';
                for (var i = 0; i < data.length; i++) {
                    html += data[i].type + ' ' + data[i].value + '<br/>';
                }
                return html;
            }

            function formatSpecies(data, type, full) {
                var html = '';
                for (var i = 0; i < data.length; i++) {
                    html += data[i] + '<br/>';
                }
                return html;
            }

            function formatExperimentType(data, type, full) {
                if (data == 'BASELINE') {
                    return '<img src="resources/images/allup2_transparent_bkg.png" title="' + data.toLowerCase() + '"/>';
                }
                if (data == 'DIFFERENTIAL') {
                    return '<img src="resources/images/updown_transparent_bkg.png" title="' + data.toLowerCase() + '"/>';
                }
                if (data == 'MICROARRAY') {
                    return '<img src="resources/images/updown_transparent_bkg.png" title="' + data.toLowerCase() + '"/>';
                }
                return data;
            }

            function formatExperimentLink(data, type, full) {
                return '<a href="http://www.ebi.ac.uk/arrayexpress/experiments/' + data + '" title="View in Array Express">' + data + '</a>';
            }

            var oTable = $('#experiments-table').dataTable({
                "bProcessing":true,
                "sAjaxSource":"json/experiments",
                "aoColumns":[
                    { "sTitle":"Type", "mData":"experimentType", "mRender":function (data, type, full) {
                        return formatExperimentType(data, type, full);
                    } },
                    { "sTitle":"Experiment", "mData":"experimentAccession", "mRender":function (data, type, full) {
                        return formatExperimentLink(data, type, full);
                    } },
                    { "sTitle":"Description", "mData":"experimentDescription" },
                    { "sTitle":"Assays", "mData":"numberOfAssays", "mRender":function (data, type, full) {
                        return replaceZero(data, type, full);
                    } },
                    { "sTitle":"Contrasts", "mData":"numberOfContrasts", "mRender":function (data, type, full) {
                        return replaceZero(data, type, full);
                    } },
                    { "sTitle":"Organisms", "mData":"species", "mRender":function (data, type, full) {
                        return formatSpecies(data, type, full);
                    } },
                    { "sTitle":"Experimental Factors", "mData":"experimentalFactors", "mRender":function (data, type, full) {
                        return formatFactors(data, type, full);
                    } },
                    { "sTitle":"Array Designs", "mData":"arrayDesigns" }
                ]
            });

        });
    })(jQuery);

</script>
