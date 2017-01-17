import React from 'react';
import GenomeBrowserButton from './GenomeBrowserButton.jsx';

class GenomeBrowserLauncher extends React.Component {
    constructor(props, context) {
        super(props, context);
    }

    render() {
        const buttons =
            this.props.genomeBrowserUrls.map(genomeBrowserUrl =>
                <GenomeBrowserButton pathToResources={this.props.pathToResources}
                                     genomeBrowserUrl={genomeBrowserUrl}
                                     trackUrlParameter={this._buildTrackUrlParameter()}
                                     key={genomeBrowserUrl}
                />
            );

        return (
            <div className="ensembl-launcher-box" style={{width: `245px`}}>
                {buttons}
                <div style={{fontSize: `small`}}>
                    {this._helpMessage(this.props.selectedColumnId, this.props.selectedGeneId)}
                </div>
            </div>
        )
    }

    _buildTrackUrlParameter() {
        if (!this.props.selectedColumnId || !this.props.selectedGeneId) {
            return ``;
        }

        const atlasTrackBaseUrlWithTrackFileHeader =
            `${this.props.atlasBaseUrl}/experiments/${this.props.experimentAccession}` +
            `/tracks/${this.props.experimentAccession}.${this.props.selectedColumnId}`;

        const contigViewBottom =
            `contigviewbottom=url:${atlasTrackBaseUrlWithTrackFileHeader}.genes.` +
            `${this.props.isBaseline ? `expressions` : `log2foldchange`}.bedGraph`;

        const tiling =
            `${this.props.isBaseline ?
                `` : `=tiling,url:${atlasTrackBaseUrlWithTrackFileHeader}.genes.pval.bedGraph=pvalue;`}`;

        return `/Location/View?g=${this.props.selectedGeneId};${contigViewBottom}${tiling};format=BEDGRAPH`;
    }

    _helpMessage(selectedColumnId, selectedGeneId) {
        if (selectedColumnId && selectedGeneId) {
            return ``;
        }

        const noSelectedColumnMessage = selectedColumnId ? `` : this.props.columnType;
        const noSelectedGeneMessage = selectedGeneId ? `` : `gene`;

        return `Please select ` + this._noSelectedColumnMessageArticle(noSelectedColumnMessage) + noSelectedColumnMessage + (!(selectedColumnId || selectedGeneId) ? ` and a ` : ``) + `${noSelectedGeneMessage} from the table`;
    }

    _noSelectedColumnMessageArticle(noun) {
        const beginsWithVowel = str => str.match(/^[aeiou]/i);
        return beginsWithVowel(noun) ? `an ` : `a `;
    }
}

// PromiseState props are populated by React Refetch; they are actually required but left as optional so that IDEA
// doesn’t complain that they’re not there
GenomeBrowserLauncher.propTypes = {
    atlasBaseUrl: React.PropTypes.string.isRequired,
    pathToResources: React.PropTypes.string,
    experimentAccession: React.PropTypes.string.isRequired,
    isBaseline: React.PropTypes.bool.isRequired,
    columnType: React.PropTypes.string.isRequired,
    genomeBrowserUrls: React.PropTypes.arrayOf(React.PropTypes.string).isRequired,
    selectedGeneId: React.PropTypes.string,
    selectedColumnId: React.PropTypes.string
};

export default GenomeBrowserLauncher;