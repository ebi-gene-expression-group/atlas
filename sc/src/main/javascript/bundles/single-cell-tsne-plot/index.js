import React from 'react'
import ReactDOM from 'react-dom'

import TSNEPlotContainer from 'single-cell-tsne-plot'

const render = function (options, target) {
    ReactDOM.render(<TSNEPlotContainer {...options} />, document.getElementById(target))
}

export {render}

