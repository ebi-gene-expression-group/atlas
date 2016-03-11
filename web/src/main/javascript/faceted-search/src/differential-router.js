"use strict";

//*------------------------------------------------------------------*

var React = require('react');
var ReactDOM = require('react-dom');
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
            differentialFacetsPath = "gxa/genes/" + query.geneQuery + "/differentialFacets.json";
            differentialResultsPath = "gxa/genes/" + query.geneQuery + "/differentialResults.json";
            break;
        case "geneSet":
            differentialFacetsPath = "gxa/genesets/" + query.geneQuery + "/differentialFacets.json";
            differentialResultsPath = "gxa/genesets/" + query.geneQuery + "/differentialResults.json";
            break;
        default:
            break;
    }

    var facetsTreeData,
        resultsData;
    $.ajaxSetup({ traditional:true });
    $.ajax({
        url: new URI({protocol: URI(window.location).protocol(), hostname: host, path: differentialFacetsPath}).normalize(),
        dataType: "json",
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
                dataType: "json",
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
        filterAndRenderResults();
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

    function filterResults(query) {
        return resultsData.results.filter(function(result) {
            for (var facetName in query) {
                if (query.hasOwnProperty(facetName)) {

                    var hasCheckedItemsInThisFacet = false;
                    var facetMatch = false;

                    for (var facetItem in query[facetName]) {
                        if (query[facetName].hasOwnProperty(facetItem)) {

                            if (query[facetName][facetItem]) {
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

    }

    function filterAndRenderResults() {

        var filteredResults = filterResults (query.select);

        var disabledCheckedFacets = {};
        var disabledUncheckedFacets = {};

        var selectedFacets = [];
        for (var facet in query.select) {
            var itemFacet = query.select[facet];
            for (var item in itemFacet) {
                if (itemFacet[item]) {
                    selectedFacets.push(item);
                }
            }
        }

        var notSelectedFacets = {};
        for (var facet in facetsTreeData) {
            var facetItems = facetsTreeData[facet];
            var unSelectedFacets = [];
            for (var item in facetItems) {
                var facetItem = facetItems[item];
                if (selectedFacets.indexOf(facetItem.name) === -1) {
                    unSelectedFacets.push(facetItem);
                }
            }
            notSelectedFacets[facet] = unSelectedFacets;
        }

        for (var facet in notSelectedFacets) {
            var facetNotExistsValue = [];
            var _query = query.select;

            for (var item in notSelectedFacets[facet]) {
                var facetItem = notSelectedFacets[facet][item];

                if (_query.hasOwnProperty(facet)) {
                    _query[facet][facetItem.name] = true;
                } else {
                    var _item = {};
                    _item[facetItem.name] = true;
                    _query[facet] = _item;
                }

                var queryResults = filterResults(_query);
                if (queryResults.length === 0 || queryResults.length === filteredResults.length) {
                    facetNotExistsValue.push(facetItem.name);
                }
                _query[facet][facetItem.name] = false; // once calculated, leave it as it was
            }

            if (facetNotExistsValue.length > 0) {
                disabledUncheckedFacets[facet] = facetNotExistsValue;
            }
        }

        for (var facet in disabledUncheckedFacets) {
            var facetValue = [];

            for (var item in disabledUncheckedFacets[facet]) {
                var existsFacet = true;
                var existMultiValueFacet = false;

                for (var index in filteredResults) {
                    var filtered = filteredResults[index];
                    var facetResults = filtered[facet];

                    if (facet === "factors" && facetResults.length > 1 ) {
                        if (facetResults.indexOf(disabledUncheckedFacets[facet][item].toString()) === -1) {
                            existsFacet = false;
                        } else {
                            existMultiValueFacet = true;
                        }

                    } else if (disabledUncheckedFacets[facet][item].toString() !== facetResults.toString()) {
                        existsFacet = false;
                    }
                }

                //All the results contain the same item
                if (existsFacet) {
                    facetValue.push(disabledUncheckedFacets[facet][item]);
                }

                //If all the results don't contain the same facet but the factor multiValue exists in more than one result
                if (!existsFacet && existMultiValueFacet) {
                    disabledUncheckedFacets[facet].splice(item, 1);
                }
            }

            //FacetValue contains all the items that are present in the results for a facet
            if (facetValue.length > 0) {
                disabledCheckedFacets[facet] = facetValue;
            }
        }

        ReactDOM.render(
            React.createElement(
                DifferentialFacetsTree, {facets: facetsTreeData, checkedFacets: query.select, setChecked: setChecked,
                    disabledCheckedFacets: disabledCheckedFacets, disabledUncheckedFacets: disabledUncheckedFacets}
            ),
            facetsElement
        );

        ReactDOM.render(
            React.createElement(
                DifferentialResults,
                {results: filteredResults.slice(0, 1000), maxDownLevel: resultsData.maxDownLevel,
                    minDownLevel: resultsData.minDownLevel, minUpLevel: resultsData.minUpLevel,
                    maxUpLevel: resultsData.maxUpLevel, host: host}
            ),
            resultsElement
        );
    }
};
