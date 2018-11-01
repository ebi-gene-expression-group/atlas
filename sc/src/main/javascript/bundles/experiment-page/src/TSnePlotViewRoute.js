import React from 'react'
import PropTypes from 'prop-types'
import URI from 'urijs'

import TSnePlotView from 'expression-atlas-experiment-page-tsne-plot'
import BioentityInformation from 'sc-atlas-bioentity-information'

const TSnePlotViewRoute = (props) => {

  const {location, history} = props

  const updateUrlWithParams = (query) => {
    history.push({...history.location, search: query.toString()})
  }

  const resetHighlightClusters = (query) => {
    if(query.has(`clusterId`)) {
      query.delete(`clusterId`)
    }
  }

  const {atlasUrl, suggesterEndpoint} = props
  const {species, experimentAccession, ks, perplexities, metadata} = props
  const search = URI(location.search).search(true)

  // Sort perplexities in ascending order
  const perplexitiesOrdered = perplexities.sort((a, b) => a - b)

  const preferredK = props.selectedK ? props.selectedK.toString() : props.ks[0].toString()

  return (
    <div className={`margin-top-large`}>
      <TSnePlotView
        atlasUrl={atlasUrl}
        suggesterEndpoint={suggesterEndpoint}
        wrapperClassName={`row expanded`}
        clusterPlotClassName={`small-12 large-6 columns`}
        expressionPlotClassName={`small-12 large-6 columns`}
        speciesName={species}
        experimentAccession={experimentAccession}
        ks={ks}
        metadata={metadata}
        selectedColourBy={search.k || search.metadata || preferredK }
        selectedColourByCategory={search.colourBy || `clusters`} // Is the plot coloured by clusters or metadata
        highlightClusters={search.clusterId ? JSON.parse(search.clusterId) : []}
        perplexities={perplexitiesOrdered}
        selectedPerplexity={Number(search.perplexity) || perplexitiesOrdered[Math.round((perplexitiesOrdered.length - 1) / 2)]}
        geneId={search.geneId || ``}
        height={800}
        onSelectGeneId={
          (geneId) => {
            const query = new URLSearchParams(history.location.search)
            query.set(`geneId`, geneId)
            resetHighlightClusters(query)
            updateUrlWithParams(query)
          }
        }
        onChangePerplexity={
          (perplexity) => {
            const query = new URLSearchParams(history.location.search)
            query.set(`perplexity`, perplexity)
            updateUrlWithParams(query)
          }
        }
        onChangeColourBy={
          (colourByCategory, colourByValue) => {
            const query = new URLSearchParams(history.location.search)
            query.set(`colourBy`, colourByCategory)
            if(colourByCategory === `clusters`) {
              query.set(`k`, colourByValue)
              query.delete(`metadata`)
            }
            else if(colourByCategory === `metadata`) {
              query.set(`metadata`, colourByValue)
              query.delete(`k`)
            }
            resetHighlightClusters(query)
            updateUrlWithParams(query)
          }
        }
      />
      {
        search.geneId &&
          <div className={`row expanded`}>
            <div className={`small-12 columns`}>
              <h4 key={`title`} className={`margin-top-large`}>Information about gene {search.geneId}</h4>
              <BioentityInformation key={`gene-information`} atlasUrl={atlasUrl} geneId={search.geneId} />
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
  suggesterEndpoint: PropTypes.string.isRequired,
  species: PropTypes.string.isRequired,
  metadata: PropTypes.arrayOf(PropTypes.shape({
    label: PropTypes.string.isRequired,
    value: PropTypes.string.isRequired
  }).isRequired).isRequired,
  selectedK: PropTypes.number
}

export default TSnePlotViewRoute
