// validate send airtime form

var sendAirtime = function(){

		// load send airtime form and elements
		var form = document.forms["send-airtime-form"]
		var checkboxes = document.getElementsByClassName('checkbox');

		console.log(checkboxes)

		// check if checkbox is selected
		var checkBoxSelection = function(){

			var checkboxSelected = false

			for (var i = checkboxes.length - 1; i >= 0; i--) {
				
				var checkbox = checkboxes[i]
				if (checkbox.checked == true) { checkboxSelected = true};
			}

			return checkboxSelected
		};


		//prevent form submission if no form checkbox is selected
		// and display a message alerting the user to select a contact
		form.addEventListener('submit', function(submitEvent){

			var submitBtn = form['submit']
			var checkboxWasSelected = checkBoxSelection();

			if (checkboxWasSelected === false) {
				
				submitEvent.preventDefault();

				var displayErrorElement = document.getElementById('select-contact-alert');

				// add class to display error message container
				displayErrorElement.classList.add('display');
			}
		}); 


		// remove error message once a user clicks on a checkbox (to select contact )

		var removeErrorMessage = function(){

			// listen for any click actions on the form check boxes
			for (var i = checkboxes.length - 1; i >= 0; i--) {
				
				var checkbox = checkboxes[i]
				
				checkbox.addEventListener('click', function(event){

					if (checkbox.checked == true) {

						var displayErrorElement = document.getElementById('select-contact-alert');

						// remove class to hide error message
						displayErrorElement.classList.remove('display');
					}	
				});
			}
		}();	
}

// run function
sendAirtime();


