import React from 'react'
import PropTypes from 'prop-types'
import URI from 'urijs'

import TSnePlotView from 'expression-atlas-experiment-page-tsne-plot'

const TSnePlotViewRoute = (props) => {

  const {location, history} = props

  const updateUrlSearch = (parameter) => {
    history.push(URI(location.search).setSearch(parameter.name, parameter.value).toString())
  }

  const {atlasUrl, resourcesUrl} = props
  const {suggesterEndpoint, experimentAccession, ks, perplexities} = props
  const search = URI(location.search).search(true)

  return (
    <TSnePlotView atlasUrl={atlasUrl}
                  suggesterEndpoint={suggesterEndpoint}
                  experimentAccession={experimentAccession}
                  ks={ks}
                  k={Number(search.k) || props.ks[0]}
                  highlightClusters={search.clusterId ? JSON.parse(search.clusterId) : []}
                  perplexities={perplexities}
                  perplexity={Number(search.perplexity) || props.perplexities[0]}
                  geneId={search.geneId || ``}
                  height={600}
                  resourcesUrl={resourcesUrl}
                  onSelectGeneId={ (geneId) => { updateUrlSearch({ name: `geneId`, value: geneId }) } }
                  onChangeK={ (k) => { updateUrlSearch({ name: `k`, value: k }) } }
                  onChangePerplexity={ (perplexity) => { updateUrlSearch({ name: `perplexity`, value: perplexity }) } }
    />
  )
}

TSnePlotViewRoute.propTypes = {
  match: PropTypes.object.isRequired,
  location: PropTypes.object.isRequired,
  history: PropTypes.object.isRequired,
  atlasUrl: PropTypes.string.isRequired,
  resourcesUrl: PropTypes.string,
  experimentAccession: PropTypes.string.isRequired,
  ks: PropTypes.arrayOf(PropTypes.number).isRequired,
  perplexities: PropTypes.arrayOf(PropTypes.number).isRequired,
  suggesterEndpoint: PropTypes.string.isRequired
}

export default TSnePlotViewRoute
