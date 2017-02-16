import React from 'react';

const EbiSpeciesIcon = require('react-ebi-species').Icon;

class BrowseBySpecies extends React.Component {

    constructor(props) {
        super(props);
    }

    render() {

        let speciesItems = this.props.speciesInfoList.map(speciesItem =>
            <SingleSpecies key = {speciesItem.species}
                           species = {speciesItem.species}
                           atlasBaseUrl = {this.props.atlasBaseUrl}
                           kingdom = {speciesItem.kingdom}
                           totalExperiments = {speciesItem.totalExperiments}
                           totalBaseline = {speciesItem.baselineExperiments}
                           totalDifferential = {speciesItem.differentialExperiments}

            />);

        const atlasBaseUrl = this.props.atlasBaseUrl + "/gxa/experiments?foundation";

        return (
            <div>
                <div className="row">
                    {speciesItems}
                </div>

                <div className="row">
                    <div className="small-6 small-centered columns margin-top-large">
                        <a href={atlasBaseUrl} className="button float-center">View all species</a>
                    </div>
                </div>
            </div>

        );
    }

}

class SingleSpecies extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {

        const iconUrl = this.props.atlasBaseUrl + "/gxa/experiments?kingdom=" + this.props.kingdom + "&foundation";
        const differentialUrl = this.props.atlasBaseUrl + "/gxa/experiments?kingdom=" + this.props.kingdom + "&experimentType=Differential&foundation";
        const baselineUrl = this.props.atlasBaseUrl + "/gxa/experiments?kingdom=" + this.props.kingdom + "&experimentType=Baseline&foundation";

        const speciesFirstCapitalLetter = this.props.species[0].toUpperCase() + this.props.species.substr(1);

        return (
            <div className="columns small-4 text-center combo single-box">
                <a href={iconUrl}>

                    <span className="large-species-icon">
                        <EbiSpeciesIcon species={this.props.species}/>
                    </span>


                    <h5 className="species">{speciesFirstCapitalLetter}</h5>
                </a>

                <p className="experiments">
                    {this.props.totalExperiments} experiments<br/>
                    <a href={differentialUrl} className="differential">
                        <span data-tooltip aria-haspopup="true" className="differential tiny button-rd" title="Differential experiments">D</span>
                        {this.props.totalDifferential}
                    </a>
                    <a href={baselineUrl} className="baseline padding-left-medium">
                        <span data-tooltip aria-haspopup="true" className="baseline tiny button-rd" title="Baseline experiments">B</span>
                        {this.props.totalBaseline}
                    </a>
                </p>
            </div>
        );
    }
}

export default BrowseBySpecies;