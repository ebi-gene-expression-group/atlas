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

$(document).ready(function () {
    if ($("body").hasClass("noresults")) {
        try {
            updateSummary({noResults: true});
        }
        catch (except_1) {
        }
    } else {
        try {
            $('#search-extras .slideToggle[data-icon="u"]').next().hide();
            jQuery("#search-extras .slideToggle").click(function () {
                $(this).attr("data-icon", $(this).attr("data-icon") === 'u' ? "w" : "u");
                if ($(this).attr("data-icon") === 'w') {
                    updateSummary({
                        searchboxId: "searchterm",
                        searchBaseURL: "http://www.ebi.ac.uk/ebisearch/"
                    });
                }
                $(this).parent().find("p").slideToggle(300);
                $(this).parent().find("ul").slideToggle(300);
            });
        }
        catch (except_1) {
        }
    }
});