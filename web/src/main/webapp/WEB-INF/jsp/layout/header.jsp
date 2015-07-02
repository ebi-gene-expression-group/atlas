<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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

<header>

    <div id="global-masthead" class="masthead grid_24">
        <!--This has to be one line and no newline characters-->
        <a href="//www.ebi.ac.uk/" title="Go to the EMBL-EBI homepage"><img
                src="//www.ebi.ac.uk/web_guidelines/images/logos/EMBL-EBI/EMBL_EBI_Logo_white.png"
                alt="EMBL European Bioinformatics Institute"></a>

        <nav>
            <ul id="global-nav">
                <!-- set active class as appropriate -->
                <li class="first active" id="services"><a href="//www.ebi.ac.uk/services">Services</a></li>
                <li id="research"><a href="//www.ebi.ac.uk/research">Research</a></li>
                <li id="training"><a href="//www.ebi.ac.uk/training">Training</a></li>
                <li id="industry"><a href="//www.ebi.ac.uk/industry">Industry</a></li>
                <li id="about" class="last"><a href="//www.ebi.ac.uk/about">About us</a></li>
            </ul>
        </nav>

    </div>

    <div id="local-masthead" class="masthead grid_24 nomenu" style="background-color: #E1EEE9">

        <!-- local-title -->
        <!-- NB: for additional title style patterns, see http://frontier.ebi.ac.uk/web/style/patterns -->

        <!-- apply grid_12 alpha class if you have a local search box in the header...
        <div class="grid_12 alpha" id="local-title">
        -->
        <div class="grid_12 alpha" id="local-title">
            <h1 style="color: #000000; text-shadow: 0px 0px #E1EEE9">
                <a href="${pageContext.request.contextPath}" title="Back to Expression Atlas homepage">
                    <img src="${pageContext.request.contextPath}/resources/latest/images/ExpressionAtlas_logo_web.png"
                         alt="Expression Atlas logo" width="64" height="64" style="vertical-align: bottom">Expression
                    Atlas
                </a>
            </h1>

        </div>

        <!-- /local-title -->

        <!-- local-search -->
        <!-- NB: if you do not have a local-search, delete the following div, and drop the class="grid_12 alpha" class from local-title above -->

        <div class="grid_12 omega">
            <form method="get" action="query" name="local-search" id="local-search">

                <fieldset>

                    <div class="left">
                        <label>
                            <input id="local-searchbox" name="geneQuery" value="${param.queryString}" maxlength="900" placeholder="(enter gene query)"/>
                        </label>
                    </div>
                    <div class="left">
                        <!-- Include some example searchterms - keep them short and few! -->
                        <span class="examples">Examples:
                                        <a href="${pageContext.request.contextPath}/query?geneQuery=ASPM">ASPM</a>,
                                        <a href="${pageContext.request.contextPath}/query?geneQuery=REACT_284558">REACT_284558</a>,
                                        <a href="${pageContext.request.contextPath}/query?geneQuery=ENSMUSG00000021789">ENSMUSG00000021789</a>,
                                        <a href="${pageContext.request.contextPath}/query?geneQuery=%22zinc+finger%22">"zinc finger"</a></span>
                    </div>

                    <div class="right" style="position: relative; top: -30px;">
                        <input id="submit-searchbox" class="submit" type="submit" value="Search"/>
                        <!-- If your search is more complex than just a keyword search, you can link to an Advanced Search,
                             with whatever features you want available -->
                        <!--
                        <span class="adv"><a title="Advanced" id="adv-search" href="../search">Advanced</a></span>
                        -->
                    </div>

                </fieldset>

            </form>
        </div>

        <!-- /local-search -->

        <!-- gxaLocalNav -->

        <nav>
            <ul class="grid_24" id="gxaLocalNav">
                <li id="gxaLocalNavHome" class="first active">
                    <a href="${pageContext.request.contextPath}" title="Expression Atlas homepage">Home</a>
                </li>
                <li id="gxaLocalNavRelease-notes">
                    <a href="${pageContext.request.contextPath}/release-notes.html" title="Release notes">Release notes</a>
                </li>
                <li id="gxaLocalNavFAQ">
                    <a href="${pageContext.request.contextPath}/FAQ.html" title="FAQs">FAQs</a>
                </li>
                <li id="gxaLocalNavDownload">
                    <a href="${pageContext.request.contextPath}/download.html" title="Download">Download</a>
                </li>
                <li id="gxaLocalNavHelp" class="last">
                    <a href="${pageContext.request.contextPath}/help/index.html"
                       title="Help pages">Help</a>
                </li>
                <li id="gxaLocalNavAbout" class="last">
                    <a href="${pageContext.request.contextPath}/about.html"
                       title="About Expression Atlas">About</a>
                </li>
                <!--
                <li class="last"><a href="#">About [service-name]</a></li>
                -->
                <!-- If you need to include functional (as opposed to purely navigational) links in your local menu,
                     add them here, and give them a class of "functional". Remember: you'll need a class of "last" for
                     whichever one will show up last...
                     For example: -->
                <li class="functional last" style="float: right"><a id="feedback-link" href="feedback-form" class="icon icon-generic"
                                               data-icon="\">Feedback</a></li>
            </ul>
        </nav>

        <!-- /gxaLocalNav -->

    </div>
</header>

<style>
    fieldset {
        padding: 0;
        border: 0;
        margin-top: 0px;
    }

    .ui-dialog .ui-state-error {
        padding: 0px;
    }

    .validateTips {
        border: 1px solid transparent;
        padding: 0em;
    }
</style>

<div style="display: none;" id="dialog-form" title="Send us feedback - we really appreciate it !">

    <div id="feedback-tips" class="validateTips">Please fill this form and click the Send button.</div>

    <form id="form">
        <fieldset id="form-fields">
            <ul>
                <li>
                    <div>
                        <label for="feedback">Your feedback:</label>
                    </div>
                    <div>
                        <textarea rows="4" cols="45" name="feedback" id="feedback"
                                  class="text ui-widget-content ui-corner-all"></textarea>
                    </div>
                </li>
                <li>
                    <div>
                        <label for="email">Your email address (optional)</label>
                    </div>
                    <div>
                        <input type="text" size="50" maxlength="80" name="email" id="email" value=""
                               class="text ui-widget-content ui-corner-all"/>
                        <input type="hidden" name="sendemail" id="sendemail" value="true"/>
                    </div>
                </li>
            </ul>
        </fieldset>
    </form>
</div>

<script>
    function clearLocalNav() {
        var listItems = $("#gxaLocalNav li");
        listItems.each(function (idx, li) {
            var item = $(li);
            item.removeClass("active");
        });
    }

    $(function () {

        $('#submit-searchbox').click(function(e) {
            if (searchBoxIsEmpty()) {
                e.preventDefault();
            }
        });

        function searchBoxIsEmpty() {
            return !$('#local-searchbox').val().trim();
        }
    });

    $(function () {
        var feedback = $("#feedback"),
                email = $("#email"),
                allFields = $([]).add(feedback).add(email),
                tips = $(".validateTips");

        function updateTips(text) {
            tips.text(text).addClass("ui-state-highlight");
            setTimeout(function () {
                tips.removeClass("ui-state-highlight", 1500);
            }, 500);
        }

        function checkLength(field, fieldName, min, max) {
            var emailAddress = $.trim(field.val());
            if (emailAddress.length > max || emailAddress.length < min) {
                field.addClass("ui-state-error");
                updateTips("Length of the " + fieldName + " field must be between " +
                        min + " and " + max + " characters.");
                return false;
            } else {
                return true;
            }
        }

        function checkRegexp(field, regexp, example) {
            var emailAddress = $.trim(field.val());
            if (!( regexp.test(emailAddress) )) {
                field.addClass("ui-state-error");
                updateTips(example);
                return false;
            } else {
                return true;
            }
        }

        $("#dialog-form").dialog({
            autoOpen:false,
            show:"blind",
            hide:"explode",
            height:"320",
            width:"440px",
            modal:true,
            buttons:[
                {width:"60px", text:"Send", id:"send-button", click:function () {
                    var bValid = true;
                    allFields.removeClass("ui-state-error");
                    bValid = bValid && ($.trim(email.val()).length == 0
                            || (checkRegexp(email, /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i, "Email format non valid")
                            && checkLength(email, "email", 6, 80)));
                    bValid = bValid && checkLength(feedback, "feedback", 3, 10000);

                    if (bValid) {
                        $.ajax({
                            type:"POST",
                            url:"email",
                            dataType:"json",
                            data:{
                                feedback: feedback.val(),
                                email: email.val()
                            },
                            success:function (data) {
                                updateTips(data);
                            },
                            error:function (jqXHR, statusCode) {
                                document.location = "error-page";
                            }
                        });

                        $("#send-button").hide();
                        $("#form-fields").hide();
                        $("#cancel-button").button('option', 'label', 'Close');

                    }
                }
                },
                {text:"Cancel", id:"cancel-button", click:function () {
                    $(this).dialog("close");
                }
                }
            ],
            close:function () {
                allFields.val("").removeClass("ui-state-error");
            }
        });

        $("#feedback-link").click(function () {
            $("#dialog-form").dialog("open");
            return false;
        });
    });
</script>

