package crawler;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import content.MovieRecord;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Iterator;

/**
 * 获得电影的海报 更新MovieRecord 的photoSucceed 和photoFailCount 但是不插入数据库
 * 生成photo内容插入数据库
 * https://movie.douban.com/subject/26816383/photos?type=R
 */
public class PhotoCrawler extends BreadthCrawler {
    public static final String temp="temp/PhotoCrawler";
    private static final String photoItemCss="div.article ul.poster-col4.clearfix li";

    private MovieRecord record;

    public PhotoCrawler(MovieRecord record) {
        super(temp,false);
        this.record=record;
        if(!record.isPhotosSucceed())
            this.addSeed("https://movie.douban.com/subject/"+record.getMovieId()+"/photos?type=R");
    }
    public void start() throws Exception {
        this.setThreads(1);
        this.setExecuteInterval(4000);
        super.start(1);
    }
    public void visit(Page page, CrawlDatums crawlDatums) {
        Elements elements=page.select(photoItemCss);
        Iterator<Element>iterator=elements.iterator();
        while(iterator.hasNext())
        {

        }

    }
}
