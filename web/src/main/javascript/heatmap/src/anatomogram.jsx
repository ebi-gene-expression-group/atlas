"use strict";

//*------------------------------------------------------------------*

var $ = require('jquery');
var jQuery = $;
require('../lib/jquery.hc-sticky.js');
var React = require('react');

//*------------------------------------------------------------------*

var anatomogramModule = require('./anatomogram-module.js');

//*------------------------------------------------------------------*

var Anatomogram = React.createClass({
    propTypes: {
        anatomogram: React.PropTypes.object.isRequired,
        heatmapConfig: React.PropTypes.object.isRequired
    },

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
            <div ref="anatomogram" id={anatomogram} className="gxaDoubleClickNoSelection" style={{display: "inline"}}>
                <table>
                    <tbody>
                    <tr>
                        <td style={{"paddingTop": "15px", "verticalAlign":"top"}}>
                            <span id={sexToggle}>
                                <img id={maleToggleImage} title="Switch anatomogram" className="gxaButtonImage"
                                     style={{"width":"20px", "height":"20px", "padding":"2px", "display":"none"}}
                                     src={maleToggleImageSrc}/><br/>
                                    <img id={femaleToggleImage} title="Switch anatomogram" className="gxaButtonImage"
                                         style={{"width":"20px", "height":"20px", "padding":"2px", "display":"none"}}
                                         src={femaleToggleImageSrc}/><br/>
                                    <img id={brainToggleImage} title="Switch anatomogram" className="gxaButtonImage"
                                         style={{"width":"20px", "height":"20px", "padding":"2px", "display":"none"}}
                                         src={brainToggleImageSrc}/><br/>
                            </span>
                        </td>
                        <td>
                            <div id={keyId} style={{"display":"inline-block", "width": "230px", "height":height}}>
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        );
    },

    componentDidMount: function() {
        anatomogramModule(
            this.props.anatomogram.allSvgPathIds, this.props.anatomogram.maleAnatomogramFile, this.props.anatomogram.femaleAnatomogramFile,
            this.props.anatomogram.brainAnatomogramFile, this.props.anatomogram.contextRoot, this.props.heatmapConfig.species, this.props.heatmapConfig.isSingleGene);
    }

});

//*------------------------------------------------------------------*

module.exports = Anatomogram;