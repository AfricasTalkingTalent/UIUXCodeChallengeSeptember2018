from django.shortcuts import render, redirect
from django.contrib.auth import login, authenticate
from django.contrib.auth.forms import UserCreationForm
from django.contrib.auth.models import User
from django.views import generic, View
from .forms import CreateContactForm
from .models import Contact
import pickle
# selected contacts

contacts_selected = ''


# Create your views here.

class Home(generic.TemplateView):
	''' Displays the home page '''

	template_name = "airtime_app/home.html"



def signup(request):
	''' this view allows a user to sign up and logs them in '''

	if request.method == 'POST':
		form = UserCreationForm(request.POST)
		if form.is_valid():
			form.save()
			username = form.cleaned_data.get('username')
			raw_password = form.cleaned_data.get('password1')
			user = authenticate(username=username, password = raw_password)
			
			#login user 
			login(request, user)
			return redirect('home')

	else:
		form = UserCreationForm()

	return render(request, 'airtime_app/signup.html', {'form':form})


def listContacts(request):
	''' lists all user contacts '''

	current_user = request.user

	current_user_contacts = current_user.contact_set.all()

	return render(request, 'airtime_app/contacts.html', {'contacts': current_user_contacts})

class CreateContact(View):

	def post(self, request):

		form = CreateContactForm(request.POST)

		if form.is_valid():
			
			first_name = form.cleaned_data['first_name']
			last_name = form.cleaned_data['last_name']
			phone_number = form.cleaned_data['phone_number']
			owner = request.user

			# create contact
			contact = Contact.objects.create(
					first_name=first_name, 
					last_name=last_name,
					phone_number=phone_number,
				)

			# create contact - owner relationship
			request.user.contact_set.add(contact)

			return redirect('contacts')

		else:
			return render(request, 'airtime_app/create_contact.html', {'form':form})


	def get(self, request):

		form = CreateContactForm()

		return render(request, 'airtime_app/create_contact.html', {'form':form})


def selectToSend(request):
	''' displays a list of contacts for selection before sending airtime '''

	if request.method == 'GET':
		current_user = request.user
		current_user_contacts = current_user.contact_set.all()

		return render(request, 'airtime_app/selectToSend.html', {'contacts': current_user_contacts})			

	else:

		selected = request.POST.getlist('selected_contacts')
		
		with open('contacts_selected', 'wb') as file:
			pickle.dump(selected, file)

		return redirect('send_airtime')


def sendAirtime(request, user):

	
		


		


			
		





