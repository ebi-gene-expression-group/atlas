<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<section id="gxaExperimentHeader">
    <div class="row">
        <div class="small-12 columns">
            <tiles:insertAttribute name="description-blurb"/>
        </div>
    </div>
</section>
<section>
    <div class="row column margin-bottom-large">
        <tiles:insertAttribute name="react-container"/>
    </div>
</section>
