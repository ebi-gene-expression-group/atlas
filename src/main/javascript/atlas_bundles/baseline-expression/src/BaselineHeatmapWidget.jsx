const React = require('react');
const ReactDOM = require('react-dom');

const EventEmitter = require('events');

//*------------------------------------------------------------------*

const HighchartsHeatmap = require('expression-atlas-heatmap-highcharts');

//*------------------------------------------------------------------*

const RequiredString = React.PropTypes.string.isRequired;
const RequiredBool = React.PropTypes.bool.isRequired;

const BaselineHeatmapWidget = React.createClass({
    propTypes: {
        hostUrl: RequiredString,
        query: RequiredString,
        geneQuery: RequiredString,
        conditionQuery: RequiredString,
        species: RequiredString,
        factor: React.PropTypes.shape({
            name: RequiredString,
            value: RequiredString
        }).isRequired,
        showAnatomogram: RequiredBool,
        showHeatmapLabel: RequiredBool,
        anatomogramDataEventEmitter: React.PropTypes.instanceOf(EventEmitter).isRequired
    },

    _renderHeatmap () {
        if (this.props.query) {
            HighchartsHeatmap.render({
                atlasHost: this.props.hostUrl,
                sourceURL: `/gxa/json/search/baselineResults?query=${this.props.query}&species=${this.props.species}&source=${this.props.factor.name}`,
                isMultiExperiment: true,
                target: ReactDOM.findDOMNode(this.refs.widgetBody),
                isWidget: false,
                showAnatomogram: this.props.showAnatomogram,
                anatomogramDataEventEmitter: this.props.anatomogramDataEventEmitter
            });
        } else {
            HighchartsHeatmap.render({
                atlasHost: this.props.hostUrl,
                params: `geneQuery=${this.props.geneQuery}&conditionQuery=${this.props.conditionQuery}&species=${this.props.species}&source=${this.props.factor.name}`,
                isMultiExperiment: true,
                target: ReactDOM.findDOMNode(this.refs.widgetBody),
                isWidget: false,
                showAnatomogram: this.props.showAnatomogram,
                anatomogramDataEventEmitter: this.props.anatomogramDataEventEmitter
            });
        }
    },

    componentDidMount () {
      this._renderHeatmap();
    },

    componentDidUpdate () {
      this._renderHeatmap();
    },

    render () {
        let speciesLabel = this._capitalize(this.props.species);

        let widgetTitle = <h5>{(this.props.showHeatmapLabel ? speciesLabel + ' — ' : '') + this.props.factor.value}</h5>;

        return(
            <div className="gxaBaselineHeatmap">
                {widgetTitle}
                <div ref="widgetBody" style={{paddingBottom: '30px'}}></div>
            </div>
        );
    },

    _capitalize (str) {
        return str.charAt(0).toUpperCase() + str.slice(1).toLowerCase();
    },

    _removeUnderScore (str) {
        return str.replace(/[-_.]/g, ' ');
    }

});

//*------------------------------------------------------------------*

module.exports = BaselineHeatmapWidget;
