function initSearchForm(homePageURL, cutoff, experimentAccession, isIE8) {

    function enableIncludeNonSelectedFactorValues(){
        $("#includeNonSelectedFactorValues").removeAttr("disabled");
        $("label[for='includeNonSelectedFactorValues']").attr('style', 'color:black')
    }

    function disableIncludeNonSelectedFactorValues(){
        $("#includeNonSelectedFactorValues").attr("disabled", true);
        $("label[for='includeNonSelectedFactorValues']").attr('style', 'color:gray')
    }

    disableIncludeNonSelectedFactorValues();

    $(".chzn-select").chosen().change(function() {
            if ($(this).val()){
                $(this).data("chosen").default_text = "";
                enableIncludeNonSelectedFactorValues();
            } else {
                $(this).data("chosen").default_text = "(all organism parts)";
                $(this).trigger("liszt:updated");
                disableIncludeNonSelectedFactorValues();
            }
        if (!isIE8){
            loadSliderAndPlot(cutoff, experimentAccession, $(this).serialize());
        }
        });

    $("#submit-button").button();

    $("#reset-button").button().click(function (event) {
        location.replace(homePageURL);
    })

    $("#geneQuery").watermark("(all genes)");

    $("#cutoff").watermark("(default 0.5)");
    if (!isIE8){
       loadSliderAndPlot(cutoff, experimentAccession, $(".chzn-select").serialize());
    }
}