<%--@elvariable id="info" type="List<String>"--%>
<%--@elvariable id="numberOfSpecies" type="Number"--%>
<%--@elvariable id="numberOfStudies" type="Number"--%>
<%--@elvariable id="numberOfAssays" type="Number"--%>

<%@ page contentType="text/html;charset=UTF-8" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="media-object">
    <div class="media-object-section top" style="position: absolute;left: 1%;">
        <h4><fmt:formatNumber type = "number" value="${numberOfSpecies}"/> species,
            <fmt:formatNumber type = "number" value="${numberOfStudies}"/> studies,
            <fmt:formatNumber type = "number" value="${numberOfAssays}"/> assays</h4>
    </div>
    <div class="media-object-section top" style="position: absolute;right: 1%;">
        <h4> ${info} </h4>
    </div>
</div>

