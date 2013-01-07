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

/*global $:false */


function initHelpTooltip() {

    "use strict";

    function enableTooltip() {
        $("[data-help-loc]")
            .tooltip("option", "disabled", false)
            .on('mouseover.loadContent', function () {
                var selectedElement = this;
                $('#helpContentTooltip').load('resources/html/atlas-help.html ' + $(selectedElement).attr('data-help-loc'), function () {
                    $(selectedElement).tooltip("option", "content", $('#helpContentTooltip').text());
                    $(selectedElement).tooltip("option", "position", { my: "bottom",
                                    at: "top-5", collision: "flipfit" });
                    $(selectedElement).tooltip("option", "tooltipClass", "help-tooltip");
                });
            });
    }

    function disableTooltip() {
        $("[data-help-loc]")
            .tooltip("option", "disabled", true)
            .off('mouseover.loadContent');
    }

    $("[data-help-loc]")
        .attr("title", "")
        .tooltip({disabled: true/*, hide: false, show: false*/}); //the commented parameters remove the in/out fade delay


    $("#display-help").button().
        toggle(function () {
            $("#display-help-image").attr("src", "resources/images/helpPressed.png");
            $(this).tooltip("option", "content", "Hide additional help");
            enableTooltip();
        }, function () {
            $("#display-help-image").attr("src", "resources/images/help.png");
            $(this).tooltip("option", "content", "Show additional help");
            disableTooltip();
        }).tooltip();

}