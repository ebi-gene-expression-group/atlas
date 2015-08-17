<c:choose>
    <c:when test="${empty jsonProfiles}">
        <c:if test="${not isPreferenceError}">
            <div id="heatmap-message">
                No expressions found
            </div>
        </c:if>
    </c:when>
    <c:otherwise>

    <script src="${pageContext.request.contextPath}/resources/js-bundles/vendor.bundle.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js-bundles/internal-atlas-heatmap.bundle.js"></script>

    <script src="${pageContext.request.contextPath}/resources/js/lib/jquery.toolbar.js"></script>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery.toolbars.css" />

    <div id="genenametooltip-content" style="display: none"></div>

        <script type="text/javascript">

            var heatmapData = <%@ include file="heatmap-data.jsp" %>;

            var heatmapModule = window.exposed;

            var multiExperiment = ${isMultiExperiment ? true : false};

            heatmapModule(heatmapData, multiExperiment, ${type.differential}, ${type.proteomicsBaseline});

        </script>
    </c:otherwise>
</c:choose>
