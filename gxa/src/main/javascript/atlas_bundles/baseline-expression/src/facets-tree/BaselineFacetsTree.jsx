import React from 'react'
import PropTypes from 'prop-types'

import Facet from './Facet.jsx'

const BaselineFacetsTree = props => {
    const facets = props.facets.map(facet =>
        <Facet
            key = {facet.facetName}
            facetName = {facet.facetName}
            facetItems = {facet.facetItems}
            setChecked = {props.setChecked}
        />
    )

    return (
        <div>
            <input type="checkbox" checked={props.showAnatomograms} onChange={props.toggleAnatomograms} disabled={props.disableAnatomogramsCheckbox}/>
            <label className={props.disableAnatomogramsCheckbox ? "gxaDisabledCheckbox" : ""}>Show anatomograms</label>

            <h4>Filter your results</h4>
            {facets}
        </div>
    )
}

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
    facets: PropTypes.arrayOf(React.PropTypes.shape({
        facetName: PropTypes.string.isRequired,
        facetItems: PropTypes.arrayOf(React.PropTypes.shape({
            name: PropTypes.string.isRequired,
            value: PropTypes.string.isRequired,
            checked: PropTypes.bool.isRequired
        })).isRequired
    })).isRequired,
    setChecked: PropTypes.func.isRequired,
    showAnatomograms: PropTypes.bool.isRequired,
    toggleAnatomograms: PropTypes.func.isRequired,
    disableAnatomogramsCheckbox: PropTypes.bool.isRequired
}

export default BaselineFacetsTree
