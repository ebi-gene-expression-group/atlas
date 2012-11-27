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

        .analysed {
            font-weight: bold
        }
    </style>

    <!-- old style end -->

    <title>Experiment - experiment design</title>

    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/ui-lightness/jquery-ui-1.9.1.custom.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/table-grid.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/atlas.css">

    <style type="text/css" title="currentStyle">
        @import "${pageContext.request.contextPath}/resources/js/datatables-1.9.4/css/jquery.dataTables_themeroller.css";
        @import "${pageContext.request.contextPath}/resources/js/tabletools-2.1.4/css/TableTools.css";
    </style>

    <script type="text/javascript" language="javascript"
            src="${pageContext.request.contextPath}/resources/js/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" language="javascript"
            src="${pageContext.request.contextPath}/resources/js/datatables-1.9.4/js/jquery.dataTables.min.js"></script>
    <script type="text/javascript" charset="utf-8"
            src="${pageContext.request.contextPath}/resources/js/tabletools-2.1.4/js/TableTools.min.js"></script>
    <script type="text/javascript" charset="utf-8">
        /* Data set - loaded from experiment tsv file */
        var aDataSet = ${tableData};
        var aHeader = ${tableHeader};
        var aRunAccessions = ${runAccessions};
        var bShow = 1;

        $(document).ready(function () {
            $('#dynamic').html('<table cellpadding="0" cellspacing="0" border="0" class="display" id="example"></table>');
            var oTable = $('#example').dataTable({
                "aaData":aDataSet,
                "aoColumns":aHeader,
                "aLengthMenu":[
                    [10, 25, 50, -1],
                    [10, 25, 50, "All"]
                ],
                "iDisplayLength":25,
                "bJQueryUI":true,
                /*"sPaginationType": "full_numbers",*/
                "sDom":'<"toolbar">Tlfr<"clear">t<"button">ip',
                "oTableTools":{
                    "sSwfPath":"${pageContext.request.contextPath}/resources/js/tabletools-2.1.4/swf/copy_csv_xls_pdf.swf",
                    "aButtons":[ "copy", "xls", "print" ]
                },
                "fnRowCallback":function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
                    // Bold selected run accessions
                    if (bShow && jQuery.inArray(aData[0], aRunAccessions) > -1) {
                        $(nRow).addClass("analysed");
                    } else {
                        $(nRow).removeClass("analysed");
                    }
                    return nRow;
                }
            });
            $("div.toolbar").html('<b>Experiment Design</b>');
            $("div.button").html('<a id="togglebutton" class="button"><span style="display:none">Highlight Analysed</span><span>De-hightlight Analysed</span></a>');
            $('a#togglebutton').click(function () {
                $('span', this).toggle();
                bShow = 1 - bShow;
                oTable.fnDraw();
            });
        });
    </script>

</head>

<body>


<!-- old style start -->

<%@ include file="layout/old/header.jsp" %>


<!-- old style end -->

<div id="contents" class="page-contents">

    <c:import url="includes/experiment-header.jsp"/>

    <div id="dynamic"></div>

    <p/>

</div>

<!-- old style start -->

<%@ include file="layout/old/footer.jsp" %>
<!-- old style end -->

</body>

</html>