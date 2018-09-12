let logoutBtn = document.getElementById('logoutBtn');
logoutBtn.addEventListener('click', ()=>{
        firebase.auth().signOut().then(function() {
        window.location = 'signin.html';
        }).catch(()=>console.log("An error occured"));
})