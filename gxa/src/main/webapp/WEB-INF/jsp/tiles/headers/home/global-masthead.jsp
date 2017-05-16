<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div id="global-masthead" class="clearfix masthead-black-bar">
    <!--This has to be one line and no newline characters-->
    <a href="https://www.ebi.ac.uk/" title="Go to the EMBL-EBI homepage"><span class="ebi-logo"></span></a>

    <nav>
        <div class="row">
            <ul id="global-nav" class="menu">
                <!-- set active class as appropriate -->
                <li id="home-mobile" class=""><a href="https://www.ebi.ac.uk/"></a></li>
                <li id="home"><a href="https://www.ebi.ac.uk/"><i class="icon icon-generic" data-icon="H"></i> EMBL-EBI</a></li>
                <li id="services"  class="active"><a href="https://www.ebi.ac.uk/services"><i class="icon icon-generic" data-icon="("></i> Services</a></li>
                <li id="research"><a href="https://www.ebi.ac.uk/research"><i class="icon icon-generic" data-icon=")"></i> Research</a></li>
                <li id="training"><a href="https://www.ebi.ac.uk/training"><i class="icon icon-generic" data-icon="t"></i> Training</a></li>
                <li id="about"><a href="https://www.ebi.ac.uk/about"><i class="icon icon-generic" data-icon="i"></i> About us</a></li>
                <li id="search">
                    <a href="#" data-toggle="search-global-dropdown" aria-controls="search-global-dropdown" data-is-focus="false" data-yeti-box="search-global-dropdown" aria-haspopup="true" aria-expanded="false"><i class="icon icon-functional" data-icon="1"></i> <span class="show-for-small-only">Search</span></a>
                    <div id="search-global-dropdown" class="dropdown-pane" data-dropdown="hkf5pm-dropdown" data-options="closeOnClick:true;" aria-hidden="true" data-yeti-box="search-global-dropdown" data-resize="search-global-dropdown" aria-labelledby="l4p782-dd-anchor">
                        <form id="global-search" name="global-search" action="/ebisearch/search.ebi" method="GET">
                            <fieldset>
                                <div class="input-group">
                                    <input name="query" id="global-searchbox" class="input-group-field" placeholder="Search all of EMBL-EBI" type="text">
                                    <div class="input-group-button">
                                        <input name="submit" value="Search" class="button" type="submit">
                                        <input name="db" value="allebi" checked="checked" type="hidden">
                                        <input name="requestFrom" value="global-masthead" checked="checked" type="hidden">
                                    </div>
                                </div>
                            </fieldset>
                        </form>
                    </div>
                </li>
                <li class="float-right show-for-medium embl-selector">
                    <button class="button float-right" type="button" data-toggle="embl-dropdown" aria-controls="embl-dropdown" data-is-focus="false" data-yeti-box="embl-dropdown" aria-haspopup="true" aria-expanded="false">Hinxton</button>
                    <!-- The dropdown menu will be programatically added by script.js -->
                </li>
            </ul>
        </div>
    </nav>

    <div>
        <div id="embl-dropdown" class="dropdown-pane bottom" data-dropdown="m4g0o0-dropdown" aria-hidden="true" data-yeti-box="embl-dropdown" data-resize="embl-dropdown" aria-labelledby="mhd9fi-dd-anchor">
            <p>EMBL-EBI in Hinxton is one of five EMBL locations across europe.<br> <a href="#" class="small readmore">More about EMBL-EBI</a></p>
            <h6>Connect to another EMBL location</h6>
            <div class="small-collapse small-up-2 padding-bottom-large clearfix">
                <div class="column padding-bottom-medium"><a href="#" class="">Grenoble</a>
                    <div class="small">Structural Biology</div>
                </div>
                <div class="column padding-bottom-medium"><a href="#" class="">Hamburg</a>
                    <div class="small">Structural Biology</div>
                </div>
                <div class="column padding-bottom-medium"><a href="#" class="">Heidelberg</a>
                    <div class="small">Main Laboratory</div>
                </div>
                <div class="column padding-bottom-medium"><a href="#" class="">Monterotondo</a>
                    <div class="small">Mouse Biology</div>
                </div>
            </div>
            <p><a href="#" class="button readmore">Or learn more about EMBL</a></p>
        </div>
    </div>
</div>