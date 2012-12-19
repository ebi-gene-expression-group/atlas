function initSearchForm(homePageURL, cutoff, experimentAccession, isIE8, defaultText) {

    function enableIncludeNonSelectedFactorValues(){
        $("#includeGenesExpressedInNonSelectedFactorValuesCheckbox").removeAttr("disabled");
        $("label[for='includeGenesExpressedInNonSelectedFactorValuesCheckbox']").attr('style', 'color:black')
        //$("#rankGenesExpressedOnMostFactorsLast").removeAttr("disabled");
        //$("label[for='rankGenesExpressedOnMostFactorsLast']").attr('style', 'color:black')
    }

    function disableIncludeNonSelectedFactorValues(){
        $("#includeGenesExpressedInNonSelectedFactorValuesCheckbox").attr("disabled", true);
        $("label[for='includeGenesExpressedInNonSelectedFactorValuesCheckbox']").attr('style', 'color:lightgray')
        //$("#rankGenesExpressedOnMostFactorsLast").attr("disabled", true);
        //$("label[for='rankGenesExpressedOnMostFactorsLast']").attr('style', 'color:gray')
    }

    $("#includeGenesExpressedInNonSelectedFactorValuesCheckbox").attr("checked", $("#includeGenesExpressedInNonSelectedFactorValues").val()=="true")

    $("#includeGenesExpressedInNonSelectedFactorValuesCheckbox").change(function(){
        $("#includeGenesExpressedInNonSelectedFactorValues").attr("value", $("#includeGenesExpressedInNonSelectedFactorValuesCheckbox").is(":checked"))
    });

    var selectedFactorValues = $(".chzn-select").chosen().change(function() {
            if ($(this).val()){
                $(this).data("chosen").default_text = "";
                enableIncludeNonSelectedFactorValues();
            } else {
                $(this).data("chosen").default_text = defaultText ;
                $(this).trigger("liszt:updated");
                disableIncludeNonSelectedFactorValues();
            }
            if (!isIE8){
                loadSliderAndPlot(cutoff, experimentAccession, $(this).serialize());
            }
        }).val();

    if (selectedFactorValues){
        enableIncludeNonSelectedFactorValues();
    }else {
        disableIncludeNonSelectedFactorValues();
    }

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