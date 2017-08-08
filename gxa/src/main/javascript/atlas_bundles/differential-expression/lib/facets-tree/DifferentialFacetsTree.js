'use strict';

Object.defineProperty(exports, "__esModule", {
  value: true
});

var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };

var _react = require('react');

var _react2 = _interopRequireDefault(_react);

var _propTypes = require('prop-types');

var _propTypes2 = _interopRequireDefault(_propTypes);

var _propTypes3 = require('./propTypes');

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

var DifferentialFacetsTree = function DifferentialFacetsTree(_ref) {
  var facets = _ref.facets,
      setChecked = _ref.setChecked;
  return _react2.default.createElement(
    'div',
    { className: 'column row' },
    _react2.default.createElement(
      'h4',
      null,
      'Filter your results'
    ),
    facets.map(function (facet) {
      return _react2.default.createElement(Facet, { key: facet.facetName,
        facetName: facet.facetName,
        facetItems: facet.facetItems,
        setChecked: setChecked });
    })
  );
};

DifferentialFacetsTree.propTypes = {
  facets: _propTypes2.default.arrayOf(_propTypes2.default.shape(_propTypes3.facetDataPropTypes)).isRequired,
  setChecked: _react2.default.PropTypes.func.isRequired
};

var prettyFacetNames = {
  kingdom: 'Kingdom',
  species: 'Species',
  experimentType: 'Experiment type',
  factors: 'Experimental variables',
  numReplicates: 'Number of replicates',
  regulation: 'Regulation'
};
var Facet = function Facet(_ref2) {
  var facetName = _ref2.facetName,
      facetItems = _ref2.facetItems,
      _setChecked = _ref2.setChecked;
  return _react2.default.createElement(
    'div',
    { className: 'column row margin-top-large' },
    _react2.default.createElement(
      'h5',
      null,
      prettyFacetNames[facetName] || facetName
    ),
    facetItems.map(function (facetItem) {
      return _react2.default.createElement(FacetItem, { key: facetItem.name,
        name: facetItem.name,
        value: facetItem.value,
        checked: facetItem.checked,
        disabled: facetItem.disabled,
        setChecked: function setChecked(facetItemName, checked) {
          _setChecked(facetName, facetItemName, checked);
        } });
    })
  );
};

Facet.propTypes = _extends({}, _propTypes3.facetDataPropTypes, {
  setChecked: _react2.default.PropTypes.func.isRequired
});

var FacetItem = function FacetItem(_ref3) {
  var name = _ref3.name,
      value = _ref3.value,
      checked = _ref3.checked,
      disabled = _ref3.disabled,
      setChecked = _ref3.setChecked;
  return _react2.default.createElement(
    'div',
    { className: 'column row' },
    _react2.default.createElement('input', { type: 'checkbox', checked: checked, onChange: function onChange() {
        setChecked(name, !checked);
      }, disabled: disabled }),
    _react2.default.createElement(
      'label',
      { style: { display: 'inline' } },
      value
    )
  );
};

FacetItem.propTypes = _extends({}, _propTypes3.facetItemDataPropTypes, {
  setChecked: _react2.default.PropTypes.func.isRequired
});

exports.default = DifferentialFacetsTree;