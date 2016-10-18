const AnatomogramFactory = require('anatomogram');
const ReactDOM = require('react-dom');
const EventEmitter = require('events');

let eventEmitter = new EventEmitter();

exports.eventEmitter = eventEmitter;

exports.render = function({species = 'mus musculus', highlightIDs = [], expressedTissueColour = 'red', hoveredTissueColour = 'purple', mountNode}) {

    let anatomogramConfig = {
        pathToFolderWithBundledResources: '..',
        anatomogramData: { species: species },
        expressedTissueColour: expressedTissueColour,
        hoveredTissueColour: hoveredTissueColour,
        eventEmitter: eventEmitter,
        idsToBeHighlighted: highlightIDs
    };

    ReactDOM.render(
        AnatomogramFactory.create(anatomogramConfig), document.getElementById(mountNode)
    );
};
