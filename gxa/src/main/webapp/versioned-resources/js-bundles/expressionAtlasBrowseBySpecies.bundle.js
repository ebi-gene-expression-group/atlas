var expressionAtlasBrowseBySpecies=webpackJsonp_name_([4],{0:/*!**************************************************!*\
  !*** ./atlas_bundles/browse-by-species/index.js ***!
  \**************************************************/
function(e,t,s){"use strict";function r(e){return e&&e.__esModule?e:{default:e}}Object.defineProperty(t,"__esModule",{value:!0}),t.render=void 0;var a=s(/*! ./src/browseBySpeciesRenderer.jsx */3010),n=r(a);t.render=n.default},3010:/*!*************************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/src/browseBySpeciesRenderer.jsx ***!
  \*************************************************************************/
function(e,t,s){"use strict";function r(e){return e&&e.__esModule?e:{default:e}}Object.defineProperty(t,"__esModule",{value:!0}),t.default=function(e){var t=e.atlasUrl,s=e.speciesInfoList,r=e.container;l.default.render(n.default.createElement(o.default,{atlasUrl:t,speciesInfoList:s}),"string"==typeof r?document.getElementById(r):r)};var a=s(/*! react */2),n=r(a),i=s(/*! react-dom */31),l=r(i),u=s(/*! ./BrowseBySpecies.jsx */3011),o=r(u)},3011:/*!*****************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/src/BrowseBySpecies.jsx ***!
  \*****************************************************************/
function(e,t,s){"use strict";function r(e){return e&&e.__esModule?e:{default:e}}Object.defineProperty(t,"__esModule",{value:!0});var a=Object.assign||function(e){for(var t=1;t<arguments.length;t++){var s=arguments[t];for(var r in s)Object.prototype.hasOwnProperty.call(s,r)&&(e[r]=s[r])}return e},n=s(/*! react */2),i=r(n),l=s(/*! ./SpeciesItem.jsx */3012),u=r(l),o=function(e){var t=e.speciesInfoList.map(function(t){return i.default.createElement(u.default,a({key:t.species,atlasUrl:e.atlasUrl},t))});return i.default.createElement("div",{className:"row small-up-2 medium-up-3"},t)};o.propTypes={atlasUrl:i.default.PropTypes.string.isRequired,speciesInfoList:i.default.PropTypes.arrayOf(i.default.PropTypes.shape({species:i.default.PropTypes.string.isRequired,totalExperiments:i.default.PropTypes.number.isRequired,baselineExperiments:i.default.PropTypes.number.isRequired,differentialExperiments:i.default.PropTypes.number.isRequired})).isRequired},t.default=o},3012:/*!*************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/src/SpeciesItem.jsx ***!
  \*************************************************************/
function(e,t,s){"use strict";function r(e){return e&&e.__esModule?e:{default:e}}Object.defineProperty(t,"__esModule",{value:!0});var a=s(/*! react */2),n=r(a),i=s(/*! urijs */3013),l=r(i),u=s(/*! react-ebi-species */3017),o=r(u),f=function(e){var t=(0,l.default)(e.atlasUrl).segment("experiments").addSearch({organism:e.species}),s=(0,l.default)(t).addSearch({experimentType:"differential"}),r=(0,l.default)(t).addSearch({experimentType:"baseline"}),a=e.species[0].toUpperCase()+e.species.substr(1);return n.default.createElement("div",{className:"column column-block text-center combo"},n.default.createElement("a",{href:t},n.default.createElement("span",{className:"large-species-icon"},n.default.createElement(o.default,{species:e.species})),n.default.createElement("h5",{className:"species"},a)),n.default.createElement("p",{className:"experiments"},e.totalExperiments," experiments",n.default.createElement("br",null),n.default.createElement("a",{href:r,className:"baseline"},n.default.createElement("span",{"data-tooltip":!0,style:{cursor:"unset",fontWeight:"bold"},className:"baseline tiny button-rd",title:"Baseline experiments"},"B"),e.baselineExperiments),n.default.createElement("a",{href:s,className:"differential padding-left-medium"},n.default.createElement("span",{"data-tooltip":!0,style:{cursor:"unset",fontWeight:"bold"},className:"differential tiny button-rd",title:"Differential experiments"},"D"),e.differentialExperiments)))};f.propTypes={atlasUrl:n.default.PropTypes.string.isRequired,species:n.default.PropTypes.string.isRequired,totalExperiments:n.default.PropTypes.number.isRequired,baselineExperiments:n.default.PropTypes.number.isRequired,differentialExperiments:n.default.PropTypes.number.isRequired},t.default=f},3013:/*!************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/urijs/src/URI.js ***!
  \************************************************************/
[3923,3014,3015,3016,3014,3015,3016],3014:/*!*****************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/urijs/src/punycode.js ***!
  \*****************************************************************/
183,3015:/*!*************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/urijs/src/IPv6.js ***!
  \*************************************************************/
185,3016:/*!***************************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/urijs/src/SecondLevelDomains.js ***!
  \***************************************************************************/
186,3017:/*!**************************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/react-ebi-species/lib/index.js ***!
  \**************************************************************************/
[4311,3018],3018:/*!***********************************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/react-ebi-species/src/EbiSpeciesIcon.js ***!
  \***********************************************************************************/
[4312,3019,3024,3027],3019:/*!***********************************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/react-ebi-species/~/prop-types/index.js ***!
  \***********************************************************************************/
[3921,3020],3020:/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/react-ebi-species/~/prop-types/factoryWithThrowingShims.js ***!
  \******************************************************************************************************/
[3922,3021,3022,3023],3021:/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/react-ebi-species/~/prop-types/~/fbjs/lib/emptyFunction.js ***!
  \******************************************************************************************************/
12,3022:/*!**************************************************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/react-ebi-species/~/prop-types/~/fbjs/lib/invariant.js ***!
  \**************************************************************************************************/
8,3023:/*!******************************************************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/react-ebi-species/~/prop-types/lib/ReactPropTypesSecret.js ***!
  \******************************************************************************************************/
181,3024:/*!****************************************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/react-ebi-species/src/ebi-visual-species.css ***!
  \****************************************************************************************/
function(e,t,s){var r=s(/*! !./../../css-loader!./ebi-visual-species.css */3025);"string"==typeof r&&(r=[[e.id,r,""]]);s(/*! ./../../../../../~/style-loader/addStyles.js */481)(r,{});r.locals&&(e.exports=r.locals)},3025:/*!***************************************************************************************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/css-loader!./atlas_bundles/browse-by-species/~/react-ebi-species/src/ebi-visual-species.css ***!
  \***************************************************************************************************************************************/
function(e,t,s){t=e.exports=s(/*! ../../css-loader/lib/css-base.js */3026)(),t.push([e.id,'@font-face{font-family:EBI-Species;src:url("https://www.ebi.ac.uk/web_guidelines/EBI-Icon-fonts/v1.2/EBI-Species/fonts/EBI-Species.eot");src:url("https://www.ebi.ac.uk/web_guidelines/EBI-Icon-fonts/v1.2/EBI-Species/fonts/EBI-Species.eot?#iefix") format("embedded-opentype"),url("https://www.ebi.ac.uk/web_guidelines/EBI-Icon-fonts/v1.2/EBI-Species/fonts/EBI-Species.woff") format("woff"),url("https://www.ebi.ac.uk/web_guidelines/EBI-Icon-fonts/v1.2/EBI-Species/fonts/EBI-Species.svg#EBI-Species") format("svg"),url("https://www.ebi.ac.uk/web_guidelines/EBI-Icon-fonts/v1.2/EBI-Species/fonts/EBI-Species.ttf") format("truetype");font-weight:400;font-style:normal}.react-ebi-species-icon:before{font-family:EBI-Species;color:inherit;content:attr(data-icon)}.react-ebi-species-icon{text-decoration:none;font-style:normal}',""])},3026:/*!**********************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/css-loader/lib/css-base.js ***!
  \**********************************************************************/
480,3027:/*!****************************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/react-ebi-species/src/mapping.js ***!
  \****************************************************************************/
1082});