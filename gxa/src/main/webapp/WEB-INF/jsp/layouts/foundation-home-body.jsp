<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<div class="row">
    <div class="small-12 columns">
        <tiles:insertAttribute name="home-hero"/>
    </div>
</div>

<div class="row margin-top-large">
    <div class="small-12 columns">
        <tiles:insertAttribute name="home-search"/>
    </div>
</div>

<div class="row margin-top-large" data-equalizer>
    <div class="small-12 medium-12 large-6 columns">
            <tiles:insertAttribute name="browse-by-box"/>
    </div>
    <div class="small-12 medium-12 large-6 columns">
            <tiles:insertAttribute name="experiment-list-latest-box"/>
    </div>
</div>

<div class="row margin-top-large" data-equalizer>
    <div class="small-12 medium-12 large-6 columns">
        <tiles:insertAttribute name="tools-box"/>
    </div>
    <div class="small-12 medium-12 large-6 columns">
        <tiles:insertAttribute name="publications-box"/>
    </div>
</div>

<div class="text-center jumbo-news-container padding-top-xlarge padding-bottom-xlarge">
    <tiles:insertAttribute name="news"/>
</div>

<script>
    /* This is to prevent overlapping between boxes in home page */
    $(document).ready(function() {
        Foundation.reInit('equalizer');
    });
</script>

