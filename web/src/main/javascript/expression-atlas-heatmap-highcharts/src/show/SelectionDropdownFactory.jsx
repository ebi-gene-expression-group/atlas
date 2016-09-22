"use strict";

//*------------------------------------------------------------------*

var React = require('react');
var PropTypes = require('../PropTypes.js');

//*------------------------------------------------------------------*

module.exports = function(displayName){
  return React.createClass({
      propTypes: PropTypes.SelectionDropdown,

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
                  <span>{displayName}</span>
                  <select onChange={this.handleChange} value={this.state.selected}>
                      {this.props.available.map(createOption)}
                  </select>
              </div>
          );
      }
  });
}
