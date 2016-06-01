<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script src="https://cdnjs.cloudflare.com/ajax/libs/URI.js/1.17.0/URI.min.js"></script>
<script language="JavaScript" type="text/javascript" src="${pageContext.request.contextPath}/resources/js/searchFormModule.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/home_request-preferences.css" media="screen">

<section class="gxaSection">
    <h2>Expression Atlas: Differential and Baseline Expression</h2>

    <p>
        The Expression Atlas provides information on gene expression patterns under
        different biological conditions. Gene expression data is re-analysed in-house
        to detect genes showing interesting baseline and differential expression
        patterns. <a href="about.html">Read more about Expression Atlas.</a>
    </p>
</section>

<%-- section for search boxes, Browse... menu, and grid sections --%>
<section>
    <div class="grid_18">

        <h3>Search...</h3>

        <form method="get" action="query" id="searchForm">
            <table class="gxaFormGrid">
                <tr>
                    <td class="gxaTableColumn35">
                        <label>Gene query</label>
                        <span data-help-loc="#geneSearch"></span>
                    </td>
                    <td class="gxaTableColumn20">
                        <label>Organism</label>
                    </td>
                    <td class="gxaTableColumn35">
                        <label>Sample properties</label>
                        <span data-help-loc="#experimentalConditions"></span>
                    </td>
                    <td class="gxaTableColumn10" rowspan="2" style="vertical-align: middle;">
                        <div class="gxaHomeSearchActionButtons">
                            <div style="text-align: right;">
                                <input id="submit-button" type="submit" value="Search" tabindex="4">
                            </div>
                            <div style="text-align: right;">
                                <input id="reset-button" type="reset" value="Reset" tabindex="5">
                            </div>
                        </div>
                    </td>
                </tr>

                <tr>
                    <td>
                        <div id="geneQuerySection">
                            <textarea id="geneQuery" name="geneQuery" rows="2" cols="36" placeholder="(all genes)" tabindex="1"></textarea>

                            <div  class="gxaSearchExamples">
                                <span style="float:left">E.g.
                                    <a href="query?geneQuery=REG1B&organism=Homo+sapiens">REG1B</a>,
                                    <a href="query?geneQuery=%22zinc+finger%22">zinc finger</a>
                                </span>

                                <span style="float:right">
                                    <input style="vertical-align: middle" id="exactMatch" name="exactMatch" type="checkbox" value="true"
                                           checked="checked" tabindex="2">
                                    <label for="exactMatch">Exact match</label>
                                    <input type="hidden" name="_exactMatch" value="on">
                                </span>
                            </div>
                        </div>
                    </td>

                    <td>
                        <form:select id="organism" name="organism" path="dummyPath">
                            <form:options items="${organisms}" />
                        </form:select>
                    </td>
                    <td>
                        <div id="conditionSection">
                            <textarea id="condition" name="condition" maxlength="900" rows="2" cols="36" placeholder="(all conditions)" tabindex="3"></textarea>

                            <div class="gxaSearchExamples">
                                <span>E.g.
                                    <a href="query?condition=lung">lung</a>,
                                    <a href="query?condition=leaf">leaf</a>,
                                    <a href="query?condition=&quot;valproic+acid&quot;">"valproic acid"</a>,
                                    <a href="query?condition=cancer">cancer</a>
                                </span>
                            </div>
                        </div>
                    </td>
                </tr>
            </table>
        </form>


        <div class="grid_12 alpha">
            <h3>iRAP: RNA-seq analysis tool</h3>
            <p><a href="http://nunofonseca.github.io/irap/">iRAP</a> is a flexible
            pipeline for RNA-seq analysis that integrates many existing tools for
            filtering and mapping reads, quantifying expression and testing for
            differential expression. iRAP is used to process all RNA-seq data in
            Expression Atlas.</p>
        </div>

        <div class="grid_12 omega">
            <h3>Publications</h3>

            <p class="icon icon-conceptual" data-icon="l"><a
            href="http://www.plosone.org/article/info%3Adoi%2F10.1371%2Fjournal.pone.0107026">RNA-Seq Gene Profiling - A
            Systematic Empirical Comparison</a> (<i>PLoS One</i>, 2014).</p>

            <p class="icon icon-conceptual" data-icon="l"><a
            href="http://nar.oxfordjournals.org/content/44/D1/D746.full">Expression Atlas update — an integrated database of gene and protein expression in humans, animals and plants</a> (<i>Nucleic Acids Research</i>, 2016).</p>

        </div>

	</div>
</section>

	<%-- Browse... menu --%>
	<aside class="grid_6">

		<h3>Browse...</h3>

        <h4><img src="resources/images/allup2_transparent_bkg.png" style="padding-right: 15px"><a href="baseline/experiments">Baseline Experiments</a></h4>
        <p>See all baseline expression data sets in Expression Atlas.</p>

        <h4><span class="icon icon-species" data-icon="P"></span><a href="plant/experiments">Plant Experiments</a></h4>
        <p>See all expression data sets in plants in Expression Atlas.</p>

		<h4><img src="resources/images/experiment_page_small.png" style="padding-right: 15px"><a href="experiments">All Experiments</a></h4>
		<p>Scroll through the complete list of all data sets in Expression Atlas.</p>
	</aside>


<%-- placeholder which is loaded with tooltip text --%>
<div id="help-placeholder" style="display: none"></div>

<spring:eval var="arrayexpressUrl" expression="@configuration['arrayexpress.autocomplete.url']" />
<%@ include file="includes/condition-autocomplete-js.jsp" %>

<script type="text/javascript">
    $(document).ready(function () {
        function disableButtonsOnChange (field, editor, tags) {
            $buttons.button('option', 'disabled', tags.length == 0);
        }

        var $buttons = $('#submit-button, #reset-button'), $searchFields = $('#geneQuery, #condition');

        geneQueryTagEditorModule.init('#geneQuery', undefined, disableButtonsOnChange);
        conditionAutocompleteModule.init('${arrayexpressUrl}', disableButtonsOnChange);

        searchFormModule.searchBoxEnterEventHandler('#submit-button');
        searchFormModule.disableCarriageReturn('#condition');

        helpTooltipsModule.init('experiment', '${pageContext.request.contextPath}', '');

        initButtons();
        selectHomoSapiens();
        disableButtonsWhenAllSearchFieldsAreEmpty();
        onResetButtonRemoveAllTagsAndSelectHomoSapiens();

        function initButtons() {
            $buttons.each(function () {
                $(this).button({ disabled: true });
            });
        }

        function onResetButtonRemoveAllTagsAndSelectHomoSapiens() {
            $('#reset-button').on('click' , function () {
                // Remove all tags
                var $geneQuery = $('#geneQuery'),
                    geneQueryTags = $geneQuery.jsonTagEditor('getTags')[0].tags;
                for (var i = 0; i < geneQueryTags.length; i++) {
                    $geneQuery.jsonTagEditor('removeTag', geneQueryTags[i].tagValue);
                }

                var $sampleProperties =  $('#condition'),
                    samplePropertiesTags = $sampleProperties.tagEditor('getTags')[0].tags;
                for (var i = 0; i < samplePropertiesTags.length; i++) {
                    $sampleProperties.tagEditor('removeTag', samplePropertiesTags[i]);
                }

                selectHomoSapiens();
            });
        }

        function disableButtonsWhenAllSearchFieldsAreEmpty() {
            $searchFields.on('keyup',function () {
                $buttons.button('option', 'disabled', allFieldsEmpty());
            }).keyup();

            function allFieldsEmpty() {
                var atLeastOneValue = false;
                $searchFields.each(function () {
                    atLeastOneValue = atLeastOneValue || ($.trim(this.value).length > 0);
                });
                return !atLeastOneValue;
            }
        }

        function selectHomoSapiens(){
            $('select[id="organism"] option[value="Homo sapiens"]').attr('selected', 'selected');
        }
    });
</script>
