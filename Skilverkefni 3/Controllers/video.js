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
const bodyParser = require('body-parser');
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({
    extend: true
}));

/**
 * Get list of all videos in the database.
 * 
 * @return: List of all videos
 */
app.get("/videos", (req, res) => {
    videoEntity.Video.find(function (err, docs) {
        if (err) {
            res.status(500).send(err);
        } else {
            var allVideos = [];
            for (var doc in docs) {
                allVideos.push({
                    title: docs[doc].title,
                    owner: docs[doc].owner,
                });
            }
            if (allVideos.length < 1) {
                res.status(204).send("There are no videos");
            } else {
                res.status(200).send(allVideos);
            }

        }
    });
});

/**
 * Adds a new video. This request needs a title, and the owner of the video
 * @title: the title of the video
 * @owner: the owner of the video
 * 
 * @return: Either the title of the video and the owner or error
 */
app.post("/videos", (req, res) => {

    //Searches through the database for a video
    videoEntity.Video.find(function (err, docs) {
        if (err) {
            res.status(500).send("Internal server error");
            return;
        } else {
            //Goes through the videos
            for (var doc in docs) {
                //Checks if title is taken
                if (docs[doc].title === req.body.title) {
                    res.status(500).send("Title already in use");
                    return;
                }
            }
        }

        var data = {
            title: req.body.title,
            owner: req.body.owner,
        };

        //Makes a new entity with the new video and pushes it to the database and saves the changes.
        var entity = new videoEntity.Video(data);

        entity.save(function (err) {
            if (err) {
                //Internal server error
                res.status(400).send("Internal server error");
                return;
            } else {
                //Sends response with the video title and its owner
                res.status(201).send({
                    title: data.title,
                    owner: data.owner
                });
                return;
            }
        });
    });
});

/**
 * This request gets information from a channel from a specific user.
 * 
 * @return: Either list of videos in the users channel or error
 */
app.get("/videos/channels/:username", (req, res) => {

    var theUsername = req.params.username;
    var userFound = false;
    var allVideosInChannel = [];

    channelEntity.Channel.find(function (err, docs) {
        if (err) {
            res.status(500).send(err);
        } else {
            var vidsInChan = [];
            for (var doc in docs) {
                if (docs[doc].username === theUsername) {
                    userFound = true;
                    vidsInChan.push({
                        videoTitle: docs[doc].videoTitle,
                    });
                }
            }
            if (vidsInChan.length < 1) {
                res.status(204).send("There are no videos in this channel");
            } else {
                res.status(200).send(vidsInChan);
            }
        }

    });
});

/**
 *  Adds a video to a users channel by the title. This request needs the title.
 * @title: the video title
 * 
 * @return:  Either the username and the video title or error
 */
app.post("/videos/channels/:username/by_title", (req, res) => {

    var theUsername = req.params.username;
    var videoFound = false;
    var loggedIn = false;

    //Searches for a user in the database.
    userEntity.User.find(function (err, docs) {
        if (err) {
            res.status(500).send("Internal server error");
            return;
        } else {
            var data;
            //Searches through the users
            for (var doc in docs) {
                //Checks for the username
                if (docs[doc].username === theUsername) {
                    //Checks if user is logged in
                    if (docs[doc].usingThisAccount === true) {
                        loggedIn = true;
                        //Searches for a video in the database.
                        videoEntity.Video.find(function (err, docs) {
                            if (err) {
                                res.status(500).send("Internal server error");
                                return;
                            } else {
                                //Goes through the videos
                                for (var doc in docs) {
                                    //Checks the title
                                    if (docs[doc].title === req.body.title) {
                                        videoFound = true;
                                        var data = {
                                            username: theUsername,
                                            videoTitle: req.body.title
                                        }

                                        //Makes a new entity with the new video and pushes it to the database and saves the changes.
                                        var entity = new channelEntity.Channel(data);

                                        entity.save(function (err) {
                                            if (err) {
                                                //Error in saving
                                                res.status(500).send("Internal server error");
                                                return;
                                            } else {
                                                //Sends response with the username and the video title
                                                res.status(201).send({
                                                    username: data.theUsername,
                                                    videoTitle: data.videoTitle
                                                });
                                            }
                                        });
                                    }
                                }

                                //Checks if the video was found
                                if (videoFound === false) {
                                    //Sends response that the video wasnt found
                                    res.status(404).send("Video not found");
                                    return;
                                }
                            }
                        });
                    } else {
                        //Sends response that you must be logged in
                        res.status(401).send("You must be logged in to add video to channel ");
                        return;
                    }
                }
            }
        }
    });
});

/**
 * Adds a existing video to favorites. This request needs the title of the video
 * @title: The video title
 * 
 * @return: Either the username and the video title or error
 */
app.post("/videos/:username/favorites", (req, res) => {

    var theUsername = req.params.username;
    var userFound = false;
    var usingThisAcc = false;

    //Searches through the database for the users
    userEntity.User.find(function (err, docs) {
        //Searches through the users
        for (var doc in docs) {
            //Checks if this is the right user
            if (docs[doc].username === theUsername) {
                userFound = true;
                //Checks if user is logged in
                if (docs[doc].usingThisAccount === true) {
                    usingThisAcc = true;
                    var data = {
                        username: theUsername,
                        videoTitle: req.body.title
                    };

                    //Makes a new entity with the username and video title and pushes it to the database and saves the changes.
                    var entity = new favoriteEntity.Favorite(data);

                    entity.save(function (err) {
                        if (err) {
                            //Error unable to add video
                            res.status(500).send("Internal server error");
                            return;
                        } else {
                            //Sends response with the username and video title.
                            res.status(201).send({
                                username: data.username,
                                videoTitle: data.videoTitle
                            });
                        }
                    });
                }
            }
        }
        //Checks if the user was found
        if (userFound === false) {
            //Sends response that the user wasnt found
            res.status(404).send("User not found");
            return;
        }
        //Checks if the user wasnt logged in
        else if (usingThisAcc === false) {
            //Sends response that you must be logged in to make add to favorites
            res.status(401).send("You must be logged in to be able to add video to favorites");
            return;
        }
    });
});

/**
 * Gets the list of favorite videos from a user.
 * 
 * @return: Either the list of videos and the username or error
 */
app.get("/videos/:username/favorites", (req, res) => {

    var theUsername = req.params.username;
    var found = false;

    //Searchers for favorites in the database
    favoriteEntity.Favorite.find(function (err, docs) {
        if (err) {
            res.status(500).send("Internal server error");
            return;
        } else {

            var favoriteVids = [];
            //Searches through the favorites
            for (var doc in docs) {
                //Checks for the username
                if (docs[doc].username === theUsername)
                    found = true;
                //Pushes video to an array
                favoriteVids.push({
                    username: docs[doc].username,
                    videoTitle: docs[doc].videoTitle
                });
            }
            //Checks if the user was found
            if (found === false) {
                //Sends response that the user wasnt found
                res.status(404).send("User not found");
                return;
            }
            else {
                //Sends response with all favorite videos
                res.status(200).send(favoriteVids);
            }
        }
    });
});

/**
 * Deletes a Video from the database. This request needs the title of the video
 * @title: The title of the video
 * 
 * @return: Either Successfully deleted video or error
 */
app.delete("/videos/:username/by_title", (req, res) => {

    var theUsr;
    var videoFound = false;
    var vidSchema = false;
    var chanSchema = false;
    var favoriteSchema = false;

    videoEntity.Video.find(function (err, docs) {
        if (err) {
            res.status(500).send(err);
        } else {
            for (var doc in docs) {
                if (docs[doc].title === req.body.title) {
                    videoFound = true;
                    theUsr = docs[doc].owner;
                    userEntity.User.find(function (err, docs2) {
                        if (err) {
                            res.status(500).send(err);
                        } else {
                            for (var doc2 in docs2) {
                                if (docs2[doc2].username === theUsr) {
                                    if (docs2[doc2].usingThisAccount === true) {
                                        videoEntity.Video.remove({
                                            owner: theUsr,
                                            title: req.body.title
                                        }, function (err, docs) {
                                            if (err) {
                                                res.status(500).send("Error in deleting video from video schema");
                                            }
                                            else {
                                                vidSchema = true;
                                            }

                                            if (vidSchema === false) {
                                                res.status(401).send("Couldnt delete video from video schema");
                                                return;
                                            }
                                        });

                                        channelEntity.Channel.remove({
                                            username: theUsr,
                                            videoTitle: req.body.title
                                        }, function (err, docs) {
                                            if (err) {
                                                res.status(500).send("Error in deleting video from channel schema");
                                                return;
                                            }
                                            else {
                                                chanSchema = true;
                                            }

                                            if (chanSchema === false) {
                                                res.status(401).send("Couldnt delete video from channel schema");
                                                return;
                                            }

                                        });

                                        favoriteEntity.Favorite.remove({
                                            username: theUsr,
                                            videoTitle: req.body.title
                                        }, function (err, docs) {
                                            if (err) {
                                                res.status(500).send("Error in deleting video from favorite schema");
                                                return;
                                            }
                                            else {
                                                favoriteSchema = true;
                                            }

                                            if (favoriteSchema === false) {
                                                res.status(401).send("Couldnt delete video from favorite schema");
                                                return;
                                            } else {
                                                res.status(200).send("Successfully deleted video");
                                                return;
                                            }

                                        });
                                    }
                                }
                            }
                        }
                    });
                }
            }
        }

    });
});

module.exports = app;
