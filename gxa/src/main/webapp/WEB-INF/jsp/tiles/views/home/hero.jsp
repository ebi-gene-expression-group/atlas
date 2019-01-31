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
        <h4><fmt:formatNumber type = "number" value="${numberOfSpecies}"/> species,
            <fmt:formatNumber type = "number" value="${numberOfStudies}"/> studies,
            <fmt:formatNumber type = "number" value="${numberOfAssays}"/> assays</h4>
        <h4>Ensembl Genomes:${info.get(genomes)},
            WormBase ParaSite:${info.get(paraSite)},
            Ensembl:${info.get(ensembl)},
            EFO:${info.get(efo)}</h4>
</div>

