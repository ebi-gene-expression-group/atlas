var highchartsAtlasHeatmap =
webpackJsonp_name_([4],{

/***/ 0:
/*!*************************************!*\
  !*** ./heatmap-highcharts/index.js ***!
  \*************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	module.exports = __webpack_require__(/*! ./src/highcharts-heatmap.js */ 1125);


/***/ },

/***/ 1125:
/*!******************************************************!*\
  !*** ./heatmap-highcharts/src/highcharts-heatmap.js ***!
  \******************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	var React = __webpack_require__(/*! react */ 167);
	var ReactDOM = __webpack_require__(/*! react-dom */ 323);
	
	var $ = __webpack_require__(/*! jquery */ 569);
	var jQuery = $;
	
	//*------------------------------------------------------------------*
	
	var HighchartsHeatmapContainer = __webpack_require__(/*! ./highcharts-heatmap-container.jsx */ 1126);
	
	//*------------------------------------------------------------------*
	
	/**
	 * @param {Object}      options
	 * @param {string}          options.proxyPrefix - Proxy URL with protocol: required by CTTV
	 * @param {boolean=}        options.disableGoogleAnalytics - Disable Google Analytics: required by CTTV
	 * @param {string=}         options.atlasHost - Atlas host with port (note: don’t include port)
	 * @param {string}          options.params
	 * @param {boolean}         options.analyticsSearch
	 * @param {boolean=}        options.isMultiExperiment
	 * @param {boolean=}        options.showAnatomogram
	 * @param {boolean=}        options.isWidget
	 * @param {string | Object} options.target - a <div> id or a DOM element, as returned by ReactDOM.findDOMNode()
	 * @param {function}        options.fail - Callback to run if the AJAX request to the server fails. (jqXHR, textStatus)
	 */
	module.exports = function(options) {
	
	    var protocol = window.location.protocol + "//",
	        atlasHost = options.atlasHost === undefined ? "www.ebi.ac.uk" : options.atlasHost,
	        atlasPath = "/gxa";
	
	    var linksAtlasBaseURL = protocol + atlasHost + atlasPath,
	        atlasBaseURL = options.proxyPrefix ? options.proxyPrefix + "/" + atlasHost + atlasPath : linksAtlasBaseURL;
	
	    var endpointPath = "/widgets/heatmap/baselineAnalytics";
	
	    var sourceURL = atlasBaseURL + endpointPath + "?" + options.params;
	
	    ReactDOM.render(
	        React.createElement(
	            HighchartsHeatmapContainer,
	            {
	                sourceURL: sourceURL,
	                atlasBaseURL: atlasBaseURL,
	                linksAtlasBaseURL: linksAtlasBaseURL,
	                isWidget: options.isWidget === undefined ? true : options.isWidget,
	                disableGoogleAnalytics: options.disableGoogleAnalytics === undefined ? false : options.disableGoogleAnalytics,
	                fail: options.fail
	            }
	        ),
	        (typeof options.target === "string") ? document.getElementById(options.target) : options.target
	    );
	};


/***/ },

/***/ 1126:
/*!*****************************************************************!*\
  !*** ./heatmap-highcharts/src/highcharts-heatmap-container.jsx ***!
  \*****************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	var React = __webpack_require__(/*! react */ 167);
	
	var $ = __webpack_require__(/*! jquery */ 569);
	var jQuery = $;
	
	//*------------------------------------------------------------------*
	
	var HighchartsHeatmap = __webpack_require__(/*! ./highchartsHeatmap.jsx */ 1127);
	var HighchartsUtils = __webpack_require__(/*! ./highcharts-utils.js */ 1133);
	
	//*------------------------------------------------------------------*
	
	var ExperimentDescription = React.createClass({
	    displayName: 'ExperimentDescription',
	
	    propTypes: {
	        linksAtlasBaseURL: React.PropTypes.string.isRequired,
	        experiment: React.PropTypes.shape({
	            URL: React.PropTypes.string.isRequired,
	            description: React.PropTypes.string.isRequired,
	            allSpecies: React.PropTypes.string.isRequired
	        }).isRequired
	    },
	
	    render: function () {
	
	        var experimentURL = this.props.linksAtlasBaseURL + this.props.experiment.URL;
	
	        return React.createElement(
	            'div',
	            { style: { width: "100%", paddingBottom: "20px" } },
	            React.createElement(
	                'div',
	                { id: 'experimentDescription' },
	                React.createElement(
	                    'a',
	                    { id: 'goto-experiment', className: 'gxaThickLink', title: 'Experiment Page', href: experimentURL },
	                    this.props.experiment.description
	                )
	            ),
	            React.createElement(
	                'div',
	                { id: 'experimentOrganisms' },
	                'Organism(s): ',
	                React.createElement(
	                    'span',
	                    { style: { "fontStyle": "italic" } },
	                    this.props.experiment.allSpecies
	                )
	            )
	        );
	    }
	
	});
	
	var HighchartsHeatmapContainer = React.createClass({
	    displayName: 'HighchartsHeatmapContainer',
	
	    propTypes: {
	        sourceURL: React.PropTypes.string.isRequired,
	        atlasBaseURL: React.PropTypes.string.isRequired,
	        linksAtlasBaseURL: React.PropTypes.string.isRequired,
	        isWidget: React.PropTypes.bool.isRequired,
	        disableGoogleAnalytics: React.PropTypes.bool.isRequired,
	        fail: React.PropTypes.func
	    },
	
	    render: function () {
	
	        var geneURL = this.props.linksAtlasBaseURL + "/query" + "?geneQuery=" + this.state.heatmapConfig.geneQuery + "&exactMatch=" + this.state.heatmapConfig.isExactMatch + "&organism=" + this.state.heatmapConfig.species;
	
	        return React.createElement(
	            'div',
	            { ref: 'this', className: 'gxaBlock' },
	            this.state.experimentData ? React.createElement(ExperimentDescription, { experiment: this.state.experimentData, linksAtlasBaseURL: this.props.linksAtlasBaseURL }) : null,
	            this.state.heatmapConfig ? React.createElement(
	                'div',
	                { id: 'heatmap-anatomogram', className: 'gxaHeatmapAnatomogramRow' },
	                React.createElement(
	                    'div',
	                    { id: 'heatmap-react', className: 'gxaInnerHeatmap' },
	                    React.createElement(HighchartsHeatmap, {
	                        profiles: this.state.profiles,
	                        atlasBaseURL: this.props.atlasBaseURL,
	                        xAxisCategories: this.state.xAxisCategories,
	                        yAxisCategories: this.state.yAxisCategories,
	                        yAxisCategoriesLinks: this.state.yAxisCategoriesLinks,
	                        seriesDataNA: this.state.seriesDataNA,
	                        seriesDataNAString: this.state.seriesDataNAString,
	                        seriesDataBelowCutoff: this.state.seriesDataBelowCutoff,
	                        seriesDataBelowCutoffString: this.state.seriesDataBelowCutoffString,
	                        seriesDataRanges: this.state.seriesDataRanges
	                    })
	                )
	            ) : React.createElement(
	                'div',
	                { ref: 'loadingImagePlaceholder' },
	                React.createElement('img', { src: this.props.atlasBaseURL + "/resources/images/loading.gif" })
	            ),
	            this.props.isWidget ? React.createElement(
	                'div',
	                null,
	                React.createElement(
	                    'p',
	                    null,
	                    React.createElement(
	                        'a',
	                        { href: geneURL },
	                        'See more expression data at Expression Atlas.'
	                    ),
	                    React.createElement('br', null),
	                    'This expression view is provided by ',
	                    React.createElement(
	                        'a',
	                        { href: this.props.linksAtlasBaseURL },
	                        'Expression Atlas'
	                    ),
	                    '.',
	                    React.createElement('br', null),
	                    'Please direct any queries or feedback to ',
	                    React.createElement(
	                        'a',
	                        { href: 'mailto:arrayexpress-atlas@ebi.ac.uk' },
	                        'arrayexpress-atlas@ebi.ac.uk'
	                    )
	                )
	            ) : null
	        );
	    },
	
	    getInitialState: function () {
	        return {
	            heatmapConfig: '',
	            profiles: {
	                rows: [],
	                minExpressionLevel: 0,
	                maxExpressionLevel: 0
	            },
	
	            xAxisCategories: {},
	            yAxisCategories: {},
	            yAxisCategoriesLinks: {},
	
	            seriesDataNA: [],
	            seriesDataNAString: "NA",
	            seriesDataBelowCutoff: [],
	            seriesDataBelowCutoffString: "Below cutoff",
	
	            seriesDataRanges: [{
	                label: "Low",
	                from: 0,
	                to: 0.25,
	                seriesData: []
	            }, {
	                label: "Medium",
	                from: 0.25,
	                to: 0.75,
	                seriesData: []
	            }, {
	                label: "High",
	                from: 0.75,
	                to: 1.0,
	                seriesData: []
	            }]
	        };
	    },
	
	    componentDidMount: function () {
	        var httpRequest = {
	            url: this.props.sourceURL,
	            dataType: "json",
	            method: "GET"
	        };
	
	        $.ajax(httpRequest).done(function (data) {
	            if (this.isMounted()) {
	
	                var xAxisCategories = HighchartsUtils.getXAxisCategories(data.columnHeaders);
	                var yAxisCategories = HighchartsUtils.getYAxisCategories(data.profiles, data.config);
	                var yAxisCategoriesLinks = HighchartsUtils.getYAxisCategoriesLinks();
	
	                var seriesDataNA = [],
	                    seriesDataNAString = "NA";
	
	                var seriesDataBelowCutoff = [],
	                    seriesDataBelowCutoffString = "Below cutoff";
	
	                var seriesDataRanges = [{
	                    label: "Low",
	                    from: 0,
	                    to: 0.25,
	                    seriesData: []
	                }, {
	                    label: "Medium",
	                    from: 0.25,
	                    to: 0.75,
	                    seriesData: []
	                }, {
	                    label: "High",
	                    from: 0.75,
	                    to: 1.0,
	                    seriesData: []
	                }];
	
	                var experimentTypes = [];
	
	                for (var i = 0; i < data.profiles.rows.length; i++) {
	                    if (experimentTypes.indexOf(data.profiles.rows[i].experimentType) === -1) {
	                        experimentTypes.push(data.profiles.rows[i].experimentType);
	                    }
	                }
	
	                for (var i = 0; i < experimentTypes.length; i++) {
	                    var experimentTypeSeriesData = [];
	
	                    for (var j = 0; j < yAxisCategories.length; j++) {
	                        if (data.profiles.rows[j].experimentType !== experimentTypes[i]) {
	                            continue;
	                        }
	
	                        for (var k = 0; k < xAxisCategories.length; k++) {
	                            var literalValue = data.profiles.rows[j].expressions[k].value;
	
	                            if (literalValue === "") {
	                                seriesDataBelowCutoff.push([k, j, seriesDataBelowCutoffString]);
	                            } else if (literalValue === "NT") {
	                                seriesDataNA.push([k, j, seriesDataNAString]);
	                            } else {
	                                var value = parseFloat(literalValue);
	                                if (!isNaN(value)) {
	                                    experimentTypeSeriesData.push([k, j, value]);
	                                }
	                            }
	                        }
	                    }
	
	                    experimentTypeSeriesData.sort(function (a, b) {
	                        return a[2] - b[2];
	                    });
	
	                    var experimentTypeMax = experimentTypeSeriesData[experimentTypeSeriesData.length - 1][2];
	
	                    for (var k = 0; k < seriesDataRanges.length; k++) {
	                        //seriesDataRanges[k].seriesData.concat(
	                        experimentTypeSeriesData.filter(function (datum) {
	                            return datum[2] > seriesDataRanges[k].from * experimentTypeMax && datum[2] <= seriesDataRanges[k].to * experimentTypeMax;
	                        }).forEach(function (filteredDatum) {
	                            seriesDataRanges[k].seriesData.push(filteredDatum);
	                        });
	                    }
	                }
	
	                this.setState({
	                    heatmapConfig: data.config,
	                    columnHeaders: data.columnHeaders,
	                    nonExpressedColumnHeaders: data.nonExpressedColumnHeaders,
	                    profiles: data.profiles,
	                    geneSetProfiles: data.geneSetProfiles,
	                    experimentData: data.experiment,
	
	                    xAxisCategories: xAxisCategories,
	                    yAxisCategories: yAxisCategories,
	                    yAxisCategoriesLinks: yAxisCategoriesLinks,
	
	                    seriesDataBelowCutoff: seriesDataBelowCutoff,
	                    seriesDataBelowCutoffString: seriesDataBelowCutoffString,
	                    seriesDataNA: seriesDataNA,
	                    seriesDataNAString: seriesDataNAString,
	                    seriesDataRanges: seriesDataRanges
	
	                });
	            }
	        }.bind(this)).fail(function (jqXHR, textStatus, errorThrown) {
	            if (this.props.fail) {
	                this.props.fail(jqXHR, textStatus, errorThrown);
	            } else if (textStatus === "parsererror") {
	                $(this.refs.this.getDOMNode()).html("<div class='gxaError'>Could not parse JSON response</div>");
	            } else {
	                $(this.refs.this.getDOMNode()).html(jqXHR.responseText);
	            }
	        }.bind(this));
	
	        if (!this.props.disableGoogleAnalytics) {
	            var _gaq = _gaq || [];
	            _gaq.push(["_setAccount", "UA-37676851-1"]);
	            _gaq.push(["_trackPageview"]);
	            (function () {
	                var ga = document.createElement("script");
	                ga.type = "text/javascript";
	                ga.async = true;
	                ga.src = ("https:" == document.location.protocol ? "https://ssl" : "http://www") + ".google-analytics.com/ga.js";
	                var s = document.getElementsByTagName("script")[0];
	                s.parentNode.insertBefore(ga, s);
	            })();
	        }
	    }
	
	});
	
	//*------------------------------------------------------------------*
	
	//componentDidUpdate: function() {
	//    // This mounted component is only going to be updated when changing this.props.showAnatomogram, so we only take
	//    // care of the anatomogram, the legend and the sticky header (the last two through an event)
	//    var $anatomogram = $(this.refs.anatomogramEnsembl.getDOMNode());
	//
	//    if (this.props.showAnatomogram) {
	//        $anatomogram.hcSticky({responsive: true});
	//    }
	//
	//    $(window).trigger("gxaResizeHeatmapAnatomogramHeader");
	//}
	module.exports = HighchartsHeatmapContainer;

/***/ },

/***/ 1127:
/*!******************************************************!*\
  !*** ./heatmap-highcharts/src/highchartsHeatmap.jsx ***!
  \******************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	var React = __webpack_require__(/*! react */ 167);
	
	var $ = __webpack_require__(/*! jquery */ 569);
	var jQuery = $;
	
	var Highcharts = __webpack_require__(/*! react-highcharts */ 586);
	__webpack_require__(/*! highcharts-heatmap */ 1128)(Highcharts.Highcharts);
	
	//*------------------------------------------------------------------*
	
	__webpack_require__(/*! ../css/heatmap-highcharts.css */ 1129);
	
	//*------------------------------------------------------------------*
	
	var HighchartsHeatmap = React.createClass({
	    displayName: 'HighchartsHeatmap',
	
	
	    propTypes: {
	        profiles: React.PropTypes.object.isRequired,
	        atlasBaseURL: React.PropTypes.string.isRequired
	
	    },
	
	    getInitialState: function () {
	        return {
	            xAxisCategories: {},
	            yAxisCategories: {},
	            yAxisCategoriesLinks: {},
	            seriesDataNA: [],
	            seriesDataNAString: "NA",
	            seriesDataBelowCutoff: [],
	            seriesDataBelowCutoffString: "Below cutoff",
	            seriesDataRanges: [{
	                label: "Low",
	                from: 0,
	                to: 0.25,
	                seriesData: []
	            }, {
	                label: "Medium",
	                from: 0.25,
	                to: 0.75,
	                seriesData: []
	            }, {
	                label: "High",
	                from: 0.75,
	                to: 1.0,
	                seriesData: []
	            }],
	
	            legend_1: false,
	            legend_2: false,
	            legend_3: false,
	            legend_4: false
	
	        };
	    },
	
	    handleClick: function (index) {
	        if (index == 1) {
	            this.setState({ legend_1: !this.state.legend_1 });
	        } else if (index == 2) {
	            this.setState({ legend_2: !this.state.legend_2 });
	        } else if (index == 3) {
	            this.setState({ legend_3: !this.state.legend_3 });
	        } else if (index == 4) {
	            this.setState({ legend_4: !this.state.legend_4 });
	        }
	    },
	
	    componentDidMount: function () {
	        var heatmap = this.refs.chart.getChart();
	        heatmap.series[0].hide();
	    },
	
	    componentDidUpdate: function () {
	        var heatmap = this.refs.chart.getChart();
	        heatmap.series[0].hide();
	
	        this.state.legend_1 ? heatmap.series[1].hide() : heatmap.series[1].show();
	        this.state.legend_2 ? heatmap.series[2].hide() : heatmap.series[2].show();
	        this.state.legend_3 ? heatmap.series[3].hide() : heatmap.series[3].show();
	        this.state.legend_4 ? heatmap.series[4].hide() : heatmap.series[4].show();
	    },
	
	    render: function () {
	        var atlasBaseURL = this.props.atlasBaseURL;
	        var yAxisCategoriesLinks = this.props.yAxisCategoriesLinks;
	        var yAxisCategories = this.props.yAxisCategories;
	
	        var highchartsOptions = {
	            plotOptions: {
	                series: {
	                    states: {
	                        hover: {
	                            color: '#eeec38' //#edab12 color cell on mouse over
	                        }
	                    }
	                }
	            },
	            credits: {
	                enabled: false //remove highchart text in the bottom right corner
	            },
	            chart: {
	                type: 'heatmap',
	                marginTop: 82, //labels
	                marginRight: 36, //leave space for the export button to appear
	                plotBorderWidth: 1,
	                //height: this.props.profiles.rows.length * 4+200,
	                height: yAxisCategories.length * 12 + 200,
	                zoomType: 'xy'
	            },
	            legend: {
	                useHTML: true,
	                enabled: false,
	                itemDistance: 18,
	                symbolWidth: 12,
	                symbolHeight: 12,
	                x: 150,
	                align: 'left',
	                verticalAlign: 'bottom',
	                layout: 'horizontal',
	                itemStyle: {
	                    lineHeight: "10px",
	                    fontSize: "10px",
	                    color: '#606060',
	                    fontWeight: 'normal'
	                }
	            },
	            title: null,
	            xAxis: { //tissues
	                tickLength: 5,
	                tickColor: "rgb(192, 192, 192)",
	                lineColor: "rgb(192, 192, 192)",
	                labels: {
	                    y: -6,
	                    style: {
	                        fontSize: "9px"
	                    },
	                    autoRotation: [-45, -90]
	                },
	                opposite: 'true',
	                categories: this.props.xAxisCategories
	            },
	            yAxis: { //experiments
	                useHTML: true,
	                reversed: true,
	                labels: {
	                    style: {
	                        fontSize: "10px",
	                        color: "#148ff3"
	                    },
	                    formatter: function () {
	                        return '<a class="project_link"  href="' + atlasBaseURL + '/experiments/' + yAxisCategoriesLinks[this.value] + '">' + this.value + '</a>';
	                    }
	                },
	                categories: this.props.yAxisCategories,
	                title: null,
	                gridLineWidth: 0,
	                minorGridLineWidth: 0
	            },
	            tooltip: {
	                useHTML: true,
	                formatter: function () {
	                    return 'Sample name: <b>' + this.series.yAxis.categories[this.point.y] + '</b>  <br> Tissue: <b>' + this.series.xAxis.categories[this.point.x] + '</b><br><b>' + '</b>' + '<span style="border:1px rgb(192, 192, 192) solid; margin-right: 2px; width:6px; height:6px; display:inline-block; background-color:' + this.point.color + ';">' + '</span> Expression level: <b></span>' + 'Expression level: <b>' + this.point.value + '</b>';
	                }
	            },
	            series: [{
	                name: this.props.seriesDataNAString,
	                color: "#f7f7f7",
	                borderWidth: 1,
	                borderColor: "#fff",
	                data: this.props.seriesDataNA
	            }, {
	                name: this.props.seriesDataBelowCutoffString,
	                color: "#eaeaea",
	                borderWidth: 1,
	                borderColor: "#fff",
	                data: this.props.seriesDataBelowCutoff
	            }, {
	                name: this.props.seriesDataRanges[0].label,
	                color: "#dff8ff",
	                borderWidth: 1,
	                borderColor: "#fff",
	                data: this.props.seriesDataRanges[0].seriesData
	            }, {
	                name: this.props.seriesDataRanges[1].label,
	                color: "#9fd2fa",
	                borderWidth: 1,
	                borderColor: "#fff",
	                data: this.props.seriesDataRanges[1].seriesData
	            }, {
	                name: this.props.seriesDataRanges[2].label,
	                color: "#45affd",
	                borderWidth: 1,
	                borderColor: "#fff",
	                data: this.props.seriesDataRanges[2].seriesData
	            }]
	        };
	
	        var clsName_1 = this.state.legend_1 ? 'legend-item legend-item-off' : 'legend-item';
	        var clsName_2 = this.state.legend_2 ? 'legend-item legend-item-off' : 'legend-item';
	        var clsName_3 = this.state.legend_3 ? 'legend-item legend-item-off' : 'legend-item';
	        var clsName_4 = this.state.legend_4 ? 'legend-item legend-item-off' : 'legend-item';
	
	        var barcharts_legend = React.createElement(
	            'div',
	            { id: 'barcharts_legend_list_items', ref: 'barcharts_legend_items' },
	            React.createElement(
	                'span',
	                null,
	                'Click to interact:'
	            ),
	            React.createElement(
	                'div',
	                { id: 'legend_1', ref: 'legend_1', className: clsName_1, onClick: this.handleClick.bind(this, 1) },
	                React.createElement('div', { className: 'legend-rectangle col_below' }),
	                React.createElement(
	                    'span',
	                    null,
	                    'Below cutoff'
	                )
	            ),
	            React.createElement(
	                'div',
	                { id: 'legend_2', className: clsName_2, onClick: this.handleClick.bind(this, 2) },
	                React.createElement('div', { className: 'legend-rectangle col_low' }),
	                React.createElement(
	                    'span',
	                    null,
	                    'Low'
	                )
	            ),
	            React.createElement(
	                'div',
	                { id: 'legend_3', className: clsName_3, onClick: this.handleClick.bind(this, 3) },
	                React.createElement('div', { className: 'legend-rectangle col_med' }),
	                React.createElement(
	                    'span',
	                    null,
	                    'Medium'
	                )
	            ),
	            React.createElement(
	                'div',
	                { id: 'legend_4', className: clsName_4, onClick: this.handleClick.bind(this, 4) },
	                React.createElement('div', { className: 'legend-rectangle col_high' }),
	                React.createElement(
	                    'span',
	                    null,
	                    'High'
	                )
	            ),
	            React.createElement(
	                'div',
	                { id: 'legend_5', className: 'legend-item special' },
	                React.createElement('div', { className: 'legend-rectangle col_nd' }),
	                React.createElement(
	                    'span',
	                    null,
	                    'No data available'
	                )
	            )
	        );
	
	        return React.createElement(
	            'div',
	            null,
	            React.createElement(
	                'div',
	                { id: 'highcharts_container' },
	                React.createElement(Highcharts, { config: highchartsOptions, ref: 'chart' }),
	                barcharts_legend
	            )
	        );
	    }
	
	});
	
	//*------------------------------------------------------------------*
	
	module.exports = HighchartsHeatmap;

/***/ },

/***/ 1128:
/*!*****************************************!*\
  !*** ./~/highcharts-heatmap/heatmap.js ***!
  \*****************************************/
/***/ function(module, exports) {

	/*
	 Highcharts JS v4.2.3 (2016-02-08)
	
	 (c) 2011-2016 Torstein Honsi
	
	 License: www.highcharts.com/license
	*/
	(function(d){typeof module==="object"&&module.exports?module.exports=d:d(Highcharts)})(function(d){var m=d.Axis,q=d.Chart,i=d.Color,x=d.Legend,s=d.LegendSymbolMixin,t=d.Series,y=d.Point,u=d.getOptions(),h=d.each,r=d.extend,v=d.extendClass,j=d.merge,k=d.pick,o=d.seriesTypes,w=d.wrap,n=function(){},p=d.ColorAxis=function(){this.isColorAxis=!0;this.init.apply(this,arguments)};r(p.prototype,m.prototype);r(p.prototype,{defaultColorAxisOptions:{lineWidth:0,minPadding:0,maxPadding:0,gridLineWidth:1,tickPixelInterval:72,
	startOnTick:!0,endOnTick:!0,offset:0,marker:{animation:{duration:50},color:"gray",width:0.01},labels:{overflow:"justify"},minColor:"#EFEFFF",maxColor:"#003875",tickLength:5},init:function(a,b){var c=a.options.legend.layout!=="vertical",f;f=j(this.defaultColorAxisOptions,{side:c?2:1,reversed:!c},b,{opposite:!c,showEmpty:!1,title:null,isColor:!0});m.prototype.init.call(this,a,f);b.dataClasses&&this.initDataClasses(b);this.initStops(b);this.horiz=c;this.zoomEnabled=!1},tweenColors:function(a,b,c){var f;
	!b.rgba.length||!a.rgba.length?a=b.input||"none":(a=a.rgba,b=b.rgba,f=b[3]!==1||a[3]!==1,a=(f?"rgba(":"rgb(")+Math.round(b[0]+(a[0]-b[0])*(1-c))+","+Math.round(b[1]+(a[1]-b[1])*(1-c))+","+Math.round(b[2]+(a[2]-b[2])*(1-c))+(f?","+(b[3]+(a[3]-b[3])*(1-c)):"")+")");return a},initDataClasses:function(a){var b=this,c=this.chart,f,e=0,l=this.options,g=a.dataClasses.length;this.dataClasses=f=[];this.legendItems=[];h(a.dataClasses,function(a,d){var h,a=j(a);f.push(a);if(!a.color)l.dataClassColor==="category"?
	(h=c.options.colors,a.color=h[e++],e===h.length&&(e=0)):a.color=b.tweenColors(i(l.minColor),i(l.maxColor),g<2?0.5:d/(g-1))})},initStops:function(a){this.stops=a.stops||[[0,this.options.minColor],[1,this.options.maxColor]];h(this.stops,function(a){a.color=i(a[1])})},setOptions:function(a){m.prototype.setOptions.call(this,a);this.options.crosshair=this.options.marker;this.coll="colorAxis"},setAxisSize:function(){var a=this.legendSymbol,b=this.chart,c,f,e;if(a)this.left=c=a.attr("x"),this.top=f=a.attr("y"),
	this.width=e=a.attr("width"),this.height=a=a.attr("height"),this.right=b.chartWidth-c-e,this.bottom=b.chartHeight-f-a,this.len=this.horiz?e:a,this.pos=this.horiz?c:f},toColor:function(a,b){var c,f=this.stops,e,l=this.dataClasses,g,d;if(l)for(d=l.length;d--;){if(g=l[d],e=g.from,f=g.to,(e===void 0||a>=e)&&(f===void 0||a<=f)){c=g.color;if(b)b.dataClass=d;break}}else{this.isLog&&(a=this.val2lin(a));c=1-(this.max-a)/(this.max-this.min||1);for(d=f.length;d--;)if(c>f[d][0])break;e=f[d]||f[d+1];f=f[d+1]||
	e;c=1-(f[0]-c)/(f[0]-e[0]||1);c=this.tweenColors(e.color,f.color,c)}return c},getOffset:function(){var a=this.legendGroup,b=this.chart.axisOffset[this.side];if(a){this.axisParent=a;m.prototype.getOffset.call(this);if(!this.added)this.added=!0,this.labelLeft=0,this.labelRight=this.width;this.chart.axisOffset[this.side]=b}},setLegendColor:function(){var a,b=this.options,c=this.reversed;a=c?1:0;c=c?0:1;a=this.horiz?[a,0,c,0]:[0,c,0,a];this.legendColor={linearGradient:{x1:a[0],y1:a[1],x2:a[2],y2:a[3]},
	stops:b.stops||[[0,b.minColor],[1,b.maxColor]]}},drawLegendSymbol:function(a,b){var c=a.padding,f=a.options,e=this.horiz,d=k(f.symbolWidth,e?200:12),g=k(f.symbolHeight,e?12:200),h=k(f.labelPadding,e?16:30),f=k(f.itemDistance,10);this.setLegendColor();b.legendSymbol=this.chart.renderer.rect(0,a.baseline-11,d,g).attr({zIndex:1}).add(b.legendGroup);this.legendItemWidth=d+c+(e?f:h);this.legendItemHeight=g+c+(e?h:0)},setState:n,visible:!0,setVisible:n,getSeriesExtremes:function(){var a;if(this.series.length)a=
	this.series[0],this.dataMin=a.valueMin,this.dataMax=a.valueMax},drawCrosshair:function(a,b){var c=b&&b.plotX,f=b&&b.plotY,e,d=this.pos,g=this.len;if(b)e=this.toPixels(b[b.series.colorKey]),e<d?e=d-2:e>d+g&&(e=d+g+2),b.plotX=e,b.plotY=this.len-e,m.prototype.drawCrosshair.call(this,a,b),b.plotX=c,b.plotY=f,this.cross&&this.cross.attr({fill:this.crosshair.color}).add(this.legendGroup)},getPlotLinePath:function(a,b,c,f,e){return typeof e==="number"?this.horiz?["M",e-4,this.top-6,"L",e+4,this.top-6,e,
	this.top,"Z"]:["M",this.left,e,"L",this.left-6,e+6,this.left-6,e-6,"Z"]:m.prototype.getPlotLinePath.call(this,a,b,c,f)},update:function(a,b){var c=this.chart,f=c.legend;h(this.series,function(a){a.isDirtyData=!0});if(a.dataClasses&&f.allItems)h(f.allItems,function(a){a.isDataClass&&a.legendGroup.destroy()}),c.isDirtyLegend=!0;c.options[this.coll]=j(this.userOptions,a);m.prototype.update.call(this,a,b);this.legendItem&&(this.setLegendColor(),f.colorizeItem(this,!0))},getDataClassLegendSymbols:function(){var a=
	this,b=this.chart,c=this.legendItems,f=b.options.legend,e=f.valueDecimals,l=f.valueSuffix||"",g;c.length||h(this.dataClasses,function(f,m){var i=!0,j=f.from,k=f.to;g="";j===void 0?g="< ":k===void 0&&(g="> ");j!==void 0&&(g+=d.numberFormat(j,e)+l);j!==void 0&&k!==void 0&&(g+=" - ");k!==void 0&&(g+=d.numberFormat(k,e)+l);c.push(r({chart:b,name:g,options:{},drawLegendSymbol:s.drawRectangle,visible:!0,setState:n,isDataClass:!0,setVisible:function(){i=this.visible=!i;h(a.series,function(a){h(a.points,
	function(a){a.dataClass===m&&a.setVisible(i)})});b.legend.colorizeItem(this,i)}},f))});return c},name:""});h(["fill","stroke"],function(a){d.Fx.prototype[a+"Setter"]=function(){this.elem.attr(a,p.prototype.tweenColors(i(this.start),i(this.end),this.pos))}});w(q.prototype,"getAxes",function(a){var b=this.options.colorAxis;a.call(this);this.colorAxis=[];b&&new p(this,b)});w(x.prototype,"getAllItems",function(a){var b=[],c=this.chart.colorAxis[0];c&&(c.options.dataClasses?b=b.concat(c.getDataClassLegendSymbols()):
	b.push(c),h(c.series,function(a){a.options.showInLegend=!1}));return b.concat(a.call(this))});q={pointAttrToOptions:{stroke:"borderColor","stroke-width":"borderWidth",fill:"color",dashstyle:"dashStyle"},pointArrayMap:["value"],axisTypes:["xAxis","yAxis","colorAxis"],optionalAxis:"colorAxis",trackerGroups:["group","markerGroup","dataLabelsGroup"],getSymbol:n,parallelArrays:["x","y","value"],colorKey:"value",translateColors:function(){var a=this,b=this.options.nullColor,c=this.colorAxis,f=this.colorKey;
	h(this.data,function(e){var d=e[f];if(d=e.options.color||(d===null?b:c&&d!==void 0?c.toColor(d,e):e.color||a.color))e.color=d})}};u.plotOptions.heatmap=j(u.plotOptions.scatter,{animation:!1,borderWidth:0,nullColor:"#F8F8F8",dataLabels:{formatter:function(){return this.point.value},inside:!0,verticalAlign:"middle",crop:!1,overflow:!1,padding:0},marker:null,pointRange:null,tooltip:{pointFormat:"{point.x}, {point.y}: {point.value}<br/>"},states:{normal:{animation:!0},hover:{halo:!1,brightness:0.2}}});
	o.heatmap=v(o.scatter,j(q,{type:"heatmap",pointArrayMap:["y","value"],hasPointSpecificOptions:!0,pointClass:v(y,{setVisible:function(a){var b=this,c=a?"show":"hide";h(["graphic","dataLabel"],function(a){if(b[a])b[a][c]()})}}),supportsDrilldown:!0,getExtremesFromAll:!0,directTouch:!0,init:function(){var a;o.scatter.prototype.init.apply(this,arguments);a=this.options;a.pointRange=k(a.pointRange,a.colsize||1);this.yAxis.axisPointRange=a.rowsize||1},translate:function(){var a=this.options,b=this.xAxis,
	c=this.yAxis,f=function(a,b,c){return Math.min(Math.max(b,a),c)};this.generatePoints();h(this.points,function(e){var d=(a.colsize||1)/2,g=(a.rowsize||1)/2,h=f(Math.round(b.len-b.translate(e.x-d,0,1,0,1)),0,b.len),d=f(Math.round(b.len-b.translate(e.x+d,0,1,0,1)),0,b.len),i=f(Math.round(c.translate(e.y-g,0,1,0,1)),0,c.len),g=f(Math.round(c.translate(e.y+g,0,1,0,1)),0,c.len);e.plotX=e.clientX=(h+d)/2;e.plotY=(i+g)/2;e.shapeType="rect";e.shapeArgs={x:Math.min(h,d),y:Math.min(i,g),width:Math.abs(d-h),
	height:Math.abs(g-i)}});this.translateColors();this.chart.hasRendered&&h(this.points,function(a){a.shapeArgs.fill=a.options.color||a.color})},drawPoints:o.column.prototype.drawPoints,animate:n,getBox:n,drawLegendSymbol:s.drawRectangle,getExtremes:function(){t.prototype.getExtremes.call(this,this.valueData);this.valueMin=this.dataMin;this.valueMax=this.dataMax;t.prototype.getExtremes.call(this)}}))});


/***/ },

/***/ 1129:
/*!*******************************************************!*\
  !*** ./heatmap-highcharts/css/heatmap-highcharts.css ***!
  \*******************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../../~/css-loader!./heatmap-highcharts.css */ 1130);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../../~/style-loader/addStyles.js */ 1132)(content, {});
	if(content.locals) module.exports = content.locals;
	// Hot Module Replacement
	if(false) {
		// When the styles change, update the <style> tags
		if(!content.locals) {
			module.hot.accept("!!./../../node_modules/css-loader/index.js!./heatmap-highcharts.css", function() {
				var newContent = require("!!./../../node_modules/css-loader/index.js!./heatmap-highcharts.css");
				if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
				update(newContent);
			});
		}
		// When the module is disposed, remove the <style> tags
		module.hot.dispose(function() { update(); });
	}

/***/ },

/***/ 1130:
/*!**********************************************************************!*\
  !*** ./~/css-loader!./heatmap-highcharts/css/heatmap-highcharts.css ***!
  \**********************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../../~/css-loader/lib/css-base.js */ 1131)();
	// imports
	
	
	// module
	exports.push([module.id, "body {\n    background-color: white;\n    font-family: Verdana, sans-serif;\n    font-size: 10px;\n}\n\na.project_link:hover,\na.project_link:active {\n    text-decoration: underline;\n}\n\n.gene_title {text-align:center; font-size:70%; border:0px red solid;}\n\n#container {min-width: 410px; min-height: 210px; /*avoid line overlap*/margin: 0 auto; border:0px red solid;}\n\n#barcharts_legend_list_items {color:#606060; margin-left: 180px; margin-top:-9px; border: 0px solid olive;position:absolute;}\n.col_below {background:#d7d7d7;}\n.col_low {background: #dff8ff;}\n.col_med {background: #9fd2fa;}\n.col_high {background: #45affd;}\n.col_nd {background: #fff;}\n#barcharts_legend_list_items .legend-item {cursor: pointer;  vertical-align: middle;display:inline-block; padding:4px;}\n#barcharts_legend_list_items .legend-item:hover {color:black;}\n#barcharts_legend_list_items .legend-item.legend-item-off {color:#ccc;}\n#barcharts_legend_list_items .legend-item.legend-item-off div {background-color: #f7f7f7;}\n#barcharts_legend_list_items .legend-item.special {cursor:default;margin-left:36px;color:black;}\n\n#barcharts_legend_list_items .legend-item .legend-rectangle {width: 12px;height: 12px; border:1px rgba(0, 0, 0, 0.2) solid;float:left;margin-right:4px;}\n\n\n#barcharts_legend_list_items .legend-item  .icon-generic:before {\n    font-size: 180%;\n    color: #7e7e7e;\n}\n\n#barcharts_legend_list_items .legend-item:hover .icon-generic:before {color:#353535;}\n\n/*****************************************EBI font*******************************************************/\n@font-face{font-family:'EBI-Conceptual';src:url('http://www.ebi.ac.uk//web_guidelines/fonts/EBI-Conceptual/fonts/EBI-Conceptual.eot');src:url('http://www.ebi.ac.uk//web_guidelines/fonts/EBI-Conceptual/fonts/EBI-Conceptual.eot?#iefix') format('embedded-opentype'),\nurl('http://www.ebi.ac.uk//web_guidelines/fonts/EBI-Conceptual/fonts/EBI-Conceptual.woff') format('woff'),\nlocal('\\263A'),\nurl('http://www.ebi.ac.uk//web_guidelines/fonts/EBI-Conceptual/fonts/EBI-Conceptual.svg#EBI-Conceptual') format('svg'),\nurl('http://www.ebi.ac.uk//web_guidelines/fonts/EBI-Conceptual/fonts/EBI-Conceptual.ttf') format('truetype');font-weight:normal;font-style:normal}\n\n\n@font-face{font-family:'EBI-Functional';src:url('http://www.ebi.ac.uk/web_guidelines/fonts/EBI-Functional/fonts/EBI-Functional.eot');src:url('http://www.ebi.ac.uk/web_guidelines/fonts/EBI-Functional/fonts/EBI-Functional.eot?#iefix') format('embedded-opentype'),\nurl('http://www.ebi.ac.uk/web_guidelines/fonts/EBI-Functional/fonts/EBI-Functional.woff') format('woff'),\nlocal('\\263A'),\nurl('http://www.ebi.ac.uk/web_guidelines/fonts/EBI-Functional/fonts/EBI-Functional.svg#EBI-Functional') format('svg'),\nurl('http://www.ebi.ac.uk/web_guidelines/fonts/EBI-Functional/fonts/EBI-Functional.ttf') format('truetype');font-weight:normal;font-style:normal}\n\n@font-face{font-family:'EBI-Generic';src:url('http://www.ebi.ac.uk//web_guidelines/fonts/EBI-Generic/fonts/EBI-Generic.eot');src:url('http://www.ebi.ac.uk//web_guidelines/fonts/EBI-Generic/fonts/EBI-Generic.eot?#iefix') format('embedded-opentype'),\nurl('http://www.ebi.ac.uk//web_guidelines/fonts/EBI-Generic/fonts/EBI-Generic.woff') format('woff'),\nlocal('\\263A'),\nurl('http://www.ebi.ac.uk//web_guidelines/fonts/EBI-Generic/fonts/EBI-Generic.svg#EBI-Generic') format('svg'),\nurl('http://www.ebi.ac.uk//web_guidelines/fonts/EBI-Generic/fonts/EBI-Generic.ttf') format('truetype');font-weight:normal;font-style:normal}\n\n\n@font-face{font-family:'EBI-Species';src:url('http://www.ebi.ac.uk/web_guidelines/fonts/EBI-Species/fonts/EBI-Species.eot');src:url('http://www.ebi.ac.uk/web_guidelines/fonts/EBI-Species/fonts/EBI-Species.eot?#iefix') format('embedded-opentype'),\nurl('http://www.ebi.ac.uk/web_guidelines/fonts/EBI-Species/fonts/EBI-Species.woff') format('woff'),\nlocal('\\263A'),\nurl('http://www.ebi.ac.uk/web_guidelines/fonts/EBI-Species/fonts/EBI-Species.svg#EBI-Species') format('svg'),\nurl('http://www.ebi.ac.uk/web_guidelines/fonts/EBI-Species/fonts/EBI-Species.ttf') format('truetype');font-weight:normal;font-style:normal}\n\n@font-face{font-family:'EBI-SocialMedia';src:url('http://www.ebi.ac.uk/web_guidelines/fonts/EBI-SocialMedia/fonts/EBI-SocialMedia.eot');src:url('http://www.ebi.ac.uk/web_guidelines/fonts/EBI-SocialMedia/fonts/EBI-SocialMedia.eot?#iefix') format('embedded-opentype'),\nurl('http://www.ebi.ac.uk/web_guidelines/fonts/EBI-SocialMedia/fonts/EBI-SocialMedia.woff') format('woff'),\nlocal('\\263A'),\nurl('http://www.ebi.ac.uk/web_guidelines/fonts/EBI-SocialMedia/fonts/EBI-SocialMedia.svg#EBI-Species') format('svg'),\nurl('http://www.ebi.ac.uk/web_guidelines/fonts/EBI-SocialMedia/fonts/EBI-SocialMedia.ttf') format('truetype');font-weight:normal;font-style:normal}\n\n.icon-socialmedia:before {font-family: 'EBI-SocialMedia';margin: 0 0.3em 0 0;content: attr(data-icon);color: white;font-size: 100%;}\n.icon-conceptual:before {font-family: 'EBI-Conceptual';margin: 0 0.3em 0 0;content: attr(data-icon); color: #7e7e7e;font-size: 180%;}\n\n.icon-species:before {\n    font-family: 'EBI-Species';\n    font-size: 100%;\n    color: white;\n    content: attr(data-icon);\n    margin: 0 0 0 0;\n}\n.icon {\n    text-decoration: none;\n    font-style: normal;\n}\n\n.icon-static:before, .icon-generic:before {\n    font-family: 'EBI-Generic';\n    font-size: 100%;\n    color: #BBB;\n    content: attr(data-icon);\n    margin: 0 0 0 0;\n}\n\n.icon-functional:before {\n    font-family: 'EBI-Functional';\n    font-size: 100%;\n    color: white;\n    content: attr(data-icon);\n    margin: 0 0.3em 0 0;\n}\n/*****************************************END EBI font*******************************************************/", ""]);
	
	// exports


/***/ },

/***/ 1131:
/*!**************************************!*\
  !*** ./~/css-loader/lib/css-base.js ***!
  \**************************************/
/***/ function(module, exports) {

	/*
		MIT License http://www.opensource.org/licenses/mit-license.php
		Author Tobias Koppers @sokra
	*/
	// css base code, injected by the css-loader
	module.exports = function() {
		var list = [];
	
		// return the list of modules as css string
		list.toString = function toString() {
			var result = [];
			for(var i = 0; i < this.length; i++) {
				var item = this[i];
				if(item[2]) {
					result.push("@media " + item[2] + "{" + item[1] + "}");
				} else {
					result.push(item[1]);
				}
			}
			return result.join("");
		};
	
		// import a list of modules into the list
		list.i = function(modules, mediaQuery) {
			if(typeof modules === "string")
				modules = [[null, modules, ""]];
			var alreadyImportedModules = {};
			for(var i = 0; i < this.length; i++) {
				var id = this[i][0];
				if(typeof id === "number")
					alreadyImportedModules[id] = true;
			}
			for(i = 0; i < modules.length; i++) {
				var item = modules[i];
				// skip already imported module
				// this implementation is not 100% perfect for weird media query combinations
				//  when a module is imported multiple times with different media queries.
				//  I hope this will never occur (Hey this way we have smaller bundles)
				if(typeof item[0] !== "number" || !alreadyImportedModules[item[0]]) {
					if(mediaQuery && !item[2]) {
						item[2] = mediaQuery;
					} else if(mediaQuery) {
						item[2] = "(" + item[2] + ") and (" + mediaQuery + ")";
					}
					list.push(item);
				}
			}
		};
		return list;
	};


/***/ },

/***/ 1132:
/*!*************************************!*\
  !*** ./~/style-loader/addStyles.js ***!
  \*************************************/
/***/ function(module, exports, __webpack_require__) {

	/*
		MIT License http://www.opensource.org/licenses/mit-license.php
		Author Tobias Koppers @sokra
	*/
	var stylesInDom = {},
		memoize = function(fn) {
			var memo;
			return function () {
				if (typeof memo === "undefined") memo = fn.apply(this, arguments);
				return memo;
			};
		},
		isOldIE = memoize(function() {
			return /msie [6-9]\b/.test(window.navigator.userAgent.toLowerCase());
		}),
		getHeadElement = memoize(function () {
			return document.head || document.getElementsByTagName("head")[0];
		}),
		singletonElement = null,
		singletonCounter = 0,
		styleElementsInsertedAtTop = [];
	
	module.exports = function(list, options) {
		if(true) {
			if(typeof document !== "object") throw new Error("The style-loader cannot be used in a non-browser environment");
		}
	
		options = options || {};
		// Force single-tag solution on IE6-9, which has a hard limit on the # of <style>
		// tags it will allow on a page
		if (typeof options.singleton === "undefined") options.singleton = isOldIE();
	
		// By default, add <style> tags to the bottom of <head>.
		if (typeof options.insertAt === "undefined") options.insertAt = "bottom";
	
		var styles = listToStyles(list);
		addStylesToDom(styles, options);
	
		return function update(newList) {
			var mayRemove = [];
			for(var i = 0; i < styles.length; i++) {
				var item = styles[i];
				var domStyle = stylesInDom[item.id];
				domStyle.refs--;
				mayRemove.push(domStyle);
			}
			if(newList) {
				var newStyles = listToStyles(newList);
				addStylesToDom(newStyles, options);
			}
			for(var i = 0; i < mayRemove.length; i++) {
				var domStyle = mayRemove[i];
				if(domStyle.refs === 0) {
					for(var j = 0; j < domStyle.parts.length; j++)
						domStyle.parts[j]();
					delete stylesInDom[domStyle.id];
				}
			}
		};
	}
	
	function addStylesToDom(styles, options) {
		for(var i = 0; i < styles.length; i++) {
			var item = styles[i];
			var domStyle = stylesInDom[item.id];
			if(domStyle) {
				domStyle.refs++;
				for(var j = 0; j < domStyle.parts.length; j++) {
					domStyle.parts[j](item.parts[j]);
				}
				for(; j < item.parts.length; j++) {
					domStyle.parts.push(addStyle(item.parts[j], options));
				}
			} else {
				var parts = [];
				for(var j = 0; j < item.parts.length; j++) {
					parts.push(addStyle(item.parts[j], options));
				}
				stylesInDom[item.id] = {id: item.id, refs: 1, parts: parts};
			}
		}
	}
	
	function listToStyles(list) {
		var styles = [];
		var newStyles = {};
		for(var i = 0; i < list.length; i++) {
			var item = list[i];
			var id = item[0];
			var css = item[1];
			var media = item[2];
			var sourceMap = item[3];
			var part = {css: css, media: media, sourceMap: sourceMap};
			if(!newStyles[id])
				styles.push(newStyles[id] = {id: id, parts: [part]});
			else
				newStyles[id].parts.push(part);
		}
		return styles;
	}
	
	function insertStyleElement(options, styleElement) {
		var head = getHeadElement();
		var lastStyleElementInsertedAtTop = styleElementsInsertedAtTop[styleElementsInsertedAtTop.length - 1];
		if (options.insertAt === "top") {
			if(!lastStyleElementInsertedAtTop) {
				head.insertBefore(styleElement, head.firstChild);
			} else if(lastStyleElementInsertedAtTop.nextSibling) {
				head.insertBefore(styleElement, lastStyleElementInsertedAtTop.nextSibling);
			} else {
				head.appendChild(styleElement);
			}
			styleElementsInsertedAtTop.push(styleElement);
		} else if (options.insertAt === "bottom") {
			head.appendChild(styleElement);
		} else {
			throw new Error("Invalid value for parameter 'insertAt'. Must be 'top' or 'bottom'.");
		}
	}
	
	function removeStyleElement(styleElement) {
		styleElement.parentNode.removeChild(styleElement);
		var idx = styleElementsInsertedAtTop.indexOf(styleElement);
		if(idx >= 0) {
			styleElementsInsertedAtTop.splice(idx, 1);
		}
	}
	
	function createStyleElement(options) {
		var styleElement = document.createElement("style");
		styleElement.type = "text/css";
		insertStyleElement(options, styleElement);
		return styleElement;
	}
	
	function createLinkElement(options) {
		var linkElement = document.createElement("link");
		linkElement.rel = "stylesheet";
		insertStyleElement(options, linkElement);
		return linkElement;
	}
	
	function addStyle(obj, options) {
		var styleElement, update, remove;
	
		if (options.singleton) {
			var styleIndex = singletonCounter++;
			styleElement = singletonElement || (singletonElement = createStyleElement(options));
			update = applyToSingletonTag.bind(null, styleElement, styleIndex, false);
			remove = applyToSingletonTag.bind(null, styleElement, styleIndex, true);
		} else if(obj.sourceMap &&
			typeof URL === "function" &&
			typeof URL.createObjectURL === "function" &&
			typeof URL.revokeObjectURL === "function" &&
			typeof Blob === "function" &&
			typeof btoa === "function") {
			styleElement = createLinkElement(options);
			update = updateLink.bind(null, styleElement);
			remove = function() {
				removeStyleElement(styleElement);
				if(styleElement.href)
					URL.revokeObjectURL(styleElement.href);
			};
		} else {
			styleElement = createStyleElement(options);
			update = applyToTag.bind(null, styleElement);
			remove = function() {
				removeStyleElement(styleElement);
			};
		}
	
		update(obj);
	
		return function updateStyle(newObj) {
			if(newObj) {
				if(newObj.css === obj.css && newObj.media === obj.media && newObj.sourceMap === obj.sourceMap)
					return;
				update(obj = newObj);
			} else {
				remove();
			}
		};
	}
	
	var replaceText = (function () {
		var textStore = [];
	
		return function (index, replacement) {
			textStore[index] = replacement;
			return textStore.filter(Boolean).join('\n');
		};
	})();
	
	function applyToSingletonTag(styleElement, index, remove, obj) {
		var css = remove ? "" : obj.css;
	
		if (styleElement.styleSheet) {
			styleElement.styleSheet.cssText = replaceText(index, css);
		} else {
			var cssNode = document.createTextNode(css);
			var childNodes = styleElement.childNodes;
			if (childNodes[index]) styleElement.removeChild(childNodes[index]);
			if (childNodes.length) {
				styleElement.insertBefore(cssNode, childNodes[index]);
			} else {
				styleElement.appendChild(cssNode);
			}
		}
	}
	
	function applyToTag(styleElement, obj) {
		var css = obj.css;
		var media = obj.media;
		var sourceMap = obj.sourceMap;
	
		if(media) {
			styleElement.setAttribute("media", media)
		}
	
		if(styleElement.styleSheet) {
			styleElement.styleSheet.cssText = css;
		} else {
			while(styleElement.firstChild) {
				styleElement.removeChild(styleElement.firstChild);
			}
			styleElement.appendChild(document.createTextNode(css));
		}
	}
	
	function updateLink(linkElement, obj) {
		var css = obj.css;
		var media = obj.media;
		var sourceMap = obj.sourceMap;
	
		if(sourceMap) {
			// http://stackoverflow.com/a/26603875
			css += "\n/*# sourceMappingURL=data:application/json;base64," + btoa(unescape(encodeURIComponent(JSON.stringify(sourceMap)))) + " */";
		}
	
		var blob = new Blob([css], { type: "text/css" });
	
		var oldSrc = linkElement.href;
	
		linkElement.href = URL.createObjectURL(blob);
	
		if(oldSrc)
			URL.revokeObjectURL(oldSrc);
	}


/***/ },

/***/ 1133:
/*!****************************************************!*\
  !*** ./heatmap-highcharts/src/highcharts-utils.js ***!
  \****************************************************/
/***/ function(module, exports) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	function getXAxisCategories (columnHeaders) {
	    return columnHeaders.map(function (columnHeader) {
	        return columnHeader.factorValue;
	    });
	}
	
	var yAxisCategoriesLinks = {};
	
	function getYAxisCategories (profiles, heatmapConfig) {
	
	    return profiles.rows.map(function (profile) {
	        yAxisCategoriesLinks[profile.name] = profile.id + "?geneQuery=" + heatmapConfig.geneQuery +
	            "&serializedFilterFactors=" + encodeURIComponent(profile.serializedFilterFactors);
	        return profile.name;
	    });
	}
	
	function getYAxisCategoriesLinks () {
	    return yAxisCategoriesLinks;
	}
	
	function getExperimentTypes (profiles) {
	    var experimentTypes = [];
	
	    for (var i = 0; i < profiles.rows.length; i++) {
	        if (experimentTypes.indexOf(profiles.rows[i].experimentType) === -1) {
	            experimentTypes.push(profiles.rows[i].experimentType);
	        }
	    }
	
	    for (var i = 0; i < experimentTypes.length; i++) {
	        this.sortExperimentExpressionLevels(experimentTypes[i]);
	    }
	
	    return experimentTypes;
	
	}
	
	function sortExperimentExpressionLevels (experimentType) {
	    var experimentTypeSeriesData = [];
	
	    for (var i = 0; i < this.yAxisCategories().length; i++) {
	        if (this.props.profiles.rows[i].experimentType !== experimentType) {
	            continue;
	        }
	
	        for (var j = 0; j < this.state.xAxisCategories.length; j++) {
	            var literalValue = this.props.profiles.rows[i].expressions[j].value;
	
	            if (literalValue === "") {
	                var dataBelowCutoff = this.state.seriesDataBelowCutoff.concat([j, i, this.state.seriesDataBelowCutoffString]);
	                this.setState({seriesDataBelowCutoff: dataBelowCutoff});
	            } else if (literalValue === "NT") {
	                var dataNA = this.state.seriesDataNA.concat([j, i, this.state.seriesDataNAString]);
	                this.setState({seriesDataNA: dataNA});
	            } else {
	                var value = parseFloat(literalValue);
	                if (!isNaN(value)) {
	                    experimentTypeSeriesData.push([j, i, value]);
	                }
	            }
	        }
	    }
	
	    experimentTypeSeriesData.sort(function(a, b) {
	        return a[2] - b[2];
	    });
	
	    var experimentTypeMax = experimentTypeSeriesData[experimentTypeSeriesData.length - 1][2];
	
	    for (var k = 0; k < this.state.seriesDataRanges.length; k++) {
	        //seriesDataRanges[k].seriesData.concat(
	        experimentTypeSeriesData.filter(
	            function(datum) {
	                return datum[2] > this.state.seriesDataRanges[k].from * experimentTypeMax && datum[2] <= this.state.seriesDataRanges[k].to * experimentTypeMax;
	            }.bind(this)).forEach(
	            function(filteredDatum) {
	                var seriesD = this.state.seriesDataRanges[k].seriesData.concat(filteredDatum);
	                this.setState({seriesDataRanges: seriesD});
	            }.bind(this));
	    }
	
	    return experimentTypeSeriesData;
	
	}
	
	//*------------------------------------------------------------------*
	
	exports.getXAxisCategories = getXAxisCategories;
	exports.getYAxisCategories = getYAxisCategories;
	exports.getYAxisCategoriesLinks = getYAxisCategoriesLinks;


/***/ }

});
//# sourceMappingURL=highchartsAtlasHeatmap.bundle.js.map