let express = require('express');
let router = express.Router();

router.get('/',function(req,res){
    

    res.render('home',{
        one:100,
        two:100,
        three:100,
        four:100,
        five:150
    });
})

module.exports = router;


