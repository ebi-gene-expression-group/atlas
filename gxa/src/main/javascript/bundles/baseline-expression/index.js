import React from 'react'
import ReactDOM from 'react-dom'

import BaselineRouter from './src/BaselineRouter'

const render = ({ atlasUrl = `https://www.ebi.ac.uk/gxa`,
                  facetsTreeData,
                  geneQuery,
                  conditionQuery,
                  species },
                target) => {
  ReactDOM.render(
    <BaselineRouter
      atlasUrl={atlasUrl}
      facetsTreeData={facetsTreeData}
      geneQuery={geneQuery}
      conditionQuery={conditionQuery}
      species={species} />,
    document.getElementById(target)
  )
}

export { render }
