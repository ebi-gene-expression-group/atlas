import React from 'react';

import Facet from './Facet.jsx';

// import './BaselineFacetsTree.css';

const BaselineFacetsTree = props => {
    const facets = props.facets.map(facet =>
        <Facet
            key = {facet.facetName}
            facetName = {facet.facetName}
            facetItems = {facet.facetItems}
            setChecked = {props.setChecked}
        />
    );

    return (
        <div>
            <h5>
                <input type="checkbox" checked={props.showAnatomograms} onChange={props.toggleAnatomograms} disabled={props.disableAnatomogramsCheckbox}/>
                <span className={props.disableAnatomogramsCheckbox ? "gxaDisabledCheckbox" : ""}>Show anatomograms</span>
            </h5>
            <h3>Filter your results</h3>
            {facets}
        </div>
    );
};

BaselineFacetsTree.propTypes = {
    /*
     [
         {
             facetName : "homo sapiens",
             facetItems: [
                 {"name": "CELL_LINE", "value": "Cell line", "checked": true},
                 {"name": "ORGANISM_PART", "value": "Tissue", "checked: true}
             ]
         },
         {
             facetName : "mus musculus",
             facetItems": [
                 {"name": "CELL_LINE", "value": "Cell line", "checked": false},
                 {"name": "ORGANISM_PART", "value": "Tissue", "checked": true}
             ]
         }
     ]
     */
    facets: React.PropTypes.arrayOf(React.PropTypes.shape({
        facetName: React.PropTypes.string.isRequired,
        facetItems: React.PropTypes.arrayOf(React.PropTypes.shape({
            name: React.PropTypes.string.isRequired,
            value: React.PropTypes.string.isRequired,
            checked: React.PropTypes.bool.isRequired
        })).isRequired
    })).isRequired,
    setChecked: React.PropTypes.func.isRequired,
    showAnatomograms: React.PropTypes.bool.isRequired,
    toggleAnatomograms: React.PropTypes.func.isRequired,
    disableAnatomogramsCheckbox: React.PropTypes.bool.isRequired
};

export default BaselineFacetsTree;
