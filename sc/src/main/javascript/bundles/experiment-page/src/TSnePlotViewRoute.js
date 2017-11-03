import React from 'react'
import PropTypes from 'prop-types'
import URI from 'urijs'

import TSnePlotView from 'expression-atlas-experiment-page-tsne-plot'
import BioentityInformation from 'sc-atlas-bioentity-information'

const fetchResponseJson = async (base, endpoint) => {
    const response = await fetch(URI(endpoint, base).toString())
    const responseJson = await response.json()
    return responseJson
}

class TSnePlotViewRoute extends React.Component {

  constructor(props) {
    super(props)
    this.state = {
      geneId: null,
      bioentityProperties: [],
      errorMessage: null,
    }
  }

  _fetchAndSetState() {
    const {atlasUrl} = this.props;
    const {geneId} = this.state;
    const search = URI(location.search).search(true)

    const gene = geneId ? geneId : search.geneId

    const bioentityEndPoint = `json/bioEntityInformation/${gene}`;

    if(gene) {
      return fetchResponseJson(atlasUrl, bioentityEndPoint)
        .then((responseJson) => {
            this.setState({
                geneId : gene,
                bioentityProperties: responseJson.bioentityProperties,
                errorMessage: null,
            })
        })
        .catch((reason) => {
            this.setState({
                errorMessage: `${reason.name}: ${reason.message}`,
            })
        })
    }
  }

  componentDidMount() {
      return this._fetchAndSetState()
  }

  render() {
    const {location, history} = this.props
    const {bioentityProperties} = this.state
    const updateUrlSearch = (parameter) => {
        history.push(URI(location.search).setSearch(parameter.name, parameter.value).toString())
        this._fetchAndSetState()
    }
    const {atlasUrl, resourcesUrl} = this.props
    const {suggesterEndpoint, experimentAccession, ks, perplexities} = this.props
    const search = URI(location.search).search(true)

    return (
      <div>
        <TSnePlotView atlasUrl={atlasUrl}
                    suggesterEndpoint={suggesterEndpoint}
                    experimentAccession={experimentAccession}
                    ks={ks}
                    k={Number(search.k) || ks[0]}
                    highlightClusters={search.clusterId ? JSON.parse(search.clusterId) : []}
                    perplexities={perplexities}
                    perplexity={Number(search.perplexity) || perplexities[0]}
                    geneId={search.geneId || ``}
                    height={600}
                    resourcesUrl={resourcesUrl}
                    onSelectGeneId={(geneId) => {
                        updateUrlSearch({name: `geneId`, value: geneId})
                    }}
                    onChangeK={(k) => {
                        updateUrlSearch({name: `k`, value: k})
                    }}
                    onChangePerplexity={(perplexity) => {
                        updateUrlSearch({name: `perplexity`, value: perplexity})
                    }}
        />

        <BioentityInformation bioentityProperties={bioentityProperties}/>
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
  suggesterEndpoint: PropTypes.string.isRequired
}

export default TSnePlotViewRoute
