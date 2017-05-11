var expressionAtlasBrowseBySpecies=webpackJsonp_name_([4],{0:/*!**************************************************!*\
  !*** ./atlas_bundles/browse-by-species/index.js ***!
  \**************************************************/
function(e,t,a){"use strict";function r(e){return e&&e.__esModule?e:{default:e}}Object.defineProperty(t,"__esModule",{value:!0}),t.render=void 0;var s=a(/*! ./src/browseBySpeciesRenderer.jsx */2702),l=r(s);t.render=l.default},2702:/*!*************************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/src/browseBySpeciesRenderer.jsx ***!
  \*************************************************************************/
function(e,t,a){"use strict";function r(e){return e&&e.__esModule?e:{default:e}}Object.defineProperty(t,"__esModule",{value:!0}),t.default=function(e){var t=e.atlasUrl,a=e.speciesInfoList,r=e.container;i.default.render(l.default.createElement(p.default,{atlasUrl:t,speciesInfoList:a}),"string"==typeof r?document.getElementById(r):r)};var s=a(/*! react */299),l=r(s),n=a(/*! react-dom */328),i=r(n),u=a(/*! ./BrowseBySpecies.jsx */2703),p=r(u)},2703:/*!*****************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/src/BrowseBySpecies.jsx ***!
  \*****************************************************************/
function(e,t,a){"use strict";function r(e){return e&&e.__esModule?e:{default:e}}Object.defineProperty(t,"__esModule",{value:!0});var s=Object.assign||function(e){for(var t=1;t<arguments.length;t++){var a=arguments[t];for(var r in a)Object.prototype.hasOwnProperty.call(a,r)&&(e[r]=a[r])}return e},l=a(/*! react */299),n=r(l),i=a(/*! ./SpeciesItem.jsx */2704),u=r(i),p=function(e){var t=e.speciesInfoList.map(function(t){return n.default.createElement(u.default,s({key:t.species,atlasUrl:e.atlasUrl},t))});return n.default.createElement("div",{className:"row small-up-2 medium-up-3"},t)};p.propTypes={atlasUrl:n.default.PropTypes.string.isRequired,speciesInfoList:n.default.PropTypes.arrayOf(n.default.PropTypes.shape({species:n.default.PropTypes.string.isRequired,totalExperiments:n.default.PropTypes.number.isRequired,baselineExperiments:n.default.PropTypes.number.isRequired,differentialExperiments:n.default.PropTypes.number.isRequired})).isRequired},t.default=p},2704:/*!*************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/src/SpeciesItem.jsx ***!
  \*************************************************************/
function(e,t,a){"use strict";function r(e){return e&&e.__esModule?e:{default:e}}Object.defineProperty(t,"__esModule",{value:!0});var s=a(/*! react */299),l=r(s),n=a(/*! urijs */2705),i=r(n),u=a(/*! react-ebi-species */2709).Icon,p=function(e){var t=(0,i.default)(e.atlasUrl).segment("experiments").addSearch({organism:e.species}),a=(0,i.default)(t).addSearch({experimentType:"differential"}),r=(0,i.default)(t).addSearch({experimentType:"baseline"}),s=e.species[0].toUpperCase()+e.species.substr(1);return l.default.createElement("div",{className:"column column-block text-center combo"},l.default.createElement("a",{href:t},l.default.createElement("span",{className:"large-species-icon"},l.default.createElement(u,{species:e.species})),l.default.createElement("h5",{className:"species"},s)),l.default.createElement("p",{className:"experiments"},e.totalExperiments," experiments",l.default.createElement("br",null),l.default.createElement("a",{href:r,className:"baseline"},l.default.createElement("span",{"data-tooltip":!0,"aria-haspopup":"false",className:"baseline tiny button-rd",title:"Baseline experiments"},"B"),e.baselineExperiments),l.default.createElement("a",{href:a,className:"differential padding-left-medium"},l.default.createElement("span",{"data-tooltip":!0,"aria-haspopup":"false",className:"differential tiny button-rd",title:"Differential experiments"},"D"),e.differentialExperiments)))};p.propTypes={atlasUrl:l.default.PropTypes.string.isRequired,species:l.default.PropTypes.string.isRequired,totalExperiments:l.default.PropTypes.number.isRequired,baselineExperiments:l.default.PropTypes.number.isRequired,differentialExperiments:l.default.PropTypes.number.isRequired},t.default=p},2705:/*!************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/urijs/src/URI.js ***!
  \************************************************************/
[3552,2706,2707,2708,2706,2707,2708],2706:/*!*****************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/urijs/src/punycode.js ***!
  \*****************************************************************/
462,2707:/*!*************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/urijs/src/IPv6.js ***!
  \*************************************************************/
464,2708:/*!***************************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/urijs/src/SecondLevelDomains.js ***!
  \***************************************************************************/
465,2709:/*!**********************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/react-ebi-species/index.js ***!
  \**********************************************************************/
[3899,2710,2715],2710:/*!*********************************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/react-ebi-species/src/SpeciesIcon.jsx ***!
  \*********************************************************************************/
[3900,2711,2714],2711:/*!********************************************************************************************************************************************************!*\
  !*** ./~/style-loader!./atlas_bundles/browse-by-species/~/css-loader!./atlas_bundles/browse-by-species/~/react-ebi-species/src/ebi-visual-species.css ***!
  \********************************************************************************************************************************************************/
function(e,t,a){var r=a(/*! !../../css-loader!./ebi-visual-species.css */2712);"string"==typeof r&&(r=[[e.id,r,""]]);a(/*! ../../../../../~/style-loader/addStyles.js */756)(r,{});r.locals&&(e.exports=r.locals)},2712:/*!***************************************************************************************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/css-loader!./atlas_bundles/browse-by-species/~/react-ebi-species/src/ebi-visual-species.css ***!
  \***************************************************************************************************************************************/
[3902,2713],2713:/*!**********************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/css-loader/lib/css-base.js ***!
  \**********************************************************************/
755,2714:/*!****************************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/react-ebi-species/src/mapping.js ***!
  \****************************************************************************/
1162,2715:/*!*****************************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/react-ebi-species/src/renderer.js ***!
  \*****************************************************************************/
[3903,2710]});