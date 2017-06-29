<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<div class="row margin-top-large">
    <div class="small-12 columns">
        <tiles:insertAttribute name="home-hero"/>
    </div>
</div>

<div class="row margin-bottom-xlarge">
    <div class="small-12 columns">
        <tiles:insertAttribute name="experiment-list-latest-box"/>
    </div>
</div>

<div class="row">
    <div class="small-12 columns">
        <tiles:insertAttribute name="search"/>
    </div>
</div>

<div class="row margin-top-xlarge">
    <tiles:insertAttribute name="type-cell"/>
    <tiles:insertAttribute name="type-tissue"/>
</div>

<div class="row margin-top-xlarge">
    <tiles:insertAttribute name="publications-box"/>
</div>

<div class="row expanded jumbo-news-container padding-top-large padding-bottom-large">
     <div class="small-centered small-8 medium-4 columns">
         <tiles:insertAttribute name="news"/>
     </div>
</div>
