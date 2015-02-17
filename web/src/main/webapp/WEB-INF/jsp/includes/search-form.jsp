<%--@elvariable id="geneQuery" type="uk.ac.ebi.atlas.web.GeneQuery"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:set var="thisPage" value="${requestScope['javax.servlet.forward.request_uri']}"/>

<form method="get" action="${thisPage}" id="searchForm">
    <table class="form-grid">
        <tr>
            <td>
                <label>Gene query</label>
                <span data-help-loc="#geneSearch"></span>
            </td>
            <td>
                <label>Sample properties</label>
                <span data-help-loc="#experimentalConditions"></span>
            </td>
            <td rowspan="2" style="display:table-cell;text-align:center;vertical-align: middle;">
                <div class="actions">
                    <div>
                        <input id="submit-button" type="submit" value="Search" tabindex="4">
                    </div>
                    <div>
                        <input id="reset-button" type="reset" value="Reset" tabindex="5">
                    </div>
                </div>
            </td>
        </tr>

        <tr>
            <td>
                <div id="geneQuerySection" style="display:inline-block">
                    <textarea id="geneQuery" name="geneQuery" rows="2" cols="36"
                              placeholder="(all genes)" tabindex="1">${geneQuery.asTags()}</textarea>

                    <div>
                        <span style="float:left">E.g.
                                <a href="${thisPage}?geneQuery=ASPM">ASPM</a>,
                                <a href="${thisPage}?geneQuery=zinc+finger">zinc finger</a>
                            </span>
                    </div>
                </div>
            </td>

            <td>
                <div id="conditionSection" style="display:inline-block">
                    <textarea id="condition" name="condition" maxlength="900" rows="2" cols="36"
                              placeholder="(all conditions)" tabindex="3"></textarea>

                    <div>
                        <span class="examples">E.g.
                            <a href="${thisPage}?condition=leaf">leaf</a>,
                            <a href="${thisPage}?condition=valproic+acid">valproic acid</a>,
                            <a href="${thisPage}?condition=cancer">cancer</a>
                        </span>
                    </div>
                </div>
            </td>
        </tr>
    </table>
</form>

<%@ include file="condition-autocomplete-js.jsp" %>
<script language="JavaScript" type="text/javascript"
        src="${pageContext.request.contextPath}/resources/js/searchFormModule.js"></script>
<script type="text/javascript">

    (function ($) { //self invoking wrapper function that prevents $ namespace conflicts

        $(document).ready(function () {
            var $buttons = $('#submit-button, #reset-button'),
                    $searchFields = $('#geneQuery, #condition');

            geneQueryTagEditorModule.init("#geneQuery", undefined, disableButtonsOnChange);

            conditonAutocompleteModule.init("${configuration['arrayexpress.autocomplete.url']}", disableButtonsOnChange);

            searchFormModule.searchBoxEnterEventHandler("#submit-button");

            searchFormModule.disableCarriageReturn("#condition");

            helpTooltipsModule.init('experiment', '${pageContext.request.contextPath}', '');

            initButtons();

            disableButtonsWhenAllSearchFieldsAreEmpty();

            function initButtons() {
                $buttons.each(function () {
                    $(this).button({disabled: true});
                });
            }

            function disableButtonsWhenAllSearchFieldsAreEmpty() {
                $searchFields.on('keyup', function () {
                    $buttons.button("option", "disabled", allFieldsEmpty());
                }).keyup();

                function allFieldsEmpty() {
                    var atLeastOneValue = false;
                    $searchFields.each(function () {
                        atLeastOneValue = atLeastOneValue || ($.trim(this.value).length > 0);
                    });
                    return !atLeastOneValue;
                }
            }

            function disableButtonsOnChange(field, editor, tags) {
                $buttons.button("option", "disabled", tags.length == 0);
            }

        });

    })(jQuery);

</script>

