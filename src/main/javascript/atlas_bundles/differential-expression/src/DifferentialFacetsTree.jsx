const React = require('react');

//*------------------------------------------------------------------*

require('./DifferentialFacetsTree.css');

//*------------------------------------------------------------------*

const RequiredString = React.PropTypes.string.isRequired;
const RequiredBool = React.PropTypes.bool.isRequired;

const DifferentialFacetsTree = React.createClass({
    propTypes: {
        /*
        [
            { "facetName" : "species",
              "facetItems" : [ {"name": "homo sapiens", "value": "Homo sapiens", checked: false, disabled: false},
                               {"name": "arabidopsis thaliana", "value": "arabidopsis thaliana", checked: true, disabled: false} ]
            },
            { "facetName" : "experimentType",
              "facetItems" : [ {"name": "rnaseq_mrna_differential", "value": "RNA-seq mRNA", checked: false, disabled: true},
                                {"name": "microarray_1colour_mrna_differential", "value": "1 colour mRNA", checked: false, disabled: false} ]
            },
            { "facetName" : "factors",
              "facetItems" : [ {"name": "genotype", "value": "genotype", checked: true, disabled: true} ]
            },
            { "facetName" : "numReplicates",
              "facetItems" : [ {"name": "3", "value": "3", checked: true, disabled: true} ]
            },
            { "facetName" : "regulation".
              "facetItems" : [ {"name": "UP", "value": "Up", checked: true, disabled: false},
                            {"name": "DOWN", "value": "Down", checked: false, disabled: false} ]
            }
        ]
        */
        facets: React.PropTypes.arrayOf(
            React.PropTypes.shape({
                facetName: RequiredString,
                facetItems: React.PropTypes.arrayOf(
                    React.PropTypes.shape({
                        name: RequiredString,
                        value: RequiredString,
                        checked: RequiredBool,
                        disabled: RequiredBool
                    }).isRequired
                ).isRequired
            }).isRequired
        ).isRequired,
        setChecked: React.PropTypes.func.isRequired
    },

    _setChecked (facetName, facetItemName, checked) {
        this.props.setChecked(facetName, facetItemName, checked);
    },

    render () {
        let facets = this.props.facets.map(facet =>
            <Facet
                key={facet.facetName}
                facetName={facet.facetName}
                facetItems={facet.facetItems}
                setChecked={this._setChecked}
            />
        );

        return (
            <div className="hidden-xs gxaFacetsContainer"><h3>Filter your results</h3>
                {facets}
            </div>
        );
    }
});

const Facet = React.createClass({
    propTypes: {
        facetName: React.PropTypes.string.isRequired,
        facetItems: React.PropTypes.arrayOf(
            React.PropTypes.shape({
                name: RequiredString,
                value: RequiredString,
                checked: RequiredBool,
                disabled: RequiredBool
            }).isRequired
        ).isRequired,
        setChecked: React.PropTypes.func.isRequired
    },

    _setChecked (facetItemName, checked) {
        this.props.setChecked(this.props.facetName, facetItemName, checked);
    },

    _prettifyFacetName (facetName) {
        switch (facetName) {
            case 'kingdom':
                return 'Kingdom';
            case 'species':
                return 'Species';
            case 'experimentType':
                return 'Experiment type';
            case 'factors':
                return 'Experimental variables';
            case 'numReplicates':
                return 'Number of replicates';
            case 'regulation':
                return 'Regulation';
            default:
                return facetName;
        }
    },

    render () {
        let facetItems = this.props.facetItems.map(facetItem =>
            <FacetItem
                key = {facetItem.name}
                name = {facetItem.name}
                value = {facetItem.value}
                checked = {facetItem.checked}
                disabled = {facetItem.disabled}
                setChecked = {this._setChecked}
            />
        );

        let className = this.props.facetName === 'species' ? 'gxaSpeciesFacet' : '';

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

const FacetItem = React.createClass({
    propTypes: {
        name: RequiredString,
        value: RequiredString,
        checked: RequiredBool,
        disabled: RequiredBool,
        setChecked: React.PropTypes.func.isRequired,
    },

    _setChecked () {
        this.props.setChecked(this.props.name, !this.props.checked);
    },

    render () {
        let className = this.props.disabled ? 'gxaDisabledFacet' : '';
        return (
            <li className={className}>
                <input type="checkbox" checked={this.props.checked} onChange={this._setChecked} disabled={this.props.disabled}/>
                {this.props.value}
            </li>
        );
    }
});

//*------------------------------------------------------------------*

module.exports = DifferentialFacetsTree;