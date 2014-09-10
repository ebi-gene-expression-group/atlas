/** @jsx React.DOM */

/*global React */
/* Modules and parameters for their init methods are passed in here.
 Parameters that affect how the DOM is generated as passed in as props. */

var heatmapModule = (function($, React, genePropertiesTooltipModule, factorInfoTooltipModule, contrastInfoTooltipModule, helpTooltipsModule, TranscriptPopup, EventEmitter, Modernizr) {

    var TypeEnum = {
        BASELINE: "baseline",
        DIFFERENTIAL: "diff",
        MULTIEXPERIMENT: "multiexperiment"
    };

    var build = function build(type, heatmapConfig, eventEmitter, $prefFormDisplayLevelsInputElement) {

        // ensemblSpecies is the first two words only, with underscores instead of spaces, and all lower case except for the first character
        // used for transcripts and to launch the ensembl genome browser for tracks
        var ensemblSpecies = (function toEnsemblSpecies(species) {
            function capitaliseFirstLetter(string)
            {
                return string.charAt(0).toUpperCase() + string.slice(1);
            }

            function firstTwoWords(text) {
                var words = text.split(" ");
                return (words.length <= 2) ? text : words[0] + " " + words[1];
            }

            return capitaliseFirstLetter(firstTwoWords(species).replace(" ", "_").toLowerCase());
        })(heatmapConfig.species);

        var ensemblHost = "http://" + ((heatmapConfig.ensemblDB == "ensembl") ? "www" : heatmapConfig.ensemblDB) + ".ensembl.org/";

        var Heatmap = React.createClass({

            getInitialState: function () {
                var displayLevels = ($prefFormDisplayLevelsInputElement.val() === "true");
                return {
                    showGeneSetProfiles: false,
                    displayLevels: displayLevels,
                    profiles: this.props.profiles,
                    selectedColumnId: null,
                    selectedGeneId: null};
            },

            toggleGeneSets: function () {
                var newProfiles = this.state.showGeneSetProfiles ? this.props.profiles : this.props.geneSetProfiles;
                this.setState({showGeneSetProfiles: !this.state.showGeneSetProfiles, profiles: newProfiles});
            },

            toggleDisplayLevels: function () {
                var newDisplayLevels = !this.state.displayLevels;
                this.setState({displayLevels: newDisplayLevels});
                $prefFormDisplayLevelsInputElement.val(newDisplayLevels);
            },

            isMicroarray: function () {
                return !(typeof(this.props.profiles.rows[0].designElement) === "undefined");
            },

            componentDidMount: function() {

                if (type != TypeEnum.MULTIEXPERIMENT) {
                    makeTableHeaderSticky.call(this);
                }

                //TODO: use Stickem instead of Sticky.js - we only need one sticky library
                function makeTableHeaderSticky() {
                    var $heatmapTable = $(this.refs.heatmapTableRow.getDOMNode()), $countAndLegend = $(this.refs.countAndLegend.getDOMNode()),
                        stickyTopOffset = $countAndLegend.height();
                    if ($.fn.stickyTableHeaders) {
                        $(this.refs.heatmapTable.getDOMNode()).stickyTableHeaders({fixedOffset: stickyTopOffset});
                    }
                    if ($.fn.sticky) {
                        if ($heatmapTable.width() > $countAndLegend.width()) {
                            //countAndLegend must be at least width of table so that we don't see the table cells scroll underneath
                            var WIDTH_PADDING_OFFSET = 19;
                            $countAndLegend.css('width', $heatmapTable.width() + WIDTH_PADDING_OFFSET);
                        }
                        $countAndLegend.sticky();
                        $(this.refs.downloadProfilesButton.getDOMNode()).sticky({topSpacing: stickyTopOffset});
                    }
                }
            },

            legendType: function () {
                return (type == TypeEnum.BASELINE || type == TypeEnum.MULTIEXPERIMENT ? <LegendBaseline displayLevels={this.state.displayLevels} minExpressionLevel={this.state.profiles.minExpressionLevel} maxExpressionLevel={this.state.profiles.maxExpressionLevel}/>
                    : <LegendDifferential displayLevels={this.state.displayLevels} minDownLevel={this.state.profiles.minDownLevel} maxDownLevel={this.state.profiles.maxDownLevel} minUpLevel={this.state.profiles.minUpLevel} maxUpLevel={this.state.profiles.maxUpLevel}/>);
            },

            render: function () {
                var downloadProfilesButton = type != TypeEnum.MULTIEXPERIMENT ? <td style={{"vertical-align": "top"}}><DownloadProfilesButton ref="downloadProfilesButton"/> </td> : '';

                return (
                    <div>
                        <table ref="countAndLegend" style={{"background-color": "white", zIndex: 1}}>
                            <tr>
                                <td>
                                    { type === TypeEnum.MULTIEXPERIMENT ? <span id="geneCount">Showing {this.state.profiles.rows.length} of {this.state.profiles.searchResultTotal} experiments found: </span> :
                                        <span id="geneCount">Showing {this.state.profiles.rows.length} of {this.state.profiles.searchResultTotal} {this.state.showGeneSetProfiles ? 'gene sets' : 'genes' } found: </span> }

                                    {this.props.geneSetProfiles && type != TypeEnum.MULTIEXPERIMENT ? <a href="javascript:void(0)" onClick={this.toggleGeneSets}>{this.state.showGeneSetProfiles ? '(show individual genes)' : '(show by gene set)'}</a> : ''}
                                </td>
                                <td>
                                    { this.legendType() }
                                </td>
                            </tr>
                        </table>

                        <table>
                            <tr>
                                <td>
                                    <div className="block">
                                        <table style={{width: "100%"}}>
                                            <tbody>
                                                <tr ref="heatmapTableRow">
                                                    <td>
                                                        <table ref="heatmapTable" id="heatmap-table" className="table-grid">
                                                            <HeatmapTableHeader isMicroarray={this.isMicroarray()} columnHeaders={this.props.columnHeaders} displayLevels={this.state.displayLevels} toggleDisplayLevels={this.toggleDisplayLevels} showGeneSetProfiles={this.state.showGeneSetProfiles}/>
                                                            <HeatmapTableRows profiles={this.state.profiles.rows} displayLevels={this.state.displayLevels} showGeneSetProfiles={this.state.showGeneSetProfiles}/>
                                                        </table>
                                                    </td>
                                                    {downloadProfilesButton}
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </div>
                    );
            }
        });

        var DownloadProfilesButton = (function (contextRoot, downloadProfilesURL) {
            return React.createClass({
                render: function () {
                    return (
                        <div style={{"float": "left"}}>
                            <a id="download-profiles-link" ref="downloadProfilesLink" title="Top 50 genes displayed on page. Download results to see the rest." href={downloadProfilesURL} className="button-image" target="_blank">
                                <img id="download-profiles" alt="Download query results" style={{width: "20px"}} src={contextRoot + "/resources/images/download_blue_small.png"}/>
                            </a>
                        </div>
                        );
                },

                componentDidMount: function () {
                    var downloadProfilesLink = this.refs.downloadProfilesLink.getDOMNode();

                    $(downloadProfilesLink).tooltip();

                    $(document).ready(function () {
                        // call this inside ready() otherwise IE8 will be stuck with a "1 item remaining" message
                        $(downloadProfilesLink).button();
                    });
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
                                        {!isNaN(this.props.minDownLevel) && !isNaN(this.props.maxDownLevel) ? <LegendRow displayLevels={this.props.displayLevels} lowExpressionLevel={this.props.minDownLevel} highExpressionLevel={this.props.maxDownLevel} lowValueColour="#C0C0C0" highValueColour="#0000FF"/> : null }
                                        {!isNaN(this.props.minUpLevel) && !isNaN(this.props.maxUpLevel) ? <LegendRow displayLevels={this.props.displayLevels} lowExpressionLevel={this.props.minUpLevel} highExpressionLevel={this.props.maxUpLevel} lowValueColour="#FFAFAF" highValueColour="#FF0000"/> : null }
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
                            <LegendGradient lowValueColour={this.props.lowValueColour} highValueColour={this.props.highValueColour}/>
                        </td>
                        <td>
                            <span style={this.props.displayLevels ? {'white-space': 'nowrap'} : {display: "none"}} className="gradient-level-max">{this.props.highExpressionLevel}</span>
                        </td>
                    </tr>
                    );
            }
        });


        var LegendGradient = React.createClass({
            render: function () {

                var BACKGROUND_IMAGE_TEMPLATE = "-webkit-gradient(linear, left top, right top,color-stop(0, ${lowValueColour}), color-stop(1, ${highValueColour}));background-image: -moz-linear-gradient(left, ${lowValueColour}, ${highValueColour});background-image: -ms-linear-gradient(left, ${lowValueColour}, ${highValueColour}); background-image: -o-linear-gradient(left, ${lowValueColour}, ${highValueColour})";
                var backgroundImage = BACKGROUND_IMAGE_TEMPLATE.replace(/\${lowValueColour}/g, this.props.lowValueColour).replace(/\${highValueColour}/g, this.props.highValueColour);

                // for IE8 and 9
                var LT_IE10_FILTER_TEMPLATE = "progid:DXImageTransform.Microsoft.Gradient(GradientType =1,startColorstr=${lowValueColour},endColorstr=${highValueColour})";
                var lt_ie10_filter = LT_IE10_FILTER_TEMPLATE.replace(/\${lowValueColour}/, this.props.lowValueColour).replace(/\${highValueColour}/, this.props.highValueColour);

                return (
                    <div className="color-gradient" style={{
                        overflow: "auto",
                        "background-image": backgroundImage,
                        filter: lt_ie10_filter}}>
                    &nbsp;
                    </div>
                    );
            }
        });

        var HeatmapTableHeader = React.createClass({

            legendType: function () {
                if (type == TypeEnum.BASELINE) {
                    return (<FactorHeaders assayGroupFactors={this.props.columnHeaders} experimentAccession={heatmapConfig.experimentAccession}/> );
                }
                else if (type == TypeEnum.DIFFERENTIAL) {
                    return (<ContrastHeaders contrasts={this.props.columnHeaders} experimentAccession={heatmapConfig.experimentAccession} showMaPlotButton={heatmapConfig.showMaPlotButton} gseaPlots={heatmapConfig.gseaPlots}/>);
                }
                else if (type == TypeEnum.MULTIEXPERIMENT) {
                     return (<FactorHeaders assayGroupFactors={this.props.columnHeaders} /> );
                }

            },

            render: function () {
                var showGeneProfile = this.props.showGeneSetProfiles ? 'Gene set' : 'Gene';
                var showExperimentProfile = type == TypeEnum.MULTIEXPERIMENT ? "Experiment" : showGeneProfile;

                return (
                    <thead>
                        <th className="horizontal-header-cell" colSpan={this.props.isMicroarray ? 2 : undefined}>
                            <TopLeftCorner displayLevels={this.props.displayLevels} toggleDisplayLevels={this.props.toggleDisplayLevels}/>
                        </th>

                        { this.legendType() }

                        <tr>
                            <td className="horizontal-header-cell" style={ this.props.isMicroarray ? {width:"166px"} : undefined}>{ showExperimentProfile }</td>
                            { this.props.isMicroarray ? <td className="horizontal-header-cell">Design Element</td> : null}
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

            getInitialState: function () {
                return ({selectedColumnId: null});
            },

            selectColumn: function (columnId) {
                var selectedColumnId = (columnId === this.state.selectedColumnId) ? null : columnId;
                this.setState({selectedColumnId: selectedColumnId}, function() {
                    eventEmitter.emitEvent('onColumnSelectionChange', [selectedColumnId]);
                });
            },

            render: function () {
                var factorHeaders = this.props.assayGroupFactors.map(function (assayGroupFactor) {
                    var factor = assayGroupFactor.factor;
                    return <FactorHeader factorName={factor.value} svgPathId={factor.valueOntologyTerm} assayGroupId={assayGroupFactor.assayGroupId} experimentAccession={this.props.experimentAccession}
                            selectColumn={this.selectColumn} selected={assayGroupFactor.assayGroupId === this.state.selectedColumnId} />;
                }.bind(this));

                return (
                    <div>{factorHeaders}</div>
                    );
            }

        });

        var FactorHeader = (function (contextRoot, accessKey, enableEnsemblLauncher, csstransforms) {
            return React.createClass({

                getInitialState: function () {
                    return ({hover:false, selected:false});
                },

                onMouseEnter: function () {
                    this.setState({hover:true});
                },

                onMouseLeave: function () {
                    this.setState({hover:false});
                },

                onClick: function () {
                    this.props.selectColumn(this.props.assayGroupId);
                },

                componentDidMount: function () {
                    if(type != TypeEnum.MULTIEXPERIMENT) {
                        factorInfoTooltipModule.init(contextRoot, accessKey, this.getDOMNode());
                    }
                },

                render: function () {

                    var showSelectTextOnHover = this.state.hover && !this.props.selected ? <span style={{position: "absolute", width:"10px", right:"0px", left:"95px", float:"right", color:"green"}}>  select</span> : null;
                    var showTickWhenSelected = this.props.selected ? <span className="rotate_tick" style={{position: "absolute", width:"5px", right:"0px", left:"125px", float:"right", color:"green"}}> &#10004; </span>: null ;
                    var thClass = "rotated_cell hoverable-header " + (this.props.selected ? "vertical-header-cell-selected " : "vertical-header-cell ") + (enableEnsemblLauncher ? "selectable-header" : "");
                    var divClass = "rotate_text factor-header";
                    var factorName = csstransforms ? restrictLabelSize(this.props.factorName, 17) : this.props.factorName;

                    return (
                        <th className={thClass} onMouseEnter={enableEnsemblLauncher ? this.onMouseEnter : undefined} onMouseLeave={enableEnsemblLauncher ? this.onMouseLeave : undefined} onClick={enableEnsemblLauncher ? this.onClick : undefined} rowSpan="2">
                            <div data-svg-path-id={this.props.svgPathId} data-assay-group-id={this.props.assayGroupId} data-experiment-accession={this.props.experimentAccession} className={divClass}>
                                {factorName}
                                {showSelectTextOnHover}
                                {showTickWhenSelected}
                            </div>
                        </th>
                        );
                }
            });
        })(heatmapConfig.contextRoot, heatmapConfig.accessKey, heatmapConfig.enableEnsemblLauncher, Modernizr.csstransforms);

        var ContrastHeaders = React.createClass({

            getInitialState: function () {
                return ({selectedColumnId: null});
            },

            selectColumn: function (columnId) {
                var selectedColumnId = (columnId === this.state.selectedColumnId) ? null : columnId;
                this.setState({selectedColumnId: selectedColumnId}, function() {
                    eventEmitter.emitEvent('onColumnSelectionChange', [selectedColumnId]);
                });
            },

            render: function () {
                var contrastHeaders = this.props.contrasts.map(function (contrast) {
                    var gseaPlotsThisContrast = this.props.gseaPlots ? this.props.gseaPlots[contrast.id] : {go: false, interpro: false, reactome: false};
                    return <ContrastHeader selectColumn={this.selectColumn} selected={contrast.id === this.state.selectedColumnId} contrastName={contrast.displayName} arrayDesignAccession={contrast.arrayDesignAccession} contrastId={contrast.id} experimentAccession={this.props.experimentAccession} showMaPlotButton={this.props.showMaPlotButton}
                    showGseaGoPlot={gseaPlotsThisContrast.go}
                    showGseaInterproPlot={gseaPlotsThisContrast.interpro}
                    showGseaReactomePlot={gseaPlotsThisContrast.reactome}/>;
                }.bind(this));

                return (
                    <div>{contrastHeaders}</div>
                    );
            }

        });

        var ContrastHeader = (function (contextRoot, accessKey, enableEnsemblLauncher, csstransforms) {
            return React.createClass({

                getInitialState: function () {
                    return ({hover:false, selected:false});
                },

                onMouseEnter: function () {
                    this.setState({hover:true});
                },

                onMouseLeave: function () {
                    this.setState({hover:false});
                },

                onClick: function () {
                    this.props.selectColumn(this.props.contrastId);
                },

                componentDidMount: function () {
                    contrastInfoTooltipModule.init(contextRoot, accessKey, this.getDOMNode());

                    function enableButton(ref) {
                        if (ref) {
                            var element = ref.getDOMNode();
                            $(element).tooltip();

                            $(element).fancybox({
                                padding:0,
                                openEffect:'elastic',
                                closeEffect:'elastic'
                            });
                        }
                    }

                    if (this.showPlotsButton()) {

                        enableButton(this.refs.maButton);

                        enableButton(this.refs.goButton);

                        enableButton(this.refs.interproButton);

                        enableButton(this.refs.reactomeButton);

                        var plotsButton = this.refs.plotsButton.getDOMNode();
                        $(plotsButton).tooltip().button();
                        $(plotsButton).toolbar({
                            content: this.refs.plotsToolbarOptions.getDOMNode(),
                            position: 'right'
                        });

                    }
                },


                clickButton: function (event) {
                    // prevent contrast from being selected
                    event.stopPropagation();
                },

                showPlotsButton: function () {
                    return this.props.showMaPlotButton || this.props.showGseaGoPlot || this.props.showGseaInterproPlot || this.props.showGseaReactomePlot;
                },

                render: function () {
                    var thStyle = this.showPlotsButton() ? {width: "60px"} : {};
                    var textStyle = this.showPlotsButton() ? {top: "57px"} : {};

                    var maPlotURL = contextRoot + '/external-resources/' + this.props.experimentAccession + '/' + (this.props.arrayDesignAccession ? this.props.arrayDesignAccession + '/' : '' ) + this.props.contrastId + '/ma-plot.png';
                    var gseaGoPlotUrl = contextRoot + '/external-resources/' + this.props.experimentAccession + '/' + this.props.contrastId + '/gsea_go.png';
                    var gseaInterproPlotUrl = contextRoot + '/external-resources/' + this.props.experimentAccession + '/' + this.props.contrastId + '/gsea_interpro.png';
                    var gseaReactomePlotUrl = contextRoot + '/external-resources/' + this.props.experimentAccession + '/' + this.props.contrastId + '/gsea_reactome.png';

                    var plotsButton = (
                        <div style={{"text-align":"right", "padding-right":"3px"}} >
                            <a href="#" ref="plotsButton" onClick={this.clickButton} className='button-image' title='Click to view plots'><img src={contextRoot + '/resources/images/yellow-chart-icon.png'}/></a>
                        </div>
                    );

                    var plotsToolbar = (
                        <div ref="plotsToolbarOptions" style={{display: "none"}} >
                            {this.props.showMaPlotButton ? <a href={maPlotURL} id="maButtonID" ref="maButton" title='Click to view MA plot for the contrast across all genes' onClick={this.clickButton}><img src={contextRoot + '/resources/images/maplot-button.png'} /></a> : null }
                            {this.props.showGseaGoPlot ? <a href={gseaGoPlotUrl} id="goButtonID" ref="goButton" title='Click to view GO terms enrichment analysis plot' onClick={this.clickButton}><img src={contextRoot + '/resources/images/gsea-go-button.png'} /></a> : null }
                            {this.props.showGseaInterproPlot ? <a href={gseaInterproPlotUrl} id="interproButtonID" ref="interproButton" title='Click to view Interpro domains enrichment analysis plot' onClick={this.clickButton}><img src={contextRoot + '/resources/images/gsea-interpro-button.png'} /></a> : null }
                            {this.props.showGseaReactomePlot ? <a href={gseaReactomePlotUrl} id="reactomeButtonID" ref="reactomeButton" title='Click to view Reactome pathways enrichment analysis plot' onClick={this.clickButton}><img src={contextRoot + '/resources/images/gsea-reactome-button.png'} /></a> : null }
                        </div>
                    );

                    var showSelectTextOnHover = this.state.hover && !this.props.selected ? <span style={{position: "absolute", width:"10px", right:"0px", left:"95px", float:"right", color:"green"}}>  select</span> : null;
                    var showTickWhenSelected = this.props.selected ? <span className="rotate_tick" style={{position: "absolute", width:"5px", right:"0px", left:"125px", float:"right", color:"green"}}> &#10004; </span>: null ;
                    var thClass = "rotated_cell hoverable-header " + (this.props.selected ? "vertical-header-cell-selected " : "vertical-header-cell ") + (enableEnsemblLauncher ? "selectable-header" : "");
                    var divClass = "rotate_text factor-header";
                    var contrastName = csstransforms ? restrictLabelSize(this.props.contrastName, 17) : this.props.contrastName;

                    return (
                        <th className={thClass} rowSpan="2" style={thStyle} onMouseEnter={enableEnsemblLauncher ? this.onMouseEnter : undefined} onMouseLeave={enableEnsemblLauncher ? this.onMouseLeave : undefined} onClick={enableEnsemblLauncher ? this.onClick : undefined}>
                            <div data-contrast-id={this.props.contrastId} data-experiment-accession={this.props.experimentAccession} className={divClass} style={textStyle}>
                                {contrastName}
                                {showSelectTextOnHover}
                                {showTickWhenSelected}
                            </div>
                            {this.showPlotsButton() ? plotsButton : null}
                            {this.showPlotsButton() ? plotsToolbar : null}
                        </th>
                        );
                }
            });
        })(heatmapConfig.contextRoot, heatmapConfig.accessKey, heatmapConfig.enableEnsemblLauncher, Modernizr.csstransforms);


        var EnsemblLauncher = (function (atlasHost, contextRoot, experimentAccession, accessKey, ensemblHost, ensemblSpecies, ensemblDB, columnType ) {

            var noSelectedColumnMessageArticle = (function (columnType) {
                var isVowel = (function() {
                    var re = /^[aeiou]$/i;
                    return function(char) {
                        return re.test(char);
                    }
                })();

                var beginsWithVowel = function (string) {
                    return isVowel(string.charAt(0));
                };

                return beginsWithVowel(columnType) ? "an " : "a ";
            })(columnType);

            return React.createClass({

                getInitialState: function () {
                    return {selectedColumnId: null, selectedGeneId: null, buttonText: ""};
                },

                componentDidMount: function () {
                    $(this.refs.button.getDOMNode()).button({icons: {primary: "ui-icon-newwin"}});
                    this.updateButton();
                    eventEmitter.addListener('onColumnSelectionChange', this.onColumnSelectionChange);
                    eventEmitter.addListener('onGeneSelectionChange', this.onGeneSelectionChange);
                },

                componentWillUnmount: function () {
                    eventEmitter.addListener('onColumnSelectionChange', this.onColumnSelectionChange);
                    eventEmitter.addListener('onGeneSelectionChange', this.onGeneSelectionChange);
                },

                onColumnSelectionChange: function (selectedColumnId) {
                    this.setState({selectedColumnId: selectedColumnId});
                },

                onGeneSelectionChange: function (selectedGeneId) {
                    this.setState({selectedGeneId: selectedGeneId});
                },

                updateButton: function() {
                    var buttonEnabled = this.state.selectedColumnId && this.state.selectedGeneId ? true : false;
                    $(this.refs.button.getDOMNode()).button("option", "disabled", !buttonEnabled);
                },

                helpMessage: function (selectedColumnId, selectedGeneId) {
                    if (selectedColumnId && selectedGeneId) {
                        return "";
                    }

                    var noSelectedColumnMessage = selectedColumnId ? "" : columnType;
                    var noSelectedGeneMessage = selectedGeneId ? "" : "gene";

                    return "Please select " + noSelectedColumnMessageArticle + noSelectedColumnMessage + (!(selectedColumnId || selectedGeneId) ? " and a " : "") + noSelectedGeneMessage + " from the table";
                },

                componentDidUpdate: function () {
                    this.updateButton();
                },

                openEnsemblWindow: function () {
                    if (!this.state.selectedColumnId || !this.state.selectedGeneId) {
                        return;
                    }

                    var trackFileHeader = experimentAccession + "." + this.state.selectedColumnId;
                    var atlasTrackBaseUrl = "http://" + atlasHost + contextRoot + "/experiments/" + experimentAccession + "/tracks/";
                    var contigviewbottom = "contigviewbottom=url:" + atlasTrackBaseUrl + trackFileHeader + (type === TypeEnum.BASELINE ? ".genes.expressions.bedGraph" : ".genes.log2foldchange.bedGraph");
                    var tiling = (type === TypeEnum.BASELINE || ensemblDB == "ensembl") ? "" : "=tiling,url:" + atlasTrackBaseUrl + trackFileHeader + ".genes.pval.bedGraph=pvalue;";
                    var url =  ensemblHost + ensemblSpecies + "/Location/View?g=" + this.state.selectedGeneId + ";db=core;" + contigviewbottom + tiling + ";format=BEDGRAPH";

                    window.open(
                        url,
                        '_blank'
                    );
                },

                render: function () {
                    //console.log("selected gene id " + this.state.selectedGeneId + " selected column: " + this.state.selectedColumnId);
                    return (
                        <div id="ensembl-launcher-box" style={{width: "245px"}}>
                            <label>Ensembl Genome Browser</label>
                            <img src="/gxa/resources/images/ensembl.gif" style={{padding: "0px 5px"}}></img>
                            <div style={{"font-size": "x-small", height: "30px"}}>{this.helpMessage(this.state.selectedColumnId, this.state.selectedGeneId)}</div>
                            <button ref="button" onClick={this.openEnsemblWindow}>Open</button>
                        </div>
                        );

                }
            });
        })(heatmapConfig.atlasHost, heatmapConfig.contextRoot, heatmapConfig.experimentAccession, heatmapConfig.accessKey, ensemblHost, ensemblSpecies, heatmapConfig.ensemblDB, heatmapConfig.columnType);


        var TopLeftCorner = (function (contextRoot) {
            return React.createClass({

                render: function () {
                    var displayLevelsButton = (type === TypeEnum.DIFFERENTIAL) ? DisplayLevelsButtonDifferential : DisplayLevelsButtonBaseline;
                    return (
                            <div className="heatmap-matrix-top-left-corner">
                                <span id='tooltip-span' data-help-loc='#heatMapTableCellInfo' ref='tooltipSpan'></span>
                                <displayLevelsButton displayLevels={this.props.displayLevels} toggleDisplayLevels={this.props.toggleDisplayLevels}/>
                            </div>
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
                        <button id='display-levels' onClick={this.props.toggleDisplayLevels}></button>
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

        var HeatmapTableRows = React.createClass({

            getInitialState: function () {
                return ({selectedGeneId: null});
            },

            selectGene: function (geneId) {
                var selectedGeneId = (geneId === this.state.selectedGeneId) ? null : geneId;
                this.setState({selectedGeneId: selectedGeneId}, function() {
                    eventEmitter.emitEvent('onGeneSelectionChange', [selectedGeneId]);
                });

            },

            profileRowType: function (profile)  {
                return (type == TypeEnum.MULTIEXPERIMENT ? <GeneProfileRow id={profile.id} name={profile.name} expressions={profile.expressions} serializedFilterFactors = {profile.serializedFilterFactors} displayLevels={this.props.displayLevels} />
                    : <GeneProfileRow selected={profile.id === this.state.selectedGeneId} selectGene={this.selectGene} designElement={profile.designElement} id={profile.id} name={profile.name} expressions={profile.expressions} displayLevels={this.props.displayLevels} showGeneSetProfiles={this.props.showGeneSetProfiles}/> );

            },

            render: function () {
                var geneProfilesRows = this.props.profiles.map(function (profile) {
                    return this.profileRowType(profile);
                }.bind(this));

                return (
                    <tbody>
                        {geneProfilesRows}
                    </tbody>
                    );
            }
        });

        var GeneProfileRow = (function (contextRoot, toolTipHighlightedWords, isExactMatch, enableGeneLinks, enableEnsemblLauncher, geneQuery) {

            return React.createClass({

                getInitialState: function () {
                    return ({hover:false, selected:false});
                },

                onMouseEnter: function () {
                    this.setState({hover:true});
                },

                onMouseLeave: function () {
                    this.setState({hover:false});
                },

                onClick: function () {
                    this.props.selectGene(this.props.id);
                },

                geneNameLinked: function () {
                    var experimentURL = '/experiments/' + this.props.id + '?geneQuery=' + geneQuery + (this.props.serializedFilterFactors ? "&serializedFilterFactors=" + encodeURIComponent(this.props.serializedFilterFactors) : "");
                    var geneURL = this.props.showGeneSetProfiles ? '/query?geneQuery=' + this.props.name + '&exactMatch=' + isExactMatch : '/genes/' + this.props.id;

                    var url = (type == TypeEnum.MULTIEXPERIMENT ? experimentURL : geneURL);

                    // don't render id for gene sets to prevent tooltips
                    return (
                        <a ref="geneName" title="" id={this.props.showGeneSetProfiles ? '' : this.props.id} href={contextRoot + url} onClick={this.geneNameLinkClicked}>{this.props.name}</a>
                        );
                },

                geneNameLinkClicked: function (event) {
                    // prevent row from being selected
                    event.stopPropagation();
                },

                geneNameNotLinked: function () {
                    // don't render id for gene sets to prevent tooltips
                    return (
                        <div ref="geneName" title="" id={this.props.showGeneSetProfiles ? '' : this.props.id}>{this.props.name}</div>
                        );
                },

                cellType: function (expression) {
                    if (type == TypeEnum.BASELINE) {
                        return (
                            <CellBaseline factorName={expression.factorName} color={expression.color} value={expression.value} displayLevels={this.props.displayLevels} svgPathId={expression.svgPathId} showGeneSetProfiles={this.props.showGeneSetProfiles} id={this.props.id} name={this.props.name}/>
                            );
                    }
                    else if (type == TypeEnum.DIFFERENTIAL) {
                        return (
                            <CellDifferential color={expression.color} foldChange={expression.foldChange} pValue={expression.pValue} tStat={expression.tStat} displayLevels={this.props.displayLevels} id={this.props.id} name={this.props.name}/>
                            );
                    }
                    else if (type == TypeEnum.MULTIEXPERIMENT) {
                        return (
                            <CellMultiExperiment factorName={expression.factorName} serializedFilterFactors={this.props.serializedFilterFactors} color={expression.color} value={expression.value} displayLevels={this.props.displayLevels} svgPathId={expression.svgPathId} id={this.props.id} name={this.props.name}/>
                            );
                    }
                },

                cells: function (expressions) {
                    return expressions.map(function (expression) {
                        return this.cellType(expression);
                    }.bind(this));
                },

                render: function () {
                    var showSelectTextOnHover = this.state.hover && !this.props.selected ? <span style={{position: "relative", float:"right", color:"green"}}>  select</span> : null;
                    var showTickWhenSelected = this.props.selected ? <span style={{position: "relative", float:"right", color:"green"}}> &#10004; </span>: null ;
                    var className = (this.props.selected ? "horizontal-header-cell-selected hoverable-header" : "horizontal-header-cell hoverable-header") + (enableEnsemblLauncher ? " selectable-header" : "");

                    // NB: empty title tag below is required for tooltip to work
                    return (
                        <tr>
                            <td className={className} onMouseEnter={enableEnsemblLauncher ? this.onMouseEnter : undefined} onMouseLeave={enableEnsemblLauncher ? this.onMouseLeave : undefined} onClick={enableEnsemblLauncher ? this.onClick : undefined}>
                                { enableGeneLinks ? this.geneNameLinked() : this.geneNameNotLinked()}
                                {showSelectTextOnHover}
                                {showTickWhenSelected}
                            </td>
                            {this.props.designElement ?  <td className="design-element">{this.props.designElement}</td> : null}
                            {this.cells(this.props.expressions)}
                        </tr>
                        );
                },

                componentDidMount: function () {
                    if(type != TypeEnum.MULTIEXPERIMENT) {
                        genePropertiesTooltipModule.init(contextRoot, toolTipHighlightedWords, this.refs.geneName.getDOMNode());
                    }
                }
            });
        })(heatmapConfig.contextRoot, heatmapConfig.toolTipHighlightedWords, heatmapConfig.isExactMatch, heatmapConfig.enableGeneLinks, heatmapConfig.enableEnsemblLauncher, heatmapConfig.geneQuery);


        var CellBaseline = (function (contextRoot, experimentAccession, ensemblHost, ensemblSpecies, selectedFilterFactorsJson, queryFactorType) {

            function hasKnownExpression(value) {
                // true if not blank or UNKNOWN, ie: has a expression with a known value
                return (value && !isUnknownExpression(value));
            }

            function isUnknownExpression(value) {
                return (value === "UNKNOWN")
            }

            function noExpression(value) {
                return !value;
            }

            function unknownCell() {
                return (
                    <span ref='unknownCell' data-help-loc='#heatMapTableUnknownCell'></span>
                    );
            }

            function hasTranscriptTooltip(props) {
                return (!props.showGeneSetProfiles && hasKnownExpression(props.value));
            }

            return React.createClass({

                onClick: function () {
                    if (hasTranscriptTooltip(this.props)) {

                        var factorValue = this.props.factorName,
                            id = this.props.id,
                            name = this.props.name;

                        TranscriptPopup.display(contextRoot, experimentAccession, id, name, queryFactorType, factorValue, selectedFilterFactorsJson, undefined, ensemblHost, ensemblSpecies);
                    }
                },

                render: function () {
                    if (noExpression(this.props.value)) {
                        return (<td></td>);
                    }

                    var style = {"background-color": this.props.color};

                    if (hasTranscriptTooltip(this.props)) {
                        style.cursor = "pointer";
                    }

                    return (
                        <td style={style} onClick={this.onClick}>
                            <div
                            className="heatmap_cell"
                            style={{visibility: isUnknownExpression(this.props.value) || this.props.displayLevels ? "visible" : "hidden"}}
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
        })(heatmapConfig.contextRoot, heatmapConfig.experimentAccession, ensemblHost, ensemblSpecies, heatmapConfig.selectedFilterFactorsJson, heatmapConfig.queryFactorType);


        var CellMultiExperiment = (function (contextRoot, ensemblHost, ensemblSpecies, queryFactorType, isGeneSetQuery, geneId, geneName) {
            
            function isNAExpression(value) {
                return (value === "NT")
            }
            
            function noExpression(value) {
                return !value;
            }

            function tissueNotStudiedInExperiment() {
                return (
                    <span>X</span>
                    );
            }

            function hasTranscriptTooltip(props) {
                return (!isGeneSetQuery && props.value && !isNAExpression(props.value));
            }

            return React.createClass({

                onClick: function () {
                    if (hasTranscriptTooltip(this.props)) {
                        var factorValue = this.props.factorName,
                            serializedFilterFactors = this.props.serializedFilterFactors,
                            experimentAccession = this.props.id;

                        TranscriptPopup.display(contextRoot, experimentAccession, geneId, geneName, queryFactorType, factorValue, undefined, serializedFilterFactors, ensemblHost, ensemblSpecies);
                    }
                },

                render: function () {
                    if (noExpression(this.props.value)) {
                        return (<td></td>);
                    }

                    var style = {"background-color": this.props.color};

                    if (hasTranscriptTooltip(this.props)) {
                        style.cursor = "pointer";
                    }

                    return (
                        <td style={style} onClick={this.onClick}>
                            <div
                            className="heatmap_cell"
                            style={{visibility: isNAExpression(this.props.value) || this.props.displayLevels ? "visible" : "hidden"}}
                            data-svg-path-id={this.props.svgPathId}>
                                {isNAExpression(this.props.value) ? tissueNotStudiedInExperiment() : this.props.value}
                            </div>
                        </td>
                        );
                }
            });
        })(heatmapConfig.contextRoot, ensemblHost, ensemblSpecies, heatmapConfig.queryFactorType, heatmapConfig.isGeneSetQuery, heatmapConfig.geneQuery, heatmapConfig.geneQuery);

        var CellDifferential = (function () {

            return React.createClass({

                hasValue: function () {
                    return (this.props.foldChange !== undefined);
                },

                render: function () {
                    if (!this.hasValue()) {
                        return (<td></td>);
                    }

                    return (
                        <td style={{"background-color": this.props.color}}>
                            <div className={this.props.displayLevels ? "show_cell" : "hide_cell"}>
                                {this.props.foldChange}
                            </div>
                        </td>
                        );
                },

                componentDidMount: function () {
                    if (this.hasValue()) {
                        this.initTooltip(this.getDOMNode());
                    }
                },

                initTooltip: function(element) {

                    //there must be a cleaner way to do this!
                    function buildHeatmapCellTooltip (pValue, tstatistic, foldChange) {

                        function pValueAsHtml(pValue) {

                            var formatParts = pValue.split('E');

                            if (formatParts.length == 1) {
                                return pValue;
                            }

                            var mantissa = formatParts[0];
                            var exponent = formatParts[1];

                            return (mantissa !== "1" ? mantissa + " \u00D7 " : '') + "10<span style='vertical-align: super;'>" + exponent + "</span>";
                        }

                        return "<table class='table-grid' style='margin: 0px; padding: 0px;'><thead><th class='header-cell'>Adjusted <i>p</i>-value</th>" +
                            (tstatistic !== undefined ? "<th class='header-cell'><i>t</i>-statistic</th>" : "") +
                            "<th class='header-cell'>Log<sub>2</sub>-fold change</th></thead>" +
                            "<tbody><tr><td style='padding:6px'><span style=\"white-space: nowrap;\">" + pValueAsHtml(pValue) + "</span></td>" +
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
            Heatmap: Heatmap,
            EnsemblLauncher: heatmapConfig.enableEnsemblLauncher ? EnsemblLauncher : undefined
        };
    };

    return {
        buildBaseline: function (heatmapConfig, $prefFormDisplayLevelsInputElement) { return build(TypeEnum.BASELINE, heatmapConfig, new EventEmitter(), $prefFormDisplayLevelsInputElement); },
        buildDifferential: function (heatmapConfig, $prefFormDisplayLevelsInputElement) { return build(TypeEnum.DIFFERENTIAL, heatmapConfig, new EventEmitter(), $prefFormDisplayLevelsInputElement); },
        buildMultiExperiment: function (heatmapConfig, $prefFormDisplayLevelsInputElement) { return build(TypeEnum.MULTIEXPERIMENT, heatmapConfig, new EventEmitter(), $prefFormDisplayLevelsInputElement); }
    };

})(jQuery, React, genePropertiesTooltipModule, factorInfoTooltipModule, contrastInfoTooltipModule, helpTooltipsModule, TranscriptPopup, EventEmitter, Modernizr);