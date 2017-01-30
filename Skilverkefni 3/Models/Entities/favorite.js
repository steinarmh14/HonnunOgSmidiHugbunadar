/**
 * @author Egill Gautur Steingrímsson and Steinar Marinó Hilmarsson
 */

const mongose = require("mongoose");

const FavoriteSchema = new mongose.Schema({

    username: {
        type: String,
        required: true,
    },
    videoTitle: {
        type: String,
        required: true
    }
}); 

const FavoriteEntity = mongose.model("Favorites", FavoriteSchema);

const favoriteEntity = {
    Favorite: FavoriteEntity
}

module.exports = favoriteEntity;