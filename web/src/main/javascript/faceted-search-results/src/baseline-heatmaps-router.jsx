"use strict";

//*------------------------------------------------------------------*

var React = require('react');
var $ = require('jquery');
var jQuery = $;
require('jquery.browser');
var queryString = require('query-string');

//*------------------------------------------------------------------*

var FacetsTree = require('./facets-tree.jsx');
var Heatmaps = require('./baseline-heatmaps.jsx');

//*------------------------------------------------------------------*

module.exports = function (facetsElement, heatmapsElement, facetData) {

    //TODO: add this outside the module, when module is first loaded
    var ie9 = $.browser.msie && $.browser.version < 10;
    if (!ie9) {
        window.addEventListener('popstate', renderPage, false);
    }

    renderPage();

    function renderPage() {
        var query = queryString.parse(window.location.search.slice(1)),
            pathname = window.location.pathname;
        query.select = query.select && JSON.parse(query.select);
        render(query, pathname);
    }

    function render(query, pathname) {
        React.render(
            React.createElement(FacetsTree, {facets: facetData, checkedFacets: query.select, setChecked: setChecked}),
            facetsElement);

        React.render(React.createElement(Heatmaps, {geneQuery: query.geneQuery, heatmaps: queryToHeatmaps(query)}),
            heatmapsElement
        );

        function setChecked(checked, species, factor) {
            var newSelect = checked ? addSelection(query.select, species, factor) : removeSelection(query.select, species, factor);
            var newQueryString = "?geneQuery=" + query.geneQuery + "&select=" + JSON.stringify(newSelect);
            console.log(newQueryString);
            navigateTo(pathname, newQueryString);
        }

        function navigateTo(pathname, newQueryString) {
            var state, title;
            if (ie9) {
                window.location.search = newQueryString;
            } else {
                history.pushState(null, null, pathname + newQueryString);
                renderPage();
            }
        }

        function addSelection(select, species, factor) {
            if (!select) {
                select = {};
            }

            if (!select[species]) {
                select[species] = {};
            }
            select[species][factor] = true;
            return select;
        }

        function removeSelection(select, species, factor) {
            select[species][factor] = false;
            return select;
        }

    }

    function queryToHeatmaps(query) {
        /* eg:
         query.geneQuery=blood
         query.select={ "homo sapiens" : { "CELL_LINE": true, "ORGANISM_PART": true } }

         ->

         [
            {"geneQuery": "blood",
             "species": "Homo sapiens",
             "factor": "ORGANISM_PART"},

            {"geneQuery": "blood",
             "species": "Homo sapiens",
             "factor": "CELL_LINE"}
         ]
        */
        var select = query.select;
        var geneQuery = query.geneQuery;
        var heatmaps = [];

        for (var species in select) {
            if (select.hasOwnProperty(species)) {

                var factors = select[species];

                for (var factor in factors) {
                    if (factors.hasOwnProperty(factor)) {
                        if (factors[factor]) {
                            heatmaps.push({
                                "geneQuery": geneQuery,
                                "species": species,
                                "factor": factor
                            });
                        }
                    }
                }
            }
        }

        return heatmaps;
    }

};

