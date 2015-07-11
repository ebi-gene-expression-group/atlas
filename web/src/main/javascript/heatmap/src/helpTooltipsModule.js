"use strict";

//*------------------------------------------------------------------*

var $ = require('jquery');
var jQuery = $;

//*------------------------------------------------------------------*

var _contextRoot;

function buildHelpAnchor() {
    return $("<a/>", {
        'class': 'help-icon',
        'href': '#',
        'title': '',
        'text': '?'
    });
};

function getHelpFileName(pageName){
    return 'help-tooltips.' + pageName + '-page.html';
};

function getHelpLocation(pageName, inlineAnchor) {
    return _contextRoot + '/resources/html/' + getHelpFileName(pageName) + ' ' + inlineAnchor;
};

function initTooltips(pageName, parentElementId) {

    var anchor = buildHelpAnchor();

    var helpSelector = (typeof parentElementId === "object") ? parentElementId : (parentElementId == '') ? "[data-help-loc]" : "#" + parentElementId + " [data-help-loc]";

    $(helpSelector)
        .append(anchor)
        .click(function (e) {
            e.preventDefault();
        })
        .tooltip(
        {
            tooltipClass: "gxaHelpTooltip",
            content: function (callback) {
                var tooltipHelpHtmlId = $(this).parent().attr('data-help-loc');

                // load help text into #help-placeholder. NB: page must contain a div with id help-placeholder
                $("#help-placeholder").load(getHelpLocation(pageName, tooltipHelpHtmlId),
                    function (response, status, xhr) {
                        var tooltipContent;
                        if (status === "error") {
                            tooltipContent = "Sorry but there was an error: " + xhr.status + " " + xhr.statusText;
                            callback(tooltipContent);
                            return;
                        }
                        tooltipContent = $(this).text();
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

exports.init =
    function(pageName, contextRoot, parentElementId) {
        _contextRoot = contextRoot;
        initTooltips(pageName, parentElementId);
    };

exports.buildHelpAnchor = buildHelpAnchor;

exports.getHelpFileName =  getHelpFileName;