const React = require( `react`);
const Modal = require(`react-bootstrap/lib/Modal`);
const Button = require(`react-bootstrap/lib/Button`);
const Glyphicon = require(`react-bootstrap/lib/Glyphicon`);

const PropTypes = require( `../../PropTypes.js`);
const FilterGroup = require(`./FilterGroup.jsx`);
const Orderings = require(`./Orderings.jsx`);

const SettingsModal = React.createClass({
    propTypes: {
        filters: PropTypes.Filter,
        disabled: React.PropTypes.bool.isRequired,
        filtersSelection: PropTypes.Filter,
        onFilterChange: React.PropTypes.func.isRequired,
        orderings: PropTypes.Orderings
    },

    getInitialState() {
        return {
            filtersSelection: JSON.parse(JSON.stringify(this.props.filtersSelection)),
            orderingsSelection: this.props.orderings.selected,
            showModal: false
        };
    },

    _close() {
        this.setState({
            showModal: false,
            orderingsSelection: this.props.orderings.selected,
            filtersSelection: this.props.filtersSelection
        });
    },

    _apply() {
        this.props.orderings.onSelect(this.state.orderingsSelection);
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
                values={filter.values.map(filterValue =>
                    ({
                         name: filterValue,
                         selected: this.props.filtersSelection.find(filterSelection => filterSelection.name === filter.name).values.includes(filterValue)
                    })
                )}
                onChange={this._onFilterChange}
                elements={[`one`, `two`, `three`]}
            />
        )
    },

    _onOrderingsSelect(event) {
        this.setState({ orderingsSelection: event.target.value })
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
                {this.props.disabled ?
                    <Button bsSize="small" onClick={this._open} disabled>
                        <Glyphicon style={{verticalAlign: `middle`}} glyph="cog"/>
                        <span style={{verticalAlign: `middle`}}> Heatmap settings</span>
                    </Button> :

                    <Button bsSize="small" onClick={this._open}>
                        <Glyphicon style={{verticalAlign: `middle`}} glyph="cog"/>
                        <span style={{verticalAlign: `middle`}}> Heatmap settings</span>
                    </Button>
                }

                <Modal show={this.state.showModal} onHide={this._close}>
                    <Modal.Header closeButton>
                        <Modal.Title>Heatmap settings</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <Orderings
                            orderings={this.props.orderings}
                            onSelect={this._onOrderingsSelect}
                        />
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

module.exports = SettingsModal;
