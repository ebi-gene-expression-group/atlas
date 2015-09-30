"use strict";

//*------------------------------------------------------------------*

var $ = require('jquery');
require('jquery-ui');

//*------------------------------------------------------------------*

require('../css/atlas.css');

//*------------------------------------------------------------------*

var _contextRoot;

function buildHelpAnchor() {
    return $("<a/>", {
        class: "help-icon",
        href: "#",
        title: "",
        text: "?"
    });
}

function getHelpFileName(pageName){
    return "help-tooltips." + pageName + "-page.html";
}

function initTooltips(contextRoot, pageName, parentElementId) {

    var anchor = buildHelpAnchor();

    var helpSelector = (typeof parentElementId === "object") ? parentElementId : (parentElementId == "") ? "[data-help-loc]" : "#" + parentElementId + " [data-help-loc]";

    $(helpSelector)
        .append(anchor)
        .click(function (e) {
            e.preventDefault();
        })
        .tooltip(
        {
            tooltipClass: "gxaHelpTooltip",
            content: function (callback) {
                var tooltipHelpHtmlId = $(this).parent().attr("data-help-loc");

                $.get(contextRoot + "/resources/html/" + getHelpFileName(pageName),
                    function (response, status, xhr) {
                        var tooltipContent;

                        if (status === "error") {
                            tooltipContent = "Sorry but there was an error: " + xhr.status + " " + xhr.statusText;
                            callback(tooltipContent);
                            return;
                        }

                        tooltipContent = $(response).filter(tooltipHelpHtmlId).text();
                        if (!tooltipContent) {
                            tooltipContent = "Missing help section for id = " + tooltipHelpHtmlId + " in html file " + getHelpFileName(pageName);
                        }

                        callback(tooltipContent);
                    }
                );
            }
        }
    );

}

//*------------------------------------------------------------------*

module.exports = initTooltips;
