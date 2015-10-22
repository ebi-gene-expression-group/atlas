"use strict";

//*------------------------------------------------------------------*

var React = require('react');
var $ = require('jquery');
var jQuery = $;
require('jquery.browser');

var URI = require('urijs');

//*------------------------------------------------------------------*

var FacetsTree = require('./facets-tree.jsx');
var Heatmaps = require('./baseline-heatmaps.jsx');

//*------------------------------------------------------------------*

module.exports = function (facetsContainerId, heatmapsContainerId, facetsTreeData, atlasHost) {

    var ie9 = $.browser.msie && $.browser.version < 10;
    if (!ie9) {
        window.addEventListener('popstate', renderPage, false);
    }

    var facetsElement = document.getElementById(facetsContainerId),
        heatmapsElement = document.getElementById(heatmapsContainerId),
        host = atlasHost ? atlasHost : window.location.host;

    renderPage();

    function renderPage() {
        var query = new URI(window.location).search(true);
        query.select = query.select ? JSON.parse(query.select) : initializeQuerySelect();

        var newQueryURI = new URI("").search({geneQuery: query.geneQuery, select: JSON.stringify(query.select)});
        navigateTo(query, newQueryURI);
    }

    function render(query) {
        React.render(
            React.createElement(FacetsTree, {
                facets: facetsTreeData,
                checkedFacets: query.select,
                setChecked: setChecked
            }),
            facetsElement
        );

        React.render(
            React.createElement(Heatmaps, {
                geneQuery: query.geneQuery,
                heatmaps: queryToHeatmaps(query),
                atlasHost: host
            }),
            heatmapsElement
        );
    }

    function initializeQuerySelect() {
        var querySelect = {};

        for (var facet in facetsTreeData) {
            if (facetsTreeData.hasOwnProperty(facet)) {

                var factors = facetsTreeData[facet];
                for(var factor in factors) {
                    if (factors.hasOwnProperty(factor) && factors[factor].name === "ORGANISM_PART") {
                        addSelection(querySelect, facet, factors[factor].name);
                    }
                }

            }
        }

        return querySelect;
    }

    function setChecked(checked, species, factor) {
        var query = new URI(window.location).search(true);
        query.select = query.select ? JSON.parse(query.select) : initializeQuerySelect();

        if (checked) {
            addSelection(query.select, species, factor);
        } else {
            removeSelection(query.select, species, factor);
        }
        var newQueryURI = new URI("").search({geneQuery: query.geneQuery, select: JSON.stringify(query.select)});
        navigateTo(query, newQueryURI);
    }

    function navigateTo(query, url) {
        var state, title;
        if (ie9) {
            //window.location.search = url;
        } else {
            history.pushState(null, null, window.location.pathname + url);
        }
        render(query);
    }

    function addSelection(querySelect, species, factor) {
        if (!querySelect[species]) {
            querySelect[species] = {};
        }
        querySelect[species][factor] = true;
    }

    function removeSelection(select, species, factor) {
        select[species][factor] = false;
        return select;
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

