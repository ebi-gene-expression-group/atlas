<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<div class="row column margin-bottom-xlarge">
    <tiles:insertAttribute name="home-hero"/>
</div>

<div class="row column margin-bottom-xlarge">
    <tiles:insertAttribute name="search"/>
</div>

<div class="row column margin-bottom-xlarge">
    <tiles:insertAttribute name="experiment-list-latest-box"/>
</div>

<%--<div class="row column">--%>
    <%--<div class="small-6 columns">--%>
        <%--<tiles:insertAttribute name="type-cell"/>--%>
    <%--</div>--%>
    <%--<div class="small-6 columns">--%>
        <%--<tiles:insertAttribute name="type-tissue"/>--%>
    <%--</div>--%>
<%--</div>--%>

<!-- Bring it back when we have some publications of our own :) -->
<%--<div class="row column margin-bottom-xlarge">--%>
    <%--<tiles:insertAttribute name="publications-box"/>--%>
<%--</div>--%>

<div class="row expanded text-center">
    <div class="small-centered small-8 medium-4 columns padding-top-large padding-bottom-large">
        <tiles:insertAttribute name="news"/>
    </div>
</div>

<script>
    document.addEventListener("DOMContentLoaded", function(event) {
        document.getElementById("local-nav-home").className += ' active';
    });
</script>