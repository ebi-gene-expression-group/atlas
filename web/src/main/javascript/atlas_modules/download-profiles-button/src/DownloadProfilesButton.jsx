"use strict";

//*------------------------------------------------------------------*

var React = require('react');
var ReactDOM = require('react-dom');

var Modal = require('react-bootstrap/lib/Modal');
var BootstrapButton = require('react-bootstrap/lib/Button');
var BlueprintText = require('./BlueprintText.jsx');

var $ = require('jquery');
require('jquery-ui-bundle');

//*------------------------------------------------------------------*



//*------------------------------------------------------------------*
var DownloadProfilesButton = React.createClass({
    propTypes: {
        atlasBaseURL: React.PropTypes.string.isRequired,
        downloadProfilesURL: React.PropTypes.string.isRequired,
        isFortLauderdale: React.PropTypes.bool.isRequired,
        googleAnalyticsCallback: React.PropTypes.func.isRequired
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
        this.props.googleAnalyticsCallback('send', 'event', 'HeatmapReact', 'downloadData');
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
               title="Download all results"
               href="javascript:void(0)" onClick={this._afterDownloadButtonClicked}>
                <img id="download-profiles" alt="Download query results" style={{width: "20px"}} src={downloadImgSrcURL}/>
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
    },

    componentDidMount: function () {
        var $downloadProfilesLink = $(ReactDOM.findDOMNode(this.refs.downloadProfilesLink));
        $downloadProfilesLink.tooltip({
            tooltipClass: "gxaHelpTooltip"
        });
        $downloadProfilesLink.button();
    }
});

module.exports = DownloadProfilesButton;
