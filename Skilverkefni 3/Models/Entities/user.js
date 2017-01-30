/**
 * @author Egill Gautur Steingrímsson and Steinar Marinó Hilmarsson
 */

const mongose = require("mongoose");

const UserSchema = new mongose.Schema({
    name: {
        type: String,
        required: true,
        validate: {
            validator: function(value) {
                return (value !== " ");
            },
            message: 'Name cannot be empty or just a white space'
        }   
    },
    username: {
        type: String,
        required: true,
        validate: {
            validator: function(value) {
                return (value !== " ");
            },
            message: 'username cannot be empty or just a white space'
        }  
    },
    password: {
        type: String,
        required: true 
    },
    /*token: {
        type: String,
        required: true
    },*/
    usingThisAccount: {
        type: Boolean,
        default: true
    }
}); 

const UserEntity = mongose.model("Users", UserSchema);

const userEntity = {
    User: UserEntity
}

module.exports = userEntity;