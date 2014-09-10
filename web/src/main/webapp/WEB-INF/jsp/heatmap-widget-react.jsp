<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="base" value="${pageContext.request.contextPath}"/>
<c:if test="${not empty preferences.rootContext}">
    <c:set var="base" value="${preferences.rootContext}"/>
</c:if>

<div id="atlas-content" class="block">
    <%@ include file="includes/anatomogram-and-heatmap-react.jsp" %>

    <%@ include file="includes/heatmap-react.jsp" %>

    <br/>

    <div id="help-placeholder" style="display: none"></div>

    <div id="queryFactorType" data-value="${queryFactorType}"></div>

    <%@ include file="includes/transcript-breakdown-popup.jsp" %>

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