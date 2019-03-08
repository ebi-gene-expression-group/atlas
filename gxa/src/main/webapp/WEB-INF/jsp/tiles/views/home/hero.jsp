<%--@elvariable id="info" type="String"--%>
<%--@elvariable id="numberOfSpecies" type="Number"--%>
<%--@elvariable id="numberOfStudies" type="Number"--%>
<%--@elvariable id="numberOfAssays" type="Number"--%>
<%--@elvariable id="genomes" type="String"--%>
<%--@elvariable id="paraSite" type="String"--%>
<%--@elvariable id="ensembl" type="String"--%>
<%--@elvariable id="efo" type="String"--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<style>
    #hero {
        display: -webkit-flex; /* Safari */
        -webkit-flex-wrap: wrap; /* Safari 6.1+ */
        display: flex;
        flex-wrap: wrap;
        justify-content: space-between;
    }
</style>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div id="hero">
        <h4>Search across
            <strong><fmt:formatNumber value="${numberOfSpecies}"/> species,
            <fmt:formatNumber value="${numberOfStudies}"/> studies,
            <fmt:formatNumber value="${numberOfAssays}"/> assays</strong>
        </h4>
        <h4>Ensembl <fmt:formatNumber value="${info.get(ensembl)}"/>,
            Ensembl Genomes <fmt:formatNumber value="${info.get(genomes)}"/>,
            WormBase ParaSite <fmt:formatNumber value="${info.get(paraSite)}"/>,
            EFO <fmt:formatNumber value="${info.get(efo)}"/></h4>
</div>

