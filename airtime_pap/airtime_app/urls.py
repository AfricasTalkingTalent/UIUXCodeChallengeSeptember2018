from django.urls import path, include
from django.contrib.auth import views as auth_views
from django.contrib.auth.decorators import login_required
from . import views

urlpatterns = [
	
	# sign up for an account
    path('signup/', views.signup, name='signup'),

    # use the built in django authentication system to provide login/logout urls
    path('accounts/', include('django.contrib.auth.urls')),
        
    # create a contact - user must be logged in
    path('add_contact/', login_required(views.CreateContact.as_view()), name='add_contact'),

    # list of contacts
    path('contacts/', views.listContacts, name='contacts'),

    # send airtime - user should be logged in
	path('send_airtime/', login_required(views.SendAirtime.as_view()), name='send_airtime'),    

    # home page - requires user to be logged in
    path('', login_required(views.Home.as_view()), name='home'),
]


