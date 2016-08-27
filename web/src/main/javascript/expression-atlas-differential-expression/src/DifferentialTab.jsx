const React = require('react');

const $ = require('jquery');
$.ajaxSetup({ traditional:true });

const Url = require('url');
const QueryString = require('querystring');

//*------------------------------------------------------------------*

const Results= require('./DifferentialResults.jsx');
const Facets = require('./DifferentialFacetsTree.jsx');
const PushQueryIntoBrowserHistory = require('./urlMaintainer.js').differentialPush;

//*------------------------------------------------------------------*

/*
 TODO if Solr queries get fast enough that we can:
 - split the two requests, so that the facets load first, initial results load second
 - a request to the server is done for every interaction with the facets tree
*/

const RequiredString = React.PropTypes.string.isRequired;

const DifferentialTab = React.createClass({
    propTypes: {
        hostUrl: RequiredString,
        geneQuery: RequiredString,
        conditionQuery : RequiredString,
        species: RequiredString
    },

    getInitialState () {
        return {
            facetsTreeData: [],
            results: [],
            legend: {
                maxDownLevel: 0,
                minDownLevel: 0,
                minUpLevel: 0,
                maxUpLevel: 0
            },
            querySelect: {}
        }
    },

    componentDidMount () {
        this._loadInitialData();
        // TODO Consider using https://github.com/reactjs/react-router
        window.addEventListener(
            'popstate',
            () => { this.setState({querySelect: this._getQuerySelectFromLocation()}); },
            false);
    },

    _getQuerySelectFromLocation() {
        let currentURL = Url.parse(window.location.toString());
        let differentialSelectParam = QueryString.parse(currentURL.query).ds;
        return differentialSelectParam ? JSON.parse(differentialSelectParam) : {};
    },

    _addElementToObjectOfArrays(obj, arrayName, element) {
        if (!obj[arrayName]) {
            obj[arrayName] = [];
        }
        obj[arrayName].push(element);
    },

    _removeElementFromObjectOfArrays(obj, arrayName, element) {
        delete obj[arrayName].splice(obj[arrayName].indexOf(element), 1);
        if (obj[arrayName].length === 0) {
            delete obj[arrayName];
        }
    },

    _setChecked (facetName, facetItemName, checked) {
        // Update URL
        let newQuerySelect = JSON.parse(JSON.stringify(this.state.querySelect));
        if (checked) {
            this._addElementToObjectOfArrays(newQuerySelect, facetName, facetItemName);
        } else {
            this._removeElementFromObjectOfArrays(newQuerySelect, facetName, facetItemName);
        }

        // TODO Consider using https://github.com/reactjs/react-router
        PushQueryIntoBrowserHistory(newQuerySelect, false);
        this.setState({
            querySelect: newQuerySelect
        });
    },

    _filteredResults (query = this.state.querySelect) {
        return this.state.results.filter(result =>
            this._resultMatchesQuery(result, query)
        );
    },

    _resultMatchesQuery (result, query) {
        if (Object.keys(query).length === 0) {
            return false;
        } else {
            return Object.keys(query).every(facetName =>
                query[facetName].some(facetItem =>
                    this._equalsToOrIncludes(result[facetName], facetItem)
                )
            );
        }
    },

    _equalsToOrIncludes(obj, value) {
        if (!!obj) {
            if (obj.constructor === Array) {
                return obj.includes(value);
            }
            else {
                return obj === value;
            }
        } else {
            return false;
        }
    },

    // Syncs tree data with URL (querySelect) and does some other smart things such as check/uncheck or disable facets based on
    // the user results (e.g. check & disable a facet if it’s shared by all results as a side effect of other choice)
    _prepareFacetTreeData (filteredResults) {
        return this.state.facetsTreeData.map(facet => ({
            facetName: facet.facetName,
            facetItems: facet.facetItems.map(facetItem => {

                let allFilteredResultsMatchThisFacetItem = filteredResults.every(result => this._equalsToOrIncludes(result[facet.facetName], facetItem.name));

                let querySelectAfterSwitchingThisFacetItem = JSON.parse(JSON.stringify(this.state.querySelect));
                if (this._equalsToOrIncludes(querySelectAfterSwitchingThisFacetItem[facet.facetName], facetItem.name)) {
                    this._removeElementFromObjectOfArrays(querySelectAfterSwitchingThisFacetItem, facet.facetName, facetItem.name);
                } else {
                    this._addElementToObjectOfArrays(querySelectAfterSwitchingThisFacetItem, facet.facetName, facetItem.name);
                }
                let resultIdsAfterSwitchingThisFacetItem = this._filteredResults(querySelectAfterSwitchingThisFacetItem).map(result => result.id).sort();
                let currentResultIds = filteredResults.map(result => result.id).sort();

                let sameResultsAfterSwitchingThisItem = JSON.stringify(resultIdsAfterSwitchingThisFacetItem) === JSON.stringify(currentResultIds);
                let noResultsAfterSwitchingThisItem = resultIdsAfterSwitchingThisFacetItem.length === 0;

                return {
                    name: facetItem.name,
                    value: facetItem.value,
                    checked: this._equalsToOrIncludes(this.state.querySelect[facet.facetName], facetItem.name) ||
                             sameResultsAfterSwitchingThisItem && allFilteredResultsMatchThisFacetItem,
                    disabled: noResultsAfterSwitchingThisItem || sameResultsAfterSwitchingThisItem
                }
            })
        }));
    },

    render () {
        let filteredResults = this._filteredResults();

        return (
            <div>
                <div className="grid_6 alpha" id="gxaDifferentialFacetsContainerDiv">
                    {Object.keys(this.state.facetsTreeData).length
                        ? <Facets
                              facets = {this._prepareFacetTreeData(filteredResults)}
                              setChecked = {this._setChecked}
                          />
                        : <div/>
                    }
                </div>
                <div className="grid_18 omega" id="gxaDifferentialResultsContainerDiv">
                    {this.state.results && this.state.results.length
                        ? <Results
                            results = {filteredResults}
                            hostUrl = {this.props.hostUrl}
                            {...this.state.legend}
                          />
                        : <div ref="loadingImagePlaceholder">
                              <img src={this.props.hostUrl + "/gxa/resources/images/loading.gif"}/>
                    </div>
                    }
                </div>
            </div>
        )
    },

    _loadInitialData () {
        let differentialFacetsUrlObject = Url.parse(this.props.hostUrl),
            differentialResultsUrlObject = Url.parse(this.props.hostUrl);

        if (window.location.pathname.match(/\/genes\//)) {
            differentialFacetsUrlObject.pathname = 'gxa/json/genes/' + this.props.identifier + '/differentialFacets';
            differentialResultsUrlObject.pathname = 'gxa/json/genes/' + this.props.identifier + '/differentialResults';
        } else if (window.location.pathname.match(/\/genesets\//)) {
            let queryParams = {organism: this.props.species};
            differentialFacetsUrlObject.pathname = 'gxa/json/genesets/' + this.props.identifier + '/differentialFacets';
            differentialFacetsUrlObject.query = queryParams;
            differentialResultsUrlObject.pathname = 'gxa/json/genesets/' + this.props.identifier + '/differentialResults';
            differentialFacetsUrlObject.query = queryParams;
        } else {
            let queryParams = {geneQuery: this.props.geneQuery, conditionQuery: this.props.conditionQuery, organism: this.props.species};
            differentialFacetsUrlObject.pathname = 'gxa/json/query/differentialFacets';
            differentialFacetsUrlObject.query = queryParams;
            differentialResultsUrlObject.pathname = 'gxa/json/query/differentialResults';
            differentialResultsUrlObject.query = queryParams;
        }

        let onAjaxFailure = (jqXHR, textStatus, errorThrown) => {
            console.log("ERROR");
            console.log("Status: " + textStatus);
            console.log("Error thrown: " + errorThrown);
        };

        $.ajax({
            url: Url.format(differentialFacetsUrlObject),
            dataType: "json",
            success: facetsResponse => {
                $.ajax({
                    url: Url.format(differentialResultsUrlObject),
                    dataType: "json",
                    success: resultsResponse => {

                        // TODO Consider using https://github.com/reactjs/react-router
                        let querySelect = this._getQuerySelectFromLocation();
                        if (!querySelect.kingdom) {
                            querySelect.kingdom = facetsResponse.kingdom.map(facetItem =>
                                facetItem.name
                            );
                        }
                        PushQueryIntoBrowserHistory(querySelect, true);

                        let facetsTreeData = this._transformFacetsResponseToArray(facetsResponse);

                        this.setState({
                            facetsTreeData: this._pruneFacetsTreeBasedOnResultsThatCameIn(facetsTreeData, resultsResponse.results),
                            querySelect: querySelect,
                            results: resultsResponse.results,
                            legend: {
                                minDownLevel: resultsResponse.minDownLevel,
                                minUpLevel: resultsResponse.minUpLevel,
                                maxDownLevel:resultsResponse.maxDownLevel,
                                maxUpLevel: resultsResponse.maxUpLevel
                            }
                        });

                    },
                    error: onAjaxFailure
                });
            },
            error: onAjaxFailure
        });
    },

    _transformFacetsResponseToArray(facetsResponse) {
        return Object.keys(facetsResponse).map(facetName => {
            return {
                facetName: facetName,
                facetItems: facetsResponse[facetName].map(facetItem => {
                    return {
                        name: facetItem.name,
                        value: facetItem.value,
                        disabled: false,
                        checked: false
                    }
                })
            };
        });
    },

    _pruneFacetsTreeBasedOnResultsThatCameIn (facetsTreeData, results) {
        return facetsTreeData.map(facet => (
            {
                facetName: facet.facetName,
                facetItems:
                    facet.facetItems.filter(facetItem =>
                        results.some(result => {
                            if (result[facet.facetName].constructor === Array) {
                                return result[facet.facetName].indexOf(facetItem.name) > -1;
                            } else {
                                return result[facet.facetName] === facetItem.name;
                            }
                        })
                    )
            }
        )).filter(facet => facet.facetItems.length > 0);
    }
});

//*------------------------------------------------------------------*

module.exports = DifferentialTab;
