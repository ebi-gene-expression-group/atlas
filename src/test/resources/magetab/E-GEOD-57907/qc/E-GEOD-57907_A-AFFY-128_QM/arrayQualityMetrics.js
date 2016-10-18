// (C) Wolfgang Huber 2010-2011

// Script parameters - these are set up by R in the function 'writeReport' when copying the 
//   template for this script from arrayQualityMetrics/inst/scripts into the report.

var highlightInitial = [ false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false ];
var arrayMetadata    = [ [ "1", "GSM1397309", "GSM1397309", "24 hour, susceptible", "GSM1397309_Marco26.CEL" ], [ "2", "GSM1397306", "GSM1397306", "24 hour, susceptible", "GSM1397306_Marco23.CEL" ], [ "3", "GSM1397314", "GSM1397314", "48 hour, resistant", "GSM1397314_Marco32.CEL" ], [ "4", "GSM1397300", "GSM1397300", "24 hour, resistant", "GSM1397300_Marco16.CEL" ], [ "5", "GSM1397316", "GSM1397316", "48 hour, susceptible", "GSM1397316_Marco35.CEL" ], [ "6", "GSM1397313", "GSM1397313", "resistant, 48 hour", "GSM1397313_Marco31.CEL" ], [ "7", "GSM1397310", "GSM1397310", "resistant, 48 hour", "GSM1397310_Marco27.CEL" ], [ "8", "GSM1397312", "GSM1397312", "resistant, 48 hour", "GSM1397312_Marco29.CEL" ], [ "9", "GSM1397311", "GSM1397311", "resistant, 48 hour", "GSM1397311_Marco28.CEL" ], [ "10", "GSM1397308", "GSM1397308", "susceptible, 24 hour", "GSM1397308_Marco25.CEL" ], [ "11", "GSM1397307", "GSM1397307", "susceptible, 24 hour", "GSM1397307_Marco24.CEL" ], [ "12", "GSM1397304", "GSM1397304", "susceptible, 24 hour", "GSM1397304_Marco21.CEL" ], [ "13", "GSM1397305", "GSM1397305", "susceptible, 24 hour", "GSM1397305_Marco22.CEL" ], [ "14", "GSM1397295", "GSM1397295", "susceptible, 0 hour", "GSM1397295_Marco09.CEL" ], [ "15", "GSM1397301", "GSM1397301", "resistant, 24 hour", "GSM1397301_Marco17.CEL" ], [ "16", "GSM1397303", "GSM1397303", "resistant, 24 hour", "GSM1397303_Marco19.CEL" ], [ "17", "GSM1397299", "GSM1397299", "resistant, 24 hour", "GSM1397299_Marco15.CEL" ], [ "18", "GSM1397298", "GSM1397298", "resistant, 24 hour", "GSM1397298_Marco14.CEL" ], [ "19", "GSM1397302", "GSM1397302", "resistant, 24 hour", "GSM1397302_Marco18.CEL" ], [ "20", "GSM1397297", "GSM1397297", "0 hour, susceptible", "GSM1397297_Marco11.CEL" ], [ "21", "GSM1397296", "GSM1397296", "0 hour, susceptible", "GSM1397296_Marco10.CEL" ], [ "22", "GSM1397318", "GSM1397318", "susceptible, 48 hour", "GSM1397318_Marco37.CEL" ], [ "23", "GSM1397319", "GSM1397319", "susceptible, 48 hour", "GSM1397319_Marco39.CEL" ], [ "24", "GSM1397317", "GSM1397317", "susceptible, 48 hour", "GSM1397317_Marco36.CEL" ], [ "25", "GSM1397315", "GSM1397315", "susceptible, 48 hour", "GSM1397315_Marco33.CEL" ], [ "26", "GSM1397292", "GSM1397292", "0 hour, resistant", "GSM1397292_Marco04.CEL" ], [ "27", "GSM1397291", "GSM1397291", "0 hour, resistant", "GSM1397291_Marco02.CEL" ], [ "28", "GSM1397294", "GSM1397294", "resistant, 0 hour", "GSM1397294_Marco06.CEL" ], [ "29", "GSM1397293", "GSM1397293", "resistant, 0 hour", "GSM1397293_Marco05.CEL" ], [ "30", "GSM1397290", "GSM1397290", "resistant, 0 hour", "GSM1397290_Marco01.CEL" ] ];
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
