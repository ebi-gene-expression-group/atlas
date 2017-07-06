import React from 'react';
import ReactDOM from 'react-dom';

import ExperimentPage from './experimentPage.jsx';

// const render = function (options, target) {
//     ReactDOM.render(<ExperimentPage {...options} />, document.getElementById(target))
// };
//
// export {render}
export default function ({atlasUrl, experimentAccession, container}) {
    ReactDOM.render(
        <ExperimentPage atlasUrl={atlasUrl}
                        experimentAccession={experimentAccession}/>,
        typeof container === `string` ? document.getElementById(container) : container)
};

