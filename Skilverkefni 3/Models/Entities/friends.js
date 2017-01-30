/**
 * @author Egill Gautur Steingrímsson and Steinar Marinó Hilmarsson
 */

const mongose = require("mongoose");

const FriendsSchema = new mongose.Schema({

    username: {
        type: String,
        required: true,
    },
    friend: {
        type: String,
        required: true
    }
}); 

const FriendsEntity = mongose.model("Friends", FriendsSchema);

const friendEntity = {
    Friend: FriendsEntity
}

module.exports = friendEntity;