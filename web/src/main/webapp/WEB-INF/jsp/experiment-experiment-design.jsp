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
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:if test="${not empty param.accessKey}">
    <c:set var="accessKeyQueryString" value="?accessKey=${param.accessKey}"></c:set>
</c:if>

<form:form method="get" commandName="preferences" id="prefForm">

    <div id="table-caption"><b>Experiment Design</b></div>

    <div id="toolbar" style="overflow: auto; padding-bottom: 5px">
        <div style="float: left">
            <span>Show analysed only?</span>
            <input type="checkbox" id="showOnlyAnalysedRuns" name="showOnlyAnalysedRuns" checked="yes"/>
        </div>

        <c:if test="${!type.isBaseline()}">
            <div style="float: right">
                <div style="display: inline-block">
                    <form:label path="selectedContrast" cssStyle="vertical-align: middle;">Comparison: </form:label>
                    <input type="hidden" name="accessKey" value="${param.accessKey}"/>
                    <form:select path="selectedContrast" items="${contrasts}" itemValue="id"
                                 itemLabel="displayName"/>
                </div>
                <div style="display: inline-block; text-align: right">
                    <span style="vertical-align: middle; padding-left: 10px">Reference:</span>
                    <span style="vertical-align: middle; background-color: #FFC266; width: 20px; display: inline-block;">&nbsp;</span>
                    <span style="vertical-align: middle; padding-left: 10px;">Test:</span>
                    <span style="vertical-align: middle; background-color: #82CDCD; width: 20px; display: inline-block;">&nbsp;</span>
                </div>
            </div>
        </c:if>
    </div>

    <table cellpadding="0" cellspacing="0" border="0" class="display" id="experiment-design-table"></table>

    <p></p>

    <div id="download-button"><a id="download-experiment-design-link" title="Download experiment design"
                                 class="gxaButtonImage" style="margin-bottom:5px"
                                 href="experiments/${experimentAccession}/experiment-design.tsv${accessKeyQueryString}" target="_blank">
        <img id="download-experiment-design" alt="Download experiment design"
             src="resources/images/download_blue_small.png"></a>
    </div>
    <div id="help-placeholder" style="display: none"></div>

</form:form>

<script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/resources/js/lib/datatables-1.9.4/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/resources/js/experimentDesignModule.js"></script>

<script>

    $(function () {
        clearLocalNav();
        $('#gxaLocalNavHome').addClass("active");
    });

    (function ($) {
        $(document).ready(function () {
            experimentDesignTableModule.init(${assayHeaders}, ${tableData}, ${runAccessions}, ${sampleHeaders}, ${factorHeaders});

            helpTooltipsModule.init('experiment-design', '${pageContext.request.contextPath}', '');

            <c:if test="${!type.isBaseline()}">
            $('#selectedContrast').change(function () {
                $('#prefForm').submit();
            });

            $('#experiment-design-table').find('tr').each(function (index) {
                var accession = $(this).find('td:first-child').text();
                if (jQuery.inArray(accession, ${referenceAssays}) > -1) {
                    $(this).find('td').css("background-color", "#FFC266");
                }
                if (jQuery.inArray(accession, ${testAssays}) > -1) {
                    $(this).find('td').css("background-color", "#82CDCD");
                }
            });

            </c:if>

            experimentDesignTableModule.adjustTableSize();
        });
    })(jQuery);

</script>
