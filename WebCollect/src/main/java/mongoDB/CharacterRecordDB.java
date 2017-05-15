package mongoDB;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import content.CharacterRecord;
import content.Content;

import javax.naming.Name;

/**
 * 已经测试
 */
public class CharacterRecordDB extends MongoDB {
    private static final String URL="url";
    private static final String SUCCEED="succeed";
    private static final String FAILCOUNT="failCount";
    private static final String NAME="name";

    private BasicDBObject contentToDBObject(CharacterRecord record)
    {
        BasicDBObject document=new BasicDBObject();
        document.put(URL,record.getUrl());
        document.put(SUCCEED,record.isSucceed());
        document.put(FAILCOUNT,record.getFailCount());
        document.put(NAME,record.getName());
        document.put("_id",record.getUrl());
        return document;
    }
    public boolean insert(Content content) {
        CharacterRecord record=(CharacterRecord)content;
        DBCollection collection=getCharacterRecordCollection();
        collection.insert(contentToDBObject(record));
        return true;
    }

    public boolean update(Content content) {
        CharacterRecord record=(CharacterRecord)content;
        DBCollection collection=getCharacterRecordCollection();
        BasicDBObject query=new BasicDBObject();
        query.put("_id",record.getUrl());
        collection.update(query,contentToDBObject(record));
        return true;

    }

    public boolean contain(Content content) {
        CharacterRecord record=(CharacterRecord)content;
        DBCollection collection=getCharacterRecordCollection();
        BasicDBObject query=new BasicDBObject();
        query.put("_id",record.getUrl());
        if(collection.find(query).hasNext())
            return true;
        else
            return false;
    }
}
