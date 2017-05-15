package generator;

import content.CommentRecord;
import content.MovieRecord;
import crawler.CommentCrawler;
import crawler.MovieCrawler;
import crawler.MovieTaskCrawler;
import mongoDB.CommentRecordDB;
import mongoDB.MovieRecordDB;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 */
public class Generator {
    //没问题
    public void startMovieTaskCrawler()
    {
        List<String> tags=new ArrayList<String>();
        tags.add("喜剧");tags.add("剧情");tags.add("科幻");
        tags.add("动作");tags.add("经典");tags.add("悬疑");tags.add("青春");
        tags.add("犯罪");tags.add("惊悚");tags.add("文艺");tags.add("搞笑");
        tags.add("纪录片");tags.add("励志");tags.add("恐怖");tags.add("战争");
        tags.add("短片");tags.add("魔幻");tags.add("传记");tags.add("情色");
        tags.add("感人");tags.add("暴力");tags.add("家庭");tags.add("音乐");
        tags.add("童年");tags.add("浪漫");tags.add("女性");tags.add("黑帮");
        tags.add("史诗");tags.add("童话");tags.add("西部");tags.add("同志");

    MovieTaskCrawler taskCrawler=new MovieTaskCrawler(tags);
    try {
        taskCrawler.setMaxPage(100);
        taskCrawler.start();
    } catch (Exception e) {
        e.printStackTrace();
    }
    }
    //无问题
    public void updateCommentRecord() {
        MovieRecordDB movieDb=new MovieRecordDB();
        CommentRecordDB commentRecordDB = new CommentRecordDB();
        List<MovieRecord> movieRecordList = movieDb.getAllFailComment();
        Iterator<MovieRecord> iterator = movieRecordList.iterator();
        while (iterator.hasNext()) {
            MovieRecord record = iterator.next();
            CommentRecord commentRecord = new CommentRecord();
            for (int i = 0; i < 10; i++) {
                commentRecord.setMovieId(record.getMovieId());
                commentRecord.setUrlStr("https://movie.douban.com/subject/" + record.getMovieId() + "/comments?start=" + (i * 20) + "&limit=20&sort=new_score&status=P");
                commentRecordDB.insert(commentRecord);
            }
            record.setCommentSucceed(true);
            movieDb.update(record);
        }
    }

    public void startCommentCrawler()
    {
        CommentRecordDB recordDB=new CommentRecordDB();
        int continueFailCount=0;
        int sleep=0;
        int maxFailCount=4;
        List<CommentRecord> list=recordDB.getFailComment(10,10);
        while(!list.isEmpty())
        {
            Iterator<CommentRecord>iterator=list.iterator();
            while(iterator.hasNext())
            {
                CommentCrawler crawler=new CommentCrawler(iterator.next());
                try {
                    crawler.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                CommentRecord record=crawler.getRecord();
                if(record.isCommentSucceed())
                {
                    record.setFailCount(0);
                    continueFailCount=0;
                }else{
                    record.setFailCount(record.getFailCount()+1);
                    continueFailCount++;
                }
                recordDB.update(record);
                if(continueFailCount>=maxFailCount)
                {
                    sleep++;
                    try {
                        Thread.sleep(1000*60*60*sleep);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            list=recordDB.getFailComment(10,10);
        }
    }

    public void startMovieCrawler()
    {

        MovieRecordDB movieDb=new MovieRecordDB();
        List<MovieRecord>list=movieDb.getFailMovie(10,3);
        int continueFailCount=0;
        int sleep=0;
        while(!list.isEmpty())
        {
            Iterator<MovieRecord> iterator=list.iterator();
            while(iterator.hasNext())
            {
                MovieCrawler crawler=new MovieCrawler(iterator.next());
                try {
                    crawler.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                MovieRecord record=crawler.getRecord();
                if(record.isMovieSucceed())
                {
                    record.setMovieFailCount(record.getMovieFailCount()+1);
                }
                movieDb.update(record);
                if(record.isMovieSucceed())
                {
                    continueFailCount=0;
                }else{
                    continueFailCount++;
                }
                if(continueFailCount>=5)
                {
                    sleep++;
                    try {
                        Thread.sleep(1000*60*60*sleep);
                        continue;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else{
                    sleep=0;
                }
            }
            iterator=null;
            list=movieDb.getFailMovie(10,3);
        }
    }
}
