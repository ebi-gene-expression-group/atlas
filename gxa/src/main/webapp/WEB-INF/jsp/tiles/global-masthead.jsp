<%@ page contentType="text/html;charset=UTF-8" %>

<header id="masthead-black-bar" class="clearfix masthead-black-bar expanded">
</header>

<script>
  // Hack that solves https://github.com/ebiwd/EBI-Framework/pull/139 (I got tired of waiting)
  window.addEventListener('load', function(event) {
    $('#masthead-black-bar nav')[2].className='row expanded';
  });
</script>
