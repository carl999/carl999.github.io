package crawler;

import mongoDB.MongoDB;
import mongoDB.MovieRecordDB;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import content.MovieRecord;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static crawler.MovieCrawler.moviePattern;

/**
 * 获取电影Id便于后面电影信息爬取
 * 界面连接为 // https://movie.douban.com/tag/   +tag+  ?start=  index  &type=T
 */
public class MovieTaskCrawler extends BreadthCrawler {
    public final static String tempPath="temp/MovieIdCrawler";
    private final static Pattern amountPattern=Pattern.compile("\\(([0-9]*)人");
    private final static String movieCss="table tbody tr.item td div.pl2";


    private int maxPage=50;
    private MongoDB recordDB =new MovieRecordDB();
    private boolean hasError=true;

    public boolean isHasError()
    {
        return hasError;
    }

    public MovieTaskCrawler(List<String> tags) {
        super(tempPath,false);
        if(tags.isEmpty())
            return;
        /*tags.add("喜剧");tags.add("剧情");tags.add("科幻");
        tags.add("动作");tags.add("经典");tags.add("悬疑");tags.add("青春");
        tags.add("犯罪");tags.add("惊悚");tags.add("文艺");tags.add("搞笑");
        tags.add("纪录片");tags.add("励志");tags.add("恐怖");tags.add("战争");
        tags.add("短片");tags.add("魔幻");tags.add("传记");tags.add("情色");
        tags.add("感人");tags.add("暴力");tags.add("家庭");tags.add("音乐");
        tags.add("童年");tags.add("浪漫");tags.add("女性");tags.add("黑帮");
        tags.add("史诗");tags.add("童话");tags.add("西部");tags.add("同志");
        */
        Iterator<String> iterator=tags.iterator();
        while(iterator.hasNext())
        {
            String tag=iterator.next();
            for(int i=0;i<maxPage;i++)
                this.addSeed("https://movie.douban.com/tag/"+tag+"?start="+i*20+"&type=T");
        }
    }

    public void start() throws Exception {
        this.setThreads(1);
        this.setExecuteInterval(5000);
        this.setResumable(false);
        super.start(1);

    }

    public void visit(Page page, CrawlDatums crawlDatums) {
            if (page.matchUrl("https://movie.douban.com/tag/.*")) {
                Elements elements = page.select(movieCss);
                for (Element e : elements) {
                    Element a = e.select("a").first();
                    String url = a.attr("href");
                    Matcher m = moviePattern.matcher(url);
                    if (m.find()) {
                        MovieRecord record = new MovieRecord();
                        record.setMovieId(m.group(1));
                        record.setMovieSucceed(false);

                        String amountStr = e.select("div span.pl").first().text();
                        Matcher matcher=amountPattern.matcher(amountStr);
                        if(matcher.find())
                        {
                            record.setCommentAmount(Integer.parseInt(matcher.group(1)));
                        }else{
                            hasError=true;
                            continue;
                        }
                        hasError = false;
                        if (!recordDB.contain(record)) {
                            recordDB.insert(record);
                        }else{
                            System.out.println("已包含该记录");
                        }
                        //else {
//                            //有问题
//                            recordDB.update(record);
//                        }
                        continue;
                    }
                }
            }

    }

    public int getMaxPage() {
        return maxPage;
    }

    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }
}
