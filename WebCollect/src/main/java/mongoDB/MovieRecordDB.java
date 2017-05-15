package mongoDB;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import content.MovieRecord;
import content.Content;

import java.util.ArrayList;
import java.util.List;

/**
 *已经测试
 */
public class MovieRecordDB extends MongoDB {
    private static final String MOVIEID="movieId";
    private static final String MOVIESUCCEED="movieSucceed";
    private static final String COMMENTSUCCEED="commentSucceed";
    private static final String PHOTOSUCCEE="photoSucceed";
    private static final String MOVIEFAILCOUNT="movieFailCount";
    private static final String COMMENTAMOUNT="commentAmount";
    private static final String PHOTOFAILCOUNT="photoFailCount";
    @Override
    public boolean insert(Content content) {
        MovieRecord record=(MovieRecord)content;
        DBCollection collection= getMovieRecordCollection();
        BasicDBObject document=recordToDBObject(record);
        collection.insert(document);
        return true;
    }

    @Override
    public boolean update(Content content) {
        MovieRecord record=(MovieRecord)content;
        DBCollection collection= getMovieRecordCollection();
        BasicDBObject document=recordToDBObject(record);
        BasicDBObject query=new BasicDBObject();
        query.put("_id",record.getMovieId());
        collection.update(query,document);
        return true;
    }

    public boolean contain(Content content) {
        MovieRecord record = (MovieRecord) content;
        BasicDBObject query = new BasicDBObject();
        query.put("_id", record.getMovieId());
        DBCollection collection = getMovieRecordCollection();
        DBCursor cursor = collection.find(query);
        if (cursor.hasNext())
            return true;
        else
            return false;
    }

    public List<MovieRecord> getFailMovie(int n,int maxFailCount)
    {
        DBCollection collection= getMovieRecordCollection();

        BasicDBObject lt=new BasicDBObject("$lt",maxFailCount);
        BasicDBObject queryObject = new BasicDBObject();
        queryObject.put(MOVIEFAILCOUNT,lt);
        queryObject.put(MOVIESUCCEED,false);

        DBCursor cursor=collection.find(queryObject);
        cursor.sort(new BasicDBObject(MOVIEFAILCOUNT,1));   //小到大输出
        cursor.limit(n);
        List<MovieRecord>list=new ArrayList<MovieRecord>();
        while(cursor.hasNext())
        {
            list.add(DBObjectToRecord(cursor.next()));
        }
        return list;
    }
    public List<MovieRecord> getAllFailComment()
    {
        DBCollection collection= getMovieRecordCollection();

        BasicDBObject queryObject = new BasicDBObject();
        queryObject.put(MOVIESUCCEED,false);

        DBCursor cursor=collection.find(queryObject);
        cursor.sort(new BasicDBObject(MOVIEFAILCOUNT,1));//小到大输出
        List<MovieRecord>list=new ArrayList<MovieRecord>();
        while(cursor.hasNext())
        {
            list.add(DBObjectToRecord(cursor.next()));
        }
        return list;
    }
    private MovieRecord DBObjectToRecord(DBObject document)
    {
        MovieRecord record=new MovieRecord();
        record.setMovieId((String)document.get(MOVIEID));
        record.setCommentSucceed((Boolean)document.get(COMMENTSUCCEED));
        record.setMovieSucceed((Boolean)document.get(MOVIESUCCEED));
        record.setCommentAmount((Integer)document.get(COMMENTAMOUNT));
        record.setPhotosSucceed((Boolean)document.get(PHOTOSUCCEE));
        record.setMovieFailCount((Integer)document.get(MOVIEFAILCOUNT));
        record.setPhotosFailCount((Integer)document.get(PHOTOFAILCOUNT));
        return record;
    }

    private BasicDBObject recordToDBObject(MovieRecord record)
    {
        BasicDBObject document=new BasicDBObject();
        document.put(MOVIEID,record.getMovieId());
        document.put(MOVIESUCCEED,record.isMovieSucceed());
        document.put(COMMENTSUCCEED,record.isCommentSucceed());
        document.put(COMMENTAMOUNT,record.getCommentAmount());
        document.put(MOVIEFAILCOUNT,record.getMovieFailCount());
        document.put(PHOTOSUCCEE,record.isPhotosSucceed());
        document.put(PHOTOFAILCOUNT,record.getPhotosFailCount());
        document.put("_id",record.getMovieId());
        return document;
    }

}
