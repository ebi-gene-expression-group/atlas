"use strict";

//*------------------------------------------------------------------*

var React = require('react');
var ReactDOM = require('react-dom');
var EventEmitter = require('events');

var Anatomogram = require('anatomogram');

//*------------------------------------------------------------------*

function AnatomogramBuilder(domNode, anatomogramData, profileRows, expressedColour, hoverColour, eventEmitter, jQueryUITheme) {

    if (jQueryUITheme !== "") {

        var href = jQueryUITheme === "atlas" || typeof jQueryUITheme === "undefined" ?
            window.location.protocol + "//" + window.location.host + "/gxa/resources/css/jquery-ui-1.11.4.custom/css/jquery-ui-min.css" :
            "https://code.jquery.com/ui/1.11.1/themes/" + jQueryUITheme + "/jquery-ui.css";

        var cssDoc = document.createElement("link");
        cssDoc.setAttribute("rel", "stylesheet");
        cssDoc.setAttribute("type", "text/css");
        cssDoc.setAttribute("href", href);
        document.getElementsByTagName("head")[0].appendChild(cssDoc);

    }

    ReactDOM.render(
        React.createElement(
            Anatomogram,
            {
                anatomogramData: anatomogramData,
                expressedTissueColour: expressedColour,
                hoveredTissueColour: hoverColour,
                atlasBaseURL: "https://www.ebi.ac.uk/gxa",
                profileRows: profileRows,
                eventEmitter: eventEmitter
            }
        ),
        domNode
    );
}

//*------------------------------------------------------------------*

exports.AnatomogramBuilder = AnatomogramBuilder;
exports.EventEmitter = EventEmitter;
