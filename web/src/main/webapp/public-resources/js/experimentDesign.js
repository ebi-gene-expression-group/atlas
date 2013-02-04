/*global $,jQuery,console,loadSliderAndPlot: false */
/*jslint browser:true */
/*jslint nomen: true*/

var experimentDesignTableModule = (function ($) {
    "use strict";

    var _dataSet,
        _runAccessions,
        _samples,
        _factors,
        _experimentAccession;

    //ToDo: review implementation
    var valuesSize = function (obj) {
        var size = 0, key;
        for (key in obj) {
            if (obj.hasOwnProperty(key)) size++;
        }
        return size;
    };

    /* populate all sub categories */
    function initColumnDefs() {
        var aoColumnDefs = [];
        aoColumnDefs[0] = { "sClass": "assays bb br bl", "aTargets": [ 0 ] };

        function initHeaderRowSection(values, startCount) {
            for (var value in values) {
                $('#headerStub').append("<th class=\"header-cell bb\">" + value + "</th>");
                aoColumnDefs[++startCount] = { "sClass": "center bb", "aTargets": [ startCount ] };
            }
            // show vertical line for the last element
            aoColumnDefs[startCount]["sClass"] = "center bb br";
        }

        initHeaderRowSection(_samples, 0);

        initHeaderRowSection(_factors, valuesSize(_samples));

        $('#headerStub th:last()').attr("class", "header-cell bb br");
        return aoColumnDefs;
    }

    function _initExperimentDesignTable() {

        /* Set colspan for each category */
        $('#samplesHeader').attr('colspan', valuesSize(_samples));
        $('#factorsHeader').attr('colspan', valuesSize(_factors));

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
            return $window.width() - 100;
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

        $('#download-experiment-design-link').button().tooltip();

    }

    function _init(experimentAccession, dataSet, runAccessions, samples, factors) {
        _dataSet = dataSet;
        _runAccessions = runAccessions;
        _samples = samples;
        _factors = factors;
        _experimentAccession = experimentAccession;


        _initExperimentDesignTable();
    }

    return {
        init: _init
    };

}(jQuery));

