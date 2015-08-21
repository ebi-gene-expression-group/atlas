"use strict";

//*------------------------------------------------------------------*

var React = require('react');

var $ = require('jquery');
var jQuery = $;

require('jquery-ui');
require('../css/jquery-ui.min.css');

var Snap = require( "imports-loader?this=>window,fix=>module.exports=0!snapsvg/dist/snap.svg.js" );

require('../lib/jquery.hc-sticky.js');

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
    propTypes: {
        anatomogram: React.PropTypes.object.isRequired,
        heatmapConfig: React.PropTypes.object.isRequired
    },

    _handleChange: function(newSelectedId) {
        this.setState({selectedId: newSelectedId});
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

        return {
            selectedId: availableAnatomograms[0].id,
            availableAnatomograms: availableAnatomograms
        }
    },

    render: function () {
        function containsHuman(s) {
            return s.indexOf("human") > -1;
        }

        var height = containsHuman(this.props.anatomogram.maleAnatomogramFile) ? "360" : "250";

        return (
            <div className="gxaDoubleClickNoSelection" style={{display: "inline"}}>
                <div style={{paddingTop: "60px"}}>
                    <AnatomogramSelectImageButtons selectedId={this.state.selectedId} availableAnatomograms={this.state.availableAnatomograms} onClick={this._handleChange}/>
                </div>

                <svg ref="anatomogram" style={{width: "230px", height:height + "px"}}>
                </svg>
            </div>
        );
    },

    componentDidMount: function() {
        this._loadAnatomogram();
    },

    componentDidUpdate: function() {
        this._loadAnatomogram();
    },

    _getSelectedSvg: function() {
        for (var i = 0 ; i < this.state.availableAnatomograms.length ; i++) {
            if (this.state.selectedId === this.state.availableAnatomograms[i].id) {
                return this.state.availableAnatomograms[i].anatomogramFile;
            }
        }
    },

    _loadAnatomogram: function() {

        var svgCanvas = Snap(this.refs.anatomogram.getDOMNode()),
            allElements = svgCanvas.selectAll("*");

        if (allElements) {
            allElements.remove();
        }

        var callback = this._highlightAllOrganismParts;

        Snap.load(
            this._getSelectedSvg(),
            function (fragment) {
                var g = fragment.select("g");
                g.transform("S1.6,0,0");

                callback(g);

                svgCanvas.append(g);
            }
        );

        //this._highlightAllOrganismParts(svgCanvas);

        //var svg = $(this.refs.anatomogram.getDOMNode()).svg("get");
        //
        //var highlightAllOrganismParts = this._highlightAllOrganismParts;
        //
        //svg.load(
        //    selectedAnatomogram.anatomogramFile,
        //    { onLoad:
        //        function() {
        //            svg.getElementById("group_all").setAttribute("transform", "scale(1.6)");
        //            //svg.configure({"transform": "scale(1.6)"});  // Doesn’t work in Chrome :(
        //            highlightAllOrganismParts(svg);
        //        }
        //    }
        //);
    },

    _highlightAllOrganismParts: function(svg) {
        this.props.anatomogram.allSvgPathIds.forEach(function(svgPathId) {

            Anatomogram._recursivelyChangeProperties(svg.select("#" + svgPathId), "gray", 0.5);

        }, this);
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
    },

    _toggleOrganismPartColor: function(svg, svgPathId, event, colour) {
        //var pathElement = svg.getElementById(svgPathId);
        //
        //if (pathElement === null) {
        //    return;
        //}
        //
        //// if pathElement is a group of paths
        //if (pathElement.nodeName === 'g') {
        //    var pathElements = pathElement.getElementsByTagName("path");
        //    for (var i = 0 ; i < pathElements.length ; i++) {
        //        this._togglePathColour(pathElements[i], svgPathId, event, colour);
        //    }
        //} else {
        //    this._togglePathColour(pathElement, svgPathId, event, colour);
        //}
    },

    _togglePathColour: function(path, svgPathId, event, colour) {
        //this._setHighlighting(path, "gray", 0.5)
    },

    _setHighlighting: function(path, colour, opacity) {
        //path.style.fill = colour;
        //path.style.fillOpacity = opacity;
    }

    //componentDidMount: function() {
    //    anatomogramModule(
    //        this.props.anatomogram.allSvgPathIds, this.props.anatomogram.maleAnatomogramFile, this.props.anatomogram.femaleAnatomogramFile,
    //        this.props.anatomogram.brainAnatomogramFile, this.props.anatomogram.contextRoot, this.props.heatmapConfig.species, this.props.heatmapConfig.isSingleGene);
    //}

});

//*------------------------------------------------------------------*

module.exports = Anatomogram;