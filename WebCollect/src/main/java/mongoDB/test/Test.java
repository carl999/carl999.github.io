package mongoDB.test;

import mongoDB.*;
import content.*;

import java.util.Iterator;
import java.util.List;

/**
 * Created by little star on 2017/5/11.
 */
public class Test  {
    private void testCharacterDB()
    {
        System.out.println("testCharacterDB");
        CharacterRecord record=new CharacterRecord();
        record.setFailCount(1);
        record.setSucceed(false);
        record.setUrl("url");
        CharacterRecordDB db=new CharacterRecordDB();
        db.insert(record);
        System.out.println(db.contain(record));
        record.setFailCount(3);
        db.update(record);
    }

    private void testCommentDB()
    {
        System.out.println("testCommentDB");
        Comment comment=new Comment();
        comment.setUserName("userName");
        comment.setPraise(1);
        comment.setCommentId("commentId");
        comment.setScore(5);
        comment.setContent("content");
        comment.setMovieId("movieId");
        comment.setStartDate("2017-12-30");
        CommentDB db=new CommentDB();
        db.insert(comment);
        System.out.println(db.contain(comment));
        comment.setStartDate("0000");
        db.update(comment);
    }
    private void testCommentRecordDB()
    {
        System.out.println("testCommentRecordDB");
        CommentRecord commentRecord=new CommentRecord();
        commentRecord.setHasError(true);
        commentRecord.setCommentSucceed(true);
        commentRecord.setUrlStr("urlStr");
        commentRecord.setMovieId("movieId");
        CommentRecordDB db=new CommentRecordDB();
        db.insert(commentRecord);
        System.out.println(db.contain(commentRecord));
        commentRecord.setCommentSucceed(false);
        commentRecord.setHasError(false);
        db.update(commentRecord);
    }

    private void testMovieDB()
    {
        System.out.println("testMovieDB");
        Movie movie=new Movie();
        movie.setHttpImage("httpImage");
        movie.setSummary("summary");
        movie.setLanguage("language");
        movie.setActors("actors");
        movie.setDirector("directors");
        movie.setMovieName("movieName");
        movie.setTags("tags");
        movie.setCountry("country");
        movie.setImdb("imdb");
        movie.setMovieId("movieId");
        movie.setDuration(100);
        movie.setEditors("editors");
        movie.setFiveStar("fiveStar");
        movie.setFourStar("fourStar");
        movie.setThreeStar("threeStar");
        movie.setTwoStar("two");
        movie.setOneStar("oneStar");
        movie.setPeople(12);
        movie.setReleaseTime("releaseTime");
        movie.setScore(-90);
        MovieDB db=new MovieDB();
        db.insert(movie);
        System.out.println(db.contain(movie));
        movie.setLanguage("00000000000000");
        db.update(movie);
    }

    private void testMovieRecordDB()
    {
        MovieRecord record=new MovieRecord();
        record.setMovieFailCount(12);
        record.setPhotosSucceed(false);
        record.setCommentAmount(1234);
        record.setCommentSucceed(false);
        record.setMovieSucceed(false);
        record.setMovieId("movieId");
        MovieRecordDB db=new MovieRecordDB();
        db.insert(record);
        System.out.println(db.contain(record));
        record.setMovieSucceed(true);
        record.setPhotosSucceed(true);
        record.setCommentSucceed(true);
        db.update(record);
    }
    private void testUserDB()
    {
        User user=new User();
        user.setPortraitHttp("httjpg");
        user.setUserName("userName");
        user.setUserHttp("userHttp");
        UserDB db=new UserDB();
        db.insert(user);
        System.out.println(db.contain(user));
        user.setUserName("userName");
        db.update(user);
    }

    public  void testGetFailMovie()
    {
        MovieRecord record=new MovieRecord();
        record.setPhotosSucceed(false);
        record.setCommentAmount(1234);
        record.setCommentSucceed(false);
        record.setMovieSucceed(false);
        MovieRecordDB db=new MovieRecordDB();
        for(int i=0;i<10;i++)
        {
            record.setMovieId("movieId"+i);
            record.setMovieFailCount(i);
            record.setCommentSucceed(true);
            db.update(record);
        }
        record.setMovieSucceed(true);
        for(int i=10;i<20;i++)
        {
            record.setMovieId("movieId"+i);
            record.setMovieFailCount(i-10);
            db.insert(record);
        }

    }

    public static void main(String[]args)
    {
        Test test=new Test();
        MongoDB.linke();
//        test.testCharacterDB();
//        test.testCommentDB();
//        test.testCommentRecordDB();
//        test.testMovieDB();
//        test.testMovieRecordDB();
//        test.testUserDB();
//  test.testGetFailMovie();
        MovieRecordDB db=new MovieRecordDB();
        List<MovieRecord> list=db.getFailMovie(10,3);
        Iterator<MovieRecord> iterator=list.iterator();
        while(iterator.hasNext())
        {
            MovieRecord record=iterator.next();
            System.out.println(record.getMovieId()+" "+record.isCommentSucceed());
        }



    }
}
