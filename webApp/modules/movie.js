/**
 * Created by loick on 10/05/2017.
 */
let mongoose = require('mongoose');
let movieSchema = require('../schema/movie');

let Movie = mongoose.model('Movie',movieSchema);

module.exports = Movie;