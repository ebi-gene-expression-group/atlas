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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="experimentsPageHeading">Experiments Expression Atlas</div>

<table cellspacing="0" cellpadding="0" border="0" id="experiments-table" class="display">

</table>

<script type="text/javascript" language="javascript"
        src="${pageContext.request.contextPath}/resources/js/datatables-1.9.4/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" language="javascript"
        src="${pageContext.request.contextPath}/resources/js/experimentsPageModule.js"></script>

<script>

    $(function () {
        clearLocalNav();
        $('#local-nav-home').addClass("active");
    });

    (function ($) {
        $(document).ready(function () {

            experimentsPageModule.init();

        });
    })(jQuery);

</script>
