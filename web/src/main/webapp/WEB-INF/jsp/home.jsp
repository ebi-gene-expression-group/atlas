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

<div id="wordcloud" style="width: 550px; height: 350px; position:relative; top: 180px; left:130px;"></div>

<div class="species-navigation" id="species-nav" style="width: 550px; height: 200px;">
    <div class="item human">
        <img src="${pageContext.request.contextPath}/resources/images/SPECIES_man.png" alt="" width="40" height="40"
             class="circle"/>
        <a href="#" class="species-icon"></a>

        <h2>Homo Sapiens</h2>
        <ul>
            <li><a href="experiments/E-MTAB-513">E-MTAB-513</a></li>
        </ul>
    </div>
    <div class="item mouse">
        <img src="${pageContext.request.contextPath}/resources/images/SPECIES_mouse2.png" alt="" width="40" height="40"
             class="circle"/>
        <a href="#" class="species-icon"></a>

        <h2>Mus Musculus</h2>
        <ul>
            <li><a href="experiments/E-MTAB-599">E-MTAB-599</a></li>
        </ul>
    </div>
    <!--<div class="item rat">
        <img src="${pageContext.request.contextPath}/resources/images/SPECIES_rat.png" alt="" width="40" height="40"
             class="circle"/>
        <a href="#" class="species-icon"></a>

        <h2>Rattus Norvegicus</h2>
        <ul>
            <li><a href="#">Test1</a></li>
            <li><a href="#">Test2</a></li>
            <li><a href="#">Test3</a></li>
        </ul>
    </div>
    <div class="item fly">
        <img src="${pageContext.request.contextPath}/resources/images/SPECIES_fly3.png" alt="" width="40" height="40"
             class="circle"/>
        <a href="#" class="species-icon"></a>

        <h2>Drosophila Melanogaster</h2>
        <ul>
            <li><a href="#">Test1</a></li>
            <li><a href="#">Test2</a></li>
            <li><a href="#">Test3</a></li>
        </ul>
    </div>
    <div class="item fish">
        <img src="${pageContext.request.contextPath}/resources/images/SPECIES_zebrafish.png" alt="" width="40"
             height="40" class="circle"/>
        <a href="#" class="species-icon"></a>

        <h2>Danio Rerio</h2>
        <ul>
            <li><a href="#">Test1</a></li>
            <li><a href="#">Test2</a></li>
            <li><a href="#">Test3</a></li>
        </ul>
    </div>-->
</div>

<!-- The JavaScript -->
<script type="text/javascript" language="javascript"
        src="${pageContext.request.contextPath}/resources/js/jquery.easing.1.3.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function () {
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
                        $(this).parent().find('ul').fadeIn(500);
                    });

                    $this.find('a:first,h2').addClass('active');
                },
                function () {
                    var $this = $(this);
                    $this.find('ul').fadeOut(500);
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

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jqcloud-1.0.2.js"></script>
<script type="text/javascript">
    /*!
     * Create an array of word objects, each representing a word in the cloud
     */
    var word_list = ${wordlist};

    $(function () {
        // When DOM is ready, select the container element and call the jQCloud method, passing the array of words as the first argument.
        $("#wordcloud").jQCloud(word_list);
    });
</script>

