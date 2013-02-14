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


<div id="wordcloud" style="position: absolute; margin-left:75px; margin-top: 150px;">
    <img src="resources/images/home/centre_landing.png"/>
</div>

<div class="species-navigation" id="species-nav"
     style="position: absolute; margin-left:50px; margin-top: 50px; width: 600px; height: 600px;">

    <c:forEach items="${experimentAccessions.keySet()}" var="specie">

        <div class="item">
            <img src="resources/images/home/${specie}.png" width="40" height="40"
                 class="circle"/>
            <a href="#" class="species-icon"></a>

            <h2>${specie}</h2>
            <ul>
                <c:forEach items="${experimentAccessions.get(specie)}" var="experimentAccession">
                    <li><a href="experiments/${experimentAccession}">${experimentAccession}</a></li>
                </c:forEach>
            </ul>
        </div>
    </c:forEach>

</div>

<!-- The JavaScript -->
<script type="text/javascript" language="javascript"
        src="${pageContext.request.contextPath}/resources/js/jquery.easing.1.3.js" type="text/javascript"></script>

<script type="text/javascript">
    $(function () {
        clearLocalNav();
        $('#local-nav-home').addClass("active");
    });
</script>

<script type="text/javascript">
    $(function () {
        $('#species-nav ul').mouseleave(function () {
            $(this).fadeOut(200)
        });
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
    /*
     drawCircle()
     selector : a jQuery selector defining an element or array of elements
     center : the center of the circle
     radius : the radius of the circle
     angle: the angle of the circle
     x: the left offset of all points on the circle
     y: the top offset of all points on the circle
     Sample usage:
     CSS: .box { background-color:red; height:90px; width:90px;  position:absolute; margin:5px;}
     JS:drawCircle('.box', 50, 200, 90, 310, 220);
     */
    function drawCircle(selector, center, radius, angle, x, y) {
        var total = $(selector).length;
        var alpha = Math.PI * 2 / total;
        $(selector).each(function (index) {
            var theta = alpha * index;
            var pointx = Math.floor(Math.cos(theta) * radius);
            var pointy = Math.floor(Math.sin(theta) * radius);
            // here is where we're outputting the necessary
            //X and Y coordinates
            $(this).css('margin-left', pointx + x + 'px');
            $(this).css('margin-top', pointy + y + 'px');
        });
    }

    $(document).ready(function () {

        drawCircle('.item', 50, 200, 0, 310, 220);

    });

</script>
