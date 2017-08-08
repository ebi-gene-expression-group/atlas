import PropTypes from 'prop-types'

const facetItemDataPropTypes = {
  name: PropTypes.string.isRequired,
  value: PropTypes.string.isRequired,
  checked: PropTypes.bool.isRequired,
  disabled: PropTypes.bool.isRequired,
}

const facetDataPropTypes = {
  facetName: PropTypes.string.isRequired,
  facetItems: PropTypes.arrayOf(PropTypes.shape(
    facetItemDataPropTypes
  )).isRequired
}

export {facetDataPropTypes, facetItemDataPropTypes}