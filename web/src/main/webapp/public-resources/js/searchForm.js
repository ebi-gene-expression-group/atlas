/*global $,console,loadSliderAndPlot: false */
/*jslint browser:true */


//object constructor and function prototypes

/*
 Search form object incapsulating jquery functionalities
 */
var searchForm = {

    removeHttpParameters : function (string) {
        "use strict";
        return string.split("?")[0];
    },

    isNotIE7orOlder : function(){
        "use strict";
        return !($.browser.msie && $.browser.version <= 8.0);
    },

    init : function (cutoff, experimentAccession, watermarkLabel) {
        "use strict";

        function updatePlot(selectedFactorValues) {

            if (searchForm.isNotIE7orOlder()) {
                loadSliderAndPlot(cutoff, experimentAccession, selectedFactorValues);
            }
        }

        $("#submit-button").button();

        $("#reset-button").button().click(function (event) {
            var urlWithoutParameters = searchForm.removeHttpParameters(window.location.href);
            window.location.replace(urlWithoutParameters);
        });

        $("#geneQuery").watermark("(all genes)");

        $("#cutoff").watermark("(default 0.5)");

        updatePlot($("#queryFactorValues").val());

        $("#queryFactorValues").chosen().change(function () {
            updatePlot($(this).val());
            //following code is a patch to reload the right watermark label in the 'chosen' plugin
            if ($(this).val()) {
                $(this).data("chosen").default_text = "";
            } else {
                $(this).data("chosen").default_text = watermarkLabel;
                $(this).trigger("liszt:updated");
            }

        });

    }

};

//---------------------------------------------------