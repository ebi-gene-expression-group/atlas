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

function initHelpTooltip() {

    $("div[data-help-loc]")
        .attr("title", "")
        .tooltip({disabled: true});


    $("#display-help-image").button().
        toggle(function () {
            $(this).attr("src", "resources/images/helpPressed.png");
            $(this).tooltip("option", "content", "Hide help");
            enableTooltip();
        },function () {
            $(this).attr("src", "resources/images/help.png");
            $(this).tooltip("option", "content", "Show help");
            disableTooltip();
        }).tooltip();

    function enableTooltip() {
        $("div[data-help-loc]")
            .tooltip("option", "disabled", false)
            .on('mouseover.loadContent', function () {
                var selectedElement = this;
                $('#tooltip').load('resources/html/atlas-help.html ' + $(selectedElement).attr('data-help-loc'), function () {
                    $(selectedElement).tooltip("option", "content", $('#tooltip').text());
                });
            })
    }

    function disableTooltip() {
        $("div[data-help-loc]")
            .tooltip("option", "disabled", true)
            .off('mouseover.loadContent');
    }
}