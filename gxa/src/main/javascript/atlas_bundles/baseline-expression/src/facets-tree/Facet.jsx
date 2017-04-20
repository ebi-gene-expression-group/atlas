import React from 'react';

import FacetItem from './FacetItem.jsx';

const Facet = props => {
    const facetItems = props.facetItems.map(facetItem =>
        <FacetItem
            key={`${props.facetName}_${facetItem.name}`}
            name={facetItem.name}
            value={facetItem.value}
            checked={facetItem.checked}
            setChecked={(facetItemName, facetItemChecked) => { props.setChecked(props.facetName, facetItemName, facetItemChecked) }}
        />
    );

    return (
        <div>
            <h4>{props.facetName}</h4>
            <ul>
                {facetItems}
            </ul>
        </div>
    );
};

Facet.propTypes = {
    facetName: React.PropTypes.string.isRequired,
    facetItems: React.PropTypes.arrayOf(React.PropTypes.shape({
        name: React.PropTypes.string.isRequired,
        value: React.PropTypes.string.isRequired,
        checked: React.PropTypes.bool.isRequired
    })).isRequired,
    setChecked: React.PropTypes.func.isRequired
};

export default Facet;