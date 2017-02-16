/**
 * Created by barrera on 19/01/2017.
 */
"use strict";

var foundationExperimentsPageModule = (function ($) {

    function replaceZeroAndLinkExpDesign(data, full) {
        if (data === 0) {
            return '<span title="' + data + '"/>';
        }
        return '<span title="' + data + '">' +
            '<a href="experiments/' + full.experimentAccession + '/experiment-design" title="View experiment design in Expression Atlas">' + data +
            '</a></span>';
    }

    function withLineBreaks(data) {
        var i, html;
        html = '';
        for (i = 0; i < data.length; i += 1) {
            html += data[i] + '<br/>';
        }
        return html;
    }

    function formatExperimentType(data) {
        if (data === 'RNASEQ_MRNA_BASELINE' || data === 'PROTEOMICS_BASELINE') {
            return '<img src="resources/images/allup2_transparent_bkg.png" alt="baseline"/>';
        }
        if (data === 'RNASEQ_MRNA_DIFFERENTIAL' || data === 'MICROARRAY_ANY') {
            return '<img src="resources/images/updown_transparent_bkg.png" alt="differential"/>';
        }
        return data;
    }

    function formatExperimentAccession(data) {
        return '<a href="http://www.ebi.ac.uk/arrayexpress/experiments/' + data + '" title="View in Array Express">' +
            '<span class="icon icon-generic" data-icon="L"></span>' + '</a>';
    }

    function formatLastUpdate(data) {
        return data;
    }

    function formatExperimentDescription(data, full) {
        return '<a href="experiments/' + full.experimentAccession + '" title="View in Expression Atlas">' + data + '</a>';
    }

    function formatArrayDesign(data, full) {
        var result = "";
        $(data).each(function (index, elem) {
            result = result + '<a href="http://www.ebi.ac.uk/arrayexpress/arrays/' + elem + '" title="View in Array Express">' + full.arrayDesignNames[index] + '</a><br/>';
        });

        return result;
    }

    var asInitVals = [];

    function _init(experimentType, kingdom, organism) {

        /* Sort on the 'alt' tags of images in a column */
        $.extend($.fn.dataTableExt.oSort, {
            "alt-string-pre": function ( a ) {
                return a.match(/alt="(.*?)"/)[1].toLowerCase();
            },

            "alt-string-asc": function( a, b ) {
                return ((a < b) ? -1 : ((a > b) ? 1 : 0));
            },

            "alt-string-desc": function(a,b) {
                return ((a < b) ? 1 : ((a > b) ? -1 : 0));
            }
        } );

        /* This was taken from datatables examples
         * This sorting function pair will use the * 'title' attribute of en empty span element (or anything else)
         * to sort numerically (for example `<span title="1000000"><span>1'000'000`) */
        $.extend($.fn.dataTableExt.oSort, {
            "title-numeric-pre":function (a) {
                var x = a.match(/title="*(-?[0-9\.]+)/)[1];
                return parseFloat(x);
            },

            "title-numeric-asc":function (a, b) {
                return ((a < b) ? -1 : ((a > b) ? 1 : 0));
            },

            "title-numeric-desc":function (a, b) {
                return ((a < b) ? 1 : ((a > b) ? -1 : 0));
            }
        });

        /* Sorting by date */
        $.extend($.fn.dataTableExt.oSort, {
            "date-eu-pre": function ( date ) {
                date = date.replace(" ", "");

                /*date a, format dd/mn/(yyyy) ; (year is optional)*/
                var eu_date = date.split('-');

                /*year (optional)*/
                var year = eu_date[2] ? eu_date[2] : 0;

                /*month*/
                var month = eu_date[1];
                if (month.length == 1) {
                    month = 0 + month;
                }

                /*day*/
                var day = eu_date[0];
                if (day.length == 1) {
                    day = 0 + day;
                }

                return (year + month + day) * 1;
            },

            "date-eu-asc": function ( a, b ) {
                return ((a < b) ? -1 : ((a > b) ? 1 : 0));
            },

            "date-eu-desc": function ( a, b ) {
                return ((a < b) ? 1 : ((a > b) ? -1 : 0));
            }
        } );

        var $experimentsTable = $("#experiments-table");
        //reset empty data message to avoid showing "Showing 0 to 0 of 0 entries"
        $experimentsTable.dataTable.defaults.oLanguage.sInfoEmpty = ' ';

        var oTable = $experimentsTable.DataTable({
            "autoWidth": false,
            "processing": true,
            "ajax":"json/experiments",
            "columns":[
                { "title":"Type", "data":"experimentType", "className":"center", "type":"alt-string",
                    "render": function (data, type, full) {
                        return formatExperimentType(data);
                    } },
                { "title":"Loaded", "data":"lastUpdate", "className":"center nowrap", 'type': 'date-eu',
                    "render": function (data, type, full) {
                        return formatLastUpdate(data);
                    } },
                { "title":"Experiment", "data":"experimentDescription", "className":"center",
                    "render": function (data, type, full) {
                        return formatExperimentDescription(data, full);
                    } },
                { "title":"Assays", "data":"numberOfAssays", "className":"center", "type":"title-numeric", "width":"5%",
                    "render": function (data, type, full) {
                        return replaceZeroAndLinkExpDesign(data, type, full);
                    } },
                { "title":"Comparisons", "data":"numberOfContrasts", "className":"center", "type":"title-numeric",
                    "render": function (data, type, full) {
                        return replaceZeroAndLinkExpDesign(data, full);
                    } },
                { "title":"Organisms", "data":"species", "className":"center italic", "width":"10%",
                    "render": function (data, type, full) {
                        return data;
                    } },
                { "title":"Experimental Variables", "data":"experimentalFactors", "className":"center",
                    "render": function (data, type, full) {
                        return withLineBreaks(data);
                    } },
                { "title":"Array Designs", "data":"arrayDesigns", "className":"center", "width":"15%",
                    "render": function (data, type, full) {
                        return formatArrayDesign(data, full);
                    } },
                { "title":"ArrayExpress", "data":"experimentAccession", "className":"center",
                    "render": function (data, type, full) {
                        return formatExperimentAccession(data);
                    } },
                { "title":"Kingdom", "data":"kingdom", "visible": false }
            ],
            "lengthMenu":[
                [10, 25, 50, 100, -1],
                [10, 25, 50, 100, "All"]
            ],
            "language":{
                "search":"Search all columns:"
            }
        });

        $('#gxaExperimentsTableDescriptionInput').on('keyup', function () {
            oTable
                .columns(2)
                .search(this.value)
                .draw();
        });

        $('#gxaExperimentsTableOrganismInput').on('keyup', function () {
            oTable
                .columns(5)
                .search(this.value)
                .draw();
        });

        $('#gxaExperimentsTableFactorsInput').on('keyup', function () {
            oTable
                .columns(6)
                .search(this.value)
                .draw();
        });

        var $experimentsTableKingdomSelect = $("#gxaExperimentsTableKingdomSelect");
        $experimentsTableKingdomSelect.val(kingdom.toLowerCase());
        var $experimentsTableTypeSelect = $("#gxaExperimentsTableExperimentTypeSelect");
        $experimentsTableTypeSelect.val(experimentType.toLowerCase());

        /*
         * Filter by experiment type
         */
        var hiddenTypeSelected = $("#hiddenTypeSelected").val();
        if(experimentType.toLowerCase()!= "") {
            hiddenTypeSelected = experimentType.toLowerCase();
        }

        $experimentsTableTypeSelect.change(function () {
            var selected = $experimentsTableTypeSelect.find(":selected").val();

            if(hiddenTypeSelected != selected) {
                hiddenTypeSelected = selected;
                $("#hiddenTypeSelected").val(selected);
                $experimentsTableTypeSelect.val(hiddenTypeSelected);
            }
            filterByExperimentType(this.value, this);
        });

        $experimentsTableTypeSelect.val(hiddenTypeSelected);
        if(hiddenTypeSelected != undefined) {
            filterByExperimentType(hiddenTypeSelected, $experimentsTableTypeSelect);
        }

        function filterByExperimentType(value, selectionId) {
            /* same for drop down filter */
            oTable.columns(0)
                  .search(value)
                  .draw();
        }

        /*
         * Filter by kingdom
         */
        var hiddenKingdomSelected = $("#hiddenKingdomSelected").val();
        if(kingdom.toLowerCase()!= "") {
            hiddenKingdomSelected = kingdom.toLowerCase();
        }

        $experimentsTableKingdomSelect.change(function () {
            var selected = $experimentsTableKingdomSelect.find(":selected").val();

            if(hiddenKingdomSelected != selected) {
                hiddenKingdomSelected = selected;
                $("#hiddenKingdomSelected").val(selected);
                $("#gxaExperimentsTableKingdomSelect").val(hiddenKingdomSelected);
            }
            filterByKingdom();
        });

        $experimentsTableKingdomSelect.val(hiddenKingdomSelected);
        if(hiddenKingdomSelected != undefined) {
            filterByKingdom();
        }

        function filterByKingdom() {
            if (hiddenKingdomSelected === 'plants') {
                oTable
                    .columns(9)
                    .search('plants')
                    .draw();
            }
            else if (hiddenKingdomSelected === 'animals-fungi') {
                oTable
                    .columns(9)
                    .search('animals|fungi', true)
                    .draw();
            }
            else if (hiddenKingdomSelected === 'animals') {
                oTable
                    .columns(9)
                    .search('animals', true)
                    .draw();
            }
            else if (hiddenKingdomSelected === 'fungi') {
                oTable
                    .columns(9)
                    .search('fungi', true)
                    .draw();
                }
            else {
                oTable
                    .columns(9)
                    .search('')
                    .draw();
            }
        }

        /*
         * Support functions to provide a little bit of 'user friendliness' to the text boxes in
         * the footer
         */
        $experimentsTable.find("tfoot input").each(function (i) {
            asInitVals[i] = this.value;
        });

        $experimentsTable.find("tfoot input").focus(function () {
            if (this.className === "search_init") {
                this.className = "";
                this.value = "";
            }
        });

        $experimentsTable.find("tfoot input").blur(function () {
            if (this.value === "") {
                this.className = "search_init";
                this.value = asInitVals[$experimentsTable.find("tfoot input").index(this)];
            }
        });

        if (organism) {
            $("#gxaExperimentsTableOrganismInput").val(organism).keyup();
        }
    }

    return {
        init: _init
    };

}(jQuery));
