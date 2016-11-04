const React = require('react');

const $ = require('jquery');
require('jquery.browser');

const EventEmitter = require('events');

//*------------------------------------------------------------------*

const BaselineHeatmapWidget = require('./BaselineHeatmapWidget.jsx');
const AtlasFeedback = require('expression-atlas-feedback');

//*------------------------------------------------------------------*

const RequiredString = React.PropTypes.string.isRequired;
const OptionalString = React.PropTypes.string;
const RequiredBool = React.PropTypes.bool.isRequired;
const RequiredEventEmitter = React.PropTypes.instanceOf(EventEmitter).isRequired;

const BaselineHeatmaps = React.createClass({
    propTypes: {
        hostUrl: RequiredString,
        query: RequiredString,
        geneQuery: RequiredString,
        conditionQuery: OptionalString,
        /*
         [{"species":"Homo sapiens", "factor":"CELL_LINE"},
          {"species":"Mus musculus", "factor":"ORGANISM_PART"}]
         */
        showAnatomograms: RequiredBool,
        heatmaps: React.PropTypes.arrayOf(
            React.PropTypes.shape({
                species: RequiredString,
                factor: React.PropTypes.shape({
                    name: RequiredString,
                    value: RequiredString
                })
            }).isRequired
        ).isRequired,
        anatomogramDataEventEmitter: RequiredEventEmitter
    },

    getInitialState () {
        return {googleAnalyticsCallback: typeof ga !== 'undefined' ? ga : () => {}}
    },

    componentDidMount () {
        $(document).ready(() => {
            this.setState({googleAnalyticsCallback: typeof ga !== 'undefined' ? ga : () => {}})
        });
    },

    render () {
        let atlasFeedback = $.browser.msie ? null
            : <AtlasFeedback
                  collectionCallback = {(score,comment) => {
                    this.state.googleAnalyticsCallback('send','event','BaselineHeatmaps', 'feedback', comment, score);
                  }}
              />;
        
        return (
            <div>
                {this.props.heatmaps.map(heatmap =>
                    <BaselineHeatmapWidget
                        key = {heatmap.species + '_' + heatmap.factor.name}
                        showAnatomogram = {this.props.showAnatomograms}
                        showHeatmapLabel = {this._hasMoreThanOneSpecies()}
                        species = {heatmap.species}
                        factor = {heatmap.factor}
                        hostUrl = {this.props.hostUrl}
                        query = {this.props.query}
                        geneQuery = {this.props.geneQuery}
                        conditionQuery = {this.props.conditionQuery}
                        anatomogramDataEventEmitter = {this.props.anatomogramDataEventEmitter}
                    />
                )}
                {atlasFeedback}
            </div>
        );
    },

    _hasMoreThanOneSpecies () {
        let uniqueSpecies = new Set();
        this.props.heatmaps.map(el => { uniqueSpecies.add(el.species) });
        return uniqueSpecies.size > 1;
    }
});

//*------------------------------------------------------------------*

module.exports = BaselineHeatmaps;
