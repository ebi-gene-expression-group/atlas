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
 * @class Biojs.AtlasHeatmap
 * @extends Biojs
 *
 * @author <a href="mailto:atlas-developers@ebi.ac.uk">ExpressionAtlas Team</a>
 * @version 1.0.2
 * @category 1
 *
 * @requires <a href='http://code.jquery.com/jquery-1.6.4.js'>jQuery Core 1.6.4</a>
 * @dependency <script language="JavaScript" type="text/javascript" src="../biojs/dependencies/jquery/jquery-1.6.4.min.js"></script>
 *
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
 * @example
 * var instance = new Biojs.Heatmap({
 * featuresUrl: '/gxa/widgets/heatmap/protein?geneQuery=P00846',
 * target : "YourOwnDivId"
 * });
 *
 */

/*global jQuery,React */
Biojs.AtlasHeatmap = Biojs.extend({

    constructor:function () {

        var self = this;

        var containerDiv = jQuery("#" + self.opt.target);
        containerDiv.empty();

        var opt = self.opt;

        var endpoint = opt.heatmapUrl ? opt.heatmapUrl : opt.isMultiExperiment ? '/widgets/heatmap/multiExperiment' : '/widgets/heatmap/referenceExperiment';
        var url = opt.gxaBaseUrl + endpoint + '?' + opt.params;

        var httpRequest = {
            url: url,
            dataType: "json",
            method:"GET",
            beforeSend:function () {
                // TODO: nasty workaround for http://youtrack.jetbrains.com/issue/IDEA-25934 (still not fixed)
                var resource_host = ("${resources.host}" === "\${resources.host}") ? "wwwdev.ebi.ac.uk" : "${resources.host}";
                containerDiv.html("<img src='http://" + resource_host + "/gxa/resources/images/loading.gif' />");
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
                self.drawHeatmap(data, opt.target, opt.heatmapClass, heatmapModule.buildMultiExperiment);
            } else {
                self.drawHeatmap(data, opt.target, opt.heatmapClass, heatmapModule.buildBaseline);
            }
        }).fail(function (jqXHR, textStatus, errorThrown) {
            Biojs.console.log("ERROR: " + jqXHR.status);
            //containerDiv.html("An error occurred while retrieving the data: " + jqXHR.status + " - " + jqXHR.statusText);
            if (textStatus === "parsererror") {
                containerDiv.html("<div class='error'>Could not parse JSON response</div>");
            } else {
                containerDiv.html(jqXHR.responseText);
            }
        });

    },

    drawHeatmap:function (data, target, heatmapClass, heatmapBuilder) {

        (function ($, React, HeatmapContainer, heatmapBuilder, heatmapConfig, columnHeaders, profiles, geneSetProfiles, anatomogramData, experimentData) {

            $(document).ready(function () {
                // call this inside ready() so all scripts load first in IE8
                var Heatmap = heatmapBuilder(heatmapConfig).Heatmap;

                React.renderComponent(HeatmapContainer({Heatmap: Heatmap, isWidget: true, heatmapClass: heatmapClass, experiment: experimentData, anatomogram: anatomogramData, columnHeaders: columnHeaders, profiles: profiles, geneSetProfiles: geneSetProfiles}),
                    document.getElementById(target)
                );

                // load anatomogram after heatmap is rendered so wiring works
                if (anatomogramData) {
                    anatomogramModule.init(anatomogramData.allSvgPathIds, anatomogramData.maleAnatomogramFile, anatomogramData.femaleAnatomogramFile, anatomogramData.contextRoot, heatmapConfig.species);
                }
            });

        })(jQuery, React, HeatmapContainer, heatmapBuilder, data.config,
            data.columnHeaders, data.profiles, data.geneSetProfiles, data.anatomogram, data.experiment);

    },

    opt:{
        gxaBaseUrl: 'http://www.ebi.ac.uk/gxa',
        /* params
         This mandatory parameter consists of the query for a particular
         gene or genes by given their properties. For a single gene query,
         please use a unique accession (e.g. ENSEMBL gene id or UniProt id).
         For example search with UniProt id P00846 returns the gene mt-atp6,
         a search for REACT_6900 returns genes belonging to this pathway.
         For multiple identifiers of the same species please use:
         geneQuery=ENSG00000187003+ENSG00000185264&propertyType=identifier
         or when using a gene name that exists for multiple species, specify
         the species, eg: geneQuery=ACTL7A&species=homo+sapiens
         */
        params:'geneQuery=REACT_1309',
        /* Target DIV
         This mandatory parameter is the identifier of the DIV tag where the
         component should be displayed. Use this value to draw your
         component into. */
        target:"YourOwnDivId"
    }
});
