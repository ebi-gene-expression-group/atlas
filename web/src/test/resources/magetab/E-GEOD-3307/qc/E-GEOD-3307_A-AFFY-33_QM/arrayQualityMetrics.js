// (C) Wolfgang Huber 2010-2011

// Script parameters - these are set up by R in the function 'writeReport' when copying the 
//   template for this script from arrayQualityMetrics/inst/scripts into the report.

var highlightInitial = [ false, false, false, false, false, true, true, false, false, true, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, false, false, false, true, false, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, true ];
var arrayMetadata    = [ [ "1", "GSM74392", "GSM74392", "X-linked recessive Emery-Dreifuss muscular dystrophy", "GSM74392.CEL" ], [ "2", "GSM74393", "GSM74393", "X-linked recessive Emery-Dreifuss muscular dystrophy", "GSM74393.CEL" ], [ "3", "GSM74389", "GSM74389", "X-linked recessive Emery-Dreifuss muscular dystrophy", "GSM74389.CEL" ], [ "4", "GSM74394", "GSM74394", "X-linked recessive Emery-Dreifuss muscular dystrophy", "GSM74394.CEL" ], [ "5", "GSM121361", "GSM121361", "Duchenne muscular dystrophy", "GSM121361.CEL" ], [ "6", "GSM74379", "GSM74379", "Duchenne muscular dystrophy", "GSM74379.CEL" ], [ "7", "GSM74378", "GSM74378", "Duchenne muscular dystrophy", "GSM74378.CEL" ], [ "8", "GSM121369", "GSM121369", "Duchenne muscular dystrophy", "GSM121369.CEL" ], [ "9", "GSM74381", "GSM74381", "Duchenne muscular dystrophy", "GSM74381.CEL" ], [ "10", "GSM74377", "GSM74377", "Duchenne muscular dystrophy", "GSM74377.CEL" ], [ "11", "GSM121363", "GSM121363", "Duchenne muscular dystrophy", "GSM121363.CEL" ], [ "12", "GSM74380", "GSM74380", "Duchenne muscular dystrophy", "GSM74380.CEL" ], [ "13", "GSM121357", "GSM121357", "Duchenne muscular dystrophy", "GSM121357.CEL" ], [ "14", "GSM121368", "GSM121368", "Duchenne muscular dystrophy", "GSM121368.CEL" ], [ "15", "GSM74397", "GSM74397", "limb girdle muscular dystrophy 2I", "GSM74397.CEL" ], [ "16", "GSM74396", "GSM74396", "limb girdle muscular dystrophy 2I", "GSM74396.CEL" ], [ "17", "GSM74400", "GSM74400", "limb girdle muscular dystrophy 2I", "GSM74400.CEL" ], [ "18", "GSM74401", "GSM74401", "limb girdle muscular dystrophy 2I", "GSM74401.CEL" ], [ "19", "GSM74399", "GSM74399", "limb girdle muscular dystrophy 2I", "GSM74399.CEL" ], [ "20", "GSM74398", "GSM74398", "limb girdle muscular dystrophy 2I", "GSM74398.CEL" ], [ "21", "GSM74395", "GSM74395", "limb girdle muscular dystrophy 2I", "GSM74395.CEL" ], [ "22", "GSM74356", "GSM74356", "normal", "GSM74356.CEL" ], [ "23", "GSM119937", "GSM119937", "normal", "GSM119937.CEL" ], [ "24", "GSM74404", "GSM74404", "normal", "GSM74404.CEL" ], [ "25", "GSM74409", "GSM74409", "normal", "GSM74409.CEL" ], [ "26", "GSM74358", "GSM74358", "normal", "GSM74358.CEL" ], [ "27", "GSM74402", "GSM74402", "normal", "GSM74402.CEL" ], [ "28", "GSM74410", "GSM74410", "normal", "GSM74410.CEL" ], [ "29", "GSM74360", "GSM74360", "normal", "GSM74360.CEL" ], [ "30", "GSM74359", "GSM74359", "normal", "GSM74359.CEL" ], [ "31", "GSM74363", "GSM74363", "normal", "GSM74363.CEL" ], [ "32", "GSM74408", "GSM74408", "normal", "GSM74408.CEL" ], [ "33", "GSM74406", "GSM74406", "normal", "GSM74406.CEL" ], [ "34", "GSM74361", "GSM74361", "normal", "GSM74361.CEL" ], [ "35", "GSM119936", "GSM119936", "normal", "GSM119936.CEL" ], [ "36", "GSM74403", "GSM74403", "normal", "GSM74403.CEL" ], [ "37", "GSM74362", "GSM74362", "normal", "GSM74362.CEL" ], [ "38", "GSM74407", "GSM74407", "normal", "GSM74407.CEL" ], [ "39", "GSM74357", "GSM74357", "normal", "GSM74357.CEL" ], [ "40", "GSM74364", "GSM74364", "Becker muscular dystrophy", "GSM74364.CEL" ], [ "41", "GSM74366", "GSM74366", "Becker muscular dystrophy", "GSM74366.CEL" ], [ "42", "GSM74239", "GSM74239", "Becker muscular dystrophy", "GSM74239.CEL" ], [ "43", "GSM74367", "GSM74367", "Becker muscular dystrophy", "GSM74367.CEL" ], [ "44", "GSM74365", "GSM74365", "Becker muscular dystrophy", "GSM74365.CEL" ], [ "45", "GSM74388", "GSM74388", "autosomal dominant Emery-Dreifuss muscular dystrophy", "GSM74388.CEL" ], [ "46", "GSM74390", "GSM74390", "autosomal dominant Emery-Dreifuss muscular dystrophy", "GSM74390.CEL" ], [ "47", "GSM74391", "GSM74391", "autosomal dominant Emery-Dreifuss muscular dystrophy", "GSM74391.CEL" ], [ "48", "GSM74387", "GSM74387", "autosomal dominant Emery-Dreifuss muscular dystrophy", "GSM74387.CEL" ], [ "49", "GSM121366", "GSM121366", "fascioscapulohumeral muscular dystrophy", "GSM121366.CEL" ], [ "50", "GSM121371", "GSM121371", "fascioscapulohumeral muscular dystrophy", "GSM121371.CEL" ], [ "51", "GSM121362", "GSM121362", "fascioscapulohumeral muscular dystrophy", "GSM121362.CEL" ], [ "52", "GSM121370", "GSM121370", "fascioscapulohumeral muscular dystrophy", "GSM121370.CEL" ], [ "53", "GSM121358", "GSM121358", "fascioscapulohumeral muscular dystrophy", "GSM121358.CEL" ], [ "54", "GSM121373", "GSM121373", "fascioscapulohumeral muscular dystrophy", "GSM121373.CEL" ], [ "55", "GSM121359", "GSM121359", "fascioscapulohumeral muscular dystrophy", "GSM121359.CEL" ], [ "56", "GSM121364", "GSM121364", "fascioscapulohumeral muscular dystrophy", "GSM121364.CEL" ], [ "57", "GSM121374", "GSM121374", "fascioscapulohumeral muscular dystrophy", "GSM121374.CEL" ], [ "58", "GSM121367", "GSM121367", "fascioscapulohumeral muscular dystrophy", "GSM121367.CEL" ], [ "59", "GSM121360", "GSM121360", "fascioscapulohumeral muscular dystrophy", "GSM121360.CEL" ], [ "60", "GSM121372", "GSM121372", "fascioscapulohumeral muscular dystrophy", "GSM121372.CEL" ], [ "61", "GSM121365", "GSM121365", "fascioscapulohumeral muscular dystrophy", "GSM121365.CEL" ], [ "62", "GSM121407", "GSM121407", "fascioscapulohumeral muscular dystrophy", "GSM121407.CEL" ], [ "63", "GSM74416", "GSM74416", "hereditary spastic paraplegia", "GSM74416.CEL" ], [ "64", "GSM74418", "GSM74418", "hereditary spastic paraplegia", "GSM74418.CEL" ], [ "65", "GSM74417", "GSM74417", "hereditary spastic paraplegia", "GSM74417.CEL" ], [ "66", "GSM74419", "GSM74419", "hereditary spastic paraplegia", "GSM74419.CEL" ], [ "67", "GSM121393", "GSM121393", "juvenile dermatomyositis", "GSM121393.CEL" ], [ "68", "GSM121392", "GSM121392", "juvenile dermatomyositis", "GSM121392.CEL" ], [ "69", "GSM121394", "GSM121394", "juvenile dermatomyositis", "GSM121394.CEL" ], [ "70", "GSM121380", "GSM121380", "juvenile dermatomyositis", "GSM121380.CEL" ], [ "71", "GSM121387", "GSM121387", "juvenile dermatomyositis", "GSM121387.CEL" ], [ "72", "GSM121385", "GSM121385", "juvenile dermatomyositis", "GSM121385.CEL" ], [ "73", "GSM121379", "GSM121379", "juvenile dermatomyositis", "GSM121379.CEL" ], [ "74", "GSM121395", "GSM121395", "juvenile dermatomyositis", "GSM121395.CEL" ], [ "75", "GSM121384", "GSM121384", "juvenile dermatomyositis", "GSM121384.CEL" ], [ "76", "GSM121383", "GSM121383", "juvenile dermatomyositis", "GSM121383.CEL" ], [ "77", "GSM121399", "GSM121399", "juvenile dermatomyositis", "GSM121399.CEL" ], [ "78", "GSM121386", "GSM121386", "juvenile dermatomyositis", "GSM121386.CEL" ], [ "79", "GSM121398", "GSM121398", "juvenile dermatomyositis", "GSM121398.CEL" ], [ "80", "GSM121382", "GSM121382", "juvenile dermatomyositis", "GSM121382.CEL" ], [ "81", "GSM121397", "GSM121397", "juvenile dermatomyositis", "GSM121397.CEL" ], [ "82", "GSM121390", "GSM121390", "juvenile dermatomyositis", "GSM121390.CEL" ], [ "83", "GSM121388", "GSM121388", "juvenile dermatomyositis", "GSM121388.CEL" ], [ "84", "GSM121381", "GSM121381", "juvenile dermatomyositis", "GSM121381.CEL" ], [ "85", "GSM121389", "GSM121389", "juvenile dermatomyositis", "GSM121389.CEL" ], [ "86", "GSM121396", "GSM121396", "juvenile dermatomyositis", "GSM121396.CEL" ], [ "87", "GSM121391", "GSM121391", "juvenile dermatomyositis", "GSM121391.CEL" ], [ "88", "GSM74382", "GSM74382", "limb girdle muscular dystrophy 2B", "GSM74382.CEL" ], [ "89", "GSM74352", "GSM74352", "limb girdle muscular dystrophy 2B", "GSM74352.CEL" ], [ "90", "GSM74385", "GSM74385", "limb girdle muscular dystrophy 2B", "GSM74385.CEL" ], [ "91", "GSM74355", "GSM74355", "limb girdle muscular dystrophy 2B", "GSM74355.CEL" ], [ "92", "GSM74386", "GSM74386", "limb girdle muscular dystrophy 2B", "GSM74386.CEL" ], [ "93", "GSM74384", "GSM74384", "limb girdle muscular dystrophy 2B", "GSM74384.CEL" ], [ "94", "GSM74353", "GSM74353", "limb girdle muscular dystrophy 2B", "GSM74353.CEL" ], [ "95", "GSM74351", "GSM74351", "limb girdle muscular dystrophy 2B", "GSM74351.CEL" ], [ "96", "GSM74383", "GSM74383", "limb girdle muscular dystrophy 2B", "GSM74383.CEL" ], [ "97", "GSM74354", "GSM74354", "limb girdle muscular dystrophy 2B", "GSM74354.CEL" ], [ "98", "GSM74246", "GSM74246", "amyotrophic lateral sclerosis", "GSM74246.CEL" ], [ "99", "GSM74244", "GSM74244", "amyotrophic lateral sclerosis", "GSM74244.CEL" ], [ "100", "GSM74242", "GSM74242", "amyotrophic lateral sclerosis", "GSM74242.CEL" ], [ "101", "GSM74240", "GSM74240", "amyotrophic lateral sclerosis", "GSM74240.CEL" ], [ "102", "GSM74243", "GSM74243", "amyotrophic lateral sclerosis", "GSM74243.CEL" ], [ "103", "GSM74247", "GSM74247", "amyotrophic lateral sclerosis", "GSM74247.CEL" ], [ "104", "GSM74241", "GSM74241", "amyotrophic lateral sclerosis", "GSM74241.CEL" ], [ "105", "GSM74248", "GSM74248", "amyotrophic lateral sclerosis", "GSM74248.CEL" ], [ "106", "GSM74245", "GSM74245", "amyotrophic lateral sclerosis", "GSM74245.CEL" ], [ "107", "GSM74414", "GSM74414", "acute quadriplegic myopathy", "GSM74414.CEL" ], [ "108", "GSM74411", "GSM74411", "acute quadriplegic myopathy", "GSM74411.CEL" ], [ "109", "GSM74412", "GSM74412", "acute quadriplegic myopathy", "GSM74412.CEL" ], [ "110", "GSM74415", "GSM74415", "acute quadriplegic myopathy", "GSM74415.CEL" ], [ "111", "GSM74413", "GSM74413", "acute quadriplegic myopathy", "GSM74413.CEL" ], [ "112", "GSM74375", "GSM74375", "limb girdle muscular dystrophy 2A", "GSM74375.CEL" ], [ "113", "GSM74405", "GSM74405", "limb girdle muscular dystrophy 2A", "GSM74405.CEL" ], [ "114", "GSM74371", "GSM74371", "limb girdle muscular dystrophy 2A", "GSM74371.CEL" ], [ "115", "GSM74374", "GSM74374", "limb girdle muscular dystrophy 2A", "GSM74374.CEL" ], [ "116", "GSM74369", "GSM74369", "limb girdle muscular dystrophy 2A", "GSM74369.CEL" ], [ "117", "GSM74372", "GSM74372", "limb girdle muscular dystrophy 2A", "GSM74372.CEL" ], [ "118", "GSM74376", "GSM74376", "limb girdle muscular dystrophy 2A", "GSM74376.CEL" ], [ "119", "GSM74373", "GSM74373", "limb girdle muscular dystrophy 2A", "GSM74373.CEL" ], [ "120", "GSM74368", "GSM74368", "limb girdle muscular dystrophy 2A", "GSM74368.CEL" ], [ "121", "GSM74370", "GSM74370", "limb girdle muscular dystrophy 2A", "GSM74370.CEL" ] ];
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
