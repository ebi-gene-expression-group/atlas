/*
 * Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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

"use strict";

var $ = require('jquery');
var jQuery = $;
require('jquery-highlight');

function splitIntoWords(geneQuery){
    var words = [];
    geneQuery.replace(/"([^"]*)"|(\S+)/g,
        function(m,g1,g2){
            if (g1 || g2){
                words.push(g1 || g2);
            }
        });
    return words;
}

function initTooltip(contextRoot, highlightedWords, element){

    $(element).tooltip({
        tooltipClass:"gxaGeneNameTooltip",
        position: { my: "left+120 top", at: "left top", collision: "flipfit" },
        hide:false,
        show:false,

        content:function (callback) {

            var identifier = $(this).attr("id"),
                geneName = $.trim($(this).text());

            if (identifier)  {

                $("#genenametooltip-content").load(

                    contextRoot + "/rest/genename-tooltip?geneName=" + geneName + "&identifier=" + identifier,

                    function (response, status, xhr) {
                        var tooltipContent;
                        if (status === "error") {
                            tooltipContent = "Sorry but there was an error: " + xhr.status + " " + xhr.statusText;
                            callback(tooltipContent);
                            return;
                        }

                        if(highlightedWords){
                            $(this).highlight(highlightedWords);
                        }

                        tooltipContent = $(this).html();
                        if (!tooltipContent) {
                            tooltipContent = "Missing properties for id = " + identifier + " in Solr.";
                        }
                        callback(tooltipContent);
                    }
                );

            }
        }
    });
}



exports.init =
    function(contextRoot, highlightedWords, element) {
            initTooltip(contextRoot, splitIntoWords(highlightedWords), element || ".genename");
    };

