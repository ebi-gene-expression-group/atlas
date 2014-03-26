<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="base" value="${pageContext.request.contextPath}"/>
<c:if test="${not empty preferences.rootContext}">
    <c:set var="base" value="${preferences.rootContext}"/>
</c:if>

<div id="atlas-content" class="block">
    <table width="100%">
        <tbody>
        <tr>
            <%@ include file="includes/experiment-description.jsp" %>
        </tr>
        </tbody>
    </table>

    <%@ include file="includes/anatomogram-and-heatmap.jsp" %>

    <br/>

    <div id="help-placeholder" style="display: none"></div>

    <div id="queryFactorType" value="${queryFactorType}"></div>

    <div id="transcript-breakdown" style="display:none;height: 320px;width: 500px; padding-top:10px">
        <p style="text-align: center">
            <span id="transcript-breakdown-title"></span>
            <span id="transcript-breakdown-title-help">
                <a class="help-icon" href="#"
                   title="Transcripts with zero expression are excluded from the pie chart. Transcripts shown in white colour have been reported with low confidence.">?</a>
            </span>
        </p>

        <div>
            <div id="transcripts-pie" style="width: 500px;height:250px;">
            </div>
        </div>
    </div>

</div>

<script>

    (function ($) { //self invoking wrapper function that prevents $ namespace conflicts

        $(document).ready(function () {

            var anyAnatomogramFile = "${maleAnatomogramFile}" + "${femaleAnatomogramFile}"

            //ToDo: this should be replaced with a JSON array directly sent from backend layer
            var allQueryFactorValues = [${allQueryFactors.size()}];
            <c:forEach varStatus="i" var="queryFactor" items="${allQueryFactors}">
            allQueryFactorValues[${i.index}] = "${type.isBaseline() ? queryFactor.valueOntologyTerm : queryFactor.displayName}";
            </c:forEach>

            if (anyAnatomogramFile && 0 < anyAnatomogramFile.length) {
                anatomogramModule.init(allQueryFactorValues, '${maleAnatomogramFile}', '${femaleAnatomogramFile}', '${base}');
            }

            helpTooltipsModule.init('experiment', '${base}', 'heatmap');

            $("#goto-ae").tooltip();
            $("#goto-experiment").tooltip();
            $('.pubmed-id').tooltip();

            //configurations required for any browser...

            if (!anyAnatomogramFile || 0 === anyAnatomogramFile.length) {
                $("#anatomogram").remove();//remove the anatomogram
                $("#heatmap-div").removeClass();
            }

        });

    })(jQuery);

</script>