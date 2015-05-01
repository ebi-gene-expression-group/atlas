<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%--
  ~ Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~ http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software
  ~ distributed under the License is distributed on an "AS IS" BASIS,
  ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  ~ See the License for the specific language governing permissions and
  ~ limitations under the License.
  ~
  ~
  ~ For further details of the Gene Expression Atlas project, including source code,
  ~ downloads and documentation, please see:
  ~
  ~ http://gxa.github.com/gxa
  --%>


<div class="gxaFiltersFrame">
    <c:forEach items="${selectedFilterFactorNamesAndValues}" var="nameFactorEntry">
        <div class="gxaFilterName">${nameFactorEntry.key}:</div>
        ${nameFactorEntry.value}<br/>
    </c:forEach>
</div>
<c:set var="filterMenuLabel" value="Change filters"/>

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

    $(function () {
        $("#filterBy").menu();
        $('li:not(:has(>ul))', 'ul#filterBy').on('click', function () {
            var json = $(this).attr('data-serialized-factors');
            var factorsCombination = $.parseJSON(json);
            $("#queryFactorType").val(factorsCombination.queryFactorType);
            $("#queryFactorValues").val(''); // clear previous selection
            $("#serializedFilterFactors").val(factorsCombination.serializedFactors);
            $("form#prefForm").submit();
        });
        $("#filterBy").show();
    });

</script>
