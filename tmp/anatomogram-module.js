"use strict";

//*------------------------------------------------------------------*

var $ = require('jquery');
var jQuery = $;

require('jquery-ui');
require('../css/jquery-ui.min.css');

require('../lib/jquery.svg.js');

//*------------------------------------------------------------------*

require('../css/table-grid.css');

//*------------------------------------------------------------------*

function setHighlighting(path, color, opacity) {
    path.style.fill = color;
    path.style.fillOpacity = opacity;
}


function isFactorExpressed(factor){
    return ($.inArray(factor, factorsExpressed) > -1);
}


function togglePathColor(path, evtType, isSingleGene, svgPathId, color) {
    if(isSingleGene && evtType === undefined && color === "red") { //We highlight the whole anatomogram with paths expressed in the gene
        setHighlighting(path, "red", 0.7);
    } else if(isSingleGene && (evtType === 'mouseenter' || evtType === 'mouseover')) { //highlight in different colors when onmouseover
        setHighlighting(path, "#421C52", 0.8);
    } else {
        if (evtType === undefined) {
            setHighlighting(path, "gray", 0.5);
        } else if (!isSingleGene && (evtType === 'mouseenter' || evtType === 'mouseover')) {
            setHighlighting(path, "red", 0.7);
        } else if (isSingleGene && (isFactorExpressed(svgPathId) || isFactorExpressed(path.id) || isFactorExpressed(path.parentElement.id))) {
            setHighlighting(path, "red", 0.7);
        } else {
            setHighlighting(path, "gray", 0.5);
        }
    }
}


function toggleOrganismPartColor(svg, isSingleGene, svgPathId, evt, color) {
    var element = svg.getElementById(svgPathId);
    var evtType = (typeof evt === 'undefined') ? evt : evt.type;

    if (element !== null) {
        if (element.nodeName === 'g') {
            $.each(element.getElementsByTagName('path'), function () {
                togglePathColor(this, evtType, isSingleGene, svgPathId, color);
            });
        } else {
            togglePathColor(element, evtType, isSingleGene, svgPathId, color);
        }
    }
}


function initMouseOverBindingForSvgPath(svgPath, svgPathId, isSingleGene) {

    var $headerDiv = $(".gxaStickyTableWrap").find("table th").has("div[data-svg-path-id='" + svgPathId + "']");

    svgPath.addEventListener("mouseover", function () {
        $headerDiv.addClass("gxaHeaderHover");
        togglePathColor(svgPath, "mouseover", isSingleGene);
    }, false);

    svgPath.addEventListener("mouseout", function () {
        $headerDiv.removeClass("gxaHeaderHover");
        togglePathColor(svgPath, "mouseout", isSingleGene);
    }, false);
}


function initBindingsForAnatomogramPaths(svg, isSingleGene, svgPathId) {

    var svgElement = svg.getElementById(svgPathId);

    if (svgElement !== null) {
        if (svgElement.nodeName === 'g') {
            $.each(svgElement.getElementsByTagName('path'), function () {
                initMouseOverBindingForSvgPath(this, svgPathId, isSingleGene);
            });
        } else {
            initMouseOverBindingForSvgPath(svgElement, svgPathId, isSingleGene);
        }
    }
}


//TODO: When widget is used by faceting we need to create the ids dynamically for
//TODO: the widget to be load with a proper scale
function scaleAnatomogram(svg, anatomogramBodyId) {
    var anatomogramBody = anatomogramBodyId.replace("#","");
    var path = svg.getElementById(anatomogramBody);
    var ps = path.getElementsByTagName('g');
    var elementById = ps['group_all'];

    // this is in case anatomogram is hidden
    if (typeof elementById !== 'undefined') {
        elementById.setAttribute('transform', 'scale(1.6)');
    }
}


function initAnatomogramBindings(svg, isSingleGene, allSvgPathIds) {
    $.each(allSvgPathIds, function () {
        initBindingsForAnatomogramPaths(svg, isSingleGene, this);
    });
}


function highlightAllOrganismParts(svg, isSingleGene, allSvgPathIds) {
    $.each(allSvgPathIds, function () {
        toggleOrganismPartColor(svg, isSingleGene, this);
    });
}


//load anatomogram from given location and display given organism parts
function loadAnatomogram(anatomogramBodyId, location, allSvgPathIds, isSingleGene) {
    var svg = $(anatomogramBodyId).svg('get');

    svg.load(location, {
        onLoad:function(){
            //scaleAnatomogram(svg, anatomogramBodyId);
            if(isSingleGene) {
                highlightAllOrganismParts(svg, isSingleGene, allSvgPathIds);
                highlightExpressedOrganismsPartsOnly(svg, isSingleGene);
                initAnatomogramBindings(svg, isSingleGene, allSvgPathIds);
            }
            else {
                highlightAllOrganismParts(svg, isSingleGene, allSvgPathIds);
                initAnatomogramBindings(svg, isSingleGene, allSvgPathIds);
            }
        }
    });
    return svg;
}


var factorsExpressed = [];


function highlightExpressedOrganismsPartsOnly(svg, isSingleGene) {

    var geneExpressions = $(".gxaStickyTableWrap").find("tbody tr td").parent("tr").find('div[data-svg-path-id!=‘’]');

    var factorValues = geneExpressions.map(function () {
        if( $(this).find("span").text() != "NA" ){
            if($(this).attr('data-svg-path-id')!=undefined)
                factorsExpressed.push($(this).attr('data-svg-path-id'));
            return $(this).attr('data-svg-path-id');
        }
    }).get();

    $.each(factorValues, function () {
        toggleOrganismPartColor(svg, isSingleGene, this, undefined, "red");    //isExpressed-> true then highlight in red
    });

}


function replaceSpaces (value) {
    var result = value.replace(" ","_");
    return result.trim();
}


function init(allSvgPathIds, fileNameMale, fileNameFemale, fileNameBrain, contextRoot, species, isSingleGene, heatmapkey) {


    var svg = loadAnatomogram(anatomogramBody, contextRoot + "/resources/svg/" + fileNameMale, allSvgPathIds, isSingleGene);

    //hover on gene/experiment name to highlight all organism parts involved on a single gene profile
    var $stickyColumnHeaders = $(".gxaStickyTableColumn").find("tbody > tr > th:first-child");
    $("#heatmap-table").find("tbody > tr > th:first-child").each(function(i) {
        var geneExpressions = $(this).parent("tr").find("div[data-svg-path-id!='']");

        var factorValues = geneExpressions.map(function () {
            if ($(this).find("span").text() != "NA") {
                return $(this).attr('data-svg-path-id');
            }
        }).get();

        $(this).add($stickyColumnHeaders.get(i)).hover(function (event) {
            $.each(factorValues, function () {
                toggleOrganismPartColor(svg, isSingleGene, this, event);
            });
        });
    });

    //hover on a header or expression level cell to highlight related SVG organism part
    $(".gxaStickyTableWrap td,th").hover(function (evt) {
        var organismPart = $(this).find('div').attr("data-svg-path-id");
        if (organismPart !== undefined){ // && !isSingleGene) {  //if is not gene page then highlight
            toggleOrganismPartColor(svg, isSingleGene, organismPart, evt);
        }
    });


}

//*------------------------------------------------------------------*

module.exports = init;
