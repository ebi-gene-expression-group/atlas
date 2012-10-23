<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div id="preferencesFormBlock" class="block">
    <form:form method="get" commandName="preferences" id="prefForm">
        <form:hidden path="heatmapMatrixSize"/>
        <form:errors id="heatmapMatrixSize" title="HeatmapMatrixSize" path="heatmapMatrixSize"
                     cssClass="error"/>
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
                    <form:textarea path="geneIDsString" rows="3" cols="35"></form:textarea>
                    <form:errors id="geneIDsString" title="geneIDsString" path="geneIDsString" cssClass="error"/>
                </td>
                <td>
                    <form:select path="organismParts" data-placeholder="" tabindex="-1"
                                 items="${applicationProperties.organismParts}" cssClass="chzn-select"
                                 cssStyle="width:350px;display:none"/>
                    <form:errors id="organismParts" title="organismParts" path="organismParts" cssClass="error"/>
                </td>
                <td>
                    <form:input size="10" path="cutoff" id="cutoff" style="border:1; font-weight:bold;"/>
                    <form:errors path="cutoff" cssClass="error"/>
                </td>
            </tr>
        </table>
        <br/>

        <div id="gene-distribution" class="block" style="margin-bottom:4px;height:100px"></div>
        <div id="slider-range-max" style="font-size:65%;margin-left:33px;margin-right:10px"></div>

    </form:form>
</div>
