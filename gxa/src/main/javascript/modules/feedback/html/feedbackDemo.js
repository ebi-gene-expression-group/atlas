import React from 'react'
import ReactDOM from 'react-dom'

var Feedback = require('../src/Feedback.js');

const collectionCallback = (satisfactionScore, optionalComment) => {
  console.log(`Score: ${satisfactionScore}` +
    ((typeof optionalComment === `undefined` || optionalComment.length === 0) ?
      `, no comment` : `, comment: ` + optionalComment))
}

const render = (target) => {
  ReactDOM.render(<Feedback collectionCallback={collectionCallback} />, document.getElementById(target))
}

export {render}
