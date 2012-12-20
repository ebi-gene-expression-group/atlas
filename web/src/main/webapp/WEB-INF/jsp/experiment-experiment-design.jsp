<%--
  ~ Copyright 2008-2012 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="f" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div id="table-caption"><b>Experiment Design</b></div>

<div id="toolbar">Show Analysed only? <input type="checkbox" id="isOnlyAnalysed" name="isOnlyAnalysed" checked="yes"/>
</div>

<table cellpadding="0" cellspacing="0" border="0" class="display" id="experiment-design-table">
    <thead>
    <tr>
        <th id="assaysHeader" class="header-cell bl br bt bb" rowspan="2">${assayHeader}</th>
        <th id="samplesHeader" class="samples br bt">Sample Characteristics</th>
        <th id="factorsHeader" class="factors br bt">Factor Values</th>
    </tr>
    <tr id="headerStub"></tr>
    </thead>
    <tbody></tbody>
</table>

<p></p>

<script type="text/javascript" language="javascript"
        src="${pageContext.request.contextPath}/resources/js/datatables-1.9.4/js/jquery.dataTables.min.js"></script>

<script type="text/javascript" charset="utf-8">

    /* this is for dynamically resizing table */
    var $window = $(window);
    var calcDataTableHeight = function () {
        return $window.height() - 270;
    };
    var calcDataTableWidth = function () {
        return $window.width() - 100;
    };


    /* Data set - loaded from experiment design tsv file */
    var aDataSet = ${tableData};
    var aRunAccessions = ${runAccessions};
    var aSamples = ${samples};
    var aFactors = ${factors};

    /* configuring actual table */
    $(document).ready(function () {

        $.assocArraySize = function (obj) {
            var size = 0, key;
            for (key in obj) {
                if (obj.hasOwnProperty(key)) size++;
            }
            return size;
        };

        /* Set colspan for each category */
        $('#samplesHeader').attr('colspan', $.assocArraySize(aSamples));
        $('#factorsHeader').attr('colspan', $.assocArraySize(aFactors));

        /* populate all sub categories */
        var aoColumnDefs = new Array();
        var i = 0;
        aoColumnDefs[i] = { "sClass":"assays bb br bl", "aTargets":[ i ] };
        for (var sample in aSamples) {
            $('#headerStub').append("<th class=\"header-cell bb\">" + sample + "</th>");
            aoColumnDefs[++i] = { "sClass":"center bb", "aTargets":[ i ] };
        }
        aoColumnDefs[i]["sClass"] = "center bb br";
        $('#headerStub th:last()').attr("class", "header-cell bb br");
        for (var factor in aFactors) {
            $('#headerStub').append("<th class=\"header-cell bb\">" + factor + "</th>");
            aoColumnDefs[++i] = { "sClass":"center bb", "aTargets":[ i ] };
        }
        aoColumnDefs[i]["sClass"] = "center bb br";
        $('#headerStub th:last()').attr("class", "header-cell bb br");

        /* Custom filtering function which will filter analysed runs */
        $.fn.dataTableExt.afnFiltering.push(
                function (oSettings, aData, iDataIndex) {
                    var only = $('#isOnlyAnalysed').is(':checked');
                    if (!only || jQuery.inArray(aData[0], aRunAccessions) > -1) {
                        return true;
                    }
                    return false;
                }
        );

        var oTable = $('#experiment-design-table').dataTable({
            "aaData":aDataSet,
            "aoColumnDefs":aoColumnDefs,
            "bPaginate":false,
            "bScrollCollapse":true,
            "sScrollY":calcDataTableHeight(),
            "sScrollX":calcDataTableWidth(),
            "sDom":'i<"download">f<"clear">t'
        });

        $('div.download').html('<a id="download-experiment-design-link" title="Download experiment design" class="button-image" style="margin-bottom:5px" href="experiments/${experimentAccession}/experiment-design.tsv" target="_blank">' +
                '<img id="download-experiment-design" alt="Download experiment design" src="resources/images/download_blue_small.png"></a>');
        $('div.download').attr('style', 'float: right');
        $('#isOnlyAnalysed').click(function () {
            oTable.fnDraw();
        });

        $window.resize(function () {
            var oSettings = oTable.fnSettings();
            oSettings.oScroll.sY = calcDataTableHeight(); // <- updated!
            //oSettings.oScroll.sX = calcDataTableWidth();

            // maybe you need to redraw the table (not sure about this)
            oTable.fnAdjustColumnSizing(false);
            oTable.fnDraw(false);
        });

        $('#download-experiment-design-link').button().tooltip();
    });

</script>
