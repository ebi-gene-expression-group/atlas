<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="row">
    <div class="small-12 columns">
        <tiles:insertAttribute name="search"/>
    </div>

    <div class="small-12 columns">
        <h3>Search results</h3>
    </div>

    <!-- If there are marker genes results show this... -->
    <div class="small-12 columns">
        <tiles:insertAttribute name="marker-genes"/>
    </div>
</div>