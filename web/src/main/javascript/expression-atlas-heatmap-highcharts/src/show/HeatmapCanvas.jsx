"use strict";

//*------------------------------------------------------------------*

var React = require('react');
var ReactHighcharts = require('react-highcharts');
var Highcharts = ReactHighcharts.Highcharts;
require('highcharts-heatmap')(Highcharts);
require('../lib/highcharts-custom-events.js')(Highcharts);
var hash = require('object-hash');

var PropTypes = require('../PropTypes.js');

//*------------------------------------------------------------------*

var HeatmapCanvas = React.createClass({
  propTypes: {
      marginRight: React.PropTypes.number.isRequired,
      ontologyIdsToHighlight: React.PropTypes.arrayOf(React.PropTypes.string).isRequired,
      heatmapData: PropTypes.HeatmapData,
      colorAxis: React.PropTypes.object,
      onHeatmapRedrawn: React.PropTypes.func.isRequired,
      formatters : React.PropTypes.shape({
        xAxis: PropTypes.Formatter,
        yAxis: PropTypes.Formatter,
        tooltip: PropTypes.Formatter
      }).isRequired,
      genomeBrowserTemplate: React.PropTypes.string.isRequired,
      onUserSelectsRow:React.PropTypes.func.isRequired,
      onUserSelectsColumn:React.PropTypes.func.isRequired,
      onUserSelectsPoint:React.PropTypes.func.isRequired
  },

  shouldComponentUpdate: function(nextProps){
    return hash.MD5(nextProps.heatmapData)!==hash.MD5(this.props.heatmapData);
  },
  componentDidUpdate: function () {
      this.props.onHeatmapRedrawn();
  },

  componentWillReceiveProps: function(nextProps){
    var chart = this.refs.chart.getChart();
    var forEachXNotInYsEmit = function(xs, ys, eventName){
      xs
      .filter(function(id){
        return ys.indexOf(id)==-1;
      })
      .filter(function uniq(id,ix,self){
        return ix==self.indexOf(id);
      })
      .forEach(function(id){
        Highcharts.fireEvent(chart, eventName, {svgPathId: id});
      }.bind(this));
    };
    forEachXNotInYsEmit(nextProps.ontologyIdsToHighlight, this.props.ontologyIdsToHighlight,'handleGxaAnatomogramTissueMouseEnter');
    forEachXNotInYsEmit(this.props.ontologyIdsToHighlight, nextProps.ontologyIdsToHighlight,'handleGxaAnatomogramTissueMouseLeave');
  },

  render: function(){
    var xAxisLongestHeaderLength =
      Math.max.apply(null, this.props.heatmapData.xAxisCategories.map(function(category) {return category.label.length}));
    var marginTop =
      this.props.heatmapData.xAxisCategories.length < 10 ? 30 :   // labels aren’t tilted
          this.props.heatmapData.xAxisCategories.length < 50 ? Math.min(150, Math.round(xAxisLongestHeaderLength * 3.75)) : // labels at -45°
              Math.min(250, Math.round(xAxisLongestHeaderLength * 5.5));  // labels at -90°

    var dimensions= {
      marginTop:marginTop,
      marginRight: //leave space for tilted long headers
      //TODO the marginRight value of props used to be the same here and in top legend.
      //Probably it's time to get rid of this prop.
        this.props.marginRight*(1+10/Math.pow(1+this._countColumnsToShow(),2)),
      height:
        Math.max(70, this._countRowsToShow() * 30 + marginTop)
    }

    var maxWidthFraction = 1-Math.exp(-(0.2+0.05*Math.pow(this._countColumnsToShow()+1,2)));
    return (
        <div style={{maxWidth:maxWidthFraction*100+"%"}}>
        <ReactHighcharts
          config={this._highchartsOptions(dimensions, this.props.heatmapData)}
          ref="chart"/>
        </div>
    );
  },
  _count_sToShow: function(xOrY){
    return (
      [].concat.apply([], this.props.heatmapData.dataSeries.map((el)=>el.data))
      .map((el)=>el[xOrY])
      .sort((l,r)=>l-r)
      .filter((el, ix, self)=>self.indexOf(el) == ix)
      .length
    );
  },

  _countRowsToShow: function(){
    return this._count_sToShow("y");
  },

  _countColumnsToShow: function(){
    return this._count_sToShow("x");
  },

  _highchartsOptions: function(dimensions, data){
    return (
      {
          plotOptions: {
              heatmap: {
                  turboThreshold: 0
              },
              series: {
                  cursor: !!this.props.genomeBrowserTemplate ? "pointer" :undefined,
                  point: {
                      events: {
                          mouseOver: function() {
                              this.series.chart.userOptions.onUserSelectsPoint(this.series.xAxis.categories[this.x].id,this.series.yAxis.categories[this.y].id);
                          },
                          mouseOut: function () {
                            this.series.chart.userOptions.onUserSelectsPoint("","");
                          },
                          click: !this.props.genomeBrowserTemplate? function(){}: function(){
                            var x = this.series.xAxis.categories[this.x].info.trackId;
                            var y = this.series.yAxis.categories[this.y].info.trackId;
                            window.open(this.series.chart.userOptions.genomeBrowserTemplate.replace(/__x__/g,x).replace(/__y__/g,y),"_blank");
                          }
                      }
                  },

                  states: {
                      hover: {
                          color: '#eeec38' //#edab12 color cell on mouse over
                      },
                      select: {
                          color: '#eeec38'
                      }
                  }
              }
          },
          credits: {
              enabled: false //remove Highcharts text in the bottom right corner
          },
          chart: Object.assign({
              type: 'heatmap',
              spacingTop: 0,
              plotBorderWidth: 1,
              zoomType: 'x',
              events: {
                  handleGxaAnatomogramTissueMouseEnter: function(e) {
                      Highcharts.each(this.series, function (series) {
                          Highcharts.each(series.points, function (point) {
                              if (point.series.xAxis.categories[point.x].id === e.svgPathId) {
                                  point.select(true, true);
                              }
                          });
                      });
                  },
                  handleGxaAnatomogramTissueMouseLeave: function(e) {
                      var points = this.getSelectedPoints();
                      if (points.length > 0) {
                          Highcharts.each(points, function (point) {
                              point.select(false);
                          });
                      }
                  }
              }
          },dimensions),
          legend: {
              enabled: false
          },
          title: null,
          colorAxis: this.props.colorAxis || undefined,
          xAxis: { //assays
              tickLength: 5,
              tickColor: 'rgb(192, 192, 192)',
              lineColor: 'rgb(192, 192, 192)',
              labels: {
                  style: {
                      fontSize: '9px',
                      textOverflow: 'ellipsis'
                  },
                  events: {
                    mouseover: (function() { var f =this.props.onUserSelectsColumn; return function(){return f(this.value);};}.bind(this))()
                    ,
                    mouseout:(function() { var f =this.props.onUserSelectsColumn; return function(){return f("");};}.bind(this))()
                  },
                  autoRotation: [-45, -90],
                  formatter: (function() { var f =this.props.formatters.xAxis; return function(){return f(this.value);};}.bind(this))()
              },

              opposite: 'true',
              categories: data.xAxisCategories
          },
          yAxis: { //experiments or bioentities
              useHTML: true,
              reversed: true,
              labels: {
                  style: {
                      fontSize: '10px',
                      color: '#148ff3'
                  },
                  events: {
                    mouseover:(function() {
                       var f =this.props.onUserSelectsRow;
                       return function(){
                         return f( //We assume the longest text is the callback we want
                           [].concat.apply([],this.element.children)
                           .map((c)=>c.textContent)
                           .reduce((l,r)=>(l.length>r.length?l:r), "")
                         )
                       };
                     }.bind(this))(),
                    mouseout: (function() { var f =this.props.onUserSelectsRow; return function(){return f("");};}.bind(this))()
                  },
                  formatter: (function() { var f =this.props.formatters.yAxis; return function(){return f(this.value);};}.bind(this))()
              },

              categories: data.yAxisCategories,
              title: null,
              gridLineWidth: 0,
              minorGridLineWidth: 0,
              endOnTick: false
          },
          tooltip: {
              useHTML: true,
              formatter: (function() { var f =this.props.formatters.tooltip; return function(){return f(this.series,this.point);};}.bind(this))()
          },
          /*
          TODO we can't access these in custom events like mouseover on row/column
          because the "this" is not the Highcharts this but the "DOM API" this.
          */
          onUserSelectsRow: this.props.onUserSelectsRow,
          onUserSelectsColumn: this.props.onUserSelectsColumn,
          onUserSelectsPoint: this.props.onUserSelectsPoint,

          genomeBrowserTemplate: this.props.genomeBrowserTemplate,
          series: data.dataSeries.map(function(e){
              return {
                name: e.info.name,
                color: e.info.colour,
                borderWidth: data.xAxisCategories.length > 200 ? 0 :1 ,
                borderColor: "white",
                data: e.data
              }
            })
      }
    );
  }
});

module.exports = HeatmapCanvas;
