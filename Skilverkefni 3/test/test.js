/**
 * @author Egill Gautur Steingrímsson and Steinar Marinó Hilmarsson
 */

var should = require('should');
var request = require('supertest');
const express = require('express');
const mongoose = require('mongoose');
const user = require("../Controllers/user");
const video = require("../Controllers/video");
const friend = require("../Controllers/friends");
const app = express();
const userEntity = require('../Models/Entities/user');
const videoEntity = require('../Models/Entities/video');
const channelEntity = require('../Models/Entities/channel');
const favoriteEntity = require('../Models/Entities/favorite');
const friendEntity = require('../Models/Entities/friends');

/**
 * Opening connection on a mock database
 */
app.use(friend);
app.use(user);
app.use(video);
mongoose.connect("localhost/tests");
mongoose.connection.once("open", () => {
    console.log("Connected to tests db");
});

/**
 * Account service does the four following tests:
 * 1) Get all users
 * 2) Create a user and confirm successful authentication
 * 3) Try to create a user that already exists
 * 4) Try to authenticate a user with a wrong password
 */
describe("Account Service", function () {

    /**
     * Before each 'it' (test case), a user with properties name, username and password, is created and logs out
     */
    beforeEach(function (done) {
        var data = {
            name: 'quang',
            username: 'quangnguyen',
            password: 'lol'
        };

        request(app)
            .post("/users")
            .send(data)
            .expect(201)
            .end(function (err, res) {
                if (err) { throw err; }
                request(app)
                    .patch("/users/logout")
                    .send({ username: 'quangnguyen' })
                    .expect(200)
                    .end(function (err, res) {
                        if (err) { throw err; }
                        res.body.usingThisAccount.should.equal(false);
                        done();
                    });
            });
    });

    /**
     * After each 'it' (test case), the mock database is cleaned (everything deleted from database)
     */
    afterEach(function (done) {
        userEntity.User.remove(function (drop) { });
        videoEntity.Video.remove(function (drop) { });
        channelEntity.Channel.remove(function (drop) { });
        favoriteEntity.Favorite.remove(function (drop) { });
        friendEntity.Friend.remove(function (drop) { });
        done();
    });

    /**
     * Gets all users in database, should only contain one user
     */
    it("Should get all users", function (done) {
        request(app)
            .get("/users")
            .expect(200)
            .end(function (err, res) {
                res.body[0].username.should.equal('quangnguyen');
                should.equal(res.body[1], undefined);
                done();
            });
    });

    /**
     * Creates a new user, database should now contain two users
     */
    it("Should create a new user", function (done) {
        var data = {
            name: 'murphidoritos',
            username: 'murphidoritos',
            password: 'asdf'
        };

        request(app)
            .post("/users")
            .send(data)
            .expect(201)
            .end(function (err, res) {
                if (err) { throw err; }
                res.body.name.should.equal('murphidoritos');
                res.body.username.should.equal('murphidoritos');
                request(app)
                    .get("/users")
                    .expect(200)
                    .end(function (err, res) {
                        if (err) { throw err; }
                        res.body[0].username.should.equal('quangnguyen');
                        res.body[1].username.should.equal('murphidoritos');
                        should.equal(res.body[2], undefined);
                        done();
                    });
            });
    });

    /**
     * Tries to log in with a wrong password, should get an error
     */
    it("Should NOT be able to log in with wrong password", function (done) {
        var data = {
            username: 'quangnguyen',
            password: 'lollol'
        };

        request(app)
            .patch("/users/login")
            .send(data)
            .expect(401)
            .end(function (err, res) {
                if (err) { throw err; }
                done();
            });
    });

    /**
     * Tries to create a user that already exists, should get an error
     */
    it("Should NOT be able to create a user that already exists", function (done) {
        var data = {
            name: 'quang',
            username: 'quangnguyen',
            password: 'lol'
        };

        request(app)
            .post("/users")
            .send(data)
            .expect(400)
            .end(function (err, res) {
                if (err) { throw err; }
                done();
            });
    });
});

/**
 * User Service does the four following tests:
 * 1) View the profile of a user and confirm it correctly matches the expected profile
 * 2) Add favorite videos to a user, read the profile back and confirm the list matches
 * 3) Update the password of a user, log out, log in with new password and confirm it changed
 * 4) Add a list of close friends to a profile and read it back to confirm it was stored properly
 */
describe("User Service", function () {

    /**
     * Before each 'it' (test case), a user with properties name, username and password, is created and logs out
     */
    beforeEach(function (done) {
        var data = {
            name: 'quang',
            username: 'quangnguyen',
            password: 'lol'
        };

        request(app)
            .post("/users")
            .send(data)
            .expect(201)
            .end(function (err, res) {
                if (err) { throw err; }
                request(app)
                    .patch("/users/logout")
                    .send({ username: 'quangnguyen' })
                    .expect(200)
                    .end(function (err, res) {
                        if (err) { throw err; }
                        res.body.usingThisAccount.should.equal(false);
                        done();
                    });
            });
    });

    /**
     * After each 'it' (test case), the mock database is cleaned (everything deleted from database)
     */
    afterEach(function (done) {
        userEntity.User.remove(function (drop) { });
        videoEntity.Video.remove(function (drop) { });
        channelEntity.Channel.remove(function (drop) { });
        favoriteEntity.Favorite.remove(function (drop) { });
        friendEntity.Friend.remove(function (drop) { });
        done();
    });

    /**
     * Gets profile of certain user and confirm all information are correct
     */
    it("Should match profile information", function (done) {
        request(app)
            .get("/users/quangnguyen")
            .expect(200)
            .end(function (err, res) {
                if (err) { throw err; }
                res.body.name.should.equal('quang');
                res.body.username.should.equal('quangnguyen');
                done();
            });
    });

    /**
     * Logs in to the user's account, adds global video, then adds it to favorites and confirm that
     * the video was successfully added to favorites
     */
    it("Should match favorite videos profile", function (done) {
        var loginData = {
            username: 'quangnguyen',
            password: 'lol'
        };

        var data = {
            title: 'Pen Pinapple Apple Pen',
            owner: 'quangnguyen'
        };

        request(app)
            .patch("/users/login")
            .send(loginData)
            .expect(200)
            .end(function (err, res) {
                if (err) { throw err; }
                request(app)
                    .post("/videos")
                    .send(data)
                    .expect(201)
                    .end(function (err, res) {
                        if (err) { throw err; }
                        res.body.title.should.equal("Pen Pinapple Apple Pen");
                        res.body.owner.should.equal("quangnguyen");
                        request(app)
                            .post("/videos/quangnguyen/favorites")
                            .send({ title: 'Pen Pinapple Apple Pen' })
                            .expect(201)
                            .end(function (err, res) {
                                if (err) { throw err; }
                                res.body.username.should.equal("quangnguyen");
                                res.body.videoTitle.should.equal("Pen Pinapple Apple Pen");
                                done();
                            });
                    });
            })
    });

    /**
     * Logs in, changes password, logs out and tries to log in with old password. Finally confirms
     * password changes by logging in with new password
     */
    it("Should update password", function (done) {
        var loginData = {
            username: 'quangnguyen',
            password: 'lol'
        };

        var newLoginData = {
            username: 'quangnguyen',
            password: 'okok'
        };

        request(app)
            .patch("/users/login")
            .send(loginData)
            .expect(200)
            .end(function (err, res) {
                if (err) { throw err; }
                request(app)
                    .patch("/users/quangnguyen/changepassword")
                    .send({ password: 'okok' })
                    .expect(200)
                    .end(function (err, res) {
                        if (err) { throw err; }
                        res.body.username.should.equal('quangnguyen');
                        res.body.password.should.equal('okok');
                        request(app)
                            .patch("/users/logout")
                            .send({ username: 'quangnguyen' })
                            .expect(200)
                            .end(function (err, res) {
                                if (err) { throw err; }
                                request(app)
                                    .patch("/users/login")
                                    .send(loginData)
                                    .expect(401)
                                    .end(function (err, res) {
                                        if (err) { throw err; }
                                        request(app)
                                            .patch("/users/login")
                                            .send(newLoginData)
                                            .expect(200)
                                            .end(function (err, res) {
                                                if (err) { throw err; }
                                                done();
                                            });
                                    })
                            });
                    });
            });
    });

    /**
     * Creates two users, then logs in to default user (user created before each test case) and
     * adds both new users to friend list. Finally confirms that both new users are the only ones listed as friends
     */
    it("Should match friendlist", function (done) {
        var data = {
            name: 'Egill',
            username: 'egill',
            password: 'asdf'
        };

        var data2 = {
            name: 'Marri',
            username: 'marri',
            password: 'asdf'
        };

        var friend = {
            username: 'quangnguyen',
            friend: 'egill'
        };

        var friend2 = {
            username: 'quangnguyen',
            friend: 'marri'
        };

        request(app)
            .post("/users")
            .send(data)
            .expect(201)
            .end(function (err, res) {
                if (err) { throw err; }
                res.body.name.should.equal('Egill');
                res.body.username.should.equal('egill');
                request(app)
                    .patch("/users/logout")
                    .send({ username: 'egill' })
                    .expect(200)
                    .end(function (err, res) {
                        if (err) { throw err; }
                        request(app)
                            .post("/users")
                            .send(data2)
                            .expect(201)
                            .end(function (err, res) {
                                if (err) { throw err; }
                                res.body.name.should.equal('Marri');
                                res.body.username.should.equal('marri');
                                request(app)
                                    .patch("/users/logout")
                                    .send({ username: 'marri' })
                                    .expect(200)
                                    .end(function (err, res) {
                                        if (err) { throw err; }
                                        request(app)
                                            .patch("/users/login")
                                            .send({ username: 'quangnguyen', password: 'lol' })
                                            .expect(200)
                                            .end(function (err, res) {
                                                if (err) { throw err; }
                                                request(app)
                                                    .post("/friends/quangnguyen")
                                                    .send(friend)
                                                    .expect(201)
                                                    .end(function (err, res) {
                                                        if (err) { throw err; }
                                                        res.body.username.should.equal('quangnguyen');
                                                        res.body.friend.should.equal('egill');
                                                        request(app)
                                                            .post("/friends/quangnguyen")
                                                            .send(friend2)
                                                            .expect(201)
                                                            .end(function (err, res) {
                                                                if (err) { throw err; }
                                                                res.body.username.should.equal('quangnguyen');
                                                                res.body.friend.should.equal('marri');
                                                                request(app)
                                                                    .get("/friends/quangnguyen")
                                                                    .expect(200)
                                                                    .end(function (err, res) {
                                                                        if (err) { throw err; }
                                                                        res.body[0].friend.should.equal('egill');
                                                                        res.body[1].friend.should.equal('marri');
                                                                        should.equal(res.body[2], undefined);
                                                                        done();
                                                                    });
                                                            })

                                                    });
                                            });
                                    })
                            })
                    });
            });
    });
});

/**
 * Video Service does the three following tests:
 * 1) Add a video to a channel and confirm it’s listed in “all videos”
 * 2) Add a video to a channel and confirm it’s listed in that channel
 * 3) Remove a video and confirm it’s not listed in all videos or the (previous) channel list
 */
describe("Video Service", function () {

    /**
     * Before each 'it' (test case), a user with properties name, username and password, is created. Then
     * a video with properties title and owner (the user which adds video) is added to 'all videos'
     */
    beforeEach(function (done) {
        var data = {
            name: 'quang',
            username: 'quangnguyen',
            password: 'lol'
        };

        var videoData = {
            title: 'Pen Pinapple Apple Pen',
            owner: 'quangnguyen'
        };

        request(app)
            .post("/users")
            .send(data)
            .expect(201)
            .end(function (err, res) {
                if (err) { throw err; }
                request(app)
                    .post("/videos")
                    .send(videoData)
                    .expect(201)
                    .end(function (err, res) {
                        if (err) { throw err; }
                        res.body.title.should.equal('Pen Pinapple Apple Pen');
                        res.body.owner.should.equal('quangnguyen');
                        done();
                    });
            });
    });

    /**
     * After each 'it' (test case), the mock database is cleaned (everything deleted from database)
     */
    afterEach(function (done) {
        userEntity.User.remove(function (drop) { });
        videoEntity.Video.remove(function (drop) { });
        channelEntity.Channel.remove(function (drop) { });
        favoriteEntity.Favorite.remove(function (drop) { });
        friendEntity.Friend.remove(function (drop) { });
        done();
    });

    /**
     * Adds the default video (video created before each test case) to user's channel and confirm
     * that by doing so, the video is listed in 'all videos'
     */
    it("Should add video to a channel and be listed in all videos", function (done) {
        request(app)
            .post("/videos/channels/quangnguyen/by_title")
            .send({ title: 'Pen Pinapple Apple Pen' })
            .expect(201)
            .end(function (err, res) {
                if (err) { throw err; }
                res.body.videoTitle.should.equal('Pen Pinapple Apple Pen');
                request(app)
                    .get("/videos")
                    .expect(200)
                    .end(function (err, res) {
                        if (err) { throw err; }
                        res.body[0].title.should.equal('Pen Pinapple Apple Pen');
                        res.body[0].owner.should.equal('quangnguyen');
                        done();
                    });
            });
    });

    /**
     * Adds the default video (video created before each test case) to user's channel and confirm
     * that by doing so, the video is listed in user's channel
     */
    it("Should add video to a channel and be listed in channel", function (done) {
        request(app)
            .post("/videos/channels/quangnguyen/by_title")
            .send({ title: 'Pen Pinapple Apple Pen' })
            .expect(201)
            .end(function (err, res) {
                if (err) { throw err; }
                res.body.videoTitle.should.equal('Pen Pinapple Apple Pen');
                request(app)
                    .get("/videos/channels/quangnguyen")
                    .expect(200)
                    .end(function (err, res) {
                        if (err) { throw err; }
                        res.body[0].videoTitle.should.equal('Pen Pinapple Apple Pen');
                        done();
                    });
            });
    });

    /**
     * Deletes default video (video created before each test case) and confirm that by doing so,
     * the video is no longer listed in neither 'all videos' nor the user's channel
     */
    it("Should delete video and confirm it's not listed in neither all videos nor channel", function (done) {
        var videoData = {
            title: 'Pen Pinapple Apple Pen',
            owner: 'quangnguyen'
        };

        request(app)
            .delete("/videos/quangnguyen/by_title")
            .send(videoData)
            .expect(200)
            .end(function (err, res) {
                if (err) { throw err; }
                request(app)
                    .get("/videos")
                    .expect(204)
                    .end(function (err, res) {
                        if (err) { throw err; }
                        arguments.should.be.empty;
                        request(app)
                            .get("/videos/channels/quangnguyen")
                            .expect(204)
                            .end(function (err, res) {
                                if (err) { throw err; }
                                arguments.should.be.empty;
                                done();
                            });
                    });
            });
    });
});

