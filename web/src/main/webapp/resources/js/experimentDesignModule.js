/*global $,jQuery,console,loadSliderAndPlot: false */
/*jslint browser:true */
/*jslint nomen: true*/

var experimentDesignTableModule = (function ($) {
    "use strict";

    var _dataSet,
        _runAccessions,
        _sampleHeaders,
        _factorHeaders,
        _assayHeaders;

    function initColumns(aoColumnDefs, values, startingFromColumnIndex) {
        for (var value in values) {
            aoColumnDefs[startingFromColumnIndex] = {
                "sClass":"center bb",
                "sTitle":values[value],
                "aTargets":[ startingFromColumnIndex ]
            };
            ++startingFromColumnIndex;

        }
        aoColumnDefs[startingFromColumnIndex - 1].sClass = "center bb br";
    }

    /* populate all sub categories */
    function initColumnDefs() {
        var aoColumnDefs = [];
        if (_assayHeaders.length === 1) {
            aoColumnDefs[0] = { "sClass":"assays bb bl br", "sTitle":_assayHeaders[0] + "<span class='doc-span' data-help-loc='#runAccs'>", "aTargets":[ 0 ]};
        } else {
            aoColumnDefs[0] = { "sClass":"assays bb bl", "sTitle":_assayHeaders[0] + "<span class='doc-span' data-help-loc='#assayAccs'>", "aTargets":[ 0 ]};
            aoColumnDefs[1] = { "sClass":"assays bb br", "sTitle":_assayHeaders[1] + "<span class='doc-span' data-help-loc='#arrayAccs'>", "aTargets":[ 1 ]};
        }

        initColumns(aoColumnDefs, _sampleHeaders, _assayHeaders.length);

        /* for IE7 & IE8 */
        Object.keys = Object.keys || function (o) {
            var result = [];
            for (var name in o) {
                if (o.hasOwnProperty(name))
                    result.push(name);
            }
            return result;
        };

        initColumns(aoColumnDefs, _factorHeaders, _assayHeaders.length + _sampleHeaders.length);

        return aoColumnDefs;
    }

    function _initExperimentDesignTable() {

        /* Custom filtering function which will filter analysed runs */
        $.fn.dataTableExt.afnFiltering.push(
            function (oSettings, aData, iDataIndex) {
                var showAllRuns = !$('#showOnlyAnalysedRuns').is(':checked');
                var runAccession = aData[0];
                if (showAllRuns || jQuery.inArray(runAccession, _runAccessions) > -1) {
                    return true;
                }
                return false;
            }
        );

        $('#showOnlyAnalysedRuns').click(function () {
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
        $('#assaysHeader').attr('colspan', Object.keys(_assayHeaders).length);
        $('#samplesHeader').attr('colspan', Object.keys(_sampleHeaders).length);
        $('#factorsHeader').attr('colspan', Object.keys(_factorHeaders).length);


        $('#download-experiment-design-link').button().tooltip();

    }

    function _init(assayHeaders, dataSet, runAccessions, sampleHeaders, factorHeaders) {
        _dataSet = dataSet;
        _runAccessions = runAccessions;
        _sampleHeaders = sampleHeaders;
        _factorHeaders = factorHeaders;
        _assayHeaders = assayHeaders;


        _initExperimentDesignTable();
    }

    return {
        init:_init
    };

}(jQuery));

