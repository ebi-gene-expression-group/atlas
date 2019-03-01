import React from 'react'
import ReactDOM from 'react-dom'

import disclaimers from '../src/disclaimers'

const DisclaimerOrCatPicture = ({project}) => {
  const Disclaimer = disclaimers[project]

  return (
    Disclaimer ?
      <Disclaimer /> :
      <img src={`http://thecatapi.com/api/images/get?format=src&type=gif`} />
  )
}

const Demo = () => {
  const allDisclaimers =
    Object.keys(disclaimers)
      .map(disclaimerKey => {
        const Disclaimer = disclaimers[disclaimerKey]
        return <div key={disclaimerKey}><Disclaimer /><hr/></div>
      })

  return(
    <div>
      {allDisclaimers}
      <DisclaimerOrCatPicture project={`lauderdale`}/>
      <hr/>
      <DisclaimerOrCatPicture project={`cat`}/>
    </div>
  )
}

const render = (options, target) => {
  ReactDOM.render(<Demo {...options} />, document.getElementById(target))
}

export { render }
