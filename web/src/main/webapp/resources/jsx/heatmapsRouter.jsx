//var React = require('react');

var HeatmapsRouter = (function (React, page, queryString, facetsModule, Heatmaps) {

    var Facets = facetsModule.build().Facets;

    return function (facetsElement, heatmapsElement, facetData) {

        //TODO: add this outside the module, when module is first loaded
        window.addEventListener('popstate', onPopState, false);

        renderMain();

        function onPopState() {
            renderMain();
        }

        function renderMain() {
            var ctx = {};
            ctx.pathname = window.location.pathname;
            ctx.query = queryString.parse(window.location.search.slice(1));
            ctx.query.select = ctx.query.select && JSON.parse(ctx.query.select);
            render(ctx);
        }

        function render(ctx) {
            React.renderComponent(Facets({facets: facetData, checkedFacets: ctx.query.select, setChecked: setChecked}),
                facetsElement
            );

            React.renderComponent(Heatmaps({geneQuery: ctx.query.geneQuery, heatmaps: queryToHeatmaps(ctx.query)}),
                heatmapsElement
            );

            function setChecked(checked, species, factor) {
                console.log(ctx.query);
                console.log("setChecked " + species + " " + factor + ": " + checked);

                var newSelect = checked ? addSelection(ctx.query.select, species, factor) : removeSelection(ctx.query.select, species, factor);
                var newQueryString = "?geneQuery=" + ctx.query.geneQuery + "&select=" + JSON.stringify(newSelect);
                console.log(newQueryString);
                navigateTo(ctx.pathname + newQueryString);
            }

            function navigateTo(url) {
                var state, title;
                history.pushState(state, title, url);
                renderMain();
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
             "species": "Mus musculus",
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

})(React, page, window.queryString, facetsModule, Heatmaps);
