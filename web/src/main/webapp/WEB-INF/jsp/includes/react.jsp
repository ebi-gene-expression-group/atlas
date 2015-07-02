<%-- console polyfill to make the unminified React work with IE8/9 --%>
<!--[if lt IE 10]>
<script type="text/javascript"> if (!window.console) console = {log: function() {}, warn: function() {}}; </script>
<![endif]-->

<%-- polyfills to make React work with IE8 --%>
<!--[if lt IE 9]>
<script src="${pageContext.request.contextPath}/resources/latest/js/react/es5-shim-4.0.1.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/latest/js/react/es5-sham-4.0.1.min.js"></script>
<![endif]-->

<script src="${pageContext.request.contextPath}/resources/latest/js/lib/react/react-0.11.1.js"></script>
<script src="${pageContext.request.contextPath}/resources/latest/js/lib/react-radiogroup.js"></script>