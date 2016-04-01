"use strict";

//*------------------------------------------------------------------*

var React = require('react');
var ReactDOMServer = require('react-dom/server');

var $ = require('jquery');
require('jquery-ui-bundle');

//*------------------------------------------------------------------*

var FactorTooltip = require('./FactorTooltip.jsx');

//*------------------------------------------------------------------*

require('./factorTooltipModule.css');

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

        tooltipClass: "gxaFactorTooltip",

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
