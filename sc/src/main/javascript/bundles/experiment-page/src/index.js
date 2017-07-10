import React from 'react';
import ReactDOM from 'react-dom';

import ExperimentPage from './experimentPage.js';

const render = function (options, container) {
    ReactDOM.render(<ExperimentPage {...options} />, document.getElementById(container))
};

export {ExperimentPage as default, render}