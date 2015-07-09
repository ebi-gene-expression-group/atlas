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
var jQuery = $;
var React = require('react');

var ContrastTooltip = require('./contrastTooltip.jsx');


function initTooltip(contextRoot, accessKey, elements) {

    $(elements).attr("title", "").tooltip({

        hide:false,
        show:false,
        tooltipClass:"gxaHelpTooltip gxaPvalueTooltipStyling",
        content:function (callback) {

            //TODO: get this via parameter instead of from the DOM
            var experimentAccession = $(this).attr("data-experiment-accession"),
                contrastId = $(this).attr("data-contrast-id");
            if (experimentAccession === undefined) {
                experimentAccession = $(this).find(":nth-child(1)").attr("data-experiment-accession");
                contrastId = $(this).find(":nth-child(1)").attr("data-contrast-id");
            }

            $.ajax({
                url:contextRoot + "/rest/contrast-summary",
                data:{
                    experimentAccession:experimentAccession,
                    contrastId: contrastId,
                    accessKey: accessKey
                },
                type:"GET",
                success:function (data) {
                    var html = React.renderToString(ContrastTooltip({
                                            experimentDescription: data.experimentDescription,
                                            contrastDescription: data.contrastDescription,
                                            testReplicates: data.testReplicates,
                                            referenceReplicates: data.referenceReplicates,
                                            properties: data.properties}));
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


exports.init = function (contextRoot, accessKey, elements) {
            initTooltip(contextRoot, accessKey, elements || ".contrastNameCell");
};

