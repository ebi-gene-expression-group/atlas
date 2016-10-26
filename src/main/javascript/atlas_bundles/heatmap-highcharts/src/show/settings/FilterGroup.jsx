const React = require( `react`);
const Glyphicon = require(`react-bootstrap/lib/Glyphicon`);
const PropTypes = require( `../../PropTypes.js`);

const FilterGroup = React.createClass({
    propTypes: {
        name: React.PropTypes.string.isRequired,
        values: React.PropTypes.arrayOf(React.PropTypes.shape({
            name: React.PropTypes.string.isRequired,
            selected: React.PropTypes.bool.isRequired
        })).isRequired,
        elements: React.PropTypes.arrayOf(React.PropTypes.string).isRequired,
        onChange: React.PropTypes.func.isRequired
    },

    getInitialState() {
        return {
            checked: this.props.values.filter(filterValue => filterValue.selected).map(filterValue => filterValue.name),
            visibleElements: this.props.values.map(filterValue =>
                ({
                    name: filterValue.name,
                    showElements: false
                })
            )
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
        this.setState({ checked: checked });

        this.props.onChange(this.props.name, checked);
    },

    _toggleElements(filterValueName) {
        const newVisibleElements = this.state.visibleElements.map(visibleElement => visibleElement.name === filterValueName ?
            ({ name: visibleElement.name, showElements: !visibleElement.showElements }) :
            visibleElement
        );

        this.setState({ visibleElements: newVisibleElements })
    },

    _showElements(filterValueName) {
        return this.state.visibleElements.find(visibleElement => visibleElement.name === filterValueName).showElements
    },

    render() {
        return (
            <div>
                <h3>{this.props.name}</h3>
                    {this.props.values.map(filterValue =>
                        (
                            <div>
                                <label key={filterValue.name}>
                                    <input type="checkbox" value={filterValue.name} onChange={this.handleChange}
                                           disabled={this.props.disabled}
                                           checked={this.state.checked.includes(filterValue.name)}/>
                                </label>
                                <a style={{
                                    paddingLeft: `5px`,
                                    display: !this._showElements(filterValue.name) ? `inline-block` : `none`
                                   }}
                                   onClick={() => { this._toggleElements(filterValue.name) }} href="#">{filterValue.name} <Glyphicon style={{fontSize: `x-small`, paddingLeft: `5px`}} glyph="menu-down"/></a>
                                <a style={{
                                    paddingLeft: `5px`,
                                    display: this._showElements(filterValue.name) ? `inline-block` : `none`
                                   }}
                                   onClick={() => { this._toggleElements(filterValue.name) }} href="#">{filterValue.name} <Glyphicon style={{fontSize: `x-small`, paddingLeft: `5px`}} glyph="menu-up"/></a>
                                <div style={{
                                    paddingLeft: `10px`,
                                    fontSize: `small`,
                                    display: this._showElements(filterValue.name) ? `block` : `none`
                                  }}>
                                    {this.props.elements.map(element =>
                                        (
                                            <span key={element}>{element}<br /></span>
                                        )
                                    )}
                                </div>
                            </div>
                        )
                    )}
            </div>
        )
    }
});

module.exports = FilterGroup;