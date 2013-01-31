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
    <c:forEach items="${selectedFactors}" var="factor">
        <div class="filter-name">${factor.name}:</div>${factor.value}<br/>
    </c:forEach>
</div>
<c:set var="filterMenuLabel" value="Change filters"/>

<span>
    <ul id="filterBy" style="display: none">
        <li><a>${filterMenuLabel}</a>
            <ul>
                <c:forEach items="${filterByMenu}" var="level1">
                    <li>
                        <a>${level1.key}</a>
                        <ul>
                            <c:forEach items="${level1.value}" var="level2">
                                <li>
                                    <a>${level2.key}</a>
                                    <ul>
                                        <c:forEach items="${level2.value}" var="level3">
                                            <li>
                                                <a>${level3.key}</a>
                                                <ul>
                                                    <c:forEach items="${level3.value}" var="level4">
                                                        <li data-serialized-factors='${level4.value}'
                                                            style="text-decoration: underline; cursor: pointer;">${level4.key}</li>
                                                    </c:forEach>
                                                </ul>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </li>
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
            var jsonData = JSON.parse($(this).attr('data-serialized-factors'));
            $("#queryFactorType").val(jsonData.queryFactorType);
            $("#queryFactorValues").val(''); // clear previous selection
            $("#serializedFilterFactorValues").val(jsonData.filterFactorsURL);
            $("form#prefForm").submit();
        });
        $("#filterBy").show();
    });

</script>
