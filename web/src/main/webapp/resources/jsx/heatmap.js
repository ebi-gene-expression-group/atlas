/** @jsx React.DOM */

/*global React */
/* Modules and parameters for their init methods are passed in here.
 Parameters that affect how the DOM is generated as passed in as props. */

var heatmapModule = (function($, React, genePropertiesTooltipModule, factorInfoTooltipModule, contrastInfoTooltipModule, helpTooltipsModule, TranscriptPopup, EventEmitter) {

    var TypeEnum = {
        BASELINE: "baseline",
        DIFFERENTIAL: "diff"
    };

    var build = function build(type, heatmapConfig, eventEmitter, $prefFormDisplayLevelsInputElement) {

        // ensemblSpecies is the first two words only, with underscores instead of spaces, and all lower case except for the first character
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

        var Heatmap = React.createClass({displayName: 'Heatmap',

            getInitialState: function () {
                var displayLevels = ($prefFormDisplayLevelsInputElement.val() === "true");
                return { showGeneSetProfiles: false,
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
                return !(typeof(this.props.profiles.genes[0].designElement) === "undefined");
            },

            render: function () {
                return (
                    React.DOM.table(null, 
                        React.DOM.tr(null, 
                            React.DOM.td(null, 
                                React.DOM.span( {id:"geneCount"}, "Showing ", this.state.profiles.genes.length, " of ", this.state.profiles.totalGeneCount, " ", this.state.showGeneSetProfiles ? 'gene sets' : 'genes',  " found: " ),
                                        this.props.geneSetProfiles ? React.DOM.a( {href:"javascript:void(0)", onClick:this.toggleGeneSets}, this.state.showGeneSetProfiles ? '(show individual genes)' : '(show by gene set)') : ''
                            ),
                            React.DOM.td(null, 
                                type === TypeEnum.BASELINE ? LegendBaseline( {displayLevels:this.state.displayLevels, minExpressionLevel:this.state.profiles.minExpressionLevel, maxExpressionLevel:this.state.profiles.maxExpressionLevel})
                                                            : LegendDifferential( {displayLevels:this.state.displayLevels, minDownLevel:this.state.profiles.minDownLevel, maxDownLevel:this.state.profiles.maxDownLevel, minUpLevel:this.state.profiles.minUpLevel, maxUpLevel:this.state.profiles.maxUpLevel})
                            )
                        ),
                        React.DOM.tr(null, 
                            React.DOM.td( {colSpan:"2"}, 
                                React.DOM.div( {className:"block"}, 
                                    React.DOM.table(null, 
                                        React.DOM.tbody(null, 
                                            React.DOM.tr(null, 
                                                React.DOM.td(null, 
                                                    React.DOM.table( {id:"heatmap-table", className:"table-grid"}, 
                                                        HeatmapTableHeader( {isMicroarray:this.isMicroarray(), columnHeaders:this.props.columnHeaders, displayLevels:this.state.displayLevels, toggleDisplayLevels:this.toggleDisplayLevels, showGeneSetProfiles:this.state.showGeneSetProfiles}),
                                                        HeatmapTableRows( {profiles:this.state.profiles.genes, displayLevels:this.state.displayLevels, showGeneSetProfiles:this.state.showGeneSetProfiles})
                                                    )
                                                ),
                                                React.DOM.td( {style:{"vertical-align": "top"}}, 
                                                    DownloadProfilesButton(null )
                                                )
                                            )
                                        )
                                    )
                                )
                            )
                        )
                    )
                    );
            }
        });

        var DownloadProfilesButton = (function (contextRoot, downloadProfilesURL) {
            return React.createClass({
                render: function () {
                    return (
                        React.DOM.div( {style:{"float": "left"}}, 
                            React.DOM.a( {ref:"downloadProfilesLink", title:"Top 50 genes displayed on page. Download results to see the rest.", href:downloadProfilesURL, className:"button-image", target:"_blank"}, 
                                React.DOM.img( {id:"download-profiles", alt:"Download query results", style:{width: "20px"}, src:contextRoot + "/resources/images/download_blue_small.png"})
                            )
                        )
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
                        React.DOM.div( {style:{float: "right", "padding-left": "100px"}}, 
                            React.DOM.div( {style:{float: "left"}}, 
                                React.DOM.table( {style:{"font-size": "10px"}}, 
                                    React.DOM.tbody(null, 
                                        LegendRow( {displayLevels:this.props.displayLevels, lowExpressionLevel:this.props.minExpressionLevel, highExpressionLevel:this.props.maxExpressionLevel, lowValueColour:"#C0C0C0", highValueColour:"#0000FF"})
                                    )
                                )
                            ),
                            React.DOM.div( {ref:"legendHelp", 'data-help-loc':"#gradient-base", style:{float: "left;"}})
                        )
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
                        React.DOM.div( {style:{float: "right", "padding-left": "100px"}}, 
                            React.DOM.div( {style:{float: "left"}}, 
                                React.DOM.table( {style:{"font-size": "10px"}}, 
                                    React.DOM.tbody(null, 
                                        LegendRow( {displayLevels:this.props.displayLevels, lowExpressionLevel:this.props.minDownLevel, highExpressionLevel:this.props.maxDownLevel, lowValueColour:"#C0C0C0", highValueColour:"#0000FF"}),
                                        LegendRow( {displayLevels:this.props.displayLevels, lowExpressionLevel:this.props.minUpLevel, highExpressionLevel:this.props.maxUpLevel, lowValueColour:"#FFAFAF", highValueColour:"#FF0000"})
                                    )
                                )
                            ),
                            React.DOM.div( {ref:"legendHelp", 'data-help-loc':"#gradient-differential", style:{float: "left;"}})
                        )
                        );
                },

                componentDidMount: function () {
                    helpTooltipsModule.init('experiment', contextRoot, this.refs.legendHelp.getDOMNode());
                }
            });
        })(heatmapConfig.contextRoot);


        var LegendRow = React.createClass({displayName: 'LegendRow',
            render: function () {
                return (
                    React.DOM.tr(null, 
                        React.DOM.td(null, 
                            React.DOM.span( {style:this.props.displayLevels ? {'white-space': 'nowrap'} : {display: "none"}, className:"gradient-level-min"}, this.props.lowExpressionLevel)
                        ),
                        React.DOM.td( {width:"200px"}, 
                            LegendGradient( {lowValueColour:this.props.lowValueColour, highValueColour:this.props.highValueColour})
                        ),
                        React.DOM.td(null, 
                            React.DOM.span( {style:this.props.displayLevels ? {'white-space': 'nowrap'} : {display: "none"}, className:"gradient-level-max"}, this.props.highExpressionLevel)
                        )
                    )
                    );
            }
        });


        var LegendGradient = React.createClass({displayName: 'LegendGradient',
            render: function () {

                var BACKGROUND_IMAGE_TEMPLATE = "-webkit-gradient(linear, left top, right top,color-stop(0, ${lowValueColour}), color-stop(1, ${highValueColour}));background-image: -moz-linear-gradient(left, ${lowValueColour}, ${highValueColour});background-image: -o-linear-gradient(left, ${lowValueColour}, ${highValueColour})";
                var FILTER_TEMPLATE = "progid:DXImageTransform.Microsoft.Gradient(GradientType =1,startColorstr=${lowValueColour},endColorstr=${highValueColour})";

                var backgroundImage = BACKGROUND_IMAGE_TEMPLATE.replace(/\${lowValueColour}/, this.props.lowValueColour).replace(/\${highValueColour}/, this.props.highValueColour);
                var filter = FILTER_TEMPLATE.replace(/\${lowValueColour}/, this.props.lowValueColour).replace(/\${highValueColour}/, this.props.highValueColour);

                return (
                    React.DOM.div( {className:"color-gradient", style:{
                        overflow: "auto",
                        "background-image": backgroundImage,
                        filter: filter}}, 
                    " "
                    )
                    );
            }
        });

        var HeatmapTableHeader = React.createClass({displayName: 'HeatmapTableHeader',

            render: function () {
                var ColumnHeaders = this.props.columnHeaders;


                return (
                    React.DOM.thead(null, 
                        React.DOM.th( {className:"horizontal-header-cell", colSpan:this.props.isMicroarray ? 2 : undefined}, 
                            TopLeftCorner( {displayLevels:this.props.displayLevels, toggleDisplayLevels:this.props.toggleDisplayLevels})
                        ),
                        type === TypeEnum.BASELINE ? FactorHeaders( {assayGroupFactors:this.props.columnHeaders, experimentAccession:heatmapConfig.experimentAccession})
                                                    : ContrastHeaders( {contrasts:this.props.columnHeaders, experimentAccession:heatmapConfig.experimentAccession, showMaPlotButton:heatmapConfig.showMaPlotButton}),
                        React.DOM.tr( {id:"injected-header"}, 
                            React.DOM.td( {className:"horizontal-header-cell"}, this.props.showGeneSetProfiles ? 'Gene set' : 'Gene'),
                             this.props.isMicroarray ? React.DOM.td( {className:"horizontal-header-cell"}, "Design Element") : null
                        )
                    )
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

        var FactorHeaders = React.createClass({displayName: 'FactorHeaders',

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
                var props = this.props;
                var factorHeaders = this.props.assayGroupFactors.map(function (assayGroupFactor) {
                    var factor = assayGroupFactor.factor;
                    return FactorHeader( {factorName:factor.value, svgPathId:factor.valueOntologyTerm, assayGroupId:assayGroupFactor.assayGroupId, experimentAccession:props.experimentAccession,
                            selectColumn:this.selectColumn, selected:assayGroupFactor.assayGroupId === this.state.selectedColumnId} );
                }.bind(this));

                return (
                    React.DOM.div(null, factorHeaders)
                    );
            }

        });

        var FactorHeader = (function (contextRoot, accessKey, enableEnsemblBrowser) {
            return React.createClass({

                getInitialState: function () {
                    return ({hover:false, selected:false});
                },

                componentDidMount: function () {
                    factorInfoTooltipModule.init(contextRoot, accessKey, this.getDOMNode());
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

                render: function () {
                    var truncatedFactorName = restrictLabelSize(this.props.factorName, 17);

                    var showSelectTextOnHover = this.state.hover && !this.props.selected ? React.DOM.span( {style:{position: "absolute", width:"10px", right:"0px", left:"95px", float:"right", color:"green"}},   "  select") : null;
                    var showTickWhenSelected = this.props.selected ? React.DOM.span( {className:"rotate_tick", style:{position: "absolute", width:"5px", right:"0px", left:"125px", float:"right", color:"green"}},  " ✔ " ): null ;
                    var className = (this.props.selected ? "rotated_cell hoverable-header vertical-header-cell-selected" : "rotated_cell hoverable-header vertical-header-cell") + (enableEnsemblBrowser ? " selectable-header" : "");

                    return (
                        React.DOM.th( {className:className, onMouseEnter:enableEnsemblBrowser ? this.onMouseEnter : undefined, onMouseLeave:enableEnsemblBrowser ? this.onMouseLeave : undefined, onClick:enableEnsemblBrowser ? this.onClick : undefined, rowSpan:"2"}, 
                            React.DOM.div( {'data-organism-part':this.props.factorName, 'data-svg-path-id':this.props.svgPathId, 'data-assay-group-id':this.props.assayGroupId, 'data-experiment-accession':this.props.experimentAccession, className:"factor-header rotate_text"}, truncatedFactorName,
                                showSelectTextOnHover,
                                showTickWhenSelected
                            )
                        )
                        );
                }
            });
        })(heatmapConfig.contextRoot, heatmapConfig.accessKey, heatmapConfig.enableEnsemblBrowser);

        var ContrastHeaders = React.createClass({displayName: 'ContrastHeaders',

            render: function () {
                var props = this.props;
                var contrastHeaders = this.props.contrasts.map(function (contrast) {
                    return ContrastHeader( {contrastName:contrast.displayName, arrayDesignAccession:contrast.arrayDesignAccession, contrastId:contrast.id, experimentAccession:props.experimentAccession, showMaPlotButton:props.showMaPlotButton});
                });

                return (
                    React.DOM.div(null, contrastHeaders)
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
                        React.DOM.div( {style:{"text-align":"right", "padding-right":"3px"}}, 
                            React.DOM.a( {href:maPlotURL, ref:"maButton", className:"button-image", title:"Click to view MA plot for the contrast across all genes"}, React.DOM.img( {src:contextRoot + '/resources/images/maplot-button.png'}))
                        )
                    );

                    return (
                        React.DOM.th( {className:"rotated_cell vertical-header-cell contrastNameCell", rowSpan:"2", style:thStyle}, 
                            React.DOM.div( {'data-organism-part':this.props.contrastName, 'data-contrast-id':this.props.contrastId, 'data-experiment-accession':this.props.experimentAccession, className:"factor-header rotate_text", style:textStyle}, truncatedName),
                            this.props.showMaPlotButton ? maPlotButton : null
                        )
                        );
                }
            });
        })(heatmapConfig.contextRoot, heatmapConfig.accessKey);


        var EnsemblBrowser = (function (atlasHost, contextRoot, experimentAccession, accessKey, ensemblSpecies, ensemblDB ) {

            return React.createClass({

                getInitialState: function () {
                    return {selectedColumnId: null, selectedGeneId: null, buttonText: ""};
                },

                componentDidMount: function () {
                    $(this.refs.button.getDOMNode()).button();
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
                        return "Go!";
                    }

                    var noSelectedColumnMessage = selectedColumnId ? "" : "factor";
                    var noSelectedGeneMessage = selectedGeneId ? "" : "gene";

                    return "Please select a " + noSelectedColumnMessage + (!(selectedColumnId || selectedGeneId) ? " and a " : "") + noSelectedGeneMessage;
                },

                componentDidUpdate: function () {
                    this.updateButton();
                },

                goToGenomeTrackBrowser: function () {
                    var ensemblHost = (ensemblDB == "ensembl") ? "www" : ensemblDB;
                    var url = "http://" + ensemblHost + ".ensembl.org/" + ensemblSpecies + "/Location/View?g=" + this.state.selectedGeneId + ";db=core;contigviewbottom=url:http://" + atlasHost + contextRoot + "/experiments/" + experimentAccession
                        + "/tracks/" + experimentAccession + "." + this.state.selectedColumnId + ".genes.expressions.bedGraph;format=BEDGRAPH";

                    window.open(
                        url,
                        '_blank'
                    );
                },

                render: function () {
                    //console.log("selected gene id " + this.state.selectedGeneId + " selected column: " + this.state.selectedColumnId);
                    return (
                        React.DOM.div(null, 
                            React.DOM.div( {style:{"font-size": "x-small"}}, this.helpMessage(this.state.selectedColumnId, this.state.selectedGeneId)),
                            React.DOM.button( {ref:"button", onClick:this.goToGenomeTrackBrowser}, "Ensembl Browser")
                        )
                        );

                }
            });
        })(heatmapConfig.atlasHost, heatmapConfig.contextRoot, heatmapConfig.experimentAccession, heatmapConfig.accessKey, ensemblSpecies, heatmapConfig.ensemblDB);


        var TopLeftCorner = (function (contextRoot) {
            return React.createClass({

                render: function () {
                    var displayLevelsButton = (type === TypeEnum.BASELINE) ? DisplayLevelsButtonBaseline : DisplayLevelsButtonDifferential;
                    return (
                            React.DOM.div( {className:"heatmap-matrix-top-left-corner"}, 
                                React.DOM.span( {id:"tooltip-span", 'data-help-loc':"#heatMapTableCellInfo", ref:"tooltipSpan"}),
                                displayLevelsButton( {displayLevels:this.props.displayLevels, toggleDisplayLevels:this.props.toggleDisplayLevels})
                            )
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
                        React.DOM.button( {id:"display-levels", onClick:this.props.toggleDisplayLevels})
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

        var HeatmapTableRows = React.createClass({displayName: 'HeatmapTableRows',

            getInitialState: function () {
                return ({selectedGeneId: null});
            },

            selectGene: function (geneId) {
                var selectedGeneId = (geneId === this.state.selectedGeneId) ? null : geneId;
                this.setState({selectedGeneId: selectedGeneId}, function() {
                    eventEmitter.emitEvent('onGeneSelectionChange', [selectedGeneId]);
                });

            },

            render: function () {
                var geneProfilesRows = this.props.profiles.map(function (profile) {
                    return GeneProfileRow( {selected:profile.geneId === this.state.selectedGeneId, selectGene:this.selectGene, designElement:profile.designElement, geneId:profile.geneId, geneName:profile.geneName, expressions:profile.expressions, displayLevels:this.props.displayLevels, showGeneSetProfiles:this.props.showGeneSetProfiles});
                }.bind(this));

                return (
                    React.DOM.tbody(null, 
                        geneProfilesRows
                    )
                    );
            }
        });

        var GeneProfileRow = (function (contextRoot, toolTipHighlightedWords, isExactMatch, enableGeneLinks, enableEnsemblBrowser) {

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
                    this.props.selectGene(this.props.geneId);
                },

                geneNameLinked: function () {
                    var geneURL = this.props.showGeneSetProfiles ? '/query?geneQuery=' + this.props.geneName + '&exactMatch=' + isExactMatch : '/genes/' + this.props.geneId;

                    // don't render id for gene sets to prevent tooltips
                    return (
                        React.DOM.a( {ref:"geneName", title:"", id:this.props.showGeneSetProfiles ? '' : this.props.geneId, href:contextRoot + geneURL}, this.props.geneName)
                        );
                },

                geneNameNotLinked: function () {
                    // don't render id for gene sets to prevent tooltips
                    return (
                        React.DOM.div( {ref:"geneName", title:"", id:this.props.showGeneSetProfiles ? '' : this.props.geneId}, this.props.geneName)
                        );
                },

                cells: function (expressions) {
                    return expressions.map(function (expression) {
                        return (type == TypeEnum.BASELINE ? CellBaseline( {factorName:expression.factorName, color:expression.color, value:expression.value, displayLevels:this.props.displayLevels, svgPathId:expression.svgPathId, showGeneSetProfiles:this.props.showGeneSetProfiles, geneId:this.props.geneId, geneName:this.props.geneName})
                                                          : CellDifferential( {contrastName:expression.contrastName, color:expression.color, foldChange:expression.foldChange, pValue:expression.pValue, tStat:expression.tStat, displayLevels:this.props.displayLevels, svgPathId:expression.svgPathId, showGeneSetProfiles:this.props.showGeneSetProfiles, geneId:this.props.geneId, geneName:this.props.geneName}));
                    }.bind(this));
                },

                render: function () {
                    var showSelectTextOnHover = this.state.hover && !this.props.selected ? React.DOM.span( {style:{position: "relative", float:"right", color:"green"}},   "  select") : null;
                    var showTickWhenSelected = this.props.selected ? React.DOM.span( {style:{position: "relative", float:"right", color:"green"}},  " ✔ " ): null ;
                    var className = (this.props.selected ? "horizontal-header-cell-selected hoverable-header" : "horizontal-header-cell hoverable-header") + (enableEnsemblBrowser ? " selectable-header" : "");

                    // NB: empty title tag below is required for tooltip to work
                    return (
                        React.DOM.tr(null, 
                            React.DOM.td( {className:className, onMouseEnter:enableEnsemblBrowser ? this.onMouseEnter : undefined, onMouseLeave:enableEnsemblBrowser ? this.onMouseLeave : undefined, onClick:enableEnsemblBrowser ? this.onClick : undefined}, 
                                 enableGeneLinks ? this.geneNameLinked() : this.geneNameNotLinked(),
                                showSelectTextOnHover,
                                showTickWhenSelected
                            ),
                            this.props.designElement ?  React.DOM.td( {class:"design-element"}, this.props.designElement) : null,
                            this.cells(this.props.expressions)
                        )
                        );
                },

                componentDidMount: function () {
                    genePropertiesTooltipModule.init(contextRoot, toolTipHighlightedWords, this.refs.geneName.getDOMNode());
                }
            });
        })(heatmapConfig.contextRoot, heatmapConfig.toolTipHighlightedWords, heatmapConfig.isExactMatch, heatmapConfig.enableGeneLinks, heatmapConfig.enableEnsemblBrowser);


        var CellBaseline = (function (contextRoot, experimentAccession, ensemblSpecies, selectedFilterFactorsJson, queryFactorType) {

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
                    React.DOM.span( {id:"unknownCell", ref:"unknownCell", 'data-help-loc':"#heatMapTableUnknownCell"})
                    );
            }

            function hasTranscriptTooltip(props) {
                return (!props.showGeneSetProfiles && hasKnownExpression(props.value));
            }

            return React.createClass({

                onClick: function () {
                    if (hasTranscriptTooltip(this.props)) {

                        var factorValue = this.props.factorName,
                            geneId = this.props.geneId,
                            geneName = this.props.geneName;

                        TranscriptPopup.display(contextRoot, experimentAccession, geneId, geneName, queryFactorType, factorValue, selectedFilterFactorsJson, ensemblSpecies);
                    }
                },

                render: function () {
                    if (noExpression(this.props.value)) {
                        return (React.DOM.td(null));
                    }

                    var style = {"background-color": this.props.color};

                    if (hasTranscriptTooltip(this.props)) {
                        style.cursor = "pointer";
                    }

                    return (
                        React.DOM.td( {style:style, onClick:this.onClick}, 
                            React.DOM.div(
                            {className:isUnknownExpression(this.props.value) || this.props.displayLevels ? "show_cell" : "hide_cell",
                            'data-svg-path-id':this.props.svgPathId}, 
                                isUnknownExpression(this.props.value) ? unknownCell() : this.props.value
                            )
                        )
                        );
                },

                componentDidMount: function () {
                    if (isUnknownExpression(this.props.value)) {
                        helpTooltipsModule.init('experiment', contextRoot, this.refs.unknownCell.getDOMNode());
                    }
                }
            });
        })(heatmapConfig.contextRoot, heatmapConfig.experimentAccession, ensemblSpecies, heatmapConfig.selectedFilterFactorsJson, heatmapConfig.queryFactorType);

        var CellDifferential = (function () {

            return React.createClass({

                render: function () {
                    if (!this.props.foldChange) {
                        return (React.DOM.td(null));
                    }

                    return (
                        React.DOM.td( {style:{"background-color": this.props.color}, onClick:this.onClick}, 
                            React.DOM.div( {className:this.props.displayLevels ? "show_cell" : "hide_cell"}, 
                                this.props.foldChange
                            )
                        )
                        );
                },

                componentDidMount: function () {
                    this.initTooltip(this.getDOMNode());
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

                            return (mantissa !== "1" ? mantissa + " \u00D7 " : '') + "10 <span style='vertical-align: super;'>" + exponent + "</span>";
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
            EnsemblBrowser: heatmapConfig.enableEnsemblBrowser ? EnsemblBrowser : undefined
        };
    };

    return {
        buildBaseline: function (heatmapConfig, $prefFormDisplayLevelsInputElement) { return build(TypeEnum.BASELINE, heatmapConfig, new EventEmitter(), $prefFormDisplayLevelsInputElement); },
        buildDifferential: function (heatmapConfig, $prefFormDisplayLevelsInputElement) { return build(TypeEnum.DIFFERENTIAL, heatmapConfig, new EventEmitter(), $prefFormDisplayLevelsInputElement); }
    };

})(jQuery, React, genePropertiesTooltipModule, factorInfoTooltipModule, contrastInfoTooltipModule, helpTooltipsModule, TranscriptPopup, EventEmitter);