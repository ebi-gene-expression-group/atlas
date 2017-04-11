const React = require('react');

const EventEmitter = require('events');

//*------------------------------------------------------------------*

const Facets = require('./BaselineFacetsTree.jsx');
const BaselineHeatmaps = require('./BaselineHeatmaps.jsx');
const UrlManager = require('./urlManager.js');

//*------------------------------------------------------------------*

const RequiredString = React.PropTypes.string.isRequired;
const RequiredObject = React.PropTypes.object.isRequired;

const BaselineRouter = React.createClass({
    propTypes: {
        hostUrl: RequiredString,
        /*
         {
             "homo sapiens": [{ "name": "ORGANISM_PART", "value": "Organism part"},
                              { "name": "CELL_LINE", "value": "Cell line"}],
             "macaca mulatta": [{ "name": "ORGANISM_PART", "value": "Organism part"}]
         }
         */
        facetsTreeData: RequiredObject,
        query: RequiredString,
        geneQuery: RequiredString,
        conditionQuery: RequiredString,
        species: RequiredString
    },

    getInitialState () {
        let anatomogramDataEventEmitter = new EventEmitter();
        anatomogramDataEventEmitter.setMaxListeners(0);

        let newQuerySelect = UrlManager.parseBaselineUrlParameter(),
            newShowAnatomograms = false;

        if (Object.keys(newQuerySelect).length === 0) {
            Object.keys(this.props.facetsTreeData).forEach(species => {
                let factorToPreselect =
                    this.props.facetsTreeData[species]
                    .find(factor => factor.name.toLowerCase() === 'organism_part');
                if (factorToPreselect) {
                    this._addElementToObjectOfArrays(newQuerySelect, species, factorToPreselect.name);
                    newShowAnatomograms = true;
                } else if (this.props.facetsTreeData[species].length) {
                    this._addElementToObjectOfArrays(
                        newQuerySelect, species, this.props.facetsTreeData[species][0].name);
                }
            });
        }

        UrlManager.baselinePush(newQuerySelect, true);

        return {
            // In this case, transforming props to state isn’t an anti-pattern because state can be mutated when facets
            // are clicked in _setChecked
            facetsTreeData: this._transformPropsFacetsObjectToArray(newQuerySelect),
            querySelect: newQuerySelect,
            anatomogramDataEventEmitter: anatomogramDataEventEmitter,
            showAnatomograms: newShowAnatomograms
        }
    },

    componentDidMount () {
        // TODO Consider using https://github.com/reactjs/react-router
        window.addEventListener(
            'popstate',
            () => {
                let newQuerySelect = UrlManager.parseBaselineUrlParameter();
                this.setState({
                    querySelect: newQuerySelect,
                    facetsTreeData: this._transformPropsFacetsObjectToArray(newQuerySelect)
                });
            },
            false);
    },

    render () {
        return (
            <div className="row">
                <div className="small-3 columns">
                    <Facets
                        facets = {this.state.facetsTreeData}
                        setChecked = {this._setChecked}
                        showAnatomograms = {this.state.showAnatomograms}
                        toggleAnatomograms = {this._toggleAnatomograms}
                        disableAnatomogramsCheckbox = {!this._organismPartInQuerySelect()}
                    />
                </div>
                <div className="small-9 columns">
                    <BaselineHeatmaps
                        hostUrl = {this.props.hostUrl}
                        query = {this.props.query}
                        geneQuery = {this.props.geneQuery}
                        conditionQuery = {this.props.conditionQuery}
                        heatmaps = {this._querySelectToHeatmaps()}
                        showAnatomograms = {this.state.showAnatomograms}
                        anatomogramDataEventEmitter = {this.state.anatomogramDataEventEmitter}
                    />
                </div>
            </div>
        )
    },

    _setChecked (species, factorName, checked) {
        let newQuerySelect = JSON.parse(JSON.stringify(this.state.querySelect));
        let newFacetsTreeData = JSON.parse(JSON.stringify(this.state.facetsTreeData));

        if (checked) {
            this._addElementToObjectOfArrays(newQuerySelect, species, factorName);
            newFacetsTreeData.find(facet => facet.facetName === species).facetItems
                .find(factor => factor.name === factorName).checked = true;
        } else {
            this._removeElementFromObjectOfArrays(newQuerySelect, species, factorName);
            newFacetsTreeData.find(facet => facet.facetName === species).facetItems
                .find(factor => factor.name === factorName).checked = false;
        }

        UrlManager.baselinePush(newQuerySelect, false);
        this.setState({
            facetsTreeData: newFacetsTreeData,
            querySelect: newQuerySelect
        });
    },

    _addElementToObjectOfArrays (obj, arrayName, element) {
        if (!obj[arrayName]) {
            obj[arrayName] = [];
        }
        obj[arrayName].push(element);
    },

    _removeElementFromObjectOfArrays (obj, arrayName, element) {
        delete obj[arrayName].splice(obj[arrayName].indexOf(element), 1);
        if (obj[arrayName].length === 0) {
            delete obj[arrayName];
        }
    },

    _toggleAnatomograms () {
        let newShowAnatomograms = !this.state.showAnatomograms;

        this.setState({
            showAnatomograms: newShowAnatomograms
        })
    },

    _organismPartInQuerySelect (querySelect = this.state.querySelect) {
        return Object.keys(querySelect)
               .some(species => querySelect[species].some(facetItem => facetItem.toLowerCase() === 'organism_part'));
    },

    // Also syncs this.state.facetsTreeData with querySelect
    _transformPropsFacetsObjectToArray (querySelect) {
        return Object.keys(this.props.facetsTreeData).map(facetName => {
            return {
                facetName: facetName,
                facetItems: this.props.facetsTreeData[facetName].map(facetItem => {
                    return {
                        name: facetItem.name,
                        value: facetItem.value,
                        checked: querySelect[facetName] ? querySelect[facetName].includes(facetItem.name) : false
                    }
                })
            };
        });
    },

    _querySelectToHeatmaps () {
        /*
         querySelect={ "Homo sapiens": [ "CELL_LINE", "ORGANISM_PART" ] }
         ->
         [ { "species": "Homo sapiens",
             "factor": { "name": "ORGANISM_PART", "value": "Organism part"}
           },
           { "species": "Homo sapiens",
             "factor": { "name": "CELL_LINE", "value": "Cell line" }
         ]
         */
        let heatmaps = [];

        // We iterate over facetsTreeData instead of over querySelect to get heatmaps in the same order as the facets
        // tree
        this.state.facetsTreeData.forEach(facet => {
            facet.facetItems.forEach(facetItem => {
                if (this.state.querySelect[facet.facetName] &&
                    this.state.querySelect[facet.facetName].includes(facetItem.name)) {
                    heatmaps.push({
                        species: facet.facetName,
                        factor: facetItem
                    })
                }
            });
        });

        return heatmaps;
    }
});

//*------------------------------------------------------------------*

module.exports = BaselineRouter;
