<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- absolute Link to bootstrap script - temp - make it relative-->
<script src="${pageContext.request.contextPath}/resources/js/lib/bootstrap-3.3.5.min.js"></script>
<!-- Script to solve conflict between Bootstrap and Jquery https://github.com/twbs/bootstrap/issues/6094 -->
<script>
    var btn = $.fn.button.noConflict() // reverts $.fn.button to jqueryui btn
    // $.fn.btn = btn // assigns bootstrap button functionality to $.fn.btn
</script>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/new-bioentities/bootstrap.css"/>
