/*global $,jQuery,console,loadSliderAndPlot: false */
/*jslint browser:true */
/*jslint nomen: true*/

var heatmapModule = (function($) {

    "use strict";

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

    function initDisplayLevelsButton(){ //binds toggle handler

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

        if ($("#prefForm #displayLevels").val() === "true") {
            $("#display-levels").click();
        }

    }

    function initHeatmapCellsClickHandling(){ //binds heatmap cells click handler

        $("#heatmap-table td:has(div[data-color])").click(function () {

            //click will have to use $.ajax, with all the following code that will be executed in the "success:" callback

            //... but for now we just use mock data

            //this is mock data in the format returned by our rest controller.
            var transcriptRates = {"ENST000001":25.0,"ENST000003":25.0,"ENST000002":25.0,"Others":25.0};

            var data = [],
                index = 0;
            $.each(transcriptRates, function(key, value){
                                                        data[index++] = {label: key , data: value};
                                                        });
            $.plot('#transcripts-pie', data, {
                series: {
                    pie: {
                        show: true,
                        radius:1,
                        label: {
                            style: {color: "white"},
                            radius: 3/5,
                            show: true,
                            formatter: function(label, series){
                                return  series.percent + "%";},
                            background: {
                                opacity: 0.5
                            }
                        }
                    }
                },
                legend: {

                    show: true,
                    labelFormatter: function(label){
                        return label === "Others" ? "Others" :
                                                    "<a href='http://www.ensembl.org/Homo_sapiens/Transcript/Summary?g=ENSG00000006042;t=ENST00000394642' target='_blank'>" +
                                                    label + "</a>";
                    }
                }
            });

            $.fancybox({href : '#transcript-breakdown',
                                    padding:0,
                                    openEffect:'elastic',
                                    closeEffect:'elastic',
                                    helpers: {
                                        overlay : {
                                            locked: false
                                        }
                                    }
                          });
            /* uncomment this to restore expression level visualization
            var div = $(this).find("div");
            if (div.hasClass("hide_cell")) {
                showCellText(div);
            } else if (div.hasClass("show_cell")) {
                hideCellText(div);
            }
            */
        });
    }

    //there must be a cleaner way to do this, but I don't know it yet!
    function buildHeatmapCellTooltip(expressionLevel, tstatistic, foldChange){
        return "<table class='table-grid'><thead><th class='header-cell'>Adjusted P-value</th>" +
            (tstatistic !== undefined ? "<th class='header-cell'>T-statistic</th>" : "") +
            "<th class='header-cell'>Log2-fold Change</th></thead>" +
            "<tbody><tr><td style='padding:6px'><span style=\"white-space: nowrap;\">" + expressionLevel + "</span></td>" +
            (tstatistic !== undefined ? "<td style='padding:6px'>" + tstatistic + "</td>" : "") +

            "<td style='padding:6px'>" + foldChange + "</td></tr></tbody>" +
            "</table>";
    }

    function initHeatmapCellsTooltip(){ //initializes heatmap cells tooltip
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

    function initDownloadButton(){
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

    function initHeatmapFactorHeaders(){//shorten header labels if necessary and inits tooltips

        $(".factor-header")
            .each(function () {
                if ($.browser.msie) {
                    $(this).append($(this).attr("data-organism-part"));
                } else {
                    var organismPartName = $(this).attr("data-organism-part");
                    organismPartName = restrictLabelSize(organismPartName, 17);
                    $(this).append(organismPartName);
                }
            }).tooltip();

    }

    function createAccessionHeaders(accessionHeaders){

        var headers;

        $(accessionHeaders).each(function(){
            headers += "<td class='header-cell'>" + this + "</td>";
        });
        //add custom header cells for gene name and design element
        $($("#heatmap-table thead")).append("<tr>" + headers + "</tr>");

        //add display levels cell colspan
        if (accessionHeaders.length === 2){

            $("#heatmap-table thead tr th:eq(1)").remove();
            $("#heatmap-table thead tr th:eq(0)").attr("colspan", 2);

        }
        //add rowspan to factor headers
        $($("#heatmap-table thead tr th:gt(0)")).attr("rowspan", 2);
    }

    function initMaPlotButtons(experimentAccession, arrayDesignAccession){
        var thElements = $(".factor-header").parent(),
            maPlotURL;

        thElements.css("width","60px");
        $(".factor-header").css("transform-origin");
        $(".factor-header").css("top","57px");

        $(thElements).each(function(){
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


    function initHeatmap(experimentAccession, differentialParameters){

        initHeatmapCellsClickHandling();
        initHeatmapCellsTooltip();
        initDownloadButton();
        initDisplayLevelsButton();
        initHeatmapFactorHeaders();

        if (differentialParameters && differentialParameters.arrayDesignAccession){ //then it is a microarray experiment
            createAccessionHeaders(['Gene', 'Design Element']);
        }   else {
            createAccessionHeaders(['Gene']);
        }

        if (differentialParameters && differentialParameters.cutoff === 0.05 && ! differentialParameters.geneQuery){
            initMaPlotButtons(experimentAccession, differentialParameters.arrayDesignAccession);
        }

        $("#heatmap-div").show();

    }

    function initBaselineHeatmap(experimentAccession){
        initHeatmap(experimentAccession);
    }

    function initRnaSeqHeatmap(experimentAccession, cutoff, geneQuery){
        initHeatmap(experimentAccession, {cutoff: cutoff, geneQuery: geneQuery});
    }

    function initMicroarrayHeatmap(experimentAccession, arrayDesignAccession, cutoff, geneQuery) {
        initHeatmap(experimentAccession, {  arrayDesignAccession: arrayDesignAccession,
                                            cutoff: cutoff,
                                            geneQuery: geneQuery });
    }

    return {

        initBaselineHeatmap: initBaselineHeatmap,
        initRnaSeqHeatmap: initRnaSeqHeatmap,
        initMicroarrayHeatmap: initMicroarrayHeatmap

    };

}(jQuery));
