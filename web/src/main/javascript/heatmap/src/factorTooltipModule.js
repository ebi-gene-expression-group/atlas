"use strict";

//*------------------------------------------------------------------*

var $ = require('jquery');
var jQuery = $;
var React = require('react');

//*------------------------------------------------------------------*

var FactorTooltip = require('./factorTooltip.jsx');

//*------------------------------------------------------------------*

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
                    var html = React.renderToString(FactorTooltip({properties: data.properties, replicates: data.replicates}));
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

//*------------------------------------------------------------------*

exports.init =
    function (contextRoot, accessKey, elements) {
            initTooltip(contextRoot, accessKey, elements || ".factorNameCell");
    };
