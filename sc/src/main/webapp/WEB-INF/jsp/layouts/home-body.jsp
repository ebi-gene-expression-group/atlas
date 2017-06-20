<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<section>
    <tiles:insertAttribute name="home-hero"/>
</section>

<section>
    <div class="row margin-bottom-xlarge">
        <tiles:insertAttribute name="experiment-list-latest-box"/>
    </div>
</section>

<section>
    <div class="row margin-top-xlarge">
        <tiles:insertAttribute name="type-cell"/>
        <tiles:insertAttribute name="type-tissue"/>
    </div>

    <div class="row margin-top-xlarge">
        <tiles:insertAttribute name="publications-box"/>
    </div>
</section>

<section>
    <div class="row expanded jumbo-news-container padding-top-large padding-bottom-large">
         <div class="small-centered small-8 medium-4 columns">
             <tiles:insertAttribute name="news"/>
         </div>
    </div>
</section>
