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
          dataSeriesToKeep: this.state.dataSeriesToShow,
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

    _makeLabelToggle: function(ix){
        return function(){
            this.setState(function(previousState){
                return Object.assign(previousState, {
                    dataSeriesToShow: previousState.dataSeriesToShow.map(function(e,jx){
                        return ix===jx ? !e : e;
                    })
                });
            });
        }.bind(this);
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

    _legend: function(){ //See properties required for HeatmapLegendBox
      return (
        this.props.loadResult.heatmapData.dataSeries
          .map(function(e, ix){
            return {
              key: e.info.name,
              name: e.info.name,
              colour: e.info.colour,
              on: this.state.dataSeriesToShow[ix],
              onClickCallback: this._makeLabelToggle(ix),
              clickable: true
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
