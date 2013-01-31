<%--
  ~ Copyright 2008-2012 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="includes/word-cloud-mock.jsp"/>

<div class="species-navigation" id="species-nav" style="width: 550px; height: 200px;">
    <div class="item human">
        <img src="resources/images/SPECIES_man.png" width="40" height="40"
             class="circle"/>
        <a href="#" class="species-icon"></a>

        <h2>Homo Sapiens</h2>
        <ul>
            <c:forEach items="${experimentAccessions.get('Homo sapiens')}" var="experimentAccession">
                <li><a href="experiments/${experimentAccession}">${experimentAccession}</a></li>
            </c:forEach>
        </ul>
    </div>
    <div class="item mouse">
        <img src="resources/images/SPECIES_mouse2.png" width="40" height="40"
             class="circle"/>
        <a href="#" class="species-icon"></a>

        <h2>Mus Musculus</h2>
        <ul>
            <c:forEach items="${experimentAccessions.get('Mus musculus')}" var="experimentAccession">
                <li><a href="experiments/${experimentAccession}">${experimentAccession}</a></li>
            </c:forEach>
        </ul>
    </div>
</div>

<!-- The JavaScript -->
<script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/resources/js/jquery.easing.1.3.js" type="text/javascript"></script>

<script type="text/javascript">
    $(function () {
        $('ul').mouseleave(function(){$(this).fadeOut(200)});
        $('#species-nav > div').hover(
                function () {
                    var $this = $(this);
                    $this.find('img').stop().animate({
                        'width':'60px',
                        'height':'60px',
                        'top':'0px',
                        'left':'0px',
                        'opacity':'1.0'
                    }, 500, 'easeOutBack', function () {
                        $(this).parent().find('ul').fadeIn(1);
                    });

                    $this.find('a:first,h2').addClass('active');
                },
                function () {
                    var $this = $(this);
                    $this.find('img').stop().animate({
                        'width':'40px',
                        'height':'40px',
                        'top':'0px',
                        'left':'0px',
                        'opacity':'1.0'
                    }, 5000, 'easeOutBack');

                    $this.find('a:first,h2').removeClass('active');
                }
        );
    });
</script>
