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

function toggleOrganismPartColor(svg, organism_part, evt) {

    function togglePathColor(path) {

        function setHilighting(color, opacity) {
            path.style.fill = color;
            path.style.fillOpacity = opacity;
        }

        if (evt == undefined || evt.type != 'mouseenter') {
            setHilighting("gray", 0.5);
        } else {
            setHilighting("red", 0.7);
        }
    }

    var element = svg.getElementById(organism_part);

    if (element != null) {
        if (element.nodeName === 'g') {
            $.each(element.getElementsByTagName('path'), function () {
                togglePathColor(this);
            });
        } else {
            togglePathColor(element);
        }

    }

}

function hoverOrganismPart(svg, organism_part) {

    var element = svg.getElementById(organism_part);
    var tableCells = $('#heatmap-table th').find("div[data-organism-part='" + organism_part + "']");

    if (element != null) {
        if (element.nodeName === 'g') {
            $.each(element.getElementsByTagName('path'), function (index, domEle) {
                domEle.addEventListener("mouseover", function () {
                    $.each(tableCells, function (i, d) {
                        var cell = $(this).parent();
                        cell.addClass("highlight");
                        var colIndex = cell.parent("tr").children().index(cell) + 1;
                        $('#heatmap-table tr>td:nth-child(' + colIndex + ')').addClass("highlight");
                    });
                    domEle.style.fill = "red";
                    domEle.style.fillOpacity = 0.7;
                }, false);
                domEle.addEventListener("mouseout", function () {
                    $.each(tableCells, function (i, d) {
                        var cell = $(this).parent();
                        cell.removeClass("highlight");
                        var colIndex = cell.parent("tr").children().index(cell) + 1;
                        $('#heatmap-table tr>td:nth-child(' + colIndex + ')').removeClass("highlight");
                    });
                    domEle.style.fill = "grey";
                    domEle.style.fillOpacity = 0.5;
                }, false);
            });
        } else {
            element.addEventListener("mouseover", function () {
                $.each(tableCells, function (index, domEle) {
                    var cell = $(this).parent();
                    cell.addClass("highlight");
                    var colIndex = cell.parent("tr").children().index(cell) + 1;
                    $('#heatmap-table tr>td:nth-child(' + colIndex + ')').addClass("highlight");
                });
                element.style.fill = "red";
                element.style.fillOpacity = 0.7;
            }, false);
            element.addEventListener("mouseout", function () {
                $.each(tableCells, function (index, domEle) {
                    var cell = $(this).parent();
                    cell.removeClass("highlight");
                    var colIndex = cell.parent("tr").children().index(cell) + 1;
                    $('#heatmap-table tr>td:nth-child(' + colIndex + ')').removeClass("highlight");
                });
                element.style.fill = "grey";
                element.style.fillOpacity = 0.5;
            }, false);

        }
    }
}

function scaleAnatomogram(svg) {
    var elementById = svg.getElementById('group_all');
    elementById.setAttribute('transform', 'scale(1.6)');
}

function initAnatomogram(organismParts, fileNameMale, fileNameFemale) {

    if ($('#anatomogramBody').length == 0) {
        return;
    }

    var svg = $('#anatomogramBody').svg().svg('get');

    loadAnatomogram("resources/svg/" + fileNameMale);

    //hover on first column, to show all organism parts involved on a single gene profile
    $("#heatmap-table").delegate("td:first-child", "hover", function (evt) { //hover on cells of the first table column
        var geneExpressions = $(this).parents("tr .even,.odd").find("div[data-organism-part!='']");

        var organismParts = geneExpressions.map(function () {
            return $(this).attr('data-organism-part')
        }).get();

        organismParts.forEach(function (entry) {
            toggleOrganismPartColor(svg, entry, evt);
        });

    });


    $('#heatmap-table').delegate("td,th", "hover", function (evt) {
        var organismPart = $(this).find('div').attr("data-organism-part");
        if (organismPart != undefined) {
            toggleOrganismPartColor(svg, organismPart, evt);
        }
    });

    //load anatomogram from given location and display given organism parts
    function displayOrganismParts() {
        $.each(organismParts, function () {
            toggleOrganismPartColor(svg, this);
            hoverOrganismPart(svg, this);
        });
    }

    function prepareAnatomogram() {
        displayOrganismParts();
        scaleAnatomogram(svg);
    }

    //load anatomogram from given location and display given organism parts
    function loadAnatomogram(location) {
        svg.load(location, {onLoad:prepareAnatomogram});
    }

    if (fileNameMale != fileNameFemale) {
        //switch sex toggle button
        $("#sex-toggle-image").button().toggle(function () {
            $(this).attr("src", "resources/images/female_selected.png")
            loadAnatomogram("resources/svg/" + fileNameFemale);
        },function () {
            $(this).attr("src", "resources/images/male_selected.png")
            loadAnatomogram("resources/svg/" + fileNameMale);
        }).tooltip();
    } else {
        $("#sex-toggle").hide();
    }
}

