package mongoDB;

import com.mongodb.*;
import content.Content;

/**
 * Created by little star on 2017/5/11.
 */
public  abstract  class MongoDB {
    private static Mongo mongo;
    private static DB db;
    public final static void linke()
    {
        mongo=new Mongo("localhost",27017);
        db=mongo.getDB("service");
    }
    public static void main(String[]args) throws Exception
    {

//        ServerAddress serverAddress = new ServerAddress("139.199.158.185",27017);
//        MongoClient client=new MongoClient(serverAddress);
//        MongoDatabase database=client.getDatabase("service");
//         MongoCollection<Document> c= database.getCollection("test");
//        Document query=new Document();
//        query.put("age",20);
//        FindIterable<Document> cursor=c.find(query);
//        for (Document d: cursor) {
//            System.out.println(d.get("name"));
//        }

//        Mongo mongo=new Mongo("139.199.158.185",27017);
//        DB db=mongo.getDB("service");
//        DBCollection c=db.getCollection("User");
//        mongoDB.linke();
//        DBCursor cursor=getUserCollection().find();
//        while(cursor.hasNext())
//        {
//            c.insert(cursor.next());
//        }




    }

    public final static DBCollection getCommentRecordCollection(){
        return db.getCollection("CommentRecord");
    }
    public final static DBCollection getMovieRecordCollection(){
        return db.getCollection("Record");
    }
    public  final static DBCollection getMovieCollection()
    {
        return db.getCollection("Movie");
    }
    public final static DBCollection getCommentCollection()
    {
        return db.getCollection("Comment");
    }
    public final static DBCollection getReleaseTimeCollection()
    {
        return db.getCollection("ReleaseTime");
    }
    public final static DBCollection getUserCollection()
    {
        return db.getCollection("User");
    }
    public final static DBCollection getCharacterRecordCollection()
    {
        return db.getCollection("CharacterRecord");
    }
    public abstract  boolean insert(Content content);
    public abstract  boolean update(Content content);
    public abstract  boolean contain(Content content);
   // public abstract  boolean delete(Content content);
}
