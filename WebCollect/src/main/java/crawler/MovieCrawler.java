package crawler;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import content.MovieRecord;
import exception.MovieHandException;

import java.sql.SQLException;
import java.util.regex.Pattern;

/**
 * 电影信息获得 界面连接为https://movie.douban.com/subject/+movieId
 * 生成人物 记录
 */
public class MovieCrawler extends BreadthCrawler{
    public static final String tempFile="temp/MovieCrawler";
    public final static Pattern moviePattern=Pattern.compile("https://movie.douban.com/subject/([0-9]*)");

    private MovieHandle movieHandle=new MovieHandle();
    private MovieRecord record;


    public MovieRecord getRecord()
    {
        return record;
    }
    public void visit(Page page, CrawlDatums crawlDatums) {
        if(page.matchUrl("https://movie.douban.com/subject/.*"))
        {
            try {
                movieHandle.parseMovieInformation(page,record.getMovieId());
                record.setMovieSucceed(true);
            } catch (MovieHandException e) {
                //出错处理
                record.setMovieSucceed(false);
                e.printStackTrace();
            } catch(Exception e)
            {   //出错处理
                record.setMovieSucceed(false);
                e.printStackTrace();
            }

        }
    }
    public void start() throws Exception {
        this.setThreads(1);
        this.setExecuteInterval(4000);
        super.start(1);

    }
    public MovieCrawler(MovieRecord record) {
        super(tempFile,false);
        this.record=record;
        this.addSeed("https://movie.douban.com/subject/"+record.getMovieId()+"/");
    }
}
