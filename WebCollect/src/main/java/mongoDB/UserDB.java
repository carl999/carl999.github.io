package mongoDB;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import content.Content;
import content.User;

/**
 * 已经测试
 */
public class UserDB extends MongoDB {
    private String USERNAME="userName";
    private String USERHTTP="userHttp";//唯一键
    private String PORTRAITHTTP="portraitHttp";
    public boolean insert(Content content) {
        User user=(User)content;
        DBCollection collection=getUserCollection();
        collection.insert(userToBasicDBObject(user));
        return true;
    }

    private BasicDBObject userToBasicDBObject(User user)
    {
        BasicDBObject document=new BasicDBObject();
        document.put(USERNAME,user.getUserName());
        document.put(USERHTTP,user.getUserHttp());
        document.put(PORTRAITHTTP,user.getPortraitHttp());
        document.put("_id",user.getUserHttp());
        return document;
    }
    public boolean update(Content content) {
        User user=(User)content;
        DBCollection collection=getUserCollection();
        BasicDBObject query=new BasicDBObject();
        query.put("_id",user.getUserHttp());
        collection.update(query,userToBasicDBObject(user));
        return true;
    }

    public boolean contain(Content content) {
        User user=(User)content;
        BasicDBObject query=new BasicDBObject();
        query.put("_id",user.getUserHttp());
        DBCollection collection=getUserCollection();
        if(collection.find(query).hasNext())
        {
            return true;
        }else
            return false;
    }
}
