var expressionAtlasBioentityInformation=(window.webpackJsonp_name_=window.webpackJsonp_name_||[]).push([[3],{809:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),Object.defineProperty(t,"default",{enumerable:!0,get:function(){return a.default}}),t.render=void 0;var r=u(n(1)),o=u(n(8)),a=u(n(810));function u(e){return e&&e.__esModule?e:{default:e}}t.render=function(e,t){o.default.render(r.default.createElement(a.default,e),document.getElementById(t))}},810:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var r=i(n(1)),o=i(n(0)),a=i(n(13)),u=i(n(811)),l=i(n(812));function i(e){return e&&e.__esModule?e:{default:e}}function s(e){return(s="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(e){return typeof e}:function(e){return e&&"function"==typeof Symbol&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":typeof e})(e)}function c(e,t){for(var n=0;n<t.length;n++){var r=t[n];r.enumerable=r.enumerable||!1,r.configurable=!0,"value"in r&&(r.writable=!0),Object.defineProperty(e,r.key,r)}}function f(e,t){return!t||"object"!==s(t)&&"function"!=typeof t?function(e){if(void 0===e)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return e}(e):t}function p(e){return(p=Object.setPrototypeOf?Object.getPrototypeOf:function(e){return e.__proto__||Object.getPrototypeOf(e)})(e)}function d(e,t){return(d=Object.setPrototypeOf||function(e,t){return e.__proto__=t,e})(e,t)}function y(e,t,n,r,o,a,u){try{var l=e[a](u),i=l.value}catch(e){return void n(e)}l.done?t(i):Promise.resolve(i).then(r,o)}var h=function(){var e,t=(e=regeneratorRuntime.mark(function e(t,n){var r,o;return regeneratorRuntime.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,fetch((0,a.default)(n,t).toString());case 2:return r=e.sent,e.next=5,r.json();case 5:return o=e.sent,e.abrupt("return",o);case 7:case"end":return e.stop()}},e,this)}),function(){var t=this,n=arguments;return new Promise(function(r,o){var a=e.apply(t,n);function u(e){y(a,r,o,u,l,"next",e)}function l(e){y(a,r,o,u,l,"throw",e)}u(void 0)})});return function(e,n){return t.apply(this,arguments)}}(),b=function(e){function t(e){var n;return function(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}(this,t),(n=f(this,p(t).call(this,e))).state={bioentityProperties:[],errorMessage:null,loading:!1},n}var n,o,a;return function(e,t){if("function"!=typeof t&&null!==t)throw new TypeError("Super expression must either be null or a function");e.prototype=Object.create(t&&t.prototype,{constructor:{value:e,writable:!0,configurable:!0}}),t&&d(e,t)}(t,r.default.Component),n=t,(o=[{key:"_fetchAndSetState",value:function(e){var t=this,n=e.atlasUrl,r=e.geneId,o="json/bioentity_information/".concat(r);return h(n,o).then(function(e){t.setState({bioentityProperties:e,errorMessage:null,loading:!1})}).catch(function(e){t.setState({errorMessage:"".concat(e.name,": ").concat(e.message)})})}},{key:"componentDidCatch",value:function(e,t){this.setState({errorMessage:"".concat(e)})}},{key:"componentDidMount",value:function(){return this.setState({loading:!0}),this._fetchAndSetState(this.props)}},{key:"componentWillReceiveProps",value:function(e){if(e.atlasUrl!==this.props.atlasUrl||e.geneId!==this.props.geneId)return this.setState({bioentityProperties:[],loading:!0}),this._fetchAndSetState(e)}},{key:"render",value:function(){var e=this.state.bioentityProperties.map(function(e){return r.default.createElement("tr",{key:e.type},r.default.createElement("th",{style:{whiteSpace:"nowrap"}},e.name),r.default.createElement("td",null,r.default.createElement(u.default,{type:e.type,values:e.values})))});return this.props.geneId&&r.default.createElement("div",{className:this.props.wrapperClassName},r.default.createElement(l.default,{loading:this.state.loading,resourcesUrl:this.props.resourcesUrl}),r.default.createElement("table",{className:"hover"},r.default.createElement("tbody",null,e)))}}])&&c(n.prototype,o),a&&c(n,a),t}();b.propTypes={atlasUrl:o.default.string.isRequired,geneId:o.default.string,resourcesUrl:o.default.string,wrapperClassName:o.default.string},b.defaultProps={geneId:"",wrapperClassName:"row column expanded"};var m=b;t.default=m},811:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var r=a(n(1)),o=a(n(0));function a(e){return e&&e.__esModule?e:{default:e}}function u(e){return(u="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(e){return typeof e}:function(e){return e&&"function"==typeof Symbol&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":typeof e})(e)}function l(e,t){for(var n=0;n<t.length;n++){var r=t[n];r.enumerable=r.enumerable||!1,r.configurable=!0,"value"in r&&(r.writable=!0),Object.defineProperty(e,r.key,r)}}function i(e){return(i=Object.setPrototypeOf?Object.getPrototypeOf:function(e){return e.__proto__||Object.getPrototypeOf(e)})(e)}function s(e,t){return(s=Object.setPrototypeOf||function(e,t){return e.__proto__=t,e})(e,t)}function c(e){if(void 0===e)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return e}var f=function(e){var t=e.hasUrl,n=e.isLast,o=e.text,a=e.url;return t?r.default.createElement("span",null,r.default.createElement("a",{className:"bioEntityCardLink",href:a,target:"_blank"},o),n?"":", "):r.default.createElement("span",null,o+(n?"":", "))},p=function(e){function t(e){var n,r,o;return function(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}(this,t),r=this,(n=!(o=i(t).call(this,e))||"object"!==u(o)&&"function"!=typeof o?c(r):o).state={showAll:!1},n.handleShowMoreClick=n.handleClick.bind(c(c(n))),n}var n,o,a;return function(e,t){if("function"!=typeof t&&null!==t)throw new TypeError("Super expression must either be null or a function");e.prototype=Object.create(t&&t.prototype,{constructor:{value:e,writable:!0,configurable:!0}}),t&&s(e,t)}(t,r.default.Component),n=t,(o=[{key:"handleClick",value:function(){this.setState(function(e){return{showAll:!e.showAll}})}},{key:"_getMostRelevant",value:function(e){return e.slice(0,3)}},{key:"_renderPropertyValues",value:function(e){return e.map(function(t,n){return r.default.createElement(f,{key:t.text,isLast:n>=e.length-1,hasUrl:!!t.url,text:t.text,url:t.url})})}},{key:"render",value:function(){var e=this.props.values.length-3,t=["go","po"].indexOf(this.props.type)>-1&&e>0,n=r.default.createElement("a",{key:"showButton",role:"button",style:{cursor:"pointer"},onClick:this.handleShowMoreClick},this.state.showAll?" (show less)":" … and ".concat(e," more")),o=this._renderPropertyValues(this.props.values),a=this._renderPropertyValues(this._getMostRelevant(this.props.values));return r.default.createElement("div",null,!t&&o,t&&!this.state.showAll&&[a,n],t&&this.state.showAll&&[o,n])}}])&&l(n.prototype,o),a&&l(n,a),t}();p.propTypes={type:o.default.string.isRequired,values:o.default.arrayOf(o.default.shape({text:o.default.string.isRequired,url:o.default.string.isRequired,relevance:o.default.number.isRequired}))};var d=p;t.default=d},812:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var r=u(n(1)),o=u(n(0)),a=u(n(13));function u(e){return e&&e.__esModule?e:{default:e}}var l=function(e){return e.loading?r.default.createElement("div",{className:"text-center"},r.default.createElement("img",{className:"small-centered",src:(0,a.default)(n(813),e.resourcesUrl).toString()})):null};l.propTypes={loading:o.default.bool.isRequired,resourcesUrl:o.default.string},l.defaultProps={resourcesUrl:""};var i=l;t.default=i},813:function(e,t,n){e.exports=n.p+"bbe30b27d9320f575e5452cf2b930c40.svg"}},[[809,0]]]);