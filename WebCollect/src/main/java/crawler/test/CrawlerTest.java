package crawler.test;

import content.CommentRecord;
import content.MovieRecord;
import crawler.CommentCrawler;
import crawler.MovieCrawler;
import crawler.MovieTaskCrawler;
import mongoDB.MongoDB;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by little star on 2017/5/14.
 */
public class CrawlerTest {
    private static void testMovieCrawler()
    {
        MovieRecord record=new MovieRecord();
        record.setMovieId("10574468");
        record.setMovieFailCount(0);
        record.setCommentAmount(1000);
        MovieCrawler crawler=new MovieCrawler(record);
        try {
            crawler.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void testCommentCrawler()
    {
        CommentRecord record=new CommentRecord();
        record.setMovieId("10574468");
        record.setUrlStr("https://movie.douban.com/subject/10574468/comments?start=24&limit=20&sort=new_score&status=P");
        CommentCrawler commentCrawler=new CommentCrawler(record);
        try {
            commentCrawler.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void testMovieTaskCrawler()
    {
        List<String> tags=new LinkedList<String>();
        tags.add("喜剧");
        MovieTaskCrawler task=new MovieTaskCrawler(tags);
        task.setMaxPage(1);
        try {
            task.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[]args)
    {
       // MongoDB.linke();
        //testMovieCrawler();
       // testCommentCrawler();
        //testMovieTaskCrawler();



    }
}
