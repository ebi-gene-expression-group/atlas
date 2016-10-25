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
        filtersSelection: React.PropTypes.arrayOf(PropTypes.FilterSelection),
        orderings: PropTypes.Orderings
    },

    getInitialState() {
        return { showModal: false };
    },

    _close() {
        this.setState({ showModal: false });
    },

    _apply() {
        // Render heatmap with new options
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
                         selected: this.props.filtersSelection.filter(filterSelection => filterSelection.name === filter.name)[0].selected[i]
                    })
                )}/>
        )
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
                        <Orderings orderings={this.props.orderings} />
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
