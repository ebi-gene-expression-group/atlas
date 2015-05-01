<%--@elvariable id="geneQuery" type="uk.ac.ebi.atlas.web.GeneQuery"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:set var="thisPage" value="${requestScope['javax.servlet.forward.request_uri']}"/>

<form method="get" action="${thisPage}" id="searchForm">
    <table class="gxaFormGrid">
        <tr>
            <td class="gxaSearchFormFullWidthColumn">
                <div>
                    <label>Gene query</label>
                    <span data-help-loc="#geneSearch"></span>
                </div>
                <div>
                    <textarea id="geneQuery" name="geneQuery"
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
                <div class="gxaFacetedSearchActionButtons">
                    <div>
                        <input id="submit-button" type="submit" value="Search" tabindex="4">
                    </div>
                    <div>
                        <input id="reset-button" type="reset" value="Reset" tabindex="5">
                    </div>
                </div>
            </td>
        </tr>
    </table>
</form>

<%@ include file="../../includes/condition-autocomplete-js.jsp" %>
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

