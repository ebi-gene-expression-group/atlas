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

    function showExpressionLevels() {
        $("div[data-color]").each(function () {
            showCellText(this);
        });
        $(".gradient-level-min").attr("style", 'white-space: nowrap;');
        $(".gradient-level-max").attr("style", 'white-space: nowrap;');
    }

    function hideExpressionLevels() {
        $("div[data-color]").each(function () {
            hideCellText(this);
        });
        $(".gradient-level-min").css("display", "none");
        $(".gradient-level-max").css("display", "none");
    }

    function initDisplayLevelsButton() { //binds toggle handler

        $("#display-levels").button()
            .toggle(
            function () {
                $(this).button('option', 'label', 'Hide levels');
                showExpressionLevels(this);
                $("#prefForm #displayLevels").val("true");
            },
            function () {
                $(this).button('option', 'label', 'Display levels');
                hideExpressionLevels(this);
                $("#prefForm #displayLevels").val("false");
            }
        );

        $("#display-levels").button({ label:'Display levels' });

        if ($("#prefForm #displayLevels").val() === "true") {
            $("#display-levels").click();
        }

    }

    function initHeatmapCellsClickHandling(experimentAccession, species, selectedFilterFactorsJson, cutoff) { //binds heatmap cells click handler

        function buildPlotData(transcriptRates) {
            var data = [],
                index = 0;
            $.each(transcriptRates, function (key, value) {
                data[index++] = {label:key, data:Math.abs(value.toFixed(1)), color:(value < 0 ? 'white' : undefined)};
            });
            return data;
        }

        function paintPieChart(plotData, geneId) {

            $('#transcript-breakdown').css('position', 'absolute')
                .css('left', '-5000px')
            $('#transcript-breakdown').show();

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
            $('#transcript-breakdown').hide();
            $('#transcript-breakdown').css('position', 'relative')
                .css('left', '0px');

        }

        //on click...
        $("#heatmap-table td:has(div[data-color])").click(function () {

            //gene and factor properties are extracted from gene and factor headers in the html table
            var factorValue = $(this).find("div").attr("data-organism-part"),
                factorType = $("#queryFactorType").attr("value"),
                geneId = $(this).parent().find("td a:eq(0)").attr("id"),
                geneName = $(this).parent().find("td a:eq(0)").text();

            $.ajax({
                url:"json/transcripts/" + experimentAccession,
                type:"GET",
                data:{
                    'geneId':geneId,
                    'factorType':factorType,
                    'factorValue':factorValue,
                    'selectedFilterFactorsJson':JSON.stringify(selectedFilterFactorsJson),
                    'cutoff':0.05
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

                    $('#transcript-breakdown-title').html("Expression Level Breakdown for " +
                        "<a id='geneid' href='http://www.ensembl.org/" + species + "/Gene/Summary?g=" + geneId +
                        "' target='_blank'" + "title='View gene in Ensembl'" + ">" + geneName + "</a> in " + factorValue +
                        "<br/>" + expressedCount + " out of " + totalCount + " transcript" + s +
                        " are expressed above the expression level cutoff.");

                }
            }).fail(function (data) {
                    console.log("ERROR:  " + data);
                });

        });
    }

    //there must be a cleaner way to do this, but I don't know it yet!
    function buildHeatmapCellTooltip(expressionLevel, tstatistic, foldChange) {
        return "<table class='table-grid' style='margin: 0px; padding: 0px;'><thead><th class='header-cell'>Adjusted P-value</th>" +
            (tstatistic !== undefined ? "<th class='header-cell'>T-statistic</th>" : "") +
            "<th class='header-cell'>Log2-fold Change</th></thead>" +
            "<tbody><tr><td style='padding:6px'><span style=\"white-space: nowrap;\">" + expressionLevel + "</span></td>" +
            (tstatistic !== undefined ? "<td style='padding:6px'>" + tstatistic + "</td>" : "") +
            "<td style='padding:6px'>" + foldChange + "</td></tr></tbody>" +
            "</table>";
    }

    function initHeatmapCellsTooltip() {
        $("#heatmap-table td:has(div[data-fold-change])").attr('title', '').tooltip(
            {
                open:function (event, ui) {
                    var colour = $(this).find("div").attr("data-color");
                    ui.tooltip.css('background', colour);
                },
                tooltipClass:"help-tooltip pvalue-tooltip-styling",

                content:function (callback) {
                    var expressionLevel = $(this).find("div").html(),
                        foldChange = $(this).find("div").attr("data-fold-change"),
                        tstatistic = $(this).find("div").attr("data-tstatistic");

                    return buildHeatmapCellTooltip(expressionLevel, tstatistic, foldChange);
                }

            });
    }

    function initDownloadButton() {
        $('#download-profiles-link').button().tooltip();
    }

    function restrictLabelSize(label, maxSize) {
        var result = label;
        if (label.length > maxSize) {
            label = label.substring(0, maxSize);
            if (label.lastIndexOf(" ") > maxSize - 5) {
                label = label.substring(0, label.lastIndexOf(" "));
            }
            label = label + "...";
        }
        return label;
    }

    function initHeatmapFactorHeaders() {//shorten header labels if necessary and inits tooltips

        $(".factor-header")
            .each(function () {
                if ($.browser.msie) {
                    $(this).append($(this).attr("data-organism-part"));
                    $("div", "th", "#heatmap-table").addClass('rotate_text_IE').removeClass('rotate_text');
                    $("th", "#heatmap-table").addClass('heatmap td').removeClass('rotated_cell');
                } else {
                    var organismPartName = $(this).attr("data-organism-part");
                    organismPartName = restrictLabelSize(organismPartName, 17);
                    $(this).append(organismPartName);
                }
            }).tooltip();

    }

    function createAccessionHeaders(accessionHeaders) {

        var headers;

        $(accessionHeaders).each(function () {
            headers += "<td class='horizontal-header-cell'>" + this + "</td>";
        });
        //add custom header cells for gene name and design element
        $($("#heatmap-table thead")).append("<tr id='injected-header'>" + headers + "</tr>");

        //add display levels cell colspan
        if (accessionHeaders.length === 2) {

            $("#heatmap-table thead tr th:eq(1)").remove();
            $("#heatmap-table thead tr th:eq(0)").attr("colspan", 2);

        }
        //add rowspan to factor headers
        $($("#heatmap-table thead tr th:gt(0)")).attr("rowspan", 2);
    }

    function initMaPlotButtons(experimentAccession, arrayDesignAccession) {
        var thElements = $(".factor-header").parent(),
            maPlotURL;

        thElements.css("width", "60px");
        $(".factor-header").css("transform-origin");
        $(".factor-header").css("top", "57px");

        $(thElements).each(function () {
            var contrastName = $(this).children().attr("data-contrast-name");

            maPlotURL = 'external-resources/' + experimentAccession + '/' + (arrayDesignAccession ? arrayDesignAccession + '/' : '' ) + contrastName + '/ma-plot.png';
            //append a button div now
            $(this).append("<div style='text-align:right;padding-right:3px'><a href='" + maPlotURL + "' class='ma-button button-image' title='Click to view MA plot for the contrast across all genes'><img src='resources/images/maplot-button.png'/></a></div>");

        });

        $(".ma-button").tooltip().button();

        $(".ma-button").fancybox({
            padding:0,
            openEffect:'elastic',
            closeEffect:'elastic'
        });

    }

    function initTranscriptBreakdownFancyBox(experimentAccession, parameters) {
        initHeatmapCellsClickHandling(experimentAccession, parameters.species, parameters.selectedFilterFactorsJson, parameters.cutoff);

        $('#geneid').tooltip();
        $('#transcript-breakdown-title-help').tooltip();

    }

    function initHeatmap(experimentAccession, parameters) {

        $('#heatmap-table th:first').addClass('horizontal-header-cell'); //because displaytag doesn't let us configure TH cells...

        if (experimentAccession !== undefined && parameters.species && !parameters.isWidget) {
            initTranscriptBreakdownFancyBox(experimentAccession, parameters);
        }

        initHeatmapCellsTooltip();
        initDownloadButton();
        initDisplayLevelsButton();
        initHeatmapFactorHeaders();

        var firstColumnHeader = parameters.geneSetMatch ? "Gene set" : "Gene";

        if (parameters.arrayDesignAccession) { //then it is a microarray experiment
            createAccessionHeaders([firstColumnHeader, "Design Element"]);
        } else {
            createAccessionHeaders([firstColumnHeader]);
        }

        if (experimentAccession !== undefined && parameters.cutoff === 0.05 && !parameters.geneQuery) {
            initMaPlotButtons(experimentAccession, parameters.arrayDesignAccession);
        }

        $("#heatmap-div").show();

    }

    function initBaselineHeatmap(experimentAccession, species, selectedFilterFactorsJson, geneSetMatch, isWidget, cutoff) {
        initHeatmap(experimentAccession, {
            species:species,
            selectedFilterFactorsJson:selectedFilterFactorsJson,
            geneSetMatch:geneSetMatch,
            isWidget:isWidget,
            cutoff:cutoff
        });
    }

    function initRnaSeqHeatmap(cutoff) {
        initHeatmap(undefined, {cutoff:cutoff, geneQuery:undefined});
    }

    function initRnaSeqHeatmap(experimentAccession, cutoff, geneQuery) {
        initHeatmap(experimentAccession, {cutoff:cutoff, geneQuery:geneQuery});
    }

    function initMicroarrayHeatmap(experimentAccession, arrayDesignAccession, cutoff, geneQuery) {
        initHeatmap(experimentAccession, {
            arrayDesignAccession:arrayDesignAccession,
            cutoff:cutoff,
            geneQuery:geneQuery
        });
    }

    return {

        initBaselineHeatmap:initBaselineHeatmap,
        initRnaSeqHeatmap:initRnaSeqHeatmap,
        initMicroarrayHeatmap:initMicroarrayHeatmap

    };

}(jQuery));
