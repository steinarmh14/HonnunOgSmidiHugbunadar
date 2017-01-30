/**
 * @author Egill Gautur Steingrímsson and Steinar Marinó Hilmarsson
 */

const express = require('express');
const mongoose = require('mongoose');
const user = require("./Controllers/user");
const video = require("./Controllers/video");
const friend = require("./Controllers/friends");
const app = express();
const port = 5000;

app.use(friend);
app.use(user);
app.use(video);
mongoose.connect("localhost/honnsmidiassign");
mongoose.connection.once("open", () => {
    console.log("Connected to db");
    app.listen(process.env.PORT || port, function() {
        console.log("Web server started at port: " + port);
    });
})

