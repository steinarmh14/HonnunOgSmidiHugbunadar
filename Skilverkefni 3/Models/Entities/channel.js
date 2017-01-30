/**
 * @author Egill Gautur Steingrímsson and Steinar Marinó Hilmarsson
 */

const mongose = require("mongoose");

const ChannelSchema = new mongose.Schema({

    username: {
        type: String,
        required: true,
    },
    videoTitle: {
        type: String,
        required: true
    }
}); 

const ChannelEntity = mongose.model("Channels", ChannelSchema);

const channelEntity = {
    Channel: ChannelEntity
}

module.exports = channelEntity;