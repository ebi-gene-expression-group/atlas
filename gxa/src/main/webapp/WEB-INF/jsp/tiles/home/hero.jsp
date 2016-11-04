<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="row">
    <div class="columns small-2" data-tooltip title="This is a Gene Expression Heatmap showing high or low expression of the gene">
        <img src="${pageContext.request.contextPath}/resources/images/foundation/heatmap-hero.png">
    </div>

    <div class="columns small-10">
        <h3>Analysing gene expression in tissues across species under different biological conditions</h3>
        <p>
            The Expression Atlas provides information on gene expression patterns under different biological
            conditions. Gene expression data is re-analysed in-house to detect genes showing interesting baseline
            and differential expression patterns.
        </p>
        <!-- SP: customise animation  http://foundation.zurb.com/sites/docs/motion-ui.html-->
        <div id="description-more" data-toggler=".is-visible" style="display:none;">
            <p>
                Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean urna orci, porta sit amet luctus
                non, efficitur eu tortor. Sed faucibus justo lectus, a condimentum tellus posuere dictum. Donec leo
                odio, luctus a dictum vitae, elementum in dui. Nunc dignissim hendrerit magna ac placerat. Sed
                mollis erat vitae neque fermentum, varius interdum purus venenatis. Aliquam neque quam, commodo
                quis posuere at, viverra quis diam. Curabitur quis tempus lorem. Praesent nunc enim, mattis vitae
                nibh eget, varius fermentum erat. Donec porta tempor ante vitae porttitor. Aliquam eleifend nisi
                non leo commodo, vel venenatis turpis mollis. In ac lacinia turpis, sit amet egestas leo. Donec eu
                dolor a lorem efficitur consectetur ac a tortor. Sed maximus, lectus vitae egestas sollicitudin,
                ligula sapien luctus enim, id tempor libero libero sit amet turpis. Pellentesque maximus, urna et
                tristique imperdiet, nibh velit aliquet risus, vel tempus dui metus cursus libero.
            </p>
        </div>

        <!-- Keep grouping otherwise button "is-visible" sets display:block to the less button -->
        <div class="button-group float-left">
            <a class="button" id="show-more" data-toggle="description-more show-more show-less" data-toggler=".hide" >Read more about Expression Atlas</a>
            <a class="button" id="show-less" data-toggle="description-more show-more show-less" data-toggler=".is-visible" style="display:none;">Show less about Expression Atlas</a>
        </div>
    </div>
</div>
