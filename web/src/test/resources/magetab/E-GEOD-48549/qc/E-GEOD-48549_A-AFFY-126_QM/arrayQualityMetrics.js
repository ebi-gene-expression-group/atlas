// (C) Wolfgang Huber 2010-2011

// Script parameters - these are set up by R in the function 'writeReport' when copying the 
//   template for this script from arrayQualityMetrics/inst/scripts into the report.

var highlightInitial = [ false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false ];
var arrayMetadata    = [ [ "1", "GSM1180679", "GSM1180679", "5 millimolar, wild type, ammonium, shoot", "GSM1180679_RE121053.CEL" ], [ "2", "GSM1180663", "GSM1180663", "shoot, wild type, ammonium, 5 millimolar", "GSM1180663_RE121033.CEL" ], [ "3", "GSM1180680", "GSM1180680", "ammonium, transgenic_AlaAT-ox 1/7, 2 millimolar, root", "GSM1180680_RE121054_2.CEL" ], [ "4", "GSM1180674", "GSM1180674", "5 millimolar, transgenic_AlaAT-ox 1/7, ammonium, shoot", "GSM1180674_RE121047.CEL" ], [ "5", "GSM1180649", "GSM1180649", "ammonium, transgenic_AlaAT-ox 1/7, 5 millimolar, shoot", "GSM1180649_RE121010.CEL" ], [ "6", "GSM1180686", "GSM1180686", "ammonium, transgenic_AlaAT-ox 3/8, 2 millimolar, shoot", "GSM1180686_RE121063.CEL" ], [ "7", "GSM1180692", "GSM1180692", "root, transgenic_AlaAT-ox 1/7, ammonium, 2 millimolar", "GSM1180692_RE121071.CEL" ], [ "8", "GSM1180648", "GSM1180648", "shoot, transgenic_AlaAT-ox 1/7, ammonium, 2 millimolar", "GSM1180648_RE121008.CEL" ], [ "9", "GSM1180682", "GSM1180682", "2 millimolar, wild type, ammonium, root", "GSM1180682_RE121056.CEL" ], [ "10", "GSM1180687", "GSM1180687", "shoot, ammonium, transgenic_AlaAT-ox 3/8, 5 millimolar", "GSM1180687_RE121064.CEL" ], [ "11", "GSM1180684", "GSM1180684", "shoot, 5 millimolar, transgenic_AlaAT-ox 1/7, ammonium", "GSM1180684_RE121060.CEL" ], [ "12", "GSM1180675", "GSM1180675", "wild type, ammonium, 2 millimolar, shoot", "GSM1180675_RE121049.CEL" ], [ "13", "GSM1180656", "GSM1180656", "root, wild type, ammonium, 2 millimolar", "GSM1180656_RE121024.CEL" ], [ "14", "GSM1180668", "GSM1180668", "ammonium, wild type, 2 millimolar, shoot", "GSM1180668_RE121040.CEL" ], [ "15", "GSM1180651", "GSM1180651", "shoot, 2 millimolar, transgenic_AlaAT-ox 3/8, ammonium", "GSM1180651_RE121016.CEL" ], [ "16", "GSM1180646", "GSM1180646", "shoot, 5 millimolar, transgenic_AlaAT-ox 3/8, ammonium", "GSM1180646_RE121006.CEL" ], [ "17", "GSM1180654", "GSM1180654", "shoot, 2 millimolar, transgenic_AlaAT-ox 1/7, ammonium", "GSM1180654_RE121022.CEL" ], [ "18", "GSM1180661", "GSM1180661", "2 millimolar, wild type, ammonium, shoot", "GSM1180661_RE121030_2.CEL" ], [ "19", "GSM1180676", "GSM1180676", "2 millimolar, ammonium, transgenic_AlaAT-ox 1/7, shoot", "GSM1180676_RE121050.CEL" ], [ "20", "GSM1180670", "GSM1180670", "root, 2 millimolar, wild type, ammonium", "GSM1180670_RE121043.CEL" ], [ "21", "GSM1180655", "GSM1180655", "shoot, transgenic_AlaAT-ox 3/8, ammonium, 5 millimolar", "GSM1180655_RE121023.CEL" ], [ "22", "GSM1180669", "GSM1180669", "shoot, ammonium, wild type, 5 millimolar", "GSM1180669_RE121042.CEL" ], [ "23", "GSM1180664", "GSM1180664", "shoot, ammonium, transgenic_AlaAT-ox 3/8, 2 millimolar", "GSM1180664_RE121036.CEL" ], [ "24", "GSM1180653", "GSM1180653", "root, ammonium, transgenic_AlaAT-ox 1/7, 2 millimolar", "GSM1180653_RE121021.CEL" ], [ "25", "GSM1180678", "GSM1180678", "shoot, 2 millimolar, ammonium, wild type", "GSM1180678_RE121052.CEL" ] ];
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
