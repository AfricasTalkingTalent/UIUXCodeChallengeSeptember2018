class App {
  constructor() {
<<<<<<< HEAD
    this.meals = [];
    document.getElementById("form-entry").addEventListener("submit", event => {
      event.preventDefault();
      this.addMeal({
        title: document.getElementById("title").value,
        calories: parseInt(document.getElementById("calories").value)
      });
    });
    document.getElementById("sign-out").addEventListener("click", event => {
      event.preventDefault();
      this.signIn.session.close(err => {
        if (err) {
          return alert(`Error: ${err}`);
        }
        this.showSignIn();
      });
    });
    this.signIn = new OktaSignIn({
      baseUrl: "https://dev-982217.oktapreview.com",
      clientId: "0oag8v434ph3zXkrz0h7",
      redirectUri: "http://localhost:8080",
      authParams: {
        issuer: "default",
        responseType: ["id_token", "token"]
      },
      logo: "//placehold.it/200x40?text=Your+Logo",
      i18n: {
        en: {
          "primaryauth.title": "Sign in to Calorie Tracker"
        }
      }
    });
  }
  async init() {
    this.signIn.session.get(async res => {
      if (res.status === "ACTIVE") {
        this.showMeals();
      } else {
        this.showSignIn();
      }
    });
  }
  async showMeals() {
    this.meals = await this.request("GET", "/meals");
=======
    this.contact = [];
    document.getElementById("form-entry").addEventListener("submit", event => {
      event.preventDefault();
      this.addcontact({
        id: Date.now(), // faux id
        name: document.getElementById("name").value,
        phone: parseInt(document.getElementById("phone").value)
      });
    });
  }
  init() {
    this.contact = [
      { id: 1, name: "Joel Machango", phone: 254714478123 },
      { id: 2, name: "Joel Machango", phone: 254714478123 },
      { id: 3, name: "Joel Machango", phone: 254714478123 }
    ];
>>>>>>> 1bc0f22a0ee8559ad4b2316efa035d8b4a50fb68
    this.render();
    document.getElementById("sign-out").style.display = "inline-block";
    document.getElementById("meals-container").style.display = "block";
    this.signIn.remove();
  }
  showSignIn() {
    document.getElementById("sign-out").style.display = "none";
    document.getElementById("meals-container").style.display = "none";
    this.signIn.renderEl({ el: "#widget-container" }, res => {
      if (res.status === "SUCCESS") {
        this.signIn.tokenManager.add("id_token", res[0]);
        this.signIn.tokenManager.add("access_token", res[1]);
        this.showMeals();
      }
    });
  }
  request(method, url, data = null) {
    return new Promise((resolve, reject) => {
      let xhr = new XMLHttpRequest();
      xhr.open(method, url, true);
      xhr.setRequestHeader("Content-Type", "application/json");
      const accessToken = this.signIn.tokenManager.get("access_token");
      if (accessToken) {
        xhr.setRequestHeader(
          "Authorization",
          `Bearer ${accessToken.accessToken}`
        );
      }
      xhr.onload = () => {
        if (xhr.status === 200) {
          return resolve(JSON.parse(xhr.responseText || "{}"));
        } else {
          return reject(new Error(`Request failed with status ${xhr.status}`));
        }
      };
      if (data) {
        xhr.send(JSON.stringify(data));
      } else {
        xhr.send();
      }
    });
  }
  async addMeal(data) {
    try {
      const meal = await this.request("POST", "/meals", data);
      document
        .getElementById("meals")
        .appendChild(this.createMealElement(meal));
      this.meals.push(meal);
      this.updateTotalCalories();
    } catch (err) {
      alert(`Error: ${err.message}`);
    }
  }
  async deleteMeal(id) {
    try {
      await this.request("DELETE", `/meals/${id}`);
      let index = this.meals.map(o => o.id).indexOf(id);
      this.meals.splice(index, 1);
      this.updateTotalCalories();
    } catch (err) {
      alert(`Error: ${err.message}`);
    }
  }
  updateTotalCalories() {
    let elTotal = document.getElementById("total");
    elTotal.textContent = this.meals
      .reduce((acc, o) => acc + o.calories, 0)
      .toLocaleString();
  }
  createMealElement({ id, title, calories }) {
    let el = document.createElement("li");
    el.className =
      "list-group-item d-flex justify-content-between align-items-center";
    el.innerHTML = `
      <div>
        <a href="#" class="remove">&times;</a>
        <span class="title">${title}</span>
      </div>
      <span class="calories badge badge-primary badge-pill">${calories}</span>
    `;
    // when the 'x' delete link is clicked
    el.querySelector("a").addEventListener("click", event => {
      event.preventDefault();
      this.deleteMeal(id);
      el.remove();
    });
    return el;
  }
  render() {
    let fragment = document.createDocumentFragment();
    for (let meal of this.meals) {
      fragment.appendChild(this.createMealElement(meal));
    }
    let el = document.getElementById("meals");
    while (el.firstChild) {
      el.removeChild(el.firstChild); // empty the <div id="meals" />
    }
    el.appendChild(fragment);
    this.updateTotalCalories();
  }
<<<<<<< HEAD
=======
  addcontact(contact) {
    document
      .getElementById("contact")
      .appendChild(this.createcontactElement(contact));
    this.contact.push(contact);
  }
  deletecontact(id) {
    let index = this.contact.map(o => o.id).indexOf(id);
    this.contact.splice(index, 1);
  }

  createcontactElement({ id, name, phone }) {
    let el = document.createElement("li");
    el.className =
      "list-group-item d-flex justify-content-between align-items-center";
    el.innerHTML = `
      <div>
        <a href="#" class="remove">&times;</a>
        <span class="name">${name}</span>
      </div>
      <span class="phone badge badge-primary badge-pill">${phone}</span>
    `;
    // when the 'x' delete link is clicked
    el.querySelector("a").addEventListener("click", event => {
      event.preventDefault();
      this.deletecontact(id);
      el.remove();
    });
    return el;
  }
  render() {
    let fragment = document.createDocumentFragment();
    for (let contact of this.contact) {
      fragment.appendChild(this.createcontactElement(contact));
    }
    let el = document.getElementById("contact");
    while (el.firstChild) {
      el.removeChild(el.firstChild); // empty the <div id="contact" />
    }
    el.appendChild(fragment);
  }
>>>>>>> 1bc0f22a0ee8559ad4b2316efa035d8b4a50fb68
}

let app = new App();
app.init();
