const React = require( `react`);
const PropTypes = require( `../../PropTypes.js`);

const FilterGroup = React.createClass({
    propTypes: {
        name: React.PropTypes.string.isRequired,
        values: React.PropTypes.arrayOf(React.PropTypes.shape({
            name: React.PropTypes.string.isRequired,
            selected: React.PropTypes.bool.isRequired
        })).isRequired
    },

    getInitialState() {
        return {
            checked: this.props.values.filter(filterValue => filterValue.selected).map(filterValue => filterValue.name)
        }
    },

    handleChange(event) {
        const val = event.target.value;
        const checked = this.state.checked.slice(); // copy
        if (checked.includes(val)) {
            checked.splice(checked.indexOf(val), 1);
        } else {
            checked.push(val);
        }
        this.setState({ checked: checked })
    },

    render() {
        return (
            <div>
                <h3>{this.props.name}</h3>
                {this.props.values.map(filterValue =>
                    (
                        <label key={filterValue.name}>
                            <input type="checkbox" value={filterValue.name} onChange={this.handleChange}
                                   disabled={this.props.disabled}
                                   checked={this.state.checked.includes(filterValue.name)}/>
                            &nbsp;{filterValue.name}<br />
                        </label>
                    )
                )}
            </div>
        )
    }
});

module.exports = FilterGroup;