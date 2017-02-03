// (C) Wolfgang Huber 2010-2011

// Script parameters - these are set up by R in the function 'writeReport' when copying the 
//   template for this script from arrayQualityMetrics/inst/scripts into the report.

var highlightInitial = [ false, true, false, true, true, true, true, true, false, true, true, true, true, true, true, false, true, false, true, true, false, true, true, false, false, true, true ];
var arrayMetadata    = [ [ "1", "34EL05_15", "34EL05_15", "berry ripening stage 34 E-L, 2005", "6_R300_sc2.CEL" ], [ "2", "34EL05_13", "34EL05_13", "berry ripening stage 34 E-L, 2005", "4_R300_sc2.CEL" ], [ "3", "36EL06_26", "36EL06_26", "berry ripening stage 36 E-L, 2006", "8_R431.CEL" ], [ "4", "33EL05_12", "33EL05_12", "2005, berry formation stage 33 E-L", "3_R300_sc2.CEL" ], [ "5", "34EL03_5", "34EL03_5", "berry ripening stage 34 E-L, 2003", "5_sc2.CEL" ], [ "6", "34EL03_4", "34EL03_4", "berry ripening stage 34 E-L, 2003", "4_sc2.CEL" ], [ "7", "33EL03_1", "33EL03_1", "berry formation stage 33 E-L, 2003", "1_sc2.CEL" ], [ "8", "34EL06_22", "34EL06_22", "2006, berry ripening stage 34 E-L", "4_R431.CEL" ], [ "9", "33EL05_10", "33EL05_10", "berry formation stage 33 E-L, 2005", "1_R300_sc2.CEL" ], [ "10", "33EL05_11", "33EL05_11", "berry formation stage 33 E-L, 2005", "2_R300_sc2.CEL" ], [ "11", "34EL03_6", "34EL03_6", "2003, berry ripening stage 34 E-L", "6_sc2.CEL" ], [ "12", "33EL06_20", "33EL06_20", "berry formation stage 33 E-L, 2006", "2_R431.CEL" ], [ "13", "33EL06_19", "33EL06_19", "berry formation stage 33 E-L, 2006", "1_R431.CEL" ], [ "14", "33EL03_2", "33EL03_2", "2003, berry formation stage 33 E-L", "2_sc2.CEL" ], [ "15", "33EL03_3", "33EL03_3", "2003, berry formation stage 33 E-L", "3_sc2.CEL" ], [ "16", "36EL06_27", "36EL06_27", "2006, berry ripening stage 36 E-L", "9_R431.CEL" ], [ "17", "36EL06_25", "36EL06_25", "2006, berry ripening stage 36 E-L", "7_R431.CEL" ], [ "18", "33EL06_21", "33EL06_21", "2006, berry formation stage 33 E-L", "3_R431.CEL" ], [ "19", "34EL05_14", "34EL05_14", "2005, berry ripening stage 34 E-L", "5_R300_sc2.CEL" ], [ "20", "36EL03_8", "36EL03_8", "berry ripening stage 36 E-L, 2003", "8_sc2.CEL" ], [ "21", "36EL05_17", "36EL05_17", "2005, berry ripening stage 36 E-L", "8_R300_sc2.CEL" ], [ "22", "34EL06_24", "34EL06_24", "berry ripening stage 34 E-L, 2006", "6_R431.CEL" ], [ "23", "34EL06_23", "34EL06_23", "berry ripening stage 34 E-L, 2006", "5_R431.CEL" ], [ "24", "36EL05_18", "36EL05_18", "berry ripening stage 36 E-L, 2005", "9_R300_sc2.CEL" ], [ "25", "36EL05_16", "36EL05_16", "berry ripening stage 36 E-L, 2005", "7_R300_sc2.CEL" ], [ "26", "36EL03_7", "36EL03_7", "2003, berry ripening stage 36 E-L", "7_sc2.CEL" ], [ "27", "36EL03_9", "36EL03_9", "2003, berry ripening stage 36 E-L", "9_sc2.CEL" ] ];
var svgObjectNames   = [ "pca", "dens" ];

var cssText = ["stroke-width:1; stroke-opacity:0.4",
               "stroke-width:3; stroke-opacity:1" ];

// Global variables - these are set up below by 'reportinit'
var tables;             // array of all the associated ('tooltips') tables on the page
var checkboxes;         // the checkboxes
var ssrules;


function reportinit() 
{
 
    var a, i, status;

    /*--------find checkboxes and set them to start values------*/
    checkboxes = document.getElementsByName("ReportObjectCheckBoxes");
    if(checkboxes.length != highlightInitial.length)
	throw new Error("checkboxes.length=" + checkboxes.length + "  !=  "
                        + " highlightInitial.length="+ highlightInitial.length);
    
    /*--------find associated tables and cache their locations------*/
    tables = new Array(svgObjectNames.length);
    for(i=0; i<tables.length; i++) 
    {
        tables[i] = safeGetElementById("Tab:"+svgObjectNames[i]);
    }

    /*------- style sheet rules ---------*/
    var ss = document.styleSheets[0];
    ssrules = ss.cssRules ? ss.cssRules : ss.rules; 

    /*------- checkboxes[a] is (expected to be) of class HTMLInputElement ---*/
    for(a=0; a<checkboxes.length; a++)
    {
	checkboxes[a].checked = highlightInitial[a];
        status = checkboxes[a].checked; 
        setReportObj(a+1, status, false);
    }

}


function safeGetElementById(id)
{
    res = document.getElementById(id);
    if(res == null)
        throw new Error("Id '"+ id + "' not found.");
    return(res)
}

/*------------------------------------------------------------
   Highlighting of Report Objects 
 ---------------------------------------------------------------*/
function setReportObj(reportObjId, status, doTable)
{
    var i, j, plotObjIds, selector;

    if(doTable) {
	for(i=0; i<svgObjectNames.length; i++) {
	    showTipTable(i, reportObjId);
	} 
    }

    /* This works in Chrome 10, ssrules will be null; we use getElementsByClassName and loop over them */
    if(ssrules == null) {
	elements = document.getElementsByClassName("aqm" + reportObjId); 
	for(i=0; i<elements.length; i++) {
	    elements[i].style.cssText = cssText[0+status];
	}
    } else {
    /* This works in Firefox 4 */
	var success = false;
	i = 0; 
	/* Some of this looping could already be cached in reportInit() */
	while( (!success) & (i < ssrules.length) ) {
	    selector = ssrules[i].selectorText;  // The selector 
            if (!selector) {
                i++;
                continue;
            }
            if (selector == (".aqm" + reportObjId)) {
		success = true; 
		ssrules[i].style.cssText = cssText[0+status];
	    } else {
		i++;
	    }
	}
    }

}

/*------------------------------------------------------------
   Display of the Metadata Table
  ------------------------------------------------------------*/
function showTipTable(tableIndex, reportObjId)
{
    var rows = tables[tableIndex].rows;
    var a = reportObjId - 1;

    if(rows.length != arrayMetadata[a].length)
	throw new Error("rows.length=" + rows.length+"  !=  arrayMetadata[array].length=" + arrayMetadata[a].length);

    for(i=0; i<rows.length; i++) 
 	rows[i].cells[1].innerHTML = arrayMetadata[a][i];
}

function hideTipTable(tableIndex)
{
    var rows = tables[tableIndex].rows;

    for(i=0; i<rows.length; i++) 
 	rows[i].cells[1].innerHTML = "";
}


/*------------------------------------------------------------
  From module 'name' (e.g. 'density'), find numeric index in the 
  'svgObjectNames' array.
  ------------------------------------------------------------*/
function getIndexFromName(name) 
{
    var i;
    for(i=0; i<svgObjectNames.length; i++)
        if(svgObjectNames[i] == name)
	    return i;

    throw new Error("Did not find '" + name + "'.");
}


/*------------------------------------------------------------
  SVG plot object callbacks
  ------------------------------------------------------------*/
function plotObjRespond(what, reportObjId, name)
{

    var a, i, status;

    switch(what) {
    case "show":
	i = getIndexFromName(name);
	showTipTable(i, reportObjId);
	break;
    case "hide":
	i = getIndexFromName(name);
	hideTipTable(i);
	break;
    case "click":
        a = reportObjId - 1;
	status = !checkboxes[a].checked;
	checkboxes[a].checked = status;
	setReportObj(reportObjId, status, true);
	break;
    default:
	throw new Error("Invalid 'what': "+what)
    }
}

/*------------------------------------------------------------
  checkboxes 'onchange' event
------------------------------------------------------------*/
function checkboxEvent(reportObjId)
{
    var a = reportObjId - 1;
    var status = checkboxes[a].checked;
    setReportObj(reportObjId, status, true);
}


/*------------------------------------------------------------
  toggle visibility
------------------------------------------------------------*/
function toggle(id){
  var head = safeGetElementById(id + "-h");
  var body = safeGetElementById(id + "-b");
  var hdtxt = head.innerHTML;
  var dsp;
  switch(body.style.display){
    case 'none':
      dsp = 'block';
      hdtxt = '-' + hdtxt.substr(1);
      break;
    case 'block':
      dsp = 'none';
      hdtxt = '+' + hdtxt.substr(1);
      break;
  }  
  body.style.display = dsp;
  head.innerHTML = hdtxt;
}