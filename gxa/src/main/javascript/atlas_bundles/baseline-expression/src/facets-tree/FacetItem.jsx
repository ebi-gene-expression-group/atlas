import React from 'react';

const FacetItem = props =>
    <li>
        <input type="checkbox" checked={props.checked} onChange={() => props.setChecked(props.name, !props.checked)}/>
        {props.value}
    </li>;

FacetItem.propTypes = {
    name: React.PropTypes.string.isRequired,
    value: React.PropTypes.string.isRequired,
    checked: React.PropTypes.bool.isRequired,
    setChecked: React.PropTypes.func.isRequired
};

export default FacetItem;