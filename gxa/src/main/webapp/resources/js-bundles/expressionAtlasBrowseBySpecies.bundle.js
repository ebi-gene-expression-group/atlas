var expressionAtlasBrowseBySpecies=webpackJsonp_name_([4],{0:/*!**************************************************!*\
  !*** ./atlas_bundles/browse-by-species/index.js ***!
  \**************************************************/
function(e,t,r){"use strict";function a(e){return e&&e.__esModule?e:{default:e}}Object.defineProperty(t,"__esModule",{value:!0}),t.render=void 0;var s=r(/*! ./src/browseBySpeciesRenderer.jsx */2735),n=a(s);t.render=n.default},2735:/*!*************************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/src/browseBySpeciesRenderer.jsx ***!
  \*************************************************************************/
function(e,t,r){"use strict";function a(e){return e&&e.__esModule?e:{default:e}}Object.defineProperty(t,"__esModule",{value:!0}),t.default=function(e){var t=e.atlasUrl,r=e.speciesInfoList,a=e.container;i.default.render(n.default.createElement(d.default,{atlasUrl:t,speciesInfoList:r}),"string"==typeof a?document.getElementById(a):a)};var s=r(/*! react */299),n=a(s),l=r(/*! react-dom */328),i=a(l),u=r(/*! ./BrowseBySpecies.jsx */2736),d=a(u)},2736:/*!*****************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/src/BrowseBySpecies.jsx ***!
  \*****************************************************************/
function(e,t,r){"use strict";function a(e){return e&&e.__esModule?e:{default:e}}Object.defineProperty(t,"__esModule",{value:!0});var s=Object.assign||function(e){for(var t=1;t<arguments.length;t++){var r=arguments[t];for(var a in r)Object.prototype.hasOwnProperty.call(r,a)&&(e[a]=r[a])}return e},n=r(/*! react */299),l=a(n),i=r(/*! ./SpeciesItem.jsx */2737),u=a(i),d=function(e){var t=e.speciesInfoList.map(function(t){return l.default.createElement(u.default,s({key:t.species,atlasUrl:e.atlasUrl},t))});return l.default.createElement("div",{className:"row small-up-2 medium-up-3"},t)};d.propTypes={atlasUrl:l.default.PropTypes.string.isRequired,speciesInfoList:l.default.PropTypes.arrayOf(l.default.PropTypes.shape({species:l.default.PropTypes.string.isRequired,totalExperiments:l.default.PropTypes.number.isRequired,baselineExperiments:l.default.PropTypes.number.isRequired,differentialExperiments:l.default.PropTypes.number.isRequired})).isRequired},t.default=d},2737:/*!*************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/src/SpeciesItem.jsx ***!
  \*************************************************************/
function(e,t,r){"use strict";function a(e){return e&&e.__esModule?e:{default:e}}Object.defineProperty(t,"__esModule",{value:!0});var s=r(/*! react */299),n=a(s),l=r(/*! urijs */2738),i=a(l),u=r(/*! react-ebi-species */2742).Icon,d=function(e){var t=(0,i.default)(e.atlasUrl).segment("experiments").addSearch({organism:e.species}),r=(0,i.default)(t).addSearch({experimentType:"differential"}),a=(0,i.default)(t).addSearch({experimentType:"baseline"}),s=e.species[0].toUpperCase()+e.species.substr(1);return n.default.createElement("div",{className:"column column-block text-center combo"},n.default.createElement("a",{href:t},n.default.createElement("span",{className:"large-species-icon"},n.default.createElement(u,{species:e.species})),n.default.createElement("h5",{className:"species"},s)),n.default.createElement("p",{className:"experiments"},e.totalExperiments," experiments",n.default.createElement("br",null),n.default.createElement("a",{href:a,className:"baseline"},n.default.createElement("span",{"data-tooltip":!0,style:{cursor:"unset",fontWeight:"bold"},className:"baseline tiny button-rd",title:"Baseline experiments"},"B"),e.baselineExperiments),n.default.createElement("a",{href:r,className:"differential padding-left-medium"},n.default.createElement("span",{"data-tooltip":!0,style:{cursor:"unset",fontWeight:"bold"},className:"differential tiny button-rd",title:"Differential experiments"},"D"),e.differentialExperiments)))};d.propTypes={atlasUrl:n.default.PropTypes.string.isRequired,species:n.default.PropTypes.string.isRequired,totalExperiments:n.default.PropTypes.number.isRequired,baselineExperiments:n.default.PropTypes.number.isRequired,differentialExperiments:n.default.PropTypes.number.isRequired},t.default=d},2738:/*!************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/urijs/src/URI.js ***!
  \************************************************************/
[3610,2739,2740,2741,2739,2740,2741],2739:/*!*****************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/urijs/src/punycode.js ***!
  \*****************************************************************/
475,2740:/*!*************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/urijs/src/IPv6.js ***!
  \*************************************************************/
477,2741:/*!***************************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/urijs/src/SecondLevelDomains.js ***!
  \***************************************************************************/
478,2742:/*!**********************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/react-ebi-species/index.js ***!
  \**********************************************************************/
[3954,2743,2748],2743:/*!*********************************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/react-ebi-species/src/SpeciesIcon.jsx ***!
  \*********************************************************************************/
[3955,2744,2747],2744:/*!********************************************************************************************************************************************************!*\
  !*** ./~/style-loader!./atlas_bundles/browse-by-species/~/css-loader!./atlas_bundles/browse-by-species/~/react-ebi-species/src/ebi-visual-species.css ***!
  \********************************************************************************************************************************************************/
function(e,t,r){var a=r(/*! !../../css-loader!./ebi-visual-species.css */2745);"string"==typeof a&&(a=[[e.id,a,""]]);r(/*! ../../../../../~/style-loader/addStyles.js */769)(a,{});a.locals&&(e.exports=a.locals)},2745:/*!***************************************************************************************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/css-loader!./atlas_bundles/browse-by-species/~/react-ebi-species/src/ebi-visual-species.css ***!
  \***************************************************************************************************************************************/
[3957,2746],2746:/*!**********************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/css-loader/lib/css-base.js ***!
  \**********************************************************************/
768,2747:/*!****************************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/react-ebi-species/src/mapping.js ***!
  \****************************************************************************/
1171,2748:/*!*****************************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/react-ebi-species/src/renderer.js ***!
  \*****************************************************************************/
[3958,2743]});