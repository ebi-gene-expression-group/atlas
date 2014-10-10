/*
 * Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * For further details of the Gene Expression Atlas project, including source code,
 * downloads and documentation, please see:
 *
 * http://gxa.github.com/gxa
 */

function initExperimentPageButtonsAndTooltips() {

    $('#display-ae').button().tooltip();
    $('#display-analysis-methods').button().tooltip();
    $('#display-experiment-design').button().tooltip();
    $('#display-experiment').button().tooltip();
    $('#display-qc-report').button().tooltip();
    $('#display-fastqc-report').button().tooltip();
    $('#clustering-pdf').button().tooltip();
    if (undefined !== $('#download-raw')) $('#download-raw').button().tooltip();
    if (undefined !== $('#download-normalized')) $('#download-normalized').button().tooltip();
    if (undefined !== $('#download-logFold')) $('#download-logFold').button().tooltip();
    if (undefined !== $('#download-analytics')) $('#download-analytics').button().tooltip();
    if (undefined !== $('#download-expressions')) $('#download-expressions').button().tooltip();
    $('#download-r').button().tooltip();
    $('#goto-ae').tooltip();
    $('#goto-experiment').tooltip();
    $("#goto-experiment-name").tooltip();
    $('.array-design').tooltip();
    $('.pubmed-id').tooltip();
}