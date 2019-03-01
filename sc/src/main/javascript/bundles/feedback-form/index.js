import React from 'react'
import ReactDOM from 'react-dom'

import FeedbackButton from 'atlas-feedback-form'

const render = (options, target) => {
    ReactDOM.render(<FeedbackButton {...options} />, document.getElementById(target))
}

export {render}
