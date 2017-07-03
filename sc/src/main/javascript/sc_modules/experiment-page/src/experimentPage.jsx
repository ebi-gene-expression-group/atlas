import React, {Component} from 'react';
import {BrowserRouter, Route} from 'react-router-dom';
import queryString from 'query-string';
import PropTypes from 'prop-types';

class ExperimentPage extends Component {

    render() {
        return (
            <BrowserRouter>
                <div>
                    <Route path='/experiment' render={props => (
                               <Experiment {...props}/>
                           )}/>
                </div>
            </BrowserRouter>

        );
    }
}

ExperimentPage.propTypes = {
    atlasUrl: PropTypes.string.isRequired,
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

        this.props.history.push("/experiment?" + queryString.stringify({p1:(param === "p1" ? item.target.value : this.state.p1),
                                                                        p2:(param === "p2" ? item.target.value : this.state.p2)}));

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
                <h3>Welcome to the Experiment Page</h3>
                <div className="small-2">
                    <label>Param p1 is:</label>
                    <input type="text" value={this.state.p1} onChange={this.handleChange.bind(this, "p1")}/>

                    <label>Param p2 is:</label>
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

