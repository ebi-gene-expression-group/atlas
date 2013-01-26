/*global $,console,loadSliderAndPlot: false */
/*jslint browser:true */


//object constructor and function prototypes

/*
 Search form object incapsulating jquery functionalities
 */
var searchForm = {

    cutoff :0,

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

        var that = this;

        //ToDo: this should not be here, plot object should be passed to searchForm init, that way it could be mocked
        //ToDo: check about IE should also be responsibility of the client
        function updatePlot(selectedFactorValues) {

            if (that.isNotIE7orOlder()) {
                loadSliderAndPlot(cutoff, experimentAccession, selectedFactorValues);
            }
        }

        $("#submit-button").button();

        $("#reset-button").button().click(function (event) {
            var urlWithoutParameters = this.removeHttpParameters(window.location.href);
            window.location.replace(urlWithoutParameters);
        });

        $("#geneQuery").watermark("(all genes)");

        $("#cutoff").watermark("(default 0.5)");

        //ToDo: this should be in a plot object init method
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