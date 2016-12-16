<script src="${pageContext.request.contextPath}/resources/js-bundles/vendorCommonsRBW3D.bundle.js"></script>
<script src="${pageContext.request.contextPath}/resources/js-bundles/reactBioWeb3D.bundle.js"></script>

    <div class="small-4 columns">
        <label>Cell type:
            <select id="cell-type-select" name="Cell type" onchange="renderOnSelectChange(this)">
                <option value="">Raw data</option>
                <option value="final_clustering">Final tissue clustering</option>
                <option value="initial_clustering">Initial tissue clustering</option>
                <option value="C1x30_1L">C1x30_1L</option>
                <option value="C1x41_1L">C1x41_1L</option>
                <option value="C1x47_1L">C1x47_1L</option>
                <option value="C1x50_1M">C1x50_1M</option>
                <option value="C1x59_1M">C1x59_1M</option>
                <option value="C2x01_1Sb">C2x01_1Sb</option>
                <option value="C2x02_1S">C2x02_1S</option>
                <option value="C2x04_1S">C2x04_1S</option>
                <option value="C2x06_1L">C2x06_1L</option>
                <option value="C2x07_1S">C2x07_1S</option>
                <option value="C2x08_1Sd">C2x08_1Sd</option>
                <option value="C2x09_1L">C2x09_1L</option>
                <option value="C2x10_1Sb">C2x10_1Sb</option>
                <option value="C2x13_1M">C2x13_1M</option>
                <option value="C2x14_1Sbd">C2x14_1Sbd</option>
                <option value="C2x15_1Sb">C2x15_1Sb</option>
                <option value="C2x18_1Sb">C2x18_1Sb</option>
                <option value="C2x20_1S">C2x20_1S</option>
                <option value="C2x24_1S">C2x24_1S</option>
                <option value="C2x28_1M">C2x28_1M</option>
                <option value="C2x29_1S">C2x29_1S</option>
                <option value="C2x30_1L">C2x30_1L</option>
                <option value="C2x31_1S">C2x31_1S</option>
                <option value="C2x32_1L">C2x32_1L</option>
                <option value="C2x34_1L">C2x34_1L</option>
                <option value="C2x35_1L">C2x35_1L</option>
                <option value="C2x36_1Ld">C2x36_1Ld</option>
                <option value="C2x38_1L">C2x38_1L</option>
                <option value="C2x3_1L">C2x3_1L</option>
                <option value="C2x40_1S">C2x40_1S</option>
                <option value="C2x41_1L">C2x41_1L</option>
                <option value="C2x42_1Md">C2x42_1Md</option>
                <option value="C2x44_1M">C2x44_1M</option>
                <option value="C2x46_1L">C2x46_1L</option>
                <option value="C2x49_1Lb">C2x49_1Lb</option>
                <option value="C2x50_1Sbd">C2x50_1Sbd</option>
                <option value="C2x51_1Mb">C2x51_1Mb</option>
                <option value="C2x52_1L">C2x52_1L</option>
                <option value="C2x53_1M">C2x53_1M</option>
                <option value="C2x55_1L">C2x55_1L</option>
                <option value="C2x58_1S">C2x58_1S</option>
                <option value="C2x61_1Sb">C2x61_1Sb</option>
                <option value="C2x62_1Sb">C2x62_1Sb</option>
                <option value="C2x65_1Sb">C2x65_1Sb</option>
                <option value="C2x67_1S">C2x67_1S</option>
                <option value="C2x72_1L">C2x72_1L</option>
                <option value="C2x74_1M">C2x74_1M</option>
                <option value="C2x80_1Mb">C2x80_1Mb</option>
                <option value="C2x83_1Md">C2x83_1Md</option>
                <option value="C2x85_1L">C2x85_1L</option>
                <option value="C2x86_1L">C2x86_1L</option>
                <option value="C2x90_1L">C2x90_1L</option>
                <option value="C2x91_1L">C2x91_1L</option>
                <option value="C2x92_1Sb">C2x92_1Sb</option>
                <option value="C2x95_1Sb">C2x95_1Sb</option>
                <option value="C2x96_1Sb">C2x96_1Sb</option>
                <option value="C4x13.1M">C4x13.1M</option>
                <option value="C4x19.1S">C4x19.1S</option>
                <option value="C4x20.1M">C4x20.1M</option>
                <option value="C4x24.1S">C4x24.1S</option>
                <option value="C4x25.1M">C4x25.1M</option>
                <option value="C4x26.1M">C4x26.1M</option>
                <option value="C4x30.1b">C4x30.1b</option>
                <option value="C4x34.1S">C4x34.1S</option>
                <option value="C4x37.1M">C4x37.1M</option>
                <option value="C4x38.1S">C4x38.1S</option>
                <option value="C4x39.1M">C4x39.1M</option>
                <option value="C4x40.1L">C4x40.1L</option>
                <option value="C4x41.1L">C4x41.1L</option>
                <option value="C4x44.1Sd">C4x44.1Sd</option>
                <option value="C4x48.1L">C4x48.1L</option>
                <option value="C4x56.1M">C4x56.1M</option>
                <option value="C4x57.1M">C4x57.1M</option>
                <option value="C4x58.1M">C4x58.1M</option>
                <option value="C4x59.1S">C4x59.1S</option>
                <option value="C4x61.1S">C4x61.1S</option>
                <option value="C4x64.1M">C4x64.1M</option>
                <option value="C4x66.1M">C4x66.1M</option>
                <option value="C4x71.1M">C4x71.1M</option>
                <option value="C4x72.1S">C4x72.1S</option>
                <option value="C4x86.1M">C4x86.1M</option>
                <option value="C4x87.1M">C4x87.1M</option>
                <option value="C4x94.1M">C4x94.1M</option>
                <option value="C5x01.1M">C5x01.1M</option>
                <option value="C5x02.1M">C5x02.1M</option>
                <option value="C5x03.1L">C5x03.1L</option>
                <option value="C5x05.1L">C5x05.1L</option>
                <option value="C5x07.1L">C5x07.1L</option>
                <option value="C5x09.1M">C5x09.1M</option>
                <option value="C5x10.1M">C5x10.1M</option>
                <option value="C5x12.1VS">C5x12.1VS</option>
                <option value="C5x14.1M">C5x14.1M</option>
                <option value="C5x16.1S">C5x16.1S</option>
                <option value="C5x17.1M">C5x17.1M</option>
                <option value="C5x18.1S">C5x18.1S</option>
                <option value="C5x21.1M">C5x21.1M</option>
                <option value="C5x22.1S">C5x22.1S</option>
                <option value="C5x23.1L">C5x23.1L</option>
                <option value="C5x24.1M">C5x24.1M</option>
                <option value="C5x26.1M">C5x26.1M</option>
                <option value="C5x27.1M">C5x27.1M</option>
                <option value="C5x28.1M">C5x28.1M</option>
                <option value="C5x29.1L">C5x29.1L</option>
                <option value="C5x30.1S">C5x30.1S</option>
                <option value="C5x31.1M">C5x31.1M</option>
                <option value="C5x32.1M">C5x32.1M</option>
                <option value="C5x33.1S">C5x33.1S</option>
                <option value="C5x34.1M">C5x34.1M</option>
                <option value="C5x35.1M">C5x35.1M</option>
                <option value="C5x36.1VS">C5x36.1VS</option>
                <option value="C5x38.1S">C5x38.1S</option>
                <option value="C5x39.1M">C5x39.1M</option>
                <option value="C5x40.1S">C5x40.1S</option>
                <option value="C5x41.1L">C5x41.1L</option>
                <option value="C5x42.1M">C5x42.1M</option>
                <option value="C5x43.1M">C5x43.1M</option>
                <option value="C5x46.1M">C5x46.1M</option>
                <option value="C5x47.1VS">C5x47.1VS</option>
                <option value="C5x50.1Lb">C5x50.1Lb</option>
                <option value="C5x51.1M">C5x51.1M</option>
                <option value="C5x53.1S">C5x53.1S</option>
                <option value="C5x56.1Md">C5x56.1Md</option>
                <option value="C5x57.1M">C5x57.1M</option>
                <option value="C5x58.1S">C5x58.1S</option>
                <option value="C5x59.1L">C5x59.1L</option>
                <option value="C5x63.1M">C5x63.1M</option>
                <option value="C5x64.1S">C5x64.1S</option>
                <option value="C5x66.1S">C5x66.1S</option>
                <option value="C5x67.1S">C5x67.1S</option>
                <option value="C5x71.1Md">C5x71.1Md</option>
                <option value="C5x72.1Md">C5x72.1Md</option>
                <option value="C5x76.1S">C5x76.1S</option>
                <option value="C5x77.1M">C5x77.1M</option>
                <option value="C5x78.1M">C5x78.1M</option>
                <option value="C5x79.1M">C5x79.1M</option>
                <option value="C5x82.1M">C5x82.1M</option>
                <option value="C5x90.1L">C5x90.1L</option>
                <option value="C5x93.1S">C5x93.1S</option>
                <option value="C5x96.1S">C5x96.1S</option>
            </select>
        </label>
    </div>

    <div class="small-12 columns">
        <div id="react-container-experiment-page"></div>
    </div>


<script type="text/javascript">
    // This should be also be set in the model attributes from the controller
    reactBioWeb3D.render({
        baseUrl: '${pageContext.request.contextPath}',
        datasetFilePath: '/gxa_sc/expdata/${experimentAccession}/spatial/32203_points/dataset.json',
        mountNode: document.getElementById('react-container-experiment-page')
    });


    function renderOnSelectChange(obj) {
        if (obj.value) {

            if (obj.value === 'initial_clustering' || obj.value === 'final_clustering') {
                reactBioWeb3D.render({
                    baseUrl: '${pageContext.request.contextPath}',
                    datasetFilePath: '/gxa_sc/expdata/${experimentAccession}/spatial/34343_points/dataset.json',
                    informationLayerFilePath: '/gxa_sc/expdata/${experimentAccession}/spatial/34343_points/' + obj.value + '.json',
                    colourScheme: 'rainbow',
                    mountNode: document.getElementById('react-container-experiment-page')
                });
            } else {
                reactBioWeb3D.render({
                    baseUrl: '${pageContext.request.contextPath}',
                    datasetFilePath: '/gxa_sc/expdata/${experimentAccession}/spatial/32203_points/dataset.json',
                    informationLayerFilePath: '/gxa_sc/expdata/${experimentAccession}/spatial/32203_points/' + obj.value + '.json',
                    colourScheme: 'ranked',
                    mountNode: document.getElementById('react-container-experiment-page')
                });

            }

        } else {
            reactBioWeb3D.render({
                baseUrl: '${pageContext.request.contextPath}',
                datasetFilePath: '/gxa_sc/expdata/${experimentAccession}/spatial/32203_points/dataset.json',
                informationLayerFilePath: '',
                colourScheme: 'ranked',
                mountNode: document.getElementById('react-container-experiment-page')
            });
        }

    }
</script>
