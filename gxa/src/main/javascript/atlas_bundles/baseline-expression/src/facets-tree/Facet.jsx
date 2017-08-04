import React from 'react'
import PropTypes from 'prop-types'

import FacetItem from './FacetItem.jsx'

const capitalizeFirstLetter = str => str.charAt(0).toUpperCase() + str.slice(1).toLowerCase()

const Facet = props => {
    const facetItems = props.facetItems.map(facetItem =>
        <FacetItem
            key={`${props.facetName}_${facetItem.name}`}
            name={facetItem.name}
            value={facetItem.value}
            checked={facetItem.checked}
            setChecked={(facetItemName, facetItemChecked) => { props.setChecked(props.facetName, facetItemName, facetItemChecked) }}
        />
    )

    return (
        <div className="margin-top-large">
            <h5>{capitalizeFirstLetter(props.facetName)}</h5>
            {facetItems}
        </div>
    )
}

Facet.propTypes = {
    facetName: PropTypes.string.isRequired,
    facetItems: PropTypes.arrayOf(React.PropTypes.shape({
        name: PropTypes.string.isRequired,
        value: PropTypes.string.isRequired,
        checked: PropTypes.bool.isRequired
    })).isRequired,
    setChecked: PropTypes.func.isRequired
}

export default Facet