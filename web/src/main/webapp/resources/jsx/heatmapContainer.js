/** @jsx React.DOM */

/*global React */



var ExperimentDescription = (function (React) {

    return React.createClass({

        render: function () {

            var experimentURL = this.props.experiment.contextRoot + this.props.experiment.URL;

            return (
                React.DOM.div({style: {width: "100%"}}, 
                    React.DOM.div({id: "experimentDescription"}, 
                        React.DOM.a({id: "goto-experiment", className: "gxaThickLink", title: "Experiment Page", href: experimentURL}, this.props.experiment.description)
                    ), 
                    React.DOM.div({id: "experimentOrganisms"}, "Organism(s): ", React.DOM.span({style: {"font-style":"italic"}}, this.props.experiment.allSpecies))
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
                React.DOM.div({id: "anatomogram", className: "gxaAside gxaDoubleClickNoSelection", style: {display: "inline"}}, 
                    React.DOM.table(null, 
                        React.DOM.tr(null, 
                            React.DOM.td({style: {"padding-top": "15px", "vertical-align":"top"}}, 
                                React.DOM.span({id: "sex-toggle"}, 
                                    React.DOM.img({id: "sex-toggle-image", title: "Switch anatomogram", className: "gxaButtonImage", 
                                        style: {"width":"20px", "height":"38px", "padding":"2px"}, 
                                        src: sexToggleImageSrc})
                                )
                            ), 
                            React.DOM.td(null, 
                                React.DOM.div({id: "anatomogramBody", style: {"display":"inline-block", "width": "230px", "height":height}}
                                )
                            )
                        )
                    ), 
                    React.DOM.div({id: "anatomogram-ensembl-launcher"})
                )
            );
        }
    });

})(React);

var HeatmapContainer = (function (React) {

    return React.createClass({

        componentDidMount: function() {
            debugger;
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
            var heatmapClass = this.props.heatmapClass ? this.props.heatmapClass : "gxaHeatmapPosition" + (this.props.isWidget ? "-widget" : "");

            return (
                    React.DOM.div({className: "gxaBlock"}, 

                         this.props.experiment ? ExperimentDescription( {experiment: this.props.experiment} ) : null, 

                        React.DOM.div({id: "heatmap-anatomogram", className: "gxaHeatmapAnatomogramRow"}, 

                             this.props.anatomogram ? Anatomogram( {anatomogram:this.props.anatomogram} ) : null, 

                            React.DOM.div({id: "ensembl-launcher", className: "gxaAside", style: {"display":"inline"}}), 

                            React.DOM.div({id: "heatmap-react", className: heatmapClass}, 
                                Heatmap({columnHeaders: this.props.columnHeaders, profiles: this.props.profiles, geneSetProfiles: this.props.geneSetProfiles, isWidget: this.props.isWidget})
                            ), 

                            /* TODO move into help tooltips module */
                            React.DOM.div({id: "help-placeholder", style: {display: "none"}}), 

                            /* TODO move into gene tooltips module */
                            React.DOM.div({id: "genenametooltip-content", style: {display: "none"}})

                        ), 

                         !this.props.heatmapClass ? React.DOM.div({id: "disclaimer-message"}, " ", React.DOM.p(null, "This expression view is provided by ", React.DOM.a({href: "http://www.ebi.ac.uk/gxa"}, "Expression Atlas"), ".", 
                            React.DOM.br(null), "Please direct any queries or feedback to ", React.DOM.a({href: "mailto:arrayexpress-atlas@ebi.ac.uk"}, "arrayexpress-atlas@ebi.ac.uk"))) : null

                    )
            );
        }
    });

})(React);
