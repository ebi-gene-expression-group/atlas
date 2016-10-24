const React = require('react');
const ReactDOMServer = require('react-dom/server');
const PropTypes = require('../PropTypes.js');
const Modal = require('react-bootstrap/lib/Modal');
const Button = require('react-bootstrap/lib/Button');
const Glyphicon = require('react-bootstrap/lib/Glyphicon');


const SettingsModal = React.createClass({
    propTypes: {
        content: React.PropTypes.element
    },

    getInitialState() {
        return { showModal: false };
    },

    _close() {
        this.setState({ showModal: false });
    },

    _open() {
        this.setState({ showModal: true });
    },


    render() {
        return (
            <div>
                <Button bsSize="small" onClick={this._open}>
                    <Glyphicon style={{verticalAlign: `middle`}} glyph="cog"/>
                    <span style={{verticalAlign: `middle`}}> Heatmap settings</span>
                </Button>


                <Modal show={this.state.showModal} onHide={this._close}>
                    <Modal.Header closeButton>
                        <Modal.Title>Modal heading</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        {this.props.content}
                    </Modal.Body>
                    <Modal.Footer>
                        <Button onClick={this._close}>Close</Button>
                    </Modal.Footer>
                </Modal>
            </div>
        )
    }
});

module.exports = SettingsModal;
