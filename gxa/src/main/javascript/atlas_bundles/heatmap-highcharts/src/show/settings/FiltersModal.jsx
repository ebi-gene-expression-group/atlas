const React = require( `react`);
const Modal = require(`react-bootstrap/lib/Modal`);
const Button = require(`react-bootstrap/lib/Button`);
const Glyphicon = require(`react-bootstrap/lib/Glyphicon`);

const PropTypes = require( `../../PropTypes.js`);
const FilterGroup = require(`./FilterGroup.jsx`);

const FiltersModal = React.createClass({
    propTypes: {
        filters: PropTypes.Filter,
        disabled: React.PropTypes.bool.isRequired,
        filtersSelection: PropTypes.Filter,
        onFilterChange: React.PropTypes.func.isRequired
    },

    getInitialState() {
        return {
            filtersSelection: JSON.parse(JSON.stringify(this.props.filtersSelection)),
            showModal: false
        };
    },

    _close() {
        this.setState({
            showModal: false,
            filtersSelection: this.props.filtersSelection
        });
    },

    _apply() {
        this.props.onFilterChange(this.state.filtersSelection);
        this.setState({ showModal: false });
    },

    _open() {
        this.setState({ showModal: true });
    },

    _createFilter(filter) {
        return (
            <FilterGroup
                key={filter.name}
                name={filter.name}
                values={filter.values.map((filterValue, i) =>
                    ({
                        name: filterValue,
                        elements: filter.elements ? filter.elements[i] : null,
                        selected: this.props.filtersSelection.find(filterSelection => filterSelection.name === filter.name).values.includes(filterValue)
                    })
                )}
                onChange={this._onFilterChange}
            />
        )
    },

    _onFilterChange(name, values) {
        // TODO Hacky: newFiltersSelection *MUST* preserve the order because HeatmapWithControls.jsx does [0] and slices at specific values
        const newFiltersSelection = this.state.filtersSelection.map(filterSelection =>
            filterSelection.name === name ?
                ({ name, values }) :
                filterSelection
        );

        this.setState({ filtersSelection: newFiltersSelection });
    },

    render() {
        return (
            <div>
                <Button bsSize="small" onClick={this._open} disabled={this.props.disabled} title={this.props.disabled ? `Reset zoom to enable filters` : ``}>
                    <Glyphicon glyph="equalizer"/>
                    <span style={{verticalAlign: `middle`}}> Filters</span>
                </Button>

                <Modal show={this.state.showModal} onHide={this._close}>
                    <Modal.Header closeButton>
                        <Modal.Title>Filters</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        {this.props.filters.map(this._createFilter)}
                    </Modal.Body>
                    <Modal.Footer>
                        <Button bsStyle="primary" onClick={this._apply}>Apply</Button>
                        <Button onClick={this._close}>Close</Button>
                    </Modal.Footer>
                </Modal>
            </div>
        )
    }
});

module.exports = FiltersModal;
