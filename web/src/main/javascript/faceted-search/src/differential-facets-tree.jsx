"use strict";

//*------------------------------------------------------------------*

var React = require('react');

//*------------------------------------------------------------------*



//*------------------------------------------------------------------*

var DifferentialFacetsTree = React.createClass({
    propTypes: {
        /*
        {
            "species": [ {"name": "homo sapiens", "value": "Homo sapiens"}, {"name": "arabidopsis thaliana", "value": "arabidopsis thaliana"} ],
            "experimentType": [ {"name": "rnaseq_mrna_differential", "value": "RNA-seq mRNA"}, {"name": "microarray_1colour_mrna_differential", "value": "1 colour mRNA"} ],
            "factors": [ {"name": "genotype", "value": "genotype"} ],
            "numReplicates": [ {"name": "3", "value": "3"} ],
            "regulation": [ {"name": "UP", "value": "up"} ]
        }
        */
        facets: React.PropTypes.object.isRequired,
        /*
        { "species" : { "homo sapiens": true, "arabidopsis thaliana": true }, "regulation": {"UP": true } }
        */
        disabledCheckedFacets: React.PropTypes.object.isRequired,
        disabledUncheckedFacets: React.PropTypes.object.isRequired,
        checkedFacets: React.PropTypes.object,
        setChecked: React.PropTypes.func.isRequired
    },

    _setChecked: function (checked, facet, facetItem) {
        this.props.setChecked(checked, facet, facetItem);
    },

    _disableUncheckedFacetItems: function (facet) {
        return this.props.disabledUncheckedFacets[facet] ? this.props.disabledUncheckedFacets[facet] : [];
    },

    render: function () {
        var facets = Object.keys(this.props.facets).map(function (facet) {
            return <Facet key={facet} facetName={facet} facetItems={this.props.facets[facet]}
                checkedFacetItems={this.props.checkedFacets && this.props.checkedFacets[facet]}
                setChecked={this._setChecked} disabledFacetItems={this.props.disabledCheckedFacets[facet] ? this.props.disabledCheckedFacets[facet] : []}
                disabledUncheckedFacetItems={this._disableUncheckedFacetItems(facet)}
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

        // eg: [ {"name": "rnaseq_mrna_differential", "value": "RNA-seq mRNA"}, {"name": "microarray_1colour_mrna_differential", "value": "1 colour mRNA"} ]
        facetItems: React.PropTypes.arrayOf(React.PropTypes.shape({
            name: React.PropTypes.string.isRequired,
            value: React.PropTypes.string.isRequired
        })).isRequired,

        disabledFacetItems: React.PropTypes.arrayOf(React.PropTypes.string).isRequired,
        disabledUncheckedFacetItems: React.PropTypes.arrayOf(React.PropTypes.string).isRequired,
        // eg: { "rnaseq_mrna_differential": true, "microarray_1colour_mrna_differential": true }
        checkedFacetItems: React.PropTypes.object,
        setChecked: React.PropTypes.func.isRequired
    },

    _disableFacets: function () {
        this.setState({disabledFacetItems: this.props.disabledUncheckedFacetItems});
    },

    _setChecked: function (checked, facetItem) {
        this.props.setChecked(checked, this.props.facetName, facetItem);
    },

    _prettifyFacetName: function(facetName) {
        switch (facetName) {
            case "kingdom":
                return "Kingdom";
            case "species":
                return "Species";
            case "experimentType":
                return "Experiment type";
            case "factors":
                return "Experimental variables";
            case "numReplicates":
                return "Number of replicates";
            case "regulation":
                return "Regulation";
            default:
                return facetName;
        }
    },

    render: function () {
        var facetItems = this.props.facetItems.map(function (facetItem) {
            var disabled = this.props.disabledFacetItems.indexOf(facetItem.name) != -1;
            var disabledUnchecked = this.props.disabledUncheckedFacetItems.indexOf(facetItem.name) != -1;

            return <FacetItem key={facetItem.name} name={facetItem.name} value={facetItem.value}
                checked={this.props.checkedFacetItems && this.props.checkedFacetItems[facetItem.name]}
                setChecked={this._setChecked} disabled={disabled} disabledUnchecked={disabledUnchecked}
            />;

        }.bind(this));

        var className = this.props.facetName === "species" ? "gxaSpeciesFacet" : "";

        return (
            <div className="gxaFacetItem">
                <h4>{this._prettifyFacetName(this.props.facetName)}</h4>
                <ul className={className}>
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
        setChecked: React.PropTypes.func.isRequired,
        disabled: React.PropTypes.bool.isRequired,
        disabledUnchecked: React.PropTypes.bool.isRequired
    },

    _setChecked: function () {
        this.props.setChecked(!this.props.checked, this.props.name);
    },

    render: function () {
        var className = this.props.disabledUnchecked ? "gxaDisabledFacet" : "";
        var _checked = this.props.checked || this.props.disabled;
        return (
            <li className={className}>
                <input type="checkbox" checked={_checked} onChange={this._setChecked} disabled={this.props.disabledUnchecked}/>
                {this.props.value}
            </li>
        );
    }
});

//*------------------------------------------------------------------*

module.exports = DifferentialFacetsTree;
