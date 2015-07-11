# Setup

```
npm install
npm install webpack-dev-server -g
```

# Tests

Tests are files in test/ that end with -test.js

## Tests via command line

To run all tests on the command line via mocha

```
npm test
```

## Tests in the browser (TDD mode)

To run all tests in the browser (for debugging/TDD):

```
npm run tdd
```

Open http://localhost:9000/webpack-dev-server/testpages/mocha.html

# Prod mode

To build minified production bundles into webapp/resources/js-bundles

```
npm run prod
```