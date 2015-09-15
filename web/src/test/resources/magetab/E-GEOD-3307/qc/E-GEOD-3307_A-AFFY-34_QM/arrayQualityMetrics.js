// (C) Wolfgang Huber 2010-2011

// Script parameters - these are set up by R in the function 'writeReport' when copying the 
//   template for this script from arrayQualityMetrics/inst/scripts into the report.

var highlightInitial = [ false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, true, true, true, false, true, true, false, false, false, false, false, false, false, false, false, false, true, false, true, false, false, false, false ];
var arrayMetadata    = [ [ "1", "GSM121356", "GSM121356", "Duchenne muscular dystrophy", "GSM121356.CEL" ], [ "2", "GSM121345", "GSM121345", "Duchenne muscular dystrophy", "GSM121345.CEL" ], [ "3", "GSM120786", "GSM120786", "Duchenne muscular dystrophy", "GSM120786.CEL" ], [ "4", "GSM120777", "GSM120777", "Duchenne muscular dystrophy", "GSM120777.CEL" ], [ "5", "GSM120760", "GSM120760", "Duchenne muscular dystrophy", "GSM120760.CEL" ], [ "6", "GSM121329", "GSM121329", "Duchenne muscular dystrophy", "GSM121329.CEL" ], [ "7", "GSM121333", "GSM121333", "Duchenne muscular dystrophy", "GSM121333.CEL" ], [ "8", "GSM120763", "GSM120763", "Duchenne muscular dystrophy", "GSM120763.CEL" ], [ "9", "GSM120764", "GSM120764", "Duchenne muscular dystrophy", "GSM120764.CEL" ], [ "10", "GSM121331", "GSM121331", "Duchenne muscular dystrophy", "GSM121331.CEL" ], [ "11", "GSM120780", "GSM120780", "X-linked recessive Emery-Dreifuss muscular dystrophy", "GSM120780.CEL" ], [ "12", "GSM121102", "GSM121102", "X-linked recessive Emery-Dreifuss muscular dystrophy", "GSM121102.CEL" ], [ "13", "GSM121203", "GSM121203", "X-linked recessive Emery-Dreifuss muscular dystrophy", "GSM121203.CEL" ], [ "14", "GSM120772", "GSM120772", "limb girdle muscular dystrophy 2I", "GSM120772.CEL" ], [ "15", "GSM120787", "GSM120787", "limb girdle muscular dystrophy 2I", "GSM120787.CEL" ], [ "16", "GSM120758", "GSM120758", "limb girdle muscular dystrophy 2I", "GSM120758.CEL" ], [ "17", "GSM120773", "GSM120773", "limb girdle muscular dystrophy 2I", "GSM120773.CEL" ], [ "18", "GSM120771", "GSM120771", "limb girdle muscular dystrophy 2I", "GSM120771.CEL" ], [ "19", "GSM120783", "GSM120783", "limb girdle muscular dystrophy 2I", "GSM120783.CEL" ], [ "20", "GSM120774", "GSM120774", "limb girdle muscular dystrophy 2I", "GSM120774.CEL" ], [ "21", "GSM120765", "GSM120765", "normal", "GSM120765.CEL" ], [ "22", "GSM121400", "GSM121400", "normal", "GSM121400.CEL" ], [ "23", "GSM121402", "GSM121402", "normal", "GSM121402.CEL" ], [ "24", "GSM120767", "GSM120767", "normal", "GSM120767.CEL" ], [ "25", "GSM121412", "GSM121412", "normal", "GSM121412.CEL" ], [ "26", "GSM121409", "GSM121409", "normal", "GSM121409.CEL" ], [ "27", "GSM121410", "GSM121410", "normal", "GSM121410.CEL" ], [ "28", "GSM121408", "GSM121408", "normal", "GSM121408.CEL" ], [ "29", "GSM120784", "GSM120784", "normal", "GSM120784.CEL" ], [ "30", "GSM121406", "GSM121406", "normal", "GSM121406.CEL" ], [ "31", "GSM121416", "GSM121416", "normal", "GSM121416.CEL" ], [ "32", "GSM121415", "GSM121415", "normal", "GSM121415.CEL" ], [ "33", "GSM121403", "GSM121403", "normal", "GSM121403.CEL" ], [ "34", "GSM121413", "GSM121413", "normal", "GSM121413.CEL" ], [ "35", "GSM121414", "GSM121414", "normal", "GSM121414.CEL" ], [ "36", "GSM121401", "GSM121401", "normal", "GSM121401.CEL" ], [ "37", "GSM121405", "GSM121405", "normal", "GSM121405.CEL" ], [ "38", "GSM121404", "GSM121404", "normal", "GSM121404.CEL" ], [ "39", "GSM120768", "GSM120768", "Becker muscular dystrophy", "GSM120768.CEL" ], [ "40", "GSM120761", "GSM120761", "Becker muscular dystrophy", "GSM120761.CEL" ], [ "41", "GSM120788", "GSM120788", "Becker muscular dystrophy", "GSM120788.CEL" ], [ "42", "GSM120781", "GSM120781", "Becker muscular dystrophy", "GSM120781.CEL" ], [ "43", "GSM120753", "GSM120753", "Becker muscular dystrophy", "GSM120753.CEL" ], [ "44", "GSM120779", "GSM120779", "autosomal dominant Emery-Dreifuss muscular dystrophy", "GSM120779.CEL" ], [ "45", "GSM120766", "GSM120766", "autosomal dominant Emery-Dreifuss muscular dystrophy", "GSM120766.CEL" ], [ "46", "GSM121204", "GSM121204", "autosomal dominant Emery-Dreifuss muscular dystrophy", "GSM121204.CEL" ], [ "47", "GSM120757", "GSM120757", "autosomal dominant Emery-Dreifuss muscular dystrophy", "GSM120757.CEL" ], [ "48", "GSM120770", "GSM120770", "autosomal dominant Emery-Dreifuss muscular dystrophy", "GSM120770.CEL" ], [ "49", "GSM121335", "GSM121335", "fascioscapulohumeral muscular dystrophy", "GSM121335.CEL" ], [ "50", "GSM121352", "GSM121352", "fascioscapulohumeral muscular dystrophy", "GSM121352.CEL" ], [ "51", "GSM121341", "GSM121341", "fascioscapulohumeral muscular dystrophy", "GSM121341.CEL" ], [ "52", "GSM121343", "GSM121343", "fascioscapulohumeral muscular dystrophy", "GSM121343.CEL" ], [ "53", "GSM121350", "GSM121350", "fascioscapulohumeral muscular dystrophy", "GSM121350.CEL" ], [ "54", "GSM121344", "GSM121344", "fascioscapulohumeral muscular dystrophy", "GSM121344.CEL" ], [ "55", "GSM121348", "GSM121348", "fascioscapulohumeral muscular dystrophy", "GSM121348.CEL" ], [ "56", "GSM121342", "GSM121342", "fascioscapulohumeral muscular dystrophy", "GSM121342.CEL" ], [ "57", "GSM121338", "GSM121338", "fascioscapulohumeral muscular dystrophy", "GSM121338.CEL" ], [ "58", "GSM121354", "GSM121354", "fascioscapulohumeral muscular dystrophy", "GSM121354.CEL" ], [ "59", "GSM121330", "GSM121330", "fascioscapulohumeral muscular dystrophy", "GSM121330.CEL" ], [ "60", "GSM121337", "GSM121337", "fascioscapulohumeral muscular dystrophy", "GSM121337.CEL" ], [ "61", "GSM121346", "GSM121346", "fascioscapulohumeral muscular dystrophy", "GSM121346.CEL" ], [ "62", "GSM121347", "GSM121347", "fascioscapulohumeral muscular dystrophy", "GSM121347.CEL" ], [ "63", "GSM121349", "GSM121349", "hereditary spastic paraplegia", "GSM121349.CEL" ], [ "64", "GSM121336", "GSM121336", "hereditary spastic paraplegia", "GSM121336.CEL" ], [ "65", "GSM121355", "GSM121355", "hereditary spastic paraplegia", "GSM121355.CEL" ], [ "66", "GSM121339", "GSM121339", "hereditary spastic paraplegia", "GSM121339.CEL" ], [ "67", "GSM121243", "GSM121243", "juvenile dermatomyositis", "GSM121243.CEL" ], [ "68", "GSM121213", "GSM121213", "juvenile dermatomyositis", "GSM121213.CEL" ], [ "69", "GSM121244", "GSM121244", "juvenile dermatomyositis", "GSM121244.CEL" ], [ "70", "GSM121248", "GSM121248", "juvenile dermatomyositis", "GSM121248.CEL" ], [ "71", "GSM121207", "GSM121207", "juvenile dermatomyositis", "GSM121207.CEL" ], [ "72", "GSM121212", "GSM121212", "juvenile dermatomyositis", "GSM121212.CEL" ], [ "73", "GSM121215", "GSM121215", "juvenile dermatomyositis", "GSM121215.CEL" ], [ "74", "GSM121211", "GSM121211", "juvenile dermatomyositis", "GSM121211.CEL" ], [ "75", "GSM121205", "GSM121205", "juvenile dermatomyositis", "GSM121205.CEL" ], [ "76", "GSM121217", "GSM121217", "juvenile dermatomyositis", "GSM121217.CEL" ], [ "77", "GSM121246", "GSM121246", "juvenile dermatomyositis", "GSM121246.CEL" ], [ "78", "GSM121206", "GSM121206", "juvenile dermatomyositis", "GSM121206.CEL" ], [ "79", "GSM121218", "GSM121218", "juvenile dermatomyositis", "GSM121218.CEL" ], [ "80", "GSM121245", "GSM121245", "juvenile dermatomyositis", "GSM121245.CEL" ], [ "81", "GSM121247", "GSM121247", "juvenile dermatomyositis", "GSM121247.CEL" ], [ "82", "GSM121210", "GSM121210", "juvenile dermatomyositis", "GSM121210.CEL" ], [ "83", "GSM121208", "GSM121208", "juvenile dermatomyositis", "GSM121208.CEL" ], [ "84", "GSM121214", "GSM121214", "juvenile dermatomyositis", "GSM121214.CEL" ], [ "85", "GSM121216", "GSM121216", "juvenile dermatomyositis", "GSM121216.CEL" ], [ "86", "GSM121209", "GSM121209", "juvenile dermatomyositis", "GSM121209.CEL" ], [ "87", "GSM121234", "GSM121234", "juvenile dermatomyositis", "GSM121234.CEL" ], [ "88", "GSM120756", "GSM120756", "limb girdle muscular dystrophy 2B", "GSM120756.CEL" ], [ "89", "GSM120792", "GSM120792", "limb girdle muscular dystrophy 2B", "GSM120792.CEL" ], [ "90", "GSM121353", "GSM121353", "limb girdle muscular dystrophy 2B", "GSM121353.CEL" ], [ "91", "GSM121334", "GSM121334", "limb girdle muscular dystrophy 2B", "GSM121334.CEL" ], [ "92", "GSM121332", "GSM121332", "limb girdle muscular dystrophy 2B", "GSM121332.CEL" ], [ "93", "GSM120778", "GSM120778", "limb girdle muscular dystrophy 2B", "GSM120778.CEL" ], [ "94", "GSM121351", "GSM121351", "limb girdle muscular dystrophy 2B", "GSM121351.CEL" ], [ "95", "GSM121340", "GSM121340", "limb girdle muscular dystrophy 2B", "GSM121340.CEL" ], [ "96", "GSM120769", "GSM120769", "limb girdle muscular dystrophy 2B", "GSM120769.CEL" ], [ "97", "GSM120755", "GSM120755", "limb girdle muscular dystrophy 2B", "GSM120755.CEL" ], [ "98", "GSM120747", "GSM120747", "amyotrophic lateral sclerosis", "GSM120747.CEL" ], [ "99", "GSM120744", "GSM120744", "amyotrophic lateral sclerosis", "GSM120744.CEL" ], [ "100", "GSM120752", "GSM120752", "amyotrophic lateral sclerosis", "GSM120752.CEL" ], [ "101", "GSM120748", "GSM120748", "amyotrophic lateral sclerosis", "GSM120748.CEL" ], [ "102", "GSM120751", "GSM120751", "amyotrophic lateral sclerosis", "GSM120751.CEL" ], [ "103", "GSM120750", "GSM120750", "amyotrophic lateral sclerosis", "GSM120750.CEL" ], [ "104", "GSM120746", "GSM120746", "amyotrophic lateral sclerosis", "GSM120746.CEL" ], [ "105", "GSM120745", "GSM120745", "amyotrophic lateral sclerosis", "GSM120745.CEL" ], [ "106", "GSM120749", "GSM120749", "amyotrophic lateral sclerosis", "GSM120749.CEL" ], [ "107", "GSM120594", "GSM120594", "acute quadriplegic myopathy", "GSM120594.CEL" ], [ "108", "GSM120720", "GSM120720", "acute quadriplegic myopathy", "GSM120720.CEL" ], [ "109", "GSM120719", "GSM120719", "acute quadriplegic myopathy", "GSM120719.CEL" ], [ "110", "GSM120591", "GSM120591", "acute quadriplegic myopathy", "GSM120591.CEL" ], [ "111", "GSM120718", "GSM120718", "acute quadriplegic myopathy", "GSM120718.CEL" ], [ "112", "GSM120785", "GSM120785", "limb girdle muscular dystrophy 2A", "GSM120785.CEL" ], [ "113", "GSM120790", "GSM120790", "limb girdle muscular dystrophy 2A", "GSM120790.CEL" ], [ "114", "GSM120776", "GSM120776", "limb girdle muscular dystrophy 2A", "GSM120776.CEL" ], [ "115", "GSM120791", "GSM120791", "limb girdle muscular dystrophy 2A", "GSM120791.CEL" ], [ "116", "GSM120759", "GSM120759", "limb girdle muscular dystrophy 2A", "GSM120759.CEL" ], [ "117", "GSM120762", "GSM120762", "limb girdle muscular dystrophy 2A", "GSM120762.CEL" ], [ "118", "GSM120775", "GSM120775", "limb girdle muscular dystrophy 2A", "GSM120775.CEL" ], [ "119", "GSM120754", "GSM120754", "limb girdle muscular dystrophy 2A", "GSM120754.CEL" ], [ "120", "GSM120782", "GSM120782", "limb girdle muscular dystrophy 2A", "GSM120782.CEL" ], [ "121", "GSM120789", "GSM120789", "limb girdle muscular dystrophy 2A", "GSM120789.CEL" ] ];
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
		continue; // Skip @import and other nonstyle rules
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
