<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<section>
    <tiles:insertAttribute name="home-hero"/>
</section>

<section class="margin-top-large">
    <div class="row small-up-1 medium-up-1 large-up-2" data-equalizer>
        <div class="columns">
            <tiles:insertAttribute name="second-box"/>
        </div>
        <div class="columns">
            <tiles:insertAttribute name="fourth-box"/>
        </div>
    </div>
</section>
