/** @jsx React.DOM */

/*global React */
/* Modules and parameters for their init methods are passed in here.
 Parameters that affect how the DOM is generated as passed in as props. */

var heatmapModule = (function($, React, genePropertiesTooltipModule, factorInfoTooltipModule, contrastInfoTooltipModule, helpTooltipsModule, TranscriptPopup) {

    var build = function build(heatmapConfig, $prefFormDisplayLevelsInputElement) {

        var Differential = React.createClass({

            columnHeaders: function () {
                var props = this.props;
                return React.createClass({
                    render: function () {
                        return (
                            <ContrastHeaders contrasts={props.contrasts} experimentAccession={heatmapConfig.experimentAccession} showMaPlotButton={heatmapConfig.showMaPlotButton}/>
                            );
                    }
                });
            },

            cells: function (props) {
                return props.expressions.map(function (expression) {
                    return <HeatmapCellDifferential contrastName={expression.contrastName} color={expression.color} foldChange={expression.foldChange} pValue={expression.pValue} tStat={expression.tStat} displayLevels={props.displayLevels} svgPathId={expression.svgPathId} showGeneSetProfiles={props.showGeneSetProfiles} geneId={props.geneId} geneName={props.geneName}/>
                });
            },

            legend: function (state) {
                return (
                    <LegendDifferential displayLevels={state.displayLevels} minDownLevel={state.profiles.minDownLevel} maxDownLevel={state.profiles.maxDownLevel} minUpLevel={state.profiles.minUpLevel} maxUpLevel={state.profiles.maxUpLevel}/>
                    );
            },

            render: function () {
                return (
                    <Heatmap legend={this.legend} columnHeaders={this.columnHeaders()} cells={this.cells} displayLevelsButton={DisplayLevelsButtonDifferential} profiles={this.props.profiles} geneSetProfiles={this.props.geneSetProfiles} />
                    );
            }
        });

        var Baseline = React.createClass({

            columnHeaders: function () {
                var props = this.props;
                return React.createClass({
                    render: function () {
                        return (
                            <FactorHeaders assayGroupFactors={props.assayGroupFactors} experimentAccession={heatmapConfig.experimentAccession} />
                            )
                    }
                });
            },

            cells: function (props) {
                return props.expressions.map(function (expression) {
                    return <HeatmapCellBaseline factorName={expression.factorName} color={expression.color} value={expression.value} displayLevels={props.displayLevels} svgPathId={expression.svgPathId} showGeneSetProfiles={props.showGeneSetProfiles} geneId={props.geneId} geneName={props.geneName}/>
                });
            },

            legend: function (state) {
                return (
                    <LegendBaseline displayLevels={state.displayLevels} minExpressionLevel={state.profiles.minExpressionLevel} maxExpressionLevel={state.profiles.maxExpressionLevel}/>
                );
            },

            render: function () {
                return (
                    <Heatmap legend={this.legend} columnHeaders={this.columnHeaders()} cells={this.cells} displayLevelsButton={DisplayLevelsButtonBaseline} profiles={this.props.profiles} geneSetProfiles={this.props.geneSetProfiles} />
                    );
            }
        });

        var Heatmap = React.createClass({

            getInitialState: function () {
                var displayLevels = ($prefFormDisplayLevelsInputElement.val() === "true");
                return { showGeneSetProfiles: false,
                    displayLevels: displayLevels,
                    profiles: this.props.profiles};
            },

            toggleGeneSets: function () {
                var newProfiles = this.state.showGeneSetProfiles ? this.props.profiles : this.props.geneSetProfiles;
                this.setState({showGeneSetProfiles: !this.state.showGeneSetProfiles, profiles: newProfiles});
            },

            toggleLevels: function () {
                var newDisplayLevels = !this.state.displayLevels;
                this.setState({displayLevels: newDisplayLevels});
                $prefFormDisplayLevelsInputElement.val(newDisplayLevels);
            },

            render: function () {
                return (
                    <table>
                        <tr>
                            <td>
                                <span id="geneSetsCount">Showing {this.state.profiles.genes.length} of {this.state.profiles.totalGeneCount} genes found: </span>
                                    {this.props.geneSetProfiles ? <a href="javascript:void(0)" onClick={this.toggleGeneSets}>{this.state.showGeneSetProfiles ? '(show individual genes)' : '(show by gene set)'}</a> : ''}
                            </td>
                            <td>
                                {this.props.legend(this.state)}
                            </td>
                        </tr>
                        <tr>
                            <td colSpan="2">
                                <div className="block">
                                    <table>
                                        <tbody>
                                            <tr>
                                                <td>
                                                    <HeatmapTable cells={this.props.cells} displayLevelsButton={this.props.displayLevelsButton} columnHeaders={this.props.columnHeaders} profiles={this.state.profiles.genes} displayLevels={this.state.displayLevels} toggleLevels={this.toggleLevels} showGeneSetProfiles={this.state.showGeneSetProfiles}/>
                                                </td>
                                                <td style={{"vertical-align": "top"}}>
                                                    <DownloadProfilesButton />
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </td>
                        </tr>
                    </table>
                    );
            }
        });

        var DownloadProfilesButton = (function (contextRoot, downloadProfilesURL) {
            return React.createClass({
                render: function () {
                    return (
                        <div style={{"float": "left"}}>
                            <a ref="downloadProfilesLink" title="Top 50 genes displayed on page. Download results to see the rest." href={downloadProfilesURL} className="button-image" target="_blank">
                                <img id="download-profiles" alt="Download query results" style={{width: "20px"}} src={contextRoot + "/resources/images/download_blue_small.png"}/>
                            </a>
                        </div>
                        );
                },

                componentDidMount: function () {
                    $(this.refs.downloadProfilesLink.getDOMNode()).button().tooltip();
                }
            });
        })(heatmapConfig.contextRoot, heatmapConfig.downloadProfilesURL);

        var LegendBaseline = (function (contextRoot) {
            return React.createClass({
                render: function () {
                    return (
                        <div style={{float: "right", "padding-left": "100px"}}>
                            <div style={{float: "left"}}>
                                <table style={{"font-size": "10px"}}>
                                    <tbody>
                                        <LegendRow displayLevels={this.props.displayLevels} lowExpressionLevel={this.props.minExpressionLevel} highExpressionLevel={this.props.maxExpressionLevel} lowValueColour="#C0C0C0" highValueColour="#0000FF"/>
                                    </tbody>
                                </table>
                            </div>
                            <div ref="legendHelp" data-help-loc="#gradient-base" style={{float: "left;"}}></div>
                        </div>
                        );
                },

                componentDidMount: function () {
                    helpTooltipsModule.init('experiment', contextRoot, this.refs.legendHelp.getDOMNode());
                }
            });
        })(heatmapConfig.contextRoot);


        var LegendDifferential = (function (contextRoot) {
            return React.createClass({
                render: function () {
                    return (
                        <div style={{float: "right", "padding-left": "100px"}}>
                            <div style={{float: "left"}}>
                                <table style={{"font-size": "10px"}}>
                                    <tbody>
                                        <LegendRow displayLevels={this.props.displayLevels} lowExpressionLevel={this.props.minDownLevel} highExpressionLevel={this.props.maxDownLevel} lowValueColour="#C0C0C0" highValueColour="#0000FF"/>
                                        <LegendRow displayLevels={this.props.displayLevels} lowExpressionLevel={this.props.minUpLevel} highExpressionLevel={this.props.maxUpLevel} lowValueColour="#FFAFAF" highValueColour="#FF0000"/>
                                    </tbody>
                                </table>
                            </div>
                            <div ref="legendHelp" data-help-loc="#gradient-differential" style={{float: "left;"}}></div>
                        </div>
                        );
                },

                componentDidMount: function () {
                    helpTooltipsModule.init('experiment', contextRoot, this.refs.legendHelp.getDOMNode());
                }
            });
        })(heatmapConfig.contextRoot);


        var LegendRow = React.createClass({
            render: function () {
                return (
                    <tr>
                        <td>
                            <span style={this.props.displayLevels ? {'white-space': 'nowrap'} : {display: "none"}} className="gradient-level-min">{this.props.lowExpressionLevel}</span>
                        </td>
                        <td width="200px">
                            <HeatmapLegendGradient lowValueColour={this.props.lowValueColour} highValueColour={this.props.highValueColour}/>
                        </td>
                        <td>
                            <span style={this.props.displayLevels ? {'white-space': 'nowrap'} : {display: "none"}} className="gradient-level-max">{this.props.highExpressionLevel}</span>
                        </td>
                    </tr>
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
                        overflow: "auto",
                        "background-image": backgroundImage,
                        filter: filter}}>
                    &nbsp;
                    </div>
                    );
            }
        });

        var HeatmapTable = React.createClass({

            render: function () {
                return (
                    <table id="heatmap-table" className="table-grid">
                        <HeatmapTableHeader displayLevelsButton={this.props.displayLevelsButton} columnHeaders={this.props.columnHeaders} displayLevels={this.props.displayLevels} toggleLevels={this.props.toggleLevels} showGeneSetProfiles={this.props.showGeneSetProfiles}/>
                        <HeatmapTableBody cells={this.props.cells} profiles={this.props.profiles} displayLevels={this.props.displayLevels} showGeneSetProfiles={this.props.showGeneSetProfiles}/>
                    </table>
                    );
            }

        });

        var HeatmapTableHeader = React.createClass({

            render: function () {
                var ColumnHeaders = this.props.columnHeaders;
                return (
                    <thead>
                        <TopLeftCorner displayLevelsButton={this.props.displayLevelsButton} displayLevels={this.props.displayLevels} toggleLevels={this.props.toggleLevels}/>
                        <ColumnHeaders />
                        <tr id="injected-header">
                            <td className="horizontal-header-cell">{this.props.showGeneSetProfiles ? 'Gene set' : 'Gene'}</td>
                        </tr>
                    </thead>
                    );
            }

        });

        function restrictLabelSize(label, maxSize) {
            var result = label;
            if (result.length > maxSize) {
                result = result.substring(0, maxSize);
                if (result.lastIndexOf(" ") > maxSize - 5) {
                    result = result.substring(0, result.lastIndexOf(" "));
                }
                result = result + "...";
            }
            return result;
        }

        var FactorHeaders = React.createClass({

            render: function () {
                var props = this.props;
                var factorHeaders = this.props.assayGroupFactors.map(function (assayGroupFactor) {
                    var factor = assayGroupFactor.factor;
                    return <FactorHeader factorName={factor.value} svgPathId={factor.valueOntologyTerm} assayGroupId={assayGroupFactor.assayGroupId} experimentAccession={props.experimentAccession}/>;
                });

                return (
                    <div>{factorHeaders}</div>
                    );
            }

        });

        var FactorHeader = (function (contextRoot, accessKey) {
            return React.createClass({

                componentDidMount: function () {
                    factorInfoTooltipModule.init(contextRoot, accessKey, this.getDOMNode());
                },

                render: function () {
                    var truncatedFactorName = restrictLabelSize(this.props.factorName, 17);
                    return (
                        <th className="rotated_cell vertical-header-cell factorNameCell" rowSpan="2">
                            <div data-organism-part={this.props.factorName} data-svg-path-id={this.props.svgPathId} data-assay-group-id={this.props.assayGroupId} data-experiment-accession={this.props.experimentAccession} className="factor-header rotate_text">{truncatedFactorName}</div>
                        </th>
                        );
                }
            });
        })(heatmapConfig.contextRoot, heatmapConfig.accessKey);

        var ContrastHeaders = React.createClass({

            render: function () {
                var props = this.props;
                var contrastHeaders = this.props.contrasts.map(function (contrast) {
                    return <ContrastHeader contrastName={contrast.displayName} arrayDesignAccession={contrast.arrayDesignAccession} contrastId={contrast.id} experimentAccession={props.experimentAccession} showMaPlotButton={props.showMaPlotButton}/>;
                });

                return (
                    <div>{contrastHeaders}</div>
                    );
            }

        });

        var ContrastHeader = (function (contextRoot, accessKey) {
            return React.createClass({

                componentDidMount: function () {
                    contrastInfoTooltipModule.init(contextRoot, accessKey, this.getDOMNode());
                    if (this.props.showMaPlotButton) {
                        var maButton = this.refs.maButton.getDOMNode();

                        $(maButton).tooltip().button();

                        $(maButton).fancybox({
                            padding:0,
                            openEffect:'elastic',
                            closeEffect:'elastic'
                        });
                    }
                },

                render: function () {
                    var truncatedName = restrictLabelSize(this.props.contrastName, 17);
                    var maPlotURL = contextRoot + '/external-resources/' + this.props.experimentAccession + '/' + (this.props.arrayDesignAccession ? this.props.arrayDesignAccession + '/' : '' ) + this.props.contrastId + '/ma-plot.png';
                    var thStyle = this.props.showMaPlotButton ? {width: "60px"} : {};
                    var textStyle = this.props.showMaPlotButton ? {top: "57px"} : {};

                    var maPlotButton = (
                        <div style={{"text-align":"right", "padding-right":"3px"}}>
                            <a href={maPlotURL} ref="maButton" className='button-image' title='Click to view MA plot for the contrast across all genes'><img src={contextRoot + '/resources/images/maplot-button.png'}/></a>
                        </div>
                    );

                    return (
                        <th className="rotated_cell vertical-header-cell contrastNameCell" rowSpan="2" style={thStyle}>
                            <div data-organism-part={this.props.contrastName} data-contrast-id={this.props.contrastId} data-experiment-accession={this.props.experimentAccession} className="factor-header rotate_text" style={textStyle}>{truncatedName}</div>
                            {this.props.showMaPlotButton ? maPlotButton : ''}
                        </th>
                        );
                }
            });
        })(heatmapConfig.contextRoot, heatmapConfig.accessKey);

        var TopLeftCorner = (function (contextRoot) {
            return React.createClass({

                render: function () {
                    var displayLevelsButton = this.props.displayLevelsButton;
                    return (
                        <th className="horizontal-header-cell">
                            <div className="heatmap-matrix-top-left-corner">
                                <span id='tooltip-span' data-help-loc='#heatMapTableCellInfo' ref='tooltipSpan'></span>
                                <displayLevelsButton displayLevels={this.props.displayLevels} toggleLevels={this.props.toggleLevels}/>
                            </div>
                        </th>
                        );
                },

                componentDidMount: function () {
                    helpTooltipsModule.init('experiment', contextRoot, this.refs.tooltipSpan.getDOMNode());
                }
            });
        })(heatmapConfig.contextRoot);

        var createDisplayLevelsButton = function (hideText, showText) {

            return React.createClass({

                buttonText: function (displayLevels) {
                    return displayLevels ? hideText : showText;
                },

                updateButtonText: function () {
                    $(this.getDOMNode()).button({ label: this.buttonText(this.props.displayLevels) });
                },

                render: function () {
                    return (
                        <button id='display-levels' className='display-levels-button' onClick={this.props.toggleLevels}></button>
                        );
                },

                componentDidMount: function () {
                    this.updateButtonText();
                },

                componentDidUpdate: function () {
                    this.updateButtonText();
                }

            });
        };

        var DisplayLevelsButtonBaseline = createDisplayLevelsButton('Hide levels', 'Display levels');

        var DisplayLevelsButtonDifferential = createDisplayLevelsButton('Hide log<sub>2</sub>-fold change', 'Display log<sub>2</sub>-fold change');

        var HeatmapTableBody = React.createClass({
            render: function () {
                var props = this.props;
                var geneProfilesRows = this.props.profiles.map(function (profile) {
                    return <GeneProfileRow cells={props.cells} geneId={profile.geneId} geneName={profile.geneName} expressions={profile.expressions} displayLevels={props.displayLevels} showGeneSetProfiles={props.showGeneSetProfiles}/>;
                });

                return (
                    <tbody>
            {geneProfilesRows}
                    </tbody>
                    );
            }
        });

        var GeneProfileRow = (function (contextRoot, toolTipHighlightedWords, isExactMatch, enableGeneLinks) {

            return React.createClass({
                geneNameLinked: function () {
                    var geneURL = this.props.showGeneSetProfiles ? '/query?geneQuery=' + this.props.geneName + '&exactMatch=' + isExactMatch : '/genes/' + this.props.geneId;

                    // don't render id for gene sets to prevent tooltips
                    return (
                        <a ref="geneName" title="" id={this.props.showGeneSetProfiles ? '' : this.props.geneId} href={contextRoot + geneURL}>{this.props.geneName}</a>
                        );
                },

                geneNameNotLinked: function () {
                    // don't render id for gene sets to prevent tooltips
                    return (
                        <div ref="geneName" title="" id={this.props.showGeneSetProfiles ? '' : this.props.geneId}>{this.props.geneName}</div>
                        );
                },

                render: function () {
                    // NB: empty title tag below is required for tooltip to work
                    return (
                        <tr>
                            <td className="horizontal-header-cell">
                                { enableGeneLinks ? this.geneNameLinked() : this.geneNameNotLinked()}
                            </td>
                            {this.props.cells(this.props)}
                        </tr>
                        );
                },

                componentDidMount: function () {
                    genePropertiesTooltipModule.init(contextRoot, toolTipHighlightedWords, this.refs.geneName.getDOMNode());
                }
            });
        })(heatmapConfig.contextRoot, heatmapConfig.toolTipHighlightedWords, heatmapConfig.isExactMatch, heatmapConfig.enableGeneLinks);


        var HeatmapCellBaseline = (function (contextRoot, experimentAccession, species, selectedFilterFactorsJson, queryFactorType) {

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

                onClick: function () {
                    if (!this.props.showGeneSetProfiles && hasKnownExpression(this.props.value)) {

                        var factorValue = this.props.factorName,
                            geneId = this.props.geneId,
                            geneName = this.props.geneName;

                        TranscriptPopup.display(contextRoot, experimentAccession, geneId, geneName, queryFactorType, factorValue, selectedFilterFactorsJson, species);
                    }
                },

                render: function () {
                    return (
                        <td style={{"background-color": this.props.color}} onClick={this.onClick}>
                            <div
                            className={isUnknownExpression(this.props.value) || this.props.displayLevels ? "show_cell" : "hide_cell"}
                            data-organism-part={this.props.factorName}
                            data-color={this.props.color}
                            data-svg-path-id={this.props.svgPathId}>
                                {isUnknownExpression(this.props.value) ? unknownCell() : this.props.value}
                            </div>
                        </td>
                        );
                },

                componentDidMount: function () {
                    if (isUnknownExpression(this.props.value)) {
                        helpTooltipsModule.init('experiment', contextRoot, this.refs.unknownCell.getDOMNode());
                    }
                }
            });
        })(heatmapConfig.contextRoot, heatmapConfig.experimentAccession, heatmapConfig.species, heatmapConfig.selectedFilterFactorsJson, heatmapConfig.queryFactorType);

        var HeatmapCellDifferential = (function () {

            return React.createClass({

                render: function () {
                    return (
                        <td style={{"background-color": this.props.color}} onClick={this.onClick}>
                            <div className={this.props.displayLevels ? "show_cell" : "hide_cell"}>
                                {this.props.foldChange}
                            </div>
                        </td>
                        );
                },

                componentDidMount: function () {
                    this.initTooltip(this.getDOMNode());
                },

                initTooltip: function(element) {

                    //there must be a cleaner way to do this, but I don't know it yet!
                    function buildHeatmapCellTooltip (pValue, tstatistic, foldChange) {
                        return "<table class='table-grid' style='margin: 0px; padding: 0px;'><thead><th class='header-cell'>Adjusted <i>p</i>-value</th>" +
                            (tstatistic !== undefined ? "<th class='header-cell'><i>t</i>-statistic</th>" : "") +
                            "<th class='header-cell'>Log<sub>2</sub>-fold change</th></thead>" +
                            "<tbody><tr><td style='padding:6px'><span style=\"white-space: nowrap;\">" + pValue + "</span></td>" +
                            (tstatistic !== undefined ? "<td style='padding:6px'>" + tstatistic + "</td>" : "") +
                            "<td style='padding:6px'>" + foldChange + "</td></tr></tbody>" +
                            "</table>";
                    }

                    var props = this.props;

                    $(element).attr('title', '').tooltip(
                        {
                            open:function (event, ui) {
                                ui.tooltip.css('background', props.color);
                            },
                            tooltipClass:"help-tooltip pvalue-tooltip-styling",

                            content:function () {
                                return buildHeatmapCellTooltip(props.pValue, props.tStat, props.foldChange);
                            }

                        });
                }

            });
        })();

        return {
            Baseline: Baseline,
            Differential: Differential
        };
    };

    return {
        build: build
    };

})(jQuery, React, genePropertiesTooltipModule, factorInfoTooltipModule, contrastInfoTooltipModule, helpTooltipsModule, TranscriptPopup);