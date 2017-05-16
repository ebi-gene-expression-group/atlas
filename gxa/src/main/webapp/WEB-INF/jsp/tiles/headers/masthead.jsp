<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<div data-sticky-container>
    <div id="local-masthead" data-sticky data-sticky-on="large" data-top-anchor="235">
        <header>
            <tiles:insertAttribute name="global-masthead" />
            <tiles:insertAttribute name="local-masthead" />
        </header>
    </div>
</div>