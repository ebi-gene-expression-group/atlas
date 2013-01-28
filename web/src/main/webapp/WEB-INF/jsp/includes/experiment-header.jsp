<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div id="helpContentTooltip" style='display:none'></div>

<div class="block">
    <table width="100%">
        <tbody>
        <tr>
            <td>
                <div class="experiment-accession" data-help-loc="#experimentAcc">
                    <a href="experiments/${experimentAccession}">${experimentAccession}</a>
                </div>
            </td>
            <td>
                <div id="experimentDescription" style="font-weight: bold;" data-help-loc="#experimentTitle">
                    ${experimentDescription}
                </div>
                <div>Organism: ${specie}</div>
            </td>
            <td style="float:right">
                <div>
                    <a id="display-analysis-methods" class="button-image"
                       href="experiments/${experimentAccession}/analysis-methods">
                        <img style="width:23px;height:23px" title="Analysis Methods"
                             src="resources/images/analysis_icon.png"/></a>

                    <a id="display-experiment-design" class="button-image"
                       title="Experiment Design" href="experiments/${experimentAccession}/experiment-design">
                        <img alt="Experiment Design"
                             src="resources/images/experiment_design_icon.png"/></a>

                    <a id="goto-ae" class="button-image"
                       href="${applicationProperties.getArrayExpressURL(experimentAccession)}" title="ArrayExpress"
                       target="_blank">
                        <img alt="ArrayExpress"
                             src="resources/images/ae2_icon.png"/></a>

                </div>
            </td>
        </tr>
        </tbody>

    </table>
</div>