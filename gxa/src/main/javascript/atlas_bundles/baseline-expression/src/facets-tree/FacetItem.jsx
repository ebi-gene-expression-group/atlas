import React from 'react'
import PropTypes from 'prop-types'

const FacetItem = props =>
    <div>
        <input type="checkbox" checked={props.checked} onChange={() => props.setChecked(props.name, !props.checked)} />
        <label>{props.value}</label>
    </div>

FacetItem.propTypes = {
    name: PropTypes.string.isRequired,
    value: PropTypes.string.isRequired,
    checked: PropTypes.bool.isRequired,
    setChecked: PropTypes.func.isRequired
};

export default FacetItem