"use strict";

//*------------------------------------------------------------------*

var React = require('react');

var ReactDOMServer = require('react-dom/server');

var $ = require('jquery');
var jQuery = $;

require('jquery-ui');
require('../css/jquery-ui.min.css');

//*------------------------------------------------------------------*

var FactorTooltip = require('./factor-tooltip.jsx');

//*------------------------------------------------------------------*

require('../css/atlas.css');
require('../css/heatmap-and-anatomogram.css');

//*------------------------------------------------------------------*

/**
 * @param {Object} options
 * @param {string} options.contextRoot
 * @param {string} options.accessKey
 * @param {string} options.experimentAccession
 * @param {string} options.assayGroupId
 * @param {Object} options.element
 */
function initTooltip(options) {

    $(options.element).attr("title", "").tooltip({

        hide:false,

        show:false,

        tooltipClass: "gxaHelpTooltip gxaPvalueTooltipStyling gxaFactorTooltip",

        close: function() {
            $(".gxaFactorTooltip").remove();
        },

        content: function (callback) {
            $.ajax({
                url: options.contextRoot + "/rest/assayGroup-summary",
                data:{
                    experimentAccession: options.experimentAccession,
                    assayGroupId: options.assayGroupId,
                    accessKey: options.accessKey
                },
                type:"GET",
                success:function (data) {
                    var html =
                        ReactDOMServer.renderToString(
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
    initTooltip({contextRoot: contextRoot, accessKey: accessKey, element: element, experimentAccession: experimentAccession, assayGroupId: assayGroupId});
};
