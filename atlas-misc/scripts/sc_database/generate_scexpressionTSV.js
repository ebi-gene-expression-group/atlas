/**
 * Created by barrera on 11/05/2017.
 */

const genes = 50;
const cells = 100;

var header = new Array(cells);
header[0] = "GeneId";
for (var i = 1; i <= cells; i++) {
    header[i] = "cell_" + i;
}

var rowNames = new Array(genes);
for (var i = 0; i < genes; i++) {
    rowNames[i] = "gene_" + i;
}

var headers = header.join("\t");
process.stdout.write(headers + "\n");

for(var i = 0; i < genes; i++) {
    var line = new Array(rowNames[i]);

    var expressionLevels = new Array(cells);
    for (var j=0; j < cells; j++) {
        expressionLevels[j] = Math.random();
    }

    var row = line.concat(expressionLevels);
    var res = row.join("\t");

    process.stdout.write(res + "\n");
}