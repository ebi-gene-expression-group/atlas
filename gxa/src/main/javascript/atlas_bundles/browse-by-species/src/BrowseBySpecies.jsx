import React from 'react';

import SpeciesRow from './SpeciesRow.jsx';

// Split an array into chunks of length `size`. Or just use lodash: _.chunk(arr, size)
const splitArray = (arr, size) =>
{
    if (size === 0) {
        return [];
    }

    return arr.reduce((partition, element, index) => {
        index % size === 0 ? partition.push([element]) : partition[Math.floor(index / size)].push(element);
        return partition;
    }, []);
};

const BrowseBySpecies = props =>
{
    const columns = 3;
    // Split into chunks and add last for incomplete columns in Foundation
    const splitSpeciesInfoList = splitArray(props.speciesInfoList, columns);

    const speciesRows = splitSpeciesInfoList.map(
        (speciesInfo, index) => <SpeciesRow key={index} atlasUrl={props.atlasUrl} speciesInfoArray={speciesInfo} />);

    return (
        <div>
            {speciesRows}
        </div>
    );
};

BrowseBySpecies.propTypes = {
    atlasUrl: React.PropTypes.string.isRequired,
    speciesInfoList: React.PropTypes.arrayOf(React.PropTypes.shape({
        species: React.PropTypes.string.isRequired,
        totalExperiments: React.PropTypes.number.isRequired,
        baselineExperiments: React.PropTypes.number.isRequired,
        differentialExperiments: React.PropTypes.number.isRequired
    })).isRequired,
};

export default BrowseBySpecies;
