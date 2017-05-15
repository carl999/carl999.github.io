/**
 * Created by loick on 11/05/2017.
 */
let express = require('express');
let Movie = require('../modules/movie');

let router = express.Router();

router.get('/:id',function(req,res,next){
     let id = req.params.id;
    Movie.findById(id,function(err,amovie){
        if(err){
            console.log(err);
        }else{
            res.render('aMovieChart',{
                movie:amovie
            });
        }
    })
})

module.exports = router;

