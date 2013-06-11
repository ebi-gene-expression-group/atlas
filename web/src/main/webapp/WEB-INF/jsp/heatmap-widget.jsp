<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="base" value="${pageContext.request.contextPath}"/>
<c:if test="${not empty preferences.rootContext}">
    <c:set var="base" value="${preferences.rootContext}"/>
</c:if>

<div id="content" class="block">
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
</div>

<script>

    (function ($) { //self invoking wrapper function that prevents $ namespace conflicts

        $(document).ready(function () {

            var anyAnatomogramFile = "${maleAnatomogramFile}" + "${femaleAnatomogramFile}"

            //ToDo: this should be replaced with a JSON array directly sent from backend layer
            var allQueryFactorValues = [${allQueryFactors.size()}];
            <c:forEach varStatus="i" var="queryFactor" items="${allQueryFactors}">
            allQueryFactorValues[${i.index}] = "${type == 'BASELINE' ? queryFactor.value : queryFactor.displayName}";
            </c:forEach>

            if (anyAnatomogramFile && 0 < anyAnatomogramFile.length) {
                anatomogramModule.init(allQueryFactorValues, '${maleAnatomogramFile}', '${femaleAnatomogramFile}', '${base}');
            }

            helpTooltipsModule.init('experiment', '${base}');

            $("#goto-ae").tooltip();
            $("#goto-experiment").tooltip();

            //configurations required for any browser...

            if (!anyAnatomogramFile || 0 === anyAnatomogramFile.length) {
                $("#anatomogram").remove();//remove the anatomogram
                $("#heatmap-div").removeClass();
            }

        });

    })(jQuery);

</script>