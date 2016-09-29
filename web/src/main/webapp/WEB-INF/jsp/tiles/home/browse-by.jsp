<div class="callout browse-by" data-equalizer-watch>
    <ul class="tabs" data-tabs id="browse-by-tabs">
        <li class="tabs-title is-active"><a href="#by-species" aria-selected="true">By species</a></li>
        <li class="tabs-title"><a href="#by-tissue">By tissue</a></li>
        <li class="tabs-title"><a href="#by-gene">By gene</a></li>
        <li class="tabs-title"><a href="#by-ef">By EF</a></li>
        <li class="tabs-title"><a href="#by-disease">By disease</a></li>
    </ul>

    <div class="tabs-content" data-tabs-content="browse-by-tabs">
        <div class="tabs-panel is-active" id="by-species">
            <div class="row">
                <div class="columns small-4 text-center combo">
                    <a href="#"><span class="icon icon-generic large-green" data-icon="R"/>
                        <h5>Plants</h5>
                        <p>
                            671 experiments<br/>
                            <a href="#" class="differential"><span data-tooltip aria-haspopup="true" class="differential tiny button-rd" title="Differential experiments">D</span> 633</a>
                            <a href="#" class="baseline padding-left-medium"><span data-tooltip aria-haspopup="true" class="baseline tiny button-rd" title="Baseline experiments">B</span> 38</a>
                        </p>
                    </a>
                </div>
                    <div class="columns small-4 text-center combo">
                        <a href="#"><span class="icon icon-species large-red" data-icon="H"></span>
                            <h5>Human</h5>
                            <p>
                                1058 experiments<br/>
                                <a href="#" class="differential"><span data-tooltip aria-haspopup="true" class="differential tiny button-rd" title="Differential experiments">D</span> 1037</a>
                                <a href="#" class="baseline padding-left-medium"><span data-tooltip aria-haspopup="true" class="baseline tiny button-rd" title="Baseline experiments">B</span> 21</a>
                            </p>
                        </a>
                    </div>
                    <div class="columns small-4 text-center combo">
                        <a href="#">
                            <span class="icon icon-species large-blue" data-icon="W"></span>
                            <h5> Worms</h5>
                            <p>
                                22 experiments<br/>
                                <a  href="#" class="differential"><span data-tooltip aria-haspopup="true" class="differential tiny button-rd" title="Differential experiments">D</span> 21</a>
                                <a  href="#" class="baseline padding-left-medium"><span data-tooltip aria-haspopup="true" class="baseline tiny button-rd " title="Baseline experiments">B</span> 1</a>
                            </p>
                        </a>
                    </div>
            </div>

            <div class="row">
                <div class="small-6 small-centered columns margin-top-large">
                    <a href="#" class="button float-center">View all species</a>
                </div>
            </div>
        </div>



        <div class="tabs-panel" id="by-tissue">
            <div class="row">
                <div class="columns small-4 text-center combo">
                    <a href="#">
                        <img src="https://progressivenutritional.com/hypfiles/uploads/2015/12/head-icon.png" width="80"/>
                        <h5>Brain</h5>
                        <p>
                            16 experiments<br/>
                            <a  href="#" class="differential"><span data-tooltip aria-haspopup="true" class="differential tiny button-rd" title="Differential experiments">D</span> 8</a>
                            <a  href="#" class="baseline padding-left-medium"><span data-tooltip aria-haspopup="true" class="baseline tiny button-rd" title="Baseline experiments">B</span> 8</a>
                        </p>
                    </a>
                </div>
                <div class="columns small-4 text-center combo">
                    <a href="#">
                        <img src="https://progressivenutritional.com/hypfiles/uploads/2015/12/head-icon.png" width="80"/>
                        <h5>Endocrine tissues</h5>
                        <p>
                            45 experiments<br/>
                            <a  href="#" class="differential"><span  data-tooltip aria-haspopup="true" class="differential tiny button-rd" title="Differential experiments">D</span> 42</a>
                            <a  href="#" class="baseline padding-left-medium"><span data-tooltip aria-haspopup="true" class="baseline tiny button-rd " title="Baseline experiments" >B</span> 3</a>
                        </p>
                    </a>
                </div>
                <div class="columns small-4 text-center combo">
                    <a href="#">
                        <img src="https://progressivenutritional.com/hypfiles/uploads/2015/12/head-icon.png" width="80"/>
                        <h5>Bone marrow &amp; immune system</h5>
                        <p>
                            68 experiments<br/>
                            <a  href="#" class="differential"><span  data-tooltip aria-haspopup="true" class="differential tiny button-rd" title="Differential experiments">D</span> 21</a>
                            <a  href="#" class="baseline padding-left-medium"><span data-tooltip aria-haspopup="true" class="baseline tiny button-rd " title="Baseline experiments" >B</span> 1</a>
                        </p>
                    </a>
                </div>
            </div>

            <div class="row">
                <div class="small-6 small-centered columns margin-top-large">
                    <a href="#" class="button float-center">View all tissues</a>
                </div>
            </div>
        </div>



        <div class="tabs-panel" id="by-gene">
            <h5>By gene category</h5>
            <div style="-webkit-column-count: 3; -moz-column-count: 3; column-count: 3;">
                <ul>
                    <li><a href="#">protein coding gene</a></li>
                    <li><a href="#">miRNA gene</a></li>
                    <li><a href="#">lincRNA gene</a></li>
                    <li><a href="#">pseudogene</a></li>
                    <li><a href="#">CpG island</a></li>
                    <li><a href="#">antisense lncRNA gene</a></li>
                    <li><a href="#">gene segment</a></li>
                    <li><a href="#">lncRNA gene</a></li>
                    <li><a href="#">scRNA gene</a></li>
                    <li><a href="#">heritable phenotypic marker</a></li>
                    <li><a href="#">polymorphic pseudogene</a></li>
                    <li><a href="#">unclassified other genome feature</a></li>
                    <li><a href="#">unclassified non-coding RNA gene</a></li>
                    <li><a href="#">unclassified gene</a></li>
                </ul>
            </div>

            <h5>By gene name</h5>
            <form id="global-search" name="global-search" action="/ebisearch/search.ebi" method="GET">
                <fieldset>
                    <div class="input-group">
                        <input type="text" name="query" id="global-searchbox" class="input-group-field" placeholder="Search by gene name">
                        <div class="input-group-button">
                            <input type="submit" name="submit" value="Search" class="button">
                            <input type="hidden" name="db" value="allebi" checked="checked">
                            <input type="hidden" name="requestFrom" value="global-masthead" checked="checked">
                        </div>
                    </div>
                </fieldset>
            </form>
        </div>



        <div class="tabs-panel" id="by-ef">
            <h5>By experimental factor</h5>

            <ul>
                <li class="padding-bottom-large">
                    <a href="#">organism part</a> <span  data-tooltip aria-haspopup="true" class="differential tiny button-rd " title="Differential experiments">D</span> <a  href="#" class="differential">42 </a><span data-tooltip aria-haspopup="true" class="baseline tiny button-rd " title="Baseline experiment icon" >B</span> <a  href="#" class="baseline">3</a>
                </li>
                <li class="padding-bottom-large">
                    <a href="#">RNA</a> <span data-tooltip aria-haspopup="true" class="differential tiny button-rd " title="Differential experiments">D</span> <a  href="#" class="differential">42 </a><span data-tooltip aria-haspopup="true" class="baseline tiny button-rd " title="Baseline experiment icon" >B</span> <a  href="#" class="baseline">3</a>
                </li>
                <li class="padding-bottom-large">
                    <a href="#">cell line</a> <span data-tooltip aria-haspopup="true" class="differential tiny button-rd " title="Differential experiments">D</span> <a  href="#" class="differential">42 </a><span data-tooltip aria-haspopup="true" class="baseline tiny button-rd " title="Baseline experiment icon" >B</span> <a  href="#" class="baseline">3</a>
                </li>
                <li class="padding-bottom-large">
                    <a href="#">cellular component</a> <span  data-tooltip aria-haspopup="true" class="differential tiny button-rd " title="Differential experiments">D</span> <a  href="#" class="differential">42 </a><span data-tooltip aria-haspopup="true" class="baseline tiny button-rd " title="Baseline experiment icon" >B</span> <a  href="#" class="baseline">3</a>
                </li>
                <li class="padding-bottom-large">
                    <a href="#">cell type</a> <span  data-tooltip aria-haspopup="true" class="differential tiny button-rd " title="Differential experiments">D</span> <a  href="#" class="differential">42 </a><span data-tooltip aria-haspopup="true" class="baseline tiny button-rd " title="Baseline experiment icon" >B</span> <a  href="#" class="baseline">3</a>
                </li>
                <li class="padding-bottom-large">
                    <a href="#">developmental stage</a> <span  data-tooltip aria-haspopup="true" class="differential tiny button-rd " title="Differential experiments">D</span> <a  href="#" class="differential">42 </a><span data-tooltip aria-haspopup="true" class="baseline tiny button-rd " title="Baseline experiment icon" >B</span> <a  href="#" class="baseline">3</a>
                </li>
                <li class="padding-bottom-large">
                    <a href="#">ecotype</a> <span  data-tooltip aria-haspopup="true" class="differential tiny button-rd " title="Differential experiments">D</span> <a  href="#" class="differential">42 </a><span data-tooltip aria-haspopup="true" class="baseline tiny button-rd " title="Baseline experiment icon" >B</span> <a  href="#" class="baseline">3</a>
                </li>
                <li>
                    <a href="#">time</a> <span  data-tooltip aria-haspopup="true" class="differential tiny button-rd " title="Differential experiments">D</span> <a  href="#" class="differential">42 </a><span data-tooltip aria-haspopup="true" class="baseline tiny button-rd " title="Baseline experiment icon" >B</span> <a  href="#" class="baseline">3</a>
                </li>
            </ul>
        </div>



        <div class="tabs-panel" id="by-disease">
            <h5>By disease category</h5>
            <div style="-webkit-column-count: 3; -moz-column-count: 3;column-count: 3;">
                <ul>
                    <li><a href="#">developmental anomalies embryogenesis</a></li>
                    <li><a href="#">endocrine</a></li>
                    <li><a href="#">renal</a></li>
                    <li><a href="#">otorhinolaryngological</a></li>
                    <li><a href="#">tumors</a></li>
                    <li><a href="#">haematological</a></li>
                    <li><a href="#">surgical maxillo facial</a></li>
                    <li><a href="#">immunologicale</a></li>
                    <li><a href="#">cardiac</a></li>
                    <li><a href="#">inborn errors metabolism</a></li>
                    <li><a href="#">skin</a></li>
                    <li><a href="#">eye</a></li>
                    <li><a href="#">bone</a></li>
                    <li><a href="#">neurological</a></li>
                </ul>
            </div>

            <h5>By disease name</h5>
            <div style="-webkit-column-count: 3; -moz-column-count: 3;column-count: 3;">
                <ul>
                    <li><a href="#">avian influenza</a></li>
                    <li><a href="#">chronic rhinosinusitis</a></li>
                </ul>
            </div>

            <div class="row">
                <div class="small-6 small-centered columns margin-top-large">
                    <a href="#" class="button float-center">View all diseases (A-Z)</a>
                </div>
            </div>
        </div>
    </div>
</div>



<%--<div class="callout "  data-equalizer-watch>--%>

    <%--<ul class="tabs" data-tabs id="example-tabs">--%>
        <%--<li class="tabs-title is-active"><a href="#panel1" aria-selected="true">by species</a></li>--%>
        <%--<li class="tabs-title"><a href="#panel2">By tissue</a></li>--%>
        <%--<li class="tabs-title"><a href="#panel3">By gene</a></li>--%>
        <%--<li class="tabs-title"><a href="#panel4">By EF</a></li>--%>
        <%--<li class="tabs-title"><a href="#panel5">By disease</a></li>--%>
    <%--</ul>--%>

    <%--<div class="tabs-content" data-tabs-content="example-tabs">--%>
        <%--<div class="tabs-panel is-active" id="panel1">--%>
            <%--<div >--%>
                <%--<div class="columns small-4 text-center combo">--%>
                    <%--<a href="#"><span class="icon icon-generic green" data-icon="R"/></a>--%>
                    <%--<h5>Plants</h5>--%>
                    <%--<p>671 experiments<br/>--%>
                        <%--<span data-tooltip aria-haspopup="true" class="differential tiny button-rd" title="Differential experiment icon">D</span>--%>
                        <%--<a href="#" class="differential">633</a>--%>
                        <%--<span data-tooltip aria-haspopup="true" class="baseline tiny button-rd" title="Baseline experiment icon">B</span>--%>
                        <%--<a href="#" class="baseline">38</a>--%>
                    <%--</p>--%>
                <%--</div>--%>
                <%--<div class="columns small-4 text-center combo"> <a href="#"><span class="icon icon-species red" data-icon="H"></span><h5> Human</h5><p>1058 experiments<br/><span  data-tooltip aria-haspopup="true" class="differential tiny button-rd " title="Differential experiment icon">D</span> <a  href="#" class="differential">1037 </a><span data-tooltip aria-haspopup="true" class="baseline tiny button-rd " title="Baseline experiment icon" >B</span> <a  href="#" class="baseline">21</a></p></a></div>--%>
                <%--<div class="columns small-4 text-center combo"> <a href="#"><span class="icon icon-species blue" data-icon="W"></span><h5> Worms</h5><p>22 experiments<br/><span  data-tooltip aria-haspopup="true" class="differential tiny button-rd" title="Differential experiment icon">D</span> <a  href="#" class="differential">21</a>  <span data-tooltip aria-haspopup="true" class="baseline tiny button-rd " title="Baseline experiment icon" >B</span> <a  href="#" class="baseline">1</a></p></a></div>--%>
            <%--</div>--%>
            <%--<a href="#" class="button margin-top-xlarge">View all species</a>--%>
        <%--</div>--%>

        <%--<div class="tabs-panel" id="panel2">--%>
            <%--<div >--%>
                <%--<div class="columns medium-4 large-4 text-center combo"> <a href="#"><img src="https://progressivenutritional.com/hypfiles/uploads/2015/12/head-icon.png" width="80"/> <h5>Brain</h5><p>16 experiments<br/><span  data-tooltip aria-haspopup="true" class="differential tiny button-rd " title="Differential experiment icon">D</span> <a  href="#" class="differential">8 </a><span data-tooltip aria-haspopup="true" class="baseline tiny button-rd " title="Baseline experiment icon" >B</span> <a  href="#" class="baseline">8</a></p></a></div>--%>
                <%--<div class="columns medium-4 large-4 text-center combo"> <a href="#"><img src="https://draxe.com/wp-content/uploads/2014/11/Water-Icon.jpg" width="80"/> </span><h5> Endocrine tissues</h5><p>45 experiments<br/><span  data-tooltip aria-haspopup="true" class="differential tiny button-rd " title="Differential experiment icon">D</span> <a  href="#" class="differential">42 </a><span data-tooltip aria-haspopup="true" class="baseline tiny button-rd " title="Baseline experiment icon" >B</span> <a  href="#" class="baseline">3</a></p></a></div>--%>
                <%--<div class="columns medium-4 large-4 text-center combo"> <a href="#"><img src="images/bone-marrow.png" width="80"/> <h5> Bone marrow & immune system</h5><p>68 experiments<br/><span  data-tooltip aria-haspopup="true" class="differential tiny button-rd" title="Differential experiment icon">D</span> <a  href="#" class="differential">21</a>  <span data-tooltip aria-haspopup="true" class="baseline tiny button-rd " title="Baseline experiment icon" >B</span> <a  href="#" class="baseline">1</a></p></a></div>--%>
            <%--</div>--%>
            <%--<a href="#" class="button margin-top-xlarge">View all tissues</a>--%>
        <%--</div>--%>
        <%--<div class="tabs-panel" id="panel3">--%>
            <%--<h5>By gene category</h5>--%>
            <%--<div style="-webkit-column-count: 3; -moz-column-count: 3; column-count: 3;">--%>
                <%--<ul>--%>
                    <%--<li><a href="#">protein coding gene </a></li>--%>
                    <%--<li><a href="#">miRNA gene</a></li>--%>
                    <%--<li><a href="#">lincRNA gene</a></li>--%>
                    <%--<li><a href="#">pseudogene</a></li>--%>
                    <%--<li><a href="#">CpG island</a></li>--%>
                    <%--<li><a href="#">antisense lncRNA gene</a></li>--%>
                    <%--<li><a href="#">gene segment</a></li>--%>
                    <%--<li><a href="#">lncRNA gene</a></li>--%>
                    <%--<li><a href="#">scRNA gene</a></li>--%>
                    <%--<li><a href="#">heritable phenotypic marker</a></li>--%>
                    <%--<li><a href="#">polymorphic pseudogene</a></li>--%>
                    <%--<li><a href="#">unclassified other genome feature</a></li>--%>
                    <%--<li><a href="#">unclassified non-coding RNA gene</a></li>--%>
                    <%--<li><a href="#">unclassified gene</a></li>--%>
                <%--</ul>--%>
            <%--</div>--%>
            <%--<h5>By gene name</h5>--%>
            <%--<form id="global-search" name="global-search" action="/ebisearch/search.ebi" method="GET">--%>
                <%--<fieldset>--%>
                    <%--<div class="input-group">--%>
                        <%--<input type="text" name="query" id="global-searchbox" class="input-group-field" placeholder="Search by gene name">--%>
                        <%--<div class="input-group-button">--%>
                            <%--<input type="submit" name="submit" value="Search" class="button">--%>
                            <%--<input type="hidden" name="db" value="allebi" checked="checked">--%>
                            <%--<input type="hidden" name="requestFrom" value="global-masthead" checked="checked">--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</fieldset>--%>
            <%--</form>--%>

        <%--</div>--%>
        <%--<div class="tabs-panel" id="panel4">--%>
            <%--<h5>By Experimental factor</h5>--%>

            <%--<ul style="line-height:2.2;">--%>
                <%--<li><a href="#">organism part</a> <span  data-tooltip aria-haspopup="true" class="differential tiny button-rd " title="Differential experiment icon">D</span> <a  href="#" class="differential">42 </a><span data-tooltip aria-haspopup="true" class="baseline tiny button-rd " title="Baseline experiment icon" >B</span> <a  href="#" class="baseline">3</a></li>--%>
                <%--<li><a href="#">RNA</a> <span data-tooltip aria-haspopup="true" class="differential tiny button-rd " title="Differential experiment icon">D</span> <a  href="#" class="differential">42 </a><span data-tooltip aria-haspopup="true" class="baseline tiny button-rd " title="Baseline experiment icon" >B</span> <a  href="#" class="baseline">3</a></li>--%>
                <%--<li><a href="#">cell line</a> <span data-tooltip aria-haspopup="true" class="differential tiny button-rd " title="Differential experiment icon">D</span> <a  href="#" class="differential">42 </a><span data-tooltip aria-haspopup="true" class="baseline tiny button-rd " title="Baseline experiment icon" >B</span> <a  href="#" class="baseline">3</a></li>--%>
                <%--<li><a href="#">cellular component</a> <span  data-tooltip aria-haspopup="true" class="differential tiny button-rd " title="Differential experiment icon">D</span> <a  href="#" class="differential">42 </a><span data-tooltip aria-haspopup="true" class="baseline tiny button-rd " title="Baseline experiment icon" >B</span> <a  href="#" class="baseline">3</a></li>--%>
                <%--<li><a href="#">cell type</a> <span  data-tooltip aria-haspopup="true" class="differential tiny button-rd " title="Differential experiment icon">D</span> <a  href="#" class="differential">42 </a><span data-tooltip aria-haspopup="true" class="baseline tiny button-rd " title="Baseline experiment icon" >B</span> <a  href="#" class="baseline">3</a></li>--%>
                <%--<li><a href="#">developmental stage</a> <span  data-tooltip aria-haspopup="true" class="differential tiny button-rd " title="Differential experiment icon">D</span> <a  href="#" class="differential">42 </a><span data-tooltip aria-haspopup="true" class="baseline tiny button-rd " title="Baseline experiment icon" >B</span> <a  href="#" class="baseline">3</a></li>--%>
                <%--<li><a href="#">ecotype</a> <span  data-tooltip aria-haspopup="true" class="differential tiny button-rd " title="Differential experiment icon">D</span> <a  href="#" class="differential">42 </a><span data-tooltip aria-haspopup="true" class="baseline tiny button-rd " title="Baseline experiment icon" >B</span> <a  href="#" class="baseline">3</a></li>--%>
                <%--<li><a href="#">time</a> <span  data-tooltip aria-haspopup="true" class="differential tiny button-rd " title="Differential experiment icon">D</span> <a  href="#" class="differential">42 </a><span data-tooltip aria-haspopup="true" class="baseline tiny button-rd " title="Baseline experiment icon" >B</span> <a  href="#" class="baseline">3</a></li>--%>
            <%--</ul>--%>

        <%--</div>--%>

        <%--<div class="tabs-panel" id="panel5">--%>
            <%--<div style="-webkit-column-count: 3;-moz-column-count: 3;column-count: 3;">--%>
                <%--<h5>By disease category</h5>--%>
                <%--<ul>--%>
                    <%--<li><a href="#">developmental anomalies embryogenesis</a></li>--%>
                    <%--<li><a href="#">endocrine</a></li>--%>
                    <%--<li><a href="#">renal</a></li>--%>
                    <%--<li><a href="#">otorhinolaryngological</a></li>--%>
                    <%--<li><a href="#">tumors</a></li>--%>
                    <%--<li><a href="#">haematological</a></li>--%>
                    <%--<li><a href="#">surgical maxillo facial</a></li>--%>
                    <%--<li><a href="#">immunologicale</a></li>--%>
                    <%--<li><a href="#">cardiac</a></li>--%>
                    <%--<li><a href="#">inborn errors metabolism</a></li>--%>
                    <%--<li><a href="#">skin</a></li>--%>
                    <%--<li><a href="#">eye</a></li>--%>
                    <%--<li><a href="#">bone</a></li>--%>
                    <%--<li><a href="#">neurological</a></li>--%>
                <%--</ul>--%>
                <%--<h5>By disease name</h5>--%>
                <%--<ul>--%>
                    <%--<li><a href="#">avian influenza</a></li>--%>
                    <%--<li><a href="#">chronic rhinosinusitis</a></li>--%>
                <%--</ul>--%>
                <%--<a href="#" class="button margin-top-xlarge">View all diseases (A-Z)</a>--%>
            <%--</div>--%>
        <%--</div>--%>
    <%--</div>--%>

<%--</div>--%>
