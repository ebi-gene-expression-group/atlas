//var React = require('react');

var HeatmapsRouter = (function (React, queryString, facetsModule, Heatmaps) {

    var Facets = facetsModule.build().Facets;

    return function (facetsElement, heatmapsElement, facetData) {

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

            React.renderComponent(Heatmaps({geneQuery: query.geneQuery, heatmaps: queryToHeatmaps(query)}),
                heatmapsElement
            );

            function setChecked(checked, species, factor) {
                var newSelect = checked ? addSelection(query.select, species, factor) : removeSelection(query.select, species, factor);
                var newQueryString = "?geneQuery=" + query.geneQuery + "&select=" + JSON.stringify(newSelect);
                console.log(newQueryString);
                navigateTo(pathname + newQueryString);
            }

            function navigateTo(url) {
                var state, title;
                history.pushState(state, title, url);
                renderPage();
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
             {
             "geneQuery": "blood",
             "species": "Homo sapiens",
             "factor": "ORGANISM_PART"
             },
             {
             "geneQuery": "blood",
             "species": "Homo sapiens",
             "factor": "CELL_LINE"
             }
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


    }

})(React, window.queryString, facetsModule, Heatmaps);
