from django.urls import path, include
from django.contrib.auth import views as auth_views
from . import views

urlpatterns = [
	
	# sign up for an account
    path('signup/', views.signup, name='signup'),

    path('accounts/', include('django.contrib.auth.urls')),
        
    # create a contact
    path('add_contact/', views.CreateContact.as_view(), name='add_contact'),

    # list of contacts
    path('contacts/', views.listContacts, name='contacts'),

    # select contacts to send airtime
    path('select_contacts/', views.selectToSend, name='select_contacts'),

    # send airtime
	path('send_airtime/<int:user>/', views.sendAirtime, name='send_airtime'),    

    # home page
    path('', views.Home.as_view(), name='home'),
]