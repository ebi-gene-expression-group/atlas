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