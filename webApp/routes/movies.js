var express = require('express');
var router = express.Router();
let Movie = require('../modules/movie');


/* GET users listing. */
router.get('/', function(req, res, next) {
    Movie.findByCountry("法国",function(err,movieslist){
        if(err){
          console.log(err);
        }else{
            console.log(movieslist.length);
          res.render('movies',{
            movies:movieslist,
          });
        }
    })
});
var types=["爱情", "喜剧", "剧情", "动画", "科幻", "动作", "经典", "悬疑", "青春", "犯罪", "惊悚", "文艺", "搞笑", "纪录片", "励志", "恐怖",
    "战争", "短片", "黑色幽默", "魔幻", "传记", "情色", "感人", "暴力", "家庭", "音乐", "动画短片", "童年", "浪漫", "女性",
    "黑帮", "同志", "烂片", "史诗", "童话", "西部"];
module.exports = router;
