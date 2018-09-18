 $(document).ready(function(){

 		// add placeholder text to forms
        $("#id_username").attr('placeholder', 'Enter your FIRST name');
        $("#id_password").attr('placeholder', 'Enter your password');
        $("#id_password1").attr('placeholder', 'Enter password');
        $("#id_password2").attr('placeholder', 'Enter password as before');

        // Menu
        var offCanvass = function() {
		$('body').on('click', '#nav-toggle-btn', function(){

			$('#sideMenu').toggleClass('menu-asleep');

			});
		};

		// Click outside of side-menu
		var mobileMenuOutsideClick = function() {
			$(document).click(function (e) {
		    var container = $("#nav-toggle-btn, #sideMenu");
		    if (!container.is(e.target) && container.has(e.target).length === 0) {
		    	if ( $('#sideMenu').hasClass('menu-asleep') ) {
		    		$('#sideMenu').removeClass('menu-asleep');
		    	}
		    };
			
			});
		};

	$(function(){
		offCanvass();
		mobileMenuOutsideClick();
	});
		
});