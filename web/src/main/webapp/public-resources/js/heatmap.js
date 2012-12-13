function initHeatmapDisplayValueToggle() {

    function showCellValue(div) {
        $(div).attr('style', '');
        $(div).removeClass("hide_cell");
        $(div).addClass("show_cell");
    }

    function hideCellValue(div) {
        $(div).attr('style', 'color:' + $(div).attr("data-color") + ';background-colour:' + $(div).attr("data-color"));
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
        .toggle(function () {
            showValues(this);
            $("#prefForm #displayLevels").val("true");
        },
        function () {
            hideValues(this);
            $("#prefForm #displayLevels").val("false");
        });

    if ($("#prefForm #displayLevels").val() == "true") {
        $("#display-levels").click();
    }


    $("#heatmap-table td:has(div[data-color])").click(function () {
        var div = $(this).find("div");
        if (div.hasClass("hide_cell")) {
            showCellValue(div);
        } else if (div.hasClass("show_cell")) {
            hideCellValue(div);
        } else {
            alert('The cell clicked is incorrect.')
        }
    });

    $('#download-profiles-link').button().tooltip();


}