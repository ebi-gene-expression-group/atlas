"use strict";

//*------------------------------------------------------------------*

var React = require('react');
var ReactDOM = require('react-dom');

var EventEmitter = require('events');

var URI = require('urijs');

//*------------------------------------------------------------------*

var FacetsTree = require('./BaselineFacetsTree.jsx');
var BaselineHeatmaps = require('./BaselineHeatmaps.jsx');

//*------------------------------------------------------------------*

/**
 * @param {Object} options
 * @param {string} options.facetsContainer - id of the facets container, i.e. a <div> id
 * @param {string} options.resultsContainer - id of the results container, i.e. a <div> id
 * @param {string} options.showAnatomogramsInput - id of the show/hide anatomograms control, i.e. an <input> id
 * @param {Object} options.facetsTreeData
 * @param {string} options.atlasHost
 * @param {string} options.species
 * @param {string} options.identifier
 * @param {string} options.geneQuery
 * @param {string} options.conditionQuery
 */
module.exports = function (options) {

    var facetsElement = document.getElementById(options.facetsContainer),
        heatmapsElement = document.getElementById(options.resultsContainer),
        host = options.atlasHost ? options.atlasHost : window.location.host;

    var query = {
        geneQuery : options.identifier,
        select    : {}
    };

    var species = options.species,
        facetsTreeData = options.facetsTreeData;

    var anatomogramDataEventEmitter = new EventEmitter();
    anatomogramDataEventEmitter.setMaxListeners(0);

    if (species && facetsTreeData.hasOwnProperty(species)) {
        var speciesFactors = facetsTreeData[species];
        for(var speciesFactor in speciesFactors) {
            if (speciesFactors.hasOwnProperty(speciesFactor)) {
                addSelection(query.select, species, speciesFactors[speciesFactor].name);
            }
        }
    } else {
        parseSelectedFacetsFromLocation();
    }

    var anatomogramsInManualMode = false,
        showAnatomogramsManual, showAnatomograms;
    showAnatomogramsManual = showAnatomograms = organismPartInQuerySelect();

    pushQueryIntoBrowserHistory(true);
    renderQueryPage();

    function parseSelectedFacetsFromLocation() {
        var currentURL = new URI(window.location);
        var selectString = currentURL.search(true)["bs"];

        if (!selectString) {
            initializeQuerySelect();
        } else {
            query.select = JSON.parse(selectString);
        }
    }

    function organismPartInQuerySelect() {
        for (var species in query.select) {
            if (query.select.hasOwnProperty(species)) {
                if (query.select[species].hasOwnProperty("ORGANISM_PART") && query.select[species]["ORGANISM_PART"]) {
                        return true;
                    }
                }
            }
        return false;
    }

    function toggleAnatomograms() {
        anatomogramsInManualMode = true;
        showAnatomogramsManual = showAnatomograms = !showAnatomograms;

        renderQueryPage();
    }

    function renderQueryPage() {
        ReactDOM.render(
            React.createElement(
                FacetsTree, {facets: facetsTreeData, checkedFacets: query.select, setChecked: setChecked,
                    toggleAnatomograms: toggleAnatomograms, showAnatomograms: showAnatomograms, disableAnatomogramsCheckbox: !organismPartInQuerySelect(),
                    anatomogramDataEventEmitter: anatomogramDataEventEmitter}
            ),
            facetsElement
        );

        ReactDOM.render(
            React.createElement(
                BaselineHeatmaps, {geneQuery: query.geneQuery, conditionQuery: options.conditionQuery, heatmaps: queryToHeatmaps(query), showAnatomograms: showAnatomograms, atlasHost: host,
                    anatomogramDataEventEmitter: anatomogramDataEventEmitter}
            ),
            heatmapsElement, triggerScrollEvent
        );
    }

    function triggerScrollEvent() {
        window.dispatchEvent(new CustomEvent("scroll"));
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

        if (anatomogramsInManualMode) {
            showAnatomograms = showAnatomogramsManual && organismPartInQuerySelect();
        } else {
            showAnatomograms = organismPartInQuerySelect();
        }

        pushQueryIntoBrowserHistory(false);
        renderQueryPage();
    }


    function pushQueryIntoBrowserHistory(replace) {
        var newURL = new URI(window.location).search(function(data) {
            data.bs = JSON.stringify(query.select);
        });

        if (replace) {
            history.replaceState(null, "", newURL);
        } else {
            history.pushState(null, "", newURL);
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

