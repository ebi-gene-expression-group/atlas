var homepageExperimentsSummaryPanel=(window.webpackJsonp_name_=window.webpackJsonp_name_||[]).push([[7],{232:function(e,t,n){"use strict";n.r(t),n.d(t,"render",function(){return f});var r=n(1),a=n.n(r),i=n(3),u=n.n(i),s=n(86),o=n.n(s),l=n(13),c=n.n(l)()(o.a),f=function(e,t){u.a.render(a.a.createElement(c,e),document.getElementById(t))}},233:function(e,t,n){"use strict";function r(e){return(r="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(e){return typeof e}:function(e){return e&&"function"==typeof Symbol&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":typeof e})(e)}Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var a=o(n(1)),i=o(n(0)),u=o(n(14)),s=o(n(234));function o(e){return e&&e.__esModule?e:{default:e}}function l(e){return(l="function"==typeof Symbol&&"symbol"===r(Symbol.iterator)?function(e){return r(e)}:function(e){return e&&"function"==typeof Symbol&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":r(e)})(e)}function c(){return(c=Object.assign||function(e){for(var t=1;t<arguments.length;t++){var n=arguments[t];for(var r in n)Object.prototype.hasOwnProperty.call(n,r)&&(e[r]=n[r])}return e}).apply(this,arguments)}function f(e,t){for(var n=0;n<t.length;n++){var r=t[n];r.enumerable=r.enumerable||!1,r.configurable=!0,"value"in r&&(r.writable=!0),Object.defineProperty(e,r.key,r)}}function d(e,t){return!t||"object"!==l(t)&&"function"!=typeof t?function(e){if(void 0===e)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return e}(e):t}function p(e){return(p=Object.setPrototypeOf?Object.getPrototypeOf:function(e){return e.__proto__||Object.getPrototypeOf(e)})(e)}function m(e,t){return(m=Object.setPrototypeOf||function(e,t){return e.__proto__=t,e})(e,t)}var y=function(e){function t(){return function(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}(this,t),d(this,p(t).apply(this,arguments))}var n,r,i;return function(e,t){if("function"!=typeof t&&null!==t)throw new TypeError("Super expression must either be null or a function");e.prototype=Object.create(t&&t.prototype,{constructor:{value:e,writable:!0,configurable:!0}}),t&&m(e,t)}(t,a.default.Component),n=t,(r=[{key:"render",value:function(){var e=this.props,t=e.host,n=e.latestExperiments,r=e.featuredExperiments,i=e.responsiveCardsRowProps,o=e.tabsId;return[a.default.createElement("ul",{key:"tabs",className:"tabs","data-tabs":!0,id:o},[r.length?a.default.createElement("li",{key:"featured",className:"tabs-title is-active",style:{textTransform:"capitalize"}},a.default.createElement("a",{href:"#featured"},"Featured experiments")):null,a.default.createElement("li",{key:"latest",className:"tabs-title".concat(r.length?"":" is-active"),style:{textTransform:"capitalize"}},a.default.createElement("a",{href:"#latest"},"Latest experiments"))]),r.length?a.default.createElement("div",{key:"tabs-content-featured",className:"tabs-content","data-tabs-content":o},a.default.createElement("div",{className:"tabs-panel is-active",id:"featured"},a.default.createElement(u.default,c({cards:r},i)))):null,a.default.createElement("div",{key:"tabs-content-latest",className:"tabs-content","data-tabs-content":o},a.default.createElement("div",{className:"tabs-panel".concat(r.length?"":" is-active"),id:"latest"},a.default.createElement(s.default,{experiments:n,host:t})))]}},{key:"componentDidMount",value:function(){this.props.onComponentDidMount()}}])&&f(n.prototype,r),i&&f(n,i),t}();y.propTypes={host:i.default.string.isRequired,featuredExperiments:u.default.propTypes.cards,latestExperiments:i.default.arrayOf(i.default.shape({experimentType:i.default.string.isRequired,experimentAccession:i.default.string.isRequired,experimentDescription:i.default.string.isRequired,numberOfAssays:i.default.number.isRequired,lastUpdate:i.default.string.isRequired,species:i.default.string.isRequired})).isRequired,onComponentDidMount:i.default.func,responsiveCardsRowProps:i.default.object,tabsId:i.default.string},y.defaultProps={onComponentDidMount:function(){},responsiveCardsRowProps:{},tabsId:"experiments-summary-tabs"};var b=y;t.default=b},234:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var r=u(n(1)),a=u(n(0)),i=u(n(235));function u(e){return e&&e.__esModule?e:{default:e}}function s(){return(s=Object.assign||function(e){for(var t=1;t<arguments.length;t++){var n=arguments[t];for(var r in n)Object.prototype.hasOwnProperty.call(n,r)&&(e[r]=n[r])}return e}).apply(this,arguments)}var o=function(e){var t=e.experiments,n=e.host;return r.default.createElement("ul",{style:{listStyle:"none",marginLeft:"offset"}},Array.isArray(t)&&t.map(function(e,t){return r.default.createElement("li",{key:t},r.default.createElement(i.default,s({host:n},e)))}))};o.propTypes={experiments:a.default.arrayOf(a.default.shape({experimentType:a.default.string.isRequired,experimentAccession:a.default.string.isRequired,experimentDescription:a.default.string.isRequired,numberOfAssays:a.default.number.isRequired,lastUpdate:a.default.string.isRequired,species:a.default.string.isRequired})).isRequired,host:a.default.string.isRequired};var l=o;t.default=l},235:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var r=s(n(1)),a=s(n(0)),i=s(n(6)),u=s(n(5));function s(e){return e&&e.__esModule?e:{default:e}}function o(){var e=c(["\n  margin-bottom: 0px;\n  width: 120px;\n  cursor: default;\n  background-color: ",";\n  :hover{\n    opacity: 1.0;\n    background-color: ",";\n  }\n"]);return o=function(){return e},e}function l(){var e=c(["\n  padding-right: 0px;\n  vertical-align: middle;\n  display: table-cell;\n"]);return l=function(){return e},e}function c(e,t){return t||(t=e.slice(0)),Object.freeze(Object.defineProperties(e,{raw:{value:Object.freeze(t)}}))}var f=i.default.div(l()),d=i.default.span(o(),function(e){return e.backgroundColor},function(e){return e.backgroundColor}),p=function(e){var t=e.experimentType,n=e.experimentAccession,a=e.experimentDescription,i=e.numberOfAssays,s=e.lastUpdate,o=e.species,l=e.host,c=(0,u.default)("experiments/".concat(n),l).toString();return r.default.createElement("div",{style:{display:"block",marginBottom:"1rem"}},!t.startsWith("SINGLE_CELL")&&r.default.createElement(f,null,r.default.createElement(d,{className:"button",backgroundColor:"#007c82"},t.endsWith("BASELINE")?"Baseline":"Differential")),r.default.createElement(f,{className:"hide-for-small-only"},r.default.createElement(d,{className:"button",backgroundColor:"gray",title:"Number of assays in experiment"},i.toString().replace(/\B(?=(\d{3})+(?!\d))/g,",")," assays")),r.default.createElement(f,{className:"hide-for-small-only"},r.default.createElement("a",{className:"button",style:{marginBottom:"0px",backgroundColor:"#3497C5"},href:c},"Results")),r.default.createElement(f,{style:{paddingLeft:"1rem"}},r.default.createElement("small",null,s),r.default.createElement("br",null),r.default.createElement("a",{href:c},a," – ",r.default.createElement("i",null,o))))};p.propTypes={experimentType:a.default.string.isRequired,experimentAccession:a.default.string.isRequired,experimentDescription:a.default.string.isRequired,numberOfAssays:a.default.number.isRequired,lastUpdate:a.default.string.isRequired,species:a.default.string.isRequired,host:a.default.string.isRequired};var m=p;t.default=m},86:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),Object.defineProperty(t,"default",{enumerable:!0,get:function(){return a.default}});var r,a=(r=n(233))&&r.__esModule?r:{default:r}}},[[232,0]]]);