// (C) Wolfgang Huber 2010-2011

// Script parameters - these are set up by R in the function 'writeReport' when copying the 
//   template for this script from arrayQualityMetrics/inst/scripts into the report.

var highlightInitial = [ false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false ];
var arrayMetadata    = [ [ "1", "GSM1566656", "GSM1566656", "transgenic line BG9, 6 week", "GSM1566656_9M_R1.CEL" ], [ "2", "GSM1566658", "GSM1566658", "transgenic line BG9, 6 week", "GSM1566658_9M_R3.CEL" ], [ "3", "GSM1566646", "GSM1566646", "transgenic line BG8, 2 week", "GSM1566646_8S_R3.CEL" ], [ "4", "GSM1566644", "GSM1566644", "transgenic line BG8, 2 week", "GSM1566644_8S_R1.CEL" ], [ "5", "GSM1566645", "GSM1566645", "2 week, transgenic line BG8", "GSM1566645_8S_R2.CEL" ], [ "6", "GSM1566649", "GSM1566649", "2 week, transgenic line BG9", "GSM1566649_9S_R3.CEL" ], [ "7", "GSM1566648", "GSM1566648", "2 week, transgenic line BG9", "GSM1566648_9S_R2.CEL" ], [ "8", "GSM1566641", "GSM1566641", "2 week, wild type", "GSM1566641_WT_S_R1.CEL" ], [ "9", "GSM1566643", "GSM1566643", "2 week, wild type", "GSM1566643_WT_S_R3.CEL" ], [ "10", "GSM1566642", "GSM1566642", "2 week, wild type", "GSM1566642_WT_S_R2.CEL" ], [ "11", "GSM1566650", "GSM1566650", "wild type, 6 week", "GSM1566650_WT_M_R1.CEL" ], [ "12", "GSM1566651", "GSM1566651", "wild type, 6 week", "GSM1566651_WT_M_R2.CEL" ], [ "13", "GSM1566652", "GSM1566652", "6 week, wild type", "GSM1566652_WT_M_R3.CEL" ], [ "14", "GSM1566655", "GSM1566655", "6 week, transgenic line BG8", "GSM1566655_8M_R3.CEL" ], [ "15", "GSM1566654", "GSM1566654", "6 week, transgenic line BG8", "GSM1566654_8M_R2.CEL" ], [ "16", "GSM1566653", "GSM1566653", "6 week, transgenic line BG8", "GSM1566653_8M_R1.CEL" ], [ "17", "GSM1566647", "GSM1566647", "transgenic line BG9, 2 week", "GSM1566647_9S_R1.CEL" ], [ "18", "GSM1566657", "GSM1566657", "6 week, transgenic line BG9", "GSM1566657_9M_R2.CEL" ] ];
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