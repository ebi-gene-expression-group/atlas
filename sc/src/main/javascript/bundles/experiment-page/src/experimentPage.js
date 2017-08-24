import React, {Component} from 'react'
import PropTypes from 'prop-types'
import {BrowserRouter, Route} from 'react-router-dom'
import queryString from 'query-string'
import URI from 'urijs'

import ReferencePlot from 'single-cell-tsne-plot'
import GeneTSNEPlot from 'single-cell-gene-tsne-plot'

class ExperimentPage extends Component {

  render() {
    return (
      <BrowserRouter>
        <div>
          <Route path='/'
                 render={(props) => (
                   <Experiment {...props}
                               atlasUrl={this.props.atlasUrl}
                               suggesterEndpoint={this.props.suggesterEndpoint}
                               experimentAccession={this.props.experimentAccession}
                               referenceDataSourceUrlTemplate={this.props.referenceDataSourceUrlTemplate}
                               clustersData={this.props.clustersData} /> )} />
        </div>
      </BrowserRouter>

    )
  }
}

ExperimentPage.propTypes = {
  atlasUrl: PropTypes.string.isRequired,
  experimentAccession: PropTypes.string.isRequired,
  clustersData: PropTypes.object.isRequired,
  suggesterEndpoint: PropTypes.string,
  referenceDataSourceUrlTemplate: PropTypes.string
}

class Experiment extends Component {

  constructor(props) {
    super(props)
    this.state = {
      params: queryString.parse(props.location.search),
      geneId: "",
      k: 0,
      clusterId: [],
    }
  }

  handleChange(param, item) {
    let _newparam = {}
    _newparam[param] = (param === "k" ? Number.parseInt(item.target.value) : item)
    this.setState(_newparam)

    this.props.history.push("?" + queryString.stringify({
        geneId: (param === "geneId" ? item : this.state.geneId),
        k: (param === "k" ? item.target.value : this.state.k)})
      + "&clusterId=[" + this.state.clusterId + "]"
    )
  }

  componentDidMount() {
    this.setState({
      geneId: this.state.params.geneId,
      k: this.state.params.k !== undefined ? Number.parseInt(this.state.params.k) : this.state.k,
      clusterId: JSON.parse(this.state.params.clusterId || '[]')
    })
  }

  render() {
    return (
      <div className="row">
        <div className="small-6 columns">
          <ReferencePlot clustersData={this.props.clustersData}
                         k={this.state.k}
                         clusterId={this.state.clusterId}
                         handleOptionsChange={this.handleChange.bind(this, "k")} />

        </div>
        <div className="small-6 columns">

          <GeneTSNEPlot atlasUrl={this.props.atlasUrl}
                        clustersData={this.props.clustersData}
                        k={this.state.k}
                        suggesterEndpoint={this.props.suggesterEndpoint}
                        referenceDataSourceUrlTemplate={this.props.referenceDataSourceUrlTemplate}
                        geneId={this.state.geneId}
                        onSelect={this.handleChange.bind(this, "geneId")}
          />

        </div>
      </div>
    )
  }
}

Experiment.propTypes = {
  props: PropTypes.object,
  atlasUrl: PropTypes.string,
  suggesterEndpoint: PropTypes.string,
  referenceDataSourceUrlTemplate: PropTypes.string
}

export default ExperimentPage
