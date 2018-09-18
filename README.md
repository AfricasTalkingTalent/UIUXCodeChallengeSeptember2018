# UI/UX Code Challenge

## Task
In this code challenge you will be required to make an application that:
1. Implements authentication (User can login and logout)
2. The user can create a list of contacts and saves it to a DB of your choice
3. The user can send airtime to the contacts/phone numbers using the Africa's Talking Airtime API

##### You are free to use any language or framework of choice

## Resources
- [API Reference](http://docs.africastalking.com/)
- [Helper libraries](https://github.com/AfricasTalkingLtd)
- [Sandbox](https://account.africastalking.com/apps/sandbox)
- [Simulator](https://simulator.africastalking.com:1517/)


## Tips
###### After signup, navigate to the sandbox(big orange button) which you’ll use to build your test app. Your API key is in settings. A link to the simulator is also in the sandbox.
###### You are free to build using any language, using the available SDKs or the API directly. We advise building a clean simple form that allows you to send airtime to multiple phone numbers in one request(form input accepting multiple entries)
###### The documentation is your friend. Carefully go through it, learn how the API works
###### Extra points for good user experience, form validation, single page applications




# [SOLUTION] AIRTIME PAP APP DOCUMENTATION

## Installation instructions 

> Clone or Download the source files at the [project repository](https://github.com/zlyxero/UIUXCodeChallengeSeptember2018)

```sh 
$ git clone https://github.com/zlyxero/UIUXCodeChallengeSeptember2018
```

> On the terminal, open the UIUXCodeChallengeSeptember2018 folder

```sh 
$ cd UIUXCodeChallengeSeptember2018
```

> Switch to the 254711276275 branch

```sh 
$ git checkout 254711276275
```

> Open the airtime_pap folder

```sh 
$ cd airtime_pap 
```

> See the files inside 

```sh 
$ ls
  airtime_app  
  airtime_project  
  manage.py  
  Pipfile  
  Pipfile.lock
```

> Create a virtual environment and install the application dependencies

```sh 
$ pipenv install
```

> Activate the virtual environment

```sh 
$ pipenv shell
```

> Run the migrate command to set up the database

```sh 
$ python manage.py migrate
```

> Run the development server

```sh 
$ python manage.py runserver
```

> View the app in your browser at [local host](http://127.0.0.1:8000/)


## Navigating the app

1. Create an account

2. Add a contact

3. In a new browser tab, Open the Africa's Talking [Sandbox](https://account.africastalking.com/apps/sandbox)

 > Register your contact and click on the launch button

 > Open the airtime menu option 

4. Switch back to the app tab on your browser

5. On the app menu, select send airtime

6. select a checkbox next to your contact to send airtime. Enter amount and click on the send
   airtime button

7. Open the Africa's Talking Sandbox tab in your browser to see the confirmation message



