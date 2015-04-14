/* global require */
/* global require */

var React = require('react');
var queryString = require('query-string');
var Facets = require('./facets.jsx');
var DifferentialResults = require('./differential-results.jsx');

module.exports = function (facetsElement, resultsElement, facetData, results) {

    //TODO: add this outside the module, when module is first loaded
    window.addEventListener('popstate', renderPage, false);

    renderPage();

    function renderPage() {
        var query = queryString.parse(window.location.search.slice(1)),
            pathname = window.location.pathname;
        query.select = query.select && JSON.parse(query.select);
        render(query, pathname);
    }

    function render(query, pathname) {
        React.renderComponent(Facets({facets: facetData, checkedFacets: query.select, setChecked: setChecked}),
            facetsElement
        );

        React.renderComponent(DifferentialResults({diffResultsData: results}),
            resultsElement
        );

        function setChecked(checked, facet, facetItem) {
            var newSelect = checked ? addSelection(query.select, facet, facetItem) : removeSelection(query.select, facet, facetItem);
            var newQueryString = "?geneQuery=" + query.geneQuery + "&select=" + JSON.stringify(newSelect);
            console.log(newQueryString);
            navigateTo(pathname + newQueryString);
        }

        function navigateTo(url) {
            var state, title;
            history.pushState(state, title, url);
            renderPage();
        }


        function addSelection(select, facet, facetItem) {
            if (!select) {
                select = {};
            }

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

    }

    //function queryToDifferentialResults(query) {
    //    /* eg:
    //     query.geneQuery="zinc finger"
    //     query.select={ "kingdom": {"ensembl": true}, "factors": {"temperature": true, "time": true} }
    //
    //     ->
    //
    //     {
    //     "geneQuery": "zinc finger",
    //     "kingdom": "ensembl",
    //     "factors": { "temperature", "time" }
    //     }
    //
    //     */
    //    var select = query.select;
    //    var geneQuery = query.geneQuery;
    //    var heatmaps = [];
    //
    //    for (var species in select) {
    //        if (select.hasOwnProperty(species)) {
    //
    //            var factors = select[species];
    //
    //            for (var factor in factors) {
    //                if (factors.hasOwnProperty(factor)) {
    //                    if (factors[factor]) {
    //                        heatmaps.push({
    //                            "geneQuery": geneQuery,
    //                            "species": species,
    //                            "factor": factor
    //                        });
    //                    }
    //                }
    //            }
    //        }
    //    }
    //
    //    return heatmaps;
    //}


};
