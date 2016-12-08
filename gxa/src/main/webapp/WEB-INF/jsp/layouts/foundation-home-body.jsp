<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<section>
    <tiles:insertAttribute name="home-hero"/>
</section>

<section class="margin-top-large">
    <tiles:insertAttribute name="home-search"/>
</section>
<section class="margin-top-large">
    <div class="row small-up-1 medium-up-1 large-up-2" data-equalizer>
        <div class="columns">
            <tiles:insertAttribute name="browse-by-box"/>
        </div>
        <div class="columns">
            <tiles:insertAttribute name="experiment-list-latest-box"/>
        </div>
    </div>
</section>

<section class="margin-top-large">
    <div class="row small-up-1 medium-up-1 large-up-2" data-equalizer>
        <div class="columns">
            <tiles:insertAttribute name="tools-box"/>
        </div>
        <div class="columns">
            <tiles:insertAttribute name="publications-box"/>
        </div>
    </div>
</section>
