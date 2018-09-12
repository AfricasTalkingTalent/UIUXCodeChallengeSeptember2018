class App {
  constructor() {
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
    this.render();
  }
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
}

let app = new App();
app.init();
