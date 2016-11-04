// (C) Wolfgang Huber 2010-2011

// Script parameters - these are set up by R in the function 'writeReport' when copying the 
//   template for this script from arrayQualityMetrics/inst/scripts into the report.

var highlightInitial = [ false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, false, false, false, false, false, false, false, false, true, false, true, false ];
var arrayMetadata    = [ [ "1", "GSM842211", "GSM842211", "KNO3 nitrogen for 2 hours, Kas2", "GSM842211.CEL" ], [ "2", "GSM842212", "GSM842212", "KNO3 nitrogen for 2 hours, Kas2", "GSM842212.CEL" ], [ "3", "GSM842213", "GSM842213", "KNO3 nitrogen for 2 hours, Kas2", "GSM842213.CEL" ], [ "4", "GSM842240", "GSM842240", "control, Var2-1", "GSM842240.CEL" ], [ "5", "GSM842239", "GSM842239", "control, Var2-1", "GSM842239.CEL" ], [ "6", "GSM842226", "GSM842226", "control, TAMM27", "GSM842226.CEL" ], [ "7", "GSM842227", "GSM842227", "control, TAMM27", "GSM842227.CEL" ], [ "8", "GSM842238", "GSM842238", "Var2-1, control", "GSM842238.CEL" ], [ "9", "GSM842231", "GSM842231", "KNO3 nitrogen for 2 hours, TAMM27", "GSM842231.CEL" ], [ "10", "GSM842230", "GSM842230", "KNO3 nitrogen for 2 hours, TAMM27", "GSM842230.CEL" ], [ "11", "GSM842232", "GSM842232", "control, Ts5", "GSM842232.CEL" ], [ "12", "GSM842234", "GSM842234", "control, Ts5", "GSM842234.CEL" ], [ "13", "GSM842233", "GSM842233", "Ts5, control", "GSM842233.CEL" ], [ "14", "GSM842242", "GSM842242", "Var2-1, KNO3 nitrogen for 2 hours", "GSM842242.CEL" ], [ "15", "GSM842241", "GSM842241", "Var2-1, KNO3 nitrogen for 2 hours", "GSM842241.CEL" ], [ "16", "GSM842243", "GSM842243", "Var2-1, KNO3 nitrogen for 2 hours", "GSM842243.CEL" ], [ "17", "GSM842209", "GSM842209", "control, Kas2", "GSM842209.CEL" ], [ "18", "GSM842208", "GSM842208", "control, Kas2", "GSM842208.CEL" ], [ "19", "GSM842219", "GSM842219", "NFA8, KNO3 nitrogen for 2 hours", "GSM842219.CEL" ], [ "20", "GSM842217", "GSM842217", "NFA8, KNO3 nitrogen for 2 hours", "GSM842217.CEL" ], [ "21", "GSM842216", "GSM842216", "NFA8, control", "GSM842216.CEL" ], [ "22", "GSM842220", "GSM842220", "control, SQ8", "GSM842220.CEL" ], [ "23", "GSM842221", "GSM842221", "control, SQ8", "GSM842221.CEL" ], [ "24", "GSM842222", "GSM842222", "control, SQ8", "GSM842222.CEL" ], [ "25", "GSM842206", "GSM842206", "KNO3 nitrogen for 2 hours, Col0", "GSM842206.CEL" ], [ "26", "GSM842207", "GSM842207", "KNO3 nitrogen for 2 hours, Col0", "GSM842207.CEL" ], [ "27", "GSM842205", "GSM842205", "KNO3 nitrogen for 2 hours, Col0", "GSM842205.CEL" ], [ "28", "GSM842204", "GSM842204", "control, Col0", "GSM842204.CEL" ], [ "29", "GSM842203", "GSM842203", "control, Col0", "GSM842203.CEL" ], [ "30", "GSM842202", "GSM842202", "control, Col0", "GSM842202.CEL" ], [ "31", "GSM842210", "GSM842210", "Kas2, control", "GSM842210.CEL" ], [ "32", "GSM842228", "GSM842228", "TAMM27, control", "GSM842228.CEL" ], [ "33", "GSM842235", "GSM842235", "KNO3 nitrogen for 2 hours, Ts5", "GSM842235.CEL" ], [ "34", "GSM842218", "GSM842218", "KNO3 nitrogen for 2 hours, NFA8", "GSM842218.CEL" ], [ "35", "GSM842224", "GSM842224", "KNO3 nitrogen for 2 hours, SQ8", "GSM842224.CEL" ], [ "36", "GSM842237", "GSM842237", "Ts5, KNO3 nitrogen for 2 hours", "GSM842237.CEL" ], [ "37", "GSM842236", "GSM842236", "Ts5, KNO3 nitrogen for 2 hours", "GSM842236.CEL" ], [ "38", "GSM842215", "GSM842215", "control, NFA8", "GSM842215.CEL" ], [ "39", "GSM842214", "GSM842214", "control, NFA8", "GSM842214.CEL" ], [ "40", "GSM842223", "GSM842223", "SQ8, KNO3 nitrogen for 2 hours", "GSM842223.CEL" ], [ "41", "GSM842225", "GSM842225", "SQ8, KNO3 nitrogen for 2 hours", "GSM842225.CEL" ], [ "42", "GSM842229", "GSM842229", "TAMM27, KNO3 nitrogen for 2 hours", "GSM842229.CEL" ] ];
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
            if (!selector) 
		continue; // Skip @import and other nonstyle rules
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
