var express = require('express');
var path = require('path');
var favicon = require('serve-favicon');
var logger = require('morgan');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');
let mongoose = require('mongoose')
let Comment = require('./modules/comment')
let formidable = require('formidable');
let Movie = require('./modules/movie')

mongoose.Promise = global.Promise;
mongoose.connect('mongodb://139.199.158.185:27017/service');

var aboutUS = require('./routes/aboutUS');
var contact = require('./routes/contact');
let detail = require('./routes/detail');
let movies = require('./routes/movies');
let aMovieChart = require('./routes/aMovieChart');
let home = require('./routes/home');


var app = express();

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'jade');

// uncomment after placing your favicon in /public
//app.use(favicon(path.join(__dirname, 'public', 'favicon.ico')));
app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());


app.use('/detail',express.static(path.join(__dirname, 'public')));

app.use(express.static(path.join(__dirname, 'public')));


app.use('/', movies);
app.use('/aboutUS', aboutUS);
app.use('/contact',contact);
app.use('/detail',detail);
app.use('/home',home);

app.route('/comment')
	.get(function(req,res){
		res.render('comment')
	})
	.post(function(req,res){
        let id = req.body._id
        new Comment({
          name: req.body.name,
          email:req.body.email,
          content: req.body.content,
        }).save()
		res.redirect(303,'/thanks')
	})


// catch 404 and forward to error handler
app.use(function(req, res, next) {
  var err = new Error('Not Found');
  err.status = 404;
  next(err);
});

// error handler
app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.render('error');
});

module.exports = app;
