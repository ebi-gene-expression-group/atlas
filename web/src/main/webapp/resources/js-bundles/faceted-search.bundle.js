webpackJsonp([1],[
/* 0 */
/*!*********************************!*\
  !*** ./faceted-search/index.js ***!
  \*********************************/
/***/ function(module, exports, __webpack_require__) {

	/* WEBPACK VAR INJECTION */(function(global) {module.exports = global["exposed"] = __webpack_require__(/*! -!./faceted-search/index.js */ 374);
	/* WEBPACK VAR INJECTION */}.call(exports, (function() { return this; }())))

/***/ },
/* 1 */
/*!**************************!*\
  !*** ./heatmap/index.js ***!
  \**************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	module.exports = __webpack_require__(/*! ./src/heatmap-anatomogram.js */ 2);

/***/ },
/* 2 */
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
/* 3 */,
/* 4 */,
/* 5 */,
/* 6 */,
/* 7 */,
/* 8 */,
/* 9 */,
/* 10 */,
/* 11 */,
/* 12 */,
/* 13 */,
/* 14 */,
/* 15 */,
/* 16 */,
/* 17 */,
/* 18 */,
/* 19 */,
/* 20 */,
/* 21 */,
/* 22 */,
/* 23 */,
/* 24 */,
/* 25 */,
/* 26 */,
/* 27 */,
/* 28 */,
/* 29 */,
/* 30 */,
/* 31 */,
/* 32 */,
/* 33 */,
/* 34 */,
/* 35 */,
/* 36 */,
/* 37 */,
/* 38 */,
/* 39 */,
/* 40 */,
/* 41 */,
/* 42 */,
/* 43 */,
/* 44 */,
/* 45 */,
/* 46 */,
/* 47 */,
/* 48 */,
/* 49 */,
/* 50 */,
/* 51 */,
/* 52 */,
/* 53 */,
/* 54 */,
/* 55 */,
/* 56 */,
/* 57 */,
/* 58 */,
/* 59 */,
/* 60 */,
/* 61 */,
/* 62 */,
/* 63 */,
/* 64 */,
/* 65 */,
/* 66 */,
/* 67 */,
/* 68 */,
/* 69 */,
/* 70 */,
/* 71 */,
/* 72 */,
/* 73 */,
/* 74 */,
/* 75 */,
/* 76 */,
/* 77 */,
/* 78 */,
/* 79 */,
/* 80 */,
/* 81 */,
/* 82 */,
/* 83 */,
/* 84 */,
/* 85 */,
/* 86 */,
/* 87 */,
/* 88 */,
/* 89 */,
/* 90 */,
/* 91 */,
/* 92 */,
/* 93 */,
/* 94 */,
/* 95 */,
/* 96 */,
/* 97 */,
/* 98 */,
/* 99 */,
/* 100 */,
/* 101 */,
/* 102 */,
/* 103 */,
/* 104 */,
/* 105 */,
/* 106 */,
/* 107 */,
/* 108 */,
/* 109 */,
/* 110 */,
/* 111 */,
/* 112 */,
/* 113 */,
/* 114 */,
/* 115 */,
/* 116 */,
/* 117 */,
/* 118 */,
/* 119 */,
/* 120 */,
/* 121 */,
/* 122 */,
/* 123 */,
/* 124 */,
/* 125 */,
/* 126 */,
/* 127 */,
/* 128 */,
/* 129 */,
/* 130 */,
/* 131 */,
/* 132 */,
/* 133 */,
/* 134 */,
/* 135 */,
/* 136 */,
/* 137 */,
/* 138 */,
/* 139 */,
/* 140 */,
/* 141 */,
/* 142 */,
/* 143 */,
/* 144 */,
/* 145 */,
/* 146 */,
/* 147 */,
/* 148 */,
/* 149 */,
/* 150 */,
/* 151 */,
/* 152 */,
/* 153 */,
/* 154 */,
/* 155 */,
/* 156 */,
/* 157 */,
/* 158 */,
/* 159 */,
/* 160 */,
/* 161 */,
/* 162 */,
/* 163 */,
/* 164 */,
/* 165 */,
/* 166 */,
/* 167 */,
/* 168 */,
/* 169 */,
/* 170 */,
/* 171 */,
/* 172 */,
/* 173 */,
/* 174 */,
/* 175 */,
/* 176 */,
/* 177 */,
/* 178 */,
/* 179 */,
/* 180 */,
/* 181 */,
/* 182 */,
/* 183 */,
/* 184 */,
/* 185 */,
/* 186 */,
/* 187 */,
/* 188 */,
/* 189 */,
/* 190 */,
/* 191 */,
/* 192 */,
/* 193 */,
/* 194 */,
/* 195 */,
/* 196 */,
/* 197 */,
/* 198 */,
/* 199 */,
/* 200 */,
/* 201 */,
/* 202 */,
/* 203 */,
/* 204 */,
/* 205 */,
/* 206 */,
/* 207 */,
/* 208 */,
/* 209 */,
/* 210 */,
/* 211 */,
/* 212 */,
/* 213 */,
/* 214 */,
/* 215 */,
/* 216 */,
/* 217 */,
/* 218 */,
/* 219 */,
/* 220 */,
/* 221 */,
/* 222 */,
/* 223 */,
/* 224 */,
/* 225 */,
/* 226 */,
/* 227 */,
/* 228 */,
/* 229 */,
/* 230 */,
/* 231 */,
/* 232 */,
/* 233 */,
/* 234 */,
/* 235 */,
/* 236 */,
/* 237 */,
/* 238 */,
/* 239 */,
/* 240 */,
/* 241 */,
/* 242 */,
/* 243 */,
/* 244 */,
/* 245 */,
/* 246 */,
/* 247 */,
/* 248 */,
/* 249 */,
/* 250 */,
/* 251 */,
/* 252 */,
/* 253 */,
/* 254 */,
/* 255 */,
/* 256 */,
/* 257 */,
/* 258 */,
/* 259 */,
/* 260 */,
/* 261 */,
/* 262 */,
/* 263 */,
/* 264 */,
/* 265 */,
/* 266 */,
/* 267 */,
/* 268 */,
/* 269 */,
/* 270 */,
/* 271 */,
/* 272 */,
/* 273 */,
/* 274 */,
/* 275 */,
/* 276 */,
/* 277 */,
/* 278 */,
/* 279 */,
/* 280 */,
/* 281 */,
/* 282 */,
/* 283 */,
/* 284 */,
/* 285 */,
/* 286 */,
/* 287 */,
/* 288 */,
/* 289 */,
/* 290 */,
/* 291 */,
/* 292 */,
/* 293 */,
/* 294 */,
/* 295 */,
/* 296 */,
/* 297 */,
/* 298 */,
/* 299 */,
/* 300 */,
/* 301 */,
/* 302 */,
/* 303 */,
/* 304 */,
/* 305 */,
/* 306 */,
/* 307 */,
/* 308 */,
/* 309 */,
/* 310 */,
/* 311 */,
/* 312 */,
/* 313 */,
/* 314 */,
/* 315 */,
/* 316 */,
/* 317 */,
/* 318 */,
/* 319 */,
/* 320 */,
/* 321 */,
/* 322 */,
/* 323 */,
/* 324 */,
/* 325 */,
/* 326 */,
/* 327 */,
/* 328 */,
/* 329 */,
/* 330 */,
/* 331 */,
/* 332 */,
/* 333 */,
/* 334 */,
/* 335 */,
/* 336 */,
/* 337 */,
/* 338 */,
/* 339 */,
/* 340 */,
/* 341 */,
/* 342 */,
/* 343 */,
/* 344 */,
/* 345 */,
/* 346 */,
/* 347 */,
/* 348 */,
/* 349 */,
/* 350 */,
/* 351 */,
/* 352 */,
/* 353 */,
/* 354 */,
/* 355 */,
/* 356 */,
/* 357 */,
/* 358 */,
/* 359 */,
/* 360 */,
/* 361 */,
/* 362 */,
/* 363 */,
/* 364 */,
/* 365 */,
/* 366 */,
/* 367 */,
/* 368 */,
/* 369 */,
/* 370 */
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
/* 371 */
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

/***/ },
/* 372 */,
/* 373 */,
/* 374 */
/*!*********************************!*\
  !*** ./faceted-search/index.js ***!
  \*********************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	exports.baseline = __webpack_require__(/*! ./src/baseline-heatmaps-router.js */ 375);
	
	exports.differential = __webpack_require__(/*! ./src/differential-router.js */ 538);


/***/ },
/* 375 */
/*!********************************************************!*\
  !*** ./faceted-search/src/baseline-heatmaps-router.js ***!
  \********************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	var React = __webpack_require__(/*! react */ 376);
	var $ = __webpack_require__(/*! jquery */ 522);
	var jQuery = $;
	__webpack_require__(/*! jquery.browser */ 523);
	
	var queryString = __webpack_require__(/*! query-string */ 524);
	
	var URI = __webpack_require__(/*! URIjs */ 526);
	
	//*------------------------------------------------------------------*
	
	var FacetsTree = __webpack_require__(/*! ./facets-tree.jsx */ 530);
	var Heatmaps = __webpack_require__(/*! ./baseline-heatmaps.jsx */ 535);
	
	//*------------------------------------------------------------------*
	
	module.exports = function (facetsContainerId, heatmapsConatinerId, facetsTreeData, atlasHost) {
	
	    var facetsElement = document.getElementById(facetsContainerId),
	        heatmapsElement = document.getElementById(heatmapsConatinerId);
	
	    //TODO: add this outside the module, when module is first loaded
	    var ie9 = $.browser.msie && $.browser.version < 10;
	    if (!ie9) {
	        window.addEventListener('popstate', renderPage, false);
	    }
	
	    renderPage();
	
	    function renderPage() {
	        var query = queryString.parse(window.location.search.slice(1));
	        query.select = query.select && JSON.parse(query.select);
	        render(query);
	    }
	
	    function render(query) {
	        var host = atlasHost ? atlasHost : window.location.host;
	
	        React.render(
	            React.createElement(FacetsTree, {facets: facetsTreeData, checkedFacets: query.select, setChecked: setChecked}),
	            facetsElement);
	
	        React.render(React.createElement(Heatmaps, {geneQuery: query.geneQuery, heatmaps: queryToHeatmaps(query), host: host}),
	            heatmapsElement
	        );
	
	        function setChecked(checked, species, factor) {
	            var newSelect = checked ? addSelection(query.select, species, factor) : removeSelection(query.select, species, factor);
	            var newQueryString = new URI("").search({geneQuery: query.geneQuery, select: JSON.stringify(newSelect)}).normalize();
	            navigateTo(newQueryString);
	        }
	
	        function navigateTo(newQueryString) {
	            var state, title;
	            if (ie9) {
	                window.location.search = newQueryString;
	            } else {
	                history.pushState(null, null, window.location.pathname + newQueryString);
	                renderPage();
	            }
	        }
	
	        function addSelection(select, species, factor) {
	            if (!select) {
	                select = {};
	            }
	
	            if (!select[species]) {
	                select[species] = {};
	            }
	            select[species][factor] = true;
	            return select;
	        }
	
	        function removeSelection(select, species, factor) {
	            select[species][factor] = false;
	            return select;
	        }
	
	    }
	
	    function queryToHeatmaps(query) {
	        /* eg:
	         query.geneQuery=blood
	         query.select={ "homo sapiens" : { "CELL_LINE": true, "ORGANISM_PART": true } }
	
	         ->
	
	         [
	            {"geneQuery": "blood",
	             "species": "Homo sapiens",
	             "factor": "ORGANISM_PART"},
	
	            {"geneQuery": "blood",
	             "species": "Homo sapiens",
	             "factor": "CELL_LINE"}
	         ]
	        */
	        var select = query.select;
	        var geneQuery = query.geneQuery;
	        var heatmaps = [];
	
	        for (var species in select) {
	            if (select.hasOwnProperty(species)) {
	
	                var factors = select[species];
	
	                for (var factor in factors) {
	                    if (factors.hasOwnProperty(factor)) {
	                        if (factors[factor]) {
	                            heatmaps.push({
	                                "geneQuery": geneQuery,
	                                "species": species,
	                                "factor": factor
	                            });
	                        }
	                    }
	                }
	            }
	        }
	
	        return heatmaps;
	    }
	
	};
	


/***/ },
/* 376 */
/*!*****************************************!*\
  !*** ./faceted-search/~/react/react.js ***!
  \*****************************************/
[695, 377],
/* 377 */
/*!*********************************************!*\
  !*** ./faceted-search/~/react/lib/React.js ***!
  \*********************************************/
[696, 378, 381, 387, 390, 397, 404, 394, 396, 393, 405, 419, 420, 449, 410, 430, 445, 403, 494, 519, 448, 395, 443, 521, 423],
/* 378 */
/*!************************************************************!*\
  !*** ./faceted-search/~/react/lib/ReactInstanceHandles.js ***!
  \************************************************************/
[697, 379, 380],
/* 379 */
/*!******************************************************!*\
  !*** ./faceted-search/~/react/lib/ReactRootIndex.js ***!
  \******************************************************/
8,
/* 380 */
/*!*************************************************!*\
  !*** ./faceted-search/~/react/lib/invariant.js ***!
  \*************************************************/
9,
/* 381 */
/*!*************************************************************!*\
  !*** ./faceted-search/~/react/lib/DOMPropertyOperations.js ***!
  \*************************************************************/
[698, 382, 383, 384, 385],
/* 382 */
/*!***************************************************!*\
  !*** ./faceted-search/~/react/lib/DOMProperty.js ***!
  \***************************************************/
[699, 380],
/* 383 */
/*!************************************************************!*\
  !*** ./faceted-search/~/react/lib/escapeTextForBrowser.js ***!
  \************************************************************/
12,
/* 384 */
/*!*********************************************************!*\
  !*** ./faceted-search/~/react/lib/memoizeStringOnly.js ***!
  \*********************************************************/
13,
/* 385 */
/*!***********************************************!*\
  !*** ./faceted-search/~/react/lib/warning.js ***!
  \***********************************************/
[700, 386],
/* 386 */
/*!*****************************************************!*\
  !*** ./faceted-search/~/react/lib/emptyFunction.js ***!
  \*****************************************************/
15,
/* 387 */
/*!********************************************************!*\
  !*** ./faceted-search/~/react/lib/EventPluginUtils.js ***!
  \********************************************************/
[701, 388, 380],
/* 388 */
/*!******************************************************!*\
  !*** ./faceted-search/~/react/lib/EventConstants.js ***!
  \******************************************************/
[702, 389],
/* 389 */
/*!*************************************************!*\
  !*** ./faceted-search/~/react/lib/keyMirror.js ***!
  \*************************************************/
[703, 380],
/* 390 */
/*!*****************************************************!*\
  !*** ./faceted-search/~/react/lib/ReactChildren.js ***!
  \*****************************************************/
[704, 391, 392, 385],
/* 391 */
/*!***************************************************!*\
  !*** ./faceted-search/~/react/lib/PooledClass.js ***!
  \***************************************************/
[705, 380],
/* 392 */
/*!***********************************************************!*\
  !*** ./faceted-search/~/react/lib/traverseAllChildren.js ***!
  \***********************************************************/
[706, 393, 378, 380],
/* 393 */
/*!****************************************************!*\
  !*** ./faceted-search/~/react/lib/ReactElement.js ***!
  \****************************************************/
[707, 394, 396, 385],
/* 394 */
/*!****************************************************!*\
  !*** ./faceted-search/~/react/lib/ReactContext.js ***!
  \****************************************************/
[708, 395],
/* 395 */
/*!*****************************************************!*\
  !*** ./faceted-search/~/react/lib/Object.assign.js ***!
  \*****************************************************/
24,
/* 396 */
/*!*********************************************************!*\
  !*** ./faceted-search/~/react/lib/ReactCurrentOwner.js ***!
  \*********************************************************/
25,
/* 397 */
/*!******************************************************!*\
  !*** ./faceted-search/~/react/lib/ReactComponent.js ***!
  \******************************************************/
[709, 395, 393, 398, 400, 380, 389],
/* 398 */
/*!**************************************************!*\
  !*** ./faceted-search/~/react/lib/ReactOwner.js ***!
  \**************************************************/
[710, 399, 380],
/* 399 */
/*!***************************************************!*\
  !*** ./faceted-search/~/react/lib/emptyObject.js ***!
  \***************************************************/
28,
/* 400 */
/*!****************************************************!*\
  !*** ./faceted-search/~/react/lib/ReactUpdates.js ***!
  \****************************************************/
[711, 401, 402, 391, 396, 403, 395, 380, 385],
/* 401 */
/*!***************************************************!*\
  !*** ./faceted-search/~/react/lib/Transaction.js ***!
  \***************************************************/
[712, 380],
/* 402 */
/*!*****************************************************!*\
  !*** ./faceted-search/~/react/lib/CallbackQueue.js ***!
  \*****************************************************/
[713, 391, 395, 380],
/* 403 */
/*!*************************************************!*\
  !*** ./faceted-search/~/react/lib/ReactPerf.js ***!
  \*************************************************/
32,
/* 404 */
/*!***************************************************************!*\
  !*** ./faceted-search/~/react/lib/ReactCompositeComponent.js ***!
  \***************************************************************/
[714, 397, 394, 396, 393, 405, 408, 409, 410, 398, 403, 411, 406, 413, 400, 395, 414, 380, 389, 416, 407, 417, 418, 385],
/* 405 */
/*!*************************************************************!*\
  !*** ./faceted-search/~/react/lib/ReactElementValidator.js ***!
  \*************************************************************/
[715, 393, 406, 396, 407, 385],
/* 406 */
/*!**************************************************************!*\
  !*** ./faceted-search/~/react/lib/ReactPropTypeLocations.js ***!
  \**************************************************************/
[716, 389],
/* 407 */
/*!******************************************************!*\
  !*** ./faceted-search/~/react/lib/monitorCodeUse.js ***!
  \******************************************************/
[717, 380],
/* 408 */
/*!***********************************************************!*\
  !*** ./faceted-search/~/react/lib/ReactEmptyComponent.js ***!
  \***********************************************************/
[718, 393, 380],
/* 409 */
/*!*******************************************************!*\
  !*** ./faceted-search/~/react/lib/ReactErrorUtils.js ***!
  \*******************************************************/
38,
/* 410 */
/*!**********************************************************!*\
  !*** ./faceted-search/~/react/lib/ReactLegacyElement.js ***!
  \**********************************************************/
[719, 396, 380, 407, 385],
/* 411 */
/*!***********************************************************!*\
  !*** ./faceted-search/~/react/lib/ReactPropTransferer.js ***!
  \***********************************************************/
[720, 395, 386, 380, 412, 385],
/* 412 */
/*!***************************************************!*\
  !*** ./faceted-search/~/react/lib/joinClasses.js ***!
  \***************************************************/
41,
/* 413 */
/*!******************************************************************!*\
  !*** ./faceted-search/~/react/lib/ReactPropTypeLocationNames.js ***!
  \******************************************************************/
42,
/* 414 */
/*!*****************************************************************!*\
  !*** ./faceted-search/~/react/lib/instantiateReactComponent.js ***!
  \*****************************************************************/
[721, 385, 393, 410, 415, 408],
/* 415 */
/*!************************************************************!*\
  !*** ./faceted-search/~/react/lib/ReactNativeComponent.js ***!
  \************************************************************/
[722, 395, 380],
/* 416 */
/*!*********************************************!*\
  !*** ./faceted-search/~/react/lib/keyOf.js ***!
  \*********************************************/
45,
/* 417 */
/*!*************************************************!*\
  !*** ./faceted-search/~/react/lib/mapObject.js ***!
  \*************************************************/
46,
/* 418 */
/*!******************************************************************!*\
  !*** ./faceted-search/~/react/lib/shouldUpdateReactComponent.js ***!
  \******************************************************************/
47,
/* 419 */
/*!************************************************!*\
  !*** ./faceted-search/~/react/lib/ReactDOM.js ***!
  \************************************************/
[723, 393, 405, 410, 417],
/* 420 */
/*!*********************************************************!*\
  !*** ./faceted-search/~/react/lib/ReactDOMComponent.js ***!
  \*********************************************************/
[724, 421, 382, 381, 429, 397, 434, 430, 445, 403, 395, 383, 380, 442, 416, 407],
/* 421 */
/*!*************************************************************!*\
  !*** ./faceted-search/~/react/lib/CSSPropertyOperations.js ***!
  \*************************************************************/
[725, 422, 423, 424, 426, 427, 384, 385],
/* 422 */
/*!***************************************************!*\
  !*** ./faceted-search/~/react/lib/CSSProperty.js ***!
  \***************************************************/
51,
/* 423 */
/*!************************************************************!*\
  !*** ./faceted-search/~/react/lib/ExecutionEnvironment.js ***!
  \************************************************************/
52,
/* 424 */
/*!*********************************************************!*\
  !*** ./faceted-search/~/react/lib/camelizeStyleName.js ***!
  \*********************************************************/
[726, 425],
/* 425 */
/*!************************************************!*\
  !*** ./faceted-search/~/react/lib/camelize.js ***!
  \************************************************/
54,
/* 426 */
/*!***********************************************************!*\
  !*** ./faceted-search/~/react/lib/dangerousStyleValue.js ***!
  \***********************************************************/
[727, 422],
/* 427 */
/*!**********************************************************!*\
  !*** ./faceted-search/~/react/lib/hyphenateStyleName.js ***!
  \**********************************************************/
[728, 428],
/* 428 */
/*!*************************************************!*\
  !*** ./faceted-search/~/react/lib/hyphenate.js ***!
  \*************************************************/
57,
/* 429 */
/*!******************************************************************!*\
  !*** ./faceted-search/~/react/lib/ReactBrowserComponentMixin.js ***!
  \******************************************************************/
[729, 408, 430, 380],
/* 430 */
/*!**************************************************!*\
  !*** ./faceted-search/~/react/lib/ReactMount.js ***!
  \**************************************************/
[730, 431, 382, 434, 396, 393, 410, 378, 403, 443, 444, 414, 380, 418, 385],
/* 431 */
/*!****************************************************!*\
  !*** ./faceted-search/~/react/lib/containsNode.js ***!
  \****************************************************/
[731, 432],
/* 432 */
/*!**************************************************!*\
  !*** ./faceted-search/~/react/lib/isTextNode.js ***!
  \**************************************************/
[732, 433],
/* 433 */
/*!**********************************************!*\
  !*** ./faceted-search/~/react/lib/isNode.js ***!
  \**********************************************/
62,
/* 434 */
/*!****************************************************************!*\
  !*** ./faceted-search/~/react/lib/ReactBrowserEventEmitter.js ***!
  \****************************************************************/
[733, 388, 435, 436, 439, 440, 395, 442],
/* 435 */
/*!******************************************************!*\
  !*** ./faceted-search/~/react/lib/EventPluginHub.js ***!
  \******************************************************/
[734, 436, 387, 437, 438, 380],
/* 436 */
/*!***********************************************************!*\
  !*** ./faceted-search/~/react/lib/EventPluginRegistry.js ***!
  \***********************************************************/
[735, 380],
/* 437 */
/*!******************************************************!*\
  !*** ./faceted-search/~/react/lib/accumulateInto.js ***!
  \******************************************************/
[736, 380],
/* 438 */
/*!**********************************************************!*\
  !*** ./faceted-search/~/react/lib/forEachAccumulated.js ***!
  \**********************************************************/
67,
/* 439 */
/*!**************************************************************!*\
  !*** ./faceted-search/~/react/lib/ReactEventEmitterMixin.js ***!
  \**************************************************************/
[737, 435],
/* 440 */
/*!*******************************************************!*\
  !*** ./faceted-search/~/react/lib/ViewportMetrics.js ***!
  \*******************************************************/
[738, 441],
/* 441 */
/*!******************************************************************!*\
  !*** ./faceted-search/~/react/lib/getUnboundedScrollPosition.js ***!
  \******************************************************************/
70,
/* 442 */
/*!********************************************************!*\
  !*** ./faceted-search/~/react/lib/isEventSupported.js ***!
  \********************************************************/
[739, 423],
/* 443 */
/*!**************************************************!*\
  !*** ./faceted-search/~/react/lib/deprecated.js ***!
  \**************************************************/
[740, 395, 385],
/* 444 */
/*!**********************************************************************!*\
  !*** ./faceted-search/~/react/lib/getReactRootElementInContainer.js ***!
  \**********************************************************************/
73,
/* 445 */
/*!*******************************************************!*\
  !*** ./faceted-search/~/react/lib/ReactMultiChild.js ***!
  \*******************************************************/
[741, 397, 446, 447, 414, 418],
/* 446 */
/*!******************************************************************!*\
  !*** ./faceted-search/~/react/lib/ReactMultiChildUpdateTypes.js ***!
  \******************************************************************/
[742, 389],
/* 447 */
/*!*******************************************************!*\
  !*** ./faceted-search/~/react/lib/flattenChildren.js ***!
  \*******************************************************/
[743, 448, 392, 385],
/* 448 */
/*!**********************************************************!*\
  !*** ./faceted-search/~/react/lib/ReactTextComponent.js ***!
  \**********************************************************/
[744, 381, 397, 393, 395, 383],
/* 449 */
/*!*************************************************************!*\
  !*** ./faceted-search/~/react/lib/ReactDefaultInjection.js ***!
  \*************************************************************/
[745, 450, 452, 457, 459, 460, 468, 469, 423, 473, 474, 429, 475, 488, 420, 489, 491, 492, 495, 496, 497, 498, 500, 378, 430, 501, 503, 504, 513, 514, 515],
/* 450 */
/*!***************************************************!*\
  !*** ./faceted-search/~/react/lib/ReactDOMImg.js ***!
  \***************************************************/
[746, 404, 388, 451, 429, 393, 419],
/* 451 */
/*!***********************************************************!*\
  !*** ./faceted-search/~/react/lib/LocalEventTrapMixin.js ***!
  \***********************************************************/
[747, 434, 437, 438, 380],
/* 452 */
/*!**************************************************************!*\
  !*** ./faceted-search/~/react/lib/BeforeInputEventPlugin.js ***!
  \**************************************************************/
[748, 388, 453, 423, 454, 416],
/* 453 */
/*!********************************************************!*\
  !*** ./faceted-search/~/react/lib/EventPropagators.js ***!
  \********************************************************/
[749, 388, 435, 437, 438],
/* 454 */
/*!***********************************************************!*\
  !*** ./faceted-search/~/react/lib/SyntheticInputEvent.js ***!
  \***********************************************************/
[750, 455],
/* 455 */
/*!******************************************************!*\
  !*** ./faceted-search/~/react/lib/SyntheticEvent.js ***!
  \******************************************************/
[751, 391, 395, 386, 456],
/* 456 */
/*!******************************************************!*\
  !*** ./faceted-search/~/react/lib/getEventTarget.js ***!
  \******************************************************/
85,
/* 457 */
/*!*********************************************************!*\
  !*** ./faceted-search/~/react/lib/ChangeEventPlugin.js ***!
  \*********************************************************/
[752, 388, 435, 453, 423, 400, 455, 442, 458, 416],
/* 458 */
/*!**********************************************************!*\
  !*** ./faceted-search/~/react/lib/isTextInputElement.js ***!
  \**********************************************************/
87,
/* 459 */
/*!************************************************************!*\
  !*** ./faceted-search/~/react/lib/ClientReactRootIndex.js ***!
  \************************************************************/
88,
/* 460 */
/*!**************************************************************!*\
  !*** ./faceted-search/~/react/lib/CompositionEventPlugin.js ***!
  \**************************************************************/
[753, 388, 453, 423, 461, 467, 464, 416],
/* 461 */
/*!***********************************************************!*\
  !*** ./faceted-search/~/react/lib/ReactInputSelection.js ***!
  \***********************************************************/
[754, 462, 431, 465, 466],
/* 462 */
/*!*********************************************************!*\
  !*** ./faceted-search/~/react/lib/ReactDOMSelection.js ***!
  \*********************************************************/
[755, 423, 463, 464],
/* 463 */
/*!*****************************************************************!*\
  !*** ./faceted-search/~/react/lib/getNodeForCharacterOffset.js ***!
  \*****************************************************************/
92,
/* 464 */
/*!**************************************************************!*\
  !*** ./faceted-search/~/react/lib/getTextContentAccessor.js ***!
  \**************************************************************/
[756, 423],
/* 465 */
/*!*************************************************!*\
  !*** ./faceted-search/~/react/lib/focusNode.js ***!
  \*************************************************/
94,
/* 466 */
/*!********************************************************!*\
  !*** ./faceted-search/~/react/lib/getActiveElement.js ***!
  \********************************************************/
95,
/* 467 */
/*!*****************************************************************!*\
  !*** ./faceted-search/~/react/lib/SyntheticCompositionEvent.js ***!
  \*****************************************************************/
[757, 455],
/* 468 */
/*!***************************************************************!*\
  !*** ./faceted-search/~/react/lib/DefaultEventPluginOrder.js ***!
  \***************************************************************/
[758, 416],
/* 469 */
/*!*************************************************************!*\
  !*** ./faceted-search/~/react/lib/EnterLeaveEventPlugin.js ***!
  \*************************************************************/
[759, 388, 453, 470, 430, 416],
/* 470 */
/*!***********************************************************!*\
  !*** ./faceted-search/~/react/lib/SyntheticMouseEvent.js ***!
  \***********************************************************/
[760, 471, 440, 472],
/* 471 */
/*!********************************************************!*\
  !*** ./faceted-search/~/react/lib/SyntheticUIEvent.js ***!
  \********************************************************/
[761, 455, 456],
/* 472 */
/*!*************************************************************!*\
  !*** ./faceted-search/~/react/lib/getEventModifierState.js ***!
  \*************************************************************/
101,
/* 473 */
/*!*************************************************************!*\
  !*** ./faceted-search/~/react/lib/HTMLDOMPropertyConfig.js ***!
  \*************************************************************/
[762, 382, 423],
/* 474 */
/*!********************************************************************!*\
  !*** ./faceted-search/~/react/lib/MobileSafariClickEventPlugin.js ***!
  \********************************************************************/
[763, 388, 386],
/* 475 */
/*!************************************************************************!*\
  !*** ./faceted-search/~/react/lib/ReactComponentBrowserEnvironment.js ***!
  \************************************************************************/
[764, 476, 478, 486, 430, 403, 444, 380, 485],
/* 476 */
/*!*****************************************************************!*\
  !*** ./faceted-search/~/react/lib/ReactReconcileTransaction.js ***!
  \*****************************************************************/
[765, 402, 391, 434, 461, 477, 401, 395],
/* 477 */
/*!*************************************************************!*\
  !*** ./faceted-search/~/react/lib/ReactPutListenerQueue.js ***!
  \*************************************************************/
[766, 391, 434, 395],
/* 478 */
/*!************************************************************!*\
  !*** ./faceted-search/~/react/lib/ReactDOMIDOperations.js ***!
  \************************************************************/
[767, 421, 479, 381, 430, 403, 380, 485],
/* 479 */
/*!*************************************************************!*\
  !*** ./faceted-search/~/react/lib/DOMChildrenOperations.js ***!
  \*************************************************************/
[768, 480, 446, 464, 380],
/* 480 */
/*!**********************************************!*\
  !*** ./faceted-search/~/react/lib/Danger.js ***!
  \**********************************************/
[769, 423, 481, 386, 484, 380],
/* 481 */
/*!*************************************************************!*\
  !*** ./faceted-search/~/react/lib/createNodesFromMarkup.js ***!
  \*************************************************************/
[770, 423, 482, 484, 380],
/* 482 */
/*!*******************************************************!*\
  !*** ./faceted-search/~/react/lib/createArrayFrom.js ***!
  \*******************************************************/
[771, 483],
/* 483 */
/*!***********************************************!*\
  !*** ./faceted-search/~/react/lib/toArray.js ***!
  \***********************************************/
[772, 380],
/* 484 */
/*!*****************************************************!*\
  !*** ./faceted-search/~/react/lib/getMarkupWrap.js ***!
  \*****************************************************/
[773, 423, 380],
/* 485 */
/*!****************************************************!*\
  !*** ./faceted-search/~/react/lib/setInnerHTML.js ***!
  \****************************************************/
[774, 423],
/* 486 */
/*!***********************************************************!*\
  !*** ./faceted-search/~/react/lib/ReactMarkupChecksum.js ***!
  \***********************************************************/
[775, 487],
/* 487 */
/*!***********************************************!*\
  !*** ./faceted-search/~/react/lib/adler32.js ***!
  \***********************************************/
116,
/* 488 */
/*!********************************************************************!*\
  !*** ./faceted-search/~/react/lib/ReactDefaultBatchingStrategy.js ***!
  \********************************************************************/
[776, 400, 401, 395, 386],
/* 489 */
/*!******************************************************!*\
  !*** ./faceted-search/~/react/lib/ReactDOMButton.js ***!
  \******************************************************/
[777, 393, 490, 429, 404, 419, 389],
/* 490 */
/*!******************************************************!*\
  !*** ./faceted-search/~/react/lib/AutoFocusMixin.js ***!
  \******************************************************/
[778, 465],
/* 491 */
/*!****************************************************!*\
  !*** ./faceted-search/~/react/lib/ReactDOMForm.js ***!
  \****************************************************/
[779, 404, 388, 451, 429, 393, 419],
/* 492 */
/*!*****************************************************!*\
  !*** ./faceted-search/~/react/lib/ReactDOMInput.js ***!
  \*****************************************************/
[780, 490, 381, 493, 429, 404, 393, 419, 430, 400, 395, 380],
/* 493 */
/*!********************************************************!*\
  !*** ./faceted-search/~/react/lib/LinkedValueUtils.js ***!
  \********************************************************/
[781, 494, 380],
/* 494 */
/*!******************************************************!*\
  !*** ./faceted-search/~/react/lib/ReactPropTypes.js ***!
  \******************************************************/
[782, 393, 413, 443, 386],
/* 495 */
/*!******************************************************!*\
  !*** ./faceted-search/~/react/lib/ReactDOMOption.js ***!
  \******************************************************/
[783, 429, 404, 393, 419, 385],
/* 496 */
/*!******************************************************!*\
  !*** ./faceted-search/~/react/lib/ReactDOMSelect.js ***!
  \******************************************************/
[784, 393, 490, 493, 429, 404, 419, 400, 395],
/* 497 */
/*!********************************************************!*\
  !*** ./faceted-search/~/react/lib/ReactDOMTextarea.js ***!
  \********************************************************/
[785, 490, 381, 493, 429, 404, 393, 419, 400, 395, 380, 385],
/* 498 */
/*!**********************************************************!*\
  !*** ./faceted-search/~/react/lib/ReactEventListener.js ***!
  \**********************************************************/
[786, 499, 423, 391, 378, 430, 400, 395, 456, 441],
/* 499 */
/*!*****************************************************!*\
  !*** ./faceted-search/~/react/lib/EventListener.js ***!
  \*****************************************************/
[787, 386],
/* 500 */
/*!******************************************************!*\
  !*** ./faceted-search/~/react/lib/ReactInjection.js ***!
  \******************************************************/
[788, 434, 382, 435, 397, 404, 408, 415, 403, 379, 400],
/* 501 */
/*!*********************************************************!*\
  !*** ./faceted-search/~/react/lib/SelectEventPlugin.js ***!
  \*********************************************************/
[789, 466, 388, 453, 461, 455, 458, 416, 502],
/* 502 */
/*!****************************************************!*\
  !*** ./faceted-search/~/react/lib/shallowEqual.js ***!
  \****************************************************/
131,
/* 503 */
/*!************************************************************!*\
  !*** ./faceted-search/~/react/lib/ServerReactRootIndex.js ***!
  \************************************************************/
132,
/* 504 */
/*!*********************************************************!*\
  !*** ./faceted-search/~/react/lib/SimpleEventPlugin.js ***!
  \*********************************************************/
[790, 505, 388, 387, 453, 506, 455, 507, 508, 470, 511, 471, 512, 509, 380, 416, 385],
/* 505 */
/*!**********************************************************!*\
  !*** ./faceted-search/~/react/lib/SyntheticDragEvent.js ***!
  \**********************************************************/
[791, 470],
/* 506 */
/*!***************************************************************!*\
  !*** ./faceted-search/~/react/lib/SyntheticClipboardEvent.js ***!
  \***************************************************************/
[792, 455],
/* 507 */
/*!***********************************************************!*\
  !*** ./faceted-search/~/react/lib/SyntheticFocusEvent.js ***!
  \***********************************************************/
[793, 471],
/* 508 */
/*!**************************************************************!*\
  !*** ./faceted-search/~/react/lib/SyntheticKeyboardEvent.js ***!
  \**************************************************************/
[794, 471, 509, 510, 472],
/* 509 */
/*!********************************************************!*\
  !*** ./faceted-search/~/react/lib/getEventCharCode.js ***!
  \********************************************************/
138,
/* 510 */
/*!***************************************************!*\
  !*** ./faceted-search/~/react/lib/getEventKey.js ***!
  \***************************************************/
[795, 509],
/* 511 */
/*!***********************************************************!*\
  !*** ./faceted-search/~/react/lib/SyntheticTouchEvent.js ***!
  \***********************************************************/
[796, 471, 472],
/* 512 */
/*!***********************************************************!*\
  !*** ./faceted-search/~/react/lib/SyntheticWheelEvent.js ***!
  \***********************************************************/
[797, 470],
/* 513 */
/*!************************************************************!*\
  !*** ./faceted-search/~/react/lib/SVGDOMPropertyConfig.js ***!
  \************************************************************/
[798, 382],
/* 514 */
/*!***************************************************************!*\
  !*** ./faceted-search/~/react/lib/createFullPageComponent.js ***!
  \***************************************************************/
[799, 404, 393, 380],
/* 515 */
/*!********************************************************!*\
  !*** ./faceted-search/~/react/lib/ReactDefaultPerf.js ***!
  \********************************************************/
[800, 382, 516, 430, 403, 517],
/* 516 */
/*!****************************************************************!*\
  !*** ./faceted-search/~/react/lib/ReactDefaultPerfAnalysis.js ***!
  \****************************************************************/
[801, 395],
/* 517 */
/*!******************************************************!*\
  !*** ./faceted-search/~/react/lib/performanceNow.js ***!
  \******************************************************/
[802, 518],
/* 518 */
/*!***************************************************!*\
  !*** ./faceted-search/~/react/lib/performance.js ***!
  \***************************************************/
[803, 423],
/* 519 */
/*!************************************************************!*\
  !*** ./faceted-search/~/react/lib/ReactServerRendering.js ***!
  \************************************************************/
[804, 520, 393, 378, 486, 414, 380],
/* 520 */
/*!***********************************************************************!*\
  !*** ./faceted-search/~/react/lib/ReactServerRenderingTransaction.js ***!
  \***********************************************************************/
[805, 401, 391, 402, 477, 395, 386],
/* 521 */
/*!*************************************************!*\
  !*** ./faceted-search/~/react/lib/onlyChild.js ***!
  \*************************************************/
[806, 393, 380],
/* 522 */
/*!************************************************!*\
  !*** ./faceted-search/~/jquery/dist/jquery.js ***!
  \************************************************/
153,
/* 523 */
/*!****************************************************************!*\
  !*** ./faceted-search/~/jquery.browser/dist/jquery.browser.js ***!
  \****************************************************************/
[807, 522],
/* 524 */
/*!************************************************!*\
  !*** ./faceted-search/~/query-string/index.js ***!
  \************************************************/
/***/ function(module, exports, __webpack_require__) {

	/* WEBPACK VAR INJECTION */(function(global) {module.exports = global["exposed"] = __webpack_require__(/*! -!./faceted-search/~/query-string/index.js */ 525);
	/* WEBPACK VAR INJECTION */}.call(exports, (function() { return this; }())))

/***/ },
/* 525 */
/*!************************************************!*\
  !*** ./faceted-search/~/query-string/index.js ***!
  \************************************************/
/***/ function(module, exports) {

	'use strict';
	
	exports.extract = function (maybeUrl) {
		return maybeUrl.split('?')[1] || '';
	};
	
	exports.parse = function (str) {
		if (typeof str !== 'string') {
			return {};
		}
	
		str = str.trim().replace(/^(\?|#|&)/, '');
	
		if (!str) {
			return {};
		}
	
		return str.split('&').reduce(function (ret, param) {
			var parts = param.replace(/\+/g, ' ').split('=');
			var key = parts[0];
			var val = parts[1];
	
			key = decodeURIComponent(key);
			// missing `=` should be `null`:
			// http://w3.org/TR/2012/WD-url-20120524/#collect-url-parameters
			val = val === undefined ? null : decodeURIComponent(val);
	
			if (!ret.hasOwnProperty(key)) {
				ret[key] = val;
			} else if (Array.isArray(ret[key])) {
				ret[key].push(val);
			} else {
				ret[key] = [ret[key], val];
			}
	
			return ret;
		}, {});
	};
	
	exports.stringify = function (obj) {
		return obj ? Object.keys(obj).sort().map(function (key) {
			var val = obj[key];
	
			if (Array.isArray(val)) {
				return val.sort().map(function (val2) {
					return encodeURIComponent(key) + '=' + encodeURIComponent(val2);
				}).join('&');
			}
	
			return encodeURIComponent(key) + '=' + encodeURIComponent(val);
		}).join('&') : '';
	};


/***/ },
/* 526 */
/*!*******************************************!*\
  !*** ./faceted-search/~/URIjs/src/URI.js ***!
  \*******************************************/
[808, 527, 528, 529],
/* 527 */
/*!************************************************!*\
  !*** ./faceted-search/~/URIjs/src/punycode.js ***!
  \************************************************/
167,
/* 528 */
/*!********************************************!*\
  !*** ./faceted-search/~/URIjs/src/IPv6.js ***!
  \********************************************/
169,
/* 529 */
/*!**********************************************************!*\
  !*** ./faceted-search/~/URIjs/src/SecondLevelDomains.js ***!
  \**********************************************************/
170,
/* 530 */
/*!********************************************!*\
  !*** ./faceted-search/src/facets-tree.jsx ***!
  \********************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	var React = __webpack_require__(/*! react */ 376);
	
	//*------------------------------------------------------------------*
	
	__webpack_require__(/*! ../css/facets-tree.css */ 531);
	
	//*------------------------------------------------------------------*
	
	var FacetsTree = React.createClass({displayName: "FacetsTree",
	    propTypes: {
	
	        /*
	         Differential eg:
	         {
	         "species": [ {"name": "homo sapiens", "value": "Homo sapiens"}, {"name": "arabidopsis thaliana", "value": "Arabidopsis thaliana"} ],
	         "experimentType": [ {"name": "rnaseq_mrna_differential", "value": "RNA-seq mRNA"}, {"name": "microarray_1colour_mrna_differential", "value": "1 colour mRNA"} ],
	         "factors": [ {"name": "genotype", "value": "Genotype"} ],
	         "numReplicates": [ {"name": "3", "value": "3"} ],
	         "regulation": [ {"name": "UP", "value": "Up"} ]
	         }
	
	         Baseline eg:
	         {
	         "homo sapiens" : [ {"factor": "CELL_LINE", "source": "Cell line"}, {"factor": "ORGANISM_PART", "source": "Tissue"} ],
	         "mus musculus" : [ {"factor": "CELL_LINE", "source": "Cell line"}, {"factor": "INDIVIDUAL", "source": "Individual"} ]
	         }
	         */
	        facets: React.PropTypes.object.isRequired,
	
	        /*
	         Differential eg:
	         { "species" : { "homo sapiens": true, "arabidopsis thaliana": true }, "regulation": {"UP": true } }
	
	         Baseline eg:
	         eg:
	         { "homo sapiens" : { "CELL_LINE": true, "ORGANISM_PART": true } }
	         */
	        checkedFacets: React.PropTypes.object,
	
	        setChecked: React.PropTypes.func.isRequired
	    },
	
	    _setChecked: function (checked, facet, facetItem) {
	        this.props.setChecked(checked, facet, facetItem);
	    },
	
	    render: function () {
	        var facets = Object.keys(this.props.facets).map(function (facet) {
	            return React.createElement(Facet, {key: facet, facetName: facet, facetItems: this.props.facets[facet], 
	                checkedFacetItems: this.props.checkedFacets && this.props.checkedFacets[facet], 
	                setChecked: this._setChecked}
	            );
	        }.bind(this));
	
	        return (
	            React.createElement("div", {className: "hidden-xs atlasFacetedSearchFacetsContainer"}, React.createElement("h3", null, "Filter your results"), 
	                facets
	            )
	        );
	    }
	});
	
	var Facet = React.createClass({displayName: "Facet",
	    propTypes: {
	        facetName: React.PropTypes.string.isRequired,
	
	        // eg: [ {"name": "rnaseq_mrna_differential", "value": "RNA-seq mRNA"}, {"name": "microarray_1colour_mrna_differential", "value": "1 colour mRNA"} ]
	        facetItems: React.PropTypes.arrayOf(React.PropTypes.shape({
	            name: React.PropTypes.string.isRequired,
	            value: React.PropTypes.string.isRequired
	        })).isRequired,
	
	        // eg: { "rnaseq_mrna_differential": true, "microarray_1colour_mrna_differential": true }
	        checkedFacetItems: React.PropTypes.object,
	
	        setChecked: React.PropTypes.func.isRequired
	    },
	
	    _setChecked: function (checked, facetItem) {
	        this.props.setChecked(checked, this.props.facetName, facetItem);
	    },
	
	    render: function () {
	        var facetItems = this.props.facetItems.map(function (facetItem) {
	            return React.createElement(FacetItem, {key: facetItem.name, name: facetItem.name, value: facetItem.value, 
	                checked: this.props.checkedFacetItems && this.props.checkedFacetItems[facetItem.name], 
	                setChecked: this._setChecked}
	            );
	
	        }.bind(this));
	
	        return (
	            React.createElement("div", {className: "atlasFacetedSearchFacetItem"}, 
	                React.createElement("h4", null, this.props.facetName), 
	                React.createElement("ul", null, 
	                    facetItems
	                )
	            )
	        );
	    }
	});
	
	var FacetItem = React.createClass({displayName: "FacetItem",
	    propTypes: {
	        name: React.PropTypes.string.isRequired,
	        value: React.PropTypes.string.isRequired,
	        checked: React.PropTypes.bool,
	        setChecked: React.PropTypes.func.isRequired
	    },
	
	    _setChecked: function () {
	        this.props.setChecked(!this.props.checked, this.props.name);
	    },
	
	    render: function () {
	        return (
	            React.createElement("li", null, 
	                React.createElement("input", {type: "checkbox", checked: this.props.checked ? true : false, 
	                    onChange: this._setChecked}
	                ), this.props.value)
	        );
	    }
	});
	
	//*------------------------------------------------------------------*
	
	module.exports = FacetsTree;

/***/ },
/* 531 */
/*!********************************************!*\
  !*** ./faceted-search/css/facets-tree.css ***!
  \********************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../~/css-loader!./facets-tree.css */ 532);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../~/style-loader/addStyles.js */ 534)(content, {});
	if(content.locals) module.exports = content.locals;
	// Hot Module Replacement
	if(false) {
		// When the styles change, update the <style> tags
		if(!content.locals) {
			module.hot.accept("!!./../node_modules/css-loader/index.js!./facets-tree.css", function() {
				var newContent = require("!!./../node_modules/css-loader/index.js!./facets-tree.css");
				if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
				update(newContent);
			});
		}
		// When the module is disposed, remove the <style> tags
		module.hot.dispose(function() { update(); });
	}

/***/ },
/* 532 */
/*!**************************************************************************!*\
  !*** ./faceted-search/~/css-loader!./faceted-search/css/facets-tree.css ***!
  \**************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../~/css-loader/lib/css-base.js */ 533)();
	// imports
	
	
	// module
	exports.push([module.id, "/*Responsive*/\n@media (max-width: 768px) {\n    .hidden-xs {display: none!important;} /*remove column like filter for small devices*/\n}\n\n/* Facets-tree container */\n.atlasFacetedSearchFacetsContainer ul {\n    list-style-type: none;\n    padding: 0;\n    margin: 0;\n}\n\n.atlasFacetedSearchFacetsContainer {padding:0 9px;}\n.atlasFacetedSearchFacetsContainer  h3 {padding-left:0;}\n.atlasFacetedSearchFacetsContainer .atlasFacetedSearchFacetItem {padding:0 0 9px 0;}\n.atlasFacetedSearchFacetsContainer .atlasFacetedSearchFacetItem ul li span {display:inline-block;/*IMPORTANT TO KEEP FOR SPAN make first-letter work*/ }\n.atlasFacetedSearchFacetsContainer .atlasFacetedSearchFacetItem ul li span:first-letter  {text-transform: capitalize;}\n.atlasFacetedSearchFacetsContainer .atlasFacetedSearchFacetItem:nth-child(3) ul li span {font-style: italic;} /*target species facet*/\n.atlasFacetedSearchFacetsContainer .atlasFacetedSearchFacetItem h4 {font-weight: bold;font-size: 144%;text-transform: capitalize; padding-left:0;  margin-bottom: 9px;}\n.atlasFacetedSearchFacetsContainer ul li  {padding:1px 0; }\n", ""]);
	
	// exports


/***/ },
/* 533 */
/*!*****************************************************!*\
  !*** ./faceted-search/~/css-loader/lib/css-base.js ***!
  \*****************************************************/
340,
/* 534 */
/*!****************************************************!*\
  !*** ./faceted-search/~/style-loader/addStyles.js ***!
  \****************************************************/
344,
/* 535 */
/*!**************************************************!*\
  !*** ./faceted-search/src/baseline-heatmaps.jsx ***!
  \**************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	var React = __webpack_require__(/*! react */ 376);
	
	var URI = __webpack_require__(/*! URIjs */ 526);
	
	//*------------------------------------------------------------------*
	
	var BaselineHeatmapWidget = __webpack_require__(/*! ./baseline-heatmap-widget.jsx */ 536);
	
	//*------------------------------------------------------------------*
	
	var Heatmaps = React.createClass({displayName: "Heatmaps",
	    propTypes: {
	        geneQuery: React.PropTypes.string.isRequired,
	        host: React.PropTypes.string.isRequired,
	        /*
	         [{"geneQuery":"zinc finger","species":"Homo sapiens","factor":"CELL_LINE"},
	          {"geneQuery":"zinc finger","species":"Homo sapiens","factor":"ORGANISM_PART"}]
	         */
	        heatmaps: React.PropTypes.arrayOf(React.PropTypes.shape({
	            geneQuery: React.PropTypes.string.isRequired,
	            species: React.PropTypes.string.isRequired,
	            factor: React.PropTypes.string.isRequired
	        })).isRequired
	    },
	
	    render: function () {
	        var geneQuery = this.props.geneQuery;
	        var gxaBaseURL = new URI({hostname: this.props.host, path: "/gxa"});
	
	        return (
	            React.createElement("div", null, 
	                this.props.heatmaps.map(function (heatmap) {
	                    return React.createElement(BaselineHeatmapWidget, {key: heatmap.species + "_" + heatmap.factor, 
	                                                  gxaBaseUrl: gxaBaseURL.normalize().toString(), geneQuery: geneQuery, species: heatmap.species, factor: heatmap.factor});
	                })
	            )
	        );
	    }
	});
	
	//*------------------------------------------------------------------*
	
	module.exports = Heatmaps;
	
	


/***/ },
/* 536 */
/*!********************************************************!*\
  !*** ./faceted-search/src/baseline-heatmap-widget.jsx ***!
  \********************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	var React = __webpack_require__(/*! react */ 376);
	
	//*------------------------------------------------------------------*
	
	var AtlasHeatmapBuilder = __webpack_require__(/*! expression-atlas-heatmap */ 537);
	
	//*------------------------------------------------------------------*
	
	var BaselineHeatmapWidget = React.createClass({displayName: "BaselineHeatmapWidget",
	    propTypes: {
	        gxaBaseUrl: React.PropTypes.string.isRequired,
	        geneQuery: React.PropTypes.string.isRequired,
	        species: React.PropTypes.string.isRequired,
	        factor: React.PropTypes.string.isRequired
	    },
	
	    componentDidMount: function() {
	        AtlasHeatmapBuilder({
	            gxaBaseUrl: this.props.gxaBaseUrl,
	            params: 'geneQuery=' + this.props.geneQuery + "&species=" + this.props.species + "&source=" + this.props.factor,
	            isMultiExperiment: true,
	            target: this.refs.widgetBody.getDOMNode(),
	            heatmapClass: "gxaHeatmapPosition",
	            heatmapUrl: "/widgets/heatmap/baselineAnalytics",
	            heatmapKey:this.props.species + this.props.factor
	        });
	    },
	
	    render: function() {
	        return(
	            React.createElement("div", {ref: "widgetBody"})
	        );
	    }
	
	});
	
	//*------------------------------------------------------------------*
	
	module.exports = BaselineHeatmapWidget;


/***/ },
/* 537 */
/*!**************************!*\
  !*** ./heatmap/index.js ***!
  \**************************/
/***/ function(module, exports, __webpack_require__) {

	/* WEBPACK VAR INJECTION */(function(global) {module.exports = global["exposed"] = __webpack_require__(/*! -!./heatmap/index.js */ 1);
	/* WEBPACK VAR INJECTION */}.call(exports, (function() { return this; }())))

/***/ },
/* 538 */
/*!***************************************************!*\
  !*** ./faceted-search/src/differential-router.js ***!
  \***************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	var React = __webpack_require__(/*! react */ 376);
	var $ = __webpack_require__(/*! jquery */ 522);
	var jQuery = $;
	__webpack_require__(/*! jquery.browser */ 523);
	
	var queryString = __webpack_require__(/*! query-string */ 524);
	
	var URI = __webpack_require__(/*! URIjs */ 526);
	
	//*------------------------------------------------------------------*
	
	var FacetsTree = __webpack_require__(/*! ./facets-tree.jsx */ 530);
	var DifferentialResults = __webpack_require__(/*! ./differential-results.jsx */ 539);
	
	//*------------------------------------------------------------------*
	
	module.exports = function (facetsContainerId, resultsContainerId, facetsTreeData, atlasHost) {
	
	    var facetsElement = document.getElementById(facetsContainerId),
	        resultsElement = document.getElementById(resultsContainerId);
	
	    //TODO: add this outside the module, when module is first loaded
	    var ie9 = $.browser.msie && $.browser.version < 10;
	    if (!ie9) {
	        window.addEventListener('popstate', renderPage, false);
	    }
	
	
	    renderPage();
	
	    function renderPage() {
	        var locationSearch = window.location.search;
	        var query = queryString.parse(locationSearch.slice(1));
	        query.select = query.select && JSON.parse(query.select);
	        render(query);
	    }
	
	    function render(query) {
	
	        React.render(
	            React.createElement(FacetsTree, {facets: facetsTreeData, checkedFacets: query.select, setChecked: setChecked}),
	            facetsElement);
	
	        queryToResults(query.geneQuery, query.select);
	
	        function setChecked(checked, facet, facetItem) {
	            var newSelect = checked ? addSelection(query.select, facet, facetItem) : removeSelection(query.select, facet, facetItem);
	            var newQueryString = "?geneQuery=" + query.geneQuery + "&select=" + JSON.stringify(newSelect);
	            queryToResults(query.geneQuery, newSelect);
	            navigateTo(newQueryString);
	        }
	
	        function navigateTo(newQueryString) {
	            var state, title;
	            if (ie9) {
	                window.location.search = newQueryString;
	            } else {
	                history.pushState(null, null, window.location.pathname + newQueryString);
	                renderPage();
	            }
	        }
	
	
	        function addSelection(select, facet, facetItem) {
	            if (!select) {
	                select = {};
	            }
	
	            if (!select[facet]) {
	                select[facet] = {};
	            }
	            select[facet][facetItem] = true;
	            return select;
	        }
	
	        function removeSelection(select, facet, facetItem) {
	            select[facet][facetItem] = false;
	            return select;
	        }
	    }
	
	    function queryToResults(geneQuery, query) {
	        /*
	         query.geneQuery=zinc finger
	         query.select= {
	         "species": {"homo sapiens":true,"mus musculus":true,"arabidopsis thaliana":true},
	         "factors":{"infect":true},
	         "numReplicates":{"3":true},
	         "experimentType":{"rnaseq_mrna_differential":true},
	         "kingdom":{"ensembl":true},
	         "regulation":{"UP":true}
	         }
	         */
	        var species = [];
	        var experimentType = [];
	        var kingdom = [];
	        var factors = [];
	        var numReplicates = [];
	        var regulation;
	
	        var select = query;
	        for (var facets in select) {
	            if (select.hasOwnProperty(facets)) {
	                var items = select[facets];
	
	                for (var key in items) {
	                    if (items.hasOwnProperty(key)) {
	                        var value = items[key];
	                        if (facets == "species" && value == true) {
	                            species.push(key);
	                        } else if (facets == "factors" && value == true) {
	                            factors.push(key);
	                        } else if (facets == "experimentType" && value == true) {
	                            experimentType.push(key);
	                        } else if (facets == "kingdom" && value == true) {
	                            kingdom.push(key);
	                        } else if (facets == "numReplicates" && value == true) {
	                            numReplicates.push(key);
	                        } else if (facets == "regulation" && value == true) {
	                            regulation = key;
	                        }
	                    }
	                }
	            }
	        }
	
	        var host = atlasHost ? atlasHost : window.location.host;
	        var differentialResultsPath = "gxa/search/differential/json";
	
	        $.ajaxSetup({ traditional:true });
	        $.ajax({
	            url: new URI({protocol: "http", hostname: host, path: differentialResultsPath}).normalize(),
	            data: {
	                'geneQuery': geneQuery,
	                'species': species.toString(),
	                'experimentType': experimentType.toString(),
	                'kingdom': kingdom.toString(),
	                'factors': factors.toString(),
	                'numReplicates': numReplicates.toString(),
	                'regulation': regulation
	            },
	            dataType: 'json',
	            success: function(response) {
	                React.render(
	                    React.createElement(DifferentialResults, {diffResultsData: $.parseJSON(response["species"])}),
	                    resultsElement
	                );
	            }
	        });
	
	    }
	
	};


/***/ },
/* 539 */
/*!*****************************************************!*\
  !*** ./faceted-search/src/differential-results.jsx ***!
  \*****************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	//*------------------------------------------------------------------*
	
	var React = __webpack_require__(/*! react */ 376);
	
	//*------------------------------------------------------------------*
	
	__webpack_require__(/*! ../css/differential-results.css */ 540);
	
	//*------------------------------------------------------------------*
	
	var DifferentialResults = React.createClass({displayName: "DifferentialResults",
	    /*
	     eg: from http://www.ebi.ac.uk/gxa/query?geneQuery=zinc&_exactMatch=on&organism=Any&condition=
	     [
	        {
	         "geneCount": 10,
	         "organism": "Mus musculus",
	         "contrastId": "g2_g4",
	         "comparison": "'LDB1 knock-down with Ldb1 delta 4/5 construct' vs 'control shRNA",
	         "experimentAccession": "E-GEOD-54549",
	         "experimentName": "Role of LDB1 in the transition from chromatin looping to transcription activation"
	        },
	        {
	         "geneCount": 2,
	         "organism": "Homo sapiens",
	         "contrastId": "g2_g5",
	         "comparison": "'SAP130 knock-down' vs 'mock'",
	         "experimentAccession": "E-GEOD-56788",
	         "experimentName": "RNA-seq analysis of vorinostat-resistant HCT116 cells following gene knockdown of potential vorinostat-resistance candidate genes"
	        }
	     ]
	     */
	    propTypes: {
	        diffResultsData: React.PropTypes.arrayOf(React.PropTypes.shape({
	            geneCount: React.PropTypes.number.isRequired,
	            organism: React.PropTypes.string.isRequired,
	            contrastId: React.PropTypes.string.isRequired,
	            comparison: React.PropTypes.string.isRequired,
	            experimentAccession: React.PropTypes.string.isRequired,
	            experimentName: React.PropTypes.string.isRequired
	        })).isRequired
	    },
	
	    render: function () {
	        var differentialResultRows = this.props.diffResultsData.map(function (diffResult) {
	            return React.createElement(DifferentialResultRow, {
	                key: diffResult.experimentAccession + diffResult.contrastId, 
	                geneCount: diffResult.geneCount, organism: diffResult.organism, comparison: diffResult.comparison, experimentName: diffResult.experimentName, 
	                contrastId: diffResult.contrastId, experimentAccession: diffResult.experimentAccession}
	            );
	        }.bind(this));
	
	        return (
	            React.createElement("table", {className: "table-striped atlasDifferentialFacetedSearchResults"}, 
	                React.createElement("thead", null, 
	                    React.createElement("tr", null, 
	                        React.createElement("th", null, "Genes"), 
	                        React.createElement("th", null, "Species"), 
	                        React.createElement("th", null, "Comparison"), 
	                        React.createElement("th", null, "Experimental variables"), 
	                        React.createElement("th", null, "Experiment name")
	                    )
	                ), 
	                React.createElement("tbody", null, 
	                        differentialResultRows
	                )
	            )
	        );
	    }
	});
	
	
	var DifferentialResultRow = React.createClass({displayName: "DifferentialResultRow",
	    propTypes: {
	        geneCount: React.PropTypes.number.isRequired,
	        organism: React.PropTypes.string.isRequired,
	        comparison: React.PropTypes.string.isRequired,
	        experimentName: React.PropTypes.string.isRequired,
	        contrastId: React.PropTypes.string.isRequired,
	        experimentAccession: React.PropTypes.string.isRequired
	    },
	
	    // TODO Use this.props.contrastId and this.props.experimentAccession to add link to the relevant experiment/comparison
	    render: function () {
	        var classColor="";
	
	        if (this.props.organism === "homo sapiens" || this.props.organism === "gallus gallus" || this.props.organism === "gorilla gorilla" || this.props.organism === "macaca mulatta" || this.props.organism === "monodelphis domestica" || this.props.organism === "mus musculus" || this.props.organism === "pan paniscus" || this.props.organism === "pan troglodytes" || this.props.organism === "rattus norvegicus") {
	            classColor="red";
	        } else if (this.props.organism == "arabidopsis thaliana" || this.props.organism === "hordeum vulgare subsp. vulgare" || this.props.organism === "oryza sativa japonica group" ) {
	            classColor="green";
	        } else if (this.props.organism === "anolis carolinensis" || this.props.organism === "drosophila melanogaster" || this.props.organism === "caenorhabditis elegans" || this.props.organism === "tetraodon nigroviridis" || this.props.organism === "xenopus (silurana) tropicalis") {
	            classColor="blue";
	        }
	//        || this.props.organism === "gallus gallus" || this.props.organism === "gorilla gorilla" || this.props.organism === "macaca mulatta" || this.props.organism === "monodelphis domestica" ||  this.props.organism === "pan paniscus" || this.props.organism === "pan troglodytes" || this.props.organism === "rattus norvegicus"
	//        || this.props.organism === "hordeum vulgare subsp. vulgare" || this.props.organism === "oryza sativa japonica group"
	//        || this.props.organism === "drosophila melanogaster" || this.props.organism === "caenorhabditis elegans" || this.props.organism === "tetraodon nigroviridis" || this.props.organism === "xenopus (silurana) tropicalis"
	        var classIcon="";
	
	        if (this.props.organism === "homo sapiens") {
	            classIcon="H";
	        } else if (this.props.organism == "mus musculus") {
	            classIcon="M";
	        } else if (this.props.organism === "anolis carolinensis") {
	            classIcon="7";
	        } else if (this.props.organism === "arabidopsis thaliana") {
	            classIcon="B";
	        } else if (this.props.organism === "bos taurus") {
	            classIcon="C";
	        } else if (this.props.organism === "caenorhabditis elegans") {
	            classIcon="W";
	        } else if (this.props.organism === "gallus gallus") {
	            classIcon="k";
	        } else if (this.props.organism === "gorilla gorilla") {
	            classIcon="G";
	        } else if (this.props.organism === "hordeum vulgare subsp. vulgare") {
	            classIcon="5";
	        } else if (this.props.organism === "macaca mulatta") {
	            classIcon="r";
	        } else if (this.props.organism === "monodelphis domestica") {
	            classIcon="9";
	        } else if (this.props.organism === "oryctolagus cuniculus") {
	            classIcon="t";
	        } else if (this.props.organism === "oryza sativa japonica group") {
	            classIcon="6";
	        } else if (this.props.organism === "pan paniscus" || this.props.organism === "pan troglodytes" ) {
	            classIcon="i";
	        } else if (this.props.organism === "papio anubis") {
	            classIcon="8";
	        } else if (this.props.organism === "rattus norvegicus") {
	            classIcon="R";
	        } else if (this.props.organism === "tetraodon nigroviridis") {
	            classIcon="E";
	        } else if (this.props.organism === "zea mays") {
	            classIcon="5";
	        } else if (this.props.organism === "xenopus (silurana) tropicalis") {
	            classIcon="f";
	        } else if (this.props.organism === "drosophila melanogaster") {
	            classIcon="F";
	        } else {classIcon="";}
	
	        return (
	            React.createElement("tr", null, 
	                React.createElement("td", {className: "col_count"}, this.props.geneCount), 
	                React.createElement("td", {className: "col_species"}, React.createElement("span", {className: "icon icon-species " + classColor, "data-icon": classIcon, style: {color: 'red'}, title: this.props.organism})), 
	                React.createElement("td", null, React.createElement("a", {href: "#"}, this.props.comparison)), 
	                React.createElement("td", null, "organism part"), 
	                React.createElement("td", null, this.props.experimentName)
	            )
	        );
	    }
	});
	
	//*------------------------------------------------------------------*
	
	module.exports = DifferentialResults;

/***/ },
/* 540 */
/*!*****************************************************!*\
  !*** ./faceted-search/css/differential-results.css ***!
  \*****************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../~/css-loader!./differential-results.css */ 541);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../~/style-loader/addStyles.js */ 534)(content, {});
	if(content.locals) module.exports = content.locals;
	// Hot Module Replacement
	if(false) {
		// When the styles change, update the <style> tags
		if(!content.locals) {
			module.hot.accept("!!./../node_modules/css-loader/index.js!./differential-results.css", function() {
				var newContent = require("!!./../node_modules/css-loader/index.js!./differential-results.css");
				if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
				update(newContent);
			});
		}
		// When the module is disposed, remove the <style> tags
		module.hot.dispose(function() { update(); });
	}

/***/ },
/* 541 */
/*!***********************************************************************************!*\
  !*** ./faceted-search/~/css-loader!./faceted-search/css/differential-results.css ***!
  \***********************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../~/css-loader/lib/css-base.js */ 533)();
	// imports
	
	
	// module
	exports.push([module.id, "table.table-striped tr:nth-child(even) {background-color: #f9f9f9;}\ntable.table-striped tr:nth-child(odd) {background: #FFF;}\n\ntable.atlasDifferentialFacetedSearchResults th {font-size: 90%; border-top: 0px #ddd solid;border-left: 0px #ddd solid; border-right: 0px #ddd solid; border-bottom: 2px solid #ddd;vertical-align: bottom;}\ntable.atlasDifferentialFacetedSearchResults tr td {padding: 8px; line-height: 1.42857143; vertical-align: top; border-top: 1px solid #ddd}\ntable.atlasDifferentialFacetedSearchResults tr td.col_count {min-width: 46px;}\n/* table.atlasDifferentialFacetedSearchResults table tr td.col_species {min-width: 162px;font-style: italic;} TEMP for species column, text version*/\ntable.atlasDifferentialFacetedSearchResults tr td.col_species span.icon-species {  font-size: 300%; margin-left: 4px;}\ntable.atlasDifferentialFacetedSearchResults tr td.col_species span.icon-species.red:before {color:#d9534f;} /*animal*/\ntable.atlasDifferentialFacetedSearchResults tr td.col_species span.icon-species.green:before {color:#5cb85c;}/*plants*/\ntable.atlasDifferentialFacetedSearchResults tr td.col_species span.icon-species.blue:before {color:#5bc0de;}/*other*/", ""]);
	
	// exports


/***/ }
]);
//# sourceMappingURL=faceted-search.bundle.js.map