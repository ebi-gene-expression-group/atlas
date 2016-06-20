<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/customized-bootstrap-3.3.5.css"/>
<script language="JavaScript" type="text/javascript" src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>

<!-- Script to solve conflict between Bootstrap and Jquery https://github.com/twbs/bootstrap/issues/6094 -->
<script>
    var btn = $.fn.button.noConflict() // reverts $.fn.button to jqueryui btn
    // $.fn.btn = btn // assigns bootstrap button functionality to $.fn.btn
</script>
