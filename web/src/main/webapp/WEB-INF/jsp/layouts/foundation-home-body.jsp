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
            <tiles:insertAttribute name="first-row-first-box"/>
        </div>
        <div class="columns">
            <tiles:insertAttribute name="first-row-second-box"/>
        </div>
    </div>
</section>

<%--<section>--%>
    <%--<div class="row small-up-1 medium-up-1 large-up-2" data-equalizer>--%>
        <%--<div class="columns">--%>
            <%--<tiles:insertAttribute name="first-row-first-box"/>--%>
        <%--</div>--%>
        <%--<div class="columns">--%>
            <%--<tiles:insertAttribute name="first-row-second-box"/>--%>
        <%--</div>--%>
    <%--</div>--%>
<%--</section>--%>

<%--<section>--%>
    <%--<tiles:insertAttribute name="social"/>--%>
<%--</section>--%>

<%--<section>--%>
    <%--<tiles:insertAttribute name="contents"/>--%>
<%--</section>--%>