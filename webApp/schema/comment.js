let mongoose = require('mongoose')

let commentSchema = new mongoose.Schema({
	name: {type: String},
	email: {type: String},
	content: {type: String},
})

commentSchema.statics = {
	fetch: function(cb){
		return this
			.find({})
			.exec(cb)
	},

	findById: function(id,cb){
		return this
			.findOne({_id: id})
			.exec(cb);
	}
}

module.exports = commentSchema;