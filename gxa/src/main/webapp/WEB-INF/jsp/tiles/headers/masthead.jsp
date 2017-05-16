<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<div>
    <div id="local-masthead">
        <header>
            <tiles:insertAttribute name="global-masthead" />
            <tiles:insertAttribute name="local-masthead" />
        </header>
    </div>
</div>