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

<form:form method="get" commandName="preferences" id="prefForm">

    <div id="table-caption"><b>Experiment Design</b></div>

    <div id="toolbar">Show Analysed only? <input type="checkbox" id="isOnlyAnalysed" name="isOnlyAnalysed"
                                                 checked="yes"/>
        <c:if test="${type eq 'DIFFERENTIAL'}">
            <div>
                <form:select path="selectedContrast" items="${contrasts}" itemValue="id" itemLabel="displayName"
                             cssStyle="width:300px"/>
            </div>
        </c:if>
    </div>

    <table cellpadding="0" cellspacing="0" border="0" class="display" id="experiment-design-table">

    </table>

    <p></p>

    <div id="download-button"><a id="download-experiment-design-link" title="Download experiment design"
                                 class="button-image" style="margin-bottom:5px"
                                 href="experiments/${experimentAccession}/experiment-design.tsv" target="_blank">
        <img id="download-experiment-design" alt="Download experiment design"
             src="resources/images/download_blue_small.png"></a>
    </div>
    <div id="help-placeholder" style="display: none"></div>

</form:form>

<script type="text/javascript" language="javascript"
        src="${pageContext.request.contextPath}/resources/js/datatables-1.9.4/js/jquery.dataTables.min.js"></script>
<script language="JavaScript" type="text/javascript"
        src="${pageContext.request.contextPath}/resources/js/chosen/chosen.jquery.min.js"></script>
<script type="text/javascript" language="javascript"
        src="${pageContext.request.contextPath}/resources/js/experimentDesignModule.js"></script>

<script>

    $(function () {
        clearLocalNav();
        $('#local-nav-home').addClass("active");
    });

    (function ($) {
        $(document).ready(function () {
            experimentDesignTableModule.init('${assayHeader}', ${tableData}, ${runAccessions}, ${samples}, ${factors}, "Select contrast");

            helpTooltipsModule.init('experiment-design');

            <c:if test="${type eq 'DIFFERENTIAL'}">
            $("#selectedContrast").chosen();

            $('#selectedContrast').change(function () {
                $('#prefForm').submit();
            });
            </c:if>
        });
    })(jQuery);

</script>
