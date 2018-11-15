<%--
  Created by IntelliJ IDEA.
  User: lingyun
  Date: 25/10/2018
  Time: 09:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div className="row">
    <div className="small-12 columns">
        <h3>Human Cell Atlas Experiments</h3>

        <div className="row">
            <div className="small-1 column">
                <img  alt="Human Cell Atlas" style="max-width: 275px"
                      src="${pageContext.request.contextPath}/resources/images/logos/human_cell_atlas.png"/>
            </div>

            <div className="small-11 columns">
                <p>
                    Thanks to funding from the <a href="https://www.humancellatlas.org/">HCA</a> project,
                    Expression Atlas contains <b>791</b>
                    <a href="/gxa/experiments?kingdom=plants">plant experiments</a>, studying
                    e.g.
                    <a href="/gxa/experiments?organism=Arabidopsis+thaliana">Arabidopsis</a>,
                    <a href="/gxa/experiments?organism=Oryza+sativa">rice</a>, and
                    <a href="/gxa/experiments?organism=Zea+mays">maize</a>.
                </p>
                <p>
                    The <i>baseline</i> experiments, are either RNA-seq or proteomics, and display expression levels of
                    gene products under 'normal' conditions (e.g. normal rice tissues). Each experiment is manually curated
                    to a high standard, and RNA expression levels are calculated using the
                    <a href="https://github.com/nunofonseca/irap">iRAP</a> pipeline.
                </p>
                <p>
                    The <i>differential</i> experiments in Atlas, containing both microarray and RNA-seq data, allows users
                    to query which genes are up-/down-regulatedin different experimental conditions, e.g. 'in Arabidopsis
                    shoots, what genes are upregulated in plants treated by X?'
                </p>
            </div>
        </div>
    </div>

</div>
