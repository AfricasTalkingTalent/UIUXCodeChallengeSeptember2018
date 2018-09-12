from django.db import models
from django.contrib.auth.models import User
from phonenumber_field.modelfields import PhoneNumberField


# Create your models here.

class Contact(models.Model):
	''' 
	defines a contact entry in the phonebook 
	a contact is associated with a user
	'''

	first_name = models.CharField(max_length = 100)
	last_name = models.CharField(max_length = 100)
	phone_number = PhoneNumberField()

	owner = models.ManyToManyField(User)

	def __str__(self):
		person = self.first_name + ' ' + self.last_name
		return person





