<%--@elvariable id="isMultiExperiment" type="boolean"--%>

<c:choose>
    <c:when test="${empty jsonProfiles}">
        <c:if test="${not isPreferenceError}">
            <div class="grid_24" id="heatmap-message">
                No expressions found
            </div>
        </c:if>
    </c:when>
    <c:otherwise>

    <script src="${pageContext.request.contextPath}/resources/js-bundles/vendorCommons.bundle.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js-bundles/experimentPageHeatmap.bundle.js"></script>

    <div id="genenametooltip-content" style="display: none"></div>

        <script type="text/javascript">

            var heatmapData = <%@ include file="heatmap-data.jsp" %>;
            var isMultiExperiment = ${isMultiExperiment ? true : false};

            experimentPageHeatmap.render({
                heatmapData: heatmapData,
                isMultiExperiment: isMultiExperiment,
                isDifferential: ${type.differential},
                isProteomicsBaseline: ${type.proteomicsBaseline}
            });

        </script>
    </c:otherwise>
</c:choose>
