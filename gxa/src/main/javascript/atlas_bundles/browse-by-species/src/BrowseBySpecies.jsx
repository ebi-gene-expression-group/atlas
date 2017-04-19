import React from 'react';

import SpeciesItem from './SpeciesItem.jsx';

const BrowseBySpecies = props =>
{
    const speciesItems = props.speciesInfoList.map(speciesInfo =>
        <SpeciesItem key={speciesInfo.species}
                     atlasUrl={props.atlasUrl}
                     {...speciesInfo}
        />);

    return (
        <div className="row small-up-2 medium-up-3">
            {speciesItems}
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
