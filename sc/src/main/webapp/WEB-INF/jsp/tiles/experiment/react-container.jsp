<script src="${pageContext.request.contextPath}/resources/js-bundles/vendorCommons.bundle.js"></script>
<script src="${pageContext.request.contextPath}/resources/js-bundles/main.bundle.js"></script>

<div id="react-container-experiment-page"></div>

<script type="text/javascript">
    main.render({
        referenceDataSourceUrlTemplate:
                "${pageContext.request.contextPath}/json/experiments/E-MTAB-4388/expression?query={0}",
        target: "react-container-experiment-page"
    });
</script>
