<script src="${pageContext.request.contextPath}/resources/js-bundles/reference_plot.bundle.js"></script>

<div id="react-container-experiment-page"></div>

<script type="text/javascript">
    reference_plot.render({
        referenceDataSourceUrlTemplate:
                "${pageContext.request.contextPath}/json/experiments/E-MTAB-4388/expression?query={0}",
        target: "react-container-experiment-page"
    });
</script>
