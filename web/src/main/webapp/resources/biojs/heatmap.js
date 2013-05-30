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

/**
 * This is the description of the ... component.
 *
 * @class Biojs.Heatmap
 * @extends Biojs
 *
 * @author <a href="mailto:johncar@gmail.com">QQQQQ QQQQ</a>
 * @version 1.0.0
 * @category 1
 *
 * @requires <a href='http://code.jquery.com/jquery-1.6.4.js'>jQuery Core 1.6.4</a>
 * @dependency <script language="JavaScript" type="text/javascript" src="../biojs/dependencies/jquery/jquery-1.6.4.min.js"></script>
 *
 * @param {Object} options An object with the options for HelloWorld component.
 *
 * @option {string} target
 *    Identifier of the DIV tag where the component should be displayed.
 *
 * @option {string} [fontFamily='"Andale mono", courier, monospace']
 *    Font list to be applied to the component content.
 *
 * @option {string} [fontColor="white"]
 *    HTML color code for the font.
 *
 * @option {string} [backgroundColor="#7BBFE9"]
 *      Background color for the entire div content.
 *
 * @option {Object} [selectionFontColor="white"]
 *      This color will be used to change the font color of selected text.
 *
 * @option {Object} [ selectionBackgroundColor="yellow"]
 *      This color will be used to change the background of selected text.
 *
 * @example
 * var instance = new Biojs.Heatmap({
 * target : "YourOwnDivId"
 * });
 *
 */
Biojs.Heatmap = Biojs.extend({

    constructor:function (options) {

        var self = this;

        var containerDiv = jQuery("#" + self.opt.target);

        var options = self.opt;
        var httpRequest = {
            url:options.featuresUrl,

            methid:"GET",
            success:function (htmlResponse) {
                Biojs.console.log("SUCCESS: data received");
                Biojs.console.log(htmlResponse);
                containerDiv.append(htmlResponse);
            },
            error:function (textStatus) {
                Biojs.console.log("ERROR: " + textStatus);
            }
        };


        jQuery.ajax(httpRequest);

    },

    opt:{

        featuresUrl:'/gxa/widgets/heatmap/protein?geneQuery=Q9GIL2',
        /* Target DIV
         This mandatory parameter is the identifier of the DIV tag where the
         component should be displayed. Use this value to draw your
         component into. */
        target:"YourOwnDivId"

    }
});
