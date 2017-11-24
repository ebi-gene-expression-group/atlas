// (C) Wolfgang Huber 2010-2011

// Script parameters - these are set up by R in the function 'writeReport' when copying the 
//   template for this script from arrayQualityMetrics/inst/scripts into the report.

var highlightInitial = [ true, true, true, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false ];
var arrayMetadata    = [ [ "1", "H_IB27", "H_IB27", "progressive supranuclear palsy", "050511MJA_U133_2.0_IB27.CEL" ], [ "2", "H_IB55", "H_IB55", "progressive supranuclear palsy", "051223MJA_U133_2.0_IB55.CEL" ], [ "3", "H_IB64", "H_IB64", "progressive supranuclear palsy", "051223MJA_U133_2.0_IB64.CEL" ], [ "4", "H_IB28", "H_IB28", "progressive supranuclear palsy", "050512MJA_U133_2.0_IB28.CEL" ], [ "5", "H_IB30", "H_IB30", "progressive supranuclear palsy", "050512MJA_U133_2.0_IB30.CEL" ], [ "6", "H_IB29", "H_IB29", "progressive supranuclear palsy", "050512MJA_U133_2.0_IB29.CEL" ], [ "7", "H_IB31", "H_IB31", "progressive supranuclear palsy", "050512MJA_U133_2.0_IB31.CEL" ], [ "8", "H_IB39", "H_IB39", "normal", "050705MJA_U133_2.0_IB09.CEL" ], [ "9", "H_IB41", "H_IB41", "normal", "050705MJA_U133_2.0_IB11.CEL" ], [ "10", "H_IB40", "H_IB40", "normal", "050705MJA_U133_2.0_IB10.CEL" ], [ "11", "H_IB37", "H_IB37", "normal", "050705MJA_U133_2.0_IB07.CEL" ], [ "12", "H_IB38", "H_IB38", "normal", "050705MJA_U133_2.0_IB08.CEL" ], [ "13", "H_IB60", "H_IB60", "Alzheimer's disease", "051223MJA_U133_2.0_IB60.CEL" ], [ "14", "H_IB35", "H_IB35", "Alzheimer's disease", "050705MJA_U133_2.0_IB05.CEL" ], [ "15", "H_IB33", "H_IB33", "Alzheimer's disease", "050705MJA_U133_2.0_IB03.CEL" ], [ "16", "H_IB32", "H_IB32", "Alzheimer's disease", "050705MJA_U133_2.0_IB02.CEL" ], [ "17", "H_IB59", "H_IB59", "Alzheimer's disease", "051223MJA_U133_2.0_IB59.CEL" ], [ "18", "H_IB34", "H_IB34", "Alzheimer's disease", "050705MJA_U133_2.0_IB04.CEL" ], [ "19", "H_IB36", "H_IB36", "Alzheimer's disease", "050705MJA_U133_2.0_IB06.CEL" ], [ "20", "H_IB18", "H_IB18", "Pick disease", "050511MJA_U133_2.0_IB18.CEL" ], [ "21", "H_IB21", "H_IB21", "Pick disease", "050511MJA_U133_2.0_IB21.CEL" ], [ "22", "H_IB62", "H_IB62", "Pick disease", "051223MJA_U133_2.0_IB62.CEL" ], [ "23", "H_IB19", "H_IB19", "Pick disease", "050511MJA_U133_2.0_IB19.CEL" ], [ "24", "H_IB63", "H_IB63", "Pick disease", "051223MJA_U133_2.0_IB63.CEL" ], [ "25", "H_IB20", "H_IB20", "Pick disease", "050511MJA_U133_2.0_IB20.CEL" ], [ "26", "H_IB25", "H_IB25", "Pick disease", "050511MJA_U133_2.0_IB25.CEL" ], [ "27", "H_IB24", "H_IB24", "Pick disease", "050511MJA_U133_2.0_IB24.CEL" ], [ "28", "H_IB22", "H_IB22", "Pick disease", "050511MJA_U133_2.0_IB22.CEL" ], [ "29", "H_IB17", "H_IB17", "Pick disease", "050511MJA_U133_2.0_IB17.CEL" ], [ "30", "H_IB23", "H_IB23", "Pick disease", "050511MJA_U133_2.0_IB23.CEL" ], [ "31", "H_IB26", "H_IB26", "Pick disease", "050511MJA_U133_2.0_IB26.CEL" ] ];
var svgObjectNames   = [ "dens", "pca" ];

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