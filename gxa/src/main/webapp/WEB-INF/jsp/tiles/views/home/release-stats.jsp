<%--@elvariable id="info" type="String"--%>
<%--@elvariable id="numberOfSpecies" type="Number"--%>
<%--@elvariable id="numberOfStudies" type="Number"--%>
<%--@elvariable id="numberOfAssays" type="Number"--%>
<%--@elvariable id="genomes" type="String"--%>
<%--@elvariable id="paraSite" type="String"--%>
<%--@elvariable id="ensembl" type="String"--%>
<%--@elvariable id="efo" type="String"--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="small-12 medium-6 columns text-left">
  <h4>
    <small>
      Search across <strong><fmt:formatNumber value="${numberOfSpecies}"/>&nbsp;species,
      <fmt:formatNumber value="${numberOfStudies}"/>&nbsp;studies,
      <fmt:formatNumber value="${numberOfAssays}"/>&nbsp;assays</strong>
    </small>
  </h4>
</div>
<div class="small-12 medium-6 columns hide-for-small-only text-right">
  <h4>
    <small>
      Ensembl&nbsp;<fmt:formatNumber value="${info.get(ensembl)}"/>,
      Ensembl&nbsp;Genomes&nbsp;<fmt:formatNumber value="${info.get(genomes)}"/>,
      WormBase&nbsp;ParaSite&nbsp;<fmt:formatNumber value="${info.get(paraSite)}"/>,
      EFO&nbsp;<fmt:formatNumber value="${info.get(efo)}"/>
    </small>
  </h4>
</div>

