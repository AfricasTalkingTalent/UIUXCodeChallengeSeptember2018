from django.forms import ModelForm
from django import forms
from .models import Contact


class CreateContactForm(ModelForm):
	''' creates a new contact '''

	class Meta:
		model = Contact
		fields = ['first_name', 'last_name', 'phone_number']
		labels = {
			'first_name': 'First Name',
			'last_name': 'Last Name',
			'phone_number': 'Phone'
		}

		widgets = {
            'first_name': forms.TextInput(attrs={'placeholder': 'first name'}),
            'last_name': forms.TextInput(attrs={'placeholder': 'last name'}),
            'phone_number': forms.TextInput(attrs={'placeholder': 'phone number e.g +2547xxx12345'}),
        }


