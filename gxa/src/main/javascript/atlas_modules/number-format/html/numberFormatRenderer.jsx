import React from 'react';
import ReactDOM from 'react-dom';

import NumberFormat from '../src/NumberFormat.jsx';

class Test extends React.Component {
    constructor(props, context) {
        super(props, context);

        this.state = {
            valueBaseline: ``,
            valueScientific: ``
        }
    }

    render() {
        return (
            <div>
                <div>
                    <h3>Baseline format</h3>
                    <input type="text"
                           onChange={evt => {evt.target.value && this.setState({ valueBaseline: evt.target.value })
                           }}/>
                    <p>{`Raw string: ${this.state.valueBaseline}`}</p>
                    <p>{NumberFormat.baselineExpression(this.state.valueBaseline)}</p>
                </div>
                <div>
                    <h3>Scientific format</h3>
                    <input type="text"
                           onChange={evt => {evt.target.value && this.setState({ valueScientific: evt.target.value })
                           }}/>
                    <p>{`Raw string: ${this.state.valueScientific}`}</p>
                    <p>{NumberFormat.scientificNotation(this.state.valueScientific)}</p>
                </div>
            </div>
        );
    }
}

const render = ({mountNode}) => {
    ReactDOM.render(<Test/>, mountNode);
};

export {render};
