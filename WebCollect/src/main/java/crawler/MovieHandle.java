package crawler;

import content.CharacterRecord;
import content.User;
import mongoDB.*;
import cn.edu.hfut.dmic.webcollector.model.Page;
import content.Movie;
import exception.MovieHandException;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by little star on 2017/5/7.
 */
public class MovieHandle {

    private static final String nameCss="#wrapper #content h1 span[property=v:itemreviewed]";
    private static final String inforParentCss=".article .indent.clearfix .subjectwrap.clearfix .subject.clearfix";
    private static final String imageCss="div#mainpic a.nbgnbg img";
    private static final String inforCssBaseOnInforParentCss="div#info";
    private static final Pattern countryPattern=Pattern.compile("制片国家/地区:[ ]*(.*) 语言:");
    private static final Pattern languagePattern=Pattern.compile("语言:[ ]*(.*) 上映日期:");
    private static final Pattern languagePattern2=Pattern.compile("语言:[ ]*(.*)首播");

    public static final String separator=",";

    private Element directorElement=null;
    private Element editorElement=null;
    private Element actorElement=null;

    private MongoDB movieDB =new MovieDB();
    private MongoDB characterRecordDB=new CharacterRecordDB();

    /**
     * 得到directorElement  editorElement actorElement
     */
    private void getThreeElements(Element infor)throws MovieHandException
    {
        Elements p1=infor.select("span span");
        int i=0;
        while(editorElement==null||directorElement==null||actorElement==null)
        {
            if(i>=p1.size())
            {
                throw new MovieHandException("director,editor,actor");
            }
            Element p1Child=p1.get(i);
            if(p1Child.text().equals("导演"))
            {
                directorElement=p1Child.parent();
            }else if(p1Child.text().equals("编剧")){
                editorElement=p1Child.parent();
            }else if(p1Child.text().equals("主演"))
            {
                actorElement=p1Child.parent();
            }
            i++;
        }
    }

    public void parseMovieInformation(Page page,String movieId) throws MovieHandException {
        String text=page.select("body").first().text();
        if(text.contains("单集片长")||text.contains("季数")||text.contains("集数"))
            return ;
        Movie movie=new Movie();
        movie.setMovieId(movieId);
        StringBuffer releaseTimeStr=null;
        String name = page.select(nameCss).text();
        movie.setMovieName(name);

        Element inforElement = page.select(inforParentCss).first();
        String httpImage = inforElement.select(imageCss).attr("src");
        movie.setHttpImage(httpImage);

        inforElement = inforElement.select(inforCssBaseOnInforParentCss).first();

        //没错过
        getThreeElements(inforElement);
        movie.setDirector(getDirector());//同时生成人物url
        movie.setEditors(getEditors());//同时生成人物url
        movie.setActors(getActors());//同时生成人物url
        movie.setTags(getTags(inforElement));
        movie.setSummary(getSummary(page));

        //可能错
        movie.setCountry(getCountry(inforElement));
        movie.setImdb(getImdb(inforElement));

        //经常错
        movie.setLanguage(getLanguage(inforElement));
        movie.setDuration(getDuration(inforElement));

        //不清楚
        movie.setScore(getScore(page));

        String star[]=getStar(page);
        movie.setFiveStar(star[4]);
        movie.setFourStar(star[3]);
        movie.setThreeStar(star[2]);
        movie.setTwoStar(star[1]);
        movie.setOneStar(star[0]);

        releaseTimeStr = getReleaseTime(inforElement,movieId);
        if(releaseTimeStr.length()<=0)
        {
            movie.setReleaseTime(null);
        }
        else{
            movie.setReleaseTime(new String(releaseTimeStr));
        }
        movie.setPeople(getPeople(page));


        if(movieDB.contain(movie))
        {
            movieDB.update(movie);
        }else{
            movieDB.insert(movie);
        }

    }

    private int getPeople(Page page)throws  MovieHandException
    {
        Elements elements=page.select("#interest_sectl div.rating_wrap.clearbox div.rating_self.clearfix div.rating_right div.rating_sum a span");
        String str=elements.first().text();
        try {
            int p = Integer.parseInt(str);
            return p;
        }catch(Exception e)
        {
            e.printStackTrace();
            throw new MovieHandException("People");
        }

    }


    private String[] getStar(Page page)throws MovieHandException
    {
        String result[]=new String[5];
        Elements elements=page.select("#interest_sectl div.rating_wrap.clearbox div.ratings-on-weight div.item");
        Iterator<Element> iterator=elements.iterator();
        while(iterator.hasNext())
        {
            Element element=iterator.next();
            if(element.text().contains("5星"))
            {
                result[4]=element.select("span.rating_per").first().text();
                continue;
            }else if(element.text().contains("4星"))
            {
                result[3]=element.select("span.rating_per").first().text();
                continue;
            }else if(element.text().contains("3星"))
            {
                result[2]=element.select("span.rating_per").first().text();
                continue;
            }else if(element.text().contains("2星"))
            {
                result[1]=element.select("span.rating_per").first().text();
                continue;
            }else if(element.text().contains("1星"))
            {
                result[0]=element.select("span.rating_per").first().text();
                continue;
            }
            throw new MovieHandException("star");
        }
        if(result[0]==null||result[1]==null||result[2]==null||result[3]==null||result[4]==null)
            throw new MovieHandException("star");
        return result;
    }

    private float getScore(Page page)throws  MovieHandException
    {
        Elements es=page.select("#interest_sectl div.rating_wrap.clearbox div.rating_self.clearfix strong.ll.rating_num");
        String scoreTxt=es.first().text();
        try {
            float score = Float.parseFloat(scoreTxt);
            return score;
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        throw new MovieHandException("score");

    }

    private String getLanguage(Element info)throws MovieHandException
    {
        Matcher m=languagePattern.matcher(info.text());
        if(m.find())
        {
            return m.group(1);
        }
        m=languagePattern2.matcher(info.text());
        if(m.find())
        {
            return m.group(1);
        }
        throw new MovieHandException("Language");
    }

    private String getCountry(Element info)throws MovieHandException
    {
        Matcher m=countryPattern.matcher(info.text());
        if(m.find())
        {
            return m.group(1);
        }
        throw new MovieHandException("Country");
    }

    private String getImdb(Element info)throws MovieHandException
    {
        Elements es=info.select("a[rel=nofollow]");
        Iterator<Element>iterator=es.iterator();
        while(iterator.hasNext())
        {
            Element e=iterator.next();
            if(e.attr("href").contains("http://www.imdb.com/title/"))
                return e.text();
        }
       return null;
    }

    private String getSummary(Page page)throws MovieHandException
    {
        Element e=page.select("div.related-info div.indent span[property=v:summary]").first();
        if(e.text().length()<=1||e.text()==null)
            throw new MovieHandException("Summary");
        return e.text();
    }
    private int getDuration(Element info)throws MovieHandException
    {
        Element e=info.select("span[property=v:runtime]").first();
        try {
            int i = Integer.parseInt(e.attr("content"));
            return i;
        }catch(NumberFormatException nx)
        {

        }
        throw new MovieHandException("Duration");
    }

    private StringBuffer getReleaseTime(Element info,String id)throws MovieHandException
    {
        StringBuffer str=new StringBuffer();

        for(Element e:info.select("span[property=v:initialReleaseDate]"))
        {
            String text=e.text();
            if(str.length()<=1)
            {
                str.append(text);
            }else{
                str.append(","+text);
            }

        }
        if(str.length()<=0)
            throw new MovieHandException("ReleaseTime");
        return str;
    }

    /**
     *  获得标签
     * @return
     */

    private String getTags(Element infor)throws MovieHandException
    {
        StringBuffer tags=new StringBuffer();
        for(Element e:infor.select("span[property=v:genre]"))
        {
            if(tags.length()<=0)
                 tags.append(e.text());
            else
            {
                tags.append(separator+e.text());
            }
        }
        if(tags.length()<0)
        {
            throw new MovieHandException("tags");
        }
        return new String(tags);
    }

    /**
     *  获得编剧
     * @return
     */
    private String getEditors()throws MovieHandException
    {
        String editor=null;
        for(Element e:editorElement.select("span.attrs a"))
        {
            CharacterRecord characterRecord=new CharacterRecord();
            characterRecord.setUrl("https://movie.douban.com"+e.attr("href"));
            characterRecord.setName(e.text());
            characterRecordDB.insert(characterRecord);
            if(editor==null)
            {
                editor=e.text();
            }else{
                editor=editor+separator+e.text();
            }
        }
        if(editor==null||editor.length()<0)
        {
            throw new MovieHandException("editor");
        }
        return editor;
    }

    /**
     * 获得导演
     * @return
     */
    private String getDirector()throws MovieHandException
    {
        String directors=null;
        for(Element e:directorElement.select("span.attrs a"))
        {
            CharacterRecord characterRecord=new CharacterRecord();
            characterRecord.setUrl("https://movie.douban.com"+e.attr("href"));
            characterRecord.setName(e.text());
            characterRecordDB.insert(characterRecord);
            if(directors==null)
            {
                directors=e.text();
            }else{
                directors=directors+separator+e.text();
            }
        }
        if(directors==null||directors.length()<0)
        {
            throw new MovieHandException("directors");
        }
        return directors;
    }

    /**
     * 获得主演
     * @return
     */
    private String getActors()throws MovieHandException
    {
        StringBuffer actors=new StringBuffer();
        for(Element e:actorElement.select("span.attrs a"))
        {
            CharacterRecord characterRecord=new CharacterRecord();
            characterRecord.setUrl("https://movie.douban.com"+e.attr("href"));
            characterRecord.setName(e.text());
            characterRecordDB.insert(characterRecord);
            if(actors.length()<=0)
            {
                actors.append(e.text());
            }else{
                actors.append(separator+e.text());
            }
        }
        if(actors.length()<=0)
        {
            throw new MovieHandException("actors");
        }
        return new String(actors);
    }
}
