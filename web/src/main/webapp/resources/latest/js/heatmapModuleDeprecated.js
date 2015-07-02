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

/*global $,jQuery,console,loadSliderAndPlot: false */
/*jslint browser:true */
/*jslint nomen: true*/

var heatmapModuleDeprecated = (function ($) {

    "use strict";

    var _contextRoot;

    function showCellText(div) {
        $(div).removeClass("gxaHideCell").addClass("gxaShowCell");
    }

    function hideCellText(div) {
        $(div).removeClass("gxaShowCell").addClass("gxaHideCell");
    }

    function showExpressionLevels($heatmap) {
        $heatmap("div[data-color]").each(function () {
            showCellText(this);
        });
        $heatmap(".gxaGradientLevelMin").css("visibility", "visible");
        $heatmap(".gxaGradientLevelMax").css("visibility", "visible");
        $heatmap(".gxaGradientLevelMax").css("display", "table-cell");
    }

    function hideExpressionLevels($heatmap) {
        $heatmap("div[data-color]").each(function () {
            hideCellText(this);
        });
        $heatmap(".gxaGradientLevelMin").css("visibility", "hidden");
        $heatmap(".gxaGradientLevelMax").css("visibility", "hidden");
        $heatmap(".gxaGradientLevelMax").css("display", "none");
    }

    function initGeneSetLinkOnClick() {
        $("#showGeneSetProfiles").click(function () {
            $("#heatmap-div").hide();
            $("#heatmap-profilesAsGeneSets").show();
        });
        $("#showIndividualGenes").click(function () {
            $("#heatmap-profilesAsGeneSets").hide();
            $("#heatmap-div").show();
        });
    }

    function initDisplayLevelsButtonOnClick(heatmapElement, prefFormDisplayLevelsInputElement) { //binds toggle handler
        var $heatmap = contextFactory(heatmapElement);

        // hacky!
        function syncDisplayLevelButtonOnOtherHeatmap(syntheticEvent) {
            if (!syntheticEvent) {
                // don't want to get stuck in an infinite loop,
                // so click other button only in response to original click
                // not the synthetic event we generate here
                $('.display-levels-button').not(this).trigger("click", "syntheticEvent")
            }
        }

        $heatmap("#display-levels, #diffresults-display-levels").button()
            .toggle(
            function (eventObject, syntheticEvent) {
                $(this).button('option', 'label', $("#buttonText").attr('pressedtext'));
                showExpressionLevels($heatmap);
                prefFormDisplayLevelsInputElement.val("true");

                syncDisplayLevelButtonOnOtherHeatmap.call(this, syntheticEvent);
            },
            function (eventObject, syntheticEvent) {
                $(this).button('option', 'label', $("#buttonText").attr('unpressedtext'));
                hideExpressionLevels($heatmap);
                prefFormDisplayLevelsInputElement.val("false");

                syncDisplayLevelButtonOnOtherHeatmap.call(this, syntheticEvent);
            }
        );

        $heatmap("#display-levels, #diffresults-display-levels").button({ label:$("#buttonText").attr('unpressedtext') });

        if (prefFormDisplayLevelsInputElement.val() === "true") {
            $heatmap("#display-levels, #diffresults-display-levels").trigger("click", "syntheticEvent")
        }

    }

    //there must be a cleaner way to do this, but I don't know it yet!
    function buildHeatmapCellTooltip(pValue, tstatistic, foldChange) {
        return "<table class='gxaTableGrid' style='margin: 0px; padding: 0px;'><thead><th class='gxaHeaderCell'>Adjusted <i>p</i>-value</th>" +
            (tstatistic !== undefined ? "<th class='gxaHeaderCell'><i>t</i>-statistic</th>" : "") +
            "<th class='gxaHeaderCell'>Log<sub>2</sub>-fold change</th></thead>" +
            "<tbody><tr><td style='padding:6px'><span style=\"white-space: nowrap;\">" + pValue + "</span></td>" +
            (tstatistic !== undefined ? "<td style='padding:6px'>" + tstatistic + "</td>" : "") +
            "<td style='padding:6px'>" + foldChange + "</td></tr></tbody>" +
            "</table>";
    }

    function initDifferentialHeatmapCellsTooltip() {
        $heatmap("#heatmap-table td:has(div[data-pValue])").attr('title', '').tooltip(
            {
                open:function (event, ui) {
                    var colour = $(this).find("div").attr("data-color");
                    ui.tooltip.css('background', colour);
                },
                tooltipClass:"gxaHelpTooltip pvalue-tooltip-styling",

                content:function () {
                    var foldChange = $(this).find("div").html(),
                        pValue = $(this).find("div").attr("data-pValue"),
                        tstatistic = $(this).find("div").attr("data-tstatistic");

                    return buildHeatmapCellTooltip(pValue, tstatistic, foldChange);
                }

            });
    }

    function initDownloadButtonTooltip() {
        $heatmap('#download-profiles-link').button().tooltip();
    }

    function restrictLabelSize(label, maxSize) {
        var result = label;
        if (result.length > maxSize) {
            result = result.substring(0, maxSize);
            if (result.lastIndexOf(" ") > maxSize - 5) {
                result = result.substring(0, result.lastIndexOf(" "));
            }
            result = result + "...";
        }
        return result;
    }

    function createHeatmapFactorHeaders() {// shorten/rotate header labels

        $heatmap(".factor-header")
            .each(function () {
                if ($.browser.msie) {
                    $(this).append($(this).attr("data-organism-part"));
                    $heatmap("div", "th", "#heatmap-table").addClass('gxaRotateTextIE').removeClass('rotate_text');
                    $heatmap("th", "#heatmap-table").addClass('heatmap td').removeClass('rotated_cell');
                } else {
                    var organismPartName = $(this).attr("data-organism-part");
                    organismPartName = restrictLabelSize(organismPartName, 17);
                    $(this).append(organismPartName);
                }
            });

    }

    function insertFirstColumnHeaders(accessionHeaders) {

        var headers = "";

        $(accessionHeaders).each(function () {
            headers += "<td class='gxaHorizontalHeaderCell'>" + this + "</td>";
        });

        //add header cells for gene name and design element (if any)
        //NB: this is subsequently removed when the heatmap is loaded by heatmap-matrix-searchresults-diffanalytics.jsp
        $heatmap("#heatmap-table thead").append("<tr id='injected-header'>" + headers + "</tr>");

        //add display levels cell colspan
        if (accessionHeaders.length === 2) {

            $heatmap("#heatmap-table thead tr th:eq(1)").remove();
            $heatmap("#heatmap-table thead tr th:eq(0)").attr("colspan", 2);

        }
        //add rowspan to factor headers
        $heatmap("#heatmap-table thead tr th:gt(0)").attr("rowspan", 2);
    }

    function initMaPlotButtons(experimentAccession) {
        var thElements = $heatmap(".factor-header").parent(),
            maPlotURL;

        thElements.css("width", "60px");
        $heatmap(".factor-header").css("transform-origin");
        $heatmap(".factor-header").css("top", "57px");

        $(thElements).each(function () {
            var contrastId = $(this).children().attr("data-contrast-id");

            var arrayDesignAccession = $(this).children().attr("data-array-design");

            maPlotURL = 'external-resources/' + experimentAccession + '/' + (arrayDesignAccession ? arrayDesignAccession + '/' : '' ) + contrastId + '/ma-plot.png';
            //append a button div now
            $(this).append("<div style='text-align:right;padding-right:3px'><a href='" + maPlotURL + "' class='ma-button gxaButtonImage' title='Click to view MA plot for the comparison across all genes'><img src='resources/latest/images/maplot-button.png'/></a></div>");

        });

        $heatmap(".ma-button").tooltip().button();

        $heatmap(".ma-button").fancybox({
            padding:0,
            openEffect:'elastic',
            closeEffect:'elastic'
        });

    }

    function contextFactory(ctx) {
        return function(selector) {
            return $(selector, ctx);
        };
    }

    var $heatmap; // stores current heatmap element. allows us to have multiple heatmaps on the same page

    function initHeatmap(experimentAccession, parameters, heatmapElementId, isHidden) {
        var prefFormDisplayLevelsInputElement = $("#prefForm").find("#displayLevels");
        var heatmapElement = $('#' + heatmapElementId);
        $heatmap = contextFactory(heatmapElement);

        $heatmap('#heatmap-table th:first').addClass('gxaHorizontalHeaderCell'); //because displaytag doesn't let us configure TH cells...

        initDifferentialHeatmapCellsTooltip();
        initDownloadButtonTooltip();
        initDisplayLevelsButtonOnClick(heatmapElement, prefFormDisplayLevelsInputElement);
        initGeneSetLinkOnClick();
        createHeatmapFactorHeaders();

        var geneNameHeader = parameters.asGeneSets ? "Gene set" : "Gene";
        insertFirstColumnHeaders(parameters.isMicroarray ? [geneNameHeader, "Design Element"] : [geneNameHeader]);

        if (experimentAccession !== undefined && parameters.cutoff === 0.05 && !parameters.geneQuery) {
            initMaPlotButtons(experimentAccession);
        }

        if (!isHidden) {
            heatmapElement.show();
        }
    }

    function initBaselineHeatmap(experimentAccession, species, serializedFilterFactors, asGeneSets, contextRoot, heatmapElementId, isHidden) {
        _contextRoot = contextRoot;
        
        initHeatmap(experimentAccession, {
            species:species,
            serializedFilterFactors:serializedFilterFactors,
            asGeneSets:asGeneSets
        }, heatmapElementId, isHidden);
    }

    function initRnaSeqHeatmap(experimentAccession, cutoff, geneQuery, heatmapElementId) {
        initHeatmap(experimentAccession, {cutoff:cutoff, geneQuery:geneQuery}, heatmapElementId);
    }

    function initMicroarrayHeatmap(experimentAccession, cutoff, geneQuery, heatmapElementId) {
        initHeatmap(experimentAccession, {cutoff:cutoff, geneQuery:geneQuery, isMicroarray:true}, heatmapElementId);
    }

    return {

        initBaselineHeatmap:initBaselineHeatmap,
        initRnaSeqHeatmap:initRnaSeqHeatmap,
        initMicroarrayHeatmap:initMicroarrayHeatmap,
        initDisplayLevelsButtonOnClick:initDisplayLevelsButtonOnClick

    };

}(jQuery));
