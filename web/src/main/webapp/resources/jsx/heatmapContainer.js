/** @jsx React.DOM */

/*global React */



var ExperimentDescription = (function (React) {

    return React.createClass({

        render: function () {

            var experimentURL = this.props.experiment.contextRoot + this.props.experiment.URL;

            return (
                React.DOM.table( {width:"100%"}, 
                    React.DOM.tbody(null, 
                        React.DOM.tr(null, 
                            React.DOM.td( {width:"100%"}, 
                                React.DOM.div( {id:"experimentDescription"}, 
                                    React.DOM.a( {id:"goto-experiment", className:"thick-link", title:"Experiment Page", href:experimentURL}, this.props.experiment.description)
                                ),
                                React.DOM.div( {id:"experimentOrganisms"}, "Organism(s): ", React.DOM.span( {style:{"font-style":"italic"}}, this.props.experiment.allSpecies))
                            )
                        )
                    )
                )
            );
        }
    });

})(React);

var Anatomogram = (function (React) {

    return React.createClass({

        render: function () {
            function containsHuman(s) {
                return s.indexOf("human") > -1;
            }

            var height = containsHuman(this.props.anatomogram.maleAnatomogramFile) ? 360 : 250;
            var sexToggleImageSrc =this.props.anatomogram.contextRoot + this.props.anatomogram.toggleButtonImage;

            return (
                React.DOM.div( {id:"anatomogram", className:"aside stickem double-click-noselection", style:{display: "inline"}}, 
                    React.DOM.table(null, 
                        React.DOM.tr(null, 
                            React.DOM.td( {style:{"padding-top": "15px", "vertical-align":"top"}}, 
                                React.DOM.span( {id:"sex-toggle"}, 
                                    React.DOM.img( {id:"sex-toggle-image", title:"Switch anatomogram", className:"button-image",
                                        style:{"width":"20px", "height":"38px", "padding":"2px"},
                                        src:sexToggleImageSrc})
                                )
                            ),
                            React.DOM.td(null, 
                                React.DOM.div( {id:"anatomogramBody", style:{"display":"inline-block", "width": "230px", "height":height}}
                                )
                            )
                        )
                    ),
                    React.DOM.div( {id:"anatomogram-ensembl-launcher"})
                )
            );
        }
    });

})(React);

var HeatmapContainer = (function (React) {

    return React.createClass({

        render: function () {
            var Heatmap = this.props.Heatmap;
            var heatmapClass = this.props.heatmapClass ? this.props.heatmapClass : "heatmap-position" + (this.props.isWidget ? "-widget" : "");

            return (
                    React.DOM.div( {className:"block"}, 

                         this.props.experiment ? ExperimentDescription( {experiment: this.props.experiment} ) : null, 

                        React.DOM.div( {id:"heatmap", className:"row stickem-container"}, 

                             this.props.anatomogram ? Anatomogram( {anatomogram:this.props.anatomogram} ) : null,

                            React.DOM.div( {id:"ensembl-launcher", className:"aside stickem", style:{"display":"inline"}}),

                            React.DOM.div( {id:"heatmap-react", className:heatmapClass}, 
                                Heatmap( {columnHeaders:this.props.columnHeaders, profiles:this.props.profiles, geneSetProfiles:this.props.geneSetProfiles} )
                            ),

                            /* TODO move into help tooltips module */
                            React.DOM.div( {id:"help-placeholder", style:{display: "none"}}),

                            /* TODO move into gene tooltips module */
                            React.DOM.div( {id:"genenametooltip-content", style:{display: "none"}})

                        )
                    )
            );
        }
    });

})(React);
