"use strict";

var geneSetEnrichmentModule = (function ($) {

    function formatExperimentDescription(data) {
        return '<a href=' + data.url + ' title="View experiment">' + data.name + '</a>';
    }

    //http://stackoverflow.com/a/26655674
    function formatPValue(num) {
        try{
            return (+num).toExponential(3).replace("e","x10<sup>")+"</sup>"
        }
        catch ( e) {
            return num;
        }
    }

    function _init(selector, allData) {

        var $experimentsTable = $(selector);
        //reset empty data message to avoid showing "Showing 0 to 0 of 0 entries"
        $experimentsTable.dataTable.defaults.oLanguage.sInfoEmpty = ' ';

        var table = $experimentsTable.DataTable({
            "autoWidth": false,
            "processing": true,
            "data": allData,
            "columns": [
                {
                    "title": "Experiment", "data": "experiment", "className": "center", "type": "center",
                    "render": function (data, type, full) {
                        return data;
                    }
                },
                {
                    "title": "Comparison", "data": "comparison_title", "className": "center", "type": "center",
                    "render": function (data, type, full) {
                        return formatExperimentDescription(data);
                    }
                },
                {
                    "title": "P-Value",
                    "data": "p-value",
                    "className": "center",
                    "type": "title-numeric",
                    "width": "10%",
                    "render": function (data, type, full) {
                        return type == 'sort' ? data : formatPValue(data);
                    }
                },
                {
                    "title": "Observed",
                    "data": "observed",
                    "className": "center",
                    "type": "title-numeric",
                    "width": "5%",
                    "render": function (data, type, full) {
                        return data;
                    }
                },
                {
                    "title": "Expected",
                    "data": "expected",
                    "className": "center",
                    "type": "title-numeric",
                    "width": "5%",
                    "render": function (data, type, full) {
                        return data;
                    }
                },
                {
                    "title": "Adjusted P-Value",
                    "data": "adjusted p-value",
                    "className": "center",
                    "type": "title-numeric",
                    "width": "10%",
                    "render": function (data, type, full) {
                        return type == 'sort' ? data : formatPValue(data);
                    }
                },
                {
                    "title": "Observed/Expected",
                    "data": "effect size",
                    "className": "center",
                    "type": "title-numeric",
                    "width": "10%",
                    "render": function (data, type, full) {
                        return type == 'sort' ? (isNaN(data) ? 0 : data) : data;
                    }
                }
            ],
            "order": [[6, "desc"]],
            "lengthMenu": [
                [10, 25, 50, 100, -1],
                [10, 25, 50, 100, "All"]
            ],
            dom: "<'row expanded margin-bottom-none'<'small-12 medium-2 medium-offset-10 columns text-right'B>>" +
                 "<'row expanded'<'small-6 columns'l><'small-6 columns'f>>" +
                 "<'row expanded'<'small-12 columns't>>" +
                 "<'row expanded'<'small-6 columns'i><'small-6 columns'p>>",
            buttons: [
              {
                extend: 'csv',
                text: '<span class="icon icon-functional" data-icon="="> Download results</span>',
                className: 'tiny'
              }
            ]
        });
    }

    return {
        init: _init
    };

}(jQuery));
