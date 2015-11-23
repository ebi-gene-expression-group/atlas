"use strict";

//*------------------------------------------------------------------*

var React = require('react');
var $ = require('jquery');
var jQuery = $;
require('jquery.browser');

var URI = require('urijs');

//*------------------------------------------------------------------*

var FacetsTree = require('./baseline-facets-tree.jsx');
var Heatmaps = require('./baseline-heatmaps.jsx');

//*------------------------------------------------------------------*

/**
 * @param {Object} options
 * @param {string} options.facetsContainer - id of the facets container, i.e. a <div> id
 * @param {string} options.resultsContainer - id of the results container, i.e. a <div> id
 * @param {Object} options.facetsTreeData
 * @param {string} options.atlasHost
 * @param {string} options.selectedSpecies
 * @param {string} options.identifier
 * @param {string} options.queryType - "gene", "geneSet"
 */
module.exports = function (options) {

    var selectedSpecies = options.selectedSpecies,
        facetsTreeData = options.facetsTreeData;

    var ie9 = $.browser.msie && $.browser.version < 10;
    !ie9 && window.addEventListener("popstate", backButtonListener, false);

    var facetsElement = document.getElementById(options.facetsContainer),
        heatmapsElement = document.getElementById(options.resultsContainer),
        host = options.atlasHost ? options.atlasHost : window.location.host;

    var query = {
        geneQuery : options.identifier,
        queryType : options.queryType,
        select    : {}
    };

    if (selectedSpecies && facetsTreeData.hasOwnProperty(selectedSpecies)) {
        var selectedSpeciesFactors = facetsTreeData[selectedSpecies];
        for(var selectedSpeciesFactor in selectedSpeciesFactors) {
            if (selectedSpeciesFactors.hasOwnProperty(selectedSpeciesFactor)) {
                addSelection(query.select, selectedSpecies, selectedSpeciesFactors[selectedSpeciesFactor].name);
            }
        }
    } else {
        parseSelectedFacetsFromLocation();
    }
    pushQueryIntoBrowserHistory(true);
    renderQueryPage();

    function backButtonListener() {
        if (window.location.hash === "#baseline") {
            parseSelectedFacetsFromLocation();
            renderQueryPage();
        }
    }

    function parseSelectedFacetsFromLocation() {
        var currentURL = new URI(window.location);
        var selectString = currentURL.search(true)["bs"];

        if (!selectString) {
            initializeQuerySelect();
        } else {
            query.select = JSON.parse(selectString);
        }
    }

    function renderQueryPage() {
        React.render(
            React.createElement(
                FacetsTree, {facets: facetsTreeData, checkedFacets: query.select, setChecked: setChecked}
            ),
            facetsElement
        );

        React.render(
            React.createElement(
                Heatmaps, {geneQuery: query.geneQuery, heatmaps: queryToHeatmaps(query), atlasHost: host}
            ),
            heatmapsElement
        );
    }

    function initializeQuerySelect() {

        for (var facet in options.facetsTreeData) {
            if (options.facetsTreeData.hasOwnProperty(facet)) {

                var factors = facetsTreeData[facet];
                var checked = false;
                for(var factor in factors) {
                    if (factors.hasOwnProperty(factor) && factors[factor].name === "ORGANISM_PART") {
                        addSelection(query.select, facet, factors[factor].name);
                        checked = true;
                        break;
                    }
                }

                if(factors.length >= 1 && !checked) {
                    addSelection(query.select, facet, factors[0].name);
                }

            }
        }

    }

    function setChecked(checked, species, factor) {
        if (checked) {
            addSelection(query.select, species, factor);
        } else {
            removeSelection(query.select, species, factor);
        }
        pushQueryIntoBrowserHistory(false);
        renderQueryPage();
    }


    function pushQueryIntoBrowserHistory(replace) {
        var newURL = new URI(window.location).search(function(data) {
            data.bs = JSON.stringify(query.select);
        });

        if (!ie9) {
            if (replace) {
                history.replaceState(null, "", newURL);
            } else {
                history.pushState(null, "", newURL);
            }
        }
    }

    function addSelection(querySelect, species, factor) {
        if (!querySelect[species]) {
            querySelect[species] = {};
        }
        querySelect[species][factor] = true;
    }

    function removeSelection(select, species, factor) {
        select[species][factor] = false;
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

