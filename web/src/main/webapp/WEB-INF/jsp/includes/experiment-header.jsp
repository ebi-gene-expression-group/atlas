<div class="block">
    <table width="100%">
        <tbody>
        <tr>
            <td>
                <div class="experiment-accession">
                    <a href="experiments/${experimentAccession}">${experimentAccession}</a>
                </div>
            </td>
            <td>
                <div id="experimentDescription" style="font-weight: bold;">
                    ${experimentDescription}
                </div>
                <div>Organism: ${specie}</div>
            </td>
            <td style="float:right">
                <div>
                    <a id="display-analysis-methods" href="experiments/${experimentAccession}-analysis-methods">
                        <img style="width:23px; height:23px" class="button-image"
                             title="Analysis Methods"
                             src="resources/images/analysis_icon.png" id="analysis-button"/></a>

                    <a id="display-experiment-design" href="experiments/${experimentAccession}-experiment-design">
                        <img class="button-image" title="Experiment Design"
                             src="resources/images/experiment_design_icon.png" id="experiment-design-button"/></a>

                    <a id="goto-ae" href="${applicationProperties.getArrayExpressURL(experimentAccession)}" target="_blank">
                        <img class="button-image" title="ArrayExpress"
                             src="resources/images/ae2_icon.png" id="ae2-button"/></a>

                </div>
                <script type="text/javascript">
                    (function ($) { //self invoking wrapper function that prevents $ namespace conflicts
                        $(document).ready(function () {
                            $('#analysis-button').button();
                            $('#experiment-design-button').button();
                            $('#ae2-button').button();
                        });

                    })(jQuery);
                </script>
            </td>
        </tr>
        </tbody>

    </table>
</div>