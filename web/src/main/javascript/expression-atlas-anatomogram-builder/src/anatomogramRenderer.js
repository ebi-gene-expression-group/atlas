const AnatomogramFactory = require('anatomogram');
const React = require('react');
const ReactDOM = require('react-dom');
const EventEmitter = require('events');

let eventEmitter = new EventEmitter();

exports.render = function({species = 'mus musculus', pathIDs = [], expressedTissueColour = '#FF0000', hoveredTissueColour = '#800080', mountNode}) {
    ReactDOM.render(
        React.createElement(
            AnatomogramFactory.create({
                pathToFolderWithBundledResources: '',
                anatomogramData: {
                    species: species,
                    allSvgPathIds: pathIDs
                },
                expressedTissueColour: expressedTissueColour,
                hoveredTissueColour: hoveredTissueColour,
                eventEmitter: eventEmitter
            }), {}
        ),
        document.getElementById(mountNode)
    );
};

exports.eventEmitter = eventEmitter;