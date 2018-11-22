import React from 'react'
import renderer from 'react-test-renderer'

import disclaimers from '../src/disclaimers'

const MyComponent = ({ title, content }) => Disclaimers[`zebrafish`].content

describe(`Disclaimer`, () => {
  test.each(Object.keys(disclaimers))(`%s matches snapshot`, (disclaimerKey) => {
    const Disclaimer = disclaimers[disclaimerKey]
    const tree = renderer.create(<Disclaimer />).toJSON()
    expect(tree).toMatchSnapshot()
  })
})
