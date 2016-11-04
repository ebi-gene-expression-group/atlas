const React = require(`react`);
const ReactDOM = require(`react-dom`);
const Button = require(`react-bootstrap/lib/Button`);
const Glyphicon = require(`react-bootstrap/lib/Glyphicon`);

const ensemblUtils = require(`./ensemblUtils.js`);
require(`./GenomeBrowserLauncher.css`);

const GenomeBrowserLauncher = React.createClass({
    propTypes: {
        atlasBaseURL: React.PropTypes.string.isRequired,
        experimentAccession: React.PropTypes.string.isRequired,
        isBaseline: React.PropTypes.bool.isRequired,
        species: React.PropTypes.string.isRequired,
        ensemblDB: React.PropTypes.string.isRequired,
        columnType: React.PropTypes.string.isRequired,
        eventEmitter: React.PropTypes.object.isRequired
    },

    _noSelectedColumnMessageArticle() {
        const beginsWithVowel = str => str.match(/^[aeiou]/i);
        return beginsWithVowel(this.props.columnType) ? `an ` : `a `;
    },

    _ensemblTrackURL(baseURL, selectedColumnId, selectedGeneId) {
        const ensemblSpecies = ensemblUtils.toEnsemblSpecies(this.props.species);

        var atlasTrackBaseURLWithTrackFileHeader =
            `${this.props.atlasBaseURL}/experiments/${this.props.experimentAccession}/tracks/${this.props.experimentAccession}.${selectedColumnId}`;

        const contigViewBottom =
            `contigviewbottom=url:${atlasTrackBaseURLWithTrackFileHeader}.genes.${this.props.isBaseline ? `expressions` : `log2foldchange`}.bedGraph`;

        const tiling =
            `${this.props.isBaseline ? `` : `=tiling,url:${atlasTrackBaseURLWithTrackFileHeader}.genes.pval.bedGraph=pvalue;`}`;

        return `${baseURL}db=core;${contigViewBottom}${tiling};format=BEDGRAPH`;
    },

    _openEnsemblWindow(baseURL) {
        if (!this.state.selectedColumnId || !this.state.selectedGeneId) {
            return;
        }
        console.log(this._ensemblTrackURL(baseURL,this.state.selectedColumnId,this.state.selectedGeneId));
        window.open(this._ensemblTrackURL(baseURL,this.state.selectedColumnId,this.state.selectedGeneId), '_blank');
    },

    _onColumnSelectionChange(selectedColumnId) {
        this.setState({selectedColumnId: selectedColumnId});
    },

    _onGeneSelectionChange(selectedGeneId) {
        this.setState({selectedGeneId: selectedGeneId});
    },

    _updateButton() {
        var buttonEnabled = this.state.selectedColumnId && this.state.selectedGeneId ? true : false;
        $(ReactDOM.findDOMNode(this.refs.ensemblButton)).button("option", "disabled", !buttonEnabled);
        if (this.props.ensemblDB == "plants") {
            $(ReactDOM.findDOMNode(this.refs.grameneButton)).button("option", "disabled", !buttonEnabled);
        }
    },

    componentDidUpdate() {
        this._updateButton();
    },

    componentDidMount() {
        this._updateButton();
        this.props.eventEmitter.addListener('onColumnSelectionChange', this._onColumnSelectionChange);
        this.props.eventEmitter.addListener('onGeneSelectionChange', this._onGeneSelectionChange);
    },

    getInitialState: function () {
        return {
            selectedColumnId: null,
            selectedGeneId: null,
            buttonText: ``
        };
    },

    render() {
        const ensemblHost = ensemblUtils.getEnsemblHost(this.props.ensemblDB);
        const grameneHost = ensemblUtils.getGrameneHost();

        return (
            <div style={{width: `245px`}}>
                <div>
                    <div className="gxaEnsemblGrameneLauncherHeader">
                        <label>Ensembl Genome Browser</label>
                        <img src={`${this.props.atlasBaseURL}/resources/images/ensembl.png`} style={{padding: `0px 5px`, verticalAlign: `text-bottom`}}/>
                    </div>
                    <button ref="ensemblButton" onClick={this._openEnsemblWindow.bind(this, ensemblHost)}>Open</button>
                </div>
                { this.props.ensemblDB === `plants` ?
                    <div>
                        <div className="gxaEnsemblGrameneLauncherHeader">
                            <label>Gramene Genome Browser</label>
                            <img src={`${this.props.atlasBaseURL}/resources/images/gramene.png`} style={{padding:  `0px 5px`, verticalAlign: "text-bottom"}}/>
                        </div>
                        <button onClick={this._openEnsemblWindow.bind(this, grameneHost)}>Open</button>
                    </div>
                    : null
                }
                <div style={{fontSize: `x-small`, padding: `9px 9px`}}>{this._helpMessage(this.state.selectedColumnId, this.state.selectedGeneId)}</div>
            </div>
        );
    },

    _helpMessage: function (selectedColumnId, selectedGeneId) {
        if (selectedColumnId && selectedGeneId) {
            return "";
        }

        var noSelectedColumnMessage = selectedColumnId ? "" : this.props.columnType;
        var noSelectedGeneMessage = selectedGeneId ? "" : "gene";

        return "Please select " + this._noSelectedColumnMessageArticle() + noSelectedColumnMessage + (!(selectedColumnId || selectedGeneId) ? " and a " : "") + noSelectedGeneMessage + " from the table";
    }
});

module.exports = GenomeBrowserLauncher;