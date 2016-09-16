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

  _onUserSelectsRow:function(rowLabel){
    this.refs["tooltip"].setState(
      rowLabel
      ? {placeholder: this.props.tooltips.row(rowLabel),
        extraClass:"gxaGlobalTooltipContent"}
      : {extraClass:"gxaDisabled"}
    );
    this.props.onUserSelectsRow && this.props.onUserSelectsRow(rowLabel);
  },
  _onUserSelectsColumn:function(columnLabel){
    this.refs["tooltip"].setState(
      columnLabel
      ? {placeholder: this.props.tooltips.column(columnLabel),
        extraClass:"gxaGlobalTooltipContent"}
      : {extraClass:"gxaDisabled"}
    );
    this.props.onUserSelectsColumn && this.props.onUserSelectsColumn(columnLabel);
  },
  _onUserSelectsPoint:function(hoveredPoint){
    this.refs["tooltip"].setState(
      hoveredPoint
      ? {placeholder: this.props.tooltips.point(hoveredPoint),
        extraClass:"gxaGlobalTooltipContent"}
      : {extraClass:"gxaDisabled"}
    );
    this.props.onUserSelectsPoint && this.props.onUserSelectsPoint(hoveredPoint);
  },


  render: function () {
    var ManagedComponent = this.props.managedComponent;
    return (
      <div>
        <div data-tip data-for='gxaGlobalTooltipOverManagedComponent'>
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
          id='gxaGlobalTooltipOverManagedComponent'
          type="light"
          class={"gxaDisabled"}>
          <div/>
        </ReactTooltip>
      </div>
    )
  }
})

module.exports = TooltipStateManager;
