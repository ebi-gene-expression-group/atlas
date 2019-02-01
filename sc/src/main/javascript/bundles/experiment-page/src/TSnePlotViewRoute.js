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

const GeneInfoRoute = (props) => {
  return (
    <section className={`margin-top-large`}>
      <h4 key={`title`}>Information about gene {props.geneId}</h4>
      <BioentityInformation key={`gene-information`} atlasUrl={props.atlasUrl} geneId={props.geneId} />
    </section>
  )
}

const TsnePlotRoute = (props) => {
  return (
    <section>
      <TSnePlotView {...props}/>
    </section>
  )
}

const MarkerGeneHeatmapRoute = (props) => {
  return (
    <section>
      <HeatmapView {...props}
      />
    </section>
  )
}

class TSnePlotViewRoute extends React.Component {
  constructor(props) {
    super(props)
  }

  render() {
    const {location, history} = this.props
    const {atlasUrl, suggesterEndpoint} = this.props
    const {species, experimentAccession, ks, perplexities, metadata} = this.props
    const search = URI(location.search).search(true)

    const routes = [
      {
        path: `/tsne`,
        title: `Clusters`,
        main: () => <TsnePlotRoute
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
        main: () => <MarkerGeneHeatmapRoute
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
        />
      },
      {
        path: `/gene-info`,
        title: `Information about ${search.geneId}`,
        main: () => <GeneInfoRoute atlasUrl={atlasUrl} geneId={search.geneId}/>
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

    const basename = URI(`experiments/${experimentAccession}${location.pathname}`, URI(atlasUrl).path()).toString()

    return (
      <BrowserRouter basename={basename}>
        <div style={{ display: `flex` }}>
          <div
            style={{
              width: `20%`,
              // background: `#e6e6e6`
              background: `#ffffff`
            }}
          >
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

            {routes.map((route, index) => (
              <Route
                key={index}
                path={`/`}
                component={route.sidebar}
              />
            ))}
          </div>

          <div style={{ flex: 1, padding: `10px` }}>
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
