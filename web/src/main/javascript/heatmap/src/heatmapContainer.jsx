"use strict";

//*------------------------------------------------------------------*

var React = require('react');
var $ = require('jquery');
var jQuery = $;
//require('./lib/jquery.hc-sticky.js');

//*------------------------------------------------------------------*

require('../css/atlas.css');
require('../css/heatmap-and-anatomogram.css');

//*------------------------------------------------------------------*

var ExperimentDescription = React.createClass({

    render: function () {

        var experimentURL = this.props.experiment.contextRoot + this.props.experiment.URL;

        return (
            <div style={{width: "100%"}}>
                <div id="experimentDescription">
                    <a id="goto-experiment" className="gxaThickLink" title="Experiment Page" href={experimentURL}>{this.props.experiment.description}</a>
                </div>
                <div id="experimentOrganisms">Organism(s): <span style={{"fontStyle":"italic"}}>{this.props.experiment.allSpecies}</span></div>
            </div>
        );
    }

});


var Anatomogram = React.createClass({

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
            <div ref="anatomogram" id={anatomogram} className="gxaAside gxaDoubleClickNoSelection" style={{display: "inline"}}>
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
                <div id="anatomogram-ensembl-launcher"></div>
            </div>
        );
    },

    componentDidMount: function() {
        if ($.fn.hcSticky) {
            var $anatomogram = $(this.refs.anatomogram.getDOMNode());
            $anatomogram.hcSticky({responsive: true});
        }
    }

});


var HeatmapContainer = React.createClass({

    componentDidMount: function() {
        var _gaq = _gaq || [];
        _gaq.push(['_setAccount', 'UA-37676851-1']);
        _gaq.push(['_trackPageview']);
        (function () {
            var ga = document.createElement('script');
            ga.type = 'text/javascript';
            ga.async = true;
            ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
            var s = document.getElementsByTagName('script')[0];
            s.parentNode.insertBefore(ga, s);
        })();
    },

    render: function () {
        var Heatmap = this.props.Heatmap;
        var heatmapClass = this.props.heatmapClass ? this.props.heatmapClass : "gxaHeatmapPosition";

        var heatmapConfig = this.props.heatmapConfig;

        var geneURL = "http://" + heatmapConfig.atlasHost + heatmapConfig.contextRoot +  'query?geneQuery=' + heatmapConfig.geneQuery + '&exactMatch='
            + heatmapConfig.isExactMatch + "&organism=" + heatmapConfig.species;


        return (
                <div className="gxaBlock">

                    { this.props.experiment ? <ExperimentDescription experiment={this.props.experiment} /> : null }

                    <div id="heatmap-anatomogram" className="gxaHeatmapAnatomogramRow">

                        { this.props.anatomogram ? <Anatomogram anatomogram={this.props.anatomogram} heatmapKey={this.props.heatmapKey} /> : null}

                        <div id="ensembl-launcher" className="gxaAside" style={{"display":"inline"}}></div>

                        <div id="heatmap-react" className={heatmapClass}>
                            <Heatmap columnHeaders={this.props.columnHeaders} profiles={this.props.profiles} geneSetProfiles={this.props.geneSetProfiles} isWidget={this.props.isWidget}/>
                        </div>

                        {/* TODO move into help tooltips module */}
                        <div id="help-placeholder" style={{display: "none"}}></div>

                        {/* TODO move into gene tooltips module */}
                        <div id="genenametooltip-content" style={{display: "none"}}></div>

                    </div>

                    { !this.props.heatmapClass ? <div id="disclaimer-message"><p><a href={geneURL}>See more expression data at Expression Atlas</a>
                        <br/>This expression view is provided by <a href="http://www.ebi.ac.uk/gxa">Expression Atlas</a>.
                        <br/>Please direct any queries or feedback to <a href="mailto:arrayexpress-atlas@ebi.ac.uk">arrayexpress-atlas@ebi.ac.uk</a></p></div> : null}

                </div>
        );
    }
});

//*------------------------------------------------------------------*

module.exports = HeatmapContainer;