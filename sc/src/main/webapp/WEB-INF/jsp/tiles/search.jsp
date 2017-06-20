<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<form method="get" action="search" id="home-search-atlas-form">
    <h3>What gene?</h3>

    <div class="row">
        <div class="small-12 columns">
            <label>Enter a gene or gene property to search for marker genes</label>
            <input id="home-search-atlas-input" type="text" placeholder="Enter your search" name="geneQuery"/>
        </div>
    </div>

    <div class="row">
        <div class="small-12 columns small">
            Examples: <a href='${pageContext.request.contextPath}/query?geneQuery=[{"value":"REG1B"}]'>REG1B</a>,
            <a href='${pageContext.request.contextPath}/query?geneQuery=[{"value":"zinc finger"}]'>zinc finger</a>,
            <a href='${pageContext.request.contextPath}/query?conditionQuery=[{"value":"lung"}]'>lung</a>,
            <a href='${pageContext.request.contextPath}/query?conditionQuery=[{"value":"leaf"}]'>leaf</a>,
            <a href='${pageContext.request.contextPath}/query?conditionQuery=[{"value":"valproic acid"}]'>valproic acid</a>,
            <a href='${pageContext.request.contextPath}/query?conditionQuery=[{"value":"cancer"}]'>cancer</a></label>
        </div>
    </div>

    <div class="row margin-top-large">
        <div class="small-12 columns">
            <input id="home-search-atlas-search-button" class="button" type="submit" value="Search"/>
                <input id="home-search-atlas-clear-button" class="secondary hollow button" type="button" value="Clear"/>
        </div>
    </div>
</form>
