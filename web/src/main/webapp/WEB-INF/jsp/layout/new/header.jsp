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

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:import url="feedback-form.jsp"/>

<div class="headerdiv" id="headerdiv">
    <div class="header">
        <div id="global-masthead" class="masthead grid_24">
            <!--This has to be one line and no newline characters-->
            <a href="//www.ebi.ac.uk/" title="Go to the EMBL-EBI homepage"><img
                    src="//www.ebi.ac.uk/web_guidelines/images/logos/EMBL-EBI/EMBL_EBI_Logo_white.png"
                    alt="EMBL European Bioinformatics Institute"/></a>

            <div class="nav">
                <ul id="global-nav">
                    <!-- set active class as appropriate -->
                    <li class="first active" id="services"><a href="//www.ebi.ac.uk/services">Services</a></li>
                    <li id="research"><a href="//www.ebi.ac.uk/research">Research</a></li>
                    <li id="training"><a href="//www.ebi.ac.uk/training">Training</a></li>
                    <li id="industry"><a href="//www.ebi.ac.uk/industry">Industry</a></li>
                    <li id="about" class="last"><a href="//www.ebi.ac.uk/about">About us</a></li>
                </ul>
            </div>
        </div>

        <div id="local-masthead" class="masthead grid_24">

            <!-- local-title -->
            <!-- NB: for additional title style patterns, see http://frontier.ebi.ac.uk/web/style/patterns -->

            <div class="logo-title" id="local-title">
                <a href="/gxa" title="Back to Expression Atlas homepage">
                    <img src="${pageContext.request.contextPath}/resources/images/ExpressionAtlas_logo_web.png"
                         alt="Expression Atlas logo" width="64" height="64">
                </a><span><h1>Expression Atlas</h1></span>
            </div>

            <!-- /local-title -->
            <nav>
                <ul class="grid_24" id="local-nav">
                    <li class="first last"><a href="/gxa" title="Go to the Expression Atlas homepage">Home</a></li>
                    <!-- If you need to include functional (as opposed to purely navigational) links in your local menu,
                    add them here, and give them a class of "functional". Remember: you'll need a class of "last" for
                    whichever one will show up last... For example: -->
                    <li class="functional last"><a id="feedback-link" href="feedback-form" class="icon icon-static"
                                                   data-icon="\">Feedback</a></li>
                </ul>
            </nav>
        </div>
    </div>
</div>