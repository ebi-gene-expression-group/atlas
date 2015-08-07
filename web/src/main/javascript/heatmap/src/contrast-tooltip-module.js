"use strict";

//*------------------------------------------------------------------*

var $ = require('jquery');
var jQuery = $;

require('jquery-ui');
require('../css/jquery-ui.min.css');

var React = require('react');

//*------------------------------------------------------------------*

var ContrastTooltip = require('./contrast-tooltip.jsx');
require('../css/atlas.css');
require('../css/heatmap-and-anatomogram.css');

//*------------------------------------------------------------------*

function initTooltip(contextRoot, accessKey, element, experimentAccession, contrastId) {

    $(element).attr("title", "").tooltip({

        hide: false,

        show: false,

        tooltipClass:"gxaHelpTooltip gxaPvalueTooltipStyling",

        content: function (callback) {
            $.ajax({
                url:contextRoot + "/rest/contrast-summary",
                data:{
                    experimentAccession: experimentAccession,
                    contrastId: contrastId,
                    accessKey: accessKey
                },
                type:"GET",
                success:function (data) {
                    var html =
                        React.renderToString(
                            React.createElement(
                                ContrastTooltip,
                                {
                                    experimentDescription: data.experimentDescription,
                                    contrastDescription: data.contrastDescription,
                                    testReplicates: data.testReplicates,
                                    referenceReplicates: data.referenceReplicates,
                                    properties: data.properties
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

exports.init = function (contextRoot, accessKey, elements, experimentAccession, contrastId) {
    initTooltip(contextRoot, accessKey, elements, experimentAccession, contrastId);
};

