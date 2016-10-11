"use strict";

//*------------------------------------------------------------------*

var React = require('react');

//*------------------------------------------------------------------*

var Button = require('react-bootstrap/lib/Button');
var DownloadProfilesButton = require('download-profiles-button');

var FormattersFactory = require('./Formatters.jsx');
var TooltipsFactory = require('./tooltips/main.jsx');
var PropTypes = require('../PropTypes.js');
var Show = require('../show/main.jsx');

//*------------------------------------------------------------------*

module.exports = React.createClass({
    displayName: "Heatmap with menus",
    propTypes: {
      loadResult: PropTypes.LoadResult,
      googleAnalyticsCallback: React.PropTypes.func.isRequired,
      ontologyIdsToHighlight: React.PropTypes.arrayOf(React.PropTypes.string).isRequired,
      onOntologyIdIsUnderFocus: React.PropTypes.func.isRequired
    },

    getInitialState: function() {
        return {
            ordering: "Default",
            grouping: "Default",
            group: "",
            dataSeriesToShow: this.props.loadResult.heatmapData.dataSeries.map(function(e){return true;}),
            coexpressionsShown: 0,
            zoom: false
        };
    },

    _onUserZoom: function(zoomedIn) {
        this.setState({
            zoom: zoomedIn
        })
    },

    _heatmapDataToPresent: function () {
      return require('./Manipulators.js').manipulate(
        {
          ordering: this.props.loadResult.orderings[this.state.ordering],
          grouping: this.state.grouping,
          group: this.state.group,
          dataSeriesToKeep: this.state.dataSeriesToShow,
          allowEmptyColumns: this.props.loadResult.heatmapConfig.isExperimentPage && this.state.grouping === this.getInitialState().grouping,
          maxIndex:this.state.coexpressionsShown
        },
      this.props.loadResult.heatmapData)
    },

    _labels: function(){
        return (
          this.props.loadResult.heatmapData.dataSeries
          .map((e)=>{return {
            colour: e.info.colour,
            name: e.info.name
          }})
        );
    },

    _orderings: function(){
      return {
        available: Object.keys(this.props.loadResult.orderings),
        current: this.state.ordering,
        disabled: this.state.zoom,
        onSelect: function(orderingChosen){
          this.setState({ordering: orderingChosen})
        }.bind(this)
      }
    },

    _filters: function(){
      return (
        [
          { name: "Expression Value",
            value: {
              available:
                ["All"].concat(this.props.loadResult.heatmapData.dataSeries.map((e)=>e.info.name)),
              current:
                this.state.dataSeriesToShow.reduce((l,r)=> l&&r, true)
                ? "All"
                : this.props.loadResult.heatmapData.dataSeries[this.state.dataSeriesToShow.indexOf(true)].info.name,
              onSelect: (selectedDataSeries) => {
                const ix =
                  this.props.loadResult.heatmapData.dataSeries
                  .map((e)=>e.info.name)
                  .indexOf(selectedDataSeries)
                const isAll =
                  !selectedDataSeries || selectedDataSeries === "All"
                this.setState((previousState) => {
                    return Object.assign(previousState, {
                        dataSeriesToShow:
                          previousState.dataSeriesToShow
                          .map(function(e, jx){
                            return isAll || (ix===jx);
                          })
                    });
                });
              }
            }
          }
        ].concat(this._groupingFilters())
      )
    },

    _groupingFilters: function(){
      const groupingNames =
        [].concat.apply([],
          this.props.loadResult.heatmapData.xAxisCategories
          .map(function(columnHeader){
            return (
              (columnHeader.info.groupings ||[])
              .map((grouping)=>grouping.name)
            )
          })
        )
        .filter((e,ix,self)=>self.indexOf(e)===ix)

      return (
        groupingNames
        .map((name)=> {
          return {
            name: name,
            value: {
              current:
                this.state.grouping === name
                ? this.state.group
                : "All",
              available:
                ["All"].concat(
                  [].concat.apply([],
                    this.props.loadResult.heatmapData.xAxisCategories
                    .map(function(columnHeader){
                      return (
                        (columnHeader.info.groupings ||[])
                        .filter((grouping)=>grouping.name)
                        .map((grouping)=>grouping.values.map((g)=>g.label))
                        .concat([[]])
                        [0]
                      )
                    })
                  )
                  .filter((e,ix,self)=>self.indexOf(e)===ix)
                  .sort()
                ),
              onSelect: (group) => {
                this.setState({
                  grouping: name,
                  group: group === "All" ? "" : group
                })
              }
            }
          }
        })

      )
    },

    _legend: function(){ //See properties required for HeatmapLegendBox
      return (
        this.props.loadResult.heatmapData.dataSeries
          .map(function(e, ix){
            return {
              key: e.info.name,
              name: e.info.name,
              colour: e.info.colour,
              on: this.state.dataSeriesToShow[ix]
            };
          }.bind(this)
        )
      );
    },

    _groupings: function(){
      return {
        available:
          [].concat.apply(["Default"],
            this.props.loadResult.heatmapData.xAxisCategories.map(function(columnHeader){
              return (
                (columnHeader.info.groupings ||[])
                .map((grouping)=>grouping.name)
              )
            })
          )
          .filter((e,ix,self)=>self.indexOf(e)==ix),
        current: this.state.grouping,
        disabled: this.state.zoom,
        onSelect: function(groupingChosen){
          this.setState({grouping: groupingChosen})
        }.bind(this)
      }
    },

    _coexpressionOption: function(){
      return (
        this.props.loadResult.heatmapConfig.coexpressions &&
        {
          geneName: this.props.loadResult.heatmapConfig.coexpressions.coexpressedGene,
          numCoexpressionsVisible: this.state.coexpressionsShown,
          numCoexpressionsAvailable: this.props.loadResult.heatmapConfig.coexpressions.numCoexpressionsAvailable,
          showCoexpressionsCallback: (e)=>{this.setState({coexpressionsShown: e})}
        }
      );
    },

    render: function(){
      var heatmapDataToPresent = this._heatmapDataToPresent();
      return (
        Show(
          heatmapDataToPresent,
          this._orderings(),
          this._filters(),
          this._onUserZoom,
          this.props.loadResult.colorAxis||undefined,
          FormattersFactory(this.props.loadResult.heatmapConfig),
          TooltipsFactory(this.props.loadResult.heatmapConfig, heatmapDataToPresent.xAxisCategories,heatmapDataToPresent.yAxisCategories),
          this._legend(),
          this._coexpressionOption(),
          this._groupings(),
          this.props
        )
      );
    }
});
