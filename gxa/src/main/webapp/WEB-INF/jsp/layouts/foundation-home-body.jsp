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

<div class="row margin-top-large">
    <div class="small-12 columns">
        <div class="row small-up-1 medium-up-1 large-up-2" data-equalizer>
            <div class="columns">
                <tiles:insertAttribute name="browse-by-box"/>
            </div>
            <div class="columns">
                <tiles:insertAttribute name="experiment-list-latest-box"/>
            </div>
        </div>
    </div>
</div>

<div class="row margin-top-large">
    <div class="small-12 columns">
        <div class="row small-up-1 medium-up-1 large-up-2" data-equalizer>
            <div class="columns">
                <tiles:insertAttribute name="tools-box"/>
            </div>
            <div class="columns">
                <tiles:insertAttribute name="publications-box"/>
            </div>
        </div>
    </div>
</div>
