
<c:choose>
    <c:when test="${empty jsonFacets}">
        {
        "error" : "No expression found for ${geneQuery}"
        }
    </c:when>
    <c:otherwise>
        "config": {
        },
        "facets": ${not empty jsonFacets ? jsonFacets : "null"}

    </c:otherwise>
</c:choose>
