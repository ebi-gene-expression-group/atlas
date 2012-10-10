<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div id="reload" class="block">
    <form:form method="get" commandName="preferences">
        <form:hidden path="heatmapMatrixSize"/>
        <form:hidden path="rankingSize"/>
        <form:errors id="heatmapMatrixSize" title="HeatmapMatrixSize" path="heatmapMatrixSize"
                     cssClass="error"/>
        <div>
            <form:label path="geneIDsString">Gene IDs (max 10)</form:label>
            <form:textarea path="geneIDsString" rows="3" size="30"></form:textarea>
        </div>
        <div>
            <form:select path="organismParts" data-placeholder="Select expressions specific to ..." tabindex="-1" items="${applicationProperties.organismParts}" cssClass="chzn-select" cssStyle="width:400px;display:none"/>
        </div>
        <div class="slider">
            <table>
                <tr>
                    <td>
                        <form:label path="cutoff">Expression Level Cutoff</form:label>
                        <form:input size="10" path="cutoff" id="cutoff"
                                    style="border:1; font-weight:bold;"/>
                        <form:errors path="cutoff" cssClass="error"/>
                    </td>
                    <td>
                        <input type="submit" value="Reload Page"/>
                    </td>
                </tr>
            </table>
            <div></div>
            <div id="slider-range-max" style="width:600px;"></div>

        </div>
    </form:form>
</div>

<script>

    (function ($) {
        $(function () {
            // The result should be between 0 (but log(0) is NaN) and max FPKM
            var minv = Math.log(0.001);
            var maxv = Math.log(71047.7);

            // position will be between 0 and 100
            var minp = 0;
            var maxp = 100;

            $("#slider-range-max").slider({
                range:"max",
                min:minp,
                max:maxp,

                value:logposition(${preferences.cutoff}),

                slide:function (event, ui) {
                    $("#cutoff").val(logslider(ui.value));
                },
                change:function (event, ui) {
                    location.replace('${pageContext.request.contextPath}/experiment?cutoff='
                            + logslider(ui.value) +
                            '&rankingSize=${preferences.rankingSize}&heatmapMatrixSize=${preferences.heatmapMatrixSize}');

                }
            });

            function logslider(position) {

                // calculate adjustment factor
                var scale = (maxv - minv) / (maxp - minp);

                return Number(Math.exp(minv + scale * (position - minp))).toFixed(3);
            }

            function logposition(value) {
                var scale = (maxv - minv) / (maxp - minp);
                return (Math.log(value) - minv) / scale + minp;
            }

            $(".chzn-select").chosen();

        });
    })(jQuery);

</script>
