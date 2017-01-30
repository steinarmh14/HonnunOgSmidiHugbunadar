/**
 * @author Egill Gautur Steingrímsson and Steinar Marinó Hilmarsson
 */

'use strict'
const express = require("express");
const app = express();
const videoEntity = require("../Models/Entities/video");
const userEntity = require("../Models/Entities/user");
const friendEntity = require("../Models/Entities/friends");
const channelEntity = require("../Models/Entities/channel");
const favoriteEntity = require("../Models/Entities/favorite");
const bodyParser = require('body-parser');
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({
    extend: true
}));

/**
 * Makes a new user in the database.
 * This request need to get the name, username and password 
 * @name: name of the user
 * @username: username of the user
 * @password: password of the user.
 * 
 * The user will be by default logged in so usingThisAccount will be by default true
 * 
 * @return: Either the name of the user and his status whether he is using this account or error
 */
app.post("/users", (req, res) => {

    //Search in the database for a user
    userEntity.User.find(function (err, docs) {
        if (err) {
            //Internal server error
            res.status(500).send("Internal server error");
            return;
        } else {
            //searching in user schema
            for (var doc in docs) {
                //if someone has the username is taken throw error.
                if (docs[doc].username === req.body.username) {
                    res.status(400).send("Username already in use");
                    return;
                }

                //checking if your are logged in to another account
                if (docs[doc].usingThisAccount === true) {
                    //log out of that account and save the changes in the database
                    docs[doc].usingThisAccount = false;
                    docs[doc].save(function (err) {
                        if (err) {
                            //Internal server error (not able to save to database)
                            res.status(500).send("Internal server error");
                            return;
                        }
                    });

                }
            }

            //data which will be saved in the database
            var data = {
                name: req.body.name,
                username: req.body.username,
                password: req.body.password,
                usingThisAccount: true,
            };

            //make new entity in user schema with the new user
            var entity = new userEntity.User(data);

            entity.save(function (err) {
                if (err) {
                    //Throw error if we cant save the new user in the database
                    res.status(500).send("Internal server error");
                    return;
                } else {
                    //saves the data to database
                    res.status(201).send({
                        name: data.name,
                        username: data.username,
                        usingThisAccount: data.usingThisAccount
                    });
                    return;
                }
            });
        }
    });
});

/**
 * This request returns all users from the database.
 * 
 * @return: Either all users or error
 */
app.get("/users", (req, res) => {
    //Searches in the user schema
    userEntity.User.find(function (err, docs) {
        if (err) {
            //Internal server error
            res.status(500).send("Internal server error");
        } else {
            var allUsers = [];
            //Pushes all users in an array
            for (var doc in docs) {
                allUsers.push({
                    name: docs[doc].name,
                    username: docs[doc].username,
                    usingThisAccount: docs[doc].usingThisAccount
                });
            }
            //Sends response with all the users.
            res.status(200).send(allUsers);
        }
    });
});

/**
 * Returns a specific user from the database.
 * 
 * @return: Either the name and username of a user or error
 */
app.get("/users/:username", (req, res) => {
    var username = req.params.username;
    //Searches in the database for a user.
    userEntity.User.find(function (err, docs) {
        if (err) {
            //Internal server error
            res.status(500).send("Internal server error");
        } else {
            //Goes through the scheme and searches for the user.
            for (var doc in docs) {
                if (docs[doc].username === username) {
                    if (err) {
                        //Found the user but there were some internal server error.
                        res.status(500).send("Internal server error");
                        return;
                    } else {
                        //Sends response with the information about the user.
                        res.status(200).send({
                            name: docs[doc].name,
                            username: docs[doc].username,
                        });
                    }
                } else {
                    //Send response if the user wasnt found.
                    res.status(404).send("User not found");
                }
            }
        }
    });
});

/**
 * Changes password of a user. 
 * To be able to change password the user must be logged in. This request needs the new password to work.
 * @password: the new password
 * 
 * @return: Either username and the new password or error
 */
app.patch("/users/:username/changepassword", (req, res) => {

    var username = req.params.username;
    //Searches for user in user schema
    userEntity.User.find(function (err, docs) {
        if (err) {
            //Internal server error
            res.status(500).send("Internal server error");
            return;
        } else {
            //Searches for the user
            for (var doc in docs) {
                //Checks if this is the user which was searched for
                if (docs[doc].username === username) {
                    //Check if the user is logged in
                    if (docs[doc].usingThisAccount === true) {
                        //Changes the password and saves it to the database
                        docs[doc].password = req.body.password;
                        docs[doc].save(function (err) {
                            if (err) {
                                //Could not save changes in the database
                                res.status(500).send("Internal server error");
                                return;
                            }
                            else {
                                //Changes are saved and sends response with the username and the new password.
                                res.status(200).send({
                                    username: req.params.username,
                                    password: req.body.password
                                });
                            }
                        });
                    }
                    else {
                        //Sends response if the user wasnt logged in
                        res.status(403).send("You cant change the password on this account. Because this is not your account");
                        return;
                    }

                }
            }
        }
    });
});

/**
 * Logs a user in. Needs the username and the password.
 * @username: the username of the user
 * @password: the password of the account
 * 
 * @return: Either the username and his status whether he is using this account or error
 */
app.patch("/users/login", (req, res) => {
    var found = false;
    //Searches in the database for a user
    userEntity.User.find(function (err, docs) {
        if (err) {
            //Internal server error
            res.status(500).send("Internal server error");
        } else {
            //Goes through the users in database
            for (var doc in docs) {
                //Checks if someone is logged in
                if (docs[doc].usingThisAccount === true) {
                    //Check if you are logged in
                    if (docs[doc].username === req.body.username) {
                        //Sends response that you are logged in
                        res.status(204).send("You are already logged in");
                        return;
                    }
                    else {
                        //Sends response that someone else is logged in
                        res.status(400).send("There is another account logged in, you must first log out from that account to be able to log in to another account");
                        return;
                    }

                }
            }
            //Goes through the users in database
            for (var doc in docs) {
                //Found the user who is logging in
                if (docs[doc].username === req.body.username) {
                    //Checks if the password is right
                    if (docs[doc].password != req.body.password) {
                        //Sends response that the password is wrong
                        res.status(401).send("Wrong password");
                        return;
                    } else {
                        //logs the user in and saves it in the database.
                        docs[doc].usingThisAccount = true;
                        var usingThisAcc = docs[doc].usingThisAccount;
                        found = true;
                        docs[doc].save(function (err) {
                            if (err) {
                                //Internal server error
                                res.status(500).send("Internal server error");
                                return;
                            } else {
                                //Sends response with username and using this account status
                                res.status(200).send({
                                    username: req.body.username,
                                    usingThisAccount: usingThisAcc
                                });
                                return;
                            }
                        });
                    }
                }
            }

            //Sends response if user wasnt found
            if (found === false) {
                res.status(404).send("User not found");
                return;
            }
        }
    });

});

/**
 * logs a user out. Needs the username and password.
 * @username: the username of the user.
 * @password: the password of the account.
 * 
 * @return: Either the username and status if he is using this account or error
 */
app.patch("/users/logout", (req, res) => {
    //Searches for a user in the database.
    userEntity.User.find(function (err, docs) {
        if (err) {
            //internal server error
            res.status(500).send("Internal server error");
        }
        else {
            //Goes through the users in the database.
            for (var doc in docs) {
                //Checks for a username.
                if (docs[doc].username === req.body.username) {
                    //Checks if the user is logged in.
                    if (docs[doc].usingThisAccount === false) {
                        //Sends respsons if user wasnt logged in.
                        res.status(204).send("You weren't logged in");
                        return;
                    }
                    //Logs the user out and saves changes in database.
                    docs[doc].usingThisAccount = false;
                    docs[doc].save(function (err) {
                        if (err) {
                            //internal server error.
                            res.status(500).send("Internal server error");
                            return;
                        }
                        else {
                            //Sends response with username and his status.
                            res.status(200).send({
                                username: req.body.username,
                                usingThisAccount: docs[doc].usingThisAccount
                            });
                        }
                    });
                }
            }
        }
    });
});

/**
 * Deletes user and his videos from the database. The user must be logged in this request needs the
 * username and password of the account.
 * @username: the username of the user
 * @password: the password of the account
 * 
 * @return: Either a successfull delete if the delete worked else error.
 */
app.delete("/users", (req, res) => {

    var userFound = false;
    var vidFound = false;
    var chanFound = false;
    var friendFound = false;

    //Search for a user in the database.
    userEntity.User.find(function (err, docs) {
        if (err) {
            //Internal server error.
            res.status(500).send("Internal server error");
        } else {
            //Searches through the users.
            for (var doc in docs) {
                //Checks the username matches the user to delete.
                if (docs[doc].username === req.body.username) {
                    //Check if the user is logged in.
                    if (docs[doc].usingThisAccount === true) {
                        //Checks if the password is right.
                        if (docs[doc].password === req.body.password) {
                            //removes the user from the database and saves the changes.
                            userEntity.User.remove({
                                username: req.body.username,
                                password: req.body.password
                            }, function (err, docs) {
                                if (err) {
                                    //Internal server error.
                                    res.status(500).send("Internal server error");
                                    return;
                                }
                                else {
                                    //Sends response if the delete was successfull.
                                    userFound = true;
                                    res.status(200).send("Successful delete");

                                }

                                if (userFound === false) {
                                    //Sends response if the delete if user wasnt found.
                                    res.status(404).send("User not found");
                                    return;
                                }

                            });

                        } else {
                            //Sends response if the delete if the password was wrong.
                            res.status(401).send("Incorrect password. Must have the right password to delete account");
                            return;
                        }
                    } else {
                        //Sends response if the delete if user wasnt logged in.
                        res.status(403).send("You are not logged in. You cant delete this account");
                        return;
                    }
                }
            }
        }
    });
    //Searches for a videos in database
    videoEntity.Video.find(function (err, docs) {
        //Goes through the videos.
        for (var doc in docs) {
            //Checks if the owner is the one we are deleting.
            if (docs[doc].owner === req.body.username) {
                //Deletes video and saves the changes in the database.
                videoEntity.Video.remove({
                    owner: req.body.username
                }, function (err, docs) {
                    if (err) {
                        //Internal server error.
                        res.status(500).send("Internal server error");
                    } else {
                        //Sends response if the delete was successful.
                        vidFound = true
                        res.status(200).send("Successful delete");

                    }

                    if (vidFound = false) {
                        //Sends response if there was no video to delete.
                        res.status(204).send("There is no video to delete by this owner");
                    }

                });
            }
        }
    });

    //Searches for a channel in database.
    channelEntity.Channel.find(function (err, docs) {
        //goes through the channels.
        for (var doc in docs) {
            //checks if the username is the user who made the channel.
            if (docs[doc].username === req.body.username) {
                //Deletes the channel and saves the changes in the datadase.
                channelEntity.Channel.remove({
                    username: req.body.username
                }, function (err, docs) {
                    if (err) {
                        //Internal server error
                        res.status(500).send("Internal server error");
                    } else {
                        //Sends response if the delete was successful.
                        chanFound = true
                        res.status(200).send("Successful delete");
                    }

                    if (chanFound = false) {
                        //Sends response that there was no channel to delete
                        res.status(204).send("There is no channel to delete");
                    }

                });
            }
        }
    });
});

module.exports = app;