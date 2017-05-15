/**
 * Created by loick on 10/05/2017.
 */
let mongoose = require('mongoose');

let movieSchema = new mongoose.Schema(
    {
        movieId:{type:String},
        movieName:{type:String},
        director:{type:String},
        editors:{type:String},
        actors:{type:String},
        tags:{type:String},
        country:{type:String},
        language:{type:String},
        duration:{type:String},
        summary:{type:String},
        imdb:{type:String},
        imageHttp:{type:String},
    }
)

movieSchema.statics = {
    fetch: function(cb){
        return this
            .find()
            .exec(cb)

    },
    findById:function(id,cb){
        return this
            .findOne({movieId:id})
            .exec(cb)
    },
    findByCountry:function(country,cb){
        return this
            .find({country:country})
            .exec(cb)
    }
}

module.exports = movieSchema;