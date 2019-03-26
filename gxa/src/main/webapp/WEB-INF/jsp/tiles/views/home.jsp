<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<div class="row expanded">
    <tiles:insertAttribute name="release-stats"/>
</div>

<div class="row column margin-bottom-xlarge expanded">
    <tiles:insertAttribute name="home-search"/>
</div>

<div class="row column margin-bottom-xlarge expanded">
    <tiles:insertAttribute name="species-summary-panel"/>
</div>

<div class="row column margin-bottom-xlarge expanded">
    <tiles:insertAttribute name="experiments-summary-panel"/>
</div>

<div class="row expanded margin-top-large" data-equalizer>
    <div class="small-12 medium-12 large-6 columns">
        <tiles:insertAttribute name="tools-box"/>
    </div>
    <div class="small-12 medium-12 large-6 columns">
        <tiles:insertAttribute name="publications-box"/>
    </div>
</div>

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

