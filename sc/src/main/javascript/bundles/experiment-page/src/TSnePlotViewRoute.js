import React from 'react'
import PropTypes from 'prop-types'
import URI from 'urijs'
import {BrowserRouter, Route, NavLink, Switch, Redirect, withRouter} from 'react-router-dom'

import HeatmapView from 'scxa-marker-gene-heatmap'
import TSnePlotView from 'expression-atlas-experiment-page-tsne-plot'
import BioentityInformation from 'sc-atlas-bioentity-information'

const RedirectWithSearchAndHash = (props) =>
  <Redirect to={{ pathname: props.pathname, search: props.location.search, hash: props.location.hash}} />

RedirectWithSearchAndHash.propTypes = {
  pathname: PropTypes.string.isRequired,
  location: PropTypes.shape({
    search: PropTypes.string.isRequired,
    hash: PropTypes.string.isRequired
  }).isRequired
}

const RedirectWithLocation = withRouter(RedirectWithSearchAndHash)

class TSnePlotViewRoute extends React.Component {
  constructor(props) {
    super(props)
  }

  render() {
    const {location, match, history} = this.props
    const {atlasUrl, suggesterEndpoint} = this.props
    const {species, experimentAccession, ks, ksWithMarkerGenes, perplexities, metadata} = this.props
    const search = URI(location.search).search(true)

    const routes = [
      {
        path: `/tsne`,
        title: `t-SNE plots`,
        main: () => <TSnePlotView
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
      },
      {
        path: `/marker-genes`,
        title: `Marker Genes`,
        main: () => <HeatmapView
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
              if (!query.has(`metadata`)) {
                query.set(`k`, k)
                query.set(`colourBy`, `clusters`)
              }
              resetHighlightClusters(query)
              updateUrlWithParams(query)
            }
          }
          ksWithMarkers={ksWithMarkerGenes}
        />
      },
      {
        path: `/gene-info`,
        title: `Gene information`,
        main: () => <BioentityInformation atlasUrl={atlasUrl} geneId={search.geneId}/>
      }
    ]

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

    const basename = URI(`experiments/${experimentAccession}${match.path}`, URI(atlasUrl).path()).toString()

    return (
      <BrowserRouter basename={basename}>
        <div className={`row expanded`}>
          <div
            className={`small-3 medium-2 large-1 columns`}
            style={{
              padding: 0,
              background: `#ffffff`
            }}>
            <ul className={`side-tabs`}>
              <li title={routes[0].title} className={`side-tabs-title`}>
                <NavLink to={{pathname:routes[0].path, search: location.search, hash: location.hash}}
                  activeClassName={`active`}>
                  {routes[0].title}</NavLink>
              </li>
              <li title={routes[1].title} className={`side-tabs-title`}>
                <NavLink to={{pathname:routes[1].path, search: location.search, hash: location.hash}}
                  activeClassName={`active`}>
                  {routes[1].title}</NavLink>
              </li>
              {
                search.geneId &&
                  <li title={routes[2].title} className={`side-tabs-title`}>
                    <NavLink to={{pathname:routes[2].path, search: location.search, hash: location.hash}}
                      activeClassName={`active`}>
                      {routes[2].title}</NavLink>
                  </li>
              }
            </ul>
          </div>
          <div
            className={`small-9 medium-10 large-11 columns`}
            style={{padding: `10px`}}>
            <Switch>
              {routes.map((route, index) => (
                <Route
                  key={index}
                  path={route.path}
                  component={route.main}
                />
              ))}
              <RedirectWithLocation pathname={`${routes[0].path}`} />
            </Switch>
          </div>
        </div>
      </BrowserRouter>
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
  ksWithMarkerGenes: PropTypes.arrayOf(PropTypes.number).isRequired,
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
