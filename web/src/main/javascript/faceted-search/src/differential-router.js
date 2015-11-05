"use strict";

//*------------------------------------------------------------------*

var React = require('react');
var $ = require('jquery');
var jQuery = $;
require('jquery.browser');

var URI = require('urijs');

//*------------------------------------------------------------------*

var FacetsTree = require('./differential-facets-tree.jsx');
var DifferentialResults = require('./differential-results.jsx');

//*------------------------------------------------------------------*

module.exports = function (facetsContainerId, resultsContainerId, selectedSpecies, facetsTreeData, resultsData) {

    var ie9 = $.browser.msie && $.browser.version < 10;
    !ie9 && window.addEventListener('popstate', backButtonListener, false);

    var facetsElement = document.getElementById(facetsContainerId),
        resultsElement = document.getElementById(resultsContainerId),
        host = window.location.host;

    var query = {
        geneQuery : "",
        select    : {}
    };

    parseGeneQueryFromLocation();

    if (selectedSpecies) {
        for (var i = 0 ; i < facetsTreeData["species"].length ; i++) {
            if (facetsTreeData["species"][i]["name"] === selectedSpecies) {
                addSelection(query.select, "species", selectedSpecies);
            }
        }
    }
    if (Object.keys(query.select).length === 0) {
        parseSelectedFacetsFromLocation();
    }
    pushQueryIntoBrowserHistory(true);
    renderQueryPage();

    function backButtonListener() {
        parseGeneQueryFromLocation();
        parseSelectedFacetsFromLocation();
        renderQueryPage();
    }

    function parseGeneQueryFromLocation() {
        var currentURL = new URI(window.location);

        // TODO Change to segment(1) when /new/ is removed
        if (currentURL.segment(2) === "genes" || currentURL.segment(2) === "genesets") {
            query.geneQuery = decodeURIComponent(currentURL.segment(3));
        } else {  // if (currentURL.segment(1) === "search") {
            query.geneQuery = currentURL.search(true)["geneQuery"];
        }
    }

    function parseSelectedFacetsFromLocation() {
        var currentURL = new URI(window.location);
        var selectString = currentURL.search(true)["ds"];
        query.select = selectString ? JSON.parse(selectString) : {};
    }

    function renderQueryPage() {
        React.render(
            React.createElement(
                FacetsTree, {facets: facetsTreeData, checkedFacets: query.select, setChecked: setChecked, disabledFacets: resultsData["commonFacetItems"]}
            ),
            facetsElement
        );

        renderQueryToResults();
    }

    function setChecked(checked, facet, facetItem) {
        if (checked) {
            addSelection(query.select, facet, facetItem);
        } else {
            removeSelection(query.select, facet, facetItem);
        }
        pushQueryIntoBrowserHistory(false);
        renderQueryPage();
    }

    function pushQueryIntoBrowserHistory(replace) {
        var newURL = new URI(window.location).search(function(data) {
            data.ds = JSON.stringify(query.select);
        });

        if (!ie9) {
            if (replace) {
                history.replaceState(null, null, newURL);
            } else {
                history.pushState(null, null, newURL);
            }
        }
    }

    function addSelection(select, facet, facetItem) {
        if (!select[facet]) {
            select[facet] = {};
        }
        select[facet][facetItem] = true;
        return select;
    }

    function removeSelection(select, facet, facetItem) {
        select[facet][facetItem] = false;
        return select;
    }

    function renderQueryToResults() {
        /*
         query.geneQuery=zinc finger
         query.select= {
         "species": {"homo sapiens":true,"mus musculus":true,"arabidopsis thaliana":true},
         "factors":{"infect":true},
         "numReplicates":{"3":true},
         "experimentType":{"rnaseq_mrna_differential":true},
         "kingdom":{"ensembl":true},
         "regulation":{"UP":true}
         }
         */
        var geneQuery = query.geneQuery,
            select = query.select;

        var species = [];
        var experimentType = [];
        var kingdom = [];
        var factors = [];
        var numReplicates = [];
        var regulation;

        for (var facets in select) {
            if (select.hasOwnProperty(facets)) {
                var items = select[facets];

                for (var key in items) {
                    if (items.hasOwnProperty(key)) {
                        var value = items[key];
                        if (facets === "species" && value === true) {
                            species.push(key);
                        } else if (facets === "experimental variables" && value === true) {
                            factors.push(key);
                        } else if (facets === "experiment type" && value === true) {
                            experimentType.push(key);
                        } else if (facets === "kingdom" && value === true) {
                            kingdom.push(key);
                        } else if (facets === "number of replicates" && value === true) {
                            numReplicates.push(key);
                        } else if (facets === "regulation" && value === true) {
                            regulation = key;
                        }
                    }
                }
            }
        }

        var differentialResultsPath = "gxa/search/differential/json";

        $.ajaxSetup({ traditional:true });
        $.ajax({
            url: new URI({protocol: "http", hostname: host, path: differentialResultsPath}).normalize(),
            data: {
                'geneQuery': geneQuery,
                'species': species.toString(),
                'experimentType': experimentType.toString(),
                'kingdom': kingdom.toString(),
                'factors': factors.toString(),
                'numReplicates': numReplicates.toString(),
                'regulation': regulation
            },
            dataType: 'json',
            success: function(response) {
                var diffResultsData = $.parseJSON(response["results"]);
                React.render(
                    React.createElement(
                        DifferentialResults,
                        {results: diffResultsData.results, maxDownLevel: diffResultsData.maxDownLevel, minDownLevel: diffResultsData.minDownLevel, minUpLevel: diffResultsData.minUpLevel, maxUpLevel: diffResultsData.maxUpLevel}
                    ),
                    resultsElement
                );
            },
            error: function(jqXHR, textStatus, errorThrown) {
                console.log("ERROR");
                console.log("Status: " + textStatus);
                console.log("Error thrown: " + errorThrown);
            }
        });
    }

};
