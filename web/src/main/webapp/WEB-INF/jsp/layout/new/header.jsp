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

<style>
    fieldset {
        padding: 0;
        border: 0;
        margin-top: 0px;
    }

    .ui-dialog .ui-state-error {
        padding: .3em;
    }

    li {
        padding-bottom: 4px;
    }

    .validateTips {
        border: 1px solid transparent;
        padding: 0em;
    }
</style>

<div id="dialog-form" title="Send us feedback - we really appreciate it !">

    <div class="validateTips">Please fill this form and click the Send button.</div>

    <form>
        <fieldset id="form-fields">
            <ul>
                <li>
                    <div>
                        <label for="feedback">Your feedback:</label>
                    </div>
                    <div>
                        <textarea rows="4" cols="50" name="feedback" id="feedback"
                                  class="text ui-widget-content ui-corner-all"></textarea>
                    </div>
                </li>
                <li>
                    <div>
                        <label for="email">Your email address (optional)</label>
                    </div>
                    <div>
                        <input type="input" size="50" maxlength="80" name="email" id="email" value=""
                               class="text ui-widget-content ui-corner-all"/>
                    </div>
                </li>
            </ul>
        </fieldset>
    </form>
</div>

<script>
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
            if (field.val().trim().length > max || field.val().length < min) {
                field.addClass("ui-state-error");
                updateTips("Length of the " + fieldName + " field must be between " +
                        min + " and " + max + " characters.");
                return false;
            } else {
                return true;
            }
        }

        function checkRegexp(field, regexp, example) {
            if (!( regexp.test(field.val()) )) {
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
            height:265,
            width:395,
            modal:true,
            buttons:[
                {width:"60px", text:"Send", id:"send-button", click:function () {
                    var bValid = true;
                    allFields.removeClass("ui-state-error");

                    bValid = bValid && (email.val().trim().length == 0
                            || (checkRegexp(email, /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i, "Email format non valid")
                            && checkLength(email, "email", 6, 80)));
                    bValid = bValid && checkLength(feedback, "feedback", 3, 10000);

                    if (bValid) {
                        /*
                         do something here....
                         */
                        updateTips("Thank you for your feedback.");
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

