<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

${content}

<script type="text/javascript">
    $(function () {
        clearLocalNav();
        $('#local-nav-${nav}').addClass("active");
    });
</script>
