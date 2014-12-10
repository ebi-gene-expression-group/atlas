/** @jsx React.DOM */

/*global React */
var HeatmapContainer = (function (React) {

    return React.createClass({

        render: function () {
            function containsHuman(s) {
                return s.indexOf("human") > -1;
            }

            var Heatmap = this.props.Heatmap;
            var height = containsHuman(this.props.anatomogram.maleAnatomogramFile) ? 360 : 250;
            var sexToggleImageSrc = this.props.anatomogram.contextRoot + "/resources/images/male_selected.png";
            var heatmapClass = "heatmap-position" + (this.props.isWidget ? "-widget" : "");

            return (

                React.DOM.div({id: "content", className: "grid_24"}, 

                /* TODO move into help tooltips module */
                    React.DOM.div({id: "help-placeholder", style: {display: "none"}}), 

                /* TODO move into gene tooltips module */
                    React.DOM.div({id: "genenametooltip-content", style: {display: "none"}}), 

                    React.DOM.div({id: "atlas-content"}, 
                        React.DOM.section({id: "stickem-container", style: {overflow: "auto", "class":"extra-padding"}}, 
                            React.DOM.div({id: "heatmap", className: "row stickem-container"}, 

                                React.DOM.div({id: "anatomogram", className: "aside stickem double-click-noselection", style: {display: "inline"}}, 
                                    React.DOM.table(null, 
                                        React.DOM.tr(null, 
                                            React.DOM.td({style: {"padding-top": "15px", "vertical-align":"top"}}, 
                                                React.DOM.span({id: "sex-toggle"}, 
                                                    React.DOM.img({id: "sex-toggle-image", title: "Switch anatomogram", className: "button-image", 
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
                                ), 

                                React.DOM.div({id: "ensembl-launcher", className: "aside stickem", style: {"display":"inline"}}), 

                                React.DOM.div({id: "heatmap-react", className: heatmapClass}, 
                                    Heatmap({columnHeaders: this.props.columnHeaders, profiles: this.props.profiles, geneSetProfiles: this.props.geneSetProfiles})
                                )
                            )
                        )
                    )
                )
            );
        }
    });

})(React);
