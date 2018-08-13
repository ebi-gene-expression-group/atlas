import React from 'react'
import PropTypes from 'prop-types'

import TSnePlotView from 'expression-atlas-experiment-page-tsne-plot'
import BioentityInformation from 'sc-atlas-bioentity-information'

const TSnePlotViewRoute = (props) => {

  const {history} = props
  const query = new URLSearchParams(history.location.search)

  const updateUrlWithParams = (query) => {
    history.push({...history.location, search: query.toString()})
  }

  const resetHighlightClusters = (query) => {
    if(query.has('clusterId')) {
      query.delete('clusterId')
    }
  }

  const {atlasUrl, suggesterEndpoint} = props
  const {species, experimentAccession, ks, perplexities, metadata} = props

  return (
    <div className={`margin-top-large`}>
      <TSnePlotView atlasUrl={atlasUrl}
                    suggesterEndpoint={suggesterEndpoint}
                    wrapperClassName={`row expanded`}
                    clusterPlotClassName={`small-12 large-6 columns`}
                    expressionPlotClassName={`small-12 large-6 columns`}
                    speciesName={species}
                    experimentAccession={experimentAccession}
                    ks={ks}
                    metadata={metadata}
                    selectedColourBy={query.get(`k`) || query.get(`metadata`) || props.ks[0].toString()}
                    selectedColourByCategory={query.get(`colourBy`) || `clusters`} // Is the plot coloured by clusters or metadata
                    highlightClusters={query.has(`clusterId`) ? JSON.parse(query.get(`clusterId`)) : []}
                    perplexities={perplexities}
                    selectedPerplexity={Number(query.get(`perplexity`))|| props.perplexities[Math.round((perplexities.length - 1) / 2)]}
                    geneId={query.get(`geneId`) || ``}
                    height={800}
                    onSelectGeneId={
                      (geneId) => {
                        query.set('geneId', geneId)
                        resetHighlightClusters(query)
                        updateUrlWithParams(query)
                      }
                    }
                    onChangePerplexity={
                      (perplexity) => {
                        query.set('perplexity', perplexity)
                        updateUrlWithParams(query)
                      }
                    }
                    onChangeColourBy={
                      (colourByCategory, colourByValue) => {
                        query.set('colourBy', colourByCategory)
                        if(colourByCategory === 'clusters') {
                          query.set('k', colourByValue)
                          query.delete('metadata')
                        }
                        else if(colourByCategory === 'metadata') {
                          query.set('metadata', colourByValue)
                          query.delete('k')
                        }
                        resetHighlightClusters(query)
                        updateUrlWithParams(query)
                      }
                    }
      />
      {
        query.has(`geneId`) &&
          <div className={`row expanded`}>
            <div className={`small-12 columns`}>
              <h4 key={`title`} className={`margin-top-large`}>Information about gene {query.get(`geneId`)}</h4>
              <BioentityInformation key={`gene-information`} atlasUrl={atlasUrl} geneId={query.get(`geneId`)} />
            </div>
          </div>
      }
    </div>

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
