/*global $,jQuery,console,loadSliderAndPlot: false */
/*jslint browser:true */
/*jslint nomen: true*/

var experimentDesignTableModule = (function ($) {
    "use strict";

    var _dataSet,
        _runAccessions,
        _samples,
        _factors,
        _assayHeader,
        _experimentAccession;


    /* populate all sub categories */
    function initColumnDefs() {
        var aoColumnDefs = [];
        aoColumnDefs[0] = { "sClass": "assays bb br bl", "sTitle": _assayHeader + "<span class='doc-span' data-help-loc='#runAccs'>", "aTargets": [ 0 ]};

        function initColumn(values, startCount) {
            for (var value in values) {
                startCount = startCount + 1;

                aoColumnDefs[startCount] = {
                    "sClass": "center bb",
                    "sTitle": value,
                    "aTargets": [ startCount ]
                };

            }
            aoColumnDefs[startCount].sClass = "center bb br";
        }

        initColumn(_samples, 0);

        initColumn(_factors, Object.keys(_samples).length);


        $('#headerStub th:last()').attr("class", "header-cell bb br");
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

        var $window = $(window);
        var calcDataTableHeight = function () {
            return $window.height() - 270;
        };
        var calcDataTableWidth = function () {
            return $('#contents').width() - 100;
        };

        var oTable = $('#experiment-design-table').dataTable({
            "aaData": _dataSet,
            "aoColumnDefs": initColumnDefs(),
            "bPaginate": false,
            "bScrollCollapse": true,
            "sScrollY": calcDataTableHeight(),
            "sScrollX": calcDataTableWidth(),
            "sDom": 'i<"download">f<"clear">t'
        });

        $('div.download').html('<a id="download-experiment-design-link" title="Download experiment design" class="button-image" style="margin-bottom:5px" href="experiments/' + _experimentAccession + '/experiment-design.tsv" target="_blank">' +
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

        var tableHeaderRow = $(".dataTables_scrollHeadInner").find('thead > tr');
        $("<tr><th id='assaysHeader' class='header-cell br bt bl'></th>" +
            "<th id='samplesHeader' class='samples br bt'>Sample Characteristics<span class='doc-span' data-help-loc='#sampleChars'></span></th>" +
            "<th id='factorsHeader' class='factors br bt'>Factor Values<span class='doc-span' data-help-loc='#factorValues'></span></th></tr>").insertBefore(tableHeaderRow);

        /* Set colspan for each category */
        $('#samplesHeader').attr('colspan', Object.keys(_samples).length);
        $('#factorsHeader').attr('colspan', Object.keys(_factors).length);


        $('#download-experiment-design-link').button().tooltip();

    }

    function _init(assayHeader, experimentAccession, dataSet, runAccessions, samples, factors) {
        _dataSet = dataSet;
        _runAccessions = runAccessions;
        _samples = samples;
        _factors = factors;
        _experimentAccession = experimentAccession;
        _assayHeader = assayHeader;


        _initExperimentDesignTable();
    }

    return {
        init: _init
    };

}(jQuery));

