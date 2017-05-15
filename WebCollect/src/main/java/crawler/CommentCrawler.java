package crawler;

import mongoDB.CommentDB;
import mongoDB.MongoDB;
import mongoDB.UserDB;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import content.Comment;
import content.CommentRecord;
import content.User;
import exception.CommentHandException;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  https://movie.douban.com/subject/ movieId /comments?start=  amount/20  &limit=20&sort=new_score&status=P
* 获得评论信息以及生成 用户记录
 */
public class CommentCrawler extends BreadthCrawler {
    public final static String temp="temp/CommentCrawler";
    private static Pattern scorePattern=Pattern.compile("allstar([0-9]*) rating");
    private static String commentItemCss="div.comment-item";
    
    private MongoDB commentDB=new CommentDB();
    private MongoDB userDB=new UserDB();
    private CommentRecord record;

    public void start() throws Exception {
        this.setThreads(1);
        this.setExecuteInterval(4000);
        this.setResumable(false);
        super.start(1);

    }
    public CommentRecord getRecord()
    {
        return record;
    }



    public CommentCrawler(CommentRecord record) {
        super(temp,false);
        this.record=record;
        this.addSeed(record.getUrlStr());
    }

    public void visit(Page page, CrawlDatums crawlDatums) {
        if(page==null)
            return;
        System.out.println(page.getUrl());
        parseCommentItem(page);
    }
    public void parseCommentItem(Page page) {
        Elements elements=page.select(commentItemCss);
        String movieId=record.getMovieId();
        for(Element e:elements)
        {
            try {
                Comment comment =getComment(e);
                comment.setMovieId(movieId);


                Element userInfor=e.select("div.avatar a").first();
                String userHttp=userInfor.attr("href");
                String userName=userInfor.attr("title");
                String img=userInfor.select("img").first().attr("src");

                User user=new User();
                user.setUserHttp(userHttp);
                user.setPortraitHttp(img);
                user.setUserName(userName);

                comment.setUserName(userName);
                if(comment.hasNullAttr())
                {
                    throw new CommentHandException("some attributions");
                }
                if (!commentDB.contain(comment)) {
                    commentDB.insert(comment);
                }
                if(!userDB.contain(user))
                {
                    userDB.insert(user);
                }

            }catch(Exception ex)
            {
                ex.printStackTrace();
                record.setHasError(true);
                continue;
            }
            record.setCommentSucceed(true);
        }
    }
    private Comment getComment(Element e) throws CommentHandException {
        Comment comment=new Comment();

        comment.setCommentId(e.attr("data-cid"));

        String usefulStr=e.select("div.comment h3 span.comment-vote span.votes").first().text();
        comment.setPraise(Integer.parseInt(usefulStr));
        String timeStr=e.select("div.comment h3 span.comment-info span.comment-time").first().attr("title");
        comment.setStartDate(timeStr);
        if(timeStr==null||timeStr.length()<=0)
            throw new CommentHandException("time");

        String contentStr=e.select("div.comment p").first().text();
        if(contentStr==null||contentStr.length()<0)
        {
            throw new CommentHandException("content");
        }
        comment.setContent(contentStr);

        String scoreStr=e.select("div.comment h3 span.comment-info span[class]").first().className();
        if(scoreStr==null||scoreStr.length()<=0)
        {
            comment.setScore(-1);
        }else{
            Matcher m=scorePattern.matcher(scoreStr);
            if(m.find()) {
                scoreStr = m.group(1);
                comment.setScore(Integer.parseInt(scoreStr));
            }else
            {
                //throw new CommentHandException("score");
                comment.setScore(-1);
            }
        }
        return comment;

    }


}
