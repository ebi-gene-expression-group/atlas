/*global $,jQuery,console,loadSliderAndPlot: false */
/*jslint browser:true */
/*jslint nomen: true*/

var experimentDesignTableModule = (function ($) {
    "use strict";

    var aDataSet;
    var aRunAccessions;
    var aSamples;
    var aFactors;

    function initExperimentDesignTable() {

        var $window = $(window);
        var calcDataTableHeight = function () {
            return $window.height() - 270;
        };
        var calcDataTableWidth = function () {
            return $window.width() - 100;
        };

        $.assocArraySize = function (obj) {
            var size = 0, key;
            for (key in obj) {
                if (obj.hasOwnProperty(key)) size++;
            }
            return size;
        };

        /* Set colspan for each category */
        $('#samplesHeader').attr('colspan', $.assocArraySize(aSamples));
        $('#factorsHeader').attr('colspan', $.assocArraySize(aFactors));

        /* populate all sub categories */
        var aoColumnDefs = new Array();
        var i = 0;
        aoColumnDefs[i] = { "sClass": "assays bb br bl", "aTargets": [ i ] };
        for (var sample in aSamples) {
            $('#headerStub').append("<th class=\"header-cell bb\">" + sample + "</th>");
            aoColumnDefs[++i] = { "sClass": "center bb", "aTargets": [ i ] };
        }
        aoColumnDefs[i]["sClass"] = "center bb br";
        $('#headerStub th:last()').attr("class", "header-cell bb br");
        for (var factor in aFactors) {
            $('#headerStub').append("<th class=\"header-cell bb\">" + factor + "</th>");
            aoColumnDefs[++i] = { "sClass": "center bb", "aTargets": [ i ] };
        }
        aoColumnDefs[i]["sClass"] = "center bb br";
        $('#headerStub th:last()').attr("class", "header-cell bb br");

        /* Custom filtering function which will filter analysed runs */
        $.fn.dataTableExt.afnFiltering.push(
            function (oSettings, aData, iDataIndex) {
                var only = $('#isOnlyAnalysed').is(':checked');
                if (!only || jQuery.inArray(aData[0], aRunAccessions) > -1) {
                    return true;
                }
                return false;
            }
        );

        var oTable = $('#experiment-design-table').dataTable({
            "aaData": aDataSet,
            "aoColumnDefs": aoColumnDefs,
            "bPaginate": false,
            "bScrollCollapse": true,
            "sScrollY": calcDataTableHeight(),
            "sScrollX": calcDataTableWidth(),
            "sDom": 'i<"download">f<"clear">t'
        });

        $('div.download').html('<a id="download-experiment-design-link" title="Download experiment design" class="button-image" style="margin-bottom:5px" href="experiments/${experimentAccession}/experiment-design.tsv" target="_blank">' +
            '<img id="download-experiment-design" alt="Download experiment design" src="resources/images/download_blue_small.png"></a>');
        $('div.download').attr('style', 'float: right');
        $('#isOnlyAnalysed').click(function () {
            oTable.fnDraw();
        });

        $window.resize(function () {
            var oSettings = oTable.fnSettings();
            oSettings.oScroll.sY = calcDataTableHeight(); // <- updated!
            //oSettings.oScroll.sX = calcDataTableWidth();

            // maybe you need to redraw the table (not sure about this)
            oTable.fnAdjustColumnSizing(false);
            oTable.fnDraw(false);
        });

        $('#download-experiment-design-link').button().tooltip();

    }

    function init(_aDataSet, _aRunAccessions, _aSamples, _aFactors) {
        aDataSet = _aDataSet;
        aRunAccessions = _aRunAccessions;
        aSamples = _aSamples;
        aFactors = _aFactors;


        initExperimentDesignTable();
    }

    return {
        init: init
    };

}(jQuery));

