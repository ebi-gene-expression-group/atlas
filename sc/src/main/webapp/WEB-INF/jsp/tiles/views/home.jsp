<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<div class="row expanded">
  <tiles:insertAttribute name="release-stats"/>
</div>

<div class="row column margin-bottom-xlarge expanded">
  <tiles:insertAttribute name="search"/>
</div>

<div class="row column margin-bottom-xlarge expanded">
  <tiles:insertAttribute name="species-summary"/>
</div>

<div class="row column margin-bottom-xlarge expanded">
  <tiles:insertAttribute name="experiments-summary-panel"/>
</div>

<script>
  document.addEventListener('DOMContentLoaded', function(event) {
    document.getElementById('local-nav-home').className += ' active';
  });
</script>