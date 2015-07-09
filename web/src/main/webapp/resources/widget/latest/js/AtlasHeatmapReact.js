/*
 * Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * For further details of the Gene Expression Atlas project, including source code,
 * downloads and documentation, please see:
 *
 * http://gxa.github.com/gxa
 */

/**
 * This is the description of the AtlasHeatmap component for displaying
 * baseline expression of genes based on RNA-seq experiments in the ExpressionAtlas
 * database.
 *
 * @class AtlasHeatmap
 * @param {Object} options An object with the options for AtlasHeatmap component.
 *
 * @option {string} featuresUrl
 *    The query URL pointing to the ExpressionAtlas for retrieving gene page results
 *    displayed as part of this widget. It is usually composed to include the identifier
 *    of the gene you are interested in, see example.
 *
 * @option {string} target
 *    Identifier of the DIV tag where the component should be displayed.
 *
 * @option {string} rootContext
 *    Specifies the root context path to be used by the widget content,
 *    i.e. this is the location of the content proxy for Ajax calls
 *
 */

/*global jQuery,React */
var AtlasHeatmapModule =  (function (){

    function build (opt) {

        var targetElement = (typeof opt.target == 'string') ? document.getElementById(opt.target) : opt.target;
        var $targetElement = jQuery(targetElement);

        var endpoint = opt.heatmapUrl ? opt.heatmapUrl : opt.isMultiExperiment ? '/widgets/heatmap/multiExperiment' : '/widgets/heatmap/referenceExperiment';
        var url = opt.gxaBaseUrl + endpoint + '?' + opt.params;

        var httpRequest = {
            url: url,
            dataType: "json",
            method:"GET",
            beforeSend:function () {
                // TODO: nasty workaround for http://youtrack.jetbrains.com/issue/IDEA-25934 (still not fixed)
                var resource_host = ("${resources.host}" === "\${resources.host}") ? "wwwdev.ebi.ac.uk" : "${resources.host}";
                $targetElement.html("<img src='http://" + resource_host + "/gxa/resources/images/loading.gif' />");
            }
        };

        jQuery.ajax(httpRequest).done(function (data) {

            function overrideContextRoot(data, gxaBaseUrl) {
                data.config.contextRoot = gxaBaseUrl;

                if (data.anatomogram) {
                    data.anatomogram.contextRoot = gxaBaseUrl;
                }

                if (data.experiment) {
                    data.experiment.contextRoot = gxaBaseUrl;
                }
            }

            overrideContextRoot(data, opt.gxaBaseUrl);

            if (opt.isMultiExperiment) {
                drawHeatmap(data, targetElement, opt.heatmapClass, heatmapModule.buildMultiExperiment, opt.heatmapKey);
            } else {
                drawHeatmap(data, targetElement, opt.heatmapClass, heatmapModule.buildBaseline);
            }
        }).fail(function (jqXHR, textStatus, errorThrown) {
            //containerDiv.html("An error occurred while retrieving the data: " + jqXHR.status + " - " + jqXHR.statusText);
            if (textStatus === "parsererror") {
                $targetElement.html("<div class='error'>Could not parse JSON response</div>");
            } else {
                $targetElement.html(jqXHR.responseText);
            }
        });

    };

    function drawHeatmap (data, targetElement, heatmapClass, heatmapBuilder, heatmapKey) {

        (function ($, React, HeatmapContainer, heatmapBuilder, heatmapConfig, columnHeaders, profiles, geneSetProfiles, anatomogramData, experimentData, heatmapKey) {

            $(document).ready(function () {
                // call this inside ready() so all scripts load first in IE8
                var Heatmap = heatmapBuilder(heatmapConfig).Heatmap;

                React.renderComponent(HeatmapContainer({Heatmap: Heatmap, isWidget: true, heatmapClass: heatmapClass, experiment: experimentData, anatomogram: anatomogramData,
                        columnHeaders: columnHeaders, profiles: profiles, geneSetProfiles: geneSetProfiles, heatmapKey: heatmapKey, heatmapConfig:heatmapConfig}),
                    targetElement
                );

                // load anatomogram after heatmap is rendered so wiring works
                if (anatomogramData) {
                    anatomogramModule.init(anatomogramData.allSvgPathIds, anatomogramData.maleAnatomogramFile, anatomogramData.femaleAnatomogramFile,
                        anatomogramData.brainAnatomogramFile, anatomogramData.contextRoot, heatmapConfig.species, heatmapConfig.isSingleGene, heatmapKey);
                }
            });

        })(jQuery, React, HeatmapContainer, heatmapBuilder, data.config,
            data.columnHeaders, data.profiles, data.geneSetProfiles, data.anatomogram, data.experiment, heatmapKey);

    };

    return {
        build: build
    };

}());
