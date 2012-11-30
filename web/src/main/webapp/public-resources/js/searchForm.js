function initSearchForm(homePageURL, cutoff, experimentAccession) {

    $(".chzn-select").chosen().change(function() {
            if ($(this).val()){
                $(this).data("chosen").default_text = "";
            } else {
                $(this).data("chosen").default_text = "(all organism parts)";
                $(this).trigger("liszt:updated");
            }
        loadSliderAndPlot(cutoff, experimentAccession, $(this).serialize());
        });

    $("#submit-button").button();

    $("#reset-button").button().click(function (event) {
        location.replace(homePageURL);
    })

    $("#geneQuery").watermark("(all genes)");

    $("#cutoff").watermark("(default 0.5)");

    loadSliderAndPlot(cutoff, experimentAccession, $(".chzn-select").serialize());
}