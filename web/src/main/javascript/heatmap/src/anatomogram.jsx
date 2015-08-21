"use strict";

//*------------------------------------------------------------------*

var React = require('react');

var $ = require('jquery');
var jQuery = $;

require('jquery-ui');
require('../css/jquery-ui.min.css');

require('../lib/jquery.hc-sticky.js');

var Snap = require( "imports-loader?this=>window,fix=>module.exports=0!snapsvg/dist/snap.svg.js" );
var EventEmitter = require('wolfy87-eventemitter');

//*------------------------------------------------------------------*

require('../css/atlas.css');

//*------------------------------------------------------------------*

var AnatomogramSelectImageButton = React.createClass({
    propTypes: {
        id: React.PropTypes.string.isRequired,
        selected: React.PropTypes.bool.isRequired,
        toggleSrcTemplate: React.PropTypes.string.isRequired,
        onClick: React.PropTypes.func.isRequired
    },

    render: function() {
        var selectedToggleSrc = this.props.toggleSrcTemplate + "_selected.png",
            unselectedToggleSrc = this.props.toggleSrcTemplate + "_unselected.png";

        return(
            <div>
                <img ref="toggleButton" onClick={this._onClick} src={this.props.selected ? selectedToggleSrc : unselectedToggleSrc}
                     style={{width: "20px", height: "20px", padding: "2px"}}></img>
            </div>
        );
    },

    componentDidMount: function() {
        $(this.refs.toggleButton.getDOMNode()).button();
    },

    _onClick: function() {
        this.props.onClick(this.props.id);
    }
});


var AnatomogramSelectImageButtons = React.createClass({
    propTypes: {
        selectedId: React.PropTypes.string.isRequired,
        availableAnatomograms: React.PropTypes.array.isRequired,
        onClick: React.PropTypes.func.isRequired
    },

    render: function() {
        if (this.props.availableAnatomograms.length > 1) {
            var selectedId = this.props.selectedId,
                onClick = this.props.onClick;
            var anatomogramSelectImageButtons = this.props.availableAnatomograms.map(function(availableAnatomogram) {
               return(
                   <AnatomogramSelectImageButton key={availableAnatomogram.id + "_toggle"}
                    id={availableAnatomogram.id} selected={selectedId === availableAnatomogram.id} toggleSrcTemplate={availableAnatomogram.toggleSrcTemplate} onClick={onClick}/>
               )
            });

            return (
                <span>
                    {anatomogramSelectImageButtons}
                </span>
            );

        } else {
            return (
                null
            )
        }
    }

});


var Anatomogram = React.createClass({
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
        anatomogram: React.PropTypes.object.isRequired,
        expressedTissueColour: React.PropTypes.string.isRequired,
        hoveredTissueColour: React.PropTypes.string.isRequired,
        heatmapConfig: React.PropTypes.object.isRequired,
        profileRows: React.PropTypes.arrayOf(
            React.PropTypes.shape({
                id: React.PropTypes.string.isRequired,
                name: React.PropTypes.string.isRequired,
                expressions: React.PropTypes.arrayOf(
                    React.PropTypes.shape({
                        factorName: React.PropTypes.string.isRequired,
                        color: React.PropTypes.string.isRequired,
                        value: React.PropTypes.string.isRequired,
                        svgPathId: React.PropTypes.string
                    })
                ).isRequired
            })
        ).isRequired,
        eventEmitter: React.PropTypes.instanceOf(EventEmitter)
    },

    _handleChange: function(newSelectedId) {
        if (newSelectedId !== this.state.selectedId) {
            this._loadAnatomogram(this._getAnatomogramSVGFile(newSelectedId));
            this.setState({selectedId: newSelectedId});
        }
    },

    getInitialState: function() {
        var contextRoot = this.props.heatmapConfig.contextRoot;
        var availableAnatomograms = [];
        if (this.props.anatomogram.maleAnatomogramFile) {
            availableAnatomograms.push(
                {id: "male",
                 anatomogramFile: contextRoot + "/resources/svg/" + this.props.anatomogram.maleAnatomogramFile,
                 toggleSrcTemplate: contextRoot + this.props.anatomogram.toggleButtonMaleImageTemplate}
            );
        }
        if (this.props.anatomogram.femaleAnatomogramFile) {
            availableAnatomograms.push(
                {id: "female",
                 anatomogramFile: contextRoot + "/resources/svg/" + this.props.anatomogram.femaleAnatomogramFile,
                 toggleSrcTemplate: contextRoot + this.props.anatomogram.toggleButtonFemaleImageTemplate}
            );
        }
        if (this.props.anatomogram.brainAnatomogramFile) {
            availableAnatomograms.push(
                {id: "brain",
                 anatomogramFile: contextRoot + "/resources/svg/" + this.props.anatomogram.brainAnatomogramFile,
                 toggleSrcTemplate: contextRoot + this.props.anatomogram.toggleButtonBrainImageTemplate}
            );
        }


        var expressedFactors = [];
        this.props.profileRows.forEach(function(profileRow) {
            profileRow.expressions.forEach(function(expression) {
                if (expression.value !== "NA" && expression.value !== "") {
                    expressedFactors.push(expression.svgPathId);
                }
            });
        });

        function onlyUnique(value, index, self) {
            return self.indexOf(value) === index;
        }
        var uniqueExpressedFactors = expressedFactors.filter(onlyUnique);


        return {
            selectedId: availableAnatomograms[0].id,
            availableAnatomograms: availableAnatomograms,
            expressedFactors: uniqueExpressedFactors,
            hoveredPathId: null
        }
    },

    render: function () {
        function containsHuman(s) {
            return s.indexOf("human") > -1;
        }

        var height = containsHuman(this.props.anatomogram.maleAnatomogramFile) ? "360" : "250";

        return (
            <div className="gxaDoubleClickNoSelection" style={{display: "table", paddingTop: "60px"}}>
                <div style={{display: "table-row"}}>
                    <div style={{display: "table-cell", verticalAlign: "top"}}>
                        <AnatomogramSelectImageButtons selectedId={this.state.selectedId} availableAnatomograms={this.state.availableAnatomograms} onClick={this._handleChange}/>
                    </div>

                    <svg ref="anatomogram" style={{display: "table-cell", width: "230px", height:height + "px"}}>
                    </svg>
                </div>
            </div>
        );
    },

    componentDidMount: function() {
        this.props.eventEmitter.addListener('onColumnHoverChange', this._onColumnHoverChange);
        this._loadAnatomogram(this._getAnatomogramSVGFile(this.state.selectedId));
    },

    componentDidUpdate: function() {
        var svg = Snap(this.refs.anatomogram.getDOMNode()).select("g");
        this._highlightAllOrganismParts(svg);
    },

    _onColumnHoverChange: function(svgPathId) {
        this.setState({hoveredPathId: svgPathId});
    },

    _getAnatomogramSVGFile: function(id) {
        for (var i = 0 ; i < this.state.availableAnatomograms.length ; i++) {
            if (id === this.state.availableAnatomograms[i].id) {
                return this.state.availableAnatomograms[i].anatomogramFile;
            }
        }
    },

    _loadAnatomogram: function(svgFile) {

        var svgCanvas = Snap(this.refs.anatomogram.getDOMNode()),
            allElements = svgCanvas.selectAll("*");

        if (allElements) {
            allElements.remove();
        }

        var highlightAllOrganismPartsCallback = this._highlightAllOrganismParts;
        Snap.load(
            svgFile,
            function (fragment) {
                var g = fragment.select("g");
                g.transform("S1.6,0,0");
                highlightAllOrganismPartsCallback(g);
                svgCanvas.append(g);
            }
        );
    },

    _highlightAllOrganismParts: function(svg) {

        this.props.anatomogram.allSvgPathIds.forEach(function(svgPathId) {
            this._highlightOrganismParts(svg, svgPathId);
        }, this);
    },

    _highlightOrganismParts: function(svg, svgPathId) {

        var colour = (this.state.hoveredPathId === svgPathId) ? this.props.hoveredTissueColour : this.props.expressedTissueColour;
        var opacity = (this.state.hoveredPathId === svgPathId) ? 0.7 : 0.5;

        if (this.state.expressedFactors.indexOf(svgPathId) > -1) {
            Anatomogram._recursivelyChangeProperties(svg.select("#" + svgPathId), colour, opacity);
        } else {
            Anatomogram._recursivelyChangeProperties(svg.select("#" + svgPathId), "gray", 0.5);
        }
    },

    statics: {
        _recursivelyChangeProperties: function(svgElement, colour, opacity) {

            if (svgElement) {
                var innerElements = svgElement.selectAll("*");

                if (innerElements.length > 0) {
                    innerElements.forEach(function(innerElement) {
                        Anatomogram._recursivelyChangeProperties(innerElement);
                    });
                }

                svgElement.attr({"fill": colour, "fill-opacity": opacity});
            }
        }
    }

});

//*------------------------------------------------------------------*

module.exports = Anatomogram;