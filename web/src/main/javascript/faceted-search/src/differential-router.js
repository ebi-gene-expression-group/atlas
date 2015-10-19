"use strict";

//*------------------------------------------------------------------*

var React = require('react');
var $ = require('jquery');
var jQuery = $;
require('jquery.browser');

var URI = require('urijs');

//*------------------------------------------------------------------*

var FacetsTree = require('./facets-tree.jsx');
var DifferentialResults = require('./differential-results.jsx');

//*------------------------------------------------------------------*

module.exports = function (facetsContainerId, resultsContainerId, facetsTreeData, atlasHost) {

    var ie9 = $.browser.msie && $.browser.version < 10;
    if (!ie9) {
        window.addEventListener('popstate', renderPage, false);
    }

    var facetsElement = document.getElementById(facetsContainerId),
        resultsElement = document.getElementById(resultsContainerId),
        host = atlasHost ? atlasHost : window.location.host;

    var query = new URI(window.location).search(true);
    query.select = query.select ? JSON.parse(query.select) : {};

    var newQueryURI = new URI("").search({geneQuery: query.geneQuery, select: JSON.stringify(query.select)});
    navigateTo(newQueryURI);

    function renderPage() {

        React.render(
            React.createElement(FacetsTree, {facets: facetsTreeData, checkedFacets: query.select, setChecked: setChecked}),
            facetsElement
        );

        renderQueryToResults(query);
    }

    function setChecked(checked, facet, facetItem) {
        if (checked) {
            addSelection(query.select, facet, facetItem)
        } else {
            removeSelection(query.select, facet, facetItem);
        }
        var newQueryURI = new URI("").search({geneQuery: query.geneQuery, select: JSON.stringify(query.select)});
        navigateTo(newQueryURI.toString());
    }

    function navigateTo(url) {
        var state, title;
        if (ie9) {
            //window.location.search = url;
        } else {
            history.pushState(null, null, window.location.pathname + url);
        }
        renderPage();
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

    function renderQueryToResults(query) {
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
                        if (facets == "species" && value == true) {
                            species.push(key);
                        } else if (facets == "factors" && value == true) {
                            factors.push(key);
                        } else if (facets == "Experiment type" && value == true) {
                            experimentType.push(key);
                        } else if (facets == "kingdom" && value == true) {
                            kingdom.push(key);
                        } else if (facets == "Number of replicates" && value == true) {
                            numReplicates.push(key);
                        } else if (facets == "regulation" && value == true) {
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
                    React.createElement(DifferentialResults, {results: diffResultsData.results, maxDownLevel: diffResultsData.maxDownLevel, minDownLevel: diffResultsData.minDownLevel, minUpLevel: diffResultsData.minUpLevel, maxUpLevel: diffResultsData.maxUpLevel}),
                    resultsElement
                );
            }
        });
    }

};
