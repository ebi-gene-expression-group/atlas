webpackJsonp([0],{

/***/ 0:
/*!**************************!*\
  !*** ./heatmap/index.js ***!
  \**************************/
/***/ function(module, exports, __webpack_require__) {

	/* WEBPACK VAR INJECTION */(function(global) {module.exports = global["exposed"] = __webpack_require__(/*! -!./heatmap/index.js */ 1);
	/* WEBPACK VAR INJECTION */}.call(exports, (function() { return this; }())))

/***/ },

/***/ 1:
/*!**************************!*\
  !*** ./heatmap/index.js ***!
  \**************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	module.exports = __webpack_require__(/*! ./src/heatmap-anatomogram.js */ 2);

/***/ },

/***/ 2:
/*!********************************************!*\
  !*** ./heatmap/src/heatmap-anatomogram.js ***!
  \********************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	var React = __webpack_require__(/*! react */ 4);
	
	var $ = __webpack_require__(/*! jquery */ 153);
	var jQuery = $;
	__webpack_require__(/*! ../lib/jquery.xdomainrequest.js */ 370);
	
	//*------------------------------------------------------------------*
	
	var heatmapModule = __webpack_require__(/*! ./heatmap.jsx */ 3);
	var HeatmapContainer = __webpack_require__(/*! ./heatmap-container.jsx */ 371);
	var anatomogramModule = __webpack_require__(/*! ./anatomogram-module.js */ 372);
	
	//*------------------------------------------------------------------*
	
	function drawHeatmap (data, targetElement, heatmapClass, heatmapBuilder, heatmapKey) {
	
	    (function ($, React, HeatmapContainer,
	               heatmapBuilder, heatmapConfig, columnHeaders, profiles, geneSetProfiles, anatomogramData, experimentData, heatmapKey) {
	
	        $(document).ready(function () {
	            // call this inside ready() so all scripts load first in IE8
	            var Heatmap = heatmapBuilder(heatmapConfig).Heatmap;
	
	            React.render(
	                React.createElement(
	                    HeatmapContainer,
	                    {Heatmap: Heatmap, isWidget: true, heatmapClass: heatmapClass, experiment: experimentData,
	                     anatomogram: anatomogramData, columnHeaders: columnHeaders, profiles: profiles,
	                     geneSetProfiles: geneSetProfiles, heatmapKey: heatmapKey, heatmapConfig: heatmapConfig}
	                ),
	                targetElement
	            );
	
	            // load anatomogram after heatmap is rendered so wiring works
	            if (anatomogramData) {
	                anatomogramModule(anatomogramData.allSvgPathIds, anatomogramData.maleAnatomogramFile, anatomogramData.femaleAnatomogramFile,
	                    anatomogramData.brainAnatomogramFile, anatomogramData.contextRoot, heatmapConfig.species, heatmapConfig.isSingleGene, heatmapKey);
	            }
	        });
	
	    })($, React, HeatmapContainer,
	       heatmapBuilder, data.config, data.columnHeaders, data.profiles, data.geneSetProfiles, data.anatomogram, data.experiment, heatmapKey);
	
	}
	
	//*------------------------------------------------------------------*
	
	module.exports = function(opt) {
	
	    var targetElement = (typeof opt.target == 'string') ? document.getElementById(opt.target) : opt.target;
	    var $targetElement = $(targetElement);
	
	    var endpoint = opt.heatmapUrl ? opt.heatmapUrl : opt.isMultiExperiment ? 'widgets/heatmap/multiExperiment' : 'widgets/heatmap/referenceExperiment';
	    var url = opt.gxaBaseUrl + endpoint + '?' + opt.params;
	
	    var httpRequest = {
	        url: url,
	        dataType: "json",
	        method:"GET",
	        beforeSend:function () {
	            // TODO: nasty workaround for http://youtrack.jetbrains.com/issue/IDEA-25934 (still not fixed)
	            var resource_host = (true) ? "wwwdev.ebi.ac.uk" : "${resources.host}";
	            $targetElement.html("<img src='http://" + resource_host + "/gxa/resources/images/loading.gif' />");
	        }
	    };
	
	    $.ajax(httpRequest).done(function (data) {
	
	        function overrideContextRoot(data, gxaBaseUrl) {
	            data.config.contextRoot = gxaBaseUrl;
	
	            if (data.anatomogram) {
	                data.anatomogram.contextRoot = gxaBaseUrl;
	            }
	            if (data.experiment) {
	                data.experiment.contextRoot = gxaBaseUrl;
	            }
	        }
	
	        overrideContextRoot(data, opt.gxaBaseUrl);
	
	        if (opt.isMultiExperiment) {
	            drawHeatmap(data, targetElement, opt.heatmapClass, heatmapModule.buildMultiExperiment, opt.heatmapKey);
	        } else {
	            drawHeatmap(data, targetElement, opt.heatmapClass, heatmapModule.buildBaseline);
	        }
	
	    }).fail(function (jqXHR, textStatus, errorThrown) {
	        //containerDiv.html("An error occurred while retrieving the data: " + jqXHR.status + " - " + jqXHR.statusText);
	        if (textStatus === "parsererror") {
	            $targetElement.html("<div class='error'>Could not parse JSON response</div>");
	        } else {
	            $targetElement.html(jqXHR.responseText);
	        }
	    });
	};


/***/ },

/***/ 370:
/*!**********************************************!*\
  !*** ./heatmap/lib/jquery.xdomainrequest.js ***!
  \**********************************************/
/***/ function(module, exports, __webpack_require__) {

	var __WEBPACK_AMD_DEFINE_FACTORY__, __WEBPACK_AMD_DEFINE_ARRAY__, __WEBPACK_AMD_DEFINE_RESULT__;/*!
	 * jQuery-ajaxTransport-XDomainRequest - v1.0.4 - 2015-03-05
	 * https://github.com/MoonScript/jQuery-ajaxTransport-XDomainRequest
	 * Copyright (c) 2015 Jason Moon (@JSONMOON)
	 * Licensed MIT (/blob/master/LICENSE.txt)
	 */
	(function(factory) {
	  if (true) {
	    // AMD. Register as anonymous module.
	    !(__WEBPACK_AMD_DEFINE_ARRAY__ = [__webpack_require__(/*! jquery */ 153)], __WEBPACK_AMD_DEFINE_FACTORY__ = (factory), __WEBPACK_AMD_DEFINE_RESULT__ = (typeof __WEBPACK_AMD_DEFINE_FACTORY__ === 'function' ? (__WEBPACK_AMD_DEFINE_FACTORY__.apply(exports, __WEBPACK_AMD_DEFINE_ARRAY__)) : __WEBPACK_AMD_DEFINE_FACTORY__), __WEBPACK_AMD_DEFINE_RESULT__ !== undefined && (module.exports = __WEBPACK_AMD_DEFINE_RESULT__));
	  } else if (typeof exports === 'object') {
	    // CommonJS
	    module.exports = factory(require('jquery'));
	  } else {
	    // Browser globals.
	    factory(jQuery);
	  }
	}(function($) {
	
	// Only continue if we're on IE8/IE9 with jQuery 1.5+ (contains the ajaxTransport function)
	if ($.support.cors || !$.ajaxTransport || !window.XDomainRequest) {
	  return $;
	}
	
	var httpRegEx = /^(https?:)?\/\//i;
	var getOrPostRegEx = /^get|post$/i;
	var sameSchemeRegEx = new RegExp('^(\/\/|' + location.protocol + ')', 'i');
	
	// ajaxTransport exists in jQuery 1.5+
	$.ajaxTransport('* text html xml json', function(options, userOptions, jqXHR) {
	
	  // Only continue if the request is: asynchronous, uses GET or POST method, has HTTP or HTTPS protocol, and has the same scheme as the calling page
	  if (!options.crossDomain || !options.async || !getOrPostRegEx.test(options.type) || !httpRegEx.test(options.url) || !sameSchemeRegEx.test(options.url)) {
	    return;
	  }
	
	  var xdr = null;
	
	  return {
	    send: function(headers, complete) {
	      var postData = '';
	      var userType = (userOptions.dataType || '').toLowerCase();
	
	      xdr = new XDomainRequest();
	      if (/^\d+$/.test(userOptions.timeout)) {
	        xdr.timeout = userOptions.timeout;
	      }
	
	      xdr.ontimeout = function() {
	        complete(500, 'timeout');
	      };
	
	      xdr.onload = function() {
	        var allResponseHeaders = 'Content-Length: ' + xdr.responseText.length + '\r\nContent-Type: ' + xdr.contentType;
	        var status = {
	          code: 200,
	          message: 'success'
	        };
	        var responses = {
	          text: xdr.responseText
	        };
	        try {
	          if (userType === 'html' || /text\/html/i.test(xdr.contentType)) {
	            responses.html = xdr.responseText;
	          } else if (userType === 'json' || (userType !== 'text' && /\/json/i.test(xdr.contentType))) {
	            try {
	              responses.json = $.parseJSON(xdr.responseText);
	            } catch(e) {
	              status.code = 500;
	              status.message = 'parseerror';
	              //throw 'Invalid JSON: ' + xdr.responseText;
	            }
	          } else if (userType === 'xml' || (userType !== 'text' && /\/xml/i.test(xdr.contentType))) {
	            var doc = new ActiveXObject('Microsoft.XMLDOM');
	            doc.async = false;
	            try {
	              doc.loadXML(xdr.responseText);
	            } catch(e) {
	              doc = undefined;
	            }
	            if (!doc || !doc.documentElement || doc.getElementsByTagName('parsererror').length) {
	              status.code = 500;
	              status.message = 'parseerror';
	              throw 'Invalid XML: ' + xdr.responseText;
	            }
	            responses.xml = doc;
	          }
	        } catch(parseMessage) {
	          throw parseMessage;
	        } finally {
	          complete(status.code, status.message, responses, allResponseHeaders);
	        }
	      };
	
	      // set an empty handler for 'onprogress' so requests don't get aborted
	      xdr.onprogress = function(){};
	      xdr.onerror = function() {
	        complete(500, 'error', {
	          text: xdr.responseText
	        });
	      };
	
	      if (userOptions.data) {
	        postData = ($.type(userOptions.data) === 'string') ? userOptions.data : $.param(userOptions.data);
	      }
	      xdr.open(options.type, options.url);
	      xdr.send(postData);
	    },
	    abort: function() {
	      if (xdr) {
	        xdr.abort();
	      }
	    }
	  };
	});
	
	return $;
	
	}));


/***/ },

/***/ 371:
/*!*******************************************!*\
  !*** ./heatmap/src/heatmap-container.jsx ***!
  \*******************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	var React = __webpack_require__(/*! react */ 4);
	var $ = __webpack_require__(/*! jquery */ 153);
	var jQuery = $;
	__webpack_require__(/*! ../lib/jquery.hc-sticky.js */ 156);
	
	var URI = __webpack_require__(/*! URIjs */ 166);
	
	//*------------------------------------------------------------------*
	
	__webpack_require__(/*! ../css/atlas.css */ 338);
	__webpack_require__(/*! ../css/heatmap-and-anatomogram.css */ 347);
	
	//*------------------------------------------------------------------*
	
	var ExperimentDescription = React.createClass({displayName: "ExperimentDescription",
	
	    render: function () {
	
	        var experimentURL = this.props.experiment.contextRoot + this.props.experiment.URL;
	
	        return (
	            React.createElement("div", {style: {width: "100%"}}, 
	                React.createElement("div", {id: "experimentDescription"}, 
	                    React.createElement("a", {id: "goto-experiment", className: "gxaThickLink", title: "Experiment Page", href: experimentURL}, this.props.experiment.description)
	                ), 
	                React.createElement("div", {id: "experimentOrganisms"}, "Organism(s): ", React.createElement("span", {style: {"fontStyle":"italic"}}, this.props.experiment.allSpecies))
	            )
	        );
	    }
	
	});
	
	
	var Anatomogram = React.createClass({displayName: "Anatomogram",
	
	    render: function () {
	        function containsHuman(s) {
	            return s.indexOf("human") > -1;
	        }
	
	        function replaceSpaces (value) {
	            var result = value.replace(" ","_");
	            return result.trim();
	        }
	
	        var height = containsHuman(this.props.anatomogram.maleAnatomogramFile) ? 360 : 250;
	        var maleToggleImageSrc =this.props.anatomogram.contextRoot + this.props.anatomogram.toggleButtonMaleImage;
	        var femaleToggleImageSrc =this.props.anatomogram.contextRoot + this.props.anatomogram.toggleButtonFemaleImage;
	        var brainToggleImageSrc =this.props.anatomogram.contextRoot + this.props.anatomogram.toggleButtonBrainImage;
	
	        var heatmapKeyTrimmed = this.props.heatmapKey ? replaceSpaces(this.props.heatmapKey) : null;
	
	        var anatomogram = this.props.heatmapKey ? "anatomogram" + heatmapKeyTrimmed : "anatomogram";
	        var sexToggle = this.props.heatmapKey ? "sex-toggle" + heatmapKeyTrimmed : "sex-toggle";
	        var maleToggleImage = this.props.heatmapKey ? "male-toggle-image" + heatmapKeyTrimmed : "male-toggle-image";
	        var femaleToggleImage = this.props.heatmapKey ? "female-toggle-image" + heatmapKeyTrimmed : "female-toggle-image";
	        var brainToggleImage = this.props.heatmapKey ? "brain-toggle-image" + heatmapKeyTrimmed : "brain-toggle-image";
	        var keyId = this.props.heatmapKey ? "anatomogramBody" + heatmapKeyTrimmed : "anatomogramBody";
	
	        return (
	            React.createElement("div", {ref: "anatomogram", id: anatomogram, className: "gxaAside gxaDoubleClickNoSelection", style: {display: "inline"}}, 
	                React.createElement("table", null, 
	                    React.createElement("tbody", null, 
	                    React.createElement("tr", null, 
	                        React.createElement("td", {style: {"paddingTop": "15px", "verticalAlign":"top"}}, 
	                            React.createElement("span", {id: sexToggle}, 
	                                React.createElement("img", {id: maleToggleImage, title: "Switch anatomogram", className: "gxaButtonImage", 
	                                     style: {"width":"20px", "height":"20px", "padding":"2px", "display":"none"}, 
	                                     src: maleToggleImageSrc}), React.createElement("br", null), 
	                                    React.createElement("img", {id: femaleToggleImage, title: "Switch anatomogram", className: "gxaButtonImage", 
	                                         style: {"width":"20px", "height":"20px", "padding":"2px", "display":"none"}, 
	                                         src: femaleToggleImageSrc}), React.createElement("br", null), 
	                                    React.createElement("img", {id: brainToggleImage, title: "Switch anatomogram", className: "gxaButtonImage", 
	                                         style: {"width":"20px", "height":"20px", "padding":"2px", "display":"none"}, 
	                                         src: brainToggleImageSrc}), React.createElement("br", null)
	                            )
	                        ), 
	                        React.createElement("td", null, 
	                            React.createElement("div", {id: keyId, style: {"display":"inline-block", "width": "230px", "height":height}}
	                            )
	                        )
	                    )
	                    )
	                ), 
	                React.createElement("div", {id: "anatomogram-ensembl-launcher"})
	            )
	        );
	    },
	
	    componentDidMount: function() {
	        if ($.fn.hcSticky) {
	            var $anatomogram = $(this.refs.anatomogram.getDOMNode());
	            $anatomogram.hcSticky({responsive: true});
	        }
	    }
	
	});
	
	
	var HeatmapContainer = React.createClass({displayName: "HeatmapContainer",
	
	    componentDidMount: function() {
	        var _gaq = _gaq || [];
	        _gaq.push(['_setAccount', 'UA-37676851-1']);
	        _gaq.push(['_trackPageview']);
	        (function () {
	            var ga = document.createElement('script');
	            ga.type = 'text/javascript';
	            ga.async = true;
	            ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
	            var s = document.getElementsByTagName('script')[0];
	            s.parentNode.insertBefore(ga, s);
	        })();
	    },
	
	    render: function () {
	        var Heatmap = this.props.Heatmap;
	        var heatmapClass = this.props.heatmapClass ? this.props.heatmapClass : "gxaHeatmapPosition";
	
	        var heatmapConfig = this.props.heatmapConfig;
	
	        var geneURL = heatmapConfig.contextRoot +  'query?geneQuery=' + heatmapConfig.geneQuery + '&exactMatch=' + heatmapConfig.isExactMatch + "&organism=" + heatmapConfig.species;
	        var normalizedGeneURL = URI(geneURL).normalize();
	
	        return (
	                React.createElement("div", {className: "gxaBlock"}, 
	
	                     this.props.experiment ? React.createElement(ExperimentDescription, {experiment: this.props.experiment}) : null, 
	
	                    React.createElement("div", {id: "heatmap-anatomogram", className: "gxaHeatmapAnatomogramRow"}, 
	
	                         this.props.anatomogram ? React.createElement(Anatomogram, {anatomogram: this.props.anatomogram, heatmapKey: this.props.heatmapKey}) : null, 
	
	                        React.createElement("div", {id: "ensembl-launcher", className: "gxaAside", style: {"display":"inline"}}), 
	
	                        React.createElement("div", {id: "heatmap-react", className: heatmapClass}, 
	                            React.createElement(Heatmap, {columnHeaders: this.props.columnHeaders, profiles: this.props.profiles, geneSetProfiles: this.props.geneSetProfiles, isWidget: this.props.isWidget})
	                        ), 
	
	                        /* TODO move into help tooltips module */
	                        React.createElement("div", {id: "help-placeholder", style: {display: "none"}})
	
	                    ), 
	
	                     !this.props.heatmapClass ? React.createElement("div", null, React.createElement("p", null, React.createElement("a", {href: normalizedGeneURL}, "See more expression data at Expression Atlas."), 
	                        React.createElement("br", null), "This expression view is provided by ", React.createElement("a", {href: "http://www.ebi.ac.uk/gxa"}, "Expression Atlas"), ".", 
	                        React.createElement("br", null), "Please direct any queries or feedback to ", React.createElement("a", {href: "mailto:arrayexpress-atlas@ebi.ac.uk"}, "arrayexpress-atlas@ebi.ac.uk"))) : null
	
	                )
	        );
	    }
	});
	
	//*------------------------------------------------------------------*
	
	module.exports = HeatmapContainer;

/***/ }

});
//# sourceMappingURL=expression-atlas-heatmap.bundle.js.map