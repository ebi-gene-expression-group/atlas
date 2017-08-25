import React from 'react'
import ReactDOM from 'react-dom'

import Disclaimers from '../src/index.js'

const renderDisclaimer = ({title, content}) => (
    <div>
        {title}
        {content}
    </div>
)

const renderDisclaimerOrCatPicture = (disclaimer) => (
    disclaimer && Disclaimers[disclaimer]
    ? renderDisclaimer(Disclaimers[disclaimer])
    : <img src="http://thecatapi.com/api/images/get?format=src&type=gif"/>
)

const Demo = () => (
    <div>
    <h3>Zebrafish</h3>
        {renderDisclaimerOrCatPicture("zebrafish")}
    <h3>Cat</h3>
        {renderDisclaimerOrCatPicture("cat")}
    </div>
)

const render = (options, target) => {
  ReactDOM.render(<Demo {...options} />, document.getElementById(target))
}

export {render}
