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
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="headerdiv" id="headerdiv">
    <header>
        <div id="global-masthead" class="masthead grid_24">
            <!-- <p>EMBL-EBI &diams;</p> -->
            <p id="logo">
                <!--This has to be one line and no newline characters-->
                <a href="" title="Go to the EMBL-EBI homepage">
                    <img src="${pageContext.request.contextPath}/resources/images/logo.png"
                         alt="European Bioinformatics Institute">
                </a>
            </p>

            <nav>
                <ul id="global-nav">
                    <li class="first" id="services">
                        <a href="/services" title="">Services</a>
                    </li>
                    <li id="research">
                        <a href="/research" title="">Research</a>
                    </li>
                    <li id="training">
                        <a href="/training" title="">Training</a>
                    </li>
                    <li id="funding">
                        <a href="/funding" title="">Funding</a>
                    </li>
                    <li id="about" class="last">
                        <a href="/about-us" title="">About us</a>
                    </li>
                </ul>
            </nav>
        </div>

        <div id="local-masthead" class="masthead grid_24 _nomenu">

            <!-- CHOOSE -->

            <div class="grid_12 alpha" id="local-title-logo">

                <h1>[Title]</h1>

                <p class="grid_">
                    <a href="${pageContext.request.contextPath}" title="Home" style="border:none">
                        <img src="${pageContext.request.contextPath}/resources/images/ATLAS_web.png"
                             alt="Gene Expression Atlas" class="logo">
                    </a>
                    <!--
                    <span class="strapline">[Strapline]</span>
                    -->
                </p>

            </div>


            <!-- OR... -->

            <!--
                <div class="grid_12 alpha" id="local-title">
                    <h1>[Title]</h1>
                </div>
            -->
            <!-- -->
            <!--
            <div class="grid_12 omega">
                <form id="local-search" name="local-search" action="#" method="post">

                    <fieldset>

                        <label>
                            <input type="text" name="first" id="local-searchbox">
                        </label>

                        <input type="submit" name="submit" value="Search" class="submit">
                    </fieldset>

                </form>
            </div>
            -->
            <nav>
                <ul class="grid_24" id="local-nav">
                    <li class="first"><a href="${pageContext.request.contextPath}" title="">Home</a></li>
                    <li class="last"><a id="feedback-link" href="#">Feedback</a></li>
                </ul>

            </nav>
        </div>
    </header>
</div>