/*global $,jQuery,console,loadSliderAndPlot: false */
/*jslint browser:true */
/*jslint nomen: true*/

var experimentDesignTableModule = (function ($) {
    "use strict";

    var _dataSet,
        _runAccessions,
        _samples,
        _factors,
        _assayHeader;

    function initColumn(aoColumnDefs, values, startCount) {
        for (var value in values) {
            startCount = startCount + 1;

            aoColumnDefs[startCount] = {
                "sClass":"center bb",
                "sTitle":value,
                "aTargets":[ startCount ]
            };

        }
        aoColumnDefs[startCount].sClass = "center bb br";
    }

    /* populate all sub categories */
    function initColumnDefs() {
        var aoColumnDefs = [];
        aoColumnDefs[0] = { "sClass":"assays bb br bl", "sTitle":_assayHeader + "<span class='doc-span' data-help-loc='#runAccs'>", "aTargets":[ 0 ]};

        initColumn(aoColumnDefs, _samples, 0);

        /* for IE7 & IE8 */
        Object.keys = Object.keys || function (o) {
            var result = [];
            for (var name in o) {
                if (o.hasOwnProperty(name))
                    result.push(name);
            }
            return result;
        };

        initColumn(aoColumnDefs, _factors, Object.keys(_samples).length);

        return aoColumnDefs;
    }

    function _initExperimentDesignTable() {

        /* Custom filtering function which will filter analysed runs */
        $.fn.dataTableExt.afnFiltering.push(
            function (oSettings, aData, iDataIndex) {
                var only = $('#isOnlyAnalysed').is(':checked');
                if (!only || jQuery.inArray(aData[0], _runAccessions) > -1) {
                    return true;
                }
                return false;
            }
        );

        $('#isOnlyAnalysed').click(function () {
            oTable.fnDraw();
        });

        var $window = $(window);
        var calcDataTableHeight = function () {
            return $window.height() - 270;
        };
        var calcDataTableWidth = function () {
            return $('#contents').width() - 100;
        };

        var oTable = $('#experiment-design-table').dataTable({
            "aaData":_dataSet,
            "aoColumnDefs":initColumnDefs(),
            "bPaginate":false,
            "bScrollCollapse":true,
            "sScrollY":calcDataTableHeight(),
            "sScrollX":calcDataTableWidth(),
            "sDom":'i<"download">f<"clear">t'
        });

        $('div.download').html($('#download-button'));
        $('div.download').attr('style', 'float: right');


        $window.resize(function () {
            var oSettings = oTable.fnSettings();
            oSettings.oScroll.sY = calcDataTableHeight(); // <- updated!
            //oSettings.oScroll.sX = calcDataTableWidth();

            // maybe you need to redraw the table (not sure about this)
            oTable.fnAdjustColumnSizing(false);
            oTable.fnDraw(false);
        });

        var tableHeaderRow = $(".dataTables_scrollHeadInner").find('thead > tr');
        $("<tr><th id='assaysHeader' class='header-cell br bt bl'></th>" +
            "<th id='samplesHeader' class='samples br bt'>Sample Characteristics<span class='doc-span' data-help-loc='#sampleChars'></span></th>" +
            "<th id='factorsHeader' class='factors br bt'>Factor Values<span class='doc-span' data-help-loc='#factorValues'></span></th></tr>")
            .insertBefore(tableHeaderRow);

        /* Set colspan for each category */
        $('#samplesHeader').attr('colspan', Object.keys(_samples).length);
        $('#factorsHeader').attr('colspan', Object.keys(_factors).length);


        $('#download-experiment-design-link').button().tooltip();

    }

    function _init(assayHeader, dataSet, runAccessions, samples, factors) {
        _dataSet = dataSet;
        _runAccessions = runAccessions;
        _samples = samples;
        _factors = factors;
        _assayHeader = assayHeader;


        _initExperimentDesignTable();
    }

    return {
        init:_init
    };

}(jQuery));

