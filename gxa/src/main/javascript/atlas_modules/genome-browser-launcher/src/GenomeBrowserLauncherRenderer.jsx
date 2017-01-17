import React from 'react';
import ReactDOM from 'react-dom';
import GenomeBrowserLauncherFetcher from './GenomeBrowserLauncherFetcher.jsx';

export default function({atlasBaseUrl, pathToResources, experimentAccession, accessKey, columnType, selectedGeneId, selectedColumnId, mountNode}) {

    ReactDOM.render(
        <GenomeBrowserLauncherFetcher atlasBaseUrl={atlasBaseUrl}
                                      pathToResources={pathToResources}
                                      experimentAccession={experimentAccession}
                                      accessKey={accessKey}
                                      columnType={columnType}
                                      selectedGeneId={selectedGeneId}
                                      selectedColumnId={selectedColumnId}
        />,
        mountNode);

};
