import React from 'react';
import SpeciesItem from './SpeciesItem.jsx';

const SpeciesRow = props => {
    const speciesItems = props.speciesInfoArray.map((speciesInfo, index) =>
        <SpeciesItem key = {speciesInfo.species}
                     atlasUrl = {props.atlasUrl}
                     last = {index === props.speciesInfoArray.length - 1}
                     {...speciesInfo}
        />);

    return (
        <div className="row">
            {speciesItems}
        </div>
    );
};

SpeciesRow.propTypes = {
    atlasUrl: React.PropTypes.string.isRequired,
    speciesInfoArray: React.PropTypes.arrayOf(React.PropTypes.shape({
        species: React.PropTypes.string.isRequired,
        totalExperiments: React.PropTypes.number.isRequired,
        baselineExperiments: React.PropTypes.number.isRequired,
        differentialExperiments: React.PropTypes.number.isRequired,
    })).isRequired
};

export default SpeciesRow;