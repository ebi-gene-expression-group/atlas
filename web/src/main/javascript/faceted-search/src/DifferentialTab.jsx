"use strict";

//*------------------------------------------------------------------*
var React = require('react');

var Results= require('./DifferentialResults.jsx');
var Facets = require('./DifferentialFacetsTree.jsx');


//*------------------------------------------------------------------*

var DifferentialTab = React.createClass({
  propTypes:{
    facets: React.PropTypes.object.isRequired,
    results: React.PropTypes.object.isRequired
  },

  render: function(){
    return (
      <div>
        <div className="grid_6 alpha" id="gxaDifferentialFacetsContainerDiv">
          <Facets {...this.props.facets}/>
        </div>
        <div className="grid_18 omega" id="gxaDifferentialResultsContainerDiv">
          <Results {...this.props.results}/>
        </div>
      </div>
    )
  }
});

module.exports = DifferentialTab;
