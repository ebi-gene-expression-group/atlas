var expressionAtlasHeatmapHighcharts=webpackJsonp_name_([4],{0:/*!******************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/index.js ***!
  \******************************************************/
function(e,t,i){"use strict";e.exports=i(925)},925:/*!******************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/src/highchartsHeatmapRenderer.js ***!
  \******************************************************************************/
function(e,t,i){"use strict";var s=i(926),o=i(1082),n=i(1083);t.render=function(e){var t=window.location.protocol+"//",i=void 0===e.atlasHost?"www.ebi.ac.uk":e.atlasHost,a="/gxa",r=t+i+a,l=e.proxyPrefix?e.proxyPrefix+"/"+i+a:r,c=e.isMultiExperiment?"/widgets/heatmap/baselineAnalytics":"/widgets/heatmap/referenceExperiment",p=l+c+"?"+e.params;o.render(s.createElement(n,{sourceURL:p,atlasBaseURL:l,linksAtlasBaseURL:r,isWidget:void 0===e.isWidget?!0:e.isWidget,disableGoogleAnalytics:void 0===e.disableGoogleAnalytics?!1:e.disableGoogleAnalytics,fail:e.fail}),"string"==typeof e.target?document.getElementById(e.target):e.target)}},926:/*!**************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/react.js ***!
  \**************************************************************/
[1392,927],927:/*!******************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/React.js ***!
  \******************************************************************/
[1393,928,1072,1076,963,1081],928:/*!*********************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactDOM.js ***!
  \*********************************************************************/
[1394,929,930,995,969,952,942,974,978,1070,1015,1071,949,933],929:5,930:/*!**********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactDOMTextComponent.js ***!
  \**********************************************************************************/
[1395,931,946,950,952,963,945,944,994],931:/*!**********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/DOMChildrenOperations.js ***!
  \**********************************************************************************/
[1396,932,940,942,943,944,937],932:/*!*******************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/Danger.js ***!
  \*******************************************************************/
[1397,933,934,939,938,937],933:9,934:/*!*****************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/~/fbjs/lib/createNodesFromMarkup.js ***!
  \*****************************************************************************************/
[1398,933,935,938,937],935:/*!****************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/~/fbjs/lib/createArrayFromMixed.js ***!
  \****************************************************************************************/
[1399,936],936:/*!***************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/~/fbjs/lib/toArray.js ***!
  \***************************************************************************/
[1400,937],937:13,938:/*!*********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/~/fbjs/lib/getMarkupWrap.js ***!
  \*********************************************************************************/
[1401,933,937],939:15,940:/*!***************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactMultiChildUpdateTypes.js ***!
  \***************************************************************************************/
[1402,941],941:/*!*****************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/~/fbjs/lib/keyMirror.js ***!
  \*****************************************************************************/
[1403,937],942:18,943:/*!*************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/setInnerHTML.js ***!
  \*************************************************************************/
[1404,933],944:/*!***************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/setTextContent.js ***!
  \***************************************************************************/
[1405,933,945,943],945:21,946:/*!**********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/DOMPropertyOperations.js ***!
  \**********************************************************************************/
[1406,947,942,948,949],947:/*!************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/DOMProperty.js ***!
  \************************************************************************/
[1407,937],948:/*!******************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/quoteAttributeValueForBrowser.js ***!
  \******************************************************************************************/
[1408,945],949:/*!***************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/~/fbjs/lib/warning.js ***!
  \***************************************************************************/
[1409,939],950:/*!*********************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactComponentBrowserEnvironment.js ***!
  \*********************************************************************************************/
[1410,951,952],951:/*!*********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactDOMIDOperations.js ***!
  \*********************************************************************************/
[1411,931,946,952,942,937],952:/*!***********************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactMount.js ***!
  \***********************************************************************/
[1412,947,953,929,965,966,968,969,971,972,942,974,977,978,963,982,983,986,937,943,991,994,949],953:/*!*************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactBrowserEventEmitter.js ***!
  \*************************************************************************************/
[1413,954,955,956,961,942,962,963,964],954:/*!***************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/EventConstants.js ***!
  \***************************************************************************/
[1414,941],955:/*!***************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/EventPluginHub.js ***!
  \***************************************************************************/
[1415,956,957,958,959,960,937,949],956:/*!********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/EventPluginRegistry.js ***!
  \********************************************************************************/
[1416,937],957:/*!*****************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/EventPluginUtils.js ***!
  \*****************************************************************************/
[1417,954,958,937,949],958:34,959:/*!***************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/accumulateInto.js ***!
  \***************************************************************************/
[1418,937],960:36,961:/*!***********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactEventEmitterMixin.js ***!
  \***********************************************************************************/
[1419,955],962:38,963:39,964:/*!*****************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/isEventSupported.js ***!
  \*****************************************************************************/
[1420,933],965:41,966:/*!*************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactElement.js ***!
  \*************************************************************************/
[1421,929,963,967],967:43,968:44,969:/*!*********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactInstanceHandles.js ***!
  \*********************************************************************************/
[1422,970,937],970:46,971:47,972:/*!********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactMarkupChecksum.js ***!
  \********************************************************************************/
[1423,973],973:49,974:/*!****************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactReconciler.js ***!
  \****************************************************************************/
[1424,975],975:/*!*********************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactRef.js ***!
  \*********************************************************************/
[1425,976],976:/*!***********************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactOwner.js ***!
  \***********************************************************************/
[1426,937],977:/*!*****************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactUpdateQueue.js ***!
  \*****************************************************************************/
[1427,929,966,971,978,963,937,949],978:/*!*************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactUpdates.js ***!
  \*************************************************************************/
[1428,979,980,942,974,981,963,937],979:/*!**************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/CallbackQueue.js ***!
  \**************************************************************************/
[1429,980,963,937],980:/*!************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/PooledClass.js ***!
  \************************************************************************/
[1430,937],981:/*!************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/Transaction.js ***!
  \************************************************************************/
[1431,937],982:58,983:/*!********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/~/fbjs/lib/containsNode.js ***!
  \********************************************************************************/
[1432,984],984:/*!******************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/~/fbjs/lib/isTextNode.js ***!
  \******************************************************************************/
[1433,985],985:61,986:/*!**************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/instantiateReactComponent.js ***!
  \**************************************************************************************/
[1434,987,992,993,963,937,949],987:/*!************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactCompositeComponent.js ***!
  \************************************************************************************/
[1435,988,929,966,971,942,989,990,974,977,963,982,937,991,949],988:/*!**************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactComponentEnvironment.js ***!
  \**************************************************************************************/
[1436,937],989:/*!***********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactPropTypeLocations.js ***!
  \***********************************************************************************/
[1437,941],990:66,991:67,992:/*!********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactEmptyComponent.js ***!
  \********************************************************************************/
function(e,t,i){"use strict";function s(){a.registerNullComponentID(this._rootNodeID)}var o,n=i(966),a=i(968),r=i(974),l=i(963),c={injectEmptyComponent:function(e){o=n.createElement(e)}},p=function(e){this._currentElement=null,this._rootNodeID=null,this._renderedComponent=e(o)};l(p.prototype,{construct:function(e){},mountComponent:function(e,t,i){return t.getReactMountReady().enqueue(s,this),this._rootNodeID=e,r.mountComponent(this._renderedComponent,e,t,i)},receiveComponent:function(){},unmountComponent:function(e,t,i){r.unmountComponent(this._renderedComponent),a.deregisterNullComponentID(this._rootNodeID),this._rootNodeID=null,this._renderedComponent=null}}),p.injection=c,e.exports=p},993:/*!*********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactNativeComponent.js ***!
  \*********************************************************************************/
[1438,963,937],994:/*!*******************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/validateDOMNesting.js ***!
  \*******************************************************************************/
[1439,963,939,949],995:/*!**********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactDefaultInjection.js ***!
  \**********************************************************************************/
[1440,996,1004,1007,1008,1009,933,1013,1014,950,1016,1017,930,1042,1045,969,952,1049,1054,1055,1056,1065,1066],996:/*!***********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/BeforeInputEventPlugin.js ***!
  \***********************************************************************************/
[1441,954,997,933,998,1e3,1002,1003],997:/*!*****************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/EventPropagators.js ***!
  \*****************************************************************************/
[1442,954,955,949,959,960],998:/*!*************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/FallbackCompositionState.js ***!
  \*************************************************************************************/
[1443,980,963,999],999:/*!***********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/getTextContentAccessor.js ***!
  \***********************************************************************************/
[1444,933],1e3:/*!**************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/SyntheticCompositionEvent.js ***!
  \**************************************************************************************/
[1445,1001],1001:/*!***************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/SyntheticEvent.js ***!
  \***************************************************************************/
[1446,980,963,939,949],1002:/*!********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/SyntheticInputEvent.js ***!
  \********************************************************************************/
[1447,1001],1003:79,1004:/*!******************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ChangeEventPlugin.js ***!
  \******************************************************************************/
[1448,954,955,997,933,978,1001,1005,964,1006,1003],1005:81,1006:82,1007:83,1008:/*!************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/DefaultEventPluginOrder.js ***!
  \************************************************************************************/
[1449,1003],1009:/*!**********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/EnterLeaveEventPlugin.js ***!
  \**********************************************************************************/
[1450,954,997,1010,952,1003],1010:/*!********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/SyntheticMouseEvent.js ***!
  \********************************************************************************/
[1451,1011,962,1012],1011:/*!*****************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/SyntheticUIEvent.js ***!
  \*****************************************************************************/
[1452,1001,1005],1012:88,1013:/*!**********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/HTMLDOMPropertyConfig.js ***!
  \**********************************************************************************/
[1453,947,933],1014:/*!***************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactBrowserComponentMixin.js ***!
  \***************************************************************************************/
[1454,971,1015,949],1015:/*!************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/findDOMNode.js ***!
  \************************************************************************/
[1455,929,971,952,937,949],1016:/*!*****************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactDefaultBatchingStrategy.js ***!
  \*****************************************************************************************/
[1456,978,981,963,939],1017:/*!******************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactDOMComponent.js ***!
  \******************************************************************************/
[1457,1018,1020,947,946,954,953,950,1028,1029,1033,1036,1037,952,1038,942,977,963,967,945,937,964,1003,943,944,1041,994,949],1018:/*!***************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/AutoFocusUtils.js ***!
  \***************************************************************************/
[1458,952,1015,1019],1019:95,1020:/*!**********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/CSSPropertyOperations.js ***!
  \**********************************************************************************/
[1459,1021,933,942,1022,1024,1025,1027,949],1021:97,1022:/*!*************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/~/fbjs/lib/camelizeStyleName.js ***!
  \*************************************************************************************/
[1460,1023],1023:99,1024:/*!********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/dangerousStyleValue.js ***!
  \********************************************************************************/
[1461,1021],1025:/*!**************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/~/fbjs/lib/hyphenateStyleName.js ***!
  \**************************************************************************************/
[1462,1026],1026:102,1027:103,1028:104,1029:/*!**************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactDOMInput.js ***!
  \**************************************************************************/
[1463,951,1030,952,978,963,937],1030:/*!*****************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/LinkedValueUtils.js ***!
  \*****************************************************************************/
[1464,1031,989,937,949],1031:/*!***************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactPropTypes.js ***!
  \***************************************************************************/
[1465,966,990,939,1032],1032:108,1033:/*!***************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactDOMOption.js ***!
  \***************************************************************************/
[1466,1034,1036,963,949],1034:/*!**************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactChildren.js ***!
  \**************************************************************************/
[1467,980,966,939,1035],1035:/*!********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/traverseAllChildren.js ***!
  \********************************************************************************/
[1468,929,966,969,1032,937,949],1036:/*!***************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactDOMSelect.js ***!
  \***************************************************************************/
[1469,1030,952,978,963,949],1037:/*!*****************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactDOMTextarea.js ***!
  \*****************************************************************************/
[1470,1030,951,978,963,937,949],1038:/*!****************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactMultiChild.js ***!
  \****************************************************************************/
[1471,988,940,929,974,1039,1040],1039:/*!*********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactChildReconciler.js ***!
  \*********************************************************************************/
[1472,974,986,991,1035,949],1040:/*!****************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/flattenChildren.js ***!
  \****************************************************************************/
[1473,1035,949],1041:117,1042:/*!*******************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactEventListener.js ***!
  \*******************************************************************************/
[1474,1043,933,980,969,952,978,963,1005,1044],1043:/*!*********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/~/fbjs/lib/EventListener.js ***!
  \*********************************************************************************/
[1475,939],1044:120,1045:/*!***************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactInjection.js ***!
  \***************************************************************************/
[1476,947,955,988,1046,992,953,993,942,970,978],1046:/*!***********************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactClass.js ***!
  \***********************************************************************/
[1477,1047,966,989,990,1048,963,982,937,941,1003,949],1047:/*!***************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactComponent.js ***!
  \***************************************************************************/
[1478,1048,967,982,937,949],1048:/*!*********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactNoopUpdateQueue.js ***!
  \*********************************************************************************/
[1479,949],1049:/*!**************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactReconcileTransaction.js ***!
  \**************************************************************************************/
[1480,979,980,953,965,1050,981,963],1050:/*!********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactInputSelection.js ***!
  \********************************************************************************/
[1481,1051,983,1019,1053],1051:/*!******************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactDOMSelection.js ***!
  \******************************************************************************/
[1482,933,1052,999],1052:128,1053:129,1054:/*!******************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/SelectEventPlugin.js ***!
  \******************************************************************************/
[1483,954,997,933,1050,1001,1053,1006,1003,1041],1055:131,1056:/*!******************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/SimpleEventPlugin.js ***!
  \******************************************************************************/
[1484,954,1043,997,952,1057,1001,1058,1059,1010,1062,1063,1011,1064,939,1060,937,1003],1057:/*!************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/SyntheticClipboardEvent.js ***!
  \************************************************************************************/
[1485,1001],1058:/*!********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/SyntheticFocusEvent.js ***!
  \********************************************************************************/
[1486,1011],1059:/*!***********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/SyntheticKeyboardEvent.js ***!
  \***********************************************************************************/
[1487,1011,1060,1061,1012],1060:136,1061:/*!************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/getEventKey.js ***!
  \************************************************************************/
[1488,1060],1062:/*!*******************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/SyntheticDragEvent.js ***!
  \*******************************************************************************/
[1489,1010],1063:/*!********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/SyntheticTouchEvent.js ***!
  \********************************************************************************/
[1490,1011,1012],1064:/*!********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/SyntheticWheelEvent.js ***!
  \********************************************************************************/
[1491,1010],1065:/*!*********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/SVGDOMPropertyConfig.js ***!
  \*********************************************************************************/
[1492,947],1066:/*!*****************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactDefaultPerf.js ***!
  \*****************************************************************************/
[1493,947,1067,952,942,1068],1067:/*!*************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactDefaultPerfAnalysis.js ***!
  \*************************************************************************************/
[1494,963],1068:/*!**********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/~/fbjs/lib/performanceNow.js ***!
  \**********************************************************************************/
[1495,1069],1069:/*!*******************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/~/fbjs/lib/performance.js ***!
  \*******************************************************************************/
[1496,933],1070:/*!*************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactVersion.js ***!
  \*************************************************************************/
function(e,t){"use strict";e.exports="0.14.8"},1071:/*!***************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/renderSubtreeIntoContainer.js ***!
  \***************************************************************************************/
[1497,952],1072:/*!***************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactDOMServer.js ***!
  \***************************************************************************/
[1498,995,1073,1070],1073:/*!*********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactServerRendering.js ***!
  \*********************************************************************************/
[1499,1016,966,969,972,1074,1075,978,982,986,937],1074:150,1075:/*!********************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactServerRenderingTransaction.js ***!
  \********************************************************************************************/
[1500,980,979,981,963,939],1076:/*!****************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactIsomorphic.js ***!
  \****************************************************************************/
[1501,1034,1047,1046,1077,966,1078,1031,1070,963,1080],1077:/*!******************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactDOMFactories.js ***!
  \******************************************************************************/
[1502,966,1078,1079],1078:/*!**********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/ReactElementValidator.js ***!
  \**********************************************************************************/
[1503,966,989,990,929,967,1032,937,949],1079:155,1080:/*!**********************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/onlyChild.js ***!
  \**********************************************************************/
[1504,966,937],1081:/*!***********************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react/lib/deprecated.js ***!
  \***********************************************************************/
[1505,963,949],1082:/*!******************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-dom/index.js ***!
  \******************************************************************/
[1506,928],1083:/*!********************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/src/HighchartsHeatmapContainer.jsx ***!
  \********************************************************************************/
function(e,t,i){"use strict";var s=i(926),o=i(1084);i(1085);var n=i(1086),a=i(1094),r=s.createClass({displayName:"ExperimentDescription",propTypes:{linksAtlasBaseURL:s.PropTypes.string.isRequired,experiment:s.PropTypes.shape({URL:s.PropTypes.string.isRequired,description:s.PropTypes.string.isRequired,allSpecies:s.PropTypes.string.isRequired}).isRequired},render:function(){var e=this.props.linksAtlasBaseURL+this.props.experiment.URL;return s.createElement("div",{style:{width:"100%",paddingBottom:"20px"}},s.createElement("div",{id:"experimentDescription"},s.createElement("a",{id:"goto-experiment",className:"gxaThickLink",title:"Experiment Page",href:e},this.props.experiment.description)),s.createElement("div",{id:"experimentOrganisms"},"Organism(s): ",s.createElement("span",{style:{fontStyle:"italic"}},this.props.experiment.allSpecies)))}}),l=s.createClass({displayName:"HighchartsHeatmapContainer",propTypes:{sourceURL:s.PropTypes.string.isRequired,atlasBaseURL:s.PropTypes.string.isRequired,linksAtlasBaseURL:s.PropTypes.string.isRequired,isWidget:s.PropTypes.bool.isRequired,disableGoogleAnalytics:s.PropTypes.bool.isRequired,fail:s.PropTypes.func},render:function(){var e=this.props.linksAtlasBaseURL+"/query?geneQuery="+this.state.heatmapConfig.geneQuery+"&exactMatch="+this.state.heatmapConfig.isExactMatch+"&organism="+this.state.heatmapConfig.species;return s.createElement("div",{ref:"this"},this.state.experimentData?s.createElement(r,{experiment:this.state.experimentData,linksAtlasBaseURL:this.props.linksAtlasBaseURL}):null,this.state.heatmapConfig?s.createElement("div",{id:"heatmap-anatomogram",className:"gxaHeatmapAnatomogramRow"},s.createElement("div",{id:"heatmap-react",className:"gxaInnerHeatmap"},s.createElement(n,{profiles:this.state.profiles,atlasBaseURL:this.props.atlasBaseURL,xAxisCategories:this.state.xAxisCategories,yAxisCategories:this.state.yAxisCategories,yAxisCategoriesLinks:this.state.yAxisCategoriesLinks,seriesDataNA:this.state.seriesDataNA,seriesDataNAString:this.state.seriesDataNAString,seriesDataBelowCutoff:this.state.seriesDataBelowCutoff,seriesDataBelowCutoffString:this.state.seriesDataBelowCutoffString,seriesDataRanges:this.state.seriesDataRanges}))):s.createElement("div",{ref:"loadingImagePlaceholder"},s.createElement("img",{src:this.props.atlasBaseURL+"/resources/images/loading.gif"})),this.props.isWidget?s.createElement("div",null,s.createElement("p",null,s.createElement("a",{href:e},"See more expression data at Expression Atlas."),s.createElement("br",null),"This expression view is provided by ",s.createElement("a",{href:this.props.linksAtlasBaseURL},"Expression Atlas"),".",s.createElement("br",null),"Please direct any queries or feedback to ",s.createElement("a",{href:"mailto:arrayexpress-atlas@ebi.ac.uk"},"arrayexpress-atlas@ebi.ac.uk"))):null)},getInitialState:function(){return{heatmapConfig:"",profiles:{rows:[],minExpressionLevel:0,maxExpressionLevel:0},xAxisCategories:{},yAxisCategories:{},yAxisCategoriesLinks:{},seriesDataNA:[],seriesDataNAString:"NA",seriesDataBelowCutoff:[],seriesDataBelowCutoffString:"Below cutoff",seriesDataRanges:[]}},componentDidMount:function(){var e={url:this.props.sourceURL,dataType:"json",method:"GET"};if(o.ajax(e).done(function(e){if(this.isMounted()){for(var t=a.getXAxisCategories(e.columnHeaders),i=a.getYAxisCategories(e.profiles,e.config),s=a.getYAxisCategoriesLinks(),o=[],n="NA",r=[],l="Below cutoff",c=[{label:"Low",from:0,to:10,seriesData:[]},{label:"Medium",from:10,to:1e3,seriesData:[]},{label:"High",from:1e3,to:1e5,seriesData:[]}],p=[],d=0;d<e.profiles.rows.length;d++)-1===p.indexOf(e.profiles.rows[d].experimentType)&&p.push(e.profiles.rows[d].experimentType);for(var d=0;d<p.length;d++){for(var f=[],h=0;h<i.length;h++)if(e.profiles.rows[h].experimentType===p[d])for(var u=0;u<t.length;u++){var g=e.profiles.rows[h].expressions[u].value;if(""===g)r.push([u,h,l]);else if("NT"===g)o.push([u,h,n]);else{var m=parseFloat(g);isNaN(m)||f.push([u,h,m])}}f.sort(function(e,t){return e[2]-t[2]});for(var b=f[f.length-1][2],u=0;u<c.length;u++)f.filter(function(e){return e[2]>c[u].from*b&&e[2]<=c[u].to*b}).forEach(function(e){c[u].seriesData.push(e)})}this.setState({heatmapConfig:e.config,columnHeaders:e.columnHeaders,nonExpressedColumnHeaders:e.nonExpressedColumnHeaders,profiles:e.profiles,geneSetProfiles:e.geneSetProfiles,experimentData:e.experiment,xAxisCategories:t,yAxisCategories:i,yAxisCategoriesLinks:s,seriesDataBelowCutoff:r,seriesDataBelowCutoffString:l,seriesDataNA:o,seriesDataNAString:n,seriesDataRanges:c})}}.bind(this)).fail(function(e,t,i){this.props.fail?this.props.fail(e,t,i):"parsererror"===t?o(this.refs["this"].getDOMNode()).html("<div class='gxaError'>Could not parse JSON response</div>"):o(this.refs["this"].getDOMNode()).html(e.responseText)}.bind(this)),!this.props.disableGoogleAnalytics){var t=t||[];t.push(["_setAccount","UA-37676851-1"]),t.push(["_trackPageview"]),function(){var e=document.createElement("script");e.type="text/javascript",e.async=!0,e.src=("https:"==document.location.protocol?"https://ssl":"http://www")+".google-analytics.com/ga.js";var t=document.getElementsByTagName("script")[0];t.parentNode.insertBefore(e,t)}()}}});e.exports=l},1084:404,1085:/*!****************************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/jQuery-ajaxTransport-XDomainRequest/jquery.xdomainrequest.min.js ***!
  \****************************************************************************************************************/
[1529,1084],1086:/*!***********************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/src/HighchartsHeatmap.jsx ***!
  \***********************************************************************/
function(e,t,i){"use strict";var s=i(926),o=i(1087);i(1089)(o.Highcharts),i(1090);var n=s.createClass({displayName:"HighchartsHeatmap",propTypes:{profiles:s.PropTypes.object.isRequired,atlasBaseURL:s.PropTypes.string.isRequired},getInitialState:function(){return{xAxisCategories:{},yAxisCategories:{},yAxisCategoriesLinks:{},seriesDataNA:[],seriesDataNAString:"NA",seriesDataBelowCutoff:[],seriesDataBelowCutoffString:"Below cutoff",seriesDataRanges:[{label:"Low",from:0,to:10,seriesData:[]},{label:"Medium",from:10,to:1e3,seriesData:[]},{label:"High",from:1e3,to:1e5,seriesData:[]}],legend_1:!1,legend_2:!1,legend_3:!1,legend_4:!1}},handleClick:function(e){1==e?this.setState({legend_1:!this.state.legend_1}):2==e?this.setState({legend_2:!this.state.legend_2}):3==e?this.setState({legend_3:!this.state.legend_3}):4==e&&this.setState({legend_4:!this.state.legend_4})},componentDidMount:function(){var e=this.refs.chart.getChart();e.series[0].hide()},componentDidUpdate:function(){var e=this.refs.chart.getChart();e.series[0].hide(),this.state.legend_1?e.series[1].hide():e.series[1].show(),this.state.legend_2?e.series[2].hide():e.series[2].show(),this.state.legend_3?e.series[3].hide():e.series[3].show(),this.state.legend_4?e.series[4].hide():e.series[4].show()},render:function(){var e=this.props.atlasBaseURL,t=this.props.yAxisCategoriesLinks,i=this.props.yAxisCategories,n={plotOptions:{series:{point:{events:{mouseOver:function(){this.series.chart.userOptions.anatomogramEventEmitter.emit("gxaHeatmapColumnHoverChange",this.series.xAxis.categories[this.x].id),this.series.chart.userOptions.anatomogramEventEmitter.emit("gxaHeatmapRowHoverChange",this.series.yAxis.categories[this.y])}}},events:{mouseOut:function(){this.chart.userOptions.anatomogramEventEmitter.emit("gxaHeatmapColumnHoverChange",null),this.chart.userOptions.anatomogramEventEmitter.emit("gxaHeatmapRowHoverChange",null)}},states:{hover:{color:"#eeec38"}}}},credits:{enabled:!1},chart:{type:"heatmap",marginTop:82,marginRight:36,plotBorderWidth:1,height:12*i.length+200,zoomType:"xy"},legend:{useHTML:!0,enabled:!1,itemDistance:18,symbolWidth:12,symbolHeight:12,x:150,align:"left",verticalAlign:"bottom",layout:"horizontal",itemStyle:{lineHeight:"10px",fontSize:"10px",color:"#606060",fontWeight:"normal"}},title:null,xAxis:{tickLength:5,tickColor:"rgb(192, 192, 192)",lineColor:"rgb(192, 192, 192)",labels:{y:-6,style:{fontSize:"9px"},autoRotation:[-45,-90],formatter:function(){return this.value.label}},opposite:"true",categories:this.props.xAxisCategories},yAxis:{useHTML:!0,reversed:!0,labels:{style:{fontSize:"10px",color:"#148ff3"},formatter:function(){return'<a class="project_link"  href="'+e+"/experiments/"+t[this.value]+'">'+this.value+"</a>"}},categories:this.props.yAxisCategories,title:null,gridLineWidth:0,minorGridLineWidth:0},tooltip:{useHTML:!0,formatter:function(){return"Sample name: <b>"+this.series.yAxis.categories[this.point.y]+"</b>  <br> Tissue: <b>"+this.series.xAxis.categories[this.point.x].label+'</b><br><b></b><span style="border:1px rgb(192, 192, 192) solid; margin-right: 2px; width:6px; height:6px; display:inline-block; background-color:'+this.point.color+';"></span> Expression level: <b></span>Expression level: <b>'+this.point.value+"</b>"}},anatomogramEventEmitter:{IAmA:"dummy0",emit:function(e,t){console.log(e+t)}},ensemblEventEmitter:{IAmA:"dummy1",emit:function(e,t){console.log(e+t)}},series:[{name:this.props.seriesDataNAString,color:"#f7f7f7",borderWidth:1,borderColor:"#fff",data:this.props.seriesDataNA},{name:this.props.seriesDataBelowCutoffString,color:"#eaeaea",borderWidth:1,borderColor:"#fff",data:this.props.seriesDataBelowCutoff},{name:this.props.seriesDataRanges[0].label,color:"#dff8ff",borderWidth:1,borderColor:"#fff",data:this.props.seriesDataRanges[0].seriesData},{name:this.props.seriesDataRanges[1].label,color:"#9fd2fa",borderWidth:1,borderColor:"#fff",data:this.props.seriesDataRanges[1].seriesData},{name:this.props.seriesDataRanges[2].label,color:"#45affd",borderWidth:1,borderColor:"#fff",data:this.props.seriesDataRanges[2].seriesData}]},a=this.state.legend_1?"legend-item legend-item-off":"legend-item",r=this.state.legend_2?"legend-item legend-item-off":"legend-item",l=this.state.legend_3?"legend-item legend-item-off":"legend-item",c=this.state.legend_4?"legend-item legend-item-off":"legend-item",p=s.createElement("div",{id:"barcharts_legend_list_items",ref:"barcharts_legend_items"},s.createElement("span",null,"Click to interact:"),s.createElement("div",{id:"legend_1",ref:"legend_1",className:a,onClick:this.handleClick.bind(this,1)},s.createElement("div",{className:"legend-rectangle col_below"}),s.createElement("span",null,"Below cutoff")),s.createElement("div",{id:"legend_2",className:r,onClick:this.handleClick.bind(this,2)},s.createElement("div",{className:"legend-rectangle col_low"}),s.createElement("span",null,"Low")),s.createElement("div",{id:"legend_3",className:l,onClick:this.handleClick.bind(this,3)},s.createElement("div",{className:"legend-rectangle col_med"}),s.createElement("span",null,"Medium")),s.createElement("div",{id:"legend_4",className:c,onClick:this.handleClick.bind(this,4)},s.createElement("div",{className:"legend-rectangle col_high"}),s.createElement("span",null,"High")),s.createElement("div",{className:"legend-item"},s.createElement("span",{className:"icon icon-generic","data-icon":"i","data-toggle":"tooltip","data-placement":"bottom",title:"This range of value indicates gene expression level accross different tissues. It is calculated in different a different way between DNA and RNA experiments"})),s.createElement("div",{id:"legend_5",className:"legend-item special"},s.createElement("div",{className:"legend-rectangle col_nd"}),s.createElement("span",null,"No data available")));return s.createElement("div",null,s.createElement("div",{id:"highcharts_container"},s.createElement(o,{config:n,ref:"chart"}),p))}});e.exports=n},1087:/*!****************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/react-highcharts/dist/ReactHighcharts.js ***!
  \****************************************************************************************/
[1533,926,1088],1088:423,1089:/*!*****************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/highcharts-heatmap/heatmap.js ***!
  \*****************************************************************************/
function(e,t){!function(t){"object"==typeof e&&e.exports?e.exports=t:t(Highcharts)}(function(e){var t=e.Axis,i=e.Chart,s=e.Color,o=e.Legend,n=e.LegendSymbolMixin,a=e.Series,r=e.Point,l=e.getOptions(),c=e.each,p=e.extend,d=e.extendClass,f=e.merge,h=e.pick,u=e.seriesTypes,g=e.wrap,m=function(){},b=e.ColorAxis=function(){this.isColorAxis=!0,this.init.apply(this,arguments)};p(b.prototype,t.prototype),p(b.prototype,{defaultColorAxisOptions:{lineWidth:0,minPadding:0,maxPadding:0,gridLineWidth:1,tickPixelInterval:72,startOnTick:!0,endOnTick:!0,offset:0,marker:{animation:{duration:50},color:"gray",width:.01},labels:{overflow:"justify"},minColor:"#EFEFFF",maxColor:"#003875",tickLength:5},init:function(e,i){var s,o="vertical"!==e.options.legend.layout;s=f(this.defaultColorAxisOptions,{side:o?2:1,reversed:!o},i,{opposite:!o,showEmpty:!1,title:null,isColor:!0}),t.prototype.init.call(this,e,s),i.dataClasses&&this.initDataClasses(i),this.initStops(i),this.horiz=o,this.zoomEnabled=!1},tweenColors:function(e,t,i){var s;return t.rgba.length&&e.rgba.length?(e=e.rgba,t=t.rgba,s=1!==t[3]||1!==e[3],e=(s?"rgba(":"rgb(")+Math.round(t[0]+(e[0]-t[0])*(1-i))+","+Math.round(t[1]+(e[1]-t[1])*(1-i))+","+Math.round(t[2]+(e[2]-t[2])*(1-i))+(s?","+(t[3]+(e[3]-t[3])*(1-i)):"")+")"):e=t.input||"none",e},initDataClasses:function(e){var t,i=this,o=this.chart,n=0,a=this.options,r=e.dataClasses.length;this.dataClasses=t=[],this.legendItems=[],c(e.dataClasses,function(e,l){var c,e=f(e);t.push(e),e.color||("category"===a.dataClassColor?(c=o.options.colors,e.color=c[n++],n===c.length&&(n=0)):e.color=i.tweenColors(s(a.minColor),s(a.maxColor),2>r?.5:l/(r-1)))})},initStops:function(e){this.stops=e.stops||[[0,this.options.minColor],[1,this.options.maxColor]],c(this.stops,function(e){e.color=s(e[1])})},setOptions:function(e){t.prototype.setOptions.call(this,e),this.options.crosshair=this.options.marker,this.coll="colorAxis"},setAxisSize:function(){var e,t,i,s=this.legendSymbol,o=this.chart;s&&(this.left=e=s.attr("x"),this.top=t=s.attr("y"),this.width=i=s.attr("width"),this.height=s=s.attr("height"),this.right=o.chartWidth-e-i,this.bottom=o.chartHeight-t-s,this.len=this.horiz?i:s,this.pos=this.horiz?e:t)},toColor:function(e,t){var i,s,o,n,a=this.stops,r=this.dataClasses;if(r){for(n=r.length;n--;)if(o=r[n],s=o.from,a=o.to,(void 0===s||e>=s)&&(void 0===a||a>=e)){i=o.color,t&&(t.dataClass=n);break}}else{for(this.isLog&&(e=this.val2lin(e)),i=1-(this.max-e)/(this.max-this.min||1),n=a.length;n--&&!(i>a[n][0]););s=a[n]||a[n+1],a=a[n+1]||s,i=1-(a[0]-i)/(a[0]-s[0]||1),i=this.tweenColors(s.color,a.color,i)}return i},getOffset:function(){var e=this.legendGroup,i=this.chart.axisOffset[this.side];e&&(this.axisParent=e,t.prototype.getOffset.call(this),this.added||(this.added=!0,this.labelLeft=0,this.labelRight=this.width),this.chart.axisOffset[this.side]=i)},setLegendColor:function(){var e,t=this.options,i=this.reversed;e=i?1:0,i=i?0:1,e=this.horiz?[e,0,i,0]:[0,i,0,e],this.legendColor={linearGradient:{x1:e[0],y1:e[1],x2:e[2],y2:e[3]},stops:t.stops||[[0,t.minColor],[1,t.maxColor]]}},drawLegendSymbol:function(e,t){var i=e.padding,s=e.options,o=this.horiz,n=h(s.symbolWidth,o?200:12),a=h(s.symbolHeight,o?12:200),r=h(s.labelPadding,o?16:30),s=h(s.itemDistance,10);this.setLegendColor(),t.legendSymbol=this.chart.renderer.rect(0,e.baseline-11,n,a).attr({zIndex:1}).add(t.legendGroup),this.legendItemWidth=n+i+(o?s:r),this.legendItemHeight=a+i+(o?r:0)},setState:m,visible:!0,setVisible:m,getSeriesExtremes:function(){var e;this.series.length&&(e=this.series[0],this.dataMin=e.valueMin,this.dataMax=e.valueMax)},drawCrosshair:function(e,i){var s,o=i&&i.plotX,n=i&&i.plotY,a=this.pos,r=this.len;i&&(s=this.toPixels(i[i.series.colorKey]),a>s?s=a-2:s>a+r&&(s=a+r+2),i.plotX=s,i.plotY=this.len-s,t.prototype.drawCrosshair.call(this,e,i),i.plotX=o,i.plotY=n,this.cross&&this.cross.attr({fill:this.crosshair.color}).add(this.legendGroup))},getPlotLinePath:function(e,i,s,o,n){return"number"==typeof n?this.horiz?["M",n-4,this.top-6,"L",n+4,this.top-6,n,this.top,"Z"]:["M",this.left,n,"L",this.left-6,n+6,this.left-6,n-6,"Z"]:t.prototype.getPlotLinePath.call(this,e,i,s,o)},update:function(e,i){var s=this.chart,o=s.legend;c(this.series,function(e){e.isDirtyData=!0}),e.dataClasses&&o.allItems&&(c(o.allItems,function(e){e.isDataClass&&e.legendGroup.destroy()}),s.isDirtyLegend=!0),s.options[this.coll]=f(this.userOptions,e),t.prototype.update.call(this,e,i),this.legendItem&&(this.setLegendColor(),o.colorizeItem(this,!0))},getDataClassLegendSymbols:function(){var t,i=this,s=this.chart,o=this.legendItems,a=s.options.legend,r=a.valueDecimals,l=a.valueSuffix||"";return o.length||c(this.dataClasses,function(a,d){var f=!0,h=a.from,u=a.to;t="",void 0===h?t="< ":void 0===u&&(t="> "),void 0!==h&&(t+=e.numberFormat(h,r)+l),void 0!==h&&void 0!==u&&(t+=" - "),void 0!==u&&(t+=e.numberFormat(u,r)+l),o.push(p({chart:s,name:t,options:{},drawLegendSymbol:n.drawRectangle,visible:!0,setState:m,isDataClass:!0,setVisible:function(){f=this.visible=!f,c(i.series,function(e){c(e.points,function(e){e.dataClass===d&&e.setVisible(f)})}),s.legend.colorizeItem(this,f)}},a))}),o},name:""}),c(["fill","stroke"],function(t){e.Fx.prototype[t+"Setter"]=function(){this.elem.attr(t,b.prototype.tweenColors(s(this.start),s(this.end),this.pos))}}),g(i.prototype,"getAxes",function(e){var t=this.options.colorAxis;e.call(this),this.colorAxis=[],t&&new b(this,t)}),g(o.prototype,"getAllItems",function(e){var t=[],i=this.chart.colorAxis[0];return i&&(i.options.dataClasses?t=t.concat(i.getDataClassLegendSymbols()):t.push(i),c(i.series,function(e){e.options.showInLegend=!1})),t.concat(e.call(this))}),i={pointAttrToOptions:{stroke:"borderColor","stroke-width":"borderWidth",fill:"color",dashstyle:"dashStyle"},pointArrayMap:["value"],axisTypes:["xAxis","yAxis","colorAxis"],optionalAxis:"colorAxis",trackerGroups:["group","markerGroup","dataLabelsGroup"],getSymbol:m,parallelArrays:["x","y","value"],colorKey:"value",translateColors:function(){var e=this,t=this.options.nullColor,i=this.colorAxis,s=this.colorKey;c(this.data,function(o){var n=o[s];(n=o.options.color||(null===n?t:i&&void 0!==n?i.toColor(n,o):o.color||e.color))&&(o.color=n)})}},l.plotOptions.heatmap=f(l.plotOptions.scatter,{animation:!1,borderWidth:0,nullColor:"#F8F8F8",dataLabels:{formatter:function(){return this.point.value},inside:!0,verticalAlign:"middle",crop:!1,overflow:!1,padding:0},marker:null,pointRange:null,tooltip:{pointFormat:"{point.x}, {point.y}: {point.value}<br/>"},states:{normal:{animation:!0},hover:{halo:!1,brightness:.2}}}),u.heatmap=d(u.scatter,f(i,{type:"heatmap",pointArrayMap:["y","value"],hasPointSpecificOptions:!0,pointClass:d(r,{setVisible:function(e){var t=this,i=e?"show":"hide";c(["graphic","dataLabel"],function(e){t[e]&&t[e][i]()})}}),supportsDrilldown:!0,getExtremesFromAll:!0,directTouch:!0,init:function(){var e;u.scatter.prototype.init.apply(this,arguments),e=this.options,e.pointRange=h(e.pointRange,e.colsize||1),this.yAxis.axisPointRange=e.rowsize||1},translate:function(){var e=this.options,t=this.xAxis,i=this.yAxis,s=function(e,t,i){return Math.min(Math.max(t,e),i)};this.generatePoints(),c(this.points,function(o){var n=(e.colsize||1)/2,a=(e.rowsize||1)/2,r=s(Math.round(t.len-t.translate(o.x-n,0,1,0,1)),0,t.len),n=s(Math.round(t.len-t.translate(o.x+n,0,1,0,1)),0,t.len),l=s(Math.round(i.translate(o.y-a,0,1,0,1)),0,i.len),a=s(Math.round(i.translate(o.y+a,0,1,0,1)),0,i.len);o.plotX=o.clientX=(r+n)/2,o.plotY=(l+a)/2,o.shapeType="rect",o.shapeArgs={x:Math.min(r,n),y:Math.min(l,a),width:Math.abs(n-r),height:Math.abs(a-l)}}),this.translateColors(),this.chart.hasRendered&&c(this.points,function(e){e.shapeArgs.fill=e.options.color||e.color})},drawPoints:u.column.prototype.drawPoints,animate:m,getBox:m,drawLegendSymbol:n.drawRectangle,getExtremes:function(){a.prototype.getExtremes.call(this,this.valueData),this.valueMin=this.dataMin,this.valueMax=this.dataMax,a.prototype.getExtremes.call(this)}}))})},1090:/*!***********************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/src/HighchartsHeatmap.css ***!
  \***********************************************************************/
function(e,t,i){var s=i(1091);"string"==typeof s&&(s=[[e.id,s,""]]);i(1093)(s,{});s.locals&&(e.exports=s.locals)},1091:/*!**************************************************************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/css-loader!./expression-atlas-heatmap-highcharts/src/HighchartsHeatmap.css ***!
  \**************************************************************************************************************************/
function(e,t,i){t=e.exports=i(1092)(),t.push([e.id,"body{background-color:#fff;font-family:Verdana,sans-serif;font-size:10px}a.project_link:active,a.project_link:hover{text-decoration:underline}.gene_title{text-align:center;font-size:70%;border:0 solid red}#container{min-width:410px;min-height:210px;margin:0 auto;border:0 solid red}#barcharts_legend_list_items{color:#606060;margin-left:180px;margin-top:-9px;border:0 solid olive;position:absolute}.col_below{background:#d7d7d7}.col_low{background:#dff8ff}.col_med{background:#9fd2fa}.col_high{background:#45affd}.col_nd{background:#fff}#barcharts_legend_list_items .legend-item{cursor:pointer;vertical-align:middle;display:inline-block;padding:4px}#barcharts_legend_list_items .legend-item:hover{color:#000}#barcharts_legend_list_items .legend-item.legend-item-off{color:#ccc}#barcharts_legend_list_items .legend-item.legend-item-off div{background-color:#f7f7f7}#barcharts_legend_list_items .legend-item.special{cursor:default;margin-left:36px;color:#000}#barcharts_legend_list_items .legend-item .legend-rectangle{width:12px;height:12px;border:1px solid rgba(0,0,0,.2);float:left;margin-right:4px}#barcharts_legend_list_items .legend-item .icon-generic:before{font-size:180%;color:#7e7e7e}#barcharts_legend_list_items .legend-item:hover .icon-generic:before{color:#353535}@font-face{font-family:EBI-Conceptual;src:url('http://www.ebi.ac.uk//web_guidelines/fonts/EBI-Conceptual/fonts/EBI-Conceptual.eot');src:url('http://www.ebi.ac.uk//web_guidelines/fonts/EBI-Conceptual/fonts/EBI-Conceptual.eot?#iefix') format('embedded-opentype'),url('http://www.ebi.ac.uk//web_guidelines/fonts/EBI-Conceptual/fonts/EBI-Conceptual.woff') format('woff'),local('\\263A'),url('http://www.ebi.ac.uk//web_guidelines/fonts/EBI-Conceptual/fonts/EBI-Conceptual.svg#EBI-Conceptual') format('svg'),url('http://www.ebi.ac.uk//web_guidelines/fonts/EBI-Conceptual/fonts/EBI-Conceptual.ttf') format('truetype');font-weight:400;font-style:normal}@font-face{font-family:EBI-Functional;src:url('http://www.ebi.ac.uk/web_guidelines/fonts/EBI-Functional/fonts/EBI-Functional.eot');src:url('http://www.ebi.ac.uk/web_guidelines/fonts/EBI-Functional/fonts/EBI-Functional.eot?#iefix') format('embedded-opentype'),url('http://www.ebi.ac.uk/web_guidelines/fonts/EBI-Functional/fonts/EBI-Functional.woff') format('woff'),local('\\263A'),url('http://www.ebi.ac.uk/web_guidelines/fonts/EBI-Functional/fonts/EBI-Functional.svg#EBI-Functional') format('svg'),url('http://www.ebi.ac.uk/web_guidelines/fonts/EBI-Functional/fonts/EBI-Functional.ttf') format('truetype');font-weight:400;font-style:normal}@font-face{font-family:EBI-Generic;src:url('http://www.ebi.ac.uk//web_guidelines/fonts/EBI-Generic/fonts/EBI-Generic.eot');src:url('http://www.ebi.ac.uk//web_guidelines/fonts/EBI-Generic/fonts/EBI-Generic.eot?#iefix') format('embedded-opentype'),url('http://www.ebi.ac.uk//web_guidelines/fonts/EBI-Generic/fonts/EBI-Generic.woff') format('woff'),local('\\263A'),url('http://www.ebi.ac.uk//web_guidelines/fonts/EBI-Generic/fonts/EBI-Generic.svg#EBI-Generic') format('svg'),url('http://www.ebi.ac.uk//web_guidelines/fonts/EBI-Generic/fonts/EBI-Generic.ttf') format('truetype');font-weight:400;font-style:normal}@font-face{font-family:EBI-Species;src:url('http://www.ebi.ac.uk/web_guidelines/fonts/EBI-Species/fonts/EBI-Species.eot');src:url('http://www.ebi.ac.uk/web_guidelines/fonts/EBI-Species/fonts/EBI-Species.eot?#iefix') format('embedded-opentype'),url('http://www.ebi.ac.uk/web_guidelines/fonts/EBI-Species/fonts/EBI-Species.woff') format('woff'),local('\\263A'),url('http://www.ebi.ac.uk/web_guidelines/fonts/EBI-Species/fonts/EBI-Species.svg#EBI-Species') format('svg'),url('http://www.ebi.ac.uk/web_guidelines/fonts/EBI-Species/fonts/EBI-Species.ttf') format('truetype');font-weight:400;font-style:normal}@font-face{font-family:EBI-SocialMedia;src:url('http://www.ebi.ac.uk/web_guidelines/fonts/EBI-SocialMedia/fonts/EBI-SocialMedia.eot');src:url('http://www.ebi.ac.uk/web_guidelines/fonts/EBI-SocialMedia/fonts/EBI-SocialMedia.eot?#iefix') format('embedded-opentype'),url('http://www.ebi.ac.uk/web_guidelines/fonts/EBI-SocialMedia/fonts/EBI-SocialMedia.woff') format('woff'),local('\\263A'),url('http://www.ebi.ac.uk/web_guidelines/fonts/EBI-SocialMedia/fonts/EBI-SocialMedia.svg#EBI-Species') format('svg'),url('http://www.ebi.ac.uk/web_guidelines/fonts/EBI-SocialMedia/fonts/EBI-SocialMedia.ttf') format('truetype');font-weight:400;font-style:normal}.icon-socialmedia:before{font-family:EBI-SocialMedia;margin:0 .3em 0 0;content:attr(data-icon);color:#fff;font-size:100%}.icon-conceptual:before{font-family:EBI-Conceptual;margin:0 .3em 0 0;content:attr(data-icon);color:#7e7e7e;font-size:180%}.icon-species:before{font-family:EBI-Species;font-size:100%;color:#fff;content:attr(data-icon);margin:0}.icon{text-decoration:none;font-style:normal}.icon-generic:before,.icon-static:before{font-family:EBI-Generic;font-size:100%;color:#bbb;content:attr(data-icon);margin:0}.icon-functional:before{font-family:EBI-Functional;font-size:100%;color:#fff;content:attr(data-icon);margin:0 .3em 0 0}",""])},1092:/*!**************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/css-loader/lib/css-base.js ***!
  \**************************************************************************/
function(e,t){e.exports=function(){var e=[];return e.toString=function(){for(var e=[],t=0;t<this.length;t++){var i=this[t];i[2]?e.push("@media "+i[2]+"{"+i[1]+"}"):e.push(i[1])}return e.join("")},e.i=function(t,i){"string"==typeof t&&(t=[[null,t,""]]);for(var s={},o=0;o<this.length;o++){var n=this[o][0];"number"==typeof n&&(s[n]=!0)}for(o=0;o<t.length;o++){var a=t[o];"number"==typeof a[0]&&s[a[0]]||(i&&!a[2]?a[2]=i:i&&(a[2]="("+a[2]+") and ("+i+")"),e.push(a))}},e}},1093:/*!*************************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/~/style-loader/addStyles.js ***!
  \*************************************************************************/
function(e,t,i){function s(e,t){for(var i=0;i<e.length;i++){var s=e[i],o=h[s.id];if(o){o.refs++;for(var n=0;n<o.parts.length;n++)o.parts[n](s.parts[n]);for(;n<s.parts.length;n++)o.parts.push(c(s.parts[n],t))}else{for(var a=[],n=0;n<s.parts.length;n++)a.push(c(s.parts[n],t));h[s.id]={id:s.id,refs:1,parts:a}}}}function o(e){for(var t=[],i={},s=0;s<e.length;s++){var o=e[s],n=o[0],a=o[1],r=o[2],l=o[3],c={css:a,media:r,sourceMap:l};i[n]?i[n].parts.push(c):t.push(i[n]={id:n,parts:[c]})}return t}function n(e,t){var i=m(),s=x[x.length-1];if("top"===e.insertAt)s?s.nextSibling?i.insertBefore(t,s.nextSibling):i.appendChild(t):i.insertBefore(t,i.firstChild),x.push(t);else{if("bottom"!==e.insertAt)throw new Error("Invalid value for parameter 'insertAt'. Must be 'top' or 'bottom'.");i.appendChild(t)}}function a(e){e.parentNode.removeChild(e);var t=x.indexOf(e);t>=0&&x.splice(t,1)}function r(e){var t=document.createElement("style");return t.type="text/css",n(e,t),t}function l(e){var t=document.createElement("link");return t.rel="stylesheet",n(e,t),t}function c(e,t){var i,s,o;if(t.singleton){var n=w++;i=b||(b=r(t)),s=p.bind(null,i,n,!1),o=p.bind(null,i,n,!0)}else e.sourceMap&&"function"==typeof URL&&"function"==typeof URL.createObjectURL&&"function"==typeof URL.revokeObjectURL&&"function"==typeof Blob&&"function"==typeof btoa?(i=l(t),s=f.bind(null,i),o=function(){a(i),i.href&&URL.revokeObjectURL(i.href)}):(i=r(t),s=d.bind(null,i),o=function(){a(i)});return s(e),function(t){if(t){if(t.css===e.css&&t.media===e.media&&t.sourceMap===e.sourceMap)return;s(e=t)}else o()}}function p(e,t,i,s){var o=i?"":s.css;if(e.styleSheet)e.styleSheet.cssText=v(t,o);else{var n=document.createTextNode(o),a=e.childNodes;a[t]&&e.removeChild(a[t]),a.length?e.insertBefore(n,a[t]):e.appendChild(n)}}function d(e,t){var i=t.css,s=t.media;if(s&&e.setAttribute("media",s),e.styleSheet)e.styleSheet.cssText=i;else{for(;e.firstChild;)e.removeChild(e.firstChild);e.appendChild(document.createTextNode(i))}}function f(e,t){var i=t.css,s=t.sourceMap;s&&(i+="\n/*# sourceMappingURL=data:application/json;base64,"+btoa(unescape(encodeURIComponent(JSON.stringify(s))))+" */");var o=new Blob([i],{type:"text/css"}),n=e.href;e.href=URL.createObjectURL(o),n&&URL.revokeObjectURL(n)}var h={},u=function(e){var t;return function(){return"undefined"==typeof t&&(t=e.apply(this,arguments)),t}},g=u(function(){return/msie [6-9]\b/.test(window.navigator.userAgent.toLowerCase())}),m=u(function(){return document.head||document.getElementsByTagName("head")[0]}),b=null,w=0,x=[];e.exports=function(e,t){if("object"!=typeof document)throw new Error("The style-loader cannot be used in a non-browser environment");t=t||{},"undefined"==typeof t.singleton&&(t.singleton=g()),"undefined"==typeof t.insertAt&&(t.insertAt="bottom");var i=o(e);return s(i,t),function(e){for(var n=[],a=0;a<i.length;a++){var r=i[a],l=h[r.id];l.refs--,n.push(l)}if(e){var c=o(e);s(c,t)}for(var a=0;a<n.length;a++){var l=n[a];if(0===l.refs){for(var p=0;p<l.parts.length;p++)l.parts[p]();delete h[l.id]}}}};var v=function(){var e=[];return function(t,i){return e[t]=i,e.filter(Boolean).join("\n")}}()},1094:/*!********************************************************************!*\
  !*** ./expression-atlas-heatmap-highcharts/src/highchartsUtils.js ***!
  \********************************************************************/
function(e,t){"use strict";function i(e){return e.map(function(e){return{label:e.factorValue,id:e.factorValueOntologyTermId}})}function s(e,t){return e.rows.map(function(e){return n[e.name]=e.id+"?geneQuery="+t.geneQuery+"&serializedFilterFactors="+encodeURIComponent(e.serializedFilterFactors),e.name})}function o(){return n}var n={};t.getXAxisCategories=i,t.getYAxisCategories=s,t.getYAxisCategoriesLinks=o}});