# Expression Atlas Disclaimers

A collection of data reuse licences from projects that publish study datasets which have been reprocessed and curated
in [Expression Atlas](https://www.ebi.ac.uk/gxa).

## How to use
```
import disclaimers from 'expression-atlas-disclaimers'

// Object.keys[disclaimers] = [`blueprint`, `lauderdale` or `pcawg`]
const Disclaimer = disclaimers[`pcawg`]
...
<Disclaimer />

const DisclaimerOrCatPicture = ({project}) => {
  const Disclaimer = disclaimers[project]

  return (
    Disclaimer ?
      <Disclaimer /> :
      <img src={`http://thecatapi.com/api/images/get?format=src&type=gif`} />
  )
}
```
