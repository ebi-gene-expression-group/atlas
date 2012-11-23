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
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/table-grid.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/atlas.css">

    <style type="text/css" title="currentStyle">
        @import "${pageContext.request.contextPath}/resources/datatables/css/demo_page.css";
        @import "/datatables/css/header.ccss";
        @import "${pageContext.request.contextPath}/resources/datatables/css/demo_table.css";
    </style>
    <script type="text/javascript" language="javascript"
            src="${pageContext.request.contextPath}/resources/datatables/js/jquery.js"></script>
    <script type="text/javascript" language="javascript"
            src="${pageContext.request.contextPath}/resources/datatables/js/jquery.dataTables.js"></script>
    <script type="text/javascript" charset="utf-8">
        /* Data set - can contain whatever information you want */
        var aDataSet = ${tableData};

        $(document).ready(function () {
            $('#dynamic').html('<table cellpadding="0" cellspacing="0" border="0" class="display" id="example"></table>');
            $('#example').dataTable({
                "aaData":aDataSet,
                "aoColumns":[
                    { "sTitle":"Assay" },
                    { "sTitle":"Characteristics[organism]", "sClass":"center" },
                    { "sTitle":"Characteristics[age]", "sClass":"center" },
                    { "sTitle":"Characteristics[sex]", "sClass":"center" },
                    { "sTitle":"Characteristics[biosource_provider]", "sClass":"center" },
                    { "sTitle":"Factor[organism_part]", "sClass":"center" },
                    { "sTitle":"Factor[library_preparation_method]", "sClass":"center" },
                    { "sTitle":"Factor Value[phenotype]", "sClass":"center" }
                ]
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

    <div class="block">

        <div style="text-align:left;padding-top:10px; padding-bottom:5px">
            <label>
                Experiment Design:
            </label>
        </div>


        <div id="dynamic"></div>
    </div>

</div>

<!-- old style start -->

<%@ include file="layout/old/footer.jsp" %>
<!-- old style end -->

</body>

</html>