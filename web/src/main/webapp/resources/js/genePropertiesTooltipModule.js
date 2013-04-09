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

/*global $,jQuery,console,loadSliderAndPlot: false */
/*jslint browser:true */
/*jslint nomen: true*/
var genePropertiesTooltipModule = (function($) {
    "use strict";

    function initHacksThatMinimizeJQueryTooltipBug(){
        //bug: http://bugs.jqueryui.com/ticket/8740
        //these events handling seem to only minimize the bug occurrency, they don't solve it completely
        $(".genename").mouseout(
            function(){
                $(this).tooltip("close");
            }
        );

        $(".genename").mouseleave(
            function(){
                $(this).tooltip("close");
            }
        );
    }

    function getWords(geneQuery){
        var words = [];
        geneQuery.replace(/"([^"]*)"|(\S+)/g,
            function(m,g1,g2){
                if (g1 || g2){
                    words.push(g1 || g2);
                }
            });
        return words;
    }

    function initTooltip(queryString){

        $(".genename").tooltip({
            tooltipClass:"genename-tooltip",
            position: { my: "left+120 top", at: "left top", collision: "flipfit" },
            hide:false,
            show:false,

            content:function (callback) {

                var identifier = $(this).attr("id"),
                    geneName = $.trim($(this).text());

                $("#genenametooltip-content").load(

                    "rest/genename-tooltip?geneName=" + geneName + "&identifier=" + identifier,

                    function (response, status, xhr) {
                        var tooltipContent;
                        if (status === "error") {
                            tooltipContent = "Sorry but there was an error: " + xhr.status + " " + xhr.statusText;
                            callback(tooltipContent);
                            return;
                        }

                        if(queryString){

                            $(this).highlight(getWords(queryString));

                        }

                        tooltipContent = $(this).html();
                        if (!tooltipContent) {
                            tooltipContent = "Missing properties for id = " + identifier + " in Solr.";
                        }
                        callback(tooltipContent);
                    }
                );
            }
        });
    }
    return {
        init:  function(queryString) {

            initHacksThatMinimizeJQueryTooltipBug();
            initTooltip(queryString);

        }

    };
}(jQuery));
