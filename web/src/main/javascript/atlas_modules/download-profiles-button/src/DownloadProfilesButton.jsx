"use strict";

//*------------------------------------------------------------------*

var React = require('react');
var ReactDOM = require('react-dom');

var Modal = require('react-bootstrap/lib/Modal');
var BootstrapButton = require('react-bootstrap/lib/Button');
var Tooltip = require('react-bootstrap/lib/Tooltip');
var OverlayTrigger = require('react-bootstrap/lib/OverlayTrigger');

var BlueprintText = require('./BlueprintText.jsx');

//*------------------------------------------------------------------*



//*------------------------------------------------------------------*
var DownloadProfilesButton = React.createClass({
    propTypes: {
        atlasBaseURL: React.PropTypes.string.isRequired,
        downloadProfilesURL: React.PropTypes.string.isRequired,
        isFortLauderdale: React.PropTypes.bool.isRequired,
        onDownloadCallbackForAnalytics: React.PropTypes.func.isRequired
    },
    getInitialState: function() {
        return { showModal: false };
    },

    _closeModal: function () {
        this.setState({ showModal: false });
    },

    _afterDownloadButtonClicked: function () {
        if(!this.props.isFortLauderdale) {
            this._commenceDownload();
        } else {
            this.setState({ showModal: true });
        }
    },

    _commenceDownload: function () {
        this.props.onDownloadCallbackForAnalytics();
        window.location.href=this.props.atlasBaseURL + this.props.downloadProfilesURL;
    },

    _commenceDownloadAndCloseModal: function (){
        this._commenceDownload();
        this._closeModal();
    },

    render: function () {
        var downloadImgSrcURL = this.props.atlasBaseURL + "/resources/images/download_blue_small.png";
        return (
            <a id="download-profiles-link" ref="downloadProfilesLink" className="gxaNoTextButton"
               href="javascript:void(0)" onClick={this._afterDownloadButtonClicked}>
                <OverlayTrigger
                  placement="bottom"
                  overlay={<Tooltip id="downloadResultsTooltip">
                              <div className="gxaHelpTooltip">
                                Download all results
                              </div>
                            </Tooltip>}
                  delay={0}>
                  <img id="download-profiles" alt="Download query results" style={{width: "20px"}} src={downloadImgSrcURL}/>
                </OverlayTrigger>
                <Modal id="myModal" show={this.state.showModal} onHide={this._closeModal} bsSize="large">
                    <Modal.Header closeButton>
                    </Modal.Header>
                    <Modal.Body style={{'maxHeight' : '360px' }}>
                        <BlueprintText/>
                    </Modal.Body>
                    <Modal.Footer>
                        <BootstrapButton onClick={this._closeModal}>Close</BootstrapButton>
                        <BootstrapButton bsStyle="primary" onClick={this._commenceDownloadAndCloseModal}>Continue downloading</BootstrapButton>
                    </Modal.Footer>
                </Modal>
            </a>
        );
    }
});

module.exports = DownloadProfilesButton;
