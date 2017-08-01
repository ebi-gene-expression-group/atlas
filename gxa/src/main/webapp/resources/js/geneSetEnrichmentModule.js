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

        var oTable = $experimentsTable.DataTable({
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
                        return data;
                    }
                }
            ],
            "order": [[5, "asc"]],
            "lengthMenu": [
                [10, 25, 50, 100, -1],
                [10, 25, 50, 100, "All"]
            ],
            dom: '<"button primary white-color float-left margin-right-large"B>lfrtip',
            language: {
                buttons: {
                    csv: "Download all results"
                }
            },
            buttons: [
                "csvHtml5"
            ]
        });
    }

    return {
        init: _init
    };

}(jQuery));
