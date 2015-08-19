"use strict";

//*------------------------------------------------------------------*

var React = require('react');
var $ = require('jquery');
var jQuery = $;
require('jquery.browser');

var queryString = require('query-string');

var URI = require('URIjs');

//*------------------------------------------------------------------*

var FacetsTree = require('./facets-tree.jsx');
var Heatmaps = require('./baseline-heatmaps.jsx');

//*------------------------------------------------------------------*

module.exports = function (facetsContainerId, heatmapsConatinerId, facetsTreeData, atlasHost) {

    var facetsElement = document.getElementById(facetsContainerId),
        heatmapsElement = document.getElementById(heatmapsConatinerId);

    //TODO: add this outside the module, when module is first loaded
    var ie9 = $.browser.msie && $.browser.version < 10;
    if (!ie9) {
        window.addEventListener('popstate', renderPage, false);
    }

    renderPage();

    function renderPage() {
        var query = queryString.parse(window.location.search.slice(1));
        query.select = query.select && JSON.parse(query.select);
        render(query);
    }

    function render(query) {
        var host = atlasHost ? atlasHost : window.location.host;

        if(query.select == undefined) {
            initializeQuery(query);
        }

        React.render(
            React.createElement(FacetsTree, {facets: facetsTreeData, checkedFacets: query.select, setChecked: setChecked}),
            facetsElement);

        React.render(React.createElement(Heatmaps, {geneQuery: query.geneQuery, heatmaps: queryToHeatmaps(query), host: host}),
            heatmapsElement
        );

        function initializeQuery(query) {
            for (var facet in facetsTreeData) {
                var factors = facetsTreeData[facet];
                for(var factor in factors) {
                    if(factors[factor].name == "ORGANISM_PART") {
                        var newSelect = addSelection(query.select, facet, factors[factor].name);
                        query.select = newSelect;
                    }
                }
            }

            var newQueryString = new URI("").search({geneQuery: query.geneQuery, select: JSON.stringify(query.select)}).normalize();
            navigateTo(newQueryString);
            return query.select;
        }

        function setChecked(checked, species, factor) {
            var newSelect = checked ? addSelection(query.select, species, factor) : removeSelection(query.select, species, factor);
            var newQueryString = new URI("").search({geneQuery: query.geneQuery, select: JSON.stringify(newSelect)}).normalize();
            navigateTo(newQueryString);
        }

        function navigateTo(newQueryString) {
            var state, title;
            if (ie9) {
                window.location.search = newQueryString;
            } else {
                history.pushState(null, null, window.location.pathname + newQueryString);
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

