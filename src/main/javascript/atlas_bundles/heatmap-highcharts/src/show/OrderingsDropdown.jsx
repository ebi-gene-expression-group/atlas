const React = require( `react`);
const Dropdown = require(`react-bootstrap/lib/Dropdown`);
const DropdownButton = require(`react-bootstrap/lib/DropdownButton`);
const MenuItem = require(`react-bootstrap/lib/MenuItem`);
const Glyphicon = require(`react-bootstrap/lib/Glyphicon`);
const PropTypes = require( `../PropTypes.js`);

const OrderingsDropdown = React.createClass({
    propTypes: {
        orderings: PropTypes.Orderings,
        disabled: React.PropTypes.bool.isRequired
    },

    handleChange(eventKey, event) {
        this.props.orderings.onSelect(event.target.text);
    },

    _orderingIcon(ordering) {
        switch (ordering) {
            case `Alphabetical order`:
                return `sort-by-alphabet`;
            case `Gene expression rank`:
                return `sort-by-attributes-alt`;
            case `Default`:
                return `sort-by-order`;
            default:
                return `sort-by-order`;
        }
    },

    render() {
        return (
            <span>
                <Dropdown bsSize="small" id="orderings-dropdown" onSelect={this.handleChange} title={this.props.disabled ? `Reset zoom to enable sorting options` : ``}>
                    <Dropdown.Toggle disabled={this.props.disabled}>
                        <Glyphicon glyph={this._orderingIcon(this.props.orderings.selected)} /> {this.props.orderings.selected}
                    </Dropdown.Toggle>
                    <Dropdown.Menu>
                        {this.props.orderings.available.map(orderingName =>
                            (
                                <MenuItem style={{textDecoration: `none`}} key={orderingName}>{orderingName}</MenuItem>
                            )
                        )}
                    </Dropdown.Menu>
                </Dropdown>
            </span>
        )
    }
});

module.exports = OrderingsDropdown;