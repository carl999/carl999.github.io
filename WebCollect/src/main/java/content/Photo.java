package content;

import java.util.*;

/**
 * Created by little star on 2017/5/15.
 */
public class Photo implements Content {
    private String movieId;
    private List<PhotoItem> list=new ArrayList<PhotoItem>();
    public static class PhotoItem
    {
        public final String imgHttp;
        public final String content;

        public PhotoItem(String imgHttp, String content) {
            this.imgHttp = imgHttp;
            this.content = content;
        }
    }
    public boolean add(String imgHttp,String content)
    {
       return list.add(new PhotoItem(imgHttp,content));
    }
    public Iterator<PhotoItem> iterator()
    {
        return list.iterator();
    }



    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public int size() {
        return list.size();
    }


}
