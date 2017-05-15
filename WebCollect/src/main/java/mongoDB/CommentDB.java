package mongoDB;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import content.Comment;
import content.Content;

/**
 * 已经测试
 */
public class CommentDB extends MongoDB {
    private static final String COMMENTID="commentId";
    private static final String USERNAME ="userName";
    private static final String SCORE="score";
    private static final String STARTDATE="startDate";
    private static final String CONTENT="content";
    private static final String PRAISE="praise";
    private static final String MOVIEID="movieId";

    public boolean insert(Content content) {
        Comment comment=(Comment)content;
       DBCollection collection= getCommentCollection();
       collection.insert(commentChangeToDBObject(comment));
       return true;
    }

    public boolean update(Content content) {
        Comment comment=(Comment)content;
        DBCollection collection= getCommentCollection();
        BasicDBObject query=new BasicDBObject();
        query.put("_id",comment.getCommentId());
        collection.update(query,commentChangeToDBObject(comment));
        return true;
    }

    @Override
    public boolean contain(Content content) {
        Comment comment=(Comment)content;
        BasicDBObject query=new BasicDBObject();
        query.put("_id",comment.getCommentId());
        DBCollection collection=getCommentCollection();
        DBCursor cursor=collection.find(query);
        if(cursor.hasNext())
            return true;
        else
            return false;
    }

    public BasicDBObject commentChangeToDBObject(Comment comment)
    {
        BasicDBObject document=new BasicDBObject();
        document.put(COMMENTID,comment.getCommentId());
        document.put(USERNAME,comment.getUserName());
        document.put(SCORE,comment.getScore());
        document.put(STARTDATE,comment.getStartDate());
        document.put(CONTENT,comment.getContent());
        document.put(PRAISE,comment.getPraise());
        document.put(MOVIEID,comment.getMovieId());
        document.put("_id",comment.getCommentId());
        return document;
    }
}
