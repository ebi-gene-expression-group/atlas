"use strict";

//*------------------------------------------------------------------*

var $ = require('jquery');
var jQuery = $;
var React = require('react');

//*------------------------------------------------------------------*

var FactorTooltip = require('./factor-tooltip.jsx');
require('../css/atlas.css');
require('../css/heatmap-and-anatomogram.css');

//*------------------------------------------------------------------*

function initTooltip(contextRoot, accessKey, element, experimentAccession, assayGroupId) {

    $(element).attr("title", "").tooltip({

        hide:false,

        show:false,

        tooltipClass: "gxaHelpTooltip gxaPvalueTooltipStyling gxaFactorTooltip",

        close: function() {
            $(".gxaFactorTooltip").remove();
        },

        content: function (callback) {
            $.ajax({
                url:contextRoot + "/rest/assayGroup-summary",
                data:{
                    experimentAccession:experimentAccession,
                    assayGroupId: assayGroupId,
                    accessKey: accessKey
                },
                type:"GET",
                success:function (data) {
                    var html =
                        React.renderToString(
                            React.createElement(
                                FactorTooltip,
                                {
                                    properties: data.properties,
                                    replicates: data.replicates
                                }
                            )
                        );
                    callback(html);
                }
            }).fail(function (data) {
                    console.log("ERROR:  " + data);
                    callback("ERROR: " + data);
            });
        }
    });
}

//*------------------------------------------------------------------*

exports.init = function (contextRoot, accessKey, element, experimentAccession, assayGroupId) {
    initTooltip(contextRoot, accessKey, element, experimentAccession, assayGroupId);
};
