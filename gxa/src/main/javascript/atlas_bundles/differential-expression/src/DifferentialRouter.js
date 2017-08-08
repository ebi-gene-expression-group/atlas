import React from 'react'
import PropTypes from 'prop-types'

import Results from './DifferentialResults'
import Facets from './facets-tree/DifferentialFacetsTree'
import UrlManager from './urlManager'

/*
 TODO if Solr queries get fast enough that we can:
 - split the two requests, so that the facets load first, initial results load second
 - a request to the server is done for every interaction with the facets tree
 - add counts to each facet and disable check boxes if count is 0
*/

const equalsToOrIncludes = (obj, value) => {
  if (!!obj) {
    if (obj.constructor === Array) {
      return obj.includes(value)
    }
    else {
      return obj === value
    }
  } else {
    return false
  }
}

const addElementToObjectOfArrays = (obj, arrayName, element) => {
  if (!obj[arrayName]) {
    obj[arrayName] = []
  }
  obj[arrayName].push(element)
}

const removeElementFromObjectOfArrays = (obj, arrayName, element) => {
  delete obj[arrayName].splice(obj[arrayName].indexOf(element), 1)
  if (obj[arrayName].length === 0) {
    delete obj[arrayName]
  }
}

const resultMatchesQuery = (result, query) => {
  if (Object.keys(query).length === 0) {
    return false
  } else {
    return Object.keys(query).every(facetName =>
      query[facetName].some(facetItem =>
        equalsToOrIncludes(result[facetName], facetItem)
      )
    )
  }
}

class DifferentialRouter extends React.Component {
  constructor(props) {
    super(props)

    const querySelect = UrlManager.parseDifferentialUrlParameter()
    if (!querySelect.kingdom) {
      querySelect.kingdom =
        props.facetsTreeData
          .find((facet) => facet.facetName === `kingdom`).facetItems
            .map((facetItem) => facetItem.name)
    }
    UrlManager.differentialPush(querySelect, true)

    this.state = {
      querySelect: querySelect
    }

    this._setChecked = this._setChecked.bind(this)
  }

  componentDidMount () {
    window.addEventListener(
      'popstate',
      () => { this.setState({querySelect: UrlManager.parseDifferentialUrlParameter()}) },
      false)
  }

  _setChecked (facetName, facetItemName, checked) {
    // Update URL
    const newQuerySelect = JSON.parse(JSON.stringify(this.state.querySelect))
    if (checked) {
      addElementToObjectOfArrays(newQuerySelect, facetName, facetItemName)
    } else {
      removeElementFromObjectOfArrays(newQuerySelect, facetName, facetItemName)
    }

    // TODO Consider using https://github.com/reactjs/react-router
    UrlManager.differentialPush(newQuerySelect, false)
    this.setState({
        querySelect: newQuerySelect
    })
  }

  _filteredResults (query = this.state.querySelect) {
    return this.props.results.filter(result =>
      resultMatchesQuery(result, query)
    )
  }

  // Syncs tree data with URL (querySelect) and does some other smart things such as check/uncheck or disable facets based on
  // the user results (e.g. check & disable a facet if it’s shared by all results as a side effect of other choice)
  _prepareFacetTreeData(filteredResults) {
    return this.props.facetsTreeData.map((facet) => ({
      facetName: facet.facetName,
      facetItems: facet.facetItems.map((facetItem) => {
        const querySelectAfterSwitchingThisFacetItem = JSON.parse(JSON.stringify(this.state.querySelect))

        if (equalsToOrIncludes(querySelectAfterSwitchingThisFacetItem[facet.facetName], facetItem.name)) {
          removeElementFromObjectOfArrays(querySelectAfterSwitchingThisFacetItem, facet.facetName, facetItem.name)
        } else {
          addElementToObjectOfArrays(querySelectAfterSwitchingThisFacetItem, facet.facetName, facetItem.name)
        }

        const resultIdsAfterSwitchingThisFacetItem = this._filteredResults(querySelectAfterSwitchingThisFacetItem).map((result) => result.id).sort();
        const currentResultIds = filteredResults.map((result) => result.id).sort()

        const sameResultsAfterSwitchingThisItem = JSON.stringify(resultIdsAfterSwitchingThisFacetItem) === JSON.stringify(currentResultIds)
        const noResultsAfterSwitchingThisItem = resultIdsAfterSwitchingThisFacetItem.length === 0

        return {
          name: facetItem.name,
          value: facetItem.value,
          checked: equalsToOrIncludes(this.state.querySelect[facet.facetName], facetItem.name) || sameResultsAfterSwitchingThisItem,
          disabled: noResultsAfterSwitchingThisItem || sameResultsAfterSwitchingThisItem
        }
      })
    }))
  }

  render () {
    const filteredResults = this._filteredResults()

    return (
      <div className={`row column expanded`}>
        <div className={`show-for-large large-3 columns`}>
          { Object.keys(this.props.facetsTreeData).length &&
            <Facets facets = {this._prepareFacetTreeData(filteredResults)}
                    setChecked = {this._setChecked} /> }
        </div>
        <div className={`small-12 large-9 columns`}>
          { this.props.results && this.props.results.length &&
            <Results results = {filteredResults}
                     atlasUrl = {this.props.atlasUrl}
                     {...this.props.legend} /> }
        </div>
      </div>
    )
  }
}

DifferentialRouter.propTypes = {
  facetsTreeData: PropTypes.array,
  results: PropTypes.array,
  legend: PropTypes.object,
  atlasUrl: PropTypes.string.isRequired,
}

export default DifferentialRouter