var anatomogramModule =
webpackJsonp_name_([0],[
/* 0 */
/***/ function(module, exports, __webpack_require__) {

	"use strict";

	//*------------------------------------------------------------------*

	module.exports = __webpack_require__(1);

/***/ },
/* 1 */
/***/ function(module, exports, __webpack_require__) {

	"use strict";

	//*------------------------------------------------------------------*

	var React = __webpack_require__(2);
	var ReactDOM = __webpack_require__(159);
	var EventEmitter = __webpack_require__(160);

	var Anatomogram = __webpack_require__(161);

	//*------------------------------------------------------------------*

	function AnatomogramBuilder(domNode, anatomogramData, profileRows, expressedColour, hoverColour, eventEmitter, jQueryUITheme) {

	    if (jQueryUITheme !== "") {

	        var href = jQueryUITheme === "atlas" || typeof jQueryUITheme === "undefined" ?
	            window.location.protocol + "//" + window.location.host + "/gxa/resources/css/jquery-ui-1.11.4.custom/css/jquery-ui-min.css" :
	            "https://code.jquery.com/ui/1.11.1/themes/" + jQueryUITheme + "/jquery-ui.css";

	        var cssDoc = document.createElement("link");
	        cssDoc.setAttribute("rel", "stylesheet");
	        cssDoc.setAttribute("type", "text/css");
	        cssDoc.setAttribute("href", href);
	        document.getElementsByTagName("head")[0].appendChild(cssDoc);

	    }

	    ReactDOM.render(
	        React.createElement(
	            Anatomogram,
	            {
	                anatomogramData: anatomogramData,
	                expressedTissueColour: expressedColour,
	                hoveredTissueColour: hoverColour,
	                atlasBaseURL: "https://www.ebi.ac.uk/gxa",
	                profileRows: profileRows,
	                eventEmitter: eventEmitter
	            }
	        ),
	        domNode
	    );
	}

	//*------------------------------------------------------------------*

	exports.AnatomogramBuilder = AnatomogramBuilder;
	exports.EventEmitter = EventEmitter;


/***/ },
/* 2 */
[1145, 3],
/* 3 */
[1146, 4, 149, 153, 40, 158],
/* 4 */
[1147, 6, 7, 72, 46, 29, 19, 51, 55, 147, 92, 148, 26, 10],
/* 5 */,
/* 6 */
170,
/* 7 */
[1148, 8, 23, 27, 29, 40, 22, 21, 71],
/* 8 */
[1149, 9, 17, 19, 20, 21, 14],
/* 9 */
[1150, 10, 11, 16, 15, 14],
/* 10 */
174,
/* 11 */
[1151, 10, 12, 15, 14],
/* 12 */
[1152, 13],
/* 13 */
[1153, 14],
/* 14 */
178,
/* 15 */
[1154, 10, 14],
/* 16 */
180,
/* 17 */
[1155, 18],
/* 18 */
[1156, 14],
/* 19 */
183,
/* 20 */
[1157, 10],
/* 21 */
[1158, 10, 22, 20],
/* 22 */
186,
/* 23 */
[1159, 24, 19, 25, 26],
/* 24 */
[1160, 14],
/* 25 */
[1161, 22],
/* 26 */
[1162, 16],
/* 27 */
[1163, 28, 29],
/* 28 */
[1164, 8, 23, 29, 19, 14],
/* 29 */
[1165, 24, 30, 6, 42, 43, 45, 46, 48, 49, 19, 51, 54, 55, 40, 59, 60, 63, 14, 20, 68, 71, 26],
/* 30 */
[1166, 31, 32, 33, 38, 19, 39, 40, 41],
/* 31 */
[1167, 18],
/* 32 */
[1168, 33, 34, 35, 36, 37, 14, 26],
/* 33 */
[1169, 14],
/* 34 */
[1170, 31, 35, 14, 26],
/* 35 */
199,
/* 36 */
[1171, 14],
/* 37 */
201,
/* 38 */
[1172, 32],
/* 39 */
203,
/* 40 */
204,
/* 41 */
[1173, 10],
/* 42 */
206,
/* 43 */
[1174, 6, 40, 44],
/* 44 */
208,
/* 45 */
209,
/* 46 */
[1175, 47, 14],
/* 47 */
211,
/* 48 */
212,
/* 49 */
[1176, 50],
/* 50 */
214,
/* 51 */
[1177, 52],
/* 52 */
[1178, 53],
/* 53 */
[1179, 14],
/* 54 */
[1180, 6, 43, 48, 55, 40, 14, 26],
/* 55 */
[1181, 56, 57, 19, 51, 58, 40, 14],
/* 56 */
[1182, 57, 40, 14],
/* 57 */
[1183, 14],
/* 58 */
[1184, 14],
/* 59 */
223,
/* 60 */
[1185, 61],
/* 61 */
[1186, 62],
/* 62 */
226,
/* 63 */
[1187, 64, 69, 70, 40, 14, 26],
/* 64 */
[1188, 65, 6, 43, 48, 19, 66, 67, 51, 54, 40, 59, 14, 68, 26],
/* 65 */
[1189, 14],
/* 66 */
[1190, 18],
/* 67 */
231,
/* 68 */
232,
/* 69 */
[1191, 43, 45, 51, 40],
/* 70 */
[1192, 40, 14],
/* 71 */
[1193, 40, 16, 26],
/* 72 */
[1194, 73, 81, 84, 85, 86, 10, 90, 91, 27, 93, 94, 7, 119, 122, 46, 29, 126, 131, 132, 133, 142, 143],
/* 73 */
[1195, 31, 74, 10, 75, 77, 79, 80],
/* 74 */
[1196, 31, 32, 26, 36, 37],
/* 75 */
[1197, 57, 40, 76],
/* 76 */
[1198, 10],
/* 77 */
[1199, 78],
/* 78 */
[1200, 57, 40, 16, 26],
/* 79 */
[1201, 78],
/* 80 */
244,
/* 81 */
[1202, 31, 32, 74, 10, 55, 78, 82, 41, 83, 80],
/* 82 */
246,
/* 83 */
247,
/* 84 */
248,
/* 85 */
[1203, 80],
/* 86 */
[1204, 31, 74, 87, 29, 80],
/* 87 */
[1205, 88, 39, 89],
/* 88 */
[1206, 78, 82],
/* 89 */
253,
/* 90 */
[1207, 24, 10],
/* 91 */
[1208, 48, 92, 26],
/* 92 */
[1209, 6, 48, 29, 14, 26],
/* 93 */
[1210, 55, 58, 40, 16],
/* 94 */
[1211, 95, 97, 24, 23, 31, 30, 27, 105, 106, 110, 113, 114, 29, 115, 19, 54, 40, 44, 22, 14, 41, 80, 20, 21, 118, 71, 26],
/* 95 */
[1212, 29, 92, 96],
/* 96 */
260,
/* 97 */
[1213, 98, 10, 19, 99, 101, 102, 104, 26],
/* 98 */
262,
/* 99 */
[1214, 100],
/* 100 */
264,
/* 101 */
[1215, 98],
/* 102 */
[1216, 103],
/* 103 */
267,
/* 104 */
268,
/* 105 */
269,
/* 106 */
[1217, 28, 107, 29, 55, 40, 14],
/* 107 */
[1218, 108, 66, 14, 26],
/* 108 */
[1219, 43, 67, 16, 109],
/* 109 */
273,
/* 110 */
[1220, 111, 113, 40, 26],
/* 111 */
[1221, 57, 43, 16, 112],
/* 112 */
[1222, 6, 43, 46, 109, 14, 26],
/* 113 */
[1223, 107, 29, 55, 40, 26],
/* 114 */
[1224, 107, 28, 55, 40, 14, 26],
/* 115 */
[1225, 65, 17, 6, 51, 116, 117],
/* 116 */
[1226, 51, 63, 68, 112, 26],
/* 117 */
[1227, 112, 26],
/* 118 */
282,
/* 119 */
[1228, 120, 10, 57, 46, 29, 55, 40, 82, 121],
/* 120 */
[1229, 16],
/* 121 */
285,
/* 122 */
[1230, 24, 32, 65, 123, 69, 30, 70, 19, 47, 55],
/* 123 */
[1231, 124, 43, 66, 67, 125, 40, 59, 14, 18, 80, 26],
/* 124 */
[1232, 125, 44, 59, 14, 26],
/* 125 */
[1233, 26],
/* 126 */
[1234, 56, 57, 30, 42, 127, 58, 40],
/* 127 */
[1235, 128, 60, 96, 130],
/* 128 */
[1236, 10, 129, 76],
/* 129 */
293,
/* 130 */
294,
/* 131 */
[1237, 31, 74, 10, 127, 78, 130, 83, 80, 118],
/* 132 */
296,
/* 133 */
[1238, 31, 120, 74, 29, 134, 78, 135, 136, 87, 139, 140, 88, 141, 16, 137, 14, 80],
/* 134 */
[1239, 78],
/* 135 */
[1240, 88],
/* 136 */
[1241, 88, 137, 138, 89],
/* 137 */
301,
/* 138 */
[1242, 137],
/* 139 */
[1243, 87],
/* 140 */
[1244, 88, 89],
/* 141 */
[1245, 87],
/* 142 */
[1246, 24],
/* 143 */
[1247, 24, 144, 29, 19, 145],
/* 144 */
[1248, 40],
/* 145 */
[1249, 146],
/* 146 */
[1250, 10],
/* 147 */
311,
/* 148 */
[1251, 29],
/* 149 */
[1252, 72, 150, 147],
/* 150 */
[1253, 93, 43, 46, 49, 151, 152, 55, 59, 63, 14],
/* 151 */
315,
/* 152 */
[1254, 57, 56, 58, 40, 16],
/* 153 */
[1255, 111, 124, 123, 154, 43, 155, 108, 147, 40, 157],
/* 154 */
[1256, 43, 155, 156],
/* 155 */
[1257, 43, 66, 67, 6, 44, 109, 14, 26],
/* 156 */
320,
/* 157 */
[1258, 43, 14],
/* 158 */
[1259, 40, 26],
/* 159 */
[1260, 4],
/* 160 */,
/* 161 */
/***/ function(module, exports, __webpack_require__) {

	"use strict";

	//*------------------------------------------------------------------*

	module.exports = __webpack_require__(162);

/***/ },
/* 162 */
/***/ function(module, exports, __webpack_require__) {

	"use strict";

	//*------------------------------------------------------------------*

	var React = __webpack_require__(2);
	var ReactDOM = __webpack_require__(159);

	var $ = __webpack_require__(163);
	__webpack_require__(164);
	__webpack_require__(165);

	var Snap = __webpack_require__(166);

	var EventEmitter = __webpack_require__(160);

	//*------------------------------------------------------------------*

	var AnatomogramSelectImageButton = React.createClass({
	    displayName: 'AnatomogramSelectImageButton',

	    propTypes: {
	        anatomogramId: React.PropTypes.string.isRequired,
	        selected: React.PropTypes.bool.isRequired,
	        toggleSrcTemplate: React.PropTypes.string.isRequired,
	        onClick: React.PropTypes.func.isRequired
	    },

	    render: function () {
	        var selectedToggleSrc = this.props.toggleSrcTemplate + "_selected.png",
	            unselectedToggleSrc = this.props.toggleSrcTemplate + "_unselected.png";

	        return React.createElement(
	            'div',
	            null,
	            React.createElement('img', { ref: 'toggleButton', onClick: this._onClick, src: this.props.selected ? selectedToggleSrc : unselectedToggleSrc,
	                style: { width: "20px", height: "20px", padding: "2px" } })
	        );
	    },

	    componentDidMount: function () {
	        $(ReactDOM.findDOMNode(this.refs.toggleButton)).button();
	    },

	    _onClick: function () {
	        this.props.onClick(this.props.anatomogramId);
	    }
	});

	var AnatomogramSelectImageButtons = React.createClass({
	    displayName: 'AnatomogramSelectImageButtons',

	    propTypes: {
	        selectedId: React.PropTypes.string.isRequired,
	        availableAnatomograms: React.PropTypes.array.isRequired,
	        onClick: React.PropTypes.func.isRequired
	    },

	    render: function () {
	        if (this.props.availableAnatomograms.length > 1) {
	            var selectedId = this.props.selectedId,
	                onClick = this.props.onClick;
	            var anatomogramSelectImageButtons = this.props.availableAnatomograms.map(function (availableAnatomogram) {
	                return React.createElement(AnatomogramSelectImageButton, { key: availableAnatomogram.id + "_toggle",
	                    anatomogramId: availableAnatomogram.id, selected: selectedId === availableAnatomogram.id, toggleSrcTemplate: availableAnatomogram.toggleSrcTemplate, onClick: onClick });
	            });

	            return React.createElement(
	                'span',
	                null,
	                anatomogramSelectImageButtons
	            );
	        } else {
	            return null;
	        }
	    }

	});

	var Anatomogram = React.createClass({
	    displayName: 'Anatomogram',

	    /*
	     E.g. of profileRows:
	     {"id":"ENSMUSG00000029019","name":"Nppb","expressions":[{"factorName":"heart","color":"#C0C0C0","value":"152","svgPathId":"UBERON_0000948"},{"factorName":"hippocampus","color":"","value":"","svgPathId":"EFO_0000530"},{"factorName":"liver","color":"","value":"","svgPathId":"UBERON_0002107"},{"factorName":"lung","color":"","value":"","svgPathId":"UBERON_0002048"},{"factorName":"spleen","color":"","value":"","svgPathId":"UBERON_0002106"},{"factorName":"thymus","color":"","value":"","svgPathId":"UBERON_0002370"}]},
	     {"id":"ENSMUSG00000027350","name":"Chgb","expressions":[{"factorName":"heart","color":"","value":"","svgPathId":"UBERON_0000948"},{"factorName":"hippocampus","color":"#C0C0C0","value":"148","svgPathId":"EFO_0000530"},{"factorName":"liver","color":"","value":"","svgPathId":"UBERON_0002107"},{"factorName":"lung","color":"","value":"","svgPathId":"UBERON_0002048"},{"factorName":"spleen","color":"","value":"","svgPathId":"UBERON_0002106"},{"factorName":"thymus","color":"","value":"","svgPathId":"UBERON_0002370"}]},
	     {"id":"ENSMUSG00000033981","name":"Gria2","expressions":[{"factorName":"heart","color":"","value":"","svgPathId":"UBERON_0000948"},{"factorName":"hippocampus","color":"#C0C0C0","value":"140","svgPathId":"EFO_0000530"},{"factorName":"liver","color":"","value":"","svgPathId":"UBERON_0002107"},{"factorName":"lung","color":"","value":"","svgPathId":"UBERON_0002048"},{"factorName":"spleen","color":"","value":"","svgPathId":"UBERON_0002106"},{"factorName":"thymus","color":"","value":"","svgPathId":"UBERON_0002370"}]},
	     {"id":"ENSMUSG00000026368","name":"F13b","expressions":[{"factorName":"heart","color":"","value":"","svgPathId":"UBERON_0000948"},{"factorName":"hippocampus","color":"","value":"","svgPathId":"EFO_0000530"},{"factorName":"liver","color":"#C0C0C0","value":"136","svgPathId":"UBERON_0002107"},{"factorName":"lung","color":"","value":"","svgPathId":"UBERON_0002048"},{"factorName":"spleen","color":"","value":"","svgPathId":"UBERON_0002106"},{"factorName":"thymus","color":"","value":"","svgPathId":"UBERON_0002370"}]},
	     {"id":"ENSMUSG00000039278","name":"Pcsk1n","expressions":[{"factorName":"heart","color":"","value":"","svgPathId":"UBERON_0000948"},{"factorName":"hippocampus","color":"#C0C0C0","value":"132","svgPathId":"EFO_0000530"},{"factorName":"liver","color":"","value":"","svgPathId":"UBERON_0002107"},{"factorName":"lung","color":"","value":"","svgPathId":"UBERON_0002048"},{"factorName":"spleen","color":"","value":"","svgPathId":"UBERON_0002106"},{"factorName":"thymus","color":"","value":"","svgPathId":"UBERON_0002370"}]},
	     {"id":"ENSMUSG00000090298","name":"Gm4794","expressions":[{"factorName":"heart","color":"","value":"","svgPathId":"UBERON_0000948"},{"factorName":"hippocampus","color":"","value":"","svgPathId":"EFO_0000530"},{"factorName":"liver","color":"#C0C0C0","value":"132","svgPathId":"UBERON_0002107"},{"factorName":"lung","color":"","value":"","svgPathId":"UBERON_0002048"},{"factorName":"spleen","color":"","value":"","svgPathId":"UBERON_0002106"},{"factorName":"thymus","color":"","value":"","svgPathId":"UBERON_0002370"}]},
	     {"id":"ENSMUSG00000002500","name":"Rpl3l","expressions":[{"factorName":"heart","color":"#C0C0C0","value":"127","svgPathId":"UBERON_0000948"},{"factorName":"hippocampus","color":"","value":"","svgPathId":"EFO_0000530"},{"factorName":"liver","color":"","value":"","svgPathId":"UBERON_0002107"},{"factorName":"lung","color":"","value":"","svgPathId":"UBERON_0002048"},{"factorName":"spleen","color":"","value":"","svgPathId":"UBERON_0002106"},{"factorName":"thymus","color":"","value":"","svgPathId":"UBERON_0002370"}]},
	     {"id":"ENSMUSG00000029158","name":"Yipf7","expressions":[{"factorName":"heart","color":"#C0C0C0","value":"123","svgPathId":"UBERON_0000948"},{"factorName":"hippocampus","color":"","value":"","svgPathId":"EFO_0000530"},{"factorName":"liver","color":"","value":"","svgPathId":"UBERON_0002107"},{"factorName":"lung","color":"","value":"","svgPathId":"UBERON_0002048"},{"factorName":"spleen","color":"","value":"","svgPathId":"UBERON_0002106"},{"factorName":"thymus","color":"","value":"","svgPathId":"UBERON_0002370"}]},
	     */
	    propTypes: {
	        anatomogramData: React.PropTypes.object.isRequired,
	        expressedTissueColour: React.PropTypes.string.isRequired,
	        hoveredTissueColour: React.PropTypes.string.isRequired,
	        profileRows: React.PropTypes.arrayOf(React.PropTypes.shape({
	            id: React.PropTypes.string,
	            name: React.PropTypes.string.isRequired,
	            expressions: React.PropTypes.arrayOf(React.PropTypes.shape({
	                factorName: React.PropTypes.string,
	                color: React.PropTypes.string,
	                value: React.PropTypes.string.isRequired,
	                svgPathId: React.PropTypes.string
	            })).isRequired
	        })).isRequired,
	        eventEmitter: React.PropTypes.instanceOf(EventEmitter),
	        atlasBaseURL: React.PropTypes.string.isRequired
	    },

	    getInitialState: function () {

	        var availableAnatomograms = [];
	        if (this.props.anatomogramData.maleAnatomogramFile) {
	            availableAnatomograms.push({ id: "male",
	                anatomogramFile: this.props.atlasBaseURL + "/resources/svg/" + this.props.anatomogramData.maleAnatomogramFile,
	                toggleSrcTemplate: this.props.atlasBaseURL + this.props.anatomogramData.toggleButtonMaleImageTemplate });
	        }
	        if (this.props.anatomogramData.femaleAnatomogramFile) {
	            availableAnatomograms.push({ id: "female",
	                anatomogramFile: this.props.atlasBaseURL + "/resources/svg/" + this.props.anatomogramData.femaleAnatomogramFile,
	                toggleSrcTemplate: this.props.atlasBaseURL + this.props.anatomogramData.toggleButtonFemaleImageTemplate });
	        }
	        if (this.props.anatomogramData.brainAnatomogramFile) {
	            availableAnatomograms.push({ id: "brain",
	                anatomogramFile: this.props.atlasBaseURL + "/resources/svg/" + this.props.anatomogramData.brainAnatomogramFile,
	                toggleSrcTemplate: this.props.atlasBaseURL + this.props.anatomogramData.toggleButtonBrainImageTemplate });
	        }

	        var allExpressedFactors = [],
	            expressedFactorsPerRow = {};
	        this.props.profileRows.forEach(function (profileRow) {
	            var expressedFactors = [];
	            profileRow.expressions.forEach(function (expression) {
	                if (expression.value !== "NT" && expression.value !== "") {
	                    expressedFactors.push(expression.svgPathId);
	                }
	            });
	            expressedFactorsPerRow[profileRow.name] = expressedFactors;
	            allExpressedFactors = allExpressedFactors.concat(expressedFactors);
	        });

	        function onlyUnique(value, index, self) {
	            return self.indexOf(value) === index;
	        }

	        return {
	            selectedId: availableAnatomograms[0].id,
	            availableAnatomograms: availableAnatomograms,
	            expressedFactors: allExpressedFactors.filter(onlyUnique),
	            expressedFactorsPerRow: expressedFactorsPerRow,
	            hoveredPathId: null,
	            hoveredRowId: null
	        };
	    },

	    render: function () {
	        function containsHuman(str) {
	            return str.indexOf("human") > -1;
	        }

	        var height = containsHuman(this.props.anatomogramData.maleAnatomogramFile) ? "360" : "250";

	        return React.createElement(
	            'div',
	            { className: 'gxaAnatomogram', style: { display: "table", paddingTop: "4px" } },
	            React.createElement(
	                'div',
	                { style: { display: "table-row" } },
	                React.createElement(
	                    'div',
	                    { style: { display: "table-cell", verticalAlign: "top" } },
	                    React.createElement(AnatomogramSelectImageButtons, { selectedId: this.state.selectedId, availableAnatomograms: this.state.availableAnatomograms, onClick: this._handleChange })
	                ),
	                React.createElement('svg', { ref: 'anatomogram', style: { display: "table-cell", width: "230px", height: height + "px" } })
	            )
	        );
	    },

	    componentDidMount: function () {
	        this.props.eventEmitter.addListener("gxaHeatmapColumnHoverChange", this._highlightPath);
	        this.props.eventEmitter.addListener("gxaHeatmapRowHoverChange", this._highlightRow);
	        this._loadAnatomogram(this._getAnatomogramSVGFile(this.state.selectedId));
	    },

	    // Only displays/highlights the relevant tissues to avoid loading the anatomogram every time we hover over a tissue or a factor header
	    componentDidUpdate: function () {
	        var svg = Snap(ReactDOM.findDOMNode(this.refs.anatomogram)).select("g");
	        this._displayAllOrganismParts(svg);
	    },

	    _handleChange: function (newSelectedId) {
	        if (newSelectedId !== this.state.selectedId) {
	            this._loadAnatomogram(this._getAnatomogramSVGFile(newSelectedId));
	            this.setState({ selectedId: newSelectedId });
	        }
	    },

	    // TODO We could manually highlight un-highlight the affected tissues instead of re-displaying all of them, as setState triggers componentDidUpdate
	    _highlightPath: function (svgPathId) {
	        this.setState({ hoveredPathId: svgPathId });
	    },

	    _highlightRow: function (rowId) {
	        this.setState({ hoveredRowId: rowId });
	    },

	    _getAnatomogramSVGFile: function (id) {
	        for (var i = 0; i < this.state.availableAnatomograms.length; i++) {
	            if (id === this.state.availableAnatomograms[i].id) {
	                return this.state.availableAnatomograms[i].anatomogramFile;
	            }
	        }
	    },

	    _loadAnatomogram: function (svgFile) {

	        var svgCanvas = Snap(ReactDOM.findDOMNode(this.refs.anatomogram)),
	            allElements = svgCanvas.selectAll("*");

	        if (allElements) {
	            allElements.remove();
	        }

	        var displayAllOrganismPartsCallback = this._displayAllOrganismParts;
	        var registerHoverEventsCallback = this._registerHoverEvents;
	        Snap.load(svgFile, function (fragment) {
	            var g = fragment.select("g");
	            g.transform("S1.6,0,0");
	            displayAllOrganismPartsCallback(g);
	            registerHoverEventsCallback(g);
	            svgCanvas.append(g);
	        });
	    },

	    _displayAllOrganismParts: function (svg) {
	        if (svg) {
	            // Sometimes svg is null... why?
	            this.props.anatomogramData.allSvgPathIds.forEach(function (svgPathId) {
	                this._displayOrganismPartsWithDefaultProperties(svg, svgPathId);
	            }, this);
	        }
	    },

	    _hoveredRowContainsPathId: function (svgPathId) {
	        if (!this.state.hoveredRowId) {
	            return false;
	        }

	        return this.state.expressedFactorsPerRow[this.state.hoveredRowId].indexOf(svgPathId) > -1;
	    },

	    _displayOrganismPartsWithDefaultProperties: function (svg, svgPathId) {

	        var colour = this.props.expressedTissueColour;
	        if (this.state.hoveredPathId === svgPathId || this._hoveredRowContainsPathId(svgPathId)) {
	            colour = this.props.hoveredTissueColour;
	        }

	        if (this.state.expressedFactors.indexOf(svgPathId) > -1) {
	            this._highlightOrganismParts(svg, svgPathId, colour, 0.7);
	        } else {
	            this._highlightOrganismParts(svg, svgPathId, "gray", 0.5);
	        }
	    },

	    _highlightOrganismParts: function (svg, svgPathId, colour, opacity) {
	        Anatomogram._recursivelyChangeProperties(svg.select("#" + svgPathId), colour, opacity);
	    },

	    _registerHoverEvents: function (svg) {
	        if (svg) {
	            // Sometimes svg is null... why?

	            var eventEmitter = this.props.eventEmitter,
	                hoverColour = this.props.hoveredTissueColour,
	                highlightOrganismPartsCallback = this._highlightOrganismParts,
	                displayOrganismPartsWithDefaultPropertiesCallback = this._displayOrganismPartsWithDefaultProperties;
	            var mouseoverCallback = function (svgPathId) {
	                highlightOrganismPartsCallback(svg, svgPathId, hoverColour, 0.7);
	                eventEmitter.emit('gxaAnatomogramTissueMouseEnter', svgPathId);
	            };
	            var mouseoutCallback = function (svgPathId) {
	                displayOrganismPartsWithDefaultPropertiesCallback(svg, svgPathId);
	                eventEmitter.emit('gxaAnatomogramTissueMouseLeave', svgPathId);
	            };

	            this.props.anatomogramData.allSvgPathIds.forEach(function (svgPathId) {
	                var svgElement = svg.select("#" + svgPathId);
	                if (svgElement) {
	                    svgElement.mouseover(function () {
	                        mouseoverCallback(svgPathId);
	                    });
	                    svgElement.mouseout(function () {
	                        mouseoutCallback(svgPathId);
	                    });
	                }
	            }, this);
	        }
	    },

	    statics: {
	        _recursivelyChangeProperties: function (svgElement, colour, opacity) {

	            if (svgElement) {
	                var innerElements = svgElement.selectAll("*");

	                if (innerElements.length > 0) {
	                    innerElements.forEach(function (innerElement) {
	                        Anatomogram._recursivelyChangeProperties(innerElement);
	                    });
	                }

	                svgElement.attr({ "fill": colour, "fill-opacity": opacity });
	            }
	        },

	        _recursivelySelectElements: function (svgElement) {
	            if (!svgElement) {
	                return [];
	            }

	            var innerElements = svgElement.selectAll("*");
	            if (innerElements.length === 0) {
	                return [svgElement];
	            } else {
	                var allElements = [];
	                innerElements.forEach(function (innerElement) {
	                    allElements = allElements.concat(Anatomogram._recursivelySelectElements(innerElement));
	                });
	                return allElements;
	            }
	        }
	    }

	});

	//*------------------------------------------------------------------*

	module.exports = Anatomogram;

/***/ },
/* 163 */
569,
/* 164 */
[1284, 163],
/* 165 */
[1281, 163],
/* 166 */
584
]);