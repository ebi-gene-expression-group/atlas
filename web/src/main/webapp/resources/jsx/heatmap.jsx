/** @jsx React.DOM */

/* Modules and parameters for their init methods are passed in here.
 Parameters that affect how the DOM is generated as passed in as props. */
var createHeatmap = function createHeatMap(heatmapConfig, prefFormDisplayLevelsInputElement, $, React, heatmapModule, genePropertiesTooltipModule, factorInfoTooltipModule, helpTooltipsModule) {

    var Heatmap = (function (heatmapModule, prefFormDisplayLevelsInputElement) {
        return React.createClass({
            render: function () {
                return (
                    <table>
                        <tr>
                            <td>
                                <span id="geneSetsCount">Showing {this.props.profiles.length} of {this.props.totalGeneCount} genes found:</span>
                                <a id="showGeneSetProfiles" href="javascript:void(0)">(show by gene set)</a>
                            </td>
                            <td>
                                <HeatmapLegend lowExpressionLevel={this.props.minExpressionLevel} highExpressionLevel={this.props.maxExpressionLevel}/>
                            </td>
                        </tr>
                        <tr>
                            <td colSpan="2">
                                <div className="heatmap-position">
                                    <HeatmapTable assayGroupFactors={this.props.assayGroupFactors} profiles={this.props.profiles}/>
                                </div>
                            </td>
                        </tr>
                    </table>
                    );
            },

            componentDidMount: function () {
                var heatmapElement = this.getDOMNode();
                heatmapModule.initDisplayLevelsButtonOnClick(heatmapElement, prefFormDisplayLevelsInputElement);
            }

        });
    })(heatmapModule, prefFormDisplayLevelsInputElement);

    var HeatmapLegend = React.createClass({
        render: function () {
            return (
                <div style={{float:"right", "padding-left": "100px"}}>
                    <div style={{float:"left"}}>
                        <table style={{"font-size":"10px"}} id="baseline-heatmap-legend">
                            <tbody>
                                <tr>
                                    <td>
                                        <span style={{display:"none"}} className="gradient-level-min">{this.props.lowExpressionLevel}</span>
                                    </td>
                                    <td width="200px">
                                        <HeatmapLegendGradient lowValueColour="#C0C0C0" highValueColour="#0000FF"/>
                                    </td>
                                    <td>
                                        <span style={{display:"none"}} className="gradient-level-max">{this.props.highExpressionLevel}</span>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <div id="baseline-help-diff" data-help-loc="#gradient-base" style={{float:"left;"}}></div>
                </div>
            );
        }
    });

    var HeatmapLegendGradient = React.createClass({
        render: function () {

            var BACKGROUND_IMAGE_TEMPLATE = "-webkit-gradient(linear, left top, right top,color-stop(0, ${lowValueColour}), color-stop(1, ${highValueColour}));background-image: -moz-linear-gradient(left, ${lowValueColour}, ${highValueColour});background-image: -o-linear-gradient(left, ${lowValueColour}, ${highValueColour})";
            var FILTER_TEMPLATE = "progid:DXImageTransform.Microsoft.Gradient(GradientType =1,startColorstr=${lowValueColour},endColorstr=${highValueColour})";

            var backgroundImage = BACKGROUND_IMAGE_TEMPLATE.replace(/\${lowValueColour}/, this.props.lowValueColour).replace(/\${highValueColour}/, this.props.highValueColour);
            var filter = FILTER_TEMPLATE.replace(/\${lowValueColour}/, this.props.lowValueColour).replace(/\${highValueColour}/, this.props.highValueColour);

            return (
                <div className="color-gradient" style={{
                    overflow:"auto",
                    "background-image": backgroundImage,
                    filter: filter}}>
                    &nbsp;
                </div>
            );
        }
    });

    var HeatmapTable = (function (experimentAccession) {
        return React.createClass({

            render: function () {
                return (
                    <table id="heatmap-table" className="table-grid">
                        <HeatmapTableHeader assayGroupFactors={this.props.assayGroupFactors} experimentAccession={experimentAccession} />
                        <HeatmapTableBody profiles={this.props.profiles} />
                    </table>
                    );
            }

        });
    })(heatmapConfig.experimentAccession);

    var HeatmapTableHeader = React.createClass({

        render: function () {
            var experimentAccession = this.props.experimentAccession;
            var factorNames = this.props.assayGroupFactors.map(function (assayGroupFactor) {
                var factor = assayGroupFactor.factor;
                return <HeatmapTableHeaderFactorNames factorName={factor.value} svgPathId={factor.valueOntologyTerm} assayGroupId={assayGroupFactor.assayGroupId} experimentAccession={experimentAccession}/>;
            });

            return (
                <thead>
                    <HeatmapTableHeaderTopLeftCorner />
        {factorNames}
                    <tr id="injected-header">
                        <td className="horizontal-header-cell">Gene</td>
                    </tr>
                </thead>
                );
        }

    });

    var HeatmapTableHeaderFactorNames = (function (factorInfoTooltipModule, contextRoot, accessKey) {
        return React.createClass({
            restrictLabelSize: function (label, maxSize) {
                var result = label;
                if (result.length > maxSize) {
                    result = result.substring(0, maxSize);
                    if (result.lastIndexOf(" ") > maxSize - 5) {
                        result = result.substring(0, result.lastIndexOf(" "));
                    }
                    result = result + "...";
                }
                return result;
            },

            componentDidMount: function () {
                factorInfoTooltipModule.init(contextRoot, accessKey, this.getDOMNode());
            },

            render: function () {
                var truncatedFactorName = this.restrictLabelSize(this.props.factorName, 17);
                return (
                    <th className="rotated_cell vertical-header-cell factorNameCell" rowSpan="2">
                        <div data-organism-part={this.props.factorName} data-svg-path-id={this.props.svgPathId} data-assay-group-id={this.props.assayGroupId} data-experiment-accession={this.props.experimentAccession} className="factor-header rotate_text">{truncatedFactorName}</div>
                    </th>
                    );
            }
        });
    })(factorInfoTooltipModule, heatmapConfig.contextRoot, heatmapConfig.accessKey);

    var HeatmapTableHeaderTopLeftCorner = (function (helpTooltipsModule, contextRoot) {
        return React.createClass({
            render: function () {
                return (
                    <th className="horizontal-header-cell">
                        <div className="heatmap-matrix-top-left-corner">
                            <span id='tooltip-span' data-help-loc='#heatMapTableCellInfo' ref='tooltipSpan'></span>
                            <DisplayLevelsButton />
                        </div>
                    </th>
                    );
            },

            componentDidMount: function () {
                helpTooltipsModule.init('experiment', contextRoot, this.refs.tooltipSpan.getDOMNode());
            }
        });
    })(helpTooltipsModule, heatmapConfig.contextRoot);

    var DisplayLevelsButton = React.createClass({
        render: function () {

            return (
                <button id='display-levels' className='display-levels-button' />
                );
        }
    });


    var HeatmapTableBody = React.createClass({
        render: function () {
            var geneProfilesRows = this.props.profiles.map(function (profile) {
                return <GeneProfileRow geneId={profile.geneId} geneName={profile.geneName} expressions={profile.expressions}/>;
            });

            return (
                <tbody>
        {geneProfilesRows}
                </tbody>
                );
        }
    });

    var GeneProfileRow = (function (genePropertiesTooltipModule, contextRoot, toolTipHighlightedWords) {
        return React.createClass({
            render: function () {

                var heatMapCells = this.props.expressions.map(function (expression) {
                    return <HeatmapCell factorName={expression.factorName} color={expression.color} value={expression.value} showValue="false" svgPathId={expression.svgPathId}/>
                });

                // NB: empty title tag below is required for tooltip to work
                return (
                    <tr>
                        <td className="horizontal-header-cell">
                            <a className="genename" ref="geneName" title="" id={this.props.geneId} href={"http://localhost:8080/gxa/genes/" + this.props.geneId}>{this.props.geneName}</a>
                        </td>
                        {heatMapCells}
                    </tr>
                    );
            },

            componentDidMount: function () {
                genePropertiesTooltipModule.init(contextRoot, toolTipHighlightedWords, this.refs.geneName.getDOMNode());
            }
        });
    })(genePropertiesTooltipModule, heatmapConfig.contextRoot, heatmapConfig.toolTipHighlightedWords);

    var HeatmapCell = (function (heatmapModule, helpTooltipsModule, contextRoot, experimentAccession, species, selectedFilterFactorsJson) {

        function hasKnownExpression(value) {
            // true if not blank or UNKNOWN, ie: has a expression with a known value
            return (value && !isUnknownExpression(value));
        }

        function isUnknownExpression(value) {
            return (value === "UNKNOWN")
        }

        function unknownCell() {
            return (
                <span id='unknownCell' ref='unknownCell' data-help-loc='#heatMapTableUnknownCell'></span>
            );
        }

        return React.createClass({
            render: function () {
                return (
                    <td style={{"background-color": this.props.color}}>
                        <div
                        className={this.props.showValue == "true" ? "show_cell" : "hide_cell"}
                        data-organism-part={this.props.factorName}
                        data-color={this.props.color}
                        data-svg-path-id={this.props.svgPathId}>
                            {isUnknownExpression(this.props.value) ? unknownCell() : this.props.value}
                        </div>
                    </td>
                    );
            },

            componentDidMount: function() {
                if (hasKnownExpression(this.props.value)) {
                    heatmapModule.initTranscriptBreakdownFancyBox(contextRoot, experimentAccession, species, selectedFilterFactorsJson, this.getDOMNode());
                } else if (isUnknownExpression(this.props.value)) {
                    helpTooltipsModule.init('experiment', contextRoot, this.refs.unknownCell.getDOMNode())
                }
            }
        });
    })(heatmapModule, helpTooltipsModule, heatmapConfig.contextRoot, heatmapConfig.experimentAccession, heatmapConfig.species, heatmapConfig.selectedFilterFactorsJson);

    return Heatmap;
};