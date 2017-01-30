/**
 * @author Egill Gautur Steingrímsson and Steinar Marinó Hilmarsson
 */

'use strict'
const express = require("express");
const app = express();
const userEntity = require("../Models/Entities/user");
const videoEntity = require("../Models/Entities/video");
const channelEntity = require("../Models/Entities/channel");
const favoriteEntity = require("../Models/Entities/favorite");
const friendEntity = require("../Models/Entities/friends");
const bodyParser = require('body-parser');
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({
    extend: true
}));


/**
 * Adds a friend to a user. This request needs the username of the user and his friends name
 * @username: the username of the user
 * @friend: the users friend
 * 
 * @return: Either the username and the friend or error
 */
app.post("/friends/:username", (req, res) => {

    var friendFound = false;
    var userFound = false;
    var userLoggedIn = false;
    //Searches for a user in the database.
    userEntity.User.find(function (err, docs) {
        if (err) {
            //Internal server error
            res.status(500).send("Internal server error");
            return;
        } else {
            //Goes through the users
            for (var doc in docs) {
                //Checks if user is in the databes
                if (docs[doc].username === req.body.username) {
                    userFound = true;
                    //Checks if the user is logged in
                    if (docs[doc].usingThisAccount === true) {
                        userLoggedIn = true;
                        //Goes through his friends
                        for (var d in docs) {
                            //checks if the friend exists
                            if (docs[d].username === req.body.friend) {
                                friendFound = true;

                                var data = {
                                    username: req.body.username,
                                    friend: req.body.friend
                                }

                                //Makes new entity and pushes the friendship to the database and saves the changes
                                var entity = new friendEntity.Friend(data);
                                entity.save(function (err) {
                                    if (err) {
                                        //Internal server error
                                        res.status(500).send("Internal server error");
                                        return;
                                    } else {
                                        //Sends response with the username and the friend
                                        res.status(201).send({
                                            username: data.username,
                                            friend: data.friend
                                        });
                                        return;
                                    }
                                });
                            }
                        }
                    }
                }
            }
            //Checks if the user was found
            if (userFound === false) {
                res.status(404).send("User not found");
                return;
            }
            //Checks if the user was logged in
            if (userLoggedIn === false) {
                res.status(401).send("You must be logged in to establish a friendship");
                return;
            }
            //Checks if the friend was found
            if (friendFound === false) {
                res.status(400).send("Your friend must have an account");
                return;
            }
        }
    });
});

/**
 * Gets friend of a specific user
 * 
 * @return: Either the list of his friends or error
 */
app.get("/friends/:username", (req, res) => {

    var username = req.params.username;
    var friendFound = false;
    var userFound = false;
    var isInList = false;

    //Searches in the database for a friendship
    friendEntity.Friend.find(function (err, docs) {
        if (err) {
            //Internal server error
            res.status(500).send("Internal server error");
            return;
        } else {
            var closeFriends = [];
            //Goes through the friendships
            for (var doc in docs) {
                //Checks if user exists
                if (docs[doc].username === username) {
                    userFound = true;
                    //Makes sure there are no duplicates of friends
                    for (var d in closeFriends) {
                        if (closeFriends[d].friend === docs[doc].friend) {
                            isInList = true;
                        }
                    }
                    if (isInList === false) {
                        //Pushes friend to an array
                        closeFriends.push({
                            friend: docs[doc].friend
                        });
                        friendFound = true;
                        isInList = false;
                    }
                }
            }
        }
        //Checks if user was found
        if (userFound === false) {
            //Sends response that the user wasnt found
            res.status(404).send("User not found");
            return;
        }
        //Checks if friend was found
        if (friendFound === false) {
            //Sends response that the user has no friends
            res.status(204).send("This user has no close friends");
            return;
        } else {
            //Sends response with users all close friends
            res.status(200).send(closeFriends);
        }
    });
});

module.exports = app;