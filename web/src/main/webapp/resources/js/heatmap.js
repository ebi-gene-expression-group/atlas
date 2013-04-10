/*global $:false */

function initHeatmapDisplayValueToggle() {

    "use strict";

    function showCellValue(div) {
        $(div).removeClass("hide_cell");
        $(div).addClass("show_cell");
    }

    function hideCellValue(div) {
        $(div).removeClass("show_cell");
        $(div).addClass("hide_cell");
    }

    function showValues(button) {
        $(button).button('option', 'label', 'Hide levels');
        $("div[data-color]").each(function () {
            showCellValue(this);
        });
        $(".gradient-level-min").attr("style", 'white-space: nowrap;');
        $(".gradient-level-max").attr("style", 'white-space: nowrap;');
    }

    function restrictSize(s, maxSize) {
        var result = s;
        if (result.length > maxSize) {
            result = result.substring(0, maxSize);
            if (result.lastIndexOf(" ") > maxSize - 5) {
                result = result.substring(0, result.lastIndexOf(" "));
            }
            result = result + "...";
        }
        return result;
    }


    function hideValues(button) {
        $(button).button('option', 'label', 'Display levels');
        $("div[data-color]").each(function () {
            hideCellValue(this);
        });
        $(".gradient-level-min").css("display", "none");
        $(".gradient-level-max").css("display", "none");
    }

    function buildHeatmapCellTooltip(expressionLevel, tstatistic, foldChange){
        return "<table class='table-grid'><thead><th class='header-cell'>Adjusted P-value</th>" +
            (tstatistic !== undefined ? "<th class='header-cell'>T-statistic</th>" : "") +
            "<th class='header-cell'>Log2-fold Change</th></thead>" +
            "<tbody><tr><td style='padding:6px'><span style=\"white-space: nowrap;\">" + expressionLevel + "</span></td>" +
            (tstatistic !== undefined ? "<td style='padding:6px'>" + tstatistic + "</td>" : "") +

            "<td style='padding:6px'>" + foldChange + "</td></tr></tbody>" +
            "</table>";
    }

    $("#display-levels").button()
        .toggle(
        function () {
            showValues(this);
            $("#prefForm #displayLevels").val("true");
        },
        function () {
            hideValues(this);
            $("#prefForm #displayLevels").val("false");
        }
    );

    if ($("#prefForm #displayLevels").val() === "true") {
        $("#display-levels").click();
    }


    $("#heatmap-table td:has(div[data-color])").click(function () {
        var div = $(this).find("div");
        if (div.hasClass("hide_cell")) {
            showCellValue(div);
        } else if (div.hasClass("show_cell")) {
            hideCellValue(div);
        }
    });

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




    $('#download-profiles-link').button().tooltip();

    $("#sort-toggle").button().
        toggle(
        function () {
            $("#sort-toggle-image").attr("src", "resources/images/sort-least-on-top.png");
            $(this).tooltip("option", "content", "Click to report genes expressed in the most organism parts last'");
        },
        function () {
            $("#sort-toggle-image").attr("src", "resources/images/sort-most-on-top.png");
            $(this).tooltip("option", "content", "Click to report genes expressed in the most organism parts first");
        }
    ).attr("title", " ").tooltip();

    $("#heatmap-div").show();

    //heatmap factor headers tooltips
    $(".factor-header")
        .tooltip()
        .each(function () {
            if ($.browser.msie) {
                $(this).append($(this).attr("data-organism-part"));
            } else {
                var organismPartName = $(this).attr("data-organism-part");
                organismPartName = restrictSize(organismPartName, 17);
                $(this).append(organismPartName);
            }
        });

}

function initHeatmapCustomHeaders(isMicroarray){
    "use strict";

    //add custom header cells for gene name and design element
    $($("#heatmap-table thead"))
        .append("<tr><td class='header-cell'>Gene</td>" +
            (isMicroarray ? "<td class='header-cell'>Design Element</td>" : "" ) +
            "</tr>");

    //add display levels cell colspan
    if (isMicroarray){

        $("#heatmap-table thead tr th:eq(1)").remove();
        $("#heatmap-table thead tr th:eq(0)").attr("colspan",2);

    }

    //add rowspan to factor headers
    $($("#heatmap-table thead tr th:gt(0)")).attr("rowspan", 2);
}

function initMaPlotButtons(areButtonsEnabled, experimentAccession){
    "use strict";
    initMaPlotButtons(areButtonsEnabled, experimentAccession, null);
}

function initMaPlotButtons(areButtonsEnabled, experimentAccession, arrayDesignAccession){
    "use strict";
    if (areButtonsEnabled){
        var thElement = $(".factor-header").parent();
        thElement.css("width","60px");
        $(".factor-header").css("transform-origin");
        $(".factor-header").css("top","57px");
        //append a button div now
        thElement.append("<div style='text-align:right;padding-right:3px'><a href='external-resources/' class='ma-button button-image' title='Click to view MA plot for the contrast across all genes'><img src='resources/images/maplot-button.png'/></a></div>");

        $(".ma-button").tooltip().button();
    }
}