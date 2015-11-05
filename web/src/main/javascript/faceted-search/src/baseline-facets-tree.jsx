"use strict";

//*------------------------------------------------------------------*

var React = require('react');

//*------------------------------------------------------------------*

require('../css/facets-tree.css');

//*------------------------------------------------------------------*

var BaselineFacetsTree = React.createClass({
    propTypes: {
        /*
        {
            "homo sapiens" : [ {"factor": "CELL_LINE", "source": "Cell line"}, {"factor": "ORGANISM_PART", "source": "Tissue"} ],
            "mus musculus" : [ {"factor": "CELL_LINE", "source": "Cell line"}, {"factor": "INDIVIDUAL", "source": "Individual"} ]
        }
        */
        facets: React.PropTypes.object.isRequired,
        /*
        { "homo sapiens" : { "CELL_LINE": true, "ORGANISM_PART": true } }
        */
        checkedFacets: React.PropTypes.object,
        setChecked: React.PropTypes.func.isRequired
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
            <div className="hidden-xs gxaFacetsContainer"><h3>Filter your results</h3>
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

    render: function () {
        var facetItems = this.props.facetItems.map(function (facetItem) {
            return <FacetItem key={facetItem.name} name={facetItem.name} value={facetItem.value}
                checked={this.props.checkedFacetItems && this.props.checkedFacetItems[facetItem.name]}
                setChecked={this._setChecked}
            />;

        }.bind(this));

        return (
            <div className="gxaFacetItem">
                <h4>{this.props.facetName}</h4>
                <ul>
                    {facetItems}
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