<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- Type of tissue -->
<div class="large-6 columns">
    <h3>What type of tissue?</h3>

    <div class="image-container-nof " title="What type of tissue?" style="margin:2.2em 0;/*temp to keep aligned boxes*/">
        <div class="flex-anato tissue_type_flate">
            <div class="tissue_bone_m" data-tooltip title="bone marrow tissue"></div>
            <div class="tissue_brain" data-tooltip title="brain tissue"></div>
            <div class="tissue_kidneys" data-tooltip title="kidneys tissue"></div>
            <div class="tissue_pancreas" data-tooltip title="pancreas tissue"></div>
        </div>
    </div>

    <label>Where are your cells originate from?
        <select>
            <option value="hotdog">None</option>
            <option value="hotdog">Bone marrow</option>
            <option value="husker">Brain</option>
            <option value="husker">Kidneys</option>
            <option value="starbuck">Pancreas</option>
        </select>
    </label>

</div>