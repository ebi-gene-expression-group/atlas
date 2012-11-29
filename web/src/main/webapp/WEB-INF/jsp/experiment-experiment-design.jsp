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

<!DOCTYPE html>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="f" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html xmlns="http://www.w3.org/1999/xhtml" lang="eng">

<head>
    <base href="${pageContext.request.contextPath}/"/>
    <!-- old style start -->

    <meta content="text/html; charset=utf-8" http-equiv="Content-Type">
    <meta content="en-GB" http-equiv="Content-Language">
    <meta content="_top" http-equiv="Window-target">
    <%--<meta content="IE=7" http-equiv="X-UA-Compatible">--%>
    <meta content="http://www.unspam.com/noemailcollection/" name="no-email-collection">
    <meta content="IE=9" http-equiv="X-UA-Compatible"/>

    <link rel="stylesheet" href="http://www.ebi.ac.uk/inc/css/contents.css" type="text/css"/>
    <link rel="stylesheet" href="http://www.ebi.ac.uk/inc/css/userstyles.css" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/old/atlas-ebi.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/old/atlas-searchform.css">
    <script src="http://www.ebi.ac.uk/inc/js/contents.js" type="text/javascript"></script>
    <link rel="SHORTCUT ICON" href="http://www.ebi.ac.uk/bookmark.ico"/>

    <style type="text/css">
        @media print {
            body, .contents, .header, .contentsarea, .head {
                position: relative;
            }
        }
    </style>

    <!-- old style end -->

    <title>Experiment - experiment design</title>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/atlas.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/experiment-design-table.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/ui-lightness/jquery-ui-1.9.1.custom.min.css">

    <script language="JavaScript" type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/jquery-1.8.3.min.js"></script>
    <script language="JavaScript" type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/jquery-ui/jquery-ui-1.9.1.custom.min.js"></script>
    <script type="text/javascript" language="javascript"
            src="${pageContext.request.contextPath}/resources/js/datatables-1.9.4/js/jquery.dataTables.min.js"></script>

    <script type="text/javascript" charset="utf-8">

        /* this is for dynamically resizing table */
        var $window = $(window);
        var calcDataTableHeight = function () {
            return $window.height() - 270;
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
                        } else {
                            return false;
                        }
                    }
            );

            var oTable = $('#experiment-design-table').dataTable({
                "aaData":aDataSet,
                "aoColumnDefs":aoColumnDefs,
                "bPaginate":false,
                "bScrollCollapse":true,
                "sScrollY":calcDataTableHeight(),
                "sDom":'i<"download">f<"clear">t'
            });

            $('div.download').html('<a id="download-experiment-design-link" title="Download experiment design" href="experiments/${experimentAccession}-experiment-design.tsv" target="_blank">' +
                    '<img id="download-experiment-design" alt="Download experiment design" style="width:20px" class="button-image" src="resources/images/download_blue_small.png"></a>');
            $('div.download').attr('style', 'float: right');
            $('#isOnlyAnalysed').click(function () {
                oTable.fnDraw();
            });

            $window.resize(function () {
                var oSettings = oTable.fnSettings();
                oSettings.oScroll.sY = calcDataTableHeight(); // <- updated!

                // maybe you need to redraw the table (not sure about this)
                oTable.fnDraw(false);
            });

            $('#download-experiment-design').button();

            $('#download-experiment-design').tooltip({content:"Download experiment design"});
        });

    </script>

</head>

<body>


<!-- old style start -->

<%@ include file="layout/old/header.jsp" %>


<!-- old style end -->

<div id="contents" class="page-contents">

    <c:import url="includes/experiment-header.jsp"/>

    <div id="table-caption"><b>Experiment Design</b></div>

    <div id="toolbar">Show Analysed only? <input type="checkbox" id="isOnlyAnalysed" checked="yes"/></div>

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

    <p/>
</div>

<!-- old style start -->

<%@ include file="layout/old/footer.jsp" %>
<!-- old style end -->

</body>

</html>