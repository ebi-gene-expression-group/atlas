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


<div id="wordcloud" style="position: absolute; margin-left:200px; margin-top: 100px;">
    <img src="resources/images/home/centre_landing.png"/>
</div>

<div class="species-navigation" id="species-nav"
     style="position: relative; top: 75px; margin-left:50px; width: 600px; height: 600px;">

    <c:forEach items="${experimentAccessions.keySet()}" var="specie">

        <div class="item">
            <img src="resources/images/home/${specie}.png" width="40" height="40"
                 class="circle"/>
            <a href="#" class="species-icon"></a>

            <h2>${specie}</h2>
            <ul>
                <c:forEach items="${experimentAccessions.get(specie)}" var="experimentAccession">
                    <c:set var="key" value="${experimentAccession}${specie}"/>
                    <li>
                        <a href="experiments/${experimentAccession}${experimentLinks.get(key)}">${experimentAccession}</a>
                    </li>
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

    /*
     drawEllipse()
     selector : a jQuery selector defining an element or array of elements
     x: the left offset of all points on the ellipse
     y: the top offset of all points on the ellipse
     a: the height of the ellipse
     b: the width of the ellipse
     angle: the angle of the ellipse
     Sample usage:
     CSS: .box { background-color:red; height:60px; width:60px;  position:absolute; margin:5px;}
     JS:drawEllipse(".box", 230,300, 200, 350, 360);
     */
    function drawEllipse(selector, x, y, a, b, angle) {
        var steps = $(selector).length;
        var i = 0;
        var beta = -angle * (Math.PI / 180);
        var sinbeta = Math.sin(beta);
        var cosbeta = Math.cos(beta);
        $(selector).each(function (index) {
            i += (360 / steps);
            var alpha = i * (Math.PI / 180);
            var sinalpha = Math.sin(alpha);
            var cosalpha = Math.cos(alpha);
            var X = x + (a * cosalpha * cosbeta - b * sinalpha * sinbeta);
            var Y = y + (a * cosalpha * sinbeta + b * sinalpha * cosbeta);
            X = Math.floor(X);
            Y = Math.floor(Y);
            //again, here's where the important X and Y coordinates
            //are being output
            $(this).css('margin-top', X + 'px');
            $(this).css('margin-left', Y + 'px');
        });
    }

    $(document).ready(function () {

        drawEllipse('.item', 200, 350, 250, 390, 360);

    });

</script>
