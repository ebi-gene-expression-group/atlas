<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div>

    <display:table style="width:100%" name="${geneExpressions}" htmlId="expressions-table"
                   id="geneExpressions"
                   class="table-grid">

        <fmt:message key="gene.id" bundle="${i18n}" var="geneIdLabel"/>
        <display:column title="${geneIdLabel}" property="geneId"/>

        <fmt:message key="factor.name.ORGANISMPART" bundle="${i18n}" var="organismpart"/>
        <display:column title="${organismpart}" property="organismPart"/>

        <fmt:message key="expression.level.metric" bundle="${i18n}" var="measurement"/>
        <display:column title="${measurement}" property="level"/>

        <display:column title="Specificity" property="specificity"/>

    </display:table>

</div>
