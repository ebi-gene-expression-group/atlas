import React from 'react'
import PropTypes from 'prop-types'
import { connect, PromiseState } from 'react-refetch'

import URI from 'urijs'

import DifferentialRouter from './DifferentialRouter'

const transformFacetsResponseToArray = (facetsResponse) => {
  return Object.keys(facetsResponse).map(facetName => {
    return {
      facetName: facetName,
      facetItems: facetsResponse[facetName].map(facetItem => {
        return {
          name: facetItem.name,
          value: facetItem.value,
          disabled: false,
          checked: false
        }
      })
    }
  })
}

const pruneFacetsTreeBasedOnResultsThatCameIn = (facetsTreeData, results) => {
  return facetsTreeData.map(facet => (
    {
      facetName: facet.facetName,
      facetItems:
        facet.facetItems.filter(facetItem =>
          results.some(result => {
            if (Array.isArray(result[facet.facetName])) {
              return result[facet.facetName].includes(facetItem.name)
            } else {
              return result[facet.facetName] === facetItem.name
            }
          })
        )
    }
  )).filter(facet => facet.facetItems.length > 0)
}

class DifferentialRouterLoader extends React.Component {
  constructor(props) {
    super(props)
  }

  render () {
    const { facetsFetch, resultsFetch } = this.props
    const allFetches = PromiseState.all([facetsFetch, resultsFetch])

    if (allFetches.pending) {
      return (
        <div className={`row column`}>
          <img src={URI(`resources/images/loading.gif`, this.props.atlasUrl).toString()} />
        </div>
      )
    }
    else if (allFetches.fulfilled) {
      const resultsResponse = resultsFetch.value
      const facetsResponse = facetsFetch.value

      const facetsTreeData = transformFacetsResponseToArray(facetsResponse)
      const prunedFacetsTreeData = pruneFacetsTreeBasedOnResultsThatCameIn(facetsTreeData, resultsResponse.results)
      const results = resultsResponse.results
      const legend = {
        minDownLevel: resultsResponse.minDownLevel,
        minUpLevel: resultsResponse.minUpLevel,
        maxDownLevel:resultsResponse.maxDownLevel,
        maxUpLevel: resultsResponse.maxUpLevel
      }

      return (
        <DifferentialRouter facetsTreeData={prunedFacetsTreeData}
                            results={results}
                            legend={legend}
                            atlasUrl={this.props.atlasUrl} />
      )
    }
  }
}

DifferentialRouterLoader.propTypes = {
  atlasUrl: PropTypes.string.isRequired,
  geneQuery: PropTypes.string.isRequired,
  conditionQuery : PropTypes.string.isRequired,
  species: PropTypes.string.isRequired
}

export default connect((props) => {
  const requestParams = {geneQuery: props.geneQuery, conditionQuery: props.conditionQuery, species: props.species}
  return {
    facetsFetch: URI(`json/search/differential_facets`, props.atlasUrl).search(requestParams).toString(),
    resultsFetch: URI(`json/search/differential_results`, props.atlasUrl).search(requestParams).toString()
  }
})(DifferentialRouterLoader)

