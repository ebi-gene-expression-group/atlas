const React = require( `react`);
const Glyphicon = require(`react-bootstrap/lib/Glyphicon`);
const PropTypes = require( `../../PropTypes.js`);

const FilterGroup = React.createClass({
    propTypes: {
        name: React.PropTypes.string.isRequired,
        values: React.PropTypes.arrayOf(React.PropTypes.shape({
            name: React.PropTypes.string.isRequired,
            elements: React.PropTypes.arrayOf(React.PropTypes.string),
            selected: React.PropTypes.bool.isRequired
        })).isRequired,
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

    _toggleElements(event, filterValueName) {
        event.preventDefault();

        const newVisibleElements = this.state.visibleElements.map(visibleElement => visibleElement.name === filterValueName ?
            ({ name: visibleElement.name, showElements: !visibleElement.showElements }) :
            visibleElement
        );

        this.setState({ visibleElements: newVisibleElements })
    },

    _areElementsVisible(filterValueName) {
        return this.state.visibleElements.find(visibleElement => visibleElement.name === filterValueName).showElements
    },

    _expandableFilterValueWithElements(filterValue) {
        return (
        <span style={{paddingLeft: `5px`, textTransform: `capitalize`}}>
            <a style={{display: !this._areElementsVisible(filterValue.name) ? `inline-block` : `none`}}
                       onClick={(event) => { this._toggleElements(event, filterValue.name)}}
               href="#"
            >{filterValue.name}<Glyphicon style={{fontSize: `x-small`, paddingLeft: `5px`}} glyph="menu-down"/>
            </a>
            <a style={{display: this._areElementsVisible(filterValue.name) ? `inline-block` : `none`}}
                       onClick={(event) => { this._toggleElements(event, filterValue.name)}}
               href="#"
            >{filterValue.name}<Glyphicon style={{fontSize: `x-small`, paddingLeft: `5px`}} glyph="menu-up"/>
            </a>
            <ul style={{paddingLeft: `30px`, fontSize: `small`,
                        display: this._areElementsVisible(filterValue.name) ? `block` : `none`}}
            >{filterValue.elements.map(element =>
                (
                    <li key={element}>{element}</li>
                )
            )}
            </ul>
        </span>
        );
    },

    render() {
        return (
            <div>
                <h4>{this.props.name}</h4>

                    {this.props.values.map(filterValue =>
                        (
                            <div key={filterValue.name}>
                                <input type="checkbox" value={filterValue.name} onChange={this.handleChange}
                                       disabled={this.props.disabled}
                                       checked={this.state.checked.includes(filterValue.name)}/>
                                {filterValue.elements ?
                                    this._expandableFilterValueWithElements(filterValue) :
                                    <span style={{paddingLeft: `5px`}}>{filterValue.name}</span>
                                }
                            </div>
                        )
                    )}
            </div>
        )
    }
});

module.exports = FilterGroup;