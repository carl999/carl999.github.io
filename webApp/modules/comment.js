let mongoose = require('mongoose')
let commentSchema = require('../schema/comment')

let Comment = mongoose.model('Comment',commentSchema)

module.exports = Comment;