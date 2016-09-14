"use strict";

//*------------------------------------------------------------------*

var React = require('react');
var ReactTooltip = require('react-tooltip');
require('./TooltipStateManager.less');
//*------------------------------------------------------------------*

//We let Highcharts manage the point formatter for now since we're not disappointed.
var TooltipStateManager = React.createClass({
  propTypes:{
    //This callback might come from above and e.g. be for highlighting tissues on anatomogram.
    onUserSelectsRow:React.PropTypes.func,
    onUserSelectsColumn:React.PropTypes.func,
    onUserSelectsPoint: React.PropTypes.func,
    tooltips: React.PropTypes.shape({
      row: React.PropTypes.func,
      column: React.PropTypes.func,
      point: React.PropTypes.func
    }).isRequired,
    managedComponent: React.PropTypes.any.isRequired,
    managedComponentProps: React.PropTypes.object.isRequired
  },
  getInitialState: function(){
    return (
      {
        hoveredRow:"",
        hoveredColumn:"",
        hoveredPoint:""
      }
    )
  },

  _onUserSelectsRow:function(rowLabel){
    this.setState({hoveredRow:rowLabel||""});
    this.props.onUserSelectsRow && this.props.onUserSelectsRow(rowLabel);
  },
  _onUserSelectsColumn:function(columnLabel){
    this.setState({hoveredColumn:columnLabel||""});
    this.props.onUserSelectsColumn && this.props.onUserSelectsColumn(columnLabel);
  },
  _onUserSelectsPoint:function(hoveredPoint){
    this.setState({hoveredPoint:hoveredPoint||""});
    this.props.onUserSelectsPoint && this.props.onUserSelectsPoint(hoveredPoint);
  },

  _tooltipContent: function(){
    return (
      this.state.hoveredRow
      ? this.props.tooltips.row(this.state.hoveredRow)
      : this.state.hoveredColumn
        ? this.props.tooltips.column(this.state.hoveredColumn)
        : this.state.hoveredPoint
          ? this.props.tooltips.point(this.state.hoveredPoint)
          : <div />
    );
  },
  _showTooltip: function(){
    return this.state.hoveredRow || this.state.hoveredColumn /*||this.state.hoveredPoint*/;
  },

  componentDidUpdate: function(){
    this.refs["tooltip"].setState({
      placeholder: this._tooltipContent(),
      extraClass: this._showTooltip()? "gxaTooltip":"gxaDisabled"
    })
  },

  render: function () {
    var ManagedComponent = this.props.managedComponent;
    return (
      <div id="managedComponentWithTooltip">
        <div data-tip data-for='gxaTooltip'>
          <ManagedComponent
            {... Object.assign({},
              this.props.managedComponentProps,
              {onUserSelectsRow : this._onUserSelectsRow,
              onUserSelectsColumn: this._onUserSelectsColumn,
              onUserSelectsPoint: this._onUserSelectsPoint,
              "data-tip": true,
            })}
            />
        </div>
        <ReactTooltip
          ref="tooltip"
          id='gxaTooltip'
          type="light"
          class={"gxaDisabled"}>
          {this._tooltipContent()}
        </ReactTooltip>
      </div>
    )
  }
})

module.exports = TooltipStateManager;
