const React = require( `react`);
const PropTypes = require( `../../PropTypes.js`);

const Orderings = React.createClass({
    propTypes: {
        orderings: PropTypes.Orderings,
        onSelect: React.PropTypes.func.isRequired
    },

    getInitialState() {
        return({ value: this.props.orderings.selected })
    },

    handleChange(event) {
        this.props.onSelect(event);
    },

    render() {
        return (
            <div>
                <h3>Ordering</h3>
                {this.props.orderings.available.map(orderingName =>
                    (
                        <label key={orderingName}>
                            <input type="radio" name="orderings" value={orderingName} onChange={this.handleChange}
                                   defaultChecked={orderingName === this.props.orderings.selected}/>&nbsp;{orderingName}<br />
                        </label>
                    )
                )}
            </div>
        )
    }
});

module.exports = Orderings;