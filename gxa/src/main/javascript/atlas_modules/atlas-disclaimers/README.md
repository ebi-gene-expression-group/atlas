# Expression Atlas Disclaimers

### Use
```
import Disclaimers from './Disclaimers.js'

const renderDisclaimer = ({title, content}) => (
    <div>
        {title}
        {content}
    </div>
)
const renderDisclaimerOrCatPicture =(disclaimer) => (
    disclaimer && Disclaimers[disclaimer]
    ? renderDisclaimer(Disclaimers[disclaimer])
    : <img src="http://thecatapi.com/api/images/get?format=src&type=gif">
)
```
