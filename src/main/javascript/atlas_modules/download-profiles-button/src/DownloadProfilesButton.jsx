"use strict";

//*------------------------------------------------------------------*

var React = require('react');

var Modal = require('react-bootstrap/lib/Modal');
var Button = require('react-bootstrap/lib/Button');
var Glyphicon = require('react-bootstrap/lib/Glyphicon');
var Tooltip = require('react-bootstrap/lib/Tooltip');
var OverlayTrigger = require('react-bootstrap/lib/OverlayTrigger');

var Disclaimers = require('./Disclaimers.jsx');

//*------------------------------------------------------------------*



//*------------------------------------------------------------------*
var DownloadProfilesButton = React.createClass({
    propTypes: {
        atlasBaseURL: React.PropTypes.string.isRequired,
        downloadProfilesURL: React.PropTypes.string.isRequired,
        disclaimer: React.PropTypes.string.isRequired,
        onDownloadCallbackForAnalytics: React.PropTypes.func.isRequired
    },
    getInitialState: function() {
        return { showModal: false };
    },

    _closeModal: function() {
        this.setState({ showModal: false });
    },

    _disclaimer: function() {
      return this.props.disclaimer && Disclaimers[this.props.disclaimer] || null;
    },

    _afterDownloadButtonClicked: function() {
        if(!this._disclaimer()) {
            this._commenceDownload();
        } else {
            this.setState({ showModal: true });
        }
    },

    _commenceDownload: function() {
        this.props.onDownloadCallbackForAnalytics();
        window.location.href=this.props.atlasBaseURL + this.props.downloadProfilesURL;
    },

    _commenceDownloadAndCloseModal: function() {
        this._commenceDownload();
        this._closeModal();
    },

    render: function() {

        return (
            <a ref="downloadProfilesLink" onClick={this._afterDownloadButtonClicked}>
                <Button bsSize="xsmall">
                    <Glyphicon style={{verticalAlign: 'middle', paddingBottom: '2px'}} glyph="download-alt"/>
                    <span style={{verticalAlign: 'middle', paddingTop: '2px'}}> Download all results</span>
                </Button>

                <Modal show={this.state.showModal} onHide={this._closeModal} bsSize="large">
                    <Modal.Header closeButton>
                    </Modal.Header>
                    <Modal.Body style={{maxHeight: '360px'}}>
                      {this._disclaimer()}
                    </Modal.Body>
                    <Modal.Footer>
                        <Button onClick={this._closeModal}>Close</Button>
                        <Button bsStyle="primary" onClick={this._commenceDownloadAndCloseModal}>Continue downloading</Button>
                    </Modal.Footer>
                </Modal>
            </a>
        );
    }
});

module.exports = DownloadProfilesButton;
