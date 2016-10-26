const React = require(`react`);
const Button = require( `react-bootstrap/lib/Button`);
const FormattersFactory = require(`./Formatters.jsx`);
const TooltipsFactory = require(`./tooltips/main.jsx`);
const PropTypes = require(`../PropTypes.js`);
const Show = require(`../show/main.jsx`);
var _ = require('lodash');

module.exports = React.createClass({
    displayName: `Heatmap with settings`,

    propTypes: {
        loadResult: PropTypes.LoadResult,
        googleAnalyticsCallback: React.PropTypes.func.isRequired,
        ontologyIdsToHighlight: React.PropTypes.arrayOf(React.PropTypes.string).isRequired,
        onOntologyIdIsUnderFocus: React.PropTypes.func.isRequired
    },

    getInitialState() {
        return {
            ordering: `Default`,
            filtersSelection: this._filters(),
            coexpressionsShown: 0,
            zoom: false
        };
    },

    _onUserZoom(zoomedIn) {
        this.setState({ zoom: zoomedIn })
    },

    _heatmapDataToPresent() {
        return require(`./Manipulators.js`).manipulate(
            {
                ordering: this.props.loadResult.orderings[this.state.ordering],
                dataSeriesToKeep: this._expressionLevelFilter().values.map(levelFilterValue => this.state.filtersSelection[0].values.includes(levelFilterValue)),
                groupsToShow: this.state.filtersSelection.length > 1 ? this.state.filtersSelection.slice(1) : null,
                allowEmptyColumns:
                    this.props.loadResult.heatmapConfig.isExperimentPage &&
                    (  this.state.grouping === this.getInitialState().grouping || !this.state.group),
                maxIndex:this.state.coexpressionsShown
            },
            this.props.loadResult.heatmapData
        )
    },

    _labels() {
        return (
            this.props.loadResult.heatmapData.dataSeries.map(e => ({
                colour: e.info.colour,
                name: e.info.name
            }))
        );
    },

    _orderings() {
        return {
            available: Object.keys(this.props.loadResult.orderings),
            selected: this.state.ordering,
            onSelect: orderingChosen => {
                this.setState({ ordering: orderingChosen })
            }
      }
    },

    _filters() {
        return [this._expressionLevelFilter()].concat(this._groupingFilters())
    },

    _expressionLevelFilter() {
        return (
            {
                name: `Expression Value${this.props.loadResult.heatmapConfig.isExperimentPage ? ` – relative` : ``}`,
                values: this.props.loadResult.heatmapData.dataSeries.map(e => e.info.name),
            }
        )
    },

    // Column header ->
    // {
    //   label: "accessory axillary lymph node",
    //   id: "",
    //   info: {
    //           trackId: "",
    //           tooltip: Object,
    //           groupings: [
    //                        {
    //                          name: "Anatomical Systems",
    //                          memberName: "Anatomical system"
    //                          values: [
    //                                    {
    //                                      label: "Unmapped",
    //                                      id: ""
    //                                    }
    //                                  ]
    //                        }
    //                      ]
    //         }
    // }
    _groupingFilters() {
        const groupingNames =
            _.uniq(
                _.flatten(
                    this.props.loadResult.heatmapData.xAxisCategories
                        .map(columnHeader =>
                            columnHeader.info.groupings.map(grouping => grouping.name)
                        )
                )
            );

        return (
            groupingNames.map(name =>
                ({
                    name: name,
                    values:
                        _.uniq(
                            _.flatten(
                                this.props.loadResult.heatmapData.xAxisCategories
                                    .map(columnHeader =>
                                        columnHeader.info.groupings
                                            .filter(grouping => grouping.name === name)
                                            .map(grouping => grouping.values.map(g => g.label))
                                            .concat([[]])
                                            [0])
                            )
                        )
                })
            )
        )
    },

    _onFilterChange(newFiltersSelection) {
        this.setState({ filtersSelection: newFiltersSelection });
    },

    _legend() { //See properties required for HeatmapLegendBox
        return (
            this.props.loadResult.heatmapData.dataSeries
                .map((e, ix) =>
                    ({
                        key: e.info.name,
                        name: e.info.name,
                        colour: e.info.colour,
                        on: true //this.state.filtersSelection[0].values[ix]
                    })
                )
        );
    },

    _coexpressionOption() {
        return (
            this.props.loadResult.heatmapConfig.coexpressions &&
            {
                geneName: this.props.loadResult.heatmapConfig.coexpressions.coexpressedGene,
                numCoexpressionsVisible: this.state.coexpressionsShown,
                numCoexpressionsAvailable: this.props.loadResult.heatmapConfig.coexpressions.numCoexpressionsAvailable,
                showCoexpressionsCallback: e => {this.setState({ coexpressionsShown: e })}
            }
        );
    },

    render() {
        const heatmapDataToPresent = this._heatmapDataToPresent();
        return (
            Show(
                heatmapDataToPresent,
                this._orderings(),
                this._filters(),
                this.state.filtersSelection,
                this._onFilterChange,
                this.state.zoom,
                this._onUserZoom,
                this.props.loadResult.colorAxis||undefined,
                FormattersFactory(this.props.loadResult.heatmapConfig),
                TooltipsFactory(this.props.loadResult.heatmapConfig, heatmapDataToPresent.xAxisCategories,heatmapDataToPresent.yAxisCategories),
                this._legend(),
                this._coexpressionOption(),
                this.props
            )
        );
    }
});
