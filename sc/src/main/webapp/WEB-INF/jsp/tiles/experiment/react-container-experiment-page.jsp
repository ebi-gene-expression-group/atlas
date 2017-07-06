<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link rel="stylesheet" href="https://www.ebi.ac.uk/gxa/resources/css/foundation/atlas.css" type="text/css" media="all">

<script src="${pageContext.request.contextPath}/versioned-resources-${resourcesVersion}/js-bundles/vendorCommons.bundle.js"></script>
<script src="${pageContext.request.contextPath}/versioned-resources-${resourcesVersion}/js-bundles/experimentPage.bundle.js"></script>


<div class="row">

    <div id="container"></div>

</div>

<script>
    <%--experimentPage.render({--%>
        <%--atlasUrl: '${pageContext.request.contextPath}/',--%>
        <%--experimentAccession: '${experimentAccession}',--%>
    <%--}, 'container');--%>

    experimentPage.render({
        atlasUrl: "${pageContext.request.contextPath}/",
        experimentAccession: "${experimentAccession}",
        container: document.getElementById('container')
    });
</script>