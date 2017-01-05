import React from 'react';
import ReactDOM from 'react-dom';
import GenomeBrowserLauncherFetcher from './GenomeBrowserLauncherFetcher.jsx';

export default function({atlasBaseUrl, experimentAccession, accessKey, columnType, selectedGeneId, selectedColumnId, mountNode}) {

    ReactDOM.render(
        <GenomeBrowserLauncherFetcher atlasBaseUrl={atlasBaseUrl}
                                      experimentAccession={experimentAccession}
                                      accessKey={accessKey}
                                      columnType={columnType}
                                      selectedGeneId={selectedGeneId}
                                      selectedColumnId={selectedColumnId}
        />,
        mountNode);

};
