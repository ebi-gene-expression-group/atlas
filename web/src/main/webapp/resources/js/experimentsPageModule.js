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

var experimentsPageModule = (function ($) {

    "use strict";

    function replaceZeroAndLinkExpDesign(data, type, full) {
        if (data === 0) {
            return '<span title="' + data + '"/>';
        }
        return '<span title="' + data + '">' +
            '<a href="experiments/' + full.experimentAccession + '/experiment-design" title="View experiment design in Expression Atlas">' + data +
            '</a></span>';
    }

    function withLineBreaks(data, type, full) {
        var i, html;
        html = '';
        for (i = 0; i < data.length; i += 1) {
            html += data[i] + '<br/>';
        }
        return html;
    }

    function formatExperimentType(data, type, full) {
        if (data === 'BASELINE') {
            return '<img src="resources/images/allup2_transparent_bkg.png" title="baseline"/>';
        }
        if (data === 'DIFFERENTIAL' || data === 'MICROARRAY') {
            return '<img src="resources/images/updown_transparent_bkg.png" title="differential"/>';
        }
        return data;
    }

    function formatExperimentAccession(data, type, full) {
        return '<a href="http://www.ebi.ac.uk/arrayexpress/experiments/' + data + '" title="View in Array Express">' + data + '</a>';
    }

    function formatLastUpdate(data, type, full) {
        return data;
    }

    function formatExperimentDescription(data, type, full) {
        return '<a href="experiments/' + full.experimentAccession + '" title="View in Expression Atlas">' + data + '</a>';
    }

    function formatArrayDesign(data, type, full) {
        return '<a href="http://www.ebi.ac.uk/arrayexpress/arrays/' + data + '" title="View in Array Express">' + data + '</a>';
    }

    function init() {

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

        $('#experiments-table').dataTable({
            "bProcessing":true,
            "sAjaxSource":"json/experiments",
            "aoColumns":[
                { "sTitle":"Type", "mData":"experimentType", "sClass":"center bb bl", "sSortDataType":"dom-text",
                    "mRender":function (data, type, full) {
                        return formatExperimentType(data, type, full);
                    } },
                { "sTitle":"Experiment", "mData":"experimentAccession", "sClass":"center bb",
                    "mRender":function (data, type, full) {
                        return formatExperimentAccession(data, type, full);
                    } },
                { "sTitle":"Loaded", "mData":"lastUpdate", "sClass":"center bb",
                    "mRender":function (data, type, full) {
                        return formatLastUpdate(data, type, full);
                    } },
                { "sTitle":"Description", "mData":"experimentDescription", "sClass":"center bb padding",
                    "mRender":function (data, type, full) {
                        return formatExperimentDescription(data, type, full);
                    } },
                { "sTitle":"Assays", "mData":"numberOfAssays", "sClass":"center bb", "sType":"title-numeric",
                    "mRender":function (data, type, full) {
                        return replaceZeroAndLinkExpDesign(data, type, full);
                    } },
                { "sTitle":"Contrasts", "mData":"numberOfContrasts", "sClass":"center bb", "sType":"title-numeric",
                    "mRender":function (data, type, full) {
                        return replaceZeroAndLinkExpDesign(data, type, full);
                    } },
                { "sTitle":"Organisms", "mData":"species", "sClass":"center bb italic",
                    "mRender":function (data, type, full) {
                        return withLineBreaks(data, type, full);
                    } },
                { "sTitle":"Experimental Factors", "mData":"experimentalFactors", "sClass":"center bb",
                    "mRender":function (data, type, full) {
                        return withLineBreaks(data, type, full);
                    } },
                { "sTitle":"Array Designs", "mData":"arrayDesigns", "sClass":"center bb br",
                    "mRender":function (data, type, full) {
                        return formatArrayDesign(data, type, full);
                    } }
            ],
            "aLengthMenu":[
                [10, 25, 50, 100, -1],
                [10, 25, 50, 100, "All"]
            ]
        });

        $("#experiments-table th").addClass("header-cell bt");

    }

    return {

        init:init

    };

}(jQuery));
