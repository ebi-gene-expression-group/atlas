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

<h2>Baseline Gene Expression Atlas</h2>

<div id="wordcloud" style="width: 550px; height: 350px; position:absolute; top: 250px; left:250px;"></div>

<div class="navigation" id="nav">
    <div class="item human">
        <img src="${pageContext.request.contextPath}/resources/images/human.png" alt="" width="75" height="75"
             class="circle"/>
        <a href="#" class="icon"></a>

        <h2>Homo Sapiens</h2>
        <ul>
            <li><a href="experiments/E-MTAB-513">E-MTAB-513</a></li>
            <li><a href="#">Test2</a></li>
            <li><a href="#">Test3</a></li>
        </ul>
    </div>
    <div class="item mouse">
        <img src="${pageContext.request.contextPath}/resources/images/mouse.png" alt="" width="75" height="75"
             class="circle"/>
        <a href="#" class="icon"></a>

        <h2>Mus Musculus</h2>
        <ul>
            <li><a href="experiments/E-MTAB-599">E-MTAB-599</a></li>
            <li><a href="#">Test2</a></li>
            <li><a href="#">Test3</a></li>
        </ul>
    </div>
    <div class="item rat">
        <img src="${pageContext.request.contextPath}/resources/images/rat.png" alt="" width="75" height="75"
             class="circle"/>
        <a href="#" class="icon"></a>

        <h2>Rattus Norvegicus</h2>
        <ul>
            <li><a href="#">Test1</a></li>
            <li><a href="#">Test2</a></li>
            <li><a href="#">Test3</a></li>
        </ul>
    </div>
    <div class="item fly">
        <img src="${pageContext.request.contextPath}/resources/images/fly.png" alt="" width="75" height="75"
             class="circle"/>
        <a href="#" class="icon"></a>

        <h2>Drosophila Melanogaster</h2>
        <ul>
            <li><a href="#">Test1</a></li>
            <li><a href="#">Test2</a></li>
            <li><a href="#">Test3</a></li>
        </ul>
    </div>
    <div class="item fish">
        <img src="${pageContext.request.contextPath}/resources/images/fish.png" alt="" width="75" height="75"
             class="circle"/>
        <a href="#" class="icon"></a>

        <h2>Danio Rerio</h2>
        <ul>
            <li><a href="#">Test1</a></li>
            <li><a href="#">Test2</a></li>
            <li><a href="#">Test3</a></li>
        </ul>
    </div>
</div>
</div>

<!-- The JavaScript -->
<script type="text/javascript" language="javascript"
        src="${pageContext.request.contextPath}/resources/js/jquery.easing.1.3.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function () {
        $('#nav > div').hover(
                function () {
                    var $this = $(this);
                    $this.find('img').stop().animate({
                        'width':'200px',
                        'height':'200px',
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
                        'width':'75px',
                        'height':'75px',
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
    var word_list = new Array(
            {text:"Homo sapiens", weight:13, link:"experiments/E-MTAB-513"},
            {text:"Mus musculus", weight:10.5, link:"experiments/E-MTAB-599"},
            {text:"Dolor", weight:9.4},
            {text:"Sit", weight:8},
            {text:"Amet", weight:6.2},
            {text:"Consectetur", weight:5},
            {text:"Adipiscing", weight:5},
            {text:"Elit", weight:5},
            {text:"Nam et", weight:5},
            {text:"Leo", weight:4},
            {text:"ArrayExpress", weight:4, link:"http://www.ebi.ac.uk/arrayexpress"},
            {text:"Pellentesque", weight:3},
            {text:"habitant", weight:3},
            {text:"morbi", weight:3},
            {text:"tristisque", weight:3},
            {text:"senectus", weight:3},
            {text:"et netus", weight:3},
            {text:"et malesuada", weight:3},
            {text:"fames", weight:2},
            {text:"ac turpis", weight:2},
            {text:"egestas", weight:2},
            {text:"Aenean", weight:2},
            {text:"vestibulum", weight:2},
            {text:"elit", weight:2},
            {text:"sit amet", weight:2},
            {text:"metus", weight:2},
            {text:"adipiscing", weight:2},
            {text:"ut ultrices", weight:2},
            {text:"justo", weight:1},
            {text:"dictum", weight:1},
            {text:"Ut et leo", weight:1},
            {text:"metus", weight:1},
            {text:"at molestie", weight:1},
            {text:"purus", weight:1},
            {text:"Curabitur", weight:1},
            {text:"diam", weight:1},
            {text:"dui", weight:1},
            {text:"ullamcorper", weight:1},
            {text:"id vuluptate ut", weight:1},
            {text:"mattis", weight:1},
            {text:"et nulla", weight:1},
            {text:"Sed", weight:1}
    );

    $(function () {
        // When DOM is ready, select the container element and call the jQCloud method, passing the array of words as the first argument.
        $("#wordcloud").jQCloud(word_list);
    });
</script>

