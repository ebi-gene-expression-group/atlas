<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="base" value="${pageContext.request.contextPath}"/>
<%-- TODO: remove rootContext when BioJs no longer uses HTML insert --%>
<c:if test="${not empty param.rootContext}">
    <c:set var="base" value="${param.rootContext}"/>
</c:if>

<div id="atlas-content" class="block">
    <%@ include file="includes/anatomogram-and-heatmap-react.jsp" %>

    <%@ include file="includes/flot.jsp" %>

    <%@ include file="includes/heatmap-react.jsp" %>

</div>

<script>

    (function ($) { //self invoking wrapper function that prevents $ namespace conflicts

        $(document).ready(function () {

            $("#goto-ae").tooltip();
            $("#goto-experiment").tooltip();
            $("#goto-experiment-name").tooltip();
            $('.pubmed-id').tooltip();

        });

    })(jQuery);

</script>