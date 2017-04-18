import React from 'react';
import ReactDOM from 'react-dom';

import BrowseBySpecies from './BrowseBySpecies.jsx';

export default function ({atlasUrl, speciesInfoList, container}) {
    ReactDOM.render(
        <BrowseBySpecies atlasUrl={atlasUrl} speciesInfoList={speciesInfoList} />,
        typeof container === `string` ? document.getElementById(container) : container)
};
