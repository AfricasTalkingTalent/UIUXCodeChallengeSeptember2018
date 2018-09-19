const express = require("express");
const mongoose = require("mongoose");

// Point urls to files
const auth = require("./routes/api/auth");
const contacts = require("./routes/api/contacts");

const app = express();

// DB Config
const db = require("./config/keys").mongoURI;

// Connect to MongoDB
mongoose
  .connect(db)
  .then(() => console.log("MongoDB Connnected"))
  .catch(err => console.log(err));

app.get("/", (req, res) => res.send("Hello World"));

// Use routes
app.use("/api/auth", auth);
app.use("/api/contacts", contacts);

const port = process.env.PORT || 5000;

app.listen(port, () => console.log(`Server running on port ${port}`));
