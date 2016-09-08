"use strict";

//*------------------------------------------------------------------*

var React = require('react');



//*------------------------------------------------------------------*

var Button = require('react-bootstrap/lib/Button');
var DownloadProfilesButton = require('download-profiles-button');

var FormattersFactory = require('./Formatters.jsx');
var PropTypes = require('./PropTypes.js');
var createColorAxis = require('./ColoursForHighcharts.js');
var HeatmapCanvas = require('./HeatmapCanvas.jsx');

//*------------------------------------------------------------------*

var HeatmapContainer = React.createClass({
    propTypes: {
        heatmapConfig: React.PropTypes.object.isRequired,
        googleAnalyticsCallback: React.PropTypes.func.isRequired,
        heatmapData: PropTypes.HeatmapData,
        onHeatmapRedrawn: React.PropTypes.func.isRequired,
        ontologyIdsToHighlight: React.PropTypes.arrayOf(React.PropTypes.string).isRequired,
        onOntologyIdIsUnderFocus : React.PropTypes.func.isRequired
    },

    getInitialState: function() {
        return {
            ordering: "Default",
            dataSeriesToShow: this.props.heatmapData.dataSeries.map(function(e){return true;})
        };
    },

    _heatmapData: function() {
      return require('./Manipulators.js').order(this.props.heatmapData.orderings[this.state.ordering], this.props.heatmapData);
    },

    _dataToShow: function () {
      return require('./Manipulators.js').filterByDataSeries(this.state.dataSeriesToShow,this._heatmapData());
    },

    _labels: function(){
        return this.props.heatmapData.dataSeries.map((e)=>{return {colour: e.info.colour, name: e.info.name}});
    },

    _renderLegend: function(){
        return (
        <div id ="barcharts_legend_list_items" ref="barcharts_legend_items">
            { this._labels().map(
                function(e, ix){
                    return (
                        <HeatmapLegendBox key={e.name}
                                          name={e.name}
                                          colour={e.colour}
                                          on={this.state.dataSeriesToShow[ix]}
                                          onClickCallback={this._makeLabelToggle(ix)} />
                    );
                }.bind(this))
            }

            <div className="legend-item special">
                <span className="icon icon-generic" data-icon="i" data-toggle="tooltip" data-placement="bottom"
                    title="Baseline expression levels in RNA-seq experiments are in FPKM or TPM. Low: 0-10, Medium: 11-1000,  High: >1000. Proteomics expression levels are mapped to low, medium, high on per experiment basis.">
                </span>
            </div>
            <HeatmapLegendBox key={"No data available"}
                              name={"No data available"}
                              colour={"white"}
                              on={ true}
                              onClickCallback={function(){}}/>
             </div>
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

    render: function () {
        var marginRight = 60;
        var data = this._dataToShow();
        return (
            <div>
                <HeatmapOptions
                    marginRight={marginRight}
                    introductoryMessage={this.props.heatmapConfig.introductoryMessage}
                    downloadOptions={{
                        downloadProfilesURL: this.props.heatmapConfig.downloadProfilesURL,
                        atlasBaseURL: this.props.heatmapConfig.atlasBaseURL,
                        disclaimer: this.props.heatmapConfig.disclaimer
                    }}
                    orderings={{
                        available: Object.keys(this.props.heatmapData.orderings),
                        current: this.state.ordering,
                        onSelect: function(orderingChosen){
                          this.setState({ordering: orderingChosen})
                        }.bind(this)
                    }}
                    googleAnalyticsCallback={this.props.googleAnalyticsCallback}
                    showUsageMessage={this.props.heatmapData.xAxisCategories.length > 100} />

                    <div id="highcharts_container">
                        {data.dataSeries
                          .map(function(e){return e.data;})
                          .reduce(function(l,r){return l.concat(r);}, [])
                          .length
                          ? <HeatmapCanvas
                              marginRight={marginRight}
                              ontologyIdsToHighlight={this.props.ontologyIdsToHighlight}
                              heatmapData={data}
                              colorAxis={this.props.heatmapConfig.isExperimentPage ? createColorAxis(this.props.heatmapData.dataSeries) : undefined}
                              onHeatmapRedrawn={this.props.onHeatmapRedrawn}
                              formatters={FormattersFactory(this.props.heatmapConfig)}
                              genomeBrowserTemplate={this.props.heatmapConfig.genomeBrowserTemplate}
                              onUserSelectsColumn={this.props.onOntologyIdIsUnderFocus} />
                          : <p> No data in the series currently selected. </p>}
                    </div>

                    {this._renderLegend()}

            </div>
        );
    }

});

var HeatmapLegendBox = React.createClass({
    propTypes: {
        name: React.PropTypes.string.isRequired,
        colour: React.PropTypes.string.isRequired,
        on: React.PropTypes.bool.isRequired,
        onClickCallback: React.PropTypes.func.isRequired
    },

    render: function () {
        return (
            <div className={"legend-item "+(this.props.on? "special" : "legend-item-off")} onClick={this.props.onClickCallback}>
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

//*------------------------------------------------------------------*

module.exports = HeatmapContainer;
