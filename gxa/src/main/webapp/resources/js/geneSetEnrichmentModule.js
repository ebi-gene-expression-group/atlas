"use strict";

var geneSetEnrichmentModule = (function ($) {

    function formatExperimentDescription(data) {
        return '<a href=' + data.url + ' title="View experiment">' + data.name + '</a>';
    }


    function _init(allData) {

        var $experimentsTable = $("#gene-set-enrichment-results-table");
        //reset empty data message to avoid showing "Showing 0 to 0 of 0 entries"
        $experimentsTable.dataTable.defaults.oLanguage.sInfoEmpty = ' ';

        var oTable = $experimentsTable.DataTable({
            "autoWidth": false,
            "processing": true,
            "data":allData,
            "columns":[
                { "title":"Experiment", "data":"experiment", "className":"center", "type":"center",
                    "render": function (data, type, full) {
                        return formatExperimentDescription(data);
                    } },
                { "title":"Comparison", "data":"comparison_title", "className":"center", "type":"center",
                    "render": function (data, type, full) {
                        return data;
                    } },
                { "title":"P-Value", "data":"p-value", "className":"center", "type":"title-numeric", "width":"10%",
                    "render": function (data, type, full) {
                        return data;
                    } },
                { "title":"Observed", "data":"observed", "className":"center", "type":"title-numeric", "width":"5%",
                    "render": function (data, type, full) {
                        return data;
                    } },
                { "title":"Expected", "data":"expected", "className":"center", "type":"title-numeric", "width":"5%",
                    "render": function (data, type, full) {
                        return data;
                    } },
                { "title":"Adjusted P-Value", "data":"adjusted p-value", "className":"center", "type":"title-numeric", "width":"10%",
                    "render": function (data, type, full) {
                        return data;
                    } },
                { "title":"Effect Size", "data":"effect size", "className":"center bold", "type":"title-numeric", "width":"10%",
                    "render": function (data, type, full) {
                        return data;
                    } }
            ],
            "order": [[ 6, "desc" ]],
            "lengthMenu":[
                [10, 25, 50, 100, -1],
                [10, 25, 50, 100, "All"]
            ]
        });
    }

    return {
        init: _init
    };

}(jQuery));
