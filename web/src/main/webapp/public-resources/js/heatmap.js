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
        $(".gradient-level").attr("style", '');
    }

    function hideValues(button) {
        $(button).button('option', 'label', 'Display levels');
        $("div[data-color]").each(function () {
            hideCellValue(this);
        });
        $(".gradient-level").attr("style", 'color:white');
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


}