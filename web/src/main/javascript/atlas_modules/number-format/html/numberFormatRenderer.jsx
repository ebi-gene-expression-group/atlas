"use strict";

var React = require('react');
var ReactDOM = require('react-dom');

//*------------------------------------------------------------------*

var NumberFormat = require('../src/NumberFormat.jsx');

//*------------------------------------------------------------------*

var Test = React.createClass({
  getInitialState: function(){
    return {
      valueBaseline: "1",
      valueScientific:"2"
    }
  },
  render: function(){
    return (
      <div>
        <p>Baseline format </p>
        <input
        type="text"
        onChange={(evt)=>{evt.target.value && this.setState({valueBaseline:evt.target.value})}}/>
        <p>{"Raw string: "+this.state.valueBaseline}</p>
        <p>{NumberFormat.baselineExpression(this.state.valueBaseline)}</p>
        <p>Scientific format </p>
        <input
        type="text"
        onChange={(evt)=>{evt.target.value && this.setState({valueScientific:evt.target.value})}}/>
        <p>{"Raw string: "+this.state.valueScientific}</p>
        <p>{NumberFormat.scientificNotation(this.state.valueScientific)}</p>
      </div>
    )
  }
})

exports.main= function(mountNode){
  ReactDOM.render(React.createElement(Test), mountNode);
}
