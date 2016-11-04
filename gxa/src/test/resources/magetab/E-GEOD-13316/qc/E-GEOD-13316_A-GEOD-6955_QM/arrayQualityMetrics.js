// (C) Wolfgang Huber 2010-2011

// Script parameters - these are set up by R in the function 'writeReport' when copying the 
//   template for this script from arrayQualityMetrics/inst/scripts into the report.

var highlightInitial = [ false, false, false, false, false, false, false, false, true, false, true, false, false, false, false, false, true, false, true, false ];
var arrayMetadata    = [ [ "1", "GSM336077", "GSM336077", "normal", "GSM336077.txt" ], [ "2", "GSM336078", "GSM336078", "normal", "GSM336078.txt" ], [ "3", "GSM336074", "GSM336074", "normal", "GSM336074.txt" ], [ "4", "GSM336080", "GSM336080", "normal", "GSM336080.txt" ], [ "5", "GSM336079", "GSM336079", "normal", "GSM336079.txt" ], [ "6", "GSM336076", "GSM336076", "normal", "GSM336076.txt" ], [ "7", "GSM336083", "GSM336083", "normal", "GSM336083.txt" ], [ "8", "GSM336075", "GSM336075", "normal", "GSM336075.txt" ], [ "9", "GSM336081", "GSM336081", "normal", "GSM336081.txt" ], [ "10", "GSM336082", "GSM336082", "normal", "GSM336082.txt" ], [ "11", "GSM336065", "GSM336065", "idiopathic pulmonary fibrosis", "GSM336065.txt" ], [ "12", "GSM336067", "GSM336067", "idiopathic pulmonary fibrosis", "GSM336067.txt" ], [ "13", "GSM336066", "GSM336066", "idiopathic pulmonary fibrosis", "GSM336066.txt" ], [ "14", "GSM336071", "GSM336071", "idiopathic pulmonary fibrosis", "GSM336071.txt" ], [ "15", "GSM336070", "GSM336070", "idiopathic pulmonary fibrosis", "GSM336070.txt" ], [ "16", "GSM336068", "GSM336068", "idiopathic pulmonary fibrosis", "GSM336068.txt" ], [ "17", "GSM336072", "GSM336072", "idiopathic pulmonary fibrosis", "GSM336072.txt" ], [ "18", "GSM336069", "GSM336069", "idiopathic pulmonary fibrosis", "GSM336069.txt" ], [ "19", "GSM336064", "GSM336064", "idiopathic pulmonary fibrosis", "GSM336064.txt" ], [ "20", "GSM336073", "GSM336073", "idiopathic pulmonary fibrosis", "GSM336073.txt" ] ];
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
            if (!selector) continue; // Skip @import and other nonstyle rules
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
