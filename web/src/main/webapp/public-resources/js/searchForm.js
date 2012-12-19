function initSearchForm(homePageURL, cutoff, experimentAccession, isIE8, defaultText) {

    $(".chzn-select").chosen().change(function () {
        if ($(this).val()) {
            $(this).data("chosen").default_text = "";
        } else {
            $(this).data("chosen").default_text = defaultText;
            $(this).trigger("liszt:updated");
        }
        if (!isIE8) {
            loadSliderAndPlot(cutoff, experimentAccession, $(this).serialize());
        }
    });

    $("#submit-button").button();

    $("#reset-button").button().click(function (event) {
        location.replace(homePageURL);
    })

    $("#geneQuery").watermark("(all genes)");

    $("#cutoff").watermark("(default 0.5)");
    if (!isIE8) {
        loadSliderAndPlot(cutoff, experimentAccession, $(".chzn-select").serialize());
    }
}