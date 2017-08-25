import React from 'react'
import renderer from 'react-test-renderer'
import {shallow, mount, render} from 'enzyme'

import Disclaimers from '../src/index.js'

const MyComponent = () => Disclaimers['zebrafish'].content

describe(`Zebrafish body`, () => {
  test(`matches snapshot`, () => {
    const tree = renderer.create(<MyComponent />).toJSON()
    expect(tree).toMatchSnapshot()
  })
})
