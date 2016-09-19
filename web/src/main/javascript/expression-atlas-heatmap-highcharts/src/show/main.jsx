"use strict";

//*------------------------------------------------------------------*

var React = require('react');

//*------------------------------------------------------------------*

var Button = require('react-bootstrap/lib/Button');
var DownloadProfilesButton = require('download-profiles-button');

var PropTypes = require('../PropTypes.js');
var HeatmapCanvas = require('./HeatmapCanvas.jsx');
var CoexpressionOption = require('./CoexpressionOption.jsx');

var TooltipStateManager = require('../util/TooltipStateManager.jsx');

//*------------------------------------------------------------------*

require('./SeriesLegend.less');
var HeatmapLegendBox = React.createClass({
    propTypes: {
        name: React.PropTypes.string.isRequired,
        colour: React.PropTypes.string.isRequired,
        on: React.PropTypes.bool.isRequired,
        onClickCallback: React.PropTypes.func.isRequired,
        clickable: React.PropTypes.bool.isRequired
    },

    render: function () {
        return (
            <div className={"legend-item " +
                            (this.props.clickable ? "clickable " : "") +
                            (this.props.clickable && !this.props.on ? "legend-item-off" : "")}
                 onClick={this.props.onClickCallback}>
                <div style={{background: this.props.colour}} className="legend-rectangle"></div>
                <span style={{verticalAlign: "middle"}}>{this.props.name}</span>
            </div>
        );
    }
});

var propsForOrderingDropdown = {
    available: React.PropTypes.arrayOf(React.PropTypes.string).isRequired,
    current: React.PropTypes.string.isRequired,
    onSelect: React.PropTypes.func.isRequired
};

var OrderingDropdown = React.createClass({
    propTypes: propsForOrderingDropdown,

    getInitialState: function () {
        return {selected: this.props.current}
    },

    handleChange: function (e) {
        this.state.selected = e.target.value;
        this.props.onSelect(this.state.selected);
        this.forceUpdate();
    },

    render: function () {

        var createOption = function (option, key) {
            return <option key={key} value={option}>{option}</option>;
        };

        return (
            <div style={{float: "left", marginRight: "10px", marginTop: "1px"}}>
                <span>Sort by: </span>
                <select onChange={this.handleChange} value={this.state.selected}>
                    {this.props.available.map(createOption)}
                </select>
            </div>
        );
    }
});

var HeatmapOptions = React.createClass({
    propTypes: {
        marginRight: React.PropTypes.number.isRequired,
        downloadOptions: React.PropTypes.object.isRequired,
        googleAnalyticsCallback: React.PropTypes.func.isRequired,
        showUsageMessage: React.PropTypes.bool.isRequired,
        orderings: React.PropTypes.shape(propsForOrderingDropdown)
    },

    render: function () {
        return (
            <div ref="countAndLegend" className="gxaHeatmapCountAndLegend" style={{paddingBottom: '15px', position: 'sticky'}}>
                <div style={{display: 'inline-block', verticalAlign: 'top'}}>
                  {this.props.introductoryMessage}
                </div>
                <div style={{display: "inline-block", verticalAlign: "top", float: "right", marginRight: this.props.marginRight}}>

                    { this.props.orderings.available.length > 1
                        ?
                          <OrderingDropdown
                            available={this.props.orderings.available}
                            current={this.props.orderings.current}
                            onSelect={this.props.orderings.onSelect}/>
                        :
                          null
                      }
                    <DownloadProfilesButton ref="downloadProfilesButton"
                      {...this.props.downloadOptions}
                      onDownloadCallbackForAnalytics={
                          function() {
                              this.props.googleAnalyticsCallback('send', 'event', 'HeatmapHighcharts', 'downloadData')
                          }.bind(this)}/>

                </div>
                    {this.props.showUsageMessage
                      ?
                        <div style={{fontSize: 'small', color: 'grey'}}>
                            Select a section of the heatmap to zoom in
                        </div>
                      : null}
                </div>
        );
    }
});

var HeatmapCanvasWithTooltips = React.createClass({
  render: function(){
    return (
      <TooltipStateManager
        managedComponent={HeatmapCanvas}
        managedComponentProps={this.props.heatmapProps}
        onUserSelectsColumn={this.props.anatomogramCallbacks.onUserSelectsColumn}
        onUserSelectsRow={this.props.anatomogramCallbacks.onUserSelectsRow}
        onUserSelectsPoint={this.props.anatomogramCallbacks.onUserSelectsPoint}
        tooltips={this.props.tooltips} />
    )
  }
});
var __heatmapCanvas = function(tooltips,anatomogramCallbacks, heatmapProps){
  return (
    !tooltips
    ? <HeatmapCanvas {...heatmapProps} {...anatomogramCallbacks}/>
    : <HeatmapCanvasWithTooltips
        heatmapProps={heatmapProps}
        tooltips={tooltips}
        anatomogramCallbacks={anatomogramCallbacks}
      />
  );
};
var heatmapCanvas = function(heatmapConfig, tooltips,anatomogramCallbacks, heatmapProps){
  return __heatmapCanvas(heatmapConfig.isExperimentPage && tooltips,anatomogramCallbacks, heatmapProps);
}

var anatomogramCallbacks = function(heatmapDataToPresent, highlightOntologyIds){
  return {
    onUserSelectsRow : function(rowLabel){
      var y =
      heatmapDataToPresent
      .yAxisCategories
      .findIndex((e)=>(e.label == rowLabel));

      var xs =
      [].concat.apply([],
         heatmapDataToPresent
         .dataSeries
         .map((series)=>series.data))
      .filter((value)=> (value.y ==y))
      .map((value)=>value.x);

      highlightOntologyIds(
        heatmapDataToPresent
        .xAxisCategories
        .filter((e,ix)=>(xs.indexOf(ix)>-1))
        .map((e)=>e.id)
      )
    },
    onUserSelectsColumn : function(columnLabel){
      highlightOntologyIds(
        heatmapDataToPresent
        .xAxisCategories
        .filter((e)=>(e.label == columnLabel))
        .map((e)=>e.id)
        .concat([""])
        [0]
      )
    },
    onUserSelectsPoint : function(columnId, rowId){
      //Column ids are, in fact, factorValueOntologyTermId's
      highlightOntologyIds(columnId||"");
    }
  }
};

var show = function (heatmapDataToPresent, orderings,colorAxis,formatters,tooltips, legend,coexpressions, properties) {
    var marginRight = 60;
    var heatmapConfig = properties.loadResult.heatmapConfig;
    return (
      <div>
        <HeatmapOptions
          marginRight={marginRight}
          introductoryMessage={heatmapConfig.introductoryMessage}
          downloadOptions={{
            downloadProfilesURL: heatmapConfig.downloadProfilesURL,
            atlasBaseURL: heatmapConfig.atlasBaseURL,
            disclaimer: heatmapConfig.disclaimer
          }}
          orderings={orderings}
          googleAnalyticsCallback={properties.googleAnalyticsCallback}
          showUsageMessage={heatmapDataToPresent.xAxisCategories.length > 100} />

        <div>
          {heatmapDataToPresent.dataSeries
            .map(function(e){return e.data;})
            .reduce(function(l,r){return l.concat(r);}, [])
            .length
            ? heatmapCanvas(
                heatmapConfig,
                tooltips,
                anatomogramCallbacks(heatmapDataToPresent, properties.onOntologyIdIsUnderFocus),
                {
                marginRight:marginRight,
                ontologyIdsToHighlight:properties.ontologyIdsToHighlight,
                heatmapData:heatmapDataToPresent,
                colorAxis:colorAxis,
                onHeatmapRedrawn:properties.onHeatmapRedrawn,
                formatters:formatters,
                genomeBrowserTemplate:heatmapConfig.genomeBrowserTemplate,
                }
              )
            : <p> No data in the series currently selected. </p>}
        </div>

        <div className ="gxaHeatmapLegend">
          {legend.map((legendItemProps) => <HeatmapLegendBox {...legendItemProps} />)}

          <div className="legend-item">
            <span className="icon icon-generic"
              data-icon="i" data-toggle="tooltip" data-placement="bottom"
              title="Baseline expression levels in RNA-seq experiments are in FPKM or TPM. Low: 0-10, Medium: 11-1000,  High: >1000. Proteomics expression levels are mapped to low, medium, high on per experiment basis.">
            </span>
          </div>
          <HeatmapLegendBox key={"No data available"}
                            name={"No data available"}
                            colour={"white"}
                            on={false}
                            onClickCallback={function(){}}
                            clickable={false}/>
        </div>
        {coexpressions?
          <CoexpressionOption {...coexpressions}/>
        : null }
      </div>
    );
};

module.exports = show;
