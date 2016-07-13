"use strict";
//*------------------------------------------------------------------*
var React = require('react');
var ReactDOMServer = require('react-dom/server')
//*------------------------------------------------------------------*

var Tooltip = React.createClass({
  propTypes: {
    config: React.PropTypes.shape({
      isDifferential: React.PropTypes.bool.isRequired
    }).isRequired,
    colour: React.PropTypes.string.isRequired,
    xLabel: React.PropTypes.string.isRequired,
    yLabel: React.PropTypes.string.isRequired,
    value:  React.PropTypes.number.isRequired,
    unit:   React.PropTypes.string
  },  //TODO extend this prop checker.Props for this component are created dynamically so it's important. If differential, expect p-values and fold changes, etc.

  render: function(){
    return (
      <div style={{whiteSpace: "pre"}}>
        {this._elt("Sample name",this.props.yLabel)}
        {this._elt("Experimental condition", this.props.xLabel)}
        {
          <div>
            {this._tinySquare()}
            {this.props.config.isDifferential? "Fold change: ": "Expression level: "}{this._bold(this.props.value ? (this.props.value+" "+(this.props.unit||"") ):"Below cutoff")}
          </div>
        }
      </div>
    );
  },
  _tinySquare: function(){
    return (
      <span style={{
        border: "1px rgb(192, 192, 192) solid",
        marginRight: "2px",
        width: "6px",
        height: "6px",
        display:"inline-block",
        backgroundColor:this.props.colour
      }} />
    );
  },
  _elt: function(name, value){
    return (
      <div key={name+" "+value}>
        {name+": "}
        {value.length >50? <br/> : null }
        {this._bold(value)}
      </div>
    );
  },
  _bold: function(value){
    return (
      <b>{value}</b>
    );
  }
});


var makeFormatter = function(config){
  return function Formatter (series, point) {
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
    return ReactDOMServer.renderToStaticMarkup(
      <Tooltip {...o} config={config}/>
    );
  }
}
//*------------------------------------------------------------------*

module.exports = makeFormatter;
