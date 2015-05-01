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
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<footer>
    <%--
    <!-- Optional local footer (insert citation / project-specific copyright / etc here -->
    <!--
    <div id="local-footer" class="grid_24 clearfix">
      <p>How to reference this page: ...</p>
    </div>
    -->
    <!-- End optional local footer -->
      --%>
    <div id="global-footer" class="grid_24">
        <nav id="global-nav-expanded">
            <div class="grid_4 alpha">
                <h3 class="embl-ebi"><a href="//www.ebi.ac.uk/" title="EMBL-EBI">EMBL-EBI</a></h3>
            </div>

            <div class="grid_4">
                <h3 class="services"><a href="//www.ebi.ac.uk/services">Services</a></h3>
            </div>

            <div class="grid_4">
                <h3 class="research"><a href="//www.ebi.ac.uk/research">Research</a></h3>
            </div>

            <div class="grid_4">
                <h3 class="training"><a href="//www.ebi.ac.uk/training">Training</a></h3>
            </div>

            <div class="grid_4">
                <h3 class="industry"><a href="//www.ebi.ac.uk/industry">Industry</a></h3>
            </div>

            <div class="grid_4 omega">
                <h3 class="about"><a href="//www.ebi.ac.uk/about">About us</a></h3>
            </div>

        </nav>

        <section id="ebi-footer-meta" class="gxaExtraPadding">
            <p class="address">EMBL-EBI, Wellcome Trust Genome Campus, Hinxton, Cambridgeshire, CB10 1SD, UK &nbsp;
                &nbsp; +44 (0)1223 49 44 44</p>

            <p class="legal">Copyright &copy; EMBL-EBI 2013 | EBI is an outstation of the <a href="http://www.embl.org">European
                Molecular Biology Laboratory</a> | <a href="/about/privacy">Privacy</a> | <a href="/about/cookies">Cookies</a>
                | <a href="/about/terms-of-use">Terms of use</a></p>
        </section>

    </div>

</footer>

<script defer="defer" src="//www.ebi.ac.uk/web_guidelines/js/cookiebanner.js"></script>
<script defer="defer" src="//www.ebi.ac.uk/web_guidelines/js/foot.js"></script>

<script type="text/javascript">

    var _gaq = _gaq || [];
    _gaq.push(['_setAccount', 'UA-37676851-1']);
    _gaq.push(['_trackPageview']);
    (function () {
        var ga = document.createElement('script');
        ga.type = 'text/javascript';
        ga.async = true;
        ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
        var s = document.getElementsByTagName('script')[0];
        s.parentNode.insertBefore(ga, s);
    })();

</script>

<%--notify users of old browsers to upgrade--%>
<script>
    var $buoop = {c:2};
    function $buo_f(){
        var e = document.createElement("script");
        e.src = "//browser-update.org/update.js";
        document.body.appendChild(e);
    };
    try {document.addEventListener("DOMContentLoaded", $buo_f,false)}
    catch(e){window.attachEvent("onload", $buo_f)}
</script>