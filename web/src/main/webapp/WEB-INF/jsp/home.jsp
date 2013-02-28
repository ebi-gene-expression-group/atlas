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

<div class="species-navigation" id="species-nav">

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
                        <a href="experiments/${experimentAccession}${experimentLinks.get(key)}">${experimentDisplayNames.get(experimentAccession)}</a>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </c:forEach>

</div>

<div class="wordcloud">
    <img src="resources/images/home/centre_landing1.png"/>
</div>

<!-- The JavaScript -->


<script type="text/javascript">

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
        // x and y offset is vice versa!
        drawEllipse('.item', 230, 310, 220, 330, 0);

    });

</script>
