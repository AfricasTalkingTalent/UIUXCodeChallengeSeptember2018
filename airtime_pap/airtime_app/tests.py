from django.test import TestCase
from .models import Contact
# Create your tests here.

class ContactModelTest(TestCase):
	''' Tests for the Contact model '''

	def setUp(self):

		Contact.objects.create(
				'first_name' = 'brenda',
				'last_name' = 'moraa',
				'phone_number' = '+254702740015'
			)


	def test_first_name_content(self):
		
		contact = Contact.objects.get(id=1)
		expected_name = contact.first_name

		self.assertEqual(expected_name, 'brenda')

	def test_phone_number_content(self):

		contact = Contact.objects.get(id=1)
		expected_number = Contact.phone_number

		self.assertEqual(expected_number, '+254702740015')	

