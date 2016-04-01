var expressionAtlasHeatmapHighcharts=webpackJsonp_name_([4],{0:/*!******************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/index.js ***!
  \******************************************************/
function(e,t,i){"use strict";e.exports=i(866)},866:/*!******************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/src/highchartsHeatmapRenderer.js ***!
  \******************************************************************************/
function(e,t,i){"use strict";var s=i(867),o=i(1023),n=i(1024);t.render=function(e){var t=window.location.protocol+"//",i=void 0===e.atlasHost?"www.ebi.ac.uk":e.atlasHost,a="/gxa",r=t+i+a,l=e.proxyPrefix?e.proxyPrefix+"/"+i+a:r,c=e.isMultiExperiment?"/widgets/heatmap/baselineAnalytics":"/widgets/heatmap/referenceExperiment",p=l+c+"?"+e.params;o.render(s.createElement(n,{sourceURL:p,atlasBaseURL:l,linksAtlasBaseURL:r,isWidget:void 0===e.isWidget?!0:e.isWidget,disableGoogleAnalytics:void 0===e.disableGoogleAnalytics?!1:e.disableGoogleAnalytics,fail:e.fail}),"string"==typeof e.target?document.getElementById(e.target):e.target)}},867:/*!**************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/react.js ***!
  \**************************************************************/
[1333,868],868:/*!******************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/React.js ***!
  \******************************************************************/
[1334,869,1013,1017,904,1022],869:/*!*********************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactDOM.js ***!
  \*********************************************************************/
[1335,870,871,936,910,893,883,915,919,1011,956,1012,890,874],870:5,871:/*!**********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactDOMTextComponent.js ***!
  \**********************************************************************************/
[1336,872,887,891,893,904,886,885,935],872:/*!**********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/DOMChildrenOperations.js ***!
  \**********************************************************************************/
[1337,873,881,883,884,885,878],873:/*!*******************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/Danger.js ***!
  \*******************************************************************/
[1338,874,875,880,879,878],874:9,875:/*!*****************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/~/fbjs/lib/createNodesFromMarkup.js ***!
  \*****************************************************************************************/
[1339,874,876,879,878],876:/*!****************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/~/fbjs/lib/createArrayFromMixed.js ***!
  \****************************************************************************************/
[1340,877],877:/*!***************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/~/fbjs/lib/toArray.js ***!
  \***************************************************************************/
[1341,878],878:13,879:/*!*********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/~/fbjs/lib/getMarkupWrap.js ***!
  \*********************************************************************************/
[1342,874,878],880:15,881:/*!***************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactMultiChildUpdateTypes.js ***!
  \***************************************************************************************/
[1343,882],882:/*!*****************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/~/fbjs/lib/keyMirror.js ***!
  \*****************************************************************************/
[1344,878],883:18,884:/*!*************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/setInnerHTML.js ***!
  \*************************************************************************/
[1345,874],885:/*!***************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/setTextContent.js ***!
  \***************************************************************************/
[1346,874,886,884],886:21,887:/*!**********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/DOMPropertyOperations.js ***!
  \**********************************************************************************/
[1347,888,883,889,890],888:/*!************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/DOMProperty.js ***!
  \************************************************************************/
[1348,878],889:/*!******************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/quoteAttributeValueForBrowser.js ***!
  \******************************************************************************************/
[1349,886],890:/*!***************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/~/fbjs/lib/warning.js ***!
  \***************************************************************************/
[1350,880],891:/*!*********************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactComponentBrowserEnvironment.js ***!
  \*********************************************************************************************/
[1351,892,893],892:/*!*********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactDOMIDOperations.js ***!
  \*********************************************************************************/
[1352,872,887,893,883,878],893:/*!***********************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactMount.js ***!
  \***********************************************************************/
[1353,888,894,870,906,907,909,910,912,913,883,915,918,919,904,923,924,927,878,884,932,935,890],894:/*!*************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactBrowserEventEmitter.js ***!
  \*************************************************************************************/
[1354,895,896,897,902,883,903,904,905],895:/*!***************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/EventConstants.js ***!
  \***************************************************************************/
[1355,882],896:/*!***************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/EventPluginHub.js ***!
  \***************************************************************************/
[1356,897,898,899,900,901,878,890],897:/*!********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/EventPluginRegistry.js ***!
  \********************************************************************************/
[1357,878],898:/*!*****************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/EventPluginUtils.js ***!
  \*****************************************************************************/
[1358,895,899,878,890],899:34,900:/*!***************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/accumulateInto.js ***!
  \***************************************************************************/
[1359,878],901:36,902:/*!***********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactEventEmitterMixin.js ***!
  \***********************************************************************************/
[1360,896],903:38,904:39,905:/*!*****************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/isEventSupported.js ***!
  \*****************************************************************************/
[1361,874],906:41,907:/*!*************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactElement.js ***!
  \*************************************************************************/
[1362,870,904,908],908:43,909:44,910:/*!*********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactInstanceHandles.js ***!
  \*********************************************************************************/
[1363,911,878],911:46,912:47,913:/*!********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactMarkupChecksum.js ***!
  \********************************************************************************/
[1364,914],914:49,915:/*!****************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactReconciler.js ***!
  \****************************************************************************/
[1365,916],916:/*!*********************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactRef.js ***!
  \*********************************************************************/
[1366,917],917:/*!***********************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactOwner.js ***!
  \***********************************************************************/
[1367,878],918:/*!*****************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactUpdateQueue.js ***!
  \*****************************************************************************/
[1368,870,907,912,919,904,878,890],919:/*!*************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactUpdates.js ***!
  \*************************************************************************/
[1369,920,921,883,915,922,904,878],920:/*!**************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/CallbackQueue.js ***!
  \**************************************************************************/
[1370,921,904,878],921:/*!************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/PooledClass.js ***!
  \************************************************************************/
[1371,878],922:/*!************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/Transaction.js ***!
  \************************************************************************/
[1372,878],923:58,924:/*!********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/~/fbjs/lib/containsNode.js ***!
  \********************************************************************************/
[1373,925],925:/*!******************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/~/fbjs/lib/isTextNode.js ***!
  \******************************************************************************/
[1374,926],926:61,927:/*!**************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/instantiateReactComponent.js ***!
  \**************************************************************************************/
[1375,928,933,934,904,878,890],928:/*!************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactCompositeComponent.js ***!
  \************************************************************************************/
[1376,929,870,907,912,883,930,931,915,918,904,923,878,932,890],929:/*!**************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactComponentEnvironment.js ***!
  \**************************************************************************************/
[1377,878],930:/*!***********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactPropTypeLocations.js ***!
  \***********************************************************************************/
[1378,882],931:66,932:67,933:/*!********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactEmptyComponent.js ***!
  \********************************************************************************/
function(e,t,i){"use strict";function s(){a.registerNullComponentID(this._rootNodeID)}var o,n=i(907),a=i(909),r=i(915),l=i(904),c={injectEmptyComponent:function(e){o=n.createElement(e)}},p=function(e){this._currentElement=null,this._rootNodeID=null,this._renderedComponent=e(o)};l(p.prototype,{construct:function(e){},mountComponent:function(e,t,i){return t.getReactMountReady().enqueue(s,this),this._rootNodeID=e,r.mountComponent(this._renderedComponent,e,t,i)},receiveComponent:function(){},unmountComponent:function(e,t,i){r.unmountComponent(this._renderedComponent),a.deregisterNullComponentID(this._rootNodeID),this._rootNodeID=null,this._renderedComponent=null}}),p.injection=c,e.exports=p},934:/*!*********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactNativeComponent.js ***!
  \*********************************************************************************/
[1379,904,878],935:/*!*******************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/validateDOMNesting.js ***!
  \*******************************************************************************/
[1380,904,880,890],936:/*!**********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactDefaultInjection.js ***!
  \**********************************************************************************/
[1381,937,945,948,949,950,874,954,955,891,957,958,871,983,986,910,893,990,995,996,997,1006,1007],937:/*!***********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/BeforeInputEventPlugin.js ***!
  \***********************************************************************************/
[1382,895,938,874,939,941,943,944],938:/*!*****************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/EventPropagators.js ***!
  \*****************************************************************************/
[1383,895,896,890,900,901],939:/*!*************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/FallbackCompositionState.js ***!
  \*************************************************************************************/
[1384,921,904,940],940:/*!***********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/getTextContentAccessor.js ***!
  \***********************************************************************************/
[1385,874],941:/*!**************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/SyntheticCompositionEvent.js ***!
  \**************************************************************************************/
[1386,942],942:/*!***************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/SyntheticEvent.js ***!
  \***************************************************************************/
[1387,921,904,880,890],943:/*!********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/SyntheticInputEvent.js ***!
  \********************************************************************************/
[1388,942],944:79,945:/*!******************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ChangeEventPlugin.js ***!
  \******************************************************************************/
[1389,895,896,938,874,919,942,946,905,947,944],946:81,947:82,948:83,949:/*!************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/DefaultEventPluginOrder.js ***!
  \************************************************************************************/
[1390,944],950:/*!**********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/EnterLeaveEventPlugin.js ***!
  \**********************************************************************************/
[1391,895,938,951,893,944],951:/*!********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/SyntheticMouseEvent.js ***!
  \********************************************************************************/
[1392,952,903,953],952:/*!*****************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/SyntheticUIEvent.js ***!
  \*****************************************************************************/
[1393,942,946],953:88,954:/*!**********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/HTMLDOMPropertyConfig.js ***!
  \**********************************************************************************/
[1394,888,874],955:/*!***************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactBrowserComponentMixin.js ***!
  \***************************************************************************************/
[1395,912,956,890],956:/*!************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/findDOMNode.js ***!
  \************************************************************************/
[1396,870,912,893,878,890],957:/*!*****************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactDefaultBatchingStrategy.js ***!
  \*****************************************************************************************/
[1397,919,922,904,880],958:/*!******************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactDOMComponent.js ***!
  \******************************************************************************/
[1398,959,961,888,887,895,894,891,969,970,974,977,978,893,979,883,918,904,908,886,878,905,944,884,885,982,935,890],959:/*!***************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/AutoFocusUtils.js ***!
  \***************************************************************************/
[1399,893,956,960],960:95,961:/*!**********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/CSSPropertyOperations.js ***!
  \**********************************************************************************/
[1400,962,874,883,963,965,966,968,890],962:97,963:/*!*************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/~/fbjs/lib/camelizeStyleName.js ***!
  \*************************************************************************************/
[1401,964],964:99,965:/*!********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/dangerousStyleValue.js ***!
  \********************************************************************************/
[1402,962],966:/*!**************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/~/fbjs/lib/hyphenateStyleName.js ***!
  \**************************************************************************************/
[1403,967],967:102,968:103,969:104,970:/*!**************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactDOMInput.js ***!
  \**************************************************************************/
[1404,892,971,893,919,904,878],971:/*!*****************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/LinkedValueUtils.js ***!
  \*****************************************************************************/
[1405,972,930,878,890],972:/*!***************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactPropTypes.js ***!
  \***************************************************************************/
[1406,907,931,880,973],973:108,974:/*!***************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactDOMOption.js ***!
  \***************************************************************************/
[1407,975,977,904,890],975:/*!**************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactChildren.js ***!
  \**************************************************************************/
[1408,921,907,880,976],976:/*!********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/traverseAllChildren.js ***!
  \********************************************************************************/
[1409,870,907,910,973,878,890],977:/*!***************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactDOMSelect.js ***!
  \***************************************************************************/
[1410,971,893,919,904,890],978:/*!*****************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactDOMTextarea.js ***!
  \*****************************************************************************/
[1411,971,892,919,904,878,890],979:/*!****************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactMultiChild.js ***!
  \****************************************************************************/
[1412,929,881,870,915,980,981],980:/*!*********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactChildReconciler.js ***!
  \*********************************************************************************/
[1413,915,927,932,976,890],981:/*!****************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/flattenChildren.js ***!
  \****************************************************************************/
[1414,976,890],982:117,983:/*!*******************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactEventListener.js ***!
  \*******************************************************************************/
[1415,984,874,921,910,893,919,904,946,985],984:/*!*********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/~/fbjs/lib/EventListener.js ***!
  \*********************************************************************************/
[1416,880],985:120,986:/*!***************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactInjection.js ***!
  \***************************************************************************/
[1417,888,896,929,987,933,894,934,883,911,919],987:/*!***********************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactClass.js ***!
  \***********************************************************************/
[1418,988,907,930,931,989,904,923,878,882,944,890],988:/*!***************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactComponent.js ***!
  \***************************************************************************/
[1419,989,908,923,878,890],989:/*!*********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactNoopUpdateQueue.js ***!
  \*********************************************************************************/
[1420,890],990:/*!**************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactReconcileTransaction.js ***!
  \**************************************************************************************/
[1421,920,921,894,906,991,922,904],991:/*!********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactInputSelection.js ***!
  \********************************************************************************/
[1422,992,924,960,994],992:/*!******************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactDOMSelection.js ***!
  \******************************************************************************/
[1423,874,993,940],993:128,994:129,995:/*!******************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/SelectEventPlugin.js ***!
  \******************************************************************************/
[1424,895,938,874,991,942,994,947,944,982],996:131,997:/*!******************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/SimpleEventPlugin.js ***!
  \******************************************************************************/
[1425,895,984,938,893,998,942,999,1e3,951,1003,1004,952,1005,880,1001,878,944],998:/*!************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/SyntheticClipboardEvent.js ***!
  \************************************************************************************/
[1426,942],999:/*!********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/SyntheticFocusEvent.js ***!
  \********************************************************************************/
[1427,952],1e3:/*!***********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/SyntheticKeyboardEvent.js ***!
  \***********************************************************************************/
[1428,952,1001,1002,953],1001:136,1002:/*!************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/getEventKey.js ***!
  \************************************************************************/
[1429,1001],1003:/*!*******************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/SyntheticDragEvent.js ***!
  \*******************************************************************************/
[1430,951],1004:/*!********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/SyntheticTouchEvent.js ***!
  \********************************************************************************/
[1431,952,953],1005:/*!********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/SyntheticWheelEvent.js ***!
  \********************************************************************************/
[1432,951],1006:/*!*********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/SVGDOMPropertyConfig.js ***!
  \*********************************************************************************/
[1433,888],1007:/*!*****************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactDefaultPerf.js ***!
  \*****************************************************************************/
[1434,888,1008,893,883,1009],1008:/*!*************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactDefaultPerfAnalysis.js ***!
  \*************************************************************************************/
[1435,904],1009:/*!**********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/~/fbjs/lib/performanceNow.js ***!
  \**********************************************************************************/
[1436,1010],1010:/*!*******************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/~/fbjs/lib/performance.js ***!
  \*******************************************************************************/
[1437,874],1011:/*!*************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactVersion.js ***!
  \*************************************************************************/
function(e,t){"use strict";e.exports="0.14.8"},1012:/*!***************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/renderSubtreeIntoContainer.js ***!
  \***************************************************************************************/
[1438,893],1013:/*!***************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactDOMServer.js ***!
  \***************************************************************************/
[1439,936,1014,1011],1014:/*!*********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactServerRendering.js ***!
  \*********************************************************************************/
[1440,957,907,910,913,1015,1016,919,923,927,878],1015:150,1016:/*!********************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactServerRenderingTransaction.js ***!
  \********************************************************************************************/
[1441,921,920,922,904,880],1017:/*!****************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactIsomorphic.js ***!
  \****************************************************************************/
[1442,975,988,987,1018,907,1019,972,1011,904,1021],1018:/*!******************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactDOMFactories.js ***!
  \******************************************************************************/
[1443,907,1019,1020],1019:/*!**********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactElementValidator.js ***!
  \**********************************************************************************/
[1444,907,930,931,870,908,973,878,890],1020:155,1021:/*!**********************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/onlyChild.js ***!
  \**********************************************************************/
[1445,907,878],1022:/*!***********************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/deprecated.js ***!
  \***********************************************************************/
[1446,904,890],1023:/*!******************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-dom/index.js ***!
  \******************************************************************/
[1447,869],1024:/*!********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/src/HighchartsHeatmapContainer.jsx ***!
  \********************************************************************************/
function(e,t,i){"use strict";var s=i(867),o=i(1025);i(1026);var n=i(1027),a=i(1035),r=s.createClass({displayName:"ExperimentDescription",propTypes:{linksAtlasBaseURL:s.PropTypes.string.isRequired,experiment:s.PropTypes.shape({URL:s.PropTypes.string.isRequired,description:s.PropTypes.string.isRequired,allSpecies:s.PropTypes.string.isRequired}).isRequired},render:function(){var e=this.props.linksAtlasBaseURL+this.props.experiment.URL;return s.createElement("div",{style:{width:"100%",paddingBottom:"20px"}},s.createElement("div",{id:"experimentDescription"},s.createElement("a",{id:"goto-experiment",className:"gxaThickLink",title:"Experiment Page",href:e},this.props.experiment.description)),s.createElement("div",{id:"experimentOrganisms"},"Organism(s): ",s.createElement("span",{style:{fontStyle:"italic"}},this.props.experiment.allSpecies)))}}),l=s.createClass({displayName:"HighchartsHeatmapContainer",propTypes:{sourceURL:s.PropTypes.string.isRequired,atlasBaseURL:s.PropTypes.string.isRequired,linksAtlasBaseURL:s.PropTypes.string.isRequired,isWidget:s.PropTypes.bool.isRequired,disableGoogleAnalytics:s.PropTypes.bool.isRequired,fail:s.PropTypes.func},render:function(){var e=this.props.linksAtlasBaseURL+"/query?geneQuery="+this.state.heatmapConfig.geneQuery+"&exactMatch="+this.state.heatmapConfig.isExactMatch+"&organism="+this.state.heatmapConfig.species;return s.createElement("div",{ref:"this"},this.state.experimentData?s.createElement(r,{experiment:this.state.experimentData,linksAtlasBaseURL:this.props.linksAtlasBaseURL}):null,this.state.heatmapConfig?s.createElement("div",{id:"heatmap-anatomogram",className:"gxaHeatmapAnatomogramRow"},s.createElement("div",{id:"heatmap-react",className:"gxaInnerHeatmap"},s.createElement(n,{profiles:this.state.profiles,atlasBaseURL:this.props.atlasBaseURL,xAxisCategories:this.state.xAxisCategories,yAxisCategories:this.state.yAxisCategories,yAxisCategoriesLinks:this.state.yAxisCategoriesLinks,seriesDataNA:this.state.seriesDataNA,seriesDataNAString:this.state.seriesDataNAString,seriesDataBelowCutoff:this.state.seriesDataBelowCutoff,seriesDataBelowCutoffString:this.state.seriesDataBelowCutoffString,seriesDataRanges:this.state.seriesDataRanges}))):s.createElement("div",{ref:"loadingImagePlaceholder"},s.createElement("img",{src:this.props.atlasBaseURL+"/resources/images/loading.gif"})),this.props.isWidget?s.createElement("div",null,s.createElement("p",null,s.createElement("a",{href:e},"See more expression data at Expression Atlas."),s.createElement("br",null),"This expression view is provided by ",s.createElement("a",{href:this.props.linksAtlasBaseURL},"Expression Atlas"),".",s.createElement("br",null),"Please direct any queries or feedback to ",s.createElement("a",{href:"mailto:arrayexpress-atlas@ebi.ac.uk"},"arrayexpress-atlas@ebi.ac.uk"))):null)},getInitialState:function(){return{heatmapConfig:"",profiles:{rows:[],minExpressionLevel:0,maxExpressionLevel:0},xAxisCategories:{},yAxisCategories:{},yAxisCategoriesLinks:{},seriesDataNA:[],seriesDataNAString:"NA",seriesDataBelowCutoff:[],seriesDataBelowCutoffString:"Below cutoff",seriesDataRanges:[]}},componentDidMount:function(){var e={url:this.props.sourceURL,dataType:"json",method:"GET"};if(o.ajax(e).done(function(e){if(this.isMounted()){for(var t=a.getXAxisCategories(e.columnHeaders),i=a.getYAxisCategories(e.profiles,e.config),s=a.getYAxisCategoriesLinks(),o=[],n="NA",r=[],l="Below cutoff",c=[{label:"Low",from:0,to:10,seriesData:[]},{label:"Medium",from:10,to:1e3,seriesData:[]},{label:"High",from:1e3,to:1e5,seriesData:[]}],p=[],d=0;d<e.profiles.rows.length;d++)-1===p.indexOf(e.profiles.rows[d].experimentType)&&p.push(e.profiles.rows[d].experimentType);for(var d=0;d<p.length;d++){for(var f=[],h=0;h<i.length;h++)if(e.profiles.rows[h].experimentType===p[d])for(var u=0;u<t.length;u++){var g=e.profiles.rows[h].expressions[u].value;if(""===g)r.push([u,h,l]);else if("NT"===g)o.push([u,h,n]);else{var m=parseFloat(g);isNaN(m)||f.push([u,h,m])}}f.sort(function(e,t){return e[2]-t[2]});for(var b=f[f.length-1][2],u=0;u<c.length;u++)f.filter(function(e){return e[2]>c[u].from*b&&e[2]<=c[u].to*b}).forEach(function(e){c[u].seriesData.push(e)})}this.setState({heatmapConfig:e.config,columnHeaders:e.columnHeaders,nonExpressedColumnHeaders:e.nonExpressedColumnHeaders,profiles:e.profiles,geneSetProfiles:e.geneSetProfiles,experimentData:e.experiment,xAxisCategories:t,yAxisCategories:i,yAxisCategoriesLinks:s,seriesDataBelowCutoff:r,seriesDataBelowCutoffString:l,seriesDataNA:o,seriesDataNAString:n,seriesDataRanges:c})}}.bind(this)).fail(function(e,t,i){this.props.fail?this.props.fail(e,t,i):"parsererror"===t?o(this.refs["this"].getDOMNode()).html("<div class='gxaError'>Could not parse JSON response</div>"):o(this.refs["this"].getDOMNode()).html(e.responseText)}.bind(this)),!this.props.disableGoogleAnalytics){var t=t||[];t.push(["_setAccount","UA-37676851-1"]),t.push(["_trackPageview"]),function(){var e=document.createElement("script");e.type="text/javascript",e.async=!0,e.src=("https:"==document.location.protocol?"https://ssl":"http://www")+".google-analytics.com/ga.js";var t=document.getElementsByTagName("script")[0];t.parentNode.insertBefore(e,t)}()}}});e.exports=l},1025:404,1026:/*!****************************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/jQuery-ajaxTransport-XDomainRequest/jquery.xdomainrequest.min.js ***!
  \****************************************************************************************************************/
[1470,1025],1027:/*!***********************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/src/HighchartsHeatmap.jsx ***!
  \***********************************************************************/
function(e,t,i){"use strict";var s=i(867),o=i(1028);i(1030)(o.Highcharts),i(1031);var n=s.createClass({displayName:"HighchartsHeatmap",propTypes:{profiles:s.PropTypes.object.isRequired,atlasBaseURL:s.PropTypes.string.isRequired},getInitialState:function(){return{xAxisCategories:{},yAxisCategories:{},yAxisCategoriesLinks:{},seriesDataNA:[],seriesDataNAString:"NA",seriesDataBelowCutoff:[],seriesDataBelowCutoffString:"Below cutoff",seriesDataRanges:[{label:"Low",from:0,to:10,seriesData:[]},{label:"Medium",from:10,to:1e3,seriesData:[]},{label:"High",from:1e3,to:1e5,seriesData:[]}],legend_1:!1,legend_2:!1,legend_3:!1,legend_4:!1}},handleClick:function(e){1==e?this.setState({legend_1:!this.state.legend_1}):2==e?this.setState({legend_2:!this.state.legend_2}):3==e?this.setState({legend_3:!this.state.legend_3}):4==e&&this.setState({legend_4:!this.state.legend_4})},componentDidMount:function(){var e=this.refs.chart.getChart();e.series[0].hide()},componentDidUpdate:function(){var e=this.refs.chart.getChart();e.series[0].hide(),this.state.legend_1?e.series[1].hide():e.series[1].show(),this.state.legend_2?e.series[2].hide():e.series[2].show(),this.state.legend_3?e.series[3].hide():e.series[3].show(),this.state.legend_4?e.series[4].hide():e.series[4].show()},render:function(){var e=this.props.atlasBaseURL,t=this.props.yAxisCategoriesLinks,i=this.props.yAxisCategories,n={plotOptions:{series:{states:{hover:{color:"#eeec38"}}}},credits:{enabled:!1},chart:{type:"heatmap",marginTop:82,marginRight:36,plotBorderWidth:1,height:12*i.length+200,zoomType:"xy"},legend:{useHTML:!0,enabled:!1,itemDistance:18,symbolWidth:12,symbolHeight:12,x:150,align:"left",verticalAlign:"bottom",layout:"horizontal",itemStyle:{lineHeight:"10px",fontSize:"10px",color:"#606060",fontWeight:"normal"}},title:null,xAxis:{tickLength:5,tickColor:"rgb(192, 192, 192)",lineColor:"rgb(192, 192, 192)",labels:{y:-6,style:{fontSize:"9px"},autoRotation:[-45,-90]},opposite:"true",categories:this.props.xAxisCategories},yAxis:{useHTML:!0,reversed:!0,labels:{style:{fontSize:"10px",color:"#148ff3"},formatter:function(){return'<a class="project_link"  href="'+e+"/experiments/"+t[this.value]+'">'+this.value+"</a>"}},categories:this.props.yAxisCategories,title:null,gridLineWidth:0,minorGridLineWidth:0},tooltip:{useHTML:!0,formatter:function(){return"Sample name: <b>"+this.series.yAxis.categories[this.point.y]+"</b>  <br> Tissue: <b>"+this.series.xAxis.categories[this.point.x]+'</b><br><b></b><span style="border:1px rgb(192, 192, 192) solid; margin-right: 2px; width:6px; height:6px; display:inline-block; background-color:'+this.point.color+';"></span> Expression level: <b></span>Expression level: <b>'+this.point.value+"</b>"}},series:[{name:this.props.seriesDataNAString,color:"#f7f7f7",borderWidth:1,borderColor:"#fff",data:this.props.seriesDataNA},{name:this.props.seriesDataBelowCutoffString,color:"#eaeaea",borderWidth:1,borderColor:"#fff",data:this.props.seriesDataBelowCutoff},{name:this.props.seriesDataRanges[0].label,color:"#dff8ff",borderWidth:1,borderColor:"#fff",data:this.props.seriesDataRanges[0].seriesData},{name:this.props.seriesDataRanges[1].label,color:"#9fd2fa",borderWidth:1,borderColor:"#fff",data:this.props.seriesDataRanges[1].seriesData},{name:this.props.seriesDataRanges[2].label,color:"#45affd",borderWidth:1,borderColor:"#fff",data:this.props.seriesDataRanges[2].seriesData}]},a=this.state.legend_1?"legend-item legend-item-off":"legend-item",r=this.state.legend_2?"legend-item legend-item-off":"legend-item",l=this.state.legend_3?"legend-item legend-item-off":"legend-item",c=this.state.legend_4?"legend-item legend-item-off":"legend-item",p=s.createElement("div",{id:"barcharts_legend_list_items",ref:"barcharts_legend_items"},s.createElement("span",null,"Click to interact:"),s.createElement("div",{id:"legend_1",ref:"legend_1",className:a,onClick:this.handleClick.bind(this,1)},s.createElement("div",{className:"legend-rectangle col_below"}),s.createElement("span",null,"Below cutoff")),s.createElement("div",{id:"legend_2",className:r,onClick:this.handleClick.bind(this,2)},s.createElement("div",{className:"legend-rectangle col_low"}),s.createElement("span",null,"Low")),s.createElement("div",{id:"legend_3",className:l,onClick:this.handleClick.bind(this,3)},s.createElement("div",{className:"legend-rectangle col_med"}),s.createElement("span",null,"Medium")),s.createElement("div",{id:"legend_4",className:c,onClick:this.handleClick.bind(this,4)},s.createElement("div",{className:"legend-rectangle col_high"}),s.createElement("span",null,"High")),s.createElement("div",{className:"legend-item"},s.createElement("span",{className:"icon icon-generic","data-icon":"i","data-toggle":"tooltip","data-placement":"bottom",title:"This range of value indicates gene expression level accross different tissues. It is calculated in different a different way between DNA and RNA experiments"})),s.createElement("div",{id:"legend_5",className:"legend-item special"},s.createElement("div",{className:"legend-rectangle col_nd"}),s.createElement("span",null,"No data available")));return s.createElement("div",null,s.createElement("div",{id:"highcharts_container"},s.createElement(o,{config:n,ref:"chart"}),p))}});e.exports=n},1028:/*!****************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-highcharts/dist/ReactHighcharts.js ***!
  \****************************************************************************************/
[1474,867,1029],1029:423,1030:/*!*****************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/highcharts-heatmap/heatmap.js ***!
  \*****************************************************************************/
function(e,t){!function(t){"object"==typeof e&&e.exports?e.exports=t:t(Highcharts)}(function(e){var t=e.Axis,i=e.Chart,s=e.Color,o=e.Legend,n=e.LegendSymbolMixin,a=e.Series,r=e.Point,l=e.getOptions(),c=e.each,p=e.extend,d=e.extendClass,f=e.merge,h=e.pick,u=e.seriesTypes,g=e.wrap,m=function(){},b=e.ColorAxis=function(){this.isColorAxis=!0,this.init.apply(this,arguments)};p(b.prototype,t.prototype),p(b.prototype,{defaultColorAxisOptions:{lineWidth:0,minPadding:0,maxPadding:0,gridLineWidth:1,tickPixelInterval:72,startOnTick:!0,endOnTick:!0,offset:0,marker:{animation:{duration:50},color:"gray",width:.01},labels:{overflow:"justify"},minColor:"#EFEFFF",maxColor:"#003875",tickLength:5},init:function(e,i){var s,o="vertical"!==e.options.legend.layout;s=f(this.defaultColorAxisOptions,{side:o?2:1,reversed:!o},i,{opposite:!o,showEmpty:!1,title:null,isColor:!0}),t.prototype.init.call(this,e,s),i.dataClasses&&this.initDataClasses(i),this.initStops(i),this.horiz=o,this.zoomEnabled=!1},tweenColors:function(e,t,i){var s;return t.rgba.length&&e.rgba.length?(e=e.rgba,t=t.rgba,s=1!==t[3]||1!==e[3],e=(s?"rgba(":"rgb(")+Math.round(t[0]+(e[0]-t[0])*(1-i))+","+Math.round(t[1]+(e[1]-t[1])*(1-i))+","+Math.round(t[2]+(e[2]-t[2])*(1-i))+(s?","+(t[3]+(e[3]-t[3])*(1-i)):"")+")"):e=t.input||"none",e},initDataClasses:function(e){var t,i=this,o=this.chart,n=0,a=this.options,r=e.dataClasses.length;this.dataClasses=t=[],this.legendItems=[],c(e.dataClasses,function(e,l){var c,e=f(e);t.push(e),e.color||("category"===a.dataClassColor?(c=o.options.colors,e.color=c[n++],n===c.length&&(n=0)):e.color=i.tweenColors(s(a.minColor),s(a.maxColor),2>r?.5:l/(r-1)))})},initStops:function(e){this.stops=e.stops||[[0,this.options.minColor],[1,this.options.maxColor]],c(this.stops,function(e){e.color=s(e[1])})},setOptions:function(e){t.prototype.setOptions.call(this,e),this.options.crosshair=this.options.marker,this.coll="colorAxis"},setAxisSize:function(){var e,t,i,s=this.legendSymbol,o=this.chart;s&&(this.left=e=s.attr("x"),this.top=t=s.attr("y"),this.width=i=s.attr("width"),this.height=s=s.attr("height"),this.right=o.chartWidth-e-i,this.bottom=o.chartHeight-t-s,this.len=this.horiz?i:s,this.pos=this.horiz?e:t)},toColor:function(e,t){var i,s,o,n,a=this.stops,r=this.dataClasses;if(r){for(n=r.length;n--;)if(o=r[n],s=o.from,a=o.to,(void 0===s||e>=s)&&(void 0===a||a>=e)){i=o.color,t&&(t.dataClass=n);break}}else{for(this.isLog&&(e=this.val2lin(e)),i=1-(this.max-e)/(this.max-this.min||1),n=a.length;n--&&!(i>a[n][0]););s=a[n]||a[n+1],a=a[n+1]||s,i=1-(a[0]-i)/(a[0]-s[0]||1),i=this.tweenColors(s.color,a.color,i)}return i},getOffset:function(){var e=this.legendGroup,i=this.chart.axisOffset[this.side];e&&(this.axisParent=e,t.prototype.getOffset.call(this),this.added||(this.added=!0,this.labelLeft=0,this.labelRight=this.width),this.chart.axisOffset[this.side]=i)},setLegendColor:function(){var e,t=this.options,i=this.reversed;e=i?1:0,i=i?0:1,e=this.horiz?[e,0,i,0]:[0,i,0,e],this.legendColor={linearGradient:{x1:e[0],y1:e[1],x2:e[2],y2:e[3]},stops:t.stops||[[0,t.minColor],[1,t.maxColor]]}},drawLegendSymbol:function(e,t){var i=e.padding,s=e.options,o=this.horiz,n=h(s.symbolWidth,o?200:12),a=h(s.symbolHeight,o?12:200),r=h(s.labelPadding,o?16:30),s=h(s.itemDistance,10);this.setLegendColor(),t.legendSymbol=this.chart.renderer.rect(0,e.baseline-11,n,a).attr({zIndex:1}).add(t.legendGroup),this.legendItemWidth=n+i+(o?s:r),this.legendItemHeight=a+i+(o?r:0)},setState:m,visible:!0,setVisible:m,getSeriesExtremes:function(){var e;this.series.length&&(e=this.series[0],this.dataMin=e.valueMin,this.dataMax=e.valueMax)},drawCrosshair:function(e,i){var s,o=i&&i.plotX,n=i&&i.plotY,a=this.pos,r=this.len;i&&(s=this.toPixels(i[i.series.colorKey]),a>s?s=a-2:s>a+r&&(s=a+r+2),i.plotX=s,i.plotY=this.len-s,t.prototype.drawCrosshair.call(this,e,i),i.plotX=o,i.plotY=n,this.cross&&this.cross.attr({fill:this.crosshair.color}).add(this.legendGroup))},getPlotLinePath:function(e,i,s,o,n){return"number"==typeof n?this.horiz?["M",n-4,this.top-6,"L",n+4,this.top-6,n,this.top,"Z"]:["M",this.left,n,"L",this.left-6,n+6,this.left-6,n-6,"Z"]:t.prototype.getPlotLinePath.call(this,e,i,s,o)},update:function(e,i){var s=this.chart,o=s.legend;c(this.series,function(e){e.isDirtyData=!0}),e.dataClasses&&o.allItems&&(c(o.allItems,function(e){e.isDataClass&&e.legendGroup.destroy()}),s.isDirtyLegend=!0),s.options[this.coll]=f(this.userOptions,e),t.prototype.update.call(this,e,i),this.legendItem&&(this.setLegendColor(),o.colorizeItem(this,!0))},getDataClassLegendSymbols:function(){var t,i=this,s=this.chart,o=this.legendItems,a=s.options.legend,r=a.valueDecimals,l=a.valueSuffix||"";return o.length||c(this.dataClasses,function(a,d){var f=!0,h=a.from,u=a.to;t="",void 0===h?t="< ":void 0===u&&(t="> "),void 0!==h&&(t+=e.numberFormat(h,r)+l),void 0!==h&&void 0!==u&&(t+=" - "),void 0!==u&&(t+=e.numberFormat(u,r)+l),o.push(p({chart:s,name:t,options:{},drawLegendSymbol:n.drawRectangle,visible:!0,setState:m,isDataClass:!0,setVisible:function(){f=this.visible=!f,c(i.series,function(e){c(e.points,function(e){e.dataClass===d&&e.setVisible(f)})}),s.legend.colorizeItem(this,f)}},a))}),o},name:""}),c(["fill","stroke"],function(t){e.Fx.prototype[t+"Setter"]=function(){this.elem.attr(t,b.prototype.tweenColors(s(this.start),s(this.end),this.pos))}}),g(i.prototype,"getAxes",function(e){var t=this.options.colorAxis;e.call(this),this.colorAxis=[],t&&new b(this,t)}),g(o.prototype,"getAllItems",function(e){var t=[],i=this.chart.colorAxis[0];return i&&(i.options.dataClasses?t=t.concat(i.getDataClassLegendSymbols()):t.push(i),c(i.series,function(e){e.options.showInLegend=!1})),t.concat(e.call(this))}),i={pointAttrToOptions:{stroke:"borderColor","stroke-width":"borderWidth",fill:"color",dashstyle:"dashStyle"},pointArrayMap:["value"],axisTypes:["xAxis","yAxis","colorAxis"],optionalAxis:"colorAxis",trackerGroups:["group","markerGroup","dataLabelsGroup"],getSymbol:m,parallelArrays:["x","y","value"],colorKey:"value",translateColors:function(){var e=this,t=this.options.nullColor,i=this.colorAxis,s=this.colorKey;c(this.data,function(o){var n=o[s];(n=o.options.color||(null===n?t:i&&void 0!==n?i.toColor(n,o):o.color||e.color))&&(o.color=n)})}},l.plotOptions.heatmap=f(l.plotOptions.scatter,{animation:!1,borderWidth:0,nullColor:"#F8F8F8",dataLabels:{formatter:function(){return this.point.value},inside:!0,verticalAlign:"middle",crop:!1,overflow:!1,padding:0},marker:null,pointRange:null,tooltip:{pointFormat:"{point.x}, {point.y}: {point.value}<br/>"},states:{normal:{animation:!0},hover:{halo:!1,brightness:.2}}}),u.heatmap=d(u.scatter,f(i,{type:"heatmap",pointArrayMap:["y","value"],hasPointSpecificOptions:!0,pointClass:d(r,{setVisible:function(e){var t=this,i=e?"show":"hide";c(["graphic","dataLabel"],function(e){t[e]&&t[e][i]()})}}),supportsDrilldown:!0,getExtremesFromAll:!0,directTouch:!0,init:function(){var e;u.scatter.prototype.init.apply(this,arguments),e=this.options,e.pointRange=h(e.pointRange,e.colsize||1),this.yAxis.axisPointRange=e.rowsize||1},translate:function(){var e=this.options,t=this.xAxis,i=this.yAxis,s=function(e,t,i){return Math.min(Math.max(t,e),i)};this.generatePoints(),c(this.points,function(o){var n=(e.colsize||1)/2,a=(e.rowsize||1)/2,r=s(Math.round(t.len-t.translate(o.x-n,0,1,0,1)),0,t.len),n=s(Math.round(t.len-t.translate(o.x+n,0,1,0,1)),0,t.len),l=s(Math.round(i.translate(o.y-a,0,1,0,1)),0,i.len),a=s(Math.round(i.translate(o.y+a,0,1,0,1)),0,i.len);o.plotX=o.clientX=(r+n)/2,o.plotY=(l+a)/2,o.shapeType="rect",o.shapeArgs={x:Math.min(r,n),y:Math.min(l,a),width:Math.abs(n-r),height:Math.abs(a-l)}}),this.translateColors(),this.chart.hasRendered&&c(this.points,function(e){e.shapeArgs.fill=e.options.color||e.color})},drawPoints:u.column.prototype.drawPoints,animate:m,getBox:m,drawLegendSymbol:n.drawRectangle,getExtremes:function(){a.prototype.getExtremes.call(this,this.valueData),this.valueMin=this.dataMin,this.valueMax=this.dataMax,a.prototype.getExtremes.call(this)}}))})},1031:/*!***********************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/src/HighchartsHeatmap.css ***!
  \***********************************************************************/
function(e,t,i){var s=i(1032);"string"==typeof s&&(s=[[e.id,s,""]]);i(1034)(s,{});s.locals&&(e.exports=s.locals)},1032:/*!**************************************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/css-loader!./expression-atlas-heatmap-highcharts/src/HighchartsHeatmap.css ***!
  \**************************************************************************************************************************/
function(e,t,i){t=e.exports=i(1033)(),t.push([e.id,"body{background-color:#fff;font-family:Verdana,sans-serif;font-size:10px}a.project_link:active,a.project_link:hover{text-decoration:underline}.gene_title{text-align:center;font-size:70%;border:0 solid red}#container{min-width:410px;min-height:210px;margin:0 auto;border:0 solid red}#barcharts_legend_list_items{color:#606060;margin-left:180px;margin-top:-9px;border:0 solid olive;position:absolute}.col_below{background:#d7d7d7}.col_low{background:#dff8ff}.col_med{background:#9fd2fa}.col_high{background:#45affd}.col_nd{background:#fff}#barcharts_legend_list_items .legend-item{cursor:pointer;vertical-align:middle;display:inline-block;padding:4px}#barcharts_legend_list_items .legend-item:hover{color:#000}#barcharts_legend_list_items .legend-item.legend-item-off{color:#ccc}#barcharts_legend_list_items .legend-item.legend-item-off div{background-color:#f7f7f7}#barcharts_legend_list_items .legend-item.special{cursor:default;margin-left:36px;color:#000}#barcharts_legend_list_items .legend-item .legend-rectangle{width:12px;height:12px;border:1px solid rgba(0,0,0,.2);float:left;margin-right:4px}#barcharts_legend_list_items .legend-item .icon-generic:before{font-size:180%;color:#7e7e7e}#barcharts_legend_list_items .legend-item:hover .icon-generic:before{color:#353535}@font-face{font-family:EBI-Conceptual;src:url('http://www.ebi.ac.uk//web_guidelines/fonts/EBI-Conceptual/fonts/EBI-Conceptual.eot');src:url('http://www.ebi.ac.uk//web_guidelines/fonts/EBI-Conceptual/fonts/EBI-Conceptual.eot?#iefix') format('embedded-opentype'),url('http://www.ebi.ac.uk//web_guidelines/fonts/EBI-Conceptual/fonts/EBI-Conceptual.woff') format('woff'),local('\\263A'),url('http://www.ebi.ac.uk//web_guidelines/fonts/EBI-Conceptual/fonts/EBI-Conceptual.svg#EBI-Conceptual') format('svg'),url('http://www.ebi.ac.uk//web_guidelines/fonts/EBI-Conceptual/fonts/EBI-Conceptual.ttf') format('truetype');font-weight:400;font-style:normal}@font-face{font-family:EBI-Functional;src:url('http://www.ebi.ac.uk/web_guidelines/fonts/EBI-Functional/fonts/EBI-Functional.eot');src:url('http://www.ebi.ac.uk/web_guidelines/fonts/EBI-Functional/fonts/EBI-Functional.eot?#iefix') format('embedded-opentype'),url('http://www.ebi.ac.uk/web_guidelines/fonts/EBI-Functional/fonts/EBI-Functional.woff') format('woff'),local('\\263A'),url('http://www.ebi.ac.uk/web_guidelines/fonts/EBI-Functional/fonts/EBI-Functional.svg#EBI-Functional') format('svg'),url('http://www.ebi.ac.uk/web_guidelines/fonts/EBI-Functional/fonts/EBI-Functional.ttf') format('truetype');font-weight:400;font-style:normal}@font-face{font-family:EBI-Generic;src:url('http://www.ebi.ac.uk//web_guidelines/fonts/EBI-Generic/fonts/EBI-Generic.eot');src:url('http://www.ebi.ac.uk//web_guidelines/fonts/EBI-Generic/fonts/EBI-Generic.eot?#iefix') format('embedded-opentype'),url('http://www.ebi.ac.uk//web_guidelines/fonts/EBI-Generic/fonts/EBI-Generic.woff') format('woff'),local('\\263A'),url('http://www.ebi.ac.uk//web_guidelines/fonts/EBI-Generic/fonts/EBI-Generic.svg#EBI-Generic') format('svg'),url('http://www.ebi.ac.uk//web_guidelines/fonts/EBI-Generic/fonts/EBI-Generic.ttf') format('truetype');font-weight:400;font-style:normal}@font-face{font-family:EBI-Species;src:url('http://www.ebi.ac.uk/web_guidelines/fonts/EBI-Species/fonts/EBI-Species.eot');src:url('http://www.ebi.ac.uk/web_guidelines/fonts/EBI-Species/fonts/EBI-Species.eot?#iefix') format('embedded-opentype'),url('http://www.ebi.ac.uk/web_guidelines/fonts/EBI-Species/fonts/EBI-Species.woff') format('woff'),local('\\263A'),url('http://www.ebi.ac.uk/web_guidelines/fonts/EBI-Species/fonts/EBI-Species.svg#EBI-Species') format('svg'),url('http://www.ebi.ac.uk/web_guidelines/fonts/EBI-Species/fonts/EBI-Species.ttf') format('truetype');font-weight:400;font-style:normal}@font-face{font-family:EBI-SocialMedia;src:url('http://www.ebi.ac.uk/web_guidelines/fonts/EBI-SocialMedia/fonts/EBI-SocialMedia.eot');src:url('http://www.ebi.ac.uk/web_guidelines/fonts/EBI-SocialMedia/fonts/EBI-SocialMedia.eot?#iefix') format('embedded-opentype'),url('http://www.ebi.ac.uk/web_guidelines/fonts/EBI-SocialMedia/fonts/EBI-SocialMedia.woff') format('woff'),local('\\263A'),url('http://www.ebi.ac.uk/web_guidelines/fonts/EBI-SocialMedia/fonts/EBI-SocialMedia.svg#EBI-Species') format('svg'),url('http://www.ebi.ac.uk/web_guidelines/fonts/EBI-SocialMedia/fonts/EBI-SocialMedia.ttf') format('truetype');font-weight:400;font-style:normal}.icon-socialmedia:before{font-family:EBI-SocialMedia;margin:0 .3em 0 0;content:attr(data-icon);color:#fff;font-size:100%}.icon-conceptual:before{font-family:EBI-Conceptual;margin:0 .3em 0 0;content:attr(data-icon);color:#7e7e7e;font-size:180%}.icon-species:before{font-family:EBI-Species;font-size:100%;color:#fff;content:attr(data-icon);margin:0}.icon{text-decoration:none;font-style:normal}.icon-generic:before,.icon-static:before{font-family:EBI-Generic;font-size:100%;color:#bbb;content:attr(data-icon);margin:0}.icon-functional:before{font-family:EBI-Functional;font-size:100%;color:#fff;content:attr(data-icon);margin:0 .3em 0 0}",""])},1033:/*!**************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/css-loader/lib/css-base.js ***!
  \**************************************************************************/
function(e,t){e.exports=function(){var e=[];return e.toString=function(){for(var e=[],t=0;t<this.length;t++){var i=this[t];i[2]?e.push("@media "+i[2]+"{"+i[1]+"}"):e.push(i[1])}return e.join("")},e.i=function(t,i){"string"==typeof t&&(t=[[null,t,""]]);for(var s={},o=0;o<this.length;o++){var n=this[o][0];"number"==typeof n&&(s[n]=!0)}for(o=0;o<t.length;o++){var a=t[o];"number"==typeof a[0]&&s[a[0]]||(i&&!a[2]?a[2]=i:i&&(a[2]="("+a[2]+") and ("+i+")"),e.push(a))}},e}},1034:/*!*************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/style-loader/addStyles.js ***!
  \*************************************************************************/
function(e,t,i){function s(e,t){for(var i=0;i<e.length;i++){var s=e[i],o=h[s.id];if(o){o.refs++;for(var n=0;n<o.parts.length;n++)o.parts[n](s.parts[n]);for(;n<s.parts.length;n++)o.parts.push(c(s.parts[n],t))}else{for(var a=[],n=0;n<s.parts.length;n++)a.push(c(s.parts[n],t));h[s.id]={id:s.id,refs:1,parts:a}}}}function o(e){for(var t=[],i={},s=0;s<e.length;s++){var o=e[s],n=o[0],a=o[1],r=o[2],l=o[3],c={css:a,media:r,sourceMap:l};i[n]?i[n].parts.push(c):t.push(i[n]={id:n,parts:[c]})}return t}function n(e,t){var i=m(),s=x[x.length-1];if("top"===e.insertAt)s?s.nextSibling?i.insertBefore(t,s.nextSibling):i.appendChild(t):i.insertBefore(t,i.firstChild),x.push(t);else{if("bottom"!==e.insertAt)throw new Error("Invalid value for parameter 'insertAt'. Must be 'top' or 'bottom'.");i.appendChild(t)}}function a(e){e.parentNode.removeChild(e);var t=x.indexOf(e);t>=0&&x.splice(t,1)}function r(e){var t=document.createElement("style");return t.type="text/css",n(e,t),t}function l(e){var t=document.createElement("link");return t.rel="stylesheet",n(e,t),t}function c(e,t){var i,s,o;if(t.singleton){var n=w++;i=b||(b=r(t)),s=p.bind(null,i,n,!1),o=p.bind(null,i,n,!0)}else e.sourceMap&&"function"==typeof URL&&"function"==typeof URL.createObjectURL&&"function"==typeof URL.revokeObjectURL&&"function"==typeof Blob&&"function"==typeof btoa?(i=l(t),s=f.bind(null,i),o=function(){a(i),i.href&&URL.revokeObjectURL(i.href)}):(i=r(t),s=d.bind(null,i),o=function(){a(i)});return s(e),function(t){if(t){if(t.css===e.css&&t.media===e.media&&t.sourceMap===e.sourceMap)return;s(e=t)}else o()}}function p(e,t,i,s){var o=i?"":s.css;if(e.styleSheet)e.styleSheet.cssText=y(t,o);else{var n=document.createTextNode(o),a=e.childNodes;a[t]&&e.removeChild(a[t]),a.length?e.insertBefore(n,a[t]):e.appendChild(n)}}function d(e,t){var i=t.css,s=t.media;if(s&&e.setAttribute("media",s),e.styleSheet)e.styleSheet.cssText=i;else{for(;e.firstChild;)e.removeChild(e.firstChild);e.appendChild(document.createTextNode(i))}}function f(e,t){var i=t.css,s=t.sourceMap;s&&(i+="\n/*# sourceMappingURL=data:application/json;base64,"+btoa(unescape(encodeURIComponent(JSON.stringify(s))))+" */");var o=new Blob([i],{type:"text/css"}),n=e.href;e.href=URL.createObjectURL(o),n&&URL.revokeObjectURL(n)}var h={},u=function(e){var t;return function(){return"undefined"==typeof t&&(t=e.apply(this,arguments)),t}},g=u(function(){return/msie [6-9]\b/.test(window.navigator.userAgent.toLowerCase())}),m=u(function(){return document.head||document.getElementsByTagName("head")[0]}),b=null,w=0,x=[];e.exports=function(e,t){if("object"!=typeof document)throw new Error("The style-loader cannot be used in a non-browser environment");t=t||{},"undefined"==typeof t.singleton&&(t.singleton=g()),"undefined"==typeof t.insertAt&&(t.insertAt="bottom");var i=o(e);return s(i,t),function(e){for(var n=[],a=0;a<i.length;a++){var r=i[a],l=h[r.id];l.refs--,n.push(l)}if(e){var c=o(e);s(c,t)}for(var a=0;a<n.length;a++){var l=n[a];if(0===l.refs){for(var p=0;p<l.parts.length;p++)l.parts[p]();delete h[l.id]}}}};var y=function(){var e=[];return function(t,i){return e[t]=i,e.filter(Boolean).join("\n")}}()},1035:/*!********************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/src/highchartsUtils.js ***!
  \********************************************************************/
function(e,t){"use strict";function i(e){return e.map(function(e){return e.factorValue})}function s(e,t){return e.rows.map(function(e){return n[e.name]=e.id+"?geneQuery="+t.geneQuery+"&serializedFilterFactors="+encodeURIComponent(e.serializedFilterFactors),e.name})}function o(){return n}var n={};t.getXAxisCategories=i,t.getYAxisCategories=s,t.getYAxisCategoriesLinks=o}});