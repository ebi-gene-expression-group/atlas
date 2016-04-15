"use strict";

//*------------------------------------------------------------------*

var React = require('react');
var ReactDOM = require('react-dom');
var ReactDOMServer = require('react-dom/server');
var RadioGroup = require('react-radio-group');
var Slider = require('rc-slider');
require('rc-slider/assets/index.css');

var $ = require('jquery');

require('jquery-ui-bundle');
require('jquery.browser');
require('jquery-hc-sticky');
require('atlas-modernizr');  // Leaks Modernizr to the global window namespace

require('fancybox')($);
require('fancybox/dist/css/jquery.fancybox.css');

require('jquery-toolbar');
require('jquery-toolbar/jquery.toolbar.css');


//*------------------------------------------------------------------*

var HeatmapBaselineCellVariance = require('heatmap-baseline-cell-variance');
var Legend = require('legend');
var LegendBaseline = Legend.LegendBaseline;
var LegendDifferential = Legend.LegendDifferential;
var CellDifferential = require('cell-differential');
var DisplayLevelsButton = require('display-levels-button');
var NumberFormat = require('number-format');
var HelpTooltips = require('help-tooltips');
var ContrastTooltips = require('contrast-tooltips');

var GenePropertiesTooltipModule = require('./genePropertiesTooltipModule.js');
var FactorTooltipModule = require('./factorTooltipModule.js');

var StickyHeaderModule = require('./stickyHeaderModule.js');
require('./stickyHeaderModule.css');

//*------------------------------------------------------------------*

require('./Heatmap.css');

//*------------------------------------------------------------------*

var Heatmap = React.createClass({

    propTypes: {
        type: React.PropTypes.shape({
            isBaseline: React.PropTypes.bool,
            isProteomics: React.PropTypes.bool,
            isDifferential: React.PropTypes.bool,
            isMultiExperiment: React.PropTypes.bool,
            heatmapTooltip: React.PropTypes.string.isRequired
        }),
        heatmapConfig: React.PropTypes.object.isRequired,
        columnHeaders: React.PropTypes.oneOfType([
            React.PropTypes.arrayOf(React.PropTypes.shape({
                assayGroupId: React.PropTypes.string.isRequired,
                factorValue: React.PropTypes.string.isRequired,
                factorValueOntologyTermId: React.PropTypes.string
            })),
            React.PropTypes.arrayOf(React.PropTypes.shape({
                id: React.PropTypes.string.isRequired,
                referenceAssayGroup: React.PropTypes.shape({
                    id: React.PropTypes.string.isRequired,
                    assayAccessions: React.PropTypes.arrayOf(React.PropTypes.string).isRequired,
                    replicates: React.PropTypes.number.isRequired
                }).isRequired,
                testAssayGroup: React.PropTypes.shape({
                    id: React.PropTypes.string.isRequired,
                    assayAccessions: React.PropTypes.arrayOf(React.PropTypes.string).isRequired,
                    replicates: React.PropTypes.number.isRequired
                }).isRequired,
                displayName: React.PropTypes.string.isRequired
            }))
        ]).isRequired,
        nonExpressedColumnHeaders: React.PropTypes.arrayOf(React.PropTypes.string).isRequired,
        profiles: React.PropTypes.object.isRequired,
        jsonCoexpressions: React.PropTypes.arrayOf(React.PropTypes.shape({
          geneName: React.PropTypes.string.isRequired,
          jsonProfiles: React.PropTypes.object.isRequired
        })),
        geneSetProfiles: React.PropTypes.object,
        prefFormDisplayLevels: React.PropTypes.object,
        ensemblEventEmitter: React.PropTypes.object,
        anatomogramEventEmitter: React.PropTypes.object,
        googleAnalytics: React.PropTypes.bool,
        atlasBaseURL: React.PropTypes.string.isRequired,
        linksAtlasBaseURL: React.PropTypes.string.isRequired
    },

    getInitialState: function () {
        var displayLevels = this.props.prefFormDisplayLevels ? (this.props.prefFormDisplayLevels.val() === "true") : false;
        var coexpressionsDisplayed = {};
        if(this.props.jsonCoexpressions ) {
          for (var i = 0; i< this.props.jsonCoexpressions.length; i++){
            coexpressionsDisplayed[this.props.jsonCoexpressions[i].geneName]=0;
          }
        }
        return {
            showGeneSetProfiles: false,
            displayLevels: displayLevels,
            selectedColumnId: null,
            selectedGeneId: null,
            hoveredColumnId: null,
            hoveredGeneId: null,
            selectedRadioButton: "gradients",
            coexpressionsDisplayed:coexpressionsDisplayed
        };
    },

    _getProfiles: function() {
      if(this.state.showGeneSetProfiles){
        return this.props.geneSetProfiles;
      } else if (! this.props.jsonCoexpressions){
        return this.props.profiles;
      } else {
        var newRows = [] ;
        for(var i = 0; i< this.props.profiles.rows.length ; i++){
            var thisRow = this.props.profiles.rows[i];
            newRows.push(thisRow);
            var coexpressionsForThisRow =
                this.props.jsonCoexpressions
                  .filter(function(o){
                    return o.geneName=== thisRow.name && this.state.coexpressionsDisplayed[o.geneName]
                  }.bind(this))
            for(var j = 0 ; j < coexpressionsForThisRow.length ; j++ ){
              [].push.apply(newRows,coexpressionsForThisRow[j].jsonProfiles.rows.slice(0, this.state.coexpressionsDisplayed[coexpressionsForThisRow[j].geneName]));
            }

        }
        return Object.create(this.props.profiles, {rows: {value: newRows}});
      }
    },

    _hoverColumn: function(columnId) {
        this.setState({hoveredColumnId: columnId}, function() {
            this.props.anatomogramEventEmitter.emit('gxaHeatmapColumnHoverChange', columnId);
        });
    },

    _hoverRow: function(rowId) {
        this.setState({hoveredRowId: rowId}, function() {
            this.props.anatomogramEventEmitter.emit('gxaHeatmapRowHoverChange', rowId);
        });
    },

    selectColumn: function (columnId) {
        var selectedColumnId = (columnId === this.state.selectedColumnId) ? null : columnId;
        this.setState({selectedColumnId: selectedColumnId}, function() {
            this.props.ensemblEventEmitter.emit('onColumnSelectionChange', selectedColumnId);
        });
    },

    selectGene: function (geneId) {
        var selectedGeneId = (geneId === this.state.selectedGeneId) ? null : geneId;
        this.setState({selectedGeneId: selectedGeneId}, function() {
            this.props.ensemblEventEmitter.emit('onGeneSelectionChange', selectedGeneId);
        });
    },

    toggleGeneSets: function () {
        this.setState({showGeneSetProfiles: !this.state.showGeneSetProfiles});
    },

    toggleDisplayLevels: function () {
        var newDisplayLevels = !this.state.displayLevels;
        this.setState({displayLevels: newDisplayLevels});
        if (this.props.prefFormDisplayLevels) {
            this.props.prefFormDisplayLevels.val(newDisplayLevels);
        }
        $(window).resize();
    },

    toggleRadioButton: function(newSelected) {
        this.setState({selectedRadioButton: newSelected});
        this.setState({displayLevels: (newSelected === "levels")}); //update the LegendType
    },

    isMicroarray: function () {
        return !(typeof(this.props.profiles.rows[0].designElement) === "undefined");
    },

    hasQuartiles: function() {
        var hasQuartiles = false;
        for(var i=0; i < this.props.profiles.rows[0].expressions.length; i++) {
            if(this.props.profiles.rows[0].expressions[i].quartiles != undefined) {
                hasQuartiles = true;
                break;
            }
        }
        return hasQuartiles;
    },

    isSingleGeneResult: function () {
        return (this.props.profiles.rows.length == 1);
    },

    componentDidMount: function() {
        var table	        = ReactDOM.findDOMNode(this.refs.heatmapTable),
            stickyIntersect = ReactDOM.findDOMNode(this.refs.stickyIntersect),
            stickyColumn    = ReactDOM.findDOMNode(this.refs.stickyColumn),
            stickyHeadRow   = ReactDOM.findDOMNode(this.refs.stickyHeader),
            stickyWrap      = ReactDOM.findDOMNode(this.refs.stickyWrap),
            countAndLegend  = ReactDOM.findDOMNode(this.refs.countAndLegend);

        var stickyHeader = StickyHeaderModule(table, stickyIntersect, stickyColumn, stickyHeadRow, stickyWrap, countAndLegend);

        stickyHeader.setWidthsAndReposition();
        $(countAndLegend).hcSticky({bottomEnd: stickyHeader.calculateAllowance()});

        $(stickyWrap).scroll(stickyHeader.stickyReposition);
        $(window).resize(stickyHeader.setWidthsAndReposition)
                 .scroll(stickyHeader.stickyReposition)
                 .on(
                     "gxaResizeHeatmapAnatomogramHeader",
                     function() {
                         stickyHeader.setWidthAndHeight();
                         $(countAndLegend).hcSticky("resize");
                     }
                 );
    },

    legendType: function () {
        return (this.props.type.isBaseline || this.props.type.isMultiExperiment ?
            <LegendBaseline atlasBaseURL={this.props.atlasBaseURL}
                                   minExpressionLevel={this._getProfiles().minExpressionLevel.toString()}
                                   maxExpressionLevel={this._getProfiles().maxExpressionLevel.toString()}
                                   isMultiExperiment={this.props.type.isMultiExperiment ? true : false}/> :
            <LegendDifferential atlasBaseURL={this.props.atlasBaseURL}
                                       minDownLevel={this._getProfiles().minDownLevel.toString()}
                                       maxDownLevel={this._getProfiles().maxDownLevel.toString()}
                                       minUpLevel={this._getProfiles().minUpLevel.toString()}
                                       maxUpLevel={this._getProfiles().maxUpLevel.toString()}/>);
    },

    _getCoexpressionsAvailable: function() {
      return ! this.props.jsonCoexpressions
             ? []
             : this.props.jsonCoexpressions.map(function(value){
        return {name: value.geneName, amount: value.jsonProfiles.rows.length}
      });
    },

    _showCoexpressionsFor : function(geneName, amount) {
      this.setState(function(previousState) {
        if(previousState.coexpressionsDisplayed.hasOwnProperty(geneName)){
          previousState.coexpressionsDisplayed[geneName] = amount;
        }
        return {coexpressionsDisplayed: previousState.coexpressionsDisplayed};
      });
    },

    _showGeneCount: function() {
      var shownRows, totalRows;
      if (this.props.type.isMultiExperiment || ! this.state.showGeneSetProfiles || ! this.props.geneSetProfiles){
          shownRows = this.props.profiles.rows.length;
          totalRows = this.props.profiles.searchResultTotal;
      } else {
        shownRows = this.props.geneSetProfiles.rows.length;
        totalRows = this.props.geneSetProfiles.searchResultTotal;
      }

      return <div style={{display: "inline-block", 'verticalAlign': "top"}}>
        {this.props.type.isMultiExperiment
          ? <span id="geneCount">Showing {shownRows} of {totalRows} experiments found: </span>
          : <span id="geneCount">Showing {shownRows} of {totalRows} {this.state.showGeneSetProfiles ? 'gene sets' : 'genes' } found
              {! this.props.jsonCoexpressions || ! this.props.jsonCoexpressions.length ? ":" : " and "+(this._getProfiles().rows.length-shownRows)+ " similarly expressed genes:"}</span> }
        {this.props.geneSetProfiles && !this.props.type.isMultiExperiment ? <a href="javascript:void(0)" onClick={this.toggleGeneSets}>{this.state.showGeneSetProfiles ? '(show individual genes)' : '(show by gene set)'}</a> : ''}
      </div>
    },

    render: function () {
        var paddingMargin = "15px";

        return (
            <div>
                <div ref="countAndLegend" className="gxaHeatmapCountAndLegend" style={{"paddingBottom": paddingMargin, "position": "sticky"}}>
                    {this._showGeneCount()}
                    <div style={{display: "inline-block", "paddingLeft": "10px", "verticalAlign": "top"}}>
                        <DownloadProfilesButton ref="downloadProfilesButton"
                                                downloadProfilesURL={this.props.heatmapConfig.downloadProfilesURL}
                                                atlasBaseURL={this.props.atlasBaseURL}/>
                    </div>
                    <div style={{display: "inline-block", "paddingLeft": "20px"}}>
                        {this.legendType()}
                    </div>
                </div>

                <div ref="stickyWrap" className="gxaStickyTableWrap" style={{"marginTop": paddingMargin}}>
                    <table ref="heatmapTable" className="gxaTableGrid gxaStickyEnabled" id="heatmap-table">
                        <HeatmapTableHeader ref="heatmapTableHeader"
                                            radioId="table"
                                            isMicroarray={this.isMicroarray()}
                                            hasQuartiles={this.hasQuartiles()}
                                            isSingleGeneResult={this.isSingleGeneResult()}
                                            type={this.props.type}
                                            columnHeaders={this.props.columnHeaders}
                                            nonExpressedColumnHeaders={this.props.nonExpressedColumnHeaders}
                                            multipleColumnHeaders={this.props.multipleColumnHeaders}
                                            selectedColumnId={this.state.selectedColumnId}
                                            selectColumn={this.selectColumn}
                                            hoverColumnCallback={this._hoverColumn}
                                            heatmapConfig={this.props.heatmapConfig}
                                            atlasBaseURL={this.props.atlasBaseURL}
                                            displayLevels={this.state.displayLevels}
                                            toggleDisplayLevels={this.toggleDisplayLevels}
                                            showGeneSetProfiles={this.state.showGeneSetProfiles}
                                            selectedRadioButton={this.state.selectedRadioButton}
                                            toggleRadioButton={this.toggleRadioButton}
                                            renderContrastFactorHeaders={true}
                                            anatomogramEventEmitter={this.props.anatomogramEventEmitter}/>
                        <HeatmapTableRows profiles={this._getProfiles().rows}
                                          nonExpressedColumnHeaders={this.props.nonExpressedColumnHeaders}
                                          selectedGeneId={this.state.selectedGeneId}
                                          selectGene={this.selectGene}
                                          type={this.props.type}
                                          heatmapConfig={this.props.heatmapConfig}
                                          atlasBaseURL={this.props.atlasBaseURL}
                                          linksAtlasBaseURL={this.props.linksAtlasBaseURL}
                                          displayLevels={this.state.displayLevels}
                                          showGeneSetProfiles={this.state.showGeneSetProfiles}
                                          selectedRadioButton={this.state.selectedRadioButton}
                                          hoverColumnCallback={this._hoverColumn}
                                          hoverRowCallback={this._hoverRow}
                                          hasQuartiles={this.hasQuartiles()}
                                          isSingleGeneResult={this.isSingleGeneResult()}
                                          renderExpressionCells={true}/>
                    </table>

                    <div ref="stickyIntersect" className="gxaStickyTableIntersect">
                        <table className="gxaTableGrid">
                            <HeatmapTableHeader isMicroarray={this.isMicroarray()}
                                                    radioId="intersect"
                                                    hasQuartiles={this.hasQuartiles()}
                                                    isSingleGeneResult={this.isSingleGeneResult()}
                                                    type={this.props.type}
                                                    columnHeaders={this.props.columnHeaders}
                                                    nonExpressedColumnHeaders={this.props.nonExpressedColumnHeaders}
                                                    multipleColumnHeaders={this.props.multipleColumnHeaders}
                                                    selectedColumnId={this.state.selectedColumnId}
                                                    selectColumn={this.selectColumn}
                                                    heatmapConfig={this.props.heatmapConfig}
                                                    atlasBaseURL={this.props.atlasBaseURL}
                                                    linksAtlasBaseURL={this.props.linksAtlasBaseURL}
                                                    displayLevels={this.state.displayLevels}
                                                    toggleDisplayLevels={this.toggleDisplayLevels}
                                                    showGeneSetProfiles={this.state.showGeneSetProfiles}
                                                    selectedRadioButton={this.state.selectedRadioButton}
                                                    toggleRadioButton={this.toggleRadioButton}
                                                    renderContrastFactorHeaders={false}/>

                        </table>
                    </div>

                    <div ref="stickyColumn" className="gxaStickyTableColumn">
                        <table className="gxaTableGrid">
                            <HeatmapTableHeader isMicroarray={this.isMicroarray()}
                                                radioId="column"
                                                hasQuartiles={this.hasQuartiles()}
                                                isSingleGeneResult={this.isSingleGeneResult()}
                                                columnHeaders={this.props.columnHeaders}
                                                nonExpressedColumnHeaders={this.props.nonExpressedColumnHeaders}
                                                type={this.props.type}
                                                multipleColumnHeaders={this.props.multipleColumnHeaders}
                                                selectedColumnId={this.state.selectedColumnId}
                                                selectColumn={this.selectColumn}
                                                heatmapConfig={this.props.heatmapConfig}
                                                atlasBaseURL={this.props.atlasBaseURL}
                                                displayLevels={this.state.displayLevels}
                                                toggleDisplayLevels={this.toggleDisplayLevels}
                                                showGeneSetProfiles={this.state.showGeneSetProfiles}
                                                selectedRadioButton={this.state.selectedRadioButton}
                                                toggleRadioButton={this.toggleRadioButton}
                                                renderContrastFactorHeaders={false}/>
                            <HeatmapTableRows profiles={this._getProfiles().rows}
                                              nonExpressedColumnHeaders={this.props.nonExpressedColumnHeaders}
                                              selectedGeneId={this.state.selectedGeneId}
                                              selectGene={this.selectGene}
                                              type={this.props.type}
                                              heatmapConfig={this.props.heatmapConfig}
                                              atlasBaseURL={this.props.atlasBaseURL}
                                              linksAtlasBaseURL={this.props.linksAtlasBaseURL}
                                              displayLevels={this.state.displayLevels}
                                              showGeneSetProfiles={this.state.showGeneSetProfiles}
                                              selectedRadioButton={this.state.selectedRadioButton}
                                              hoverRowCallback={this._hoverRow}
                                              hasQuartiles={this.hasQuartiles()}
                                              isSingleGeneResult={this.isSingleGeneResult()}
                                              renderExpressionCells={false}/>
                        </table>
                    </div>


                    <div ref="stickyHeader" className="gxaStickyTableHeader">
                        <table className="gxaTableGrid">
                            <HeatmapTableHeader isMicroarray={this.isMicroarray()}
                                                    radioId="header"
                                                    hasQuartiles={this.hasQuartiles()}
                                                    isSingleGeneResult={this.isSingleGeneResult()}
                                                    hoverColumnCallback={this._hoverColumn}
                                                    type={this.props.type}
                                                    columnHeaders={this.props.columnHeaders}
                                                    nonExpressedColumnHeaders={this.props.nonExpressedColumnHeaders}
                                                    multipleColumnHeaders={this.props.multipleColumnHeaders}
                                                    selectedColumnId={this.state.selectedColumnId}
                                                    selectColumn={this.selectColumn}
                                                    heatmapConfig={this.props.heatmapConfig}
                                                    atlasBaseURL={this.props.atlasBaseURL}
                                                    linksAtlasBaseURL={this.props.linksAtlasBaseURL}
                                                    displayLevels={this.state.displayLevels}
                                                    toggleDisplayLevels={this.toggleDisplayLevels}
                                                    showGeneSetProfiles={this.state.showGeneSetProfiles}
                                                    selectedRadioButton={this.state.selectedRadioButton}
                                                    toggleRadioButton={this.toggleRadioButton}
                                                    renderContrastFactorHeaders={true}
                                                    anatomogramEventEmitter={this.props.anatomogramEventEmitter}/>

                        </table>
                    </div>
                    <HeatmapBottomOptions coexpressionsAvailable={this._getCoexpressionsAvailable()}
                                          showCoexpressionsFor={this._showCoexpressionsFor} />
                </div>

            </div>
        );
    }

});


var DownloadProfilesButton = React.createClass({
    propTypes: {
        atlasBaseURL: React.PropTypes.string.isRequired,
        downloadProfilesURL: React.PropTypes.string.isRequired
    },

    render: function () {
        var downloadURL = this.props.atlasBaseURL + this.props.downloadProfilesURL;
        var downloadImgSrcURL = this.props.atlasBaseURL + "/resources/images/download_blue_small.png";

        return (
            <a id="download-profiles-link" ref="downloadProfilesLink" className="gxaNoTextButton"
               title="Download all results"
               href={downloadURL} target="_blank">
               <img id="download-profiles" alt="Download query results" style={{width: "20px"}} src={downloadImgSrcURL}/>
            </a>
        );
    },

    componentDidMount: function () {
        var $downloadProfilesLink = $(ReactDOM.findDOMNode(this.refs.downloadProfilesLink));
        $downloadProfilesLink.tooltip({
            tooltipClass: "gxaHelpTooltip"
        });
        $downloadProfilesLink.button();
        $downloadProfilesLink.addClass("gxaNoTextButton");
    }
});


var HeatmapTableHeader = React.createClass({
    propTypes: {
        nonExpressedColumnHeaders: React.PropTypes.arrayOf(React.PropTypes.string)
    },

    renderContrastFactorHeaders: function () {
        var heatmapConfig = this.props.heatmapConfig;
        if (this.props.type.isBaseline) {
            return renderFactorHeaders(heatmapConfig, this.props.atlasBaseURL, this.props.mainHeaderNames, this.props.type, this.props.columnHeaders, this.props.nonExpressedColumnHeaders, heatmapConfig.experimentAccession,
                                        this.props.selectColumn, this.props.selectedColumnId, this.props.hoverColumnCallback, this.props.anatomogramEventEmitter);
        }
        else if (this.props.type.isDifferential) {
            return (<ContrastHeaders heatmapConfig={heatmapConfig}
                                     atlasBaseURL={this.props.atlasBaseURL}
                                     contrasts={this.props.columnHeaders}
                                     selectedColumnId={this.props.selectedColumnId}
                                     selectColumn={this.props.selectColumn}
                                     experimentAccession={heatmapConfig.experimentAccession}
                                     showMaPlotButton={heatmapConfig.showMaPlotButton}
                                     gseaPlots={heatmapConfig.gseaPlots}/>);
        }
        else if (this.props.type.isMultiExperiment) {
            return renderFactorHeaders(heatmapConfig, this.props.atlasBaseURL, null, this.props.type, this.props.columnHeaders, this.props.nonExpressedColumnHeaders, "",
                this.props.selectColumn, this.props.selectedColumnId, this.props.hoverColumnCallback, this.props.anatomogramEventEmitter);
        }
    },

    render: function () {
        var showGeneProfile = this.props.showGeneSetProfiles ? "Gene set" : "Gene";
        var showExperimentProfile = this.props.type.isMultiExperiment ? "Experiment" : showGeneProfile;

        return (
            <thead>
                <tr>
                    <th className="gxaHorizontalHeaderCell gxaHeatmapTableIntersect" colSpan={this.props.isMicroarray ? 2 : undefined}>
                        <TopLeftCorner type={this.props.type}
                                       hasQuartiles={this.props.hasQuartiles}
                                       radioId={this.props.radioId}
                                       isSingleGeneResult={this.props.isSingleGeneResult}
                                       heatmapConfig={this.props.heatmapConfig}
                                       displayLevels={this.props.displayLevels}
                                       toggleDisplayLevels={this.props.toggleDisplayLevels}
                                       selectedRadioButton={this.props.selectedRadioButton}
                                       toggleRadioButton={this.props.toggleRadioButton}
                                       atlasBaseURL={this.props.atlasBaseURL}/>
                    </th>

                    { this.props.renderContrastFactorHeaders ? this.renderContrastFactorHeaders() : null }
                </tr>

                <tr>
                    <th className="gxaHorizontalHeaderCell gxaHeatmapTableIntersect" style={ this.props.isMicroarray ? {width: "166px"} : {}}><div>{ showExperimentProfile }</div></th>
                    { this.props.isMicroarray ? <th className="gxaHorizontalHeaderCell gxaHeatmapTableIntersect"><div>Design Element</div></th> : null}
                </tr>
            </thead>
        );
    }
});


function restrictLabelSize(label, maxSize) {
    var result = label;
    if (result.length > maxSize + 1) {  // +1 to account for the extra ellipsis character appended
        result = result.substring(0, maxSize);
        if (result.lastIndexOf(" ") > maxSize - 5) {
            result = result.substring(0, result.lastIndexOf(" "));
        }
        result = result + "…";
    }
    return result;
}


function renderFactorHeaders(heatmapConfig, atlasBaseURL, mainHeaderNames, type, assayGroupFactors, nonExpressedGroupFactors, experimentAccession, selectColumn,
                             selectedColumnId, hoverColumnCallback, anatomogramEventEmitter) {

    return assayGroupFactors.map(function (assayGroupFactor) {
        return <FactorHeader key={mainHeaderNames + assayGroupFactor.factorValue}
                             type={type}
                             heatmapConfig={heatmapConfig}
                             factorName={assayGroupFactor.factorValue}
                             svgPathId={assayGroupFactor.factorValueOntologyTermId}
                             assayGroupId={assayGroupFactor.assayGroupId}
                             experimentAccession={experimentAccession}
                             selectColumn={selectColumn}
                             selected={assayGroupFactor.assayGroupId === selectedColumnId}
                             hoverColumnCallback={hoverColumnCallback}
                             anatomogramEventEmitter={anatomogramEventEmitter}
                             atlasBaseURL={atlasBaseURL}/>;
    });
}

var FactorHeader = React.createClass({

    getInitialState: function () {
        return ({hover: false, selected: false});
    },

    onMouseEnter: function () {
        if (this.props.heatmapConfig.enableEnsemblLauncher) {
            this.setState({hover: true});
        }
        this.props.hoverColumnCallback(this.props.svgPathId);
    },

    onMouseLeave: function () {
        if (this.props.heatmapConfig.enableEnsemblLauncher) {
            this.setState({hover: false});
        }
        this.props.hoverColumnCallback(null);
        this._closeTooltip();
    },

    _closeTooltip: function() {
        if(!this.props.type.isMultiExperiment) {
            $(ReactDOM.findDOMNode(this)).tooltip("close");
        }
    },

    _anatomogramTissueMouseEnter: function(svgPathId) {
        if (svgPathId === this.props.svgPathId) {
            $(ReactDOM.findDOMNode(this.refs.headerCell)).addClass("gxaHeaderHover");
        }
    },

    _anatomogramTissueMouseLeave: function(svgPathId) {
        if (svgPathId === this.props.svgPathId) {
            $(ReactDOM.findDOMNode(this.refs.headerCell)).removeClass("gxaHeaderHover");
        }
    },

    onClick: function () {
        if (this.props.heatmapConfig.enableEnsemblLauncher) {
            this.props.selectColumn(this.props.assayGroupId);
        }
    },

    componentDidMount: function () {
        if(!this.props.type.isMultiExperiment) {
            FactorTooltipModule.init(this.props.atlasBaseURL, this.props.heatmapConfig.accessKey, ReactDOM.findDOMNode(this), this.props.experimentAccession, this.props.assayGroupId);
        }
        if (this.props.anatomogramEventEmitter) {
            this.props.anatomogramEventEmitter.addListener('gxaAnatomogramTissueMouseEnter', this._anatomogramTissueMouseEnter);
            this.props.anatomogramEventEmitter.addListener('gxaAnatomogramTissueMouseLeave', this._anatomogramTissueMouseLeave);
        }
    },

    render: function () {
        var showSelectTextOnHover = this.state.hover && !this.props.selected ? <span style={{position: "absolute", width:"10px", right:"0px", left:"95px", float:"right", color:"green"}}>  select</span> : null;
        var showTickWhenSelected = this.props.selected ? <span className="rotate_tick" style={{position: "absolute", width:"5px", right:"0px", left:"125px", float:"right", color:"green"}}> &#10004; </span>: null ;
        var thClass = "rotated_cell gxaHoverableHeader" + (this.props.selected ? " gxaVerticalHeaderCell-selected" : " gxaVerticalHeaderCell") + (this.props.heatmapConfig.enableEnsemblLauncher ? " gxaSelectableHeader" : "");
        var divClass = "rotate_text factor-header";
        var factorName = Modernizr.csstransforms ? restrictLabelSize(this.props.factorName, 14) : this.props.factorName;

        return (
            <th ref="headerCell" className={thClass} onMouseEnter={this.onMouseEnter} onMouseLeave={this.onMouseLeave} onClick={this.onClick} rowSpan="2">
                <div data-assay-group-id={this.props.assayGroupId} data-experiment-accession={this.props.experimentAccession} className={divClass}>
                    {factorName}
                    {showSelectTextOnHover}
                    {showTickWhenSelected}
                </div>
            </th>
        );
    }

});


var ContrastHeaders = React.createClass({

    render: function () {
        var heatmapConfig = this.props.heatmapConfig;

        var contrastHeaders = this.props.contrasts.map(function (contrast) {
            var gseaPlotsThisContrast = this.props.gseaPlots ? this.props.gseaPlots[contrast.id] : {go: false, interpro: false, reactome: false};
            return <ContrastHeader key={contrast.id}
                                   heatmapConfig={heatmapConfig}
                                   atlasBaseURL={this.props.atlasBaseURL}
                                   selectColumn={this.props.selectColumn}
                                   selected={contrast.id === this.props.selectedColumnId}
                                   contrastName={contrast.displayName} arrayDesignAccession={contrast.arrayDesignAccession}
                                   contrastId={contrast.id} experimentAccession={this.props.experimentAccession}
                                   showMaPlotButton={this.props.showMaPlotButton}
                                   showGseaGoPlot={gseaPlotsThisContrast.go}
                                   showGseaInterproPlot={gseaPlotsThisContrast.interpro}
                                   showGseaReactomePlot={gseaPlotsThisContrast.reactome}/>;
        }.bind(this));

        return (
            <div>{contrastHeaders}</div>
        );
    }

});


var ContrastHeader = React.createClass({

    getInitialState: function () {
        return ({hover:false, selected:false});
    },

    onMouseEnter: function () {
        this.setState({hover:true});
    },

    onMouseLeave: function () {
        this.setState({hover:false});
        this._closeTooltip();
    },

    _closeTooltip: function() {
        $(ReactDOM.findDOMNode(this)).tooltip("close");
    },

    onClick: function () {
        this.props.selectColumn(this.props.contrastId);
    },

    componentDidMount: function () {
        ContrastTooltips(this.props.atlasBaseURL, this.props.heatmapConfig.accessKey, ReactDOM.findDOMNode(this), this.props.experimentAccession, this.props.contrastId);

        if (this.showPlotsButton()) {
            this.renderToolBarContent(ReactDOM.findDOMNode(this.refs.plotsToolBarContent));

            var $plotsButton = $(ReactDOM.findDOMNode(this.refs.plotsButton));
            $plotsButton.tooltip({
                hide: false,
                show: false,
                tooltipClass: "gxaHelpTooltip"
            }).button();
            $plotsButton.toolbar({
                content: ReactDOM.findDOMNode(this.refs.plotsToolBarContent),
                position: "right",
                style: "white",
                event: "click",
                hideOnClick: true
            });
            $plotsButton.addClass("gxaNoTextButton");
        }
    },

    renderToolBarContent: function(contentNode) {

        var $contentNode = $(contentNode);

        var maPlotURL = this.props.atlasBaseURL + "/external-resources/" + this.props.experimentAccession + '/' + (this.props.arrayDesignAccession ? this.props.arrayDesignAccession + "/" : "" ) + this.props.contrastId + "/ma-plot.png";
        var maPlotImgSrcURL = this.props.atlasBaseURL + "/resources/images/maplot-button.png";

        var gseaGoPlotURL = this.props.atlasBaseURL + "/external-resources/" + this.props.experimentAccession + '/' + this.props.contrastId + "/gsea_go.png";
        var gseaGoPlotImgSrcURL = this.props.atlasBaseURL + "/resources/images/gsea-go-button.png";

        var gseaInterproPlotURL = this.props.atlasBaseURL + "/external-resources/" + this.props.experimentAccession + '/' + this.props.contrastId + "/gsea_interpro.png";
        var gseaInterproImgSrcURL = this.props.atlasBaseURL + '/resources/images/gsea-interpro-button.png';

        var gseaReactomePlotURL = this.props.atlasBaseURL + "/external-resources/" + this.props.experimentAccession + '/' + this.props.contrastId + "/gsea_reactome.png";
        var gseaReactomePlotImgSrcURL = this.props.atlasBaseURL + "/resources/images/gsea-reactome-button.png";

        var content =
            <div>
                {this.props.showMaPlotButton ? <a href={maPlotURL} id="maButtonID" title="Click to view MA plot for the contrast across all genes" onClick={this.clickButton}><img src={maPlotImgSrcURL} /></a> : null }
                {this.props.showGseaGoPlot ? <a href={gseaGoPlotURL} id="goButtonID" title="Click to view GO terms enrichment analysis plot" onClick={this.clickButton}><img src={gseaGoPlotImgSrcURL} /></a> : null }
                {this.props.showGseaInterproPlot ? <a href={gseaInterproPlotURL} id="interproButtonID" title="Click to view Interpro domains enrichment analysis plot" onClick={this.clickButton}><img src={gseaInterproImgSrcURL} /></a> : null }
                {this.props.showGseaReactomePlot ? <a href={gseaReactomePlotURL} id="reactomeButtonID" title="Click to view Reactome pathways enrichment analysis plot" onClick={this.clickButton}><img src={gseaReactomePlotImgSrcURL} /></a> : null }
            </div>;

        // the tool bar content will be copied around the DOM by the toolbar plugin
        // so we render using static markup because otherwise when copied, we'll end up with
        // duplicate data-reactids
        $contentNode.html(ReactDOMServer.renderToStaticMarkup(content));

        $contentNode.find('a').tooltip({
            tooltipClass: "gxaHelpTooltip"
        });

        //need to use each here otherwise we get a fancybox error
        $contentNode.find('a').each(function (index, button) {
            $(button).fancybox({
                padding:0,
                openEffect:'elastic',
                closeEffect:'elastic'
            });
        });
    },

    clickButton: function (event) {
        // prevent contrast from being selected
        event.stopPropagation();
    },

    showPlotsButton: function () {
        return this.props.showMaPlotButton || this.props.showGseaGoPlot || this.props.showGseaInterproPlot || this.props.showGseaReactomePlot;
    },

    render: function () {
        var thStyle = this.showPlotsButton() ? {minWidth: "80px"} : {};
        var textStyle = this.showPlotsButton() ? {top: "57px"} : {};

        var plotsImgSrcURL = this.props.atlasBaseURL + "/resources/images/yellow-chart-icon.png";

        var plotsButton = (
            <div style={{textAlign: "right", paddingRight: "3px"}} >
                <a href="#" ref="plotsButton" onClick={this.clickButton} title="Click to view plots"><img src={plotsImgSrcURL}/></a>
            </div>
        );

        var showSelectTextOnHover = this.state.hover && !this.props.selected ? <span style={{position: "absolute", width: "10px", right: "0px", left: "95px", bottom: "-35px", color: "green"}}>  select</span> : null;
        var showTickWhenSelected = this.props.selected ? <span className="rotate_tick" style={{position: "absolute", width: "5px", right: "0px", left: "125px", bottom: "-35px", color: "green"}}> &#10004; </span>: null;
        var thClass = "rotated_cell gxaHoverableHeader" + (this.props.selected ? " gxaVerticalHeaderCell-selected" : " gxaVerticalHeaderCell") + (this.props.heatmapConfig.enableEnsemblLauncher ? " gxaSelectableHeader " : "");
        var divClass = "rotate_text factor-header";
        var contrastName = Modernizr.csstransforms ? restrictLabelSize(this.props.contrastName, 17) : this.props.contrastName;

        return (
            <th className={thClass} rowSpan="2" style={thStyle} onMouseEnter={this.props.heatmapConfig.enableEnsemblLauncher ? this.onMouseEnter : undefined} onMouseLeave={this.props.heatmapConfig.enableEnsemblLauncher ? this.onMouseLeave : this._closeTooltip} onClick={this.props.heatmapConfig.enableEnsemblLauncher ? this.onClick : undefined}>
                <div data-contrast-id={this.props.contrastId} data-experiment-accession={this.props.experimentAccession} className={divClass} style={textStyle}>
                    {contrastName}
                    {showSelectTextOnHover}
                    {showTickWhenSelected}
                </div>
                {this.showPlotsButton() ? plotsButton : null}
                {this.showPlotsButton() ? <div ref="plotsToolBarContent" style={{display: "none"}}>placeholder</div> : null}
            </th>
        );
    }

});


var TopLeftCorner = React.createClass({

    displayLevelsBaseline: function() {
        if (this.props.hasQuartiles && this.props.isSingleGeneResult) {
            return (
                <LevelsRadioGroup radioId={this.props.radioId}
                                  selectedRadioButton={this.props.selectedRadioButton}
                                  toggleRadioButton={this.props.toggleRadioButton}/>
            );
        } else if (this.props.type.isBaseline || this.props.type.isMultiExperiment) {
            return (
                    <DisplayLevelsButton hideText="Hide levels"
                                         showText="Display levels"
                                         onClickCallback={this.props.toggleDisplayLevels}
                                         displayLevels={this.props.displayLevels}
                                         width="150px" fontSize="14px"/>
            );
        } else {
            return (
                    <DisplayLevelsButton hideText="Hide log<sub>2</sub>-fold change"
                                         showText="Display log<sub>2</sub>-fold change"
                                         onClickCallback={this.props.toggleDisplayLevels}
                                         displayLevels={this.props.displayLevels}
                                         width="200px" fontSize="14px"/>
            );
        }
    },

    render: function () {
        return (
            <div className="gxaHeatmapMatrixTopLeftCorner">
                <span data-help-loc={this.props.type.heatmapTooltip} ref="tooltipSpan"/>
                <div style={{display: "table-cell", verticalAlign: "middle", textAlign: "center"}}>
                    {this.displayLevelsBaseline()}
                </div>
            </div>
        );
    },

    componentDidMount: function () {
        HelpTooltips(this.props.atlasBaseURL, 'experiment', ReactDOM.findDOMNode(this.refs.tooltipSpan));
    }

});


var LevelsRadioGroup = React.createClass({

    getInitialState: function() {
        return {value: this.props.selectedRadioButton};
    },

    render: function() {
        return (
            <RadioGroup name={"displayLevelsGroup_" + this.props.radioId} value={this.props.selectedRadioButton} onChange={this.handleChange}>
                <div style={{"marginLeft": "10px", "marginTop": "8px"}}>
                    <input type="radio" value="gradients"/>Display gradients<br />
                    <input type="radio" value="levels"/>Display levels<br />
                    <input type="radio" value="variance"/>Display variance
                </div>
            </RadioGroup>
        );
    },

    handleChange: function(event) {
        this.props.toggleRadioButton(event.target.value);
        this.setState({value: this.props.selectedRadioButton});

        // To resize the sticky column/header in case the row height or column width changes
        $(window).resize();
    }
});


var HeatmapTableRows = React.createClass({
    propTypes: {
        nonExpressedColumnHeaders: React.PropTypes.arrayOf(React.PropTypes.string),
        profiles: React.PropTypes.arrayOf(React.PropTypes.object).isRequired
    },

    profileRowType: function (profile)  {
        var geneProfileKey = this.props.heatmapConfig.species + "-" +
            (this.props.type.isDifferential
              ? profile.name + "-" + profile.designElement
              : profile.name);
        return (this.props.type.isMultiExperiment ?
            <GeneProfileRow key={geneProfileKey}
                            id={profile.id}
                            name={profile.name}
                            type={this.props.type}
                            experimentType={profile.experimentType}
                            expressions={profile.expressions}
                            nonExpressedColumnHeaders={this.props.nonExpressedColumnHeaders}
                            serializedFilterFactors={profile.serializedFilterFactors}
                            heatmapConfig={this.props.heatmapConfig}
                            atlasBaseURL={this.props.atlasBaseURL}
                            linksAtlasBaseURL={this.props.linksAtlasBaseURL}
                            displayLevels={this.props.displayLevels}
                            renderExpressionCells={this.props.renderExpressionCells}
                            hoverColumnCallback={this.props.hoverColumnCallback}
                            hoverRowCallback={this.props.hoverRowCallback}/>
            :
            <GeneProfileRow key={geneProfileKey}
                            selected={profile.id === this.props.selectedGeneId}
                            selectGene={this.props.selectGene}
                            designElement={profile.designElement}
                            id={profile.id}
                            name={profile.name}
                            type={this.props.type}
                            expressions={profile.expressions}
                            nonExpressedColumnHeaders={this.props.nonExpressedColumnHeaders}
                            heatmapConfig={this.props.heatmapConfig}
                            atlasBaseURL={this.props.atlasBaseURL}
                            linksAtlasBaseURL={this.props.linksAtlasBaseURL}
                            displayLevels={this.props.displayLevels}
                            showGeneSetProfiles={this.props.showGeneSetProfiles}
                            selectedRadioButton={this.props.selectedRadioButton}
                            hasQuartiles={this.props.hasQuartiles}
                            isSingleGeneResult={this.props.isSingleGeneResult}
                            renderExpressionCells={this.props.renderExpressionCells}
                            hoverColumnCallback={this.props.hoverColumnCallback}
                            hoverRowCallback={this.props.hoverRowCallback}/>
        );
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


var GeneProfileRow = React.createClass({
    propTypes: {
        nonExpressedColumnHeaders: React.PropTypes.arrayOf(React.PropTypes.string),
        atlasBaseURL: React.PropTypes.string.isRequired,
        linksAtlasBaseURL: React.PropTypes.string.isRequired
    },

    getInitialState: function () {
        return ({hover:false, selected:false, levels: this.props.displayLevels});
    },

    onMouseEnter: function () {
        if (this.props.heatmapConfig.enableEnsemblLauncher) {
            this.setState({hover:true});
        }
        // We use name instead of id because in multiexperiment the same id can appear under different name (same experiment, different conditions)
        this.props.hoverRowCallback(this.props.name);
    },

    onMouseLeave: function () {
        if (this.props.heatmapConfig.enableEnsemblLauncher) {
            this.setState({hover:false});
        }
        this._closeTooltip();
        this.props.hoverRowCallback(null);
    },

    onClick: function () {
        if (this.props.heatmapConfig.enableEnsemblLauncher) {
            this.props.selectGene(this.props.id);
        }
    },

    geneNameLinked: function () {
        var experimentURL = '/experiments/' + this.props.id + '?geneQuery=' + this.props.heatmapConfig.geneQuery + (this.props.serializedFilterFactors ? "&serializedFilterFactors=" + encodeURIComponent(this.props.serializedFilterFactors) : "");
        var geneURL = this.props.showGeneSetProfiles ? '/query?geneQuery=' + this.props.name + '&exactMatch=' + this.props.heatmapConfig.isExactMatch : '/genes/' + this.props.id;

        var titleTooltip = this.props.type.isMultiExperiment ? (this.props.experimentType == "PROTEOMICS_BASELINE" ? "Protein Expression" : "RNA Expression" ) : "";

        var experimentOrGeneURL = this.props.linksAtlasBaseURL + (this.props.type.isMultiExperiment ? experimentURL : geneURL);

        // don't render id for gene sets to prevent tooltips
        // The vertical align in the <a> element is needed because the kerning in the font used in icon-conceptual is vertically off
        return (
            <span title={titleTooltip} style={{"display": "table-cell"}}>
                <span className="icon icon-conceptual icon-c2" data-icon={this.props.type.isMultiExperiment ? (this.props.experimentType == "PROTEOMICS_BASELINE" ? 'P' : 'd') : ''}/>
                <a ref="geneName" id={this.props.showGeneSetProfiles ? '' : this.props.id} href={experimentOrGeneURL} onClick={this.geneNameLinkClicked} style={{"verticalAlign": "15%"}}>{this.props.name}</a>
            </span>
        );
    },

    geneNameLinkClicked: function (event) {
        // prevent row from being selected
        event.stopPropagation();
    },

    geneNameNotLinked: function () {
        // don't render id for gene sets to prevent tooltips
        return (
            <span style={{"float": "left"}} ref="geneName" title="" id={this.props.showGeneSetProfiles ? '' : this.props.id}>{this.props.name}</span>
        );
    },

    displayLevelsRadio: function() {
        if(this.props.hasQuartiles && this.props.isSingleGeneResult) {
            return this.props.selectedRadioButton === "levels";
        }
        else return (this.props.displayLevels);
    },

    cellType: function (expression) {
        if (this.props.type.isBaseline) {
            if(this.props.selectedRadioButton === "variance" && expression.quartiles) {
                return (
                    <HeatmapBaselineCellVariance key={this.props.id + expression.factorName}
                                                 quartiles={expression.quartiles}
                                                 hoverColumnCallback={this.props.hoverColumnCallback}/>
                );
            }
            else {
                return (
                    <CellBaseline key={this.props.id + expression.factorName}
                                  factorName={expression.factorName}
                                  color={expression.color}
                                  value={expression.value}
                                  heatmapConfig={this.props.heatmapConfig}
                                  displayLevels={this.displayLevelsRadio()}
                                  svgPathId={expression.svgPathId}
                                  geneSetProfiles={this.props.showGeneSetProfiles}
                                  id={this.props.id}
                                  name={this.props.name}
                                  hoverColumnCallback={this.props.hoverColumnCallback}/>
                );
            }
        }
        else if (this.props.type.isDifferential) {
            return (
                <CellDifferential key={this.props.designElement + this.props.name + expression.contrastName}
                                  colour={expression.color}
                                  foldChange={expression.foldChange}
                                  pValue={expression.pValue}
                                  tStat={expression.tStat}
                                  displayLevels={this.props.displayLevels}/>
            );
        }
        else if (this.props.type.isMultiExperiment) {
            return (
                <CellMultiExperiment key={this.props.id + expression.factorName}
                                     factorName={expression.factorName}
                                     serializedFilterFactors={this.props.serializedFilterFactors}
                                     color={expression.color}
                                     value={expression.value}
                                     displayLevels={this.props.displayLevels}
                                     svgPathId={expression.svgPathId}
                                     id={this.props.id}
                                     name={this.props.name}
                                     hoverColumnCallback={this.props.hoverColumnCallback}/>
            );
        }
    },

    cells: function (expressions, nonExpressedColumnHeaders) {
        //var filteredExpressions = expressions.filter(function(expression) {
        //    return (nonExpressedColumnHeaders.indexOf(expression.factorName) == -1)
        //});
        //
        //return filteredExpressions.map(function (expression) {
        //    return this.cellType(expression);
        //}.bind(this));

        return expressions.map(function (expression) {
            return this.cellType(expression);
        }.bind(this));
    },

    render: function () {
        var showSelectTextOnHover = this.state.hover && !this.props.selected ? <span style={{"display": "table-cell", "textAlign": "right", "paddingLeft": "10px", "color": "green", "visibility": "visible"}}>select</span> :
            <span style={{"display": "table-cell", "textAlign": "right", "paddingLeft": "10px", "color": "green", "visibility": "hidden"}}>select</span>;
        var showTickWhenSelected = this.props.selected ? <span style={{"float": "right", "color": "green"}}> &#10004; </span>: null ;
        var className = (this.props.selected ? "gxaHorizontalHeaderCell-selected gxaHoverableHeader" : "gxaHorizontalHeaderCell gxaHoverableHeader") + (this.props.heatmapConfig.enableEnsemblLauncher ? " gxaSelectableHeader" : "");
        var rowClassName = this.props.type.isMultiExperiment ? (this.props.experimentType == "PROTEOMICS_BASELINE" ? "gxaProteomicsExperiment" : "gxaTranscriptomicsExperiment" ) : "";

        return (
            <tr className={rowClassName}>
                <th className={className} onMouseEnter={this.onMouseEnter} onMouseLeave={this.onMouseLeave} onClick={this.onClick}>
                    <div style={{display: "table", width: "100%"}}>
                        <div style={{display: "table-row"}}>
                            { this.props.heatmapConfig.enableGeneLinks ?  this.geneNameLinked() : this.geneNameNotLinked()}
                            { this.props.heatmapConfig.enableEnsemblLauncher ? showSelectTextOnHover : null }
                            { this.props.heatmapConfig.enableEnsemblLauncher ? showTickWhenSelected : null }
                        </div>
                    </div>
                </th>
                {this.props.designElement ? <th className="gxaHeatmapTableDesignElement">{this.props.designElement}</th> : null}
                {this.props.renderExpressionCells ? this.cells(this.props.expressions, this.props.nonExpressedColumnHeaders) : null}
            </tr>
        );
    },

    componentDidMount: function () {
        if(!this.props.type.isMultiExperiment) {
            GenePropertiesTooltipModule.init(this.props.atlasBaseURL, ReactDOM.findDOMNode(this.refs.geneName), this.props.id, this.props.name);
        }
    },

    _closeTooltip: function() {
        if(!this.props.type.isMultiExperiment) {
            $(ReactDOM.findDOMNode(this.refs.geneName)).tooltip("close");
        }
    }

});

var CellBaseline = React.createClass({
    render: function () {
        if (this._noExpression()) {
            return (<td></td>);
        }

        var style = {"backgroundColor": this._isUnknownExpression() ? "white" : this.props.color};

        return (
            <td style={style} onMouseEnter={this._onMouseEnter} onMouseLeave={this._onMouseLeave}>
                <div
                    className="gxaHeatmapCell"
                    style={{visibility: this._isUnknownExpression() || this.props.displayLevels ? "visible" : "hidden"}}>
                    {this._isUnknownExpression() ? this._unknownCell() : NumberFormat.baselineExpression(this.props.value)}
                </div>
            </td>
        );
    },

    componentDidMount: function () {
        this.addQuestionMarkTooltip();
    },

    // need this so that we re-add question mark tooltip, if it doesn't exist, when switching between
    // individual genes and gene sets
    componentDidUpdate: function () {
        this.addQuestionMarkTooltip();
    },

    addQuestionMarkTooltip: function() {
        function hasQuestionMark(unknownElement) {
            return unknownElement.children.length;
        }

        if (this._isUnknownExpression() && !hasQuestionMark(ReactDOM.findDOMNode(this.refs.unknownCell))) {
            HelpTooltips(this.props.atlasBaseURL, 'experiment', ReactDOM.findDOMNode(this.refs.unknownCell));
        }
    },

    _hasKnownExpression: function () {
        // true if not blank or UNKNOWN, ie: has a expression with a known value
        return (this.props.value && !this._isUnknownExpression());
    },

    _isUnknownExpression: function () {
        return (this.propsvalue === "UNKNOWN")
    },

    _noExpression: function () {
        return !this.props.value;
    },

    _unknownCell: function () {
        return (
            <span ref='unknownCell' data-help-loc={this.props.geneSetProfiles ? '#heatMapTableGeneSetUnknownCell' : '#heatMapTableUnknownCell'}></span>
        );
    },

    _onMouseEnter: function() {
        if (this._hasKnownExpression()) {
            this.props.hoverColumnCallback(this.props.svgPathId);
        }
    },

    _onMouseLeave: function() {
        if (this._hasKnownExpression()) {
            this.props.hoverColumnCallback(null);
        }
    }
});


var CellMultiExperiment = React.createClass({
    _isNAExpression : function () {
        return (this.props.value === "NT");
    },

    _noExpression: function () {
        return !this.props.value;
    },

    _tissueNotStudiedInExperiment: function () {
        return (
            <span>NA</span>
        );
    },

    _onMouseEnter: function() {
        if (!this._noExpression() && !this._isNAExpression()) {
            this.props.hoverColumnCallback(this.props.svgPathId);
        }
    },

    _onMouseLeave: function() {
        if (!this._noExpression() && !this._isNAExpression()) {
            this.props.hoverColumnCallback(null);
        }
    },

    render: function () {

        if (this._noExpression()) {
            return (<td></td>);
        }

        var style = {"backgroundColor": this.props.color};

        return (
            <td style={style} onMouseEnter={this._onMouseEnter} onMouseLeave={this._onMouseLeave}>
                <div className="gxaHeatmapCell" style={{visibility: this._isNAExpression() || this.props.displayLevels ? "visible" : "hidden"}}>
                    {this._isNAExpression(this.props.value) ? this._tissueNotStudiedInExperiment() : NumberFormat.baselineExpression(this.props.value)}
                </div>
            </td>
        );
    }
});

var HeatmapBottomOptions = React.createClass({
  propTypes: {
      coexpressionsAvailable:React.PropTypes.arrayOf(React.PropTypes.shape({
        name:React.PropTypes.string.isRequired,
        amount: React.PropTypes.number.isRequired
      })).isRequired,
      showCoexpressionsFor: React.PropTypes.func.isRequired
  },

  render: function() {
    var options = [];
    for(var i = 0; i< this.props.coexpressionsAvailable.length ; i++){
      var el = this.props.coexpressionsAvailable[i];
      options.push(<CoexpressionOption
                key={i}
                geneName={el.name}
                numCoexpressionsAvailable = {el.amount}
                showCoexpressionsCallback={ function(amount){this.props.showCoexpressionsFor(el.name,amount)}.bind(this) }
                />);
    };
    return (
      <div>
      {options}
      </div>
    );
  }


});

var CoexpressionOption = React.createClass({
  propTypes: {
    geneName: React.PropTypes.string.isRequired,
    numCoexpressionsAvailable: React.PropTypes.number.isRequired,
    showCoexpressionsCallback: React.PropTypes.func.isRequired
  },
  getInitialState: function() {
    return {visible: 0};
  },
  _chooseValue: function(amount) {
    this.setState({visible: amount});
    this.props.showCoexpressionsCallback(amount);
  },

  _turnOnWithDefaultValue: function() {
    this._chooseValue(10);
  },

  _showOfferToDisplay: function(){
    return <DisplayLevelsButton hideText=""
                         showText="Show genes with similar expression pattern"
                         onClickCallback={this._turnOnWithDefaultValue}
                         displayLevels={false}
                         width="200px"
                         fontSize="14px"/>
  },

  _showSlider: function() {
    var marks = {
      0: "off",
      10: "10"
    };
    marks[this.props.numCoexpressionsAvailable] = this.props.numCoexpressionsAvailable;
    return <div>
    <p style={{"fontSize":"93%"}}>{"Display genes with similar expressions as "+this.props.geneName+":"}</p>
    <div style={{"width":"200px", "margin":"20px"}}>
    <Slider min={0} max={this.props.numCoexpressionsAvailable} onAfterChange={this._chooseValue} marks={marks} included={false} defaultValue={10} />
    </div>
    </div>
  },

  render: function() {
    return  <div>
              {this.state.visible
              ? this._showSlider()
              : this._showOfferToDisplay()}
              </div>
  }



});

//*------------------------------------------------------------------*

module.exports = Heatmap;
