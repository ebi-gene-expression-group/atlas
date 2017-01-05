import React from 'react';
import Button from 'react-bootstrap/lib/Button';
import Url from 'url';

import ensemblLogo from '../assets/ensembl.png';
import grameneLogo from '../assets/gramene.png';
import wormbaseLogo from '../assets/wbps.png';

class GenomeBrowserButton extends React.Component {
    constructor(props, context) {
        super(props, context);
    }

    render() {

        const {label, logo} = this._getLabelAndLogo();

        return(
            <div style={{paddingBottom: `6px`}}>
                <Button bsSize="small"
                        disabled={!this.props.trackUrlParameter}
                        onClick={() => {this._handleOnClick()}}>
                    <img src={logo} height="16px"/> Open {label} genome browser
                </Button>
            </div>
        );
    }

    _getLabelAndLogo() {
        const hostname = Url.parse(this.props.genomeBrowserUrl).hostname;

        if (hostname.endsWith(`ensembl.org`)) {
            return {
                label: `Ensembl`,
                logo: ensemblLogo
            };
        } else if (hostname.endsWith(`gramene.org`)) {
            return {
                label: `Gramene`,
                logo: grameneLogo
            };
        } else if (hostname.endsWith(`wormbase.org`)) {
            return {
                label: `WormBase`,
                logo: wormbaseLogo
            };
        } else {
            return {
                label: ``,
                logo: ``
            };
        }
    }

    _handleOnClick() {
        console.log(`${this.props.genomeBrowserUrl}${this.props.trackUrlParameter}`);
        window.open(`${this.props.genomeBrowserUrl}${this.props.trackUrlParameter}`, '_blank');
    }

}


GenomeBrowserButton.propTypes = {
    genomeBrowserUrl: React.PropTypes.string.isRequired,
    trackUrlParameter: React.PropTypes.string.isRequired
};

export default GenomeBrowserButton;