
<%--@elvariable id="applicationProperties" type="uk.ac.ebi.atlas.web.ApplicationProperties"--%>
<%--@elvariable id="preferences" type="uk.ac.ebi.atlas.web.SearchRequest"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:choose>
    <c:when test="${empty jsonFacets}">
        {
        "error" : "No expression found for "
        }
    </c:when>

    <c:otherwise>
        "config": {
        },
        "facets": ${not empty jsonFacets ? jsonFacets : "null"}

    </c:otherwise>
</c:choose>
