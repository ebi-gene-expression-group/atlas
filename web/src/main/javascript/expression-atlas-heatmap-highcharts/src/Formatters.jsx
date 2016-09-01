"use strict";
//*------------------------------------------------------------------*
var React = require('react');
var ReactDOMServer = require('react-dom/server');
var scientificNotation = function(value){
  return <b>{require('number-format').scientificNotation(value)}</b>;
};
var escapedHtmlDecoder = require('he');

//*------------------------------------------------------------------*

var Tooltip = React.createClass({
  propTypes: {
    config: React.PropTypes.shape({
      isDifferential: React.PropTypes.bool.isRequired,
      xAxisLegendName: React.PropTypes.string.isRequired,
      yAxisLegendName: React.PropTypes.string.isRequired,
      genomeBrowserTemplate:React.PropTypes.string.isRequired
    }).isRequired,
    colour: React.PropTypes.string.isRequired,
    xLabel: React.PropTypes.string.isRequired,
    yLabel: React.PropTypes.string.isRequired,
    value:  React.PropTypes.number.isRequired,
    unit:   React.PropTypes.string,
    foldChange: React.PropTypes.number,
    pValue: React.PropTypes.string,
    tStat: React.PropTypes.string
  },  //TODO extend this prop checker.Props for this component are created dynamically so it's important. If differential, expect p-values and fold changes, etc.

  render: function(){
    return (
      <div style={{whiteSpace: "pre"}}>
        {this._div(this.props.config.yAxisLegendName,this.props.yLabel)}
        {this._div(this.props.config.xAxisLegendName, this.props.xLabel)}
        { this.props.config.isDifferential
          ? [<div key={""}>
              {this._tinySquare()}{this._span("Fold change",this.props.foldChange)}
            </div>,
             this._div("P-value", this.props.pValue,scientificNotation),
             this._div("T-statistic", this.props.tStat)]
          : <div>
            {this._tinySquare()}
            {this._span("Expression level",this.props.value ? (this.props.value+" "+(this.props.unit||"") ):"Below cutoff")}
          </div>
        }
        {!!this.props.config.genomeBrowserTemplate? this._info("Click on the cell to show expression in the Genome Browser") : null}
      </div>
    );
  },
  _tinySquare: function(){
    return (
      <span key={"Tiny "+this.props.colour+" square"}
        style={{
          border: "1px rgb(192, 192, 192) solid",
          marginRight: "2px",
          width: "6px",
          height: "6px",
          display:"inline-block",
          backgroundColor:this.props.colour
        }} />
    );
  },
  _info: function(text){
    return (
      <div>
        <i>{text}</i>
      </div>
    );
  },
  _div: function(name, value, format){
    return (
      name &&value
      ? <div key={name+" "+value}>
          {name+": "}
          {value.length >50? <br/> : null }
          {(format || this._bold)(value)}
        </div>
      : null
    );
  },
  _span: function(name, value){
    return (
      <span key={name+" "+value}>
        {name+": "}
        {value.length >50? <br/> : null }
        {this._bold(value)}
      </span>
    );
  },
  _bold: function(value){
    return (
      <b>{value}</b>
    );
  }
});

var YAxisLabel = React.createClass({
  propTypes: {
    config: React.PropTypes.shape({
      atlasBaseURL: React.PropTypes.string.isRequired,
      isMultiExperiment: React.PropTypes.bool.isRequired
    }).isRequired,
    labelText: React.PropTypes.string.isRequired,
    resourceId: React.PropTypes.string.isRequired
  },
  render: function(){
    return (
      <a href={this.props.config.atlasBaseURL+(this.props.config.isMultiExperiment? "/experiments/":"/genes/")+this.props.resourceId}>
        {this.props.labelText}
      </a>
    );
  }
});

var reactToHtml = function(component){
  return escapedHtmlDecoder.decode(ReactDOMServer.renderToStaticMarkup(component));
}

var makeFormatter = function(config){
  return {
    xAxis: function Formatter(value){
      return value.label;
    },
    yAxis: function Formatter(value){
      return reactToHtml(
        <YAxisLabel
          config={config}
          labelText={value.label}
          resourceId={value.id}/>
      );
    },
    tooltip: function Formatter (series, point) {
      var o = {
        colour: point.color,
        xLabel: series.xAxis.categories[point.x].label,
        yLabel: series.yAxis.categories[point.y].label,
        value:  point.value,
      }
      for(var key in point.options.info){
        if(point.options.info.hasOwnProperty(key)){
          o[key] = point.options.info[key];
        }
      }
      return reactToHtml(
        <Tooltip {...o} config={config}/>
      );
    }
  }
}
//*------------------------------------------------------------------*

module.exports = makeFormatter;
