import React from 'react';

const FacetItem = props =>
    <div>
        <input type="checkbox" checked={props.checked} onChange={() => props.setChecked(props.name, !props.checked)} />
        <label>{props.value}</label>
    </div>;

FacetItem.propTypes = {
    name: React.PropTypes.string.isRequired,
    value: React.PropTypes.string.isRequired,
    checked: React.PropTypes.bool.isRequired,
    setChecked: React.PropTypes.func.isRequired
};

export default FacetItem;