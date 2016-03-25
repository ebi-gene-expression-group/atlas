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
[1336, 3],
/* 3 */
[1337, 4, 149, 153, 40, 158],
/* 4 */
[1338, 6, 7, 72, 46, 29, 19, 51, 55, 147, 92, 148, 26, 10],
/* 5 */,
/* 6 */
170,
/* 7 */
[1339, 8, 23, 27, 29, 40, 22, 21, 71],
/* 8 */
[1340, 9, 17, 19, 20, 21, 14],
/* 9 */
[1341, 10, 11, 16, 15, 14],
/* 10 */
174,
/* 11 */
[1342, 10, 12, 15, 14],
/* 12 */
[1343, 13],
/* 13 */
[1344, 14],
/* 14 */
178,
/* 15 */
[1345, 10, 14],
/* 16 */
180,
/* 17 */
[1346, 18],
/* 18 */
[1347, 14],
/* 19 */
183,
/* 20 */
[1348, 10],
/* 21 */
[1349, 10, 22, 20],
/* 22 */
186,
/* 23 */
[1350, 24, 19, 25, 26],
/* 24 */
[1351, 14],
/* 25 */
[1352, 22],
/* 26 */
[1353, 16],
/* 27 */
[1354, 28, 29],
/* 28 */
[1355, 8, 23, 29, 19, 14],
/* 29 */
[1356, 24, 30, 6, 42, 43, 45, 46, 48, 49, 19, 51, 54, 55, 40, 59, 60, 63, 14, 20, 68, 71, 26],
/* 30 */
[1357, 31, 32, 33, 38, 19, 39, 40, 41],
/* 31 */
[1358, 18],
/* 32 */
[1359, 33, 34, 35, 36, 37, 14, 26],
/* 33 */
[1360, 14],
/* 34 */
[1361, 31, 35, 14, 26],
/* 35 */
199,
/* 36 */
[1362, 14],
/* 37 */
201,
/* 38 */
[1363, 32],
/* 39 */
203,
/* 40 */
204,
/* 41 */
[1364, 10],
/* 42 */
206,
/* 43 */
[1365, 6, 40, 44],
/* 44 */
208,
/* 45 */
209,
/* 46 */
[1366, 47, 14],
/* 47 */
211,
/* 48 */
212,
/* 49 */
[1367, 50],
/* 50 */
214,
/* 51 */
[1368, 52],
/* 52 */
[1369, 53],
/* 53 */
[1370, 14],
/* 54 */
[1371, 6, 43, 48, 55, 40, 14, 26],
/* 55 */
[1372, 56, 57, 19, 51, 58, 40, 14],
/* 56 */
[1373, 57, 40, 14],
/* 57 */
[1374, 14],
/* 58 */
[1375, 14],
/* 59 */
223,
/* 60 */
[1376, 61],
/* 61 */
[1377, 62],
/* 62 */
226,
/* 63 */
[1378, 64, 69, 70, 40, 14, 26],
/* 64 */
[1379, 65, 6, 43, 48, 19, 66, 67, 51, 54, 40, 59, 14, 68, 26],
/* 65 */
[1380, 14],
/* 66 */
[1381, 18],
/* 67 */
231,
/* 68 */
232,
/* 69 */
[1382, 43, 45, 51, 40],
/* 70 */
[1383, 40, 14],
/* 71 */
[1384, 40, 16, 26],
/* 72 */
[1385, 73, 81, 84, 85, 86, 10, 90, 91, 27, 93, 94, 7, 119, 122, 46, 29, 126, 131, 132, 133, 142, 143],
/* 73 */
[1386, 31, 74, 10, 75, 77, 79, 80],
/* 74 */
[1387, 31, 32, 26, 36, 37],
/* 75 */
[1388, 57, 40, 76],
/* 76 */
[1389, 10],
/* 77 */
[1390, 78],
/* 78 */
[1391, 57, 40, 16, 26],
/* 79 */
[1392, 78],
/* 80 */
244,
/* 81 */
[1393, 31, 32, 74, 10, 55, 78, 82, 41, 83, 80],
/* 82 */
246,
/* 83 */
247,
/* 84 */
248,
/* 85 */
[1394, 80],
/* 86 */
[1395, 31, 74, 87, 29, 80],
/* 87 */
[1396, 88, 39, 89],
/* 88 */
[1397, 78, 82],
/* 89 */
253,
/* 90 */
[1398, 24, 10],
/* 91 */
[1399, 48, 92, 26],
/* 92 */
[1400, 6, 48, 29, 14, 26],
/* 93 */
[1401, 55, 58, 40, 16],
/* 94 */
[1402, 95, 97, 24, 23, 31, 30, 27, 105, 106, 110, 113, 114, 29, 115, 19, 54, 40, 44, 22, 14, 41, 80, 20, 21, 118, 71, 26],
/* 95 */
[1403, 29, 92, 96],
/* 96 */
260,
/* 97 */
[1404, 98, 10, 19, 99, 101, 102, 104, 26],
/* 98 */
262,
/* 99 */
[1405, 100],
/* 100 */
264,
/* 101 */
[1406, 98],
/* 102 */
[1407, 103],
/* 103 */
267,
/* 104 */
268,
/* 105 */
269,
/* 106 */
[1408, 28, 107, 29, 55, 40, 14],
/* 107 */
[1409, 108, 66, 14, 26],
/* 108 */
[1410, 43, 67, 16, 109],
/* 109 */
273,
/* 110 */
[1411, 111, 113, 40, 26],
/* 111 */
[1412, 57, 43, 16, 112],
/* 112 */
[1413, 6, 43, 46, 109, 14, 26],
/* 113 */
[1414, 107, 29, 55, 40, 26],
/* 114 */
[1415, 107, 28, 55, 40, 14, 26],
/* 115 */
[1416, 65, 17, 6, 51, 116, 117],
/* 116 */
[1417, 51, 63, 68, 112, 26],
/* 117 */
[1418, 112, 26],
/* 118 */
282,
/* 119 */
[1419, 120, 10, 57, 46, 29, 55, 40, 82, 121],
/* 120 */
[1420, 16],
/* 121 */
285,
/* 122 */
[1421, 24, 32, 65, 123, 69, 30, 70, 19, 47, 55],
/* 123 */
[1422, 124, 43, 66, 67, 125, 40, 59, 14, 18, 80, 26],
/* 124 */
[1423, 125, 44, 59, 14, 26],
/* 125 */
[1424, 26],
/* 126 */
[1425, 56, 57, 30, 42, 127, 58, 40],
/* 127 */
[1426, 128, 60, 96, 130],
/* 128 */
[1427, 10, 129, 76],
/* 129 */
293,
/* 130 */
294,
/* 131 */
[1428, 31, 74, 10, 127, 78, 130, 83, 80, 118],
/* 132 */
296,
/* 133 */
[1429, 31, 120, 74, 29, 134, 78, 135, 136, 87, 139, 140, 88, 141, 16, 137, 14, 80],
/* 134 */
[1430, 78],
/* 135 */
[1431, 88],
/* 136 */
[1432, 88, 137, 138, 89],
/* 137 */
301,
/* 138 */
[1433, 137],
/* 139 */
[1434, 87],
/* 140 */
[1435, 88, 89],
/* 141 */
[1436, 87],
/* 142 */
[1437, 24],
/* 143 */
[1438, 24, 144, 29, 19, 145],
/* 144 */
[1439, 40],
/* 145 */
[1440, 146],
/* 146 */
[1441, 10],
/* 147 */
311,
/* 148 */
[1442, 29],
/* 149 */
[1443, 72, 150, 147],
/* 150 */
[1444, 93, 43, 46, 49, 151, 152, 55, 59, 63, 14],
/* 151 */
315,
/* 152 */
[1445, 57, 56, 58, 40, 16],
/* 153 */
[1446, 111, 124, 123, 154, 43, 155, 108, 147, 40, 157],
/* 154 */
[1447, 43, 155, 156],
/* 155 */
[1448, 43, 66, 67, 6, 44, 109, 14, 26],
/* 156 */
320,
/* 157 */
[1449, 43, 14],
/* 158 */
[1450, 40, 26],
/* 159 */
[1451, 4],
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
[1475, 163],
/* 165 */
[1472, 163],
/* 166 */
584
]);