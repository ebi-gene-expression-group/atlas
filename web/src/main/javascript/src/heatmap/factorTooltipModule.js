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

var $ = require('jquery');
var React = require('react');

var FactorTooltip = require('./factorTooltip.jsx');

function initTooltip(contextRoot, accessKey, elements) {

    $(elements).attr("title", "").tooltip({

        hide:false,
        show:false,
        tooltipClass:"gxaHelpTooltip gxaPvalueTooltipStyling",
        content:function (callback) {

            //TODO: get this via a function parameter instead of the DOM
            var experimentAccession = $(this).attr("data-experiment-accession"),
                assayGroupId = $(this).attr("data-assay-group-id");
            if (experimentAccession === undefined) {
                experimentAccession = $(this).find(":nth-child(1)").attr("data-experiment-accession");
                assayGroupId = $(this).find(":nth-child(1)").attr("data-assay-group-id");
            }

            $.ajax({
                url:contextRoot + "/rest/assayGroup-summary",
                data:{
                    experimentAccession:experimentAccession,
                    assayGroupId: assayGroupId,
                    accessKey: accessKey
                },
                type:"GET",
                success:function (data) {
                    var html = React.renderComponentToString(FactorTooltip({properties: data.properties, replicates: data.replicates}));
                    callback(html);
                }
            }).fail(function (data) {
                    //"Sorry but there was an error: " + xhr.status + " " + xhr.statusText
                    console.log("ERROR:  " + data);
                    callback("ERROR: " + data);
            });
        }
    });
};

exports.init =
    function (contextRoot, accessKey, elements) {
            initTooltip(contextRoot, accessKey, elements || ".factorNameCell");
    };
