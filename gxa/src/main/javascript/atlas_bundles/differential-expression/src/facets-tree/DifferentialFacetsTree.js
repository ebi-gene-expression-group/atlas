import React from 'react'
import PropTypes from 'prop-types'

import {facetDataPropTypes, facetItemDataPropTypes} from './propTypes'

const DifferentialFacetsTree = ({facets, setChecked}) =>
  <div className={`column row`}>
    <h4>Filter your results</h4>
    { facets.map((facet) => <Facet key={facet.facetName}
                                   facetName={facet.facetName}
                                   facetItems={facet.facetItems}
                                   setChecked={setChecked} />) }
  </div>

DifferentialFacetsTree.propTypes = {
  facets: PropTypes.arrayOf(PropTypes.shape(
    facetDataPropTypes
  )).isRequired,
  setChecked: React.PropTypes.func.isRequired
}


const prettyFacetNames = {
  kingdom: `Kingdom`,
  species: `Species`,
  experimentType: `Experiment type`,
  factors: `Experimental variables`,
  numReplicates: `Number of replicates`,
  regulation: `Regulation`
}
const Facet = ({facetName, facetItems, setChecked}) =>
  <div className={`column row margin-top-large`}>
    <h5>{prettyFacetNames[facetName] || facetName}</h5>
      { facetItems.map((facetItem) =>
          <FacetItem key = {facetItem.name}
                     name = {facetItem.name}
                     value = {facetItem.value}
                     checked = {facetItem.checked}
                     disabled = {facetItem.disabled}
                     setChecked = {(facetItemName, checked) => {setChecked(facetName, facetItemName, checked)}} />)}
  </div>

Facet.propTypes = {
  ...facetDataPropTypes,
  setChecked: React.PropTypes.func.isRequired
}


const FacetItem = ({name, value, checked, disabled, setChecked}) =>
  <div className={`column row`}>
    <input type={`checkbox`} checked={checked} onChange={() => {setChecked(name, !checked)}} disabled={disabled}/>
    <label style={{display: `inline`}}>{value}</label>
  </div>

FacetItem.propTypes = {
  ...facetItemDataPropTypes,
  setChecked: React.PropTypes.func.isRequired,
}


export default DifferentialFacetsTree
