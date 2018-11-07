import React from 'react'
import PropTypes from 'prop-types'
import URI from 'urijs'

import HeatmapView from 'scxa-marker-gene-heatmap'
import TSnePlotView from 'expression-atlas-experiment-page-tsne-plot'
import BioentityInformation from 'sc-atlas-bioentity-information'

class TSnePlotViewRoute extends React.Component {
  constructor(props) {
    super(props)
  }

  componentDidMount() {
    // Required for the magellan element to be initialised. Even if this is called in the JSP, it doesn't seem to affect the element inside a React component
    window.$(document).foundation()
  }

  render() {
    const {location, history} = this.props
    const {atlasUrl, suggesterEndpoint} = this.props
    const {species, experimentAccession, ks, perplexities, metadata} = this.props
    const search = URI(location.search).search(true)

    const updateUrlWithParams = (query) => {
      history.push({...history.location, search: query.toString()})
    }

    const resetHighlightClusters = (query) => {
      if(query.has(`clusterId`)) {
        query.delete(`clusterId`)
      }
    }

    // Sort perplexities in ascending order
    const perplexitiesOrdered = perplexities.sort((a, b) => a - b)

    const preferredK = this.props.selectedK ? this.props.selectedK.toString() : this.props.ks[0].toString()

    return (
      <div className={`margin-top-large`} id={`plotContainer`}>
        <div className={`row expanded`}>
          <div className="large-2 columns">
            <nav className="sticky-container" data-sticky-container>
              <div className="sticky experiment-page-side-nav" data-sticky data-anchor="plotContainer" data-sticky-on="large" data-margin-top="3">
                <ul className="vertical menu" data-magellan data-events="scroll">
                  <li><a href="#first">Marker gene heatmap</a></li>
                  <li><a href="#second">t-SNE plots</a></li>
                  {
                    search.geneId && <li><a href="#third">Gene information</a></li>
                  }
                </ul>
              </div>
            </nav>
          </div>
          <div className="sections large-10 columns">
            <section id="first" data-magellan-target="first" className={`margin-bottom-large`}>
              <HeatmapView
                host={atlasUrl}
                resource={`json/experiments/${experimentAccession}/marker-genes/${search.markerGeneK || preferredK}`}
                wrapperClassName={`row expanded`}
                ks={ks}
                selectedK={search.markerGeneK || preferredK}
                onSelectK={
                  (k) => {
                    const query = new URLSearchParams(history.location.search)
                    query.set(`markerGeneK`, k)
                    // If tsne plot is coloured by k
                    if(!query.has(`metadata`)) {
                      query.set(`k`, k)
                      query.set(`colourBy`, `clusters`)
                    }
                    resetHighlightClusters(query)
                    updateUrlWithParams(query)
                  }
                }/>
            </section>
            <hr/>
            <section id="second" data-magellan-target="second" className={`margin-top-large margin-bottom-large`}>
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
                selectedColourBy={search.k || search.metadata || preferredK}
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
                      query.set(`markerGeneK`, colourByValue)
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
            </section>
            {
              search.geneId &&
                [
                  <hr/>,
                  <section id="third" data-magellan-target="third" className={`margin-top-large`}>
                    <h4 key={`title`}>Information about gene {search.geneId}</h4>
                    <BioentityInformation key={`gene-information`} atlasUrl={atlasUrl} geneId={search.geneId} />
                  </section>
                ]
            }
          </div>
        </div>
      </div>
    )
  }
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
