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

module.exports = function (facetsContainerId, heatmapsContainerId, selectedSpecies, facetsTreeData, atlasHost) {

    var ie9 = $.browser.msie && $.browser.version < 10;
    !ie9 && window.addEventListener('popstate', backButtonListener, false);

    var facetsElement = document.getElementById(facetsContainerId),
        heatmapsElement = document.getElementById(heatmapsContainerId),
        host = atlasHost ? atlasHost : window.location.host;

    var query = {
        geneQuery : "",
        select    : {}
    };
    query.select = {};
    query.geneQuery = "";

    parseGeneQueryFromLocation();
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
        var selectString = currentURL.search(true)["bs"];

        if (!selectString) {
            initializeQuerySelect();
        } else {
            query.select = JSON.parse(selectString);
        }
    }

    function renderQueryPage() {
        React.render(
            React.createElement(FacetsTree, {
                facets: facetsTreeData,
                checkedFacets: query.select,
                setChecked: setChecked,
                isDifferential: false
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

        for (var facet in facetsTreeData) {
            if (facetsTreeData.hasOwnProperty(facet)) {

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
                history.replaceState(null, null, newURL);
            } else {
                history.pushState(null, null, newURL);
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

