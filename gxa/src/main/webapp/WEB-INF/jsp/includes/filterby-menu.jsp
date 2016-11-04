<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/filterby-menu.css">

<%--@elvariable id="selectedFilterFactorNamesAndValues" type="java.util.Map"--%>

<div class="gxaFiltersFrame">
    <c:forEach items="${selectedFilterFactorNamesAndValues}" var="nameFactorEntry">
        <div class="gxaFilterName">${nameFactorEntry.key}:</div>
        ${nameFactorEntry.value}<br/>
    </c:forEach>
</div>
<c:set var="filterMenuLabel" value="Change filters"/>

<%--@elvariable id="filterFactorMenu" type="java.util.Set"--%>
<%--@elvariable id="firstFactorName" type="atlas.experimentpage.baseline.FilterFactorMenuVoice"--%>
<%--@elvariable id="menuFactorNames" type="java.util.Set"--%>
<%--@elvariable id="filterFactorMenuBuilder" type="uk.ac.ebi.atlas.experimentpage.baseline.filterfactormenubuilder"--%>

<div id="filterby-menu" style="display:table-cell">
    <ul id="filterBy" style="display: none">
        <li><a>${filterMenuLabel}</a>
            <ul>
                <c:forEach items="${filterFactorMenu}" var="firstFactorName">
                    <c:if test="${menuFactorNames.contains(firstFactorName.displayName)}">
                        <li>
                            <a>${firstFactorName.displayName}</a>
                            <ul>
                                <c:forEach items="${firstFactorName.children}" var="firstFactorValue">
                                    <c:set var="secondFilterFactorMenu" value="${firstFactorValue.children}"/>
                                    <c:choose>
                                        <c:when test="${secondFilterFactorMenu.size() == 1}">
                                            <c:forEach items="${secondFilterFactorMenu}" var="secondFactorName">
                                                <li data-serialized-factors='${filterFactorMenuBuilder.getLink(secondFactorName.type, firstFactorValue.factor)}'
                                                    style="text-decoration: underline; cursor: pointer;">${firstFactorValue.displayName}</li>
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <li>
                                                <a>${firstFactorValue.displayName}</a>
                                                <ul>
                                                    <c:forEach items="${secondFilterFactorMenu}" var="secondFactorName">
                                                        <li>
                                                            <a>${secondFactorName.displayName}</a>
                                                            <ul>
                                                                <c:forEach items="${secondFactorName.children}"
                                                                           var="secondFactorValue">
                                                                    <c:set var="thirdFilterFactorMenu"
                                                                           value="${secondFactorValue.children}"/>
                                                                    <c:forEach items="${thirdFilterFactorMenu}"
                                                                               var="thirdFactorName">
                                                                        <li data-serialized-factors='${filterFactorMenuBuilder.getLink(thirdFactorName.type, firstFactorValue.factor, secondFactorValue.factor)}'
                                                                            style="text-decoration: underline; cursor: pointer;">${secondFactorValue.displayName}</li>
                                                                    </c:forEach>
                                                                </c:forEach>
                                                            </ul>
                                                        </li>
                                                    </c:forEach>
                                                </ul>
                                            </li>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </ul>
                        </li>
                    </c:if>
                </c:forEach>
            </ul>
        </li>
    </ul>
</div>

<script type="text/javascript">

    var $filterBy = $("#filterBy");
    $(function () {
        $filterBy.menu();
        $('li:not(:has(>ul))', 'ul#filterBy').on('click', function () {
            var json = $(this).attr('data-serialized-factors');
            var factorsCombination = $.parseJSON(json);
            $("#queryFactorType").val(factorsCombination.queryFactorType);
            $("#queryFactorValues").val(''); // clear previous selection
            $("#serializedFilterFactors").val(factorsCombination.serializedFactors);
            $("form#prefForm").submit();
        });
        $filterBy.show();
    });

</script>
