<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<section>
    <span class="anchor" id="first" data-magellan-target="first">&nbsp;</span>
    <div class="row">
        <div class="large-12 columns">

            <div class="image-container" data-tooltip  title="Single-cell expression" >
                <div class="cell_3d_intro"></div>
            </div>

            <h3>Analysing gene expression for single cells experiments</h3>
            <p>The cell is a natural unit of biology, whose type and state can vary according to external influences or to internal processes.
                In multicellular organisms, all cells are derived from a single zygote which, through regulated programmes of
                proliferation and differentiation, generates all of the diverse cell types that populate the organism.
                Dysregulation of these programmes in single ‘renegade’ cells can lead to diseases such as cancers, neurological
                disorders and developmental disorders .
            </p>

            <blockquote class="lead quote industry-color">“You could think of DNA sequencing as a fruit smoothie.  Traditional sequencing technology is a bit like taking a sip of the smoothie, then trying to guess what the ingredients are. <b>Single-cell genomics</b> now lets us study the ingredients individually, so we get direct insight into the constituent parts. Extrapolating, this means that single-cell sequencing allows researchers to individually look at thousands of genes at any given time.”
                <cite>Dr John Marioni, Group Leader at EMBL-EBI and the Cancer Research UK–Cambridge Institute </cite>
            </blockquote>

            <!-- Customise animation  http://foundation.zurb.com/sites/docs/motion-ui.html-->
            <div id="description-more"  data-toggler=".is-visible" data-animate="fade-in fade-out" style="display:none;">

                <p> Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean urna orci, porta sit amet luctus non, efficitur eu tortor. Sed faucibus justo lectus, a condimentum tellus posuere dictum. Donec leo odio, luctus a dictum vitae, elementum in dui. Nunc dignissim hendrerit magna ac placerat. Sed mollis erat vitae neque fermentum, varius interdum purus venenatis. Aliquam neque quam, commodo quis posuere at, viverra quis diam. Curabitur quis tempus lorem. Praesent nunc enim, mattis vitae nibh eget, varius fermentum erat. Donec porta tempor ante vitae porttitor. Aliquam eleifend nisi non leo commodo, vel venenatis turpis mollis. In ac lacinia turpis, sit amet egestas leo. Donec eu dolor a lorem efficitur consectetur ac a tortor. Sed maximus, lectus vitae egestas sollicitudin, ligula sapien luctus enim, id tempor libero libero sit amet turpis. Pellentesque maximus, urna et tristique imperdiet, nibh velit aliquet risus, vel tempus dui metus cursus libero.</p>
            </div>

            <!-- Keep grouping otherwise button "show less" expand trangely-->
            <div class="button-group float-left">
                <a class="button" id="show-more" data-toggle="description-more show-more show-less" data-toggler=".hide" >Read more</a></p>
                <a class="button" id="show-less"  data-toggle="description-more show-more show-less" data-toggler=".is-visible" style="display:none;">Show less</a>
            </div>
        </div>
    </div>

</section>
