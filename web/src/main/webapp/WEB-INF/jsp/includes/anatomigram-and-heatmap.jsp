<c:choose>
    <c:when test="${empty geneProfiles}">
        <c:if test="${not isPreferenceError}">
            <div id="heatmap-message">
                No expressions found above the expression level cutoff for the query.
            </div>
        </c:if>
    </c:when>
    <c:otherwise>

        <div id="heatmap" class="row stickem-container">

            <div id="anatomogram" class="aside stickem double-click-noselection">
                <table>
                    <tr>
                        <td style="padding-top: 15px; vertical-align:top">
                <span id="sex-toggle">
                    <img id="sex-toggle-image" title="Switch anatomogram" class="button-image"
                         style="width:20px;height:38px;padding:2px"
                         src="${pageContext.request.contextPath}/resources/images/male_selected.png"/>
                </span>
                            <!--
                            <span data-help-loc="#anatomogram"/>
                            -->
                        </td>
                        <td>
                            <div id="anatomogramBody" style="display:inline-block;width: 230px; height:360px">
                            </div>
                        </td>
                    </tr>
                </table>
            </div>

            <div id="heatmap-div" class="heatmap-position" style="display:none">

                <table>
                    <tr>
                        <td>
                        <span id="geneCount">Showing ${geneProfiles.size()}
                            of ${geneProfiles.getTotalResultCount()} genes found:
                        </span>
                        </td>
                        <td>
                            <c:import url="includes/gradient-legend.jsp"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <c:import url="includes/heatmap-matrix-gene-oriented.jsp"/>
                        </td>
                    </tr>
                </table>
            </div>
        </div>

        </div>
    </c:otherwise>
</c:choose>