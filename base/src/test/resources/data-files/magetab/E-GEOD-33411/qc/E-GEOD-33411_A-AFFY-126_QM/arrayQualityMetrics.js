// (C) Wolfgang Huber 2010-2011

// Script parameters - these are set up by R in the function 'writeReport' when copying the 
//   template for this script from arrayQualityMetrics/inst/scripts into the report.

var highlightInitial = [ true, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false ];
var arrayMetadata    = [ [ "1", "GSM826517", "GSM826517", "IRBB5, 3 hour", "GSM826517_5_86_3_r3_0620-19.CEL" ], [ "2", "GSM826508", "GSM826508", "12 hour, IR24", "GSM826508_24_86_12_r3_0620-20.CEL" ], [ "3", "GSM826510", "GSM826510", "24 hour, IR24", "GSM826510_24_86_24_r2_0620-12.CEL" ], [ "4", "GSM826509", "GSM826509", "24 hour, IR24", "GSM826509_24_86_24_r4_0620-31.CEL" ], [ "5", "GSM826540", "GSM826540", "IRBB7, 24 hour", "GSM826540_7_86_24_r2_0620-5.CEL" ], [ "6", "GSM826526", "GSM826526", "IRBB5, 24 hour", "GSM826526_5_86_24_r3_0620-23.CEL" ], [ "7", "GSM826524", "GSM826524", "24 hour, IRBB5", "GSM826524_5_86_24_r4_0620-40.CEL" ], [ "8", "GSM826525", "GSM826525", "24 hour, IRBB5", "GSM826525_5_86_24_r2_0620-1.CEL" ], [ "9", "GSM826513", "GSM826513", "0 hour, IRBB5", "GSM826513_5_86_0_r2_0620-14.CEL" ], [ "10", "GSM826505", "GSM826505", "6 hour, IR24", "GSM826505_24_86_6_r3_0620-16.CEL" ], [ "11", "GSM826503", "GSM826503", "6 hour, IR24", "GSM826503_24_86_6_r4_0620-38.CEL" ], [ "12", "GSM826504", "GSM826504", "6 hour, IR24", "GSM826504_24_86_6_r2_0620-10.CEL" ], [ "13", "GSM826520", "GSM826520", "6 hour, IRBB5", "GSM826520_5_86_6_r3_0620-26.CEL" ], [ "14", "GSM826518", "GSM826518", "6 hour, IRBB5", "GSM826518_5_86_6_r4_0620-32.CEL" ], [ "15", "GSM826498", "GSM826498", "0 hour, IR24", "GSM826498_24_86_0_r2_0620-15.CEL" ], [ "16", "GSM826497", "GSM826497", "0 hour, IR24", "GSM826497_24_86_0_r4_0620-45.CEL" ], [ "17", "GSM826523", "GSM826523", "12 hour, IRBB5", "GSM826523_5_86_12_r3_0620-25.CEL" ], [ "18", "GSM826501", "GSM826501", "3 hour, IR24", "GSM826501_24_86_3_r2_0620-7.CEL" ], [ "19", "GSM826502", "GSM826502", "3 hour, IR24", "GSM826502_24_86_3_r3_0620-21.CEL" ], [ "20", "GSM826500", "GSM826500", "3 hour, IR24", "GSM826500_24_86_3_r4_0620-39.CEL" ], [ "21", "GSM826521", "GSM826521", "IRBB5, 12 hour", "GSM826521_5_86_12_r4_0620-42.CEL" ], [ "22", "GSM826522", "GSM826522", "IRBB5, 12 hour", "GSM826522_5_86_12_r2_0620-3.CEL" ], [ "23", "GSM826535", "GSM826535", "6 hour, IRBB7", "GSM826535_7_86_6_r3_0620-22.CEL" ], [ "24", "GSM826534", "GSM826534", "6 hour, IRBB7", "GSM826534_7_86_6_r2_0620-6.CEL" ], [ "25", "GSM826533", "GSM826533", "6 hour, IRBB7", "GSM826533_7_86_6_r4_0620-34.CEL" ], [ "26", "GSM826511", "GSM826511", "IR24, 24 hour", "GSM826511_24_86_24_r3_0620-24.CEL" ], [ "27", "GSM826536", "GSM826536", "IRBB7, 12 hour", "GSM826536_7_86_12_r4_0620-37.CEL" ], [ "28", "GSM826539", "GSM826539", "24 hour, IRBB7", "GSM826539_7_86_24_r4_0620-35.CEL" ], [ "29", "GSM826541", "GSM826541", "24 hour, IRBB7", "GSM826541_7_86_24_r3_0620-18.CEL" ], [ "30", "GSM826512", "GSM826512", "IRBB5, 0 hour", "GSM826512_5_86_0_r4_0620-44.CEL" ], [ "31", "GSM826514", "GSM826514", "IRBB5, 0 hour", "GSM826514_5_86_0_r3_0620-29.CEL" ], [ "32", "GSM826506", "GSM826506", "IR24, 12 hour", "GSM826506_24_86_12_r4_0620-36.CEL" ], [ "33", "GSM826507", "GSM826507", "IR24, 12 hour", "GSM826507_24_86_12_r2_0620-2.CEL" ], [ "34", "GSM826530", "GSM826530", "3 hour, IRBB7", "GSM826530_7_86_3_r4_0620-33.CEL" ], [ "35", "GSM826527", "GSM826527", "IRBB7, 0 hour", "GSM826527_7_86_0_r4_0620-43.CEL" ], [ "36", "GSM826529", "GSM826529", "IRBB7, 0 hour", "GSM826529_7_86_0_r3_0620-30.CEL" ], [ "37", "GSM826499", "GSM826499", "IR24, 0 hour", "GSM826499_24_86_0_r3_0620-28.CEL" ], [ "38", "GSM826538", "GSM826538", "12 hour, IRBB7", "GSM826538_7_86_12_r3_0620-17.CEL" ], [ "39", "GSM826537", "GSM826537", "12 hour, IRBB7", "GSM826537_7_86_12_r2_0620-9.CEL" ], [ "40", "GSM826516", "GSM826516", "3 hour, IRBB5", "GSM826516_5_86_3_r2_0620-11.CEL" ], [ "41", "GSM826515", "GSM826515", "3 hour, IRBB5", "GSM826515_5_86_3_r4_0620-41.CEL" ], [ "42", "GSM826519", "GSM826519", "IRBB5, 6 hour", "GSM826519_5_86_6_r2_0620-8.CEL" ], [ "43", "GSM826528", "GSM826528", "0 hour, IRBB7", "GSM826528_7_86_0_r2_0620-13.CEL" ], [ "44", "GSM826531", "GSM826531", "IRBB7, 3 hour", "GSM826531_7_86_3_r2_0620-4.CEL" ], [ "45", "GSM826532", "GSM826532", "IRBB7, 3 hour", "GSM826532_7_86_3_r3_0620-27.CEL" ] ];
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