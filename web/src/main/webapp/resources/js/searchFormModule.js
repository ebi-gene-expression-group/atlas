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

/*
 Search form object encapsulating form related jquery plugin initializations and binding.
 I am using the Module pattern, it gives high control on what is implementation (not to be exposed)
 [http://www.adequatelygood.com/2010/3/JavaScript-Module-Pattern-In-Depth]
 and what is public (what is exposed through the returned object literal)
 I am not using any prototypal construction pattern because we will never have multiple instances of the entire form on the same page.
 */
var searchFormModule = (function($) {
    "use strict";

    function removeHttpParameters (string) {
        return string.split("?")[0];
    }

    function disableCarriageReturn(selector) {
        $(selector).keypress(function(event) {
            if (event.keyCode === 13) {
                event.preventDefault();
                $("#submit-button").click();
            }
        });
    }

    function searchBoxEnterEventHandler(element) {
        $('#searchForm .tag-editor').on('submit', function (e) {
            $(element).click();
        });
    }

    function init () {
        // Initialize buttons and select box
        $("#submit-button").button();
        $("#reset-button").button();
        $("#queryFactorValues").chosen();
    }

    return {
        init: init,
        disableCarriageReturn: disableCarriageReturn,
        searchBoxEnterEventHandler: searchBoxEnterEventHandler,
        removeHttpParameters: removeHttpParameters
    };

}(jQuery));



