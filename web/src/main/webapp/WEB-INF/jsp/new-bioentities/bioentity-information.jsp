<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/new-bioentities/bioentity-information.css"/>

<div class="grid_18 omega gxaBioentityInformationCard">
    <table>
    <c:forEach var="propertyType" items="${propertyNames.keySet()}">
    <c:choose>
        <c:when test="${propertyType == 'go' || propertyType == 'po'}">
            <c:set var="relevantGoPoLinks" value="${bioEntityPropertyService.fetchRelevantGoPoLinks(propertyType, 3)}"/>
            <c:set var="allGoPoLinks" value="${bioEntityPropertyService.fetchGoPoLinksOrderedByDepth(propertyType)}"/>

            <c:if test="${relevantGoPoLinks.size() > 0}">
                <tr>
                    <td class="gxaBioentityInformationCardPropertyType">${propertyNames.get(propertyType)}</td>
                    <td class="gxaBioentityInformationCardPropertyValue">
                        <div id="${propertyType}RelevantLinks">
                            <c:set var="count" value="0"/>
                            <c:forEach var="goLink" items="${relevantGoPoLinks}">
                                <c:set var="count" value="${count + 1}"/>
                                <c:set var="comma" value=""/>
                                <c:if test="${count < relevantGoPoLinks.size()}">
                                    <c:set var="comma" value=","/>
                                </c:if>

                                <c:set var="preLinkHTML" value=""/>
                                <c:set var="postLinkHTML" value=""/>
                                <c:if test="${not goLink.getUrl().isEmpty()}">
                                    <c:set var="preLinkHTML" value="<a class=\"bioEntityCardLink\" href=\"${goLink.getUrl()}\" target=\"_blank\">"/>
                                    <c:set var="postLinkHTML" value="</a>"/>
                                </c:if>
                                <span>${preLinkHTML}${goLink.getText()}${postLinkHTML}${comma}</span>
                            </c:forEach>

                            <c:if test="${allGoPoLinks.size() > relevantGoPoLinks.size()}">
                                <a id="${propertyType}MoreLinks" role="button" style="cursor: pointer">(... and ${allGoPoLinks.size() - relevantGoPoLinks.size()} more)</a>
                            </c:if>
                        </div>

                        <c:if test="${allGoPoLinks.size() > relevantGoPoLinks.size()}">
                            <div id="${propertyType}AllLinks" style="display: none">
                                <c:set var="count" value="0"/>
                                <c:forEach var="goLink" items="${allGoPoLinks}">
                                    <c:set var="count" value="${count + 1}"/>
                                    <c:set var="comma" value=""/>
                                    <c:if test="${count < allGoPoLinks.size()}">
                                        <c:set var="comma" value=","/>
                                    </c:if>

                                    <c:set var="preLinkHTML" value=""/>
                                    <c:set var="postLinkHTML" value=""/>
                                    <c:if test="${not goLink.getUrl().isEmpty()}">
                                        <c:set var="preLinkHTML" value="<a class=\"bioEntityCardLink\" href=\"${goLink.getUrl()}\" target=\"_blank\">"/>
                                        <c:set var="postLinkHTML" value="</a>"/>
                                    </c:if>
                                    <span>${preLinkHTML}${goLink.getText()}${postLinkHTML}${comma}</span>
                                </c:forEach>
                                <a id="${propertyType}LessLinks" role="button" style="cursor: pointer">(show less)</a>
                            </div>
                        </c:if>
                    </td>
                </tr>
            </c:if>
        </c:when>

        <c:otherwise>
            <c:set var="propertyLinks" value="${bioEntityPropertyService.fetchPropertyLinks(propertyType)}"/>
            <c:if test="${propertyLinks.size() > 0}">
                <tr>
                    <td class="gxaBioEntityCardPropertyType">${propertyNames.get(propertyType)}</td>
                    <td class="gxaBioEntityCardPropertyValue">
                        <c:set var="count" value="0"/>
                        <c:forEach var="propertyLink" items="${propertyLinks}">
                            <c:set var="count" value="${count + 1}"/>
                            <c:set var="comma" value=""/>
                            <c:if test="${count < propertyLinks.size()}">
                                <c:set var="comma" value=","/>
                            </c:if>
                            <c:set var="preLinkHTML" value=""/>
                            <c:set var="postLinkHTML" value=""/>
                            <c:if test="${not propertyLink.getUrl().isEmpty()}">
                                <c:set var="preLinkHTML" value="<a class=\"bioEntityCardLink\" href=\"${propertyLink.getUrl()}\" target=\"_blank\">"/>
                                <c:set var="postLinkHTML" value="</a>"/>
                            </c:if>
                            <span>${preLinkHTML}${propertyLink.getText()}${postLinkHTML}${comma}</span>
                        </c:forEach>
                    </td>
                </tr>
            </c:if>
        </c:otherwise>
    </c:choose>
    </c:forEach>
    </table>
</div>

<script>
    (function addClickEventsToExpandAndCollapseGoAndPoTermNames() {
        $.each(
            ["go", "po"],
            function (i, val) {
                $('#' + val + 'MoreLinks').click(function () {
                    $('#' + val + 'MoreLinks').hide();
                    $('#' + val + 'RelevantLinks').hide();
                    $('#' + val + 'AllLinks').show();
                    $('#' + val + 'LessLinks').show();
                    return false;
                });
                $('#' + val + 'LessLinks').click(function () {
                    $('#' + val + 'LessLinks').hide();
                    $('#' + val + 'AllLinks').hide();
                    $('#' + val + 'RelevantLinks').show();
                    $('#' + val + 'MoreLinks').show();
                    return false;
                });
            });
    })();
</script>
