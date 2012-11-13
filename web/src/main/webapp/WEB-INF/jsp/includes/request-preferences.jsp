<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div id="preferencesFormBlock" class="block">
    <form:form method="get" commandName="preferences" id="prefForm">
        <form:hidden path="heatmapMatrixSize"/>
        <form:hidden id="displayLevels" path="displayLevels"/>
        <form:errors title="HeatmapMatrixSize" path="*" cssClass="error"/>
        <table class="form-grid">
            <tr>
                <td>
                    <form:label path="geneIDsString">Gene IDs (max 10)</form:label>
                </td>
                <td>
                    <form:label path="organismParts">Organism parts</form:label>
                </td>
                <td>
                    <form:label path="cutoff">Expression level cutoff</form:label>
                </td>
                <td rowspan="4">
                    <input id="submit-button" type="submit" value="Update"/>
                    <br/>
                    <input id="reset-button" type="button" value="Reset"/>
                </td>
            </tr>
            <tr>
                <td>
                    <form:textarea path="geneIDsString" rows="2" cols="35"></form:textarea>
                </td>
                <td>
                    <form:select path="organismParts" data-placeholder="(all organism parts)" tabindex="-1"
                                 items="${allOrganismParts}" cssClass="chzn-select"
                                 cssStyle="width:350px;display:none"/>
                </td>
                <td>
                    <c:choose>
                        <c:when  test="${fn:endsWith('' + preferences.cutoff, '.0')}" >
                            <fmt:formatNumber value="${preferences.cutoff}" groupingUsed="false"
                                              type="number"
                                              maxFractionDigits="0"
                                              var="formattedCutoff" />
                            <form:input size="10" path="cutoff" value="${formattedCutoff}" id="cutoff" style="border:1; font-weight:bold;"/>
                        </c:when>
                        <c:otherwise>
                            <form:input size="10" path="cutoff" id="cutoff" style="border:1; font-weight:bold;"/>
                        </c:otherwise>
                     </c:choose>
                </td>
            </tr>
        </table>
        <br/>

        <div id="gene-distribution" class="block" style="margin-bottom:4px;height:100px"></div>
        <div id="slider-range-max" style="font-size:65%;margin-left:27px;margin-right:17px"></div>

    </form:form>
</div>
