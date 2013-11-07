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


<h2>Expression Atlas, Differential and Baseline Expression</h2>

<p>Expression Atlas is a semantically enriched database of publicly available gene and transcript expression data.
    The data is re-analysed in-house to detect genes showing interesting baseline and differential expression patterns
    under the conditions of the original experiment. <a href="about.html">Read more about Expression Atlas.</a></p>

<section class="grid_6 alpha">
    <table class="form-grid" style="margin:0px 9px;">
        <tr>
            <td><label>Browse</label></td>
        </tr>
        <tr>
            <td><a href="experiments"><img src="resources/images/experiment_page_small.png">
                Experiments</a></td>
        </tr>
    </table>
</section>

<section class="grid_18 omega">
    <form method="get" action="query" id="searchForm">
        <table class="form-grid">
            <tr>
                <td>
                    <label>Gene Query</label>
                    <span data-help-loc="#geneSearch"/>
                </td>
                <td>
                    <label>Experimental conditions</label>
                    <span data-help-loc="#experimentalConditions"/>
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
                    <div style="display:inline-block">
                        <textarea id="geneQuery" name="geneQuery" maxlength="900" rows="2" cols="36"
                                  placeholder="(all genes)" tabindex="1"></textarea>

                        <div>
                                <span style="float:left">
                                    <input id="exactMatch" name="exactMatch" type="checkbox" value="true"
                                           checked="checked" tabindex="2">
                                    <label for="exactMatch">Exact match</label>
                                    <input type="hidden" name="_exactMatch" value="on">
                                </span>
                        </div>
                    </div>
                </td>
                <td>
                    <div style="display:inline-block">
                        <textarea id="condition" name="condition" maxlength="900" rows="2" cols="36"
                                  placeholder="(all conditions)" tabindex="3"></textarea>
                    </div>
                </td>
            </tr>
        </table>
    </form>
</section>


<%-- placeholder which is loaded with tooltip text --%>
<div id="help-placeholder" style="display: none"></div>

<script type="text/javascript">

    (function ($) { //self invoking wrapper function that prevents $ namespace conflicts

        $(document).ready(function () {
            var $buttons = $('#submit-button, #reset-button'),
                    $searchFields = $('#geneQuery, #condition');

            searchFormModule.geneQuerySearchBoxInitAutocomplete();

            searchFormModule.disableCarriageReturn("#geneQuery");

            searchFormModule.disableCarriageReturn("#condition");

            helpTooltipsModule.init('experiment', '${pageContext.request.contextPath}');

            initButtons();

            disableButtonsWhenAllSearchFieldsAreEmpty();

            function initButtons() {
                $buttons.each(function () {
                    $(this).button({ disabled: true });
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

        });

    })(jQuery);

</script>