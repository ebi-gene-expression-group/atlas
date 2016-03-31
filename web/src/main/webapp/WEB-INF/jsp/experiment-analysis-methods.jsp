<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="f" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/experiment-analysis-methods.css">

<div class="gxaBlock grid_24 gxaNewSection">
    <display:table name="${csvLines}" id="csvLine" htmlId="methods-table" class="gxaAnalysisGrid">
        <display:caption>
            <div style="text-align:left;padding-top:10px; padding-bottom:5px">
                <label style="font-weight: bold">
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
