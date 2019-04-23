var feedbackForm=(window.webpackJsonp_name_=window.webpackJsonp_name_||[]).push([[6],{339:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;t.default={SHOW:"SHOW",CLOSE:"CLOSE",REFRESH:"REFRESH"}},340:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.element=t.modifier=void 0;t.element=function(e,t){return"".concat(t,"__").concat(e)};t.modifier=function(e,t){if(!e)return null;var n=[];return e.split(" ").forEach(function(e){n.push("".concat(t,"--").concat(e))}),n.join(" ")}},829:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),Object.defineProperty(t,"default",{enumerable:!0,get:function(){return l.default}}),t.render=void 0;var o=u(n(1)),r=u(n(8)),l=u(n(830));function u(e){return e&&e.__esModule?e:{default:e}}t.render=function(e,t){r.default.render(o.default.createElement(l.default,e),document.getElementById(t))}},830:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var o=s(n(1)),r=s(n(0)),l=s(n(14)),u=s(n(831)),i=s(n(346)),a=s(n(839));function s(e){return e&&e.__esModule?e:{default:e}}function c(e){return(c="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(e){return typeof e}:function(e){return e&&"function"==typeof Symbol&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":typeof e})(e)}function f(e,t){for(var n=0;n<t.length;n++){var o=t[n];o.enumerable=o.enumerable||!1,o.configurable=!0,"value"in o&&(o.writable=!0),Object.defineProperty(e,o.key,o)}}function d(e){return(d=Object.setPrototypeOf?Object.getPrototypeOf:function(e){return e.__proto__||Object.getPrototypeOf(e)})(e)}function p(e){if(void 0===e)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return e}function y(e,t){return(y=Object.setPrototypeOf||function(e,t){return e.__proto__=t,e})(e,t)}function b(){var e=function(e,t){t||(t=e.slice(0));return Object.freeze(Object.defineProperties(e,{raw:{value:Object.freeze(t)}}))}(["\n  position: fixed;\n  bottom: 50%;\n  right: 0;\n  display: inline-block;\n  background-color: #3497c5;\n  color: white;\n  font-size: 1.2rem;\n  padding: 0.25rem 0.25rem 1rem 0.25rem;\n  transition: all 0.2s;\n  margin: -45px;\n  :hover {\n    background-color: #2f5767;\n    cursor: pointer;\n  }\n  box-shadow: 2px 2px 3px #999;\n  transform: rotate(-90deg);\n"]);return b=function(){return e},e}var h=l.default.button(b()),m=function(e){function t(e){var n,r,l;return function(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}(this,t),r=this,(n=!(l=d(t).call(this,e))||"object"!==c(l)&&"function"!=typeof l?p(r):l).state={smileyScore:null},n.onClick=n.onClick.bind(p(n)),n.smileyChange=n.smileyChange.bind(p(n)),u.default.registerPlugin("prompt",function(t,n,r){u.default.create({title:"Your feedback",content:o.default.createElement(a.default,{feedbackFormLink:e.feedbackFormLink,onSelect:n}),buttons:{left:["cancel"],right:t?[{text:"Submit",className:"success",action:function(){r(),t&&i.default.event({category:"Satisfaction",action:t.toString()}),t&&u.default.close()}}]:[]}})}),n}var n,r,l;return function(e,t){if("function"!=typeof t&&null!==t)throw new TypeError("Super expression must either be null or a function");e.prototype=Object.create(t&&t.prototype,{constructor:{value:e,writable:!0,configurable:!0}}),t&&y(e,t)}(t,o.default.Component),n=t,(r=[{key:"smileyChange",value:function(e){var t=this;this.setState({smileyScore:e},function(){u.default.close(),t.onClick()})}},{key:"onClick",value:function(){u.default.plugins().prompt(this.state.smileyScore,this.smileyChange,function(){u.default.alert("Thank you for submitting your feedback.")})}},{key:"render",value:function(){return i.default.initialize(this.props.gaId),i.default.pageview(window.location.pathname+window.location.search),o.default.createElement("div",null,o.default.createElement(u.default,null),o.default.createElement(h,{onClick:this.onClick},o.default.createElement("i",{className:"icon icon-functional","data-icon":"n",style:{paddingRight:"0.5rem"}}),"Feedback"))}}])&&f(n.prototype,r),l&&f(n,l),t}();m.propTypes={feedbackFormLink:r.default.oneOf(["https://www.ebi.ac.uk/support/gxa","https://www.ebi.ac.uk/support/gxasc"]).isRequired,gaId:r.default.string.isRequired};var v=m;t.default=v},831:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),Object.defineProperty(t,"default",{enumerable:!0,get:function(){return r.default}});var o,r=(o=n(832))&&o.__esModule?o:{default:o}},832:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var o=f(n(1)),r=f(n(0)),l=f(n(833)),u=f(n(834)),i=f(n(835)),a=f(n(836)),s=f(n(339)),c=n(340);function f(e){return e&&e.__esModule?e:{default:e}}function d(e){return(d="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(e){return typeof e}:function(e){return e&&"function"==typeof Symbol&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":typeof e})(e)}function p(e,t){return(p=Object.setPrototypeOf||function(e,t){return e.__proto__=t,e})(e,t)}function y(e){return(y=Object.getPrototypeOf||function(e){return e.__proto__})(e)}function b(e,t){for(var n=0;n<t.length;n++){var o=t[n];o.enumerable=o.enumerable||!1,o.configurable=!0,"value"in o&&(o.writable=!0),Object.defineProperty(e,o.key,o)}}function h(e,t,n){return t&&b(e.prototype,t),n&&b(e,n),e}function m(e){if(void 0===e)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return e}var v,k,O,g=l.default.filter,C=new u.default,_=function(){l.default.deleteScope("react-popup"),l.default.filter=g,C.close()},w={id:null,title:null,buttons:null,content:null,visible:!1,className:null,noOverlay:!1,position:!1,closeOnOutsideClick:!0,onClose:function(){},onOpen:function(){}},E=function(e){function t(e){var n,o,r;return function(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}(this,t),o=this,n=!(r=y(t).call(this,e))||"object"!==d(r)&&"function"!=typeof r?m(o):r,w.closeOnOutsideClick=n.props.closeOnOutsideClick,n.state=w,n.bound={onShow:n.onShow.bind(m(m(n))),onClose:n.onClose.bind(m(m(n))),onRefresh:n.onRefresh.bind(m(m(n))),containerClick:n.containerClick.bind(m(m(n))),handleButtonClick:n.handleButtonClick.bind(m(m(n)))},n.boxRef=null,n.defaultKeyBindings={ok:n.props.defaultOkKey,cancel:n.props.defaultCancelKey},n}return h(t,null,[{key:"addShowListener",value:function(e){C.on(s.default.SHOW,e)}},{key:"removeShowListener",value:function(e){C.removeListener(s.default.SHOW,e)}},{key:"addCloseListener",value:function(e){C.on(s.default.CLOSE,e)}},{key:"removeCloseListener",value:function(e){C.removeListener(s.default.CLOSE,e)}},{key:"register",value:function(e){var t=C.getId();return C.popups[t]=Object.assign({},w,e),t}},{key:"queue",value:function(e){return!!Object.prototype.hasOwnProperty.call(C.popups,e)&&(C.queue.push(e),C.dispatch(),e)}},{key:"create",value:function(e,t){var n=this.register(e);if(!0===t){var o=C.active;C.active=null,this.queue(n),this.queue(o),C.dispatch()}else this.queue(n);return n}},{key:"alert",value:function(e,t,n){var o={title:t,content:e,buttons:{right:["ok"]}};return this.create(o,n)}},{key:"close",value:function(){C.close()}},{key:"registerPlugin",value:function(e,t){C.plugins[e]=t.bind(this)}},{key:"plugins",value:function(){return C.plugins}},{key:"refreshPosition",value:function(e){return C.refreshPosition(e)}},{key:"clearQueue",value:function(){return C.clearQueue()}}]),h(t,[{key:"componentDidMount",value:function(){C.on(s.default.SHOW,this.bound.onShow),C.on(s.default.CLOSE,this.bound.onClose),C.on(s.default.REFRESH,this.bound.onRefresh)}},{key:"componentDidUpdate",value:function(){this.boxRef&&this.boxRef.focus(),this.setPosition(this.state.position)}},{key:"componentWillUnmount",value:function(){C.removeListener(s.default.SHOW,this.bound.onShow),C.removeListener(s.default.CLOSE,this.bound.onClose),C.removeListener(s.default.REFRESH,this.bound.onRefresh),l.default.deleteScope("react-popup"),l.default.filter=g}},{key:"onRefresh",value:function(e){this.setPosition(e)}},{key:"onClose",value:function(){l.default.deleteScope("react-popup"),l.default.filter=g,this.state.onClose(this.state.id,this.state.title),this.setState(w)}},{key:"onShow",value:function(e){var t=this;l.default.deleteScope("react-popup"),l.default.filter=function(){return!0};var n=C.activePopup();n.buttons&&!Object.prototype.hasOwnProperty.call(n.buttons,"left")&&(n.buttons.left=[]),n.buttons&&!Object.prototype.hasOwnProperty.call(n.buttons,"right")&&(n.buttons.right=[]),this.setState({id:e,title:n.title,content:n.content,buttons:n.buttons,visible:!0,className:n.className,noOverlay:n.noOverlay,position:n.position,closeOnOutsideClick:n.closeOnOutsideClick,onClose:n.onClose,onOpen:n.onOpen},function(){l.default.setScope("react-popup"),t.state.onOpen(t.state.id,t.state.title),t.props.escToClose&&(0,l.default)("esc","react-popup",t.handleKeyEvent.bind(t,"cancel",t.state.id)),t.state.buttons&&(t.state.buttons.left.length&&t.state.buttons.left.forEach(function(e){return t.bindKeyEvents(e)}),t.state.buttons.right.length&&t.state.buttons.right.forEach(function(e){return t.bindKeyEvents(e)}))})}},{key:"setPosition",value:function(e){var t=this.boxRef,n=e;if(t){if(n||(n=this.state.position),!n)return t.style.opacity=1,t.style.top=null,t.style.left=null,void(t.style.margin=null);"function"!=typeof n?(t.style.top="".concat(parseInt(n.y,10),"px"),t.style.left="".concat(parseInt(n.x,10),"px"),t.style.margin=0,t.style.opacity=1):n.call(null,t)}}},{key:"containerClick",value:function(){this.state.closeOnOutsideClick&&_()}},{key:"bindKeyEvents",value:function(e){var t=null;"string"==typeof e?t=this.defaultKeyBindings[e]:Object.prototype.hasOwnProperty.call(e,"key")&&(t=e.key),this.props.escToClose&&"esc"===t||t&&(0,l.default)(t,"react-popup",this.handleKeyEvent.bind(this,e,this.state.id))}},{key:"handleKeyEvent",value:function(e,t,n){return this.state.id!==t||"enter"===e.key&&["INPUT","TEXTAREA","BUTTON"].indexOf(n.target.tagName)>=0||("string"==typeof e?_():Object.prototype.hasOwnProperty.call(e,"action")&&this.handleButtonClick(e.action),!1)}},{key:"handleButtonClick",value:function(e){return"function"==typeof e?e.call(this,C):null}},{key:"className",value:function(e){return(0,c.element)(e,this.props.className)}},{key:"render",value:function(){var e=this,t=this.props.className,n=null,r={};if(this.state.visible){var l=null;t+=" ".concat(this.props.className,"--visible"),this.props.closeBtn&&(l=o.default.createElement("button",{onClick:_,className:"".concat(this.props.className,"__close")},this.props.closeHtml));var u=this.className("box");this.state.className&&(u+=" ".concat((0,c.modifier)(this.state.className,u))),n=o.default.createElement("article",{role:"dialog",tabIndex:"-1",ref:function(t){e.boxRef=t},style:{opacity:0,outline:"none"},className:u},l,o.default.createElement(i.default,{title:this.state.title,className:this.className("box__header")}),o.default.createElement("div",{className:this.className("box__body")},this.state.content),o.default.createElement(a.default,{className:this.className("box__footer"),btnClass:this.props.btnClass,buttonClick:this.bound.handleButtonClick,onClose:_,onOk:_,defaultOk:this.props.defaultOk,defaultCancel:this.props.defaultCancel,buttons:this.state.buttons}))}return this.state.noOverlay&&(r.background="transparent"),o.default.createElement("div",{className:t},o.default.createElement("div",{role:"presentation",onClick:this.bound.containerClick,className:this.className("overlay"),style:r}),n)}}]),function(e,t){if("function"!=typeof t&&null!==t)throw new TypeError("Super expression must either be null or a function");p(e.prototype,t&&t.prototype),t&&p(e,t)}(t,e),t}(o.default.Component);O={className:"mm-popup",btnClass:"mm-popup__btn",closeBtn:!0,closeHtml:null,defaultOk:"Ok",defaultOkKey:"enter",defaultCancel:"Cancel",defaultCancelKey:"esc",closeOnOutsideClick:!0,escToClose:!0,onClose:function(){},onOpen:function(){}},(k="defaultProps")in(v=E)?Object.defineProperty(v,k,{value:O,enumerable:!0,configurable:!0,writable:!0}):v[k]=O,E.propTypes={className:r.default.string,btnClass:r.default.string,closeBtn:r.default.bool,closeHtml:r.default.node,defaultOk:r.default.string,defaultOkKey:r.default.string,defaultCancel:r.default.string,defaultCancelKey:r.default.string,closeOnOutsideClick:r.default.bool,escToClose:r.default.bool,onClose:r.default.func,onOpen:r.default.func};var S=E;t.default=S},833:function(e,t,n){!function(t){var n,o={},r={16:!1,18:!1,17:!1,91:!1},l="all",u={"⇧":16,shift:16,"⌥":18,alt:18,option:18,"⌃":17,ctrl:17,control:17,"⌘":91,command:91},i={backspace:8,tab:9,clear:12,enter:13,return:13,esc:27,escape:27,space:32,left:37,up:38,right:39,down:40,del:46,delete:46,home:36,end:35,pageup:33,pagedown:34,",":188,".":190,"/":191,"`":192,"-":189,"=":187,";":186,"'":222,"[":219,"]":221,"\\":220},a=function(e){return i[e]||e.toUpperCase().charCodeAt(0)},s=[];for(n=1;n<20;n++)i["f"+n]=111+n;function c(e,t){for(var n=e.length;n--;)if(e[n]===t)return n;return-1}function f(e,t){if(e.length!=t.length)return!1;for(var n=0;n<e.length;n++)if(e[n]!==t[n])return!1;return!0}var d={16:"shiftKey",18:"altKey",17:"ctrlKey",91:"metaKey"};function p(e){for(n in r)r[n]=e[d[n]]}function y(e,t,n){var r,l;r=h(e),void 0===n&&(n=t,t="all");for(var u=0;u<r.length;u++)l=[],(e=r[u].split("+")).length>1&&(l=m(e),e=[e[e.length-1]]),e=e[0],(e=a(e))in o||(o[e]=[]),o[e].push({shortcut:r[u],scope:t,method:n,key:r[u],mods:l})}for(n in u)y[n]=!1;function b(){return l||"all"}function h(e){var t;return""==(t=(e=e.replace(/\s/g,"")).split(","))[t.length-1]&&(t[t.length-2]+=","),t}function m(e){for(var t=e.slice(0,e.length-1),n=0;n<t.length;n++)t[n]=u[t[n]];return t}function v(e,t,n){e.addEventListener?e.addEventListener(t,n,!1):e.attachEvent&&e.attachEvent("on"+t,function(){n(window.event)})}v(document,"keydown",function(e){!function(e){var t,n,l,i,a,f;if(t=e.keyCode,-1==c(s,t)&&s.push(t),93!=t&&224!=t||(t=91),t in r)for(l in r[t]=!0,u)u[l]==t&&(y[l]=!0);else if(p(e),y.filter.call(this,e)&&t in o)for(f=b(),i=0;i<o[t].length;i++)if((n=o[t][i]).scope==f||"all"==n.scope){for(l in a=n.mods.length>0,r)(!r[l]&&c(n.mods,+l)>-1||r[l]&&-1==c(n.mods,+l))&&(a=!1);(0!=n.mods.length||r[16]||r[18]||r[17]||r[91])&&!a||!1===n.method(e,n)&&(e.preventDefault?e.preventDefault():e.returnValue=!1,e.stopPropagation&&e.stopPropagation(),e.cancelBubble&&(e.cancelBubble=!0))}}(e)}),v(document,"keyup",function(e){var t,n=e.keyCode,o=c(s,n);if(o>=0&&s.splice(o,1),93!=n&&224!=n||(n=91),n in r)for(t in r[n]=!1,u)u[t]==n&&(y[t]=!1)}),v(window,"focus",function(){for(n in r)r[n]=!1;for(n in u)y[n]=!1});var k=t.key;t.key=y,t.key.setScope=function(e){l=e||"all"},t.key.getScope=b,t.key.deleteScope=function(e){var t,n,r;for(t in o)for(n=o[t],r=0;r<n.length;)n[r].scope===e?n.splice(r,1):r++},t.key.filter=function(e){var t=(e.target||e.srcElement).tagName;return!("INPUT"==t||"SELECT"==t||"TEXTAREA"==t)},t.key.isPressed=function(e){return"string"==typeof e&&(e=a(e)),-1!=c(s,e)},t.key.getPressedKeyCodes=function(){return s.slice(0)},t.key.noConflict=function(){var e=t.key;return t.key=k,e},t.key.unbind=function(e,t){var n,r,l,u,i,s=[];for(n=h(e),u=0;u<n.length;u++){if((r=n[u].split("+")).length>1&&(s=m(r),e=r[r.length-1]),e=a(e),void 0===t&&(t=b()),!o[e])return;for(l=0;l<o[e].length;l++)(i=o[e][l]).scope===t&&f(i.mods,s)&&(o[e][l]={})}},e.exports=y}(this)},834:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var o,r=n(312),l=(o=n(339))&&o.__esModule?o:{default:o};function u(e){return(u="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(e){return typeof e}:function(e){return e&&"function"==typeof Symbol&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":typeof e})(e)}function i(e,t){return(i=Object.setPrototypeOf||function(e,t){return e.__proto__=t,e})(e,t)}function a(e,t){for(var n=0;n<t.length;n++){var o=t[n];o.enumerable=o.enumerable||!1,o.configurable=!0,"value"in o&&(o.writable=!0),Object.defineProperty(e,o.key,o)}}function s(e,t){return!t||"object"!==u(t)&&"function"!=typeof t?function(e){if(void 0===e)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return e}(e):t}function c(e){return(c=Object.getPrototypeOf||function(e){return e.__proto__})(e)}var f=function(e){function t(e){var n;return function(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}(this,t),(n=s(this,c(t).call(this,e))).id=1,n.popups={},n.queue=[],n.active=null,n.plugins={},n}var n,o,r;return n=t,(o=[{key:"getId",value:function(){return"id_".concat(this.id++)}},{key:"activePopup",value:function(){return this.popups[this.active]}},{key:"close",value:function(){if(!this.active)return!1;var e=this.active;return this.active=null,this.emit(l.default.CLOSE,e),this.dispatch(),this.value=null,e}},{key:"dispatch",value:function(){if(this.active||this.queue.length<1)return!1;var e=this.queue.shift();return this.active=e,this.emit(l.default.SHOW,e),!0}},{key:"refreshPosition",value:function(e){this.emit(l.default.REFRESH,e)}},{key:"clearQueue",value:function(){this.queue=[]}}])&&a(n.prototype,o),r&&a(n,r),function(e,t){if("function"!=typeof t&&null!==t)throw new TypeError("Super expression must either be null or a function");i(e.prototype,t&&t.prototype),t&&i(e,t)}(t,e),t}(r.EventEmitter);t.default=f},835:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var o=l(n(1)),r=l(n(0));function l(e){return e&&e.__esModule?e:{default:e}}var u=function(e){return e.title?o.default.createElement("header",{className:e.className},o.default.createElement("h1",{className:"".concat(e.className,"__title")},e.title)):null};u.defaultProps={title:null,className:null},u.propTypes={title:r.default.string,className:r.default.string};var i=u;t.default=i},836:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var o=u(n(1)),r=u(n(0)),l=u(n(837));function u(e){return e&&e.__esModule?e:{default:e}}var i=function(e){return e.buttons?o.default.createElement("footer",{className:e.className},o.default.createElement(l.default,{buttonClick:e.buttonClick,onOk:e.onOk,onClose:e.onClose,className:"".concat(e.className,"__left-space"),btnClass:e.btnClass,defaultOk:e.defaultOk,defaultCancel:e.defaultCancel,buttons:e.buttons.left}),o.default.createElement(l.default,{buttonClick:e.buttonClick,onOk:e.onOk,onClose:e.onClose,className:"".concat(e.className,"__right-space"),btnClass:e.btnClass,defaultOk:e.defaultOk,defaultCancel:e.defaultCancel,buttons:e.buttons.right})):null};i.propTypes={buttons:r.default.shape({left:r.default.arrayOf(r.default.oneOfType([r.default.string,r.default.object])),right:r.default.arrayOf(r.default.oneOfType([r.default.string,r.default.object]))}),className:r.default.string,btnClass:r.default.string,onOk:r.default.func,onClose:r.default.func,buttonClick:r.default.func,defaultOk:r.default.string,defaultCancel:r.default.string},i.defaultProps={buttons:null,className:null,btnClass:null,defaultOk:null,defaultCancel:null,buttonClick:function(){},onOk:function(){},onClose:function(){}};var a=i;t.default=a},837:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var o=i(n(1)),r=i(n(0)),l=i(n(838)),u=n(340);function i(e){return e&&e.__esModule?e:{default:e}}function a(e){return(a="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(e){return typeof e}:function(e){return e&&"function"==typeof Symbol&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":typeof e})(e)}function s(e,t){return(s=Object.setPrototypeOf||function(e,t){return e.__proto__=t,e})(e,t)}function c(e,t){for(var n=0;n<t.length;n++){var o=t[n];o.enumerable=o.enumerable||!1,o.configurable=!0,"value"in o&&(o.writable=!0),Object.defineProperty(e,o.key,o)}}function f(e,t){return!t||"object"!==a(t)&&"function"!=typeof t?function(e){if(void 0===e)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return e}(e):t}function d(e){return(d=Object.getPrototypeOf||function(e){return e.__proto__})(e)}var p,y,b,h=function(e){function t(){return function(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}(this,t),f(this,d(t).apply(this,arguments))}var n,r,i;return n=t,(r=[{key:"onOk",value:function(){return this.props.onOk()}},{key:"onClose",value:function(){return this.props.onClose()}},{key:"buttonClick",value:function(e){return this.props.buttonClick(e)}},{key:"render",value:function(){var e=this;if(!this.props.buttons)return null;var t=[];return this.props.buttons.forEach(function(n,r){var i=n.url?n.url:null,a=r;if("string"==typeof n)"ok"===n?t.push(o.default.createElement(l.default,{className:"".concat(e.props.btnClass," ").concat(e.props.btnClass,"--ok"),key:a,onClick:function(){return e.onOk()}},e.props.defaultOk)):"cancel"===n&&t.push(o.default.createElement(l.default,{className:"".concat(e.props.btnClass," ").concat(e.props.btnClass,"--cancel"),key:a,onClick:function(){return e.onClose()}},e.props.defaultCancel));else if(o.default.isValidElement(n))t.push(n);else{var s="".concat(e.props.btnClass," ").concat((0,u.modifier)(n.className,e.props.btnClass)),c=o.default.createElement(l.default,{className:s,key:a,url:i,onClick:function(){return e.buttonClick(n.action)}},n.text);t.push(c)}}),o.default.createElement("div",{className:this.props.className},t)}}])&&c(n.prototype,r),i&&c(n,i),function(e,t){if("function"!=typeof t&&null!==t)throw new TypeError("Super expression must either be null or a function");s(e.prototype,t&&t.prototype),t&&s(e,t)}(t,e),t}(o.default.Component);t.default=h,b={buttons:null,className:null,onOk:function(){},onClose:function(){},buttonClick:function(){},btnClass:null,defaultOk:null,defaultCancel:null},(y="defaultProps")in(p=h)?Object.defineProperty(p,y,{value:b,enumerable:!0,configurable:!0,writable:!0}):p[y]=b,h.propTypes={buttons:r.default.arrayOf(r.default.oneOfType([r.default.string,r.default.object])),className:r.default.string,onOk:r.default.func,onClose:r.default.func,buttonClick:r.default.func,btnClass:r.default.string,defaultOk:r.default.string,defaultCancel:r.default.string}},838:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var o=l(n(1)),r=l(n(0));function l(e){return e&&e.__esModule?e:{default:e}}function u(e){return(u="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(e){return typeof e}:function(e){return e&&"function"==typeof Symbol&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":typeof e})(e)}function i(e,t){return(i=Object.setPrototypeOf||function(e,t){return e.__proto__=t,e})(e,t)}function a(e,t){for(var n=0;n<t.length;n++){var o=t[n];o.enumerable=o.enumerable||!1,o.configurable=!0,"value"in o&&(o.writable=!0),Object.defineProperty(e,o.key,o)}}function s(e,t){return!t||"object"!==u(t)&&"function"!=typeof t?function(e){if(void 0===e)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return e}(e):t}function c(e){return(c=Object.getPrototypeOf||function(e){return e.__proto__})(e)}var f,d,p,y=function(e){function t(){return function(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}(this,t),s(this,c(t).apply(this,arguments))}var n,r,l;return n=t,(r=[{key:"handleClick",value:function(){return this.props.onClick()}},{key:"render",value:function(){var e=this,t=this.props.className;return this.props.url&&"#"!==this.props.url?o.default.createElement("a",{href:this.props.url,target:"_blank",className:t},this.props.children):o.default.createElement("button",{onClick:function(){return e.handleClick()},className:t},this.props.children)}}])&&a(n.prototype,r),l&&a(n,l),function(e,t){if("function"!=typeof t&&null!==t)throw new TypeError("Super expression must either be null or a function");i(e.prototype,t&&t.prototype),t&&i(e,t)}(t,e),t}(o.default.Component);p={onClick:function(){},className:"btn",url:null},(d="defaultProps")in(f=y)?Object.defineProperty(f,d,{value:p,enumerable:!0,configurable:!0,writable:!0}):f[d]=p,y.propTypes={onClick:r.default.func,className:r.default.string,children:r.default.node.isRequired,url:r.default.string};var b=y;t.default=b},839:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var o=a(n(1)),r=a(n(0)),l=a(n(14)),u=a(n(840)),i=a(n(841));function a(e){return e&&e.__esModule?e:{default:e}}function s(e){return(s="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(e){return typeof e}:function(e){return e&&"function"==typeof Symbol&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":typeof e})(e)}function c(e,t){for(var n=0;n<t.length;n++){var o=t[n];o.enumerable=o.enumerable||!1,o.configurable=!0,"value"in o&&(o.writable=!0),Object.defineProperty(e,o.key,o)}}function f(e){return(f=Object.setPrototypeOf?Object.getPrototypeOf:function(e){return e.__proto__||Object.getPrototypeOf(e)})(e)}function d(e){if(void 0===e)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return e}function p(e,t){return(p=Object.setPrototypeOf||function(e,t){return e.__proto__=t,e})(e,t)}function y(){var e=function(e,t){t||(t=e.slice(0));return Object.freeze(Object.defineProperties(e,{raw:{value:Object.freeze(t)}}))}(["\n  display: flex;\n  justify-content: space-evenly;\n"]);return y=function(){return e},e}var b=l.default.div(y()),h=function(e){function t(e){var n,o,r;return function(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}(this,t),o=this,(n=!(r=f(t).call(this,e))||"object"!==s(r)&&"function"!=typeof r?d(o):r).state={selectedSmileyScore:0},n.onClick=n.onClick.bind(d(n)),n}var n,r,l;return function(e,t){if("function"!=typeof t&&null!==t)throw new TypeError("Super expression must either be null or a function");e.prototype=Object.create(t&&t.prototype,{constructor:{value:e,writable:!0,configurable:!0}}),t&&p(e,t)}(t,o.default.Component),n=t,(r=[{key:"onClick",value:function(e){this.setState({selectedSmileyScore:e}),this.props.onSelect(e)}},{key:"render",value:function(){var e=this;return o.default.createElement("div",null,o.default.createElement("p",{style:{paddingBottom:"1rem"}},"How satisfied are you?"),o.default.createElement(b,null,u.default.map(function(t,n){return o.default.createElement(i.default,{key:n,onClick:function(){return e.onClick(t.score)},emoji:t.emoji,label:t.label,selected:t.score===e.state.selectedSmileyScore})})),o.default.createElement("p",{style:{paddingTop:"1rem"}},o.default.createElement("a",{href:this.props.feedbackFormLink,target:"_blank"},"Click here if you need support.")))}}])&&c(n.prototype,r),l&&c(n,l),t}();h.propTypes={onSelect:r.default.func.isRequired,feedbackFormLink:r.default.string.isRequired};var m=h;t.default=m},840:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var o=[{emoji:"😞",label:"Terrible",score:1},{emoji:"😔",label:"Bad",score:2},{emoji:"😐",label:"Okay",score:3},{emoji:"😁",label:"Good",score:4},{emoji:"😆",label:"Great",score:5}];t.default=o},841:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var o=r(n(0));function r(e){return e&&e.__esModule?e:{default:e}}function l(){var e=function(e,t){t||(t=e.slice(0));return Object.freeze(Object.defineProperties(e,{raw:{value:Object.freeze(t)}}))}(["\n  transition: all 0.5s;\n  text-align: center;\n  cursor: pointer;\n  ::before {\n    font-size: 2.5rem;\n    transition: inherit;\n    content: '","';\n    opacity: ",";\n  }\n  :hover::before {\n    opacity: 1.0\n  }\n\n  ::after {\n    font-size: 1rem;\n    transition: inherit;\n    content: '","';\n    opacity: ",";\n  }\n  :hover::after {\n    opacity: 1.0\n  }\n\n"]);return l=function(){return e},e}var u=r(n(14)).default.div(l(),function(e){return e.emoji},function(e){return e.selected?1:.2},function(e){return e.label},function(e){return e.selected?1:0});u.propTypes={emoji:o.default.string.isRequired,label:o.default.string.isRequired,selected:o.default.bool.isRequired};var i=u;t.default=i}},[[829,0]]]);