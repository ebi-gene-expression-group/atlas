var expressionAtlasBrowseBySpecies=webpackJsonp_name_([4],{0:/*!**************************************************!*\
  !*** ./atlas_bundles/browse-by-species/index.js ***!
  \**************************************************/
function(e,t,s){"use strict";function r(e){return e&&e.__esModule?e:{default:e}}Object.defineProperty(t,"__esModule",{value:!0}),t.render=void 0;var a=s(/*! ./src/browseBySpeciesRenderer.jsx */2877),i=r(a);t.render=i.default},2877:/*!*************************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/src/browseBySpeciesRenderer.jsx ***!
  \*************************************************************************/
function(e,t,s){"use strict";function r(e){return e&&e.__esModule?e:{default:e}}Object.defineProperty(t,"__esModule",{value:!0}),t.default=function(e){var t=e.atlasUrl,s=e.speciesInfoList,r=e.container;l.default.render(i.default.createElement(o.default,{atlasUrl:t,speciesInfoList:s}),"string"==typeof r?document.getElementById(r):r)};var a=s(/*! react */2),i=r(a),n=s(/*! react-dom */31),l=r(n),u=s(/*! ./BrowseBySpecies.jsx */2878),o=r(u)},2878:/*!*****************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/src/BrowseBySpecies.jsx ***!
  \*****************************************************************/
function(e,t,s){"use strict";function r(e){return e&&e.__esModule?e:{default:e}}Object.defineProperty(t,"__esModule",{value:!0});var a=Object.assign||function(e){for(var t=1;t<arguments.length;t++){var s=arguments[t];for(var r in s)Object.prototype.hasOwnProperty.call(s,r)&&(e[r]=s[r])}return e},i=s(/*! react */2),n=r(i),l=s(/*! ./SpeciesItem.jsx */2879),u=r(l),o=function(e){var t=e.speciesInfoList.map(function(t){return n.default.createElement(u.default,a({key:t.species,atlasUrl:e.atlasUrl},t))});return n.default.createElement("div",{className:"row small-up-2 medium-up-3"},t)};o.propTypes={atlasUrl:n.default.PropTypes.string.isRequired,speciesInfoList:n.default.PropTypes.arrayOf(n.default.PropTypes.shape({species:n.default.PropTypes.string.isRequired,totalExperiments:n.default.PropTypes.number.isRequired,baselineExperiments:n.default.PropTypes.number.isRequired,differentialExperiments:n.default.PropTypes.number.isRequired})).isRequired},t.default=o},2879:/*!*************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/src/SpeciesItem.jsx ***!
  \*************************************************************/
function(e,t,s){"use strict";function r(e){return e&&e.__esModule?e:{default:e}}Object.defineProperty(t,"__esModule",{value:!0});var a=s(/*! react */2),i=r(a),n=s(/*! urijs */2880),l=r(n),u=s(/*! react-ebi-species */2884).Icon,o=function(e){var t=(0,l.default)(e.atlasUrl).segment("experiments").addSearch({organism:e.species}),s=(0,l.default)(t).addSearch({experimentType:"differential"}),r=(0,l.default)(t).addSearch({experimentType:"baseline"}),a=e.species[0].toUpperCase()+e.species.substr(1);return i.default.createElement("div",{className:"column column-block text-center combo"},i.default.createElement("a",{href:t},i.default.createElement("span",{className:"large-species-icon"},i.default.createElement(u,{species:e.species})),i.default.createElement("h5",{className:"species"},a)),i.default.createElement("p",{className:"experiments"},e.totalExperiments," experiments",i.default.createElement("br",null),i.default.createElement("a",{href:r,className:"baseline"},i.default.createElement("span",{"data-tooltip":!0,style:{cursor:"unset",fontWeight:"bold"},className:"baseline tiny button-rd",title:"Baseline experiments"},"B"),e.baselineExperiments),i.default.createElement("a",{href:s,className:"differential padding-left-medium"},i.default.createElement("span",{"data-tooltip":!0,style:{cursor:"unset",fontWeight:"bold"},className:"differential tiny button-rd",title:"Differential experiments"},"D"),e.differentialExperiments)))};o.propTypes={atlasUrl:i.default.PropTypes.string.isRequired,species:i.default.PropTypes.string.isRequired,totalExperiments:i.default.PropTypes.number.isRequired,baselineExperiments:i.default.PropTypes.number.isRequired,differentialExperiments:i.default.PropTypes.number.isRequired},t.default=o},2880:/*!************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/urijs/src/URI.js ***!
  \************************************************************/
[3790,2881,2882,2883,2881,2882,2883],2881:/*!*****************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/urijs/src/punycode.js ***!
  \*****************************************************************/
184,2882:/*!*************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/urijs/src/IPv6.js ***!
  \*************************************************************/
186,2883:/*!***************************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/urijs/src/SecondLevelDomains.js ***!
  \***************************************************************************/
187,2884:/*!**********************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/react-ebi-species/index.js ***!
  \**********************************************************************/
[4166,2885,2890],2885:/*!*********************************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/react-ebi-species/src/SpeciesIcon.jsx ***!
  \*********************************************************************************/
[4167,2886,2889],2886:/*!********************************************************************************************************************************************************!*\
  !*** ./~/style-loader!./atlas_bundles/browse-by-species/~/css-loader!./atlas_bundles/browse-by-species/~/react-ebi-species/src/ebi-visual-species.css ***!
  \********************************************************************************************************************************************************/
function(e,t,s){var r=s(/*! !./../../css-loader!./ebi-visual-species.css */2887);"string"==typeof r&&(r=[[e.id,r,""]]);s(/*! ./../../../../../~/style-loader/addStyles.js */478)(r,{});r.locals&&(e.exports=r.locals)},2887:/*!***************************************************************************************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/css-loader!./atlas_bundles/browse-by-species/~/react-ebi-species/src/ebi-visual-species.css ***!
  \***************************************************************************************************************************************/
function(e,t,s){t=e.exports=s(/*! ../../css-loader/lib/css-base.js */2888)(),t.push([e.id,'@font-face{font-family:EBI-Species;src:url("https://www.ebi.ac.uk/web_guidelines/EBI-Icon-fonts/v1.1/EBI-Species/fonts/EBI-Species.eot");src:url("https://www.ebi.ac.uk/web_guidelines/EBI-Icon-fonts/v1.1/EBI-Species/fonts/EBI-Species.eot?#iefix") format("embedded-opentype"),url("https://www.ebi.ac.uk/web_guidelines/fonts/EBI-Species/fonts/EBI-Species.woff") format("woff"),local("\\263A"),url("https://www.ebi.ac.uk/web_guidelines/fonts/EBI-Species/fonts/EBI-Species.svg#EBI-Species") format("svg"),url("https://www.ebi.ac.uk/web_guidelines/fonts/EBI-Species/fonts/EBI-Species.ttf") format("truetype");font-weight:400;font-style:normal}.react-ebi-species-icon:before{font-family:EBI-Species;color:inherit;content:attr(data-icon)}.react-ebi-species-icon{text-decoration:none;font-style:normal}',""])},2888:/*!**********************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/css-loader/lib/css-base.js ***!
  \**********************************************************************/
477,2889:/*!****************************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/react-ebi-species/src/mapping.js ***!
  \****************************************************************************/
979,2890:/*!*****************************************************************************!*\
  !*** ./atlas_bundles/browse-by-species/~/react-ebi-species/src/renderer.js ***!
  \*****************************************************************************/
[4170,2885]});