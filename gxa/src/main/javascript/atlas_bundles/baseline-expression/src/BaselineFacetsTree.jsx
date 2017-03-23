const React = require('react');

//*------------------------------------------------------------------*

require('./BaselineFacetsTree.css');

//*------------------------------------------------------------------*

const RequiredString = React.PropTypes.string.isRequired;
const RequiredFunction = React.PropTypes.func.isRequired;
const RequiredBool = React.PropTypes.bool.isRequired;

const BaselineFacetsTree = React.createClass({
    propTypes: {
        /*
        [
            {"facetName" : "homo sapiens",
             "facetItems": [ {"name": "CELL_LINE", "value": "Cell line", "checked": true},
                             {"name": "ORGANISM_PART", "value": "Tissue", "checked: true} ]},
             "facetName" : "mus musculus",
             "facetItems": [ {"name": "CELL_LINE", "value": "Cell line", "checked": false},
                             {"name": "ORGANISM_PART", "value": "Tissue", "checked": true} ]}
        ]
        */
        facets: React.PropTypes.arrayOf(
            React.PropTypes.shape({
                facetName: RequiredString,
                facetItems: React.PropTypes.arrayOf(
                    React.PropTypes.shape({
                        name: RequiredString,
                        value: RequiredString,
                        checked: RequiredBool
                    }).isRequired
                ).isRequired
            }).isRequired
        ).isRequired,
        setChecked: React.PropTypes.func.isRequired,
        showAnatomograms: RequiredBool,
        toggleAnatomograms: RequiredFunction,
        disableAnatomogramsCheckbox: RequiredBool
    },

    _setChecked (facetName, facetItemName, checked) {
        this.props.setChecked(facetName, facetItemName, checked);
    },

    render () {
        let facets = this.props.facets.map(facet =>
            <Facet
                key = {facet.facetName}
                facetName = {facet.facetName}
                facetItems = {facet.facetItems}
                setChecked = {this._setChecked}
            />
        );

        return (
            <div className="hidden-xs gxaFacetsContainer">
                <h5 style={{padding: 0}}>
                    <input type="checkbox" checked={this.props.showAnatomograms} onChange={this.props.toggleAnatomograms} disabled={this.props.disableAnatomogramsCheckbox}/>
                    <span className={this.props.disableAnatomogramsCheckbox ? "gxaDisabledCheckbox" : ""}>Show anatomograms</span>
                </h5>
                <h3 className="filterTitle">Filter your results</h3>
                {facets}
            </div>
        );
    }
});

const Facet = React.createClass({
    propTypes: {
        facetName: RequiredString,
        facetItems: React.PropTypes.arrayOf(React.PropTypes.shape({
            name: RequiredString,
            value: RequiredString,
            checked: RequiredBool
        })).isRequired,
        setChecked: RequiredFunction
    },

    _setChecked (facetItemName, checked) {
        this.props.setChecked(this.props.facetName, facetItemName, checked);
    },

    render () {
        let facetItems = this.props.facetItems.map(facetItem =>
            <FacetItem
                key = {this.props.facetName + '_' + facetItem.name}
                name = {facetItem.name}
                value = {facetItem.value}
                checked = {facetItem.checked}
                setChecked = {this._setChecked}
            />
        );

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

const FacetItem = React.createClass({
    propTypes: {
        name: RequiredString,
        value: RequiredString,
        checked: RequiredBool,
        setChecked: RequiredFunction
    },

    _setChecked () {
        this.props.setChecked(this.props.name, !this.props.checked);
    },

    render () {
        return (
            <li>
                <input type="checkbox" checked={!!this.props.checked} onChange={this._setChecked}/>
                {this.props.value}
            </li>
        );
    }
});

//*------------------------------------------------------------------*

module.exports = BaselineFacetsTree;
