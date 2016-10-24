"use strict";
/*
This class is a wrapper around react-tooltip that lets us have one big tooltip.
The tooltip gets hidden when you hover off, and changes content when you change what you hover on.
Hack 1:
I wanted the component to store the tooltip content, and then pass it as a child to react-tooltip.
This didn't really work because I also need to put display:none on the tooltip when we hover off, not just an empty white tooltip.
Hack 2:
Additionally we use the freeze feature of react-tooltip- passed inside through props- and want to unfreeze after click outside.
But that unfreeze also fired on clicking the column that turned freeze on in the first place, hence counting clicks.
*/
//*------------------------------------------------------------------*
var React = require('react');
var ReactTooltip = require('react-tooltip');
require('./TooltipStateManager.less');
//*------------------------------------------------------------------*

var TooltipStateManager = React.createClass({
  propTypes:{
    onUserSelectsRow: React.PropTypes.func.isRequired,
    onUserSelectsColumn: React.PropTypes.func.isRequired,
    onUserSelectsPoint: React.PropTypes.func.isRequired,
    tooltips: React.PropTypes.shape({
      row: React.PropTypes.func,
      column: React.PropTypes.func,
      point: React.PropTypes.func
    }).isRequired,
    managedComponent: React.PropTypes.any.isRequired,
    managedComponentProps: React.PropTypes.object.isRequired,
    enableFreeze: React.PropTypes.bool.isRequired
  },

  getInitialState: function(){
    return {
      tooltipFrozen: false,
      clickCount: 0
    }
  },

  _onUserSelectsRow:function(rowLabel){
    if(this.state.tooltipFrozen) return ;
    this.refs["tooltip"].setState(
      rowLabel
      ? {placeholder: this.props.tooltips.row(rowLabel),
        place:"right",
        extraClass:"gxaGlobalTooltipContent"}
      : {extraClass:"gxaDisabled"}
    );
    this.props.onUserSelectsRow(rowLabel);
  },
  _onUserSelectsColumn:function(columnLabel){
    if(this.state.tooltipFrozen) return;
    this.refs["tooltip"].setState(
      columnLabel
      ? {placeholder: this.props.tooltips.column(columnLabel),
        place:"bottom",
        extraClass:"gxaGlobalTooltipContent"}
      : {extraClass:"gxaDisabled"}
    );
    this.props.onUserSelectsColumn(columnLabel);
  },
  _onUserSelectsPoint:function(__args__){
    /*
    //We let Highcharts manage the point formatter for now since we're not disappointed.
    this.refs["tooltip"].setState(
      hoveredPoint
      ? {placeholder: this.props.tooltips.point(hoveredPoint),
        extraClass:"gxaGlobalTooltipContent"}
      : {extraClass:"gxaDisabled"}
    );
    */
    this.props.onUserSelectsPoint.apply({},arguments);
  },

  _dismissTooltip: function(){
    if(this.state.clickCount>0){
      this.setState({tooltipFrozen:false, clickCount:0})
      this.refs["tooltip"].setState({extraClass:"gxaDisabled"})
    }
    this.setState((previousState)=>({clickCount: previousState.clickCount+1}))
  },

  _onUserClicksColumn: function(columnLabel){
    if(this.props.enableFreeze){
      this.setState((previousState)=>({tooltipFrozen: !previousState.tooltipFrozen, clickCount: 0}))
    }
    this._onUserSelectsColumn(columnLabel);
  },

  render: function () {
    var ManagedComponent = this.props.managedComponent;
    return (
      <div>
        <div
          data-tip
          data-for='gxaGlobalTooltipOverManagedComponent'
          {...this.state.tooltipFrozen
              ? {
                  className: "gxaFadeBackgroundForOpenTooltip"
                }
              : {}
            }>
          <ManagedComponent
            {... Object.assign({},
              this.props.managedComponentProps,
              {onUserSelectsRow : this._onUserSelectsRow,
              onUserSelectsColumn: this._onUserSelectsColumn,
              onUserSelectsPoint: this._onUserSelectsPoint,
              onUserClicksColumn: this._onUserClicksColumn,
              "data-tip": true,
            })}
            />
        </div>
        <ReactTooltip
          ref="tooltip"
          id='gxaGlobalTooltipOverManagedComponent'
          type="light"
          frozen={!!this.state.tooltipFrozen}
          onClickOutside={this.props.enableFreeze? this._dismissTooltip : undefined}
          class={"gxaDisabled"}>
          <div/>
        </ReactTooltip>
      </div>
    )
  }
})

module.exports = TooltipStateManager;
