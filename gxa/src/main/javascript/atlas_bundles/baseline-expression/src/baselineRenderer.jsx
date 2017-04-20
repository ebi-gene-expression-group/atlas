import React from 'react';
import ReactDOM from 'react-dom';

import BaselineRouter from './BaselineRouter.jsx';

export default function (
    { atlasUrl = `https://www.ebi.ac.uk/gxa`, target = 'gxaBaselineTab', facetsTreeData,
      geneQuery, conditionQuery, species }) {

    ReactDOM.render(
        <BaselineRouter atlasUrl={atlasUrl}
                        facetsTreeData={facetsTreeData}
                        geneQuery={geneQuery}
                        conditionQuery={conditionQuery}
                        species={species}
        />,
        document.getElementById(target)
    );

};
