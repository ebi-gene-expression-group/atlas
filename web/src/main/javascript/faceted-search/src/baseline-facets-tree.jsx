"use strict";

//*------------------------------------------------------------------*

var React = require('react');

//*------------------------------------------------------------------*



//*------------------------------------------------------------------*

var BaselineFacetsTree = React.createClass({
    propTypes: {
        /*
        {
            "homo sapiens" : [ {"name": "CELL_LINE", "value": "Cell line"}, {"name": "ORGANISM_PART", "value": "Tissue"} ],
            "mus musculus" : [ {"name": "CELL_LINE", "value": "Cell line"}, {"name": "INDIVIDUAL", "value": "Individual"} ]
        }
        */
        facets: React.PropTypes.object.isRequired,
        /*
        { "homo sapiens" : { "CELL_LINE": true, "ORGANISM_PART": true } }
        */
        checkedFacets: React.PropTypes.object,
        setChecked: React.PropTypes.func.isRequired,
        showAnatomograms: React.PropTypes.bool.isRequired,
        toggleAnatomograms: React.PropTypes.func.isRequired,
        disableAnatomogramsCheckbox: React.PropTypes.bool.isRequired
    },

    _setChecked: function (checked, facet, facetItem) {
        this.props.setChecked(checked, facet, facetItem);
    },

    render: function () {
        var facets = Object.keys(this.props.facets).map(function (facet) {
            return <Facet key={facet} facetName={facet} facetItems={this.props.facets[facet]}
                checkedFacetItems={this.props.checkedFacets && this.props.checkedFacets[facet]}
                setChecked={this._setChecked}
            />;
        }.bind(this));

        return (
            <div className="hidden-xs gxaFacetsContainer">
                <h5 style={{padding: 0}}>
                    <input type="checkbox" checked={this.props.showAnatomograms} onChange={this.props.toggleAnatomograms} disabled={this.props.disableAnatomogramsCheckbox}/>
                    <span className={this.props.disableAnatomogramsCheckbox ? "gxaDisabledCheckbox" : ""}>Show anatomograms</span>
                </h5>
                <h3>Filter your results</h3>
                {facets}
            </div>
        );
    }
});

var Facet = React.createClass({
    propTypes: {
        facetName: React.PropTypes.string.isRequired,
        facetItems: React.PropTypes.arrayOf(React.PropTypes.shape({
            name: React.PropTypes.string.isRequired,
            value: React.PropTypes.string.isRequired
        })).isRequired,
        checkedFacetItems: React.PropTypes.object,
        setChecked: React.PropTypes.func.isRequired
    },

    _setChecked: function (checked, facetItem) {
        this.props.setChecked(checked, this.props.facetName, facetItem);
    },

    _isOrganismPart: function(a){
        return a.props.name==="ORGANISM_PART";
    },
    _isNotOrganismPart: function(a){
        return ! this._isOrganismPart(a);
    },


    render: function () {
        var facetItems = this.props.facetItems.map(function (facetItem) {
            return <FacetItem key={facetItem.name} name={facetItem.name} value={facetItem.value}
                checked={this.props.checkedFacetItems && this.props.checkedFacetItems[facetItem.name]}
                setChecked={this._setChecked}
            />;

        }.bind(this));
        var facetItemsInOrderWeWant=facetItems.filter(this._isOrganismPart).concat(facetItems.filter(this._isNotOrganismPart));

        return (
            <div className="gxaFacetItem">
                <h4>{this.props.facetName}</h4>
                <ul>
                    {facetItemsInOrderWeWant}
                </ul>
            </div>
        );
    }
});

var FacetItem = React.createClass({
    propTypes: {
        name: React.PropTypes.string.isRequired,
        value: React.PropTypes.string.isRequired,
        checked: React.PropTypes.bool,
        setChecked: React.PropTypes.func.isRequired
    },

    _setChecked: function () {
        this.props.setChecked(!this.props.checked, this.props.name);
    },

    render: function () {
        return (
            <li>
                <input type="checkbox" checked={this.props.checked ? true : false} onChange={this._setChecked}/>
                {this.props.value}
            </li>
        );
    }
});

//*------------------------------------------------------------------*

module.exports = BaselineFacetsTree;
