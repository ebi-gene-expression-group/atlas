import React, {Component} from 'react';
import {BrowserRouter, Route} from 'react-router-dom';
import queryString from 'query-string';
import PropTypes from 'prop-types';
import URI from 'urijs';

import ReferencePlot from 'single-cell-tsne-plot';

class ExperimentPage extends Component {

    render() {
        return (
            <BrowserRouter
                basename={URI(`experiments/${this.props.experimentAccession}`, URI(this.props.atlasUrl).path()).toString()}>
                <div>
                    <Route path='/' render={props => (
                        <Experiment {...props} clustersData={this.props.clustersData}/>
                    )}/>
                </div>
            </BrowserRouter>

        );
    }
}

ExperimentPage.propTypes = {
    atlasUrl: PropTypes.string.isRequired,
    experimentAccession: PropTypes.string.isRequired,
    clustersData: PropTypes.object.isRequired
};

class Experiment extends Component {

    constructor(props) {
        super(props);
        this.state = {
            params: queryString.parse(props.location.search),
            geneId: "",
            clusterId: "",
            K: "",
        };
    }

    handleChange(param, item) {
        let _newparam = {};
        _newparam[param] = item.target.value;
        this.setState(_newparam);

        this.props.history.push("?" + queryString.stringify({
            geneId: (param === "geneId" ? item.target.value : this.state.geneId),
            clusterId: (param === "clusterId" ? item.target.value : this.state.clusterId)
        }));
    };

    handleOptionsChange(e) {
        this.setState({clusterId: e.target.value});

        this.props.history.push("?" + queryString.stringify({
                clusterId: e.target.value
            }));
    }

    componentDidMount() {
        this.setState({
            geneId: this.state.params.geneId,
            clusterId: this.state.params.clusterId,
            K: this.state.params.k
        });
    }

    render() {

        return (
            <div>
                <h3>Experiment Page section</h3>
                <div className="large-6 columns">
                    <ReferencePlot clustersData={this.props.clustersData}
                                   clusterId={this.state.clusterId}
                                   handleOptionsChange={this.handleOptionsChange.bind(this)} />

                </div>
                <div className="large-6 columns">
                    <label>GeneId:</label>
                    <input type="text" value={this.state.geneId} onChange={this.handleChange.bind(this, "geneId")}/>
                </div>
            </div>
        );
    }
}

Experiment.propTypes = {
    props: PropTypes.object
};


export default ExperimentPage;

