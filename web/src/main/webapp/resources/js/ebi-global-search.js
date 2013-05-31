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

function renderMenu(elem, items, baseURL, query, skipEmptyResults) {
    var ul = $("<ul>").attr("id", "global-search-results");
    $(elem).find("h3").after(ul);
    var subtitle;
    var totalCount = 0;
    $.each(items, function (index, item) {
        if (!(skipEmptyResults && item.numberOfResults <= 0)) {
            renderItem(ul, item, baseURL);
        }
        totalCount += item.numberOfResults;
    });
    if (totalCount <= 0 && skipEmptyResults) {
        subtitle = $("<p>").text("No results for \"" + query + "\"");
    } else {
        subtitle = $("<p>").text("Other results for \"" + query + "\"");
    }
    $(elem).find("h3").after(subtitle);
}
function renderItem(ul, item, baseURL) {
    $("<li>").append($("<a>").attr("href", baseURL + item.url).text(item.name + " (" + item.numberOfResults + ")")).appendTo(ul);
}
function updateSummary(options) {
    var opts = {};
    opts.searchboxId = options.searchboxId || "local-searchbox";
    opts.searchBaseURL = options.searchBaseURL || "/ebisearch/";
    opts.globalSearchBoxId = options.globalSearchBoxId || "ebi_search_results";
    opts.loadingLabel = options.loadingLabel || "Loading other results";
    opts.loadingLabelClass = options.loadingLabelClass || "loading";
    opts.titleLabel = options.titleLabel || "Show all data from EMBL-EBI";
    opts.skipEmptyResults = options.skipEmptyResults !== null ? options.skipEmptyResults : true;
    opts.noResults = options.noResults !== null ? options.noResults : false;
    var query = $("#" + opts.searchboxId).val();
    if (query) {
        var searchBaseURL = opts.searchBaseURL;
        var thisElem = $.find("#" + opts.globalSearchBoxId);
        $(thisElem).find("ul").remove();
        $(thisElem).find("p").remove();
        $(thisElem).find("h3").addClass(opts.loadingLabelClass);
        $.ajax({searchBaseURL: searchBaseURL, url: searchBaseURL + "globalsearchsummary.ebi?query=" + query + "&noResults=" + opts.noResults, context: thisElem, dataType: "json", crossdoamin: true, error: function (request, error) {
            alert("Error occurred: " + error);
        }, success: function (data, textStatus, jqHXR) {
        }}).done(function (response) {
                var obj = response;
                $(this).find("h3").removeClass(opts.loadingLabelClass);
                renderMenu(this, obj, searchBaseURL, query, opts.skipEmptyResults);
            });
    }
}
