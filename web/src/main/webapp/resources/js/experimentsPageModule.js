/*
 * Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * For further details of the Gene Expression Atlas project, including source code,
 * downloads and documentation, please see:
 *
 * http://gxa.github.com/gxa
 */

"use strict";

var experimentsPageModule = (function ($) {

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
            return '<img src="resources/images/allup2_transparent_bkg.png" title="baseline"/>';
        }
        if (data === 'RNASEQ_MRNA_DIFFERENTIAL' || data === 'MICROARRAY_ANY') {
            return '<img src="resources/images/updown_transparent_bkg.png" title="differential"/>';
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

    function init(experimentType, kingdom, organism) {

        /* Create an array with the values of all the img title attributes in a column */
        $.fn.dataTableExt.afnSortData['dom-text'] = function (oSettings, iColumn) {
            return $.map(oSettings.oApi._fnGetTrNodes(oSettings), function (tr, i) {
                return $('td:eq(' + iColumn + ') img', tr).attr("title");
            });
        };
        /* This was taken from datatables examples */
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

        var oTable = $experimentsTable.dataTable({
            "bAutoWidth": false,
            "bProcessing":true,
            "sAjaxSource":"json/experiments",
            "aoColumns":[
                { "sTitle":"Type", "mData":"experimentType", "sClass":"center gxaBB gxaBL", "sSortDataType":"dom-text",
                    "mRender": function (data, type, full) {
                        return formatExperimentType(data);
                    } },
                { "sTitle":"Loaded", "mData":"lastUpdate", "sClass":"center gxaBB nowrap", 'sType': 'date-eu',
                    "mRender": function (data, type, full) {
                        return formatLastUpdate(data);
                    } },
                { "sTitle":"Experiment", "mData":"experimentDescription", "sClass":"center gxaBB padding",
                    "mRender": function (data, type, full) {
                        return formatExperimentDescription(data, full);
                    } },
                { "sTitle":"Assays", "mData":"numberOfAssays", "sClass":"center gxaBB", "sType":"title-numeric", "sWidth":"5%",
                    "mRender": function (data, type, full) {
                        return replaceZeroAndLinkExpDesign(data, type, full);
                    } },
                { "sTitle":"Comparisons", "mData":"numberOfContrasts", "sClass":"center gxaBB", "sType":"title-numeric",
                    "mRender": function (data, type, full) {
                        return replaceZeroAndLinkExpDesign(data, full);
                    } },
                { "sTitle":"Organisms", "mData":"species", "sClass":"center gxaBB italic", "sWidth":"10%",
                    "mRender": function (data, type, full) {
                        return withLineBreaks(data);
                    } },
                { "sTitle":"Experimental Variables", "mData":"experimentalFactors", "sClass":"center gxaBB",
                    "mRender": function (data, type, full) {
                        return withLineBreaks(data);
                    } },
                { "sTitle":"Array Designs", "mData":"arrayDesigns", "sClass":"center gxaBB", "sWidth":"15%",
                    "mRender": function (data, type, full) {
                        return formatArrayDesign(data, full);
                    } },
                { "sTitle":"ArrayExpress", "mData":"experimentAccession", "sClass":"center gxaBB gxaBR",
                    "mRender": function (data, type, full) {
                        return formatExperimentAccession(data);
                    } },
                { "sTitle":"Kingdom", "mData":"kingdom", "sClass":"center gxaBB italic", "bVisible": false }
            ],
            "aLengthMenu":[
                [10, 25, 50, 100, -1],
                [10, 25, 50, 100, "All"]
            ],
            "oLanguage":{
                "sSearch":"Search all columns:"
            }
        });

        $experimentsTable.find("thead th").addClass("gxaHeaderCell gxaBT");

        $experimentsTable.find("tfoot input").keyup(function () {
            /* Filter on the column (the index) of this element */
            oTable.fnFilter(this.value, $experimentsTable.find("tfoot input").index(this));
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
            oTable.fnFilter(value, $experimentsTable.find("tfoot select").index(selectionId));
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
                oTable.fnFilter('plants', 9);
            }
            else if (hiddenKingdomSelected === 'animals-fungi') {
                oTable.fnFilter('animals|fungi', 9, true);
            }
            else {
                oTable.fnFilter('', 9);
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

        $("#gxaExperimentsTableOrganismInput").val(organism).keyup();
    }

    return {
        init: init
    };

}(jQuery));
