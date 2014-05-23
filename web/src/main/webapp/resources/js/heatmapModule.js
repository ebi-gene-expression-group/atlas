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

var heatmapModule = (function ($) {

    "use strict";

    var _transcriptUrlRoot;
    
    function showTranscriptBreakdownFancyBox() {
        $.fancybox({
                href:'#transcript-breakdown',
                padding:0,
                openEffect:'elastic',
                closeEffect:'elastic',
                helpers:{
                    overlay:{
                        locked:false
                    }
                }
            }
        );
    }

    function showCellText(div) {
        $(div).removeClass("hide_cell").addClass("show_cell");
    }

    function hideCellText(div) {
        $(div).removeClass("show_cell").addClass("hide_cell");
    }

    function showExpressionLevels($heatmap) {
        $heatmap("div[data-color]").each(function () {
            showCellText(this);
        });
        $heatmap(".gradient-level-min").attr("style", 'white-space: nowrap;');
        $heatmap(".gradient-level-max").attr("style", 'white-space: nowrap;');
    }

    function hideExpressionLevels($heatmap) {
        $heatmap("div[data-color]").each(function () {
            hideCellText(this);
        });
        $heatmap(".gradient-level-min").css("display", "none");
        $heatmap(".gradient-level-max").css("display", "none");
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

    function initDisplayLevelsButtonOnClick(heatmapElement) { //binds toggle handler
        var $heatmap = contextFactory(heatmapElement);
        var prefFormDisplayLevels = $("#prefForm").find("#displayLevels");

        // hacky!
        function syncDisplayLevelButtonOnOtherHeatmap(syntheticEvent) {
            if (!syntheticEvent) {
                // don't want to get stuck in an infinite loop,
                // so click other button only in response to original click
                // not the synthetic event we generate here
                $('.display-levels-button').not(this).trigger("click", "syntheticEvent")
            }
        }

        $heatmap("#display-levels").button()
            .toggle(
            function (eventObject, syntheticEvent) {
                $(this).button('option', 'label', $("#buttonText").attr('pressedtext'));
                showExpressionLevels($heatmap);
                prefFormDisplayLevels.val("true");

                syncDisplayLevelButtonOnOtherHeatmap.call(this, syntheticEvent);
            },
            function (eventObject, syntheticEvent) {
                $(this).button('option', 'label', $("#buttonText").attr('unpressedtext'));
                hideExpressionLevels($heatmap);
                prefFormDisplayLevels.val("false");

                syncDisplayLevelButtonOnOtherHeatmap.call(this, syntheticEvent);
            }
        );

        $heatmap("#display-levels").button({ label:$("#buttonText").attr('unpressedtext') });

        if (prefFormDisplayLevels.val() === "true") {
            $heatmap("#display-levels").trigger("click", "syntheticEvent")
        }

    }

    function initTranscriptPopupOnHeatMapCellClick(experimentAccession, species, selectedFilterFactorsJson) {
        var $transcript = $('#transcript-breakdown');

        function buildPlotData(transcriptRates) {
            var data = [],
                index = 0;
            $.each(transcriptRates, function (key, value) {
                data[index++] = {label:key, data:Math.abs(value.toFixed(1)), color:(value < 0 ? 'white' : undefined)};
            });
            return data;
        }

        function paintPieChart(plotData, geneId) {

            $transcript.css('position', 'absolute').css('left', '-5000px');
            $transcript.show();

            $.plot('#transcripts-pie', plotData, {
                series:{
                    pie:{stroke:{
                        color:"#d3d3d3"
                    },
                        show:true
                    }
                },
                legend:{
                    show:true,
                    labelFormatter:function (label) {
                        return label === "Others" ? "Others" :
                            "<a class='transcriptid' href='http://www.ensembl.org/" + species + "/Transcript/Summary?g=" + geneId + ";t="
                                + label + "' target='_blank'" + "title='View transcript in Ensembl'" + ">" +
                                label + "</a>";
                    }
                }
            });

            //next lines are required because the div is configured to stay in an invisible position (position:absolute; left:-5000px)
            //in order to make it invisible during the show-up of fancybox
            //all of this is required because of IE8 :( . It doesn' t allow painting canvas in a hidden div, so we need to first show the div, then paint in it, then reposition it, then fancybox it...
            $transcript.hide();
            $transcript.css('position', 'relative')
                .css('left', '0px');

        }

        $heatmap("#heatmap-table").find("td:has(div[data-color])").click(function () {

            //gene and factor properties are extracted from gene and factor headers in the html table
            var $queryFactorType = $("#queryFactorType");

            var factorValue = $(this).find("div").attr("data-organism-part"),
                factorType = $queryFactorType.attr("value") || $queryFactorType.attr("data-value"),
                geneId = $(this).parent().find("td a:eq(0)").attr("id") || $(this).parent().find("td div:eq(0)").attr("id"),
                geneName = $(this).parent().find("td a:eq(0)").text() || $(this).parent().find("td div:eq(0)").text() ;

            $.ajax({
                url: _transcriptUrlRoot + "/json/transcripts/" + experimentAccession,
                type:"GET",
                data:{
                    'geneId':geneId,
                    'factorType':factorType,
                    'factorValue':factorValue,
                    'selectedFilterFactorsJson':JSON.stringify(selectedFilterFactorsJson)
                },
                datatype:'json',
                success:function (data) {
                    var totalCount = data.totalTranscriptsCount,
                        expressedCount = data.expressedTranscriptsCount,
                        plotData = buildPlotData(data.transcriptExpressions);

                    species = species.replace(" ", "_");

                    paintPieChart(plotData, geneId);

                    showTranscriptBreakdownFancyBox();

                    var s = '';
                    if (totalCount > 1) {
                        s = 's';
                    }

                    var is = 'is';
                    if (totalCount > 1) {
                        is = 'are';
                    }

                    $('#transcript-breakdown-title').html("Expression Level Breakdown for " +
                        "<a id='transcript-breakdown-geneid' href='http://www.ensembl.org/" + species + "/Gene/Summary?g=" + geneId +
                        "' target='_blank'" + "title='View gene in Ensembl'" + ">" + geneName + "</a> in " + factorValue +
                        "<br/>(" + expressedCount + " out of " + totalCount + " transcript" + s +
                        " " + is + " expressed):");

                }
            }).fail(function (data) {
                    console.log("ERROR:  " + data);
                });

        });
    }

    //there must be a cleaner way to do this, but I don't know it yet!
    function buildHeatmapCellTooltip(pValue, tstatistic, foldChange) {
        return "<table class='table-grid' style='margin: 0px; padding: 0px;'><thead><th class='header-cell'>Adjusted <i>p</i>-value</th>" +
            (tstatistic !== undefined ? "<th class='header-cell'><i>t</i>-statistic</th>" : "") +
            "<th class='header-cell'>Log<sub>2</sub>-fold change</th></thead>" +
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
                tooltipClass:"help-tooltip pvalue-tooltip-styling",

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
                    $heatmap("div", "th", "#heatmap-table").addClass('rotate_text_IE').removeClass('rotate_text');
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
            headers += "<td class='horizontal-header-cell'>" + this + "</td>";
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
            $(this).append("<div style='text-align:right;padding-right:3px'><a href='" + maPlotURL + "' class='ma-button button-image' title='Click to view MA plot for the contrast across all genes'><img src='resources/images/maplot-button.png'/></a></div>");

        });

        $heatmap(".ma-button").tooltip().button();

        $heatmap(".ma-button").fancybox({
            padding:0,
            openEffect:'elastic',
            closeEffect:'elastic'
        });

    }

    function initTranscriptBreakdownFancyBox(experimentAccession, parameters) {
        initTranscriptPopupOnHeatMapCellClick(experimentAccession, parameters.species, parameters.selectedFilterFactorsJson);

        $('#transcript-breakdown-geneid').tooltip();
        $('#transcript-breakdown-title-help').tooltip();

    }

    function contextFactory(ctx) {
        return function(selector) {
            return $(selector, ctx);
        };
    }

    var $heatmap; // stores current heatmap element. allows us to have multiple heatmaps on the same page

    function initHeatmap(experimentAccession, parameters, heatmapElementId, isHidden) {
        var heatmapElement = $('#' + heatmapElementId);
        $heatmap = contextFactory(heatmapElement);

        $heatmap('#heatmap-table th:first').addClass('horizontal-header-cell'); //because displaytag doesn't let us configure TH cells...

        if (experimentAccession !== undefined && parameters.species && !parameters.asGeneSets) {
            initTranscriptBreakdownFancyBox(experimentAccession, parameters);
        }

        initDifferentialHeatmapCellsTooltip();
        initDownloadButtonTooltip();
        initDisplayLevelsButtonOnClick(heatmapElement);
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

    function initBaselineHeatmap(experimentAccession, species, selectedFilterFactorsJson, asGeneSets, transcriptUrlRoot, heatmapElementId, isHidden) {
        _transcriptUrlRoot = transcriptUrlRoot;
        
        initHeatmap(experimentAccession, {
            species:species,
            selectedFilterFactorsJson:selectedFilterFactorsJson,
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
