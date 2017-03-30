import React from 'react';
import { connect, PromiseState } from 'react-refetch';
import GenomeBrowserLauncher from './GenomeBrowserLauncher.jsx';

class GenomeBrowserLauncherFetcher extends React.Component {
    constructor(props, context) {
        super(props, context);
    }

    render() {
        if (this.props.experimentInfoFetch.fulfilled) {
            const experimentType = this.props.experimentInfoFetch.value.experimentInfo.experimentType;
            const genomeBrowserUrls = this.props.experimentInfoFetch.value.species.resources.genome_browser;

            return (
                <GenomeBrowserLauncher atlasBaseUrl={this.props.atlasBaseUrl}
                                       pathToResources={this.props.pathToResources}
                                       experimentAccession={this.props.experimentAccession}
                                       isBaseline={experimentType.endsWith(`BASELINE`)}
                                       genomeBrowserUrls={genomeBrowserUrls}
                                       columnType={this.props.columnType}
                                       selectedGeneId={this.props.selectedGeneId}
                                       selectedColumnId={this.props.selectedColumnId}
                />
            );
        } else if (this.props.experimentInfoFetch.pending) {
            return <div>Waiting for experiment...</div>;
        } else if (this.props.experimentInfoFetch.rejected) {
            return <div>Error requesting experiment info</div>;
        }
    }

}

GenomeBrowserLauncherFetcher.propTypes = {
    atlasBaseUrl: React.PropTypes.string.isRequired,
    pathToResources: React.PropTypes.string,
    experimentAccession: React.PropTypes.string.isRequired,
    accessKey: React.PropTypes.string,
    columnType: React.PropTypes.string.isRequired,
    selectedGeneId: React.PropTypes.string,
    selectedColumnId: React.PropTypes.string,

    experimentInfoFetch: React.PropTypes.instanceOf(PromiseState),
    updateStatus: React.PropTypes.func,
    updateStatusResponse: React.PropTypes.instanceOf(PromiseState) // will not be set until after `updateStatus()` is called
};

export default connect(props => ({
    experimentInfoFetch: {
        url: `${props.atlasBaseUrl}/json/experiments/${props.experimentAccession}/info` + (props.accessKey ? `?accessKey=${props.accessKey}` :  ``),
        then: experimentInfo => ({
            url: `${props.atlasBaseUrl}/json/species/${experimentInfo.species}`,
            then: species => ({
                value: { experimentInfo, species }
            })
        })
    }
}))(GenomeBrowserLauncherFetcher)