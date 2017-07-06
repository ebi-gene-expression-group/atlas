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
                        <Experiment {...props}/>
                    )}/>
                </div>
            </BrowserRouter>

        );
    }
}

ExperimentPage.propTypes = {
    atlasUrl: PropTypes.string.isRequired,
    experimentAccession: PropTypes.string.isRequired
};

class Experiment extends Component {

    constructor(props) {
        super(props);
        this.state = {
            params: queryString.parse(props.location.search),
            p1: "",
            p2: ""
        };
    }

    handleChange(param, item) {
        let _newparam = {};
        _newparam[param] = item.target.value;
        this.setState(_newparam);

        this.props.history.push("?" + queryString.stringify({
            p1: (param === "p1" ? item.target.value : this.state.p1),
            p2: (param === "p2" ? item.target.value : this.state.p2)
        }));
    };

    componentDidMount() {
        this.setState({
            p1: this.state.params.p1,
            p2: this.state.params.p2
        });
    }

    render() {

        return (
            <div>
                <h3>Experiment Page section</h3>
                <div className="large-6 columns">
                    <label>Perplexity:</label>
                    <input type="text" value={this.state.p1} onChange={this.handleChange.bind(this, "p1")}/>
                    <h3>Plot</h3>
                    <ReferencePlot />

                </div>
                <div className="large-6 columns">
                    <label>GeneId:</label>
                    <input type="text" value={this.state.p2} onChange={this.handleChange.bind(this, "p2")}/>
                </div>
            </div>
        );
    }
}

Experiment.propTypes = {
    props: PropTypes.object
};


export default ExperimentPage;

