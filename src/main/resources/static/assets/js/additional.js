$(document).ready(function(){
	$('.dropdown-menu a.dropdown-toggle').on('click', function(e) {
	  if (!$(this).next().hasClass('show')) {
	    $(this).parents('.dropdown-menu').first().find('.show').removeClass("show");
	  }
	  var $subMenu = $(this).next(".dropdown-menu");
	  $subMenu.toggleClass('show');


	  $(this).parents('li.nav-item.dropdown.show').on('hidden.bs.dropdown', function(e) {
	    $('.dropdown-submenu .show').removeClass("show");
	  });


	  return false;
	});

	$('.search-box input').on('focus', function(){
	  $('#search-suggestion').addClass('show');
	})
	
	$('.search-box input').on('blur', function(){
	  $('#search-suggestion').removeClass('show');
	})

	$('.owl-carousel').owlCarousel({
	    loop:true,
	    margin:10,
	    nav:true,
	    items: 1,
	    navText: ['Олдинги','Кейинги'],
	    autoHeight: false,
	    autoplay:true,
	    autoplayTimeout: 4000,
	    autoplayHoverPause:true
	})
})