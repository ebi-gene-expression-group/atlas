/*global $:false */

function initSearchForm(homePageURL, cutoff, experimentAccession, isIE8, defaultText) {

    "use strict";

    function enableIncludeNonSelectedFactorValues() {
        $("#includeGenesExpressedOnNonSelectedFactorValuesCheckbox").removeAttr("disabled");
        $("label[for='includeGenesExpressedOnNonSelectedFactorValuesCheckbox']").attr('style', 'color:black');
        //$("#rankGenesExpressedOnMostFactorsLast").removeAttr("disabled");
        //$("label[for='rankGenesExpressedOnMostFactorsLast']").attr('style', 'color:black')
    }

    function disableIncludeNonSelectedFactorValues() {
        $("#includeGenesExpressedOnNonSelectedFactorValuesCheckbox").attr("disabled", true);
        $("label[for='includeGenesExpressedOnNonSelectedFactorValuesCheckbox']").attr('style', 'color:lightgray');
        //$("#rankGenesExpressedOnMostFactorsLast").attr("disabled", true);
        //$("label[for='rankGenesExpressedOnMostFactorsLast']").attr('style', 'color:gray')
    }

    function updatePlot(selectedFactorValues) {
        loadSliderAndPlot(cutoff, experimentAccession, selectedFactorValues,
            $("#includeGenesExpressedOnNonSelectedFactorValues").attr("value"));
    }

    $("#includeGenesExpressedOnNonSelectedFactorValuesCheckbox").attr("checked", $("#includeGenesExpressedOnNonSelectedFactorValues").val() === "true");

    $("#includeGenesExpressedOnNonSelectedFactorValuesCheckbox").change(function () {
        $("#includeGenesExpressedOnNonSelectedFactorValues").attr("value", $("#includeGenesExpressedOnNonSelectedFactorValuesCheckbox").is(":checked"));
        updatePlot($(".chzn-select").val());
    });

    var selectedFactorValues = $(".chzn-select").chosen().change(function () {
            if ($(this).val()) {
                $(this).data("chosen").default_text = "";
                enableIncludeNonSelectedFactorValues();
            } else {
                $(this).data("chosen").default_text = defaultText;
                $(this).trigger("liszt:updated");
                disableIncludeNonSelectedFactorValues();
            }
            if (!isIE8) {
                updatePlot($(this).val());
            }
        }).val();

    if (selectedFactorValues) {
        enableIncludeNonSelectedFactorValues();
    } else {
        disableIncludeNonSelectedFactorValues();
    }

    $("#submit-button").button();

    $("#reset-button").button().click(function (event) {
        location.replace(homePageURL);
    });

    $("#geneQuery").watermark("(all genes)");

    $("#cutoff").watermark("(default 0.5)");
    if (!isIE8) {
        loadSliderAndPlot(cutoff, experimentAccession, $(".chzn-select").val(), $("#includeGenesExpressedOnNonSelectedFactorValues").val());
    }

    $("#organismParts_chzn").mouseleave(function () {
        $("#factor-values-div").tooltip("close");
    });

}