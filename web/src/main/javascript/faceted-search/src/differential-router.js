"use strict";

//*------------------------------------------------------------------*

var React = require('react');
var $ = require('jquery');
var jQuery = $;
require('jquery.browser');

var URI = require('urijs');

//*------------------------------------------------------------------*

var DifferentialFacetsTree = require('./differential-facets-tree.jsx');
var DifferentialResults = require('./differential-results.jsx');

//*------------------------------------------------------------------*

/**
 * @param {Object} options
 * @param {string} options.facetsContainer - id of the facets container, i.e. a <div> id
 * @param {string} options.resultsContainer - id of the results container, i.e. a <div> id
 * @param {string} options.identifier
 * @param {string} options.queryType - "gene", "geneSet"
 * @param {string} options.species
 */
module.exports = function (options) {

    var ie9 = $.browser.msie && $.browser.version < 10;
    !ie9 && window.addEventListener("popstate", backButtonListener, false);

    var facetsElement = document.getElementById(options.facetsContainer),
        resultsElement = document.getElementById(options.resultsContainer),
        host = window.location.host;

    var query = {
        geneQuery : options.identifier,
        queryType : options.queryType,
        select    : {}
    };

    var differentialFacetsPath = "";
    var differentialResultsPath = "";
    switch (query.queryType) {
        case "gene":
            differentialFacetsPath = "gxa/new/genes/" + query.geneQuery + "/differentialFacets.json";
            differentialResultsPath = "gxa/new/genes/" + query.geneQuery + "/differentialResults.json";
            break;
        case "geneSet":
            differentialFacetsPath = "gxa/new/genesets/" + query.geneQuery + "/differentialFacets.json";
            differentialResultsPath = "gxa/new/genesets/" + query.geneQuery + "/differentialResults.json";
            break;
        default:
            break;
    }

    var facetsTreeData,
        resultsData;
    $.ajaxSetup({ traditional:true });
    $.ajax({
        url: new URI({protocol: URI(window.location).protocol(), hostname: host, path: differentialFacetsPath}).normalize(),
        dataType: 'json',
        success: function(response) {
            facetsTreeData = response;
            if (options.species) {
                for (var i = 0 ; i < facetsTreeData.species.length ; i++) {
                    if (facetsTreeData.species[i].name === options.species) {
                        addSelection(query.select, "species", options.species);
                    }
                }
            }
            if (Object.keys(query.select).length === 0) {
                parseSelectedFacetsFromLocation();
            }
            pushQueryIntoBrowserHistory(true);

            $.ajax({
                url: new URI({protocol: URI(window.location).protocol(), hostname: host, path: differentialResultsPath}).normalize(),
                dataType: 'json',
                success: function(response) {
                    resultsData = response;

                    filterAndRenderResults();
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    console.log("ERROR");
                    console.log("Status: " + textStatus);
                    console.log("Error thrown: " + errorThrown);
                }
            });
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log("ERROR");
            console.log("Status: " + textStatus);
            console.log("Error thrown: " + errorThrown);
        }
    });


    function backButtonListener() {
        if (window.location.hash === "#differential") {
            parseSelectedFacetsFromLocation();
            filterAndRenderResults();
        }
    }

    /**
     * Parse the `ds` search field in the URL and assign it to the `query` object
     */
    function parseSelectedFacetsFromLocation() {
        var currentURL = new URI(window.location);
        var selectString = currentURL.search(true)["ds"];
        query.select = selectString ? JSON.parse(selectString) : {};
    }


    function setChecked(checked, facet, facetItem) {
        if (checked) {
            addSelection(query.select, facet, facetItem);
        } else {
            removeSelection(query.select, facet, facetItem);
        }
        pushQueryIntoBrowserHistory(false);
        filterAndRenderResults(checked, facet, facetItem);
    }

    function addSelection(select, facet, facetItem) {
        if (!select[facet]) {
            select[facet] = {};
        }
        select[facet][facetItem] = true;
    }

    function removeSelection(select, facet, facetItem) {
        select[facet][facetItem] = false;
    }

    /**
     * Stringify the `query` object, assign it to the `ds` search field in the URL and store it in the History
     * @param {boolean} replace - use `replaceState` instead of `pushState`
     */
    function pushQueryIntoBrowserHistory(replace) {
        var newURL = new URI(window.location).search(function(data) {
            data.ds = JSON.stringify(query.select);
        });

        if (!ie9) {
            if (replace) {
                history.replaceState(null, "", newURL);
            } else {
                history.pushState(null, "", newURL);
            }
        }
    }



    function filterAndRenderResults(checked, facetChecked, facetItemChecked) {
        var disabledFacets = resultsData["commonFacetItems"] ? resultsData["commonFacetItems"] : {};

        var filteredResults = resultsData.results.filter(function(result) {

            for (var facetName in query.select) {
                if (query.select.hasOwnProperty(facetName)) {

                    var hasCheckedItemsInThisFacet = false;
                    var facetMatch = false;

                    for (var facetItem in query.select[facetName]) {
                        if (query.select[facetName].hasOwnProperty(facetItem)) {

                            if (query.select[facetName][facetItem]) {
                                hasCheckedItemsInThisFacet = true;

                                if (result[facetName].constructor === Array) {
                                    facetMatch = facetMatch || result[facetName].indexOf(facetItem) != -1;
                                }
                                else {
                                    facetMatch = facetMatch || result[facetName] === facetItem;
                                }
                            }

                        }
                    }

                    if (hasCheckedItemsInThisFacet && !facetMatch) {
                        return false;
                    }

                }
            }

            return true;
        });

        var facetTypes = ["kingdom", "species", "experimentType", "factors", "numReplicates", "regulation"];
        var disabledCheckedFacets = {};
        for (var facetIndex in facetTypes) {
            var facetItem = facetTypes[facetIndex];
            var facetsInCommon = {};
            var facetValue = [];
            var sameValue = true;
            for (var index in filteredResults) {
                var filtered = filteredResults[index];
                var facet = filtered[facetItem];

                if(!facetsInCommon.hasOwnProperty(facetItem)) { //if is empty the first time
                    facetValue.push(facet);
                    facetsInCommon[facetItem] = facetValue;
                }
                if(facetsInCommon.hasOwnProperty(facetItem) && facetsInCommon[facetItem].toString() !== facet) {
                    sameValue = false;
                }

            }
            if(sameValue && facetItemChecked != facetValue.toString()) {
                disabledCheckedFacets[facetItem] = facetValue;
            }
        }

        React.render(
            React.createElement(
                DifferentialFacetsTree, {facets: facetsTreeData, checkedFacets: query.select, setChecked: setChecked, disabledFacets: disabledCheckedFacets}
            ),
            facetsElement
        );

        React.render(
            React.createElement(
                DifferentialResults,
                {results: filteredResults, maxDownLevel: resultsData.maxDownLevel, minDownLevel: resultsData.minDownLevel, minUpLevel: resultsData.minUpLevel, maxUpLevel: resultsData.maxUpLevel}
            ),
            resultsElement
        );
    }
};
