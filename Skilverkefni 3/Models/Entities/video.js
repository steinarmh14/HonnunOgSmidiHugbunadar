/**
 * @author Egill Gautur Steingrímsson and Steinar Marinó Hilmarsson
 */

const mongose = require("mongoose");

const VideoSchema = new mongose.Schema({
    title: {
        type: String,
        required: true,
        validate: {
            validator: function(value) {
                return (value !== " ");
            },
            message: 'Name cannot be empty or just a white space'
        }   
    }, owner : {
        type: String,
        require: true
    }
});

const VideoEntity = mongose.model("Videos", VideoSchema);

const videoEntity = {
    Video: VideoEntity
}

module.exports = videoEntity;