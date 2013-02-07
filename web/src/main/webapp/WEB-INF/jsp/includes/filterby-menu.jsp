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


<div class="filters-frame">
    <c:forEach items="${selectedFilterFactors}" var="firstFactor">
        <div class="filter-name">${firstFactor.name}:</div>
        ${firstFactor.value}<br/>
    </c:forEach>
</div>
<c:set var="filterMenuLabel" value="Change filters"/>

<span>
    <ul id="filterBy" style="display: none">
        <li><a>${filterMenuLabel}</a>
            <ul>
                <c:forEach items="${factorLevel.allFactorNames}" var="firstFactorName">
                    <li>
                        <a>${firstFactorName}</a>
                        <ul>
                            <c:forEach items="${factorLevel.getFactorsForFactorName(firstFactorName)}"
                                       var="firstFactor">
                                <c:set var="secondFactorLevel"
                                       value="${factorLevel.filterOutByFactor(firstFactor)}"/>
                                <c:choose>
                                    <c:when test="${secondFactorLevel.allFactorNames.size() == 1}">
                                        <c:forEach items="${secondFactorLevel.allFactorNames}"
                                                   var="queryFactorName">
                                            <c:set var="queryFactorType"
                                                   value="${factorLevel.resolveTypeForName(queryFactorName)}"/>
                                            <li data-serialized-factors='${secondFactorLevel.getLink(queryFactorType, firstFactor)}'
                                                style="text-decoration: underline; cursor: pointer;">${firstFactor.value}</li>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <li>
                                            <a>${firstFactor.value}</a>
                                            <ul>
                                                <c:forEach items="${secondFactorLevel.allFactorNames}"
                                                           var="secondFactorName">
                                                    <li>
                                                        <a>${secondFactorName}</a>
                                                        <ul>
                                                            <c:forEach
                                                                    items="${secondFactorLevel.getFactorsForFactorName(secondFactorName)}"
                                                                    var="secondFactor">
                                                                <c:set var="lastFactorLevel"
                                                                       value="${secondFactorLevel.filterOutByFactor(secondFactor)}"/>

                                                                <c:forEach items="${lastFactorLevel.allFactorNames}"
                                                                           var="queryFactorName">
                                                                    <c:set var="queryFactorType"
                                                                           value="${factorLevel.resolveTypeForName(queryFactorName)}"/>
                                                                    <li data-serialized-factors='${lastFactorLevel.getLink(queryFactorType, firstFactor, secondFactor)}'
                                                                        style="text-decoration: underline; cursor: pointer;">${secondFactor.value}</li>
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
                </c:forEach>
            </ul>
        </li>
    </ul>
</span>

<script type="text/javascript">

    $(function () {
        $("#filterBy").menu();
        $('li:not(:has(>ul))', 'ul#filterBy').on('click', function () {
            var json = $(this).attr('data-serialized-factors');
            var obj = $.parseJSON(json);
            $("#queryFactorType").val(obj.queryFactorType);
            $("#queryFactorValues").val(''); // clear previous selection
            $("#serializedFilterFactors").val(obj.filterFactorsURL);
            $("form#prefForm").submit();
        });
        $("#filterBy").show();
    });

</script>
