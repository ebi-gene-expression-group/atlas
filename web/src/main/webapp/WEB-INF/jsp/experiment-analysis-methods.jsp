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

<div class="gxaBlock">
    <display:table name="${csvLines}" id="csvLine" htmlId="methods-table" class="gxaAnalysisGrid">
        <display:caption>
            <div style="text-align:left;padding-top:10px; padding-bottom:5px">
                <label>
                    Analysis Methods:
                </label>
            </div>
        </display:caption>
        <display:column class="gxaHorizontalHeaderCell">
            <label>
                    ${csvLine[0]}
            </label>
        </display:column>
        <display:column>
            ${csvLine[1]}
        </display:column>
    </display:table>
</div>

<script type="text/javascript">
    $(function () {
        clearLocalNav();
        $('#gxaLocalNavHome').addClass("active");
    });
</script>
