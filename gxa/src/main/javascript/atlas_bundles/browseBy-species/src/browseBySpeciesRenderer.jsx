import React from 'react';
import ReactDOM from 'react-dom';

//*------------------------------------------------------------------*

import BrowseBySpecies from './BrowseBySpecies.jsx';

//*------------------------------------------------------------------*

export default function ({atlasHost : hostUrl = window.location.protocol + "//" + window.location.host,
                            speciesInfoList, mountNode}) {
    ReactDOM.render(
        <BrowseBySpecies atlasBaseUrl={hostUrl}
                         speciesInfoList={speciesInfoList}
        />
    , mountNode)
};
