from django.forms import ModelForm
from .models import Contact

class CreateContactForm(ModelForm):
	''' creates a new contact '''

	class Meta:
		model = Contact
		fields = ['first_name', 'last_name', 'phone_number']
		labels = {
			'first_name': 'First Name',
			'last_name': 'Last Name',
			'phone_number': 'Phone Number'
		}

		help_texts = {
			'phone_number': 'enter phone number in the format +2547XXXXXXXX'
		}


