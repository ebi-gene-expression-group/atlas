<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script language="JavaScript" type="text/javascript"
        src="${pageContext.request.contextPath}/resources/js/searchFormModule.js"></script>


<h2>Expression Atlas: Differential and Baseline Expression</h2>

<%-- section for the description --%>
<div class="grid_24">

<p>The Expression Atlas provides information on gene expression patterns under
    different biological conditions. Gene expression data is re-analysed in-house
    to detect genes showing interesting baseline and differential expression
    patterns. <a href="about.html">Read more about Expression Atlas.</a></p>
</div>

<%-- section for search boxes, Browse... menu, and grid sections --%>
<div class="container_24">

	<div class="grid_18 alpha">

		<h3>Search...</h3>

		<div class="grid_24 alpha">

			<form method="get" action="query" id="searchForm">
				<table class="form-grid">
					<tr>
						<td class="gxaHomeSearchFluidTableColumn">
							<label>Gene query</label>
							<span data-help-loc="#geneSearch"></span>
						</td>
						<td  class="gxaHomeSearchFluidTableColumn">
							<label>Organism</label>
						</td>
						<td  class="gxaHomeSearchFluidTableColumn">
							<label>Sample properties</label>
							<span data-help-loc="#experimentalConditions"></span>
						</td>
						<td rowspan="2" class="gxaHomeSearchFixedSizeTableColumn">
							<div class="gxaHomeSearchActionButtons">
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
						<td class="gxaHomeSearchFluidTableColumn">
							<div id="geneQuerySection">
								<textarea id="geneQuery" name="geneQuery" rows="2" cols="36"
										  placeholder="(all genes)" tabindex="1"></textarea>
								<div>
									<span style="float:left">E.g.
											<a href="query?geneQuery=SFTPA2&organism=Homo+sapiens">SFTPA2</a>,
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

						<td class="gxaHomeSearchFluidTableColumn">
							<form:select id="organism" name="organism" path="dummyPath">
								<form:options items="${organisms}" />
							</form:select>
						</td>
						<td class="gxaHomeSearchFluidTableColumn">
                    		<div id="conditionSection">
								<textarea id="condition" name="condition" maxlength="900" rows="2" cols="36"
										  placeholder="(all conditions)" tabindex="3"></textarea>

								<div>
									<span class="examples">E.g.
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

		</div>


		<%-- first row of the grid --%>
		<div class="grid_24 alpha" style="padding-top: 20px">
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
				href="http://nar.oxfordjournals.org/content/42/D1/D926.full">Expression
				Atlas update - a database of gene and transcript expression from microarray
				and sequencing-based functional genomics experiments</a> (<i>Nucleic Acids Research</i>, 2014).</p>

			</div>
		</div>

	</div>

	<%-- Browse... menu --%>
	<aside class="grid_6 omega">

		<h3>Browse...</h3>

        <h4><img src="resources/images/allup2_transparent_bkg.png" style="padding-right: 15px"><a href="baseline/experiments">Baseline Experiments</a></h4>
        <p>See all baseline expression data sets in Expression Atlas.</p>

        <h4><span class="icon icon-species" data-icon="P"></span><a href="plant/experiments">Plant Experiments</a></h4>
        <p>See all expression data sets in plants in Expression Atlas.</p>

		<h4><img src="resources/images/experiment_page_small.png" style="padding-right: 15px"><a href="experiments">All Experiments</a></h4>
		<p>Scroll through the complete list of all data sets in Expression Atlas.</p>
	</aside>

</div>

<%-- Link to the old Atlas in its own section at the bottom --%>
<div class="grid_24 alpha">
	<div>
<p>Still need the old Expression Atlas? <a href="http://www-test.ebi.ac.uk/gxa">Click here</a>.</p>
</div>
</div>



<%-- placeholder which is loaded with tooltip text --%>
<div id="help-placeholder" style="display: none"></div>

<%@ include file="includes/condition-autocomplete-js.jsp" %>

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

            selectDefaultOrganism();

            onResetButtonEventHandler();

            function initButtons() {
                $buttons.each(function () {
                    $(this).button({ disabled: true });
                });
            }

            function onResetButtonEventHandler() {
                $('#reset-button').on('click' , function () {
                    // Remove all tags
                    var tags = $('#geneQuery').tagEditor('getTags')[0].tags;
                    for (i = 0; i < tags.length; i++) {
                        $('#geneQuery').tagEditor('removeTag', tags[i]);
                    }

                    var tags = $('#condition').tagEditor('getTags')[0].tags;
                    for (i = 0; i < tags.length; i++) {
                        $('#condition').tagEditor('removeTag', tags[i]);
                    }

                    selectDefaultOrganism();
                });
            }

            function disableButtonsWhenAllSearchFieldsAreEmpty() {
                $searchFields.on('keyup',function () {
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

            function disableButtonsOnChange (field, editor, tags) {
                $buttons.button("option", "disabled", tags.length == 0);
            }

            // TODO: This change has been requested by Alvis for the services review. We should review it
            // with him once the faceting is in place.
            function selectDefaultOrganism(){
                $('select[id="organism"] option[value="Homo sapiens"]').attr("selected","selected");

            }

        });

    })(jQuery);

</script>
