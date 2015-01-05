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

var anatomogramModule = (function ($) {

    "use strict";

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

    function toggleOrganismPartColor(svg, svgPathId, evt) {

        "use strict";

        var element = svg.getElementById(svgPathId);
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


    function initMouseOverBindingForSvgPath(svgPath, svgPathId) {

        var headerDiv = $('#heatmap-table th').has("div[data-svg-path-id='" + svgPathId + "']");

        svgPath.addEventListener("mouseover", function () {
            headerDiv.addClass("headerHover");
            togglePathColor(svgPath, "mouseover");
        }, false);

        svgPath.addEventListener("mouseout", function () {
            headerDiv.removeClass("headerHover");
            togglePathColor(svgPath, "mouseout");
        }, false);
    }


    function initBindingsForAnatomogramPaths(svg, svgPathId) {

        var svgElement = svg.getElementById(svgPathId);

        if (svgElement !== null) {
            if (svgElement.nodeName === 'g') {
                $.each(svgElement.getElementsByTagName('path'), function () {
                    initMouseOverBindingForSvgPath(this, svgPathId);
                });
            } else {
                initMouseOverBindingForSvgPath(svgElement, svgPathId);
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


    function initAnatomogramBindings(svg, allSvgPathIds) {
        $.each(allSvgPathIds, function () {
            initBindingsForAnatomogramPaths(svg, this);
        });
    }

    function highlightAllOrganismParts(svg, allSvgPathIds) {
        $.each(allSvgPathIds, function () {
            toggleOrganismPartColor(svg, this);
        });
    }

    //load anatomogram from given location and display given organism parts
    function loadAnatomogram(location, allSvgPathIds) {
        var svg = $('#anatomogramBody').svg('get');

        svg.load(location, {
            onLoad:function(){
                scaleAnatomogram(svg);
                highlightAllOrganismParts(svg, allSvgPathIds);
                initAnatomogramBindings(svg, allSvgPathIds);

            }
        });
        return svg;
    }

    function init(allSvgPathIds, fileNameMale, fileNameFemale, contextRoot, species) {
        if ($('#anatomogramBody').length === 0) {
            return;
        }

        //init svg
        $('#anatomogramBody').svg();

        var svg = loadAnatomogram(contextRoot + "/resources/svg/" + fileNameMale, allSvgPathIds);

        //hover on gene name, to highlight all organism parts involved on a single gene profile
        $("#heatmap-table td:first-child").on("hover", function (evt) { //hover on cells of the first table column
            var geneExpressions = $(this).parent("tr").find("div[data-svg-path-id!='']");

            var factorValues = geneExpressions.map(function () {
                return $(this).attr('data-svg-path-id');
            }).get();

            $.each(factorValues, function () {
                toggleOrganismPartColor(svg, this, evt);
            });

        });

        //hover on a header or expression level cell to highlight related SVG organism part
        $("#heatmap-table td,th").on("hover", function (evt) {
            var organismPart = $(this).find('div').attr("data-svg-path-id");
            if (organismPart !== undefined) {
                toggleOrganismPartColor(svg, organismPart, evt);
            }
        });

        if (fileNameMale !== fileNameFemale) {
            //switch sex toggle button
            $("#sex-toggle-image").button().toggle(
                function () {

                    $(this).attr("src", contextRoot + selectFemaleImageToggle(species));
                    loadAnatomogram(contextRoot + "/resources/svg/" + fileNameFemale, allSvgPathIds);
                },
                function () {
                    $(this).attr("src", contextRoot + selectMaleImageToggle(species));
                    loadAnatomogram(contextRoot + "/resources/svg/" + fileNameMale, allSvgPathIds);
                }
            ).tooltip();
        } else {
            $("#sex-toggle").hide();
        }
    }

    function selectFemaleImageToggle (species) {
        return (species === "oryza sativa japonica group" || species === "oryza sativa" ? "/resources/images/plant_switch_buttons_2.png" : "/resources/images/female_selected.png");
    }

    function selectMaleImageToggle (species) {
        return (species === "oryza sativa japonica group" || species === "oryza sativa" ? "/resources/images/plant_switch_buttons_1.png" : "/resources/images/male_selected.png");
    }

    return {

        init: init

    };

}(jQuery));