// Initialize Firebase
	let config = {
		apiKey: "AIzaSyBBzCLmcWTQ0aWjcza5XER1lXdd0PaJpjU",
		authDomain: "uiuxcodechallengeseptemb-2a792.firebaseapp.com",
		databaseURL: "https://uiuxcodechallengeseptemb-2a792.firebaseio.com",
		projectId: "uiuxcodechallengeseptemb-2a792",
		storageBucket: "uiuxcodechallengeseptemb-2a792.appspot.com",
		messagingSenderId: "768281040126"
	};
	firebase.initializeApp(config);
let addContactBtn = document.getElementById('createContact');
addContactBtn.addEventListener('click',()=>{
    let fname = document.getElementById('fname').value;
    let lname = document.getElementById('lname').value;
    let mobile = document.getElementById('mnumber').value;

    // Initialize Cloud Firestore through Firebase
    var db = firebase.firestore();

    // Disable deprecated features
    db.settings({
    timestampsInSnapshots: true
    });

    // Add a new document in collection "cities"
    db.collection("contacts").doc(`${mobile}`).set({
        fname: fname,
        lname: lname,
        mobile: mobile
    })
    .then(function() {
        console.log("Document successfully written!");
    })
    .catch(function(error) {
        console.error("Error writing document: ", error);
    });

    console.log(fname + lname + mobile);
})