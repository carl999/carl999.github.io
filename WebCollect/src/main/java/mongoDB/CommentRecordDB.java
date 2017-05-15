package mongoDB;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import content.CommentRecord;
import content.Content;

import java.util.ArrayList;
import java.util.List;

/**
 * 已经测试
 */


public class CommentRecordDB extends MongoDB{
    private final static String URLSTR="urlStr";
    private final static String COMMENTSUCCEED="commentSucceed";
    private final static String HASERROR="hasError";
    private final static String MOVIEID="movieId";
    private final static String FAILCOUNT="failCount";
    @Override
    public boolean insert(Content content) {
        CommentRecord record=(CommentRecord)content;
        DBCollection collection=getCommentRecordCollection();
        collection.insert(commentRecordToDBObject(record));
        return true;
    }

    @Override
    public boolean update(Content content) {
        CommentRecord record=(CommentRecord)content;
        DBCollection collection=getCommentRecordCollection();
        BasicDBObject query=new BasicDBObject();
        query.append("_id",record.getUrlStr());
        collection.update(query,commentRecordToDBObject(record));
        return true;
    }

    @Override
    public boolean contain(Content content) {
        CommentRecord record=(CommentRecord)content;
        DBCollection collection=getCommentRecordCollection();
        BasicDBObject query=new BasicDBObject();
        query.append("_id",record.getUrlStr());
        if(collection.find(query).count()>=0)
        {
            return true;
        }else
        {
            return false;
        }

    }
    public BasicDBObject commentRecordToDBObject(CommentRecord record)
    {
        BasicDBObject document= new BasicDBObject();
        document.put(URLSTR,record.getUrlStr());
        document.put(COMMENTSUCCEED,record.isCommentSucceed());
        document.put(HASERROR,record.isHasError());
        document.put(MOVIEID,record.getMovieId());
        document.put("_id",record.getUrlStr());
        document.put(FAILCOUNT,record.getFailCount());
        return document;
    }
    public CommentRecord dBObjectToCommentRecord(DBObject document)
    {
        CommentRecord record=new CommentRecord();
        record.setUrlStr((String)document.get(URLSTR));
        record.setCommentSucceed((Boolean)document.get(COMMENTSUCCEED));
        record.setHasError((Boolean)document.get(HASERROR));
        record.setMovieId((String)document.get(MOVIEID));
        record.setFailCount((Integer)document.get(FAILCOUNT));
        return record;
    }

    public List<CommentRecord> getFailComment(int n,int maxFailCount)
    {
        List<CommentRecord>list=new ArrayList<CommentRecord>();
        BasicDBObject queryObject = new BasicDBObject();
        queryObject.put(COMMENTSUCCEED,false);
        BasicDBObject lt=new BasicDBObject("$lt",maxFailCount);
        queryObject.put(FAILCOUNT,lt);
        DBCollection collection=getCommentRecordCollection();
        DBCursor cursor=collection.find(queryObject);
        cursor.sort(new BasicDBObject(FAILCOUNT,1));
        cursor.limit(n);
        while(cursor.hasNext())
        {
            list.add(dBObjectToCommentRecord(cursor.next()));
        }
        return list;
    }


}
