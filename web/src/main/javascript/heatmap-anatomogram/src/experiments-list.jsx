"use strict";

//*------------------------------------------------------------------*

var React = require('react');

//*------------------------------------------------------------------*

var ExperimentsList = React.createClass({
    propTypes: {
        profiles: React.PropTypes.object.isRequired,
        atlasBaseURL: React.PropTypes.string.isRequired,
        linksAtlasBaseURL: React.PropTypes.string.isRequired,
        geneQuery: React.PropTypes.string.isRequired
    },

    _lexicalSort: function(thisProfile, thatProfile) {
        if (thisProfile.name > thatProfile.name) {
            return 1;
        }
        if (thisProfile.name < thatProfile.name) {
            return -1;
        }
        // a must be equal to b
        return 0;
    },

    _renderListItems: function(options) {
        return options.profiles.sort(this._lexicalSort).map(function(profile) {

            var experimentURL =
                options.linksAtlasBaseURL +
                "/experiments/" + profile.id + "?geneQuery=" + options.geneQuery +
                (profile.serializedFilterFactors ?
                    "&serializedFilterFactors=" + encodeURIComponent(profile.serializedFilterFactors) : "");

            return (
                <li key={profile.name}>
                    <a target="_blank" href={experimentURL}>{profile.name}</a>
                </li>
            );
        });
    },

    render: function() {
        return (
            <ul style={{listStyleType: "none", paddingLeft: "0"}}>
                {this._renderListItems({profiles: this.props.profiles.rows, linksAtlasBaseURL: this.props.linksAtlasBaseURL, geneQuery: this.props.geneQuery})}
            </ul>
        );
    }
});

//*------------------------------------------------------------------*

module.exports = ExperimentsList;