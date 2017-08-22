<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<tiles:insertAttribute name="home-hero"/>

<tiles:insertAttribute name="home-search"/>

<div class="row expanded margin-top-large" data-equalizer>
    <div class="small-12 medium-12 large-6 columns">
            <tiles:insertAttribute name="browse-by-box"/>
    </div>
    <div class="small-12 medium-12 large-6 columns">
            <tiles:insertAttribute name="experiment-list-latest-box"/>
    </div>
</div>

<div class="row expanded margin-top-large" data-equalizer>
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

