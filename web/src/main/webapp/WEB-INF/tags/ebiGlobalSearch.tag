<%@ attribute name="ebiSearchTerm" required="true" type="java.lang.String"%>

<aside id="search-extras" class="push_18 grid_6 shortcuts expander">
    <input type="text"
           value="${ebiSearchTerm}"
           style="display: none" id="searchterm">

    <div id="ebi_search_results"><h3 data-icon="u" class="slideToggle icon icon-functional">Show more data from
        EMBL-EBI</h3>
    </div>
</aside>

<script src="${pageContext.request.contextPath}/resources/js/ebi-global-search-run.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/ebi-global-search.js"></script>