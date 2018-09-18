from django.shortcuts import render, redirect
from django.contrib.auth import login, authenticate
from django.contrib.auth.forms import UserCreationForm
from django.contrib.auth.models import User
from django.contrib.auth.decorators import login_required
from django.views import generic, View
from .forms import CreateContactForm
from .models import Contact
import africastalking



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


@login_required
def listContacts(request):
	''' orders contacts by name and lists them'''

	# current logged in user
	current_user = request.user
	has_contacts = False
	current_user_contacts = ''
	# check if user has contacts
	if current_user.contact_set.all():

		has_contacts = True

		# get an ordered list of the logged in user contacts
		current_user_contacts = current_user.contact_set.all().order_by('first_name')

	return render(request, 'airtime_app/contacts.html', {'contacts': current_user_contacts, 'has_contacts': has_contacts})

class CreateContact(View):
	''' this view provides functionality that allows the creation of new users. '''
	
	def post(self, request):

		form = CreateContactForm(request.POST)

		if form.is_valid():
			
			first_name = form.cleaned_data['first_name']
			last_name = form.cleaned_data['last_name']
			phone_number = form.cleaned_data['phone_number']

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



class SendAirtime(View):

	def get(self, request):

		# current logged in user
		current_user = request.user
		has_contacts = False
		current_user_contacts = ''
		
		# check if user has contacts
		if current_user.contact_set.all():

			has_contacts = True

			# get an ordered list of the logged in user contacts
			current_user_contacts = current_user.contact_set.all().order_by('first_name')

		return render(request, 'airtime_app/send_airtime.html', {'user_contacts_list': current_user_contacts, 'has_contacts': has_contacts} )

	def post(self, request):
		''' 
		Initializes the AT sdk and sends airtime to recipient accounts selected by user.

		The value from post data (checked boxes) maps to contact id's in the databases.
		The id is used to obtain the recipient phone number used in the Africa's talking sdk
		Phone number is in the format '+2547xxxxxxxx'. Using the country code, we determine how
		to format amount string required in the AT sdk
		'''

		# get post data - airtime amount
		airtime_amount = request.POST.get('airtime_amount')


		
		# get post data - recipient id's 
		recipient_ids_list = request.POST.getlist('recipient_checkbox')

		# save recipient's phone number and airtime amount in a list
		recipient_data = []

		# loop through recipient id's, fetch the recipient object and obtain phone number
		for recipient_id in recipient_ids_list:

			recipient = Contact.objects.get(id=int(recipient_id))

			# convert PhoneNumber to string!
			recipient_phone_number = str(recipient.phone_number) 
			
			airtime_amount_str = 'KES ' + airtime_amount					 

			recipient_data.append(
					{'phoneNumber': recipient_phone_number, 'amount': airtime_amount_str}
				)


		# Initialize SDK and send airtime
		username = "sandbox"    # 'sandbox' for development in the test environment
		api_key = "075f0814105492672d70e47a098b6e2952da5712953270fad4a37d769e82de9c"      
		
		africastalking.initialize(username, api_key)	
		airtime = africastalking.Airtime
		
		response = airtime.send(recipients = recipient_data)

		return render(request, 'airtime_app/send_airtime_success.html', {'resp': response})









	
		


		


			
		





