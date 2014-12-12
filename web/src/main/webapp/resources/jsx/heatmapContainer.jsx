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

                <div id="content" className="grid_24">

                {/* TODO move into help tooltips module */}
                    <div id="help-placeholder" style={{display: "none"}}></div>

                {/* TODO move into gene tooltips module */}
                    <div id="genenametooltip-content" style={{display: "none"}}></div>

                    <div id="atlas-content">
                        <div id="heatmap" className="row stickem-container">

                            <div id="anatomogram" className="aside stickem double-click-noselection" style={{display: "inline"}}>
                                <table>
                                    <tr>
                                        <td style={{"padding-top": "15px", "vertical-align":"top"}}>
                                            <span id="sex-toggle">
                                                <img id="sex-toggle-image" title="Switch anatomogram" className="button-image"
                                                style={{"width":"20px", "height":"38px", "padding":"2px"}}
                                                src={sexToggleImageSrc}/>
                                            </span>
                                        </td>
                                        <td>
                                            <div id="anatomogramBody" style={{"display":"inline-block", "width": "230px", "height":height}}>
                                            </div>
                                        </td>
                                    </tr>
                                </table>
                                <div id="anatomogram-ensembl-launcher"></div>
                            </div>

                            <div id="ensembl-launcher" className="aside stickem" style={{"display":"inline"}}></div>

                            <div id="heatmap-react" className={heatmapClass}>
                                <Heatmap columnHeaders={this.props.columnHeaders} profiles={this.props.profiles} geneSetProfiles={this.props.geneSetProfiles} />
                            </div>
                        </div>
                    </div>
                </div>
            );
        }
    });

})(React);
