/*
 * Copyright 2008-2012 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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

/*global $, svg:false */


function setHilighting(path, color, opacity) {
    path.style.fill = color;
    path.style.fillOpacity = opacity;
}

function togglePathColor(path, evtType) {

    "use strict";

    if (evtType === undefined) {
        setHilighting(path, "gray", 0.5);
    } else if (evtType === 'mouseenter' || evtType === 'mouseover') {
        setHilighting(path, "red", 0.7);
    } else {
        setHilighting(path, "gray", 0.5);
    }
}

function toggleOrganismPartColor(svg, factorValue, evt) {

    "use strict";

    var element = svg.getElementById(factorValue);
    var evtType = (typeof evt === 'undefined') ? evt : evt.type;

    if (element !== null) {
        if (element.nodeName === 'g') {
            $.each(element.getElementsByTagName('path'), function () {
                togglePathColor(this, evtType);
            });
        } else {
            togglePathColor(element, evtType);
        }

    }

}

function hoverOrganismPart(svg, organism_part) {

    var tableHeaderDivs = $('#heatmap-table th').find("div[data-organism-part='" + organism_part + "']");

    function mouseHover(domElem) {

        function toggleClass(elem, evtType) {
            var headerCell = elem.parent();
            //var colIndex = headerCell.parent("tr").children().index(headerCell) + 1;
            //var dataCells = $('#heatmap-table').find('tr>td:nth-child(' + colIndex + ')');
            if (evtType !== "mouseover") {
                headerCell.removeClass("headerHover");
            //    dataCells.removeClass("highlight");
            } else {
                headerCell.addClass("headerHover");
            //    dataCells.addClass("highlight");
            }
        }

        domElem.addEventListener("mouseover", function () {
            $.each(tableHeaderDivs, function () {
                toggleClass($(this), "mouseover");
            });
            togglePathColor(domElem, "mouseover");
        }, false);
        domElem.addEventListener("mouseout", function () {
            $.each(tableHeaderDivs, function () {
                toggleClass($(this), "mouseout");
            });
            togglePathColor(domElem, "mouseout");
        }, false);
    }

    var element = svg.getElementById(organism_part);

    if (element !== null) {
        if (element.nodeName === 'g') {
            $.each(element.getElementsByTagName('path'), function (index, domEle) {
                mouseHover(domEle);
            });
        } else {
            mouseHover(element);
        }
    }
}

function scaleAnatomogram(svg) {
    var elementById = svg.getElementById('group_all');
    // this is in case anatomogram is hidden
    if (typeof elementById !== 'undefined') {
        elementById.setAttribute('transform', 'scale(1.6)');
    }
}


//load anatomogram from given location and display given organism parts
function displayFactorValues(svg, factorValues) {
    $.each(factorValues, function () {
        toggleOrganismPartColor(svg, this);
        hoverOrganismPart(svg, this);
    });
}

function initAnatomogramBindings(svg, factorValues) {
    displayFactorValues(svg, factorValues);
}

//load anatomogram from given location and display given organism parts
function loadAnatomogram(location, factorValues) {
    var svg = $('#anatomogramBody').svg('get');

    svg.load(location, {
        onLoad:function(){
            scaleAnatomogram(svg);
            initAnatomogramBindings(svg, factorValues);

        }
    });
    return svg;
}

function initAnatomogram(factorValues, fileNameMale, fileNameFemale) {

    if ($('#anatomogramBody').length === 0) {
        return;
    }

    //init svg
    $('#anatomogramBody').svg();


    var svg = loadAnatomogram("resources/svg/" + fileNameMale, factorValues);

    //hover on gene name, to highlight all organism parts involved on a single gene profile
    $("#heatmap-table td:first-child").on("hover", function (evt) { //hover on cells of the first table column
        var geneExpressions = $(this).parents("tr .even,.odd").find("div[data-organism-part!='']");

        var factorValues = geneExpressions.map(function () {
            return $(this).attr('data-organism-part');
        }).get();

        $.each(factorValues, function () {
            toggleOrganismPartColor(svg, this, evt);
        });

    });

    //hover on a cell to highlight related organism part
    $("#heatmap-table td,th").on("hover", function (evt) {
        var organismPart = $(this).find('div').attr("data-organism-part");
        if (organismPart !== undefined) {
            toggleOrganismPartColor(svg, organismPart, evt);
        }
    });

    if (fileNameMale !== fileNameFemale) {
        //switch sex toggle button
        $("#sex-toggle-image").button().toggle(
            function () {
                $(this).attr("src", "resources/images/female_selected.png");
                loadAnatomogram("resources/svg/" + fileNameFemale, factorValues);
            },
            function () {
                $(this).attr("src", "resources/images/male_selected.png");
                loadAnatomogram("resources/svg/" + fileNameMale, factorValues);
            }
        ).tooltip();
    } else {
        $("#sex-toggle").hide();
    }
}

