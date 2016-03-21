"use strict";

//*------------------------------------------------------------------*

var React = require('react');
var BootstrapButton = require('react-bootstrap/lib/Button');

//*------------------------------------------------------------------*

var ExperimentsList = React.createClass({
    propTypes: {
        profiles: React.PropTypes.shape({
            rows: React.PropTypes.array.isRequired
        }).isRequired,
        atlasBaseURL: React.PropTypes.string.isRequired,
        linksAtlasBaseURL: React.PropTypes.string.isRequired,
        geneQuery: React.PropTypes.string.isRequired
    },

    getInitialState: function() {
        return {"displayAll": this.props.profiles.rows.length < 10};
    },

    _getRowsToDisplay: function() {
        var rows = this.props.profiles.rows.sort(this._lexicalSort);
        return this.state.displayAll ? rows : rows.slice(0,10);
    },

    _displayAll :function() {
      this.setState({"displayAll" : true});
    },

    _lexicalSort: function(thisProfile, thatProfile) {
        if (thisProfile.name > thatProfile.name) {
            return 1;
        }
        if (thisProfile.name < thatProfile.name) {
            return -1;
        }
        return 0;
    },

    _renderListItem: function (profile) {
        var experimentURL =
            this.props.linksAtlasBaseURL +
            "/experiments/" + profile.id + "?geneQuery=" + this.props.geneQuery +
            (profile.serializedFilterFactors ?
            "&serializedFilterFactors=" + encodeURIComponent(profile.serializedFilterFactors) : "");

        return (
            <li key={profile.name}>
                <a target="_blank" href={experimentURL}>{profile.name}</a>
            </li>
        );
    },

    _renderListItems: function(options) {
        return this._getRowsToDisplay().map(this._renderListItem);
    },

    render: function() {
        return (
            <ul style={{listStyleType: "none", paddingLeft: "0"}}>
                {this._renderListItems()}
                {this.state.displayAll? <a/> : <BootstrapButton bsStyle="default" bsSize="xsmall" onClick={this._displayAll}>More</BootstrapButton> }
            </ul>
        );
    }
});

//*------------------------------------------------------------------*

module.exports = ExperimentsList;