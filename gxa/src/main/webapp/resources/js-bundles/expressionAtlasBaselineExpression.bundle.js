var expressionAtlasBaselineExpression=(window.webpackJsonp_name_=window.webpackJsonp_name_||[]).push([[2],{1029:function(e,t,a){"use strict";a.r(t);var r=a(1),n=a.n(r),o=a(10),s=a.n(o),i=a(0),c=a.n(i),u=function(e){return n.a.createElement("div",null,n.a.createElement("input",{type:"checkbox",checked:e.checked,onChange:function(){return e.setChecked(e.name,!e.checked)}}),n.a.createElement("label",null,e.value))};u.propTypes={name:c.a.string.isRequired,value:c.a.string.isRequired,checked:c.a.bool.isRequired,setChecked:c.a.func.isRequired};var l=u,m=function(e){var t,a=e.facetItems.map(function(t){return n.a.createElement(l,{key:"".concat(e.facetName,"_").concat(t.name),name:t.name,value:t.value,checked:t.checked,setChecked:function(t,a){e.setChecked(e.facetName,t,a)}})});return n.a.createElement("div",{className:"margin-top-large"},n.a.createElement("h5",null,(t=e.facetName).charAt(0).toUpperCase()+t.slice(1).toLowerCase()),a)};m.propTypes={facetName:c.a.string.isRequired,facetItems:c.a.arrayOf(c.a.shape({name:c.a.string.isRequired,value:c.a.string.isRequired,checked:c.a.bool.isRequired})).isRequired,setChecked:c.a.func.isRequired};var p=m,f=function(e){var t=e.facets.map(function(t){return n.a.createElement(p,{key:t.facetName,facetName:t.facetName,facetItems:t.facetItems,setChecked:e.setChecked})});return n.a.createElement("div",null,n.a.createElement("input",{type:"checkbox",checked:e.showAnatomograms,onChange:e.toggleAnatomograms,disabled:e.disableAnatomogramsCheckbox}),n.a.createElement("label",null,"Show anatomograms"),n.a.createElement("h4",null,"Filter your results"),t)};f.propTypes={facets:c.a.arrayOf(c.a.shape({facetName:c.a.string.isRequired,facetItems:c.a.arrayOf(c.a.shape({name:c.a.string.isRequired,value:c.a.string.isRequired,checked:c.a.bool.isRequired})).isRequired})).isRequired,setChecked:c.a.func.isRequired,showAnatomograms:c.a.bool.isRequired,toggleAnatomograms:c.a.func.isRequired,disableAnatomogramsCheckbox:c.a.bool.isRequired};var d=f,h=a(94),g=a.n(h),y=function(e){return n.a.createElement("div",{className:"row column expanded margin-top-large margin-bottom-xlarge"},n.a.createElement("h5",null,(e.showHeatmapLabel?"".concat((t=e.species).charAt(0).toUpperCase()+t.slice(1).toLowerCase()," — "):"")+e.factor.value),n.a.createElement(g.a,{atlasUrl:e.atlasUrl,query:{gene:e.geneQuery,condition:e.conditionQuery,species:e.species,source:e.factor.name},isWidget:!1,showAnatomogram:e.showAnatomogram}));var t};y.propTypes={atlasUrl:c.a.string.isRequired,geneQuery:c.a.string.isRequired,conditionQuery:c.a.string.isRequired,species:c.a.string.isRequired,factor:c.a.shape({name:c.a.string.isRequired,value:c.a.string.isRequired}).isRequired,showAnatomogram:c.a.bool.isRequired,showHeatmapLabel:c.a.bool.isRequired};var v=y;function b(e){return(b="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(e){return typeof e}:function(e){return e&&"function"==typeof Symbol&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":typeof e})(e)}function _(e,t){for(var a=0;a<t.length;a++){var r=t[a];r.enumerable=r.enumerable||!1,r.configurable=!0,"value"in r&&(r.writable=!0),Object.defineProperty(e,r.key,r)}}function w(e,t){return!t||"object"!==b(t)&&"function"!=typeof t?function(e){if(void 0===e)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return e}(e):t}function O(e){return(O=Object.setPrototypeOf?Object.getPrototypeOf:function(e){return e.__proto__||Object.getPrototypeOf(e)})(e)}function k(e,t){return(k=Object.setPrototypeOf||function(e,t){return e.__proto__=t,e})(e,t)}var q=function(e){function t(){return function(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}(this,t),w(this,O(t).apply(this,arguments))}var a,r,o;return function(e,t){if("function"!=typeof t&&null!==t)throw new TypeError("Super expression must either be null or a function");e.prototype=Object.create(t&&t.prototype,{constructor:{value:e,writable:!0,configurable:!0}}),t&&k(e,t)}(t,n.a.Component),a=t,(r=[{key:"render",value:function(){var e=this;return n.a.createElement("div",null,this.props.heatmaps.map(function(t){return n.a.createElement(v,{key:"".concat(t.species,"_").concat(t.factor.name),showAnatomogram:e.props.showAnatomograms,showHeatmapLabel:e._hasMoreThanOneSpecies(),species:t.species,factor:t.factor,atlasUrl:e.props.atlasUrl,geneQuery:e.props.geneQuery,conditionQuery:e.props.conditionQuery})}))}},{key:"_hasMoreThanOneSpecies",value:function(){var e=new Set;return this.props.heatmaps.forEach(function(t){e.add(t.species)}),e.size>1}}])&&_(a.prototype,r),o&&_(a,o),t}();q.propTypes={atlasUrl:c.a.string.isRequired,geneQuery:c.a.string.isRequired,conditionQuery:c.a.string,showAnatomograms:c.a.bool.isRequired,heatmaps:c.a.arrayOf(c.a.shape({species:c.a.string.isRequired,factor:c.a.shape({name:c.a.string.isRequired,value:c.a.string.isRequired})})).isRequired};var E=q,R=a(35),S=a.n(R),T=a(57),A=a.n(T),j={baselinePush:function(e,t){var a=S.a.parse(window.location.toString()),r=A.a.parse(a.query);r.bs=JSON.stringify(e);var n={protocol:a.protocol,host:a.host,hash:a.hash,pathname:a.pathname,query:r};t?history.replaceState(null,"",S.a.format(n)):history.pushState(null,"",S.a.format(n))},parseBaselineUrlParameter:function(){var e=arguments.length>0&&void 0!==arguments[0]?arguments[0]:window.location,t=S.a.parse(e.toString()),a=A.a.parse(t.query).bs;return a?JSON.parse(a):{}}};function C(e){return(C="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(e){return typeof e}:function(e){return e&&"function"==typeof Symbol&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":typeof e})(e)}function N(e,t){for(var a=0;a<t.length;a++){var r=t[a];r.enumerable=r.enumerable||!1,r.configurable=!0,"value"in r&&(r.writable=!0),Object.defineProperty(e,r.key,r)}}function Q(e){return(Q=Object.setPrototypeOf?Object.getPrototypeOf:function(e){return e.__proto__||Object.getPrototypeOf(e)})(e)}function P(e,t){return(P=Object.setPrototypeOf||function(e,t){return e.__proto__=t,e})(e,t)}function D(e){if(void 0===e)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return e}var U=function(e){function t(e){var a,r,n;!function(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}(this,t),r=this,n=Q(t).call(this,e),a=!n||"object"!==C(n)&&"function"!=typeof n?D(r):n;var o=j.parseBaselineUrlParameter(),s=!1;return 0===Object.keys(o).length&&Object.keys(a.props.facetsTreeData).forEach(function(e){var t=a.props.facetsTreeData[e].find(function(e){return"organism_part"===e.name.toLowerCase()});t?(a._addElementToObjectOfArrays(o,e,t.name),s=!0):a.props.facetsTreeData[e].length&&a._addElementToObjectOfArrays(o,e,a.props.facetsTreeData[e][0].name)}),j.baselinePush(o,!0),a.state={facetsTreeData:a._transformPropsFacetsObjectToArray(o),querySelect:o,showAnatomograms:s},a.setChecked=a._setChecked.bind(D(D(a))),a.toggleAnatomograms=a._toggleAnatomograms.bind(D(D(a))),a}var a,r,o;return function(e,t){if("function"!=typeof t&&null!==t)throw new TypeError("Super expression must either be null or a function");e.prototype=Object.create(t&&t.prototype,{constructor:{value:e,writable:!0,configurable:!0}}),t&&P(e,t)}(t,n.a.Component),a=t,(r=[{key:"componentDidMount",value:function(){var e=this;window.addEventListener("popstate",function(){var t=j.parseBaselineUrlParameter();e.setState({querySelect:t,facetsTreeData:e._transformPropsFacetsObjectToArray(t)})},!1)}},{key:"render",value:function(){var e=this._organismPartInQuerySelect(),t=this._querySelectToHeatmaps();return n.a.createElement("div",{className:"row expanded"},n.a.createElement("div",{className:"small-3 large-2 columns"},n.a.createElement(d,{facets:this.state.facetsTreeData,setChecked:this.setChecked,showAnatomograms:this.state.showAnatomograms,toggleAnatomograms:this.toggleAnatomograms,disableAnatomogramsCheckbox:!e})),n.a.createElement("div",{className:"small-9 large-10 columns"},n.a.createElement(E,{atlasUrl:this.props.atlasUrl,geneQuery:this.props.geneQuery,conditionQuery:this.props.conditionQuery,heatmaps:t,showAnatomograms:this.state.showAnatomograms})))}},{key:"_setChecked",value:function(e,t,a){var r=JSON.parse(JSON.stringify(this.state.querySelect)),n=JSON.parse(JSON.stringify(this.state.facetsTreeData));a?(this._addElementToObjectOfArrays(r,e,t),n.find(function(t){return t.facetName===e}).facetItems.find(function(e){return e.name===t}).checked=!0):(this._removeElementFromObjectOfArrays(r,e,t),n.find(function(t){return t.facetName===e}).facetItems.find(function(e){return e.name===t}).checked=!1),j.baselinePush(r,!1),this.setState({facetsTreeData:n,querySelect:r})}},{key:"_addElementToObjectOfArrays",value:function(e,t,a){e[t]||(e[t]=[]),e[t].push(a)}},{key:"_removeElementFromObjectOfArrays",value:function(e,t,a){e[t].splice(e[t].indexOf(a),1),0===e[t].length&&delete e[t]}},{key:"_toggleAnatomograms",value:function(){var e=!this.state.showAnatomograms;this.setState({showAnatomograms:e})}},{key:"_organismPartInQuerySelect",value:function(){var e=arguments.length>0&&void 0!==arguments[0]?arguments[0]:this.state.querySelect;return Object.keys(e).some(function(t){return e[t].some(function(e){return"organism_part"===e.toLowerCase()})})}},{key:"_transformPropsFacetsObjectToArray",value:function(e){var t=this;return Object.keys(this.props.facetsTreeData).map(function(a){return{facetName:a,facetItems:t.props.facetsTreeData[a].map(function(t){return{name:t.name,value:t.value,checked:!!e[a]&&e[a].includes(t.name)}})}})}},{key:"_querySelectToHeatmaps",value:function(){var e=this,t=[];return this.state.facetsTreeData.forEach(function(a){a.facetItems.forEach(function(r){e.state.querySelect[a.facetName]&&e.state.querySelect[a.facetName].includes(r.name)&&t.push({species:a.facetName,factor:r})})}),t}}])&&N(a.prototype,r),o&&N(a,o),t}();U.propTypes={atlasUrl:c.a.string.isRequired,facetsTreeData:c.a.object.isRequired,geneQuery:c.a.string.isRequired,conditionQuery:c.a.string.isRequired,species:c.a.string.isRequired};var x=U;a.d(t,"render",function(){return I});var I=function(e,t){var a=e.atlasUrl,r=void 0===a?"https://www.ebi.ac.uk/gxa":a,o=e.facetsTreeData,i=e.geneQuery,c=e.conditionQuery,u=e.species;s.a.render(n.a.createElement(x,{atlasUrl:r,facetsTreeData:o,geneQuery:i,conditionQuery:c,species:u}),document.getElementById(t))}},123:function(e,t){},184:function(e,t){},188:function(e,t){},189:function(e,t){},196:function(e,t,a){var r={"./brain.selected.png":197,"./brain.unselected.png":198,"./female.selected.png":199,"./female.unselected.png":200,"./flower_parts.selected.png":201,"./flower_parts.unselected.png":202,"./male.selected.png":203,"./male.unselected.png":204,"./whole_plant.selected.png":205,"./whole_plant.unselected.png":206};function n(e){var t=o(e);return a(t)}function o(e){var t=r[e];if(!(t+1)){var a=new Error("Cannot find module '"+e+"'");throw a.code="MODULE_NOT_FOUND",a}return t}n.keys=function(){return Object.keys(r)},n.resolve=o,e.exports=n,n.id=196},207:function(e,t,a){var r={"./anolis_carolinensis.svg":208,"./arabidopsis_thaliana.svg":209,"./bos_taurus.svg":210,"./brachypodium_distachyon.flower_parts.svg":211,"./brachypodium_distachyon.whole_plant.svg":212,"./gallus_gallus.svg":213,"./homo_sapiens.brain.svg":214,"./homo_sapiens.female.svg":215,"./homo_sapiens.male.svg":216,"./hordeum_vulgare.flower_parts.svg":217,"./hordeum_vulgare.whole_plant.svg":218,"./macaca_mulatta.svg":219,"./monodelphis_domestica.svg":220,"./mus_musculus.brain.svg":221,"./mus_musculus.female.svg":222,"./mus_musculus.male.svg":223,"./oryza_sativa.flower_parts.svg":224,"./oryza_sativa.whole_plant.svg":225,"./papio_anubis.svg":226,"./rattus_norvegicus.svg":227,"./solanum_lycopersicum.flower_parts.svg":228,"./solanum_lycopersicum.whole_plant.svg":229,"./solanum_tuberosum.svg":230,"./sorghum_bicolor.flower_parts.svg":231,"./sorghum_bicolor.whole_plant.svg":232,"./tetraodon_nigroviridis.svg":233,"./triticum_aestivum.flower_parts.svg":234,"./triticum_aestivum.whole_plant.svg":235,"./xenopus_tropicalis.svg":236,"./zea_mays.flower_parts.svg":237,"./zea_mays.whole_plant.svg":238};function n(e){var t=o(e);return a(t)}function o(e){var t=r[e];if(!(t+1)){var a=new Error("Cannot find module '"+e+"'");throw a.code="MODULE_NOT_FOUND",a}return t}n.keys=function(){return Object.keys(r)},n.resolve=o,e.exports=n,n.id=207}},[[1029,0]]]);