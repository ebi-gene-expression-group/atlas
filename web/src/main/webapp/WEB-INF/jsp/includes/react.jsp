<%-- console polyfill to make the unminified React work with IE8/9 --%>
<!--[if lt IE 10]>
<script type="text/javascript"> if (!window.console) console = {log: function() {}, warn: function() {}}; </script>
<![endif]-->

<%-- polyfills to make React work with IE8 --%>
<!--[if lt IE 9]>
<script src="//cdnjs.cloudflare.com/ajax/libs/es5-shim/4.0.1/es5-shim.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/es5-shim/4.0.1/es5-sham.min.js"></script>
<![endif]-->

<script src="//cdnjs.cloudflare.com/ajax/libs/react/0.11.1/react.js"></script>