package mongoDB;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import content.Movie;
import content.Content;

/**
 * 已经测试
 */

public class MovieDB  extends MongoDB{
    private final static String MOVIEID="movieId";
    private final static String MOVIENAME="movieName";
    private final static String DIRECTOR="director";
    private final static String EDITORS="editors";
    private final static String ACTORS="actors";
    private final static String TAGS="tags";
    private final static String SCORE="score";
    private final static String COUNTRY="country";
    private final static String LANGUAGE="language";
    private final static String DURATION="duration";
    private final static String SUMMARY="summary";
    private final static String IMDB="imdb";
    private final static String IMAGEHTTP="imageHttp";
    private final static String FIVESTAR="fiveStar";
    private final static String FOURSTAR="fourStar";
    private final static String THREESTAR="threeStar";
    private final static String TWOSTAR="twoStar";
    private final static String ONESTAR="oneStar";
    private final static String PEOPLE="people";
    private final static String RELEASETIME="releaseTime";
    @Override
    public boolean contain(Content content) {
        Movie movie=(Movie)content;
        BasicDBObject query=new BasicDBObject();
        query.put("_id",movie.getMovieId());
        DBCollection collection=getMovieCollection();
        DBCursor cursor=collection.find(query);
        if(cursor.hasNext())
            return true;
        else
            return false;
    }

    @Override
    public boolean update(Content content) {
        Movie movie=(Movie)content;
        DBCollection collection=getMovieCollection();
        BasicDBObject query=new BasicDBObject();
        query.put("_id",movie.getMovieId());
        collection.update(query,MovieChangeToDBObject(movie));
        return true;
    }
    public boolean insert(Content content)
    {
        Movie movie=(Movie)content;
        DBCollection collection=getMovieCollection();
        BasicDBObject document=MovieChangeToDBObject(movie);
        collection.insert(document);
        return true;
    }
    private BasicDBObject MovieChangeToDBObject(Movie movie)
    {
        BasicDBObject document=new BasicDBObject();
        document.put(MOVIEID,movie.getMovieId());
        document.put(MOVIENAME,movie.getMovieName());
        document.put(DIRECTOR,movie.getDirector());
        document.put(EDITORS,movie.getEditors());
        document.put(ACTORS,movie.getActors());
        document.put(TAGS,movie.getTags());
        document.put(COUNTRY,movie.getCountry());
        document.put(LANGUAGE,movie.getLanguage());
        document.put(DURATION,movie.getDuration());
        document.put(SUMMARY,movie.getSummary());
        document.put(IMDB,movie.getImdb());
        document.put(IMAGEHTTP,movie.getHttpImage());
        document.put(SCORE,movie.getScore());
        document.put(FIVESTAR,movie.getFiveStar());
        document.put(FOURSTAR,movie.getFourStar());
        document.put(THREESTAR,movie.getThreeStar());
        document.put(TWOSTAR,movie.getTwoStar());
        document.put(ONESTAR,movie.getOneStar());
        document.put(PEOPLE,movie.getPeople());
        document.put(RELEASETIME,movie.getReleaseTime());
        document.put("_id",movie.getMovieId());
        return document;
    }

}
