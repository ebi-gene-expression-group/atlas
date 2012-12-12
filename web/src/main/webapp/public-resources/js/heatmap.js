function initHeatmapDisplayValueToggle() {

    function showCellValue(div) {
        $(div).attr('style', 'font-size:9px;background-color:white;margin:4px;padding:2px;');
    }

    function hideCellValue(div) {
        $(div).attr('style', 'font-size:1px;color:' + $(div).attr("data-color") + ';background-colour:' + $(div).attr("data-color") + ';height:100%;width:100%');
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
        var style = div.attr("style");
        if (style.search("font-size:\\s*1px") != -1) {
            showCellValue(div)
        } else {
            hideCellValue(div);
        }
    });

    $('#download-profiles-link').button().tooltip();


}