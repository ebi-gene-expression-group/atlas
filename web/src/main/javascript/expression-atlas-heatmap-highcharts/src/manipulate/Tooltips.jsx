"use strict";
/*
This module returns functions that take in labels of rows/columns,
and return content as it should appear in tooltip.
*/
//*------------------------------------------------------------------*
var React = require('react');

//*------------------------------------------------------------------*

//TODO consider what the interface of this function should be.
module.exports = function(loadResult){
  return {
    row: (rowLabel)=>(<div>{"Row:" + rowLabel}</div>),
    column: (columnLabel)=>(<div>{"Column:" + columnLabel}</div>),
    point: ()=>{}
  }
}
