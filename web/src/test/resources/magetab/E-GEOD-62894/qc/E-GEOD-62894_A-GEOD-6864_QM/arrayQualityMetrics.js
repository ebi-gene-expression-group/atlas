// (C) Wolfgang Huber 2010-2011

// Script parameters - these are set up by R in the function 'writeReport' when copying the 
//   template for this script from arrayQualityMetrics/inst/scripts into the report.

var highlightInitial = [ false, false, true, true, false, true, false, false, false, false, false, true, true, false, false, true, false, false, true, false, true, false, false, true, true, true, false, false, true, false, false, false, true, false, false, false, true, false, false, false, true, false, false, false, true, false, false, false ];
var arrayMetadata    = [ [ "1", "GSM1535804", "GSM1535804", "2 day, water, susceptible, pish", "/nfs/ma/home/arrayexpress/ae2_production/data/EXPERIMENT/GEOD/E-GEOD-62894/GSM1535804_WLsh_3SC-2dH2OS_2043_S01_1_2.txt" ], [ "2", "GSM1535792", "GSM1535792", "avirulent line of rice blast fungus, 2 day, resistant, Pish", "/nfs/ma/home/arrayexpress/ae2_production/data/EXPERIMENT/GEOD/E-GEOD-62894/GSM1535792_WLsh_3R-2d77R_2042_S01_1_2.txt" ], [ "3", "GSM1535773", "GSM1535773", "3 day, water, susceptible, pish", "/nfs/ma/home/arrayexpress/ae2_production/data/EXPERIMENT/GEOD/E-GEOD-62894/GSM1535773_WLsh_1SC-3dH2OS_2247_S01_1_3.txt" ], [ "4", "GSM1535768", "GSM1535768", "susceptible, pish, virulent line of rice blast fungus, 2 day", "/nfs/ma/home/arrayexpress/ae2_production/data/EXPERIMENT/GEOD/E-GEOD-62894/GSM1535768_WLsh_1S-2d77S_2248_S01_1_2.txt" ], [ "5", "GSM1535760", "GSM1535760", "2 day, avirulent line of rice blast fungus, Pish, resistant", "/nfs/ma/home/arrayexpress/ae2_production/data/EXPERIMENT/GEOD/E-GEOD-62894/GSM1535760_WLsh_1R-2d77R_2246_S01_1_2.txt" ], [ "6", "GSM1535762", "GSM1535762", "Pish, resistant, 5 day, avirulent line of rice blast fungus", "/nfs/ma/home/arrayexpress/ae2_production/data/EXPERIMENT/GEOD/E-GEOD-62894/GSM1535762_WLsh_1R-5d77R_2246_S01_1_4.txt" ], [ "7", "GSM1535782", "GSM1535782", "water, 5 day, Pish, resistant", "/nfs/ma/home/arrayexpress/ae2_production/data/EXPERIMENT/GEOD/E-GEOD-62894/GSM1535782_WLsh_2RC-5dH2OR_2030_S01_1_4.txt" ], [ "8", "GSM1535771", "GSM1535771", "1 day, water, susceptible, pish", "/nfs/ma/home/arrayexpress/ae2_production/data/EXPERIMENT/GEOD/E-GEOD-62894/GSM1535771_WLsh_1SC-1dH2OS_2034_S01_1_1.txt" ], [ "9", "GSM1535761", "GSM1535761", "3 day, avirulent line of rice blast fungus, resistant, Pish", "/nfs/ma/home/arrayexpress/ae2_production/data/EXPERIMENT/GEOD/E-GEOD-62894/GSM1535761_WLsh_1R-3d77R_2246_S02_1_3.txt" ], [ "10", "GSM1535777", "GSM1535777", "3 day, avirulent line of rice blast fungus, resistant, Pish", "/nfs/ma/home/arrayexpress/ae2_production/data/EXPERIMENT/GEOD/E-GEOD-62894/GSM1535777_WLsh_2R-3d77R_2031_S01_1_3.txt" ], [ "11", "GSM1535778", "GSM1535778", "resistant, Pish, avirulent line of rice blast fungus, 5 day", "/nfs/ma/home/arrayexpress/ae2_production/data/EXPERIMENT/GEOD/E-GEOD-62894/GSM1535778_WLsh_2R-5d77R_2031_S01_1_4.txt" ], [ "12", "GSM1535770", "GSM1535770", "5 day, virulent line of rice blast fungus, susceptible, pish", "/nfs/ma/home/arrayexpress/ae2_production/data/EXPERIMENT/GEOD/E-GEOD-62894/GSM1535770_WLsh_1S-5d77S_2034_S01_1_2.txt" ], [ "13", "GSM1535786", "GSM1535786", "5 day, virulent line of rice blast fungus, susceptible, pish", "/nfs/ma/home/arrayexpress/ae2_production/data/EXPERIMENT/GEOD/E-GEOD-62894/GSM1535786_WLsh_2S-5d77S_2033_S01_1_4.txt" ], [ "14", "GSM1535789", "GSM1535789", "pish, susceptible, 3 day, water", "/nfs/ma/home/arrayexpress/ae2_production/data/EXPERIMENT/GEOD/E-GEOD-62894/GSM1535789_WLsh_2SC-3dH2OS_2032_S01_1_3.txt" ], [ "15", "GSM1535796", "GSM1535796", "Pish, resistant, water, 2 day", "/nfs/ma/home/arrayexpress/ae2_production/data/EXPERIMENT/GEOD/E-GEOD-62894/GSM1535796_WLsh_3RC-2dH2OR_2041_S01_1_2.txt" ], [ "16", "GSM1535764", "GSM1535764", "Pish, resistant, water, 2 day", "/nfs/ma/home/arrayexpress/ae2_production/data/EXPERIMENT/GEOD/E-GEOD-62894/GSM1535764_WLsh_1RC-2dH2OR_2245_S01_1_2.txt" ], [ "17", "GSM1535798", "GSM1535798", "resistant, Pish, water, 5 day", "/nfs/ma/home/arrayexpress/ae2_production/data/EXPERIMENT/GEOD/E-GEOD-62894/GSM1535798_WLsh_3RC-5dH2OR_2041_S01_1_4.txt" ], [ "18", "GSM1535797", "GSM1535797", "3 day, water, Pish, resistant", "/nfs/ma/home/arrayexpress/ae2_production/data/EXPERIMENT/GEOD/E-GEOD-62894/GSM1535797_WLsh_3RC-3dH2OR_2041_S01_1_3.txt" ], [ "19", "GSM1535765", "GSM1535765", "3 day, water, Pish, resistant", "/nfs/ma/home/arrayexpress/ae2_production/data/EXPERIMENT/GEOD/E-GEOD-62894/GSM1535765_WLsh_1RC-3dH2OR_2245_S01_1_3.txt" ], [ "20", "GSM1535801", "GSM1535801", "virulent line of rice blast fungus, 3 day, pish, susceptible", "/nfs/ma/home/arrayexpress/ae2_production/data/EXPERIMENT/GEOD/E-GEOD-62894/GSM1535801_WLsh_3S-3d77S_2044_S01_1_3.txt" ], [ "21", "GSM1535763", "GSM1535763", "resistant, Pish, water, 1 day", "/nfs/ma/home/arrayexpress/ae2_production/data/EXPERIMENT/GEOD/E-GEOD-62894/GSM1535763_WLsh_1RC-1dH2OR_2245_S01_1_1.txt" ], [ "22", "GSM1535806", "GSM1535806", "5 day, water, susceptible, pish", "/nfs/ma/home/arrayexpress/ae2_production/data/EXPERIMENT/GEOD/E-GEOD-62894/GSM1535806_WLsh_3SC-5dH2OS_2043_S01_1_4.txt" ], [ "23", "GSM1535779", "GSM1535779", "water, 1 day, Pish, resistant", "/nfs/ma/home/arrayexpress/ae2_production/data/EXPERIMENT/GEOD/E-GEOD-62894/GSM1535779_WLsh_2RC-1dH2OR_2030_S01_1_1.txt" ], [ "24", "GSM1535759", "GSM1535759", "avirulent line of rice blast fungus, 1 day, Pish, resistant", "/nfs/ma/home/arrayexpress/ae2_production/data/EXPERIMENT/GEOD/E-GEOD-62894/GSM1535759_WLsh_1R-1d77R_2246_S01_1_1.txt" ], [ "25", "GSM1535769", "GSM1535769", "3 day, virulent line of rice blast fungus, susceptible, pish", "/nfs/ma/home/arrayexpress/ae2_production/data/EXPERIMENT/GEOD/E-GEOD-62894/GSM1535769_WLsh_1S-3d77S_2248_S01_1_3.txt" ], [ "26", "GSM1535772", "GSM1535772", "water, 2 day, pish, susceptible", "/nfs/ma/home/arrayexpress/ae2_production/data/EXPERIMENT/GEOD/E-GEOD-62894/GSM1535772_WLsh_1SC-2dH2OS_2247_S01_1_2.txt" ], [ "27", "GSM1535803", "GSM1535803", "pish, susceptible, water, 1 day", "/nfs/ma/home/arrayexpress/ae2_production/data/EXPERIMENT/GEOD/E-GEOD-62894/GSM1535803_WLsh_3SC-1dH2OS_2043_S01_1_1.txt" ], [ "28", "GSM1535788", "GSM1535788", "water, 2 day, susceptible, pish", "/nfs/ma/home/arrayexpress/ae2_production/data/EXPERIMENT/GEOD/E-GEOD-62894/GSM1535788_WLsh_2SC-2dH2OS_2032_S01_1_2.txt" ], [ "29", "GSM1535793", "GSM1535793", "Pish, resistant, avirulent line of rice blast fungus, 3 day", "/nfs/ma/home/arrayexpress/ae2_production/data/EXPERIMENT/GEOD/E-GEOD-62894/GSM1535793_WLsh_3R-3d77R_2042_S01_1_3.txt" ], [ "30", "GSM1535780", "GSM1535780", "Pish, resistant, 2 day, water", "/nfs/ma/home/arrayexpress/ae2_production/data/EXPERIMENT/GEOD/E-GEOD-62894/GSM1535780_WLsh_2RC-2dH2OR_2030_S02_1_2.txt" ], [ "31", "GSM1535785", "GSM1535785", "susceptible, pish, 3 day, virulent line of rice blast fungus", "/nfs/ma/home/arrayexpress/ae2_production/data/EXPERIMENT/GEOD/E-GEOD-62894/GSM1535785_WLsh_2S-3d77S_2033_S01_1_3.txt" ], [ "32", "GSM1535795", "GSM1535795", "1 day, water, resistant, Pish", "/nfs/ma/home/arrayexpress/ae2_production/data/EXPERIMENT/GEOD/E-GEOD-62894/GSM1535795_WLsh_3RC-1dH2OR_2041_S01_1_1.txt" ], [ "33", "GSM1535767", "GSM1535767", "pish, susceptible, 1 day, virulent line of rice blast fungus", "/nfs/ma/home/arrayexpress/ae2_production/data/EXPERIMENT/GEOD/E-GEOD-62894/GSM1535767_WLsh_1S-1d77S_2248_S01_1_1.txt" ], [ "34", "GSM1535800", "GSM1535800", "virulent line of rice blast fungus, 2 day, susceptible, pish", "/nfs/ma/home/arrayexpress/ae2_production/data/EXPERIMENT/GEOD/E-GEOD-62894/GSM1535800_WLsh_3S-2d77S_2044_S01_1_2.txt" ], [ "35", "GSM1535805", "GSM1535805", "water, 3 day, susceptible, pish", "/nfs/ma/home/arrayexpress/ae2_production/data/EXPERIMENT/GEOD/E-GEOD-62894/GSM1535805_WLsh_3SC-3dH2OS_2043_S01_1_3.txt" ], [ "36", "GSM1535784", "GSM1535784", "virulent line of rice blast fungus, 2 day, pish, susceptible", "/nfs/ma/home/arrayexpress/ae2_production/data/EXPERIMENT/GEOD/E-GEOD-62894/GSM1535784_WLsh_2S-2d77S_2033_S01_1_2.txt" ], [ "37", "GSM1535774", "GSM1535774", "pish, susceptible, 5 day, water", "/nfs/ma/home/arrayexpress/ae2_production/data/EXPERIMENT/GEOD/E-GEOD-62894/GSM1535774_WLsh_1SC-5dH2OS_2247_S01_1_4.txt" ], [ "38", "GSM1535791", "GSM1535791", "1 day, avirulent line of rice blast fungus, Pish, resistant", "/nfs/ma/home/arrayexpress/ae2_production/data/EXPERIMENT/GEOD/E-GEOD-62894/GSM1535791_WLsh_3R-1d77R_2042_S01_1_1.txt" ], [ "39", "GSM1535790", "GSM1535790", "susceptible, pish, 5 day, water", "/nfs/ma/home/arrayexpress/ae2_production/data/EXPERIMENT/GEOD/E-GEOD-62894/GSM1535790_WLsh_2SC-5dH2OS_2032_S01_1_4.txt" ], [ "40", "GSM1535775", "GSM1535775", "Pish, resistant, 1 day, avirulent line of rice blast fungus", "/nfs/ma/home/arrayexpress/ae2_production/data/EXPERIMENT/GEOD/E-GEOD-62894/GSM1535775_WLsh_2R-1d77R_2031_S01_1_1.txt" ], [ "41", "GSM1535766", "GSM1535766", "Pish, resistant, water, 5 day", "/nfs/ma/home/arrayexpress/ae2_production/data/EXPERIMENT/GEOD/E-GEOD-62894/GSM1535766_WLsh_1RC-5dH2OR_2245_S01_1_4.txt" ], [ "42", "GSM1535783", "GSM1535783", "virulent line of rice blast fungus, 1 day, susceptible, pish", "/nfs/ma/home/arrayexpress/ae2_production/data/EXPERIMENT/GEOD/E-GEOD-62894/GSM1535783_WLsh_2S-1d77S_2033_S01_1_1.txt" ], [ "43", "GSM1535799", "GSM1535799", "virulent line of rice blast fungus, 1 day, susceptible, pish", "/nfs/ma/home/arrayexpress/ae2_production/data/EXPERIMENT/GEOD/E-GEOD-62894/GSM1535799_WLsh_3S-1d77S_2044_S01_1_1.txt" ], [ "44", "GSM1535794", "GSM1535794", "5 day, avirulent line of rice blast fungus, Pish, resistant", "/nfs/ma/home/arrayexpress/ae2_production/data/EXPERIMENT/GEOD/E-GEOD-62894/GSM1535794_WLsh_3R-5d77R_2042_S01_1_4.txt" ], [ "45", "GSM1535802", "GSM1535802", "susceptible, pish, 5 day, virulent line of rice blast fungus", "/nfs/ma/home/arrayexpress/ae2_production/data/EXPERIMENT/GEOD/E-GEOD-62894/GSM1535802_WLsh_3S-5d77S_2044_S01_1_4.txt" ], [ "46", "GSM1535781", "GSM1535781", "Pish, resistant, 3 day, water", "/nfs/ma/home/arrayexpress/ae2_production/data/EXPERIMENT/GEOD/E-GEOD-62894/GSM1535781_WLsh_2RC-3dH2OR_2030_S01_1_3.txt" ], [ "47", "GSM1535776", "GSM1535776", "resistant, Pish, avirulent line of rice blast fungus, 2 day", "/nfs/ma/home/arrayexpress/ae2_production/data/EXPERIMENT/GEOD/E-GEOD-62894/GSM1535776_WLsh_2R-2d77R_2031_S01_1_2.txt" ], [ "48", "GSM1535787", "GSM1535787", "water, 1 day, susceptible, pish", "/nfs/ma/home/arrayexpress/ae2_production/data/EXPERIMENT/GEOD/E-GEOD-62894/GSM1535787_WLsh_2SC-1dH2OS_2032_S01_1_1.txt" ] ];
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
