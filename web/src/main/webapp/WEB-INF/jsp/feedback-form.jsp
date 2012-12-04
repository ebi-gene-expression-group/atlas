
<style>
    fieldset { padding:0; border:0; margin-top:5px; }
    .ui-dialog .ui-state-error { padding: .3em; }
    .validateTips { border: 1px solid transparent; padding: 0.3em; }
</style>
<script>
    $(function() {
        var whatWereYouDoing = $( "#whatWereYouDoing" ),
            whatWentWrong = $( "#whatWentWrong" ),
            whatCouldBeBetter = $( "#whatCouldBeBetter" ),
            email = $( "#email" ),
            allFields = $( [] ).add( whatWereYouDoing ).add( whatWentWrong ).add( whatCouldBeBetter ).add( email ),
            tips = $( ".validateTips" );

        function updateTips( text ) {
            tips.text( text ).addClass( "ui-state-highlight" );
            setTimeout(function() {
                tips.removeClass( "ui-state-highlight", 1500 );
            }, 500 );
        }

        function checkRegexp( field, regexp, example ) {
            if ( !( regexp.test( field.val() ) ) ) {
                field.addClass( "ui-state-error" );
                updateTips( example );
                return false;
            } else {
                return true;
            }
        }

        $( "#dialog-form" ).dialog({
            autoOpen: false,
            show: "blind",
            hide: "explode",
            height: 370,
            width: 370,
            modal: true,
            buttons: [{text:"Send", id: "send-button", click: function() {
                    var bValid = true;
                    allFields.removeClass( "ui-state-error" );

                    bValid = bValid && (email.val().trim().length == 0 || checkRegexp( email, /^[a-zA-Z0-9]+[a-zA-Z0-9_.-]*[a-zA-Z0-9_-]+@[a-zA-Z0-9]+[a-zA-Z0-9.-]+[a-zA-Z0-9]+\.[a-z]{2,4}$/, "Email format non valid" ));

                    //bValid = bValid && checkLength( name, "username", 3, 16 );
                    //bValid = bValid && checkLength( email, "email", 6, 80 );
                    //bValid = bValid && checkLength( password, "password", 5, 16 );

                    if ( bValid ) {
                        /*
                        $( "#users tbody" ).append( "<tr>" +
                                "<td>" + name.val() + "</td>" +
                                "<td>" + email.val() + "</td>" +
                                "<td>" + password.val() + "</td>" +
                                "</tr>" );
                        */
                        updateTips( "Thank you for your feedback." );
                        $("#send-button").hide();
                        $("#form-fields").hide();
                        $("#cancel-button").button('option','label','Close');

                    }
                }
            },{text:"Cancel", id: "cancel-button", click:function() {
                    $( this ).dialog( "close" );
                }
            }],
            close: function() {
                allFields.val( "" ).removeClass( "ui-state-error" );
            }
        });

        $( "#feedback-link" ).click(function() {
            $( "#dialog-form" ).dialog( "open" );
            return false;
        });
    });
</script>

<div id="error-content" class="block">
    <div class="error">
        Hello...
    </div>
</div>

<div id="content" class="block">
    <a id="feedback-link" href="#">Send us some feedback</a>
    <br/>
    <a href="/gxa">Atlas baseline homepage</a>
</div>

<div id="dialog-form" title="Send Feedback">

    <div class="validateTips">All form fields are required.</div>

    <form>
        <fieldset id="form-fields">
            <ul>
                <li>
                    <label for="whatWereYouDoing">What were you trying to do</label>
                    <textarea rows="2" cols="50"  name="whatWereYouDoing" id="whatWereYouDoing" class="text ui-widget-content ui-corner-all">
                    </textarea>
                </li>
                <li>
                    <label for="whatWentWrong">What went wrong</label>
                    <textarea rows="2" cols="50"  name="whatWentWrong" id="whatWentWrong" value="" class="text ui-widget-content ui-corner-all" >
                    </textarea>
                </li>
                <li>
                    <label for="whatCouldBeBetter">What could be better</label>
                    <textarea rows="2" cols="50"  name="whatCouldBeBetter" id="whatCouldBeBetter" value="" class="text ui-widget-content ui-corner-all" >
                    </textarea>
                </li>
                <li>
                    <label for="email">Your email address</label>
                    <input type="input" size="50" name="email" id="email" value="" class="text ui-widget-content ui-corner-all" />
                </li>
            </ul>
        </fieldset>
    </form>
</div>