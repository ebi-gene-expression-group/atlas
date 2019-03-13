<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<div class="row column margin-bottom-xlarge expanded">
    <tiles:insertAttribute name="home-search"/>
</div>

<div class="row column margin-bottom-xlarge expanded">
    <tiles:insertAttribute name="browse-by-box"/>
</div>

<div class="row column margin-bottom-xlarge expanded">
    <tiles:insertAttribute name="experiment-list-latest-box"/>
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
        // /* This is to prevent overlapping between boxes in home page */
        Foundation.reInit('equalizer');
    });
</script>

