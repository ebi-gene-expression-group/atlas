<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link rel="stylesheet" href="https://www.ebi.ac.uk/gxa/resources/css/foundation/atlas.css" type="text/css" media="all">

<script src="${pageContext.request.contextPath}/versioned-resources-${resourcesVersion}/js-bundles/vendorCommons.bundle.js"></script>
<script src="${pageContext.request.contextPath}/versioned-resources-${resourcesVersion}/js-bundles/experimentPage.bundle.js"></script>


<div class="row">

    <div id="container"></div>

<%--<div class="columns medium-6"> Main Left column </div>--%>

    <%--<div class="columns medium-6"> Main Right column </div>--%>
</div>

<script>
    experimentPage.render({
        atlasUrl: "${pageContext.request.contextPath}/",
        container: document.getElementById('container')
    });
</script>