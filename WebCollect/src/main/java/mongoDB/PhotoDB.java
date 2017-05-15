package mongoDB;

import com.mongodb.BasicDBObject;
import content.Content;
import content.Photo;

import java.util.Iterator;

/**
 *
 */
public class PhotoDB extends MongoDB{
    private final static String MOVIEID="movieId";
    private final static String SIZE="size";
    private final static String IMGHTTP="imgHttp";//使用时加序号
    private final static String IMGCONTENT="imgContent";//使用时加序号
    public boolean insert(Content content) {
        return false;
    }

    public boolean update(Content content) {
        return false;
    }

    public boolean contain(Content content) {
        return false;
    }

    private BasicDBObject photoToBasicDBObject(Photo photo)
    {
        BasicDBObject document=new BasicDBObject();
        document.put("_id",photo.getMovieId());
        document.put(MOVIEID,photo.getMovieId());
        document.put(SIZE,photo.size());
        Iterator<Photo.PhotoItem> itemIterator=photo.iterator();
        int index=0;
        while(itemIterator.hasNext())
        {
            Photo.PhotoItem item=itemIterator.next();
            document.put(IMGHTTP+index,item.imgHttp);
            document.put(IMGCONTENT+index,item.content);
            index++;
        }
        return document;
    }
}
