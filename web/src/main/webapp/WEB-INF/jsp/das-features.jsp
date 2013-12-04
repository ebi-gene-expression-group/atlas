<%@ page contentType="application/xml;charset=UTF-8" language="java" %><?xml version="1.0" standalone="no"?>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="baseURL" value="http://www.ebi.ac.uk${pageContext.request.contextPath}" />
<c:set var="geneUrl" value="${baseURL}/genes/${geneId}"/>
<DASGFF>
    <GFF href="${baseURL}/das/s4/features?segment=${geneId}">
        <SEGMENT id="${geneId}" start="1" stop="1" total="11" version="1.0" label="Expression Atlas annotation for ${geneId}">
            <FEATURE id="${geneId}" label="Expression summary">
                <TYPE id="description" category="description">001. Gene</TYPE>
                <METHOD id="ExpressionSummary">ExpressionSummary</METHOD>
                <SCORE>0.0</SCORE>
                <NOTE>${geneName} expression summary</NOTE>
                <LINK href="${geneUrl}">View in Expression Atlas</LINK>
            </FEATURE>
            <FEATURE id="organism part" label="organism part">
                <TYPE id="summary" category="summary">002. organism part</TYPE>
                <METHOD id="ExpressionSummary">ExpressionSummary</METHOD>
                <SCORE>0.0</SCORE>
                <NOTE>${factorValues_ORGANISM_PART}</NOTE>
                <LINK href="${geneUrl}">View all</LINK>
            </FEATURE>
            <FEATURE id="disease" label="disease">
                <TYPE id="summary" category="summary">003. disease</TYPE>
                <METHOD id="ExpressionSummary">ExpressionSummary</METHOD>
                <SCORE>0.0</SCORE>
                <NOTE>${factorValues_DISEASE}</NOTE>
                <LINK href="${geneUrl}">View all</LINK>
            </FEATURE>
            <FEATURE id="cell type" label="cell type">
                <TYPE id="summary" category="summary">004. cell type</TYPE>
                <METHOD id="ExpressionSummary">ExpressionSummary</METHOD>
                <SCORE>0.0</SCORE>
                <NOTE>${factorValues_CELL_TYPE}</NOTE>
                <LINK href="${geneUrl}">View all</LINK>
            </FEATURE>
            <FEATURE id="cell line" label="cell line">
                <TYPE id="summary" category="summary">005. cell line</TYPE>
                <METHOD id="ExpressionSummary">ExpressionSummary</METHOD>
                <SCORE>0.0</SCORE>
                <NOTE>${factorValues_CELL_LINE}</NOTE>
                <LINK href="${geneUrl}">View all</LINK>
            </FEATURE>
            <FEATURE id="Compound treatment" label="Compound treatment">
                <TYPE id="summary" category="summary">006. Compound treatment</TYPE>
                <METHOD id="ExpressionSummary">ExpressionSummary</METHOD>
                <SCORE>0.0</SCORE>
                <NOTE>${factorValues_COMPOUND}</NOTE>
                <LINK href="${geneUrl}">View all</LINK>
            </FEATURE>
            <FEATURE id="developmental stage" label="developmental stage">
                <TYPE id="summary" category="summary">007. developmental stage</TYPE>
                <METHOD id="ExpressionSummary">ExpressionSummary</METHOD>
                <SCORE>0.0</SCORE>
                <NOTE>${factorValues_DEVELOPMENTAL_STAGE}</NOTE>
                <LINK href="${geneUrl}">View all</LINK>
            </FEATURE>
            <FEATURE id="Infection" label="Infection">
                <TYPE id="summary" category="summary">008. Infection</TYPE>
                <METHOD id="ExpressionSummary">ExpressionSummary</METHOD>
                <SCORE>0.0</SCORE>
                <NOTE>${factorValues_INFECT}</NOTE>
                <LINK href="${geneUrl}">View all</LINK>
            </FEATURE>
            <FEATURE id="phenotype" label="phenotype">
                <TYPE id="summary" category="summary">009. phenotype</TYPE>
                <METHOD id="ExperimentalFactor">ExperimentalFactor</METHOD>
                <SCORE>0.0</SCORE>
                <NOTE>${factorValues_PHENOTYPE}</NOTE>
                <LINK href="${geneUrl}">View all</LINK>
            </FEATURE>
            <%--      <FEATURE id="Anatomogram" label="ENSG00000012048">
                    <TYPE id="html" category="html">html</TYPE>
                    <METHOD id="html">html</METHOD>
                    <SCORE>0.0</SCORE>
                    <NOTE>anatomogram</NOTE>
                    <LINK href="http://www.ebi.ac.uk/gxa/widgets/heatmap/protein?geneQuery=%20ENSG00000124233&amp;propertyType=bioentity_identifier&amp;rootContext=">Expression in healthy or untreated tissues</LINK>
                  </FEATURE>
            --%>
            <FEATURE id="Provenance" label="Provenance">
                <TYPE id="atlas-provenance" cvId="description" category="description">provenance</TYPE>
                <METHOD id="provenance" cvId="provenance">provenance</METHOD>
                <SCORE>0.0</SCORE>
                <NOTE>Data source: Expression Atlas (12-11-2013).</NOTE>
                <NOTE>Expression Atlas is a semantically enriched database of publicly available gene and transcript expression data. The data is re-analysed in-house to detect genes showing interesting baseline and differential expression patterns under the conditions of the original experiment.</NOTE>
                <LINK href="${baseURL}/about.html">About Expression Atlas</LINK>
            </FEATURE>
        </SEGMENT>
    </GFF>
</DASGFF>